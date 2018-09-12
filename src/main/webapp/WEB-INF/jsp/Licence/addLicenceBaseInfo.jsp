<%@page import="com.mlsc.waste.utils.Constant"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
	String appPath=request.getContextPath();
	String ticketId = (String)request.getAttribute("ticketId");
%>
<!DOCTYPE html>
<html lang="en">
<link type="text/css" rel="stylesheet" href="<%=appPath %>/thirdparty/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="<%=appPath %>/css/licence/licence.css">
<style >
.form-horizontal .control-label {
    text-align:right;
}

.wizard > .actions {
    text-align: left;
}
</style>
<section>
	<div class="panel-heading">许可证基本信息</div>
	<div class="panel-body">
		<form:form class="form-horizontal" modelAttribute="operationLicence" id="editForm" data-parsley-validate="true">
			<form:hidden path="id"/><!-- 许可证基本信息Id -->
			<form:hidden path="enterprise_id"/><!-- 许可证所属企业Id -->
			<div class="form-group">
				<label class="col-md-2 control-label">许可证编号*</label>
				<div class="col-md-6">
					<form:input type="text" path="licence_no" class="form-control" placeholder="输入许可证编号" maxlength="32" onblur="licenceNoExist(this)"
								data-trigger="change" data-parsley-required="required" data-parsley-required-message="许可证编号不可以为空"
								data-parsley-licenceno="true" data-parsley-licenceno-message=""/>
				</div>
			</div>
			<%-- <div class="form-group">
                 <label class="col-md-2 control-label " for="licence_org">发证机关*</label>
                 <div class="col-md-6">
                     <form:select path="licence_org" class="form-control" data-trigger="change"
                                  data-parsley-required-message="请选择所属环保单位" data-live-search="true">
                         <form:option value=""></form:option>
                         &lt;%&ndash; <form:option value="11111">数据库没有数据-测试数据</form:option> &ndash;%&gt;
                         <c:forEach var="rpcSysOrgCom" items="${rpcSysOrgComs}" varStatus="status" begin="0" step="1">
                             <form:option value="${rpcSysOrgCom.comID}">${rpcSysOrgCom.comName}</form:option>
                         </c:forEach>
                     </form:select>
                 </div>
             </div>--%>
			<div class="form-group" >
				<label class="col-md-2 control-label " for="licenceDate">发证日期*</label>
				<div class="col-md-6">
					<div class="input-group input-append date form_datetime">
						<form:input type="text" path="licence_date" placeholder="请输入发证日期" class="form-control" readonly="true"
									data-trigger="focusout" data-parsley-required="required" data-parsley-required-message="日期不能为空"/>
						<span class="input-group-addon"> <span class="fa fa-calendar"></span></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label " for="beginDate">许可证有效日期*</label>
				<div class="col-md-3">
					<div class="input-group input-append date form_datetime" id="startDate">
						<form:input type="text" path="start_date" placeholder="开始日期必须小于结束日期" class="form-control" readonly="true"
									data-parsley-required="required" data-parsley-required-message="日期不能为空"/>
						<span class="input-group-addon"> <span class="fa fa-calendar"></span></span>
					</div>
				</div>
				<div class="col-md-3">
					<div class="input-group input-append date form_datetime" id="endDate">
						<form:input type="text" path="end_date" placeholder="结束日期必须大于开始日期" class="form-control" readonly="true"
									data-parsley-required="required" data-parsley-required-message="日期不能为空"/>
						<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label " for="operation_mode">核准经营方式 *</label>
				<div class="col-md-6">
					<form:select path="operation_mode" class="form-control" data-trigger="change"
								 data-parsley-required="required" data-parsley-required-message="请选择核准经营方式">
						<form:option value="" selected="selected"></form:option>
						<c:forEach var="it" items="${licModelList}" varStatus="status" begin="0" step="1">
							<form:option value="${it.id}" class="${it.code}">${it.value}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<p>带(*)为必填项目</p>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" id="saveBtn" class="btn btn-primary" onclick="saveLicence(this)">保存并下一步</button>
				</div>
			</div>
		</form:form>
	</div>
</section>
<script type="text/javascript" src="<%=appPath %>/thirdparty/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=appPath %>/thirdparty/eonasdan-bootstrap-datetimepicker/build/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=appPath %>/thirdparty/parsleyjs/dist/parsley.min.js"></script>
<script type="text/javascript">
var lienceDetailArrays = [];

var errorLicenceNoisExist = true;
var validityPeriodRepeatflg = false;
$(document).ready(function(){
    //datetimepicker控件初始化
    $('.form_datetime').datetimepicker({
        format : 'yyyy-mm-dd',
        language:  'zh-CN',
        weekStart: 1,
        todayBtn: "linked",
        todayHighlight:true,
        autoclose: true,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: false
    }).on('changeDate', function (ev) {
        reInitDatePicker ($(this));
    });
    
    //许可证重复性验证
    window.Parsley.addValidator('licenceno', function(value){
        return errorLicenceNoisExist;
    }, 32);
});

//给开始结束日期控件重新初始化
function reInitDatePicker($that){
    var id = $that.attr("id");
    if (id == "startDate") {
        $('#endDate').datetimepicker('setStartDate', $that.find("input").val());
    } else if (id == "endDate") {
        $('#startDate').datetimepicker('setEndDate', $that.find("input").val());
    }
}

// 许可证编码离焦事件
function licenceNoExist(that){
    errorLicenceNoisExist = true;
    $(that).parsley().validate();
    if ($(that).parsley().isValid()) {
        $.page.ajax($.page.getAjaxSettings({
            async: false,
            type: "POST",
            dataType: "json",
            url: "/licence/isLicenceNoExist.htm?etc=" + new Date().getTime(),
            data: {
                "licenceNo":$("#licence_no").val(),
                "licenceId":$("#id").val(),
            },
            success: function (result, textStatus, jqXHR) {
                if (result.data) {
                    // 许可证编码已经存在的场合
                    errorLicenceNoisExist = false;
                    $(that).attr("data-parsley-licenceno-message","输入的许可证编码已经存在了，请重新输入");
                    $(that).parsley().validate();
                } else {
                   // 许可证编码不存在的场合
                    errorLicenceNoisExist = true;
                }
            }
        }));
    }
}
//点击保存并下一步按钮
function saveLicence(that){
    isValidityPeriodRepeat ();
    if(formValidate() && validityPeriodRepeatflg){
        var berforeText = $(that).text();
        $(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
        $.page.ajax($.page.getAjaxSettings({
            async: false,
            type: "POST",
            dataType: "json",
            url: "/licence/saveLicenceBaseInfo.htm?etc=" + new Date().getTime(),
            data: $('#editForm').serialize(),
            success: function (result, textStatus, jqXHR) {
                // 许可证基本信息保存成功
//				$("#saveBtn").popover({
//				    trigger:'manual',
//				    placement : 'right',
//				    html: 'true',
//				    content : " <p style='color:#4f5669;font-size:16px;'><span class='fa fa-check-circle'  style='color:#58c558;font-size:26px;color:#58c558'></span>&nbsp保存信息成功，请点击下一步</p>",
//				    animation: true
//				});
//                $("#saveBtn").popover("show");
//                setTimeout('$("#saveBtn").popover("hide");', 3000);
				var id=$('#id').val();
                $.notify(id?'编辑成功':'创建成功,请进行下一步操作',{status:'success',timeout:2000});
                window.parent.addSuccess(result.data);
                $('ul[role=menu] li:nth-child(2) a').click();
            },complete: function (jqXHR, textStatus) {
                $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                $(that).prev(".btn_loading").remove();
            }
        }));
    }
}

//form表单验证
function formValidate(){
    var $form = $("#editForm");
    $form.parsley().validate();
    return $form.parsley().isValid();
}

//验证许可证有效期是否重复
function isValidityPeriodRepeat(){
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/licence/isValidityPeriodRepeat.htm?etc=" + new Date().getTime(),
        data: {
            "start_date":$("#start_date").val(),
            "end_date":$("#end_date").val(),
            "enterpriseId":$("#enterprise_id").val(),
            "licenceId":$("#id").val()
        },
        success: function (result, textStatus, jqXHR) {
            if (result.data) {
                // 许可证基本信息保存成功
                var options={"status":"<%= Constant.STATUS_INFO%>"};
                $.notify("当前许可证的有效期已经存在了，请重新输入有效期",options);
                validityPeriodRepeatflg = false;
            } else {
                // 许可证编码不存在的场合
                validityPeriodRepeatflg = true;
            }
        },complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                
            }
        }
    }));
}

</script>
</html>
