<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@page import="com.mlsc.waste.utils.LoginStatusUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
    request.setAttribute("loginEnterpriseId",LoginStatusUtils.getUserByTicketId(ticketId).getEnterpriseId());
%>
<%------- 导入页头信息 -------%>
<c:if test="${empty noTopLeftButtom}">
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
</c:if>
<%------- 结束导入左侧信息 -------%>

<!-- Main section-->
<section id="licenceDetail">
<!-- Page content-->
    <div class="content-wrapper">
        <c:if test="${empty noTopLeftButtom}">
	        <div class="content-heading">
	            <strong class="lead">许可证管理</strong>
	            <small>许可证信息</small>
	        </div>
        </c:if>
        <div class="row">
            <div class="col-md-12  text-center" >
                <c:if test="${promptType == '1'}">
                    <div>
                        <span>企业信息尚未完善</span>
                    </div>
                </c:if>
                <c:if test="${promptType != '1'}">
                	<c:if test="${enterpriseId != loginEnterpriseId && not isSysUser}">
                		<span>无有效许可证</span>
                	</c:if>
                	<c:if test="${enterpriseId == loginEnterpriseId || isSysUser}">
                		<div>
	                    	<span>尚无有效许可证，</span><a href="<%=appPath %>/licence/licenceSteps.htm?ticketId=<%=ticketId %>&enterpriseId=${enterpriseId}">请新增一个许可证</a>
	                	</div> 
	                	<c:if test="${usedLicence == '1'}">
		                	<div>
		                    	<span>或</span><a href="<%=appPath %>/licence/list.htm?ticketId=<%=ticketId %>&enterpriseId=${enterpriseId}">查看无效许可证</a>
		                	</div>
	                	</c:if>
                	</c:if>
                </c:if>
            </div>
        </div>
    </div>
</section>
<%------- 导入底部信息 -------%>
<c:if test="${empty noTopLeftButtom}">
    <jsp:include page="/common/webCommon/bottom.jsp" flush="true"/>
</c:if>
<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
$(document).ready( function(){
  var rs = window.globalInit();
  rs.done(function () {
    if ("${empty noTopLeftButtom}" == "false") {
      $("#licenceDetail").find(".tool-div").addClass("hidden");
      $("#licenceDetail").find(".content-heading").addClass("hidden");
    }
  });

});
</script>