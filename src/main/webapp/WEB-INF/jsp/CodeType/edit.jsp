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

<!-- Main section-->
<section>
   <!-- Page content-->
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
            <span class="el-breadcrumb__item__inner">常用数据编辑</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
   <div>
      <div class="row">
          <div class="col-md-12">
          <div class="panel panel-default">
              <div class="panel-heading">编辑常用字典数据</div>
              <div class="panel-body">
                  <form:form class="form-horizontal" modelAttribute="codeType" id="editForm" data-parsley-validate="true">
                      <form:hidden id="id" path="id"/>
                      <div class="form-group">
                          <label class="col-md-2 control-label">字典类型</label>
                          <div class="col-md-5" id="divCodeInfo">
                              <form:input type="text" path="type_name" class="form-control" disabled="true"/>
                          </div>
                          <div class="col-md-5" id="divCodeInfo">
                              <button type="button" id="saveTypeInfo" class="btn btn-sm btn-primary" onclick="editTypeInfo()">编辑</button>
                              <button type="button" id="cancelBtn" class="btn btn-sm btn-primary" onclick="cancel()">取消</button>
                          </div>
                      </div>
                      <div class="form-group" id="divTypeCode">
                          <label class="col-md-2 control-label">信息类型代码</label>
                          <div class="col-md-5">
                              <form:input type="text" path="type_code" placeholder="输入信息类型代码" class="form-control" maxlength="20" 
                                           data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入信息类型代码" 
                                           data-parsley-pattern="^[A-Za-z0-9_]+$" data-parsley-pattern-message="输入信息类型代码的格式不正确"  
                                           data-parsley-checkcode="true" data-parsley-checkcode-message=""/>
                          </div>
                      </div>
                      <div class="form-group"  id="divTypeName">
                          <label class="col-md-2 control-label">信息类型名称</label>
                          <div class="col-md-5">
                              <form:input type="text" path="type_name" placeholder="输入信息类型名称" class="form-control typename" maxlength="50" 
                                           data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入信息类型名称" />
                          </div>
                      </div>
                      <hr>
                      <div class="form-group">
                           <div class="col-md-offset-2 col-md-7">
                                 <div class="checkbox c-checkbox">
                                    <label>
                                       <input type="checkbox" checked="checked" id="splice" name="splice" value="1" onClick="spliceSelected(this)"/>
                                       <span class="fa fa-check"></span>字典信息是否含有编码
                                    </label>
                                 </div>
                           </div>
                      </div>
                      <div class="form-group">
                           <label class="col-md-2 control-label">字典信息</label>
                           <div class="col-md-5">
                               <button type="button" id="addButton" class="btn btn-sm btn-primary" onclick="addRow()">增加</button>
                               <button type="button" id="deleteButton" class="btn btn-sm btn-primary" onclick="deleteRow(this)">删除</button>
                           </div>
                      </div>
                      <div class="form-group">
                          <div class="col-md-offset-2 col-md-8 table-responsive">
                              <table class="table table-condensed" id="infoTable">
                                  <thead>
                                     <tr>
                                        <th>选择</th>
                                        <th>信息编码</th>
                                        <th>信息名称</th>
                                     </tr>
                                  </thead>
                                  <tbody>
                                      <c:forEach var="it" items="${codeValues}" varStatus="status" begin="0" step="1">
                                          <tr>
                                              <td>
                                                  <label class="checkbox-inline c-checkbox">
                                                      <input type="checkbox" name="selectck" onClick="selected(this)" value="${it.id}"/>
                                                      <span class="fa fa-check"></span>
                                                  </label>
                                              </td>
                                              <td>
                                                  <label class="hidden">${it.code}</label><input type="text" name="code" class="form-control" value="${it.code}" disabled="disabled" data-parsley-pattern="^[A-Za-z0-9_]+$" 
                                                         data-parsley-pattern-message="输入信息代码的格式不正确" placeholder='输入信息代码' maxlength='20' onblur="codeblur(this)"/>
                                              </td>
                                              <td>
                                                  <label class="hidden">${it.value}</label><input type="text" name="value" class="form-control" placeholder='请输入信息名称' maxlength='50' value="${it.value}" disabled="disabled" onblur="valueblur(this)"/>
                                              </td>
                                          </tr>
                                      </c:forEach>
                                  </tbody>
                              </table>
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="col-md-offset-2 col-md-8">
                               <button type="button" id="saveExit" class="btn btn-primary" onclick="sub(this)">保存</button>
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
var checkboxButton = "<label  class='checkbox-inline c-checkbox'><input type='checkbox' checked='checked' name='selectck' onClick='selected(this)'/>"
                   + "<span class='fa fa-check'></span></label>";
var inputCode = "<label class='hidden'></label><input type='text' name='code' class='form-control' value='' placeholder='请输入信息代码' maxlength='20' ";
    inputCode += "data-parsley-pattern='^[A-Za-z0-9_]+$' data-parsley-pattern-message='输入信息代码的格式不正确' onblur='codeblur(this)'/>";
var inputValue = "<label class='hidden'></label><input type='text' name='value' class='form-control' value='' placeholder='请输入信息名称' maxlength='50' onblur='valueblur(this)'/>";
    
$(document).ready(function(){
    // 初始页面时信息类型代码，信息类型名称隐藏
    $("#divTypeCode").hide();
    $("#divTypeName").hide();
    
    // DataTable
    $('#infoTable').DataTable({
       // "searching": false,
        "ordering": false,
       // "sScrollY": "300px",
        "bPaginate": false,
        "bInfo": false,
        "bFilter":true,
        "responsive": true,
        "oLanguage":{
            "sZeroRecords": "没有检索到数据",
            "sSearch":"检索"
        }
    });
 
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
    $("#cancelBtn").hide();
});

//字典信息是否含有编码绑定事件
function spliceSelected(t){
    var ck= $("#splice");
    var codes = $("[name='code']");
    if (ck.is(':checked')) {
        codes.each(function(item) {
            var currentRow = $(this).parent().parent();
            // 当前行的checkbox
            var currentRowCheckbox = currentRow.find("[name='selectck']");
            // 当前行的id
            var currentRowId = currentRow.find("[name='selectck']").attr("value");
            // code的label值
            var codeHidden = $(this).parent().find(".hidden").text();
            $(this).val(codeHidden);
            
            if (currentRowCheckbox.is(':checked')) {
               $(this).removeAttr("disabled");
            }
            
            if (currentRowId == undefined) {
               $(this).attr("placeholder","输入信息代码");
            }
            
        });
    }else{
        codes.each(function(item) {
            var currentRow = $(this).parent().parent();
            // 当前行的id
            var currentRowId = currentRow.find("[name='selectck']").attr("value");
            $(this).val("");
            $(this).attr("disabled","disabled");
            if (currentRowId == undefined) {
                $(this).attr("placeholder","输入信息代码");
            }
        });
    }
}

//信息编码绑定离焦事件
function codeblur(t){
	validateByInfoCodeRequired(t);
    validateByInfoCode(t);
    $(t).parsley().validate();
    $(t).parent().siblings().find("[name='value']").parsley().validate();
    // label中保存 页面数据检索用
    if ($(t).parsley().isValid()) {
        getValueToLable(t, "code"); 
    }
    validateButton();
}

//信息名称绑定离焦事件
function valueblur(t){
	validateByInfoValueRequired(t);
    $(t).parsley().validate();
    $(t).parent().siblings().find("[name='code']").parsley().validate();
    // label中保存 页面数据检索用
    if ($(t).parsley().isValid()) {
        getValueToLable(t, "value"); 
    }
    validateButton();
}

//table中所有选择框绑定
function selected(t){
	// 当前行
	var currentRow = $(t).parent().parent().parent();
    if ($(t).is(':checked')) {
        // 当前行可编辑
        currentRow.find("[name='code']").removeAttr("disabled");
        currentRow.find("[name='value']").removeAttr("disabled");
    } else {
        // 当前行不可编辑
        currentRow.find("[name='code']").attr("disabled","disabled");
        currentRow.find("[name='value']").attr("disabled","disabled");
    }
}

//新增一行
function addRow(){
	var table = $("#infoTable").DataTable();
	table.row.add([
	            checkboxButton,
	            inputCode,
	            inputValue
	        ]).draw();
}

//删除选择的字典信息
function deleteRow(t) {
	$(t).attr("disabled","disabled");
	var table = $("#infoTable").DataTable();
	addSelecteCss();
	//未选择的情况
	var $allSelectCheckbox = $("[name='selectck']:checked");
    if ($allSelectCheckbox.length == 0) {
    	swal({  
            title: "请选择一条或多条进行删除",   
            type: "warning",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        });
    	$(t).removeAttr("disabled");
    } else {
    	swal({  title: "确认删除",   
            text: "请确认是否删除数据",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "删除",   
            cancelButtonText: "取消",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        }, 
        function(isConfirm){
            if(isConfirm){
                $.page.ajax($.page.getAjaxSettings({
                    async: false,
                    type: "POST",
                    dataType: "json",
                    url: "/codeType/removeCodeValue.htm",
                    data: $allSelectCheckbox.serialize(),
                    success: function (result, textStatus, jqXHR) {
                        swal({  
                            title: "删除成功",   
                            type: "success",   
                            confirmButtonColor: "#3399FF",   
                            confirmButtonText: "确定",   
                            closeOnConfirm: false,   
                            closeOnCancel: false 
                        });
                        table.rows('.selected').remove().draw(false);
                    },complete: function (jqXHR, textStatus) {
                        $(t).removeAttr("disabled");
                    }
                }));
            } else {
            	swal({  
                    title: "操作已取消",   
                    type: "success",   
                    confirmButtonColor: "#3399FF",   
                    confirmButtonText: "确定",   
                    closeOnConfirm: false,   
                    closeOnCancel: false 
                });
            	$(t).removeAttr("disabled");
            }
        });
     }
}

//保存按钮点击
function sub(that){
	var berforeText = $(that).text();
    $(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
	$.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/codeType/updateCodeTypeAndValue.htm",
        data: $('form').serialize(),
        success: function (result, textStatus, jqXHR) {
            $.notify("已成功编辑字典信息" + $("#type_name").val(),{"status":"success"});
            $("[name='selectck']").each(function(item) {
                var currentRow = $(this).parent().parent().parent();
                if ($(this).is(':checked')) {
                    // 当前行可编辑
                    currentRow.find("[name='code']").attr("disabled","disabled");
                    currentRow.find("[name='value']").attr("disabled","disabled");
                    $(this).removeAttr("checked");  
                }
            });
        },complete: function (jqXHR, textStatus) {
	        $(that).text(berforeText).parent().find("button").removeAttr("disabled");
	        $(that).prev(".btn_loading").remove();
        }
    }));
}

//返回按钮点击
function back(){
    location="<%=appPath%>/codeType/list.htm?ticketId=<%=ticketId %>";
}

//保存按钮可用设置
function validateButton(){
    if ($("#editForm").parsley().isValid()){
        $("#saveExit").removeAttr("disabled");
        $("#saveTypeInfo").removeAttr("disabled");
    } else {
        $("#saveExit").attr("disabled","disabled");
        $("#saveTypeInfo").attr("disabled","disabled");
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
}
//信息编码必须输入检查
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
}
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
}
//输入代码是否重复
function validateByInfoCode(t){
	var codes = $("[name='code']");
	var count=0;
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
}
// 隐藏字典类型信息
function hideButton(){
	$("#divCodeInfo").show();
	$("#cancelBtn").hide();
    $("#divTypeCode").hide();
    $("#divTypeName").hide();
}

// 显示字典类型信息
function showButton(){
	$("#divCodeInfo").hide();
    $("#divTypeCode").show();
    $("#divTypeName").show();
}

//编辑按钮事件
function editTypeInfo(){
	if ($("#saveTypeInfo").html() == "编辑") {
	  showButton();
	  $("#saveTypeInfo").html("确定");
	  $("#cancelBtn").show();
	} else if ($("#saveTypeInfo").html() == "确定") {
		var typeName = $(".typename").val();
		$.page.ajax($.page.getAjaxSettings({
			async: false,
			type: "POST",
			dataType: "json",
			url: "/codeType/saveTypeCode.htm",
			data: {
			    id:$("#id").val(),
			    type_code:$("#type_code").val(),
			    type_name:typeName
			},
			success: function (result, textStatus, jqXHR) {
			    hideButton();
                $("#type_name").val(typeName);
                $("#saveTypeInfo").html("编辑");
                $.notify("已成功编辑字典类型" + typeName,{"status":"success"});
			}
		}));
	}
}

//取消按钮事件
function cancel() {
	hideButton();
    $("#saveTypeInfo").html("编辑");
}

// 给所有选择的行加一个select的class
function addSelecteCss(){
	// 所有选中的checkbox
	var allCheckbox = $("[name='selectck']");
    allCheckbox.each(function(item) {
    	var currentRow = $(this).parent().parent().parent();
        if ($(this).is(':checked')) {
            if (!currentRow.hasClass('selected') ) {
                currentRow.addClass('selected');
            }
        } else {
            if (currentRow.hasClass('selected') ) {
                currentRow.removeClass('selected');
            }
        }
   });
}

//label中保存 页面数据检索用
function getValueToLable(t,type){
    if (!$(t).parent().hasClass('match')) {
        $(t).parent().addClass('match');
    }
    var temVal = $(t).val();
    var cell = $('#infoTable').DataTable().cell(".match");
    var jcell = $("<td>" + cell.data() + "</td>");
    jcell.find(".hidden").html(temVal);
    jcell.find("[name='" + type + "']").attr("value",temVal);
    jcell.find("[name='" + type + "']").removeAttr("disabled");
    cell.data(jcell.html()).draw();
    $(".match").removeClass('match');
}
</script>
