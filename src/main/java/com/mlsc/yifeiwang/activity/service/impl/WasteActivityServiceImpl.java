package com.mlsc.yifeiwang.activity.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.activity.common.ActivityStatus;
import com.mlsc.common.constant.BaseConstant;
import com.mlsc.yifeiwang.bindserve.common.PayStatus;
import com.mlsc.common.util.DateUtil;
import com.mlsc.common.util.StringUtils;
import com.mlsc.yifeiwang.activity.entity.WasteActivity;
import com.mlsc.yifeiwang.activity.entity.WasteActivityContacts;
import com.mlsc.yifeiwang.activity.entity.WasteActivityEnterprise;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.task.WebActivityNoticeTask;
import com.mlsc.task.WebActivityNotifierJob;
import com.mlsc.task.WebActivityQuartzManager;
import com.mlsc.task.TaskUtils;
import com.mlsc.yifeiwang.activity.mapper.WasteActivityMapper;
import com.mlsc.yifeiwang.activity.model.ActivityQueryParam;
import com.mlsc.yifeiwang.activity.model.EnterpriseWasteVo;
import com.mlsc.yifeiwang.activity.model.WasteActivityDetailVO;
import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import com.mlsc.yifeiwang.activity.service.IWasteActivityContactsService;
import com.mlsc.yifeiwang.activity.service.IWasteActivityEnterpriseService;
import com.mlsc.yifeiwang.activity.service.IWasteActivityService;
import com.mlsc.waste.fw.mapper.SysCantonMapper;
import com.mlsc.waste.fw.model.SysCanton;
import com.mlsc.waste.fw.model.SysCantonQueryParam;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.wasterealase.model.ReleaseStatusModel;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.mlsc.yifeiwang.activity.common.ActivityStatus.ACTIVE;

@Service
public class WasteActivityServiceImpl extends ServiceImpl<WasteActivityMapper, WasteActivity> implements IWasteActivityService {
    private final static Logger logger = LoggerFactory.getLogger(WasteActivityServiceImpl.class);
    @Autowired
    private WebActivityQuartzManager webActivityQuartzManager;

    @Autowired
    private IWasteActivityEnterpriseService wasteActivityEnterpriseService;

    @Autowired
    private SysCantonMapper sysCantonMapper;

    @Autowired
    private LicenceService licenceService;

    @Autowired
    private IEntReleaseService entReleaseService;

    @Autowired
    private IWasteActivityContactsService wasteActivityContactsService;

    @Override
    public List<WasteActivityVO> listWasteActivityByEntId(ActivityQueryParam param, PagingParameter pagingParameter) throws Exception {
        int count = this.baseMapper.countWasteActivityByEntId(param);
        List<WasteActivityVO> list = null;
        if (count > 0) {
            Util.initPagingParameter(pagingParameter);
            pagingParameter.setTotalRecord(count);
            param.setStartRowIndex(pagingParameter.getStart());
            param.setRows(pagingParameter.getLimit());
            if (param.getStartDate() != null) {
                param.setStartDate(DateUtil.convertToStartDate(param.getStartDate()));
            }
            if (param.getEndDate() != null) {
                param.setEndDate(DateUtil.convertToStartDate(param.getEndDate()));
            }

            list = this.baseMapper.listWasteActivityByEntId(param);
            if (list != null && list.size() > 0) {
                Iterator<WasteActivityVO> it = list.iterator();
                while (it.hasNext()) {
                    WasteActivityVO activityVO = it.next();
                    List<ReleaseStatusModel> releaseStatusModels = entReleaseService.countActivityStatusByActivityId(activityVO.getActivityId());
                    activityVO.setReleaseStatusList(releaseStatusModels);
                }
            }
        }
        return list;
    }

    @Override
    public List<WasteActivityVO> listWasteActivity(ActivityQueryParam param) {
        return this.baseMapper.listWasteActivity(param);
    }

    @Override
    public Page<WasteActivity> listWasteActivity(Page<WasteActivity> page) {
        EntityWrapper<WasteActivity> ew = new EntityWrapper<>();
        ew.setSqlSelect("id, activity_name activityName, start_date startDate, end_date endDate,status,create_time createTime ,pay_status payStatus");
        ew.eq("valid", BaseConstant.YES);
        ew.orderBy(" create_time desc ");
        return selectPage(page, ew);
    }


    @Override
    public List<WasteActivityVO> listAcitiveByShoppingCardId(ActivityQueryParam activityVo) {
        return this.baseMapper.listAcitiveByShoppingCardId(activityVo);
    }

    @Override
    public List<WasteActivityVO> listAcitiveByOrderId(
            ActivityQueryParam activityVo) {
        return this.baseMapper.listAcitiveByOrderId(activityVo);
    }

    @Override
    public List<WasteActivityDetailVO> listCapacityreleaseByActivityId(User user, ActivityQueryParam param) {
        param.setWasteEnterpriseId(user.getEnterpriseId());
        List<WasteActivityDetailVO> activityDetailListByEnt = this.baseMapper.listCapacityreleaseByActivityId(param);
        //危废名称+购买的次数
        for (WasteActivityDetailVO activityDetailByEnt : activityDetailListByEnt) {
            param.setWasteEnterpriseId(activityDetailByEnt.getWasteEntId());
            param.setWasteId(activityDetailByEnt.getWasteId());
            List<EnterpriseWasteVo> enterpriseWastelist = this.listEnterpriseWasteByWasteId(param);
            activityDetailByEnt.setEnterpriseWastelist(enterpriseWastelist);
            //购买过的次数
            param.setResponseEnterpriseId(user.getEnterpriseId());
            param.setReleaseEnterpriseId(activityDetailByEnt.getEntId());
            param.setDispositionTypeId(activityDetailByEnt.getDispositionType());
            Integer buycount = this.getEnterpriseWasteBuyCount(param);
            activityDetailByEnt.setBuyCount(buycount);
        }

        return activityDetailListByEnt;
    }

    @Override
    public List<EnterpriseWasteVo> listEnterpriseWasteByWasteId(ActivityQueryParam param) {
        Objects.requireNonNull(param.getWasteEnterpriseId());
        Objects.requireNonNull(param.getWasteId());
        return this.baseMapper.listEnterpriseWasteByWasteId(param);
    }

    @Override
    public Integer getEnterpriseWasteBuyCount(ActivityQueryParam param) {
        return this.baseMapper.getEnterpriseWasteBuyCount(param);
    }

    @Override
    public boolean deleteLogicBatchIds(List<String> ids) {
        EntityWrapper<WasteActivity> ew = new EntityWrapper<>();
        ew.in("id", ids);
        WasteActivity wasteActivity = new WasteActivity();
        wasteActivity.setValid(BaseConstant.NO);
        for (String id : ids) {
            WasteActivity updateJobs = this.selectById(id);
            if (ActivityStatus.ACTIVE.getValue().equalsIgnoreCase(updateJobs.getStatus())) {
                WasteActivityVO wasteActivityVO = new WasteActivityVO();
                wasteActivityVO.setActivityId(id);
                webActivityQuartzManager.removeJob(wasteActivityVO);
            }
        }

        return this.update(wasteActivity, ew);
    }

    @Override
    public WasteActivityDetailVO getActivityCantonCodeById(ActivityQueryParam param) {
        return this.baseMapper.getActivityCantonCodeById(param);
    }

    @Override
    public Integer isInActivityCanton(String cantonCodes, User user) {
        Integer isInActivityCanton = 0;
        ActivityQueryParam param = new ActivityQueryParam();
        if (StringUtils.isNullOrEmpty(cantonCodes) || "86".equals(cantonCodes)) {
            isInActivityCanton = 99;
        } else {
            List<String> cantonCodesList = this.getCantonCodeList(cantonCodes);
            param.setWasteEnterpriseId(user.getEnterpriseId());
            param.setCantonCodeList(cantonCodesList);
            isInActivityCanton = this.baseMapper.isInActivityCanton(param);
        }
        return isInActivityCanton;
    }

    @Override
    public boolean publishByBatchIds(String ticketId, List<String> ids) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        EntityWrapper<WasteActivity> ew = new EntityWrapper<>();
        ew.in("id", ids);
        WasteActivity wa = new WasteActivity();
        wa.setStatus(ActivityStatus.ACTIVE.getValue());
        wa.setEditBy(user.getUserId());
        wa.setEditTime(new Date());
        boolean updateFlag = this.update(wa, ew);
        notifyUsers(user, ids, selectBatchIds(ids));
        if (updateFlag) {
            for (String id : ids) {
                WasteActivity wasteActivity = this.selectById(id);
                WasteActivityVO wasteActivityVO = new WasteActivityVO();
                wasteActivityVO.setActivityId(id);
                wasteActivityVO.setCronJob(wasteActivity.getCronJob());
                webActivityQuartzManager.addJob(wasteActivityVO, WebActivityNotifierJob.class);
            }
        }


        return updateFlag;
    }

    @Override
    public List<String> getCantonCodeList(String cantonCode) {
        List<String> cantonCodeList = new ArrayList<>();
        if (StringUtils.isNotNullOrEmpty(cantonCode)) {
            SysCantonQueryParam param = new SysCantonQueryParam();
            if (cantonCode.indexOf(",") != -1) {
                String[] cantonCodeArray = cantonCode.split(",");
                List<String> pCodeList = Arrays.asList(cantonCodeArray);
                cantonCodeList.addAll(pCodeList);
                param.setParentCantonCodeList(pCodeList);
            } else {
                cantonCodeList.add(cantonCode);
                param.setParentCantonCode(cantonCode);
            }
            List<SysCanton> children = sysCantonMapper.listSysCanton(param);
            List<String> parentCodeList = null;
            while (children != null && children.size() > 0) {
                parentCodeList = new ArrayList<>();
                for (SysCanton sysCanton : children) {
                    parentCodeList.add(sysCanton.getCantonCode());
                    cantonCodeList.add(sysCanton.getCantonCode());
                }
                param = new SysCantonQueryParam();
                param.setParentCantonCodeList(parentCodeList);
                children = sysCantonMapper.listSysCanton(param);
            }
        }
        return cantonCodeList;
    }

    @Override
    public Integer getWasteCountByWasteCode(List<WasteActivityDetailVO> activityDetailListByEnt) {
        TreeSet<String> codeSet = new TreeSet<String>();
        for (WasteActivityDetailVO activityDetail : activityDetailListByEnt) {
            codeSet.add(activityDetail.getWasteCode());
        }
        return codeSet.size();
    }


    @Override
    public List<WasteActivityVO> listOrderActiviyName(String entId) throws Exception {
        List<WasteActivityVO> list = new ArrayList<WasteActivityVO>();
        try {
            list = this.baseMapper.listOrderActiviyName(entId);
        } catch (Exception e) {
            logger.error("处置企业活动名称", e);
            throw e;
        }
        return list;
    }

    @Override
    public List<WasteActivityVO> listActiviyNameByInquiryEntId(String enterpriseId) throws Exception {
        List<WasteActivityVO> list = new ArrayList<WasteActivityVO>();
        try {
            list = this.baseMapper.listActiviyNameByInquiryEntId(enterpriseId);
        } catch (Exception e) {
            logger.error("处置企业询价活动名称", e);
            throw e;
        }
        return list;
    }

    @Override
    public void updateActivity(List<String> ids, WasteActivity wa, User user) throws Exception {
        EntityWrapper<WasteActivity> ew = new EntityWrapper<>();
        ew.in("id", ids);

        WasteActivity wasteActivity = new WasteActivity();
        if (StringUtils.isNotNullOrEmpty(wa.getStatus())) {
            wa.setStatus(wa.getStatus());
        }
        if (StringUtils.isNotNullOrEmpty(wa.getPayStatus())) {
            wa.setPayStatus(wa.getPayStatus());
        }
        wa.setEditBy(user.getUserId());
        wa.setEditTime(new Date());
        this.update(wa, ew);

    }

    @Override
    public void reomoveJob(List<String> ids, String status) throws Exception {
        for (String id : ids) {
            if (!ActivityStatus.ACTIVE.getValue().equalsIgnoreCase(status)) {
                WasteActivityVO wasteActivityVO = new WasteActivityVO();
                wasteActivityVO.setActivityId(id);
                webActivityQuartzManager.removeJob(wasteActivityVO);
            }
        }

    }

    @Override
    public List<WasteActivityVO> listActiviyNameByApplyEntId(String enterpriseId) throws Exception {
        List<WasteActivityVO> list = new ArrayList<WasteActivityVO>();
        try {
            list = this.baseMapper.listActiviyNameByApplyEntId(enterpriseId);
        } catch (Exception e) {
            logger.error("产废企业参加活动名称", e);
            throw e;
        }
        return list;
    }

    @Override
    public List<WasteActivityVO> listOrderActiviyNameByApplyEntId(String entId) throws Exception {
        List<WasteActivityVO> list = new ArrayList<WasteActivityVO>();
        try {
            list = this.baseMapper.listOrderActiviyNameByApplyEntId(entId);
        } catch (Exception e) {
            logger.error("产废企业企业活动名称", e);
            throw e;
        }
        return list;
    }

    @Override
    public List<WasteActivityDetailVO> getActivityDetailById(ActivityQueryParam param) {
        Objects.requireNonNull(param.getActivityId());
        return this.baseMapper.getActivityDetailById(param);
    }

    @Override
    public WasteActivity save(String ticketId, WasteActivityVO wasteActivityVO) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        if (Util.isSysUser(ticketId)) {
            wasteActivityVO.setPayStatus(PayStatus.FREE.getCode());
        } else {
            wasteActivityVO.setPayStatus(PayStatus.NONEPAYMENT.getCode());
        }
        boolean isInsert = StringUtils.isNullOrEmpty(wasteActivityVO.getActivityId());
        WasteActivity wa = copyToWasteActivityEntity(wasteActivityVO, user, isInsert);
        if (insertOrUpdate(wa)) {
            WasteActivityEnterprise wae = copyToWasteActivityEnterpriseEntity(wasteActivityVO, wa, isInsert);
            wasteActivityEnterpriseService.insertOrUpdate(wae);
        }
        return wa;
    }

    private WasteActivity copyToWasteActivityEntity(WasteActivityVO wasteActivityVO, User user, boolean isInsert) {
        WasteActivity wa = null;
        Date nowTime = new Date();
        if (isInsert) {
            wa = new WasteActivity();
            wa.setCreateTime(nowTime);
            wa.setCreateBy(user.getUserId());
            wa.setStatus(ActivityStatus.DRAFT.getValue());
            wa.setValid(BaseConstant.YES);
        } else {
            wa = selectById(wasteActivityVO.getActivityId());
            wa.setId(wasteActivityVO.getActivityId());
        }
        wa.setVideoResource(wasteActivityVO.getVideoResource());
        wa.setFileCount(wasteActivityVO.getFileCount());
        wa.setFileCount(wasteActivityVO.getFileCount());
        wa.setActivityName(wasteActivityVO.getActivityName());
        wa.setCantonCode(wasteActivityVO.getCantonCode());
        wa.setPriceType(wasteActivityVO.getPriceType());
        wa.setActivityRemark(wasteActivityVO.getActivityRemark());
        wa.setParentActivityId(wasteActivityVO.getParentActivityId());
        wa.setStartDate(wasteActivityVO.getStartDate());
        wa.setEndDate(wasteActivityVO.getEndDate());
        wa.setIndustry(wasteActivityVO.getIndustry());
        wa.setPayStatus(wasteActivityVO.getPayStatus());
        if (wasteActivityVO.getRiseEndDate() != null) {
            wa.setRiseEndDate(DateUtil.convertToEndDate(wasteActivityVO.getRiseEndDate()));
        }
        wa.setEnrollFee(wasteActivityVO.getEnrollFee());
        wa.setDiscount(wasteActivityVO.getDiscount());
        wa.setNumberGroupBuying(wasteActivityVO.getNumberGroupBuying());
        wa.setSubjectFileId(wasteActivityVO.getSubjectFileId());
        wa.setPresenter(wasteActivityVO.getPresenter());
        wa.setDuration(wasteActivityVO.getDuration());
        wa.setCustomCourseDesc(wasteActivityVO.getCustomCourseDesc());
        wa.setPayNote(wasteActivityVO.getPayNote());
        wa.setLogoFileId(wasteActivityVO.getLogoFileId());
        wa.setInquiryFileId(wasteActivityVO.getInquiryFileId());
        wa.setSwipeFileId(wasteActivityVO.getSwipeFileId());
        wa.setVideoResource(wasteActivityVO.getVideoResource());
        wa.setCronJob(wasteActivityVO.getCronJob());
        wa.setSmsTemplate(wasteActivityVO.getSmsTemplate());
        wa.setEditTime(nowTime);
        wa.setEditBy(user.getUserId());
        return wa;
    }

    private WasteActivityEnterprise copyToWasteActivityEnterpriseEntity(WasteActivityVO wasteActivityVO, WasteActivity wa, boolean isInsert) {

        WasteActivityEnterprise wae = null;
        if (isInsert) {
            wae = new WasteActivityEnterprise();
            wae.setCreateTime(wa.getCreateTime());
            wae.setCreateBy(wa.getCreateBy());
        } else {
            EntityWrapper<WasteActivityEnterprise> ew = new EntityWrapper<>();
            ew.eq("activity_id", wa.getId());
            wae = wasteActivityEnterpriseService.selectOne(ew);
        }
        wae.setEntName(wasteActivityVO.getEntName());
        wae.setEntId(wasteActivityVO.getEntId());
        wae.setStartPrice(wasteActivityVO.getStartPrice());
        wae.setEndPrice(wasteActivityVO.getEndPrice());
        wae.setEntRemark(wasteActivityVO.getEntRemark());

        wae.setActivityId(wa.getId());
        wae.setEditTime(wa.getEditTime());
        wae.setEditBy(wa.getEditBy());
        return wae;
    }

    @Override
    public List<WasteActivityVO> getCurrentActivity(String cantonCode) {
        if (StringUtils.isNullOrEmpty(cantonCode)) return null;

        ActivityQueryParam param = new ActivityQueryParam();
        param.setStatus(ACTIVE.getValue());
        DateTime now = DateTime.now();
        param.setEndDate(DateUtil.convertToEndDate(now).toDate());

        param.setCantonCode(cantonCode.substring(0, 2));
        param.setOrderBy("create_time desc, start_date desc, end_date desc");
        List<WasteActivityVO> voList = listWasteActivity(param);
        return voList;
    }

    @Override
    public WasteActivityVO getWasteActivityVoById(ActivityQueryParam param) {
        Objects.requireNonNull(param.getActivityId());
        List<WasteActivityVO> voList = listWasteActivity(param);
        return (voList != null && voList.size() > 0) ? voList.get(0) : null;
    }

    private void notifyUsers(User sendUser, List<String> activityIds, List<WasteActivity> activityList) throws Exception {
        EntityWrapper<WasteActivityContacts> ew = new EntityWrapper<>();
        ew.in("activity_id", activityIds);
        ew.setSqlSelect("id");
        List<WasteActivityContacts> list = wasteActivityContactsService.selectList(ew);
        if (list != null && list.size() > 0) {
            wasteActivityContactsService.delete(ew);
        }
        WebActivityNoticeTask activityNoticeTask = TaskUtils.getTask(WebActivityNoticeTask.class);
        activityNoticeTask.setActivity(activityList);
        activityNoticeTask.setSendUser(sendUser);
        TaskUtils.executeTask(activityNoticeTask);
    }


        @Override
    public List<WasteActivityVO> listHomePageWasteActivity(ActivityQueryParam param, PagingParameter pagingParameter) throws Exception {

        int count = this.baseMapper.countHomePageWasteActivity(param);
        List<WasteActivityVO> list = null;
        if (count > 0) {
            Util.initPagingParameter(pagingParameter);
            pagingParameter.setTotalRecord(count);
            param.setStartRowIndex(pagingParameter.getStart());
            param.setRows(pagingParameter.getLimit());
            list = this.baseMapper.listHomePageWasteActivity(param);
        }
        return list;
    }

    @Override
    public List<String> listStopWasteActivity() {
        return this.baseMapper.listStopWasteActivity();
    }

    @Override
    public List<WasteActivityVO> listStartWasteActivity() {
        return this.baseMapper.listStartWasteActivity();
    }

    @Override
    public void updateActivityCoverEntCount(User user, String id) throws Exception {
        int coverEntCount = wasteActivityContactsService.generateWasteActivityCoverEntCount(id);
        if (coverEntCount > 0) {
            WasteActivity wasteActivity = this.baseMapper.selectById(id);
            wasteActivity.setCoverEntCount(coverEntCount);
            wasteActivity.setBitCion((int) (Constant.half * coverEntCount) + Constant.baseFee);
            wasteActivity.setEditBy(user.getUserId());
            wasteActivity.setEditTime(new Date());
            this.updateAllColumnById(wasteActivity);
        }

    }

    @Override
    public void stopActivity(List<String>  idList, User user, String status) throws Exception {
        try {
            if (idList != null && idList.size() > 0 && StringUtils.isNotNullOrEmpty(status)) {
                WasteActivity wasteActivity = new WasteActivity();
                wasteActivity.setStatus(status);
                updateActivity(idList, wasteActivity, user);
                reomoveJob(idList, status);
            }
        } catch (Exception e) {
            logger.error("停止活动", e);
            throw e;
        }

    }

    @Override
    public List<WasteActivityVO> listLiveWasteActivity(ActivityQueryParam activityVo) {
        int count = this.baseMapper.countLiveWasteActivity(activityVo);
        List<WasteActivityVO> list = null;
        if (count > 0) {
            list = this.baseMapper.listLiveWasteActivity(activityVo);


        }
        return list;
    }
}
