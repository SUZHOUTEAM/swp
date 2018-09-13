package com.mlsc.yifeiwang.bindserve.controller;


import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.bindserve.entity.EntBindServe;
import com.mlsc.yifeiwang.bindserve.model.EntBindServeModel;
import com.mlsc.yifeiwang.bindserve.model.EntBindServeParam;
import com.mlsc.yifeiwang.bindserve.service.IEntBindServeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2018-04-26
 */
@Controller
@RequestMapping("/entBindServe")
public class EntBindServeController {
    private static final String PATH = "EntBindServe/";

    @Autowired
    private IEntBindServeService entBindServeService;

    /**
     * 企业绑定服务
     *
     * @param user
     * @param entBindServeParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveBindServe", method = RequestMethod.POST)
    public Result<String> save(User user, @RequestBody EntBindServeParam entBindServeParam) {
        Result<String> result = new Result<String>();
        try {
            List<String> errorList = entBindServeService.saveBindServe(user, entBindServeParam);
            if (StringUtils.isNotBlank(entBindServeParam.getId())) {
                result.setData(entBindServeParam.getId());
            }
            result.setInfoList(errorList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 更新绑定服务
     *
     * @param user
     * @param entBindServeParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateBindServe", method = RequestMethod.POST)
    public Result updateBindServe(User user, @RequestBody EntBindServeParam entBindServeParam) {
        Result result = new Result();
        try {
            entBindServeService.updateBindServe(user, entBindServeParam);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 处置企业交易记录列表
     *
     * @param user
     * @param entBindServeParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listBindServe",  method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Map<String, Object>> listBindServe(User user, @RequestBody EntBindServeParam entBindServeParam, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<>();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            entBindServeParam.setBindUserId(user.getUserId());
            List<EntBindServeModel> entBindServeModels = entBindServeService.listBindServe(entBindServeParam, pagingParameter);
            resultMap.put("entBindServeModels", entBindServeModels);
            resultMap.put("pagingParameter", pagingParameter);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 系统管理员查看绑定服务
     * @param request
     * @return
     */
    @RequestMapping("/intiBindServeList")
    public ModelAndView intiBindServeList(HttpServletRequest request,String ticketId) {
        ModelAndView mv = new ModelAndView(PATH + "bindServeList");
        mv.addObject("ticketId", ticketId);
        return mv;
    }


    /**
     * 获取管理服务套餐
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listButlerServicesByEntId", method = RequestMethod.POST)
    public Result<List<EntBindServe>> listButlerServicesByEntId(User user) {
        Result<List<EntBindServe>> result = new Result();
        try {
            EntBindServeParam entBindServeParam = new EntBindServeParam();
            entBindServeParam.setBindEntId(user.getEnterpriseId());
            List entBindServes =  entBindServeService.listButlerServicesByEntId(entBindServeParam);
            result.setData(entBindServes);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
}
