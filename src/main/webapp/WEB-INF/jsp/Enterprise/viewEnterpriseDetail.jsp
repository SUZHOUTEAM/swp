<%@page import="com.mlsc.waste.utils.Constant"%>
<%@page import="com.mlsc.waste.utils.CodeTypeConstant"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<c:choose>
	<c:when test="${isSysUser}">
		<jsp:include page="/common/top.jsp" flush="true">
			<jsp:param name="title" value="企业管理"/>
		</jsp:include>
		<jsp:include page="/common/left.jsp" flush="true">
			<jsp:param name="menuId" value="#enterpriseManagement"/>
		</jsp:include>
	</c:when>
	<c:when test="${index !=null&&index!=true}">
		<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
			<jsp:param name="title" value="企业管理"/>
		</jsp:include>
		<jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
			<jsp:param name="menuId" value="#${index}"/>
		</jsp:include>
	</c:when>
	<c:otherwise>
		<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
			<jsp:param name="title" value="企业详情" />
		</jsp:include>
		<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
			<jsp:param name="menuId" value="#myEnterprise" />
		</jsp:include>
	</c:otherwise>
</c:choose>
<link rel="stylesheet" href="<%=appPath %>/css/enterprise/enterprise_common.css">
<!-- Main section-->
<section>
	<div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="history.go(-1);">${breadcrumbName} </span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
		<span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">企业详情</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
	</div>
	<div class="panel panel-body">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="media-box col-md-12 colcss">
					<div class="pull-left col-md-1 colcss">
						<img id="enterpriselog" src="<%=appPath %>/app/img/business_icon/default_enterprise.png" alt="img" class="media-box-object img-responsive img-rounded thumb65">
					</div>
					<div class="media-box-body box1">
						<input type="hidden" value="${sysEnterpriseBase.entId}" id="enterpriseId">
						<div class="col-md-9 colcss">
							<strong class="media-box-heading text-primary">
                                <span class="">${sysEnterpriseBase.entName}</span>
                                <c:if test="${!empty enterpriseTypes }">
                                    <c:forEach var="entTypeIt" items="${enterpriseTypes}" varStatus="status" begin="0" step="1">
                                        <span class="enterprise-type-badge ${entTypeIt.code.toLowerCase()}-badge">${entTypeIt.value}</span>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${onlyViewFlg}">
                                    <img title="关注" src="<%=appPath %>/app/img/icon/un_follow.png" onclick="changeImg(this,'1')">
                                </c:if>
                            </strong>
							<p class="mb-sm">
								<span>企业代码:${sysEnterpriseBase.entCode}</span>
							</p>
							<p class="mb-sm">
								<span>状态:${sysEnterpriseBase.enterpriseStatusLabel}</span>
							</p>
							<p class="mb-sm">
								<span>地址:${sysEnterpriseBase.entAddress}</span>
							</p>
							<p class="mb-sm">
								<span>创建时间:${sysEnterpriseBase.createTime}</span>
							</p>
							<c:if test="${isSysUser}">
							<p class="mb-sm">
								<span>申请人姓名:${sysEnterpriseBase.userName}</span>
							</p>
							<p class="mb-sm">
								<span>申请人电话:${sysEnterpriseBase.phoneNum}</span>
							</p>
							</c:if>
						<c:if test="${not onlyViewFlg}">
							<div class="pull-right col-md-3 colcss right-bottom toolDiv">
								<button id="auditEnterprisePass" class="btn btn-md btn-success" onclick="auditPass(this)">审核通过</button>
								<button id="auditEnterpriseRefuse" class="btn btn-md btn-warning" onclick="auditRefuse(this)">审核退回</button>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<ul id="myTab" class="nav nav-tabs">
					<li class="active">
						<a href="#contacts" data-toggle="tab">联系人</a>
					</li>
					<li class="hidden wasteInfo">
						<a href="javascript:" onclick="wasteinfo(this)">产废信息</a>
					</li>
					<li class="hidden licenceItemInfo">
						<a href="javascript:" onclick="licenceItem(this)">许可内容</a>
					</li>
					<c:if test="${not onlyViewFlg}">
					<li>
						<a href="javascript:" onclick="viewBusinessLicence(this)">营业执照</a>
					</li>
					</c:if>
				</ul>
				<div id="myTabContent" class="tab-content col-md-12">
					<div class="tab-pane fade in active" id="contacts">
						<c:if test="${!empty prcSysUserList}">
							<table class="table-bordered table-condensed col-md-12" style="width:100%">
								<thead>
									<tr>
										<th>联系人</th>
										<th>电话</th>
										<th>邮箱</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="it" items="${prcSysUserList}" varStatus="status" begin="0" step="1">
										<tr>
											<td>${it.chineseName}</td>
											<td><a href="javascript:" onclick="contact('${it.phoneNum}')">联系TA</a><%--${it.phoneNum}--%></td>
											<td>${it.emailAddress}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						<c:if test="${empty prcSysUserList}">
							<span>暂时还没有联系人</span>
						</c:if>
					</div>
					<div class="tab-pane fade hidden" id="wasteInfo">
					</div>
					<div class="tab-pane fade hidden" id="licenceItemInfo">
					</div>
					<div class="tab-pane fade" id="businessLicenceInfo">
						<img src="<%=appPath%>/app/img/business_icon/default_enterprise.png" />
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script type="text/javascript">
	//页面加载完成，把按钮放开
	var enterpriseSataus = "${sysEnterpriseBase.enterpriseStatus}";
	$(document).ready(function() {
	    var index='${index}';
	    if(index&&index!='true'){
            init();
        }else{
            var rs = window.globalInit();
            rs.done(function () {
                init();
            });
        }
	});
	function contact(phoneNo) {
        collectingUserBehavior('<%=ticketId %>','CONTACTHIM','${sysEnterpriseBase.entName}','企业详情');
		IMChat.window.yunXin.openChatBox(phoneNo,'p2p');
		loadData('<%=ticketId %>',phoneNo);
	}
	function init() {
      if(enterpriseSataus != "<%=CodeTypeConstant.USER_EVENT_STATUS_SUBMIT%>") {
        $(".toolDiv").find(".btn").attr("disabled", "disabled");
      }

      $(".enterprise-type-badge").each(function() {
        if($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION%>")) {
          $(".wasteInfo").removeClass("hidden");
          $("#wasteInfo").removeClass("hidden");
          $(this).addClass("production-badge");
        } else if($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_DISPOSITION%>")) {
          $(".licenceItemInfo").removeClass("hidden");
          $("#licenceItemInfo").removeClass("hidden");
          $(this).addClass("disposition-badge");
        } else if($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_RECYCLING%>")) {
          $(this).addClass("recycling-badge");
        } else if($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_IDENTIFICATION%>")) {
          $(this).addClass("identification-badge");
        } else if($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_TRANSPORTATION%>")) {
          $(this).addClass("transportation-badge");
        }
      });

      // 加载企业log
      loadingEnterpriseImg($("#enterpriselog"), $("#enterpriseId").val());
      loadBusinessLicence($("#enterpriseId").val());
      if($("strong img").length > 0) {
        //判断是否关注过企业
        loadingFollowRelation($("strong img"), $("#enterpriseId").val());
      }
    }
	function loadBusinessLicence(enterpriseId){
		$.ajax({
			url: "<%=appPath %>/fileUpload/getFileByFileTypeAndReferenceId",
			data: {
				'referenceId' :enterpriseId,
				'fileType':'b'
			},
			type: "POST",
			dataType: 'json',
			async: true,
			success: function(data) {
				if(data.status==1){
					var uploadFileInfo=data.data.uploadFileInfo;
					if(uploadFileInfo&&uploadFileInfo.length>0){
						var obj=uploadFileInfo[0];
						var imgurl=IMG_VIEW_URL+'&businessCode='+obj.businessCode+'&fileID='+obj.fileId;
	                    $("#businessLicenceInfo img").attr('src',imgurl);
					}
				}
			}
		});
	}
	function auditPass(that) {
		swal({
				title: "申请通过",
				text: "请确认是否通过该申请",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#DD6B55",
				confirmButtonText: "通过",
				cancelButtonText: "取消",
				closeOnConfirm: false,
				closeOnCancel: true
			},
			function(isConfirm) {
				if(isConfirm) {
					$(that).parent().find(".btn").attr("disabled", "disabled");
					var enterpriseId = $("#enterpriseId").val();
					$.post("<%=appPath %>/enterprisemanagement/auditEnterprisePass.htm?etc=" + new Date().getTime() + "&enterpriseId=" + enterpriseId + "&ticketId=<%=ticketId %>", function(result) {
						var obj = $.parseJSON(result);
						if(obj.status == 1) {
							location = "<%=appPath %>/enterprisemanagement/list.htm?ticketId=<%=ticketId %>";
						} else {
							$.notify("系统错误,请联系管理员!", "danger");
							$(that).parent().find(".btn").removeAttr("disabled");
						}
					});
				}
			});
	}

	//平台管理员审核企业退回
	function auditRefuse(that) {
		swal({
				title: "申请退回",
				text: "请确认是否退回该申请",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#DD6B55",
				confirmButtonText: "退回",
				cancelButtonText: "取消",
				closeOnConfirm: false,
				closeOnCancel: true
			},

			function(isConfirm) {
				if(isConfirm) {
					$(that).parent().find(".btn").attr("disabled", "disabled");
					var enterpriseId = $("#enterpriseId").val();
					$.post('<%=appPath %>/enterprisemanagement/auditEnterpriseRefuse.htm?etc=' + new Date().getTime() + "&enterpriseId=" + enterpriseId + "&ticketId=<%=ticketId %>", function(result) {
						var obj = $.parseJSON(result);
						if(obj.status == 1) {
							location = "<%=appPath %>/enterprisemanagement/list.htm?ticketId=<%=ticketId %>";
						} else {
							$.notify("系统错误,请联系管理员!", "danger");
							$(that).parent().find(".btn").removeAttr("disabled");
						}
					});
				}
			});
	}

	function wasteinfo(that) {
		$("#myTab").find("li").removeClass("active");
		$(that).parent().addClass("active");
		$("#myTabContent").find(".tab-pane").removeClass("active in");
		var enterpriseId = $("#enterpriseId").val();
		var page = "<%=appPath %>/entWaste/listView.htm?ticketId=<%=ticketId %>&enterpriseId=" + enterpriseId;
		var $wasteInfo = $("#wasteInfo");
		if(!$wasteInfo.hasClass("whirl")) {
			$wasteInfo.addClass("whirl");
		}
		$wasteInfo.addClass("active in").load(page, function() {
			if($wasteInfo.hasClass("whirl")) {
				$wasteInfo.removeClass("whirl");
			}
			//          $wasteInfo.addClass("whirl");
		});
	}

	function licenceItem(that) {
		$("#myTab").find("li").removeClass("active");
		$(that).parent().addClass("active");
		$("#myTabContent").find(".tab-pane").removeClass("active in");
		var enterpriseId = $("#enterpriseId").val();
		var page = "<%=appPath %>/licence/detail.htm?ticketId=<%=ticketId %>&noTopLeftButtom=1&enterpriseId=" + enterpriseId;
		var $licenceItemInfo = $("#licenceItemInfo");
		if(!$licenceItemInfo.hasClass("whirl")) {
			$licenceItemInfo.addClass("whirl");
		}
		$licenceItemInfo.addClass("active in").load(page, function() {
			if($licenceItemInfo.hasClass("whirl")) {
				$licenceItemInfo.removeClass("whirl");
			}
		});
	}

	function viewBusinessLicence(that) {
		$("#myTab").find("li").removeClass("active");
		$(that).parent().addClass("active");
		$("#myTabContent").find(".tab-pane").removeClass("active in");
		var enterpriseId = $("#enterpriseId").val();
		$("#businessLicenceInfo").addClass("active in");
	}

	function getParam(paraName) {
		var search = document.location.search,
				reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
		if(search && reg.test(search)) {
			return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
		}
		return null;
	}

	function goBack(){
		var index = getParam("index");
		if(index == "true"){
			document.cookie="index=true;path=<%=appPath%>/wastecircle/";
		}
		window.history.go(-1);
	}

	//加载企业log
	function loadingEnterpriseImg($imgSelector, enterpriseId) {
		$.ajax({
			async: true,
			type: "post",
			url: "<%=appPath %>/myenterprise/getEnterImgSrc.htm?ticketId=<%=ticketId %>&enterpriseId=" + enterpriseId,
			success: function(result) {
				var obj = $.parseJSON(result);
				if(obj.status == 1) {
					if(obj.data.imgSrc) {
						$imgSelector.attr("src", obj.data.imgSrc);
					}
				} else if(obj.status == 0) {
					var options = {
						"status": "<%=Constant.STATUS_INFO %>"
					};
					$.notify("企业图像数据加载过程中发生了异常，稍后再试", options);
                }
            },
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				var options = {
					"status": "<%=Constant.STATUS_INFO %>"
				};
				$.notify("请求错误", options);
			}
		});
	}

	//判断是否关注过企业
	function loadingFollowRelation($imgSelector, enterpriseId) {
		$.ajax({
			async: true,
			type: "post",
			url: "<%=appPath %>/follow/getFollowRealtion.htm?ticketId=<%=ticketId %>&followId=" + enterpriseId,
			success: function(result) {
				var obj = $.parseJSON(result);
				if(obj.status == 1) {
					if(obj.data.followRealtion) { // 已经关注过了当前企业
						$imgSelector.attr("src", "<%=appPath %>/app/img/icon/followed.png").attr("title", "取消关注").attr("onclick", "changeImg(this,'0')");
					} else {

					}
				} else if(obj.status == 0) {
					var options = {
						"status": "<%=Constant.STATUS_INFO %>"
					};
					$.notify("获取关注关系时发生了异常，稍后再试", options);
                }
            },
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				var options = {
					"status": "<%=Constant.STATUS_INFO %>"
				};
				$.notify("请求错误", options);
			}
		});
	}

	//判断是否关注过企业
	function changeImg(that, action) {
		var followBerforText = "";
		if(action == "0") {
			followBerforText = "取消关注";
		} else {
			followBerforText = "关注";
		}
		$.ajax({
			async: false,
			url: "<%=appPath %>/wastecircle/saveFollow.htm?ticketId=<%=ticketId %>&entId=" + $("#enterpriseId").val() + "&action=" + action,
			success: function(result) {
				var obj = $.parseJSON(result);
				if(obj.status == 1) {
					var options = {
						"status": "<%=Constant.STATUS_INFO%>"
					};
					var text = followBerforText + "成功";
					$.notify(text, options);
					if(action == "0") {
						$(that).attr("src", "<%=appPath %>/app/img/icon/un_follow.png").attr("title", "关注").attr("onclick", "changeImg(this,'1')");
					} else {
						$(that).attr("src", "<%=appPath %>/app/img/icon/followed.png").attr("title", "取消关注").attr("onclick", "changeImg(this,'0')");
					}
				} else if(obj.status == 0) {
					var options = {
						"status": "<%=Constant.STATUS_INFO%>"
					};
					$.notify(followBerforText + "异常，请稍后再试", options);
				}
			}
		});
	}
</script>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true" />
<%------- 结束导入底部信息 -------%>