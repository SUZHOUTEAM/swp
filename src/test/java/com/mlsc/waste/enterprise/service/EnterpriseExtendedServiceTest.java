package com.mlsc.waste.enterprise.service;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.dao.EnterpriseExtendedDao;
import com.mlsc.waste.enterprise.model.EnterpriseExtended;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.CodeTypeConstant;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING )
public class EnterpriseExtendedServiceTest {
    
    @Autowired
    private EnterpriseExtendedService enterpriseExtendedService;
    
    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private ICodeValueService codeValueService;
    
    @Autowired
    private EnterpriseExtendedDao enterpriseExtendedDao;

    @Autowired
    private EnterpriseService enterpriseServiceImpl;
    
    private static RPCSysEnterpriseBase enterpriseInfo;
    
    private String getTicketId() {
        String ticketId = null;
        try {
            ticketId = userLoginService.userLogin("18862190636", "1qazxsw2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketId;
    }
    @Autowired
    private IRPCServiceClient client;
    
    private RPCSysEnterpriseBase getEnterpiseInfo(){
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
        
        enterpriseInfo =  enterpriseServiceImpl.saveEnterpriseInfo(getTicketId(), enterpriseInfo);
        
        return enterpriseInfo;
        
        
    }
    
    @Test
    public void TestEnterpriseExtendedServiceA() throws DaoAccessException{
        CodeValue codeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS, CodeTypeConstant.USER_EVENT_STATUS_PASS);
        getEnterpiseInfo();
        EnterpriseExtended enterpriseExtended= enterpriseExtendedService.getEnterpriseExtendedByEnterpriseId(enterpriseInfo.getEntId());
        Assert.assertTrue(enterpriseExtended.getId()==null);
        
        EnterpriseExtended record = new EnterpriseExtended();
        record.setSysEnterpriseBaseId(enterpriseInfo.getEntId());
        record.setEnterpriseStatus(codeValue.getId());
        record.setEnterpriseIcon("test");
        record.setValid("1");
        enterpriseExtendedService.saveEnterpriseExtended(record,getTicketId());
        
        enterpriseExtended= enterpriseExtendedService.getEnterpriseExtendedByEnterpriseId(enterpriseInfo.getEntId());
        Assert.assertTrue(StringUtils.isNotBlank(enterpriseExtended.getId()));
        Assert.assertEquals(enterpriseInfo.getEntId(), enterpriseExtended.getSysEnterpriseBaseId());
    }
    
    
    @Test
    public void TestEnterpriseExtendedServiceB() throws DaoAccessException{
        
        CodeValue codeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS, CodeTypeConstant.USER_EVENT_STATUS_PASS);
        EnterpriseExtended record = enterpriseExtendedService.getEnterpriseExtendedByEnterpriseId(enterpriseInfo.getEntId());
        Assert.assertEquals(codeValue.getId(), record.getEnterpriseStatus());
        
    }
    
    
    
    
    @Test
    public void TestEnterpriseExtendedServiceC() throws DaoAccessException{
        CodeValue codeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS, CodeTypeConstant.USER_EVENT_STATUS_REFUSED);
        EnterpriseExtended record = enterpriseExtendedService.getEnterpriseExtendedByEnterpriseId(enterpriseInfo.getEntId());
        record.setEnterpriseStatus(codeValue.getId());
        
        enterpriseExtendedService.updateEnterpriseExtended(record,getTicketId());
        
        EnterpriseExtended entExtended = enterpriseExtendedDao.get(record.getId());
        Assert.assertEquals(codeValue.getId(),entExtended.getEnterpriseStatus());
        
    }

    
    @Test
    public void TestEnterpriseExtendedServiceZ() throws Exception{
        
        EnterpriseExtended record = enterpriseExtendedService.getEnterpriseExtendedByEnterpriseId(enterpriseInfo.getEntId());
        enterpriseExtendedDao.delete(record.getId());
        Iface service = client.getOrgComServiceManager();
        service.removeEnterprise("123123123", enterpriseInfo.getEntId());
        
    }
}
