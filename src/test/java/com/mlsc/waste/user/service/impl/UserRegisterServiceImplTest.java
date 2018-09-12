package com.mlsc.waste.user.service.impl;

import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.api.service.ISysUserService.Iface;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.im.service.ImService;
import com.mlsc.waste.user.dao.UserExtendedDao;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserRegisterService;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml", "classpath:spring.xml"})
public class UserRegisterServiceImplTest {
    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserExtendedService userExtendedService;

    @Autowired
    private UserExtendedDao userExtendedDao;
    @Autowired
    private IRPCServiceClient client;

    @Autowired
    private ImService imService;

    @Test
    public void testIsPhoneNumExist() throws Exception {
        assertTrue(userRegisterService.isPhoneNumExist("15301540690"));
        assertFalse(userRegisterService.isPhoneNumExist("15301540691"));
    }

    @Test
    public void testGetIdentifyCode() throws Exception {
        // fail("Not yet implemented");
    }

    @Test
    public void testCheckIdentifyCode() throws Exception {
        // fail("Not yet implemented");
    }

    @Test
    public void testUserRegisterSave() throws Exception {
        RPCSysUser sysUser = initialUserInfo();
        String ticketId = userRegisterService.userRegisterSave(sysUser,null).getString("ticketId");
        assertTrue(userRegisterService.isPhoneNumExist("15301540699"));

        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        assertTrue(user != null);
        assertTrue("15301540699".equals(user.getPhoneNo()));
        assertTrue("junit测试".equals(user.getUserName()));

        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(user.getUserId());
        assertTrue(userExtended != null);

        Iface userServiceManager = client.getUserServiceManager();
        userServiceManager.removeUser(ticketId, user.getUserId());
        userExtendedDao.delete(userExtended.getId());
    }

    /**
     * @Override public UserExtended saveUserExtended (RPCSysUser sysUser, String ticketId) throws Exception{
     * UserExtended record = new UserExtended();
     * record.setSysUserId(sysUser.getUserId());
     * record.setMobileStatus(Constant.IS_VALID);
     * record.setToken(imService.genterateIMToken(sysUser.getPhoneNum(),sysUser.getChineseName()));
     * userExtendedService.saveUserExtended(record, ticketId);
     * return record;
     */
    @Test
    public void testsaveUserExtended() throws Exception {
        RPCSysUser sysUser = new RPCSysUser();
        sysUser.setUserId("100000000000010");
        sysUser.setPhoneNum("10000000010");
        sysUser.setChineseName("临时帐号10");
        userRegisterService.saveUserExtended(sysUser, "123123123132",null);
    }


    private RPCSysUser initialUserInfo() {
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

}
