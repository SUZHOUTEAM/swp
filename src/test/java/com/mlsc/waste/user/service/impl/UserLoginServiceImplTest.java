package com.mlsc.waste.user.service.impl;

import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class UserLoginServiceImplTest {
    
    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private UserService userService;
    
    @Test
    public void testUpdateUserPasswordByPhoneNum() throws Exception {
        
        RPCSysUser user = userService.getUserInfoByPhoneNum("1111", "15301540690");
        assertTrue(user != null);
        assertTrue("test123".equals(user.getPassword()));
        userLoginService.updateUserPasswordByPhoneNum("15301540690", "test125");
        RPCSysUser userUpdate = userService.getUserInfoByPhoneNum("1111", "15301540690");
        assertTrue(userUpdate != null);
        assertTrue("test125".equals(userUpdate.getPassword()));
        
        userLoginService.updateUserPasswordByPhoneNum("15301540690", "test123");
    }
    
    @Test
    public void testUserLogin() throws Exception {
        try{
            userLoginService.userLogin("15301540691", "test123");
        }catch(Exception ex){
            assertTrue(ex instanceof WasteBusinessException);
            assertTrue(((WasteBusinessException) ex).getMessageBean() != null);
            assertTrue("手机号码不存在，请重新输入,或者先注册一个账号。".equals(((WasteBusinessException) ex).getMessageBean().getErrorContent()));
        }
        
        try{
            userLoginService.userLogin("15301540690", "test125");
        }catch(Exception ex){
            assertTrue(ex instanceof WasteBusinessException);
            assertTrue(((WasteBusinessException) ex).getMessageBean() != null);
            assertTrue("用户名或密码不正确,请重新输入。".equals(((WasteBusinessException) ex).getMessageBean().getErrorContent()));
        }
        
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        assertTrue("15301540690".equals(user.getPhoneNo()));
    }
    
}
