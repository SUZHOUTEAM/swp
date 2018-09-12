package com.mlsc.yifeiwang.wasterealase.mapper;

import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseBonus;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseBonusModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseBonusParam;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-04-09
 */
public interface EntReleaseBonusMapper extends BaseMapper<EntReleaseBonus> {

    boolean recievedBonus(EntReleaseBonus entReleaseBonus);

    boolean maxEntLimit(EntReleaseBonus entReleaseBonus);

    boolean maxActivityLimit5(EntReleaseBonus entReleaseBonus);

    int countEntReleaseBonus(EntReleaseBonusParam bonusParam);

    List<EntReleaseBonusModel> listEntReleaseBonus(EntReleaseBonusParam bonusParam);
}