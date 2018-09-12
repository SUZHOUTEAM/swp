package com.mlsc.yifeiwang.licence.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.licence.entity.OperationLicenceDetail;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25 0025.
 */
public interface IOperationLicenceDetailService extends IService<OperationLicenceDetail> {
    List<EntWasteModel> listWasteInfoByLicenceId(String endId, String releaseId);

}
