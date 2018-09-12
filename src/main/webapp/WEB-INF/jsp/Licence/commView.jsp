<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
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
      <jsp:param name="title" value="许可证详情"/>
    </jsp:include>
    <jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
        <jsp:param name="menuId" value="#licenceManage" />
    </jsp:include>
</c:if>
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
            <span class="el-breadcrumb__item__inner" onclick="history.go(-1);">许可证列表</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">许可证详情</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <!-- Page content-->
    <div class="panel panel-body">
        <div class="row">
            <div class="col-md-12">         
                <div class="panel panel-default">
                    <div class="panel-heading">许可证基本信息
                        <a href="javascript:;" data-tool="panel-collapse" data-toggle="tooltip" title="" class="pull-right">
                            <em id="licenceInfo" class="fa fa-plus"></em>
                        </a>
                    </div>
                    <div class="panel-collapse collapse">
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
                                       <%--  <tr>
                                            <td>申请日期</td>
                                            <td>${operationLicence.application_time}</td>
                                        </tr> --%>
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
                                <div id="licenceImg">
                                	<img src="">
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
                    <div class="panel-collapse collapse in">
                        <div class="panel-body">
                            <c:forEach var="it" items="${listItemVo}" varStatus="status" begin="0" step="1">
                                <div class="panel panel-default">
                                    <div class="panel-heading item-heading"><span class="headText"></span>
                                        <a href="javascript:;" data-tool="panel-collapse" data-toggle="tooltip" title="" class="pull-right">
                                            <em class="fa fa-minus"></em>
                                        </a>
                                    </div>
                                    <div class="panel-collapse collapse in">
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
<jsp:include page="/common/webCommon/bottom.jsp" flush="true"/>
<%------- 结束导入底部信息 -------%>
<script src="<%=appPath %>/thirdparty/jquery-classyloader/js/jquery.classyloader.min.js"></script>
<script src="<%=appPath %>/app/js/classyloader.js"></script>
<!-- notify-->
<script src="<%=appPath %>/main/common/main.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script type="text/javascript">
var ticketId="<%=ticketId %>";
$(document).ready( function(){
  var rs = window.globalInit();
  rs.done(function () {
    init();
  });
});
function init() {
  var licenceId=$('#licenceId').val();
  $.ajax({
    url: "<%=appPath %>/fileUpload/getFileByBusinessCode",
    data: {
      'businessCode' :'d'+licenceId,
      'ticketId':ticketId
    },
    type: "POST",
    dataType: 'json',
    async: false,
    success: function(data) {
      if(data.status==1){
        //$.notify("修改成功",{status:'success'});
        var uploadFileInfo=data.data.uploadFileInfo;
        if(uploadFileInfo){
          $("#licenceImg img").attr('src',IMG_VIEW_URL+'&businessCode='+uploadFileInfo.businessCode+'&fileID='+uploadFileInfo.fileId);
        }else{
          $('#licenceImg').html('暂无数据');
        }
      }else{
        $.notify("获取图片信息失败",{status:'danger'});
      }
    },
    error: function(er) {
      $.notify("获取图片信息失败",{status:'danger'});
    }
  });

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
}
</script>