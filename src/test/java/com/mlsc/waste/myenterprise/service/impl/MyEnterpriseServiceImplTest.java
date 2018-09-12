/**
 * 
 */
package com.mlsc.waste.myenterprise.service.impl;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.dao.EnterpriseTypeDao;
import com.mlsc.waste.enterprise.service.EnterpriseExtendedService;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.myenterprise.service.MyEnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.user.service.UserRegisterService;
import com.mlsc.waste.userenterpriseapproverecord.dao.UserEnterpriseApproveRecordDao;
import com.mlsc.waste.userenterpriseapproverecord.model.AuditUserRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecord;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.service.UserEnterpriseApproveRecordService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.dao.WasteDao;
import com.mlsc.waste.wastedirectory.dao.WasteNameDao;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class MyEnterpriseServiceImplTest {
    
    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private UserRegisterService userRegisterService;
    
    @Autowired
    private PlatformSupporter platformSupporter;
    
    @Autowired
    private MyEnterpriseService myEnterpriseService;

    @Autowired
    private UserEnterpriseApproveRecordDao userEntRecordDao;
    
    @Autowired
    private UserEnterpriseApproveRecordService userEntRecordService;
    
    @Autowired
    private UploadfileService uploadfileService;
    
    @Autowired
    private EnterpriseExtendedService enterpriseExtendedService;
    
    @Autowired
    private ICodeValueService codeValueService;
    
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EnterpriseTypeDao enterpriseTypeDao;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    private WasteDao wasteDao;
    @Autowired
    private WasteNameDao wasteNameDao;
    
    /**
     * Test method for {@link com.mlsc.waste.myenterprise.service.impl.MyEnterpriseServiceImpl#isBindedEnterprise(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testIsBindedEnterprise() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        assertTrue(myEnterpriseService.isBindedEnterprise(ticketId));
        
        ticketId = userLoginService.userLogin("15001878006", "1qazxsw2");
        assertFalse(myEnterpriseService.isBindedEnterprise(ticketId));
    }
    
    private void removeUser(String ticketId,String userId,String entId) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();// 执行sql所需的参数
        StringBuffer tempSql = new StringBuffer();// 执行sql的字符串拼接工具
        
        platformSupporter.getIRPCServiceClient().getOrgComServiceManager().removeUserEnterpriseRelation(ticketId, userId, entId);
        platformSupporter.getIRPCServiceClient().getUserServiceManager().removeUser(ticketId, userId);
        
        //删除用户企业扩展表数据 user_extended
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_USER_EXTENDED );
        tempSql.append(" where sys_user_id = :userId ");
        paramMap.clear();
        paramMap.put("userId", userId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        //删除用户申请记录表数据 user_enterprise_approve_record
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_USER_ENTERPRISE_APPROVE_RECORD );
        tempSql.append(" where user_id = :userId ");
        paramMap.clear();
        paramMap.put("userId", userId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
    }
    
    private void removeEnterprise (String enterpriseId) throws Exception {
        String ticketId = "123465789";
        Map<String, Object> paramMap = new HashMap<String, Object>();// 执行sql所需的参数
        StringBuffer tempSql = new StringBuffer();// 执行sql的字符串拼接工具
        Iface service = platformSupporter.getOrgComServiceManager();
        service.removeEnterprise(ticketId, enterpriseId);
        
        //删除用户企业扩展表数据 sys_user_enterprise_relation
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_ENTERPRISE_EXTENDED );
        tempSql.append(" where sys_enterprise_base_id = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        //删除用户企业关系表数据 sys_user_enterprise_relation
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION );
        tempSql.append(" where EntId = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
       //删除企业类型表数据 waste_enterprise_type
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_WASTE_ENTERPRISE_TYPE );
        tempSql.append(" where enterprise_id = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
       //删除用户申请记录表数据 user_enterprise_approve_record
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_USER_ENTERPRISE_APPROVE_RECORD );
        tempSql.append(" where enterprise_id = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        //删除企业关联的产废信息 enterprise_waste
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_ENTERPRISE_WASTE );
        tempSql.append(" where enterprise_id = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        //删除企业关联的许可证信息 operation_licence operation_licence_item operation_licence_detail
        tempSql = new StringBuffer("delete licence_detail from  " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " licence_detail " );
        tempSql.append(" inner join  "+ TableNameConstants.TABLE_OPERATION_LICENCE + " licence on licence_detail.licence_id = licence.id ");
        tempSql.append(" where licence.enterprise_id = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        tempSql = new StringBuffer("delete licence_item from  " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM + " licence_item " );
        tempSql.append(" inner join  "+ TableNameConstants.TABLE_OPERATION_LICENCE + " licence on licence_item.licence_id = licence.id ");
        tempSql.append(" where licence.enterprise_id = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_OPERATION_LICENCE );
        tempSql.append(" where enterprise_id = :enterpriseId ");
        paramMap.clear();
        paramMap.put("enterpriseId", enterpriseId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
    }
    
    /**
     * Test method for {@link com.mlsc.waste.myenterprise.service.impl.MyEnterpriseServiceImpl#getUserEnterpriseApproveRecordVos(java.lang.String)}.
     * @throws Exception
     */
    @Test
    public void testGetUserEnterpriseApproveRecordVos() throws Exception {
        // ----------------------- 数据准备 -----------------------
        RPCSysUser sysUser = new RPCSysUser();
        sysUser.setLoginName("15301540691");
        sysUser.setPhoneNum("15301540691");
        sysUser.setChineseName("junit-测试用户");
        sysUser.setPassword("junit-test-password");
        String ticketId = userRegisterService.userRegisterSave(sysUser,null).getString("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        
        String waitJoinTicketId = userLoginService.userLogin("15301540690", "test123");//需要加入企业的Id
        User waitJoinuser = LoginStatusUtils.getUserByTicketId(waitJoinTicketId);
        RPCSysEnterpriseBase enterpriseBase = platformSupporter.getOrgComServiceManager().queryEnterpriseInfo(waitJoinTicketId, waitJoinuser.getUserId());
        
        // ----------------------- 数据准备结束 -----------------------
        try {
            List<UserEnterpriseApproveRecordVo> recordVos = myEnterpriseService.getUserEnterpriseApproveRecordVos(ticketId);
            assertTrue(recordVos == null || recordVos.size() == 0);
            
            myEnterpriseService.joinEnterprise(ticketId, null, enterpriseBase.getEntId());
            recordVos = myEnterpriseService.getUserEnterpriseApproveRecordVos(ticketId);
            assertTrue(recordVos != null && recordVos.size() == 1);
        } catch (Exception e) {
            assertTrue(false);
        } finally{
            // ----------------------- 数据删除 -----------------------
            removeUser(ticketId, user.getUserId(), enterpriseBase.getEntId());
        }
    }
    
    private RPCSysEnterpriseBase doEnterpriseInfoDataReady() throws Exception {
        RPCSysEnterpriseBase enterpriseBase = new RPCSysEnterpriseBase();
        enterpriseBase.setEntName("junit-测试企业");
        enterpriseBase.setShortName("junit-测试企业");
        enterpriseBase.setEntCode("junit-Test-001");
        enterpriseBase.setLegalName("junit-朱高玲");
        enterpriseBase.setContacts("junit-朱高玲");
        enterpriseBase.setContactsTel("15301540691");
        enterpriseBase.setContactsEMail("1353317165@qq.com");
        enterpriseBase.setFax("2521965");
        enterpriseBase.setZipCode("246738");
        enterpriseBase.setEntAddress("江苏省苏州市园区展业路18号");
        enterpriseBase.setPosx(null);
        enterpriseBase.setPosy(null);
        enterpriseBase.setCantonCode("320506");
        return enterpriseBase;
    }
    

    
    /**
     * Test method for {@link com.mlsc.waste.myenterprise.service.impl.MyEnterpriseServiceImpl#getEnterImgSrc(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetEnterImgSrc() throws Exception {
        Iface service = platformSupporter.getOrgComServiceManager();
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        
        RPCSysEnterpriseBase enterpriseBase1 = doEnterpriseInfoDataReady();
        enterpriseBase1.setEntName("junit-测试处置企业1");
        enterpriseBase1.setEntCode("junit-Test-001");
        enterpriseBase1 = service.saveEnterpriseInfo("1234567890", enterpriseBase1);
        String fileId = Util.uuid32();
     // ----------------------- 数据准备结束 -----------------------
        try {
            String enterpriseImgSrc = myEnterpriseService.getEnterImgSrc(enterpriseBase1.getEntId());
            assertTrue(StringUtils.isBlank(enterpriseImgSrc));
            
            uploadfileService.saveUploadFile(ticketId, "a" + enterpriseBase1.getEntId(), Util.uuid32(), fileId, "a", Util.uuid32());
            
            enterpriseImgSrc = myEnterpriseService.getEnterImgSrc(enterpriseBase1.getEntId());
            String testEnterImgSrc = Constant.IMG_SERVICE_URL + "&businessCode=a" + enterpriseBase1.getEntId() + "&fileID=" + fileId;
            assertTrue(testEnterImgSrc.equals(enterpriseImgSrc));
        } catch (Exception e) {
            assertTrue(false);
        } finally{
            removeEnterprise(enterpriseBase1.getEntId());
            uploadfileService.deleteFileByFileId(fileId);
        }
    }



    
    private UserEnterpriseApproveRecord doUserEntRecordJoinDataReady(String userId,String entId) throws Exception {
        UserEnterpriseApproveRecord record = new UserEnterpriseApproveRecord();
        
        CodeValue eventTypeCodeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_TYPE,CodeTypeConstant.USER_EVENT_TYPE_JOIN);
        CodeValue eventStatusCodeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS,CodeTypeConstant.USER_EVENT_STATUS_SUBMIT);
        
        record.setUserId(userId);
        record.setEnterpriseId(entId);
        record.setEventType(eventTypeCodeValue.getId());
        record.setEventStatus(eventStatusCodeValue.getId());
        record.setApplicationBy(userId);
        record.setApplicationTime(Util.datetimeToString(new Date()));
        return record;
    }
    
    private UserEnterpriseApproveRecord doUserEntRecordCreatDataReady(String userId,String entId) throws Exception {
        UserEnterpriseApproveRecord record = new UserEnterpriseApproveRecord();
        
        CodeValue eventTypeCodeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_TYPE,CodeTypeConstant.USER_EVENT_TYPE_CREATE);
        CodeValue eventStatusCodeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS,CodeTypeConstant.USER_EVENT_STATUS_SUBMIT);
        
        record.setUserId(userId);
        record.setEnterpriseId(entId);
        record.setEventType(eventTypeCodeValue.getId());
        record.setEventStatus(eventStatusCodeValue.getId());
        record.setApplicationBy(userId);
        record.setApplicationTime(Util.datetimeToString(new Date()));
        return record;
    }
    
    /**
     * Test method for {@link com.mlsc.waste.myenterprise.service.impl.MyEnterpriseServiceImpl#getUserEnterpriseApproveRecords(java.lang.String, java.lang.String, java.lang.String)}.
     * @throws DaoAccessException
     */
    @Test
    public void testGetUserEnterpriseApproveRecords() throws Exception {
     // 创建一个指定的用户
        RPCSysUser sysUser = new RPCSysUser();
        sysUser.setLoginName("15301540691");
        sysUser.setPhoneNum("15301540691");
        sysUser.setChineseName("junit-测试用户");
        sysUser.setPassword("junit-test-password");
        String ticketId = userRegisterService.userRegisterSave(sysUser,null).getString("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        
        // 创建一个指定的企业
        Iface service = platformSupporter.getOrgComServiceManager();
        RPCSysEnterpriseBase enterpriseBase1 = doEnterpriseInfoDataReady();
        enterpriseBase1.setEntName("junit-测试处置企业1");
        enterpriseBase1.setEntCode("junit-Test-001");
        enterpriseBase1 = service.saveEnterpriseInfo("1234567890", enterpriseBase1);
        
        // 构建用户行为记录
        UserEnterpriseApproveRecord record1 = doUserEntRecordJoinDataReady(user.getUserId(), enterpriseBase1.getEntId());
        userEntRecordService.saveUserEnterpriseApproveRecord(record1, ticketId);
        
        UserEnterpriseApproveRecord record2 = doUserEntRecordCreatDataReady(user.getUserId(), enterpriseBase1.getEntId());
        userEntRecordService.saveUserEnterpriseApproveRecord(record2, ticketId);
        
        // 断言开始
        try {
            List<AuditUserRecordVo> recordVos = myEnterpriseService.getUserEnterpriseApproveRecords(CodeTypeConstant.USER_EVENT_TYPE_JOIN, CodeTypeConstant.USER_EVENT_STATUS_SUBMIT, enterpriseBase1.getEntId());
            assertTrue(recordVos.size() == 1);
        } catch (Exception e) {
            assertTrue(false);
        } finally{
            // ----------------------- 数据删除 -----------------------
            removeUser(ticketId, user.getUserId(), enterpriseBase1.getEntId());
            removeEnterprise(enterpriseBase1.getEntId());
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.myenterprise.service.impl.MyEnterpriseServiceImpl#updateApproveRecordPass(java.lang.String[], java.lang.String)}.
     * @throws DaoAccessException
     */

    



    
    /**
     * Test method for {@link com.mlsc.waste.myenterprise.service.impl.MyEnterpriseServiceImpl#getUserInfoByEntId(java.lang.String)}.
     * @throws DaoAccessException
     */
    @Test
    public void testGetUserInfoByEntId() throws Exception {
        // 创建3个测试用户
        RPCSysUser sysUser1 = new RPCSysUser();
        sysUser1.setLoginName("15301540691");
        sysUser1.setPhoneNum("15301540691");
        sysUser1.setChineseName("junit-测试用户1");
        sysUser1.setPassword("junit-test-password");
        String ticketId1 = userRegisterService.userRegisterSave(sysUser1,null).getString("ticketId");
        User user1 = LoginStatusUtils.getUserByTicketId(ticketId1);
        
        RPCSysUser sysUser2 = new RPCSysUser();
        sysUser2.setLoginName("15301540692");
        sysUser2.setPhoneNum("15301540692");
        sysUser2.setChineseName("junit-测试用户2");
        sysUser2.setPassword("junit-test-password");
        String ticketId2 = userRegisterService.userRegisterSave(sysUser2,null).getString("ticketId");
        User user2 = LoginStatusUtils.getUserByTicketId(ticketId2);
        
        RPCSysUser sysUser3 = new RPCSysUser();
        sysUser3.setLoginName("15301540693");
        sysUser3.setPhoneNum("15301540693");
        sysUser3.setChineseName("junit-测试用户3");
        sysUser3.setPassword("junit-test-password");
        String ticketId3 = userRegisterService.userRegisterSave(sysUser3,null).getString("ticketId");
        User user3 = LoginStatusUtils.getUserByTicketId(ticketId3);
        
        // 创建2个测试企业
        Iface service = platformSupporter.getOrgComServiceManager();
        RPCSysEnterpriseBase enterpriseBase1 = doEnterpriseInfoDataReady();
        enterpriseBase1.setEntName("junit-测试处置企业1");
        enterpriseBase1.setEntCode("junit-Test-001");
        enterpriseBase1 = service.saveEnterpriseInfo("1234567890", enterpriseBase1);
        
        RPCSysEnterpriseBase enterpriseBase2 = doEnterpriseInfoDataReady();
        enterpriseBase2.setEntName("junit-测试产废企业2");
        enterpriseBase2.setEntCode("junit-Test-002");
        enterpriseBase2 = service.saveEnterpriseInfo("1234567890", enterpriseBase2);
        
        platformSupporter.getOrgComServiceManager().saveUserEnterpriseRelationMaster("1234567890", user1.getUserId(), enterpriseBase1.getEntId());
        platformSupporter.getOrgComServiceManager().saveUserEnterpriseRelationMaster("1234567890", user2.getUserId(), enterpriseBase1.getEntId());
        platformSupporter.getOrgComServiceManager().saveUserEnterpriseRelationMaster("1234567890", user3.getUserId(), enterpriseBase2.getEntId());
        
        // 断言开始
        try {
            List<RPCSysUser> rpcSysUsers = myEnterpriseService.getUserInfoByEntId(enterpriseBase1.getEntId());
            assertTrue(rpcSysUsers.size() == 2);
            for (RPCSysUser rpcSysUser : rpcSysUsers) {
                assertTrue(user1.getUserId().equals(rpcSysUser.getUserId()) || user2.getUserId().equals(rpcSysUser.getUserId()));
            }
            
            rpcSysUsers = myEnterpriseService.getUserInfoByEntId(enterpriseBase2.getEntId());
            assertTrue(rpcSysUsers.size() == 1);
            for (RPCSysUser rpcSysUser : rpcSysUsers) {
                assertTrue(user3.getUserId().equals(rpcSysUser.getUserId()));
            }
        } catch (Exception e) {
            assertTrue(false);
        } finally{
         // ----------------------- 数据删除 -----------------------
            removeUser(ticketId1, user1.getUserId(), enterpriseBase1.getEntId());
            removeUser(ticketId1, user2.getUserId(), enterpriseBase1.getEntId());
            removeUser(ticketId1, user3.getUserId(), enterpriseBase2.getEntId());
            removeEnterprise(enterpriseBase1.getEntId());
            removeEnterprise(enterpriseBase2.getEntId());
        }
    }
    

    

    
}
