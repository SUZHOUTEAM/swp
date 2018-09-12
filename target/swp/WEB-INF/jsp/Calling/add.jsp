<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="行业管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#callingItem"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath %>/thirdparty/silviomoreto-bootstrap-select/dist/css/bootstrap-select.css">
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
            <span class="el-breadcrumb__item__inner">基础数据管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner"  onclick="history.go(-1);">行业管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">行业新增</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
   <!-- Page content-->
   <div>
      <div class="row">
          <%------- 导入按钮工具栏 -------%>
          <div class="col-md-12">
          <div class="panel panel-default">
              <div class="panel-heading">行业新增页面</div>
              <div class="panel-body">
                  <form:form class="form-horizontal" modelAttribute="calling" id="editForm" data-parsley-validate="true">
                      <form:hidden id="id" path="id"/>
                      <div class="form-group">
                          <label class="col-md-2 control-label text-left">上级行业</label>
                          <div class="col-md-10">
                            <form:select id="parentId" name="parentId" path="parent_id" class="selectpicker form-control" data-live-search="true">
                                <form:option value="-1">无上级行业</form:option>
                                <c:forEach var="itroot" items="${rootCallingList}" varStatus="status" begin="0" step="1">
                                    <optgroup label="${itroot.name}">
                                        <form:option value="${itroot.id}">${itroot.name}</form:option>
                                        <c:forEach var="it" items="${subCallingList}" varStatus="status" begin="0" step="1">
                                            <c:if test="${itroot.id == it.rootid}">
                                                <form:option value="${it.id}">${it.name}</form:option>
                                            </c:if>
                                        </c:forEach>
                                    </optgroup>
                                </c:forEach>
                            </form:select>
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-2 control-label">行业代码*</label>
                          <div class="col-md-10">
                               <form:input type="text" path="code" placeholder="输入行业代码,4位以内的字母或者数字" class="form-control" maxlength="4" 
                                           data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入行业代码"  
                                           data-parsley-pattern="^[A-Z0-9]+$" data-parsley-pattern-message="输入行业代码的格式不正确"
                                           data-parsley-checkcode="true" data-parsley-checkcode-message=""/>
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-2 control-label">行业名称*</label>
                          <div class="col-md-10">
                              <form:input type="text" path="name" placeholder="输入行业名称,20位字符以内" class="form-control" maxlength="20"
                                          data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入行业名称" />
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-2 control-label">行业说明</label>
                          <div class="col-md-10">
                              <form:textarea path="description" placeholder="输入行业说明,200位字符以内"  class="form-control" rows="5"
                                             data-trigger="keyup" data-parsley-error-message="说明文字只能在200字符以内"  data-parsley-maxlength="200" />
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="col-md-offset-2 col-md-10">
                               <label class="control-label">(*)&nbsp;&nbsp;必须输入</label>
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="col-md-offset-2 col-md-10">
                               <button type="button" id="saveExit" class="btn btn-primary" onclick="sub(this,'1')">保存并结束</button>
                               <button type="button" id="saveAdd" class="btn btn-default" onclick="sub(this,'2')" >保存并继续追加</button>
                          </div>
                      </div>
                  </form:form>
              </div>
          </div>
          </div>
      </div>
   </div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script src="<%=appPath %>/thirdparty/silviomoreto-bootstrap-select/dist/js/bootstrap-select.js"></script>
<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
var msg = "";// 保存按钮点击后，回显的信息
var status = "";// 保存按钮点击后，回显的状态
var error = true;
$(document).ready(function(){
    // 行业代码重复性验证
    window.Parsley.addValidator('checkcode', function(value){
     	return error;
    }, 32);
	 
    $("#code").focus(function(){
	    var optionSelect = $('#parentId option:selected').val();
	    if (optionSelect == "-1") {
	        $("#code").attr("placeholder","输入行业代码,1位以内的大写字母").attr("maxlength","1").attr("data-parsley-pattern","^[A-Z]$");
	        $("#code").parsley().validate();
	    } else {
	        $("#code").attr("placeholder","输入行业代码,4位以内的字母或者数字").attr("maxlength","4").attr("data-parsley-pattern","^[A-Z0-9]+$");
	        $("#code").parsley().validate(); 
	    }
    });
	
	// 行业代码绑定事件，
    $("#code").blur(function() {
    	validateByCode();
        $("#code").parsley().validate();
        validateButton();
    }); 
    $("#name").blur(function() {
        $("#name").parsley().validate();
        validateButton();
     }); 
    
    // 初始情况下，按钮不可用
    $("#saveExit").attr("disabled","disabled");
    $("#saveAdd").attr("disabled","disabled");
});

//返回按钮点击
function back(){
    location="<%=appPath%>/calling/list.htm?ticketId=<%=ticketId %>";
}
//输入代码是否重复
function validateByCode(){
	$.ajax({
		  async: false,
		  url: "isCallingCodeExistent.htm?ticketId=<%=ticketId %>&etc="+new Date().getTime(),
		  data: $('form').serialize(),
		  success: function(result){
		        var obj = $.parseJSON(result);
		        if(obj.status == 0){
		            error = false;
		            $("#code").attr("data-parsley-checkCode-message",obj.data.msg);
		        }  else {
		            error = true;
		            $("#code").attr("data-parsley-checkCode-message","");
		        }
		  } 
	});
};

// 保存按钮点击
function sub(that, model){
    if(validate()){
        var berforeText = $(that).text();
        $(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
        $.post('saveCalling.htm?ticketId=<%=ticketId %>&etc='+new Date().getTime(),$('form').serialize(),function(result){
        	var obj = $.parseJSON(result);
        	msg = obj.data.msg;
        	status = obj.data.status;
	       	if(obj.status == 1){
	       	    var options={"status":"success"};
	       	    $.notify(msg,options);
	       	    toUrl(model);
	        }else{
                var options={"status":"danger"};
                $.notify(msg,options);
                $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                $(that).prev(".btn_loading").remove();
	        }
        });
    }
}
//form表单验证
function validate(){
    var form = $("#editForm").parsley();
    form.validate();
    return form.isValid();
}

//保存按钮可用设置
function validateButton(){
    if ($("#editForm").parsley().isValid()){
        $("#saveExit").removeAttr("disabled");
        $("#saveAdd").removeAttr("disabled");
    } else {
        $("#saveExit").attr("disabled","disabled");
        $("#saveAdd").attr("disabled","disabled");
    }
}

//页面信息提示框弹出
function toUrl(model){
    if (model == '1') {
        location="<%=appPath%>/calling/list.htm?ticketId=<%=ticketId %>";  
    } else {
        location="<%=appPath%>/calling/add.htm?ticketId=<%=ticketId %>"; 
    }
}
</script>