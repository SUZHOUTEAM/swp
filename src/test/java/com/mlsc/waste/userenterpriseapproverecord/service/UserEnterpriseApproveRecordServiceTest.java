package com.mlsc.waste.userenterpriseapproverecord.service;

import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserRegisterService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.userenterpriseapproverecord.model.AuditUserRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecord;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecordVo;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.utils.Util;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING )
public class UserEnterpriseApproveRecordServiceTest {
    
    @Autowired
    private UserEnterpriseApproveRecordService userEnterpriseApproveRecordService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRegisterService userRegisterService;
    
    @Autowired
    private EnterpriseService enterpriseService;
    
    @Autowired
    private ICodeValueService codeValueService;
    
    @Autowired
    private PlatformSupporter platformSupporter;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private static RPCSysUser sysUser;
    
    private static User user;
    
    private static String ticketId ;
    
    private static RPCSysEnterpriseBase enterpriseInfo;
    
    
    public  void preparedData() throws Exception{
        
        sysUser = new RPCSysUser();
        sysUser.setLoginName("11220046882");
        sysUser.setPhoneNum("11220046882");
        sysUser.setChineseName("junit-测试用户");
        sysUser.setPassword("junit-test-password");
        sysUser.setUserType(0);
        
        
        ticketId = userRegisterService.userRegisterSave(sysUser,null).getString("ticketId");
        user = LoginStatusUtils.getUserByTicketId(ticketId);
        sysUser =  userService.getUserInfoByPhoneNum(ticketId,user.getPhoneNo());
        
        enterpriseInfo = new RPCSysEnterpriseBase();
        enterpriseInfo.setCantonCode("320509"); 
        enterpriseInfo.setContacts("");
        enterpriseInfo.setContactsEMail("");
        enterpriseInfo.setContactsTel("");
        enterpriseInfo.setEntAddress("江苏省苏州市吴江区迎燕东路");
        enterpriseInfo.setEntCode("123");   
        enterpriseInfo.setEntName("张三处置危废有限公司");
        enterpriseInfo.setEntStatus(0);
        enterpriseInfo.setFax("");
        enterpriseInfo.setLegalName("");
        enterpriseInfo.setNameSpelling("");
        enterpriseInfo.setPosx("120.72627");
        enterpriseInfo.setPosy("31.15328");
        enterpriseInfo.setShortName("张三处置危废有限公司");
        enterpriseInfo.setZipCode("");
        
        enterpriseInfo = enterpriseService.saveEnterpriseInfo(ticketId, enterpriseInfo);
        
    }
    
    @Test
    public void TestUserEnterpriseApproveRecordServiceA() throws Exception{
        preparedData() ;
        String eventTypeId =  codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_TYPE, CodeTypeConstant.USER_EVENT_TYPE_CREATE).getId();
        String eventSatusId = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS,CodeTypeConstant.USER_EVENT_STATUS_SUBMIT).getId();
        UserEnterpriseApproveRecord userEnterpriseApproveRecord = new UserEnterpriseApproveRecord();
        userEnterpriseApproveRecord.setUserId(user.getUserId());
        userEnterpriseApproveRecord.setEnterpriseId(enterpriseInfo.getEntId());
        userEnterpriseApproveRecord.setEventType(eventTypeId);
        userEnterpriseApproveRecord.setEventStatus(eventSatusId);
        userEnterpriseApproveRecord.setApplicationBy(user.getUserId());
        userEnterpriseApproveRecord.setApplicationTime(Util.datetimeToString(new Date()));
        userEnterpriseApproveRecord.setApprovedBy(null);
        userEnterpriseApproveRecord.setApprovedTime(null);
        
        String id = userEnterpriseApproveRecordService.saveUserEnterpriseApproveRecord(userEnterpriseApproveRecord, ticketId);
        List<AuditUserRecordVo> recordList =  userEnterpriseApproveRecordService.getUserEnterpriseApproveRecords(CodeTypeConstant.USER_EVENT_TYPE_CREATE, CodeTypeConstant.USER_EVENT_STATUS_SUBMIT, enterpriseInfo.getEntId());
        Assert.assertEquals(1, recordList.size());
        String eventApproveSatusId = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS,CodeTypeConstant.USER_EVENT_STATUS_PASS).getId();
        userEnterpriseApproveRecordService.updateRecordByEnpId(enterpriseInfo.getEntId(), eventApproveSatusId);
        
        recordList =  userEnterpriseApproveRecordService.getUserEnterpriseApproveRecords(CodeTypeConstant.USER_EVENT_TYPE_CREATE, CodeTypeConstant.USER_EVENT_STATUS_PASS, enterpriseInfo.getEntId());
        Assert.assertEquals(1, recordList.size());
        Assert.assertEquals(CodeTypeConstant.USER_EVENT_STATUS_PASS, recordList.get(0).getEventStatusCode());
        
        String eventRefusedSatusId = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS,CodeTypeConstant.USER_EVENT_STATUS_REFUSED).getId();
        userEnterpriseApproveRecord.setEventStatus(eventRefusedSatusId);
        userEnterpriseApproveRecordService.updateUserEnterpriseApproveRecord(userEnterpriseApproveRecord, ticketId);
        
        UserEnterpriseApproveRecord updateRecord = userEnterpriseApproveRecordService.getUserEnterpriseApproveRecordById(id);
        
        Assert.assertEquals(eventRefusedSatusId, updateRecord.getEventStatus());
        String[] eventType = {CodeTypeConstant.USER_EVENT_TYPE_CREATE};
        String[] eventStatus = {CodeTypeConstant.USER_EVENT_STATUS_REFUSED};
        List<UserEnterpriseApproveRecordVo> recordListCodeList = userEnterpriseApproveRecordService.getUserEnterpriseApproveRecordVos(sysUser.getUserId(),enterpriseInfo.getEntId(),eventType,eventStatus);
        
        Assert.assertEquals(CodeTypeConstant.USER_EVENT_STATUS_REFUSED,recordListCodeList.get(0).getEventStatusCode());
        
    }
    @Test
    public  void TestUserEnterpriseApproveRecordServiceZ() throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();// 执行sql所需的参数
        StringBuffer tempSql = new StringBuffer();// 执行sql的字符串拼接工具
        
        platformSupporter.getIRPCServiceClient().getOrgComServiceManager().removeUserEnterpriseRelation(ticketId, sysUser.getUserId(), enterpriseInfo.getEntId());
        platformSupporter.getIRPCServiceClient().getUserServiceManager().removeUser(ticketId, sysUser.getUserId());
        
        //删除用户企业扩展表数据 user_extended
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_USER_EXTENDED );
        tempSql.append(" where sys_user_id = :userId ");
        paramMap.clear();
        paramMap.put("userId", sysUser.getUserId());
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        //删除用户企业扩展表数据 user_enterprise_approve_record
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_USER_ENTERPRISE_APPROVE_RECORD );
        tempSql.append(" where user_id = :userId ");
        paramMap.clear();
        paramMap.put("userId", sysUser.getUserId());
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        
        //删除关注表 cooperation_relation
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_COOPERATION_RELATION );
        tempSql.append(" where user_id = :user_id ");
        paramMap.clear();
        paramMap.put("user_id", sysUser.getUserId());
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        tempSql = new StringBuffer();// 执行sql的字符串拼接工具
        Iface service = platformSupporter.getOrgComServiceManager();
        service.removeEnterprise(ticketId, enterpriseInfo.getEntId());
        paramMap.clear();
        
        //删除用户企业扩展表数据 sys_user_enterprise_relation
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_ENTERPRISE_EXTENDED );
        tempSql.append(" where sys_enterprise_base_id = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseInfo.getEntId());
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        //删除用户企业关系表数据 sys_user_enterprise_relation
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION );
        tempSql.append(" where EntId = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseInfo.getEntId());
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
       //删除企业类型表数据 waste_enterprise_type
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_WASTE_ENTERPRISE_TYPE );
        tempSql.append(" where enterprise_id = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseInfo.getEntId());
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
    }
    
    
  

}
