package com.mlsc.yifeiwang.actionrecord.controller;

import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.actionrecord.modal.ActionVO;
import com.mlsc.yifeiwang.actionrecord.service.IActionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户行为记录Controller
 */
@Controller
@RequestMapping("/actionRecord")
@Scope("prototype")
public class ActionRecordController {
    public static final String RECODE_SUCCESS = "已成功记录该用户行为";


    @Autowired
    private IActionRecordService actionRecordService;

    @ResponseBody
    @RequestMapping("/recordUserAction")
    public Result<Map<String, String>> recordUserAction(ActionVO actionVO) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        try {
            result.setStatus(ResultStatus.Success);
            actionRecordService.saveActionRecord(actionVO);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }

}
