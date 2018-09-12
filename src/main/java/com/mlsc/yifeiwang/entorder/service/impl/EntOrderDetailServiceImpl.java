package com.mlsc.yifeiwang.entorder.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mlsc.yifeiwang.entorder.entity.EntOrderDetail;
import com.mlsc.yifeiwang.entorder.mapper.EntOrderDetailMapper;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailModel;
import com.mlsc.yifeiwang.entorder.model.OrderDetailModel;
import com.mlsc.yifeiwang.entorder.model.OrderParam;
import com.mlsc.yifeiwang.entorder.service.IEntOrderDetailService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2017-10-09
 */
@Service
public class EntOrderDetailServiceImpl extends ServiceImpl<EntOrderDetailMapper, EntOrderDetail> implements IEntOrderDetailService {
    private final static Logger logger = LoggerFactory.getLogger(EntOrderDetailServiceImpl.class);

    @Override
    public List<OrderDetailModel> listOrderDetailByOrderId(@Param(value = "orderId") String orderId) {
        List<OrderDetailModel> inquiryDetailList = null;
        try {
            inquiryDetailList = this.baseMapper.listOrderDetailByOrderId(orderId);
        } catch (Exception e) {
            logger.error("获取询价详情时异常", e);
            throw e;
        }
        return inquiryDetailList;
    }

    @Override
    public List<EntOrderDetail> listOrderDetailByOrderId(EntOrderDetail entOrderDetail) {
        EntityWrapper<EntOrderDetail> detailEntityWrapper = new EntityWrapper<EntOrderDetail>();
        detailEntityWrapper.eq("orderId", entOrderDetail.getOrderId());
        List<EntOrderDetail> entOrderDetailList = this.baseMapper.selectList(detailEntityWrapper);
        return entOrderDetailList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateDetailOrder(EntOrderDetail orderDetail) {
        try {
            this.baseMapper.updateOrderDetail(orderDetail);
        } catch (Exception e) {
            logger.error("更新订单详细时异常", e);
            throw e;
        }
    }

    @Override
    public List<EntReleaseDetailModel> countOrderWasteAmount(OrderParam orderParam) {
        List<EntReleaseDetailModel> inquiryDetailList = null;
        try {
            inquiryDetailList = this.baseMapper.countOrderWasteAmount(orderParam);
        } catch (Exception e) {
            logger.error("统计订单危废总量时异常", e);
            throw e;
        }
        return inquiryDetailList;
    }


    @Override
    public List<OrderDetailModel> listTotalPrice(OrderParam orderParam) {
        List<OrderDetailModel> inquiryDetailList = null;
        try {
            inquiryDetailList = this.baseMapper.listTotalPrice(orderParam);
        } catch (Exception e) {
            logger.error("统计订单危废总额时异常", e);
            throw e;
        }
        return inquiryDetailList;
    }
}
