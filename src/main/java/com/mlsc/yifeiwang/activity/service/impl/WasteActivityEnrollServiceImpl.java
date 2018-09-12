package com.mlsc.yifeiwang.activity.service.impl;

import cn.jiguang.common.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mlsc.yifeiwang.activity.common.EnrollStatusEnum;
import com.mlsc.yifeiwang.activity.entity.WasteActivity;
import com.mlsc.yifeiwang.activity.entity.WasteActivityEnroll;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import com.mlsc.yifeiwang.activity.service.IWasteActivityService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.activity.mapper.WasteActivityEnrollMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.activity.model.WasteActivityEnrollModel;
import com.mlsc.yifeiwang.activity.service.IWasteActivityEnrollService;
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
 * @since 2018-02-05
 */
@Service
public class WasteActivityEnrollServiceImpl extends ServiceImpl<WasteActivityEnrollMapper, WasteActivityEnroll> implements IWasteActivityEnrollService {
    private final static Logger logger = LoggerFactory.getLogger(WasteActivityEnrollServiceImpl.class);
    @Autowired
    private IWasteActivityService wasteActivityService;

    @Autowired
    private SysMsgServcie sysMsgServcie;
    @Autowired
    private IUserInfoService userInfoService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<String> saveWasteActivityEnroll(User user, WasteActivityEnroll wasteActivityEnroll) throws Exception {
        try {
            List<String> info = new ArrayList<String>();
            wasteActivityEnroll.setUserId(user.getUserId());
            wasteActivityEnroll.setEntId(user.getEnterpriseId());
            WasteActivityEnroll activityEnroll = checkEntrollStatus(wasteActivityEnroll);
            if (activityEnroll != null && StringUtils.isNotEmpty(activityEnroll.getId()) && (EnrollStatusEnum.PAYMENTSUCCESS.getCode().equals(activityEnroll.getBusiStatus()) || EnrollStatusEnum.SUBMIT.getCode().equals(activityEnroll.getBusiStatus()))) {
                info.add("你所在的企业已经报名,请勿重复报名");
                return info;
            }

            saveActivityEnroll(wasteActivityEnroll, user);
            sendEnrollInfo(wasteActivityEnroll, user);
            return info;
        } catch (Exception e) {
            logger.error("报名时异常", e);
            throw e;
        }

    }

    private void sendEnrollInfo(WasteActivityEnroll wasteActivityEnroll, User user) throws Exception {
        try {
            User sysAdmin = new User();
            sysAdmin.setUserId(Constant.SYS_ADMIN);
            MsgEvent msgEvent = new MsgEvent(SmsAction.ACCT_TRANSFER);
            msgEvent.setSendUser(sysAdmin);
            Map<String, String> placeholderValueMap = new HashMap<String, String>();
            placeholderValueMap.put("amount", String.valueOf(wasteActivityEnroll.getFee()));
            placeholderValueMap.put("accountNo", Constant.ADMIN_ACCT_NO);
            msgEvent.setRelId(wasteActivityEnroll.getActivityId());
            msgEvent.setPlaceholderValueMap(placeholderValueMap);
            List<User> userList = new ArrayList<User>();
            userList.add(sysAdmin);
            msgEvent.setSendUser(user);
            msgEvent.setReceiveUserList(userList);
            sysMsgServcie.sendMessageSync(msgEvent);
        } catch (Exception e) {
            logger.error("参加活动成功后通知用户时异常", e);
            throw e;
        }
    }

    private void saveActivityEnroll(WasteActivityEnroll wasteActivityEnroll, User user) {
        Date date = new Date();
        WasteActivity wasteActivity = wasteActivityService.selectById(wasteActivityEnroll.getActivityId());
        if (wasteActivity.getRiseEndDate().after(date) && wasteActivity.getEnrollFee() != 0.0) {
            Double fee = wasteActivity.getEnrollFee() * wasteActivity.getDiscount();
            wasteActivityEnroll.setFee(fee);
        } else {
            wasteActivityEnroll.setFee(wasteActivity.getEnrollFee());
        }
        wasteActivityEnroll.setCreateBy(user.getUserId());
        wasteActivityEnroll.setCreateTime(date);
        wasteActivityEnroll.setEditBy(user.getUserId());
        wasteActivityEnroll.setEditTime(date);
        wasteActivityEnroll.setBusiStatus(EnrollStatusEnum.SUBMIT.getCode());
        this.baseMapper.insert(wasteActivityEnroll);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean auditApprove(User user, List<String> ids) throws Exception {
        boolean approveResult = true;
        try {
            for (String id : ids) {
                WasteActivityEnroll wasteActivityEnroll = this.baseMapper.selectById(id);
                Date date = new Date();
                wasteActivityEnroll.setEditBy(user.getUserId());
                wasteActivityEnroll.setEditTime(date);
                wasteActivityEnroll.setBusiStatus(EnrollStatusEnum.PAYMENTSUCCESS.getCode());
                approveResult = approveResult && this.updateById(wasteActivityEnroll);
            }
        } catch (Exception e) {
            logger.error("付款成功状态更新时异常", e);
            throw e;
        }
        return approveResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean auditReject(User user, List<String> ids) throws Exception {
        boolean rejectResult = true;
        try {
            for (String id : ids) {
                WasteActivityEnroll wasteActivityEnroll = this.baseMapper.selectById(id);
                Date date = new Date();
                wasteActivityEnroll.setEditBy(user.getUserId());
                wasteActivityEnroll.setEditTime(date);
                wasteActivityEnroll.setBusiStatus(EnrollStatusEnum.PAYMENTFAILED.getCode());
                rejectResult =  rejectResult && this.updateById(wasteActivityEnroll);
            }
        } catch (Exception e) {
            logger.error("审付款失败状态更新时异常", e);
            throw e;
        }
        return rejectResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteEntroll(User user, List<String> ids) throws Exception {
        boolean deleteResult = true;
        try {
            for (String id : ids) {
                WasteActivityEnroll wasteActivityEnroll = this.baseMapper.selectById(id);
                Date date = new Date();
                wasteActivityEnroll.setEditBy(user.getUserId());
                wasteActivityEnroll.setEditTime(date);
                wasteActivityEnroll.setDeleteFlag(Constant.DELETED);
                deleteResult =  deleteResult && this.updateById(wasteActivityEnroll);
            }
        } catch (Exception e) {
            logger.error("删除报名时异常", e);
            throw e;
        }
        return deleteResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public WasteActivityEnroll checkEntrollStatus(WasteActivityEnroll wasteActivityEnroll) throws Exception {
        try {
            EntityWrapper<WasteActivityEnroll> ew = new EntityWrapper<WasteActivityEnroll>();
            ew.setSqlSelect("id,busiStatus");
            ew.eq("deleteFlag", 0);
            ew.eq("entId", wasteActivityEnroll.getEntId());
            ew.eq("activityId", wasteActivityEnroll.getActivityId());
            ew.orderBy(" editTime desc ");
            List<WasteActivityEnroll> activityEnroll = wasteActivityEnroll.selectList(ew);
            if (activityEnroll.size() > 0) {
                return activityEnroll.get(0);
            }
        } catch (Exception e) {
            logger.error("确认报名状态时异常", e);
            throw e;
        }
        return null;
    }

    @Override
    public List<WasteActivityEnrollModel> listActivityEnroll(WasteActivityEnrollModel wasteActivityEnrollModel, PagingParameter pagingParameter) throws Exception {
        List<WasteActivityEnrollModel> enrollModelList = new ArrayList<WasteActivityEnrollModel>();
        try {
            Util.initPagingParameter(pagingParameter);
            wasteActivityEnrollModel.setStartRowIndex(pagingParameter.getStart());
            wasteActivityEnrollModel.setRows(pagingParameter.getLimit());
            int queryCount = this.baseMapper.countActivityEnroll(wasteActivityEnrollModel);
            if (queryCount > 0) {
                pagingParameter.setTotalRecord(queryCount);
                enrollModelList = this.baseMapper.listActivityEnroll(wasteActivityEnrollModel);
            }
        } catch (Exception e) {
            logger.error("系统管理员查看活动报名情况列表异常", e);
            throw e;
        }

        return enrollModelList;
    }

    @Override
    public List<WasteActivityEnrollModel> registrationEnrollActivity(WasteActivityEnrollModel wasteActivityEnrollModel, PagingParameter pagingParameter) throws Exception {
        List<WasteActivityEnrollModel> enrollModelList = new ArrayList<WasteActivityEnrollModel>();
        try {
            Util.initPagingParameter(pagingParameter);
            wasteActivityEnrollModel.setStartRowIndex(pagingParameter.getStart());
            wasteActivityEnrollModel.setRows(pagingParameter.getLimit());
            int queryCount = this.baseMapper.countRegistrationEnrollActivity(wasteActivityEnrollModel);
            if (queryCount > 0) {
                pagingParameter.setTotalRecord(queryCount);
                enrollModelList = this.baseMapper.registrationEnrollActivity(wasteActivityEnrollModel);
            }
        } catch (Exception e) {
            logger.error("系统管理员查看活动报名人数时异常", e);
            throw e;
        }

        return enrollModelList;
    }

    @Override
    public boolean crowdFundingSucceed(User user, WasteActivityEnrollModel wasteActivityEnrollModel) throws Exception {
        List<WasteActivityEnrollModel> enrollModelList = new ArrayList<WasteActivityEnrollModel>();
        try {
            String busiStatus = wasteActivityEnrollModel.getBusiStatus();
            wasteActivityEnrollModel.setBusiStatus("");
            enrollModelList = this.baseMapper.listActivityEnroll(wasteActivityEnrollModel);
            if (enrollModelList != null && enrollModelList.size() > 0) {
                sendCrowdFundingMsg(user, busiStatus, wasteActivityEnrollModel, enrollModelList);
                return true;
            }
        } catch (Exception e) {
            logger.error("活动通知用户失败", e);
            throw e;
        }
        return false;
    }

    private void sendCrowdFundingMsg(User user, String busiStatus, WasteActivityEnrollModel wasteActivityEnrollModel, List<WasteActivityEnrollModel> enrollModelList) throws Exception {
        MsgEvent msgEvent = new MsgEvent(SmsAction.CROWD_FUNDING_SUCCEED);
        List<String> enterIdList = new ArrayList<String>();
        if (Constant.FAIL.equalsIgnoreCase(busiStatus)) {
            msgEvent = new MsgEvent(SmsAction.CROWD_FUNDING_FAILED);
        }
        for (WasteActivityEnrollModel enrollModel : enrollModelList) {
            enterIdList.add(enrollModel.getEntId());
        }
        List<User> receiveUserList = userInfoService.listUserInfoByEnterIds(enterIdList);
        try {
            if (receiveUserList != null && receiveUserList.size() > 0) {
                msgEvent.setSendUser(user);
                Map<String, String> placeholderValueMap = new HashMap<String, String>();
                placeholderValueMap.put("activityName", wasteActivityEnrollModel.getActivityName());
                msgEvent.setRelId(wasteActivityEnrollModel.getActivityId());
                msgEvent.setPlaceholderValueMap(placeholderValueMap);
                msgEvent.setSendUser(user);
                msgEvent.setReceiveUserList(receiveUserList);
                sysMsgServcie.sendMessageSync(msgEvent);
            }
        } catch (Exception e) {
            logger.error("直通开通或取消通知时异常", e);
            throw e;
        }

    }
}
