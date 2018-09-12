package com.mlsc.yifeiwang.enterprise.controller;


import com.mlsc.yifeiwang.enterprise.entity.EntGlory;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.service.IEntGloryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2017-10-20
 */
@Controller
@RequestMapping("/entGlory")
public class EntGloryController {
    @Autowired
    private IEntGloryService entGloryService;

    /**
     * 企业荣誉-保存
     *
     * @param user
     * @param entGlory
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveEntGlory")
    public Result<Boolean> saveEntGlory(User user, EntGlory entGlory) {
        Result<Boolean> result = new Result<Boolean>();
        List<String> errorList = new ArrayList<String>();
        try {
            boolean validate = entGloryService.validateEntGlory(entGlory, errorList);
            if (validate) {
                boolean hasCustomer = entGloryService.saveEntGlory(user, entGlory);
                result.setData(hasCustomer);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setInfoList(errorList);
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 企业荣誉-列表
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listEntGlory")
    public Result<List<EntGlory>> listEntGlory(User user) {
        Result<List<EntGlory>> result = new Result<List<EntGlory>>();
        List<EntGlory> entGlories;
        try {
            entGlories = entGloryService.listEntGloryByEntId(user);
            result.setData(entGlories);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 根据id查看荣誉明细
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getEntGlory")
    public Result<EntGlory> getEntGlory(String id) {
        Result<EntGlory> result = new Result<EntGlory>();
        EntGlory entGlory;
        try {
            entGlory = entGloryService.getEntGloryById(id);
            result.setData(entGlory);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 删除荣誉
     *
     * @param entGlory
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteEntGlory")
    public Result<Boolean> deleteEntGlory(EntGlory entGlory) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean hasCustomer = entGloryService.deleteEntGlory(entGlory);
            result.setData(hasCustomer);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 更新荣誉
     *
     * @param user
     * @param entGlory
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateEntGlory")
    public Result<Boolean> updateEntGlory(User user, EntGlory entGlory) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean hasCustomer = entGloryService.updateEntGlory(user, entGlory);
            result.setData(hasCustomer);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

}
