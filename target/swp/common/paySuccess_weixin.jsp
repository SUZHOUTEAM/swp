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
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,viewport-fit=cover">
<script>
    document.getElementsByTagName('html')[0].style.fontSize=window.innerWidth/10+'px';
    window.onresize=function() {
        document.getElementsByTagName('html')[0].style.fontSize=window.innerWidth/10+'px';
    };
</script>
<div class="header">
    <a href="javascript:" onclick="myBit();" class="back">‹</a>
    <span class="middle-text">充值成功</span>
    <a href="javascript:" onclick="myBit();" class="over">完成</a>
</div>
<%
    String appPath = request.getContextPath();
    String out_trade_no = request.getParameter("orderNo");
    ApplicationContext ctx =
            WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    if(ctx != null && ctx.getBean("entRechargeService") != null){
        IEntRechargeService entRechargeService = (IEntRechargeService) ctx.getBean("entRechargeService");
        EntRechargeParam entBindServeParam = new EntRechargeParam();
        entBindServeParam.setOrderNo(out_trade_no);
        EntRecharge entRecharge=entRechargeService.selectEntRecharge(entBindServeParam);
        if (entRecharge != null) {
            out.println("<div class=\"pay-success-icon\"></div>"+
                    "<div class=\"pay-success-div-text2\">恭喜您成功充值易废币<b>"+entRecharge.getBitCionAmount()+"</b>个</div>");
        }else{
            out.println("<div class=\"pay-success-div-text1\">验签失败！</div><div class=\"pay-success-div-text2\"></div>");
        }
    }
    out.println("<div class=\"btn-div\"><a href=\"javascript:;\" onclick=\"goOn()\" class=\"recharge-btn\">继续充值</a><a href=\"javascript:;\" onclick=\"buyMeal()\" class=\"buy-meal\">购买套餐</a></div>");
%>
<style>
    body{margin:0}.header,body{text-align:center}.header{height:1.2rem;line-height:1.2rem;border-bottom:1px solid #e7ebf6;display:table}.back{color:#38f;text-decoration:none;width:1.4rem}.back,.middle-text{display:table-cell;vertical-align:middle}.middle-text{font-size:.4rem;width:7.2rem;text-overflow:ellipsis;overflow:hidden;white-space:nowrap}.over{width:1.4rem;font-size:.38rem;text-decoration:none;color:#273e67;display:table-cell;vertical-align:middle}.btn-div{text-align:center}.btn-div a{display:inline-block;width:4rem;height:1rem;line-height:1rem;font-size:.4rem;text-decoration:none;border-radius:1rem;border:1px solid #2675ff}.btn-div a.recharge-btn{color:#2675ff;margin-right:.5rem}.btn-div a.buy-meal{background-color:#2675ff;color:#fff}.pay-success-div-text1{font-size:.5rem;margin:.8rem 0}.pay-success-icon{width:2rem;height:2rem;background:url(<%=appPath%>/main/mobile/img/pay-success.png) center center no-repeat;background-size:100% 100%;display:block;margin:1rem auto .3rem}.pay-success-div-text2{color:#6b7c9a;font-size:.38rem;margin-bottom:1rem}.pay-success-div-text2 b{font-size:.6rem;margin:0 .1rem;line-height:.4rem;position:relative;top:.06rem;color:#f96204;font-weight:400}
</style>
<script src="<%=appPath%>/main/common/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        collectingUserBehavior(localStorage.ticketId,'MB_PAY_ALI',$('.pay-success-div-text2 b').text()+'个易废币')
    });
    function goOn() {
        window.location='/mobile/#/my/recharge';
    }
    function buyMeal() {
        window.location='/mobile/#/my/service';
    }
    function myBit() {
        window.location='/mobile/#/my/account';
    }
</script>