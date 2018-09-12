package com.mlsc.yifeiwang.user.controller;

import com.mlsc.common.util.StringUtils;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.user.model.UserParam;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by user on 2018/7/17.
 */
@Controller
@RequestMapping("/user")
public class userController {
    @Autowired
    private IUserInfoService userInfoService;

    @ResponseBody
    @RequestMapping(value = "getCodeByOpenId", method = RequestMethod.POST)
    public Result<Map<String, Object>> getCodeByOpenId(String openId) {
        Result<Map<String, Object>> result = new Result<>();
        try {
            if (StringUtils.isNotNullOrEmpty(openId)) {
                Map<String, Object> resultMapper = userInfoService.getCodeByOpenId(openId);
                result.setData(resultMapper);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setStatus(ResultStatus.Error);
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "getTicketId", method = RequestMethod.POST)
    public Result<Map<String, Object>> getTicketId(UserParam userParam, String ticketId) {
        Result<Map<String, Object>> result = new Result<>();
        try {
            if (userParam != null) {
                Map<String, Object> resultMapper = userInfoService.getTicketId(ticketId, userParam);
                result.setData(resultMapper);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setStatus(ResultStatus.Error);
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
}
