package com.mlsc.waste.enterprisemanagement.service.impl;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.solr.SimpleSolr;
import com.mlsc.solr.model.EnterpriseIndex;
import com.mlsc.solr.util.DocumentUtil;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprise.service.EnterpriseExtendedService;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.enterprisemanagement.service.EnterpriseManagementService;
import com.mlsc.waste.fw.model.SysCanton;
import com.mlsc.waste.fw.service.SysCantonService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.userenterpriseapproverecord.service.UserEnterpriseApproveRecordService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.UserStatus;
import com.mlsc.waste.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 */
@Service("EnterpriseManagementService")
public  class EnterpriseManagementServiceImpl implements EnterpriseManagementService{
    private final static Logger logger = LoggerFactory.getLogger(EnterpriseManagementServiceImpl.class);
    
    @Autowired
    private ICodeValueService codeValueService;
    
    @Autowired
    private EnterpriseService enterpriseService;
    
    @Autowired
    private UserEnterpriseApproveRecordService recordService;
    
    @Autowired
    private EnterpriseExtendedService enterpriseExtendedService;
    
    @Autowired
    private UserExtendedService userExtendedService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private IRPCServiceClient client;

    @Autowired
    private SysMsgServcie sysMsgServcie;

    @Autowired
    private SysCantonService sysCantonService;

    
    @Override
    public int getAllEnterpriseBaseCount(String sql, Map<String, Object> paramMap) {
        int count = 0;
        try {
            count = enterpriseExtendedService.getAllEnterpriseStatusCount(sql, paramMap);
        } catch (Exception e) {
            logger.error("平台管理员获取企业列表件数时异常", e);
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public List<RPCSysEnterpriseBaseVo> getAllEnterpriseBaseList(String sql, Map<String, Object> paramMap, PagingParameter paging) {
        List<RPCSysEnterpriseBaseVo> enterpriseBaseList = new ArrayList<RPCSysEnterpriseBaseVo>();
        try {
            enterpriseBaseList = enterpriseExtendedService.getAllEnterpriseStatusList(sql, paramMap, paging);
            if (enterpriseBaseList != null) {
            	for (RPCSysEnterpriseBaseVo baseVo : enterpriseBaseList) {
                    baseVo.setEntTypes(codeValueService.getEnterpriseTypesByEntId(baseVo.getEntId()));
                    baseVo.setEnterpriseStatusLabel(getEventStatus(baseVo.getEnterpriseStatus(), baseVo.getEnterpriseStatusLabel()));
                    if(StringUtils.isNotBlank(baseVo.getResponsibleArea())){
                        SysCanton sysCanton = sysCantonService.queryCantonNameByCantonCode(baseVo.getResponsibleArea());
                        if(sysCanton!=null){
                            baseVo.setResponsibleAreaName(sysCanton.getCantonName());
                        }

                    }

                }
            }
        } catch (Exception e) {
            logger.error("平台管理员获取企业列表时异常", e);
            throw new RuntimeException(e);
        }
        return enterpriseBaseList;
    }
    
    // 对企业进行标注是否通过平台验证
    private String getEventStatus(String enterpriseStatusCode, String enterpriseStatusLabel) {
        String statusString = null;
        switch (enterpriseStatusCode) {
            case CodeTypeConstant.USER_EVENT_STATUS_SUBMIT:
                statusString = enterpriseStatusLabel + "（待验证）";
                break;
            case CodeTypeConstant.USER_EVENT_STATUS_PASS:
                statusString = enterpriseStatusLabel + "（已验证）";
                break;
            case CodeTypeConstant.USER_EVENT_STATUS_REFUSED:
                statusString = enterpriseStatusLabel + "（未通过验证）";
                break	;
            case CodeTypeConstant.USER_EVENT_STATUS_REVERSED:
                statusString = "申请已撤销";
                break;
            default:
                break;
        }
        return statusString;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> updateRecords(String ticketId ,String[] enterpriseIds,String eventStatus){
        List<String> result = new ArrayList<>();
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            CodeValue codeValueByCode = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS, eventStatus);
            for (String enterpriseId : enterpriseIds) {
                recordService.updateRecordByEnpId(enterpriseId,codeValueByCode.getId());
                enterpriseExtendedService.updateByEnpId(enterpriseId,codeValueByCode.getId(),Constant.IS_VALID);
                UserExtended userExtended = userExtendedService.getAdminUserByEnterpriseId(enterpriseId);
                if(userExtended != null){
                    userExtended.setUserStatus(UserStatus.PASS.getStatusCode());
                	userExtended.setUpdateTime(Util.datetimeToString(new Date()));
                    userExtendedService.updateUserExtended(userExtended);
                    result.add(userExtended.getSysUserId());
                    //发送通知
                    User receiver = userService.getUserByUserId(userExtended.getSysUserId());
                    MsgEvent msgEvent = new MsgEvent(SmsAction.USER_CREATE_ENT_PASS);
                    msgEvent.getPlaceholderValueMap().put("entName",receiver.getEnterpriseName());
                    msgEvent.setSendUser(user);
                    msgEvent.setReceiveUser(receiver);
                    sysMsgServcie.sendMessageAsync(msgEvent);
                }
            }
            
            
            
        } catch (Exception e) {
            logger.error("管理员审核新加入企业失败", e);
            throw new RuntimeException(e);
        }
        return  result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> updateEnterRefuse(String[] enterpriseIds,String eventStatus,String ticketId){
        List<String> result = new ArrayList<>();
        try {
            CodeValue codeValueByCode = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS, eventStatus);
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            for (String enterpriseId : enterpriseIds) {
                recordService.updateRecordByEnpId(enterpriseId,codeValueByCode.getId());
                enterpriseExtendedService.updateByEnpId(enterpriseId,codeValueByCode.getId(),Constant.IS_NOT_VALID);
                UserExtended userExtended = userExtendedService.getAdminUserByEnterpriseId(enterpriseId);
                if(userExtended!=null){

                    User receiver = userService.getUserByUserId(userExtended.getSysUserId());

                	 userExtended.setUserStatus( UserStatus.REJECT.getStatusCode());
                	 userExtended.setUpdateTime(Util.datetimeToString(new Date()));
                     userExtendedService.updateUserExtended(userExtended);
                     removeUserEnterpriseRelationByEnterId(enterpriseId,userExtended.getSysUserId(),ticketId);
                    result.add(userExtended.getSysUserId());

                    //发送通知
                    MsgEvent msgEvent = new MsgEvent(SmsAction.USER_CREATE_ENT_REFUSE);
                    msgEvent.getPlaceholderValueMap().put("entName",receiver.getEnterpriseName());
                    msgEvent.setSendUser(user);
                    msgEvent.setReceiveUser(receiver);
                    sysMsgServcie.sendMessageAsync(msgEvent);
                }
               
            }
            
            
        } catch (Exception e) {
            logger.error("管理员审核新加入企业失败", e);
            throw new RuntimeException(e);
        }
        return  result;
    }

	public void removeUserEnterpriseRelationByEnterId(String enterpriseId,String userId,String ticketId) {
		try {
			 client.getOrgComServiceManager().removeUserEnterpriseRelation(ticketId, userId, enterpriseId);
		} catch (Exception e) {
            logger.error("删除企业下所有用户企业关系时报错", e);
            throw new RuntimeException(e);
        }
		
	}


	@Override
	public List<EnterpriseIndex> enterpriseIndexList(
			Map<String, Object> paramMap, PagingParameter paging) {
		List<EnterpriseIndex> resultList =  new ArrayList<EnterpriseIndex>();
		SimpleSolr simpleSolr = null;
		String entType = "DISPOSITION";
		try {
			String entName = null;
			if(paramMap.keySet().size()>0){
				entName = (String) paramMap.get("entName");
			}

			simpleSolr = new SimpleSolr(entType);

			SolrQuery params = new SolrQuery();
			if (StringUtils.isBlank(entName)) {
				params.setQuery("*:*");
			} else {
				entName = entName.replace(" ", ",");
				params.setQuery("entName:" + entName);
			}

			params.set("start", paging.getStart());
			params.set("rows", paging.getLimit());

			QueryResponse rsp = simpleSolr.getClient().query(params);
			SolrDocumentList docs = rsp.getResults();
			paging.setTotalRecord((int)docs.getNumFound());
			 for (SolrDocument record : docs) {
				 resultList.add(DocumentUtil.toBean(record, EnterpriseIndex.class));
		     }

		} catch (Exception e) {
			logger.error("根据条件查询企业失败(门户首页)", e);
			throw new RuntimeException(e);
		}finally{
			if(simpleSolr!=null){
				simpleSolr.close();
			}

		}
        return resultList;
	}

	@Override
	public void deleteEnterpriseIndex(String[] enterpriseIds) {
		SimpleSolr simpleSolr = null;
		String entType = "DISPOSITION";
		try {
			simpleSolr = new SimpleSolr(entType);
			for (String entId : enterpriseIds) {
				simpleSolr.deleteById(entId);
			}

		} catch (Exception e) {
			logger.error("删除企业索引时异常", e);
			throw new RuntimeException(e);
		}finally{
			if(simpleSolr!=null){
				simpleSolr.close();
			}

		}
	}
}