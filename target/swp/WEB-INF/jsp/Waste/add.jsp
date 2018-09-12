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
            <span class="el-breadcrumb__item__inner">危废名录新增</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
   <!-- Page content-->
   <div>
      <div class="row">
       	  <div class="col-md-12">
          <div class="panel panel-default">
          	  <div class="panel-heading">危废名录新增页面</div>
              <div class="panel-body">
                  <form:form class="form-horizontal" modelAttribute="waste" id="editForm" data-parsley-validate="true">
                      <input type="hidden" name="type"/>
                      <form:hidden id="id" path="id"/>
			          <div class="form-group">
                          <label class="col-md-2 control-label text-left">行业来源*</label>
                          <div class="col-md-10">
                            <form:select id="callingId" name="callingId" path="calling_id" class="selectpicker form-control" onchange="change('1')" data-live-search="true">
                                <form:option value="-1">请选择行业来源</form:option>
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
                                <form:option value="-1">请选择废物类别</form:option>
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
                          <div class="col-md-2">
                               <form:input type="text" name="threeYardsCode" path="threeYardsCode" placeholder="3位顺序码" class="form-control" minlength="3" maxlength="3" 
                                          data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入3位顺序码"  
                                           data-parsley-pattern="^[A-Z0-9]+$" data-parsley-pattern-message="顺序码格式不正确"
                                           data-parsley-checkcode="true" data-parsley-checkcode-message=""/>
                          </div>
                           <div class="col-md-3">
                          		<form:input id="wasteTypeCode" name="wasteTypeCode" type="text" path="wasteTypeCode" class="form-control" data-trigger="change" required="required" disabled="true"/>
                          </div>
                      </div>
			          <div class="form-group">
                          <label class="col-md-2 control-label">废物描述*</label>
                          <div class="col-md-10">
                              <form:input type="text" path="description" placeholder="输入废物信息" class="form-control" maxlength="200"
                                           data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入废物信息" />
                          </div>
                      </div>
			          <div class="form-group">
                           <label class="col-md-2 control-label">危险特性*</label>
                           <div class="col-md-10">
                              <form:select id="parentId" name="parentId" path="riskCharacteristics" multiple="multiple" class="selectpicker form-control" data-live-search="true">
                              	<form:option value="-1" disabled="true">请选择危险特性</form:option>
								<c:forEach var="it" items="${riskCharacteristicsList}" varStatus="status" begin="0" step="1">
                                	<form:option value="${it.code}">${it.code} - ${it.value}</form:option>
                                </c:forEach>
                            </form:select>
                           </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-2 control-label">废物名称*</label>
                      </div>
                      <div class="form-group">
                          <div class="col-md-offset-2 col-md-8 table-responsive">
                              <table class="table" >
                                  <thead>
                                     <tr>
                                        <th>废物名称</th>
                                        <th></th>
                                     </tr>
                                  </thead>
                                  <tbody id="infoTable" >
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
                          <div class="col-md-offset-2 col-md-10">
                               <button type="button" id="saveExit" class="btn btn-sm btn-primary" onclick="sub(this,'1')">保存并结束</button>
                               <button type="button" id="saveAdd" class="btn btn-sm btn-primary" onclick="sub(this,'2')" >保存并继续追加</button>
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
	
	 // 危废名录代码重复性验证
    window.Parsley.addValidator('checkcode', function(value){
     	return error;
    }, 32);  
	
	// 危废名录代码绑定事件，
    $("#threeYardsCode").blur(function() {
    	validateByCode();
        $("#threeYardsCode").parsley().validate();
        validateButton();
    }); 
    $("#description").blur(function() {
        $("#description").parsley().validate();
        validateButton();
     });
    
 	// 初始情况下，按钮不可用
    $("#saveExit").attr("disabled","disabled");
    $("#saveAdd").attr("disabled","disabled");
    
});

//信息名称绑定离焦事件
function valueblur(t){
	validateByInfoValueRequired(t);
    $(t).parsley().validate();
    $(t).parent().siblings().find("[name='code']").parsley().validate();
    validateButton();
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
};

function back(){
    location="<%=appPath%>/Waste/list.htm?ticketId=<%=ticketId %>";
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
    tbodeyCode += "<input type='text' name='value' placeholder='输入废物名称' class='form-control' maxlength='50' data-trigger='change' onblur='valueblur(this)'>";
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

//输入代码是否重复
function validateByCode(){
	$.ajax({
		  async: false,
		  url: "isWasteCodeExistent.htm?etc="+new Date().getTime() + "&ticketId=<%=ticketId %>",
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

//保存按钮点击
function sub(that,model){
	if ($('#editForm').parsley().isValid()) {
        $("#saveExit").attr("disabled","disabled");
        $("#saveAdd").attr("disabled","disabled");
        var berforeText = $(that).text();
        $(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
        $.post('saveWaste.htm?ticketId=<%=ticketId %>&etc='+new Date().getTime(),$('form').serialize(),function(result){
        	var obj = $.parseJSON(result);
        	msg = obj.data.msg;
        	if(obj.status == 1){
                 var options={"status":"success"};
                 $.notify(msg,options);
                 toUrl(model);
            }else{
                 var options={"status":"danger"};
                 $.notify(msg,options);
                 $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                 $(that).prev(".btn_loading").remove();
                 $("#saveExit").removeAttr("disabled");
                 $("#saveAdd").removeAttr("disabled");
            }
        });
	}
};

//form表单验证
function validate(){
    var form = $("#editForm").parsley();
    form.validate();
    return form.isValid();
}

function change(value){
	var callingid = document.getElementById("callingId");
	var wasteTypeid = document.getElementById("wasteTypeId");
	if(value == '1'){
		for (var i=0;i < callingid.length; i++) {
			if(callingid.options[i].selected == true){
				var id = callingid.options[i].text;
				callingID_code1 = id.substring(0,3);
				document.getElementById("callingCode").value = callingID_code1;
			}
		}
	}else{
		for (var i=0;i < wasteTypeid.length; i++) {
			if(wasteTypeid.options[i].selected == true){
				var id = wasteTypeid.options[i].text;
				wasteTypeId_code3 = id.substring(2,4);
				document.getElementById("wasteTypeCode").value = wasteTypeId_code3;
			}
		}
	}
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
        location="<%=appPath%>/Waste/list.htm?ticketId=<%=ticketId %>";  
    } else {
        location="<%=appPath%>/Waste/add.htm?ticketId=<%=ticketId %>"; 
    }
}

</script>