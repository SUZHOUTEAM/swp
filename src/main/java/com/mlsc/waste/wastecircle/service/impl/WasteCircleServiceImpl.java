package com.mlsc.waste.wastecircle.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.waste.fw.service.SysCantonService;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.ParamDataException;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.im.service.ImService;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityDetailReleaseService;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityReleaseService;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.licence.dao.LicenceDao;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.MapUtil;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.utils.PropertyUtil;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.mapper.CooperationMessageMapper;
import com.mlsc.waste.wastecircle.model.CoopBusVo;
import com.mlsc.waste.wastecircle.model.CoopMsg;
import com.mlsc.waste.wastecircle.model.CooperationRelation;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.waste.wastecircle.model.MessageBodyVo;
import com.mlsc.waste.wastecircle.model.SearchCondition;
import com.mlsc.waste.wastecircle.service.CoopMsgService;
import com.mlsc.waste.wastecircle.service.CooperationRelationService;
import com.mlsc.waste.wastecircle.service.WasteCircleService;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.service.WasteService;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import com.mlsc.yifeiwang.licence.service.IDispositionCapacitydetailReleaseService;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.entwaste.model.EntWasteParams;
import com.mlsc.yifeiwang.entwaste.service.IEntWasteService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("wasteCircleService")
public class WasteCircleServiceImpl implements WasteCircleService {
    private final static Logger logger = LoggerFactory.getLogger(WasteCircleServiceImpl.class);

    @Autowired
    private CoopMsgService coopMsgService;

    @Autowired
    private ImService imService;

    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private UserExtendedService userExtendedService;

    @Autowired
    private UserService userService;

    @Autowired
    private WasteService wasteService;

    @Autowired
    private CooperationRelationService followService;

    @Autowired
    private DispositionCapacityDetailReleaseService dispositionCapacityDetailReleaseService;

    @Autowired
    private DispositionCapacityReleaseService dispositionCapacityReleaseService;

    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    public PlatformSupporter platformSupporter;


    @Autowired
    private LicenceDao licenceDao;

    @Autowired
    private IDispositionCapacitydetailReleaseService capacitydetailReleaseService;

    @Autowired
    private IEntWasteService entWasteService;

    @Autowired
    private CooperationMessageMapper cooperationMessageMapper;

    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;

    @Override
    public List<MessageBodyVo> getMessageListReleaseList(SearchCondition searchCondition, String ticketId, User user, PagingParameter pagingParameter)
            throws Exception {
        List<MessageBodyVo> messageList = new ArrayList<MessageBodyVo>();
        initPagingParameter(pagingParameter);
        searchCondition.setStartRowIndex(pagingParameter.getStart());
        searchCondition.setRows(pagingParameter.getLimit());
        RPCSysEnterpriseBase enterpriseInfo = enterpriseService.getEnterpriseInfoById(ticketId, user.getEnterpriseId());
        String cantonCode = enterpriseInfo.getCantonCode();
        searchCondition.setCantonCode(cantonCode);
        if (enterpriseInfo != null) {
            String wasteCode = searchCondition.getCodeWaste();
            if (StringUtils.isNotBlank(wasteCode)) {
                if (wasteCode.length() == 4) {
                    wasteCode = wasteCode.substring(2, wasteCode.length());
                    searchCondition.setCodeWaste(wasteCode);
                }
            }
            if (StringUtils.isNotBlank(searchCondition.getArea())) {
                String area = Util.calculateCantonCode(searchCondition.getArea());
                searchCondition.setArea(area);
            }
            RPCSysEnterpriseBaseVo enterpriseBaseVo = enterpriseService.getPosxVsPosyByEntId(user.getEnterpriseId());
            if (enterpriseBaseVo != null && (enterpriseBaseVo.getPosx() != null || enterpriseBaseVo.getPosy() != null)) {
                searchCondition.setPosx(enterpriseBaseVo.getPosx());
                searchCondition.setPosy(enterpriseBaseVo.getPosy());
            }
            searchCondition.setInfinity(Constant.DISTANCE_INFINITY);
            searchCondition.setEntId(user.getEnterpriseId());
            int count = cooperationMessageMapper.countDispositionReleaseList(searchCondition);
            if (count > 0) {
                pagingParameter.setTotalRecord(count);
                messageList = cooperationMessageMapper.listDispositionReleaseList(searchCondition);
                Iterator<MessageBodyVo> it = messageList.iterator();
                while (it.hasNext()) {
                    MessageBodyVo bodyVo = it.next();
                    bodyVo.setEntWasteModels(capacitydetailReleaseService.listWasteInfoByReleaseDetailId(user.getEnterpriseId(), bodyVo.getEnterBusId()));
                }
            }

        }
        return messageList;
    }


    private void initPagingParameter(PagingParameter pagingParameter) {
        if (pagingParameter.getPageSize() == 0) {
            pagingParameter.setPageSize(5);
        }

        if (pagingParameter.getPageIndex() == 0) {
            pagingParameter.setPageIndex(1);
        }

        int start = (pagingParameter.getPageIndex() - 1) * pagingParameter.getPageSize();
        pagingParameter.setStart(start);
        pagingParameter.setLimit(pagingParameter.getPageSize());
    }


    @Override
    public int queryMyAllCoopMsg(User user) {
        int count;
        try {
            CoopMsg temp = new CoopMsg(user.getEnterpriseId(), Constant.IS_VALID);
            count = coopMsgService.getCoopMsgCountByEnterId(temp);
        } catch (Exception e) {
            logger.error("获取我的发布信息出错", e);
            throw new RuntimeException(e);
        }

        return count;
    }


    private String calculateDistance(RPCSysEnterpriseBase targetEnterBase, String loginUserEnterId, String ticketId) {
        String distanceStr = null;
        if (StringUtils.isNotBlank(loginUserEnterId)) {
            RPCSysEnterpriseBase enterBase = enterpriseService.getEnterpriseInfoById(ticketId, loginUserEnterId);

            if (StringUtils.isNotBlank(targetEnterBase.getPosx()) && StringUtils.isNotBlank(targetEnterBase.getPosy()) && StringUtils.isNotBlank(enterBase.getPosx()) && StringUtils.isNotBlank(enterBase.getPosy())) {
                Double distance = MapUtil.getDistance(Double.valueOf(targetEnterBase.getPosx()), Double.valueOf(targetEnterBase.getPosy()), Double.valueOf(enterBase.getPosx()), Double.valueOf(enterBase.getPosy()));
                distanceStr = MapUtil.getDistanceStr(distance);
            }
        }
        return distanceStr;
    }


    @Override
    public void updateWasteCycleInit(String userId) {
        try {
            userExtendedService.updateUserExtended(userId);
        } catch (Exception e) {
            logger.error("更新企业扩展表失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public RPCSysEnterpriseBase getEnterIdByUserId(String ticketId, String userId) {

        try {
            RPCSysEnterpriseBase enterBase = platformSupporter.getOrgComServiceManager().queryEnterpriseInfo(ticketId, userId);
            if (enterBase != null && StringUtils.isNotBlank(enterBase.getEntId())) {
                return enterBase;
            }
        } catch (Exception e) {
            logger.error("根据用户id获取企业信息时异常", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public EnterpriseInfo getEnterpriseInfoByUserId(String ticketId, User user) throws Exception {
        EnterpriseInfo enterpriseInfo = null;
        String enterId = null;
        RPCSysEnterpriseBase enterInfo = getEnterIdByUserId(ticketId, user.getUserId());
        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(user.getUserId());
        if (userExtended != null) {
            user.setUserStatus(userExtended.getUserStatus());
        }
        if (enterInfo != null && StringUtils.isNotBlank(enterInfo.getEntId())) {
            enterId = enterInfo.getEntId();
            enterpriseInfo = new EnterpriseInfo();
            enterpriseInfo.setId(enterInfo.getEntId());
            enterpriseInfo.setEnterName(enterInfo.getEntName());
            enterpriseInfo.setEnterCode(enterInfo.getEntCode());
            getEnterpriseImg(enterpriseInfo);
            enterpriseInfo.setEnterType(getEnterpriseTypeByEntId(enterId));
            if (enterpriseInfo.getEnterType() != null) {
                enterpriseInfo.setCoopBusList(getBusinessServiceByEnterType(enterpriseInfo.getEnterType().getCode()));
            }
            if(enterpriseInfo.getEnterType()!=null&&(CodeTypeConstant.ENTERPRISE_TYPE_DIS_FACILITATOR.equalsIgnoreCase(enterpriseInfo.getEnterType().getCode())||
                    CodeTypeConstant.ENTERPRISE_TYPE_FACILITATOR.equalsIgnoreCase(enterpriseInfo.getEnterType().getCode()))){
                RPCSysEnterpriseBaseVo sysEnterpriseBase = enterpriseBaseService.getEnterpriseByEntId(enterpriseInfo.getId());
                if(sysEnterpriseBase!=null) {
                    enterpriseInfo.setResponsibleArea(sysEnterpriseBase.getResponsibleAreaName());
                    enterpriseInfo.setResponsibleCantonCode(sysEnterpriseBase.getResponsibleArea());
                }
            }
            enterpriseInfo.setEnterWasteStatus(checkHasEnterpriseWaste(enterpriseInfo.getId()));
            enterpriseInfo.setEnterStatus(codeValueService.getEnterStatusCodeByEntId(enterId));

        }
        return enterpriseInfo;
    }

    private CodeValue getEnterpriseTypeByEntId(String enterId) {
        CodeValue enterType = null;
        List<CodeValue> enterTypeList = codeValueService.getEnterpriseTypesByEntId(enterId);
        if (enterTypeList != null && enterTypeList.size() > 0) {
            enterType = enterTypeList.get(0);
        }

        return enterType;

    }

    private void getEnterpriseImg(EnterpriseInfo enterpriseInfo) {
        Uploadfile uploadFile = uploadfileService.getFileByBusinessCode("a" + enterpriseInfo.getId());
        if (uploadFile != null) {
            enterpriseInfo.setBusinessCode(uploadFile.getBusinessCode());
            enterpriseInfo.setFileId(uploadFile.getFileId());
        }

    }


    private List<CoopBusVo> getBusinessServiceByEnterType(String enterType) {
        List<CoopBusVo> coppBusList = new ArrayList<CoopBusVo>();
        if (StringUtils.isNotBlank(enterType)) {
            String enterBusinessValue = PropertyUtil.getValue(enterType + "_business");
            if (StringUtils.isNotBlank(enterBusinessValue)) {
                String[] businessCodes = enterBusinessValue.split(",");
                for (String businessCode : businessCodes) {
                    CoopBusVo coopBus = new CoopBusVo();
                    String busName = PropertyUtil.getValue(businessCode);
                    String busUrl = PropertyUtil.getValue(businessCode + "_URL");
                    coopBus.setBusCode(businessCode);
                    coopBus.setBusName(busName);
                    coopBus.setBusUrl(busUrl);
                    coppBusList.add(coopBus);
                }
            }
        }
        return coppBusList;

    }

    @Override
    public Map<String, Object> getEntDropDownList(String keyword) {
        Map<String, Object> datamap = new HashMap<String, Object>();
        List<RPCSysEnterpriseBaseVo> entList = enterpriseBaseService.listEntDropDown(keyword);
        JSONArray entArray = new JSONArray();
        JSONObject entObject = null;
        if (entList.size() != 0) {
            for (RPCSysEnterpriseBaseVo ent : entList) {
                entObject = new JSONObject();
                entObject.put("entId", ent.getEntId());
                entObject.put("entName", ent.getEntName());
                entArray.add(entObject);
            }

        }
        datamap.put("value", entArray);
        datamap.put("message", "");
        return datamap;
    }

    @Override
    public Map<String, Object> getCodeWasteDropDownList(String keyword) {
        Map<String, Object> datamap = new HashMap<String, Object>();
        List<Waste> wasteList = wasteService.getCodeWasteDropDownList(keyword);
        JSONArray entArray = new JSONArray();
        JSONObject entObject = null;
        if (wasteList.size() != 0) {
            for (Waste waste : wasteList) {
                entObject = new JSONObject();
                entObject.put("code", waste.getCode());
                entObject.put("id", waste.getId());
                entArray.add(entObject);
            }

        }
        datamap.put("value", entArray);
        datamap.put("message", "");
        return datamap;
    }

    @Override
    public void saveOrRemoveFollow(String enterId, String action, String ticketId) {
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            String followType = codeValueService.getCodeValueByCode(CodeTypeConstant.FOLLOW_TYPE, CodeTypeConstant.FOLLOW_TYPE_ORGANIZED).getId();
            if ("0".equals(action)) {// 取消关注
                followService.removeFollowByFollowId(user.getUserId(), enterId, followType);
            } else if ("1".equals(action)) {// 关注
                CooperationRelation follow = new CooperationRelation();
                follow.setUserId(user.getUserId());
                follow.setFollowId(enterId);
                follow.setFollowType(followType);
                followService.saveCooperationRelation(follow, ticketId);
            }
        } catch (Exception e) {
            logger.error("取消关注企业时异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getFollow(String ticketId, String entId) {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        user.getUserId();
        try {
            CodeValue codeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.FOLLOW_TYPE, CodeTypeConstant.FOLLOW_TYPE_ORGANIZED);
            return followService.isFollowed(user.getUserId(), entId, codeValue.getId());
        } catch (Exception e) {
            logger.error("判断有没有和某个企业或者个人建立关注关系时异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> getWasteNameDropDownList(String keyword) {
        Map<String, Object> datamap = new HashMap<String, Object>();
        List<Waste> wasteList = wasteService.getWasteNameDropDownList(keyword);
        JSONArray entArray = new JSONArray();
        JSONObject entObject = null;
        if (wasteList.size() != 0) {
            for (Waste waste : wasteList) {
                entObject = new JSONObject();
                entObject.put("name", waste.getName());
                entObject.put("code", waste.getCode());
                entObject.put("id", waste.getId());
                entArray.add(entObject);
            }

        }
        datamap.put("value", entArray);
        datamap.put("message", "");
        return datamap;
    }

    @Override
    public String getUserExtendedByUserId(String userId) {
        String wastecycleInit = "";
        try {
            UserExtended userExtended = userExtendedService.getUserExtendedByUserId(userId);
            wastecycleInit = userExtended.getIsWastecycleInit();
        } catch (Exception e) {
            logger.error("根据用户id获取用户扩展表信息时异常", e);
            throw new RuntimeException(e);
        }
        if (wastecycleInit != null && wastecycleInit.equals(Constant.IS_VALID)) {
            return Constant.IS_VALID;
        } else {
            return Constant.IS_NOT_VALID;
        }
    }

    @Override
    public boolean checkHasEnterpriseWaste(String enterpriseId) throws Exception {
        List<CodeValue> enterTypeList = codeValueService.getEnterpriseTypesByEntId(enterpriseId);
        if (enterTypeList != null && enterTypeList.size() > 0) {
            CodeValue entType = enterTypeList.get(0);
            if (CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equalsIgnoreCase(entType.getCode())) {
                EntWasteParams entWasteParams = new EntWasteParams();
                entWasteParams.setEntId(enterpriseId);
                List<EntWasteModel> list = entWasteService.listEntWasteByEntId(entWasteParams);
                if (list != null && list.size() > 0) {
                    return true;
                }
            } else if (CodeTypeConstant.ENTERPRISE_TYPE_DISPOSITION.equalsIgnoreCase(entType.getCode())) {
                List<OperationLicence> list = licenceDao.getValidLicIdByEnterpriseId(enterpriseId);
                if (list != null && list.size() > 0) {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }
}
