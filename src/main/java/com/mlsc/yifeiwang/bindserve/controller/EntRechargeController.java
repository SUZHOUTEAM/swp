package com.mlsc.yifeiwang.bindserve.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.mlsc.common.util.AlipayUtil;
import com.mlsc.common.util.StringUtils;
import com.mlsc.yifeiwang.bindserve.entity.EntRecharge;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.yifeiwang.bindserve.model.EntRechargeParam;
import com.mlsc.yifeiwang.bindserve.service.IEntRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2018-04-26
 */
@Controller
@RequestMapping("/entRecharge")
public class EntRechargeController {
    @Autowired
    private IEntRechargeService entRechargeService;


    /**
     * 企业充值
     *
     * @param user
     * @param entBindServeParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveEntRecharge")
    public Result saveEntRecharge(User user,@RequestBody EntRechargeParam entBindServeParam, HttpServletResponse response) {
        Result result = new Result();
        try {
            String orderNO = entRechargeService.saveEntRecharge(user, entBindServeParam);
            result.setData(orderNO);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 支付宝回调url
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "notifyUrl", method = {RequestMethod.POST, RequestMethod.GET})
    public Result notify(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            Map<String, String[]> requestParams = request.getParameterMap();
            String orderNo = AlipayUtil.getOrderNo(requestParams);
            entRechargeService.updateUserAccount(orderNo);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 支付成功后，返回支付信息
     *
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getRechargeInfoByOrderNo", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getRechargeInfoByOrderNo(String orderNo) {
        Result result = new Result();
        try {
            if (StringUtils.isNotNullOrEmpty(orderNo)) {
                EntRechargeParam entBindServeParam = new EntRechargeParam();
                entBindServeParam.setOrderNo(orderNo);
                EntRecharge entRecharge = entRechargeService.selectEntRecharge(entBindServeParam);
                if (entRecharge != null) {
                    result.setData(entRecharge);
                    result.setStatus(ResultStatus.Success);
                }
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 更新支付宝支付成功后更新支付状态
     *
     * @param ticketId
     * @param entBindServeParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateEntRechargeStatus", method = RequestMethod.POST)
    public Result updateEntRechargeStatus(String ticketId, @RequestBody EntRechargeParam entBindServeParam) {
        Result result = new Result();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            entRechargeService.updateRechargeStatus(user, entBindServeParam);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 处置企业充值记录
     *
     * @param user
     * @param entBindServeParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listRecharge", method = RequestMethod.POST)
    public Result<Page<EntRecharge>> listRecharge(User user, @RequestBody EntRechargeParam entBindServeParam, Page<EntRecharge> page) {
        Result<Page<EntRecharge>> result = new Result<Page<EntRecharge>>();
        try {
            entBindServeParam.setUserId(user.getUserId());
            Page<EntRecharge> entBindServePage = entRechargeService.listRecharge(entBindServeParam, page);
            result.setData(entBindServePage);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业删除充值记录
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result<Boolean> delete(String id) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean deleteResult = entRechargeService.delete(id);
            result.setData(deleteResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setData(false);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


}
