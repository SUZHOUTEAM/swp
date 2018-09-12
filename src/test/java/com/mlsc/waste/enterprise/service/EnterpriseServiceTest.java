package com.mlsc.waste.enterprise.service;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.dao.EnterpriseTypeDao;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprise.model.WasteEnterpriseType;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.user.service.UserRegisterService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.userenterpriseapproverecord.model.AuditUserRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.service.UserEnterpriseApproveRecordService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.dao.WasteDao;
import com.mlsc.waste.wastedirectory.dao.WasteNameDao;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteName;
import com.mlsc.waste.wastedirectory.service.WasteNameService;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING )
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class EnterpriseServiceTest {
    
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EnterpriseTypeDao enterpriseTypeDao;

    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private UserEnterpriseApproveRecordService userEnterpriseApproveRecordService;
    

    @Autowired
    private UserRegisterService userRegisterService;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    private WasteNameService wasteNameService;

    @Autowired
    private WasteDao wasteDao;
    
    @Autowired
    private WasteNameDao wasteNameDao;
    
    @Autowired
    private PlatformSupporter platformSupporter;
    
    @Autowired
    private UserService userService;

    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;
    
    private static RPCSysEnterpriseBase enterpriseInfo;
    
    private static RPCSysUser sysUser;
    
    private static User user;
    
    private static String ticketId ;
    
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
    
    
    
    
    private void getUserInfo() throws Exception{
        sysUser = new RPCSysUser();
        sysUser.setLoginName("11220046881");
        sysUser.setPhoneNum("11220046881");
        sysUser.setChineseName("junit-测试用户");
        sysUser.setPassword("junit-test-password");
        sysUser.setUserType(0);
    }
    
    @Test
    public void TestEnterpriseServiceA() throws Exception{
        getEnterpiseInfo();
        getUserInfo();
        
        ticketId = userRegisterService.userRegisterSave(sysUser,null).getString("ticketId");
        user = LoginStatusUtils.getUserByTicketId(ticketId);
        sysUser =  userService.getUserInfoByPhoneNum(ticketId,user.getPhoneNo());

       
        String enterpriseId  =  enterpriseService.saveEnterpriseInformation(ticketId, enterpriseInfo);
        enterpriseInfo.setEntId(enterpriseId);
        Assert.assertTrue(enterpriseService.getEnterpriseInfoById(ticketId,enterpriseId)!=null);
        Assert.assertTrue(enterpriseService.getEnterpriseInfoById(ticketId,enterpriseId).getEntName().equals("张三处置危废有限公司"));
        List<AuditUserRecordVo> recordList = userEnterpriseApproveRecordService.getUserEnterpriseApproveRecords(CodeTypeConstant.USER_EVENT_TYPE_CREATE, CodeTypeConstant.USER_EVENT_STATUS_SUBMIT, enterpriseInfo.getEntId());
        Assert.assertTrue(recordList.get(0).getId() != null);
        
       
    }   
    
    
    @Test
    public void TestEnterpriseServiceB() throws Exception{
        String cantonCode = enterpriseService.getCanonCode(ticketId, "吴江区", "苏州市");
        Assert.assertTrue(cantonCode.equals("320509"));
    }   
    
    @Test
    public void TestEnterpriseServiceC() throws Exception{
        
        List<CodeValue> enterpriseTypeCodeList = codeValueService.getEnterpriseTypesByEntId(enterpriseInfo.getEntId());
        Assert.assertTrue(enterpriseTypeCodeList==null||enterpriseTypeCodeList!=null && enterpriseTypeCodeList.size() == 0);
        Assert.assertTrue(enterpriseService.isEnterpriseVaild(ticketId)==false);
        
        Map<String, String> enterpriseTypeMap = new HashMap<String, String>();
        enterpriseTypeMap.put(CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION, "12");
        enterpriseTypeMap.put(CodeTypeConstant.ENTERPRISE_TYPE_DISPOSITION, "");
        
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        List<WasteEnterpriseType> enterpriseTypeList = null;
//        enterpriseTypeList = enterpriseService.getWasteEnterpriseTypesByEnterpriseId(enterpriseInfo.getEntId());
//        enterpriseTypeCodeList = enterpriseService.getCodeValueByEnterpriseTypeId(enterpriseTypeList);
        boolean checkFlag = false;
        for(CodeValue enterpriseTypeCode:enterpriseTypeCodeList){
            if(CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equals(enterpriseTypeCode.getCode())){
                checkFlag = true;
            }
        }
        Assert.assertTrue(enterpriseService.isEnterpriseVaild(ticketId));
        Assert.assertTrue(checkFlag);
    }
    
    @Test
    public void TestEnterpriseServiceE() throws Exception{
        
        RPCSysEnterpriseBaseVo enterpriseUpdate =  enterpriseBaseService.getEnterpriseByEntId(enterpriseInfo.getEntId());
                
        enterpriseUpdate.setEntName("李四处置危废有限公司");
        
        enterpriseService.updateEnterpriseBase(ticketId, enterpriseUpdate,null);
        

    }
    
    

    
    
    @Test
    public void TestEnterpriseServiceG() throws Exception{
        
        Waste waste = new Waste();
        waste.setCalling_id(Util.uuid32());
        waste.setWaste_type_id(Util.uuid32());
        waste.setCode("222YYY");
        waste.setDescription("hahhahhah");
        waste.setStatus("1");
        waste.setCreate_by("systemUser");
        waste.setCreate_time(Util.datetimeToString(new Date()));
        waste.setEdit_by("systemUser");
        waste.setEdit_time(Util.datetimeToString(new Date()));
        waste.setCorrosivity("1");
        waste.setToxicity("1");
        waste.setIgnitability("1");
        waste.setReactivity("1");
        waste.setInfectivity("1");
        String id1 = Util.uuid32();
        wasteDao.save(waste, id1);
        
        WasteName wasteName = new WasteName();
        wasteName.setCreate_by("1111111111");
        wasteName.setCreate_time(Util.datetimeToString(new Date()));
        wasteName.setDescription("222222222222222222");
        wasteName.setEdit_by("11111122222222");
        wasteName.setEdit_time(Util.datetimeToString(new Date()));
        wasteName.setName("测试危废784574");
        wasteName.setStatus("2");
        wasteName.setWaste_id(id1);
        String id = wasteNameService.saveWasteName(wasteName, ticketId);
        Map<String, Object> datamap  = enterpriseService.getWasteNameDropDownList("测试危废784574");
        JSONArray entArray = (JSONArray) datamap.get("value");
        Assert.assertTrue(entArray.size()==1);
        
       datamap  = enterpriseService.getCodeWastesDropDownList("222YYY");
       entArray = (JSONArray) datamap.get("value");
       Assert.assertTrue(entArray.size()==1);
        
        
        wasteDao.delete(id1);
        wasteNameDao.delete(id);
        
        List<WasteEnterpriseType> enterpriseTypeList = null;
//        enterpriseTypeList = enterpriseService.getWasteEnterpriseTypesByEnterpriseId(enterpriseInfo.getEntId());
//
//        enterpriseService.deleteEnterpriseTypeById(enterpriseTypeList.get(0).getId());
//
//        enterpriseTypeList = enterpriseService.getWasteEnterpriseTypesByEnterpriseId(enterpriseInfo.getEntId());
        
        Assert.assertTrue(enterpriseTypeList==null || enterpriseTypeList!=null && enterpriseTypeList.size()==0);
        
    }
    
    @Test
    public void TestEnterpriseServiceZZ() throws Exception{
        
        removeUser(ticketId, sysUser.getUserId(), enterpriseInfo.getEntId());
        removeEnterprise(enterpriseInfo.getEntId());
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
        
        //删除用户企业扩展表数据 user_enterprise_approve_record
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_USER_ENTERPRISE_APPROVE_RECORD );
        tempSql.append(" where user_id = :userId ");
        paramMap.clear();
        paramMap.put("userId", userId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
        
        
        //删除关注表 cooperation_relation
        tempSql = new StringBuffer("delete from  " + TableNameConstants.TABLE_COOPERATION_RELATION );
        tempSql.append(" where user_id = :user_id ");
        paramMap.clear();
        paramMap.put("user_id", userId);
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
        
        
       
    }
   
}
