package com.mlsc.yifeiwang.discusstag.controller;


import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.discusstag.entity.DiscussTag;
import com.mlsc.yifeiwang.discusstag.model.DiscussTagParam;
import com.mlsc.yifeiwang.discusstag.service.IDiscussTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @since 2017-11-13
 */
@Controller
@RequestMapping("/discussTag")
public class DiscussTagController {
    @Autowired
    private IDiscussTagService discussTagService;

    @ResponseBody
    @RequestMapping(value = "saveDiscussTag", method = RequestMethod.POST)
    public Result<Boolean> saveDiscussTag(User user, @RequestBody DiscussTagParam discussTagParam) {
        Result<Boolean> result = new Result<Boolean>();
        List<String> errorList = new ArrayList<>();
        try {
            boolean validate = discussTagService.validateDiscussTag(discussTagParam,errorList);
            if (validate) {
                boolean saveFlag = discussTagService.saveDiscussTag(user, discussTagParam);
                result.setData(saveFlag);
                result.setStatus(ResultStatus.Success);
            }else{
                result.setData(validate);
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listDiscussTag")
    public Result<List<DiscussTag>> listDiscussTag(User user, String releaseId) {
        Result<List<DiscussTag>> result = new Result<List<DiscussTag>>();
        String msg = null;
        try {
            List<DiscussTag> discussTagPage = discussTagService.listTagListByReleaseId(releaseId, user.getEnterpriseId());
            result.setData(discussTagPage);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/deleteDiscussTag")
    public Result<Boolean> deleteDiscussTag(User user, String id) {
        Result<Boolean> result = new Result<Boolean>();
        String msg = null;
        try {
            boolean deleteResult = discussTagService.deleteDiscussTag(user, id);
            result.setData(deleteResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setData(false);
            result.setStatus(ResultStatus.Error);
        }

        return result;
    }

}
