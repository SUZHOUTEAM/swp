package com.mlsc.yifeiwang.wasterealase.service.impl;

import com.mlsc.yifeiwang.wasterealase.common.BonusStatus;
import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseBonus;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.sms.common.SmsTemplateCode;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;
import com.mlsc.yifeiwang.sms.service.SmsService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.wasterealase.mapper.EntReleaseBonusMapper;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseBonusModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseBonusParam;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseBonusService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2018-04-09
 */
@Service
public class EntReleaseBonusServiceImpl extends ServiceImpl<EntReleaseBonusMapper, EntReleaseBonus> implements IEntReleaseBonusService {
    private final static Logger logger = LoggerFactory.getLogger(EntReleaseBonusServiceImpl.class);
    @Autowired
    private SmsService smsService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveReleaseBonus(User user, EntReleaseBonus entReleaseBonus) throws Exception {
        String status = null;
        try {
            entReleaseBonus.setEntId(user.getEnterpriseId());
            entReleaseBonus.setUserId(user.getUserId());
            status = checkBonusCondition(entReleaseBonus);
            if (StringUtils.isEmpty(status)) {
                Date currentDate = new Date();
                entReleaseBonus.setBusiStatus(BonusStatus.SUBMIT.getCode());
                entReleaseBonus.setCreateBy(user.getUserId());
                entReleaseBonus.setCreateTime(currentDate);
                entReleaseBonus.setEditBy(user.getUserId());
                entReleaseBonus.setEditTime(currentDate);
                if (Constant.NOT_WEIXIN_BROWSER.equals(entReleaseBonus.getBrowserType())) {
                    String token = getSix();
                    entReleaseBonus.setToken(token);
                    entReleaseBonus.setBusiStatus(BonusStatus.SEND.getCode());
                    this.baseMapper.insert(entReleaseBonus);
                    sendBonusMsg(user, token, SmsTemplateCode.SMS_130926247);
                } else if (Constant.WEIXIN_BROWSER.equals(entReleaseBonus.getBrowserType())) {
                    this.baseMapper.insert(entReleaseBonus);
                }

            }
        } catch (Exception e) {
            logger.error("用户发布领取红包时异常", e);
            throw e;
        }
        return status;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean sendBonusToken(User user, EntReleaseBonus entReleaseBonus) throws Exception {
        try {
            entReleaseBonus.setEntId(user.getEnterpriseId());
            entReleaseBonus = this.selectById(entReleaseBonus.getId());
            if (entReleaseBonus != null && StringUtils.isEmpty(entReleaseBonus.getToken())) {
                String token = getSix();
                Date currentDate = new Date();
                entReleaseBonus.setToken(token);
                entReleaseBonus.setBusiStatus(BonusStatus.SEND.getCode());
                entReleaseBonus.setEditBy(user.getUserId());
                entReleaseBonus.setEditTime(currentDate);
                if(this.updateById(entReleaseBonus)){
                    sendBonusMsg(user, token, SmsTemplateCode.SMS_130916150);
                    return true;
                }else{
                    return false;
                }
            }

        } catch (Exception e) {
            logger.error("发送红包口令时异常", e);
            throw e;
        }
        return false;
    }

    private void sendBonusMsg(User user, String token, String smgTemplate) throws Exception {
        try {
            Map<String, String> placeholderValueMap = new HashMap<String, String>();
            SendMsgParameter parameter = new SendMsgParameter();
            placeholderValueMap.put("token", token);
            parameter.setSmsParam(placeholderValueMap);
            parameter.setSmsTemplateCode(smgTemplate);
            parameter.setPhoneNum(user.getPhoneNo());
            smsService.sendMsg(parameter);
        } catch (Exception e) {
            logger.error("发送红包短信通知时异常", e);
            throw e;
        }

    }


    private String checkBonusCondition(EntReleaseBonus entReleaseBonus) {
        boolean recievedBonus = this.baseMapper.recievedBonus(entReleaseBonus);
        boolean maxEntLimit = this.baseMapper.maxEntLimit(entReleaseBonus);
        if (recievedBonus) {
            return "recievedBonus";
        } else if (maxEntLimit) {
            return "maxEntLimit";
        } else {
            return "";
        }
    }

    @Override
    public List<EntReleaseBonusModel> listEntReleaseBonus(PagingParameter paging, EntReleaseBonusParam bonusParam) throws Exception {
        List<EntReleaseBonusModel> entReleaseBonusModels = null;
        try {
            Util.initPagingParameter(paging);
            bonusParam.setStartRowIndex(paging.getStart());
            bonusParam.setRows(paging.getLimit());
            int count = this.baseMapper.countEntReleaseBonus(bonusParam);
            if (count > 0) {
                entReleaseBonusModels = this.baseMapper.listEntReleaseBonus(bonusParam);
                paging.setTotalRecord(count);
            }
            return entReleaseBonusModels;
        } catch (Exception e) {
            logger.error("获取发布奖励列表时异常", e);
            throw e;
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateRecievedStatus(User user, EntReleaseBonus entReleaseBonus) throws Exception {
        try {
            entReleaseBonus = this.selectById(entReleaseBonus.getId());
            if (entReleaseBonus != null) {
                Date currentDate = new Date();
                entReleaseBonus.setSendTime(currentDate);
                entReleaseBonus.setBusiStatus(BonusStatus.RECEIVED.getCode());
                entReleaseBonus.setEditBy(user.getUserId());
                entReleaseBonus.setEditTime(currentDate);
                return this.updateAllColumnById(entReleaseBonus);
            }

        } catch (Exception e) {
            logger.error("更新用户领取状态时异常", e);
            throw e;
        }
        return false;
    }


    public static String getSix() {
        int s = ((int) ((Math.random() * 9 + 1) * 100000));
        return s + "";
    }
}


