package com.mlsc.waste.enterprise.service;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.model.WasteEnterpriseType;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING )
public class WasteEnterpriseTypeServiceTest {
    
    @Autowired
    private WasteEnterpriseTypeService wasteEnterpriseTypeService;
    
    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private ICodeValueService codeValueService;
    
    private static String[] idArray = null; 
    
    private String getTicketId() {
        String ticketId = null;
        try {
            ticketId = userLoginService.userLogin("13916354217", "test123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketId;
    }
    
    
    @Test
    public void TestWasteEnterpriseTypeServiceA()throws DaoAccessException{
        CodeValue codeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.ENTERPRISE_TYPE, CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION);
        
        List<WasteEnterpriseType> records = new ArrayList<WasteEnterpriseType>();
        WasteEnterpriseType entType = new WasteEnterpriseType();
        User user = LoginStatusUtils.getUserByTicketId(getTicketId());
        entType.setEnterpriseId(user.getEnterpriseId());
        entType.setEnterpriseTypeId(codeValue.getId());
        records.add(entType);
        idArray = wasteEnterpriseTypeService.saveWasteEnterpriseTypes(records,getTicketId());
        
//        List<WasteEnterpriseType> wasteEnterpriseTypes = wasteEnterpriseTypeService.getWasteEnterpriseTypesByEnterpriseId(user.getEnterpriseId());
//        boolean checkFlag = false;
//        for(String id:idArray){
//            for(WasteEnterpriseType wasteEntType:wasteEnterpriseTypes){
//                if(id.equals(wasteEntType.getId())){
//                    checkFlag = true;
//                    break;
//                }
//            }
//        }
//
//        Assert.assertTrue(checkFlag);
        
    }
    
    @Test
    public void TestWasteEnterpriseTypeServiceB() throws DaoAccessException{
//        User user = LoginStatusUtils.getUserByTicketId(getTicketId());
//        List<WasteEnterpriseType> list = wasteEnterpriseTypeService.getWasteEnterpriseTypesByEnterpriseId(user.getEnterpriseId());
//
//        boolean checkFlag = false;
//
//        for(String id:idArray){
//            for(WasteEnterpriseType wasteEntType:list){
//                if(id.equals(wasteEntType.getId())){
//                    checkFlag = true;
//                    break;
//                }
//            }
//        }
//        Assert.assertTrue(checkFlag);
        
    }
    

    
    
    
    @Test
    public void TestWasteEnterpriseTypeServiceD() throws DaoAccessException{
        
        User user = LoginStatusUtils.getUserByTicketId(getTicketId());
        String enterpriseId = user.getEnterpriseId();
        List<CodeValue> enterpriseTypesByEntId = codeValueService.getEnterpriseTypesByEntId(enterpriseId);
        String code = enterpriseTypesByEntId.get(0).getCode();
        String value = enterpriseTypesByEntId.get(0).getValue();
        
        CodeValue codeValueByCode = codeValueService.getCodeValueByCode(CodeTypeConstant.ENTERPRISE_TYPE, CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION);
        Assert.assertEquals(code, codeValueByCode.getCode());
        Assert.assertEquals(value, codeValueByCode.getValue());
        
        boolean checkFlag = false;
        for(CodeValue codeValue:enterpriseTypesByEntId){
            if(codeValue.getCode().equals(codeValueByCode.getCode())&&codeValue.getValue().equals(codeValueByCode.getValue())){
                checkFlag = true;
                break;
            }
        }
        
        Assert.assertTrue(checkFlag);
    }
    

    
}
