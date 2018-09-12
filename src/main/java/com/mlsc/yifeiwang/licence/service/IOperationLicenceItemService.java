package com.mlsc.yifeiwang.licence.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.licence.entity.OperationLicenceItem;

/**
 * Created by Administrator on 2017/8/25 0025.
 */
public interface IOperationLicenceItemService extends IService<OperationLicenceItem> {
    double getApprovedQuantity(String licenceId);
}
