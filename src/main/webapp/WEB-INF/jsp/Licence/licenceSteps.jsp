<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.mlsc.waste.utils.Constant"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
    String licenceId=(String)request.getAttribute("licenceId");
%>
<c:if test="${isSysUser}">
    <jsp:include page="/common/top.jsp" flush="true">
      <jsp:param name="title" value="企业信息"/>
    </jsp:include>
    <jsp:include page="/common/left.jsp" flush="true">
        <jsp:param name="menuId" value="#enterpriseManagement" />
    </jsp:include>
</c:if>
<c:if test="${not isSysUser}">
    <jsp:include page="/common/webCommon/user_top.jsp" flush="true">
      <jsp:param name="title" value="许可证管理"/>
    </jsp:include>
    <jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
        <jsp:param name="menuId" value="#licenceManage" />
    </jsp:include>
</c:if>
<link rel="stylesheet" href="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.css?1">
<link rel="stylesheet" href="<%=appPath %>/css/licence/licence.css">
<!-- Main section-->
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的企业</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner"><a href="<%=appPath%>/licence/detail.htm?ticketId=<%=ticketId%>">许可证管理</a></span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">许可证<%=StringUtils.isEmpty(licenceId)?"新增":"编辑"%></span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div class="">
        <div id="example-basic">
	        <h3>许可证基本信息</h3>
            <section id="baseInfo" class="licenceSteps" data-mode="async" data-url="<%=appPath %>/licence/addLicenceBaseInfo.htm?ticketId=<%=ticketId%>&enterpriseId=${enterpriseId}"></section>
            <h3>危废信息填写</h3>
            <section id="wasteInfo" class="licenceSteps" data-mode="async" data-url="<%=appPath %>/licence/addLicence.htm?ticketId=<%=ticketId%>&enterpriseId=${enterpriseId}"></section>
            <h3>文件上传</h3>
            <section id="fileInfo" class="licenceSteps" data-mode="async" data-url="<%=appPath %>/licence/licenceUpload.htm?ticketId=<%=ticketId%>&enterpriseId=${enterpriseId}"></section>
        </div>
    </div>
</section>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script src="<%=appPath %>/thirdparty/jquery.steps/build/jquery.steps.js"></script>
<script src="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.min.js?1"></script>

<%------- 结束导入底部信息 -------%>

<script type="text/javascript">
var operationLicenceId="${licenceId}";
$(document).ready(function() {
  var rs = window.globalInit();
  rs.done(function () {
    // 判断是否是编辑的场合
    isHasLicenceId ();

    // steps初始化
    $("#example-basic").steps({
      headerTag: "h3",
      bodyTag: "section",
      transitionEffect: "slide",
      onStepChanging: function (event, currentIndex, newIndex)
      {
        if(operationLicenceId == "" || operationLicenceId == "null"){
          // 许可证基本信息保存异常
          var options={"status":"<%= Constant.STATUS_INFO%>"};
          $.notify("许可证基本信息还没有保存，请先填写基本信息并保存",options);
          return false;
        }
        var flag=false;
        if(currentIndex==1&&newIndex==2){
            $.page.ajax($.page.getAjaxSettings({
                async: false,
                type: "POST",
                dataType: "json",
                url: '/licence/checkLicenceDetail.htm',
                data: {
                    "id": operationLicenceId
                },
                success: function (result, textStatus, jqXHR) {
                    if(result.status!=1){
                        var options={"status":"info"};
                        $.notify("尚未添加许可证详情",options);
                        flag=false;
                    }else{
                        flag=true;
                    }
                }
            }));
        }else{
            flag=true;
        }
        return flag;
      },
      onFinishing: function (event, currentIndex)
      {
        return true;
      },
      onFinished: function (event, currentIndex)
      {
        submitAudit ();
      }
    });
  });

    
});

// 判断是否是编辑的场合
function isHasLicenceId(){
    if ("${licenceId}" != "") {
        var $section = $("#example-basic").find(".licenceSteps");
        $section.each(function(){
            var dataurl = $(this).attr("data-url");
            if (dataurl.indexOf("licenceId") == -1) {
                $(this).attr("data-url", dataurl + "&licenceId=" + operationLicenceId);
            }
        });
    }
}

function addSuccess(licenceId){
    if ("${licenceId}" == "") {// 许可证新增的场合
        //licencdId的值设置后，本页面由新增页面变成了编辑页面
        operationLicenceId = licenceId;
        updateStepSetting();
    }
}

function updateStepSetting(){
    updateStepDataUrl (0, "addLicenceBaseInfo.htm");
    updateStepDataUrl (1, "addLicence.htm");
    updateStepDataUrl (2, "licence_upload.jsp");
}

function updateStepDataUrl(index, action) {
    var step = $("#example-basic").steps("getStep",index);
    if (step.contentUrl.indexOf("licenceId") == -1) {
        var contentUrl = step.contentUrl;
        step.contentUrl = contentUrl + "&licenceId=" + operationLicenceId;
    }
}

// 提交审核
function submitAudit(){
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/licence/submitAudit.htm?etc=" + new Date().getTime(),
        data: {
            "id": operationLicenceId
        },
        success: function (result, textStatus, jqXHR) {
            swal({
                    title: "许可证创建成功!",
                    text: "系统正在审核，请等待系统通知，审核结果会有短信通知到你，如需帮助，请拨打电话：0512-62717018",
                    type: "success",
                    confirmButtonColor: "#1171d1",
                    closeOnConfirm: true,
                    confirmButtonText: "知道了"
                },
                function(){
                    window.location='<%=appPath %>/licence/detail.htm?ticketId=<%=ticketId %>';
                });
        },complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                
            }
        }
    }));
}

</script>