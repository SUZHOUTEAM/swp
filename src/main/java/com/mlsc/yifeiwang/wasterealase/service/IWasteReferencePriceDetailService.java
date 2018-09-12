package com.mlsc.yifeiwang.wasterealase.service;

import com.mlsc.yifeiwang.wasterealase.entity.WasteReferencePriceDetail;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.wasterealase.model.WasteReferencePriceModel;
import com.mlsc.yifeiwang.wasterealase.model.WasteReferencePriceParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-05-24
 */
public interface IWasteReferencePriceDetailService extends IService<WasteReferencePriceDetail> {

    WasteReferencePriceModel getPriceByDefaultDispositionType(WasteReferencePriceParam priceParam);

    WasteReferencePriceModel getPriceByParentAndAmount(WasteReferencePriceParam priceParam);
}
