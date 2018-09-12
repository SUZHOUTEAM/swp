<%@ page import="com.mlsc.yifeiwang.sms.common.NoticeCategory" %>
<%@ page import="com.mlsc.common.constant.BaseConstant" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="我的消息"/>
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myMessage"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath %>/css/Notice/notice.css?1">

<%------- 结束导入左侧信息 -------%>

<style>
    .input-group.input-append.date > .input-group-addon, .input-group.input-append.date > input {
        background-color: white;
    }
</style>
<!-- Main section-->
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的消息</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div class="panel panel-body">
        <div class="searchtoolNotice">
            <form class="form-inline seekform">

                <div class="form-group">
                    <div class="input-group input-append date form_datetime margin-right"
                         id="startDate">
                        <input type="text" id="start_date" placeholder="开始时间" class="form-control"
                               readonly="readonly"/>
                        <span class="input-group-addon"> <span class="fa fa-calendar"></span></span>
                    </div>
                    <div class="input-group input-append date form_datetime margin-right"
                         id="endDate">
                        <input type="text" id="end_date" placeholder="结束时间" class="form-control"
                               readonly="readonly"/>
                        <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                    </div>
                    <select id="noticeCategory" name="noticeCategory"
                            class="selectpicker form-control">
                        <option value="">全部类型</option>
                        <option value="PURCHASE_STATUS">购买动态</option>
                        <option value="NEW_ORDER">新的订单</option>
                        <option value="SYS_TYPE">系统消息</option>
                        <option value="NEW_RESOURCELIST">新的资源单</option>
                        <option value="ACTIVITY_STATUS">活动动态</option>
                    </select>
                    <label class="show-unread"><input id="unread" type="checkbox" value="0"/>只显示未读信息</label>
                </div>
                <button type="button" class="btn btn-primary " onclick="searchNoticeData(true)">筛选
                </button>
            </form>
        </div>
        <div class="table-grid table-grid-desktop">
            <div class="col todo-item-list">
                <div id="noticelist" role="tablist" aria-multiselectable="true" class="panel-group">
                    <%--<input type="hidden"><!-- 当前页码保存 -->--%>
                </div>
                <div class="clearfix mb">
                    <div class="btn-group pull-right">
                        <button id="pre" class="btn btn-sm btn-default" onclick="getPrePage()">
                            <span>上一页</span></button>
                        <button id="next" class="btn btn-sm btn-default" onclick="getNextPage()">
                            <span>下一页</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script type="text/javascript"
        src="<%=appPath %>/thirdparty/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"
        src="<%=appPath %>/thirdparty/eonasdan-bootstrap-datetimepicker/build/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
  var pageSize = 8;
  var pageIndex = 1;
  $(document).ready(function () {
    var rs = window.globalInit();
    rs.done(function () {
      // 初始化加载数据
      $("#indexTab").val("1");
      searchNoticeData(true);
      //datetimepicker控件初始化
      $(".form_datetime").datetimepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: "linked",
        todayHighlight: true,
        autoclose: true,
        todayHighlight: 1,
        clearBtn:true,
        startView: 2,
        minView: 2,
        forceParse: false,
        pickerPosition: "bottom-left"
      }).on('changeDate', function (ev) {
        reInitDatePicker($(this));
      });
    });

  });

  //给开始结束日期控件重新初始化
  function reInitDatePicker($that) {
    var id = $that.attr("id");
    if (id == "startDate") {
      $('#endDate').datetimepicker('setStartDate', $that.find("input").val());
    } else if (id == "endDate") {
      $('#startDate').datetimepicker('setEndDate', $that.find("input").val());
    }
  }

  function searchNoticeData(reset) {
    if (reset) {
      pageSize = 8;
      pageIndex = 1;
    }
    var startDate = $("#start_date").val();
    if (startDate != "") {
      startDate = startDate + " 00:00:00";
    }
    var endDate = $("#end_date").val();
    if (endDate != "") {
      endDate = endDate + " 23:59:59";
    }
    var noticeCategory = $("#noticeCategory").val();
    var hasRead = "";
    if ($("#unread").is(':checked')) {
      hasRead = $("#unread").val();
    }
    $.ajax({
      url: "<%=appPath %>/sysNotice/pageNotice.htm?ticketId=<%=ticketId %>&pageIndex=" + pageIndex,
      data: {
        'startDate': startDate,
        'endDate': endDate,
        'noticeCategory': noticeCategory,
        'hasRead': hasRead,
        'pageSize': pageSize,
        'pageIndex': pageIndex
      },
      type: "POST",
      dataType: 'json',
      success: function (result) {
        if (result.status === 1) {
          var paging = result.data.paging;
          var dataList = result.data.datas;
          var $noticelist = $("#noticelist");
          pageIndex = paging.pageIndex;
          // 当前页保存
          $noticelist.find(".noticeItem").remove();
          if (dataList.length > 0) {
            for (var index in dataList) {
              $noticelist.append(creatNoticeDiv(dataList[index]));
              if (dataList[index].hasRead === "<%=BaseConstant.YES%>") {
                $("#label" + dataList[index].id + "").removeClass('label-default').addClass(
                    'label-success');
                $("#label" + dataList[index].id + "").html("已读");
              }
            }
          }
          // 上一页，下一页按钮控制
          initBtnDisabled(paging);
        } else {
          var options = {"status": "info"};
          $.notify(result.infoList[0], options);
        }
      }
    });
  }

  // 创建一个通知信息显示项
  function creatNoticeDiv(noticeVo) {
    var $itemDiv = $("<div class='todo-item panel panel-default todo-complete noticeItem'></div>");
    var $panelheadingDiv ;
      if (noticeVo.directUrl!=null) {
          $panelheadingDiv = $(
              "<div role='tab'  class='panel-heading clickable clearfix' style='display: block;' "
              +" onclick='showDetail(\"" + noticeVo.directUrl + "\",\"" + noticeVo.id
              + "\" )' > </div>");
      }else{
          $panelheadingDiv = $(
              "<div role='tab'  class='panel-heading clearfix' style='display: block;'>  </div>");
      }

    $panelheadingDiv.append(
        "<div class='pull-left' ><img alt='' src='<%=appPath %>/app/img/" + getUrlByNoticeType(
            noticeVo.noticeCategory) + "'></div>");
    var inputHtml = "<input type='hidden'  value='" + noticeVo.relId + "'/>";
    var senderEnterHtml = "<input type='hidden'  value='" + noticeVo.senderEnterpriseId + "'/>";
    var noticeidHtml = "<input type='hidden'  value='" + noticeVo.id + "'/>";
    $panelheadingDiv.append(inputHtml);
    $panelheadingDiv.append(senderEnterHtml);
    $panelheadingDiv.append(noticeidHtml);
    $panelheadingDiv.append(
        "<div class='pull-left' style='width: 120px;padding-left: 20px' > <span class='summary notice-font14'>["
        + noticeVo.noticeCategoryLabel + "]</span></div>");
    $panelheadingDiv.append(
        "<div class='pull-left' style='padding-left: 100px;'><span class='notice-font15' >"
        + noticeVo.noticeContent + "</span></div>");
    $panelheadingDiv.append(
        "<div class='pull-right'>" + noticeVo.createTime
        + "</span></div>");
    $panelheadingDiv.append(
        "<div class='pull-right test' style='padding-right: 45px;' ><label id='label" + noticeVo.id
        + "' onclick='signRead(\"" + noticeVo.id
        + "\")' class='signread label label-default small'>标志为已读</label></div>");

    var $collapseDiv = $("<div class='panel-collapse collapse detailDiv'></div>");
    $collapseDiv.append("<div class='panel-body'></div>");
    $itemDiv.append($panelheadingDiv.prop("outerHTML"));
    $itemDiv.append($collapseDiv.prop("outerHTML"));
    return $itemDiv;
  }

  function getUrlByNoticeType(type) {
    var url = "newresourcelist.png";
    switch (type) {
      case "<%=NoticeCategory.NEW_ORDER.getCode()%>":
        url = "neworder.png";
        break;
      case "<%=NoticeCategory.PURCHASE_STATUS.getCode()%>":
        url = "purchasestatus.png";
        break;
      case "<%=NoticeCategory.SYS_TYPE.getCode()%>":
        url = "systemmessage.png";
        break;
      default:
        break;
    }
    return url;
  }

  function signRead(noticeId) {
      var event=event||window.event;
      event.preventDefault();
      event.stopPropagation();
    if ($("#label" + noticeId).hasClass('label-default')) {
      var ticketId = "<%=ticketId %>";
      $.ajax({
        url: "<%=appPath %>/sysNotice/readNotice.htm",
        data: {'noticeId': noticeId, 'ticketId': ticketId},
        type: "POST",
        dataType: 'json',
        async: false,
        success: function (result) {
          if (result.status === 1) {
            $("#label" + noticeId).removeClass('label-default').addClass('label-success');
            $("#em" + noticeId).removeClass(
                'icon-envelope text-muted icon-envelope-color').addClass(
                'icon-envelope-open text-muted icon-envelope-open-color');
            $("#label" + noticeId).html("已读");
            $.notify("标志为已读成功", {status: 'success'});
          } else {
            $.notify(result.infoList[0] ,{status: 'danger'});
          }
        },
        error: function (er) {
          $.notify("标志为已读出错", {status: 'danger'});
        }
      });
    }
  }

  //点击查看进入不同页面
  function showDetail(directUrl, noticeId) {
    if ($("#label" + noticeId).hasClass('label-default')) {
      var ticketId = "<%=ticketId %>";
      $.ajax({
        url: "<%=appPath %>/sysNotice/readNotice.htm",
        data: {'noticeId': noticeId, 'ticketId': ticketId},
        type: "POST",
        dataType: 'json',
        async: false,
        success: function (result) {
          if (result.status === 1) {
            $("#label" + noticeId).removeClass('label-default').addClass('label-success');
            $("#em" + noticeId).removeClass(
                'icon-envelope text-muted icon-envelope-color').addClass(
                'icon-envelope-open text-muted icon-envelope-open-color');
            $("#label" + noticeId).html("已读");
          } else {
            $.notify(result.infoList[0] ,{status: 'danger'});
          }
        },
        error: function (er) {
          $.notify("标志为已读出错", {status: 'danger'});
        }
      });
    }
    if(directUrl){
      window.location.href = "<%=appPath %>"+directUrl+"?ticketId=<%=ticketId %>";
    }
  }

  //上一页，下一页初始化控制
  function initBtnDisabled(paging) {
    var pageIndex = parseInt(paging.pageIndex);
    var totalPage = parseInt(paging.totalPage);
    if (totalPage <= 1) {
      $("#pre").attr("disabled", "disabled");
      $("#next").attr("disabled", "disabled");
    } else {
      if (pageIndex == totalPage) {
        $("#pre").removeAttr("disabled");
        $("#next").attr("disabled", "disabled");
      } else if (pageIndex == 1) {
        $("#pre").attr("disabled", "disabled");
        $("#next").removeAttr("disabled");
      } else {
        $("#pre").removeAttr("disabled");
        $("#next").removeAttr("disabled");
      }
    }
  }

  <%--function goback() {--%>
    <%--location = "<%=appPath %>/wastecircle/init.htm?ticketId=${ticketId}";--%>
  <%--}--%>

  //获取上一页数据，下一页初始化控制
  function getPrePage() {
//    var pageIndex = parseInt($("#noticelist").find(":hidden").val());
    pageIndex--;
    searchNoticeData();
  }
  // 获取下一页数据，
  function getNextPage() {
//    var pageIndex = parseInt($("#noticelist").find(":hidden").val());
    pageIndex++;
    searchNoticeData();
  }
</script>
