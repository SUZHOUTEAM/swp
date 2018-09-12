package com.mlsc.yifeiwang.wasterealase.service;

import com.mlsc.yifeiwang.wasterealase.entity.WasteReference;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailParam;
import com.mlsc.yifeiwang.wasterealase.model.WasteReferencePriceModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-05-24
 */
public interface IWasteReferenceService extends IService<WasteReference> {
    List<WasteReferencePriceModel> listDefDisposition(List<EntReleaseDetailParam> releaseDetailParams, String cantonCode);

}
