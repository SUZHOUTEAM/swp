/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-09-01 08:33:15 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.Waste;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class list_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\n');
      out.write('\n');

    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");

      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/top.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("title", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("危废名录", request.getCharacterEncoding()), out, true);
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/left.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("menuId", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("#wasteItem", request.getCharacterEncoding()), out, true);
      out.write("\n");
      out.write("\n");
      out.write("<section>\n");
      out.write("    <div class=\"el-breadcrumb\">\n");
      out.write("        <span class=\"el-breadcrumb__item\">\n");
      out.write("            <span class=\"el-breadcrumb__item__inner\">基础数据管理</span>\n");
      out.write("            <span class=\"el-breadcrumb__separator\">/</span>\n");
      out.write("        </span>\n");
      out.write("        <span class=\"el-breadcrumb__item\">\n");
      out.write("            <span class=\"el-breadcrumb__item__inner\"  onclick=\"history.go(-1);\">危废名录管理</span>\n");
      out.write("            <span class=\"el-breadcrumb__separator\">/</span>\n");
      out.write("        </span>\n");
      out.write("    </div>\n");
      out.write("    <div>\n");
      out.write("        <div class=\"row\">\n");
      out.write("            <div class=\"col-xs-12\">\n");
      out.write("                <div class=\"tool-div\">\n");
      out.write("                    <div class=\"btn-group\" role=\"group\">\n");
      out.write("                        <a class=\"btn btn-default\" href=\"#\" role=\"button\"  onclick=\"add(this)\">新增</a>\n");
      out.write("                        <a class=\"btn btn-default\" href=\"#\" role=\"button\"  onclick=\"edit(this)\">编辑</a>\n");
      out.write("                        ");
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"btn-group\" role=\"group\">\n");
      out.write("                        <a class=\"btn btn-default\" href=\"#\" role=\"button\"  onclick=\"enableWaste(this)\">启用</a>\n");
      out.write("                        <a class=\"btn btn-default\" href=\"#\" role=\"button\"  onclick=\"disableWaste(this)\">停用</a>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"waste-panel\">\n");
      out.write("                    <table id=\"example\" class=\"table table-striped table-hover table-condensed\">\n");
      out.write("                        <thead>\n");
      out.write("                            <tr>\n");
      out.write("                                <th>\n");
      out.write("                                    <label class=\"checkbox-inline c-checkbox\">\n");
      out.write("                                        <input id=\"checkAll\" name=\"checkAll\" type=\"checkbox\">\n");
      out.write("                                        <span class=\"fa fa-check\"></span>\n");
      out.write("                                    </label>\n");
      out.write("                                </th>\n");
      out.write("                                <th>废物类别</th>\n");
      out.write("                                <th>行业来源</th>\n");
      out.write("                                <th>八位码</th>\n");
      out.write("                                <th>危废废物名称</th>\n");
      out.write("                                <th>危险特性</th>\n");
      out.write("                                <th>别称</th>\n");
      out.write("                                <th>更新时间</th>\n");
      out.write("                            </tr>\n");
      out.write("                        </thead>\n");
      out.write("                        <tbody></tbody>\n");
      out.write("                            <tfoot class='filter'>\n");
      out.write("                                <tr>\n");
      out.write("                                    <th></th>\n");
      out.write("                                    <th>废物类别</th>\n");
      out.write("                                    <th>行业来源</th>\n");
      out.write("                                    <th>八位码</th>\n");
      out.write("                                    <th>危废废物名称</th>\n");
      out.write("                                    <th>危险特性</th>\n");
      out.write("                                    <th>别称</th>\n");
      out.write("                                    <th class=\"hidden-xs\"></th>\n");
      out.write("                                </tr>\n");
      out.write("                            </tfoot>\n");
      out.write("                    </table>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("</section>\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/bottom.jsp", out, true);
      out.write("\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("var table ;\n");
      out.write("var searchTimeOut;//timeout对象\n");
      out.write("var idx;//过虑的列序号\n");
      out.write("var val;//过虑值\n");
      out.write("function search2() {\n");
      out.write("    table.column( idx ).search( val ).draw();\n");
      out.write("}\n");
      out.write("\n");
      out.write("$(document).ready( function () {\n");
      out.write("    $('#example tfoot th').each( function () {\n");
      out.write("        if($(this).index() != 7){\n");
      out.write("            var title = $('#example tfoot th').eq( $(this).index() ).text();\n");
      out.write("            \n");
      out.write("            if(title!=''){\n");
      out.write("                $(this).html( '<input type=\"text\" placeholder=\"搜索 '+title+'\" class=\"datatable_input_col_search\"/>' );\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("    } );\n");
      out.write("    \n");
      out.write("   // DataTable\n");
      out.write("    table = $('#example').DataTable({\n");
      out.write("        \"processing\": true,\n");
      out.write("        \"serverSide\": true,\n");
      out.write("        \"ajax\": $.fn.dataTable.pipeline( {\n");
      out.write("            url: '");
      out.print(appPath);
      out.write("/Waste/listForJson.htm?ticketId=");
      out.print(ticketId );
      out.write("',\n");
      out.write("            pages: 7 // number of pages to cache\n");
      out.write("        } ),\n");
      out.write("        \"columns\": [\n");
      out.write("                    { \"data\": \"id\", \"width\": \"40px\", \n");
      out.write("                        \"render\": function(data, type, row) {\n");
      out.write("                            return '<label class=\"checkbox-inline c-checkbox\"><input type=\"checkbox\" class=\"checkCalling\" name=\"id\" value=\"'+ data +'\">'\n");
      out.write("                              +'<span class=\"fa fa-check\"></span></label>';\n");
      out.write("                        },\n");
      out.write("                        \"orderable\": false,\n");
      out.write("                    },\n");
      out.write("                    { \"data\": \"wasteType\", \"width\": \"90px\"},\n");
      out.write("                    { \"data\": \"callingName\", \"width\": \"90px\" },\n");
      out.write("                    { \"data\": \"code\",\"width\": \"85px\" },\n");
      out.write("                    { \"data\": \"description\", \"width\": \"190px\" },\n");
      out.write("                    { \"data\": \"riskCharacteristics\", \"width\": \"80px\", \"className\": \"hidden-xs hidden-sm\" },\n");
      out.write("                    { \"data\": \"w_name\", \"width\": \"90px\", \"className\": \"hidden-xs hidden-sm\" },\n");
      out.write("                    { \"data\": \"edit_time\", \"width\": \"90px\", \"className\": \"hidden-xs hidden-sm\" }\n");
      out.write("        ],\n");
      out.write("        \"ordering\": false,\n");
      out.write("        \"pagingType\": \"simple\",\n");
      out.write("        \"language\": language,\n");
      out.write("        \"scrollX\": true,\n");
      out.write("        \"dom\": 'rt<\"bottom\"lip >'\n");
      out.write("    });\n");
      out.write("   \n");
      out.write("    function search( colIdx,value ) {\n");
      out.write("        table\n");
      out.write("           .column( colIdx )\n");
      out.write("           .search( value )\n");
      out.write("           .draw();\n");
      out.write("    }\n");
      out.write("   \n");
      out.write("     // Apply the search\n");
      out.write("    table.columns().eq( 0 ).each( function ( colIdx ) {\n");
      out.write("        $( 'input,select', table.column( colIdx ).footer() ).on( 'keyup change propertychange input', function () {\n");
      out.write("                clearTimeout(searchTimeOut);\n");
      out.write("                idx = colIdx;\n");
      out.write("                val = this.value;\n");
      out.write("                searchTimeOut = setTimeout(\"search2()\", 1000);\n");
      out.write("        } );\n");
      out.write("    } );\n");
      out.write("     \n");
      out.write("    function initTableCheckbox(){\n");
      out.write("        /*“全选/反选”复选框*/  \n");
      out.write("        var $checkAll = $('#checkAll');\n");
      out.write("        $checkAll.click(function(event){  \n");
      out.write("            /*将所有行的选中状态设成全选框的选中状态*/  \n");
      out.write("            $('.checkCalling').prop('checked',$(this).prop('checked')); \n");
      out.write("        });  \n");
      out.write("\n");
      out.write("        /*点击每一行的选中复选框时*/  \n");
      out.write("        $('.checkCalling').click(function(event){\n");
      out.write("            $checkAll.prop('checked',$('table tbody tr').find('input:checked').length == $('.checkCalling').length ? true : false);  \n");
      out.write("\n");
      out.write("        }); \n");
      out.write("    }  \n");
      out.write("    initTableCheckbox();  \n");
      out.write("} );\n");
      out.write("\n");
      out.write("function edit(t){\n");
      out.write("    var $checkItem = $(\"input[name='id']:checked\");\n");
      out.write("    if ($checkItem.length == 1) {\n");
      out.write("        var paramObj = {};\n");
      out.write("        paramObj.id = $checkItem.val();\n");
      out.write("        var pageUrl = \"/Waste/edit.htm\";\n");
      out.write("        $.page.gotoTargetLocation(pageUrl, paramObj);\n");
      out.write("    } else {\n");
      out.write("        $.notify(\"请选择一条进行编辑\", {\"status\": \"info\"});\n");
      out.write("    }\n");
      out.write("}\n");
      out.write("\n");
      out.write("function add(t){\n");
      out.write("    var paramObj = {};\n");
      out.write("    var pageUrl = \"/Waste/add.htm\";\n");
      out.write("    $.page.gotoTargetLocation(pageUrl, paramObj);\n");
      out.write("}\n");
      out.write("\n");
      out.write("function del(t){\n");
      out.write("    var $checkItem = $(\"input[name='id']:checked\");\n");
      out.write("    if ($checkItem.length == 0) {\n");
      out.write("        $.notify(\"请选择一条或多条进行删除\", {\"status\": \"info\"});\n");
      out.write("    } else {\n");
      out.write("        swal({  title: \"确认删除\",   \n");
      out.write("            text: \"请确认是否删除数据\",   \n");
      out.write("            type: \"warning\",   \n");
      out.write("            showCancelButton: true,   \n");
      out.write("            confirmButtonColor: \"#DD6B55\",   \n");
      out.write("            confirmButtonText: \"删除\",   \n");
      out.write("            cancelButtonText: \"取消\",   \n");
      out.write("            closeOnConfirm: false,   \n");
      out.write("            closeOnCancel: false \n");
      out.write("        }, function(isConfirm){\n");
      out.write("            if(isConfirm){\n");
      out.write("                $.page.ajax($.page.getAjaxSettings({\n");
      out.write("                    async: false,\n");
      out.write("                    type: \"POST\",\n");
      out.write("                    dataType: \"json\",\n");
      out.write("                    url: \"/Waste/removeWaste.htm\",\n");
      out.write("                    data: $checkItem.serialize(),\n");
      out.write("                    success: function (result, textStatus, jqXHR) {\n");
      out.write("                        swal(\"删除成功!\", \"删除成功!\", \"success\");\n");
      out.write("                        location.reload();\n");
      out.write("                    }\n");
      out.write("                }));\n");
      out.write("            } else {\n");
      out.write("                swal(\"操作已取消!\", \"操作已取消.\", \"success\");\n");
      out.write("            }\n");
      out.write("        });\n");
      out.write("    }\n");
      out.write("}\n");
      out.write("\n");
      out.write("function enableWaste(t){\n");
      out.write("    var $checkItem = $(\"input[name='id']:checked\");\n");
      out.write("    if ($checkItem.length == 0) {\n");
      out.write("        $.notify(\"请选择一条或多条进行启用\", {\"status\": \"info\"});\n");
      out.write("    } else {\n");
      out.write("        swal({  title: \"确认启用\",   \n");
      out.write("            text: \"请确认是否需要启用\",   \n");
      out.write("            type: \"warning\",   \n");
      out.write("            showCancelButton: true,   \n");
      out.write("            confirmButtonColor: \"#DD6B55\",   \n");
      out.write("            confirmButtonText: \"启用\",   \n");
      out.write("            cancelButtonText: \"取消\",   \n");
      out.write("            closeOnConfirm: false,   \n");
      out.write("            closeOnCancel: false \n");
      out.write("        }, function(isConfirm){\n");
      out.write("            if(isConfirm){\n");
      out.write("                $.page.ajax($.page.getAjaxSettings({\n");
      out.write("                    async: false,\n");
      out.write("                    type: \"POST\",\n");
      out.write("                    dataType: \"json\",\n");
      out.write("                    url: $.page.webSiteRootUrl + \"/Waste/enableWaste.htm\",\n");
      out.write("                    data: $checkItem.serialize(),\n");
      out.write("                    success: function (result, textStatus, jqXHR) {\n");
      out.write("                        swal(\"启用成功!\", \"启用成功!\", \"success\");\n");
      out.write("                        location.reload();\n");
      out.write("                    }\n");
      out.write("                }));\n");
      out.write("            } else {\n");
      out.write("                swal(\"操作已取消!\", \"操作已取消.\", \"success\");\n");
      out.write("            }\n");
      out.write("        });\n");
      out.write("    }\n");
      out.write("}\n");
      out.write("\n");
      out.write("function disableWaste(t){\n");
      out.write("    var $checkItem = $(\"input[name='id']:checked\");\n");
      out.write("    if ($checkItem.length == 0) {\n");
      out.write("        $.notify(\"请选择一条或多条进行停用\", {\"status\": \"info\"});\n");
      out.write("    } else {\n");
      out.write("        swal({  title: \"确认停用\",   \n");
      out.write("            text: \"请确认是否需要停用\",   \n");
      out.write("            type: \"warning\",   \n");
      out.write("            showCancelButton: true,   \n");
      out.write("            confirmButtonColor: \"#DD6B55\",   \n");
      out.write("            confirmButtonText: \"停用\",   \n");
      out.write("            cancelButtonText: \"取消\",   \n");
      out.write("            closeOnConfirm: false,   \n");
      out.write("            closeOnCancel: false \n");
      out.write("        }, function(isConfirm){\n");
      out.write("            if(isConfirm){\n");
      out.write("                $.page.ajax($.page.getAjaxSettings({\n");
      out.write("                    async: false,\n");
      out.write("                    type: \"POST\",\n");
      out.write("                    dataType: \"json\",\n");
      out.write("                    url: $.page.webSiteRootUrl + \"/Waste/disableWaste.htm\",\n");
      out.write("                    data: $checkItem.serialize(),\n");
      out.write("                    success: function (result, textStatus, jqXHR) {\n");
      out.write("                        swal(\"停用成功!\", \"停用成功!\", \"success\");\n");
      out.write("                        location.reload();\n");
      out.write("                    }\n");
      out.write("                }));\n");
      out.write("            } else {\n");
      out.write("                swal(\"操作已取消!\", \"操作已取消.\", \"success\");\n");
      out.write("            }\n");
      out.write("        });\n");
      out.write("    }\n");
      out.write("}\n");
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
