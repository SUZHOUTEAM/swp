package com.mlsc.waste.wastecircle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.myenterprise.service.MyEnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.user.service.UserRegisterService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.model.MessageBodyVo;
import com.mlsc.waste.wastecircle.model.SearchCondition;
import com.mlsc.waste.wastecircle.service.WasteCircleService;
import com.mlsc.waste.wastedirectory.dao.WasteDao;
import com.mlsc.waste.wastedirectory.dao.WasteNameDao;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteName;
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

@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING )
public class WasteCircleServiceImplTest {
    @Autowired
    private UserService userService;
    
    @Autowired
    private WasteDao wasteDao;
    
    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private UserRegisterService userRegisterService;
    
    @Autowired
    private WasteCircleService wasteCircleService;
    
    @Autowired
    private MyEnterpriseService myEnterpriseService;
    
    @Autowired
    private EnterpriseService enterpriseService;
    
    @Autowired
    private PlatformSupporter platformSupporter;
    
    @Autowired
    private UserExtendedService userExtendService;
    
    @Autowired
    private WasteNameDao wasteNameDao;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private static RPCSysEnterpriseBase enterpriseInfo;
    
    
    private static RPCSysUser sysUser;
    
    private static String ticketId;
    
    private static User user;
    @Autowired
    private static IRPCServiceClient client;
   
    private String getTicketId1() {
        String ticketId = null;
        try {
            ticketId = userLoginService.userLogin("18120046886", "test123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketId;
    }
    
    private String getTicketId2() {
        String ticketId = null;
        try {
            ticketId = userLoginService.userLogin("13916354217", "test123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketId;
    }
    
    private void saveUserInfo() throws Exception{
        sysUser = new RPCSysUser();
        sysUser.setLoginName("11220646881");
        sysUser.setPhoneNum("11220646881");
        sysUser.setChineseName("junit-测试用户");
        sysUser.setPassword("junit-test-password");
        sysUser.setUserType(0);
        
        ticketId = userRegisterService.userRegisterSave(sysUser,null).getString("ticketId");
        user = LoginStatusUtils.getUserByTicketId(ticketId);
        sysUser =  userService.getUserInfoByPhoneNum(ticketId,user.getPhoneNo());
        
        
    }
    
    private void getEnterpiseInfo(){
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
    }
    
    
    @Test
    public void TestWasteCircleServiceA() throws Exception{
        saveUserInfo();
        SearchCondition searchCondition = new SearchCondition();
        PagingParameter pagingParameter = new PagingParameter();
        List<MessageBodyVo>  list = wasteCircleService.getMessageListReleaseList(searchCondition, ticketId, user, pagingParameter);
        Assert.assertTrue(list.size()==0);
        
        RPCSysUser sysUser1 = userService.getUserInfoByPhoneNum(ticketId, "18120046886");
        
        RPCSysEnterpriseBase enterpriseBase = client.getOrgComServiceManager().queryEnterpriseInfo(getTicketId1(), sysUser1.getUserId());
        myEnterpriseService.joinEnterprise(ticketId, null, enterpriseBase.getEntId());
        
        list = wasteCircleService.getMessageListReleaseList(searchCondition, ticketId, user, pagingParameter);
        Assert.assertTrue(list.size()>0);
        for(MessageBodyVo messageBodyVo:list){
            Assert.assertEquals("dispositionPlanRelease", messageBodyVo.getBusCode());
        }

        client.getOrgComServiceManager().removeUserEnterpriseRelation(ticketId, sysUser.getUserId(), enterpriseBase.getEntId());
        
        
        sysUser1 = userService.getUserInfoByPhoneNum(ticketId, "13916354217");
        
        enterpriseBase = client.getOrgComServiceManager().queryEnterpriseInfo(getTicketId2(), sysUser1.getUserId());
        myEnterpriseService.joinEnterprise(ticketId, null, enterpriseBase.getEntId());
        
        list = wasteCircleService.getMessageListReleaseList(searchCondition, ticketId, user, pagingParameter);
        Assert.assertTrue(list.size()>0);
        for(MessageBodyVo messageBodyVo:list){
            Assert.assertEquals("dispositionCapacityRelease", messageBodyVo.getBusCode());
        }
        
        int count = wasteCircleService.queryMyAllCoopMsg(user);
        Assert.assertTrue(count>0);
        
        client.getOrgComServiceManager().removeUserEnterpriseRelation(ticketId, sysUser.getUserId(), enterpriseBase.getEntId());
        
    }
    @Test
    public void TestWasteCircleServiceB() throws Exception{
        
        String valid = wasteCircleService.getUserExtendedByUserId(user.getUserId());
        Assert.assertEquals(Constant.IS_NOT_VALID, valid);
        
        UserExtended record = new UserExtended();
        record.setSysUserId(user.getUserId());
        record.setMobileStatus(Constant.IS_VALID);
        record.setIsWastecycleInit(Constant.IS_NOT_VALID);
        userExtendService.saveUserExtended(record, ticketId);
        
        valid = wasteCircleService.getUserExtendedByUserId(user.getUserId());
        Assert.assertEquals(Constant.IS_NOT_VALID, valid);
        
        wasteCircleService.updateWasteCycleInit(user.getUserId());
        valid = wasteCircleService.getUserExtendedByUserId(user.getUserId());
        Assert.assertEquals(Constant.IS_VALID, valid);
        
        
        
    }
    
    @Test
    public void TestWasteCircleServiceC() throws Exception{
        getEnterpiseInfo();
        String enterpriseId  =  enterpriseService.saveEnterpriseInformation(ticketId, enterpriseInfo);
        enterpriseInfo.setEntId(enterpriseId);
        
        
        Assert.assertFalse(wasteCircleService.getFollow(ticketId, enterpriseInfo.getEntId())); 
        wasteCircleService.saveOrRemoveFollow(enterpriseInfo.getEntId(), "1", ticketId);
        Assert.assertTrue(wasteCircleService.getFollow(ticketId, enterpriseInfo.getEntId()));
    }
    
    @Test
    public void TestWasteCircleServiceY() throws Exception{
        RPCSysUser sysUser1 = userService.getUserInfoByPhoneNum(ticketId, "13916354217");
        RPCSysEnterpriseBase enterpriseBase = client.getOrgComServiceManager().queryEnterpriseInfo(getTicketId1(), sysUser1.getUserId());
        String keyWord = enterpriseBase.getEntName();
        Map<String, Object> datamap = wasteCircleService.getEntDropDownList(keyWord);
        JSONArray entArray = (JSONArray) datamap.get("value");
        Assert.assertEquals(1, entArray.size());
        
        
        Waste waste = new Waste();
        waste.setCalling_id(Util.uuid32());
        waste.setWaste_type_id(Util.uuid32());
        waste.setCode("222");
        waste.setDescription("hahhahhah");
        waste.setStatus("1");
        waste.setCreate_by(LoginStatusUtils.getUserByTicketId(ticketId).getUserId());
        waste.setCreate_time(Util.datetimeToString(new Date()));
        waste.setEdit_by(LoginStatusUtils.getUserByTicketId(ticketId).getUserId());
        waste.setEdit_time(Util.datetimeToString(new Date()));
        waste.setCorrosivity("1");
        waste.setToxicity("1");
        waste.setIgnitability("1");
        waste.setReactivity("1");
        waste.setInfectivity("1");
        String id1 = Util.uuid32();
        wasteDao.save(waste, id1);
   
        datamap = wasteCircleService.getCodeWasteDropDownList(waste.getCode());
        JSONArray wasteArray = (JSONArray) datamap.get("value");
        Assert.assertTrue(wasteArray.size()>=1);
        boolean checkflag = false;
        for(int i = 0 ;i<wasteArray.size();i++){
            JSONObject entObject = (JSONObject) wasteArray.get(i);
            if(entObject.get("code").equals(waste.getCode())){
                checkflag = true;
            }
        }
        Assert.assertTrue(checkflag);
        wasteDao.delete(id1);
        
        List<WasteName> list = wasteNameDao.query("select * from waste_name LIMIT 1;");
        String wasteName = list.get(0).getName();
        datamap = wasteCircleService.getWasteNameDropDownList(wasteName);
        wasteArray = (JSONArray) datamap.get("value");
        Assert.assertTrue(wasteArray.size()>=1);
        
    }
    
    @Test
    public void TestWasteCircleServiceZ() throws Exception{
        platformSupporter.getIRPCServiceClient().getUserServiceManager().removeUser(ticketId, sysUser.userId);
        
        
        Map<String, Object> paramMap = new HashMap<String, Object>();// 执行sql所需的参数
        StringBuffer tempSql = new StringBuffer();// 执行sql的字符串拼接工具
        
        
        //删除用户企业扩展表数据 user_extended
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_USER_EXTENDED );
        tempSql.append(" where sys_user_id = :userId ");
        paramMap.clear();
        paramMap.put("userId", sysUser.getUserId());
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
    }

}
