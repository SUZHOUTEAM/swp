package com.mlsc.yifeiwang.licence.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.entity.DispositionCapacitydetailRelease;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;

import java.util.List;

/**
 * Created by user on 2018/4/26.
 */
public interface IDispositionCapacitydetailReleaseService extends IService<DispositionCapacitydetailRelease> {
    List<EntWasteModel> listWasteInfoByReleaseDetailId(String entId,String enterBusId) throws Exception ;
}
