/**
 * 
 */
package com.mlsc.waste.licence.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fw.service.SysOrgComService;
import com.mlsc.waste.licence.dao.LicenceDetailDao;
import com.mlsc.waste.licence.model.OperationLicenceDetail;
import com.mlsc.waste.licence.model.OperationLicenceDetailExtend;
import com.mlsc.waste.licence.service.LicenceDetailService;
import com.mlsc.waste.licence.service.LicenceItemService;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.util.FaceMocker;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.wastedirectory.model.WasteName;
import com.mlsc.waste.wastedirectory.service.WasteNameService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class LicenceServiceImplNewTest {
    
    private Iface faceMock;
    
    @Mock
    private PlatformSupporter platformSuppoter;

    @InjectMocks
    private LicenceService licenceService = new LicenceServiceImpl();
   
    @Mock
    private LicenceDetailService licenceDetailService;//操作许可证datail表
    
    @Mock
    private LicenceItemService licenceItemService ;//操作许可证item表

    @Mock
    private WasteNameService wasteNameService;//操做危废waste表
    
    @Mock
    public ICodeValueService codeValueService;//操作数据字典，根据处置方式ID查询处置方式

    @Mock
    private EnterpriseService enterpriseService;
    
    @Mock
    private SysOrgComService sysOrgComService;
    
    @Mock
    private LicenceDetailDao licenceDetailDao;
    
    @Before  
    public void setUp() {
        faceMock = new FaceMocker();
        MockitoAnnotations.initMocks(this);
    } 
    
    private RPCSysEnterpriseBase getEnterpriseInfoById() {
        RPCSysEnterpriseBase base = new RPCSysEnterpriseBase();
        base.setCantonCode("340823");//枞阳县
        return base;
    }
    
    private List<RPCSysOrgCom> querySysOrgComListByCantonID(String cantonCode) {
        RPCSysOrgCom base = new RPCSysOrgCom();
        if ("34".equals(cantonCode)) {
            base.setComID("1111111");
            base.setComName("安徽省环保局");
            base.setComCode("34");
            base.setCantonCode("34");
            base.setComType(Constant.COM_TYPE_EPA);
        } else if ("3408".equals(cantonCode)) {
            base.setComID("1111112");
            base.setComName("安庆市环保局");
            base.setComCode("3408");
            base.setCantonCode("3408");
            base.setComType(Constant.COM_TYPE_EPA);
        } else if ("340823".equals(cantonCode)) {
            base.setComID("1111113");
            base.setComName("枞阳县环保局");
            base.setComCode("340823");
            base.setCantonCode("340823");
            base.setComType(Constant.COM_TYPE_EPA);
        }
        List<RPCSysOrgCom> returnComs = new ArrayList<RPCSysOrgCom>();
        returnComs.add(base);
        return returnComs;
    }
    
    private List<RPCSysOrgCom> querySysOrgComListByParentCantonID(String cantonCode) {
        RPCSysOrgCom base = null;
        List<RPCSysOrgCom> returnComs = new ArrayList<RPCSysOrgCom>();
        if ("3408".equals(cantonCode)) {
            base = new RPCSysOrgCom();
            base.setComID("1111113");
            base.setComName("枞阳县环保局");
            base.setComCode("340823");
            base.setCantonCode("340823");
            base.setComType(Constant.COM_TYPE_EPA);
            returnComs.add(base);
            
            base = new RPCSysOrgCom();
            base.setComID("1111114");
            base.setComName("枞阳县某某产废企业1");
            base.setComCode("340823-1");
            base.setCantonCode("340823");
            base.setComType(1);
            returnComs.add(base);
            
            base = new RPCSysOrgCom();
            base.setComID("1111115");
            base.setComName("枞阳县某某产废企业2");
            base.setComCode("340823-2");
            base.setCantonCode("340823");
            base.setComType(1);
            returnComs.add(base);
            
            base = new RPCSysOrgCom();
            base.setComID("1111116");
            base.setComName("桐城县环保局");
            base.setComCode("340824");
            base.setCantonCode("340824");
            base.setComType(Constant.COM_TYPE_EPA);
            returnComs.add(base);
            
            base = new RPCSysOrgCom();
            base.setComID("1111117");
            base.setComName("桐城县某某产废企业1");
            base.setComCode("340824-1");
            base.setCantonCode("340824");
            base.setComType(1);
            returnComs.add(base);
            
            base = new RPCSysOrgCom();
            base.setComID("1111118");
            base.setComName("桐城县某某产废企业2");
            base.setComCode("340824-2");
            base.setCantonCode("340824");
            base.setComType(1);
            returnComs.add(base);
            
            base = new RPCSysOrgCom();
            base.setComID("1111118");
            base.setComName("桐城县某某产废企业2");
            base.setComCode("340824-2");
            base.setCantonCode("340824");
            base.setComType(1);
            returnComs.add(base);
        }
        
        return returnComs;
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getOrgByEnterpriseId(java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    // @Test
    public void testGetOrgByEnterpriseId() throws Exception {
        // MockitoAnnotations.initMocks(this);
        
       //  String ticketId = userLoginService.userLogin("15301540690", "test123");
        String ticketId = "005a238f6fee4bff892173f5f5a0a262";
        String entId = "005a238f6fee4bff892173f5f5a0a263";
        // User userInfoUser = LoginStatusUtils.getUserByTicketId(ticketId);
        Mockito.when(platformSuppoter.getOrgComServiceManager()).thenReturn(faceMock);
        Mockito.when(enterpriseService.getEnterpriseInfoById(ticketId, entId)).thenReturn(getEnterpriseInfoById());
        
        Mockito.when(sysOrgComService.querySysOrgComListByCantonID(ticketId, Constant.COM_TYPE_EPA, "34")).thenReturn(querySysOrgComListByCantonID("34"));
        Mockito.when(sysOrgComService.querySysOrgComListByCantonID(ticketId, Constant.COM_TYPE_EPA, "3408")).thenReturn(querySysOrgComListByCantonID("3408"));
        
        Mockito.when(sysOrgComService.querySysOrgComListByParentCantonID(ticketId, Constant.COM_TYPE_EPA, "3408")).thenReturn(querySysOrgComListByParentCantonID("3408"));
        
        List<RPCSysOrgCom> rpcSysOrgComs = licenceService.getOrgByEnterpriseId(ticketId, entId);
        
        assertTrue(rpcSysOrgComs.size() == 4);
        for (RPCSysOrgCom orgCom:rpcSysOrgComs) {
            assertTrue("枞阳县环保局".equals(orgCom.getComName()) || "桐城县环保局".equals(orgCom.getComName()) || "安庆市环保局".equals(orgCom.getComName()) || "安徽省环保局".equals(orgCom.getComName()));
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#isHasDispositionItem(java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testIsHasDispositionItem() throws Exception {
        Mockito.when(licenceItemService.isHasDispositionItem("11111111", "15633333")).thenReturn("1568966");
        Mockito.when(licenceItemService.isHasDispositionItem("11111111", "AAAAAA")).thenReturn("");
        assertTrue("1568966".equals(licenceService.isHasDispositionItem("11111111", "15633333")));
        assertTrue(StringUtils.isBlank(licenceService.isHasDispositionItem("11111111", "AAAAAA")));
    }
    
    
    private List<WasteName> getWasteNamesByNameAndWasteId() {
        WasteName base = null;
        List<WasteName> returnComs = new ArrayList<WasteName>();
        base = new WasteName();
        base.setId("1111113");
        base.setName("危废名称3");
        returnComs.add(base);
        
        base = new WasteName();
        base.setId("1111114");
        base.setName("危废名称4");
        returnComs.add(base);
        
        base = new WasteName();
        base.setId("1111115");
        base.setName("危废名称5");
        returnComs.add(base);
        
        base = new WasteName();
        base.setId("1111116");
        base.setName("危废名称6");
        returnComs.add(base);
        
        base = new WasteName();
        base.setId("1111117");
        base.setName("危废名称7");
        returnComs.add(base);
        
        base = new WasteName();
        base.setId("1111118");
        base.setName("危废名称8");
        returnComs.add(base);
        
        base = new WasteName();
        base.setId("1111119");
        base.setName("危废名称9");
        returnComs.add(base);
        
        return returnComs;
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getWasteNamesByWasteId(java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetWasteNamesByWasteId() throws Exception {
        Mockito.when(wasteNameService.getWasteNamesByNameAndWasteid("1111111", "处置", null)).thenReturn(getWasteNamesByNameAndWasteId());
        Map<String, Object> jsonMap = licenceService.getWasteNamesByWasteId("1111111", "处置");
        
        JSONArray jsonArray = (JSONArray) jsonMap.get("value");
        assertTrue(jsonArray != null && jsonArray.size() == 7);
        
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            if ("1111113".equals(jsonObject.get("wasteNameId").toString())) {
                assertTrue("危废名称3".equals(jsonObject.get("wasteName").toString()));
            } else if ("1111114".equals(jsonObject.get("wasteNameId").toString())) {
                assertTrue("危废名称4".equals(jsonObject.get("wasteName").toString()));
            } else if ("1111115".equals(jsonObject.get("wasteNameId").toString())) {
                assertTrue("危废名称5".equals(jsonObject.get("wasteName").toString()));
            } else if ("1111116".equals(jsonObject.get("wasteNameId").toString())) {
                assertTrue("危废名称6".equals(jsonObject.get("wasteName").toString()));
            } else if ("1111117".equals(jsonObject.get("wasteNameId").toString())) {
                assertTrue("危废名称7".equals(jsonObject.get("wasteName").toString()));
            } else if ("1111118".equals(jsonObject.get("wasteNameId").toString())) {
                assertTrue("危废名称8".equals(jsonObject.get("wasteName").toString()));
            } else if ("1111119".equals(jsonObject.get("wasteNameId").toString())) {
                assertTrue("危废名称9".equals(jsonObject.get("wasteName").toString()));
            }
        }
    }
    
    private List<OperationLicenceDetailExtend> doLicenceDetailDataReady() throws Exception {
        List<OperationLicenceDetailExtend> returnResult = new ArrayList<OperationLicenceDetailExtend>();
        OperationLicenceDetailExtend detailExtend = null;
        String licenceId = "AAAAAAAA1111";
        String licenceItemId = "BBBBBBBBBB1111";
        for (int i=0;i<10;i++) {
            if (i == 0) {
                detailExtend = new OperationLicenceDetailExtend();
                detailExtend.setLicence_id(licenceId);
                detailExtend.setOperation_item_id(licenceItemId);
                detailExtend.setWaste_type("CCCCCCCCCC1111");
                detailExtend.setWaste_id("DDDDDDDDDD1111");
                detailExtend.setWaste_name_id("");
                detailExtend.setWaste_name("测试危废1");
            }else if (i == 1) {
                detailExtend = new OperationLicenceDetailExtend();
                detailExtend.setLicence_id(licenceId);
                detailExtend.setOperation_item_id(licenceItemId);
                detailExtend.setWaste_type("CCCCCCCCCC1111");
                detailExtend.setWaste_id("DDDDDDDDDD1111");
                detailExtend.setWaste_name_id("");
                detailExtend.setWaste_name("");
            }else if (i == 2) {
                detailExtend = new OperationLicenceDetailExtend();
                detailExtend.setLicence_id(licenceId);
                detailExtend.setOperation_item_id(licenceItemId);
                detailExtend.setWaste_type("CCCCCCCCCC1111");
                detailExtend.setWaste_id("DDDDDDDDDD1111");
                detailExtend.setWaste_name_id("EEEEEEEEEEE1112");
                detailExtend.setWaste_name("测试危废3");
            } else {
                detailExtend = new OperationLicenceDetailExtend();
                detailExtend.setLicence_id(licenceId);
                detailExtend.setOperation_item_id(licenceItemId);
                detailExtend.setWaste_type("CCCCCCCCCC1111");
                detailExtend.setWaste_id("DDDDDDDDDD1111");
                detailExtend.setWaste_name_id("EEEEEEEEEEE111" + i);
                detailExtend.setWaste_name("测试危废" + i);
            }
            returnResult.add(detailExtend);
        }
        
        detailExtend = new OperationLicenceDetailExtend();
        detailExtend.setLicence_id(licenceId);
        detailExtend.setOperation_item_id(licenceItemId);
        detailExtend.setWaste_type("CCCCCCCCCC1111");
        detailExtend.setWaste_id("DDDDDDDDDD1111");
        detailExtend.setWaste_name_id("EEEEEEEEEEE1119");
        detailExtend.setWaste_name("测试危废9");
        
        returnResult.add(detailExtend);
        return returnResult;
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#saveDispositionItemAndDetail(java.util.List, java.lang.String)}.
     * @throws Exception 
     */
    // @Test
    public void testSaveDispositionItemAndDetail() throws Exception {
       //String ticketId = userLoginService.userLogin("15301540690", "test123");
        String ticketId = "005a238f6fee4bff892173f5f5a0a262";
        
        List<OperationLicenceDetailExtend> detailExtendResult = null;
        licenceService.saveDispositionItemAndDetail(detailExtendResult, ticketId);
        licenceService.saveDispositionItemAndDetail(new ArrayList<OperationLicenceDetailExtend>(), ticketId);
        detailExtendResult = doLicenceDetailDataReady();
        
        Mockito.when(wasteNameService.saveOrUpdateWasteName("DDDDDDDDDD1111", "测试危废1", ticketId)).thenReturn("EEEEEEEEEEE1110");
        Mockito.when(licenceDetailService.isHasLicenceDetail("AAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111", "EEEEEEEEEEE1110")).thenReturn(false);
        Mockito.when(licenceDetailService.isHasLicenceDetail("AAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111", "EEEEEEEEEEE1111")).thenReturn(false);
        Mockito.when(licenceDetailService.isHasLicenceDetail("AAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111", "EEEEEEEEEEE1112")).thenReturn(false);
        Mockito.when(licenceDetailService.isHasLicenceDetail("AAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111", "EEEEEEEEEEE1113")).thenReturn(false);
        Mockito.when(licenceDetailService.isHasLicenceDetail("AAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111", "EEEEEEEEEEE1114")).thenReturn(false);
        Mockito.when(licenceDetailService.isHasLicenceDetail("AAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111", "EEEEEEEEEEE1115")).thenReturn(true);
        Mockito.when(licenceDetailService.isHasLicenceDetail("AAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111", "EEEEEEEEEEE1116")).thenReturn(true);
        Mockito.when(licenceDetailService.isHasLicenceDetail("AAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111", "EEEEEEEEEEE1117")).thenReturn(true);
        Mockito.when(licenceDetailService.isHasLicenceDetail("AAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111", "EEEEEEEEEEE1118")).thenReturn(false);
        Mockito.when(licenceDetailService.isHasLicenceDetail("AAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111", "EEEEEEEEEEE1119")).thenReturn(false);
        
        licenceService.saveDispositionItemAndDetail(detailExtendResult, ticketId);
        
        OperationLicenceDetail query = new OperationLicenceDetail();
        query.setLicence_id("AAAAAAAA1111");
        query.setOperation_item_id("BBBBBBBBBB1111");
        query.setWaste_type("CCCCCCCCCC1111");
        query.setWaste_id("DDDDDDDDDD1111");
        List<OperationLicenceDetail> details = licenceDetailDao.query(query);
        
        assertTrue(details.size() == 7);
        
        for (int i=0;i<10;i++) {
            query = details.get(i);
            if (i == 5) {
                assertTrue(("EEEEEEEEEEE1118" + i).equals(query.getWaste_name_id()));
            }else if (i == 6) {
                assertTrue(("EEEEEEEEEEE1119" + i).equals(query.getWaste_name_id()));
            }else {
                assertTrue(("EEEEEEEEEEE111" + i).equals(query.getWaste_name_id()));
            }
            
            licenceDetailDao.delete(query.getId());
        }
    }
    
    
    
}
