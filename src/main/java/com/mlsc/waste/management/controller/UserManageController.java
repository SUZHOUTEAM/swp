package com.mlsc.waste.management.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.management.service.UserManageService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.utils.datatable.DataTablesUtils;
import com.mlsc.waste.utils.datatable.FilterConditions;
import com.mlsc.waste.wastecircle.model.OfflineMessageVo;
import com.mlsc.waste.wastecircle.service.OfflineMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghj on 2017/7/4.
 */

@Controller
@RequestMapping("/userManage")
public class UserManageController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(UserManageController.class);


    @Autowired
    private UserManageService userManageService;

    private static final String PATH = "userManagement/";

    @Autowired
    private OfflineMessageService offlineMessageService;

    @RequestMapping("/index")
    public ModelAndView listUser(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "list");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/appList")
    public ModelAndView appList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "appList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/addAPP")
    public ModelAndView addAPP(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "addAPP");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/suggestList")
    public ModelAndView suggestList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "suggestList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/actionList")
    public ModelAndView actionList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "actionList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }
    /**
     * 返回json格式的行业列表
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, int start, int length, String draw, String userStatus) {
        Map<String, Object> result = new HashMap<String, Object>();
        int total = 0;

        // 构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);

        try {
            ResultData<User> resultData = userManageService.pageResultData(filterConditions,start, length,userStatus);
            total = resultData.getPaging().getTotalRecord();
            result = DataTablesUtils.generateResult(draw, total, total, resultData.getDatas());
        } catch (DaoAccessException e) {
            logger.error("datatable获取用户列表出错", e);
        }
        return result;
    }

    @RequestMapping("/auditUser")
    @ResponseBody
    public Result<String> auditUser(String ticketId, String userIds, String userStatus, String role) {
        Result<String> result = new Result<>();
        try {
            userManageService.updateUsers(userIds, userStatus, role,ticketId);
            User userParam = new User();
            userParam.setUserIds(userIds);
            LoginStatusUtils.refreshUserToCacheByUserParam(userParam);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("用户管理页面审核用户出错", e);
            result.getInfoList().add(Constant.SYS_MSG);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @RequestMapping("/offlineMsgList")
    public ModelAndView offlineMsgList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "offlineMsgList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/getOfflineMsgList")
    @ResponseBody
    public Map<String, Object> getOfflineMsgList(HttpServletRequest request, int start, int length, String draw) {
        List<OfflineMessageVo> list = null;
        int total = 0;
        // 构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);

        // 构造分页对象
        PagingParameter paging = DataTablesUtils.generatePagingParameter(start, length);
        // 统计符合条件的记录数
        try {
            total = offlineMessageService.getOfflineMsgcount(filterConditions.getWhere(), filterConditions.getParamMap());
            if (total > 0) {
                list = offlineMessageService.getOfflineMsgList(filterConditions.getWhere(), filterConditions.getParamMap(), paging);
            }
        } catch (Exception e) {
            logger.error("获取离线消息列表异常", e);
        }
        // 构造并返回结果集
        return DataTablesUtils.generateResult(draw, total, total, list);

    }

    @ResponseBody
    @RequestMapping("/getOfflineMsgById")
    public Result<Map<String, Object>> getOfflineMsgById(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String id = request.getParameter("id");
        String ticketId = request.getParameter("ticketId");
        try {
            OfflineMessageVo offlineMessageVo = offlineMessageService.getOfflineMsgById(id);
            dataMap.put("offlineMessageVo", JSONArray.toJSON(offlineMessageVo));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
            logger.error("查看离线消息详情异常", e);
        }
        result.setData(dataMap);
        return result;
    }
}
