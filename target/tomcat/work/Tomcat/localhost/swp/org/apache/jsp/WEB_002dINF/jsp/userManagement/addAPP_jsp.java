/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-09-10 09:11:29 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.userManagement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class addAPP_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');

    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");

      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/top.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("title", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("APP管理", request.getCharacterEncoding()), out, true);
      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/left.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("menuId", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("#appManager", request.getCharacterEncoding()), out, true);
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(appPath);
      out.write("/main/common/elementui/index.css\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(appPath);
      out.write("/css/user/appRecord.css\">\r\n");
      out.write("<script src=\"");
      out.print(appPath );
      out.write("/app/js/constants.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(appPath);
      out.write("/main/common/upload/upload.js\"></script>\r\n");
      out.write("<section id=\"app\">\r\n");
      out.write("    <template>\r\n");
      out.write("        <el-breadcrumb separator=\"/\">\r\n");
      out.write("            <el-breadcrumb-item>权限管理</el-breadcrumb-item>\r\n");
      out.write("            <el-breadcrumb-item onclick=\"history.go(-1);\">APP管理</el-breadcrumb-item>\r\n");
      out.write("            <el-breadcrumb-item>{{this.appId?'编辑':'创建'}}APP管理</el-breadcrumb-item>\r\n");
      out.write("        </el-breadcrumb>\r\n");
      out.write("        <div>\r\n");
      out.write("            <div>\r\n");
      out.write("                <el-form label-position=\"right\" label-width=\"160px\" style=\"max-width: 800px;margin-top: 16px;\"\r\n");
      out.write("                         :model=\"appRecord\" ref=\"appRecordValidateForm\" :rules=\"appRecordRules\">\r\n");
      out.write("                    <el-form-item label=\"app类型：\" prop=\"appType\">\r\n");
      out.write("                        <el-select v-model=\"appRecord.appType\" placeholder=\"请选择APP类型\" style=\"width: 100%\">\r\n");
      out.write("                            <el-option :label=\"item.value\" :value=\"item.code\" v-for=\"(item,index) in appTypeList\"\r\n");
      out.write("                                       :key=\"item.code\"></el-option>\r\n");
      out.write("                        </el-select>\r\n");
      out.write("                    </el-form-item>\r\n");
      out.write("                    <el-form-item label=\"企业类型：\" prop=\"entType\">\r\n");
      out.write("                        <el-select v-model=\"appRecord.entType\" placeholder=\"请选择企业类型\" style=\"width: 100%\">\r\n");
      out.write("                            <el-option :label=\"item.value\" :value=\"item.code\" v-for=\"(item,index) in entTypeList\"\r\n");
      out.write("                                       :key=\"item.code\"></el-option>\r\n");
      out.write("                        </el-select>\r\n");
      out.write("                    </el-form-item>\r\n");
      out.write("                    ");
      out.write("\r\n");
      out.write("                        ");
      out.write("\r\n");
      out.write("                        ");
      out.write("\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                            ");
      out.write("\r\n");
      out.write("                            ");
      out.write("\r\n");
      out.write("                        ");
      out.write("\r\n");
      out.write("                            ");
      out.write("\r\n");
      out.write("                    ");
      out.write("\r\n");
      out.write("                    <el-form-item label=\"版本号：\" prop=\"versionCode\">\r\n");
      out.write("                        <el-input v-model=\"appRecord.versionCode\" placeholder=\"请输入版本号\"></el-input>\r\n");
      out.write("                    </el-form-item>\r\n");
      out.write("                    <el-form-item label=\"更新日志：\" prop=\"description\">\r\n");
      out.write("                        <el-input v-model=\"appRecord.description\" type=\"textarea\" :rows=\"3\"\r\n");
      out.write("                                  placeholder=\"请输入更新日志，多个功能用“%”分割\"></el-input>\r\n");
      out.write("                    </el-form-item>\r\n");
      out.write("                    <el-form-item align=\"left\">\r\n");
      out.write("                        <el-button type=\"primary\" class=\"save\" @click=\"saveAppRecord\">保存</el-button>\r\n");
      out.write("                    </el-form-item>\r\n");
      out.write("                </el-form>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </template>\r\n");
      out.write("</section>\r\n");
      out.write("<!-- 先引入 Vue -->\r\n");
      out.write("<script src=\"");
      out.print(appPath);
      out.write("/main/common/elementui/vue.min.js\"></script>\r\n");
      out.write("<!-- 引入组件库 -->\r\n");
      out.write("<script src=\"");
      out.print(appPath);
      out.write("/main/common/elementui/index.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(appPath );
      out.write("/app/js/util.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(appPath );
      out.write("/app/js/constants.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(appPath);
      out.write("/main/common/upload/upload.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("    var vue = new Vue({\r\n");
      out.write("        el: '#app',\r\n");
      out.write("        appPath:'',\r\n");
      out.write("        data: {\r\n");
      out.write("            appId: getParam('appId') ? getParam('appId') : '',\r\n");
      out.write("            appRecord: {\r\n");
      out.write("                id: '',\r\n");
      out.write("                appType: '',\r\n");
      out.write("                entType: '',\r\n");
      out.write("                versionCode: '',\r\n");
      out.write("                description: '',\r\n");
      out.write("                fileId:'',\r\n");
      out.write("                fileName: ''\r\n");
      out.write("            },\r\n");
      out.write("            appTypeList: [{'value': 'ANDROID', 'code': 'ANDROID'}, {'value': 'IOS', 'code': 'IOS'}],\r\n");
      out.write("            entTypeList: [{'value': '处置企业', 'code': 'DISPOSITION'}, {'value': '产废企业', 'code': 'PRODUCTION'}],\r\n");
      out.write("            fileID: '',\r\n");
      out.write("            uploadAction: upload.IMG_URL + '/upload',\r\n");
      out.write("            appRecordAction: 'add',\r\n");
      out.write("            appRecordRules: {\r\n");
      out.write("                appType: [\r\n");
      out.write("                    {required: true, message: '请选择app类型', trigger: 'blur'}\r\n");
      out.write("                ],\r\n");
      out.write("                entType: [\r\n");
      out.write("                    {required: true, message: '请选择企业类型', trigger: 'blur'}\r\n");
      out.write("                ],\r\n");
      out.write("//                fileName: [\r\n");
      out.write("//                    {required: true, message: '请选择安装包', trigger: 'blur'}\r\n");
      out.write("//                ],\r\n");
      out.write("                description: [\r\n");
      out.write("                    {required: true, message: '请输入更新日志', trigger: 'blur'}\r\n");
      out.write("                ],\r\n");
      out.write("                versionCode: [\r\n");
      out.write("                    {required: true, message: '请输入版本号', trigger: 'blur'}\r\n");
      out.write("                ],\r\n");
      out.write("            },\r\n");
      out.write("            appRecordParam: {'appKey': APPKEY, 'prodID': PRODID, businessCode: upload.randomChar(19)},\r\n");
      out.write("            uploadPrev:IMG_PREV\r\n");
      out.write("        },\r\n");
      out.write("        mounted: function () {\r\n");
      out.write("            if(this.appId!=''){\r\n");
      out.write("                this.getAppInfo();\r\n");
      out.write("            }\r\n");
      out.write("        },\r\n");
      out.write("        methods: {\r\n");
      out.write("            saveAppRecord: function () {\r\n");
      out.write("                this.$refs['appRecordValidateForm'].validate(function (valid) {\r\n");
      out.write("                    if (valid) {\r\n");
      out.write("                        vue.saveAppRecordAction();\r\n");
      out.write("                    }\r\n");
      out.write("                });\r\n");
      out.write("            },\r\n");
      out.write("            getAppInfo: function () {\r\n");
      out.write("                ajax({\r\n");
      out.write("                    url: '/appManagement/getAppManagementById.htm',\r\n");
      out.write("                    data: {\r\n");
      out.write("                        id: this.appId\r\n");
      out.write("                    },\r\n");
      out.write("                    success: function (result) {\r\n");
      out.write("                        if(result.status==1){\r\n");
      out.write("                            vue.appRecord=result.data;\r\n");
      out.write("                        }\r\n");
      out.write("\r\n");
      out.write("                    }\r\n");
      out.write("                });\r\n");
      out.write("            },\r\n");
      out.write("            saveAppRecordAction: function () {\r\n");
      out.write("                if(vue.appId==''){\r\n");
      out.write("                    ajax({\r\n");
      out.write("                        url:'/appManagement/saveAppManagement.htm?ticketId=");
      out.print(ticketId);
      out.write("',\r\n");
      out.write("                        data:JSON.stringify(vue.appRecord),\r\n");
      out.write("                        contentType:'application/json',\r\n");
      out.write("                        success:function (result) {\r\n");
      out.write("                            if(result.status==1){\r\n");
      out.write("//                                vue.$refs.appRecordUpload.submit();\r\n");
      out.write("                                vue.appRecord.id= result.data;\r\n");
      out.write("                                $.notify('新增APP成功',{status:'success',timeout:2000});\r\n");
      out.write("                                setTimeout(function () {\r\n");
      out.write("                                    history.go(-1);\r\n");
      out.write("                                },2000)\r\n");
      out.write("                            }\r\n");
      out.write("                        }\r\n");
      out.write("                    })\r\n");
      out.write("                }else{\r\n");
      out.write("                    ajax({\r\n");
      out.write("                        url:'/appManagement/updateAppManagement.htm?ticketId=");
      out.print(ticketId);
      out.write("',\r\n");
      out.write("                        data:JSON.stringify(vue.appRecord),\r\n");
      out.write("                        contentType:'application/json',\r\n");
      out.write("                        success:function (result) {\r\n");
      out.write("                            if(result.status==1){\r\n");
      out.write("                                $.notify('更新APP成功',{status:'success',timeout:2000});\r\n");
      out.write("                                setTimeout(function () {\r\n");
      out.write("                                    history.go(-1);\r\n");
      out.write("                                },2000)\r\n");
      out.write("                                ");
      out.write("\r\n");
      out.write("                            }\r\n");
      out.write("                        }\r\n");
      out.write("                    })\r\n");
      out.write("                }\r\n");
      out.write("\r\n");
      out.write("            },\r\n");
      out.write("            imgUploadError: function (error, file, fileList) {\r\n");
      out.write("                console.log(error);\r\n");
      out.write("            },\r\n");
      out.write("            handleAvatarSuccess: function (file, fileList) {\r\n");
      out.write("                console.log('选择成功');\r\n");
      out.write("                Vue.set(this.appRecord, 'fileName', file.name);\r\n");
      out.write("            },\r\n");
      out.write("            appRecordImgUploadSuccess: function (response, file, fileList) {\r\n");
      out.write("                console.log(file);\r\n");
      out.write("                if (!file.response.success) {\r\n");
      out.write("                    $.notify('文件保存失败', {status: 'danger', timeout: 1000});\r\n");
      out.write("                    return;\r\n");
      out.write("                }\r\n");
      out.write("                var obj = file.response.message;\r\n");
      out.write("                var fileID = obj.fileID;\r\n");
      out.write("                vue.appRecord.fileId = fileID;\r\n");
      out.write("                ajax({\r\n");
      out.write("                    url:'/appManagement/updateAppManagement.htm?ticketId=");
      out.print(ticketId);
      out.write("',\r\n");
      out.write("                    data:JSON.stringify(vue.appRecord),\r\n");
      out.write("                    contentType:'application/json',\r\n");
      out.write("                    success:function (result) {\r\n");
      out.write("                        if(result.status==1){\r\n");
      out.write("                            $.notify('新增APP成功',{status:'success',timeout:2000});\r\n");
      out.write("                            setTimeout(function () {\r\n");
      out.write("                                history.go(-1);\r\n");
      out.write("                            },2000)\r\n");
      out.write("                            ");
      out.write("\r\n");
      out.write("                        }\r\n");
      out.write("                    }\r\n");
      out.write("                })\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("            },\r\n");
      out.write("            init:function(){\r\n");
      out.write("                this.appPath=$('#appPath').val();\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("    })\r\n");
      out.write("</script>\r\n");
      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/webCommon/bottom.jsp", out, true);
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    $(document).ready(function () {\r\n");
      out.write("    });\r\n");
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
