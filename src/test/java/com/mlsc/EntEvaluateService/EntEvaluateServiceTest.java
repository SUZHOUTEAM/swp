package com.mlsc.EntEvaluateService;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.enterprise.entity.EntEvaluate;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.service.IEntEvaluateService;
import com.mlsc.yifeiwang.entorder.model.OrderModel;
import com.mlsc.yifeiwang.entorder.model.OrderParam;
import com.mlsc.yifeiwang.entorder.service.IEntOrdersService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 2017/10/17.
 */
public class EntEvaluateServiceTest extends BaseTest {
    @Autowired
    private IEntOrdersService entOrdersService;
    @Autowired
    private IEntEvaluateService entEvaluateService;

    @Test
    public void listEvaluated() {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868915894272");
        PagingParameter pagingParameter = new PagingParameter();
        pagingParameter.setStart(1);
        pagingParameter.setPageIndex(1);
        pagingParameter.setLimit(5);
        OrderParam orderParam = new OrderParam();
        orderParam.setInquiryEntId("2235868915894272");

        try {
            List<OrderModel> orderList = entOrdersService.listOrder(user, pagingParameter, orderParam);
            EntEvaluate entEvaluate = new EntEvaluate();
            if (orderList != null && orderList.size() > 0) {
                entEvaluate.setComment("很好");
                entEvaluate.setScore(3);
                entEvaluate.setOrderId(orderList.get(0).getId());
                entEvaluate.setEvaluatedEntId(orderList.get(0).getReleaseEntId());
                entEvaluate.setEvaluatedBy(user.getEnterpriseId());
                entEvaluateService.addEntEvaluate(user, entEvaluate);
            }
            List<EntEvaluate> list = entEvaluateService.listEntEvaluate(entEvaluate);
            Assert.assertTrue(list.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
