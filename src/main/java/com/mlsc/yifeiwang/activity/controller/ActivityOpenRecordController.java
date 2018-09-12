package com.mlsc.yifeiwang.activity.controller;


import cn.jiguang.common.utils.StringUtils;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.activity.service.IActivityOpenRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2017-12-11
 */
@Controller
@RequestMapping("/activityOpenRecord")
public class ActivityOpenRecordController {


    @Autowired
    private IActivityOpenRecordService activityOpenRecordService;


    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result<Boolean> save(User user, String activityId) {
        Result<Boolean> result = new Result<Boolean>();
        List<String> errorList = new ArrayList<String>();
        try {
            if (StringUtils.isNotEmpty(activityId)) {
                boolean saveResult = activityOpenRecordService.saveActivityOpenRecord(activityId);
                result.setData(saveResult);
                result.setStatus(ResultStatus.Success);
            } else {
                errorList.add("活动id为空");
                result.setInfoList(errorList);
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
}
