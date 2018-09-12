<%@page import="com.mlsc.waste.utils.Constant"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="企业信息"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#sysorgcomlist" />
</jsp:include>
<link href="<%=appPath %>/thirdparty/city-picker/css/city-picker.css" rel="stylesheet" type="text/css" />

<style id="enterpriseCss">
  .form-horizontal .control-label {
    margin-bottom: 0;
    padding-top: 7px;
    font-weight:normal;
  }
  
</style>

<!-- Main section-->
<section>
    <div class="hidden"><!-- city-picker选择时获取数据参数的设定 ID不能改变 -->
		<input type='hidden' id="appPath" value="<%=appPath %>"/>
		<input type='hidden' id="ticketId" value="<%=ticketId %>"/>
	</div>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">权限管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner"  onclick="javascript:history.back(-1);">组织机构管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">组织机构新增</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <!-- Page content-->
    <div >
        <div class="row">
            <%------- 导入按钮工具栏 -------%>
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">创建组织机构</div>
                    <div class="panel-body">
                        <form class="form-horizontal" id="editForm" data-parsley-validate="true">
                            <div class="form-group">
                                <label class="col-md-2 control-label text-left">组织机构名称*</label>
                                <div class="col-md-6">
                                    <input id="comName" name="comName" type="text" placeholder="输入组织机构名称" class="form-control" maxlength="64" 
                                    data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入组织机构名称" 
                                    data-parsley-checkname="true" data-parsley-checkname-message="该机构名称已经存在"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label text-left">组织机构代码*</label>
                                <div class="col-md-6">
                                    <input id="comCode" name="comCode" type="text" placeholder="输入组织机构代码" class="form-control" maxlength="64" 
                                    data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入组织机构代码" onblur="checkOrgCode(this)"
                                    data-parsley-checkcode="true" data-parsley-checkcode-message="" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label text-left">行政区*</label>
                                <div class="col-md-6" id="cantonDiv">
                                    <input id="cantonCode" name="cantonCode" type="text" class="form-control" data-toggle="city-picker" 
                                    data-trigger="change" data-parsley-required="required" data-parsley-required-message="请选择行政区" data-parsley-errors-container="#cantonDiv"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label text-left">机构说明</label>
                                <div class="col-md-6">
                                    <input id="comDesc" name="comDesc" type="text" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-2 col-md-10">
                                     <label class="control-label">(*)&nbsp;&nbsp;必须输入</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-2 col-md-10">
                                    <button type="button" id="saveExit" class="btn btn-primary" onclick="saveOrgComInfo(this,'0')">保存并结束</button>
                                    <button type="button" id="saveAdd" class="btn btn-primary" onclick="saveOrgComInfo(this,'1')">保存并继续</button>
                                    <button type="button" id="cancel" class="btn btn-default" onclick="javascript:history.back(-1);">取消</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.data.js"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.js"></script>
<script type="text/javascript" id="enterpriseScript">
var checkName = true;
var checkCode = true;
$(document).ready(function(){
    $('[data-toggle="city-picker"]').citypicker({
        responsive:true,
    });
    
    // 组织机构名称不能重复
    window.Parsley.addValidator('checkname', function(value){
        return checkName;
    }, 31);
    
    // 组织机构代码不能重复
    window.Parsley.addValidator('checkcode', function(value){
        return checkCode;
    }, 32);
    
});

 // 保存按钮点击
function saveOrgComInfo(that,model){
    if(validate()){
        var berforeText = $(that).text();
        $(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
        $.ajax({
            async: false,
            url: "<%=appPath%>/sysorgcom/saveOrgCom.htm?ticketId=<%=ticketId %>&etc="+new Date().getTime(),
            data: $('form').serialize(),
            success: function(result){
                var obj = $.parseJSON(result);
                if(obj.status == 1){
                    if (obj.data.statusCode == "0") {
                        $.notify("已成功创建组织机构" + $("#comName").val(), {"status":"<%=Constant.STATUS_SUCCESS %>"});
                        setTimeout("toUrl(" + model + ")",2000);
                    } else if (obj.data.statusCode == "1") {
                        $.notify("请选择行政区",{"status":"<%=Constant.STATUS_INFO %>"});
                        $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                        $(that).prev(".btn_loading").remove();
                    } else if (obj.data.statusCode == "2") {
                        $.notify("您选择了无效的行政区，如有问题，请联系我们的客服",{"status":"<%=Constant.STATUS_INFO %>"});
                        $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                        $(that).prev(".btn_loading").remove();
                    }
                }else{
                    $.notify("<%=Constant.SYS_MSG %>",{"status":"<%=Constant.STATUS_DANGER %>"});
                    $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                    $(that).prev(".btn_loading").remove();
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                var options={"status":"<%=Constant.STATUS_INFO %>"};
                $.notify("请求错误",options);
            }
        });
    }
}

//输入机构名称是否重复
function checkOrgCode(that){
    validateByOrgComCode ();
    $(that).parsley().validate();
    validateButton();
};

//输入机构名称是否重复
function validateByOrgComName(){
    $.ajax({
          async: false,
          url: "<%=appPath%>/sysorgcom/isExistentOrgComName.htm?ticketId=<%=ticketId %>&etc="+new Date().getTime(),
          data: {
              "comName":$("#comName").val()
          },
          success: function(result){
              if(Boolean(result)){
                  checkName = false;
              }  else {
                  checkName = true;
              }
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
              var options={"status":"<%=Constant.STATUS_INFO %>"};
              $.notify("请求错误",options);
          }
    });
};

//输入机构代码是否重复
function validateByOrgComCode(){
    $.ajax({
          async: false,
          url: "<%=appPath%>/sysorgcom/isExistentOrgComCode.htm?ticketId=<%=ticketId %>&etc="+new Date().getTime(),
          data: {
              "comCode":$("#comCode").val()
          },
          success: function(result){
              if(Boolean(result)){
                  checkCode = false;
                  $("#comCode").attr("data-parsley-checkcode-message","与" + result + "的机构代码重复了");
              }  else {
                  checkCode = true;
              }
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
              var options={"status":"<%=Constant.STATUS_INFO %>"};
              $.notify("请求错误",options);
          }
    });
};

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
        location="<%=appPath%>/sysorgcom/addOrgCom.htm?ticketId=<%=ticketId %>";  
    } else {
        location="<%=appPath%>/sysorgcom/list.htm?ticketId=<%=ticketId %>"; 
    }
}
</script>
<!-- jQuery -->
