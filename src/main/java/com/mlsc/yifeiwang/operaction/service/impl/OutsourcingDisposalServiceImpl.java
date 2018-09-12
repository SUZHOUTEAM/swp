package com.mlsc.yifeiwang.operaction.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.operaction.entity.OutsourcingDisposal;
import com.mlsc.yifeiwang.operaction.mapper.OutsourcingDisposalMapper;
import com.mlsc.yifeiwang.operaction.service.IOutsourcingDisposalService;
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
 * @since 2017-11-21
 */
@Service
public class OutsourcingDisposalServiceImpl extends ServiceImpl<OutsourcingDisposalMapper, OutsourcingDisposal> implements IOutsourcingDisposalService {
    private final static Logger logger = LoggerFactory.getLogger(OutsourcingDisposalServiceImpl.class);

    @Override
    public List<OutsourcingDisposal> listOutSouringDisposalEnterprise(OutsourcingDisposal outsourcingDisposal) throws Exception {
        List<OutsourcingDisposal> list = null;
        try {
            list = this.baseMapper.listOutSouringDisposalEnterprise(outsourcingDisposal);
        } catch (Exception e) {
            logger.error("获取委外处置企业时异常", e);
            throw e;
        }
        return list;
    }
}
