<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="常用字典管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#codeTypeItem"/>
</jsp:include>
<style>
  .form-horizontal .control-label {
    text-align: left;
    margin-bottom: 0;
    padding-top: 7px;
    font-weight:normal;
  }
  
  td{
     padding-left:0 !important;
  }
</style>

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
            <span class="el-breadcrumb__item__inner">常用数据新增</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
	</div>
   <div>
      <div class="row">
          <div class="col-md-6">
	          <div class="panel panel-default">
	              <div class="panel-heading">新增常用字典数据</div>
	              <div class="panel-body">
	                  <form:form class="form-horizontal" modelAttribute="codeType" id="editForm" data-parsley-validate="true">
	                      <form:hidden id="id" path="id"/>
	                      <div class="form-group">
	                          <label class="col-md-2 control-label">字典类型</label>
	                      </div>
	                      <div class="form-group">
	                          <label class="col-md-2 control-label">信息类型代码</label>
	                          <div class="col-md-5">
	                              <form:input type="text" path="type_code" placeholder="输入信息类型代码" class="form-control" maxlength="20" 
	                                           data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入信息类型代码"
	                                           data-parsley-pattern="^[A-Za-z0-9_]+$" data-parsley-pattern-message="请信息类型代码的格式不正确"  
	                                           data-parsley-checkcode="true" data-parsley-checkcode-message=""/>
	                          </div>
	                      </div>
	                      <div class="form-group">
	                          <label class="col-md-2 control-label">信息类型名称</label>
	                          <div class="col-md-5">
	                              <form:input type="text" path="type_name" placeholder="输入信息类型名称" class="form-control" maxlength="50" 
	                                           data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入信息类型名称" />
	                          </div>
	                      </div>
	                      <div class="form-group">
	                           <div class="col-sm-offset-2 col-md-7">
	                                 <div class="checkbox c-checkbox">
	                                    <label>
	                                       <input type="checkbox" checked="checked" value="1" id="splice" name="splice" onClick="selected(this)"/>
	                                       <span class="fa fa-check"></span>字典信息是否含有编码
	                                    </label>
	                                 </div>
	                           </div>
	                      </div>
	                      <div class="form-group">
	                           <label class="col-md-2 control-label">字典信息</label>
	                      </div>
	                      <div class="form-group">
	                          <div class="col-sm-offset-2 col-md-8 table-responsive">
	                              <table class="table" >
	                                  <thead>
	                                     <tr>
	                                        <th>信息编码</th>
	                                        <th>信息名称</th>
	                                        <th></th>
	                                     </tr>
	                                  </thead>
	                                  <tbody id="infoTable" >
	                                  </tbody>
	                              </table>
	                          </div>
	                      </div>
	                      <div class="form-group">
	                          <div class="col-sm-offset-2 col-md-8">
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
<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
var codeCheck = true;//输入代码是否重复
var msg = "";// 保存按钮点击后，回显的信息
var status = "";// 保存按钮点击后，回显的状态
var error = true;

var addButton = "<a href='javascript:;' data-toggle='tooltip' title='新增一条信息' onclick='addRow(this)'>"
               +"<em class='fa fa-plus'>&nbsp;&nbsp;&nbsp;</em></a> ";
var deleteButton = "<a href='javascript:;' data-toggle='tooltip' title='删除这条信息' onclick='deleteRow(this)'>"
               +"<em class='fa fa-minus'></em></a>";
$(document).ready(function(){
    var tbody=$("#infoTable");
    // 初始化页面
    for (var i = 0;i < 4; i++) {
       tbody.append(creatDeleteRow());
    }
    tbody.append(creatAddRow());
    
    // 信息类型代码重复性验证
    window.Parsley.addValidator('checkcode', function(value){
        return error;
    }, 32); 
    
    // 信息类型代码重复性验证
    window.Parsley.addValidator('infocode', function(value){
        return error;
    }, 32); 
    
    // 信息类型代码绑定事件，
    $("#type_code").blur(function() {
    	validateByCode();
        $("#type_code").parsley().validate();
        validateButton();
    }); 
    
    // 信息类型名称绑定事件，
    $("#type_name").blur(function() {
        $("#type_name").parsley().validate();
        validateButton();
    }); 
    
    // 初始情况下，按钮不可用
    $("#saveExit").attr("disabled","disabled");
    $("#saveAdd").attr("disabled","disabled");
});

//信息编码绑定离焦事件，
function codeblur(t){
    validateByInfoCodeRequired(t);
    validateByInfoCode(t);
    $(t).parsley().validate();
    $(t).parent().siblings().find("[name='value']").parsley().validate();
    validateButton();
}

//信息名称绑定离焦事件
function valueblur(t){
    validateByInfoValueRequired(t);
    $(t).parsley().validate();
    $(t).parent().siblings().find("[name='code']").parsley().validate();
    validateButton();
}

//字典信息是否含有编码绑定事件
function selected(t){
    var ck= $("#splice");
    var codes = $("[name='code']");
    if (ck.is(':checked')) {
        codes.removeAttr("disabled");
        codes.attr("placeholder","输入信息代码");
    }else{
        codes.val("");
        codes.attr("disabled","disabled");
        codes.removeAttr("placeholder");
    }
}

//新增一行
function addRow(t){
	var ccolm = $(".fa-plus").parent().parent();
	// 检查当前行有没有删除号
    if (ccolm.find("[class='fa fa-minus']").parent().length == 0) {
    	ccolm.append(deleteButton);
    }
	 $(".fa-plus").remove();
	 var tbody=$("#infoTable");
	 tbody.append(creatAddRow());
	 
    if (!$("#splice").is(':checked')) {
    	// 字典信息是否含有编码未选中时，新增一行代码编辑不可
    	var codeColm = $("[class='fa fa-plus']").parent().parent().parent().find("[name='code']");
    	codeColm.removeAttr("placeholder");
    	codeColm.attr("disabled","disabled");
    }
}

//删除一行
function deleteRow(t){
	var crow = $(t).parent().parent();
	// 正在删除最后一行
	if (crow.find("[class='fa fa-plus']").parent().length>0) {
		var tdbuttonTool = crow.prev().find("[class='buttonTool']");
		tdbuttonTool.html("");
		tdbuttonTool.append(addButton);
		tdbuttonTool.append(deleteButton);
	}
	crow.remove();
	// 表单中只有一行的时候，不能被删掉
    var rowCount = $(".fa-minus").length;
	if (rowCount == 1) {
		$(".buttonTool").find("[class='fa fa-minus']").parent().remove();
	}
}

//创建一个行
function creatRow(){
    var tbodeyCode="<tr>";
    tbodeyCode += "<td>";
    tbodeyCode += "<input type='text' name='code' placeholder='输入信息代码' class='form-control' maxlength='20' data-trigger='change' ";
    tbodeyCode += "data-parsley-pattern='^[A-Za-z0-9_]+$' data-parsley-pattern-message='输入信息代码的格式不正确' onblur='codeblur(this)'>";
    tbodeyCode += "</td>";
    tbodeyCode += "<td>";
    tbodeyCode += "<input type='text' name='value' placeholder='输入信息名称' class='form-control' maxlength='50' data-trigger='change' onblur='valueblur(this)'>";
    tbodeyCode += "</td>";
    
    return tbodeyCode;
}

//创建一个带追加符号的行
function creatAddRow(){
    var trCode = creatRow();
    trCode += "<td class='buttonTool'>";
    trCode += addButton;
    trCode += deleteButton;
    trCode += "</td>";
    trCode += "</tr>";
    return trCode;
}

//创建一个只有删除符号的行
function creatDeleteRow(){
    var trCode = creatRow();
    trCode += "<td class='buttonTool'>";
    trCode += deleteButton;
    trCode += "</td>";
    trCode += "</tr>";
    return trCode;
}

//保存按钮点击
function sub(that, model){
    var berforeText = $(that).text();
    $(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/codeType/saveCodeTypeAndValue.htm",
        data: $('form').serialize(),
        success: function (result, textStatus, jqXHR) {
            toUrl(model);
        },complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                $(that).prev(".btn_loading").remove();
            }
        }
    }));
}

//返回按钮点击
function back(){
    var pageUrl = "/codeType/list.htm";
    $.page.gotoTargetLocation(pageUrl);
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

//输入代码是否重复
function validateByCode(){
    error = true;
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/codeType/isTypeCodeExistent.htm",
        data: {
            "id":$("#id").val(),
            "type_code":$("#type_code").val()
        },
        success: function (result, textStatus, jqXHR) {
            if(result.data.checkResult){
                error = false;
                $("#type_code").attr("data-parsley-checkCode-message","输入的字典类型代码" + $("#type_code").val() + "已存在，请重新填写！");
            }
        }
    }));
};

// 信息编码必须输入检查
function validateByInfoCodeRequired(t){
    var ck= $("#splice");
    var msg = null;
    var valueobj = $(t).parent().siblings().find("[name='value']");
    if (ck.is(':checked')) {
    	if (valueobj.val() != "" && $(t).val() == "") {
            error = false;
            msg = "请输入信息编码";
            $(t).attr("data-parsley-required","required");
            $(t).attr("data-parsley-required-message",msg);
    	} else {
   	        error = true;
   	        $(t).removeAttr("data-parsley-required");
    	}
    } else {
    	error = true;
    	$(t).removeAttr("data-parsley-required");
    }
};

//信息名称必须输入检查
function validateByInfoValueRequired(t){
    var ck= $("#splice");
    var msg = null;
    var codeobj = $(t).parent().siblings().find("[name='code']");
    if (ck.is(':checked')) {
        if (codeobj.val() != "" && $(t).val() == "") {
            error = false;
            msg = "请输入信息名称";
            $(t).attr("data-parsley-required","required");
            $(t).attr("data-parsley-required-message",msg);
        } else {
            error = true;
            $(t).removeAttr("data-parsley-required");
        }
    } else {
        error = true;
        $(t).removeAttr("data-parsley-required");
    }
};

//输入代码是否重复
function validateByInfoCode(t){
	var count = 0;
	var codes = $("[name='code']");
	codes.each(function(item) {
		 if ($(t).val() != "" && $(t).val() == $(this).val()) {
			 count++;
		 }
	});
	
	if (count >= 2) {
		error = false;
		$(t).attr("data-parsley-infocode",true);
		$(t).attr("data-parsley-infocode-message","当前信息编码已经输入了，请重新输入！");
	} else {
		error = true;
	}
};

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
    var paramObj = {};
    var pageUrl = "/codeType/add.htm";
    if (model == '1') {
        pageUrl = "/codeType/list.htm";  
    }
    $.page.gotoTargetLocation(pageUrl, paramObj);
}
</script>
