/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-08-10 02:08:18 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.mlsc.common.util.AlipayConfig;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.mlsc.yifeiwang.bindserve.service.IEntRechargeService;
import com.mlsc.yifeiwang.bindserve.model.EntRechargeParam;
import com.mlsc.yifeiwang.bindserve.entity.EntRecharge;

public final class paySuccess_005fweixin_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,viewport-fit=cover\">\r\n");
      out.write("<script>\r\n");
      out.write("    document.getElementsByTagName('html')[0].style.fontSize=window.innerWidth/10+'px';\r\n");
      out.write("    window.onresize=function() {\r\n");
      out.write("        document.getElementsByTagName('html')[0].style.fontSize=window.innerWidth/10+'px';\r\n");
      out.write("    };\r\n");
      out.write("</script>\r\n");
      out.write("<div class=\"header\">\r\n");
      out.write("    <a href=\"javascript:\" onclick=\"myBit();\" class=\"back\">‹</a>\r\n");
      out.write("    <span class=\"middle-text\">充值成功</span>\r\n");
      out.write("    <a href=\"javascript:\" onclick=\"myBit();\" class=\"over\">完成</a>\r\n");
      out.write("</div>\r\n");

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

      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("    body{margin:0}.header,body{text-align:center}.header{height:1.2rem;line-height:1.2rem;border-bottom:1px solid #e7ebf6;display:table}.back{color:#38f;text-decoration:none;width:1.4rem}.back,.middle-text{display:table-cell;vertical-align:middle}.middle-text{font-size:.4rem;width:7.2rem;text-overflow:ellipsis;overflow:hidden;white-space:nowrap}.over{width:1.4rem;font-size:.38rem;text-decoration:none;color:#273e67;display:table-cell;vertical-align:middle}.btn-div{text-align:center}.btn-div a{display:inline-block;width:4rem;height:1rem;line-height:1rem;font-size:.4rem;text-decoration:none;border-radius:1rem;border:1px solid #2675ff}.btn-div a.recharge-btn{color:#2675ff;margin-right:.5rem}.btn-div a.buy-meal{background-color:#2675ff;color:#fff}.pay-success-div-text1{font-size:.5rem;margin:.8rem 0}.pay-success-icon{width:2rem;height:2rem;background:url(");
      out.print(appPath);
      out.write("/main/mobile/img/pay-success.png) center center no-repeat;background-size:100% 100%;display:block;margin:1rem auto .3rem}.pay-success-div-text2{color:#6b7c9a;font-size:.38rem;margin-bottom:1rem}.pay-success-div-text2 b{font-size:.6rem;margin:0 .1rem;line-height:.4rem;position:relative;top:.06rem;color:#f96204;font-weight:400}\r\n");
      out.write("</style>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    function goOn() {\r\n");
      out.write("        window.location='/mobile/#/my/recharge';\r\n");
      out.write("    }\r\n");
      out.write("    function buyMeal() {\r\n");
      out.write("        window.location='/mobile/#/my/service';\r\n");
      out.write("    }\r\n");
      out.write("    function myBit() {\r\n");
      out.write("        window.location='/mobile/#/my/account';\r\n");
      out.write("    }\r\n");
      out.write("</script>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}