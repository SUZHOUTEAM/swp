package com.mlsc.yifeiwang.wasterealase.controller;


import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseBonus;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseBonusModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseBonusParam;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseBonusService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2018-04-09
 */
@Controller
@RequestMapping("/entReleaseBonus")
public class EntReleaseBonusController {

    @Autowired
    private IEntReleaseBonusService entReleaseBonusService;

    @ResponseBody
    @RequestMapping(value = "saveReleaseBonus")
    public Result<Map<String, Object>> saveReleaseBonus(User user, EntReleaseBonus entReleaseBonus) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String status = entReleaseBonusService.saveReleaseBonus(user, entReleaseBonus);
            resultMap.put("status", status);
            resultMap.put("entReleaseBonus", entReleaseBonus);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "sendBonusToken")
    public Result<Boolean> sendBonusToken(User user, EntReleaseBonus entReleaseBonus) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean sendResult = entReleaseBonusService.sendBonusToken(user, entReleaseBonus);
            result.setData(sendResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "listEntReleaseBonus", method = RequestMethod.POST)
    public Result<Map<String, Object>> listEntReleaseBonus(PagingParameter paging, EntReleaseBonusParam releaseBonusParam) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<EntReleaseBonusModel> bonusModelList = entReleaseBonusService.listEntReleaseBonus(paging, releaseBonusParam);
            resultMap.put("bonusModelList", bonusModelList);
            resultMap.put("paging", paging);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;

    }

    @ResponseBody
    @RequestMapping(value = "updateRecievedStatus")
    public Result<Boolean> updateRecievedStatus(User user, EntReleaseBonus entReleaseBonus) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean udpateResult = entReleaseBonusService.updateRecievedStatus(user, entReleaseBonus);
            result.setData(udpateResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "saveReleaseBonus2")
    public Result<Map<String, Object>> saveReleaseBonus2(User user, EntReleaseBonus entReleaseBonus) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String status = entReleaseBonusService.saveReleaseBonus(user, entReleaseBonus);
            resultMap.put("status", status);
            resultMap.put("entReleaseBonus", entReleaseBonus);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }

        return result;
    }
}
