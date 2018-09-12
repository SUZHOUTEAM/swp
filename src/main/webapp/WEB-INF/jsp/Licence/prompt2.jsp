<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
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
<section>
<!-- Page content-->
    <div class="content-wrapper">
        <div class="row">
            <div class="col-md-12  text-center" >
                <div>
                    <span class="lead">您拥有多个有效许可证为异常情况，请及时联系所属地环保局进行处理!</span><br>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
  var rs = window.globalInit();
</script>
<%------- 导入底部信息 -------%>
<c:if test="${empty noTopLeftButtom}">
    <jsp:include page="/common/webCommon/bottom.jsp" flush="true"/>
</c:if>
<%------- 结束导入底部信息 -------%>