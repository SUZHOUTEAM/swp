package com.mlsc.yifeiwang.recordcontract.service.impl;

import cn.jiguang.common.utils.StringUtils;
import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContractDetail;
import com.mlsc.yifeiwang.recordcontract.mapper.EntRecordContractDetailMapper;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractDetailModel;
import com.mlsc.yifeiwang.recordcontract.service.IEntRecordContractDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2018-03-01
 */
@Service
public class EntRecordContractDetailServiceImpl extends ServiceImpl<EntRecordContractDetailMapper, EntRecordContractDetail> implements IEntRecordContractDetailService {
    private final static Logger logger = LoggerFactory.getLogger(EntRecordContractServiceImpl.class);

    @Override
    public List<EntRecordContractDetailModel> listContractDetailByRecordId(String recordId) throws Exception {
        List<EntRecordContractDetailModel> recordContractDetailModelList = null;
        try {
            if (StringUtils.isNotEmpty(recordId)) {
                recordContractDetailModelList = this.baseMapper.listContractDetailByRecordId(recordId);
            }
        } catch (Exception e) {
            logger.error("根据备案id获取备案明细时异常", e);
            throw e;
        }
        return recordContractDetailModelList;
    }
}
