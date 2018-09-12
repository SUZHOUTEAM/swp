package com.mlsc.yifeiwang.entorder.mapper;

import com.mlsc.yifeiwang.entorder.entity.EntOrderDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailModel;
import com.mlsc.yifeiwang.entorder.model.OrderDetailModel;
import com.mlsc.yifeiwang.entorder.model.OrderParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yinxl
 * @since 2017-10-09
 */
public interface EntOrderDetailMapper extends BaseMapper<EntOrderDetail> {

    List<OrderDetailModel> listOrderDetailByOrderId(@Param(value = "orderId") String orderId);

    void updateOrderDetail(EntOrderDetail orderDetail);

    List<EntReleaseDetailModel> countOrderWasteAmount(OrderParam orderParam);

    List<OrderDetailModel> listTotalPrice(OrderParam orderParam);
}