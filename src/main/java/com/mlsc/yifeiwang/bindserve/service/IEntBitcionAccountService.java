package com.mlsc.yifeiwang.bindserve.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.bindserve.entity.EntBitcionAccount;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.bindserve.model.EntBitcionAccountModel;
import com.mlsc.yifeiwang.bindserve.model.EntBitcionAccountParam;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-04-26
 */
public interface IEntBitcionAccountService extends IService<EntBitcionAccount> {

    EntBitcionAccount getAccountByUserId(String userId) throws Exception;

    EntBitcionAccountModel checkEntAccount(User user, EntBitcionAccountParam bitcionAccountParam) throws Exception;

    EntBitcionAccount saveOrUpdateEntBitcionAccount(User user, EntBitcionAccountParam bitcionAccountParam) throws Exception;

    EntBitcionAccount saveBitcion(User user, int bitcion) throws Exception;

    boolean bitcionAccountExist(User user) throws Exception;
}
