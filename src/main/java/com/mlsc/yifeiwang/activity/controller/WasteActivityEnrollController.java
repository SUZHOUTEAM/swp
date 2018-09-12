package com.mlsc.yifeiwang.activity.controller;


import com.alibaba.fastjson.JSONArray;
import com.mlsc.common.util.DateUtil;
import com.mlsc.yifeiwang.activity.entity.WasteActivityEnroll;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.activity.model.WasteActivityEnrollModel;
import com.mlsc.yifeiwang.activity.service.IWasteActivityEnrollService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2018-02-05
 */
@Controller
@RequestMapping("/wasteActivityEnroll")
public class WasteActivityEnrollController {
    @Autowired
    private IWasteActivityEnrollService wasteActivityEnrollService;

    /**
     * 保存报名信息
     *
     * @param user
     * @param wasteActivityEnroll
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveWasteActivityEnroll", method = RequestMethod.POST)
    public Result<Boolean> saveWasteActivityEnroll(User user, @RequestBody WasteActivityEnroll wasteActivityEnroll) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            List<String> infoList = wasteActivityEnrollService.saveWasteActivityEnroll(user, wasteActivityEnroll);
            result.setData(true);
            result.setInfoList(infoList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 报名成功
     *
     * @param user
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "auditApprove", method = RequestMethod.POST)
    public Result<Boolean> auditApprove(User user, @RequestBody List<String> ids) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean approveResult = wasteActivityEnrollService.auditApprove(user, ids);
            result.setData(approveResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 报名失败
     *
     * @param user
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "auditReject", method = RequestMethod.POST)
    public Result<Boolean> auditReject(User user, @RequestBody List<String> ids) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean rejectResult = wasteActivityEnrollService.auditReject(user, ids);
            result.setData(rejectResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 删除报名
     *
     * @param user
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteEnroll", method = RequestMethod.POST)
    public Result<Boolean> deleteEnroll(User user, @RequestBody List<String> ids) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean deleteResult = wasteActivityEnrollService.deleteEntroll(user, ids);
            result.setData(deleteResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 确认报名状态
     *
     * @param user
     * @param wasteActivityEnroll
     * @return SUBMIT("SUBMIT", "等待支付"), PAYMENTSUCCESS("PAYMENTSUCCESS", "付款成功"), PAYMENTFAILED("PAYMENTFAILED", "付款失败");
     */
    @ResponseBody
    @RequestMapping(value = "checkEnrollStatus", method = RequestMethod.POST)
    public Result<WasteActivityEnroll> checkEnrollStatus(User user, @RequestBody WasteActivityEnroll wasteActivityEnroll) {
        Result<WasteActivityEnroll> result = new Result<WasteActivityEnroll>();
        try {
            wasteActivityEnroll.setEntId(user.getEnterpriseId());
            WasteActivityEnroll queryResult = wasteActivityEnrollService.checkEntrollStatus(wasteActivityEnroll);
            result.setData(queryResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 系统管理员查看企业报名参加活动列表
     *
     * @param user
     * @param wasteActivityEnrollModel
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listActivityEnroll")
    public Result<Map<String, Object>> listActivityEnroll(User user, @RequestBody WasteActivityEnrollModel wasteActivityEnrollModel, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            wasteActivityEnrollModel.setEntId(user.getEnterpriseId());
            List<WasteActivityEnrollModel> enrollModelList = wasteActivityEnrollService.listActivityEnroll(wasteActivityEnrollModel, pagingParameter);
            dataMap.put("list", JSONArray.toJSON(enrollModelList));
            dataMap.put("paging", JSONArray.toJSON(pagingParameter));
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 系统管理员各个活动的统计报名人数
     *
     * @param user
     * @param wasteActivityEnrollModel
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "registrationEnrollActivity")
    public Result<Map<String, Object>> registrationEnrollActivity(User user, @RequestBody WasteActivityEnrollModel wasteActivityEnrollModel, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            wasteActivityEnrollModel.setEntId(user.getEnterpriseId());
            List<WasteActivityEnrollModel> enrollModelList = wasteActivityEnrollService.registrationEnrollActivity(wasteActivityEnrollModel, pagingParameter);
            dataMap.put("list", JSONArray.toJSON(enrollModelList));
            dataMap.put("paging", JSONArray.toJSON(pagingParameter));
            dataMap.put("serverTime", DateUtil.convertToString(DateTime.now()));
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 系统管理员众筹成功后，通知企业准时参加直播
     *
     * @param user
     * @param wasteActivityEnrollModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "crowdFundingSucceed")
    public Result<Boolean> crowdFundingSucceed(User user, @RequestBody WasteActivityEnrollModel wasteActivityEnrollModel) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean informResult = wasteActivityEnrollService.crowdFundingSucceed(user, wasteActivityEnrollModel);
            result.setData(informResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


}
