/**
 * 
 */
package com.mlsc.waste.licence.service.impl;

import com.mlsc.common.util.RSAEncryptUtils;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.fw.service.SysOrgComService;
import com.mlsc.waste.licence.dao.LicenceDao;
import com.mlsc.waste.licence.dao.LicenceDetailDao;
import com.mlsc.waste.licence.dao.LicenceItemDao;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceDetail;
import com.mlsc.waste.licence.model.OperationLicenceItem;
import com.mlsc.waste.licence.service.LicenceDetailService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteName;
import com.mlsc.waste.wastedirectory.model.WasteType;
import com.mlsc.waste.wastedirectory.service.WasteNameService;
import com.mlsc.waste.wastedirectory.service.WasteService;
import com.mlsc.waste.wastedirectory.service.WasteTypeService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class LicenceDetailServiceImplTest {
    @Autowired
    private IRPCServiceClient client;
    
    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private LicenceDetailService licenceDetailService;
    
    @Autowired
    private ICodeValueService codeValueService;
    
    @Autowired
    private SysOrgComService sysOrgComService;
    
    @Autowired
    private WasteTypeService wasteTypeService;
    
    @Autowired
    private WasteService wasteService;
    
    @Autowired
    private WasteNameService wasteNameService;
    
    @Autowired
    private LicenceDao licenceDao;
    
    @Autowired
    private LicenceItemDao licenceItemDao;
    
    @Autowired
    private LicenceDetailDao licenceDetailDao;
    
    
    private OperationLicence doLicenceDataReady(String ticketId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        CodeValue auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE);
        CodeValue licenceStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_VALID, CodeTypeConstant.LIC_VALID_INVALID);
        CodeValue operation_mode = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_MODE, CodeTypeConstant.LIC_MODE_COLLECTION);
        List<RPCSysOrgCom> licence_orglistComs = sysOrgComService.querySysOrgComListByCantonID(ticketId, 1, "340823");
        
        RPCSysEnterpriseBase enterpriseBase = client.getOrgComServiceManager().queryEnterprise(ticketId, user.getEnterpriseId());
        
        OperationLicence licence = new OperationLicence();
        licence.setEnterprise_id(user.getEnterpriseId());
        licence.setEnterprise_name(enterpriseBase.getEntName());
        licence.setLicence_no("jtest-1000001");
        licence.setLicence_org(licence_orglistComs.get(0).getComID());
        licence.setLicence_date(Util.datetimeToString(new Date()));
        licence.setInitiallic_date(null);
        licence.setCorporate(enterpriseBase.getLegalName());
        licence.setRegister_addr(enterpriseBase.getEntAddress());
        licence.setMachine_addr(enterpriseBase.getEntAddress());
        licence.setStart_date("2017-01-01 00:00:00");
        licence.setEnd_date("2017-01-01 59:59:59");
        licence.setLicence_status(licenceStatus.getId());
        licence.setOperation_mode(operation_mode.getId());
        licence.setApplication_time(null);
        licence.setAudit_status(auditStatus.getId());
        licence.setApproved_by(null);
        licence.setCreate_by(user.getUserId());
        licence.setCreate_time(Util.datetimeToString(new Date()));
        licence.setEdit_by(user.getUserId());
        licence.setEdit_time(Util.datetimeToString(new Date()));
        licence.setValid(Constant.IS_VALID);
        return licence;
    }
    
    
    private List<OperationLicenceItem> doLicenceItemDataReady(String licenceId,String ticketId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        CodeValue disposition_type_c1 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C1);
        CodeValue disposition_type_c2 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C2);
        
        List<OperationLicenceItem> returnResult = new ArrayList<OperationLicenceItem>();
        OperationLicenceItem licenceItem1 = new OperationLicenceItem();
        licenceItem1.setLicence_id(licenceId);
        licenceItem1.setDisposition_type(disposition_type_c1.getId());
        licenceItem1.setApproved_quantity("12000");
        licenceItem1.setExcuted_quantity("0");
        licenceItem1.setRemark(null);
        licenceItem1.setCreate_by(user.getUserId());
        licenceItem1.setCreate_time(Util.datetimeToString(new Date()));
        licenceItem1.setEdit_by(user.getUserId());
        licenceItem1.setEdit_time(Util.datetimeToString(new Date()));
        licenceItem1.setValid(Constant.IS_VALID);
        returnResult.add(licenceItem1);
        OperationLicenceItem licenceItem2 = new OperationLicenceItem();
        licenceItem2.setLicence_id(licenceId);
        licenceItem2.setDisposition_type(disposition_type_c2.getId());
        licenceItem2.setApproved_quantity("15000");
        licenceItem2.setExcuted_quantity("0");
        licenceItem2.setRemark(null);
        licenceItem2.setCreate_by(user.getUserId());
        licenceItem2.setCreate_time(Util.datetimeToString(new Date()));
        licenceItem2.setEdit_by(user.getUserId());
        licenceItem2.setEdit_time(Util.datetimeToString(new Date()));
        licenceItem2.setValid(Constant.IS_VALID);
        returnResult.add(licenceItem2);
        return returnResult;
    }
    
    private List<OperationLicenceDetail> doLicenceDetailDataReady(String licenceId,List<OperationLicenceItem> licenceItems,String ticketId) throws Exception {
        CodeValue disposition_type_c1 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C1);
        CodeValue disposition_type_c2 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C2);
        List<OperationLicenceDetail> returnResult = new ArrayList<OperationLicenceDetail>();
        OperationLicenceDetail licenceDetail = null;
        for (OperationLicenceItem item : licenceItems) {
            if (disposition_type_c1.getId().equals(item.getDisposition_type())) {
                WasteType wt_hw01 = wasteTypeService.getWasteTypeByWasteCode("HW01");
                List<Waste> wt_hw01_wasteList = wasteService.getWastesByWasteTypeId(wt_hw01.getId());
                for (Waste waste : wt_hw01_wasteList) {
                    licenceDetail = new OperationLicenceDetail();
                    licenceDetail.setLicence_id(licenceId);
                    licenceDetail.setOperation_item_id(item.getId());
                    licenceDetail.setWaste_type(waste.getWaste_type_id());
                    licenceDetail.setWaste_id(waste.getId());
                    List<WasteName> wasteNames = wasteNameService.getWasteNamesByWasteId(waste.getId());
                    if (wasteNames == null || wasteNames.size() == 0) {
                        licenceDetail.setWaste_name_id(null);
                    } else {
                        licenceDetail.setWaste_name_id(wasteNames.get(0).getId());
                    }
                    
                    returnResult.add(licenceDetail);
                }
            } else if (disposition_type_c2.getId().equals(item.getDisposition_type())) {
                WasteType wt_hw07 = wasteTypeService.getWasteTypeByWasteCode("HW07");
                List<Waste> wt_hw07_wasteList = wasteService.getWastesByWasteTypeId(wt_hw07.getId());
                for (Waste waste : wt_hw07_wasteList) {
                    licenceDetail = new OperationLicenceDetail();
                    licenceDetail.setLicence_id(licenceId);
                    licenceDetail.setOperation_item_id(item.getId());
                    licenceDetail.setWaste_type(waste.getWaste_type_id());
                    licenceDetail.setWaste_id(waste.getId());
                    List<WasteName> wasteNames = wasteNameService.getWasteNamesByWasteId(waste.getId());
                    if (wasteNames == null || wasteNames.size() == 0) {
                        licenceDetail.setWaste_name_id(null);
                    } else {
                        licenceDetail.setWaste_name_id(wasteNames.get(0).getId());
                    }
                    
                    returnResult.add(licenceDetail);
                }
            }
        }
        return returnResult;
    }
    private OperationLicenceDetail doLicenceDetailDataReady() throws Exception {
        OperationLicenceDetail testDetail = new OperationLicenceDetail();
        testDetail.setLicence_id("AAAAAAAAAA1111");
        testDetail.setOperation_item_id("BBBBBBBBBB1111");
        testDetail.setWaste_type("CCCCCCCCCC1111");
        testDetail.setWaste_id("DDDDDDDDDD1111");
        testDetail.setWaste_name_id("EEEEEEEEEE1111");
        testDetail.setCreate_by("2000000000");
        testDetail.setCreate_time(Util.datetimeToString(new Date()));
        testDetail.setEdit_by("2000000000");
        testDetail.setEdit_time(Util.datetimeToString(new Date()));
        testDetail.setValid(Constant.IS_VALID);
        
        return testDetail;
    }
    
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceDetailServiceImpl#removeDetails(java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testRemoveDetails() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        OperationLicence licence = doLicenceDataReady(ticketId);
        String licenceId = Util.uuid32();
        licenceDao.save(licence, licenceId);
        
        List<OperationLicenceItem> licenceItems = doLicenceItemDataReady(licenceId, ticketId);
        for (OperationLicenceItem item : licenceItems) {
            licenceItemDao.save(item, Util.uuid32());
        }
        
        List<OperationLicenceDetail> licenceDetails = doLicenceDetailDataReady(licenceId, licenceItems, ticketId);
        assertTrue(licenceDetails.size() == 12);
        licenceDetailService.saveLicenceDetail(licenceDetails, ticketId);
        
        //-------------------------保存结果验证-----------------------------------------
        List<OperationLicenceDetail> saveResult = new ArrayList<OperationLicenceDetail>();
        for (OperationLicenceItem item : licenceItems) {
            saveResult.addAll(licenceDetailService.getlicenceDetails(licenceId, item.getId()));
        }
        assertTrue(saveResult.size() == 12);
        
      //-------------------------删除操作-----------------------------------------
        String removeItemId = null;
        CodeValue disposition_type_c1 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C1);
        CodeValue disposition_type_c2 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C2);
        for (OperationLicenceItem item : licenceItems) {
            if (disposition_type_c1.getId().equals(item.getId())) {
                removeItemId = item.getId();
            }
        }
        licenceDetailService.removeDetails(licenceId, removeItemId);
        
        for (OperationLicenceItem item : licenceItems) {
            if (disposition_type_c1.getId().equals(item.getId())) {
                List<OperationLicenceDetail> c1resultDetails = licenceDetailService.getlicenceDetails(licenceId, item.getId());
                assertTrue(c1resultDetails == null || c1resultDetails.size() == 0);
            }
            if (disposition_type_c2.getId().equals(item.getId())) {
                List<OperationLicenceDetail> c2resultDetails = licenceDetailService.getlicenceDetails(licenceId, item.getId());
                assertTrue(c2resultDetails.size() == 6);
            }
        }
        
        licenceDao.delete(licenceId);
        for (OperationLicenceItem item : licenceItems) {
            licenceItemDao.delete(item.getId());
        }
        
        for (OperationLicenceDetail detail : saveResult) {
            licenceDetailDao.delete(detail.getId());
        }
        licenceDetails = new ArrayList<OperationLicenceDetail>();
        licenceDetailService.saveLicenceDetail(licenceDetails, ticketId);
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceDetailServiceImpl#removeDetail(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testRemoveDetail() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        OperationLicence licence = doLicenceDataReady(ticketId);
        String licenceId = Util.uuid32();
        licenceDao.save(licence, licenceId);
        
        List<OperationLicenceItem> licenceItems = doLicenceItemDataReady(licenceId, ticketId);
        for (OperationLicenceItem item : licenceItems) {
            licenceItemDao.save(item, Util.uuid32());
        }
        
        List<OperationLicenceDetail> licenceDetails = doLicenceDetailDataReady(licenceId, licenceItems, ticketId);
        assertTrue(licenceDetails.size() == 12);
        licenceDetailService.saveLicenceDetail(licenceDetails, ticketId);
        
        List<OperationLicenceDetail> saveResult = new ArrayList<OperationLicenceDetail>();
        for (OperationLicenceItem item : licenceItems) {
            saveResult.addAll(licenceDetailService.getlicenceDetails(licenceId, item.getId()));
        }
        assertTrue(saveResult.size() == 12);
        licenceDetailService.removeDetail(saveResult.get(0).getId());
        
        saveResult = new ArrayList<OperationLicenceDetail>();
        for (OperationLicenceItem item : licenceItems) {
            saveResult.addAll(licenceDetailService.getlicenceDetails(licenceId, item.getId()));
        }
        assertTrue(saveResult.size() == 11);
        
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licenceId);
        for (OperationLicenceItem item : licenceItems) {
            licenceItemDao.delete(item.getId());
        }
        
        for (OperationLicenceDetail detail : saveResult) {
            licenceDetailDao.delete(detail.getId());
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceDetailServiceImpl#updateDetailWasteNameId(java.lang.String, java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testUpdateDetailWasteNameId() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        OperationLicenceDetail testDetail = doLicenceDetailDataReady();
        String detailId = Util.uuid32();
        licenceDetailDao.save(testDetail, detailId);
        
        OperationLicenceDetail saveDetail = licenceDetailDao.get(detailId);
        assertTrue(saveDetail != null);
        assertTrue("EEEEEEEEEE1111".equals(saveDetail.getWaste_name_id()));
        
        licenceDetailService.updateDetailWasteNameId(detailId, "EEEEEEEEEE2222", ticketId);
        OperationLicenceDetail updateDetail = licenceDetailDao.get(detailId);
        assertTrue(updateDetail != null);
        assertTrue("EEEEEEEEEE2222".equals(updateDetail.getWaste_name_id()));
      //-------------------------删除操作-----------------------------------------
        licenceDetailDao.delete(detailId);
    }
    
    /**
     * @throws Exception
     */
    @Test
    public void testIsInlicence() throws Exception {
        String password = RSAEncryptUtils.encrypt("test123");
        String ticketId = userLoginService.userLogin("18120046886", password);
        OperationLicence licence = doLicenceDataReady(ticketId);
        String licenceId = Util.uuid32();
        licenceDao.save(licence, licenceId);
        
        List<OperationLicenceItem> licenceItems = doLicenceItemDataReady(licenceId, ticketId);
        for (OperationLicenceItem item : licenceItems) {
            item.setExcuted_quantity("1000");
            licenceItemDao.save(item, Util.uuid32());
        }
        try{
            List<OperationLicenceDetail> licenceDetails = doLicenceDetailDataReady(licenceId, licenceItems, ticketId);
            assertTrue(licenceDetails.size() == 12);
            licenceDetailService.saveLicenceDetail(licenceDetails, ticketId);
            
            CodeValue disposition_type_c1 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C1);
            WasteType wt_hw01 = wasteTypeService.getWasteTypeByWasteCode("HW01");
            List<Waste> wt_hw01_wasteList = wasteService.getWastesByWasteTypeId(wt_hw01.getId());
            assertTrue(Constant.IS_YSE.equals(String.valueOf(licenceDetailService.checkLicenceStatus(licenceId,disposition_type_c1.getId(), "11000", wt_hw01.getId(), wt_hw01_wasteList.get(0).getId()))));
            assertTrue(Constant.IS_NO.equals(String.valueOf(licenceDetailService.checkLicenceStatus(licenceId, "11001",disposition_type_c1.getId(), wt_hw01.getId(), wt_hw01_wasteList.get(0).getId()))));
            assertTrue(Constant.IS_YSE.equals(String.valueOf(licenceDetailService.checkLicenceStatus(licenceId, "10999",disposition_type_c1.getId(), wt_hw01.getId(), wt_hw01_wasteList.get(0).getId()))));
            
            
        }finally{
            List<OperationLicenceDetail> saveResult = new ArrayList<OperationLicenceDetail>();
            for (OperationLicenceItem item : licenceItems) {
                saveResult.addAll(licenceDetailService.getlicenceDetails(licenceId, item.getId()));
            }
            licenceDao.delete(licenceId);
            for (OperationLicenceItem item : licenceItems) {
                licenceItemDao.delete(item.getId());
            }
            
            for (OperationLicenceDetail detail : saveResult) {
                licenceDetailDao.delete(detail.getId());
            }
        }
       
      //-------------------------删除操作-----------------------------------------
        
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceDetailServiceImpl#saveLicenceDetail(java.util.List, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testSaveLicenceDetail() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        OperationLicence licence = doLicenceDataReady(ticketId);
        String licenceId = Util.uuid32();
        licenceDao.save(licence, licenceId);
        
        List<OperationLicenceItem> licenceItems = doLicenceItemDataReady(licenceId, ticketId);
        for (OperationLicenceItem item : licenceItems) {
            licenceItemDao.save(item, Util.uuid32());
        }
        
        List<OperationLicenceDetail> licenceDetails = doLicenceDetailDataReady(licenceId, licenceItems, ticketId);
        assertTrue(licenceDetails.size() == 12);
        licenceDetailService.saveLicenceDetail(licenceDetails, ticketId);
        
        //-------------------------保存结果验证-----------------------------------------
        List<OperationLicenceDetail> saveResult = new ArrayList<OperationLicenceDetail>();
        for (OperationLicenceItem item : licenceItems) {
            saveResult.addAll(licenceDetailService.getlicenceDetails(licenceId, item.getId()));
        }
        assertTrue(saveResult.size() == 12);
        
        for (OperationLicenceDetail detail : licenceDetails) {
            if (StringUtils.isBlank(detail.getWaste_name_id())) {
                detail.setWaste_name_id("");
            }
        }
        for (OperationLicenceDetail detail : saveResult) {
            if (StringUtils.isBlank(detail.getWaste_name_id())) {
                detail.setWaste_name_id("");
            }
        }
        
        sort(licenceDetails);
        sort(saveResult);
        
        Set<String> set = new HashSet<String>();
        set.add("licence_id");
        set.add("operation_item_id");
        set.add("waste_type");
        set.add("waste_id");
        set.add("waste_name_id");
        for(int i=0;i<licenceDetails.size();i++){
            boolean compareValue = Util.compareValue(licenceDetails.get(i),saveResult.get(i),OperationLicenceDetail.class, set);
            assertTrue(compareValue);
        }
        
        licenceDao.delete(licenceId);
        for (OperationLicenceItem item : licenceItems) {
            licenceItemDao.delete(item.getId());
        }
        
        for (OperationLicenceDetail detail : saveResult) {
            licenceDetailDao.delete(detail.getId());
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceDetailServiceImpl#isHasLicenceDetail(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testIsHasLicenceDetail() throws Exception {
        OperationLicenceDetail testDetail = doLicenceDetailDataReady();
        String detailId = Util.uuid32();
        licenceDetailDao.save(testDetail, detailId);
        //-------------------------数据准备结束-----------------------------------------
        
        assertTrue(licenceDetailService.isHasLicenceDetail("AAAAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111","EEEEEEEEEE1111"));
        assertFalse(licenceDetailService.isHasLicenceDetail("AAAAAAAAAA2222", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111","EEEEEEEEEE1111"));
        assertFalse(licenceDetailService.isHasLicenceDetail("AAAAAAAAAA1111", "BBBBBBBBBB2222", "CCCCCCCCCC1111", "DDDDDDDDDD1111","EEEEEEEEEE1111"));
        assertFalse(licenceDetailService.isHasLicenceDetail("AAAAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC2222", "DDDDDDDDDD1111","EEEEEEEEEE1111"));
        assertFalse(licenceDetailService.isHasLicenceDetail("AAAAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD2222","EEEEEEEEEE1111"));
        assertFalse(licenceDetailService.isHasLicenceDetail("AAAAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111","EEEEEEEEEE2222"));
        assertFalse(licenceDetailService.isHasLicenceDetail("AAAAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111",""));
        assertFalse(licenceDetailService.isHasLicenceDetail("AAAAAAAAAA1111", "BBBBBBBBBB1111", "CCCCCCCCCC1111", "DDDDDDDDDD1111",null));
        
      //-------------------------删除操作-----------------------------------------
      licenceDetailDao.delete(detailId);
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceDetailServiceImpl#deleteLicenceDetailByLicenceIds(java.util.List, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testDeleteLicenceDetailByLicenceIds() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        OperationLicenceDetail testDetail1 = doLicenceDetailDataReady();
        testDetail1.setLicence_id("AAAAAAAAAA1111");
        licenceDetailDao.save(testDetail1, Util.uuid32());
        
        OperationLicenceDetail testDetail2 = doLicenceDetailDataReady();
        testDetail2.setLicence_id("AAAAAAAAAA2222");
        licenceDetailDao.save(testDetail2, Util.uuid32());
        
        OperationLicenceDetail testDetail3 = doLicenceDetailDataReady();
        testDetail3.setLicence_id("AAAAAAAAAA3333");
        licenceDetailDao.save(testDetail3, Util.uuid32());
        //-------------------------数据准备结束-----------------------------------------
        OperationLicenceDetail saveDetail1 = licenceDetailDao.get(testDetail1.getId());
        assertTrue(saveDetail1 != null);
        assertTrue(Constant.IS_VALID.equals(saveDetail1.getValid()));
        OperationLicenceDetail saveDetail2 = licenceDetailDao.get(testDetail2.getId());
        assertTrue(saveDetail2 != null);
        assertTrue(Constant.IS_VALID.equals(saveDetail2.getValid()));
        OperationLicenceDetail saveDetail3 = licenceDetailDao.get(testDetail3.getId());
        assertTrue(saveDetail3 != null);
        assertTrue(Constant.IS_VALID.equals(saveDetail3.getValid()));
        
        
        List<String> ids = new ArrayList<String>(3);
        licenceDetailService.deleteLicenceDetailByLicenceIds(ids, ticketId);
        
        ids.add("AAAAAAAAAA1111");
        ids.add("AAAAAAAAAA2222");
        ids.add("AAAAAAAAAA4444");
        licenceDetailService.deleteLicenceDetailByLicenceIds(ids, ticketId);
        
        OperationLicenceDetail update1 = licenceDetailDao.get(testDetail1.getId());
        assertTrue(update1 != null);
        assertTrue(Constant.IS_NOT_VALID.equals(update1.getValid()));
        OperationLicenceDetail update2 = licenceDetailDao.get(testDetail2.getId());
        assertTrue(update2 != null);
        assertTrue(Constant.IS_NOT_VALID.equals(update2.getValid()));
        OperationLicenceDetail update3 = licenceDetailDao.get(testDetail3.getId());
        assertTrue(update3 != null);
        assertTrue(Constant.IS_VALID.equals(update3.getValid()));
      //-------------------------删除操作-----------------------------------------
      licenceDetailDao.delete(testDetail1.getId());
      licenceDetailDao.delete(testDetail2.getId());
      licenceDetailDao.delete(testDetail3.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceDetailServiceImpl#getlicenceDetails(java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetlicenceDetails() throws Exception {
        OperationLicenceDetail testDetail1 = doLicenceDetailDataReady();
        testDetail1.setLicence_id("AAAAAAAAAA1111");
        testDetail1.setOperation_item_id("BBBBBBBBBB1111");
        licenceDetailDao.save(testDetail1, Util.uuid32());
        
        OperationLicenceDetail testDetail2 = doLicenceDetailDataReady();
        testDetail2.setLicence_id("AAAAAAAAAA2222");
        testDetail2.setOperation_item_id("BBBBBBBBBB2222");
        licenceDetailDao.save(testDetail2, Util.uuid32());
        
        OperationLicenceDetail testDetail3 = doLicenceDetailDataReady();
        testDetail3.setLicence_id("AAAAAAAAAA2222");
        testDetail3.setOperation_item_id("BBBBBBBBBB2222");
        licenceDetailDao.save(testDetail3, Util.uuid32());
        //-------------------------数据准备结束-----------------------------------------
        assertTrue(licenceDetailService.getlicenceDetails(null, "qqqq") == null);
        assertTrue(licenceDetailService.getlicenceDetails("qqqq", null) == null);
        
        List<OperationLicenceDetail> testDetails = licenceDetailService.getlicenceDetails(null, "qqqq");
        assertTrue(testDetails == null || testDetails.size() > 0);
        testDetails = licenceDetailService.getlicenceDetails("qqqq", null);
        assertTrue(testDetails == null || testDetails.size() > 0);
        testDetails = licenceDetailService.getlicenceDetails("AAAAAAAAAA1111", "BBBBBBBBBB1111");
        assertTrue(testDetails != null && testDetails.size() == 1);
        OperationLicenceDetail testDetail = testDetails.get(0);
        assertTrue("AAAAAAAAAA1111".equals(testDetail.getLicence_id()));
        assertTrue("BBBBBBBBBB1111".equals(testDetail.getOperation_item_id()));
        
        testDetails = licenceDetailService.getlicenceDetails("AAAAAAAAAA2222", "BBBBBBBBBB2222");
        assertTrue(testDetails != null && testDetails.size() == 2);
        
      //-------------------------删除操作-----------------------------------------
      licenceDetailDao.delete(testDetail1.getId());
      licenceDetailDao.delete(testDetail2.getId());
      licenceDetailDao.delete(testDetail3.getId());
    }
    
    public static void sort(List<OperationLicenceDetail> list){
        if(list != null){
            Collections.sort(list, new  Comparator<OperationLicenceDetail>() {
                @Override
                public int compare(OperationLicenceDetail paramT1, OperationLicenceDetail paramT2) {
                    int idCompare = paramT1.getLicence_id().compareTo(paramT2.getLicence_id());
                    if (idCompare == 0) {
                        idCompare = paramT1.getOperation_item_id().compareTo(paramT2.getOperation_item_id());
                        if (idCompare == 0) {
                            idCompare = paramT1.getWaste_type().compareTo(paramT2.getWaste_type());
                            if (idCompare == 0) {
                                idCompare = paramT1.getWaste_id().compareTo(paramT2.getWaste_id());
                                if (idCompare == 0) {
                                    if (StringUtils.isBlank(paramT1.getWaste_name_id())) {
                                        paramT1.setWaste_name_id("");
                                    }
                                    if (StringUtils.isBlank(paramT2.getWaste_name_id())) {
                                        paramT2.setWaste_name_id("");
                                    }
                                    return (paramT1.getWaste_name_id() == null?"":paramT1.getWaste_name_id()).compareTo((paramT2.getWaste_name_id() == null?"":paramT2.getWaste_name_id()));
                                } else{
                                    return idCompare;
                                }
                            } else{
                                return idCompare;
                            }
                        } else{
                            return idCompare;
                        }
                    } else {
                        return idCompare;
                    }
                }
            } );
        }
    }
    
}
