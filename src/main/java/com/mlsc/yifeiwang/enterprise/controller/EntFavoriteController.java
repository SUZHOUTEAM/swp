package com.mlsc.yifeiwang.enterprise.controller;


import com.mlsc.yifeiwang.enterprise.entity.EntFavorite;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.service.IEntFavoriteService;
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
 * @since 2017-10-16
 */
@Controller
@RequestMapping("/entFavorite")
public class EntFavoriteController {
    @Autowired
    private IEntFavoriteService entFavoriteService;

    /**
     * 收藏
     *
     * @param user
     * @param entFavorite
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addEntFavorite")
    public Result<Boolean> addEntFavorite(User user, EntFavorite entFavorite) {
        Result<Boolean> result = new Result<Boolean>();
        List<String> errorList = new ArrayList<String>();
        try {
            boolean validate = entFavoriteService.validateEntFavorite(entFavorite, errorList);
            if (validate) {
                boolean insertResult = entFavoriteService.insertEntFavorite(user, entFavorite);
                result.setData(insertResult);
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
     * 取消收藏
     *
     * @param user
     * @param entFavorite
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cancelEntFavorite")
    public Result<Boolean> cancelEntFavorite(User user, EntFavorite entFavorite) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean cancelResult = entFavoriteService.cancelEntFavorite(user, entFavorite);
            result.setData(cancelResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
}
