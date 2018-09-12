package com.mlsc.yifeiwang.wasterealase.mapper;

import com.mlsc.yifeiwang.wasterealase.entity.WasteReference;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.wasterealase.model.WasteReferencePriceParam;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-05-24
 */
public interface WasteReferenceMapper extends BaseMapper<WasteReference> {
    List<WasteReference> listDefDisposition(WasteReferencePriceParam referencePriceParam);
}