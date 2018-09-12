package com.mlsc.yifeiwang.useraction.controller;


import com.mlsc.common.util.StringUtils;
import com.mlsc.yifeiwang.useraction.entity.UserAction;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.yifeiwang.useraction.model.ActionTypeModel;
import com.mlsc.yifeiwang.useraction.model.UserActionModel;
import com.mlsc.yifeiwang.useraction.model.UserActionParam;
import com.mlsc.yifeiwang.useraction.service.IUserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2018-01-03
 */
@Controller
@RequestMapping("/userAction")
public class UserActionController {
    @Autowired
    private IUserActionService userActionService;

    /**
     * 保存用户行为
     *
     * @param ticketId
     * @param user
     * @param userAction
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveUserAction", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> saveUserAction(String ticketId, User user, UserAction userAction) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            if (StringUtils.isNotNullOrEmpty(ticketId) && StringUtils.isNullOrEmpty(user.getUserId())) {
                user = LoginStatusUtils.getUserByTicketId(ticketId);
            }
            boolean saveResult = userActionService.saveUserAction(ticketId, user, userAction);
            result.setData(saveResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            List<String> infoList = new ArrayList<String>();
            result.setStatus(ResultStatus.Error);
            result.setInfoList(infoList);
        }
        return result;
    }

    /**
     * 列出企业行为列表
     *
     * @param user
     * @param userActionParam
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listUserAction", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Map<String, Object>> listUserAction(User user, UserActionParam userActionParam, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<UserActionModel> userActionModels = userActionService.listUserAction(userActionParam, pagingParameter);
            resultMap.put("userActionModels", userActionModels);
            resultMap.put("pagingParameter", pagingParameter);
            if (userActionModels != null && userActionModels.size() > 0) {
                userActionParam.setCip("");
                userActionParam.setActionTicketId("");
                List<ActionTypeModel> actionTypeModels = userActionService.listActionType(userActionParam);
                resultMap.put("actionTypeModels", actionTypeModels);
            }
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            List<String> infoList = new ArrayList<String>();
            result.setStatus(ResultStatus.Error);
            result.setInfoList(infoList);
        }
        return result;
    }

    /**
     * 未登录用户，根据ip,显示用户行为列表
     *
     * @param user
     * @param userActionParam
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listUserActionDetailByIp", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Map<String, Object>> listUserActionDetailByIp(User user, UserActionParam userActionParam, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<UserActionModel> userActionModels = userActionService.listUserActionDetailByIp(userActionParam, pagingParameter);
            resultMap.put("userActionModels", userActionModels);
            resultMap.put("pagingParameter", pagingParameter);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            List<String> infoList = new ArrayList<String>();
            result.setStatus(ResultStatus.Error);
            result.setInfoList(infoList);
        }
        return result;
    }

    /**
     * 登录用户，根据ticketId显示登录用户后行为列表
     *
     * @param user
     * @param userActionParam
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listUserActionDetailByTicketId", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Map<String, Object>> listUserActionDetailByTicketId(User user, UserActionParam userActionParam, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<UserActionModel> userActionModels = userActionService.listUserActionDetailByTicketId(userActionParam, pagingParameter);
            resultMap.put("userActionModels", userActionModels);
            resultMap.put("pagingParameter", pagingParameter);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            List<String> infoList = new ArrayList<String>();
            result.setStatus(ResultStatus.Error);
            result.setInfoList(infoList);
        }
        return result;
    }
}
