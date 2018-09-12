package com.mlsc.waste.user.service.impl;

import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserRegisterService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.utils.TableNameConstants;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING )
public class UserExtendedServiceTest {
    @Autowired
    private UserExtendedService userExtendedService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private PlatformSupporter platformSupporter;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private static RPCSysUser sysUser;
    
    private static User user;
    
    private static String ticketId ;
    

    public  void preparedData() throws Exception{
        
        sysUser = new RPCSysUser();
        sysUser.setLoginName("11220046888");
        sysUser.setPhoneNum("11220046888");
        sysUser.setChineseName("junit-测试用户");
        sysUser.setPassword("junit-test-password");
        sysUser.setUserType(0);
        ticketId = userRegisterService.userRegisterSave(sysUser,null).getString("ticketId");
        
        user = LoginStatusUtils.getUserByTicketId(ticketId);
        sysUser =  userService.getUserInfoByPhoneNum(ticketId,user.getPhoneNo());
        
        
    }
    
   @Test
   public void TestUserExtendedServiceA()throws Exception{
        preparedData();
        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(sysUser.getUserId());
        Assert.assertNotNull(userExtended);
        Assert.assertNull(userExtended.getEmailStatus());
        userExtended.setEmailStatus("1");
//        userExtendedService.updateUserExtended(userExtended, ticketId);
        UserExtended userExtendedAfter = userExtendedService.getUserExtendedById(userExtended.getId());
        Assert.assertEquals("1", userExtendedAfter.getEmailStatus());
        
        userExtendedService.updateUserExtended(sysUser.getUserId());
        
        userExtendedAfter = userExtendedService.getUserExtendedById(userExtended.getId());
        Assert.assertEquals("1",userExtendedAfter.getIsWastecycleInit());
        
    }
    
    @Test
    public void TestUserExtendedServiceB()throws Exception{
    	preparedData();
        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(sysUser.getUserId());
        userExtendedService.saveOrUpdateUserResgistration(userExtended.getId(), "123", ticketId);
        userExtended = userExtendedService.getUserExtendedByUserId(sysUser.getUserId());
        Assert.assertEquals(userExtended.getRegistrationCode(), "123");
        
        userExtendedService.saveOrUpdateUserResgistration(userExtended.getId(), "456", ticketId);
         userExtended = userExtendedService.getUserExtendedByUserId(sysUser.getUserId());
        Assert.assertEquals(userExtended.getRegistrationCode(), "456");
       
        
    }
    
    
    @Test
    public  void TestUserExtendedServiceZ() throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();// 执行sql所需的参数
        StringBuffer tempSql = new StringBuffer();// 执行sql的字符串拼接工具
        
        platformSupporter.getIRPCServiceClient().getUserServiceManager().removeUser(ticketId, sysUser.getUserId());
        
        //删除用户企业扩展表数据 user_extended
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_USER_EXTENDED );
        tempSql.append(" where sys_user_id = :userId ");
        paramMap.clear();
        paramMap.put("userId", sysUser.getUserId());
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
    }
}
