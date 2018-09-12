package com.mlsc.yifeiwang.operaction.mapper;

import com.mlsc.yifeiwang.operaction.entity.OutsourcingDisposal;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-11-21
 */
public interface OutsourcingDisposalMapper extends BaseMapper<OutsourcingDisposal> {
    List<OutsourcingDisposal> listOutSouringDisposalEnterprise(OutsourcingDisposal outsourcingDisposal);
}