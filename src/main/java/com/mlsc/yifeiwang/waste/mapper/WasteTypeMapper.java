package com.mlsc.yifeiwang.waste.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.waste.entity.Waste;
import com.mlsc.yifeiwang.waste.entity.WasteType;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangj
 * @since 2017-08-23
 */
public interface WasteTypeMapper extends BaseMapper<WasteType> {
    List<WasteType> listWasteInfo();

    List<WasteType> listWasteType();

    List<Waste> listWasteCode();
}