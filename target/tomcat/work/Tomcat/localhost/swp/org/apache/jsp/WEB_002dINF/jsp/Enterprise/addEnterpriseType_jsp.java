/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-08-28 02:55:16 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.Enterprise;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class addEnterpriseType_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005fform_0026_005fmodelAttribute_005fid_005fdata_002dparsley_002dvalidate_005fclass;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fform_005fform_0026_005fmodelAttribute_005fid_005fdata_002dparsley_002dvalidate_005fclass = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
    _005fjspx_005ftagPool_005fform_005fform_0026_005fmodelAttribute_005fid_005fdata_002dparsley_002dvalidate_005fclass.release();
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

      out.write('\n');
      out.write('\n');
      out.write('\n');

    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
    String enterpriseId = (String)request.getAttribute("enterpriseId");

      out.write('\n');
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write('\n');
      if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
        return;
      out.write('\n');
      out.write("\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/css/enterprise/enterprise_common.css?1\">\n");
      out.write("<style>\n");
      out.write(".panel .panel-heading {\n");
      out.write("    text-align: right;\n");
      out.write("}\n");
      out.write(".panel .panel-body {\n");
      out.write("    text-align: center;\n");
      out.write("}\n");
      out.write(".panel-footer {\n");
      out.write("    text-align: center;\n");
      out.write("}\n");
      out.write(".panel-footer ul{\n");
      out.write("    padding-left: 26%;\n");
      out.write("    width: 90%;\n");
      out.write("    text-align: left;\n");
      out.write("    margin: auto;\n");
      out.write("}\n");
      out.write(".areaSelect{\n");
      out.write("    position: relative;\n");
      out.write("    width: 94%;\n");
      out.write("    margin: 0 23px 40px;\n");
      out.write("    display: none;\n");
      out.write("    text-align: left;\n");
      out.write("}\n");
      out.write(".areaSelect select.form-control{\n");
      out.write("    width: 200px;\n");
      out.write("    display: inline-block;\n");
      out.write("}\n");
      out.write("</style>\n");
      out.write("<!-- Main section-->\n");
      out.write("<section>\n");
      out.write("    ");
      out.write("\n");
      out.write("   <!-- Page content-->\n");
      out.write("        <div class=\"bind-success-dialog\">\n");
      out.write("            <div class=\"bind-success-content\">\n");
      out.write("                <a href=\"");
      out.print(appPath );
      out.write("/myenterprise/myEnterprise.htm?ticketId=");
      out.print(ticketId );
      out.write("\" class=\"bind_dialog_close\"></a>\n");
      out.write("                <div class=\"bind_text\"><i class=\"success_icon\"></i>绑定企业成功！欢迎来到易废网~</div>\n");
      out.write("                <div style=\"text-align: center\">\n");
      out.write("                    <a href=\"");
      out.print(appPath);
      out.write("/entRelease/entWasteList.htm?ticketId=");
      out.print(ticketId);
      out.write("\" class=\"publish_btn\">危废处置询价></a>\n");
      out.write("                </div>\n");
      out.write("                ");
      out.write("\n");
      out.write("                    ");
      out.write("\n");
      out.write("                ");
      out.write("\n");
      out.write("                <img src=\"");
      out.print(appPath );
      out.write("/main/pc/img/publishProcedure.jpg\" class=\"procedure\">\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("   <div class=\"content-wrapper\">\n");
      out.write("       <div class=\"el-breadcrumb\">\n");
      out.write("        <span class=\"el-breadcrumb__item\">\n");
      out.write("            <span class=\"el-breadcrumb__item__inner\">企业信息</span>\n");
      out.write("            <span class=\"el-breadcrumb__separator\">/</span>\n");
      out.write("        </span>\n");
      out.write("           <span class=\"el-breadcrumb__item\">\n");
      out.write("            <span class=\"el-breadcrumb__item__inner\">确认企业类型</span>\n");
      out.write("            <span class=\"el-breadcrumb__separator\">/</span>\n");
      out.write("        </span>\n");
      out.write("       </div>\n");
      out.write("          <div class=\"panel panel-default\">\n");
      out.write("\n");
      out.write("              ");
      //  form:form
      org.springframework.web.servlet.tags.form.FormTag _jspx_th_form_005fform_005f0 = (org.springframework.web.servlet.tags.form.FormTag) _005fjspx_005ftagPool_005fform_005fform_0026_005fmodelAttribute_005fid_005fdata_002dparsley_002dvalidate_005fclass.get(org.springframework.web.servlet.tags.form.FormTag.class);
      _jspx_th_form_005fform_005f0.setPageContext(_jspx_page_context);
      _jspx_th_form_005fform_005f0.setParent(null);
      // /WEB-INF/jsp/Enterprise/addEnterpriseType.jsp(88,14) null
      _jspx_th_form_005fform_005f0.setDynamicAttribute(null, "class", "form-horizontal");
      // /WEB-INF/jsp/Enterprise/addEnterpriseType.jsp(88,14) name = modelAttribute type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setModelAttribute("enterprise");
      // /WEB-INF/jsp/Enterprise/addEnterpriseType.jsp(88,14) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setId("editForm");
      // /WEB-INF/jsp/Enterprise/addEnterpriseType.jsp(88,14) null
      _jspx_th_form_005fform_005f0.setDynamicAttribute(null, "data-parsley-validate", "true");
      int[] _jspx_push_body_count_form_005fform_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_form_005fform_005f0 = _jspx_th_form_005fform_005f0.doStartTag();
        if (_jspx_eval_form_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\n");
            out.write("              \t");
            out.write("\n");
            out.write("                    ");
            out.write("\n");
            out.write("                ");
            out.write("\n");
            out.write("                <div class=\"panel-body\" id=\"totalContainer\">\n");
            out.write("                <div class=\"form-group\">\n");
            out.write("                        <div class=\"col-md-4 enterpriseType\">\n");
            out.write("                         <div id=\"panel1\" class=\"panel panel-default coursor_enterprise\"  onclick=\"clickDivCheckBox('1')\">\n");
            out.write("                             <div class=\"panel-heading checkbox c-checkbox\">\n");
            out.write("                                 <label>\n");
            out.write("                                     <input type=\"checkbox\" id=\"production\" name=\"production\"/>\n");
            out.write("                                     <span class=\"fa fa-check\"></span>\n");
            out.write("                                 </label>\n");
            out.write("                             </div>\n");
            out.write("                             <div>\n");
            out.write("                                 <img alt=\"\" src=\"");
            out.print(appPath);
            out.write("/app/img/business_icon/production.png\">\n");
            out.write("                             </div>\n");
            out.write("                             <div class=\"panel-body\">\n");
            out.write("                                <p class=\"production_p\">产废企业</p>\n");
            out.write("                                <p class=\"enterprise_body\">产生危险废物需要处置</p>\n");
            out.write("                            </div>\n");
            out.write("                           </div>\n");
            out.write("                         </div>\n");
            out.write("                         <div class=\"col-md-4 enterpriseType\">\n");
            out.write("                          <div id=\"panel2\" class=\"panel panel-default coursor_enterprise\"  onclick=\"clickDivCheckBox('2')\">\n");
            out.write("                             <div class=\"panel-heading checkbox c-checkbox\">\n");
            out.write("\t\t\t\t                 <label>\n");
            out.write("\t\t\t\t                     <input type=\"checkbox\" id=\"disposition\" name=\"disposition\"/>\n");
            out.write("\t\t\t\t                     <span class=\"fa fa-check\"></span>\n");
            out.write("\t\t\t\t                 </label>\n");
            out.write("                             </div>\n");
            out.write("                             <div>\n");
            out.write("                                 <img alt=\"\" src=\"");
            out.print(appPath);
            out.write("/app/img/business_icon/disposition.png\">\n");
            out.write("                             </div>\n");
            out.write("                             <div class=\"panel-body\"  style=\"padding: 15px 0\">\n");
            out.write("                                <p class=\"disposition_p\">处置企业</p>\n");
            out.write("                                <p class=\"enterprise_body\">处置利用危险废物的持证单位</p>\n");
            out.write("                            </div>\n");
            out.write("                           </div>\n");
            out.write("                         </div>\n");
            out.write("                        <div class=\"col-md-4 enterpriseType\">\n");
            out.write("                            <div id=\"panel3\" class=\"panel panel-default coursor_enterprise\"  onclick=\"clickDivCheckBox('3')\">\n");
            out.write("                                <div class=\"panel-heading checkbox c-checkbox\">\n");
            out.write("                                    <label>\n");
            out.write("                                        <input type=\"checkbox\" id=\"DIS_FACILITATOR\" name=\"dis_facilitator\"/>\n");
            out.write("                                        <span class=\"fa fa-check\"></span>\n");
            out.write("                                    </label>\n");
            out.write("                                </div>\n");
            out.write("                                <div>\n");
            out.write("                                    <img alt=\"\" src=\"");
            out.print(appPath);
            out.write("/app/img/business_icon/identification.png\">\n");
            out.write("                                </div>\n");
            out.write("                                <div class=\"panel-body\">\n");
            out.write("                                    <p class=\"identification_p\">处置服务商</p>\n");
            out.write("                                    <p class=\"enterprise_body\">提供危险废物的处置服务</p>\n");
            out.write("                                </div>\n");
            out.write("                            </div>\n");
            out.write("                        </div>\n");
            out.write("                </div>\n");
            out.write("\n");
            out.write("                    <div class=\"areaSelect\">\n");
            out.write("                        <label>选择服务区域：</label>\n");
            out.write("                        <select class=\"form-control\" id=\"areaSelect\" name=\"responsibleArea\">\n");
            out.write("                        </select>\n");
            out.write("                        ");
            out.write("\n");
            out.write("\n");
            out.write("                    </div>\n");
            out.write("                    <div class=\"form-group\">\n");
            out.write("          \t\t        <div class=\"col-md-8\">\n");
            out.write("                            <button type=\"button\" id=\"saveExit\"  class=\"btn btn-primary\" onclick=\"confirmSubmit(this)\">确认</button>\n");
            out.write("                        </div>\n");
            out.write("          \t        </div>\n");
            out.write("                </div>\n");
            out.write("              ");
            int evalDoAfterBody = _jspx_th_form_005fform_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_form_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
      } catch (java.lang.Throwable _jspx_exception) {
        while (_jspx_push_body_count_form_005fform_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_form_005fform_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_form_005fform_005f0.doFinally();
        _005fjspx_005ftagPool_005fform_005fform_0026_005fmodelAttribute_005fid_005fdata_002dparsley_002dvalidate_005fclass.reuse(_jspx_th_form_005fform_005f0);
      }
      out.write("\n");
      out.write("          </div>\n");
      out.write("   </div>\n");
      out.write("</section>\n");
      out.write("\n");
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/bottom.jsp", out, true);
      out.write("\n");
      out.write("<script src=\"");
      out.print(appPath );
      out.write("/main/common/provinces.js?5\"></script>\n");
      out.write('\n');
      out.write('\n');
      out.write("\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("var msg = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\";\n");
      out.write("var status = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${status}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\";\n");
      out.write("var enterpriseId = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${enterpriseId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\";\n");
      out.write("\n");
      out.write("$(document).ready(function(){\n");
      out.write("//  var rs = window.globalInit();\n");
      out.write("//  rs.done(function () {\n");
      out.write("    // 初始情况下，按钮不可用\n");
      out.write("    $(\"#saveExit\").attr(\"disabled\",\"disabled\");\n");
      out.write("//      $('[data-toggle=\"city-picker\"]').citypicker({\n");
      out.write("//          responsive:true\n");
      out.write("//      });\n");
      out.write("//  });\n");
      out.write("    var str='<option value=\"\">请选择服务区域</option>';\n");
      out.write("    for(var code in serviceProvince){\n");
      out.write("        str+='<option value=\"'+code+'\">'+serviceProvince[code]+'</option>';\n");
      out.write("    }\n");
      out.write("    $('#areaSelect').html(str);\n");
      out.write("\n");
      out.write("});\n");
      out.write("\n");
      out.write("function confirmSubmit(that){\n");
      out.write("    console.log($('#areaSelect').val());\n");
      out.write("    if($('#DIS_FACILITATOR')[0].checked && !$('#areaSelect').val()){\n");
      out.write("        $.notify(\"请选择服务区域\",{status:'danger',timeout:1500});\n");
      out.write("        return;\n");
      out.write("    }\n");
      out.write("\tvar berforeText = $(that).text();\n");
      out.write("\t$(that).before(\"<i class='btn_loading'></i>\").text(\"保存中...\").parent().find(\"button\").attr(\"disabled\",\"disabled\");\n");
      out.write("\t$.post('");
      out.print(appPath);
      out.write("/enterprise/saveEnterpriseType.htm?ticketId=");
      out.print(ticketId );
      out.write("&enterpriseId=");
      out.print(enterpriseId );
      out.write("&etc='+new Date().getTime(),$('form').serialize(),function(result){\n");
      out.write("\t\tvar obj = $.parseJSON(result);\n");
      out.write("\t    msg = obj.data.msg;\n");
      out.write("\t    enterpriseId = obj.data.enterpriseId;\n");
      out.write("\t    if(obj.status == 1){\n");
      out.write("\t        if($('#production')[0].checked){\n");
      out.write("\t            $('.bind-success-dialog').show();\n");
      out.write("\t        }else{\n");
      out.write("                var options={\"status\":\"success\"};\n");
      out.write("                $.notify(msg,options);\n");
      out.write("                setTimeout(\"toUrl()\",1000);\n");
      out.write("            }\n");
      out.write("\t    }else{\n");
      out.write("\t    \tvar options={\"status\":\"danger\"};\n");
      out.write("\t        $.notify(msg,options);\n");
      out.write("\t        $(that).text(berforeText).parent().find(\"button\").removeAttr(\"disabled\");\n");
      out.write("            $(that).prev(\".btn_loading\").remove();\n");
      out.write("\t    }\n");
      out.write("\t   });\n");
      out.write("}\n");
      out.write("\n");
      out.write("function clickDivCheckBox(value){\n");
      out.write("\tif(value == '1'){\n");
      out.write("\t\tvar isChecked = document.getElementById(\"production\").checked;\n");
      out.write("\t\tvar div = document.getElementById('panel1');\n");
      out.write("        $('.areaSelect').hide();\n");
      out.write("\t\tif(isChecked){\n");
      out.write("\t\t\tdiv.style.borderColor = \"#cfdbe2\";\n");
      out.write("\t\t\tdocument.getElementById(\"production\").checked = false;\n");
      out.write("\t\t\tifChecked();\n");
      out.write("\t\t}else{\n");
      out.write("\t\t\tdocument.getElementById(\"production\").checked = true;\n");
      out.write("\t\t\tdiv.style.borderColor = \"#4f5565\";\n");
      out.write("\t\t\tdocument.getElementById(\"disposition\").checked = false;\n");
      out.write("\t\t\tdocument.getElementById('panel2').style.borderColor = \"#cfdbe2\";\n");
      out.write("            document.getElementById(\"DIS_FACILITATOR\").checked = false;\n");
      out.write("            document.getElementById('panel3').style.borderColor = \"#cfdbe2\";\n");
      out.write("\t\t\tifChecked();\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("\tif(value == '2'){\n");
      out.write("\t\tvar isChecked = document.getElementById(\"disposition\").checked;\n");
      out.write("\t\tvar div = document.getElementById('panel2');\n");
      out.write("        $('.areaSelect').hide();\n");
      out.write("\t\tif(isChecked){\n");
      out.write("\t\t\tdiv.style.borderColor = \"#cfdbe2\";\n");
      out.write("\t\t\tdocument.getElementById(\"disposition\").checked = false;\n");
      out.write("\t\t\tifChecked();\n");
      out.write("\t\t}else{\n");
      out.write("\t\t\tdocument.getElementById(\"disposition\").checked = true;\n");
      out.write("\t\t\tdiv.style.borderColor = \"#18caa6\";\n");
      out.write("\t\t\tdocument.getElementById(\"production\").checked = false;\n");
      out.write("\t\t\tdocument.getElementById('panel1').style.borderColor = \"#cfdbe2\";\n");
      out.write("            document.getElementById(\"DIS_FACILITATOR\").checked = false;\n");
      out.write("            document.getElementById('panel3').style.borderColor = \"#cfdbe2\";\n");
      out.write("\t\t\tifChecked();\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("\tif(value == '3'){\n");
      out.write("\t\tvar isChecked = document.getElementById(\"DIS_FACILITATOR\").checked;\n");
      out.write("\t\tvar div = document.getElementById('panel3');\n");
      out.write("\t\tif(isChecked){\n");
      out.write("            $('.areaSelect').hide();\n");
      out.write("\t\t\tdiv.style.borderColor = \"#cfdbe2\";\n");
      out.write("\t\t\tdocument.getElementById(\"DIS_FACILITATOR\").checked = false;\n");
      out.write("\t\t\tifChecked();\n");
      out.write("\t\t}else{\n");
      out.write("            $('.areaSelect').show();\n");
      out.write("\t\t\tdocument.getElementById(\"DIS_FACILITATOR\").checked = true;\n");
      out.write("\t\t\tdiv.style.borderColor = \"#9900ff\";\n");
      out.write("\t\t\tdocument.getElementById(\"production\").checked = false;\n");
      out.write("\t\t\tdocument.getElementById('panel1').style.borderColor = \"#cfdbe2\";\n");
      out.write("            document.getElementById(\"disposition\").checked = false;\n");
      out.write("            document.getElementById('panel2').style.borderColor = \"#cfdbe2\";\n");
      out.write("\t\t\tifChecked();\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("function ifChecked(){\n");
      out.write("\tvar isCheckedProduction = document.getElementById(\"production\").checked;\n");
      out.write("\tvar isCheckedDisposition = document.getElementById(\"disposition\").checked;\n");
      out.write("//\tvar isCheckedIdentification = document.getElementById(\"identification\").checked;\n");
      out.write("\tvar isCheckedDIS_FACILITATOR=document.getElementById(\"DIS_FACILITATOR\").checked;\n");
      out.write("\tif(isCheckedProduction || isCheckedDisposition ||isCheckedDIS_FACILITATOR){\n");
      out.write("\t\t$(\"#saveExit\").removeAttr(\"disabled\");\n");
      out.write("\t}else{\n");
      out.write("\t\t$(\"#saveExit\").attr(\"disabled\",\"disabled\");\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("\n");
      out.write("//跳转到易废圈\n");
      out.write("function toUrl(){\n");
      out.write("    if(\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isSysUser}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\" == \"true\"){\n");
      out.write("        location=\"");
      out.print(appPath);
      out.write("/enterprisemanagement/list.htm?ticketId=");
      out.print(ticketId );
      out.write("\";\n");
      out.write("    }else{\n");
      out.write("        location=\"");
      out.print(appPath);
      out.write("/wastecircle/init.htm?ticketId=");
      out.print(ticketId );
      out.write("&enterpriseId=\"+enterpriseId; \n");
      out.write("    }\n");
      out.write("}\n");
      out.write("\n");
      out.write("//显示信息\n");
      out.write("function hideTipMessage() {\n");
      out.write("    var notice = document.getElementById(\"tip_notice\");\n");
      out.write("    $(notice).slideToggle();\n");
      out.write("    var tar = notice.getElementsByTagName(\"span\")[0];\n");
      out.write("    tar.innerHTML = \"\";\n");
      out.write("}\n");
      out.write("</script>\n");
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

  private boolean _jspx_meth_c_005fif_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest)_jspx_page_context.getRequest();
    javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse)_jspx_page_context.getResponse();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    // /WEB-INF/jsp/Enterprise/addEnterpriseType.jsp(9,0) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isSysUser}", java.lang.Boolean.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("    ");
        org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/top.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("title", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("企业信息", request.getCharacterEncoding()), out, true);
        out.write("\n");
        out.write("    ");
        org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/left.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("menuId", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("#enterpriseManagement", request.getCharacterEncoding()), out, true);
        out.write('\n');
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest)_jspx_page_context.getRequest();
    javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse)_jspx_page_context.getResponse();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent(null);
    // /WEB-INF/jsp/Enterprise/addEnterpriseType.jsp(17,0) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${not isSysUser}", java.lang.Boolean.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("    ");
        org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/webCommon/user_top.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("title", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("企业信息", request.getCharacterEncoding()), out, true);
        out.write("\n");
        out.write("    ");
        org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/webCommon/wasteCircleMenu.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("menuId", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("#myEnterprise", request.getCharacterEncoding()), out, true);
        out.write('\n');
        int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
    return false;
  }
}