<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="系统平台"/>
</jsp:include>

<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#removeUserEnt"/>
</jsp:include>
<%------- 结束导入左侧信息 -------%>
<style>
  .form-horizontal .control-label {
    text-align: left;
    margin-bottom: 0;
    padding-top: 7px;
    font-weight:normal;
  }
</style>

<!-- Main section-->
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">权限管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">删除管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
   <!-- Page content-->
   <div>
      <div class="row">
          <%------- 导入按钮工具栏 -------%>
          <div class="col-md-12">
	          <div class="panel panel-default">
	              <div class="panel-body">
	                  <div class="form-group">
	                      <div class="col-md-10">
	                           <input type="text" placeholder="输入手机号码退出企业" class="form-control phoneNum" maxlength="11"/>
	                      </div>
	                      <div class="col-md-2">
                               <button type="button" class="btn btn-sm btn-primary" onclick="removeEntByPhoneNum(this)">退出企业</button>
                          </div>
	                  </div>
	                  <br/><br/>
	                  <!-- <div class="form-group">
	                      <div class="col-md-10">
	                           <input type="text" placeholder="输入企业ID删除企业" class="form-control phoneNum" maxlength="11"/>
	                      </div>
	                      <div class="col-md-2">
                               <button type="button" class="btn btn-sm btn-primary" onclick="removeEntById(this)">删除企业</button>
                          </div>
	                  </div> -->
	                  <br/><br/>
	                  <div class="form-group">
	                      <div class="col-md-10">
	                           <input type="text" placeholder="输入手机号码删除用户" class="form-control phoneNum" maxlength="11"/>
	                      </div>
	                      <div class="col-md-2">
                               <button type="button" class="btn btn-sm btn-primary" onclick="removeUser(this)">删除用户</button>
                          </div>
	                  </div>
	              </div>
	          </div>
          </div>
      </div>
   </div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
var msg = "";// 保存按钮点击后，回显的信息
var status = "";// 保存按钮点击后，回显的状态
$(document).ready(function(){
    
});

function removeEntByPhoneNum(that){
	$(that).attr("disabled","disabled").text("退出中...");
	var phoneNum = $(that).parent().parent().find(".phoneNum").val();
	$.ajax({
        async: false,
        url: "removeEntByPhoneNum.htm?ticketId=<%=ticketId %>",
        data: {
            "phoneNum":phoneNum
        },
        success: function(result){
	        var obj = $.parseJSON(result);
	        if(obj.status == 0){
	            alert(obj.data.msg);
	        }  else {
	            alert("退出成功！");
	        }
	        $(that).removeAttr("disabled").text("退出企业");
        } 
    });
}

function removeUser(that){
	$(that).attr("disabled","disabled").text("删除中...");
	var phoneNum = $(that).parent().parent().find(".phoneNum").val();
	$.ajax({
        async: false,
        url: "removeUser.htm?ticketId=<%=ticketId %>",
        data: {
            "phoneNum":phoneNum
        },
        success: function(result){
	        var obj = $.parseJSON(result);
	        if(obj.status == 0){
	            alert(obj.data.msg);
	        }  else {
	            alert("删除成功！");
	        }
	        
	        $(that).removeAttr("disabled").text("删除用户");
        } 
    });
}

</script>
