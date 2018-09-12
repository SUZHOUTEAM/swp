package com.mlsc.yifeiwang.operaction.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.operaction.common.OperationType;
import com.mlsc.yifeiwang.operaction.common.WebsiteOperationStatus;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.task.*;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.operaction.mapper.WebsiteOperationMapper;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.operaction.model.WebsiteOperationModel;
import com.mlsc.yifeiwang.operaction.model.WebsiteOperationParam;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOperation;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOpertionDisposalEnterprise;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOutsourcingDisposalEnterprise;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationContactsService;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationService;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOpertionDisposalEnterpriseService;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOutsourcingDisposalEnterpriseService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mlsc.waste.user.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
@Service
public class WebsiteOperationServiceImpl extends ServiceImpl<WebsiteOperationMapper, WebsiteOperation> implements IWebsiteOperationService {
    private final static Logger logger = LoggerFactory.getLogger(WebsiteOperationServiceImpl.class);
    @Autowired
    private IWebsiteOutsourcingDisposalEnterpriseService outsourcingDisposalEnterpriseService;

    @Autowired
    private IWebsiteOperationContactsService contactsService;

    @Autowired
    private WebOperactionQuartzManager webOperactionQuartzManager;

    @Autowired
    private IWebsiteOpertionDisposalEnterpriseService opertionDisposalEnterpriseService;

    @Override
    public List<SysEnterpriseBase> listWasteEnterpriseList(String type, PagingParameter pagingParameter, WebsiteOperationParam websiteOperationParam) throws Exception {
        List<SysEnterpriseBase> enterpriseUserList = null;
        if (pagingParameter != null) {
            Util.initPagingParameter(pagingParameter);
            websiteOperationParam.setStartRowIndex(pagingParameter.getStart());
            websiteOperationParam.setRows(pagingParameter.getLimit());
        }


        if (StringUtils.isNotEmpty(websiteOperationParam.getOperationId())) {
            List<String> licenceIds = listLicenceIdsByOperationId(websiteOperationParam.getOperationId());
            if (licenceIds != null && licenceIds.size() > 0) {
                websiteOperationParam.setLicenceIdList(licenceIds);
            }
        }

        if (websiteOperationParam.getCantonCodes() != null && websiteOperationParam.getCantonCodes().size() > 0) {
            List<String> cantonCodes = listCantonCode(websiteOperationParam);
            if (cantonCodes != null && cantonCodes.size() > 0) {
                websiteOperationParam.setCantonCodes(cantonCodes);
            }
        }

        if (OperationType.NOPLAN.getValue().equals(websiteOperationParam.getHasPlan())) {
            enterpriseUserList = listNoPlanWasteEnterprise(websiteOperationParam);
        } else if (OperationType.HASPlan.getValue().equals(websiteOperationParam.getHasPlan())) {
            enterpriseUserList = listPlanWasteEnterprise(websiteOperationParam);
        }

        if (pagingParameter != null && websiteOperationParam.getTotalRecord() != null) {
            pagingParameter.setTotalRecord(websiteOperationParam.getTotalRecord());
        }

        return enterpriseUserList;
    }

    private List<String> listCantonCode(WebsiteOperationParam websiteOperationParam) {
        Iterator<String> it = websiteOperationParam.getCantonCodes().iterator();
        List<String> cantonCodes = new ArrayList<String>();
        while (it.hasNext()) {
            String key = it.next();
            key = Util.calculateCantonCode(key);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(key)) {
                cantonCodes.add(key);
            }
        }
        return cantonCodes;
    }

    private List<String> listLicenceIdsByOperationId(String operationId) throws Exception {
        try {
            WebsiteOpertionDisposalEnterprise disposalEnterprise = new WebsiteOpertionDisposalEnterprise();
            disposalEnterprise.setOperationId(operationId);
            List<WebsiteOpertionDisposalEnterprise> disposalEnterpriseList = opertionDisposalEnterpriseService.listDisposalEnterprise(disposalEnterprise);
            if (disposalEnterpriseList != null && disposalEnterpriseList.size() > 0) {
                List<String> licenceIds = new ArrayList<String>();
                for (WebsiteOpertionDisposalEnterprise temp : disposalEnterpriseList) {
                    licenceIds.add(temp.getLicenceId());
                }
                return licenceIds;
            }
        } catch (Exception e) {
            logger.error("获取许可证信息时异常", e);
            throw e;
        }
        return null;
    }

    @Override
    public List<SysEnterpriseBase> initOutSouringWasteEnterprise(String entName) throws Exception {
        List<SysEnterpriseBase> enterpriseUserList = null;
        try {
            enterpriseUserList = this.baseMapper.initOutSouringWasteEnterprise(entName);
        } catch (Exception e) {
            logger.error("获取委外产废企业的处置企业时异常", e);
            throw e;
        }
        return enterpriseUserList;
    }

    @Override
    public List<SysEnterpriseBase> listOutSouringWasteEnterprise(WebsiteOperationParam websiteOperationParam) throws Exception {
        List<SysEnterpriseBase> enterpriseUserList = null;
        try {
            int count = this.baseMapper.countOutSouringWasteEnterprise(websiteOperationParam);
            if (count > 0) {
                websiteOperationParam.setTotalRecord(count);
                enterpriseUserList = this.baseMapper.listOutSouringWasteEnterprise(websiteOperationParam);
            }

        } catch (Exception e) {
            logger.error("获取委外产废企业时异常", e);
            throw e;
        }
        return enterpriseUserList;
    }

    @Override
    public List<SysEnterpriseBase> listNoPlanWasteEnterprise(WebsiteOperationParam websiteOperationParam) throws Exception {
        List<SysEnterpriseBase> enterpriseUserList = null;
        try {
            int count = this.baseMapper.countNoAnnualPlan(websiteOperationParam);
            if (count > 0) {
                websiteOperationParam.setTotalRecord(count);
                enterpriseUserList = this.baseMapper.listNoAnnualPlan(websiteOperationParam);
            }
        } catch (Exception e) {
            logger.error("获取未做当年管理计划的产废企业时异常", e);
            throw e;
        }
        return enterpriseUserList;
    }

    @Override
    public List<SysEnterpriseBase> listPlanWasteEnterprise(WebsiteOperationParam websiteOperationParam) throws Exception {
        List<SysEnterpriseBase> enterpriseUserList = null;
        try {
            int count = this.baseMapper.countHasAnnualPlan(websiteOperationParam);
            if (count > 0) {
                websiteOperationParam.setTotalRecord(count);
                enterpriseUserList = this.baseMapper.listHasAnnualPlan(websiteOperationParam);
            }
        } catch (Exception e) {
            logger.error("获取已做当年管理计划的产废企业时异常", e);
            throw e;
        }
        return enterpriseUserList;
    }

    @Override
    public List<SysEnterpriseBase> listNoTransferWasteEnterprise(WebsiteOperationParam websiteOperationParam) throws Exception {
        List<SysEnterpriseBase> enterpriseUserList = null;
        try {
            int count = this.baseMapper.countNoTransferWasteEnterprise(websiteOperationParam);
            if (count > 0) {
                websiteOperationParam.setTotalRecord(count);
                enterpriseUserList = this.baseMapper.listNoTransferWasteEnterprise(websiteOperationParam);
            }
        } catch (Exception e) {
            logger.error("获取当年未发生转移的产废企业时异常", e);
            throw e;
        }
        return enterpriseUserList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveOrUpdateWebSiteOperationInfo(User user, WebsiteOperationParam websiteOperationParam, List<SysEnterpriseBase> enterpriseUserList) throws Exception {
        try {
            boolean result = saveOrUpdateWebSiteOperation(user, websiteOperationParam);
            if (result) {
                if (websiteOperationParam.getOutSourceDisposalEnterName() != null && websiteOperationParam.getOutSourceDisposalEnterName().size() > 0) {
                    saveWebSiteOutSouringDisposalEnterprise(user, websiteOperationParam.getOutSourceDisposalEnterName(), websiteOperationParam.getOperationId());
                }
                WebOperactionNoticeTask operactionNoticeTask = TaskUtils.getTask(WebOperactionNoticeTask.class);
                operactionNoticeTask.setUser(user);
                operactionNoticeTask.setOperactionId(websiteOperationParam.getOperationId());
                operactionNoticeTask.setEnterpriseUserList(enterpriseUserList);
                TaskUtils.executeTask(operactionNoticeTask);
                return websiteOperationParam.getOperationId();
            }

        } catch (Exception e) {
            logger.error("保存运营时异常", e);
            throw e;
        }
        return null;
    }

    private boolean saveOrUpdateWebSiteOperation(User user, WebsiteOperationParam websiteOperationParam) {
        try {
            WebsiteOperation websiteOperation = prepareWebSiteOperation(websiteOperationParam);
            WebsiteOperation websiteOperationExsit = this.selectById(websiteOperation.getId());
            Date date = new Date();
            if (websiteOperationExsit != null) {
                websiteOperation.setEditBy(user.getUserId());
                websiteOperation.setEditTime(date);
                return this.updateAllColumnById(websiteOperation);
            } else {
                websiteOperation.setCreateBy(user.getUserId());
                websiteOperation.setCreateTime(date);
                websiteOperation.setEditBy(user.getUserId());
                websiteOperation.setEditTime(date);
                boolean insertResult = this.insert(websiteOperation);
                websiteOperationParam.setOperationId(websiteOperation.getId());
                return insertResult;
            }
        } catch (Exception e) {
            logger.error("保存运营信息时异常", e);
            throw e;
        }
    }

    private WebsiteOperation prepareWebSiteOperation(WebsiteOperationParam websiteOperationParam) {
        WebsiteOperation websiteOperation = this.selectById(websiteOperationParam.getOperationId());
        if (websiteOperation == null) {
            websiteOperation = new WebsiteOperation();
        }

        websiteOperation.setApplyType(websiteOperationParam.getApplyType());
        websiteOperation.setQualificationMatch(websiteOperationParam.getQualificationMatch());
        websiteOperation.setBusiStatus(WebsiteOperationStatus.SUBMIT.getCode());
        websiteOperation.setOperationCode(Util.getOrdersCode());

        String cantonCodeStr = Util.listToString(websiteOperationParam.getCantonCodes());
        websiteOperation.setAreaCode(cantonCodeStr);

        String wasteTypes = Util.listToString(websiteOperationParam.getWasteTypes());
        websiteOperation.setWasteType(wasteTypes);


        if(StringUtils.isNotBlank(websiteOperationParam.getOperationName())){
            websiteOperation.setOperationName(websiteOperationParam.getOperationName());
        }
        if(StringUtils.isNotBlank(websiteOperationParam.getHasPlan())){
            websiteOperation.setHasPlan(websiteOperationParam.getHasPlan());
        }

        if (websiteOperationParam.getPlanStartAmount() != null || websiteOperationParam.getPlanEndAmount() != null) {
            websiteOperation.setStartAmount(websiteOperationParam.getPlanStartAmount());
            websiteOperation.setEndAmount(websiteOperationParam.getPlanEndAmount());
        } else if (websiteOperationParam.getDetailStartAmount() != null || websiteOperationParam.getDetailEndAmount() != null) {
            websiteOperation.setStartAmount(websiteOperationParam.getDetailStartAmount());
            websiteOperation.setEndAmount(websiteOperationParam.getDetailEndAmount());
        }else{
            websiteOperation.setStartAmount(null);
            websiteOperation.setEndAmount(null);
        }

        if (websiteOperationParam.getStartTime() != null) {
            websiteOperation.setStartTime(websiteOperationParam.getStartTime());
        }
        if (websiteOperationParam.getEndTime() != null) {
            websiteOperation.setEndTime(websiteOperationParam.getEndTime());
        }
        if (StringUtils.isNotEmpty(websiteOperationParam.getCronJob())) {
            websiteOperation.setCronJob(websiteOperationParam.getCronJob());
        }
        websiteOperation.setId(websiteOperationParam.getOperationId());
        return websiteOperation;
    }


    private void saveWebSiteOutSouringDisposalEnterprise(User user, List<String> outSourceDisposalEnterNames, String operationId) throws Exception {
        try {
            EntityWrapper<WebsiteOutsourcingDisposalEnterprise> ew = new EntityWrapper<WebsiteOutsourcingDisposalEnterprise>();
            ew.setSqlSelect("id");
            if (StringUtils.isNotEmpty(operationId)) {
                ew.eq("operationId", operationId);
            }
            List<WebsiteOutsourcingDisposalEnterprise> list = outsourcingDisposalEnterpriseService.selectList(ew);
            if (list != null && list.size() > 0) {
                outsourcingDisposalEnterpriseService.delete(ew);
            }

            List<WebsiteOutsourcingDisposalEnterprise> disposalEnterprises = new ArrayList<WebsiteOutsourcingDisposalEnterprise>();
            for (String entName : outSourceDisposalEnterNames) {
                WebsiteOutsourcingDisposalEnterprise disposalEnterprise = new WebsiteOutsourcingDisposalEnterprise();
                disposalEnterprise.setOperationId(operationId);
                disposalEnterprise.setOutsourcingDisposalName(entName);
                disposalEnterprise.setBusiStatus(WebsiteOperationStatus.SUBMIT.getCode());
                Date date = new Date();
                disposalEnterprise.setCreateBy(user.getUserId());
                disposalEnterprise.setCreateTime(date);
                disposalEnterprise.setEditBy(user.getUserId());
                disposalEnterprise.setEditTime(date);
                disposalEnterprises.add(disposalEnterprise);
            }
            outsourcingDisposalEnterpriseService.insertBatch(disposalEnterprises);
        } catch (Exception e) {
            logger.error("保存委外处置企业时异常", e);
            throw e;
        }

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean startWebSiteOperationInfo(User user, List<String> operationIds) throws Exception {
        boolean result = true;
        try {
            for (String operationId : operationIds) {
                WebsiteOperation websiteOperation = this.selectById(operationId);
                Date currentDate = new Date();
                if (websiteOperation.getStartTime().before(currentDate) && websiteOperation.getEndTime().after(currentDate)) {
                    websiteOperation.setBusiStatus(WebsiteOperationStatus.RUNNING.getCode());
                    webOperactionQuartzManager.removeJob(websiteOperation);
                    webOperactionQuartzManager.addJob(websiteOperation, WebOperactionJob.class);
                } else {
                    websiteOperation.setBusiStatus(WebsiteOperationStatus.START.getCode());
                }
                Date date = new Date();
                websiteOperation.setEditBy(user.getUserId());
                websiteOperation.setEditTime(date);
                result = result && this.updateById(websiteOperation);
            }
        } catch (Exception e) {
            logger.error("更新运营计划时异常", e);
            throw e;
        }
        return result;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean stopWebSiteOperationInfo(User user, List<String> operationIds) throws Exception {
        boolean result = true;
        try {
            for (String operationId : operationIds) {
                WebsiteOperation websiteOperation = this.selectById(operationId);
                websiteOperation.setBusiStatus(WebsiteOperationStatus.STOP.getCode());
                Date date = new Date();
                websiteOperation.setEditBy(user.getUserId());
                websiteOperation.setEditTime(date);
                this.baseMapper.updateById(websiteOperation);
                result = result && this.updateById(websiteOperation);
                webOperactionQuartzManager.removeJob(websiteOperation);
            }
        } catch (Exception e) {
            logger.error("停止运营计划时异常", e);
            throw e;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteWebSiteOperationInfo(User user, List<String> operationIds) throws Exception {
        boolean result = true;
        try {
            for (String operationId : operationIds) {
                WebsiteOperation websiteOperation = this.selectById(operationId);
                websiteOperation.setDeleteFlag(1);
                Date date = new Date();
                websiteOperation.setEditBy(user.getUserId());
                websiteOperation.setEditTime(date);
                result = result && this.updateById(websiteOperation);
                webOperactionQuartzManager.removeJob(websiteOperation);
            }
        } catch (Exception e) {
            logger.error("删除运营计划时异常", e);
            throw e;
        }
        return result;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateWebSiteOperationInfo(User user, WebsiteOperationParam websiteOperationParam) throws Exception {
        try {
            if (websiteOperationParam != null && StringUtils.isNotBlank(websiteOperationParam.getOperationId())) {
                WebsiteOperation websiteOperation = this.selectById(websiteOperationParam.getOperationId());
                if (websiteOperation != null) {
                    if (StringUtils.isNotEmpty(websiteOperationParam.getOperationName())) {
                        websiteOperation.setOperationName(websiteOperationParam.getOperationName());
                    }
                    if (websiteOperationParam.getStartTime() != null) {
                        websiteOperation.setStartTime(websiteOperationParam.getStartTime());
                    }
                    if (websiteOperationParam.getEndTime() != null) {
                        websiteOperation.setEndTime(websiteOperationParam.getEndTime());
                    }
                    if (StringUtils.isNotEmpty(websiteOperationParam.getCronJob())) {
                        websiteOperation.setCronJob(websiteOperationParam.getCronJob());
                    }
                    websiteOperation.setId(websiteOperationParam.getOperationId());

                    Date date = new Date();
                    websiteOperation.setBusiStatus(WebsiteOperationStatus.SUBMIT.getCode());
                    websiteOperation.setEditBy(user.getUserId());
                    websiteOperation.setEditTime(date);
                    return this.updateById(websiteOperation);
                }
            }

        } catch (Exception e) {
            logger.error("更新运营计划时异常", e);
            throw e;
        }
        return false;
    }

    @Override
    public List<WebsiteOperationModel> listWebSiteOperationInfo(PagingParameter pagingParameter) {
        int count = this.baseMapper.countWebSiteOperationInfo();
        List<WebsiteOperationModel> list = null;
        if (count > 0) {
            if (pagingParameter != null) {
                Util.initPagingParameter(pagingParameter);
                pagingParameter.setTotalRecord(count);
            }
            list = this.baseMapper.listWebSiteOperationInfo(pagingParameter);
        }
        return list;
    }

    @Override
    public WebsiteOperationModel getWebSiteOperationInfo(String id) {
        return this.baseMapper.getWebSiteOperationInfo(id);
    }

    @Override
    public List<WebsiteOperation> listOperationByStatus(String busiStatus) throws Exception {
        EntityWrapper<WebsiteOperation> ew = new EntityWrapper<>();
        ew.setSqlSelect("id,startTime,endTime,cronJob");
        ew.eq("deleteFlag", 0);
        ew.eq("busiStatus", busiStatus);
        return selectList(ew);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateOperationStatus(WebsiteOperation websiteOperation, String busiStatus) throws Exception {
        websiteOperation = this.selectById(websiteOperation.getId());
        Date date = new Date();
        websiteOperation.setEditTime(date);
        websiteOperation.setBusiStatus(busiStatus);
        this.baseMapper.updateById(websiteOperation);
    }

    @Override
    public List<WebsiteOperation> listStartWebSiteOperationInfo() throws Exception {
        return this.baseMapper.listStartWebSiteOperationInfo();
    }

    @Override
    public List<WebsiteOperation> listStopWebSiteOperationInfo() throws Exception {
        return this.baseMapper.listStopWebSiteOperationInfo();
    }

    @Override
    public List<EntWasteModel> listEntWasteByEntId(String entId) throws Exception {
        return this.baseMapper.listEntWasteByEntId(entId);
    }


}
