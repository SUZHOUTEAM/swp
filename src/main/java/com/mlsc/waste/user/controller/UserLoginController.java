package com.mlsc.waste.user.controller;

import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户注册Controller
 *
 * @author zhugl
 */
@Controller
@RequestMapping("/userLogin")
@Scope("prototype")
public class UserLoginController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private UserLoginService userLoginService;


    /**
     * 用户登录验证
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public Result<Map<String, Object>> login(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 手机号码
        String phoneNum = request.getParameter("phoneNum");
        // 密码
        String password = request.getParameter("password");
        String resource = request.getParameter("resource");
        String openId = request.getParameter("openId");
        // 登录是否成功验证
        if (PropertyUtil.getValue("ssoOpen").equals("1")) {
            String clientMessage = NetworkUtil.getIpAddress(request);
            dataMap.put("ticketId", userLoginService.userLoginForClient(phoneNum, password, clientMessage));
        } else {
            dataMap.put("ticketId", userLoginService.userLogin(phoneNum, password, resource));
        }
        User user = LoginStatusUtils.getUserByTicketId(dataMap.get("ticketId").toString());
        if (StringUtils.isNotBlank(openId)) {
            userLoginService.updateUserExtended(user.getUserId(), openId);
        }
        dataMap.put("user", user);
        result.setStatus(ResultStatus.Success);

        result.setData(dataMap);
        return result;
    }


    /**
     * 用户找回密码
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/resetPassword")
    public Result<Map<String, Object>> resetPassword(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 手机号码
        String phoneNum = request.getParameter("phoneNum");
        // 密码
        String password = request.getParameter("password");
        // 短信验证码正确性验证
        boolean isSuccess = userLoginService.updateUserPasswordByPhoneNum(phoneNum, password);
        dataMap.put("isSuccess", isSuccess);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    /**
     * 用户退出操作
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        LoginStatusUtils.clearUserByTicketId(request.getParameter("ticketId"));
        String path = request.getContextPath();
        try {
            response.sendRedirect(path + "/login.jsp");
        } catch (IOException e) {
            logger.error("用户退出异常", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录成功后，根据业务不同，跳转不同的页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/loginSuccess")
    public ModelAndView loginSuccess(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        String ticketId = request.getParameter("ticketId");
        try {
            if (Util.isSysUser(ticketId)) {
                mv.setViewName("redirect:/" + "enterprisemanagement/list.htm");
            } else {
                User user = LoginStatusUtils.getUserByTicketId(ticketId);
                String entType = user.getEntType();

                if (StringUtils.isEmpty(entType) || CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equalsIgnoreCase(entType)) {//产废企业
                    mv.setViewName("redirect:/" + "entRelease/entWasteList.htm");
                } else {
                    mv.setViewName("redirect:/" + "wastecircle/init.htm");
                }
            }
            mv.addObject("ticketId", ticketId);
        } catch (Exception e) {
            mv = new ModelAndView(Constant.URL_500);
            logger.error("登录成功后，请求转发时异常", e);
        }

        return mv;
    }
}
