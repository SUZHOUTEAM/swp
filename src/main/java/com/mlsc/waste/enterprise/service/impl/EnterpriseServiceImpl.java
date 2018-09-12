package com.mlsc.waste.enterprise.service.impl;

import com.mlsc.common.exception.BusinessException;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysCanton;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import com.mlsc.solr.SimpleSolr;
import com.mlsc.solr.model.EnterpriseIndex;
import com.mlsc.solr.util.DocumentUtil;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.dao.EnterpriseDao;
import com.mlsc.waste.enterprise.dao.EnterpriseTypeDao;
import com.mlsc.waste.enterprise.dao.SysEnterpriseBaseDao;
import com.mlsc.waste.enterprise.model.EnterpriseExtended;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprise.model.SysEnterpriseBase;
import com.mlsc.waste.enterprise.model.WasteEnterpriseType;
import com.mlsc.waste.enterprise.service.EnterpriseExtendedService;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.enterprise.service.WasteEnterpriseTypeService;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.management.dao.SysUserEnterpriseRelationDao;
import com.mlsc.waste.user.model.SysUserEnterpriseRelation;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecord;
import com.mlsc.waste.userenterpriseapproverecord.service.UserEnterpriseApproveRecordService;
import com.mlsc.waste.utils.*;
import com.mlsc.waste.wastecircle.model.CooperationRelation;
import com.mlsc.waste.wastecircle.model.EnterpriseVo;
import com.mlsc.waste.wastecircle.service.CooperationRelationService;
import com.mlsc.waste.wastecircle.service.WasteCircleService;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author sunjl
 */
@Service("EnterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService {
    private final static Logger logger = LoggerFactory.getLogger(EnterpriseServiceImpl.class);

    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private ICodeTypeService codeTypeService;

    @Autowired
    private UserEnterpriseApproveRecordService userEnterpriseApproveRecordService;

    @Autowired
    private EnterpriseTypeDao enterpriseTypeDao;

    @Autowired
    private WasteEnterpriseTypeService wasteEnterpriseTypeService;

    @Autowired
    private EnterpriseExtendedService enterpriseExtendedService;

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private WasteCircleService wasteCircleService;

    @Autowired
    private CooperationRelationService cooperationRelationService;

    @Autowired
    private IRPCServiceClient client;

    @Autowired
    private UserExtendedService userExtendedService;

    @Autowired
    private SysUserEnterpriseRelationDao userEnterpriseRelationDao;

    @Autowired
    private SysEnterpriseBaseDao sysEnterpriseBaseDao;

    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private SysMsgServcie sysMsgServcie;

    @Autowired
    private UploadfileService uploadfileService;


    /**
     * 保存企业类型
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveEnterpriseType(String ticketId, String enterpriseId, Map<String, String> enterpriseTypeMap, String responsibleArea) {
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            String typeCode = enterpriseTypeMap.entrySet().stream().filter(entType -> StringUtils.isNotBlank(entType.getValue())).findFirst().map(value -> value.getKey()).orElse(null);
            updateEnterpriseType(ticketId, enterpriseId, typeCode, responsibleArea);
            if (CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equalsIgnoreCase(typeCode)) {
                confirmPassForProduction(enterpriseId, user);
            }
        } catch (Exception e) {
            logger.error("企业类型保存失败", e);
            throw new RuntimeException(e);
        }
    }

    private void confirmPassForProduction(String enterpriseId, User user) {
        CodeValue codeValueByCode = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS, CodeTypeConstant.USER_EVENT_STATUS_PASS);
        userEnterpriseApproveRecordService.updateRecordByEnpId(enterpriseId, codeValueByCode.getId());
        enterpriseExtendedService.updateByEnpId(enterpriseId, codeValueByCode.getId(), Constant.IS_VALID);
        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(user.getUserId());
        if (userExtended != null) {
            userExtended.setUserStatus(UserStatus.PASS.getStatusCode());
            userExtended.setUpdateTime(Util.datetimeToString(new Date()));
            userExtendedService.updateUserExtended(userExtended);
        }
    }

    private void updateEnterpriseType(String ticketId, String enterpriseId, String typeCode, String responsibleArea) throws Exception {
        try {
            SysEnterpriseBase sysEnterpriseBase = new SysEnterpriseBase();
            sysEnterpriseBase.setEntId(enterpriseId);
            sysEnterpriseBase.setEntType(typeCode);
            if (CodeTypeConstant.ENTERPRISE_TYPE_FACILITATOR.equalsIgnoreCase(typeCode) || CodeTypeConstant.ENTERPRISE_TYPE_DIS_FACILITATOR.equalsIgnoreCase(typeCode)) {
                sysEnterpriseBase.setResponsibleArea(responsibleArea);
            }
            sysEnterpriseBaseDao.updateChange(sysEnterpriseBase);
            List<WasteEnterpriseType> records = new ArrayList<WasteEnterpriseType>();
            records.add(getWasteEnterpriseType(enterpriseId, typeCode));
            wasteEnterpriseTypeService.saveWasteEnterpriseTypes(records, ticketId);
            refreshCacheInfo(ticketId);
        } catch (Exception e) {
            logger.error("更新企业类型时异常", e);
            throw e;
        }

    }

    private void refreshCacheInfo(String ticketId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        LoginStatusUtils.refreshUserToCacheByUserParam(user);
    }

    @Override
    public void informSysAdminUser(String enterpriseId, User user) throws Exception {
        List<User> sysAdminUserList = userInfoService.listSysAdminUser(UserRole.ADMIN.getRoleCode());
        if (sysAdminUserList != null && sysAdminUserList.size() > 0) {
            SysEnterpriseBase sysEnterpriseBase = sysEnterpriseBaseDao.get(enterpriseId);
            User sysAdmin = new User();
            sysAdmin.setUserId(Constant.SYS_ADMIN);
            MsgEvent msgEvent = new MsgEvent(SmsAction.NEW_USER);
            msgEvent.setSendUser(sysAdmin);
            Map<String, String> placeholderValueMap = new HashMap<String, String>();
            placeholderValueMap.put("entName", sysEnterpriseBase.getEntName());
            placeholderValueMap.put("userName", user.getUserName());
            placeholderValueMap.put("phoneNo", user.getPhoneNo());
            msgEvent.setPlaceholderValueMap(placeholderValueMap);
            msgEvent.setReceiveUserList(sysAdminUserList);
            sysMsgServcie.sendMessageSync(msgEvent);
        }
    }

    /**
     * 获取企业类型
     *
     * @param enterpriseId
     * @return
     * @throws Exception
     */
    private WasteEnterpriseType getWasteEnterpriseType(String enterpriseId, String enterpriseTypeCode) {
        WasteEnterpriseType wastEnterpriseType = new WasteEnterpriseType();
        CodeValue codeVo;
        try {
            codeVo = codeValueService.getCodeValueByCode(CodeTypeConstant.ENTERPRISE_TYPE, enterpriseTypeCode);
        } catch (Exception e) {
            logger.error("获取企业类型失败", e);
            throw new RuntimeException(e);
        }
        wastEnterpriseType.setEnterpriseId(enterpriseId);
        wastEnterpriseType.setEnterpriseTypeId(codeVo.getId());
        return wastEnterpriseType;
    }

    /**
     * 保存企业基本信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RPCSysEnterpriseBase saveEnterpriseInfo(String ticketId, RPCSysEnterpriseBase enterprise) {
        Iface service = client.getOrgComServiceManager();
        RPCSysEnterpriseBase enterpriseBase = null;
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            enterprise.setCreateId(user.getUserId());
            if (StringUtils.isBlank(enterprise.getPosy()) || StringUtils.isBlank(enterprise.getPosx())) {
                Map map = Util.getLngAndLat(enterprise.getEntAddress());
                if (map.get("lng") != null) {
                    enterprise.setPosx(map.get("lng").toString());
                }
                if (map.get("lat") != null) {
                    enterprise.setPosy(map.get("lat").toString());
                }
            }
            enterpriseBase = service.saveEnterpriseInfo(ticketId, enterprise);
        } catch (Exception e) {
            logger.error("企业基本信息保存失败", e);
            throw new RuntimeException(e);
        }
        return enterpriseBase;
    }


    /**
     * 保存企业信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveEnterpriseInformation(String ticketId, RPCSysEnterpriseBase enterpriseBase) throws Exception {
        String enterpriseId = "";
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            boolean exist = checkEnterpriseCodeExist(null, enterpriseBase.getEntCode());
            if (exist) {
                throw new BusinessException("企业代码已存在");
            }
            enterpriseId = saveEnterpriseRelation(ticketId, enterpriseBase, user);
            updateEnterpriseNewField(enterpriseBase);
            refreshCacheInfo(ticketId);

        } catch (RuntimeException e) {
            logger.error("企业信息保存失败！", e);
            throw new RuntimeException(e);
        }
        return enterpriseId;
    }


    /**
     * 保存信息到企业相关的表中
     *
     * @param ticketId
     * @param enterpriseBase
     * @param user
     * @return
     * @throws Exception
     */
    private String saveEnterpriseRelation(String ticketId, RPCSysEnterpriseBase enterpriseBase, User user) throws Exception {
        String enterpriseId = "";
        RPCSysEnterpriseBase rpcsysenterprisebase = saveEnterpriseInfo(ticketId, enterpriseBase);// 保存到企业基础表中
        enterpriseId = rpcsysenterprisebase.getEntId();// 获取企业ID
        // 如果是平台管理员的话，不需要建立用户与企业的关系
        if (!Util.isSysUser(ticketId)) {
            saveUserEnterpriseRelaction(user, enterpriseId);
            saveUserExtends(user);
        }

        saveEnterperiseExtended(ticketId, enterpriseId);// 保存到企业扩展表
        saveUserEnterpriseApprovedRecord(ticketId, enterpriseId);// 保存到用户/企业关系审核记录表中
        return enterpriseId;
    }

    private void saveUserExtends(User user) {
        UserExtended userExtend = userExtendedService.getUserExtendedByUserId(user.getUserId());
        userExtend.setRole(UserRole.ADMIN.getRoleCode());
        userExtendedService.updateUserExtended(userExtend);
    }

    private void saveUserEnterpriseRelaction(User user, String enterpriseId) throws Exception {
        SysUserEnterpriseRelation userEnterpriseRelation = userEnterpriseRelationDao
                .getSysUserEnterpriseRelationByUserId(user.getUserId());
        if (userEnterpriseRelation != null) {
            throw new BusinessException("你已经绑定了企业，若需要重新绑定请先退出当前绑定企业");
        }

        userEnterpriseRelation = new SysUserEnterpriseRelation();
        userEnterpriseRelation.setUserId(user.getUserId());
        userEnterpriseRelation.setEntId(enterpriseId);
        userEnterpriseRelationDao.save(userEnterpriseRelation, Util.uuid32());
    }


    /**
     * 保存企业扩展表
     */
    private String saveEnterperiseExtended(String ticketId, String enterpriseId) throws Exception {
        EnterpriseExtended enterpriseExtended = new EnterpriseExtended();
        CodeValue codeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS, CodeTypeConstant.USER_EVENT_STATUS_SUBMIT);
        enterpriseExtended.setSysEnterpriseBaseId(enterpriseId);
        enterpriseExtended.setEnterpriseStatus(codeValue.getId());
        enterpriseExtended.setEnterpriseIcon(null);
        enterpriseExtended.setValid(Constant.IS_VALID);
        String extendedId = enterpriseExtendedService.saveEnterpriseExtended(enterpriseExtended, ticketId);
        return extendedId;
    }

    /**
     * 保存用户/企业关系审核记录
     *
     * @throws Exception
     */
    private String saveUserEnterpriseApprovedRecord(String ticketId, String enterpriseId) throws Exception {
        CodeValue eventType = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_TYPE, CodeTypeConstant.USER_EVENT_TYPE_CREATE);
        CodeValue eventStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS, CodeTypeConstant.USER_EVENT_STATUS_SUBMIT);
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        UserEnterpriseApproveRecord userEnterpriseApproveRecord = new UserEnterpriseApproveRecord();
        userEnterpriseApproveRecord.setUserId(user.getUserId());
        userEnterpriseApproveRecord.setEnterpriseId(enterpriseId);
        userEnterpriseApproveRecord.setEventType(eventType.getId());
        userEnterpriseApproveRecord.setEventStatus(eventStatus.getId());
        userEnterpriseApproveRecord.setApplicationBy(user.getUserId());
        userEnterpriseApproveRecord.setApplicationTime(Util.datetimeToString(new Date()));
        userEnterpriseApproveRecord.setApprovedBy(null);
        userEnterpriseApproveRecord.setApprovedTime(null);
        String recordid = userEnterpriseApproveRecordService.saveUserEnterpriseApproveRecord(userEnterpriseApproveRecord, ticketId);

        return recordid;
    }

    /**
     * 获取企业行政区域
     *
     * @return
     */
    @Override
    public String getCanonCode(String ticketId, String district, String city) {
        String cantonCode = "";
        try {
            RPCSysCanton rPCSysCanton = client.getOrgComServiceManager().querySysCanton(ticketId, district, city);
            // 获取行政区code
            if (StringUtils.isNotBlank(rPCSysCanton.getCantonCode())) {
                cantonCode = rPCSysCanton.getCantonCode();
            }
        } catch (Exception e) {
            logger.error("保存信息异常", e);
            throw new RuntimeException(e);
        }
        return cantonCode;
    }

    /**
     * 更新企业基本信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RPCSysEnterpriseBase updateEnterpriseBase(String ticketId, RPCSysEnterpriseBase enterprise, String latitudeAndLongitude)
            throws Exception {
        boolean exist = checkEnterpriseCodeExist(enterprise.getEntId(), enterprise.getEntCode());
        if (exist) {
            throw new BusinessException("企业代码已存在");
        }
        if (StringUtils.isNotBlank(latitudeAndLongitude)) {
            String[] posxAndPosy = latitudeAndLongitude.split(", ");
            enterprise.setPosx(posxAndPosy[0]);
            enterprise.setPosy(posxAndPosy[1]);
        }
        enterprise.setShortName(getShortName(enterprise.getEntName()));
        enterprise.setContacts(enterprise.getContacts());
        enterprise.setContactsTel(enterprise.getContactsTel());
        RPCSysEnterpriseBase rpcsysenterprisebase = saveEnterpriseInfo(ticketId, enterprise);
        return rpcsysenterprisebase;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateEnterpriseNewField(RPCSysEnterpriseBase sysEnterpriseBase) throws DaoAccessException {
        SysEnterpriseBase updatedFields = new SysEnterpriseBase();
        if (StringUtils.isEmpty(sysEnterpriseBase.getPosx()) && StringUtils.isEmpty(sysEnterpriseBase.getPosy()) && StringUtils.isEmpty(sysEnterpriseBase.getCantonCode()) && StringUtils.isEmpty(sysEnterpriseBase.getContacts()) && StringUtils.isEmpty(sysEnterpriseBase.getContactsTel()))
            return;

        if (StringUtils.isNotBlank(sysEnterpriseBase.getEntId())) {
            updatedFields.setEntId(sysEnterpriseBase.getEntId());
        }
        if (StringUtils.isNotBlank(sysEnterpriseBase.getPosx())) {
            updatedFields.setPosx(Double.valueOf(sysEnterpriseBase.getPosx()));
        }
        if (StringUtils.isNotBlank(sysEnterpriseBase.getPosy())) {
            updatedFields.setPosy(Double.valueOf(sysEnterpriseBase.getPosy()));
        }
        if (StringUtils.isNotBlank(sysEnterpriseBase.getCantonCode())) {
            updatedFields.setCantonCode(sysEnterpriseBase.getCantonCode());
        }
        if (StringUtils.isNotBlank(sysEnterpriseBase.getContacts())) {
            updatedFields.setContacts(sysEnterpriseBase.getContacts());
        }
        if (StringUtils.isNotBlank(sysEnterpriseBase.getContactsTel())) {
            updatedFields.setContactsTel(sysEnterpriseBase.getContactsTel());
        }
        sysEnterpriseBaseDao.updateChange(updatedFields);

    }

    /**
     * 公司名称缩写
     *
     * @return
     */
    @Override
    public String getShortName(String entName) {
        if (StringUtils.isNotBlank(entName) && entName.length() >= 10) {
            return entName.substring(0, 10);
        } else {
            return entName;
        }
    }

    /**
     * 获取企业类型集合
     */
    @Override
    public List<CodeValue> getDropDownListByTypeCode(String typecode) {
        List<CodeValue> enterpriseTypeList = null;
        try {
            enterpriseTypeList = codeTypeService.getCodeValuesTypeCode(typecode);
        } catch (Exception e) {
            logger.error("获取企业类型集合时异常", e);
            throw new RuntimeException(e);
        }
        return enterpriseTypeList;
    }

    /**
     * 判断企业是否有效
     */
    @Override
    public boolean isEnterpriseVaild(String ticketId) throws Exception {
        boolean result = true;
        if (!Util.isSysUser(ticketId)) {// 如果是平台管理员，默认不检查企业的完整性
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            if (user == null || StringUtils.isBlank(user.getEnterpriseId())) {
                result = false;
            } else {
                // 判断平台企业表（sys_enterprise_base）以及企业扩展表（enterprise_extended）有无数据
                result = isExistsEnterpriseTypeByEnterId(user.getEnterpriseId());
            }
        }
        return result;
    }

    /**
     * 判断企业类型表中是否有数据
     *
     * @return
     * @throws Exception
     */
    private boolean isExistsEnterpriseTypeByEnterId(String enterpriseId) throws Exception {
        boolean result = true;
        RPCSysEnterpriseBaseVo enterpriseBaseVo = enterpriseBaseService.getEnterpriseByEntId(enterpriseId);
        if (enterpriseBaseVo == null || StringUtils.isBlank(enterpriseId)) {
            result = false;
        } else {
            // 判断企业类型表（waste_enterprise_type）有无数据
            List<WasteEnterpriseType> entTypes = wasteEnterpriseTypeService.listWasteEnterpriseTypesByEnterpriseId(enterpriseId);
            if (entTypes == null || entTypes.isEmpty()) {
                result = false;
            }
        }
        return result;
    }


    /**
     * 关注或者取消关注
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveOrRemoveFollow(String enterId, String action, String ticketId) {
        try {
            wasteCircleService.saveOrRemoveFollow(enterId, action, ticketId);
        } catch (Exception e) {
            logger.error("关注或取消关注时异常", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 一键关注
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int saveFollowAndEnterWasteCircle(String ticketId, List<String> entIds) {
        int followSuccessCount = 0;
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            String followType = codeValueService.getCodeValueByCode(CodeTypeConstant.FOLLOW_TYPE, CodeTypeConstant.FOLLOW_TYPE_ORGANIZED).getId();
            List<CooperationRelation> cooperationRelations = new ArrayList<CooperationRelation>();
            CooperationRelation follow = null;
            for (String entId : entIds) {
                follow = new CooperationRelation();
                follow.setUserId(user.getUserId());
                follow.setFollowId(entId);
                follow.setFollowType(followType);
                cooperationRelations.add(follow);
            }
            followSuccessCount = cooperationRelationService.savesCooperationRelation(cooperationRelations, ticketId);
        } catch (Exception e) {
            logger.error("一键关注时异常", e);
            throw new RuntimeException(e);
        }
        return followSuccessCount;

    }


    @Override
    public Map<String, Object> getCodeWastesDropDownList(String keyword) {
        return wasteCircleService.getCodeWasteDropDownList(keyword);
    }


    @Override
    public String getCantonNameByEnterpriseId(String enterpriseId) {
        RPCSysEnterpriseBaseVo rpcSysEnterpriseBaseVo = null;
        try {
            rpcSysEnterpriseBaseVo = enterpriseDao.getCantonNameByEnterpriseId(enterpriseId);
        } catch (DaoAccessException e) {
            logger.error("获取行政区失败", e);
            e.printStackTrace();
        }
        if (rpcSysEnterpriseBaseVo != null) {
            return rpcSysEnterpriseBaseVo.getCantonName();
        } else {
            return "";
        }
    }


    @Override
    public List<EnterpriseIndex> listEnterpriseSuggest(String cantonCode, String entType, String keywords, PagingParameter paging) {
        List<EnterpriseIndex> resultList = listEnterpriseSuggestFromSolr(cantonCode, entType, keywords, paging);
        resultList.stream().forEach(enterpriseIndex -> {
            Uploadfile uploadFile = uploadfileService.getFileByFileTypeAndReferenceId("a", enterpriseIndex.getId()).stream().findFirst().orElse(null);
            if (uploadFile != null) {
                enterpriseIndex.setFileId(uploadFile.getFileId());
            }
        });
        return resultList;
    }

    private List<EnterpriseIndex> listEnterpriseSuggestFromSolr(String cantonCode, String entType, String keywords, PagingParameter paging) {
        List<EnterpriseIndex> resultList = null;
        SimpleSolr simpleSolr = null;
        try {
            simpleSolr = new SimpleSolr(entType);
            SolrQuery params = new SolrQuery();
            String key = "";
            String orginalKeyWords = keywords;
            List<String> wasteCodes = null;
            if (StringUtils.isBlank(keywords)) {
                if (StringUtils.isNotEmpty(cantonCode)) {
                    params.setQuery("cantonCode:" + cantonCode);
                } else {
                    params.setQuery("*:*");
                }

            } else {
                StringBuilder queryParam = new StringBuilder("cantonCode:" + cantonCode + " AND ");
                StringBuilder queryField = new StringBuilder();
                keywords = keywords.replaceAll("hw", "").replaceAll("HW", "");
                wasteCodes = getSubUtil(keywords, "[0-9]{3}-[0-9]{3}-[0-9]{2}");
                SolrQuery filterQuery = new SolrQuery();
                if (wasteCodes != null && wasteCodes.size() > 0) {
                    queryField.append("wasteCode ");
                    for (String wasteCode : wasteCodes) {
                        keywords = keywords.replace(wasteCode, "");
                        queryParam.append(" WASTECODE:" + wasteCode + "^20  AND ");
                        filterQuery.addFilterQuery("WASTECODE:" + wasteCode);
                    }

                }
                List<String> wasteCodeStr = getWasteCodeUtil(keywords, "[0-9]{8}");
                if (wasteCodeStr != null && wasteCodeStr.size() > 0) {
                    queryField.append("wasteCode ");
                    for (String wasteCode : wasteCodeStr) {
                        String orgWasteCode = wasteCode.replaceAll("-", "");
                        keywords = keywords.replace(orgWasteCode, "");
                        queryParam.append(" WASTECODE:" + wasteCode + "^20 AND ");
                        filterQuery.addFilterQuery("WASTECODE:" + wasteCode);
                    }
                }
                List<String> wasteTypeCodes = getSubUtil(keywords, "\\b\\d{2}\\b");
                if (wasteTypeCodes != null && wasteTypeCodes.size() > 0) {
                    queryField.append("wasteTypeCode ");
                    for (String wasteTypeCode : wasteTypeCodes) {
                        keywords = keywords.replace(wasteTypeCode, "");
                        queryParam.append(" wasteTypeCode:" + wasteTypeCode + "^20 AND  ");
                        filterQuery.addFilterQuery("wasteTypeCode:" + wasteTypeCode);
                    }
                }
                if (StringUtils.isNotEmpty(keywords.trim())) {
                    String keyWords = "";
                    String[] keyWordList = keywords.trim().split("\\s+");
                    if (keyWordList != null && keyWordList.length > 0) {
                        for (String keyWord : keyWordList) {
                            queryParam.append(queryParam + " " + entType + ":" + keyWord.trim() + "^10 AND  ");
                        }
                    }

                }

                if (queryParam.toString().trim().endsWith("AND")) {
                    String queryParamStr = queryParam.substring(0, queryParam.lastIndexOf("AND"));
                    params.setQuery(queryParamStr);
                }
                key = queryField.toString();
                params.add(filterQuery);
            }
            params.set("defType", "edismax");
            params.setFacet(true);
            params.setRequestHandler("/browse");
            params.set("start", paging.getStart());
            params.set("rows", paging.getLimit());
            QueryResponse rsp = simpleSolr.getClient().query(params);
            SolrDocumentList docs = rsp.getResults();
            paging.setTotalRecord((int) docs.getNumFound());
            resultList = DocumentUtil.toBeanList(docs, EnterpriseIndex.class, rsp.getHighlighting(), key);
        } catch (Exception e) {
            logger.error("根据条件查询企业失败(门户首页)", e);
        } finally {
            if (simpleSolr != null) {
                simpleSolr.close();
            }

        }
        return resultList;
    }

    private List<String> getWasteCodeUtil(String keywords, String rgex) {
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(keywords);
        while (m.find()) {
            String wasteCode = m.group(0);
            wasteCode = wasteCode.substring(0, 3) + "-" + wasteCode.substring(3, 6) + "-" + wasteCode.substring(6, wasteCode.length());
            list.add(wasteCode);
        }
        return list;
    }


    public List<String> getSubUtil(String keywords, String rgex) {
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(keywords);
        while (m.find()) {
            list.add(m.group(0));
        }
        return list;
    }

    @Override
    public Map<String, Object> getWasteNameDropDownList(String keyword) {
        return wasteCircleService.getWasteNameDropDownList(keyword);
    }

    @Override
    public RPCSysEnterpriseBase getEnterpriseInfoById(String ticketId, String enterpriseId) {
        Iface service = client.getOrgComServiceManager();
        RPCSysEnterpriseBase enterpriseBase = null;
        try {
            enterpriseBase = service.queryEnterprise(ticketId, enterpriseId);
        } catch (Exception e) {
            logger.error("获取企业信息时异常", e);
            throw new RuntimeException(e);
        }
        return enterpriseBase;
    }

    @Override
    public RPCSysEnterpriseBaseVo getPosxVsPosyByEntId(String enterpriseId) {
        RPCSysEnterpriseBaseVo enterpriseBaseVo = new RPCSysEnterpriseBaseVo();
        try {
            enterpriseBaseVo = enterpriseBaseService.getCoordinateByEntId(enterpriseId);
        } catch (Exception e) {
            logger.error("获取经纬度时异常", e);
            throw new RuntimeException(e);
        }
        return enterpriseBaseVo;
    }

    @Override
    public List<EnterpriseVo> getEnterpriseVosByName(String enterpriseName, String enterpriseType) {
        List<EnterpriseVo> enterpriseVos = enterpriseDao.getEnterpriseVosByName(enterpriseName, enterpriseType);
        return enterpriseVos;
    }

    @Override
    public boolean checkEnterpriseCodeExist(String entId, String entCode) {
        String whereSql;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(entId)) {
            whereSql = " AND ent.entCode = :entCode AND ent.entId != :entId AND cv.code = :passStatus ";
            paramMap.put("entId", entId);
        } else {
            whereSql = " AND ent.entCode = :entCode AND cv.code = :passStatus ";
        }
        paramMap.put("entCode", entCode);
        paramMap.put("passStatus", CodeTypeConstant.USER_EVENT_STATUS_PASS);
        List<SysEnterpriseBase> list = sysEnterpriseBaseDao.listSysEnterpriseBase(whereSql, paramMap);
        return list != null && list.size() > 0;
    }

    @Override
    public String getDistrictByEnterpriseId(String enterpriseId) throws Exception {
        RPCSysEnterpriseBaseVo rpcSysEnterpriseBaseVo = null;
        try {
            rpcSysEnterpriseBaseVo = enterpriseDao.getDistrictByEnterpriseId(enterpriseId);
            if (rpcSysEnterpriseBaseVo != null) {
                return rpcSysEnterpriseBaseVo.getCantonName();
            } else {
                return "";
            }
        } catch (DaoAccessException e) {
            logger.error("获取行政区失败", e);
            throw e;
        }

    }

    @Override
    public List<EnterpriseVo> getCZEnterpriseVosByName(String enterpriseName) {
        List<EnterpriseVo> enterpriseVos = enterpriseDao.getCZEnterpriseVosByName(enterpriseName);
        return enterpriseVos;
    }

}