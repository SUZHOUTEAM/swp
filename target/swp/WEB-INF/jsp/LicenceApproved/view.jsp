<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>

<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="许可证详情"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#approveLicence"/>
</jsp:include>
<%------- 结束导入左侧信息 -------%>
<link rel="stylesheet" href="<%=appPath %>/css/licence/licence.css">
<!-- Main section-->
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">权限管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="back();">许可证审核</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">许可证详情</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
   <!-- Page content-->
   <div>
      <div class="row">
         <div class="col-md-12 marginbottom">
             <div class="tool-div">
                 <div class="btn-group" role="group">
                     <button type="button" id="approvedPass" class="btn btn-default" onclick="approvedPass(this)">审核通过</button>
                     <button type="button" id="approvedReject" class="btn btn-default" onclick="approvedReject(this)">审核退回</button>
                 </div>
             </div>
         </div>
         <br/>
         <div class="col-md-12">
             <div class="panel panel-default">
                 <div class="panel-heading">${operationLicence.auditStatus}许可证基本信息
                     <a href="javascript:;" data-tool="panel-collapse" data-toggle="tooltip" title="" class="pull-right">
                         <em id="licenceInfo" class="fa fa-minus"></em>
                     </a>
                 </div>
                 <div id="panelDiv" class="panel-wrapper collapse in">
                     <div class="panel-body">
                         <div class="panel panel-default col-md-8">
                             <div class="panel-body">
                                 <input type="hidden" id="licenceId" value="${operationLicence.id}">
                                 <table class="table col-md-12">
                                     <tr>
                                         <td>许可证编号</td>
                                         <td>${operationLicence.licence_no}</td>
                                     </tr>
                                     <tr>
                                         <td>发证机关</td>
                                         <td>${operationLicence.licence_org}</td>
                                     </tr>
                                     <tr>
                                         <td>发证日期</td>
                                         <td>${operationLicence.licence_date}</td>
                                     </tr>
                                     <tr>
                                         <td>初次发证日期</td>
                                         <td>${operationLicence.licence_date}</td>
                                     </tr>
                                     <tr>
                                         <td>许可证有效期</td>
                                         <td>${operationLicence.validityPeriod}</td>
                                     </tr>
                                     <tr>
                                         <td>经营单位</td>
                                         <td>${operationLicence.enterpriseName}</td>
                                     </tr>
                                     <tr>
                                         <td>法定代表人</td>
                                         <td>${operationLicence.corporate}</td>
                                     </tr>
                                     <tr>
                                         <td>注册地址</td>
                                         <td>${operationLicence.register_addr}</td>
                                     </tr>
                                     <tr>
                                         <td>经营设施地址</td>
                                         <td>${operationLicence.machine_addr}</td>
                                     </tr>
                                     <tr>
                                         <td>申请日期</td>
                                         <td>${operationLicence.application_time}</td>
                                     </tr>
                                     <tr>
                                         <td>核准经营方式</td>
                                         <td>${operationLicence.operationMode}</td>
                                     </tr>
                                 </table>
                             </div>
                         </div>
                         <div class="panel panel-default col-md-4">
                              <div>
                                    <label >许可证附件</label>
                                </div>
                                <div id="licenceImgOne">
                                    <img src=""  style="max-width:80%;max-height:30%;">
                                </div>
                         </div>
                     </div>
                 </div>
             </div>
             <div class="panel panel-default">
                 <div class="panel-heading">
                      <span>许可证危废信息</span>
                      <span class="marginLeft">${operationLicence.licence_no}</span>
                      <span class="marginLeft">许可证有效期：${operationLicence.validityPeriod}</span>
                     <a href="javascript:;" data-tool="panel-collapse" data-toggle="tooltip" title="" class="pull-right">
                         <em id="licenceWasteInfo" class="fa fa-minus"></em>
                     </a>
                 </div>
                 <div class="panel-wrapper collapse in">
                     <div class="panel-body">
                         <c:forEach var="it" items="${listItemVo}" varStatus="status" begin="0" step="1">
                             <div class="panel panel-default">
                                 <div role='tab' class="panel-heading item-heading"><span class="headText"></span>
                                     <a href="javascript:;" data-tool="panel-collapse" data-toggle="tooltip" title="" class="pull-right">
                                         <em class="fa fa-minus"></em>
                                     </a>
                                 </div>
                                 <div role='tabpanel' class="panel-collapse collapse in">
	                                 <div class="col col-sm">
	                                     <div class="ph">
	                                        <img src="<%=appPath %>/app/img/business_icon/disposition.png" alt="" class="media-box-object img-responsive img-rounded thumb64">
	                                     </div>
	                                 </div>
	                                 <div class="col col-md">
	                                     <div>
	                                         <strong class="dispositionType">${it.dispositionType}</strong>
	                                     </div>
	                                     <div>
	                                         <strong>许可总数量：${it.approved_quantity} 吨/年</strong>
	                                     </div>
	                                     <div>
	                                         <c:if test="${!empty it.itemRemark}">
	                                             <strong>备注：${it.itemRemark}</strong>
	                                         </c:if>
	                                     </div>
	                                 </div>
	                                 <div class="col col-xxl">
	                                     <c:forEach var="wt" items="${it.listWasteTypeVo}" varStatus="status" begin="0" step="1">
	                                         <div>
	                                             <strong>${wt.wasteTypeCode}</strong>
	                                             <small class="text-muted">&nbsp;${wt.wasteTypeValue}</small>
	                                         </div>
	                                         <div>
	                                             <small>包含以下八位码</small>
                                                 <div class="wasteCode">${wt.listWasteVo}</div>
	                                         </div>
	                                     </c:forEach>
	                                 </div>
	                                 <%--<div class="col col-md">--%>
	                                     <%--<div class="ph text-center">--%>
	                                         <%--<canvas data-classyloader="" data-height="80px" data-diameter="30" data-font-size="25px" --%>
	                                                 <%--data-percentage="${it.surplus_percentage}" data-speed="30" data-line-color="#23b7e5" --%>
	                                                 <%--data-remaining-line-color="#edf2f6" data-line-width="2">--%>
	                                         <%--</canvas>--%>
	                                     <%--</div>--%>
	                                     <%--<div class="text-center">--%>
	                                         <%--<strong class="text-muted">剩余量 ：${it.surplus_quantity}吨</strong>--%>
	                                     <%--</div>--%>
	                                 <%--</div>--%>
                                 </div>
                             </div>
                         </c:forEach>
                     </div>
                 </div>
             </div>
         </div>
      </div>
   </div>
</section>
      
<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script src="<%=appPath %>/thirdparty/jquery-classyloader/js/jquery.classyloader.min.js"></script>
<script src="<%=appPath %>/app/js/classyloader.js"></script>
<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
var msg = "${msg}";// 保存按钮点击后，回显的信息
var status = "${status}";// 保存按钮点击后，回显的状态
var auditStatusCode = "${operationLicence.auditStatusCode}";// 许可证是否已经通过审核
var error = true;
$(document).ready( function(){
    // 页面信息提示框弹出
    if (msg != "") {
      var options={"status":status};
      $.notify(msg,options);
    }
    if ("PASS" == auditStatusCode) {
        $("#approvedPass").attr("disabled","disabled");
        $("#approvedReject").attr("disabled","disabled");
    }
    
    // 许可证基本信息点击事件
    $(".fa").click(function() {
        var em= $(this);
        var headDiv= em.parent().parent();
        var headText= em.parent().parent().find("[class='headText']");
        var panelDiv= em.parent().parent().next();
        if (em.hasClass('fa-plus')) {
             if (!panelDiv.hasClass('in')) {
                 panelDiv.addClass('in');
                 em.removeClass('fa-plus').addClass('fa-minus');
             }
             
             if (headDiv.hasClass('item-heading')) {
                 headText.text("");
             }
        } else if (em.hasClass('fa-minus')) {
            if (panelDiv.hasClass('in')) {
                panelDiv.removeClass('in');
                em.removeClass('fa-minus').addClass('fa-plus');
            }
            
            if (headDiv.hasClass('item-heading')) {
                var headtext = panelDiv.find("[class='dispositionType']").html();
                headText.text(headtext);
            } 
        }
    });
    
    //显示图片
    var licenceId=$('#licenceId').val();
    $.ajax({
        url: "<%=appPath %>/fileUpload/getFileByBusinessCode",
        data: {
            'businessCode' :'d'+licenceId,
            'ticketId':"<%=ticketId %>"
        },
        type: "POST",
        dataType: 'json',
        async: false,
        success: function(data) {
            if(data.status==1){
                var uploadFileInfo=data.data.uploadFileInfo;
                if(uploadFileInfo){
                    $("#licenceImgOne img").attr('src',IMG_VIEW_URL+'&businessCode='+uploadFileInfo.businessCode+'&fileID='+uploadFileInfo.fileId);
                }else{
                    $('#licenceImgOne').html('暂无数据');
                }
            }else{
                $.notify("获取图片信息失败",{status:'danger'});
            }
        },
        error: function(er) {
            $.notify("获取图片信息失败",{status:'danger'});
        }
    });
    
});

//返回按钮点击
function back(){
    var paramObj = {};
    var pageUrl = "/licenceApproved/list.htm";
    $.page.gotoTargetLocation(pageUrl, paramObj);
}

//审核通过经营许可证
function approvedPass(that){
    var berforeText = $(that).text();
    $(that).before("<i class='btn_loading'></i>").text("审核中...").parent().find("button").attr("disabled","disabled");
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/licenceApproved/confirmPass.htm",
        data: {
            "id":$("#licenceId").val()
        },
        success: function (result, textStatus, jqXHR) {
            var paramObj = {};
            var pageUrl = "/licenceApproved/list.htm";
            $.page.gotoTargetLocation(pageUrl, paramObj);
        },
        complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                $(that).prev(".btn_loading").remove();
            }
        }
    }));
}

//审核退回经营许可证
function approvedReject(){
    var berforeText = $(that).text();
    $(that).before("<i class='btn_loading'></i>").text("审核中...").parent().find("button").attr("disabled","disabled");
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/licenceApproved/confirmReject.htm",
        data: {
            "id":$("#licenceId").val()
        },
        success: function (result, textStatus, jqXHR) {
            var paramObj = {};
            var pageUrl = "/licenceApproved/list.htm";
            $.page.gotoTargetLocation(pageUrl, paramObj);
        },
        complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                $(that).prev(".btn_loading").remove();
            }
        }
    }));
}
</script>