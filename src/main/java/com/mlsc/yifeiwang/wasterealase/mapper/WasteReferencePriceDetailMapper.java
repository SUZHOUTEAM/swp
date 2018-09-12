package com.mlsc.yifeiwang.wasterealase.mapper;

import com.mlsc.yifeiwang.wasterealase.entity.WasteReferencePriceDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.wasterealase.model.WasteReferencePriceModel;
import com.mlsc.yifeiwang.wasterealase.model.WasteReferencePriceParam;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-05-24
 */
public interface WasteReferencePriceDetailMapper extends BaseMapper<WasteReferencePriceDetail> {

    WasteReferencePriceModel getPriceByDefaultDispositionType(WasteReferencePriceParam priceParam);

    WasteReferencePriceModel getPriceByParentAndAmount(WasteReferencePriceParam priceParam);
}