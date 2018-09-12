package com.mlsc.order;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.entorder.common.OrderStatusEnum;
import com.mlsc.yifeiwang.entorder.entity.EntOrder;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.yifeiwang.entorder.model.OrderModel;
import com.mlsc.yifeiwang.entorder.model.OrderParam;
import com.mlsc.yifeiwang.entorder.service.IEntOrdersService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 2017/10/10.
 */
public class EntOrdersServiceTest extends BaseTest {
    @Autowired
    private IEntOrdersService entOrdersService;

    @Test
    public void listOrderByRelaseEnterIdTest() throws Exception {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868915894272");
        PagingParameter pagingParameter = new PagingParameter();
        pagingParameter.setStart(1);
        pagingParameter.setPageIndex(1);
        pagingParameter.setLimit(100);
        OrderParam orderParam = new OrderParam();
        orderParam.setReleaseEntId("2235872864520192");
        List<OrderModel> orderList = entOrdersService.listOrder(user,pagingParameter,orderParam );

        Assert.assertTrue(orderList.size()>0);
    }

    @Test
    public void listOrderByInquiryEnterIdTest() throws Exception {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868915894272");
        PagingParameter pagingParameter = new PagingParameter();
        pagingParameter.setStart(1);
        pagingParameter.setPageIndex(1);
        pagingParameter.setLimit(5);
        OrderParam orderParam = new OrderParam();
        orderParam.setOrderStatus(OrderStatusEnum.ONGOING.getCode());
        orderParam.setInquiryEntId("2235868915894272");
        orderParam.setInquiryPersonId("2255423617927168");
        List<OrderModel> orderList = entOrdersService.listOrder(user,pagingParameter,orderParam );
        Assert.assertTrue(orderList.size()>0);
    }

    @Test
    public void countOrderWasteAmountTest() throws Exception {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868915894272");
        PagingParameter pagingParameter = new PagingParameter();
        pagingParameter.setStart(1);
        pagingParameter.setPageIndex(1);
        pagingParameter.setLimit(5);
        OrderParam orderParam = new OrderParam();
        orderParam.setOrderStatus(OrderStatusEnum.ONGOING.getCode());
        orderParam.setInquiryEntId("2235868915894272");
        orderParam.setInquiryPersonId("2255423617927168");
        OrderModel orderModel = entOrdersService.countOrderWasteAmount(orderParam );
        Assert.assertTrue(orderModel!=null);
    }

    @Test
    public void listReleaseEntTest() throws Exception {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868915894272");
        List<SysEnterpriseBase> enterList = entOrdersService.listReleaseEnt(user);
        Assert.assertTrue(enterList.size()>0);
    }

    @Test
    public void listPersonByEnterIdTest() throws Exception {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868915894272");
        List<RPCSysUser> userList = entOrdersService.listPersonByEnterId(user);
        Assert.assertTrue(userList.size()>0);
    }


    @Test
    public void closeOrderTest() throws Exception {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868915894272");
        OrderParam orderParam = new OrderParam();
        orderParam.setOrderId("1c12b19324824014a1ca5b630c0f9049");
        entOrdersService.closeOrder(user,orderParam);
        EntOrder order = entOrdersService.selectById("1c12b19324824014a1ca5b630c0f9049");
        Assert.assertTrue(order.getBusiStatus().equals(OrderStatusEnum.DONE.getCode()));
    }
}
