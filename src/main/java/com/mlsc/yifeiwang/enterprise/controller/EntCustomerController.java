package com.mlsc.yifeiwang.enterprise.controller;


import com.mlsc.yifeiwang.enterprise.entity.EntCustomer;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.service.IEntCustomerService;
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
@RequestMapping("/entCustomer")
public class EntCustomerController {
    @Autowired
    private IEntCustomerService entCustomerService;

    @ResponseBody
    @RequestMapping(value = "saveEntCustomer")
    public Result<Boolean> saveEntCustomer(User user, EntCustomer entCustomer) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            List<String> errorList = new ArrayList<String>();
            if(entCustomerService.validateEntCustomer(entCustomer,errorList)){
                boolean hasCustomer = entCustomerService.saveEntCustomer(user, entCustomer);
                result.setData(hasCustomer);
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

    @ResponseBody
    @RequestMapping(value = "listEntCustomers")
    public Result<List<EntCustomer>> listEntCustomer(User user) {
        Result<List<EntCustomer>> result = new Result<List<EntCustomer>>();
        List entCustomers;
        try {
            entCustomers = entCustomerService.listEntCustomersByEntId(user);
            result.setData(entCustomers);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "getEntCustomer")
    public Result<EntCustomer> getEntCustomer(User user, String id) {
        Result<EntCustomer> result = new Result<EntCustomer>();
        EntCustomer entCustomer;
        try {
            entCustomer = entCustomerService.getEntCustomersById(id);
            result.setData(entCustomer);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "updateEntCustomer")
    public Result<Boolean> updateEntCustomer(User user, EntCustomer entCustomers) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean updateResult = entCustomerService.updateEntCustomer(user, entCustomers);
            result.setData(updateResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "deleteEntCustomer")
    public Result<Boolean> deleteEntCustomer(EntCustomer entCustomers) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean deleteResult = entCustomerService.deleteEntCustomer(entCustomers);
            result.setData(deleteResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


}
