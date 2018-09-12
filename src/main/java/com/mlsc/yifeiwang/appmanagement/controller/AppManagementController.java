package com.mlsc.yifeiwang.appmanagement.controller;


import com.mlsc.yifeiwang.appmanagement.entity.AppManagement;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.yifeiwang.appmanagement.service.IAppManagementService;
import com.mlsc.yifeiwang.appmanagement.model.AppManagementParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2017-12-11
 */
@Controller
@RequestMapping("/appManagement")
public class AppManagementController {
    private static final String PATH = "userManagement/";

    @Autowired
    private IAppManagementService appManagementService;

    @ResponseBody
    @RequestMapping(value = "saveAppManagement", method = RequestMethod.POST)
    public Result<String> saveAppManagement(User user, @RequestBody AppManagement appRecord) {
        Result<String> result = new Result<String>();
        List<String> errorList = new ArrayList<String>();
        try {
            boolean validate = appManagementService.validateAppManagement(appRecord, errorList);
            if (validate) {
                AppManagement appManagement = appManagementService.saveAppManagement(user, appRecord);
                result.setData(appManagement.getId());
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


    @ResponseBody
    @RequestMapping(value = "updateAppManagement", method = RequestMethod.POST)
    public Result<Boolean> updateAppManagement(User user, @RequestBody AppManagement appRecord) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            if (appRecord != null && org.apache.commons.lang3.StringUtils.isNotBlank(appRecord.getId())) {
                Boolean flag = appManagementService.updateAppManagement(user, appRecord);
                result.setData(flag);
                result.setStatus(ResultStatus.Success);
            } else {
                List<String> errorList = new ArrayList<String>();
                errorList.add("手机APP管理信息为空");
                result.setData(false);
                result.setInfoList(errorList);
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @RequestMapping("/initAddAppManagement")
    public ModelAndView addOperation(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "addAPP");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/initListAppManagement")
    public ModelAndView initListAppManagement(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "appList");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/homeSetting")
    public ModelAndView homeSetting(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "homeSetting");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/homeEdit")
    public ModelAndView homeEdit(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "homeEdit");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "getLatestVersion", method = RequestMethod.POST)
    public Result<AppManagement> getLatestVersion(String ticketId, @RequestBody AppManagement appManagement) {
        Result<AppManagement> result = new Result<AppManagement>();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            AppManagement latestAppVersion = appManagementService.getLatestVersion(appManagement);
            result.setData(latestAppVersion);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "getAppManagementById", method = RequestMethod.POST)
    public Result<AppManagement> getAppManagementById(String ticketId, String id) {
        Result<AppManagement> result = new Result<AppManagement>();
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isNotBlank(id)) {
            try {
                AppManagement appManagement = appManagementService.selectById(id);
                result.setData(appManagement);
                result.setStatus(ResultStatus.Success);
            } catch (Exception e) {
                result.setStatus(ResultStatus.Error);
            }
        } else {
            errorList.add("手机APP版本表id为空");
            result.setInfoList(errorList);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "listAppManagement", method = RequestMethod.POST)
    public Result<Map<String, Object>> listAppManagement(PagingParameter paging, AppManagementParam managementParam) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<AppManagement> appManagementList = null;
        try {
            appManagementList = appManagementService.listAppManagement(paging, managementParam);
            resultMap.put("appList", appManagementList);
            resultMap.put("pagingParameter", paging);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "releaseAppManagementStatus", method = RequestMethod.POST)
    public Result<Boolean> releaseAppManagementStatus(User user, @RequestBody List<String> ids) {
        Result<Boolean> result = new Result<Boolean>();
        List<String> errorList = new ArrayList<String>();
        try {
            if (ids != null && ids.size() > 0) {
                boolean releaseStatus = appManagementService.releaseAppManagementStatus(user, ids);
                result.setData(releaseStatus);
                result.setStatus(ResultStatus.Success);
            } else {
                errorList.add("未选中任何要发布版本");
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "deleteAppMangement", method = RequestMethod.POST)
    public Result<Boolean> deleteAppMangement(User user, @RequestBody List<String> ids) {
        Result<Boolean> result = new Result<Boolean>();
        List<String> errorList = new ArrayList<String>();
        try {
            if (ids != null && ids.size() > 0) {
                boolean deleteResult = appManagementService.deleteAppMangement(user, ids);
                result.setData(deleteResult);
                result.setStatus(ResultStatus.Success);
            } else {
                errorList.add("未选中任何要删除版本");
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


}
