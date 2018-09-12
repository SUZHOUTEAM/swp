/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-09-12 02:24:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.EntBindServe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class bindServeList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');

    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");

      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/top.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("title", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("绑定服务列表", request.getCharacterEncoding()), out, true);
      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/left.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("menuId", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("#bindServeList", request.getCharacterEncoding()), out, true);
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/main/common/elementui/index.css\">\r\n");
      out.write("<section id=\"app\" v-cloak>\r\n");
      out.write("    <el-breadcrumb separator=\"/\">\r\n");
      out.write("        <el-breadcrumb-item>服务管理</el-breadcrumb-item>\r\n");
      out.write("        <el-breadcrumb-item>购买服务列表</el-breadcrumb-item>\r\n");
      out.write("    </el-breadcrumb>\r\n");
      out.write("    <div >\r\n");
      out.write("        <el-form :model=\"entBindServeParam\" :inline=\"true\" class=\"demo-form-inline\">\r\n");
      out.write("            <el-form-item label=\"服务类型\">\r\n");
      out.write("                <el-select v-model=\"entBindServeParam.serviceType\" placeholder=\"选择服务类型\" style=\"margin:0 12px\" @change=\"search\">\r\n");
      out.write("                    <el-option v-for=\"item in serviceTypes\" :key=\"item.code\" :label=\"item.value\" :value=\"item.code\">\r\n");
      out.write("                    </el-option>\r\n");
      out.write("                </el-select>\r\n");
      out.write("            </el-form-item>\r\n");
      out.write("            ");
      out.write("\r\n");
      out.write("                ");
      out.write("\r\n");
      out.write("            ");
      out.write("\r\n");
      out.write("        </el-form>\r\n");
      out.write("        <el-table :data=\"entBindServeModels\" border style=\"width:100%\">\r\n");
      out.write("            <el-table-column\r\n");
      out.write("                    prop=\"userName\"\r\n");
      out.write("                    width=\"100px\"\r\n");
      out.write("                    label=\"用户名称\">\r\n");
      out.write("            </el-table-column>\r\n");
      out.write("            <el-table-column\r\n");
      out.write("                    prop=\"bindEntName\"\r\n");
      out.write("                    width=\"200px\"\r\n");
      out.write("                    label=\"所在公司\">\r\n");
      out.write("            </el-table-column>\r\n");
      out.write("            <el-table-column\r\n");
      out.write("                    prop=\"PhoneNum\"\r\n");
      out.write("                    width=\"120px\"\r\n");
      out.write("                    label=\"用户电话\">\r\n");
      out.write("            </el-table-column>\r\n");
      out.write("            <el-table-column\r\n");
      out.write("                    prop=\"serviceType\"\r\n");
      out.write("                    width=\"100px\"\r\n");
      out.write("                    label=\"服务类型\">\r\n");
      out.write("            </el-table-column>\r\n");
      out.write("            <el-table-column\r\n");
      out.write("                    prop=\"remark\"\r\n");
      out.write("                    label=\"备注\">\r\n");
      out.write("            </el-table-column>\r\n");
      out.write("        </el-table>\r\n");
      out.write("        <div v-show=\"entBindServeModels&&entBindServeModels.length>0\" align=\"right\">\r\n");
      out.write("            <el-pagination @size-change=\"handleSizeChange\" @current-change=\"handleCurrentChange\"\r\n");
      out.write("                           :current-page.sync=\"pageIndex\" :page-sizes=\"[10,20,30,50]\" :page-size=\"pageSize\"\r\n");
      out.write("                           layout=\"total, sizes, prev, pager, next, jumper\" :total=\"total\">\r\n");
      out.write("            </el-pagination>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</section>\r\n");
      out.write("<!-- 先引入 Vue -->\r\n");
      out.write("<script src=\"");
      out.print(appPath );
      out.write("/main/common/elementui/vue.min.js\"></script>\r\n");
      out.write("<!-- 引入组件库 -->\r\n");
      out.write("<script src=\"");
      out.print(appPath );
      out.write("/main/common/elementui/index.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("    var vue = new Vue({\r\n");
      out.write("        el: '#app',\r\n");
      out.write("        data: {\r\n");
      out.write("            entBindServeModels: [],\r\n");
      out.write("            pageIndex: 1,\r\n");
      out.write("            pageSize: 10,\r\n");
      out.write("            total: 0,\r\n");
      out.write("            entBindServeParam: {\r\n");
      out.write("                serviceType: ''\r\n");
      out.write("            },\r\n");
      out.write("            serviceTypes: [{code: 'RESOURCE_POOL', value: '资源池'}, {code: 'ACTIVITY', value: '活动'}, {\r\n");
      out.write("                code: 'GOLDEN',\r\n");
      out.write("                value: '公爵套餐'\r\n");
      out.write("            }, {code: 'SLIVER', value: '伯爵套餐'}, {code: 'CUPRUM', value: '候爵套餐'}]\r\n");
      out.write("        },\r\n");
      out.write("        created: function () {\r\n");
      out.write("            this.listBindServe();\r\n");
      out.write("        },\r\n");
      out.write("        mounted: function () {\r\n");
      out.write("\r\n");
      out.write("        },\r\n");
      out.write("        methods: {\r\n");
      out.write("            search:function () {\r\n");
      out.write("              this.pageIndex=1;\r\n");
      out.write("              this.entBindServeModels=[];\r\n");
      out.write("              this.listBindServe();\r\n");
      out.write("            },\r\n");
      out.write("            listBindServe:function () {\r\n");
      out.write("                var param = {\r\n");
      out.write("                    serviceType: this.entBindServeParam.serviceType\r\n");
      out.write("                };\r\n");
      out.write("                $.ajax({\r\n");
      out.write("                    url:'");
      out.print(appPath);
      out.write("/entBindServe/listBindServe.htm?ticketId=");
      out.print(ticketId);
      out.write("&pageIndex='+this.pageIndex+'&pageSize='+this.pageSize,\r\n");
      out.write("                    data:JSON.stringify(param),\r\n");
      out.write("                    dataType:'json',\r\n");
      out.write("                    contentType: 'application/json',\r\n");
      out.write("                    success: function (result) {\r\n");
      out.write("                        console.log(result);\r\n");
      out.write("                        if (result.status == 1) {\r\n");
      out.write("                            vue.entBindServeModels = result.data.entBindServeModels;\r\n");
      out.write("                            vue.total = result.data.pagingParameter.totalRecord;\r\n");
      out.write("                        }\r\n");
      out.write("                    }\r\n");
      out.write("                })\r\n");
      out.write("            },\r\n");
      out.write("            handleSizeChange:function (val) {\r\n");
      out.write("                this.pageIndex = 1;\r\n");
      out.write("                this.pageSize = val\r\n");
      out.write("                this.listBindServe();\r\n");
      out.write("            },\r\n");
      out.write("            handleCurrentChange:function (val) {\r\n");
      out.write("                this.pageIndex = val;\r\n");
      out.write("                this.listBindServe();\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("    });\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/bottom.jsp", out, true);
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
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
