/**
 * 
 */
package com.mlsc.waste.licence.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.fw.service.SysOrgComService;
import com.mlsc.waste.licence.dao.LicenceDao;
import com.mlsc.waste.licence.dao.LicenceDetailDao;
import com.mlsc.waste.licence.dao.LicenceItemDao;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceDetail;
import com.mlsc.waste.licence.model.OperationLicenceDetailExtend;
import com.mlsc.waste.licence.model.OperationLicenceDetailVo;
import com.mlsc.waste.licence.model.OperationLicenceItem;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
import com.mlsc.waste.licence.model.OperationLicenceVo;
import com.mlsc.waste.licence.service.LicenceDetailService;
import com.mlsc.waste.licence.service.LicenceItemService;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.utils.datatable.DataTablesUtils;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class LicenceServiceImplTest {
    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private LicenceService licenceService;
    
    @Autowired
    private LicenceItemService licenceItemService;
    
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
    
    @Autowired
    private PlatformSupporter platformSupporter; //拿到平台表的client
    
    private static Map<String, String> testListWasteVo = new HashMap<String, String>();
    
    private OperationLicence doLicenceDataReady(String ticketId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        CodeValue auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE);
        CodeValue licenceStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_VALID, CodeTypeConstant.LIC_VALID_INVALID);
        CodeValue operation_mode = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_MODE, CodeTypeConstant.LIC_MODE_COLLECTION);
        List<RPCSysOrgCom> licence_orglistComs = sysOrgComService.querySysOrgComListByCantonID(ticketId, Constant.COM_TYPE_EPA, "340823");
        
        RPCSysEnterpriseBase enterpriseBase = platformSupporter.getOrgComServiceManager().queryEnterprise(ticketId, user.getEnterpriseId());
        
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
        licence.setEnd_date("2018-01-01 23:59:59");
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
        testListWasteVo.clear();
        
        CodeValue disposition_type_c1 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C1);
        CodeValue disposition_type_c2 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C2);
        List<OperationLicenceDetail> returnResult = new ArrayList<OperationLicenceDetail>();
        OperationLicenceDetail licenceDetail = null;
        StringBuffer listWasteVosb = null;
        for (OperationLicenceItem item : licenceItems) {
            if (disposition_type_c1.getId().equals(item.getDisposition_type())) {
                WasteType wt_hw01 = wasteTypeService.getWasteTypeByWasteCode("HW01");
                List<Waste> wt_hw01_wasteList = wasteService.getWastesByWasteTypeId(wt_hw01.getId());
                listWasteVosb = new StringBuffer();
                for (Waste waste : wt_hw01_wasteList) {
                    listWasteVosb.append(waste.getCode());
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
                        listWasteVosb.append(":" + wasteNames.get(0).getName());
                    }
                    
                    listWasteVosb.append(";");
                    returnResult.add(licenceDetail);
                }
                if (listWasteVosb.length() > 0 ) {
                    listWasteVosb.deleteCharAt(listWasteVosb.length()-1);//去掉末尾的逗号
                }
                testListWasteVo.put(wt_hw01.getId(), listWasteVosb.toString());
            } else if (disposition_type_c2.getId().equals(item.getDisposition_type())) {
                WasteType wt_hw07 = wasteTypeService.getWasteTypeByWasteCode("HW07");
                List<Waste> wt_hw07_wasteList = wasteService.getWastesByWasteTypeId(wt_hw07.getId());
                listWasteVosb = new StringBuffer();
                for (Waste waste : wt_hw07_wasteList) {
                    listWasteVosb.append(waste.getCode());
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
                        listWasteVosb.append(":" + wasteNames.get(0).getName());
                    }
                    
                    listWasteVosb.append(";");
                    returnResult.add(licenceDetail);
                }
                if (listWasteVosb.length() > 0 ) {
                    listWasteVosb.deleteCharAt(listWasteVosb.length()-1);//去掉末尾的逗号
                }
                testListWasteVo.put(wt_hw07.getId(), listWasteVosb.toString());
            }
        }
        return returnResult;
    }

    private String newAddLicenceAndSumit() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence = doLicenceDataReady(ticketId);
        String licenceId = Util.uuid32();
        licenceDao.save(licence, licenceId);
        
        List<OperationLicenceItem> licenceItems = doLicenceItemDataReady(licenceId, ticketId);
        CodeValue disposition_type_c1 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C1);
        CodeValue disposition_type_c2 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C2);
        for (OperationLicenceItem item : licenceItems) {
            if (disposition_type_c1.getId().equals(item.getDisposition_type())) {
                item.setExcuted_quantity("2000");
            } else if (disposition_type_c2.getId().equals(item.getDisposition_type())) {
                item.setExcuted_quantity("5000");
            }
            licenceItemDao.save(item, Util.uuid32());
        }
        
        List<OperationLicenceDetail> licenceDetails = doLicenceDetailDataReady(licenceId, licenceItems, ticketId);
        assertTrue(licenceDetails.size() == 12);
        licenceDetailService.saveLicenceDetail(licenceDetails, ticketId);
        return licenceId;
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#list(java.lang.String, java.util.Map, com.mlsc.epdp.common.base.entity.PagingParameter, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testList() throws Exception {
        String where = "";
        Map<String, Object> params = new HashMap<String, Object>(3);
        PagingParameter paging=DataTablesUtils.generatePagingParameter(0, 10);
        
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2017-01-02 00:00:00");
        licence1.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence1, Util.uuid32());//企业创建的许可证
        
        OperationLicence licence2 = doLicenceDataReady(ticketId);
        licence2.setLicence_no("jtest-1000002");
        licence2.setStart_date("2018-01-02 00:00:00");
        licence2.setEnd_date("2019-01-01 23:59:59");
        licenceDao.save(licence2, Util.uuid32());//审核被拒的许可证
        String auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED).getId();
        List<String> licenceIds = new ArrayList<String>();
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        licenceIds.add(licence2.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence3 = doLicenceDataReady(ticketId);
        licence3.setLicence_no("jtest-1000003");
        licence3.setStart_date("2017-01-02 00:00:00");
        licence3.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence3, Util.uuid32());//待审核的许可证
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence3.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence4 = doLicenceDataReady(ticketId);
        licence4.setLicence_no("jtest-1000004");
        licence4.setStart_date("2016-01-02 00:00:00");
        licence4.setEnd_date("2017-01-01 23:59:59");
        licenceDao.save(licence4, Util.uuid32());//审核通过的许可证,并过期
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence4.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence5 = doLicenceDataReady(ticketId);
        licence5.setLicence_no("jtest-1000005");
        licence5.setStart_date("2017-01-02 00:00:00");
        licence5.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence5, Util.uuid32());//审核通过的许可证
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence5.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
      //-------------------------数据准备结束-----------------------------------------
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        List<OperationLicenceVo> testlist = licenceService.list(where, params, paging,user.getEnterpriseId());
        assertTrue(testlist != null && testlist.size() == 4);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue(!"jtest-1000005".equals(licenceVo.getLicence_no()));
        }
        
        where = "and licence_no like :licence_no";
        params = new HashMap<String, Object>(3);
        params.put("licence_no", "%0004%");
        testlist = licenceService.list(where, params, paging,user.getEnterpriseId());
        assertTrue(testlist != null && testlist.size() == 1);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue("jtest-1000004".equals(licenceVo.getLicence_no()));
        }
        
        where = "and licence_no like :licence_no  and licenceStatus like :licenceStatus";
        params = new HashMap<String, Object>(3);
        params.put("licence_no", "%jtest-%");
        params.put("licenceStatus", "%INVALID%");
        testlist = licenceService.list(where, params, paging,user.getEnterpriseId());
        assertTrue(testlist != null && testlist.size() == 3);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue(!"jtest-1000004".equals(licenceVo.getLicence_no()));
            assertTrue(!"jtest-1000005".equals(licenceVo.getLicence_no()));
        }
        
        params.put("licenceStatus", "%OVERDUE%");
        testlist = licenceService.list(where, params, paging,user.getEnterpriseId());
        assertTrue(testlist != null && testlist.size() == 1);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue("jtest-1000004".equals(licenceVo.getLicence_no()));
        }
        
        CodeValue auditStatusCreat = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE);
        CodeValue auditStatusSubmit = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT);
        CodeValue auditStatusRefused = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED);
        where = "and licence_no like :licence_no and auditStatus like :auditStatus";
        params = new HashMap<String, Object>(3);
        params.put("licence_no", "%jtest-%");
        params.put("auditStatus", "%" + auditStatusCreat.getId() + "%");
        testlist = licenceService.list(where, params, paging,user.getEnterpriseId());
        assertTrue(testlist != null && testlist.size() == 1);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue("jtest-1000001".equals(licenceVo.getLicence_no()));
        }
        
        params.put("auditStatus", "%" + auditStatusSubmit.getId() + "%");
        testlist = licenceService.list(where, params, paging,user.getEnterpriseId());
        assertTrue(testlist != null && testlist.size() == 1);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue("jtest-1000003".equals(licenceVo.getLicence_no()));
        }
        
        params.put("auditStatus", "%" + auditStatusRefused.getId() + "%");
        testlist = licenceService.list(where, params, paging,user.getEnterpriseId());
        assertTrue(testlist != null && testlist.size() == 1);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue("jtest-1000002".equals(licenceVo.getLicence_no()));
        }
        
        where = "and licence_no like :licence_no";
        params = new HashMap<String, Object>(3);
        params.put("licence_no", "%jtest-w%");
        testlist = licenceService.list(where, params, paging,user.getEnterpriseId());
        assertTrue(testlist == null || testlist.size() == 0);
        
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
        licenceDao.delete(licence2.getId());
        licenceDao.delete(licence3.getId());
        licenceDao.delete(licence4.getId());
        licenceDao.delete(licence5.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#count(java.lang.String, java.util.Map, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testCount() throws Exception {
        String where = "";
        Map<String, Object> params = new HashMap<String, Object>(3);
        
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2017-01-02 00:00:00");
        licence1.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence1, Util.uuid32());//企业创建的许可证
        
        OperationLicence licence2 = doLicenceDataReady(ticketId);
        licence2.setLicence_no("jtest-1000002");
        licence1.setStart_date("2018-01-02 00:00:00");
        licence1.setEnd_date("2019-01-01 23:59:59");
        licenceDao.save(licence2, Util.uuid32());//审核被拒的许可证
        String auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED).getId();
        List<String> licenceIds = new ArrayList<String>();
        licenceIds.add(licence2.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence3 = doLicenceDataReady(ticketId);
        licence3.setLicence_no("jtest-1000003");
        licence3.setStart_date("2017-01-02 00:00:00");
        licence3.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence3, Util.uuid32());//待审核的许可证
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence3.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence4 = doLicenceDataReady(ticketId);
        licence4.setLicence_no("jtest-1000004");
        licence4.setStart_date("2016-01-02 00:00:00");
        licence4.setEnd_date("2017-01-01 23:59:59");
        licenceDao.save(licence4, Util.uuid32());//审核通过的许可证,并过期
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence4.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence5 = doLicenceDataReady(ticketId);
        licence5.setLicence_no("jtest-1000005");
        licence5.setStart_date("2017-01-02 00:00:00");
        licence5.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence5, Util.uuid32());//审核通过的许可证
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence5.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
      //-------------------------数据准备结束-----------------------------------------
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        int count = licenceService.count(where, params,user.getEnterpriseId());
        assertTrue(count == 4);
        
        where = "and licence_no like :licence_no";
        params = new HashMap<String, Object>(3);
        params.put("licence_no", "%0004%");
        count = licenceService.count(where, params,user.getEnterpriseId());
        assertTrue(count == 1);
        
        where = "and licence_no like :licence_no  and licenceStatus like :licenceStatus";
        params = new HashMap<String, Object>(3);
        params.put("licence_no", "%jtest-%");
        params.put("licenceStatus", "%INVALID%");
        count = licenceService.count(where, params,user.getEnterpriseId());
        assertTrue(count == 3);
        
        params.put("licenceStatus", "%OVERDUE%");
        count = licenceService.count(where, params,user.getEnterpriseId());
        assertTrue(count == 1);
        
        CodeValue auditStatusCreat = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE);
        CodeValue auditStatusSubmit = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT);
        CodeValue auditStatusRefused = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED);
        where = "and licence_no like :licence_no and auditStatus like :auditStatus";
        params = new HashMap<String, Object>(3);
        params.put("licence_no", "%jtest-%");
        params.put("auditStatus", "%" + auditStatusCreat.getId() + "%");
        count = licenceService.count(where, params,user.getEnterpriseId());
        assertTrue(count == 1);
        
        params.put("auditStatus", "%" + auditStatusSubmit.getId() + "%");
        count = licenceService.count(where, params,user.getEnterpriseId());
        assertTrue(count == 1);
        
        params.put("auditStatus", "%" + auditStatusRefused.getId() + "%");
        count = licenceService.count(where, params,user.getEnterpriseId());
        assertTrue(count == 1);
        
        where = "and licence_no like :licence_no";
        params = new HashMap<String, Object>(3);
        params.put("licence_no", "%jtest-w%");
        count = licenceService.count(where, params,user.getEnterpriseId());
        assertTrue(count == 0);
        
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
        licenceDao.delete(licence2.getId());
        licenceDao.delete(licence3.getId());
        licenceDao.delete(licence4.getId());
        licenceDao.delete(licence5.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#submitAudit(java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testSubmitAudit() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licenceDao.save(licence1, Util.uuid32());
        
        CodeValue auditStatusCreat = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE);
        CodeValue auditStatusSubmit = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT);
      //-------------------------数据准备结束-----------------------------------------
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        OperationLicence licenceUpdateLicence1 = licenceDao.get(licence1.getId());
        assertTrue(auditStatusCreat.getId().equals(licenceUpdateLicence1.getAudit_status()));
        assertTrue(StringUtils.isBlank(licenceUpdateLicence1.getApproved_by()));
        
        licenceService.submitAudit(licence1.getId(), ticketId);
        
        licenceUpdateLicence1 = licenceDao.get(licence1.getId());
        assertTrue(auditStatusSubmit.getId().equals(licenceUpdateLicence1.getAudit_status()));
        assertTrue(user.getUserId().equals(licenceUpdateLicence1.getApproved_by()));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#deleteLicenceByLicenceIds(java.util.List, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testDeleteLicenceByLicenceIds() throws Exception {
         String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licenceDao.save(licence1, Util.uuid32());
        
        OperationLicence licence2 = doLicenceDataReady(ticketId);
        licence2.setLicence_no("jtest-1000002");
        licenceDao.save(licence2, Util.uuid32());
        
        OperationLicence licence3 = doLicenceDataReady(ticketId);
        licence3.setLicence_no("jtest-1000003");
        licenceDao.save(licence3, Util.uuid32());
        //-------------------------数据准备结束-----------------------------------------
        OperationLicence saveLicence1 = licenceDao.get(licence1.getId());
        assertTrue(saveLicence1 != null && Constant.IS_VALID.equals(saveLicence1.getValid()));
        OperationLicence saveLicence2 = licenceDao.get(licence2.getId());
        assertTrue(saveLicence2 != null && Constant.IS_VALID.equals(saveLicence2.getValid()));
        OperationLicence saveLicence3 = licenceDao.get(licence3.getId());
        assertTrue(saveLicence3 != null && Constant.IS_VALID.equals(saveLicence3.getValid()));
        
        List<String> ids = new ArrayList<String>(3);
        licenceService.deleteLicenceByLicenceIds(ids, ticketId);
        ids.add(licence1.getId());
        ids.add(licence2.getId());
        ids.add("AAAAAAAAAA4444");
        licenceService.deleteLicenceByLicenceIds(ids, ticketId);
        
        saveLicence1 = licenceDao.get(licence1.getId());
        assertTrue(saveLicence1 != null && Constant.IS_NOT_VALID.equals(saveLicence1.getValid()));
        saveLicence2 = licenceDao.get(licence2.getId());
        assertTrue(saveLicence2 != null && Constant.IS_NOT_VALID.equals(saveLicence2.getValid()));
        saveLicence3 = licenceDao.get(licence3.getId());
        assertTrue(saveLicence3 != null && Constant.IS_VALID.equals(saveLicence3.getValid()));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
        licenceDao.delete(licence2.getId());
        licenceDao.delete(licence3.getId());
    }
    
    /**
     * @throws Exception
     */
    @Test
    public void testGetValidLicId() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2017-01-02 00:00:00");
        licence1.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence1, Util.uuid32());//企业创建的许可证
        
        OperationLicence licence2 = doLicenceDataReady(ticketId);
        licence2.setLicence_no("jtest-1000002");
        licence2.setStart_date("2018-01-02 00:00:00");
        licence2.setEnd_date("2019-01-01 23:59:59");
        licenceDao.save(licence2, Util.uuid32());//审核被拒的许可证
        String auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED).getId();
        List<String> licenceIds = new ArrayList<String>();
        licenceIds.add(licence2.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence3 = doLicenceDataReady(ticketId);
        licence3.setLicence_no("jtest-1000003");
        licence3.setStart_date("2017-01-02 00:00:00");
        licence3.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence3, Util.uuid32());//待审核的许可证
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence3.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence4 = doLicenceDataReady(ticketId);
        licence4.setLicence_no("jtest-1000004");
        licence4.setStart_date("2016-01-02 00:00:00");
        licence4.setEnd_date("2017-01-01 23:59:59");
        licenceDao.save(licence4, Util.uuid32());//审核通过的许可证,并过期
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence4.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence5 = doLicenceDataReady(ticketId);
        licence5.setLicence_no("jtest-1000005");
        licence5.setStart_date("2017-01-02 00:00:00");
        licence5.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence5, Util.uuid32());//审核通过的许可证
        
      //-------------------------数据准备结束-----------------------------------------
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        List<OperationLicence> testList = licenceService.listLicenceByEnterId(user.getEnterpriseId());
        assertTrue(testList == null || testList.size() == 0);
        
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence5.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        testList = licenceService.listLicenceByEnterId(user.getEnterpriseId());
        assertTrue(testList != null && testList.size() == 1);
        assertTrue("jtest-1000005".equals(testList.get(0).getLicence_no()));
        
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
        licenceDao.delete(licence2.getId());
        licenceDao.delete(licence3.getId());
        licenceDao.delete(licence4.getId());
        licenceDao.delete(licence5.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getAuditStatusByLicenceId(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetAuditStatusByLicenceId() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2017-01-02 00:00:00");
        licence1.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence1, Util.uuid32());//企业创建的许可证
      //-------------------------数据准备结束-----------------------------------------
        assertTrue(CodeTypeConstant.LIC_AUDIT_CREATE.equals(licenceService.getAuditStatusByLicenceId(licence1.getId())));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#isLicenceNoExist(java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testIsLicenceNoExist() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2017-01-02 00:00:00");
        licence1.setEnd_date("2018-01-01 23:59:59");
      //-------------------------数据准备结束-----------------------------------------
        assertFalse(licenceService.isLicenceNoExist("jtest-1000001",null));
        licenceDao.save(licence1, Util.uuid32());//企业创建的许可证
        assertTrue(licenceService.isLicenceNoExist("jtest-1000001",null));
        assertFalse(licenceService.isLicenceNoExist("jtest-1000001",licence1.getId()));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#isValidityPeriodRepeat(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testIsValidityPeriodRepeat() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2017-01-02 00:00:00");
        licence1.setEnd_date("2018-01-01 23:59:59");
      //-------------------------数据准备结束-----------------------------------------
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        assertFalse(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),"","2017-01-02 00:00:00","2018-01-01 23:59:59"));
        licenceDao.save(licence1, Util.uuid32());//企业创建的许可证
        assertTrue(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),"","2017-01-02 00:00:01","2018-01-01 23:59:59"));
        assertTrue(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),"","2017-01-02 00:00:00","2018-01-02 00:00:00"));
        assertTrue(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),"","2017-01-01 00:00:00","2018-01-02 23:59:59"));
        assertTrue(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),"","2017-01-03 00:00:00","2017-01-02 23:59:59"));
        assertFalse(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),"","2018-01-03 00:00:00","2019-01-02 23:59:59"));
        assertFalse(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),licence1.getId(),"2018-01-03 00:00:00","2019-01-02 23:59:59"));
        
        assertFalse(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),licence1.getId(),"2017-01-02 00:00:01","2018-01-01 23:59:59"));
        assertFalse(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),licence1.getId(),"2017-01-02 00:00:00","2018-01-02 00:00:00"));
        assertFalse(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),licence1.getId(),"2017-01-01 00:00:00","2018-01-02 23:59:59"));
        assertFalse(licenceService.isValidityPeriodRepeat(user.getEnterpriseId(),licence1.getId(),"2017-01-03 00:00:00","2017-01-02 23:59:59"));
        
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getLicneceById(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetLicneceById() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2017-01-02 00:00:00");
        licence1.setEnd_date("2018-01-01 23:59:59");
      //-------------------------数据准备结束-----------------------------------------
        OperationLicence testLicence = licenceService.getLicneceById("1111");
        assertTrue(testLicence == null);
        licenceDao.save(licence1, Util.uuid32());//企业创建的许可证
        testLicence = licenceService.getLicneceById(licence1.getId());
        assertTrue(testLicence != null && "jtest-1000001".equals(testLicence.getLicence_no()));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#deleteDispositionType(java.lang.String, java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testDeleteDispositionType() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        String licenceId = newAddLicenceAndSumit();
      //-------------------------数据准备结束-----------------------------------------
        OperationLicence testLicence = licenceDao.get(licenceId);
        assertTrue(testLicence != null);
        
        OperationLicenceItem queryItem = new OperationLicenceItem();
        queryItem.setLicence_id(licenceId);
        List<OperationLicenceItem> listItems = licenceItemDao.query(queryItem);
        assertTrue(listItems.size() == 2);
        
        OperationLicenceDetail queryDetail = new OperationLicenceDetail();
        queryDetail.setLicence_id(licenceId);
        List<OperationLicenceDetail> listDetails = licenceDetailDao.query(queryDetail);
        assertTrue(listDetails.size() == 12);
        
        licenceService.deleteDispositionType(listItems.get(0).getId(), licenceId, ticketId);
        
        listItems = licenceItemDao.query(queryItem);
        assertTrue(listItems.size() == 1);
        
        listDetails = licenceDetailDao.query(queryDetail);
        assertTrue(listDetails.size() == 6);
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licenceId);
        licenceItemDao.delete(listItems.get(0).getId());
        for (OperationLicenceDetail detail : listDetails){
            licenceDetailDao.delete(detail.getId());
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getlicenceApprovedById(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetlicenceApprovedById() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2017-01-02 00:00:00");
        licence1.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence1, Util.uuid32());//企业创建的许可证
      //-------------------------数据准备结束-----------------------------------------
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        OperationLicenceVo licenceVo = licenceService.getlicenceApprovedById(licence1.getId());
        assertTrue(licence1.getId().equals(licenceVo.getId()));
        assertTrue(user.getEnterpriseId().equals(licenceVo.getEnterprise_id()));
        assertTrue(licence1.getLicence_no().equals(licenceVo.getLicence_no()));
        assertTrue(null == licenceVo.getInitiallic_date());
        assertTrue(licence1.getCorporate().equals(licenceVo.getCorporate()));
        assertTrue(licence1.getRegister_addr().equals(licenceVo.getRegister_addr()));
        assertTrue(licence1.getMachine_addr().equals(licenceVo.getMachine_addr()));
        assertTrue(licence1.getLicence_status().equals(licenceVo.getLicence_status()));
        assertTrue(licence1.getOperation_mode().equals(licenceVo.getOperation_mode()));
        assertTrue(licence1.getAudit_status().equals(licenceVo.getAudit_status()));
        assertTrue("2017-01-02 至 2018-01-01".equals(licenceVo.getValidityPeriod()));
        assertTrue("无效".equals(licenceVo.getLicenceStatus()));
        assertTrue(CodeTypeConstant.LIC_AUDIT_CREATE.equals(licenceVo.getAuditStatusCode()));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getWasteInfoByLicenceId(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetWasteInfoByLicenceId() throws Exception {
        String licenceId = newAddLicenceAndSumit();
        OperationLicence testLicence = licenceDao.get(licenceId);
        assertTrue(testLicence != null);
        
        OperationLicenceItem queryItem = new OperationLicenceItem();
        queryItem.setLicence_id(licenceId);
        List<OperationLicenceItem> listItems = licenceItemDao.query(queryItem);
        assertTrue(listItems.size() == 2);
        
        OperationLicenceDetail queryDetail = new OperationLicenceDetail();
        queryDetail.setLicence_id(licenceId);
        List<OperationLicenceDetail> listDetails = licenceDetailDao.query(queryDetail);
        assertTrue(listDetails.size() == 12);
      //-------------------------数据准备结束-----------------------------------------
        List<OperationLicenceItemVo> itemVos = licenceService.getWasteInfoByLicenceId(licenceId);
        assertTrue(itemVos.size() == 2);
        CodeValue disposition_type_c1 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C1);
        CodeValue disposition_type_c2 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C2);
        for (OperationLicenceItemVo itemVo : itemVos){
            if (disposition_type_c1.getId().equals(itemVo.getDispositionTypeId())) {
                assertTrue("12,000.000".equals(itemVo.getApproved_quantity()));
                assertTrue("2,000.000".equals(itemVo.getExcuted_quantity()));
                assertTrue("10,000.000".equals(itemVo.getSurplus_quantity()));
                assertTrue("83.3333".equals(itemVo.getSurplus_percentage().substring(0, 7)));
                assertTrue(itemVo.getListWasteTypeVo().size() == 1);
                for (OperationLicenceDetailVo typeDetail : itemVo.getListWasteTypeVo()){
                    assertTrue("HW01".equals(typeDetail.getWasteTypeCode()));
                    assertTrue(testListWasteVo.get(typeDetail.getWasteTypeId()).toString().equals(typeDetail.getListWasteVo().get(0)));
                }
            } else if (disposition_type_c2.getId().equals(itemVo.getDispositionTypeId())) {
                assertTrue("15,000.000".equals(itemVo.getApproved_quantity()));
                assertTrue("5,000.000".equals(itemVo.getExcuted_quantity()));
                assertTrue("10,000.000".equals(itemVo.getSurplus_quantity()));
                assertTrue("66.6666".equals(itemVo.getSurplus_percentage().substring(0, 7)));
                assertTrue(itemVo.getListWasteTypeVo().size() == 1);
                for (OperationLicenceDetailVo typeDetail : itemVo.getListWasteTypeVo()){
                    assertTrue("HW07".equals(typeDetail.getWasteTypeCode()));
                    assertTrue(testListWasteVo.get(typeDetail.getWasteTypeId()).toString().equals(typeDetail.getListWasteVo().get(0)));
                }
            }
        }
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licenceId);
        for (OperationLicenceItem item : listItems){
            licenceDetailDao.delete(item.getId());
        }
        for (OperationLicenceDetail detail : listDetails){
            licenceDetailDao.delete(detail.getId());
        }
    }
    

    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getWasteByWasteTypeId(java.lang.String)}.
     */
    @Test
    public void testGetWasteByWasteTypeId() throws Exception {
        List<WasteType> wtlist = licenceService.getAllWateType();
        assertTrue(wtlist.size() != 0);
        for (WasteType wt : wtlist){
            if ("HW01".equals(wt.getCode())) {
                List<Waste> wlist = licenceService.getWasteByWasteTypeId(wt.getId());
                assertTrue(wlist.size() != 0);
                for (Waste w : wlist){
                    assertTrue("01".equals(w.getCode().split("-")[2]));
                }
            }
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#deleteLicenceByIds(java.util.List, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testDeleteLicenceByIds() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2017-01-02 00:00:00");
        licence1.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence1, Util.uuid32());//企业创建的许可证
        
        OperationLicence licence2 = doLicenceDataReady(ticketId);
        licence2.setLicence_no("jtest-1000002");
        licence2.setStart_date("2018-01-02 00:00:00");
        licence2.setEnd_date("2019-01-01 23:59:59");
        licenceDao.save(licence2, Util.uuid32());//审核被拒的许可证
        String auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED).getId();
        List<String> licenceIds = new ArrayList<String>();
        licenceIds.add(licence2.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence3 = doLicenceDataReady(ticketId);
        licence3.setLicence_no("jtest-1000003");
        licence3.setStart_date("2017-01-02 00:00:00");
        licence3.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence3, Util.uuid32());//待审核的许可证
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence3.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence4 = doLicenceDataReady(ticketId);
        licence4.setLicence_no("jtest-1000004");
        licence4.setStart_date("2016-01-02 00:00:00");
        licence4.setEnd_date("2017-01-01 23:59:59");
        licenceDao.save(licence4, Util.uuid32());//审核通过的许可证,并过期
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence4.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence5 = doLicenceDataReady(ticketId);
        licence5.setLicence_no("jtest-1000005");
        licence5.setStart_date("2017-01-02 00:00:00");
        licence5.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence5, Util.uuid32());//审核通过的许可证
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence5.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
      //-------------------------数据准备结束-----------------------------------------
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence3.getId());
        assertFalse(licenceService.deleteLicenceByIds(licenceIds, ticketId));
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence4.getId());
        assertFalse(licenceService.deleteLicenceByIds(licenceIds, ticketId));
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence5.getId());
        assertFalse(licenceService.deleteLicenceByIds(licenceIds, ticketId));
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence1.getId());
        licenceIds.add(licence2.getId());
        assertTrue(licenceService.deleteLicenceByIds(licenceIds, ticketId));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
        licenceDao.delete(licence2.getId());
        licenceDao.delete(licence3.getId());
        licenceDao.delete(licence4.getId());
        licenceDao.delete(licence5.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#isEnterpriseVaild(java.lang.String)}.
     * @throws Exception 
     */
   // @Test
    public void testIsEnterpriseVaild() throws Exception {
        String ticketId = userLoginService.userLogin("18051116815", "123!@#");
        assertFalse(licenceService.isEnterpriseVaild(ticketId));
        
        ticketId = userLoginService.userLogin("18120046886", "test123");
        assertTrue(licenceService.isEnterpriseVaild(ticketId));
    }
    
    private OperationLicenceItem doLicenceItemDataReady() throws Exception {
        OperationLicenceItem testItem = new OperationLicenceItem();
        testItem.setLicence_id("AAAAAAAAAA1111");
        testItem.setDisposition_type("CCCCCCCCCC1111");
        testItem.setApproved_quantity("10000");
        testItem.setExcuted_quantity("50");
        testItem.setRemark(null);
        testItem.setCreate_by("2000000000");
        testItem.setCreate_time(Util.datetimeToString(new Date()));
        testItem.setEdit_by("2000000000");
        testItem.setEdit_time(Util.datetimeToString(new Date()));
        testItem.setValid(Constant.IS_VALID);
        
        return testItem;
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#saveOrUpdateDispositionItem(com.mlsc.waste.licence.model.OperationLicenceItem, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testSaveOrUpdateDispositionItem() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        OperationLicenceItem testItem = doLicenceItemDataReady();
        String itemId = licenceService.saveOrUpdateDispositionItem(testItem, ticketId);
      //-------------------------数据准备结束-----------------------------------------
        OperationLicenceItem saveItem = licenceItemDao.get(itemId);
        assertTrue(saveItem != null);
        assertTrue(testItem.getLicence_id().equals(saveItem.getLicence_id()));
        assertTrue(testItem.getDisposition_type().equals(saveItem.getDisposition_type()));
        assertTrue(testItem.getApproved_quantity().split("\\.")[0].equals(saveItem.getApproved_quantity().split("\\.")[0]));
        assertTrue("0".equals(saveItem.getExcuted_quantity().split("\\.")[0]));
        
        saveItem.setDisposition_type("CCCCCCCCCC2222");
        saveItem.setApproved_quantity("800");
        saveItem.setExcuted_quantity("51");
        String itemId1 = licenceService.saveOrUpdateDispositionItem(saveItem, ticketId);
        
        assertTrue(itemId.equals(itemId1));
        
        OperationLicenceItem saveItem1 = licenceItemDao.get(itemId);
        assertTrue(saveItem1 != null);
        assertTrue("CCCCCCCCCC2222".equals(saveItem1.getDisposition_type()));
        assertTrue("800".equals(saveItem1.getApproved_quantity().split("\\.")[0]));
      //-------------------------删除操作-----------------------------------------
        licenceItemDao.delete(itemId);
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getWasteNamesByWasteId(java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetWasteNamesByWasteId() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        String wasteId = "WASTE20170118";
        String wasteName = "WASTE_NAME20170118";
        for (int i = 0 ;i < 10; i++) {
            wasteNameService.saveOrUpdateWasteName(wasteId, wasteName + i, ticketId);
        }
        wasteNameService.saveOrUpdateWasteName("WASTE20170119", "WASTE_NAME201701182", ticketId);
        wasteNameService.saveOrUpdateWasteName("WASTE20170118", "WASTE_NAME201701189", ticketId);
      //-------------------------数据准备结束-----------------------------------------
        Map<String, Object> jsonMap = licenceService.getWasteNamesByWasteId(wasteId, wasteName);
        
        JSONArray jsonArray = (JSONArray) jsonMap.get("value");
        assertTrue(jsonArray != null && jsonArray.size() == 10);
        int index=0;
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            if ((wasteId + index).equals(jsonObject.get("wasteNameId").toString())) {
                assertTrue((wasteName + index).equals(jsonObject.get("wasteName").toString()));
            }
            
            index++;
        }
        
        assertTrue(index == 10);
      //-------------------------删除操作-----------------------------------------
        wasteNameService.removeWasteNamesByWasteId(wasteId);
        wasteNameService.removeWasteNamesByWasteId("WASTE20170119");
    }
    
    private List<OperationLicenceDetailExtend> doLicenceDetailDataReady() throws Exception {
        List<OperationLicenceDetailExtend> returnResult = new ArrayList<OperationLicenceDetailExtend>();
        OperationLicenceDetailExtend detailExtend = null;
        String licenceId = "AAAAAAAA1111";
        String licenceItemId = "BBBBBBBBBB1111";
        String waste_type = "CCCCCCCCCC1111";
        String waste_Id = "DDDDDDDDDD1111";
        for (int i=0;i<10;i++) {
            if (i == 0) {
                detailExtend = new OperationLicenceDetailExtend();
                detailExtend.setLicence_id(licenceId);
                detailExtend.setOperation_item_id(licenceItemId);
                detailExtend.setWaste_type(waste_type);
                detailExtend.setWaste_id(waste_Id);
                detailExtend.setWaste_name_id("");
                detailExtend.setWaste_name("测试危废1");
            }else if (i == 1) {
                detailExtend = new OperationLicenceDetailExtend();
                detailExtend.setLicence_id(licenceId);
                detailExtend.setOperation_item_id(licenceItemId);
                detailExtend.setWaste_type(waste_type);
                detailExtend.setWaste_id(waste_Id);
                detailExtend.setWaste_name_id("");
                detailExtend.setWaste_name("");
            }else if (i == 2) {
                detailExtend = new OperationLicenceDetailExtend();
                detailExtend.setLicence_id(licenceId);
                detailExtend.setOperation_item_id(licenceItemId);
                detailExtend.setWaste_type(waste_type);
                detailExtend.setWaste_id(waste_Id);
                detailExtend.setWaste_name_id("EEEEEEEEEEE1112");
                detailExtend.setWaste_name("测试危废3");
            } else {
                detailExtend = new OperationLicenceDetailExtend();
                detailExtend.setLicence_id(licenceId);
                detailExtend.setOperation_item_id(licenceItemId);
                detailExtend.setWaste_type(waste_type);
                detailExtend.setWaste_id(waste_Id);
                detailExtend.setWaste_name_id("EEEEEEEEEEE111" + i);
                detailExtend.setWaste_name("测试危废" + i);
            }
            returnResult.add(detailExtend);
        }
        
        detailExtend = new OperationLicenceDetailExtend();
        detailExtend.setLicence_id(licenceId);
        detailExtend.setOperation_item_id(licenceItemId);
        detailExtend.setWaste_type(waste_type);
        detailExtend.setWaste_id(waste_Id);
        detailExtend.setWaste_name_id("EEEEEEEEEEE1119");
        detailExtend.setWaste_name("测试危废9");
        
        returnResult.add(detailExtend);
        return returnResult;
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#saveDispositionItemAndDetail(java.util.List, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testSaveDispositionItemAndDetail() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        String licenceId = "AAAAAAAA1111";
        String licenceItemId = "BBBBBBBBBB1111";
        String waste_type = "CCCCCCCCCC1111";
        String waste_Id = "DDDDDDDDDD1111";
        
        OperationLicenceDetailExtend detailExtend = null;
        List<OperationLicenceDetail> lienceDetails = new ArrayList<OperationLicenceDetail>();
        for (int i=0;i<3;i++) {
            detailExtend = new OperationLicenceDetailExtend();
            detailExtend.setLicence_id(licenceId);
            detailExtend.setOperation_item_id(licenceItemId);
            detailExtend.setWaste_type(waste_type);
            detailExtend.setWaste_id(waste_Id);
            detailExtend.setWaste_name_id("EEEEEEEEEEE111" + (i+5));
            
            lienceDetails.add(detailExtend);
        }
        licenceDetailService.saveLicenceDetail(lienceDetails, ticketId);
      //-------------------------数据准备结束-----------------------------------------
        ticketId = userLoginService.userLogin("10000000000", "test125");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        List<OperationLicenceDetailExtend> detailExtendResult = null;
        licenceService.saveDispositionItemAndDetail(detailExtendResult, ticketId);
        licenceService.saveDispositionItemAndDetail(new ArrayList<OperationLicenceDetailExtend>(), ticketId);
        
        detailExtendResult = doLicenceDetailDataReady();
        licenceService.saveDispositionItemAndDetail(detailExtendResult, ticketId);
        
        OperationLicenceDetail query = new OperationLicenceDetail();
        query.setLicence_id(licenceId);
        query.setOperation_item_id(licenceItemId);
        query.setWaste_type(waste_type);
        query.setWaste_id(waste_Id);
        query.setCreate_by(user.getUserId());
        List<OperationLicenceDetail> details = licenceDetailDao.query(query);
        assertTrue(details.size() == 7);
        
        for (OperationLicenceDetail detail : details) {
            boolean assertResult = ("EEEEEEEEEEE1112").equals(detail.getWaste_name_id()) || ("EEEEEEEEEEE1113").equals(detail.getWaste_name_id()) ||
                    ("EEEEEEEEEEE1114").equals(detail.getWaste_name_id()) || ("EEEEEEEEEEE1118").equals(detail.getWaste_name_id()) || 
                    ("EEEEEEEEEEE1119").equals(detail.getWaste_name_id()) || StringUtils.isBlank(detail.getWaste_name_id());
            if (!assertResult) {
                List<WasteName> wasteNames = wasteNameService.getWasteNamesByNameAndWasteid(waste_Id, "测试危废1", "1");
                assertTrue(wasteNames.size() == 1);
                assertTrue(detail.getWaste_name_id().equals(wasteNames.get(0).getId()));
            }
        }
        
      //-------------------------删除操作-----------------------------------------
        wasteNameService.removeWasteNamesByWasteId(waste_Id);
        
        query.setLicence_id(licenceId);
        query.setOperation_item_id(licenceItemId);
        query.setWaste_type(waste_type);
        query.setWaste_id(waste_Id);
        query.setCreate_by(null);
        details = licenceDetailDao.query(query);
        for (OperationLicenceDetail detail : details) {
            licenceDetailDao.delete(detail.getId());
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getDispositionItems(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetDispositionItems() throws Exception {
        CodeValue disposition_type_c1 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C1);
        CodeValue disposition_type_c2 = codeValueService.getCodeValueByCode(CodeTypeConstant.DISPOSE_TYPE, CodeTypeConstant.DISPOSE_TYPE_C2);
        String licenceId = newAddLicenceAndSumit();
      //-------------------------数据准备结束-----------------------------------------
        
        List<OperationLicenceItemVo> licenceItems = licenceService.getDispositionItems(licenceId);
        assertTrue(licenceItems.size() == 2);
        for (OperationLicenceItemVo itemVo : licenceItems) {
            assertTrue(disposition_type_c1.getId().equals(itemVo.getDispositionTypeId()) || disposition_type_c2.getId().equals(itemVo.getDispositionTypeId()));
        }
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licenceId);
        
        OperationLicenceItem queryItem = new OperationLicenceItem();
        queryItem.setLicence_id(licenceId);
        List<OperationLicenceItem> listItems = licenceItemDao.query(queryItem);
        for (OperationLicenceItem item : listItems){
            licenceDetailDao.delete(item.getId());
        }
        
        OperationLicenceDetail queryDetail = new OperationLicenceDetail();
        queryDetail.setLicence_id(licenceId);
        List<OperationLicenceDetail> listDetails = licenceDetailDao.query(queryDetail);
        for (OperationLicenceDetail detail : listDetails){
            licenceDetailDao.delete(detail.getId());
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getDispositionDetails(java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetDispositionDetails() throws Exception {
        String licenceId = newAddLicenceAndSumit();
      //-------------------------数据准备结束-----------------------------------------
        List<OperationLicenceDetailVo> licenceDetais = null;
        OperationLicenceItem queryItem = new OperationLicenceItem();
        queryItem.setLicence_id(licenceId);
        List<OperationLicenceItem> listItems = licenceItemDao.query(queryItem);
        for (OperationLicenceItem item : listItems){
            licenceDetais = licenceService.getDispositionDetails(licenceId,item.getId());
            assertTrue(licenceDetais.size() == 6);
            for (OperationLicenceDetailVo detailVo : licenceDetais){
                if (StringUtils.isNotBlank(detailVo.getWasteNameId())) {
                    assertTrue(wasteNameService.getWasteNameById(detailVo.getWasteNameId()).getName().equals(detailVo.getWasteName()));
                }
            }
        }
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licenceId);
        
        queryItem = new OperationLicenceItem();
        queryItem.setLicence_id(licenceId);
        listItems = licenceItemDao.query(queryItem);
        for (OperationLicenceItem item : listItems){
            licenceDetailDao.delete(item.getId());
        }
        
        OperationLicenceDetail queryDetail = new OperationLicenceDetail();
        queryDetail.setLicence_id(licenceId);
        List<OperationLicenceDetail> listDetails = licenceDetailDao.query(queryDetail);
        for (OperationLicenceDetail detail : listDetails){
            licenceDetailDao.delete(detail.getId());
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#removeDetail(java.lang.String, java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testRemoveDetail() throws Exception {
        String licenceId = newAddLicenceAndSumit();
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        
        List<String> licenceIds = null;
        String auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licenceId);
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicenceDetail queryDetail = new OperationLicenceDetail();
        queryDetail.setLicence_id(licenceId);
        List<OperationLicenceDetail> listDetails = licenceDetailDao.query(queryDetail);
        assertTrue(listDetails.size() == 12);
      //-------------------------数据准备结束-----------------------------------------
        licenceService.removeDetail(licenceId, listDetails.get(0).getId(), ticketId);
        listDetails = licenceDetailDao.query(queryDetail);
        assertTrue(listDetails.size() == 11);
        
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE).getId();
        OperationLicence licence = licenceDao.get(licenceId);
        assertTrue(auditStatus.equals(licence.getAudit_status()));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licenceId);
        
        OperationLicenceItem queryItem = new OperationLicenceItem();
        queryItem.setLicence_id(licenceId);
        List<OperationLicenceItem> listItems = licenceItemDao.query(queryItem);
        for (OperationLicenceItem item : listItems){
            licenceDetailDao.delete(item.getId());
        }
        
        queryDetail = new OperationLicenceDetail();
        queryDetail.setLicence_id(licenceId);
        listDetails = licenceDetailDao.query(queryDetail);
        for (OperationLicenceDetail detail : listDetails){
            licenceDetailDao.delete(detail.getId());
        }
    }
    
    private List<OperationLicenceDetail> doLicenceDetailDataReady(String licenceId) throws Exception {
        List<OperationLicenceDetail> returnResult = new ArrayList<OperationLicenceDetail>();
        OperationLicenceDetail detail = null;
        String licenceItemId = "BBBBBBBBBB1111";
        String waste_type = "CCCCCCCCCC1111";
        String waste_Id = "DDDDDDDDDD1111";
        for (int i=0;i<1;i++) {
            detail = new OperationLicenceDetailExtend();
            detail.setLicence_id(licenceId);
            detail.setOperation_item_id(licenceItemId);
            detail.setWaste_type(waste_type);
            detail.setWaste_id(waste_Id);
            detail.setWaste_name_id("");
            
            returnResult.add(detail);
        }
        return returnResult;
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#updateWasteName(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testUpdateWasteName() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        List<String> licenceIds = null;
        String auditStatus = null;
        
        OperationLicence licence3 = doLicenceDataReady(ticketId);
        licence3.setLicence_no("jtest-1000003");
        licence3.setStart_date("2017-01-02 00:00:00");
        licence3.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence3, Util.uuid32());//待审核的许可证
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence3.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        String waste_Id = "DDDDDDDDDD1111";
        String waste_name_Id = "EEEEEEEEEE1111";
        String waste_name = "junit-测试危废1111";
        List<OperationLicenceDetail> licenceDetails = doLicenceDetailDataReady(licence3.getId());
        licenceDetailService.saveLicenceDetail(licenceDetails, ticketId);
      //-------------------------数据准备结束-----------------------------------------
        OperationLicenceDetail queryDetail = new OperationLicenceDetail();
        queryDetail.setLicence_id(licence3.getId());
        licenceDetails = licenceDetailDao.query(queryDetail);
        assertTrue(licenceDetails.size() == 1);
        
        licenceService.updateWasteName(licence3.getId(), licenceDetails.get(0).getId(), waste_Id, waste_name_Id, waste_name, ticketId);
        
        licenceDetails = licenceDetailDao.query(queryDetail);
        assertTrue(licenceDetails.size() == 1);
        assertTrue(waste_name_Id.equals(licenceDetails.get(0).getWaste_name_id()));
        
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE).getId();
        OperationLicence licence = licenceDao.get(licence3.getId());
        assertTrue(auditStatus.equals(licence.getAudit_status()));
        
        licenceService.updateWasteName(licence3.getId(), licenceDetails.get(0).getId(), waste_Id, "", waste_name, ticketId);
        licenceDetails = licenceDetailDao.query(queryDetail);
        assertTrue(licenceDetails.size() == 1);
        assertTrue(!waste_name_Id.equals(licenceDetails.get(0).getWaste_name_id()));
        assertTrue(waste_name.equals(wasteNameService.getWasteNameById(licenceDetails.get(0).getWaste_name_id()).getName()));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence3.getId());
        
        OperationLicenceItem queryItem = new OperationLicenceItem();
        queryItem.setLicence_id(licence3.getId());
        List<OperationLicenceItem> listItems = licenceItemDao.query(queryItem);
        for (OperationLicenceItem item : listItems){
            licenceDetailDao.delete(item.getId());
        }
        
        licenceDetails = licenceDetailDao.query(queryDetail);
        for (OperationLicenceDetail detail : licenceDetails){
            licenceDetailDao.delete(detail.getId());
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getlicenceVoById(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetlicenceVoById() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        
        OperationLicence licence3 = doLicenceDataReady(ticketId);
        licence3.setLicence_no("jtest-1000003");
        licence3.setStart_date("2017-01-02 00:00:00");
        licence3.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence3, Util.uuid32());//待审核的许可证
        
      //-------------------------数据准备结束-----------------------------------------
        OperationLicenceVo licenceVo = licenceService.getlicenceVoById(licence3.getId());
        assertTrue(licenceVo != null);
        
        licence3 = licenceDao.get(licence3.getId());
        licence3.setValid(Constant.IS_NOT_VALID);
        licenceDao.update(licence3);
        
        licenceVo = licenceService.getlicenceVoById(licence3.getId());
        assertTrue(licenceVo == null);
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence3.getId());
        
        OperationLicenceItem queryItem = new OperationLicenceItem();
        queryItem.setLicence_id(licence3.getId());
        List<OperationLicenceItem> listItems = licenceItemDao.query(queryItem);
        for (OperationLicenceItem item : listItems){
            licenceDetailDao.delete(item.getId());
        }
        
        OperationLicenceDetail queryDetail = new OperationLicenceDetail();
        queryDetail.setLicence_id(licence3.getId());
        List<OperationLicenceDetail> licenceDetails = licenceDetailDao.query(queryDetail);
        for (OperationLicenceDetail detail : licenceDetails){
            licenceDetailDao.delete(detail.getId());
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#saveLicenceBaseInfo(com.mlsc.waste.licence.model.OperationLicence, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testSaveLicenceBaseInfo() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        RPCSysEnterpriseBase enterpriseBase = platformSupporter.getOrgComServiceManager().queryEnterprise(ticketId, user.getEnterpriseId());
        // 许可证审核状态audit_status
        CodeValue audit_status = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE);
        // 许可证有效状态licence_status
        CodeValue licenceStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_VALID, CodeTypeConstant.LIC_VALID_INVALID);
        
        CodeValue operation_mode = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_MODE, CodeTypeConstant.LIC_MODE_COLLECTION);
        List<RPCSysOrgCom> licence_orglistComs = sysOrgComService.querySysOrgComListByCantonID(ticketId, Constant.COM_TYPE_EPA, "340823");
        
        OperationLicence licence = new OperationLicence();
        licence.setEnterprise_id(user.getEnterpriseId());
        licence.setLicence_no("jtest-1000001");
        licence.setLicence_org(licence_orglistComs.get(0).getComID());
        licence.setLicence_date(Util.datetimeToString(new Date()));
        licence.setStart_date("2017-01-01");
        licence.setEnd_date("2018-01-01");
        licence.setOperation_mode(operation_mode.getId());
      //-------------------------数据准备结束-----------------------------------------
        String licenceId = licenceService.saveLicenceBaseInfo(licence, ticketId);
        OperationLicence operationLicence = licenceDao.get(licenceId);
        assertTrue(operationLicence != null);
        assertTrue(user.getEnterpriseId().equals(operationLicence.getEnterprise_id()));
        assertTrue(user.getEnterpriseName().equals(operationLicence.getEnterprise_name()));
        assertTrue("jtest-1000001".equals(operationLicence.getLicence_no()));
        assertTrue(licence_orglistComs.get(0).getComID().equals(operationLicence.getLicence_org()));
        assertTrue(licence.getLicence_date().subSequence(0, 19).equals(operationLicence.getLicence_date().subSequence(0, 19)));
        assertTrue(StringUtils.isBlank(operationLicence.getInitiallic_date()));
        assertTrue(enterpriseBase.getLegalName().equals(operationLicence.getCorporate()));
        assertTrue(enterpriseBase.getEntAddress().equals(operationLicence.getRegister_addr()));
        assertTrue(enterpriseBase.getEntAddress().equals(operationLicence.getMachine_addr()));
        assertTrue("2017-01-01 00:00:00".equals(operationLicence.getStart_date().subSequence(0, 19)));
        assertTrue("2018-01-01 23:59:59".equals(operationLicence.getEnd_date().subSequence(0, 19)));
        assertTrue(licenceStatus.getId().equals(operationLicence.getLicence_status()));
        assertTrue(operation_mode.getId().equals(operationLicence.getOperation_mode()));
        assertTrue(StringUtils.isBlank(operationLicence.getApplication_time()));
        assertTrue(audit_status.getId().equals(operationLicence.getAudit_status()));
        assertTrue(StringUtils.isBlank(operationLicence.getApproved_by()));
      //-------------------------测试UpdateLicenceBaseInfo方法-----------------------------------------
        
        List<String> licenceIds = null;
        String auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(operationLicence.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        operation_mode = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_MODE, CodeTypeConstant.LIC_MODE_INCLUDEALL);
        
        OperationLicence oldOperationLicence = new OperationLicence();
        oldOperationLicence.setId(operationLicence.getId());
        oldOperationLicence.setLicence_no("jtest-1000002");// 许可证编号*
        oldOperationLicence.setLicence_org("jtest-org-340823");// 发证机关*
        oldOperationLicence.setLicence_date("2017-01-03");// 发证日期*
        oldOperationLicence.setStart_date("2017-01-04");// 许可证有效日期*开始
        oldOperationLicence.setEnd_date("2018-01-03");// 许可证有效日期*结束
        oldOperationLicence.setOperation_mode(operation_mode.getId());// 核准经营方式 *
        licenceService.updateLicenceBaseInfo(oldOperationLicence, ticketId);
        
        operationLicence = licenceDao.get(licenceId);
        assertTrue(operationLicence != null);
        assertTrue(user.getEnterpriseId().equals(operationLicence.getEnterprise_id()));
        assertTrue(user.getEnterpriseName().equals(operationLicence.getEnterprise_name()));
        assertTrue("jtest-1000002".equals(operationLicence.getLicence_no()));
        assertTrue("jtest-org-340823".equals(operationLicence.getLicence_org()));
        assertTrue("2017-01-03 00:00:00".equals(operationLicence.getLicence_date().subSequence(0, 19)));
        assertTrue(StringUtils.isBlank(operationLicence.getInitiallic_date()));
        assertTrue(enterpriseBase.getLegalName().equals(operationLicence.getCorporate()));
        assertTrue(enterpriseBase.getEntAddress().equals(operationLicence.getRegister_addr()));
        assertTrue(enterpriseBase.getEntAddress().equals(operationLicence.getMachine_addr()));
        assertTrue("2017-01-04 00:00:00".equals(operationLicence.getStart_date().subSequence(0, 19)));
        assertTrue("2018-01-03 23:59:59".equals(operationLicence.getEnd_date().subSequence(0, 19)));
        assertTrue(licenceStatus.getId().equals(operationLicence.getLicence_status()));
        assertTrue(operation_mode.getId().equals(operationLicence.getOperation_mode()));
        assertTrue(StringUtils.isBlank(operationLicence.getApplication_time()));
        assertTrue(audit_status.getId().equals(operationLicence.getAudit_status()));
        assertTrue(user.getUserId().equals(operationLicence.getApproved_by()));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licenceId);
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#getUsedLicId(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetUsedLicId() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        assertFalse(licenceService.getUsedLicId(user.getEnterpriseId()));
        
        OperationLicence licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2017-01-02 00:00:00");
        licence1.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence1, Util.uuid32());//企业创建的许可证
        assertTrue(licenceService.getUsedLicId(user.getEnterpriseId()));
        
        String auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId();
        List<String> licenceIds = new ArrayList<String>();
        licenceIds.add(licence1.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        assertTrue(licenceService.getUsedLicId(user.getEnterpriseId()));
        
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence1.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        assertTrue(licenceService.getUsedLicId(user.getEnterpriseId()));
        
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence1.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        assertFalse(licenceService.getUsedLicId(user.getEnterpriseId()));
        
        licenceDao.delete(licence1.getId());
        
        licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2018-01-02 00:00:00");
        licence1.setEnd_date("2019-01-01 23:59:59");
        licenceDao.save(licence1, Util.uuid32());
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence1.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        assertTrue(licenceService.getUsedLicId(user.getEnterpriseId()));
        
        licenceDao.delete(licence1.getId());
        licence1 = doLicenceDataReady(ticketId);
        licence1.setLicence_no("jtest-1000001");
        licence1.setStart_date("2016-01-02 00:00:00");
        licence1.setEnd_date("2017-01-01 23:59:59");
        licenceDao.save(licence1, Util.uuid32());
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence1.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        assertTrue(licenceService.getUsedLicId(user.getEnterpriseId()));
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceServiceImpl#isHasDetails(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testIsHasDetails() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        String licenceId = "AAAAAAAAA111";
        assertFalse(licenceService.isHasDetails(licenceId));
        
        List<OperationLicenceDetail> licenceDetails = doLicenceDetailDataReady(licenceId);
        licenceDetailService.saveLicenceDetail(licenceDetails, ticketId);
        
        assertTrue(licenceService.isHasDetails(licenceId));
      //-------------------------删除操作-----------------------------------------
        OperationLicenceDetail queryDetail = new OperationLicenceDetail();
        queryDetail.setLicence_id(licenceId);
        List<OperationLicenceDetail> deletelicenceDetails = licenceDetailDao.query(queryDetail);
        for (OperationLicenceDetail detail : deletelicenceDetails){
            licenceDetailDao.delete(detail.getId());
        }
    }
    
}
