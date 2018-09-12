package com.mlsc.yifeiwang.bindserve.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.bindserve.common.RechargeStatus;
import com.mlsc.common.util.AlipayUtil;
import com.mlsc.common.util.StringUtils;
import com.mlsc.yifeiwang.bindserve.entity.EntRecharge;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.bindserve.service.IEntRechargeService;
import com.mlsc.yifeiwang.bindserve.mapper.EntRechargeMapper;
import com.mlsc.yifeiwang.bindserve.model.EntBitcionAccountParam;
import com.mlsc.yifeiwang.bindserve.model.EntRechargeParam;
import com.mlsc.yifeiwang.bindserve.service.IEntBitcionAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2018-04-26
 */
@Service
@Repository("entRechargeService")
public class EntRechargeServiceImpl extends ServiceImpl<EntRechargeMapper, EntRecharge> implements IEntRechargeService {
    private final static Logger logger = LoggerFactory.getLogger(EntRechargeServiceImpl.class);
    private final static String SUBJECT = "购买易废币";

    @Autowired
    private IEntBitcionAccountService bitcionAccountService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveEntRecharge(User user, EntRechargeParam entRechargeParam) {
        try {
            if (entRechargeParam.getPrice() != null) {
                String orderNo = saveEntRechargeInfo(user, entRechargeParam);
                return orderNo;
            }
        } catch (Exception e) {
            logger.error("企业充值时异常", e);
        }
        return null;
    }

    @Override
    public EntRecharge selectEntRecharge(EntRechargeParam entBindServeParam) throws Exception {
        try {
            EntityWrapper<EntRecharge> ew = new EntityWrapper<>();
            ew.setSqlSelect("id,entId,userId,orderNo,price,bitCionAmount,busiStatus,remark,createBy,createTime,editBy,editTime,deleteFlag,orderName\n");
            if (StringUtils.isNotNullOrEmpty(entBindServeParam.getId())) {
                ew.eq("id", entBindServeParam.getId());
            }
            if (StringUtils.isNotNullOrEmpty(entBindServeParam.getOrderNo())) {
                ew.eq("orderNo", entBindServeParam.getOrderNo());
            }
            ew.eq("deleteFlag", 0);
            return this.selectOne(ew);

        } catch (Exception e) {
            logger.error("根据id或订单号获取充值记录时异常", e);
            throw e;
        }
    }

    private String saveEntRechargeInfo(User user, EntRechargeParam entRechargeParam) throws Exception {
        try {
            EntRecharge recharge = new EntRecharge();
            recharge.setEntId(user.getEnterpriseId());
            recharge.setUserId(user.getUserId());
            recharge.setPrice(entRechargeParam.getPrice());
            recharge.setBitCionAmount(entRechargeParam.getBitCionAmount());
            recharge.setBusiStatus(RechargeStatus.SUBMIT.getCode());
            recharge.setOrderName(entRechargeParam.getOrderName());
            recharge.setOrderNo(Util.uuid32());
            recharge.setRemark(entRechargeParam.getRemark());
            Date currentDate = new Date();
            recharge.setCreateBy(user.getUserId());
            recharge.setCreateTime(currentDate);
            recharge.setEditBy(user.getUserId());
            recharge.setEditTime(currentDate);
            this.baseMapper.insert(recharge);
            return recharge.getOrderNo();
        } catch (Exception e) {
            logger.error("企业充值时异常", e);
            throw e;
        }
    }


    @Override
    public boolean updateRechargeStatus(User user, EntRechargeParam entBindServeParam) throws Exception {
        try {
            EntRecharge entRecharge = selectEntRecharge(entBindServeParam);
            Date currentDate = new Date();
            entRecharge.setEditBy(user.getUserId());
            entRecharge.setEditTime(currentDate);
            entRecharge.setBusiStatus(RechargeStatus.SUCCESS.getCode());
            return this.updateAllColumnById(entRecharge);
        } catch (Exception e) {
            logger.error("更新充值帐户状态时异常", e);
            throw e;
        }

    }


    @Override
    public Page<EntRecharge> listRecharge(EntRechargeParam entBindServeParam, Page<EntRecharge> page) throws Exception {
        EntityWrapper<EntRecharge> ew = new EntityWrapper<>();
        try {
            ew.setSqlSelect("id,entId,orderNo,price,bitCionAmount,busiStatus,remark,orderName,createTime");
//            if (StringUtils.isNotNullOrEmpty(entBindServeParam.getEntId())) {
//                ew.eq("entId", entBindServeParam.getEntId());
//            }
            if (StringUtils.isNotNullOrEmpty(entBindServeParam.getUserId())) {
                ew.eq("userId", entBindServeParam.getUserId());
            }
            ew.eq("deleteFlag", 0);
            ew.orderBy("createTime desc");
            Page<EntRecharge> entBindServePage = this.selectPage(page, ew);
            return entBindServePage;
        } catch (Exception e) {
            logger.error("处置企业充值易废币记录列表时异常", e);
            throw e;
        }
    }

    @Override
    public void updateUserAccount(String orderNo) throws Exception {
        try {
            if (StringUtils.isNotNullOrEmpty(orderNo)) {
                EntRechargeParam entBindServeParam = new EntRechargeParam();
                entBindServeParam.setOrderNo(orderNo);
                EntRecharge entRecharge = selectEntRecharge(entBindServeParam);
                if (entRecharge != null && RechargeStatus.SUBMIT.getCode().equalsIgnoreCase(entRecharge.getBusiStatus())) {
                    User sysUser = new User();
                    sysUser.setUserId(Constant.SYS_ADMIN);
                    entBindServeParam.setBusiStatus(RechargeStatus.SUCCESS.getCode());
                    boolean updateStatus = updateRechargeStatus(sysUser, entBindServeParam);
                    if (updateStatus) {
                        User user = new User();
                        user.setUserId(entRecharge.getUserId());
                        saveOrUpdateEntBitcionAccount(user, entBindServeParam);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("异步更新用户信息时异常", e);
            throw e;
        }
    }

    @Override
    public boolean delete(String id) {
        return this.deleteById(id);
    }

    private void saveOrUpdateEntBitcionAccount(User user, EntRechargeParam entBindServeParam) throws Exception {
        EntBitcionAccountParam bitcionAccountParam = new EntBitcionAccountParam();
        EntRecharge entRecharge = selectEntRecharge(entBindServeParam);
        bitcionAccountParam.setUserId(entRecharge.getUserId());
        bitcionAccountParam.setBitcion(entRecharge.getBitCionAmount());
        bitcionAccountService.saveOrUpdateEntBitcionAccount(user, bitcionAccountParam);
    }

}
