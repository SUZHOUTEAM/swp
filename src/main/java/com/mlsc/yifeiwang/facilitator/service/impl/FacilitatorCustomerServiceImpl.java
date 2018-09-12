package com.mlsc.yifeiwang.facilitator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.yifeiwang.facilitator.entity.FacilitatorCustomer;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.model.EnterpriseExtended;
import com.mlsc.waste.enterprise.service.EnterpriseExtendedService;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fw.model.SysCanton;
import com.mlsc.waste.fw.service.SysCantonService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecord;
import com.mlsc.waste.userenterpriseapproverecord.service.UserEnterpriseApproveRecordService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import com.mlsc.yifeiwang.facilitator.mapper.FacilitatorCustomerMapper;
import com.mlsc.yifeiwang.facilitator.model.FacilitatorCustomerModel;
import com.mlsc.yifeiwang.facilitator.model.FacilitatorCustomerParam;
import com.mlsc.yifeiwang.facilitator.service.IFacilitatorCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2018-01-15
 */
@Service
public class FacilitatorCustomerServiceImpl extends ServiceImpl<FacilitatorCustomerMapper, FacilitatorCustomer> implements IFacilitatorCustomerService {
    private final static Logger logger = LoggerFactory.getLogger(FacilitatorCustomerServiceImpl.class);

    @Autowired
    private ISysEnterpriseBaseService sysEnterpriseBaseService;
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private EnterpriseExtendedService enterpriseExtendedService;

    @Autowired
    private UserEnterpriseApproveRecordService userEnterpriseApproveRecordService;

    @Autowired
    private SysCantonService sysCantonService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveFacilitatorCustomer(User user, FacilitatorCustomer facilitatorCutomer) throws Exception {
        try {
            Date date = new Date();
            facilitatorCutomer.setFacilitatorEntId(user.getEnterpriseId());
            facilitatorCutomer.setCreateBy(user.getUserId());
            facilitatorCutomer.setCreateTime(date);
            facilitatorCutomer.setEditBy(user.getUserId());
            facilitatorCutomer.setEditTime(date);
            return this.insert(facilitatorCutomer);
        } catch (Exception e) {
            logger.error("保存服务商客户时异常", e);
            throw e;
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteFacilitatorCustomer(User user, List<String> ids) throws Exception {
        try {
            if (ids != null && ids.size() > 0) {
                Date date = new Date();
                List<FacilitatorCustomer> customers = new ArrayList<FacilitatorCustomer>();
                for (String id : ids) {
                    FacilitatorCustomer facilitatorCutomer = this.baseMapper.selectById(id);
                    facilitatorCutomer.setEditBy(user.getUserId());
                    facilitatorCutomer.setEditTime(date);
                    facilitatorCutomer.setDeleteFlag(1);
                    customers.add(facilitatorCutomer);
                }
                this.updateAllColumnBatchById(customers);
            }
        } catch (Exception e) {
            logger.error("删除服务商客户时异常", e);
            throw e;
        }

        return true;
    }

    @Override
    public List<FacilitatorCustomerModel> listFacilitatorCustomer(FacilitatorCustomerParam customerParam, PagingParameter pagingParameter) throws Exception {
        List<FacilitatorCustomerModel> customerModels = null;
        try {
            int count = this.baseMapper.countEntByFacilitatorEnt(customerParam);
            if (count > 0) {
                Util.initPagingParameter(pagingParameter);
                int startRowIndex = pagingParameter.getStart();
                int rows = pagingParameter.getLimit();
                pagingParameter.setTotalRecord(count);
                customerParam.setStartRowIndex(startRowIndex);
                customerParam.setRows(rows);
                customerModels = this.baseMapper.listEntByFacilitatorEnt(customerParam);
            }
        } catch (Exception e) {
            logger.error("获取服务商客户列表时异常", e);
            throw e;
        }
        return customerModels;
    }

    @Override
    public List<FacilitatorCustomerModel> listBindEnterprise(FacilitatorCustomerParam customerParam) throws Exception {
        List<FacilitatorCustomerModel> sysEnterpriseBases = null;
        try {
            EntityWrapper<SysEnterpriseBase> ew = new EntityWrapper<SysEnterpriseBase>();
            ew.setSqlSelect("responsibleArea");
            ew.eq("entId", customerParam.getFacilitatorEntId());
            SysEnterpriseBase sysEnterpriseBase = sysEnterpriseBaseService.getEnterpriseInfoById(customerParam.getFacilitatorEntId());
            if (sysEnterpriseBase != null) {
                String responsibleArea = Util.calculateCantonCode(sysEnterpriseBase.getResponsibleArea());
                customerParam.setResponsibleArea(responsibleArea);
                sysEnterpriseBases = this.baseMapper.listFacilitatorBindEnterprise(customerParam);
            }

        } catch (Exception e) {
            logger.error("获取客户列表时异常", e);
            throw e;
        }
        return sysEnterpriseBases;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveProductionEnt(String ticketId, String entType, RPCSysEnterpriseBase enterpriseBase) throws Exception {
        try {
            enterpriseBase.setEntCode(Constant.EntCode + Util.uuid32());
            String entId = saveEntbaseInfo(ticketId, entType, enterpriseBase);
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            FacilitatorCustomer facilitatorCutomer = new FacilitatorCustomer();
            facilitatorCutomer.setCustomerId(entId);
            saveFacilitatorCustomer(user, facilitatorCutomer);
            return entId;
        } catch (Exception e) {
            logger.error("保存服务商客户时异常", e);
            throw e;
        }
    }

    private String saveEntbaseInfo(String ticketId, String entType, RPCSysEnterpriseBase enterpriseBase) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        if (StringUtils.isBlank(enterpriseBase.getCantonCode())) {
            if (StringUtils.isBlank(enterpriseBase.getCantonCode())) {
                SysEnterpriseBase sysEnterpriseBase = sysEnterpriseBaseService.getEnterpriseInfoById(user.getEnterpriseId());
                enterpriseBase.setCantonCode(sysEnterpriseBase.getResponsibleArea());
            }
        }
        RPCSysEnterpriseBase rpcsysenterprisebase = enterpriseService.saveEnterpriseInfo(ticketId, enterpriseBase);// 保存到企业基础表中
        Map<String, String> enterpriseTypeMap = new HashMap<String, String>();
        enterpriseTypeMap.put(entType, "on");
        enterpriseService.updateEnterpriseNewField(rpcsysenterprisebase);

        enterpriseService.saveEnterpriseType(ticketId, rpcsysenterprisebase.getEntId(), enterpriseTypeMap, null);
        saveEnterpriseExtended(ticketId, rpcsysenterprisebase.getEntId());
        saveUserEnterpriseApprovedRecord(ticketId, rpcsysenterprisebase.getEntId());

        return rpcsysenterprisebase.getEntId();
    }

    private String saveEnterpriseExtended(String ticketId, String enterpriseId) throws Exception {
        EnterpriseExtended enterpriseExtended = new EnterpriseExtended();
        CodeValue codeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS, CodeTypeConstant.USER_EVENT_STATUS_PASS);
        enterpriseExtended.setSysEnterpriseBaseId(enterpriseId);
        enterpriseExtended.setEnterpriseStatus(codeValue.getId());
        enterpriseExtended.setEnterpriseIcon(null);
        enterpriseExtended.setValid(Constant.IS_VALID);
        String extendedId = enterpriseExtendedService.saveEnterpriseExtended(enterpriseExtended, ticketId);
        return extendedId;
    }

    private String saveUserEnterpriseApprovedRecord(String ticketId, String enterpriseId) throws Exception {
        CodeValue eventType = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_TYPE, CodeTypeConstant.USER_EVENT_TYPE_CREATE);
        CodeValue eventStatus = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS, CodeTypeConstant.USER_EVENT_STATUS_PASS);
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        UserEnterpriseApproveRecord userEnterpriseApproveRecord = new UserEnterpriseApproveRecord();
        userEnterpriseApproveRecord.setUserId(user.getUserId());
        userEnterpriseApproveRecord.setEnterpriseId(enterpriseId);
        userEnterpriseApproveRecord.setEventType(eventType.getId());
        userEnterpriseApproveRecord.setEventStatus(eventStatus.getId());
        userEnterpriseApproveRecord.setApplicationBy(user.getUserId());
        userEnterpriseApproveRecord.setApplicationTime(Util.datetimeToString(new Date()));
        userEnterpriseApproveRecord.setApprovedBy(null);
        userEnterpriseApproveRecord.setApprovedTime(null);
        String recordid = userEnterpriseApproveRecordService.saveUserEnterpriseApproveRecord(userEnterpriseApproveRecord, ticketId);

        return recordid;
    }

    @Override
    public void checkAreaCoverage(String productEntCantonCode, String enterpriseId, List<String> infoList) throws Exception {
        try {
            SysEnterpriseBase sysEnterpriseBase = sysEnterpriseBaseService.getEnterpriseInfoById(enterpriseId);
            if (sysEnterpriseBase != null) {
                String facilitatorCantonCode = sysEnterpriseBase.getResponsibleArea();
                if (StringUtils.isNotEmpty(facilitatorCantonCode)) {
                    facilitatorCantonCode = Util.calculateCantonCode(facilitatorCantonCode);
                    if (!productEntCantonCode.startsWith(facilitatorCantonCode)) {
                        SysCanton sysCanton = sysCantonService.queryCantonNameByCantonCode(sysEnterpriseBase.getResponsibleArea());
                        if (sysCanton != null) {
                            String responsibleAreaName = sysCanton.getCantonName();
                            infoList.add("创建的企业不在" + responsibleAreaName + "范围之内");
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("检查服务商是否在规定范围内创建产废企业时异常", e);
            throw e;
        }

    }

    @Override
    public FacilitatorCustomerModel getCustomerImg(FacilitatorCustomerParam facilitatorCustomerParam) throws Exception {
        FacilitatorCustomerModel customerModel ;
        try {
            customerModel = this.baseMapper.getCustomerImg(facilitatorCustomerParam);
            return customerModel;
        } catch (Exception e) {
            logger.error("获取服务商客户资质文件时异常", e);
            throw e;
        }

    }


}
