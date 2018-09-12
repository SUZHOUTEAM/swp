package com.mlsc.yifeiwang.enterprise.controller;


import com.mlsc.yifeiwang.enterprise.entity.EntEvaluate;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.service.IEntEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @since 2017-10-17
 */
@Controller
@RequestMapping("/entEvaluate")
public class EntEvaluateController {
    @Autowired
    private IEntEvaluateService entEvaluateService;

    /**
     * 添加评价
     *
     * @param user
     * @param entEvaluate
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addEntEvaluate")
    public Result<List<EntEvaluate>> addEntFavorite(User user, EntEvaluate entEvaluate) {
        Result<List<EntEvaluate>> result = new Result<List<EntEvaluate>>();
        List<String> errorList = new ArrayList<>();
        try {
            boolean validate = entEvaluateService.validateEntEvaluate(entEvaluate,errorList);
            if(validate){
                List<EntEvaluate> list = entEvaluateService.addEntEvaluate(user, entEvaluate);
                result.setData(list);
                result.setStatus(ResultStatus.Success);
            }else{
                result.setInfoList(errorList);
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }

        return result;
    }


    /**
     * 查看评价
     *
     * @param user
     * @param entEvaluate
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listEntEvaluate")
    public Result<List<EntEvaluate>> listEntEvaluate(User user, EntEvaluate entEvaluate) {
        Result<List<EntEvaluate>> result = new Result<List<EntEvaluate>>();
        List<EntEvaluate> evaluateList;
        try {
            evaluateList = entEvaluateService.listEntEvaluate(entEvaluate);
            result.setData(evaluateList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 查看企业评价
     *
     * @param user
     * @param entEvaluate
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listEntEvaluateByEntId")
    public Result<List<EntEvaluate>> listEntEvaluateByEntId(User user, @RequestBody EntEvaluate entEvaluate) {
        Result<List<EntEvaluate>> result = new Result<List<EntEvaluate>>();
        List<EntEvaluate> evaluateList;
        try {
            evaluateList = entEvaluateService.listEntEvaluateByEntId(entEvaluate);
            result.setData(evaluateList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


}
