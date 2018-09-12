package com.mlsc.yifeiwang.licence.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.entity.DispositionCapacitydetailRelease;
import com.mlsc.yifeiwang.licence.service.IDispositionCapacitydetailReleaseService;
import com.mlsc.yifeiwang.licence.mapper.DispositionCapacitydetailReleaseMapper;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25 0025.
 */
@Service
public class DispositionCapacitydetailReleaseServiceImpl extends ServiceImpl<DispositionCapacitydetailReleaseMapper, DispositionCapacitydetailRelease> implements IDispositionCapacitydetailReleaseService {

    @Override
    public List<EntWasteModel> listWasteInfoByReleaseDetailId(String  endId,String releaseId) throws Exception{
        List<EntWasteModel> entWasteList = this.baseMapper.listWasteInfoByReleaseDetailId(endId,releaseId);
        return entWasteList;
    }

}
