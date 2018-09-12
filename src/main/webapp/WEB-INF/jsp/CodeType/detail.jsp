<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="常用字典管理" />
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#codeTypeItem"/>
</jsp:include>
<script type="text/javascript">
    function back(t){
        location = "<%=request.getContextPath()%>/codeType/list.htm?ticketId=<%=ticketId %>";
    }
</script>
<section>
	<div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">基础数据管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
		<span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner"  onclick="history.go(-1);">常用数据管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
		<span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">常用数据详情</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
	</div>
    <div>
        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">常用字典数据查看页面</div>
                    <div class="panel-body">
	                    <div class="form-group">
	                        <div class="col-lg-offset-1 col-lg-7">
	                            <label>字典类型</label>&nbsp;&nbsp;<span style="font-size: 24px; color: blue">${typeName}</span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <div class="col-lg-offset-1 col-lg-7">
	                            <label >字典信息</label>
	                        </div>
	                     </div>
	                    <div class="form-group">
	                        <div class="col-lg-offset-1 col-lg-8 table-responsive">
	                            <table class="table" id="infoTable">
	                                <thead>
	                                    <tr>
	                                        <th>信息编码</th>
	                                        <th>信息名称</th>
	                                    </tr>
	                                </thead>
	                                <tbody id="infoTbody" >
	                                    <c:forEach var="it" items="${codeValueList}" varStatus="status" begin="0" step="1">
	                                        <tr>
	                                            <td align="left">${it.code}</td>
	                                            <td align="left">${it.value}</td>
	                                        </tr>
	                                    </c:forEach>
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="/common/bottom.jsp" flush="true" />