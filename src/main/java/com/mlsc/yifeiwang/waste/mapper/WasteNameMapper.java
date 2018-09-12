package com.mlsc.yifeiwang.waste.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.waste.entity.WasteName;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangj
 * @since 2017-08-23
 */
public interface WasteNameMapper extends BaseMapper<WasteName> {
    int getWasteNameId(WasteName wasteName);

    List<WasteName> listWasteName(WasteName wasteName);
}