package com.mlsc.waste.enterprise.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprisemanagement.service.EnterpriseManagementService;
import com.mlsc.waste.utils.CodeTypeConstant;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING )
public class EnterpriseManagementServiceTest {
    
    @Autowired
    private EnterpriseManagementService enterpriseManagementService;
    
    @Test
    public void TestEnterpriseManagementServiceA(){
        
        String sqlWhere = "";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        int allEnterpriseBaseCount = enterpriseManagementService.getAllEnterpriseBaseCount(sqlWhere,paramMap);
        Assert.assertTrue(allEnterpriseBaseCount > 0);
    }
    
    @Test
    public void TestEnterpriseManagementServiceB(){
        String sql = "";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        int allEnterpriseBaseCount = enterpriseManagementService.getAllEnterpriseBaseCount(sql,paramMap);
        List<RPCSysEnterpriseBaseVo> allEnterpriseBaseList = enterpriseManagementService.getAllEnterpriseBaseList(sql,paramMap,new PagingParameter());
        Assert.assertEquals(allEnterpriseBaseCount, allEnterpriseBaseList.size());
        
        RPCSysEnterpriseBaseVo baseVo =  allEnterpriseBaseList.get(0);
        sql = " and entName like :entName ";
        paramMap.put("entName",  baseVo.getEntName());
        
        allEnterpriseBaseCount = enterpriseManagementService.getAllEnterpriseBaseCount(sql,paramMap);
        allEnterpriseBaseList = enterpriseManagementService.getAllEnterpriseBaseList(sql,paramMap,new PagingParameter());
        Assert.assertEquals(allEnterpriseBaseCount, allEnterpriseBaseList.size());
    }
    
    
    
    @Test
    public void TestEnterpriseManagementServiceZ() throws DaoAccessException{
        String sql = "";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<RPCSysEnterpriseBaseVo> allEnterpriseBaseList = enterpriseManagementService.getAllEnterpriseBaseList(sql,paramMap,new PagingParameter());
        RPCSysEnterpriseBaseVo baseVo =  allEnterpriseBaseList.get(0);
        String EnterpriseStatus = baseVo.getEnterpriseStatus();
        
        String[] enterpriseIds = new String[]{baseVo.getEntId()};
//        enterpriseManagementService.updateRecords(enterpriseIds,CodeTypeConstant.USER_EVENT_STATUS_SUBMIT);
        boolean checkFlag = false;
        for(RPCSysEnterpriseBaseVo baseVoTemp:allEnterpriseBaseList){
            if(baseVoTemp.getEnterpriseStatus().equals(CodeTypeConstant.USER_EVENT_STATUS_SUBMIT)){
                checkFlag = true;
                break;
            }
        }
        
        Assert.assertTrue(checkFlag);
        
//        enterpriseManagementService.updateRecords(enterpriseIds, CodeTypeConstant.USER_EVENT_STATUS_REFUSED);
        checkFlag = false;
      
        
       
        
        allEnterpriseBaseList = enterpriseManagementService.getAllEnterpriseBaseList(sql,paramMap,new PagingParameter());
        for(RPCSysEnterpriseBaseVo baseVoTemp:allEnterpriseBaseList){
            if(baseVoTemp.getEnterpriseStatus().equals(CodeTypeConstant.USER_EVENT_STATUS_REFUSED)){
                checkFlag = true;
                break;
            }
        }
        Assert.assertTrue(checkFlag);
        
        
//        enterpriseManagementService.updateRecords(enterpriseIds,EnterpriseStatus);
        
    }
}
