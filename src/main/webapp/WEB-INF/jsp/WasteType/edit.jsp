<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="危废类别"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#wastetypeItem"/>
</jsp:include>
<style>
  .form-horizontal .control-label {
    text-align: left;
    margin-bottom: 0;
    padding-top: 7px;
    font-weight:normal;
  }
</style>
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">基础数据管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner"  onclick="history.go(-1);">危废类别管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">危废类别编辑</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">危废类别编辑页面</div>
                    <div class="panel-body">
                        <form:form class="form-horizontal" target="" modelAttribute="wasteType" id="editForm" data-parsley-validate="true" action="saveOrUpdate">
                            <form:hidden id="id" path="id"/>
                            <div class="form-group">
                                <label class="col-md-2 control-label text-left">废物类别代码</label>
                                <div class="col-md-10">
                                    <div class="input-group" id="divHW">
                                      <span class="input-group-addon">HW</span>
                                      <form:input type="text" path="code" placeholder="输入废物类别代码" class="form-control"  maxlength="2" 
                                                  data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入危废类别代码"  
                                                  data-parsley-pattern="^[0-9]{2}$" data-parsley-pattern-message="输入危废类别代码的格式不正确"
                                                  data-parsley-checkcode="true" data-parsley-checkcode-message="" data-parsley-errors-container="#divHW"/>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">废物类别说明</label>
                                <div class="col-md-10">
                                    <form:textarea path="description" placeholder="输入废物类别说明,200位字符以内"  class="form-control" rows="5"
                                                   data-trigger="keyup" data-parsley-required="required" data-parsley-required-message="请输入废物类别名称" 
                                                   data-parsley-error-message="说明文字必须输入，而且只能在200字符以内"  
                                                   data-parsley-maxlength="200" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label"></label>
                                <div class="col-md-10">
                                    <button type="button" id="saveExit" class="btn btn-sm btn-primary" onclick="sub(this,'3')">保存</button>
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
var msg = "";
var status = "";
var error = true;
$(document).ready(function(){
    // 行业代码重复性验证
    window.Parsley.addValidator('checkcode', function(value){
        return error;
    }, 32);  
    
    $("#code").blur(function() {
        validateByCode();
        $("#code").parsley().validate();
        validateButton();
    }); 
    $("#description").blur(function() {
        $("#description").parsley().validate();
        validateButton();
     });
});
function back(){
    var pageUrl = "/WasteType/list.htm";
    $.page.gotoTargetLocation(pageUrl)
}

//输入代码是否重复
function validateByCode(){
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/WasteType/isWasteTypeCodeExistent.htm",
        data: {
            "id":$("#id").val(),
            "code":$("#code").val()
        },
        success: function (result, textStatus, jqXHR) {
            if(result.data.checkResult){
                error = false;
                $("#code").attr("data-parsley-checkcode-message", "危废类别代码已存在,请重新输入");
            }else {
                error = true;
            }
        }
    }));
};

//保存按钮点击
function sub(that,model){
    if (validate ()) {
        var berforeText = $(that).text();
        $(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
        $.page.ajax($.page.getAjaxSettings({
            async: false,
            type: "POST",
            dataType: "json",
            url: "/WasteType/updateWasteType.htm",
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
};
function validate(){
    var $form = $("#editForm");
    $form.parsley().validate();
    return $form.parsley().isValid();
}

//保存按钮可用设置
function validateButton(){
    if ($("#editForm").parsley().isValid()){
        $("#saveExit").removeAttr("disabled");
    } else {
        $("#saveExit").attr("disabled","disabled");
    }
}

//页面信息提示框弹出
function toUrl(model){
    var paramObj = {};
    var pageUrl = "/WasteType/list.htm";  
    $.page.gotoTargetLocation(pageUrl, paramObj);
}
</script>
