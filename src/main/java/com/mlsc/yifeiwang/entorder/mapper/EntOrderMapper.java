package com.mlsc.yifeiwang.entorder.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.entorder.entity.EntOrder;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.entorder.model.OrderModel;
import com.mlsc.yifeiwang.entorder.model.OrderParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yinxl
 * @since 2017-10-09
 */
public interface EntOrderMapper extends BaseMapper<EntOrder> {

    int countOrderList(OrderParam orderParam);

    List<OrderModel> listOrderList(OrderParam orderParam);

    List<SysEnterpriseBase> listReleaseEnt(@Param(value="enterpriseId")String enterpriseId);
}