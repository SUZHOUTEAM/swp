/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-08-24 04:36:35 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.Notice;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.mlsc.yifeiwang.sms.common.NoticeCategory;
import com.mlsc.common.constant.BaseConstant;

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

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");

      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/webCommon/user_top.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("title", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("我的消息", request.getCharacterEncoding()), out, true);
      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/webCommon/wasteCircleMenu.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("menuId", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("#myMessage", request.getCharacterEncoding()), out, true);
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(appPath );
      out.write("/css/Notice/notice.css?1\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("    .input-group.input-append.date > .input-group-addon, .input-group.input-append.date > input {\r\n");
      out.write("        background-color: white;\r\n");
      out.write("    }\r\n");
      out.write("</style>\r\n");
      out.write("<!-- Main section-->\r\n");
      out.write("<section>\r\n");
      out.write("    <div class=\"el-breadcrumb\">\r\n");
      out.write("        <span class=\"el-breadcrumb__item\">\r\n");
      out.write("            <span class=\"el-breadcrumb__item__inner\">我的消息</span>\r\n");
      out.write("            <span class=\"el-breadcrumb__separator\">/</span>\r\n");
      out.write("        </span>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"panel panel-body\">\r\n");
      out.write("        <div class=\"searchtoolNotice\">\r\n");
      out.write("            <form class=\"form-inline seekform\">\r\n");
      out.write("\r\n");
      out.write("                <div class=\"form-group\">\r\n");
      out.write("                    <div class=\"input-group input-append date form_datetime margin-right\"\r\n");
      out.write("                         id=\"startDate\">\r\n");
      out.write("                        <input type=\"text\" id=\"start_date\" placeholder=\"开始时间\" class=\"form-control\"\r\n");
      out.write("                               readonly=\"readonly\"/>\r\n");
      out.write("                        <span class=\"input-group-addon\"> <span class=\"fa fa-calendar\"></span></span>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <div class=\"input-group input-append date form_datetime margin-right\"\r\n");
      out.write("                         id=\"endDate\">\r\n");
      out.write("                        <input type=\"text\" id=\"end_date\" placeholder=\"结束时间\" class=\"form-control\"\r\n");
      out.write("                               readonly=\"readonly\"/>\r\n");
      out.write("                        <span class=\"input-group-addon\"><span class=\"fa fa-calendar\"></span></span>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <select id=\"noticeCategory\" name=\"noticeCategory\"\r\n");
      out.write("                            class=\"selectpicker form-control\">\r\n");
      out.write("                        <option value=\"\">全部类型</option>\r\n");
      out.write("                        <option value=\"PURCHASE_STATUS\">购买动态</option>\r\n");
      out.write("                        <option value=\"NEW_ORDER\">新的订单</option>\r\n");
      out.write("                        <option value=\"SYS_TYPE\">系统消息</option>\r\n");
      out.write("                        <option value=\"NEW_RESOURCELIST\">新的资源单</option>\r\n");
      out.write("                        <option value=\"ACTIVITY_STATUS\">活动动态</option>\r\n");
      out.write("                    </select>\r\n");
      out.write("                    <label class=\"show-unread\"><input id=\"unread\" type=\"checkbox\" value=\"0\"/>只显示未读信息</label>\r\n");
      out.write("                </div>\r\n");
      out.write("                <button type=\"button\" class=\"btn btn-primary \" onclick=\"searchNoticeData(true)\">筛选\r\n");
      out.write("                </button>\r\n");
      out.write("            </form>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"table-grid table-grid-desktop\">\r\n");
      out.write("            <div class=\"col todo-item-list\">\r\n");
      out.write("                <div id=\"noticelist\" role=\"tablist\" aria-multiselectable=\"true\" class=\"panel-group\">\r\n");
      out.write("                    ");
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"clearfix mb\">\r\n");
      out.write("                    <div class=\"btn-group pull-right\">\r\n");
      out.write("                        <button id=\"pre\" class=\"btn btn-sm btn-default\" onclick=\"getPrePage()\">\r\n");
      out.write("                            <span>上一页</span></button>\r\n");
      out.write("                        <button id=\"next\" class=\"btn btn-sm btn-default\" onclick=\"getNextPage()\">\r\n");
      out.write("                            <span>下一页</span></button>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</section>\r\n");
      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/common/bottom.jsp", out, true);
      out.write("\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("        src=\"");
      out.print(appPath );
      out.write("/thirdparty/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("        src=\"");
      out.print(appPath );
      out.write("/thirdparty/eonasdan-bootstrap-datetimepicker/build/locales/bootstrap-datetimepicker.zh-CN.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("  var pageSize = 8;\r\n");
      out.write("  var pageIndex = 1;\r\n");
      out.write("  $(document).ready(function () {\r\n");
      out.write("    var rs = window.globalInit();\r\n");
      out.write("    rs.done(function () {\r\n");
      out.write("      // 初始化加载数据\r\n");
      out.write("      $(\"#indexTab\").val(\"1\");\r\n");
      out.write("      searchNoticeData(true);\r\n");
      out.write("      //datetimepicker控件初始化\r\n");
      out.write("      $(\".form_datetime\").datetimepicker({\r\n");
      out.write("        format: 'yyyy-mm-dd',\r\n");
      out.write("        language: 'zh-CN',\r\n");
      out.write("        weekStart: 1,\r\n");
      out.write("        todayBtn: \"linked\",\r\n");
      out.write("        todayHighlight: true,\r\n");
      out.write("        autoclose: true,\r\n");
      out.write("        todayHighlight: 1,\r\n");
      out.write("        clearBtn:true,\r\n");
      out.write("        startView: 2,\r\n");
      out.write("        minView: 2,\r\n");
      out.write("        forceParse: false,\r\n");
      out.write("        pickerPosition: \"bottom-left\"\r\n");
      out.write("      }).on('changeDate', function (ev) {\r\n");
      out.write("        reInitDatePicker($(this));\r\n");
      out.write("      });\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("  });\r\n");
      out.write("\r\n");
      out.write("  //给开始结束日期控件重新初始化\r\n");
      out.write("  function reInitDatePicker($that) {\r\n");
      out.write("    var id = $that.attr(\"id\");\r\n");
      out.write("    if (id == \"startDate\") {\r\n");
      out.write("      $('#endDate').datetimepicker('setStartDate', $that.find(\"input\").val());\r\n");
      out.write("    } else if (id == \"endDate\") {\r\n");
      out.write("      $('#startDate').datetimepicker('setEndDate', $that.find(\"input\").val());\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  function searchNoticeData(reset) {\r\n");
      out.write("    if (reset) {\r\n");
      out.write("      pageSize = 8;\r\n");
      out.write("      pageIndex = 1;\r\n");
      out.write("    }\r\n");
      out.write("    var startDate = $(\"#start_date\").val();\r\n");
      out.write("    if (startDate != \"\") {\r\n");
      out.write("      startDate = startDate + \" 00:00:00\";\r\n");
      out.write("    }\r\n");
      out.write("    var endDate = $(\"#end_date\").val();\r\n");
      out.write("    if (endDate != \"\") {\r\n");
      out.write("      endDate = endDate + \" 23:59:59\";\r\n");
      out.write("    }\r\n");
      out.write("    var noticeCategory = $(\"#noticeCategory\").val();\r\n");
      out.write("    var hasRead = \"\";\r\n");
      out.write("    if ($(\"#unread\").is(':checked')) {\r\n");
      out.write("      hasRead = $(\"#unread\").val();\r\n");
      out.write("    }\r\n");
      out.write("    $.ajax({\r\n");
      out.write("      url: \"");
      out.print(appPath );
      out.write("/sysNotice/pageNotice.htm?ticketId=");
      out.print(ticketId );
      out.write("&pageIndex=\" + pageIndex,\r\n");
      out.write("      data: {\r\n");
      out.write("        'startDate': startDate,\r\n");
      out.write("        'endDate': endDate,\r\n");
      out.write("        'noticeCategory': noticeCategory,\r\n");
      out.write("        'hasRead': hasRead,\r\n");
      out.write("        'pageSize': pageSize,\r\n");
      out.write("        'pageIndex': pageIndex\r\n");
      out.write("      },\r\n");
      out.write("      type: \"POST\",\r\n");
      out.write("      dataType: 'json',\r\n");
      out.write("      success: function (result) {\r\n");
      out.write("        if (result.status === 1) {\r\n");
      out.write("          var paging = result.data.paging;\r\n");
      out.write("          var dataList = result.data.datas;\r\n");
      out.write("          var $noticelist = $(\"#noticelist\");\r\n");
      out.write("          pageIndex = paging.pageIndex;\r\n");
      out.write("          // 当前页保存\r\n");
      out.write("          $noticelist.find(\".noticeItem\").remove();\r\n");
      out.write("          if (dataList.length > 0) {\r\n");
      out.write("            for (var index in dataList) {\r\n");
      out.write("              $noticelist.append(creatNoticeDiv(dataList[index]));\r\n");
      out.write("              if (dataList[index].hasRead === \"");
      out.print(BaseConstant.YES);
      out.write("\") {\r\n");
      out.write("                $(\"#label\" + dataList[index].id + \"\").removeClass('label-default').addClass(\r\n");
      out.write("                    'label-success');\r\n");
      out.write("                $(\"#label\" + dataList[index].id + \"\").html(\"已读\");\r\n");
      out.write("              }\r\n");
      out.write("            }\r\n");
      out.write("          }\r\n");
      out.write("          // 上一页，下一页按钮控制\r\n");
      out.write("          initBtnDisabled(paging);\r\n");
      out.write("        } else {\r\n");
      out.write("          var options = {\"status\": \"info\"};\r\n");
      out.write("          $.notify(result.infoList[0], options);\r\n");
      out.write("        }\r\n");
      out.write("      }\r\n");
      out.write("    });\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  // 创建一个通知信息显示项\r\n");
      out.write("  function creatNoticeDiv(noticeVo) {\r\n");
      out.write("    var $itemDiv = $(\"<div class='todo-item panel panel-default todo-complete noticeItem'></div>\");\r\n");
      out.write("    var $panelheadingDiv ;\r\n");
      out.write("      if (noticeVo.directUrl!=null) {\r\n");
      out.write("          $panelheadingDiv = $(\r\n");
      out.write("              \"<div role='tab'  class='panel-heading clickable clearfix' style='display: block;' \"\r\n");
      out.write("              +\" onclick='showDetail(\\\"\" + noticeVo.directUrl + \"\\\",\\\"\" + noticeVo.id\r\n");
      out.write("              + \"\\\" )' > </div>\");\r\n");
      out.write("      }else{\r\n");
      out.write("          $panelheadingDiv = $(\r\n");
      out.write("              \"<div role='tab'  class='panel-heading clearfix' style='display: block;'>  </div>\");\r\n");
      out.write("      }\r\n");
      out.write("\r\n");
      out.write("    $panelheadingDiv.append(\r\n");
      out.write("        \"<div class='pull-left' ><img alt='' src='");
      out.print(appPath );
      out.write("/app/img/\" + getUrlByNoticeType(\r\n");
      out.write("            noticeVo.noticeCategory) + \"'></div>\");\r\n");
      out.write("    var inputHtml = \"<input type='hidden'  value='\" + noticeVo.relId + \"'/>\";\r\n");
      out.write("    var senderEnterHtml = \"<input type='hidden'  value='\" + noticeVo.senderEnterpriseId + \"'/>\";\r\n");
      out.write("    var noticeidHtml = \"<input type='hidden'  value='\" + noticeVo.id + \"'/>\";\r\n");
      out.write("    $panelheadingDiv.append(inputHtml);\r\n");
      out.write("    $panelheadingDiv.append(senderEnterHtml);\r\n");
      out.write("    $panelheadingDiv.append(noticeidHtml);\r\n");
      out.write("    $panelheadingDiv.append(\r\n");
      out.write("        \"<div class='pull-left' style='width: 120px;padding-left: 20px' > <span class='summary notice-font14'>[\"\r\n");
      out.write("        + noticeVo.noticeCategoryLabel + \"]</span></div>\");\r\n");
      out.write("    $panelheadingDiv.append(\r\n");
      out.write("        \"<div class='pull-left' style='padding-left: 100px;'><span class='notice-font15' >\"\r\n");
      out.write("        + noticeVo.noticeContent + \"</span></div>\");\r\n");
      out.write("    $panelheadingDiv.append(\r\n");
      out.write("        \"<div class='pull-right'>\" + noticeVo.createTime\r\n");
      out.write("        + \"</span></div>\");\r\n");
      out.write("    $panelheadingDiv.append(\r\n");
      out.write("        \"<div class='pull-right test' style='padding-right: 45px;' ><label id='label\" + noticeVo.id\r\n");
      out.write("        + \"' onclick='signRead(\\\"\" + noticeVo.id\r\n");
      out.write("        + \"\\\")' class='signread label label-default small'>标志为已读</label></div>\");\r\n");
      out.write("\r\n");
      out.write("    var $collapseDiv = $(\"<div class='panel-collapse collapse detailDiv'></div>\");\r\n");
      out.write("    $collapseDiv.append(\"<div class='panel-body'></div>\");\r\n");
      out.write("    $itemDiv.append($panelheadingDiv.prop(\"outerHTML\"));\r\n");
      out.write("    $itemDiv.append($collapseDiv.prop(\"outerHTML\"));\r\n");
      out.write("    return $itemDiv;\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  function getUrlByNoticeType(type) {\r\n");
      out.write("    var url = \"newresourcelist.png\";\r\n");
      out.write("    switch (type) {\r\n");
      out.write("      case \"");
      out.print(NoticeCategory.NEW_ORDER.getCode());
      out.write("\":\r\n");
      out.write("        url = \"neworder.png\";\r\n");
      out.write("        break;\r\n");
      out.write("      case \"");
      out.print(NoticeCategory.PURCHASE_STATUS.getCode());
      out.write("\":\r\n");
      out.write("        url = \"purchasestatus.png\";\r\n");
      out.write("        break;\r\n");
      out.write("      case \"");
      out.print(NoticeCategory.SYS_TYPE.getCode());
      out.write("\":\r\n");
      out.write("        url = \"systemmessage.png\";\r\n");
      out.write("        break;\r\n");
      out.write("      default:\r\n");
      out.write("        break;\r\n");
      out.write("    }\r\n");
      out.write("    return url;\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  function signRead(noticeId) {\r\n");
      out.write("    if ($(\"#label\" + noticeId).hasClass('label-default')) {\r\n");
      out.write("      var ticketId = \"");
      out.print(ticketId );
      out.write("\";\r\n");
      out.write("      $.ajax({\r\n");
      out.write("        url: \"");
      out.print(appPath );
      out.write("/sysNotice/readNotice.htm\",\r\n");
      out.write("        data: {'noticeId': noticeId, 'ticketId': ticketId},\r\n");
      out.write("        type: \"POST\",\r\n");
      out.write("        dataType: 'json',\r\n");
      out.write("        async: false,\r\n");
      out.write("        success: function (result) {\r\n");
      out.write("          if (result.status === 1) {\r\n");
      out.write("            $(\"#label\" + noticeId).removeClass('label-default').addClass('label-success');\r\n");
      out.write("            $(\"#em\" + noticeId).removeClass(\r\n");
      out.write("                'icon-envelope text-muted icon-envelope-color').addClass(\r\n");
      out.write("                'icon-envelope-open text-muted icon-envelope-open-color');\r\n");
      out.write("            $(\"#label\" + noticeId).html(\"已读\");\r\n");
      out.write("            $.notify(\"标志为已读成功\", {status: 'success'});\r\n");
      out.write("          } else {\r\n");
      out.write("            $.notify(result.infoList[0] ,{status: 'danger'});\r\n");
      out.write("          }\r\n");
      out.write("        },\r\n");
      out.write("        error: function (er) {\r\n");
      out.write("          $.notify(\"标志为已读出错\", {status: 'danger'});\r\n");
      out.write("        }\r\n");
      out.write("      });\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  //点击查看进入不同页面\r\n");
      out.write("  function showDetail(directUrl, noticeId) {\r\n");
      out.write("    if ($(\"#label\" + noticeId).hasClass('label-default')) {\r\n");
      out.write("      var ticketId = \"");
      out.print(ticketId );
      out.write("\";\r\n");
      out.write("      $.ajax({\r\n");
      out.write("        url: \"");
      out.print(appPath );
      out.write("/sysNotice/readNotice.htm\",\r\n");
      out.write("        data: {'noticeId': noticeId, 'ticketId': ticketId},\r\n");
      out.write("        type: \"POST\",\r\n");
      out.write("        dataType: 'json',\r\n");
      out.write("        async: false,\r\n");
      out.write("        success: function (result) {\r\n");
      out.write("          if (result.status === 1) {\r\n");
      out.write("            $(\"#label\" + noticeId).removeClass('label-default').addClass('label-success');\r\n");
      out.write("            $(\"#em\" + noticeId).removeClass(\r\n");
      out.write("                'icon-envelope text-muted icon-envelope-color').addClass(\r\n");
      out.write("                'icon-envelope-open text-muted icon-envelope-open-color');\r\n");
      out.write("            $(\"#label\" + noticeId).html(\"已读\");\r\n");
      out.write("          } else {\r\n");
      out.write("            $.notify(result.infoList[0] ,{status: 'danger'});\r\n");
      out.write("          }\r\n");
      out.write("        },\r\n");
      out.write("        error: function (er) {\r\n");
      out.write("          $.notify(\"标志为已读出错\", {status: 'danger'});\r\n");
      out.write("        }\r\n");
      out.write("      });\r\n");
      out.write("    }\r\n");
      out.write("    if(directUrl){\r\n");
      out.write("      window.location.href = \"");
      out.print(appPath );
      out.write("\"+directUrl+\"?ticketId=");
      out.print(ticketId );
      out.write("\";\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  //上一页，下一页初始化控制\r\n");
      out.write("  function initBtnDisabled(paging) {\r\n");
      out.write("    var pageIndex = parseInt(paging.pageIndex);\r\n");
      out.write("    var totalPage = parseInt(paging.totalPage);\r\n");
      out.write("    if (totalPage <= 1) {\r\n");
      out.write("      $(\"#pre\").attr(\"disabled\", \"disabled\");\r\n");
      out.write("      $(\"#next\").attr(\"disabled\", \"disabled\");\r\n");
      out.write("    } else {\r\n");
      out.write("      if (pageIndex == totalPage) {\r\n");
      out.write("        $(\"#pre\").removeAttr(\"disabled\");\r\n");
      out.write("        $(\"#next\").attr(\"disabled\", \"disabled\");\r\n");
      out.write("      } else if (pageIndex == 1) {\r\n");
      out.write("        $(\"#pre\").attr(\"disabled\", \"disabled\");\r\n");
      out.write("        $(\"#next\").removeAttr(\"disabled\");\r\n");
      out.write("      } else {\r\n");
      out.write("        $(\"#pre\").removeAttr(\"disabled\");\r\n");
      out.write("        $(\"#next\").removeAttr(\"disabled\");\r\n");
      out.write("      }\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  ");
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("  ");
      out.write("\r\n");
      out.write("\r\n");
      out.write("  //获取上一页数据，下一页初始化控制\r\n");
      out.write("  function getPrePage() {\r\n");
      out.write("//    var pageIndex = parseInt($(\"#noticelist\").find(\":hidden\").val());\r\n");
      out.write("    pageIndex--;\r\n");
      out.write("    searchNoticeData();\r\n");
      out.write("  }\r\n");
      out.write("  // 获取下一页数据，\r\n");
      out.write("  function getNextPage() {\r\n");
      out.write("//    var pageIndex = parseInt($(\"#noticelist\").find(\":hidden\").val());\r\n");
      out.write("    pageIndex++;\r\n");
      out.write("    searchNoticeData();\r\n");
      out.write("  }\r\n");
      out.write("</script>\r\n");
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
