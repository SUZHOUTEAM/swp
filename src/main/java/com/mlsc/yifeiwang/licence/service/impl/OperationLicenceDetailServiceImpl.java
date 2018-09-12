package com.mlsc.yifeiwang.licence.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.licence.entity.OperationLicenceDetail;
import com.mlsc.yifeiwang.licence.mapper.OperationLicenceDetailMapper;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.licence.service.IOperationLicenceDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018/5/29.
 */
@Service
class OperationLicenceDetailServiceImpl extends ServiceImpl<OperationLicenceDetailMapper, OperationLicenceDetail> implements IOperationLicenceDetailService {
    @Override
    public List<EntWasteModel> listWasteInfoByLicenceId(String endId, String releaseId) {
        return this.baseMapper.listWasteInfoByLicenceId(endId, releaseId);
    }
}


