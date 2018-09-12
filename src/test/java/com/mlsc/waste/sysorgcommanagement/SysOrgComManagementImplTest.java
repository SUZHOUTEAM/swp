package com.mlsc.waste.sysorgcommanagement;

import com.mlsc.common.util.RSAEncryptUtils;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.fw.dao.SysOrgComDao;
import com.mlsc.waste.fw.model.SysOrgCom;
import com.mlsc.waste.fw.service.SysOrgComManagementService;
import com.mlsc.waste.fw.service.SysOrgComService;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class SysOrgComManagementImplTest {
    
    @Autowired
    private SysOrgComManagementService sysOrgComManagementService;
    
    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private SysOrgComDao sysOrgComDao;
    
    @Autowired
    private SysOrgComService sysOrgComService;
    
    @Test
    public void getOrgComCountTest(){
        
        assertTrue(true);
        String sql = "";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        int orgComCount = sysOrgComManagementService.getOrgComCount(sql, paramMap);
        
        List<SysOrgCom> orgComList = sysOrgComManagementService.getOrgComList(sql, paramMap, new PagingParameter());
        int orgComListSize = orgComList.size();
        
        assertTrue(orgComCount == orgComListSize);
    }
    
    @Test
    public void getOrgComNameTest() throws Exception{
        SysOrgCom orgCom = new SysOrgCom();
        orgCom.setComName("安徽绩溪测试产废公司");
        orgCom.setComCode("JX000001");
        orgCom.setComStatus(0);
        orgCom.setComDesc("");
        orgCom.setComType(1);
        orgCom.setComFunc("");
        orgCom.setCoordinateId("");
        orgCom.setCreaterId("system");
        orgCom.setCreateTime(Util.datetimeToString(new Date()));
        orgCom.setUpdaterId("system");
        orgCom.setUpdateTime(Util.datetimeToString(new Date()));
        
        String cantonCode = "215123";
        String password = RSAEncryptUtils.encrypt("test123");
        String ticketId = userLoginService.userLogin("18862190636", password);
        String saveOrgComAndCantonRelation = sysOrgComManagementService.saveOrgComAndCantonRelation(orgCom,cantonCode,ticketId);
        assertTrue(saveOrgComAndCantonRelation.equals("0"));
        String orgComName = sysOrgComManagementService.getOrgComName("","JX000001");
        assertTrue(orgComName.equals("安徽绩溪测试产废公司"));
        
    }
    
    @Test
    public void saveOrgComAndCantonRelationTest() throws Exception{
        try {
            SysOrgCom orgCom = new SysOrgCom();
            orgCom.setComName("安徽绩溪测试产废公司");
            orgCom.setComCode("JX000001");
            orgCom.setComStatus(0);
            orgCom.setComDesc("");
            orgCom.setComType(1);
            orgCom.setComFunc("");
            orgCom.setCoordinateId("");
            orgCom.setCreaterId("system");
            orgCom.setCreateTime(Util.datetimeToString(new Date()));
            orgCom.setUpdaterId("system");
            orgCom.setUpdateTime(Util.datetimeToString(new Date()));
            
            String cantonCode = "215123";
            String password = RSAEncryptUtils.encrypt("test123");
            String ticketId = userLoginService.userLogin("18862190636", password);
            String saveOrgComAndCantonRelation = sysOrgComManagementService.saveOrgComAndCantonRelation(orgCom,cantonCode,ticketId);
            assertTrue(saveOrgComAndCantonRelation.equals("0"));
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void getCantonValueTest() throws Exception{
        String cantoncCode = "320922";
        String password = RSAEncryptUtils.encrypt("test123");
        String ticketId = userLoginService.userLogin("18862190636", password);
        
        Map<String, String> cantonValue = sysOrgComManagementService.getCantonValue(cantoncCode,ticketId);
        assertTrue(cantonValue.get("province").equals("江苏省"));
        assertTrue(cantonValue.get("city").equals("盐城市"));
        assertTrue(cantonValue.get("district").equals("滨海县"));
    }
    
    @Test
    public void testSaveOrgComAndCantonRelation() throws DaoAccessException{
        
        String orgComName = "安徽绩溪测试产废公司";
        String orgComCode = "JX000001";
        String saveOrgComAndCantonRelation = sysOrgComManagementService.saveOrgComAndCantonRelation(orgComName, orgComCode);
        SysOrgCom orgCom = sysOrgComService.getOrgCom(saveOrgComAndCantonRelation);
        assertTrue(orgCom.getComName().equals(orgComName));
        assertTrue(orgCom.getComCode().equals(orgComCode));
        sysOrgComDao.delete(saveOrgComAndCantonRelation);
    }
}
