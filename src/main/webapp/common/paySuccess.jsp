<%@ page import="java.util.*"%>
<%@ page import="com.mlsc.common.util.AlipayConfig"%>
<%@ page import="com.alipay.api.internal.util.AlipaySignature"%>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.mlsc.yifeiwang.bindserve.service.IEntRechargeService" %>
<%@ page import="com.mlsc.yifeiwang.bindserve.model.EntRechargeParam" %>
<%@ page import="com.mlsc.yifeiwang.bindserve.entity.EntRecharge" %>
<%@ page contentType="text/html; charset=UTF-8" language="java"
         errorPage="" %>
<link rel="stylesheet" href="../main/pc/css/common.css?8" />
<div class="header" style="position: relative">
    <div class="container">
        <div class="logo">
            <a href="#" tag="0" title="易废网"><img src="../main/pc/img/logo_short.png" alt="易废网 logo"></a>
        </div>
        <ul>
            <li  class="homePage">
                <a href="javascript:" title="首页" onclick="goIndex()">首页</a>
            </li>
            <li class="company_menu">
                <a href="javascript:" onclick="toPage('company')">企业信息</a>
            </li>
            <li class="company_menu">
                <a href="javascript:" onclick="toPage('activityList')">活动</a>
            </li>
        </ul>
    </div>
</div>
<%
    String appPath = request.getContextPath();
    try{
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE); //调用SDK验证签名
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            ApplicationContext ctx =
                    WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
            if(ctx != null && ctx.getBean("entRechargeService") != null){
                IEntRechargeService entRechargeService = (IEntRechargeService) ctx.getBean("entRechargeService");
                EntRechargeParam entBindServeParam = new EntRechargeParam();
                entBindServeParam.setOrderNo(out_trade_no);
                EntRecharge entRecharge=entRechargeService.selectEntRecharge(entBindServeParam);
                if (entRecharge != null) {
                    out.println("<div class=\"pay-success-div-text1\">支付成功！</div>"+
                            "<div class=\"pay-success-div-text2\">你已成功充值<span><b>"+entRecharge.getBitCionAmount()+"</b>个</span>易废币</div>");
                }else{
                    out.println("<div class=\"pay-success-div-text1\">验签失败！</div><div class=\"pay-success-div-text2\"></div>");
                }
            }
        }else {//验签失败
            out.println("<div class=\"pay-success-div-text1\">验签失败！</div><div class=\"pay-success-div-text2\"></div>");
        }
    }catch (Exception e){
        out.println("<div class=\"pay-success-div-text1\">验签失败！</div><div class=\"pay-success-div-text2\"></div>");
    }
    out.println("<a href=\"javascript:;\" onclick=\"backMyAccount()\" class=\"recharge-btn\">&lt;返回个人账户</a>");
    //——请在这里编写您的程序（以下代码仅作参考）——
%>
<link rel="shortcut icon" href="<%=appPath %>/app/img/favicon.ico">
<link rel="stylesheet" href="<%=appPath %>/main/pc/css/recharge.css">
<style>body{text-align: center;}</style>
<script src="<%=appPath%>/main/common/jquery.min.js"></script>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<script src="<%=appPath%>/app/js/util.js?1"></script>
<script type="text/javascript">
    $(document).ready(function () {
        collectingUserBehavior(localStorage.ticketId,'PC_PAY_ALI',$('.pay-success-div-text2 b').text()+'个易废币')
    });
    function backMyAccount() {
        if(localStorage.ticketId){
            window.location='<%=appPath%>/personaluser/myBit.htm?ticketId='+localStorage.ticketId;
        }else{
            window.location='<%=appPath%>/login.jsp?redirectURL=/personaluser/myBit.htm';
        }
    }
    function goIndex() {
        if(localStorage.ticketId){
            window.location='<%=appPath%>/index.html?ticketId='+localStorage.ticketId;
        }else{
            window.location='<%=appPath%>/index.html';
        }
    }
    function toPage(page) {
        if(localStorage.ticketId){
            window.location='<%=appPath%>/main/pc/view/'+page+'.html?ticketId='+localStorage.ticketId;
        }else{
            window.location='<%=appPath%>/main/pc/view/'+page+'.html';
        }
    }
</script>