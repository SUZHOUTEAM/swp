/**
 * 
 */
package com.mlsc.waste.licence.service.impl;

import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.common.util.RSAEncryptUtils;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.dispositioncapacityrelease.dao.DispositionCapacityDetailReleaseDao;
import com.mlsc.waste.dispositioncapacityrelease.dao.DispositionCapacityReleaseDao;
import com.mlsc.waste.fw.service.SysOrgComService;
import com.mlsc.waste.licence.dao.LicenceDao;
import com.mlsc.waste.licence.dao.LicenceDetailDao;
import com.mlsc.waste.licence.dao.LicenceItemDao;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceDetail;
import com.mlsc.waste.licence.model.OperationLicenceItem;
import com.mlsc.waste.licence.model.OperationLicenceVo;
import com.mlsc.waste.licence.service.LicenceApprovedService;
import com.mlsc.waste.licence.service.LicenceDetailService;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.utils.datatable.DataTablesUtils;
import com.mlsc.waste.wastecircle.dao.CoopMsgBusDao;
import com.mlsc.waste.wastecircle.dao.CoopMsgDao;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteName;
import com.mlsc.waste.wastedirectory.model.WasteType;
import com.mlsc.waste.wastedirectory.service.WasteNameService;
import com.mlsc.waste.wastedirectory.service.WasteService;
import com.mlsc.waste.wastedirectory.service.WasteTypeService;
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

import static org.junit.Assert.assertTrue;

/**
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class LicenceApprovedServiceImplTest {
    
    @Autowired
    private LicenceApprovedService licenceApprovedService;
    
    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private LicenceService licenceService;

    @Autowired
    private LicenceDetailService licenceDetailService;
    
    @Autowired
    private ICodeValueService codeValueService;
    
    @Autowired
    private LicenceDao licenceDao;
    
    @Autowired
    private LicenceItemDao licenceItemDao;
    
    @Autowired
    private LicenceDetailDao licenceDetailDao;
    
    @Autowired
    private DispositionCapacityReleaseDao capacityReleaseDao;
    @Autowired
    private DispositionCapacityDetailReleaseDao capacityDetailReleaseDao;
    
    @Autowired
    private CoopMsgDao coopMsgDao;
    
    @Autowired
    private CoopMsgBusDao  coopMsgBusDao;
    
    @Autowired
    private PlatformSupporter platformSupporter;
    
    @Autowired
    private SysOrgComService sysOrgComService;
    
    @Autowired
    private WasteTypeService wasteTypeService;
    
    @Autowired
    private WasteService wasteService;
    
    @Autowired
    private WasteNameService wasteNameService;
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceApprovedServiceImpl#list(java.lang.String, java.util.Map, com.mlsc.epdp.common.base.entity.PagingParameter)}.
     * @throws Exception 
     */
    @Test
    public void testList() throws Exception {
        String where = "";
        Map<String, Object> params = new HashMap<String, Object>(3);
        CodeValue operationMode_includeall = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_MODE, CodeTypeConstant.LIC_MODE_INCLUDEALL);
        CodeValue operationMode_collection = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_MODE, CodeTypeConstant.LIC_MODE_COLLECTION);
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
        licence2.setOperation_mode(operationMode_includeall.getId());
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
        licence3.setOperation_mode(operationMode_includeall.getId());
        licenceDao.save(licence3, Util.uuid32());//待审核的许可证
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence3.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence licence4 = doLicenceDataReady(ticketId);
        licence4.setLicence_no("jtest-1000004");
        licence4.setEnterprise_name("苏州处置有限公司");
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
        
        OperationLicence licence6 = doLicenceDataReady(ticketId);
        licence6.setLicence_no("jtest-1000006");
        licence6.setStart_date("2017-01-02 00:00:00");
        licence6.setEnd_date("2018-01-01 23:59:59");
        licence6.setValid(Constant.IS_NOT_VALID);
        licenceDao.save(licence6, Util.uuid32());//审核通过的许可证(valid=0)
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence6.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
      //-------------------------数据准备结束-----------------------------------------
        List<OperationLicenceVo> testlist = null;
        where = "and licence_no like :licence_no ";
        params = new HashMap<String, Object>(3);
        params.put("licence_no", "%0004%");
        assertTrue(licenceApprovedService.count(where, params) == 0);
        testlist = licenceApprovedService.list(where, params, paging);
        assertTrue(testlist == null || testlist.size() == 0);
        
        params.put("licence_no", "%0003%");
        assertTrue(licenceApprovedService.count(where, params) == 1);
        testlist = licenceApprovedService.list(where, params, paging);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue("jtest-1000003".equals(licenceVo.getLicence_no()));
        }
        
        where = "and enterpriseName like :enterpriseName and licence_no like :licence_no and auditStatus like :auditStatus ";
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED).getId();
        params = new HashMap<String, Object>(3);
        params.put("enterpriseName", "%吴中处置%");
        params.put("auditStatus", "%" + auditStatus + "%");
        params.put("licence_no", "%jtest-%");
        assertTrue(licenceApprovedService.count(where, params) == 1);
        testlist = licenceApprovedService.list(where, params, paging);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue("jtest-1000002".equals(licenceVo.getLicence_no()));
        }
        
        where = "and operationMode like :operationMode and licence_no like :licence_no and auditStatus like :auditStatus ";
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_PASS).getId();
        params = new HashMap<String, Object>(3);
        params.put("operationMode", "%" + operationMode_collection.getId() + "%");
        params.put("auditStatus", "%" + auditStatus + "%");
        params.put("licence_no", "%jtest-%");
        assertTrue(licenceApprovedService.count(where, params) == 2);
        testlist = licenceApprovedService.list(where, params, paging);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue("jtest-1000004".equals(licenceVo.getLicence_no()) || "jtest-1000005".equals(licenceVo.getLicence_no()));
        }
        
        where = "and enterpriseName like :enterpriseName  and licence_no like :licence_no and operationMode like :operationMode  and auditStatus like :auditStatus ";
        params = new HashMap<String, Object>(4);
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED).getId();
        params.put("auditStatus", "%" + auditStatus + "%");
        params.put("operationMode", "%" + operationMode_includeall.getId() + "%");
        params.put("enterpriseName", "%吴中处置%");
        params.put("licence_no", "%jtest-%");
        assertTrue(licenceApprovedService.count(where, params) == 1);
        testlist = licenceApprovedService.list(where, params, paging);
        for (OperationLicenceVo licenceVo : testlist) {
            assertTrue("jtest-1000002".equals(licenceVo.getLicence_no()));
        }
        
        params.put("licence_no", "%0004%");
        assertTrue(licenceApprovedService.count(where, params) == 0);
        testlist = licenceApprovedService.list(where, params, paging);
        assertTrue(testlist == null || testlist.size() == 0);
      //-------------------------删除操作-----------------------------------------
        licenceDao.delete(licence1.getId());
        licenceDao.delete(licence2.getId());
        licenceDao.delete(licence3.getId());
        licenceDao.delete(licence4.getId());
        licenceDao.delete(licence5.getId());
        licenceDao.delete(licence6.getId());
    }
    
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

    private String newAddLicenceAndSumit() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
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
        licenceService.submitAudit(licenceId, ticketId);
        return licenceId;
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceApprovedServiceImpl#confirmPass(java.util.List, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testConfirmPass() throws Exception {
        String ticketId = userLoginService.userLogin("10000000000", RSAEncryptUtils.encrypt("Tanshier888"));

        
        List<String> licenceIds = new ArrayList<String>();
        licenceIds.add("bcc519fa573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc51d73573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc52642573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc52867573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc52966573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc52a4a573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc52b7c573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc52c4d573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc52d19573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc52de2573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc52e9e573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc53051573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5312a573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc531f0573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5329d573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc53352573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc53469573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5352f573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc535ff573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc537d3573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc538f3573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc53c7e573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc53de6573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc53f3e573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc54087573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc54140573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc54334573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc54475573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5453b573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc546c7573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc547af573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc548b4573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc54970573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc54a7d573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc54b25573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc54bd4573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc54c85573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc54d2e573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc55044573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc55229573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc55469573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc556fe573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc565c3573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5691f573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc56b0a573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc56ce3573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc56e2b573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc56f0e573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc57039573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc57111573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5722f573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc57314573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc573c9573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc57476573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc575a0573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc57697573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc577bf573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc57900573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc579c2573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc57a6c573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc57b1a573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc57c15573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc57d65573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc58328573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc58508573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc585bb573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc588d2573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc58996573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc58a48573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc58ba2573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc58cb8573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc58df1573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc58f2c573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc59064573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc59160573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc59226573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc592f7573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc59553573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc598de573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc59b1e573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc59c77573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc59e43573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc59f10573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5a0c8573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5a20e573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5a352573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5a4a2573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5a558573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5a610573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5a6c8573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5a7eb573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5a8b5573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5a96a573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5aa0d573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5ab34573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5aca6573a11e8a24b7cd30ac4d794");
        licenceIds.add("bcc5ae5c573a11e8a24b7cd30ac4d794");

        try{
            licenceApprovedService.confirmPass(licenceIds, ticketId);
        }catch(Exception ex){
            assertTrue(ex instanceof WasteBusinessException);
            assertTrue(((WasteBusinessException) ex).getMessageBean() != null);
        }
        

    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceApprovedServiceImpl#confirmReject(java.util.List, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testConfirmReject() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        String auditStatus = null;
        List<String> licenceIds = null;
        licenceApprovedService.confirmReject(licenceIds, ticketId);
        
        OperationLicence licence3 = doLicenceDataReady(ticketId);
        licence3.setLicence_no("jtest-1000003");
        licence3.setStart_date("2017-01-02 00:00:00");
        licence3.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence3, Util.uuid32());//待审核的许可证
        auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId();
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence3.getId());
        licenceService.updateAuditStatus(licenceIds, auditStatus, ticketId);
        
        OperationLicence operationLicence = licenceDao.get(licence3.getId());
        assertTrue(codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId().equals(operationLicence.getAudit_status()));
        licenceApprovedService.confirmReject(licenceIds, ticketId);
        
        operationLicence = licenceDao.get(licence3.getId());
        assertTrue(codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_REFUSED).getId().equals(operationLicence.getAudit_status()));
        
        licenceDao.delete(licence3.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceApprovedServiceImpl#getCodeValuesTypeCode(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetCodeValuesTypeCode() throws Exception {
        List<CodeValue> licModelList = licenceApprovedService.getCodeValuesTypeCode(CodeTypeConstant.LIC_MODE);
        assertTrue(licModelList.size() == 2);
        for (CodeValue codevalue : licModelList){
            assertTrue(CodeTypeConstant.LIC_MODE_COLLECTION.equals(codevalue.getCode()) || CodeTypeConstant.LIC_MODE_INCLUDEALL.equals(codevalue.getCode()));
        }
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceApprovedServiceImpl#saveDispositionCapacityByLicIds(java.util.List, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testSaveDispositionCapacityByLicIds() throws Exception {
        String ticketId = userLoginService.userLogin("18896580471", "tao199308");
        List<String> licenceIds = null;
        licenceApprovedService.saveDispositionCapacityByLicIds(licenceIds, ticketId);
        
        OperationLicence licence3 = doLicenceDataReady(ticketId);
        licence3.setLicence_no("jtest-1000003");
        licence3.setStart_date("2017-01-02 00:00:00");
        licence3.setEnd_date("2018-01-01 23:59:59");
        licenceDao.save(licence3, Util.uuid32());
        
        licenceIds = new ArrayList<String>();
        licenceIds.add(licence3.getId());
        
        try{
            licenceApprovedService.saveDispositionCapacityByLicIds(licenceIds, ticketId);
        }catch(Exception ex){
            assertTrue(ex instanceof WasteBusinessException);
            assertTrue(((WasteBusinessException) ex).getMessageBean() != null);
            assertTrue("[jtest-1000003]许可证没有详细的许可项，不能发布处置能力。".equals(((WasteBusinessException) ex).getMessageBean().getErrorContent()));
            licenceDao.delete(licence3.getId());
        }
    }
    
}
