package com.mlsc.yifeiwang.enterprise.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.enterprise.entity.EntEvaluate;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import com.mlsc.yifeiwang.enterprise.mapper.SysEnterpriseBaseMapper;
import com.mlsc.yifeiwang.enterprise.model.SysEnterpriseModel;
import com.mlsc.yifeiwang.licence.service.IOperationLicenceItemService;
import com.mlsc.yifeiwang.licence.service.IOperationLicenceService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 企业基本信息 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2017-10-20
 */
@Service
public class SysEnterpriseBaseServiceImpl extends ServiceImpl<SysEnterpriseBaseMapper, SysEnterpriseBase> implements ISysEnterpriseBaseService {
    private final static Logger logger = LoggerFactory.getLogger(SysEnterpriseBaseServiceImpl.class);

    @Autowired
    private LicenceService licenceService;
    @Autowired
    private IOperationLicenceItemService operationLicenceItemService;
    @Autowired
    private IOperationLicenceService operationLicenceService;

    @Override
    public SysEnterpriseModel checkEntInfoCompleted(User user) throws Exception {
        String entId = user.getEnterpriseId();
        SysEnterpriseBase entBase = new SysEnterpriseBase();
        entBase.setEntId(entId);
        entBase = this.baseMapper.getEnterpriseInfo(entBase);
        SysEnterpriseModel enterpriseModel = new SysEnterpriseModel();
        if (StringUtils.isNotEmpty(entBase.getSummary())) {
            enterpriseModel.setHasSummary(true);
        }
        if (StringUtils.isNotEmpty(entBase.getSalesNote())) {
            enterpriseModel.setHasSalesNote(true);
        }
        OperationLicence licence = licenceService.getValidLicIdByEnterpriseId(entId);
        if (licence != null) {
            enterpriseModel.setHasLicence(true);
        } else {
            enterpriseModel.setLicenceAuditStatus(operationLicenceService.getLicenceCreateStatus(entId));
        }
        if (enterpriseModel.isHasLicence() && enterpriseModel.isHasSummary() && enterpriseModel.isHasSalesNote()) {
            enterpriseModel.setAllCompleted(true);
        }

        return enterpriseModel;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateEnterprise(User user, SysEnterpriseBase enterpriseBase) throws Exception {
        Date date = new Date();
        try {
            enterpriseBase.setUpdaterID(user.getUserId());
            enterpriseBase.setUpdateTime(date);
            this.baseMapper.updateEnterpriseInfo(enterpriseBase);
        } catch (Exception e) {
            logger.error("更新企业简介和销售说明时异常", e);
            throw e;
        }
        return true;
    }

    @Override
    public SysEnterpriseBase getEnterpriseInfoById(String entId) throws Exception {
        SysEnterpriseBase enterpriseBase = null;
        try {
            if (StringUtils.isNotEmpty(entId)) {
                enterpriseBase = new SysEnterpriseBase();
                enterpriseBase.setEntId(entId);
                enterpriseBase = this.baseMapper.getEnterpriseInfo(enterpriseBase);
            }
        } catch (Exception e) {
            logger.error("获取企业信息时异常", e);
            throw e;
        }
        return enterpriseBase;
    }

    @Override
    public SysEnterpriseBase getEnterpriseInfo(SysEnterpriseBase enterpriseBase) throws Exception {
        try {
            if (enterpriseBase != null) {
                enterpriseBase = this.baseMapper.getEnterpriseInfo(enterpriseBase);
            }
        } catch (Exception e) {
            logger.error("获取企业信息时异常", e);
            throw e;
        }
        return enterpriseBase;
    }

    @Override
    public SysEnterpriseModel getEnterpriseSummaryInfo(SysEnterpriseBase enterpriseBase) throws Exception {
        SysEnterpriseModel sysEnterpriseModel = new SysEnterpriseModel();
        try {
            if (StringUtils.isNotEmpty(enterpriseBase.getEntId())) {
                String licenceId = null;
                OperationLicence licence = licenceService.getValidLicIdByEnterpriseId(enterpriseBase.getEntId());
                if (licence != null) {
                    licenceId = licence.getId();
                    sysEnterpriseModel = this.baseMapper.getEnterpriseSummaryInfo(enterpriseBase.getEntId(), licenceId);
                    sysEnterpriseModel.setApprovedQuantity(operationLicenceItemService.getApprovedQuantity(licenceId));
                    sysEnterpriseModel.setOperationLicence(licence);
                } else {
                    sysEnterpriseModel = this.baseMapper.getEnterpriseSummaryInfo(enterpriseBase.getEntId(), null);
                }


            }
        } catch (Exception e) {
            logger.error("获取企业概述时异常", e);
            throw e;
        }
        return sysEnterpriseModel;
    }

    private void listAllEntEvaluates(SysEnterpriseModel sysEnterpriseModel) {
        if (sysEnterpriseModel != null) {
            List<EntEvaluate> entEvaluateList = sysEnterpriseModel.getEntEvaluates();
            Iterator<EntEvaluate> it = entEvaluateList.iterator();
            while (it.hasNext()) {
                EntEvaluate evaluate = it.next();
                SysEnterpriseBase enterpriseBase = new SysEnterpriseBase();
                enterpriseBase.setEntId(evaluate.getEvaluatedBy());
                enterpriseBase = this.baseMapper.getEnterpriseInfo(enterpriseBase);
                evaluate.setCreateBy(enterpriseBase.getEntName());
            }
            sysEnterpriseModel.setEntEvaluates(entEvaluateList);
        }
    }

    @Override
    public RPCSysEnterpriseBaseVo getEnterpriseByEntId(String enterpriseId) {
        RPCSysEnterpriseBaseVo enterpriseBaseVo = null;
        try {
            if(StringUtils.isNotBlank(enterpriseId)){
                enterpriseBaseVo = this.baseMapper.queryEnterpriseByEntId(enterpriseId);
            }
        } catch (Exception e) {
            logger.error("获取企业基础信息失败", e);
            throw new RuntimeException(e);
        }
        return enterpriseBaseVo;
    }

    @Override
    public List<RPCSysEnterpriseBaseVo> listEntDropDown(String entName) {
        List<RPCSysEnterpriseBaseVo> entList = null;
        try {
            entList = this.baseMapper.getEntDropDownList(entName);
        } catch (Exception e) {
            logger.error("查询企业下拉列表时异常", e);
            throw new RuntimeException(e);
        }
        return entList;
    }

    @Override
    public RPCSysEnterpriseBaseVo getCoordinateByEntId(String entId) {
        RPCSysEnterpriseBaseVo enterpriseBaseVo = null;
        try {
            enterpriseBaseVo = this.baseMapper.getCoordinateByEntId(entId);
        } catch (Exception e) {
            logger.error("查询企业经纬度时异常", e);
            throw new RuntimeException(e);
        }
        return enterpriseBaseVo;
    }

    @Override
    public boolean checkResponsibleArea(String responsibleArea) {
        int count = this.baseMapper.checkResponsibleArea(responsibleArea);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean isEnterpriseVaild(String ticketId) throws Exception {
        boolean result = true;
        if (!Util.isSysUser(ticketId)) {// 如果是平台管理员，默认不检查企业的完整性
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            if (user == null || StringUtils.isNotBlank(user.getEnterpriseId())) {
                result = false;
            } else {
                // 判断平台企业表（sys_enterprise_base）以及企业扩展表（enterprise_extended）有无数据
                result = isExistsEnterpriseTypeByEnterId(user.getEnterpriseId());
            }
        }
        return result;
    }

    @Override
    public SysEnterpriseBase getDefaultDispositionEnt() throws Exception {
        EntityWrapper<SysEnterpriseBase> ew = new EntityWrapper<>();
        ew.setSqlSelect("entId,entName");
        ew.eq("entName", Constant.Default_DIS_ENTNAME);
        return this.selectOne(ew);
    }

    /**
     * 判断企业类型表中是否有数据
     *
     * @return
     * @throws Exception
     */
    private boolean isExistsEnterpriseTypeByEnterId(String enterpriseId) throws Exception {
        boolean result = true;
        SysEnterpriseBase entBase = new SysEnterpriseBase();
        entBase.setEntId(enterpriseId);
        entBase = this.baseMapper.getEnterpriseInfo(entBase);
        if (entBase == null || StringUtils.isBlank(enterpriseId) || StringUtils.isBlank(entBase.getEntType())) {
            result = false;
        }
        return result;
    }

}
