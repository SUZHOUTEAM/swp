package com.mlsc.yifeiwang.licence.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.licence.entity.OperationLicence;
import com.mlsc.yifeiwang.licence.entity.OperationLicenceItem;
import com.mlsc.yifeiwang.licence.mapper.OperationLicenceItemMapper;
import com.mlsc.yifeiwang.licence.mapper.OperationLicenceMapper;
import com.mlsc.yifeiwang.licence.service.IOperationLicenceItemService;
import com.mlsc.yifeiwang.licence.service.IOperationLicenceService;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 2017/8/25 0025.
 */
@Service
public class OperationLicenceServiceImpl extends ServiceImpl<OperationLicenceMapper, OperationLicence> implements IOperationLicenceService {


    @Override
    public Boolean getLicenceCreateStatus(String endId) {
        return this.baseMapper.getLicenceCreateStatus(endId);
    }
}
