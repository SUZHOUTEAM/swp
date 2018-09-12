/**
 *
 */
package com.mlsc.waste.licence.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.mobileservice.model.DispositionCapacityDetailReleaseVo;
import com.mlsc.rpc.thrift.api.dto.RPCSysCanton;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fw.service.SysOrgComService;
import com.mlsc.waste.licence.dao.LicenceDao;
import com.mlsc.waste.licence.dao.LicenceDetailDao;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceDetail;
import com.mlsc.waste.licence.model.OperationLicenceDetailExtend;
import com.mlsc.waste.licence.model.OperationLicenceDetailVo;
import com.mlsc.waste.licence.model.OperationLicenceItem;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
import com.mlsc.waste.licence.model.OperationLicenceVo;
import com.mlsc.waste.licence.service.LicenceApprovedService;
import com.mlsc.waste.licence.service.LicenceDetailService;
import com.mlsc.waste.licence.service.LicenceItemService;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteName;
import com.mlsc.waste.wastedirectory.model.WasteType;
import com.mlsc.waste.wastedirectory.service.WasteNameService;
import com.mlsc.waste.wastedirectory.service.WasteService;
import com.mlsc.waste.wastedirectory.service.WasteTypeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 */
@Service("LicenceService")
public class LicenceServiceImpl implements LicenceService {

    private final static Logger logger = LoggerFactory.getLogger(LicenceServiceImpl.class);

    @Autowired
    private LicenceDao licenceDao;

    @Autowired
    private LicenceDetailService licenceDetailService;// 操作许可证datail表

    @Autowired
    private LicenceItemService licenceItemService;// 操作许可证item表

    @Autowired
    private WasteService wasteService;// 操做危废waste表

    @Autowired
    private WasteNameService wasteNameService;// 操做危废waste表

    @Autowired
    private ICodeValueService codeValueService;// 操作数据字典，根据处置方式ID查询处置方式

    @Autowired
    private LicenceApprovedService licenceApprovedService;// 操作许可证版本信息相关

    @Autowired
    private WasteTypeService wasteTypeService;// 操作二位码

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private SysOrgComService sysOrgComService;

    @Autowired
    private PlatformSupporter platformSupporter; // 拿到平台表的client

    @Autowired
    private LicenceDetailDao licenceDetailDao;

    @Override
    public List<OperationLicenceVo> list(String sql, Map<String, Object> params, PagingParameter paging, String enterpriseId) throws Exception {
        return licenceDao.list(sql, params, paging, enterpriseId);
    }

    @Override
    public Integer count(String where, Map<String, Object> params, String enterpriseId) throws Exception {
        return licenceDao.count(where, params, enterpriseId);
    }

    @Override
    public void submitAudit(String licenceId, String ticketId) throws Exception {
        String auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_SUBMIT).getId();
        List<String> licenceIds = new ArrayList<String>();
        licenceIds.add(licenceId);
        updateAuditStatus(licenceIds, auditStatus, ticketId);
    }

    @Override
    public void deleteLicenceByLicenceIds(List<String> licenceIds, String ticketId) {
        if (licenceIds == null || licenceIds.isEmpty()) {
            return;
        }
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("valid", Constant.IS_NOT_VALID);
            paraMap.put("licenceIds", licenceIds);
            paraMap.put("editBy", user.getUserId());
            paraMap.put("editTime", Util.datetimeToString(new Date()));
            licenceDao.deleteLicenceByLicenceIds(licenceIds, paraMap);
        } catch (Exception e) {
            logger.error("批量删除operation_licence时异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public OperationLicence getValidLicIdByEnterpriseId(String enterpriseId) {
        List<OperationLicence> operationLicenceList;
        // 获得有效许可证ID
        try {
            operationLicenceList = licenceDao.getValidLicIdByEnterpriseId(enterpriseId);
            if (operationLicenceList != null && operationLicenceList.size() > 0) {
                return operationLicenceList.get(0);
            }
        } catch (Exception e) {
            logger.error("获取企业唯一许可证时异常", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<OperationLicence> listLicenceByEnterId(String enterpriseId) {
        List<OperationLicence> operationLicenceList;
        // 获得有效许可证ID
        try {
            operationLicenceList = licenceDao.getValidLicIdByEnterpriseId(enterpriseId);
        } catch (Exception e) {
            logger.error("查询许可证列表时异常", e);
            throw new RuntimeException(e);
        }

        return operationLicenceList;
    }

    ;


    @Override
    public String getAuditStatusByLicenceId(String id) throws Exception {
        return licenceDao.getAuditStatusByLicenceId(id);
    }

    @Override
    public boolean isLicenceNoExist(String licenceNo, String licenceId) throws Exception {
        int count = licenceDao.queryLicenceNoIsExist(licenceNo, licenceId);
        return count > 0;
    }

    @Override
    public boolean isValidityPeriodRepeat(String entId, String licenceId, String startDate, String endDate) throws Exception {
        startDate = startDate + " 00:00:00";// 许可证有效日期*开始
        endDate = endDate + " 23:59:59";// 许可证有效日期*结束
        int count = licenceDao.isValidityPeriodRepeat(entId, licenceId, startDate, endDate);
        return count > 0;
    }

    @Override
    public OperationLicence getLicneceById(String licenceId) {
        try {
            return licenceDao.get(licenceId);
        } catch (Exception e) {
            logger.error("根据许可证ID查询许可证时异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDispositionType(String itemId, String licenceId, String ticketId) throws Exception {
        licenceItemService.removeItemById(itemId);
        licenceDetailService.removeDetails(licenceId, itemId);
        updateAuditStatus(licenceId, ticketId);
    }

    @Override
    public OperationLicenceVo getlicenceApprovedById(String licenceId) throws Exception {
        return licenceApprovedService.getOperationLicenceVoById(licenceId);
    }

    @Override
    public List<OperationLicenceItemVo> getWasteInfoByLicenceId(String licenceId) throws Exception {
        List<OperationLicenceItemVo> listVo = null;
        listVo = licenceApprovedService.getWasteInfoByLicenceId(licenceId);
        return listVo;
    }

    @Override
    public List<WasteType> getAllWateType() throws Exception {
        return wasteTypeService.getAllWateType();
    }

    @Override
    public List<Waste> getWasteByWasteTypeId(String wasteTypeId) throws Exception {
        return wasteService.getWastesByWasteTypeId(wasteTypeId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteLicenceByIds(List<String> licenceIds, String ticketId) throws Exception {
        boolean deleteFlg = true; // 是否可以删除标注
        for (String licenceid : licenceIds) {
            String auditStatus = getAuditStatusByLicenceId(licenceid);
            if (!CodeTypeConstant.LIC_AUDIT_CREATE.equals(auditStatus) && !CodeTypeConstant.LIC_AUDIT_REFUSED.equals(auditStatus)) {
                deleteFlg = false;
                break;
            }
        }

        if (deleteFlg) {
            deleteLicenceByLicenceIds(licenceIds, ticketId);
            licenceItemService.deleteLicenceItem(licenceIds, ticketId);
            licenceDetailService.deleteLicenceDetailByLicenceIds(licenceIds, ticketId);
        }
        return deleteFlg;
    }

    @Override
    public List<RPCSysOrgCom> getOrgByEnterpriseId(String ticketId, String enterpriseId) throws Exception {
        List<RPCSysOrgCom> rpcSysOrgComs = null;
        HashSet<RPCSysOrgCom> rpcSysOrgComset = new HashSet<RPCSysOrgCom>();
        Iface orgComServiceManager = platformSupporter.getOrgComServiceManager();
        RPCSysEnterpriseBase enterpriseInfoById = enterpriseService.getEnterpriseInfoById(ticketId, enterpriseId);
        String districtCantonCode = enterpriseInfoById.getCantonCode();// 拿到企业行政区代码

        RPCSysCanton districtCanton = orgComServiceManager.querySysCantonByCantonCode(ticketId, districtCantonCode);
        String cityCantonCode = districtCanton.getParentCantonCode();// 行政区编码
        // (市的编码)

        RPCSysCanton cityCanton = orgComServiceManager.querySysCantonByCantonCode(ticketId, cityCantonCode);
        String provinceCantonCode = cityCanton.getParentCantonCode();// 拿到父行政区的编码
        // (省的编码)

        // 根据省行政代码获取省级环保局
        rpcSysOrgComset.addAll(sysOrgComService.querySysOrgComListByCantonID(ticketId, Constant.COM_TYPE_EPA, provinceCantonCode));
        // 根据市行政代码获取市级环保局
        rpcSysOrgComset.addAll(sysOrgComService.querySysOrgComListByCantonID(ticketId, Constant.COM_TYPE_EPA, cityCantonCode));
        // 根据市行政代码获取所有区县的组织机构代码
        rpcSysOrgComset.addAll(sysOrgComService.querySysOrgComListByParentCantonID(ticketId, Constant.COM_TYPE_EPA, cityCantonCode));

        // 过滤掉非环保局的组织机构
        Iterator<RPCSysOrgCom> it = rpcSysOrgComset.iterator();
        RPCSysOrgCom rpcSysOrgCom = null;
        while (it.hasNext()) {
            rpcSysOrgCom = it.next();
            if (Constant.COM_TYPE_EPA != rpcSysOrgCom.getComType()) {// 暂定环保局类型是2
                it.remove();// 移除当前的对象
            }
        }
        rpcSysOrgComs = new ArrayList<RPCSysOrgCom>(rpcSysOrgComset);
        return rpcSysOrgComs;
    }

    @Override
    public boolean isEnterpriseVaild(String ticketId) {
        boolean result = true;
        try {
            result = enterpriseService.isEnterpriseVaild(ticketId);
        } catch (Exception e) {
            logger.error("验证企业信息是否完整时异常", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public String isHasDispositionItem(String licenceId, String dispositionTypeId) throws Exception {
        return licenceItemService.isHasDispositionItem(licenceId, dispositionTypeId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveOrUpdateDispositionItem(OperationLicenceItem licenceItem, String ticketId) throws Exception {
        String itemId = licenceItem.getId();
        if (StringUtils.isBlank(licenceItem.getId())) {
            licenceItem.setExcuted_quantity("0");
            itemId = licenceItemService.saveLicenceItem(licenceItem, ticketId);
        } else {
            licenceItemService.updateLicenceItem(licenceItem, ticketId);
        }
        updateAuditStatus(licenceItem.getLicence_id(), ticketId);
        return itemId;
    }

    @Override
    public Map<String, Object> getWasteNamesByWasteId(String wasteId, String keyword) throws Exception {
        Map<String, Object> datamap = new HashMap<String, Object>();
        List<WasteName> wasteNameList = wasteNameService.getWasteNamesByNameAndWasteid(wasteId, keyword, null);
        JSONArray jsonWfArray = new JSONArray();
        JSONObject wfObj = null;
        for (WasteName wasteName : wasteNameList) {
            wfObj = new JSONObject();
            wfObj.put("wasteNameId", wasteName.getId());
            wfObj.put("wasteName", wasteName.getName());
            jsonWfArray.add(wfObj);
        }
        datamap.put("value", jsonWfArray);
        datamap.put("redirect", "");
        datamap.put("message", "");
        datamap.put("code", 200);
        return datamap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveDispositionItemAndDetail(List<OperationLicenceDetailExtend> licenceDetailExList, String ticketId) throws Exception {
        if (licenceDetailExList == null || licenceDetailExList.size() == 0) {
            return;
        }
        List<OperationLicenceDetail> licenceDetailList = new ArrayList<OperationLicenceDetail>();
        for (OperationLicenceDetailExtend detailExtend : licenceDetailExList) {
            if (StringUtils.isBlank(detailExtend.getWaste_name_id()) && StringUtils.isNotBlank(detailExtend.getWaste_name())) {
                detailExtend.setWaste_name_id(wasteNameService.saveOrUpdateWasteName(detailExtend.getWaste_id(), detailExtend.getWaste_name(), ticketId));
            }

            if (cleanRepeatDetail(licenceDetailList, detailExtend) && !licenceDetailService.isHasLicenceDetail(detailExtend.getLicence_id(), detailExtend.getOperation_item_id(), detailExtend.getWaste_type(), detailExtend.getWaste_id(), detailExtend.getWaste_name_id())) {
                licenceDetailList.add(detailExtend);
            }
        }

        licenceDetailService.saveLicenceDetail(licenceDetailList, ticketId);
        if (licenceDetailList.size() > 0) {
            updateAuditStatus(licenceDetailList.get(0).getLicence_id(), ticketId);
        }
    }

    // 去掉重复的危废明细
    private boolean cleanRepeatDetail(List<OperationLicenceDetail> licenceDetailList, OperationLicenceDetailExtend detailExtend) throws Exception {
        boolean result = true;// 没有重复的
        for (OperationLicenceDetail detail : licenceDetailList) {
            if (detail.getWaste_type().equals(detailExtend.getWaste_type()) && detail.getWaste_id().equals(detailExtend.getWaste_id())) {
                if ((StringUtils.isBlank(detail.getWaste_name_id()) && StringUtils.isBlank(detailExtend.getWaste_name_id())) || detail.getWaste_name_id().equals(detailExtend.getWaste_name_id())) {
                    result = false;
                }
            }
        }
        return result;
    }

    @Override
    public List<OperationLicenceItemVo> getDispositionItems(String licenceId) throws Exception {
        return licenceApprovedService.getItemByLicenceId(licenceId);
    }

    @Override
    public List<OperationLicenceDetailVo> getDispositionDetails(String licenceId, String licenceItemId) throws Exception {
        return licenceApprovedService.getWasteCodeByLicenceId(licenceId, licenceItemId);
    }

    @Override
    public void removeDetail(String licenceId, String detailId, String ticketId) throws Exception {
        licenceDetailService.removeDetail(detailId);
        updateAuditStatus(licenceId, ticketId);
    }

    @Override
    public String updateWasteName(String licenceId, String detailId, String wasteId, String wasteNameId, String wasteName, String ticketId) throws Exception {
        String returnWasteNameId = null;
        if (StringUtils.isBlank(wasteNameId) && StringUtils.isNotBlank(wasteName)) {
            returnWasteNameId = wasteNameService.saveOrUpdateWasteName(wasteId, wasteName, ticketId);
        } else {
            returnWasteNameId = wasteNameId;
        }

        licenceDetailService.updateDetailWasteNameId(detailId, returnWasteNameId, ticketId);
        updateAuditStatus(licenceId, ticketId);

        return returnWasteNameId;
    }

    @Override
    public OperationLicenceVo getlicenceVoById(String licenceId) throws DaoAccessException {
        return licenceDao.getlicenceVoById(licenceId);
    }

    @Override
    public String saveLicenceBaseInfo(OperationLicence operationLicence, String ticketId) throws Exception {
        String licenceId = null;
        initLicenceDataAdd(operationLicence, ticketId);
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        operationLicence.setValid(Constant.IS_VALID);
        operationLicence.setCreate_by(user.getUserId());
        operationLicence.setCreate_time(Util.datetimeToString(new Date()));
        operationLicence.setEdit_by(user.getUserId());
        operationLicence.setEdit_time(Util.datetimeToString(new Date()));
        licenceId = Util.uuid32();
        licenceDao.save(operationLicence, licenceId);
        return licenceId;
    }

    @Override
    public void updateLicenceBaseInfo(OperationLicence operationLicence, String ticketId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        OperationLicence newLicence = getLicneceById(operationLicence.getId());
        initLicenceDataUpdate(newLicence, operationLicence);
        newLicence.setEdit_by(user.getUserId());
        newLicence.setEdit_time(Util.datetimeToString(new Date()));
        licenceDao.update(newLicence);
    }

    private void initLicenceDataAdd(OperationLicence newLicence, String ticketId) throws Exception {
        RPCSysEnterpriseBase enterpriseBase = platformSupporter.getOrgComServiceManager().queryEnterprise(ticketId, newLicence.getEnterprise_id());
        // 许可证审核状态audit_status
        CodeValue auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE);
        // 许可证有效状态licence_status
        CodeValue licenceStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_VALID, CodeTypeConstant.LIC_VALID_INVALID);
        if (enterpriseBase != null && StringUtils.isNotBlank(enterpriseBase.getEntId())) {
            newLicence.setEnterpriseName(enterpriseBase.getEntName());// 企业名称
            newLicence.setInitiallic_date(null);// 初次发证日期
            newLicence.setCorporate(enterpriseBase.getLegalName());// 法定代表人
            newLicence.setRegister_addr(enterpriseBase.getEntAddress());// 注册地址
            newLicence.setMachine_addr(enterpriseBase.getEntAddress());// 经营设施地址
            newLicence.setLicence_status(licenceStatus.getId());// 许可证状态
            newLicence.setApplication_time(null);// 申请发证日期
            newLicence.setAudit_status(auditStatus.getId());// 许可证审核状态
            newLicence.setApproved_by(null);// 许可证审核人
        }
        newLicence.setStart_date(newLicence.getStart_date() + " 00:00:00");// 许可证有效日期*开始
        newLicence.setEnd_date(newLicence.getEnd_date() + " 23:59:59");// 许可证有效日期*结束
    }

    private void initLicenceDataUpdate(OperationLicence newLicence, OperationLicence oldLicence) throws Exception {
        // 许可证审核状态audit_status
        CodeValue auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE);
        // 许可证有效状态licence_status
        CodeValue licenceStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_VALID, CodeTypeConstant.LIC_VALID_INVALID);
        newLicence.setLicence_no(oldLicence.getLicence_no());// 许可证编号*
        newLicence.setLicence_org(oldLicence.getLicence_org());// 发证机关*
        newLicence.setLicence_date(oldLicence.getLicence_date());// 发证日期*
        newLicence.setStart_date(oldLicence.getStart_date() + " 00:00:00");// 许可证有效日期*开始
        newLicence.setEnd_date(oldLicence.getEnd_date() + " 23:59:59");// 许可证有效日期*结束
        newLicence.setOperation_mode(oldLicence.getOperation_mode());// 核准经营方式 *
        newLicence.setAudit_status(auditStatus.getId());// 许可证审核状态
        newLicence.setLicence_status(licenceStatus.getId());// 许可证状态
    }

    @Override
    public void updateAuditStatus(List<String> licenceIds, String auditStatus, String ticketId) {
        if (licenceIds == null || licenceIds.isEmpty()) {
            return;
        }

        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("auditStatus", auditStatus);
            paraMap.put("approvedBy", user.getUserId());
            paraMap.put("licenceIds", licenceIds);
            paraMap.put("editBy", user.getUserId());
            paraMap.put("editTime", Util.datetimeToString(new Date()));
            licenceDao.updateAuditStatus(licenceIds, paraMap);
        } catch (Exception e) {
            logger.error("批量更新许可证审核状态时异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getUsedLicId(String enterpriseId) throws Exception {
        int count = licenceDao.getUsedLicId(enterpriseId);
        return count > 0;
    }

    @Override
    public boolean isHasDetails(String licenceId) throws Exception {
        int count = licenceDao.isHasDetails(licenceId);
        return count > 0;
    }

    private void updateAuditStatus(String licenceId, String ticketId) throws Exception {
        // 处置方式保存成功后，更新许可证的审核状态
        CodeValue auditStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.LIC_AUDIT, CodeTypeConstant.LIC_AUDIT_CREATE);
        List<String> licenceIds = new ArrayList<String>();
        licenceIds.add(licenceId);
        updateAuditStatus(licenceIds, auditStatus.getId(), ticketId);
    }

    @Override
    public int getProcessableTotalCount(String licenceId) {
        int count = licenceDao.getProcessableTotalCount(licenceId);
        return count;
    }

    @Override
    public List<DispositionCapacityDetailReleaseVo> dispositionCapacityDetailReleaseVoList(String licenceId, String entId) throws Exception {
        List<DispositionCapacityDetailReleaseVo> canList = new ArrayList<DispositionCapacityDetailReleaseVo>();
        List<DispositionCapacityDetailReleaseVo> noList = new ArrayList<DispositionCapacityDetailReleaseVo>();
        canList = licenceDetailDao.getDispositionCapacityDetailReleaseCanList(licenceId, entId);
        noList = licenceDetailDao.getDispositionCapacityDetailReleaseNoList(licenceId, entId);
        noList.addAll(0, canList);
        return noList;
    }

    @Override
    public List<OperationLicence> listLiceneByEnterpriseIdAndStatus(String enterpriseId, String status) throws Exception {
        List<OperationLicence> submitLicenceList = new ArrayList<OperationLicence>();
        // 获得有效许可证ID
        try {
            submitLicenceList = licenceDao.listLiceneByEnterpriseIdAndStatus(enterpriseId, status);
        } catch (Exception e) {
            logger.error("查询失败", e);
            throw new RuntimeException(e);
        }

        return submitLicenceList;
    }

    @Override
    public int submitCount(String where, Map<String, Object> paramMap, String enterpriseId) throws Exception {
        List<OperationLicenceVo> list = licenceDao.getSubmitList(where, paramMap, null, enterpriseId);
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    @Override
    public List<OperationLicenceVo> getSubmitList(String where, Map<String, Object> paramMap, PagingParameter paging, String enterpriseId) throws Exception {
        List<OperationLicenceVo> list = licenceDao.getSubmitList(where, paramMap, paging, enterpriseId);
        return list;
    }
}
