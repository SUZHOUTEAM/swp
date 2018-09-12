package com.mlsc.yifeiwang.bindserve.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.bindserve.common.EntServeType;
import com.mlsc.yifeiwang.bindserve.common.PayStatus;
import com.mlsc.common.util.StringUtils;
import com.mlsc.yifeiwang.bindserve.entity.EntBindServe;
import com.mlsc.yifeiwang.bindserve.entity.EntBitcionAccount;
import com.mlsc.yifeiwang.activity.entity.WasteActivity;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import com.mlsc.yifeiwang.activity.service.IWasteActivityService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.UserRole;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.bindserve.mapper.EntBindServeMapper;
import com.mlsc.yifeiwang.bindserve.model.EntBindServeModel;
import com.mlsc.yifeiwang.bindserve.model.EntBindServeParam;
import com.mlsc.yifeiwang.bindserve.model.EntBitcionAccountModel;
import com.mlsc.yifeiwang.bindserve.model.EntBitcionAccountParam;
import com.mlsc.yifeiwang.bindserve.service.IEntBindServeService;
import com.mlsc.yifeiwang.bindserve.service.IEntBitcionAccountService;
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
 * @since 2018-04-26
 */
@Service
public class EntBindServeServiceImpl extends ServiceImpl<EntBindServeMapper, EntBindServe> implements IEntBindServeService {
    private final static Logger logger = LoggerFactory.getLogger(EntBindServeServiceImpl.class);
    @Autowired
    private IEntBitcionAccountService entBitcionAccountService;
    @Autowired
    private IWasteActivityService wasteActivityService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private SysMsgServcie sysMsgServcie;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<String> saveBindServe(User user, EntBindServeParam entBindServeParam) throws Exception {
        try {
            if (entBindServeParam.getConsumeAmount() <= 0) return null;

            EntBitcionAccountParam bitcionAccountParam = new EntBitcionAccountParam();
            bitcionAccountParam.setConsumeAmount(entBindServeParam.getConsumeAmount());
            bitcionAccountParam.setEntId(user.getEnterpriseId());

            List<String> errorList = listEntAccountErrors(user, bitcionAccountParam);
            if (Util.isNotEmpty(errorList)) {
                return errorList;
            } else {
                saveOrUpdateEntBitcionAccount(user, entBindServeParam, bitcionAccountParam);
                informUser(user, entBindServeParam);
                informSystemAdmin(user, entBindServeParam);
            }

        } catch (Exception e) {
            logger.error("企业绑定服务时异常", e);
            throw e;
        }

        return null;
    }

    private void informUser(User user, EntBindServeParam entBindServeParam) throws Exception {
        switch (entBindServeParam.getServiceType()) {
            case "GOLDEN":
            case "SLIVER":
            case "CUPRUM":
                User sysAdmin = new User();
                sysAdmin.setUserId(Constant.SYS_ADMIN);
                MsgEvent msgEvent = new MsgEvent(SmsAction.BIND_SET);
                msgEvent.setSendUser(sysAdmin);
                Map<String, String> placeholderValueMap = new HashMap<String, String>();
                placeholderValueMap.put("setName", EntServeType.getNameByCode(entBindServeParam.getServiceType()));
                msgEvent.setPlaceholderValueMap(placeholderValueMap);
                msgEvent.setSendUser(sysAdmin);
                msgEvent.setReceiveUser(user);
                sysMsgServcie.sendMessageSync(msgEvent);
                break;
            default:
                break;
        }


    }


    private void informSystemAdmin(User user, EntBindServeParam entBindServeParam) {
        try {
            List<User> sysAdminUserList = userInfoService.listSysAdminUser(UserRole.ADMIN.getRoleCode());
            if (sysAdminUserList != null && sysAdminUserList.size() > 0) {
                User sysAdmin = new User();
                sysAdmin.setUserId(Constant.SYS_ADMIN);
                MsgEvent msgEvent = new MsgEvent(SmsAction.BIND_SET_SYSTEM);
                msgEvent.setSendUser(sysAdmin);
                Map<String, String> placeholderValueMap = new HashMap<String, String>();
                placeholderValueMap.put("entName", user.getEnterpriseName());
                placeholderValueMap.put("userName", user.getUserName());
                placeholderValueMap.put("setName", EntServeType.getNameByCode(entBindServeParam.getServiceType()));
                msgEvent.setPlaceholderValueMap(placeholderValueMap);
                msgEvent.setSendUser(sysAdmin);
                msgEvent.setReceiveUserList(sysAdminUserList);
                sysMsgServcie.sendMessageSync(msgEvent);
            }
        } catch (Exception e) {
            logger.error("通知工程师查看购买套餐的用户时异常", e);
        }


    }

    private void saveOrUpdateEntBitcionAccount(User user, EntBindServeParam entBindServeParam, EntBitcionAccountParam bitcionAccountParam) throws Exception {
        EntBitcionAccount remainAccount = entBitcionAccountService.saveOrUpdateEntBitcionAccount(user, bitcionAccountParam);
        EntBindServe entBindServe = saveBindServeInfo(user, entBindServeParam, remainAccount);
        updateActivityStatus(entBindServe, user);
        if (entBindServe != null) {
            entBindServeParam.setId(entBindServe.getId());
        }
    }

    private List<String> listEntAccountErrors(User user, EntBitcionAccountParam bitcionAccountParam) throws Exception {
        EntBitcionAccountModel entBitcionAccount = entBitcionAccountService.checkEntAccount(user, bitcionAccountParam);
        List<String> errorList = entBitcionAccount.getErrorList();
        return errorList;
    }

    private void updateActivityStatus(EntBindServe entBindServe, User user) throws Exception {
        try {
            if (entBindServe.getServiceType().equalsIgnoreCase(EntServeType.ACTIVITY.getCode())) {
                WasteActivity wasteActivity = new WasteActivity();
                wasteActivity.setPayStatus(PayStatus.PAYMENT.getCode());
                List<String> ids = new ArrayList<String>();
                ids.add(entBindServe.getBindServiceId());
                wasteActivityService.updateActivity(ids, wasteActivity, user);
            }
        } catch (Exception e) {
            logger.error("企业绑定服务时异常", e);
            throw e;
        }

    }

    private EntBindServe saveBindServeInfo(User user, EntBindServeParam entBindServeParam, EntBitcionAccount remainAccount) throws Exception {
        EntBindServe entBindServe = new EntBindServe();
        entBindServe.setBindEntId(user.getEnterpriseId());
        entBindServe.setBindUserId(user.getUserId());
        entBindServe.setBindServiceId(entBindServeParam.getBindServiceId());
        entBindServe.setServiceType(entBindServeParam.getServiceType());
        entBindServe.setConsumeAmount(entBindServeParam.getConsumeAmount());
        entBindServe.setRemainAmount(remainAccount.getBitcion());
        entBindServe.setRemark(entBindServeParam.getRemark());
        Date currentDate = new Date();
        entBindServe.setConsumeTime(currentDate);
        entBindServe.setCreateBy(user.getUserId());
        entBindServe.setCreateTime(currentDate);
        entBindServe.setEditBy(user.getUserId());
        entBindServe.setEditTime(currentDate);
        this.insert(entBindServe);
        return entBindServe;
    }


    @Override
    public void updateBindServe(User user, EntBindServeParam entBindServeParam) throws Exception {
        if (StringUtils.isNotNullOrEmpty(entBindServeParam.getId())) {
            EntBindServe entBindServe = this.selectById(entBindServeParam.getId());
            if (StringUtils.isNotNullOrEmpty(entBindServeParam.getRemark())) {
                entBindServe.setRemark(entBindServeParam.getRemark());
            }
            Date currentDate = new Date();
            entBindServe.setEditBy(user.getUserId());
            entBindServe.setEditTime(currentDate);

            this.updateById(entBindServe);
        }

    }

    @Override
    public List<EntBindServeModel> listBindServe(EntBindServeParam entBindServeParam, PagingParameter pagingParameter) throws Exception {
        try {
            int count = this.baseMapper.countBindServe(entBindServeParam);
            List<EntBindServeModel> entBindServes = null;
            if (count > 0) {
                pagingParameter.setTotalRecord(count);
                Util.initPagingParameter(pagingParameter);
                entBindServeParam.setStartRowIndex(pagingParameter.getStart());
                entBindServeParam.setRows(pagingParameter.getLimit());
                entBindServes = this.baseMapper.listBindServe(entBindServeParam);
                entBindServes.forEach(entBindServe -> {
                    entBindServe.setServiceType(EntServeType.getNameByCode(entBindServe.getServiceType()));
                });
            }
            return entBindServes;

        } catch (Exception e) {
            logger.error("处置企业用户消费易废币记录时异常", e);
            throw e;
        }
    }

    @Override
    public List<EntBindServe> listButlerServicesByEntId(EntBindServeParam entBindServeParam) {
        EntityWrapper<EntBindServe> ew = new EntityWrapper<>();
        ew.setSqlSelect("id,bindServiceId,serviceType");
        ew.eq("bindEntId",entBindServeParam.getBindEntId());
        ew.in("serviceType","CUPRUM,SLIVER,GOLDEN");
        ew.orderBy("createTime",false);
        List<EntBindServe> entBindServes = this.selectList(ew);
        return entBindServes;
    }

}
