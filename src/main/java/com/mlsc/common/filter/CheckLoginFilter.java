package com.mlsc.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.util.ShortUrlUtil;
import com.mlsc.common.util.StringUtils;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.utils.GsonUtils;
import com.mlsc.sso.utils.HttpRequestUtils;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.NetworkUtil;
import com.mlsc.waste.utils.PropertyUtil;
import com.mlsc.yifeiwang.shorturl.service.IShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录过滤器
 */
public class CheckLoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(CheckLoginFilter.class);

    private String path;

    private IShortUrlService shortUrlService;

    public void init(FilterConfig filterConfig) throws ServletException {
        path = filterConfig.getServletContext().getContextPath();
        ServletContext sc = filterConfig.getServletContext();
        XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
        if (cxt != null && cxt.getBean("shortUrlService") != null && shortUrlService == null) {
            shortUrlService = (IShortUrlService) cxt.getBean("shortUrlService");
        }
    }

    public void destroy() {
        path = null;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String urls = request.getRequestURI().toString();
        if (!urls.endsWith(".htm") && !urls.endsWith(".jsp")) {
            String[] arr = urls.split("/");
            if (arr.length == 3 && !urls.contains(".")) {//短地址
                String code = ShortUrlUtil.getShortKey(urls);
                /* 根据随机码获取对应的长地址, 再重定向跳转 */
                String longUrl = this.shortUrlService.getLongUrlByShortCode(code);
                if (StringUtils.isNotNullOrEmpty(longUrl)) {
                    ((HttpServletResponse) response).sendRedirect(longUrl);
                } else {
                    ((HttpServletResponse) response).sendRedirect("http://www.yifeiwang.com/swp/main/mobile/view/index.html");
                }
                return;
            }
            chain.doFilter(request, response);
            return;
        }
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String requestType = request.getHeader("x-requested-with");
        String userAgent = request.getHeader("user-agent");
//        Enumeration e =  request.getHeaderNames();
//        while (e.hasMoreElements()){
//            Object obj = e.nextElement();
//            System.out.println(obj.toString()+":"+request.getHeader(obj.toString()));
//        }
        if (user == null) {

            if (path == null) {
                path = request.getContextPath();
            }


            if (doCheckedByNotTicketId(urls)) {//登录动作，用户注册动作，直接放行
            } else {
                if ((requestType != null && requestType.equals("XMLHttpRequest")) || checkApp(userAgent)) {
                    writeUnloginInfo((HttpServletResponse) response);
                } else {
                    ((HttpServletResponse) response).sendRedirect(path + "/login.jsp");
                }
                return;
            }
        } else {
            if (!doCheckedByNotTicketId(urls) && PropertyUtil.getValue("ssoOpen").equals("1")) {
                String clientMessage = NetworkUtil.getIpAddress(request);
                Boolean flag = checkClientMessage(ticketId, clientMessage);
                if (!flag) {//客户信息无效
                    if ((requestType != null && requestType.equals("XMLHttpRequest")) || checkApp(userAgent)) {
                        writeUnloginInfo((HttpServletResponse) response);
                    } else {
                        ((HttpServletResponse) response).sendRedirect(path + "/login.jsp");
                    }
                    return;
                }
            }
        }
        request.setAttribute("waste_user", user);
        request.setAttribute("ticketId", ticketId);
        chain.doFilter(request, response);
    }

    private boolean checkApp(String userAgent) {
        if (userAgent != null) {
            if (userAgent.indexOf("okhttp") != -1) {
                return true;
            }
            if (userAgent.indexOf("Darwin") != -1) {
                return true;
            }
        }
        return false;
    }


    private void writeUnloginInfo(HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.setStatus(HttpStatus.OK.value()); //设置状态码
            writer = response.getWriter();
            Result<?> result = new Result<>();
            result.setStatus(ResultStatus.LoginOut);
            result.getInfoList().add("登录信息已过期");
            String text = GsonUtils.serializeObject(result);
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean checkClientMessage(String ticketId, String clientMessage) {
        Boolean result = false;
        Map<String, String> params = new HashMap<String, String>();
        params.put("ticketId", ticketId);
        params.put("clientMessage", clientMessage);
        try {
            String ssoDemain = PropertyUtil.getValue("ssoDemain");
            String jsonStr = HttpRequestUtils.httpPost(ssoDemain + "userLogin/checkClientMessage", params).toString();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            if (jsonObject.getBooleanValue("data") == true) {//ip匹配
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 不需要ticketId的url请求
    private boolean doCheckedByNotTicketId(String urls) {
        boolean result = true;
        // 用户登录
// 用户注册
// 用户找回密码
        result = (urls.indexOf("login.jsp") > -1) || (urls.indexOf("gfLogin.jsp") > -1) ||(urls.indexOf("/userLogin/") > -1) || (urls.indexOf("/loginservice/") > -1) ||// 用户登录
                (urls.indexOf("register.jsp") > -1) || (urls.indexOf("/userRegister/") > -1) || // 用户注册
                (urls.indexOf("resetPassword.jsp") > -1) || // 用户找回密码
                (urls.indexOf("/userservice/getIdentifyCode") > -1) ||
                (urls.indexOf("/userservice/checkIdentifyCode") > -1) ||
                (urls.indexOf("/userservice/getUserAdminByEnterId") > -1) ||
                (urls.indexOf("/userservice/saveUserRegisterInfo") > -1) ||
                (urls.indexOf("404.jsp") > -1) || (urls.indexOf("500.jsp") > -1) ||
                (urls.indexOf("enterprise/getDifferentAreaEnterpriseInfoByEnterpriseType.htm") > -1) ||
                (urls.indexOf("/enterprise/getEnterpriseSuggest.htm") > -1) ||
                (urls.indexOf("/enterprise/getEnterpriseImg.htm") > -1) ||
                (urls.indexOf("/myenterprise/getHomePageEnterpriseInfoDetail.htm") > -1) ||
                (urls.indexOf("/enterprise/getCodeWasteDropDownList.htm") > -1) ||
                (urls.indexOf("/actionRecord/recordUserAction.htm") > -1) ||
                (urls.indexOf("/enterprise/getWasteNameDropDownList.htm") > -1) ||
                (urls.indexOf("/app/getAPPConfig.htm") > -1) ||
                (urls.indexOf("/enterpriseWaste/getPublishWaste.htm") > -1) ||
                (urls.indexOf("/imService//getIMAppKey") > -1) ||
                (urls.indexOf("/yunxin/monitorStatus") > -1) ||
                (urls.indexOf("/yunxin/getEnableYunXinAccount") > -1) ||
                (urls.indexOf("/customerSuggestion/saveCustomerSuggestion") > -1) ||
                (urls.indexOf("/listWasteEntRelease4MobileWeb.htm") > -1) ||
                (urls.indexOf("/getEnterpriseSummaryInfo.htm") > -1) ||
                (urls.indexOf("/common/paySuccess.jsp") > -1) ||
                (urls.indexOf("/common/paySuccess_weixin.jsp") > -1) ||
                (urls.indexOf("/common/pay.jsp") > -1) ||
                (urls.indexOf("/common/pay_mobile.jsp") > -1) ||
                (urls.indexOf("/weixin/payfailed") > -1) ||
                (urls.indexOf("/common/paySuccess_mobile.jsp") > -1) ||
                (urls.indexOf("listHomePageWasteActivity") > -1);

        return result;
    }

}
