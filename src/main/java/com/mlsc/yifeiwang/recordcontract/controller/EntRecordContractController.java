package com.mlsc.yifeiwang.recordcontract.controller;


import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractModel;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractParam;
import com.mlsc.yifeiwang.recordcontract.service.IEntRecordContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
 * @since 2018-03-01
 */
@Controller
@RequestMapping("/entRecordContract")
public class EntRecordContractController {

    @Autowired
    private IEntRecordContractService entRecordContractService;

    /**
     * 保存合同备案
     *
     * @param user
     * @param recordContractParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveRecordContract")
    public Result<String> saveRecordContract(User user, @RequestBody EntRecordContractParam recordContractParam) {
        Result<String> result = new Result<String>();
        try {
            String id = entRecordContractService.saveRecordContract(user, recordContractParam);
            result.setData(id);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 获取备案详情
     *
     * @param user
     * @param recordContractParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getRecordContractById")
    public Result<EntRecordContractModel> getRecordContractById(User user, @RequestBody EntRecordContractParam recordContractParam) {
        Result<EntRecordContractModel> result = new Result<EntRecordContractModel>();
        try {
            EntRecordContractModel entRecordContractModel = entRecordContractService.getRecordContractById(user, recordContractParam);
            result.setData(entRecordContractModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 更新备案
     *
     * @param user
     * @param recordContractParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateRecordContract")
    public Result<Boolean> updateRecordContract(User user, @RequestBody EntRecordContractParam recordContractParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean updateResult = entRecordContractService.updateRecordContract(user, recordContractParam);
            result.setData(updateResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 管理员审核通过备案
     *
     * @param user
     * @param recordContractParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "approveRecordContract")
    public Result<Boolean> approveRecordContract(User user, @RequestBody EntRecordContractParam recordContractParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean approveResult = entRecordContractService.approveRecordContract(user, recordContractParam);
            result.setData(approveResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 管理员审核退回备案
     *
     * @param user
     * @param recordContractParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "rejectRecordContract")
    public Result<Boolean> rejectRecordContract(User user, @RequestBody EntRecordContractParam recordContractParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean rejectResult = entRecordContractService.rejectRecordContract(user, recordContractParam);
            result.setData(rejectResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 管理员查看备案列表
     *
     * @param user
     * @param pagingParameter
     * @param recordContractParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listRecordContractList")
    public Result<Map<String, Object>> listRecordContractList(User user, PagingParameter pagingParameter, @RequestBody EntRecordContractParam recordContractParam) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<EntRecordContractModel> recordContractList = entRecordContractService.listRecordContractList(user, pagingParameter, recordContractParam);
            dataMap.put("recordContractList", recordContractList);
            dataMap.put("paging", pagingParameter);
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
}
