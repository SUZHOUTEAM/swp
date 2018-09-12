package com.mlsc.yifeiwang.bindserve.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.bindserve.entity.EntRecharge;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.bindserve.model.EntRechargeParam;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-04-26
 */
public interface IEntRechargeService extends IService<EntRecharge> {

    String saveEntRecharge(User user, EntRechargeParam entRechargeParam);

    boolean updateRechargeStatus(User user, EntRechargeParam entBindServeParam) throws Exception;

    EntRecharge selectEntRecharge(EntRechargeParam entBindServeParam) throws Exception;

    Page<EntRecharge> listRecharge(EntRechargeParam entBindServeParam, Page<EntRecharge> page) throws Exception;

    void updateUserAccount(String orderNo) throws Exception;

    boolean delete(String id);
}
