<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<link rel="stylesheet" href="<%=appPath %>/thirdparty/silviomoreto-bootstrap-select/dist/css/bootstrap-select.css">
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="危废名录"/>
</jsp:include>
<%------- 结束导入页头信息 -------%>

<%------- 导入左侧信息 -------%>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#wasteItem"/>
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
            <span class="el-breadcrumb__item__inner">基础数据管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner"  onclick="history.go(-1);">危废名录管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">危废名录编辑</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
   <!-- Page content-->
   <div>
      <div class="row">
       <div class="col-md-12">
          <div class="panel panel-default">
          <div class="panel-heading">危废名录编辑页面</div>
              <div class="panel-body">
                  <form:form class="form-horizontal" modelAttribute="waste" id="editForm" data-parsley-validate="true">
                      <input type="hidden" name="type"/>
                      <form:hidden id="id" path="id"/>
			          <div class="form-group">
                          <label class="col-md-2 control-label text-left">行业来源*</label>
                          <div class="col-md-10">
                            <form:select id="callingId" name="callingId" path="calling_id" class="selectpicker form-control" onchange="change('1')" data-live-search="true">
                                <form:option value="-1" disabled="true">请选择行业来源</form:option>
                                <c:forEach var="it" items="${callingList}" varStatus="status" begin="0" step="1">
                                	<form:option value="${it.calling_id}">${it.callingOriginal}</form:option>
                                </c:forEach>
                            </form:select>
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-2 control-label text-left">废物类别*</label>
                          <div class="col-md-10">
                            <form:select id="wasteTypeId" name="wasteTypeId" path="waste_type_id" class="selectpicker form-control" onchange="change('2')" data-live-search="true">
                                <form:option value="-1" disabled="true">请选择废物类别</form:option>
                                <c:forEach var="it" items="${wasteTypeList}" varStatus="status" begin="0" step="1">
                                	<form:option value="${it.waste_type_id}">${it.wasteType}</form:option>
                                </c:forEach>
                            </form:select>
                          </div>
                      </div>
                      
                      <div class="form-group">
                          <label class="col-md-2 control-label">废物代码*</label>
                          <div class="col-md-3">
                          		<form:input id="callingCode" name="callingCode" type="text" path="callingCode" class="form-control" data-trigger="change" required="required" disabled="true"/>
                          </div>
                          <div class="col-md-4">
                               <form:input type="text" name="threeYardsCode" path="threeYardsCode" placeholder="3位顺序码" class="form-control" minlength="3" maxlength="3" 
                                          data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入3位顺序码"  
                                           data-parsley-pattern="^[A-Z0-9]+$" data-parsley-pattern-message="输入顺序码格式不正确"
                                           data-parsley-checkcode="true" data-parsley-checkcode-message=""/>
                          </div>
                           <div class="col-md-3">
                          		<form:input id="wasteTypeCode" name="wasteTypeCode" type="text" path="wasteTypeCode" class="form-control" data-trigger="change" required="required" disabled="true"/>
                          </div>
                      </div>
			          <div class="form-group">
                          <label class="col-md-2 control-label">废物描述*</label>
                          <div class="col-md-10">
                              <form:input type="text" path="description" placeholder="输入危废信息,20位字符以内" class="form-control" maxlength="200"
                                           data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入危废信息" />
                          </div>
                      </div>
			          <div class="form-group">
                           <label class="col-md-2 control-label">危险特性*</label>
                           <div class="col-md-10">
                              <form:select id="riskCharacteristics" name="riskCharacteristics" path="riskCharacteristics" multiple="multiple" class="selectpicker form-control" data-live-search="true">
                              	<form:option value="-1" disabled="true">多选</form:option>
								<c:forEach var="it" items="${riskCharacteristicsList}" varStatus="status" begin="0" step="1">
									<c:if test="${it.isChecked == '1'}">
									    <form:option value="${it.code}" selected="selected">${it.code} - ${it.value}</form:option>
                               		</c:if>
                               		<c:if test="${it.isChecked != '1'}">
									    <form:option value="${it.code}" >${it.code} - ${it.value}</form:option>
                               		</c:if>
                                </c:forEach>
                            </form:select>
                           </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-2 control-label">废物名称*</label>
                          <div class="col-md-5">
                               <button type="button" id="addButton" class="btn btn-sm btn-primary" onclick="addRow()">增加</button>
                               <button type="button" id="deleteButton" class="btn btn-sm btn-primary" onclick="deleteRow(this)">删除</button>
                           </div>
                      </div>
                      <div class="form-group">
                          <div class="col-md-offset-2 col-md-10 table-responsive">
                              <table class="table table-condensed" id="infoTable">
                                  <thead>
                                     <tr>
                                     	<th>选择</th>
                                        <th>废物名称</th>
                                     </tr>
                                  </thead>
                                  <tbody>
                                      <c:forEach var="it" items="${wasteNames}" varStatus="status" begin="0" step="1">
                                          <tr>
                                          	  <td>
                                                  <label class="checkbox-inline c-checkbox">
                                                      <input type="checkbox" name="selectck" onClick="selected(this)" value="${it.id}"/>
                                                      <span class="fa fa-check"></span>
                                                  </label>
                                              </td>
                                              <td>
                                                  <label class="hidden">${it.name}</label><input type="text" name="value" class="form-control" value="${it.name}" disabled="disabled" onblur="valueblur(this)"/>
                                              </td>
                                          </tr>
                                      </c:forEach>
                                  </tbody>
                              </table>
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="col-md-offset-2 col-md-10">
                               <label class="control-label">(*)&nbsp;&nbsp;必须输入</label>
                          </div>
                      </div>
                      <div class="form-group">
                      	  <label class="col-md-2 control-label"></label>
                          <div class="col-md-10">
                                  <button type="button" id="saveExit" class="btn btn-sm btn-primary" onclick="sub(this)">保存</button>
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
var msg = "";
var status = "";
var checkboxButton = "<label  class='checkbox-inline c-checkbox'><input type='checkbox' checked='checked' name='selectck' onClick='selected(this)'/>"
    + "<span class='fa fa-check'></span></label>";
var inputValue = "<label class='hidden'></label><input type='text' name='value' class='form-control' value='' placeholder='请输入信息名称' onblur='valueblur(this)'/>";
$(document).ready(function(){
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
	
	 // 危废名录代码重复性验证
    window.Parsley.addValidator('checkcode', function(value){
    	validateByCode();
     	return error;
    }, 32);  
	
	// 危废名录代码绑定事件，
    $("#threeYardsCode").blur(function() {
        $("#threeYardsCode").parsley().validate();
    }); 
    $("#description").blur(function() {
        $("#description").parsley().validate();
     });
    
});

function back(){
    location="<%=appPath%>/Waste/list.htm?ticketId=<%=ticketId %>";
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
};

//输入代码是否重复
function validateByCode(){
	$.ajax({
		  async: false,
		  url: "isWasteCodeExistent.htm?etc="+new Date().getTime()  + "&ticketId=<%=ticketId %>",
		  data: $('form').serialize(),
		  success: function(result){
		        var obj = $.parseJSON(result);
		        if(obj.status == 0){
		            error = false;
		            $("#threeYardsCode").attr("data-parsley-checkCode-message",obj.data.msg);
		        }  else {
		            error = true;
		            $("#threeYardsCode").attr("data-parsley-checkCode-message","");
		        }
		  } 
	});
};

//信息名称绑定离焦事件
function valueblur(t){
	validateByInfoValueRequired(t);
    $(t).parsley().validate();
    $(t).parent().siblings().find("[name='code']").parsley().validate();
    // label中保存 页面数据检索用
    getValueToLable(t, "value");
}

//table中所有选择框绑定
function selected(t){
	// 当前行
	var currentRow = $(t).parent().parent().parent();
    if ($(t).is(':checked')) {
        // 当前行可编辑
        currentRow.find("[name='value']").removeAttr("disabled");
    } else {
        // 当前行不可编辑
        currentRow.find("[name='value']").attr("disabled","disabled");
    }
}

function addRow(){
	var table = $("#infoTable").DataTable();
	table.row.add([
				checkboxButton,
	            inputValue
	        ]).draw();
}

function deleteRow(t) {
	$(t).attr("disabled","disabled");
	var table = $("#infoTable").DataTable();
	addSelecteCss();

	//未选择的情况
   if ($("[name='selectck']:checked").length == 0) {
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
               $.post('removeWasteName.htm?etc='+new Date().getTime() + "&ticketId=<%=ticketId %>",$("input[name='selectck']").serialize(),function(json){
                   var obj = $.parseJSON(json);
                   if(obj.status == "1"){
                       swal({  
                           title: "删除成功",   
                           type: "success",   
                           confirmButtonColor: "#3399FF",   
                           confirmButtonText: "确定",   
                           closeOnConfirm: false,   
                           closeOnCancel: false 
                       });
                       table.rows('.selected').remove().draw(false);
                   }else{
                   	swal({  
                           title: "删除失败",   
                           type: "warning",   
                           confirmButtonColor: "#DD6B55",   
                           confirmButtonText: "确定",   
                           closeOnConfirm: false,   
                           closeOnCancel: false 
                       });
                   }
               });
           } else {
           	swal({  
                   title: "操作已取消",   
                   type: "success",   
                   confirmButtonColor: "#3399FF",   
                   confirmButtonText: "确定",   
                   closeOnConfirm: false,   
                   closeOnCancel: false 
               });
           }
           $(t).removeAttr("disabled");
       });
    }
}

//给所有选择的行加一个select的class
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

//保存按钮点击
function sub(that){
	$("#saveExit").attr("disabled","disabled");
	var berforeText = $(that).text();
    $(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
	$.post('updateWaste.htm?etc=' + new Date().getTime() + "&ticketId=<%=ticketId %>",$('form').serialize(), function(result){
	    var obj = $.parseJSON(result);
	    msg = obj.data.msg;
	    if(obj.status == 1) {
            var options={"status":"success"};
            $.notify(msg,options);
            // 所有选中的checkbox
            $("[name='selectck']").each(function(item) {
                  // 当选择的行前行
                var currentRow = $(this).parent().parent().parent();
                if ($(this).is(':checked')) {
                    // 当前行可编辑
                    currentRow.find("[name='value']").attr("disabled","disabled");
                    $(this).removeAttr("checked");  
                }
           });
            toUrl();
        } else {
            var options={"status":"danger"};
            $.notify(msg,options);
            $("#saveExit").removeAttr("disabled");
            $(that).text(berforeText).parent().find("button").removeAttr("disabled");
            $(that).prev(".btn_loading").remove();
        }
	});
}

function toUrl(){
	location="<%=appPath%>/Waste/list.htm?ticketId=<%=ticketId %>";  
}
	
//form表单验证
function validate(){
    var form = $("#editForm").parsley();
    form.validate();
    return form.isValid();
}

function change(value){
	if(value == '1'){
		for (var i=0;i < callingId.length; i++) {
			if(callingId.options[i].selected == true){
				var id = callingId.options[i].text;
				var callingID_code1 = id.substring(0,3);
				document.getElementById("callingCode").value = callingID_code1;
			}
		}
	}else{
		for (var i=0;i < wasteTypeId.length; i++) {
			if(wasteTypeId.options[i].selected == true){
				var id = wasteTypeId.options[i].text;
				var wasteTypeId_code3 = id.substring(2,4);
				document.getElementById("wasteTypeCode").value = wasteTypeId_code3;
			}
		}
	}
}
</script>