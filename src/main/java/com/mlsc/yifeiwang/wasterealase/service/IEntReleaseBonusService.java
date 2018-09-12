package com.mlsc.yifeiwang.wasterealase.service;

import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseBonus;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseBonusModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseBonusParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-04-09
 */
public interface IEntReleaseBonusService extends IService<EntReleaseBonus> {

    String saveReleaseBonus(User user, EntReleaseBonus entReleaseBonus) throws Exception;

    boolean sendBonusToken(User user, EntReleaseBonus entReleaseBonus) throws Exception;

    boolean updateRecievedStatus(User user, EntReleaseBonus entReleaseBonus)  throws Exception;

    List<EntReleaseBonusModel> listEntReleaseBonus(PagingParameter paging, EntReleaseBonusParam bonusParam)  throws Exception;
}
