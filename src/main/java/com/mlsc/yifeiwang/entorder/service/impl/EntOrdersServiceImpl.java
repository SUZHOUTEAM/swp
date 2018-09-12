package com.mlsc.yifeiwang.entorder.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.entorder.common.OrderContractStatus;
import com.mlsc.yifeiwang.entorder.common.OrderStatusEnum;
import com.mlsc.yifeiwang.discusstag.service.IDiscussTagService;
import com.mlsc.yifeiwang.entorder.entity.EntOrder;
import com.mlsc.yifeiwang.entorder.entity.EntOrderDetail;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.UserStatus;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.yifeiwang.discusstag.model.DiscussTagParam;
import com.mlsc.yifeiwang.entorder.model.OrderDetailModel;
import com.mlsc.yifeiwang.entorder.model.OrderModel;
import com.mlsc.yifeiwang.entorder.model.OrderParam;
import com.mlsc.yifeiwang.entorder.mapper.EntOrderMapper;
import com.mlsc.yifeiwang.entorder.service.IEntOrderDetailService;
import com.mlsc.yifeiwang.entorder.service.IEntOrdersService;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseDetailService;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2017-10-09
 */
@Service

public class EntOrdersServiceImpl extends ServiceImpl<EntOrderMapper, EntOrder> implements IEntOrdersService {
    private final static Logger logger = LoggerFactory.getLogger(EntOrdersServiceImpl.class);

    @Autowired
    private IEntOrderDetailService entOrderDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private SysMsgServcie sysMsgService;

    @Autowired
    private IDiscussTagService discussTagService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private SysMsgServcie sysMsgServcie;

    @Autowired
    private IEntReleaseDetailService entReleaseDetailService;

    @Override
    public List<OrderModel> listOrder(User user, PagingParameter pagingParameter, OrderParam orderParam) throws Exception {
        List<OrderModel> orderList = null;
        try {
            Util.initPagingParameter(pagingParameter);
            orderParam.setStartRowIndex(pagingParameter.getStart());
            orderParam.setRows(pagingParameter.getLimit());
            int count = this.baseMapper.countOrderList(orderParam);
            if (count > 0) {
                orderList = this.baseMapper.listOrderList(orderParam);
                pagingParameter.setTotalRecord(count);
                Iterator<OrderModel> iter = orderList.iterator();
                while (iter.hasNext()) {
                    OrderModel orderModel = iter.next();
                    String BusiStatusValue = OrderStatusEnum.getNameByCode(orderModel.getBusiStatus());
                    String orderContractStatus = OrderContractStatus.getNameByCode(orderModel.getOrderContractStatus());
                    orderModel.setBusiStatus(BusiStatusValue);
                    orderModel.setOrderContractStatus(orderContractStatus);
                    List<OrderDetailModel> detailList = entOrderDetailService.listOrderDetailByOrderId(orderModel.getId());
                    if (detailList != null && detailList.size() > 0) {
                        orderModel.setOrderDetail(detailList);
                        orderModel.setOrderWasteCount(detailList.size());
                    }
                    if (StringUtils.isNotEmpty(orderParam.getInquiryEntId())) {
                        DiscussTagParam tagInfo = discussTagService.countTagInfo(orderModel.getReleaseId(), user.getEnterpriseId());
                        if (tagInfo != null) {
                            orderModel.setTagInfo(tagInfo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取订单列表时异常", e);
            throw e;
        }
        return orderList;
    }


    @Override
    public List<RPCSysUser> listPersonByEnterId(User user) throws Exception {
        List<RPCSysUser> list = null;
        try {
            list = userService.getUserInfoByEntId(user.getEnterpriseId());
        } catch (Exception e) {
            logger.error("获取用户列表时异常", e);
            throw e;
        }

        return list;

    }

    @Override
    public List<SysEnterpriseBase> listReleaseEnt(User user) throws Exception {
        List<SysEnterpriseBase> list = null;
        try {
            list = this.baseMapper.listReleaseEnt(user.getEnterpriseId());
        } catch (Exception e) {
            logger.error("获取发布企业列表时异常", e);
            throw e;
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean closeOrder(User user, OrderParam orderParam) throws Exception {
        List<EnterpriseInfo> list = null;
        try {
            String orderId = orderParam.getOrderId();
            boolean updateResult = updateOrderStatus(user, OrderStatusEnum.DONE.getCode(), orderId);
            if(updateResult){
                updateOrderDetailStatus(user, OrderStatusEnum.DONE.getCode(), orderId);
                sendCloseOrderMsg(user, orderId);
                return true;
            }
        } catch (Exception e) {
            logger.error("完成订单时异常", e);
            throw e;
        }
        return false;
    }

    private void sendCloseOrderMsg(User user, String orderId) {
        EntOrder entOrder = this.baseMapper.selectById(orderId);
        if (StringUtils.isNotEmpty(entOrder.getReleaseEntId())) {
            MsgEvent msgEvent = new MsgEvent(SmsAction.ENT_NEW_ORDER);
            msgEvent.setRelId(entOrder.getId());
            msgEvent.setSendUser(user);
            User queryParam = new User();
            queryParam.setEnterpriseId(entOrder.getReleaseEntId());
            queryParam.setUserStatus(UserStatus.PASS.getStatusCode());
            msgEvent.setReceiverUserQueryParam(queryParam);
            Map<String, String> placeholderMap = new HashMap<>();
            placeholderMap.put("entName", user.getEnterpriseName());
            msgEvent.setPlaceholderValueMap(placeholderMap);
            sysMsgService.sendMessageAsync(msgEvent);
        }
    }

    private void updateOrderDetailStatus(User user, String code, String orderId) {
        Date date = new Date();
        EntOrderDetail orderDetail = new EntOrderDetail();
        orderDetail.setOrderId(orderId);
        orderDetail.setBusiStatus(OrderStatusEnum.DONE.getCode());
        orderDetail.setEditBy(user.getUserId());
        orderDetail.setEditTime(date);
        entOrderDetailService.updateDetailOrder(orderDetail);
    }

    private boolean updateOrderStatus(User user, String code, String orderId) {
        Date date = new Date();
        EntOrder entOrder = this.baseMapper.selectById(orderId);
        entOrder.setBusiStatus(OrderStatusEnum.DONE.getCode());
        entOrder.setEditBy(user.getUserId());
        entOrder.setEditTime(date);
        return this.updateById(entOrder);
    }


    @Override
    public OrderModel countOrderWasteAmount(OrderParam orderParam) {
        OrderModel orderModel = new OrderModel();
        List<EntReleaseDetailModel> orderList = null;
        List<OrderDetailModel> priceList = null;
        try {
            orderList = entOrderDetailService.countOrderWasteAmount(orderParam);
            priceList = entOrderDetailService.listTotalPrice(orderParam);
            double totalPrice = 0d;
            if (orderList != null && orderList.size() > 0) {
                StringBuilder toltalAmount = entReleaseDetailService.countWasteAmount(orderList);
                orderModel.setTotalAmount(toltalAmount.toString());
                orderModel.setQueryCount(this.baseMapper.countOrderList(orderParam));
            }
            if (priceList != null && priceList.size() > 0) {
                for (OrderDetailModel orderDetail : priceList) {
                    totalPrice = totalPrice + orderDetail.getWasteTotalPrice();
                }
                orderModel.setTotalPrice(totalPrice);
            }
        } catch (Exception e) {
            logger.error("统计订单危废总量时异常", e);
            throw e;
        }
        return orderModel;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateContractStatus(User user, OrderParam orderParam) throws Exception {
        try {
            if (StringUtils.isNotBlank(orderParam.getOrderId())) {
                EntOrder entOrder = this.baseMapper.selectById(orderParam.getOrderId());
                if (entOrder != null) {
                    Date date = new Date();
                    entOrder.setEditBy(user.getUserId());
                    entOrder.setEditTime(date);
                    entOrder.setContractStatus(OrderContractStatus.UPLOADED.getCode());
                    boolean updateResult = this.updateById(entOrder);
                    if(updateResult){
                        sendContractInfoMsg(entOrder, user);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("更新订单时异常", e);
            throw e;
        }
        return false;
    }

    private void sendContractInfoMsg(EntOrder entOrder, User user) throws Exception {
        try {
            String recievedEntId = "";
            if (StringUtils.isNotEmpty(entOrder.getFacilitatorEntId())) {
                recievedEntId = entOrder.getFacilitatorEntId();
            } else {
                recievedEntId = entOrder.getReleaseEntId();
            }
            if (StringUtils.isNotEmpty(recievedEntId)) {
                List<String> entIds = new ArrayList<String>();
                entIds.add(recievedEntId);
                List<User> recievedUsers = userInfoService.listUserInfoByEnterIds(entIds);
                if (recievedUsers != null && recievedUsers.size() > 0) {
                    MsgEvent msgEvent = new MsgEvent(SmsAction.UPLOADED_CONTRACT);
                    msgEvent.setSendUser(user);
                    Map<String, String> placeholderValueMap = new HashMap<String, String>();
                    msgEvent.setRelId(entOrder.getId());
                    msgEvent.setPlaceholderValueMap(placeholderValueMap);
                    msgEvent.setSendUser(user);
                    msgEvent.setReceiveUserList(recievedUsers);
                    sysMsgServcie.sendMessageSync(msgEvent);
                }
            }
        } catch (Exception e) {
            logger.error("上传合同后通知用户时异常", e);
            throw e;
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteUploadContract(User user, OrderParam orderParam) throws Exception {
        try {
            EntOrder entOrder = this.baseMapper.selectById(orderParam.getOrderId());
            if (entOrder != null) {
                Date date = new Date();
                entOrder.setEditBy(user.getUserId());
                entOrder.setEditTime(date);
                entOrder.setContractStatus(OrderContractStatus.WAITINGUPLOAD.getCode());
                return this.updateById(entOrder);
            }
        } catch (Exception e) {
            logger.error("删除上传文件时异常", e);
            throw e;
        }
        return false;

    }


}
