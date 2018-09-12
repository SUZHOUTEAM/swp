package com.mlsc.yifeiwang.licence.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.licence.entity.OperationLicenceItem;
import org.springframework.stereotype.Service;
import com.mlsc.yifeiwang.licence.mapper.OperationLicenceItemMapper;
import com.mlsc.yifeiwang.licence.service.IOperationLicenceItemService;


/**
 * Created by Administrator on 2017/8/25 0025.
 */
@Service
public class OperationLicenceItemServiceImpl extends ServiceImpl<OperationLicenceItemMapper, OperationLicenceItem> implements IOperationLicenceItemService {

    @Override
    public double getApprovedQuantity(String licenceId) {
        return this.baseMapper.getApprovedQuantity(licenceId);
    }
}
