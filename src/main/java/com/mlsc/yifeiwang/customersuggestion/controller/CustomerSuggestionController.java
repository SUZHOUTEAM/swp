package com.mlsc.yifeiwang.customersuggestion.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.mlsc.yifeiwang.customersuggestion.entity.CustomerSuggestion;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.customersuggestion.service.ICustomerSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
 * @since 2017-11-16
 */
@Controller
@RequestMapping("/customerSuggestion")
public class CustomerSuggestionController {
    @Autowired
    private ICustomerSuggestionService customerSuggestionService;

    /**
     * 保存用户建议
     *
     * @param customerSuggestion
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveCustomerSuggestion", method = RequestMethod.POST)
    public Result<Boolean> saveDiscussTag(@RequestBody CustomerSuggestion customerSuggestion) {
        Result<Boolean> result = new Result<Boolean>();
        List<String> errorList = new ArrayList<String>();
        try {
            boolean validate = customerSuggestionService.validateCustomerSuggestion(customerSuggestion, errorList);
            if (validate) {
                boolean saveFlag = customerSuggestionService.saveCustomerSuggestion(customerSuggestion);
                result.setData(saveFlag);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setInfoList(errorList);
                result.setData(false);
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 用户建议列表
     *
     * @param customerSuggestion
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listCustomerSuggestion", method = RequestMethod.POST)
    public Result<Page<CustomerSuggestion>> listCustomerSuggestion(@RequestBody Page<CustomerSuggestion> customerSuggestion) {
        Result<Page<CustomerSuggestion>> result = new Result<Page<CustomerSuggestion>>();
        try {
            if (customerSuggestion != null){
                Page<CustomerSuggestion> customerSuggestionList = customerSuggestionService.listCustomerSuggestion(customerSuggestion);
                result.setData(customerSuggestionList);
                result.setStatus(ResultStatus.Success);
            }else{
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
}
