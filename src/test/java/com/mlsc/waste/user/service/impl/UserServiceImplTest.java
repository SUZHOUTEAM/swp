package com.mlsc.waste.user.service.impl;

import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.api.service.ISysUserService.Iface;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;
import com.mlsc.yifeiwang.sms.service.SmsService;
import com.mlsc.waste.user.dao.UserExtendedDao;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.user.service.UserRegisterService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.mlsc.yifeiwang.sms.common.SmsTemplateCode.SMS_12856054;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class UserServiceImplTest {
    
    @Autowired
    private UserRegisterService userRegisterService;
    
    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserExtendedService userExtendedService;
    
    @Autowired
    private UserExtendedDao userExtendedDao;
    @Autowired
    private IRPCServiceClient client;
    
    @Test
    public void testIsPhoneNumExist() throws Exception {
        assertTrue(userService.isPhoneNumExist("1111","15301540690"));
        assertFalse(userService.isPhoneNumExist("1111","15301540691"));
    }
    
    @Test
    public void testGetIdentifyCode() throws Exception {
        SendMsgParameter sendMsgParameter = new SendMsgParameter("15301540690", SMS_12856054);
        
        sendMsgParameter.getSmsParam().put("product", "易废网");
        String identifyCode = "9999";//junit测试 写死
        sendMsgParameter.getSmsParam().put("code", identifyCode);
        smsService.getIdentifyCode(sendMsgParameter, null);
    }
    
    @Test
    public void testCheckIdentifyCode() throws Exception {
        try{
            userService.checkIdentifyCode("15301540691","9999");
        }catch(Exception ex){
            assertTrue(ex instanceof WasteBusinessException);
            assertTrue(((WasteBusinessException) ex).getMessageBean() != null);
            assertTrue("验证码过期了".equals(((WasteBusinessException) ex).getMessageBean().getErrorContent()));
        }
        
        try{
            userService.checkIdentifyCode("15301540690","9998");
        }catch(Exception ex){
            assertTrue(ex instanceof WasteBusinessException);
            assertTrue(((WasteBusinessException) ex).getMessageBean() != null);
            assertTrue("验证码错误".equals(((WasteBusinessException) ex).getMessageBean().getErrorContent()));
        }
        
        assertTrue("1".equals(userService.checkIdentifyCode("15301540690","9999")));
    }
    
    @Test
    public void testUpdateUserName() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        assertTrue("朱高玲".equals(user.getUserName()));
//        userService.updateUserName(ticketId, user.getUserId(), "朱高玲999");
        
        Iface userServiceManager = client.getUserServiceManager();
        RPCSysUser rpcSysUser = userServiceManager.queryUserInfoByUserId(ticketId, user.getUserId());
        assertTrue("朱高玲999".equals(rpcSysUser.getChineseName()));
        
//        userService.updateUserName(ticketId, user.getUserId(), "朱高玲");
    }
    
    @Test
    public void testUpdateUserPassword() throws Exception {
        Iface userServiceManager = client.getUserServiceManager();
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        RPCSysUser rpcSysUser = userServiceManager.queryUserInfoByUserId(ticketId, user.getUserId());
        assertTrue("test123".equals(rpcSysUser.getPassword()));
        
        userService.updateUserPassword(ticketId, user.getUserId(), "test125");
        RPCSysUser rpcSysUser1 = userServiceManager.queryUserInfoByUserId(ticketId, user.getUserId());
        assertTrue("test125".equals(rpcSysUser1.getPassword()));
        
        userService.updateUserPassword(ticketId, user.getUserId(), "test123");
    }
    
    @Test
    public void testUpdateUserPasswordByPhoneNum() throws Exception {
        Iface userServiceManager = client.getUserServiceManager();
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        
        RPCSysUser rpcSysUser = userServiceManager.queryUserInfoByUserId(ticketId, user.getUserId());
        assertTrue("test123".equals(rpcSysUser.getPassword()));
        
        userService.updateUserPasswordByPhoneNum(ticketId, "15301540690", "test125");
        RPCSysUser rpcSysUser1 = userServiceManager.queryUserInfoByUserId(ticketId, user.getUserId());
        assertTrue("test125".equals(rpcSysUser1.getPassword()));
        
        userService.updateUserPassword(ticketId, user.getUserId(), "test123");
    }
    
    @Test
    public void testIsPasswordCorrect() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        
        assertFalse(userService.isPasswordCorrect(ticketId, user.getUserId(), "test125"));
        assertTrue(userService.isPasswordCorrect(ticketId, user.getUserId(), "test123"));
    }
    
    @Test
    public void testIsPasswordCorrectByPhoneNum() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        
        assertFalse(userService.isPasswordCorrectByPhoneNum(ticketId, "15301540690", "test125"));
        assertTrue(userService.isPasswordCorrectByPhoneNum(ticketId, "15301540690", "test123"));
    }
    
    @Test
    public void testIsMailAddressExist() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        
        assertTrue(userService.isMailAddressExist(ticketId, "anc@ac.com").equals(Constant.IS_VALID));
        assertFalse(userService.isMailAddressExist(ticketId, "anc@acqq.com").equals(Constant.IS_VALID));
    }
    
    @Test
    public void testUpdateUserMailAddress() throws Exception {
        Iface userServiceManager = client.getUserServiceManager();
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        
        RPCSysUser rpcSysUser = userServiceManager.queryUserInfoByUserId(ticketId, user.getUserId());
        assertTrue("zhugaoling168@163.com".equals(rpcSysUser.getEmailAddress()));
        userService.updateUserMailAddress(ticketId, user.getUserId(), "zhugaoling168789@163.com");
        RPCSysUser rpcSysUser1 = userServiceManager.queryUserInfoByUserId(ticketId, user.getUserId());
        assertTrue("zhugaoling168789@163.com".equals(rpcSysUser1.getEmailAddress()));
        userService.updateUserMailAddress(ticketId, user.getUserId(), "zhugaoling168@163.com");
    }
    
    @Test
    public void testUpdateUserPhone() throws Exception {
        Iface userServiceManager = client.getUserServiceManager();
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        RPCSysUser rpcSysUser = userServiceManager.queryUserInfoByUserId(ticketId, user.getUserId());
        assertTrue("15301540690".equals(rpcSysUser.getPhoneNum()));
        
        userService.updateUserPhone(ticketId, user.getUserId(), "15301540691");
        
        RPCSysUser rpcSysUser1 = userServiceManager.queryUserInfoByUserId(ticketId, user.getUserId());
        assertTrue("15301540691".equals(rpcSysUser1.getPhoneNum()));
        
        userService.updateUserPhone(ticketId, user.getUserId(), "15301540690");
    }
    
    @Test
    public void testGetUserInfoByUserId() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        
        RPCSysUser rpcSysUser = userService.getUserInfoByUserId(ticketId, user.getUserId());
        assertTrue("15301540690".equals(rpcSysUser.getPhoneNum()));
        assertTrue("15301540690".equals(rpcSysUser.getLoginName()));
        assertTrue("test123".equals(rpcSysUser.getPassword()));
        assertTrue("朱高玲".equals(rpcSysUser.getChineseName()));
    }
    
    @Test
    public void testGetUserInfoByPhoneNum() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        
        RPCSysUser rpcSysUser = userService.getUserInfoByPhoneNum(ticketId, "15301540690");
        assertTrue("15301540690".equals(rpcSysUser.getPhoneNum()));
        assertTrue("15301540690".equals(rpcSysUser.getLoginName()));
        assertTrue("test123".equals(rpcSysUser.getPassword()));
        assertTrue("朱高玲".equals(rpcSysUser.getChineseName()));
    }
    
    @Test
    public void testUserRegisterSave() throws Exception {
        RPCSysUser sysUser = initialUserInfo();
        RPCSysUser rpcSysUser = userService.userRegisterSave(sysUser);
        assertTrue(StringUtils.isNotBlank(rpcSysUser.getUserId()));
        assertTrue("15301540699".equals(rpcSysUser.getPhoneNum()));
        assertTrue("junit测试".equals(rpcSysUser.getChineseName()));
        
        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(rpcSysUser.getUserId());
        assertTrue(userExtended != null);
        
        Iface userServiceManager = client.getUserServiceManager();
        userServiceManager.removeUser("11111", rpcSysUser.getUserId());
        userExtendedDao.delete(userExtended.getId());
    }
    
    private RPCSysUser initialUserInfo () {
        RPCSysUser sysUser = new RPCSysUser();
        // 登录名
        sysUser.setLoginName("15301540699");
        // 手机号码
        sysUser.setPhoneNum("15301540699");
        // 姓名
        sysUser.setChineseName("junit测试");
        // 密码
        sysUser.setPassword("junitTest");
        
        return sysUser;
    }
    
    @Test
    public void testGetUserExtendedInfoByUserId() {
        // fail("Not yet implemented");
    }
    
    @Test
    public void testGetMailStatusAndMobileStatus() {
        // fail("Not yet implemented");
    }
    
    @Test
    public void testGetUserInfoByEntId() {
        // fail("Not yet implemented");
    }
    
    @Test
    public void testSendMsgsSendMsgParameterString() {
        // fail("Not yet implemented");
    }
    
    @Test
    public void testSendMsgsListOfSendMsgParameterString() {
        // fail("Not yet implemented");
    }
    
}
