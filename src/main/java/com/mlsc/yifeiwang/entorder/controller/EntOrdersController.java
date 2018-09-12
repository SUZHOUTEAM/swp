package com.mlsc.yifeiwang.entorder.controller;


import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.entorder.model.OrderModel;
import com.mlsc.yifeiwang.entorder.model.OrderParam;
import com.mlsc.yifeiwang.entorder.service.IEntOrdersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2017-10-09
 */
@Controller
@RequestMapping("/entOrders")
public class EntOrdersController {
    private static final String PATH = "Orders/";

    @Autowired
    private IEntOrdersService entOrdersService;

    @RequestMapping("/list")
    public ModelAndView list(String ticketId,User user) {
        ModelAndView mv = null;
        String entType = user.getEntType();
        if (StringUtils.isEmpty(entType)) {
            mv = new ModelAndView();
            mv.setViewName("redirect:/" + "myenterprise/bindEnterprise.htm");
        } else {
            if (CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equalsIgnoreCase(entType)) {//产废企业
                mv = new ModelAndView(PATH + "cfList");
            } else {//处置企业
                mv = new ModelAndView(PATH + "czList");
            }
        }
        mv.addObject("ticketId", ticketId);
        return mv;
    }

    /**
     * 产废企业查看生成订单列表
     *
     * @param user
     * @param pagingParameter
     * @param orderParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listOrderByReleaseEntId")
    public Result<Map<String, Object>> listOrderByReleaseEntId(User user, PagingParameter pagingParameter, OrderParam orderParam) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            if (user != null) {
                orderParam.setReleaseEntId(user.getEnterpriseId());
                orderParam.setLoginUserEntId(user.getEnterpriseId());
                orderParam.setReleaseEntType(user.getEntType());
            }
            List<OrderModel> orderList = entOrdersService.listOrder(user, pagingParameter, orderParam);
            dataMap.put("orderList", orderList);
            dataMap.put("paging", pagingParameter);
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 询价企业查询企业订单
     *
     * @param ticketId
     * @param pagingParameter
     * @param orderParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listOrderByInquiryEntId")
    public Result<Map<String, Object>> listOrderByInquiryEntId(String ticketId, PagingParameter pagingParameter, OrderParam orderParam){
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try{
            if(user!=null){
                orderParam.setInquiryEntId(user.getEnterpriseId());
                orderParam.setLoginUserEntId(user.getEnterpriseId());
            }
            List<OrderModel> orderList = entOrdersService.listOrder(user, pagingParameter, orderParam);
            dataMap.put("orderList", orderList);
            dataMap.put("paging", pagingParameter);
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 企业询价人列表
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listInquiryPerson")
    public Result<List<RPCSysUser>> listInquiryPerson(User user) {
        Result<List<RPCSysUser>> result = new Result<List<RPCSysUser>>();
        try {
            List<RPCSysUser> userList = entOrdersService.listPersonByEnterId(user);
            result.setData(userList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业登录订单--企业列表
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listReleaseEnt")
    public Result<List<SysEnterpriseBase>> listReleaseEnt(User user) {
        Result<List<SysEnterpriseBase>> result = new Result<List<SysEnterpriseBase>>();
        try {
            List<SysEnterpriseBase> userList = entOrdersService.listReleaseEnt(user);
            result.setData(userList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业关闭订单
     *
     * @param user
     * @param orderParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "closeOrder")
    public Result<Boolean> closedOrder(User user, OrderParam orderParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            if(StringUtils.isNotBlank(orderParam.getOrderId())){
                boolean closeResult = entOrdersService.closeOrder(user, orderParam);
                result.setData(closeResult);
                result.setStatus(ResultStatus.Success);
            }else{
                result.setData(false);
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 产废企业-订单筛选，汇总结果
     *
     * @param user
     * @param pagingParameter
     * @param orderParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "countOrderWasteAmount4ReleaseEnt")
    public Result<OrderModel> countOrderWasteAmount4ReleaseEnt(User user, PagingParameter pagingParameter, OrderParam orderParam) {
        Result<OrderModel> result = new Result<OrderModel>();
        try {
            if (user != null) {
                orderParam.setReleaseEntId(user.getEnterpriseId());
                orderParam.setLoginUserEntId(user.getEnterpriseId());
                orderParam.setReleaseEntType(user.getEntType());
            }
            OrderModel orderList = entOrdersService.countOrderWasteAmount(orderParam);
            result.setData(orderList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业-订单筛选，汇总结果
     *
     * @param user
     * @param pagingParameter
     * @param orderParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "countOrderWasteAmount4InquiryEnt")
    public Result<OrderModel> countOrderWasteAmount4InquiryEnt(User user, PagingParameter pagingParameter, OrderParam orderParam) {
        Result<OrderModel> result = new Result<OrderModel>();
        try {
            if (user != null) {
                orderParam.setInquiryEntId(user.getEnterpriseId());
                orderParam.setLoginUserEntId(user.getEnterpriseId());
            }
            OrderModel orderList = entOrdersService.countOrderWasteAmount(orderParam);
            result.setData(orderList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业上传合同后，更新订单的合同状态及通知企业下用户。
     *
     * @param user
     * @param pagingParameter
     * @param orderParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateContractStatus")
    public Result<Boolean> updateContractStatus(User user, PagingParameter pagingParameter, OrderParam orderParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean updateResult = entOrdersService.updateContractStatus(user, orderParam);
            result.setData(updateResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 处置企业删除合同，更新订单的合同状态。
     *
     * @param user
     * @param pagingParameter
     * @param orderParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteUploadContract")
    public Result<Boolean> deleteUploadContract(User user, PagingParameter pagingParameter, OrderParam orderParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean deleteResult = entOrdersService.deleteUploadContract(user, orderParam);
            result.setData(deleteResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
}
