/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-08-23 03:36:15 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;

public final class top_jsp extends org.apache.jasper.runtime.HttpJspBase
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

   String appPath=request.getContextPath();
   String ticketId = (String)request.getAttribute("ticketId");
   User user = LoginStatusUtils.getUserByTicketId(ticketId);
   String title = request.getParameter("title");

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("   <meta charset=\"utf-8\">\r\n");
      out.write("   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\r\n");
      out.write("   <meta name=\"description\" content=\"Bootstrap Admin App + jQuery\">\r\n");
      out.write("   <meta name=\"keywords\" content=\"app, responsive, jquery, bootstrap, dashboard, admin\">\r\n");
      out.write("   <title></title>\r\n");
      out.write("   <meta name=\"keywords\" content=\"危废处置,危废询价,危废管家服务,处置危废\">\r\n");
      out.write("   <meta name=\"description\" content=\"易废网,危废处置,危废询价,处置危废,提供一站式固废解决方案\">\r\n");
      out.write("   <link rel=\"shortcut icon\" href=\"");
      out.print(appPath );
      out.write("/app/img/favicon.ico\">\r\n");
      out.write("   <!-- =============== VENDOR STYLES ===============-->\r\n");
      out.write("   <!-- FONT AWESOME-->\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/thirdparty/fontawesome/css/font-awesome.min.css\">\r\n");
      out.write("   <!-- SIMPLE LINE ICONS-->\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/thirdparty/simple-line-icons/css/simple-line-icons.css\">\r\n");
      out.write("   <!-- ANIMATE.CSS-->\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/thirdparty/animate.css/animate.min.css\">\r\n");
      out.write("   <!-- WHIRL (spinners)-->\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/thirdparty/whirl/dist/whirl.css\">\r\n");
      out.write("   <!-- =============== PAGE VENDOR STYLES ===============-->\r\n");
      out.write("   <!-- SWEET ALERT-->\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/thirdparty/sweetalert/dist/sweetalert.css?1\">\r\n");
      out.write("   <!-- =============== BOOTSTRAP STYLES ===============-->\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/app/css/bootstrap.css\" id=\"bscss\">\r\n");
      out.write("   <!-- =============== APP STYLES ===============-->\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/app/css/app.css?3\" id=\"maincss\">\r\n");
      out.write("\r\n");
      out.write("   <!-- jQuery -->\r\n");
      out.write("   <script type=\"text/javascript\" charset=\"utf8\" src=\"");
      out.print(appPath );
      out.write("/resources/static/js/jquery1x.js\"></script>\r\n");
      out.write("   <!-- BOOTSTRAP-->\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/thirdparty/bootstrap/dist/js/bootstrap.js\"></script>\r\n");
      out.write("   <!-- STORAGE API-->\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/thirdparty/jQuery-Storage-API/jquery.storageapi.js\"></script>\r\n");
      out.write("   <!-- JQUERY EASING-->\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/thirdparty/jquery.easing/js/jquery.easing.js\"></script>\r\n");
      out.write("   <!-- =============== PAGE VENDOR SCRIPTS ===============-->\r\n");
      out.write("   <!-- =============== APP SCRIPTS ===============-->\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/app/js/app.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/app/js/constants.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/thirdparty/layui/layer-v3.0.1/layer/layer.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/app/js/page.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/app/js/notify.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/thirdparty/parsleyjs/dist/parsley.min.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/thirdparty/sweetalert/dist/sweetalert.min.js?1\"></script>\r\n");
      out.write("   ");
      out.write("\r\n");
      out.write("   <!-- DATATABLES-->\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/thirdparty/datatables-colvis/css/dataTables.colVis.css\">\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/thirdparty/datatables/media/css/dataTables.bootstrap.css\">\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/thirdparty/dataTables.fontAwesome/index.css\">\r\n");
      out.write("   <!-- DATATABLES-->\r\n");
      out.write("   ");
      out.write("\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/thirdparty/datatables/media/js/jquery.dataTables.min.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/thirdparty/datatables-colvis/js/dataTables.colVis.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/thirdparty/datatables/media/js/dataTables.bootstrap.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/main/common/main.js\"></script>\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/css/main.css?3\">\r\n");
      out.write("   <link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/css/main-extends.css\">\r\n");
      out.write("   <!--[if lt IE 9]>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/resources/static/js/html5shiv.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/resources/static/js/respond.js\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(appPath );
      out.write("/resources/static/js/placeholder.js\"></script>\r\n");
      out.write("   <![endif]-->\r\n");
      out.write("   <script type=\"text/javascript\">\r\n");
      out.write("       var appPath='");
      out.print(appPath);
      out.write("';\r\n");
      out.write("       var ticketId='");
      out.print(ticketId);
      out.write("';\r\n");
      out.write("       $(\"title\").text(\"");
      out.print(title );
      out.write("-易废网\");\r\n");
      out.write("       $(document).ready(function(){\r\n");
      out.write("           window.webSiteRootUrl = \"");
      out.print(appPath );
      out.write("\";\r\n");
      out.write("           if (\"");
      out.print(Constant.SYS_USER_TYPE);
      out.write("\" == \"");
      out.print(user.getUserType());
      out.write("\") {// 平台管理员不能进入业务平台\r\n");
      out.write("               $(\".business-platform\").remove();\r\n");
      out.write("           }\r\n");
      out.write("       });\r\n");
      out.write("   </script>\r\n");
      out.write("   <!-- start Dplus --><script type=\"text/javascript\">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(\".\");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for(\"undefined\"!=typeof d?h=b[d]=[]:d=\"dplus\",h.people=h.people||[],h.toString=function(a){var b=\"dplus\";return\"dplus\"!==d&&(b+=\".\"+d),a||(b+=\" (stub)\"),b},h.people.toString=function(){return h.toString(1)+\".people (stub)\"},e=\"disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user\".split(\" \"),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement(\"script\"),c.type=\"text/javascript\",c.charset=\"utf-8\",c.async=!0,c.src=\"//w.cnzz.com/dplus.php?id=1262787667\",d=a.getElementsByTagName(\"script\")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init(\"1262787667\");</script><!-- end Dplus -->\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("   <!-- top navbar-->\r\n");
      out.write("   <header class=\"topnavbar-wrapper\">\r\n");
      out.write("      <!-- START Top Navbar-->\r\n");
      out.write("      <nav role=\"navigation\" class=\"navbar topnavbar\">\r\n");
      out.write("         <!-- START navbar header-->\r\n");
      out.write("         <div class=\"navbar-header\">\r\n");
      out.write("            <a href=\"#/\" class=\"navbar-brand\">\r\n");
      out.write("               <div class=\"brand-logo\">\r\n");
      out.write("                  <img src=\"");
      out.print(appPath );
      out.write("/app/img/logo.png\" alt=\"易废网\" class=\"img-responsive\">\r\n");
      out.write("               </div>\r\n");
      out.write("               <div class=\"brand-logo-collapsed\">\r\n");
      out.write("                  <img src=\"");
      out.print(appPath );
      out.write("/app/img/logo/logo_mini.png\" alt=\"易废网\" class=\"img-responsive\">\r\n");
      out.write("               </div>\r\n");
      out.write("            </a>\r\n");
      out.write("         </div>\r\n");
      out.write("         <!-- END navbar header-->\r\n");
      out.write("         <!-- START Nav wrapper-->\r\n");
      out.write("         <div class=\"nav-wrapper\">\r\n");
      out.write("            <!-- START Left navbar-->\r\n");
      out.write("            <ul class=\"nav navbar-nav\">\r\n");
      out.write("               <li>\r\n");
      out.write("                  <!-- Button used to collapse the left sidebar. Only visible on tablet and desktops-->\r\n");
      out.write("                  <a href=\"#\" data-toggle-state=\"aside-collapsed\" class=\"hidden-xs\">\r\n");
      out.write("                     <em class=\"fa fa-navicon\"></em>\r\n");
      out.write("                  </a>\r\n");
      out.write("                  <!-- Button to show/hide the sidebar on mobile. Visible on mobile only.-->\r\n");
      out.write("                  <a href=\"#\" data-toggle-state=\"aside-toggled\" data-no-persist=\"true\" class=\"visible-xs sidebar-toggle\">\r\n");
      out.write("                     <em class=\"fa fa-navicon\"></em>\r\n");
      out.write("                  </a>\r\n");
      out.write("               </li>\r\n");
      out.write("            </ul>\r\n");
      out.write("            <ul class=\"nav navbar-nav\">\r\n");
      out.write("               <!-- 平台信息-->\r\n");
      out.write("               <li class=\"platformtitle\">\r\n");
      out.write("               </li>\r\n");
      out.write("            </ul>\r\n");
      out.write("            <!-- END Left navbar-->\r\n");
      out.write("            <!-- START Right Navbar-->\r\n");
      out.write("            <ul class=\"nav navbar-nav navbar-right\">\r\n");
      out.write("               <li style=\"left: left\">\r\n");
      out.write("                  <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">");
      out.print(user.getUserName() );
      out.write("，您好 <span class=\"caret\"></span></a>\r\n");
      out.write("                  <ul class=\"dropdown-menu\">\r\n");
      out.write("                     <li class=\"business-platform\"><a href=\"");
      out.print(appPath );
      out.write("/wastecircle/init.htm?ticketId=");
      out.print(ticketId );
      out.write("\">业务平台</a></li>\r\n");
      out.write("                     <li class=\"quit-btn\"><a href=\"javascript:\" onclick=\"logout()\">退出</a></li>\r\n");
      out.write("                  </ul>\r\n");
      out.write("               </li>\r\n");
      out.write("            </ul>\r\n");
      out.write("            <!-- END Right Navbar-->\r\n");
      out.write("         </div>\r\n");
      out.write("         <!-- END Nav wrapper-->\r\n");
      out.write("      </nav>\r\n");
      out.write("      <!-- END Top Navbar-->\r\n");
      out.write("   </header>\r\n");
      out.write("   <script>\r\n");
      out.write("      function logout() {\r\n");
      out.write("          localStorage.ticketId='';\r\n");
      out.write("          window.location='");
      out.print(appPath );
      out.write("/userLogin/logout.htm?ticketId=");
      out.print(ticketId );
      out.write("';\r\n");
      out.write("      }\r\n");
      out.write("   </script>\r\n");
      out.write("<div class=\"wrapper\">");
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
