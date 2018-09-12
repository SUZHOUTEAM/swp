<%@page import="com.mlsc.waste.utils.Constant"%>
<%@page import="com.mlsc.waste.utils.CodeTypeConstant"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<link rel="stylesheet" href="<%=appPath %>/css/enterprise/enterprise_common.css">
<!-- Main section-->
<section>
    <div class="panel panel-body">
        <div class="row">
            <div style="text-align:center;width:100%;" id="tip">
                <span>根据您填的产废信息，为您推荐附近相关企业</span>
            </div>
            <div style="text-align:center;width:100%;">
                <button type="button" id="attentionWasteCircle" class="btn btn-primary" onclick="saveFollowAndEnterWasteCircle()">一键关注</button>
            </div>
            <div class="col-md-12">
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-10">
                    </div>
                </div>
            </div>
            <div id="enterpriseList" class="row">
                    <input type="hidden"><!-- 当前页码保存 -->
                    <div class="loadingDiv">
                    	<div class="loading"></div><div>loading...</div>
                    </div>
                </div>
            <div class="clearfix mb">
                <div class="btn-group pull-right">
                    <button id="pre"  class="btn btn-sm btn-default" onclick="getPrePage()"><span>上一页</span></button>
                    <button id="next" class="btn btn-sm btn-default" onclick="getNextPage()"><span>下一页</span></button>
                </div>
            </div>
        </div>
    </div>
</section>
<%------- 导入底部信息 -------%>
<%-- <jsp:include page="/common/bottom.jsp" flush="true"/> --%>
<!-- bootstrap-search-suggest -->
<script src="<%=appPath %>/thirdparty/bootstrap-suggest-plugin-master/dist/bootstrap-suggest.js"></script>
<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
var msg = "${msg}";
var status = "${status}";
$(document).ready(function(){
	initEnterpriseData(1);
});

//初始化加载数据
function initEnterpriseData(pageIndex){
	$('.loadingDiv').show();
    $.ajax({
        async: false,
        url: "<%=appPath %>/enterprise/initEnterpriseData.htm?ticketId=<%=ticketId %>&pageIndex=" + pageIndex,
        success: function(result) {        
        	$('.loadingDiv').hide();
            var obj = $.parseJSON(result); 
            if(obj.status == 1){
                var $enterpriselist = $("#enterpriseList");
                // 当前页保存
                $enterpriselist.find(":hidden").val(obj.data.paging.pageIndex);
                $enterpriselist.find(".enterpriseItem").remove();
                if (obj.data.enterpriseData.length > 0) {
                    for (var index in obj.data.enterpriseData) {
                        $enterpriselist.append(creatEnterpriseDiv(obj.data.enterpriseData[index]));
                    }
                    addEntTypeColor ();
                } else {
                    
                }
                // 上一页，下一页按钮控制
                initBtnDisabled(obj.data.paging);
            } else if(obj.status == 0){
                var options={"status":"info"};
                $.notify("关注企业页面初始化加载异常，请稍后再试",options);
            }
        }
    });
}

//创建一个通知信息显示项
function creatEnterpriseDiv(enterpriseVo){
	    var str='<div class="col-lg-4 col-md-6 follow enterpriseItem" >'+
        	'<div class="panel b">'+
            '<div class="media-box padding">'+
                '<div class="pull-left" >'+
                   '<img src="<%=appPath %>/app/img/business_icon/default_enterprise.png" alt="Image" class="img-responsive media-box-object img-circle thumb32" style="vertical-align: middle;">'+
                '</div>'+
                '<div class="media-box-body clearfix">'+
                   ' <p class="mb-sm">'+ ' <span class="entId hidden">'+enterpriseVo.entId+'</span>' + 
                       ' <span class="text-primary  pointer" onclick="_entDetail(' + enterpriseVo.entId  +')">'+enterpriseVo.entName+'</span>'+
                       "<img id= " + enterpriseVo.entId + " src='<%=appPath %>/app/img/icon/un_follow.png' onclick='changeImg("+enterpriseVo.entId+")' style='float: right;'>"+
                    '</p>'+
                    '<p class="mb-sm">'+
                        '<span style="font-size:85%;">'+enterpriseVo.entAddress+'</span>'+
                    '</p>'+
                    '<p class="mb-sm">';
                     if(enterpriseVo.entTypes.length > 0){
                         for (var index in enterpriseVo.entTypes) {
                        	 var className=enterpriseVo.entTypes[index].code;
                             var classText=enterpriseVo.entTypes[index].value;
                             var classNameStr=' '+className.toLowerCase()+'-badge';
                             str+='<span class="enterprise-type-suggest-badge '+className+classNameStr+'">'+classText+'</span>';
                         }
                    	 /* 
                    	 // var arr=enterpriseVo.enterpriseTypeValue.split(' ');
                    	 for(var i=0;i<arr.length;i++){
                    		 var entTypeIt=arr[i]; 
                    		 if(entTypeIt){
                    			 str+='<span class="badge '+parseType(entTypeIt)+'" >'+entTypeIt+'</span>';
                    		 }
                    	 } */
                     }
                    str+='</p>'+
                '</div>'+
            '</div>'+
        '</div>'+
    '</div>';
	    
    return $(str);
}
function parseType(type){
	var className='';
	switch (type){
		case '产废企业':
			className='bg-production';
			break;
		case '处置企业':
			className='bg-disposition';
			break;
		case '综合利用企业':
			className='bg-recycling';
			break;
		case '鉴定机构':
			className='bg-identification';
			break;
		case '运输企业':
			className='bg-transportation';
			break;
		default:
			break;
	}
	return className;
}
//上一页，下一页初始化控制
function initBtnDisabled(paging){
    var pageIndex = parseInt(paging.pageIndex);
    var totalPage = parseInt(paging.totalPage);
    if (totalPage <= 1) {
        $("#pre").attr("disabled","disabled");
        $("#next").attr("disabled","disabled");
    } else {
        if (pageIndex == totalPage) {
            $("#pre").removeAttr("disabled");
            $("#next").attr("disabled","disabled");
        } else if (pageIndex == 1) {
            $("#pre").attr("disabled","disabled");
            $("#next").removeAttr("disabled");
        } else {
            $("#pre").removeAttr("disabled");
            $("#next").removeAttr("disabled");
        }
    }
}

//获取上一页数据，下一页初始化控制
function getPrePage(){
    var pageIndex = parseInt($("#enterpriseList").find(":hidden").val());
    initEnterpriseData(pageIndex -1);
}
// 获取下一页数据，
function getNextPage(){
    var pageIndex = parseInt($("#enterpriseList").find(":hidden").val());
    initEnterpriseData(pageIndex + 1);
}


function changeImg(value){
	var action = null;
	var imgSrcValue = document.getElementById(value).src;
	if(imgSrcValue.indexOf("followed.png") == -1){
		document.getElementById(value).src = "<%=appPath%>/app/img/icon/followed.png";
		action = "1";
	}else{
		document.getElementById(value).src = "<%=appPath%>/app/img/icon/un_follow.png";
		action = "0";
	}
	 $.ajax({
        async: false,
        url: "<%=appPath %>/enterprise/saveOrRemoveFollow.htm?ticketId=<%=ticketId %>&enterpriseIdValue=" + value + "&action=" + action,
        success: function(result) {        
            var obj = $.parseJSON(result);
            msg = obj.data.msg;
            if(obj.status == 1){
            	var options={"status":"success"};
            	$.notify(msg,options);
            } else if(obj.status == 0){
            	var options={"status":"danger"};
                $.notify(msg,options);
            }
        }
    });
}

//一键关注，进入易废圈
function saveFollowAndEnterWasteCircle(){
    var entIdarr = [];
    $(".entId").each(function(){
        entIdarr.push ($(this).text());
    });
	$.ajax({
        async: false,
        url: "<%=appPath %>/enterprise/saveFollowAndEnterWasteCircle.htm?ticketId=<%=ticketId %>",
        data:{
            "entId":JSON.stringify(entIdarr)
        },
        success: function(result) {        
            var obj = $.parseJSON(result);
            msg = obj.data.msg;
            if(obj.status == 1){
            	var options={"status":"<%=Constant.STATUS_INFO%>"};
            	$.notify("成功关注所有企业",options);
            	setTimeout("enterWasteCircle()",1000);
            } else if(obj.status == 0){
            	var options={"status":"<%=Constant.STATUS_DANGER%>"};
                $.notify("<%=Constant.SYS_MSG%>",options);
            }
        }
    });
}

//给不同企业类型加上颜色标识
function addEntTypeColor(){
    $(".badge").each(function(){
        if ($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION%>")) {
            $(this).addClass("bg-production");
        } else if ($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_DISPOSITION%>")) {
            $(this).addClass("bg-disposition");
        } else if ($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_RECYCLING%>")) {
            $(this).addClass("bg-recycling");
        } else if ($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_IDENTIFICATION%>")) {
            $(this).addClass("bg-identification");
        } else if ($(this).hasClass("<%=CodeTypeConstant.ENTERPRISE_TYPE_TRANSPORTATION%>")) {
            $(this).addClass("bg-transportation");
        }
    });
}

//直接进入易废圈
function enterWasteCircle(){
	location="<%=appPath%>/wastecircle/init.htm?ticketId=<%=ticketId %>";
}

//给企业名称加一个点击事件，跳转到企业详情页面
function _entDetail(entid){
    location="<%=appPath%>/myenterprise/viewEnterpriseDetail.htm?ticketId=<%=ticketId %>&onlyViewFlg=true&enterpriseId="+entid;
}
</script>