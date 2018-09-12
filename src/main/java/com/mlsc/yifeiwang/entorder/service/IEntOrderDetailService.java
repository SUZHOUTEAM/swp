package com.mlsc.yifeiwang.entorder.service;

import com.mlsc.yifeiwang.entorder.entity.EntOrderDetail;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailModel;
import com.mlsc.yifeiwang.entorder.model.OrderDetailModel;
import com.mlsc.yifeiwang.entorder.model.OrderParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-10-09
 */
public interface IEntOrderDetailService extends IService<EntOrderDetail> {
    List<OrderDetailModel> listOrderDetailByOrderId(@Param(value = "orderId") String orderId);

    List<EntOrderDetail> listOrderDetailByOrderId(EntOrderDetail entOrderDetail);

    void updateDetailOrder(EntOrderDetail orderDetail);

    List<EntReleaseDetailModel> countOrderWasteAmount(OrderParam orderParam);

    List<OrderDetailModel> listTotalPrice(OrderParam orderParam);
}
