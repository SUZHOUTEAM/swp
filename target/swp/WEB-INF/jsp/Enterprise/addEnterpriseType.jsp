<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
    String enterpriseId = (String)request.getAttribute("enterpriseId");
%>
<c:if test="${isSysUser}">
    <jsp:include page="/common/top.jsp" flush="true">
      <jsp:param name="title" value="企业信息"/>
    </jsp:include>
    <jsp:include page="/common/left.jsp" flush="true">
        <jsp:param name="menuId" value="#enterpriseManagement" />
    </jsp:include>
</c:if>
<c:if test="${not isSysUser}">
    <jsp:include page="/common/webCommon/user_top.jsp" flush="true">
      <jsp:param name="title" value="企业信息"/>
    </jsp:include>
    <jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
        <jsp:param name="menuId" value="#myEnterprise" />
    </jsp:include>
</c:if>
<%--<link href="<%=appPath %>/thirdparty/city-picker/css/city-picker.css" rel="stylesheet" type="text/css" />--%>
<link rel="stylesheet" href="<%=appPath %>/css/enterprise/enterprise_common.css?1">
<style>
.panel .panel-heading {
    text-align: right;
}
.panel .panel-body {
    text-align: center;
}
.panel-footer {
    text-align: center;
}
.panel-footer ul{
    padding-left: 26%;
    width: 90%;
    text-align: left;
    margin: auto;
}
.areaSelect{
    position: relative;
    width: 94%;
    margin: 0 23px 40px;
    display: none;
    text-align: left;
}
.areaSelect select.form-control{
    width: 200px;
    display: inline-block;
}
</style>
<!-- Main section-->
<section>
    <%--<div class="hidden"><!-- city-picker选择时获取数据参数的设定 ID不能改变 -->
        <input type='hidden' id="appPath" value="<%=appPath %>"/>
        <input type='hidden' id="ticketId" value="<%=ticketId %>"/>
    </div>--%>
   <!-- Page content-->
        <div class="bind-success-dialog">
            <div class="bind-success-content">
                <a href="<%=appPath %>/myenterprise/myEnterprise.htm?ticketId=<%=ticketId %>" class="bind_dialog_close"></a>
                <div class="bind_text"><i class="success_icon"></i>绑定企业成功！欢迎来到易废网~</div>
                <div style="text-align: center">
                    <a href="<%=appPath%>/entRelease/entWasteList.htm?ticketId=<%=ticketId%>" class="publish_btn">危废处置询价></a>
                </div>
                <%--<div style="text-align: center;padding: 20px 0">--%>
                    <%--<a href="<%=appPath%>/wastecircle/init.htm?ticketId=<%=ticketId%>">我先看看></a>--%>
                <%--</div>--%>
                <img src="<%=appPath %>/main/pc/img/publishProcedure.jpg" class="procedure">
            </div>
        </div>
   <div class="content-wrapper">
       <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">企业信息</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
           <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">确认企业类型</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
       </div>
          <div class="panel panel-default">

              <form:form class="form-horizontal" modelAttribute="enterprise" id="editForm" data-parsley-validate="true">
              	<%--<div class="choseEnterpriseType">--%>
                    <%--<span>选择企业类型</span>--%>
                <%--</div>--%>
                <div class="panel-body" id="totalContainer">
                <div class="form-group">
                        <div class="col-md-4 enterpriseType">
                         <div id="panel1" class="panel panel-default coursor_enterprise"  onclick="clickDivCheckBox('1')">
                             <div class="panel-heading checkbox c-checkbox">
                                 <label>
                                     <input type="checkbox" id="production" name="production"/>
                                     <span class="fa fa-check"></span>
                                 </label>
                             </div>
                             <div>
                                 <img alt="" src="<%=appPath%>/app/img/business_icon/production.png">
                             </div>
                             <div class="panel-body">
                                <p class="production_p">产废企业</p>
                                <p class="enterprise_body">产生危险废物需要处置</p>
                            </div>
                           </div>
                         </div>
                         <div class="col-md-4 enterpriseType">
                          <div id="panel2" class="panel panel-default coursor_enterprise"  onclick="clickDivCheckBox('2')">
                             <div class="panel-heading checkbox c-checkbox">
				                 <label>
				                     <input type="checkbox" id="disposition" name="disposition"/>
				                     <span class="fa fa-check"></span>
				                 </label>
                             </div>
                             <div>
                                 <img alt="" src="<%=appPath%>/app/img/business_icon/disposition.png">
                             </div>
                             <div class="panel-body"  style="padding: 15px 0">
                                <p class="disposition_p">处置企业</p>
                                <p class="enterprise_body">处置利用危险废物的持证单位</p>
                            </div>
                           </div>
                         </div>
                        <div class="col-md-4 enterpriseType">
                            <div id="panel3" class="panel panel-default coursor_enterprise"  onclick="clickDivCheckBox('3')">
                                <div class="panel-heading checkbox c-checkbox">
                                    <label>
                                        <input type="checkbox" id="DIS_FACILITATOR" name="dis_facilitator"/>
                                        <span class="fa fa-check"></span>
                                    </label>
                                </div>
                                <div>
                                    <img alt="" src="<%=appPath%>/app/img/business_icon/identification.png">
                                </div>
                                <div class="panel-body">
                                    <p class="identification_p">处置服务商</p>
                                    <p class="enterprise_body">提供危险废物的处置服务</p>
                                </div>
                            </div>
                        </div>
                </div>

                    <div class="areaSelect">
                        <label>选择服务区域：</label>
                        <select class="form-control" id="areaSelect" name="responsibleArea">
                        </select>
                        <%--<input name="responsibleArea" id="responsibleArea" type="text" class="form-control" style="position: relative;" placeholder="选择服务区域" data-toggle="city-picker"/>--%>

                    </div>
                    <div class="form-group">
          		        <div class="col-md-8">
                            <button type="button" id="saveExit"  class="btn btn-primary" onclick="confirmSubmit(this)">确认</button>
                        </div>
          	        </div>
                </div>
              </form:form>
          </div>
   </div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script src="<%=appPath %>/main/common/provinces.js?5"></script>
<%--<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker_facilitator.data.js"></script>--%>
<%--<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.js"></script>--%>
<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
var msg = "${msg}";
var status = "${status}";
var enterpriseId = "${enterpriseId}";

$(document).ready(function(){
//  var rs = window.globalInit();
//  rs.done(function () {
    // 初始情况下，按钮不可用
    $("#saveExit").attr("disabled","disabled");
//      $('[data-toggle="city-picker"]').citypicker({
//          responsive:true
//      });
//  });
    var str='<option value="">请选择服务区域</option>';
    for(var code in serviceProvince){
        str+='<option value="'+code+'">'+serviceProvince[code]+'</option>';
    }
    $('#areaSelect').html(str);

});

function confirmSubmit(that){
    console.log($('#areaSelect').val());
    if($('#DIS_FACILITATOR')[0].checked && !$('#areaSelect').val()){
        $.notify("请选择服务区域",{status:'danger',timeout:1500});
        return;
    }
	var berforeText = $(that).text();
	$(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
	$.post('<%=appPath%>/enterprise/saveEnterpriseType.htm?ticketId=<%=ticketId %>&enterpriseId=<%=enterpriseId %>&etc='+new Date().getTime(),$('form').serialize(),function(result){
		var obj = $.parseJSON(result);
	    msg = obj.data.msg;
	    enterpriseId = obj.data.enterpriseId;
	    if(obj.status == 1){
	        if($('#production')[0].checked){
	            $('.bind-success-dialog').show();
	        }else{
                var options={"status":"success"};
                $.notify(msg,options);
                setTimeout("toUrl()",1000);
            }
	    }else{
	    	var options={"status":"danger"};
	        $.notify(msg,options);
	        $(that).text(berforeText).parent().find("button").removeAttr("disabled");
            $(that).prev(".btn_loading").remove();
	    }
	   });
}

function clickDivCheckBox(value){
	if(value == '1'){
		var isChecked = document.getElementById("production").checked;
		var div = document.getElementById('panel1');
        $('.areaSelect').hide();
		if(isChecked){
			div.style.borderColor = "#cfdbe2";
			document.getElementById("production").checked = false;
			ifChecked();
		}else{
			document.getElementById("production").checked = true;
			div.style.borderColor = "#4f5565";
			document.getElementById("disposition").checked = false;
			document.getElementById('panel2').style.borderColor = "#cfdbe2";
            document.getElementById("DIS_FACILITATOR").checked = false;
            document.getElementById('panel3').style.borderColor = "#cfdbe2";
			ifChecked();
		}
	}
	if(value == '2'){
		var isChecked = document.getElementById("disposition").checked;
		var div = document.getElementById('panel2');
        $('.areaSelect').hide();
		if(isChecked){
			div.style.borderColor = "#cfdbe2";
			document.getElementById("disposition").checked = false;
			ifChecked();
		}else{
			document.getElementById("disposition").checked = true;
			div.style.borderColor = "#18caa6";
			document.getElementById("production").checked = false;
			document.getElementById('panel1').style.borderColor = "#cfdbe2";
            document.getElementById("DIS_FACILITATOR").checked = false;
            document.getElementById('panel3').style.borderColor = "#cfdbe2";
			ifChecked();
		}
	}
	if(value == '3'){
		var isChecked = document.getElementById("DIS_FACILITATOR").checked;
		var div = document.getElementById('panel3');
		if(isChecked){
            $('.areaSelect').hide();
			div.style.borderColor = "#cfdbe2";
			document.getElementById("DIS_FACILITATOR").checked = false;
			ifChecked();
		}else{
            $('.areaSelect').show();
			document.getElementById("DIS_FACILITATOR").checked = true;
			div.style.borderColor = "#9900ff";
			document.getElementById("production").checked = false;
			document.getElementById('panel1').style.borderColor = "#cfdbe2";
            document.getElementById("disposition").checked = false;
            document.getElementById('panel2').style.borderColor = "#cfdbe2";
			ifChecked();
		}
	}
}
function ifChecked(){
	var isCheckedProduction = document.getElementById("production").checked;
	var isCheckedDisposition = document.getElementById("disposition").checked;
//	var isCheckedIdentification = document.getElementById("identification").checked;
	var isCheckedDIS_FACILITATOR=document.getElementById("DIS_FACILITATOR").checked;
	if(isCheckedProduction || isCheckedDisposition ||isCheckedDIS_FACILITATOR){
		$("#saveExit").removeAttr("disabled");
	}else{
		$("#saveExit").attr("disabled","disabled");
	}
}

//跳转到易废圈
function toUrl(){
    if("${isSysUser}" == "true"){
        location="<%=appPath%>/enterprisemanagement/list.htm?ticketId=<%=ticketId %>";
    }else{
        location="<%=appPath%>/wastecircle/init.htm?ticketId=<%=ticketId %>&enterpriseId="+enterpriseId; 
    }
}

//显示信息
function hideTipMessage() {
    var notice = document.getElementById("tip_notice");
    $(notice).slideToggle();
    var tar = notice.getElementsByTagName("span")[0];
    tar.innerHTML = "";
}
</script>
