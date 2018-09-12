package com.mlsc.yifeiwang.operaction.service;

import com.mlsc.yifeiwang.operaction.entity.OutsourcingDisposal;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-21
 */
public interface IOutsourcingDisposalService extends IService<OutsourcingDisposal> {
    List<OutsourcingDisposal> listOutSouringDisposalEnterprise(OutsourcingDisposal outsourcingDisposal) throws Exception;
}
