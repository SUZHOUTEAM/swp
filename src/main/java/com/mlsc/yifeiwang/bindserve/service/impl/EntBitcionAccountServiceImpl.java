package com.mlsc.yifeiwang.bindserve.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.wasterealase.common.ChargeErrorCode;
import com.mlsc.yifeiwang.bindserve.entity.EntBitcionAccount;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.bindserve.mapper.EntBitcionAccountMapper;
import com.mlsc.yifeiwang.bindserve.model.EntBitcionAccountModel;
import com.mlsc.yifeiwang.bindserve.model.EntBitcionAccountParam;
import com.mlsc.yifeiwang.bindserve.service.IEntBitcionAccountService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2018-04-26
 */
@Service
public class EntBitcionAccountServiceImpl extends ServiceImpl<EntBitcionAccountMapper, EntBitcionAccount> implements IEntBitcionAccountService {
    private final static Logger logger = LoggerFactory.getLogger(EntBitcionAccountServiceImpl.class);

    @Override
    public EntBitcionAccountModel checkEntAccount(User user, EntBitcionAccountParam bitcionAccountParam) throws Exception {
        try {
            EntBitcionAccount entBitcionAccount = getAccountByUserId(user.getUserId());
            List<String> errorList = new ArrayList<String>();
            EntBitcionAccountModel entBitcionAccountModel = new EntBitcionAccountModel();
            if (entBitcionAccount == null) {
                errorList.add(ChargeErrorCode.INSUFFICIENT.getCode());
                entBitcionAccountModel.setBitcion(0);
                entBitcionAccountModel.setErrorList(errorList);
            } else {
                if (entBitcionAccount.getBitcion() < bitcionAccountParam.getConsumeAmount()) {
                    errorList.add(ChargeErrorCode.INSUFFICIENT.getCode());
                    entBitcionAccountModel.setBitcion(entBitcionAccount.getBitcion());
                    entBitcionAccountModel.setErrorList(errorList);
                } else {
                    entBitcionAccountModel.setBitcion(entBitcionAccount.getBitcion());
                }
                entBitcionAccountModel.setId(entBitcionAccount.getId());
            }
            return entBitcionAccountModel;
        } catch (Exception e) {
            logger.error("帐户余额确认及更新时出错", e);
            throw e;
        }


    }

    @Override
    public EntBitcionAccount getAccountByUserId(String userId) throws Exception {
        try {
            if (StringUtils.isNotBlank(userId)) {
                EntityWrapper<EntBitcionAccount> ew = new EntityWrapper<>();
                ew.setSqlSelect("id,userId,bitcion,version,createBy,createTime,editBy,editTime,deleteFlag\n");
                ew.eq("userId", userId);
                ew.eq("deleteFlag", 0);
                List<EntBitcionAccount> bitcionAccountList = this.selectList(ew);
                if (bitcionAccountList != null && bitcionAccountList.size() > 0) {
                    return bitcionAccountList.get(0);
                }
            }
        } catch (Exception e) {
            logger.error("根据用户id获取易废币帐户时异常", e);
            throw e;
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public EntBitcionAccount saveOrUpdateEntBitcionAccount(User user, EntBitcionAccountParam bitcionAccountParam) throws Exception {
        try {
            Date currentDate = new Date();
            EntBitcionAccount lastetEntity = this.getAccountByUserId(user.getUserId());
            if (lastetEntity != null) {
                if (bitcionAccountParam.getConsumeAmount() == 0) {
                    int recharge = lastetEntity.getBitcion() + bitcionAccountParam.getBitcion();
                    lastetEntity.setBitcion(recharge);
                } else {
                    lastetEntity.setBitcion(lastetEntity.getBitcion() - bitcionAccountParam.getConsumeAmount());
                }
                lastetEntity.setEditBy(user.getUserId());
                lastetEntity.setEditTime(currentDate);
                lastetEntity.setVersion(Util.uuid32());
                this.updateAllColumnById(lastetEntity);
                lastetEntity = this.selectById(lastetEntity.getId());
            } else {
                lastetEntity = saveBitcion(user, bitcionAccountParam.getBitcion());
            }
            return lastetEntity;

        } catch (Exception e) {
            logger.error("更新易废币帐户时异常", e);
            throw e;
        }

    }

    @Override
    public EntBitcionAccount saveBitcion(User user, int bitcion) throws Exception {
        try {
            if (!bitcionAccountExist(user)) {
                EntBitcionAccount bitcionAccount = new EntBitcionAccount();
                Date currentDate = new Date();
                bitcionAccount.setBitcion(bitcion);
                bitcionAccount.setUserId(user.getUserId());
//                bitcionAccount.setEntId(user.getEnterpriseId());
                bitcionAccount.setCreateBy(user.getUserId());
                bitcionAccount.setVersion(Util.uuid32());
                bitcionAccount.setCreateTime(currentDate);
                bitcionAccount.setEditBy(user.getUserId());
                bitcionAccount.setEditTime(currentDate);
                this.baseMapper.insert(bitcionAccount);
                return bitcionAccount;
            }
        } catch (Exception e) {
            logger.error("获取保存易废币时异常", e);
            throw e;
        }
        return null;
    }

    @Override
    public boolean bitcionAccountExist(User user) throws Exception {
        try {
            EntBitcionAccount bitcionAccount = getAccountByUserId(user.getUserId());
            if (bitcionAccount == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error("用户是否有易废币帐户时异常", e);
            throw e;
        }
    }

}
