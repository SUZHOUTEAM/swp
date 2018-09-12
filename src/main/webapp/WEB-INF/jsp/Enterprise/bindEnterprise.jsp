<%@page import="com.mlsc.waste.utils.CodeTypeConstant"%>
<%@page import="com.mlsc.waste.utils.Constant"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="我的企业信息" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myEnterprise" />
</jsp:include>

<link rel="stylesheet" href="<%=appPath %>/css/enterprise/enterprise_common.css?5">
<link rel="stylesheet" href="<%=appPath %>/css/wastecircle/index.css">
<!-- Main section-->
<style type="text/css">
</style>
<section>
    <div class="content-wrapper" style="margin-bottom: 60px">
        <div class="content-heading">
            <strong class="lead">我的企业</strong>
        </div>
        <%--<div class="bind-success-dialog">
            <div class="bind-success-content">
                <a href="<%=appPath %>/myenterprise/myEnterprise.htm?ticketId=<%=ticketId %>" class="bind_dialog_close"></a>
                <div class="bind_text"><i class="success_icon"></i>绑定企业成功！欢迎来到易废网~</div>
                <div style="text-align: center">
                    <a href="<%=appPath%>/entRelease/entWasteList.htm?ticketId=<%=ticketId%>" class="publish_btn redpack-btn">委托危废领取红包></a>
                </div>
                <img src="<%=appPath %>/main/pc/img/redpack-liucheng.png" class="procedure">
                <div style="text-align: center">
                    <a href="<%=appPath%>/wastecircle/init.htm?ticketId=<%=ticketId%>" class="view_btn">我先看看></a>
                </div>
            </div>
        </div>--%>
        <div class="bind-success-dialog">
            <div class="bind-success-content">
                <a href="<%=appPath %>/myenterprise/myEnterprise.htm?ticketId=<%=ticketId %>" class="bind_dialog_close"></a>
                <div class="bind_text"><i class="success_icon"></i>绑定企业成功！欢迎来到易废网~</div>
                <div style="text-align: center">
                    <a href="<%=appPath%>/entRelease/entWasteList.htm?ticketId=<%=ticketId%>" class="publish_btn">危废处置询价></a>
                </div>
                <img src="<%=appPath %>/main/pc/img/publishProcedure.jpg" class="procedure">
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <!-- 绑定企业 -->
                <div class="panel-heading text-center"><span>请绑定你所在的企业，完成绑定后即可开展危废业务</span></div>
                <%--<div class="load">搜索中...</div>--%>
                <div id="bindEnterprise" style="text-align: center;font-size: 14px;color: #333">输入企业名称看看你所在的公司是否已经在易废网系统中
                </div>
                <div class="panel-body">
                    <div class="row" id="serachTool">
                        <div class="col-md-offset-3 col-md-6 colcss" >
                            <input type="text" id="enterName" class="form-control" placeholder="输入您的企业名称" onblur="enterpriseCheck(this)"
                                   data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入您的企业名称"
                                   data-parsley-checkentname="true" data-parsley-checkentname-message="请输入更多的条件进行检索"/>
                        </div>
                        <div class="col-md-1" id="btnSearchEnterpriseDiv">
                            <button id="btnSearchEnterprise" class="btn" onclick="searchEnterprise(this)">查询企业</button>
                        </div>
                    </div>
                    <div class="row text-center prompt hidden"><span>已为您筛选出相关名称的企业，若未找到您的企业，可立即创建。</span></div>
                    <div class="row" id="enterpriseList">
                        
                    </div>
                    <div class="row text-center registerDiv hidden" style="margin-top: 40px;border-top: 1px solid #dddd;padding-top: 20px">
                        <div class="nodata-tip">
                            系统中没有查询到你所属的公司，需要你手动创建。<br/>
                            请先准备好公司的营业执照扫描图片。<br/>
                        </div>
	                    <a id="btnCreateEnrterprise" class="btn" href="<%=appPath %>/enterprise/add.htm?ticketId=<%=ticketId %>">立即创建</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />
<script src="<%=appPath %>/thirdparty/parsleyjs/dist/parsley.min.js"></script>
<script type="text/javascript" src="<%=appPath %>/app/js/constants.js"></script>
<script type="text/javascript">
var enterNameRegStart = new RegExp(/^(公司|企业|集团)+/g);// 企业名称搜索时，过滤掉【公司，企业，集团关键字】
var enterNameRegEnd= new RegExp(/(公司|企业|集团)+$/g);
var entNameCheck = true;//企业名称搜索时，过滤掉【公司，企业，集团关键字】后，企业名称为空时报错
$(document).ready(function(){
  var rs = window.globalInit();
  rs.done(function () {
//    $("#bindEnterprise").html(window.globalParam.reminder);
    window.Parsley.addValidator('checkentname', function(value){
      return entNameCheck;
    }, 32);
      var flag=/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent);
      if(flag){
        $('#btnSearchEnterpriseDiv').css('margin-top','20px');
      }
  });

});

function enterpriseCheck(that){
    if ($(that).val() != "" && $.trim($(that).val().replace(enterNameRegStart,'').replace(enterNameRegEnd,'')) == "") {
        entNameCheck = false;
    } else {
        entNameCheck = true;
    }
    $(that).parsley().validate();
}
function searchEnterprise(that) {
    $("#enterName").parsley().validate();
    if ($("#enterName").parsley().isValid()) {
    	$(".registerDiv").addClass("hidden");
        loadEnterpriseList();
    }
}
function loadEnterpriseList(){
    var $enterpriselist = $("#enterpriseList");
    var $prompt = $(".prompt");
    $enterpriselist.addClass("whirl");
    $('.loading').show();
    $enterpriselist.html('');
    $prompt.addClass("hidden");
    $.page.ajax($.page.getAjaxSettings({
        async: true,
        type: "POST",
        dataType: "json",
        url: "<%=appPath %>/myenterprise/loadEnterpriseList.htm",
        data: {
            "enterpriseName": $.trim($("#enterName").val().replace(enterNameRegStart,'').replace(enterNameRegEnd,''))
        },
        success: function (result, textStatus, jqXHR) {
            $enterpriselist.find(".panel.panel-default").remove();
            $('.loading').hide();
            if (result.data.enterpriselist.length > 0) {
                $enterpriselist.removeClass("hidden");
                $prompt.removeClass("hidden");
                for (var index in result.data.enterpriselist) {
                    $enterpriselist.append(creatEnterpriseItem(result.data.enterpriselist[index]).prop("outerHTML"));
                }
                $prompt.removeClass("hidden").find("span").text("已为您筛选出以下相关名称的企业");
                $(".registerDiv").removeClass("hidden");
            } else {
                $enterpriselist.addClass("hidden");
                $(".registerDiv").removeClass("hidden");
                $prompt.removeClass("hidden").find("span").text("未搜索到相关名称的企业");
            }
        },
        complete: function (jqXHR, textStatus) {
            $enterpriselist.removeClass("whirl");

            /*var result = jqXHR.responseJSON;
            if (result.status != 1) {
                var options={"status":"<%=Constant.STATUS_INFO %>"};
                $.notify("企业页面列表加载异常，请稍后再试",options);
            }*/
        }
    }));
}

function creatEnterpriseItem(rpcSysEnterpriseBase){
	var imgUrl='<%=appPath %>/app/img/business_icon/default_enterprise.png';
	if(rpcSysEnterpriseBase.businessCode&&rpcSysEnterpriseBase.fileId){
		imgUrl=IMG_VIEW_URL+'&businessCode='+rpcSysEnterpriseBase.businessCode+'&fileId='+rpcSysEnterpriseBase.fileId;
	}
    var $panel = $("<div class='panel panel-default enterpriseItem' id='" + rpcSysEnterpriseBase.entId + "'></div>").append("<div class='panel-body'></div>");
    var $col_img = $("<div class='col col-md-1 colcss col-img'></div>");
    $col_img.append("<img src='"+imgUrl+"' alt='企业logo' class='img-responsive media-box-object img-circle' style='vertical-align: middle;'>");
    var $col_ent = $("<div class='col col-md-6 colcss col-ent'></div>");
    var str='';
    if(rpcSysEnterpriseBase.entTypeCode){
    	var str1='enterprise-type-badge '+rpcSysEnterpriseBase.entTypeCode+' '+rpcSysEnterpriseBase.entTypeCode.toLowerCase()+'-badge';
    	str='<span class="'+str1+'">'+rpcSysEnterpriseBase.entTypeValue+'</span>'
    }
    $col_ent.append("<div class='item_name'><span class='ent-name'>" + rpcSysEnterpriseBase.entName +str+ '</span>');
    $col_ent.append("<div class='item_address'><span class='addr'><em class='areaicon'></em>" + (rpcSysEnterpriseBase.entAddress||'--') + "</span></div>");
    if (rpcSysEnterpriseBase.contactsTel) {
        $col_ent.append("<div class='item_phone'><span class='tel'><em class='telicon'></em>" + rpcSysEnterpriseBase.contactsTel + "</span></div>");
    }
    
    var $col_btn = $("<div class='btn-group pull-right'></div>");
    $col_btn.append("<button class='btn view' onclick='viewEnterprise(this)'>查看</button>");
    $col_btn.append('<button class="btn bound" onclick="boundEnterprise(this,\''+rpcSysEnterpriseBase.entTypeCode+'\')">绑定</button>');
    
    $panel.find(".panel-body").append($col_img.prop("outerHTML")).append($col_ent.prop("outerHTML")).append($col_btn.prop("outerHTML"));
    return $panel;
}

//加载企业类型
function loadingEntType($entSelector, enterpriseId){
    $.page.ajax($.page.getAjaxSettings({
        async: true,
        type: "POST",
        dataType: "json",
        url: "<%=appPath %>/myenterprise/getEnterpriseTypesByEntId.htm",
        data: {
            "enterpriseId": enterpriseId
        },
        success: function (result, textStatus, jqXHR) {
            if (result.data.enterpriseTypes && result.data.enterpriseTypes.length > 0) {
                for (var index in result.data.enterpriseTypes) {
                    var codevalue = result.data.enterpriseTypes[index];
                    $entSelector.after("<span class='enterprise-type-badge " + codevalue.code + "'>" + codevalue.value + "</span>");
                }
                badgeRenderer($(".enterprise-type-badge"));
                $(".enterprise-type-badge").each(function() {
                    if ($(this).hasClass("PRODUCTION")) {
                        $(this).parents(".enterpriseItem").addClass("panel-PRODUCTION");
                    } else if ($(this).hasClass("DISPOSITION")) {
                        $(this).parents(".enterpriseItem").addClass("panel-DISPOSITION");
                    } else if ($(this).hasClass("RECYCLING")) {
                        $(this).parents(".enterpriseItem").addClass("panel-RECYCLING");
                    } else if ($(this).hasClass("IDENTIFICATION")) {
                        $(this).parents(".enterpriseItem").addClass("panel-IDENTIFICATION");
                    } else if ($(this).hasClass("TRANSPORTATION")) {
                        $(this).parents(".enterpriseItem").addClass("panel-TRANSPORTATION");
                    }
                });
            }
        },
        complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                var options={"status":"<%=Constant.STATUS_INFO %>"};
                $.notify("企业类型数据加载过程中发生了异常，稍后再试",options);
            }
        }
    }));
}

//加载企业图片
function loadingEntImg($imgSelector, enterpriseId){
    $.page.ajax($.page.getAjaxSettings({
        async: true,
        type: "POST",
        dataType: "json",
        url: "<%=appPath %>/myenterprise/getEnterImgSrc.htm",
        data: {
            "enterpriseId": enterpriseId
        },
        success: function (result, textStatus, jqXHR) {
            if (obj.data.imgSrc) {
                $imgSelector.attr("src",obj.data.imgSrc);
            }
        },
        complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                var options={"status":"<%=Constant.STATUS_INFO %>"};
                $.notify("企业图像数据加载过程中发生了异常，稍后再试",options);
            }
        }
    }));
}

function viewEnterprise(that){
    location="<%=appPath %>/myenterprise/viewOwnEnterprise.htm?ticketId=<%=ticketId %>&enterpriseId=" + $(that).parents(".enterpriseItem").attr("id") + "&dispalyAddBtn=true";
}

function boundEnterprise(that,entTypeCode){
    var berforeText = $(that).text();
    $(that).after("<i class='btn_loading'></i>").text("绑定中...").parent().find("button").attr("disabled","disabled");
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "<%=appPath %>/myenterprise/joinEnterprise.htm",
        data: {
            "enterpriseId": $(that).parents(".enterpriseItem").attr("id")//待加入企业的ID
        },
        success: function (result, textStatus, jqXHR) {
            localStorage.entType=entTypeCode;
            localStorage.userId=globalInit.userId;
            localStorage.ticketId='<%=ticketId%>';
            if(entTypeCode=='PRODUCTION'){
                var flag=/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent);
                if(flag){
                    $.notify("绑定企业成功",{"status":"success",timeout:2000});
                    if(isWeixin()&&localStorage.openId&&localStorage.bindStatus=='NOENT'){
                        localStorage.bindStatus='';
                    }
                    setTimeout(function () {
                        window.location='/mobile/#/publish';
                    },1000)
                }else{
                    $('.bind-success-dialog').show();
                    $('.btn_loading').hide();
                    $(that).text('绑定');
                }
            }else{
                location = "<%=appPath %>/myenterprise/myEnterprise.htm?ticketId=<%=ticketId %>"
            }
        },
        complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                var options={"status":"<%= Constant.STATUS_INFO%>","pos":"top-center"};
                $.notify("绑定企业过程中发生了异常:" + result.infoList[0],options);
                $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                $(that).next(".btn_loading").remove();
            }
        }
    }));
}
function isWeixin() {
    var ua = navigator.userAgent.toLowerCase();
    var isWeixin = ua.indexOf('micromessenger') != -1;
    if (isWeixin) {
        return true;
    }else{
        return false;
    }
}
</script>