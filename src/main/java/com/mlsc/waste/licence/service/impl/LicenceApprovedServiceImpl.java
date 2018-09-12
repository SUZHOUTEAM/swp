/**
 * 
 */
package com.mlsc.waste.licence.service.impl;

import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.model.MessageBean;
import com.mlsc.yifeiwang.sms.model.ReleaseStatus;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityRelease;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityReleaseService;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.licence.dao.LicenceApprovedDao;
import com.mlsc.waste.licence.dao.LicenceDao;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceDetailVo;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
import com.mlsc.waste.licence.model.OperationLicenceVo;
import com.mlsc.waste.licence.service.LicenceApprovedService;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.UserStatus;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.dao.CoopMsgBusDao;
import com.mlsc.waste.wastecircle.dao.CoopMsgDao;
import com.mlsc.waste.wastecircle.model.CoopMsg;
import com.mlsc.waste.wastecircle.model.CoopMsgBus;
import com.mlsc.waste.wastecircle.service.CooperationRelationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author sunjl
 *
 */
@Service("LicenceApprovedService")
public class LicenceApprovedServiceImpl implements LicenceApprovedService {
    
    @Autowired
    private LicenceApprovedDao licenceApprovedDao;

    @Autowired
    private LicenceDao licenceDao;
    
    @Autowired
    private LicenceService licenceService;
    
    @Autowired
    private ICodeTypeService codeTypeService;
    
    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private DispositionCapacityReleaseService capacityReleaseService;

    @Autowired
    private CoopMsgDao coopMsgDao;

    @Autowired
    private CoopMsgBusDao  coopMsgBusDao;

    @Autowired
    private SysMsgServcie sysMsgServcie;

    @Autowired
    private CooperationRelationService cooperationRelationService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private UserService userService;

    /**
     * 件数
     * @param where 查询条件 
     * @param params 查询条件参数
     * @return
     * @throws Exception
     */
    @Override
    public Integer count(String where, Map<String, Object> params) throws Exception  {
        Integer count = null;
        // 默认是待审核状态
        StringBuffer sbwhere = new StringBuffer(where);
        appendWhereCondition(sbwhere, params);
        count = licenceApprovedDao.count(sbwhere.toString(),params);
        return count;
    }
    
    /**
     * 查询审核许可证列表
     * 
     * @return
     * @throws Exception 
     */
    @Override
    public List<OperationLicenceVo> list(String where, Map<String, Object> params,PagingParameter paging) throws Exception {
        List<OperationLicenceVo> operationLicenceVoList = null;
        StringBuffer sbwhere = new StringBuffer(where);
        appendWhereCondition(sbwhere, params);
        operationLicenceVoList =  licenceApprovedDao.list(sbwhere.toString(),params,paging);
        return operationLicenceVoList;
    }
    
    private void appendWhereCondition(StringBuffer sbwhere, Map<String, Object> params) throws Exception {
        // 默认是待审核状态
        if (params.get("auditStatus") == null) {
            sbwhere.append(" and auditStatus like :auditStatus ");
            CodeValue auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT);
            params.put("auditStatus","%" + auditStatus.getId() + "%");
        } else {
            if (sbwhere.indexOf("and auditStatus like :auditStatus") < 0) {
                sbwhere.append("and auditStatus like :auditStatus");
            }
        }
    }
    
    /**
     * 许可证审核通过
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void confirmPass(List<String> licenceIds, String ticketId) throws Exception  {
        if (licenceIds == null || licenceIds.isEmpty()) {
            return ;
        }
        //如果许可证中没有明细表的话不给审核
        for(String  licenceId : licenceIds){
            boolean flag = licenceService.isHasDetails(licenceId);
            if(!flag){
                String licence_no = licenceService.getLicneceById(licenceId).getLicence_no();
                throw new WasteBusinessException(new MessageBean(MessageBean.STATUS_INFO, MessageBean.NOTICE_TYPE_NOTIFY, "[" + licence_no + "]许可证没有添加详细的许可项，不能通过审核。"));
            }
        }
        String auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        saveDispositionCapacityByLicIds(licenceIds, ticketId);//许可证审核通过添加处置能到消息相关的表中(批量审核批量添加)
    }
    
    /**
     * 许可证审核退回
     * @throws Exception
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void confirmReject(List<String> licenceIds, String ticketId) throws Exception {
        if (licenceIds == null || licenceIds.isEmpty()) {
            return ;
        }
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String auditStatus = codeValueService .getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED).getId();
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        for(String liceId : licenceIds){
            OperationLicence licence = licenceDao.get(liceId);
            User queryParam = new User();
            queryParam.setEnterpriseId(licence.getEnterprise_id());
            queryParam.setUserStatus(UserStatus.PASS.getStatusCode());
            MsgEvent msgEvent = new MsgEvent(SmsAction.ENT_NEW_LIC_REFUSE);
            msgEvent.setSendUser(user);
            msgEvent.setRelId(liceId);
            sysMsgServcie.sendMessageAsync(msgEvent);
        }
    }
    
    @Override
    public OperationLicenceVo getOperationLicenceVoById(String licenceId) throws Exception  {
        return licenceApprovedDao.queryById(licenceId);
    }

    @Override
    public List<OperationLicenceItemVo> getItemByLicenceId(String licenceId) throws Exception {
        List<OperationLicenceItemVo> operationLicenceItemVoList = null;
        operationLicenceItemVoList = licenceApprovedDao.queryItemByLicenceId(licenceId);
        return operationLicenceItemVoList;
    }

    private List<OperationLicenceDetailVo> getWasteTypeByLicenceId(String licenceId) throws Exception {
        List<OperationLicenceDetailVo> operationLicenceDetailVoList = null;
        operationLicenceDetailVoList =  licenceApprovedDao.queryWasteTypeByLicenceId(licenceId);
        return operationLicenceDetailVoList;
    }

    @Override
    public List<OperationLicenceDetailVo> getWasteCodeByLicenceId(String licenceId,String licenceItemId) throws Exception {
        List<OperationLicenceDetailVo>  operationLicenceDetailVoList = null;
        operationLicenceDetailVoList =  licenceApprovedDao.queryWasteCodeByLicenceId(licenceId,licenceItemId);
        return operationLicenceDetailVoList;
    }
    
    private List<OperationLicenceDetailVo> getListWasteTypeVo(String itemId, List<OperationLicenceDetailVo> listDetailWasteTypeVo,List<OperationLicenceDetailVo> listDetailWasteVo) throws Exception {
        List<OperationLicenceDetailVo> listWasteTypeVo = new ArrayList<OperationLicenceDetailVo>();
        
        Iterator<OperationLicenceDetailVo> it = listDetailWasteTypeVo.iterator(); 
        OperationLicenceDetailVo wasteTypeVo = null;
        while(it.hasNext()){
            wasteTypeVo = it.next();
            if (itemId.equals(wasteTypeVo.getItemId())) {
                listWasteTypeVo.add(getWasteTypeVoIsNotCalcAll(itemId, wasteTypeVo, listDetailWasteVo));
                it.remove();
            }
        }
        return listWasteTypeVo;
    }
    
    private OperationLicenceDetailVo getWasteTypeVoIsNotCalcAll(String itemId, OperationLicenceDetailVo wasteTypeVo,List<OperationLicenceDetailVo> listDetailWasteVo) throws Exception {
        StringBuffer listWasteVo = new StringBuffer();
        Iterator<OperationLicenceDetailVo> listDetailWasteIt = listDetailWasteVo.iterator(); 
        OperationLicenceDetailVo wasteVo = null;
        while(listDetailWasteIt.hasNext()){
            wasteVo = listDetailWasteIt.next();
            if (itemId.equals(wasteTypeVo.getItemId()) && itemId.equals(wasteVo.getItemId()) && wasteTypeVo.getWasteTypeId().equals(wasteVo.getWasteTypeId())){
                    listWasteVo.append(wasteVo.getWasteCode());
                    if (StringUtils.isNotBlank(wasteVo.getWasteName())) {
                        listWasteVo.append(":" + wasteVo.getWasteName());
                    }
                    listWasteVo.append(";");
                    listDetailWasteIt.remove();
            }
        }
        if (listWasteVo.length() > 0 ) {
            listWasteVo.deleteCharAt(listWasteVo.length()-1);//去掉末尾的逗号
        }
        
        if (itemId.equals(wasteTypeVo.getItemId())) {
            List<String> wasteList  = new ArrayList<String>();
            wasteList.add(listWasteVo.toString());
            wasteTypeVo.setListWasteVo(wasteList);
        }
        return wasteTypeVo;
    }

    @Override
    public List<OperationLicenceItemVo> getWasteInfoByLicenceId(String licenceId) throws Exception {
        List<OperationLicenceItemVo> listItemVo  = null;
        // 许可证危废信息处置方式查询
        listItemVo = getItemByLicenceId(licenceId);
        // 许可证危废信息二位码查询
        List<OperationLicenceDetailVo> listDetailWasteTypeVo = getWasteTypeByLicenceId(licenceId);
        
        // 许可证危废信息八位码查询
        List<OperationLicenceDetailVo> listDetailWasteVo = getWasteCodeByLicenceId(licenceId,null);
        for (OperationLicenceItemVo itemVo : listItemVo) {
            itemVo.setListWasteTypeVo(getListWasteTypeVo(itemVo.getItemId() == null?"":itemVo.getItemId(), listDetailWasteTypeVo, listDetailWasteVo));
        }
        return listItemVo; 
    }
    
    @Override
    public List<CodeValue> getCodeValuesTypeCode(String typeCode) throws Exception {
        return codeTypeService.getCodeValuesTypeCode(typeCode);
    }

    /**
     * 批量添加处置企业处置能力，许可证审核通过的时候
     * @throws Exception 
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveDispositionCapacityByLicIds(List<String> licenceIds,String ticketId) throws Exception {
        if (licenceIds == null || licenceIds.isEmpty()) {
            return ;
        }
        //如果许可证明细表没有数据，就认为是无效许可证，不添加
        for(String  licenceId : licenceIds){
            boolean flag = licenceService.isHasDetails(licenceId);
            if(!flag){
                String licence_no = licenceService.getLicneceById(licenceId).getLicence_no();
                throw new WasteBusinessException(new MessageBean(MessageBean.STATUS_INFO, MessageBean.NOTICE_TYPE_NOTIFY, "[" + licence_no + "]许可证没有详细的许可项，不能发布处置能力。"));
            }
        }
        
        capacityReleaseService.saveDispositionCapacityByLicIds(ticketId,licenceIds);
        saveCoopMsgByLicIds(licenceIds, ticketId);
    }
    //许可证审核通过
    private void saveCoopMsgByLicIds(List<String> licenceIds, String ticketId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        for(String licenceId : licenceIds){
            DispositionCapacityRelease capacityRelease  = capacityReleaseService.getDispositionCapacityReleaseByLicId(licenceId);
            CoopMsg coopMsg = getCoopMsg(ticketId, licenceId);
            String msgId = coopMsgDao.saveCoopMsg(coopMsg);
            
            CoopMsgBus coopMsgBus = getCoopMsgBus(ticketId, msgId, capacityRelease.getId());
            coopMsgBusDao.saveCoopMsgBus(coopMsgBus);

            //发送通知给关注的人
            List<User> receiveUserList = cooperationRelationService
                    .listUserByFollowIdAndFollowTypeCode(capacityRelease.getReleaseEnterpriseId(),
                            CodeTypeConstant.FOLLOW_TYPE_ORGANIZED);
            User sendUser = userService.getUserByUserId(capacityRelease.getCreateBy());
            if (receiveUserList != null && receiveUserList.size() > 0) {
                Map<String, String> placeholderValueMap = new HashMap<>();
                placeholderValueMap.put("entName", sendUser.getEnterpriseName());
                MsgEvent msgEvent = new MsgEvent(SmsAction.FOLLOW_NEW_PUBLISH);
                msgEvent.setSendUser(sendUser);
                msgEvent.setReceiveUserList(receiveUserList);
                msgEvent.setPlaceholderValueMap(placeholderValueMap);
                msgEvent.setRelId(msgId);
                sysMsgServcie.sendMessageAsync(msgEvent);
            }

            //通知许可证所属企业用户
            User queryParam = new User();
            queryParam.setEnterpriseId(capacityRelease.getReleaseEnterpriseId());
            queryParam.setUserStatus(UserStatus.PASS.getStatusCode());
            MsgEvent licMsgEvent = new MsgEvent(SmsAction.ENT_NEW_LIC_PASS);
            licMsgEvent.setSendUser(user);
            licMsgEvent.setReceiverUserQueryParam(queryParam);
            licMsgEvent.setRelId(licenceId);
            Map<String, String> placeholderValueMap = new HashMap<>();
            String licence_no = licenceService.getLicneceById(licenceId).getLicence_no();
            placeholderValueMap.put("licenceNo", licence_no);
            licMsgEvent.setPlaceholderValueMap(placeholderValueMap);
            sysMsgServcie.sendMessageAsync(licMsgEvent);
        }
    }
    
    private CoopMsg getCoopMsg(String ticketId, String licenceId) throws Exception {
        User userByTicketId = LoginStatusUtils.getUserByTicketId(ticketId);
        OperationLicence licence = licenceService.getLicneceById(licenceId);
        
        CoopMsg coopMsg = new CoopMsg();
        
        coopMsg.setPublisherId(licence.getCreate_by());
        coopMsg.setEnterId(licence.getEnterprise_id());
        coopMsg.setMessageDetail("");//消息正文暂时无
        coopMsg.setCreateBy(userByTicketId.getUserId());
        coopMsg.setCreateTime(Util.datetimeToString(new Date()));
        coopMsg.setEditBy(userByTicketId.getUserId());
        coopMsg.setEditTime(Util.datetimeToString(new Date()));
        coopMsg.setValid(Constant.IS_VALID);
        return coopMsg;
    }
    
    private CoopMsgBus getCoopMsgBus(String ticketId, String msgId, String capacityReleaseId) throws Exception {
        User userByTicketId = LoginStatusUtils.getUserByTicketId(ticketId);
        
        CoopMsgBus coopMsgBus = new CoopMsgBus();
        
        coopMsgBus.setMsgId(msgId);
        coopMsgBus.setBusTypeCode(Constant.DISPOSITION_CAPACITY_RELEASE);
        coopMsgBus.setBusId(capacityReleaseId);
        coopMsgBus.setCreateBy(userByTicketId.getUserId());
        coopMsgBus.setCreateTime(Util.datetimeToString(new Date()));
        coopMsgBus.setEditBy(userByTicketId.getUserId());
        coopMsgBus.setEditTime(Util.datetimeToString(new Date()));
        coopMsgBus.setValid(Constant.IS_VALID);
        return coopMsgBus;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void termination(List<String> licenceIds, String ticketId) throws Exception {
    	if (licenceIds == null || licenceIds.isEmpty()) {
            return ;
        }
        String auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_TERMINATION).getId();
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        for(String licenceId : licenceIds){
        	DispositionCapacityRelease releaseInfo = capacityReleaseService.getDispositionCapacityReleaseByLicId(licenceId);
        	releaseInfo.setReleaseStatus(ReleaseStatus.TERMINATION.getStatusCode());
        	capacityReleaseService.updateDispositionCapacityRelease(releaseInfo, user);
        }
		
	}
}
