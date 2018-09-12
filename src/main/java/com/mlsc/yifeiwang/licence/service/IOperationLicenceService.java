package com.mlsc.yifeiwang.licence.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.licence.entity.OperationLicence;
import com.mlsc.yifeiwang.licence.entity.OperationLicenceDetail;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25 0025.
 */
public interface IOperationLicenceService extends IService<OperationLicence> {
    Boolean getLicenceCreateStatus(String endId);

}
