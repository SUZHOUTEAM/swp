<%@page import="com.mlsc.waste.utils.Constant" %>
<%@page import="com.mlsc.waste.utils.CodeTypeConstant" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="企业信息详情"/>
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myEnterprise"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath %>/css/enterprise/enterprise_common.css?1">
<script src="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.css">
<!-- Main section-->
<style>
    table th, table td {
        text-align: center;
    }
    .adminClickTag{
        margin-left: 10px;
    }
</style>
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的企业</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">企业信息</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div class="">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="media-box col-md-12 colcss">
                    <div class="pull-left col-md-1 colcss">
                        <img src="<%=appPath %>/app/img/business_icon/default_enterprise.png" alt=""
                             id="enterpriseImg"
                             class="media-box-object img-responsive img-rounded thumb65">
                    </div>
                    <div class="media-box-body box1">
                        <input type="hidden" value="${sysEnterpriseBase.entId}" id="enterpriseId">
                        <div class="col-md-9 colcss">
                            <strong class="media-box-heading text-primary">
                                <span class="">${sysEnterpriseBase.entName}</span>
                                <c:if test="${!empty enterpriseTypes }">
                                    <c:forEach var="entTypeIt" items="${enterpriseTypes}"
                                               varStatus="status" begin="0" step="1">
                                        <span class="enterprise-type-badge marginSpanEnterpriseType ${entTypeIt.code.toLowerCase().concat("-badge")}">${entTypeIt.value}</span>
                                    </c:forEach>
                                </c:if>
                            </strong>
                            <p class="mb-sm">
                                <span>企业代码：${sysEnterpriseBase.entCode}</span>
                            </p>
                            <c:if test="${enterpriseTypes[0].code.equals(\"DIS_FACILITATOR\")}">
                            <p class="mb-sm">
                                <span>服务区域：${sysEnterpriseBase.responsibleArea}</span>
                            </p>
                            </c:if>
                            <p class="mb-sm">
                                <span>状态：${sysEnterpriseBase.enterpriseStatusLabel}</span>
                            </p>
                            <p class="mb-sm">
                                <span>地址：${sysEnterpriseBase.entAddress}</span>
                            </p>
                            <p class="mb-sm">
                                <span>绑定时间：${sysEnterpriseBase.createTime}</span>
                            </p>
                            <p id="modifyEnter" class="mb-sm hidden">
                        		<a href="<%=appPath %>/enterprise/modifyEnterprise.htm?ticketId=<%=ticketId %>">修改企业信息</a>
                            </p>
                        </div>
                            <div class="col-md-3 colcss right-bottom">
                                <c:if test="${!dispalyAddBtn}">
                                    <button id="unbindEnterprise" class="pull-right  btn btn-default"
                                            onclick="unbindEnterprise(this)">解绑企业
                                    </button>
                                </c:if>
                            </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="msgView">
        </div>
        <c:if test="${dispalyAddBtn}">
            <div style="text-align: center">
                <button id="btnjoinEnterprise" class="btn btn-primary" style="width: 178px;height: 40px;font-size: 14px;margin-top: 16px;"
                        onclick="joinEnterprise(this)">加入企业
                </button>
            </div>
        </c:if>
        <div id="bindEnterprise">
        </div>
        <div class="panel panel-default hidden " id="mainPanel">
            <div class="panel-body">
                <ul id="myTab" class="nav nav-tabs">
                    <li class="active">
                       <!--  <a href="#contacts" data-toggle="tab">联系人</a> -->
                       <a href="javascript:" data-toggle="tab" id="contactTab" onclick="enterUserList()">联系人</a>
                    </li>
                    <c:if test="${sysEnterpriseBase.enterpriseStatus eq 'PASS'}">
                        <li class="hidden wasteInfo">
                            <a href="javascript:" onclick="wasteinfo(this)">产废信息</a>
                        </li>

                        <li class="hidden licenceItemInfo">
                            <a href="javascript:" onclick="licenceItem(this)">许可内容</a>
                        </li>
                        <c:if test="${userRole eq 'ADMIN' }">
                            <li>
                                <a href="#manageInfo" data-toggle="tab">管理员工</a>
                            </li>
                        </c:if>
                    </c:if>
                </ul>
                <div id="myTabContent" class="tab-content col-md-12">
                    <div class="tab-pane fade in active" id="contacts">
                        <%-- <c:if test="${!empty prcSysUserList}">
                            <table id="contactsTable"
                                   class="table-bordered table-condensed col-md-12"
                                   style="width: 100%;">
                                <thead>
                                <tr>
                                    <th>联系人</th>
                                    <th>电话</th>
                                    <th>邮箱</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="it" items="${prcSysUserList}" varStatus="status"
                                           begin="0" step="1">
                                    <tr userId="${it.userId}">
                                        <td>${it.chineseName}</td>
                                        <td>${it.phoneNum}</td>
                                        <td>${it.emailAddress}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                        <c:if test="${empty prcSysUserList}">
                            <span>暂时还没有联系人</span>
                        </c:if> --%>
                    </div>
                    <div class="tab-pane fade hidden" id="wasteInfo">

                    </div>
                    <div class="tab-pane fade hidden" id="licenceItemInfo">

                    </div>
                    <c:if test="${userRole eq 'ADMIN'}">
                        <div class="tab-pane fade" id="manageInfo">
                            <!-- 我的企业信息 -->

                            <div class="panel panel-default">
                                <div class="panel-heading">待审批申请
                                    <a href="javascript:" data-tool="panel-collapse"
                                       data-toggle="tooltip" title="" class="pull-right">
                                        <em id="pendingApprove" class="fa fa-minus"></em>
                                    </a>
                                </div>
                                <div class="panel-body collapse in ">
                                    <c:if test="${!empty pendingApproveUserList}">
                                        <table class="table table-striped table-hover table-condensed">
                                            <thead>
                                            <tr>
                                                <th>申请人</th>
                                                <th>申请人电话</th>
                                                <th>申请状态</th>
                                                <th>操作</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="it" items="${pendingApproveUserList}"
                                                       varStatus="status" begin="0" step="1">
                                                <tr userId="${it.sysUserId}">
                                                    <td>${it.chineseName}</td>
                                                    <td>${it.phoneNum}</td>
                                                    <td>${it.userStatus}</td>
                                                    <td>
                                                        <a href="javascript:"
                                                           onclick="applyApproved('${it.id}',this,'${it.chineseName}')">通过</a>
                                                        <a href="javascript:"
                                                           onclick="applyReject('${it.id}',this)">拒绝</a>

                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>
                                    <c:if test="${empty pendingApproveUserList}">
                                        <span>暂无加入企业申请</span>
                                    </c:if>
                                </div>
                            </div>


                            <div class="panel panel-default">
                                <div class="panel-heading">已加入员工
                                    <a href="javascript:" data-tool="panel-collapse"
                                       data-toggle="tooltip" title="" class="pull-right">
                                        <em class="fa fa-minus"></em>
                                    </a>
                                </div>
                                <div class="panel-body collapse in ">
                                    <table id="hasApprovedTable"
                                           class="table table-striped table-hover table-condensed">
                                        <thead>
                                        <tr>
                                            <th>姓名</th>
                                            <th>电话</th>
                                            <th>角色</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="it" items="${approvedUserList}"
                                                   varStatus="status" begin="0" step="1">

                                            <tr userId="${it.sysUserId}" userRole="${it.role}">
                                                <td>${it.chineseName}</td>
                                                <td>${it.phoneNum}</td>
                                                <td>${it.roleName}</td>
                                                <td>
                                                    <c:if test="${it.role ne  'ADMIN' ||(it.role eq 'ADMIN' && userExtendId eq it.id)}">
                                                    <a href="javascript:void(0);"
                                                       onclick="unbindUser('${it.id}',this,'${it.chineseName}')">退出</a>
                                                    </c:if>
                                                    <c:if test="${it.role ne  'ADMIN'}">
                                                        <a href="javascript:void(0);"
                                                           onclick="beAdmin('${it.id}',this,'${it.chineseName}')" class="adminClickTag"> 设为管理员</a>
                                                    </c:if>
                                                    <c:if test="${it.role eq  'ADMIN' && userExtendId eq it.id}">
                                                        <a href="javascript:void(0);"
                                                           onclick="removeAdmin('${it.id}',this,'${it.chineseName}')" class="adminClickTag"> 移除管理员</a>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script type="text/javascript">
  <%--var dispalyAddBtn = "${dispalyAddBtn}";--%>
  //页面加载完成，把按钮放开
  $(document).ready(function () {
    var rs = window.globalInit();
    rs.done(function () {
      init();
    });
  });
  function init() {
    if (window.globalParam.userStatus == "PASS") {
      $('#mainPanel').removeClass('hidden');
    }
    if(window.globalParam.enterStatus  == "SUBMIT" || window.globalParam.userRole == "ADMIN"){
    	$('#modifyEnter').removeClass('hidden');
    }
    
    
    enterUserList();
    if(window.globalParam.userStatus!='QUIT'&&window.globalParam.userStatus!=' '&&window.globalParam.userStatus!='REJECT'&&window.globalParam.reminder){
        $(".msgView").html(window.globalParam.reminder).show();
    }
    if(window.globalParam.enterStatus=='SUBMIT'&&window.globalParam.reminder){
        $(".msgView").html(window.globalParam.reminder).show();
//        $("#bindEnterprise").hide();
    }
    var entId = "${sysEnterpriseBase.entId}";

    var ticketId = "<%= ticketId %>";
    $.ajax({
      url: "<%=appPath %>/fileUpload/getFileByBusinessCode",
      data: {
        'businessCode': 'a' + entId,
        'ticketId': ticketId
      },
      type: "POST",
      dataType: 'json',
      async: false,
      success: function (data) {
        if (data.status == 1) {
          var uploadFileInfo = data.data.uploadFileInfo;
          if (uploadFileInfo) {
            $("#enterpriseImg").attr('src',
                IMG_VIEW_URL + '&businessCode=' + uploadFileInfo.businessCode + '&fileID='
                + uploadFileInfo.fileId);
          }
        } else {
          $.notify("获取图片信息失败", {
            status: 'danger'
          });
        }
      },
      error: function (er) {
        $.notify("获取图片信息失败", {
          status: 'danger'
        });
      }
    });

    // 展开，收缩事件
    $(".fa").click(function () {
      var em = $(this);
      var panelDiv = em.parent().parent().next();
      if (em.hasClass('fa-plus')) {
        if (!panelDiv.hasClass('in')) {
          panelDiv.addClass('in');
          em.removeClass('fa-plus').addClass('fa-minus');
        }
      } else if (em.hasClass('fa-minus')) {
        if (panelDiv.hasClass('in')) {
          panelDiv.removeClass('in');
          em.removeClass('fa-minus').addClass('fa-plus');
        }
      }
    });

    // 根据企业类型显示产废信息和许可内容
    $(".enterprise-type-badge").each(function () {
      if ($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION%>")) {
        $(".wasteInfo").removeClass("hidden");
        $("#wasteInfo").removeClass("hidden");
        $(this).addClass("production-badge");
      } else if ($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_DISPOSITION%>")) {
        $(".licenceItemInfo").removeClass("hidden");
        $("#licenceItemInfo").removeClass("hidden");
        $(this).addClass("disposition-badge");
      } else if ($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_RECYCLING%>")) {
        $(this).addClass("recycling-badge");
      } else if ($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_IDENTIFICATION%>")) {
        $(this).addClass("identification-badge");
      } else if ($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_TRANSPORTATION%>")) {
        $(this).addClass("transportation-badge");
      }
    });
    if (window.globalParam.userStatus != "PASS" || window.globalParam.userRole != "ADMIN") {
      $("[href='#manageInfo'], #manageInfo").remove();
    }
//      if (dispalyAddBtn == "true") { //显示加入企业按钮时，【我的管理信息界面】隐藏
//        $("[href='#manageInfo'], #manageInfo").remove();
//      }
  }

  function wasteinfo(that) {
    $("#myTab").find("li").removeClass("active");
    $(that).parent().addClass("active");
    $("#myTabContent").find(".tab-pane").removeClass("active in");
    var enterpriseId = $("#enterpriseId").val();
    var page = "<%=appPath %>/entWaste/listView.htm?ticketId=<%=ticketId %>&noTopLeftButtom=1&enterpriseId="
        + enterpriseId;
    var $wasteInfo = $("#wasteInfo");
    if (!$wasteInfo.hasClass("whirl")) {
      $wasteInfo.addClass("whirl");
    }
    $wasteInfo.addClass("active in").load(page, function () {
      if ($wasteInfo.hasClass("whirl")) {
        $wasteInfo.removeClass("whirl");
      }
    });
  }

  function licenceItem(that) {
    $("#myTab").find("li").removeClass("active");
    $(that).parent().addClass("active");
    $("#myTabContent").find(".tab-pane").removeClass("active in");
    var enterpriseId = $("#enterpriseId").val();
    var page = "<%=appPath %>/licence/detail.htm?ticketId=<%=ticketId %>&noTopLeftButtom=1&enterpriseId="
        + enterpriseId;
    var $licenceItemInfo = $("#licenceItemInfo");
    if (!$licenceItemInfo.hasClass("whirl")) {
      $licenceItemInfo.addClass("whirl");
    }
    $licenceItemInfo.addClass("active in").load(page, function () {
      if ($licenceItemInfo.hasClass("whirl")) {
        $licenceItemInfo.removeClass("whirl");
      }
    });
  }

  //加入企业
  function joinEnterprise(that) {
    var berforeText = $(that).text();
    $(that).after("<i class='btn_loading'></i>").text("加入中...").parent().find("button").attr(
        "disabled", "disabled");
    $.page.ajax($.page.getAjaxSettings({
      type: "POST",
      dataType: "json",
      url: "<%=appPath %>/myenterprise/joinEnterprise.htm",
      data: {
        "enterpriseId": $("#enterpriseId").val() //待加入企业的ID
      },
      success: function (result, textStatus, jqXHR) {
        location = "<%=appPath %>/myenterprise/myEnterprise.htm?ticketId=<%=ticketId %>";
      },
      complete: function (jqXHR, textStatus) {
        var result = jqXHR.responseJSON;
        if (result.status != 1) {
          var options = {
            "status": "<%= Constant.STATUS_INFO%>",
            "pos": "top-center"
          };
          $.notify("绑定企业过程中发生了异常:" + result.infoList[0], options);
          $(that).text(berforeText).parent().find("button").removeAttr("disabled");
          $(that).next(".btn_loading").remove();
        }
      }
    }));
  }
  function applyApproved(recordid, domEle, name) {
    $(domEle).hide();
    $.ajax({
      url: "<%=appPath %>/myenterprise/updateUserStatus.htm?etc=" + new Date().getTime()
      + "&ticketId=<%=ticketId %>",
      data: {
        "recordid": recordid,
        "userStatus": 'PASS'
      },
      dataType:"json",
      success: function (result) {
        $(domEle).show();
        if (result.status == 1) {
          var user = result.data;
          var $tr = $(domEle).parents("tr");
          var $cloneTr = $tr.clone();
          $cloneTr.attr("userRole", "RUGULAR");
          var $a1 = $(
              '<a href="javascript:void(0);" onclick="unbindUser(\'' + recordid + '\',this,\''
              + name + '\')">退出</a>');
          var $a2 = $(
              '<a href="javascript:void(0);" onclick="beAdmin(\'' + recordid + '\',this,\'' + name
              + '\') " class="adminClickTag">设为管理员</a>');
          $cloneTr.children("td:eq(2)").empty().append("普通用户");
          $cloneTr.children("td:eq(3)").empty().append($a1).append(
              $a2);
          $tr.remove();
          $("#hasApprovedTable>tbody").append($cloneTr);
          var contactsTr = "<tr userId='" + user.userId + "'>";
          contactsTr += "<td>" + (user.userName || "") + "</td>";
          contactsTr += "<td>" + (user.phoneNo || "") + "</td>";
          contactsTr += "<td>" + (user.mailAddress || "") + "</td>";
          contactsTr += "</tr>";
          $("#contactsTable>tbody").append(contactsTr);
        } else {
          var options = {
            "status": "<%= Constant.STATUS_INFO%>",
            "pos": "top-center"
          };
          $.notify("申请加入企业异常", options);
        }
      }
    });
  }
  function enterUserList(){
		$("#myTab").find("li").removeClass("active");
		$("#contactTab").parent().addClass("active");
		$("#myTabContent").find(".tab-pane").removeClass("active in");
		var enterpriseId = $("#enterpriseId").val();
		var $contacts = $("#contacts");
		$contacts.empty();
		$contacts.addClass("active in");
		
		$.ajax({
			async: false,
			url: "<%=appPath %>/myenterprise/getEnterUserByEnterId.htm?ticketId=<%=ticketId %>",
			data: {
				"enterpriseId": enterpriseId
			},
			success: function(result) {
				var obj = $.parseJSON(result);
				if(obj.status == 1) {
					if(obj.data.userInfo==null){
						$contacts.append("<span>暂时还没有联系人</span>");
					}else{
						$contacts.append(createTable(obj.data.userInfo).prop("outerHTML"));
					}
					
				}
				
			}
		}
		);
		
		
	}
	
	
	function createTable(userInfo) {
		// 创建表头
		var $table = $("<table class='table-bordered table-condensed col-md-12' style='width:100%'></table>");
		var $thead = $("<thead><tr></tr></thead>");
		/* $thead.append("<th></th>"); */
		$thead.append("<th>联系人</th>");
//		$thead.append("<th>电话</th>");
		$thead.append("<th>邮箱</th>"); // 委托处理的场合 
		$table.append($thead.prop("outerHTML"));
		$table.append("<tbody></tbody>");
		// 创建行项目
		for(var index in userInfo) {
			var $tr = $("<tr userId="+ userInfo[index].userId+ "></tr>");
			$tr.append("<td > " + userInfo[index].chineseName + "</td>");
//			$tr.append("<td > " + userInfo[index].phoneNum + "</td>");
			if(userInfo[index].emailAddress==null){
				$tr.append("<td > </td>");
			}else{
				$tr.append("<td > " + userInfo[index].emailAddress + "</td>");
			}
			
			$table.find("tbody").append($tr.prop("outerHTML"));
			
		}
		return $table;
	}
	
  function applyReject(recordid, domEle) {
    $(domEle).hide();
    $.ajax({
      url: "<%=appPath %>/myenterprise/updateUserStatus.htm?etc=" + new Date().getTime()
      + "&ticketId=<%=ticketId %>",
      dataType:"json",
      data: {
        "recordid": recordid,
        "userStatus": 'REJECT'
      },
      success: function (result) {
        $(domEle).show();
        if (result.status == 1) {
          $(domEle).parents("tr").remove();
        } else {
          var options = {
            "status": "<%= Constant.STATUS_INFO%>",
            "pos": "top-center"
          };
          $.notify("申请加入企业异常", options);
        }
      }
    });
  }

  function unbindUser(recordid, domEle, name) {
    $(domEle).hide();
    var title = "确定退出吗？";
    var text = "";
    var userRole = $(domEle).parents("tr").attr("userRole");
    if (userRole == "ADMIN") {
      text = "点击确定后你将退出该企业,";
    } else {
      text = "点击确定后" + name + "将退出该企业";
    }
    swal({
      title: title,
      text: text,
      type: "warning",
      showCancelButton: true,
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      closeOnConfirm: true,
      closeOnCancel: true
    }, function (isConfirm) {
      if (isConfirm) {
        $.ajax({
          dataType:"json",
          url: "<%=appPath %>/myenterprise/enterpriseExit.htm?etc=" + new Date().getTime()
          + "&ticketId=<%=ticketId %>",
          data: {
            "recordid": recordid
          },
          success: function (result) {
             $(domEle).show();
              if (result.status == 1) {
                if (userRole=="ADMIN") {
                  window.location.href = "<%=appPath %>/myenterprise/myEnterprise.htm?etc="
                      + new Date().getTime() + "&ticketId=<%=ticketId %>";
                } else {
                  $(domEle).parents("tr").remove();
                  var userId = $(domEle).parents("tr").attr("userId");
                  $("#contactsTable>tbody>tr[userId='" + userId + "']").remove();
                }
              } else {
                var options = {
                  "status": "<%= Constant.STATUS_INFO%>",
                  "pos": "top-center"
                };
                $.notify(result.infoList[0], options);
              }
          }
        });
      }else {
        $(domEle).show();
      }
    });

  }

  function unbindEnterprise(domEle) {
    $(domEle).text("退出中...").attr("disabled","disabled");
    var title = "确定退出吗？";
    var text = "点击确定后你将退出该企业,";
    swal({
      title: title,
      text: text,
      type: "warning",
      showCancelButton: true,
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      closeOnConfirm: true,
      closeOnCancel: true
    }, function (isConfirm) {
      if (isConfirm) {
        $.ajax({
          dataType:"json",
          url: "<%=appPath %>/myenterprise/enterpriseExit.htm?etc=" + new Date().getTime()
          + "&ticketId=<%=ticketId %>",
          success: function (result) {
              if (result.status == 1) {
                window.location.href = "<%=appPath %>/myenterprise/myEnterprise.htm?etc="
                    + new Date().getTime() + "&ticketId=<%=ticketId %>";
              } else {
                $(domEle).text("解绑企业").removeAttr("disabled");
                var options = {
                  "status": "<%= Constant.STATUS_INFO%>",
                  "pos": "top-center"
                };
                $.notify(result.infoList[0], options);
              }
          }
        });
      }else{
        $(domEle).text("解绑企业").removeAttr("disabled");
      }
    });
  }
  function updateRole(recordid, domEle, name, role) {
    $(domEle).hide();
    var title = "";
    var text = "";
    var userRole = $(domEle).parents("tr").attr("userRole");
    if (userRole == "ADMIN") {
      title="确定移除吗？";
      text = "点击确定后你移除管理员身份";
    } else {
      title="确定设为管理员吗？";
      text = "点击确定后" + name + "将被设为管理员，你将无法对其执行退出或移除操作";
    }
    swal({
      title: title,
      text: text,
      type: "warning",
      showCancelButton: true,
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      closeOnConfirm: true,
      closeOnCancel: true
    }, function (isConfirm) {
      if (isConfirm) {
        $.ajax({
          url: "<%=appPath %>/myenterprise/updateUserRole.htm?etc=" + new Date().getTime()
          + "&ticketId=<%=ticketId %>",
          data: {
            "id": recordid,
            "role": role,
          },
          dataType:"json",
          success: function (result) {
            $(domEle).show();
            if (result.status == 1) {
              var $td = $(domEle).parent("td");
              $td.empty();
              $td.prev("td").text("管理员");
              if (userRole == "ADMIN") {
                window.location.href = "<%=appPath %>/myenterprise/myEnterprise.htm?etc="
                    + new Date().getTime() + "&ticketId=<%=ticketId %>";
              }
            } else {
              var options = {
                "status": "<%= Constant.STATUS_INFO%>",
                "pos": "top-center"
              };
              $.notify(result.infoList[0], options);
            }
          }
        });
      }else{
        $(domEle).show();
      }
    });

  }

  function beAdmin(recordid, domEle, name) {
    updateRole(recordid, domEle, name, "ADMIN");
  }
  function removeAdmin(recordid, domEle, name, role) {
    updateRole(recordid, domEle, name, "REGULAR");
  }

</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<%------- 结束导入底部信息 -------%>