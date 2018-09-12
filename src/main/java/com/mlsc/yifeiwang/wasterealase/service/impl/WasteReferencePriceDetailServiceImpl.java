package com.mlsc.yifeiwang.wasterealase.service.impl;

import com.mlsc.yifeiwang.wasterealase.entity.WasteReferencePriceDetail;
import com.mlsc.yifeiwang.wasterealase.mapper.WasteReferencePriceDetailMapper;
import com.mlsc.yifeiwang.wasterealase.model.WasteReferencePriceModel;
import com.mlsc.yifeiwang.wasterealase.model.WasteReferencePriceParam;
import com.mlsc.yifeiwang.wasterealase.service.IWasteReferencePriceDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2018-05-24
 */
@Service
public class WasteReferencePriceDetailServiceImpl extends ServiceImpl<WasteReferencePriceDetailMapper, WasteReferencePriceDetail> implements IWasteReferencePriceDetailService {

    @Override
    public WasteReferencePriceModel getPriceByDefaultDispositionType(WasteReferencePriceParam priceParam) {
        return this.baseMapper.getPriceByDefaultDispositionType(priceParam);
    }

    @Override
    public WasteReferencePriceModel getPriceByParentAndAmount(WasteReferencePriceParam priceParam) {
        return this.baseMapper.getPriceByParentAndAmount(priceParam);

    }
}
