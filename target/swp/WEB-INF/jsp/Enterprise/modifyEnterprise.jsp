<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
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
<link rel="stylesheet" href="<%=appPath %>/css/enterprise/enterprise_common.css?2">
<style>
.form-horizontal .control-label {
    margin-bottom: 0;
    padding-top: 7px;
    font-weight:normal;
}
.map{
    margin-top: 10px;
    width: 800px;
    height: 400px;
    top: 50;
    border: 1px solid gray;
    overflow: hidden;
  }
  .btn_search{
     width: 80px;
     height: 36px;
     background-color: #23a9c6;
     font-size: 16px;
     font-weight: bold;
     color: #fff;
     font-family: "Microsoft YaHei";
     border: none;
  }
  .btn_closeMap{
     width: 80px;
     height: 36px;
     background-color: #d1d4e5;
     font-size: 16px;
     font-weight: bold;
     color: #fff;
     font-family: "Microsoft YaHei";
     border: none;
  }
.disposition-badge {
    margin-top: 5px;
}
.production-badge {
    margin-top: 5px;
}
.recycling-badge{
    margin-top: 5px;
}
.identification-badge{
    margin-top: 5px;
}
 #uploadImage{
    position: absolute;
    background: #FF0000;
    left: 0;
    right: 0;
    margin: 0 auto;
    width: 89px;
    height: 33px;
    opacity: 0;
    filter:alpha(opacity=0);
}
#uploadImage2{
    position: absolute;
    background: #FF0000;
    left: 0;
    margin: 0 auto;
    width: 140px;
    height: 33px;
    margin-left: 15px;
    opacity: 0;
    //filter:alpha(opacity=0);
}
#uploadImage.second{
    left:-94px;
}
#uploadImage2.second{
    width:85px;
}
#hasSelectedImg,#hasSelectedImg2{
    display: none;
}
#uploadPreview{
    margin:0 auto 10px;
    width:85%;
}
#uploadPreview2{
    margin:10px auto;
    width:100%;
}
#uploadPreview2 img{
    max-width:100px;
}
#uploadPreview2 img.second{
    max-width:100%;
}
#uploadPreview img{
    max-width: 100px;
}
input:read-only{
     background-color: #ffffff!important;
     border: none;
 }
.review{
    display: block;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
    border: 1px dotted #ddd;
}
</style>
<!--[if lt IE 9]>
<style type="text/css">
    #uploadImage2 {
        filter:alpha(opacity=0);
    }
</style>
 <![endif]-->
<link href="<%=appPath %>/thirdparty/city-picker/css/city-picker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=appPath %>/thirdparty/map/js/api.js"></script>

<!-- Main section-->
<section>
    <div class="hidden"><!-- city-picker选择时获取数据参数的设定 ID不能改变 -->
        <input type='hidden' id="appPath" value="<%=appPath %>"/>
        <input type='hidden' id="ticketId" value="<%=ticketId %>"/>
    </div>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">${isSysUser?'权限管理':'我的企业'}</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="back();">企业管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">企业信息维护</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div class="dialog">
        <a href="javascript:" class="close-dialog" title="关闭" onClick="$('.dialog').hide()">×</a>
        <img  class="largeImg"/>
    </div>
    <!-- Page content-->
    <div class="panel panel-body">
        <%--<div class="content-heading">--%>
            <%--<strong class="lead">企业管理</strong>--%>
            <%--<small data-localize="dashboard.WELCOME">企业信息维护</small>--%>
        <%--</div>--%>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default" style="border-top-width:0">
                    <div style="background-color: #b5d0ff" class="bg-cover">
                        <div class="p-xl text-center text-primary">
                            <div id="uploadPreview">
                                <img src="<%=appPath%>/main/pc/img/company_default.png">
                            </div>
                            <form action="" id="myform" method="post" enctype="multipart/form-data" style="margin-top: 6px;">
                                <input type="hidden" id="appkey" name="appKey" value="5da5441f62e48aedc7a3853ffc75c2db"/>
                                <input type="hidden" id="proId" name="prodID" value="gf"/>
                                <input type="hidden" id="businessCode" name="businessCode" value="njknnvskAWD"/>
                                <!--<input type="hidden" id="name" name="name" value="营业执照" />-->
                                <input id="uploadImage" type="file" class="fimg1" name="file" />
                                <a id="addImg" class="btn btn-default">修改logo</a>
                                <div id="hasSelectedImg">
                                    <a id="edit" class="btn btn-default">重新选择</a>
                                    <a href="javascript:void(0);" id="uploadImg" class="btn btn-default">确认上传</a>
                                </div>
                            </form>
                        </div>
                    </div>
                <div class="panel-body">
                    <form:form class="form-horizontal" modelAttribute="enterprise" id="editForm" data-parsley-validate="true">
	                    <div class="form-group">
                            <label class="col-md-2 control-label text-left">企业名称*</label>
							<div class="col-md-8">
                                <form:input path="entId" id="enterpriseid" name="entId" type="hidden" />
                                    <form:input path="entName" id="enterprisename" name="enterprisename" type="text" placeholder="输入企业名称" class="form-control" maxlength="20"
                                                data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入企业名称" />
							</div>
	                    </div>
						<div class="form-group">
						    <label class="col-md-2 control-label text-left">企业代码*</label>
						    <div class="col-md-8">
						        <form:input path="entCode" id="enterprisecode" name="enterprisecode" type="text" placeholder="输入企业代码" class="form-control" maxlength="18"
                                            data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入企业代码"
                                            data-parsley-checkcode="true" data-parsley-checkcode-message=""/>
						     </div>
						</div>
						<div class="form-group">
                            <label class="col-md-2 control-label text-left">企业法人*</label>
						    <div class="col-md-8">
						        <form:input path="legalName" type="text" id="legalName" name="legalName" class="form-control" maxlength="20" data-parsley-required="required" data-parsley-required-message="请输入企业法人"/>
						    </div>
						</div>
						<div class="form-group">
                            <label class="col-md-2 control-label text-left">联系电话</label>
						    <div class="col-md-8">
						        <form:input path="contactsTel" type="text" id="contactsTel" name="contactsTel" class="form-control" data-parsley-required-message="请输入联系电话"/>
						    </div>
						</div>
						<div class="form-group">
                            <label class="col-md-2 control-label text-left">企业类型*</label>
							<div class="col-md-8">
                                <c:if test="${enterprise.entType == 'PRODUCTION'}">
                                    <span class="enterprise-type-badge production-badge">产废企业</span>
                                </c:if>
                                <c:if test="${enterprise.entType == 'DISPOSITION'}">
                                    <span class="enterprise-type-badge disposition-badge">处置企业</span>
                                </c:if>
                                <c:if test="${enterprise.entType == 'RECYCLING'}">
                                    <span class="enterprise-type-badge recycling-badge">综合利用企业</span>
                                </c:if>
                                <c:if test="${enterprise.entType == 'IDENTIFICATION'}">
                                    <span class="enterprise-type-badge identification-badge">鉴定、检测机构</span>
                                </c:if>
                                <c:if test="${enterprise.entType == 'FACILITATOR'}">
                                    <span class="enterprise-type-badge facilitator-badge" style="margin-top: 6px">服务商</span>
                                </c:if>
                                <c:if test="${enterprise.entType == 'DIS_FACILITATOR'}">
                                    <span class="enterprise-type-badge dis_facilitator-badge" style="margin-top: 6px">处置服务商</span>
                                </c:if>
							    <%--<span id="modifyEntTypeBtn" style="position: absolute;padding-top: 5px;padding-left: 10px;">--%>
							        <%--<a href="<%=appPath%>/enterprise/modifyEnterpriseType.htm?ticketId=<%=ticketId %>&modifyEnterpriseId=<%=enterpriseId %>">修改企业类型</a>--%>
							    <%--</span>--%>
							</div>
						</div>
						<div class="form-group">
                            <label class="col-md-2 control-label text-left">行政区*</label>
							<div class="col-md-8" style="position: relative;">
							    <form:input path="cantonCode" name="cantonCode" type="text" class="form-control" data-toggle="city-picker"/>
							</div>
						</div>
						<div class="form-group">
						    <label class="col-md-2 control-label text-left">企业位置*</label>
						    <div class="col-md-8">
                                <form:input path="entAddress" type="text" id="enterpriseAddress" name="enterpriseAddress" class="form-control"  data-parsley-required="required"
                                            placeholder="选择企业位置" data-parsley-required-message="选择企业位置"/>
						    </div>
						</div>
                    </form:form>
                    <form action="" id="myform2" class="form-horizontal" method="post" enctype="multipart/form-data" style="margin-top: 6px;">
                        <div class="form-group">
                            <label class="col-md-2 control-label">营业执照*</label>
                            <div class="col-md-8">
                                <input id="uploadImage2" type="file" class="fimg1" name="file" />
                                <a id="addImg2" class="btn btn-default">修改营业执照图片</a>
                                <input type="hidden" id="appkey" name="appKey" value="5da5441f62e48aedc7a3853ffc75c2db"/>
                                <input type="hidden" id="proId" name="prodID" value="gf"/>
                                <input type="hidden" id="businessCode2" name="businessCode" value="njknnvskAWD"/>
                                <div id="hasSelectedImg2">
                                    <a id="edit" class="btn btn-default">重新选择</a>
                                    <a href="javascript:void(0);" id="uploadImg2" class="btn btn-default">确认上传</a>
                                </div>
                                <div id="uploadPreview2">
                                    <a href="javascript:void(0);" title="点击查看大图" class="review" onclick="showLargeImg()"><img src="http://www.yifeiwang.com/img/source/upload.png"></a>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div id="sysListFile"></div>
                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-10">
                            <button type="button" id="confirm" class="btn btn-primary" onclick="updateEnterprsie(this)">确定</button>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<%------- 结束导入底部信息 -------%>
<script src="<%=appPath %>/thirdparty/parsleyjs/dist/parsley.min.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/main/common/jquery.form.js"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.data.js"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.js"></script>

<script type="text/javascript">
var msg = "${msg}";
var status = "${status}";
var enterpriseId = "${enterprise.entId}";
var map;//map实例
var localSearch;
var addr;//地址信息
var marker;//标注点
var cityName;//当前城市
var imgType='a';
var imgType2='b';
var businessCode=imgType+enterpriseId;
var businessCode2=imgType2+enterpriseId;
var ticketId="<%=ticketId %>";
var error = true;
function showLargeImg() {
    $('.dialog img').attr('src',$('#uploadPreview2 img').attr('src'));
    $('.dialog').show();
}
//保存图片信息到图片表中
function saveImg(fileFingerPrint,fileID){
    $.ajax({
        url: "<%=appPath %>/fileUpload/updateFileByBusinessCode",
        data: {
            'businessCode' :businessCode,
            'fileFingerPrint':fileFingerPrint,
            'fileId':fileID,
            'fileType':imgType,
            'referenceId':enterpriseId,
            'ticketId':ticketId
        },
        type: "POST",
        dataType: 'json',
        async: false,
        success: function(data) {
            if(data.status==1){
                $.notify("修改logo成功",{status:'success'});
                $("#uploadPreview img").attr('src',IMG_VIEW_URL+'businessCode='+businessCode+'&fileID='+fileID);
                $('#addImg').show();
                $('#uploadImage').removeClass('second');
                $('#hasSelectedImg').hide();
            }else{
                $.notify("修改logo失败",{status:'danger'});
            }
        },
        error: function(er) {
            $.notify("保存图片信息出错",{status:'danger'});
        }
    });
}
function saveImg2(fileFingerPrint,fileID){
    $.ajax({
        url: "<%=appPath %>/fileUpload/updateFileByBusinessCode",
        data: {
            'businessCode' :businessCode2,
            'fileFingerPrint':fileFingerPrint,
            'fileId':fileID,
            'fileType':imgType2,
            'referenceId':enterpriseId,
            'ticketId':ticketId
        },
        type: "POST",
        dataType: 'json',
        async: false,
        success: function(data) {
            if(data.status==1){
                $.notify("修改营业执照成功",{status:'success'});
                var imgurl=IMG_VIEW_URL+'businessCode='+businessCode2+'&fileID='+fileID;
                $("#uploadPreview2 img").attr('src',imgurl);
                $('#addImg2').show();
                $('#uploadImage2').removeClass('second');
                $('#hasSelectedImg2').hide();
            }else{
                $.notify("修改logo失败",{status:'danger'});
            }
        },
        error: function(er) {
            $.notify("保存图片信息出错",{status:'danger'});
        }
    });
}
$(document).ready(function(){
  var rs = window.globalInit();
  rs.done(function () {
//    if(window.globalParam.enterStatus === "PASS"){
//      $("#modifyEntTypeBtn").remove();
//      $("#enterprisecode").attr("readonly","readonly");
//      $("#enterprisename").attr("readonly","readonly");
//      $("#addImg2").attr("disabled","disabled");
//    }else{
//      //企业名称校验
//      $("#enterprisename").blur(function() {
//        $("#enterprisename").parsley().validate();
//        validateButton();
//      });
//
//      //企业代码校验
//      $("#enterprisecode").blur(function() {
//        validateByCode();
//        $("#enterprisecode").parsley().validate();
//        validateButton();
//      });
//    }
    init();
  });
});
function init() {
  $('[data-toggle="city-picker"]').citypicker({
    responsive:true
  });
  $('#businessCode').val(businessCode);
  $('#businessCode2').val(businessCode2);
  $('#appkey').val(APPKEY);
  $('#prodId').val(PRODID);
  initImg();
  $('#uploadImage').change(function(){
    var files = !!this.files ? this.files : [];
      if(files.length==1&&FILETYPE.indexOf(files[0].type)==-1){
          $.notify(TYPE_ERROR_MSG, {status:'info',timeout:1500 });
          return;
      }
    if (!files.length || !window.FileReader) return;
    $('#addImg').hide();
    $('#uploadImage').addClass('second');
    $('#hasSelectedImg').show();

    if (/^image/.test( files[0].type)){
      $('#uploadImg').click();
      var reader = new FileReader();
      reader.readAsDataURL(files[0]);
      reader.onloadend = function(){
        $("#uploadPreview img").attr('src',this.result);
      }
    }
  });
  $('#uploadImage2').change(function(){
    var files = !!this.files ? this.files : [];
      if(files.length==1&&FILETYPE.indexOf(files[0].type)==-1){
          $.notify(TYPE_ERROR_MSG, {status:'info',timeout:1500 });
          return;
      }
    if (!files.length || !window.FileReader) return;
    $('#addImg2').hide();
    $('#uploadImage2').addClass('second');
    $('#hasSelectedImg2').css('display','inline-block');
    if (/^image/.test( files[0].type)){
      $('#uploadImg2').click();
      var reader = new FileReader();
      reader.readAsDataURL(files[0]);
      reader.onloadend = function(){
        $("#uploadPreview2 img").attr('src',this.result);
      }
    }
  });
  $('#uploadImg').click(function(){
    $('#myform').ajaxSubmit({
      url:IMG_PREV+UPLOAD_ACTION,
      type:'POST',
      async:false,
      success:function(data) {
        if(data.success){
          //开始插入数据
          saveImg(data.message.fileFingerPrint,data.message.fileID);
        }else{
          $.notify("上传失败",{status:'danger'});
        }
      },
      error: function(err) {
        $.notify(err.message,{status:'danger'});
      }
    });
  });
  $('#uploadImg2').click(function(){
    $('#myform2').ajaxSubmit({
      url:IMG_PREV+UPLOAD_ACTION,
      type:'POST',
      async:false,
      success:function(data) {
        if(data.success){
          //开始插入数据
          saveImg2(data.message.fileFingerPrint,data.message.fileID);
        }else{
          $.notify("上传失败",{status:'danger'});
        }
      },
      error: function(err) {
        $.notify(err.message,{status:'danger'});
      }
    });
  });
  if (msg != "") {
    var options={"status":status};
    $.notify(msg,options);
  }

  // 企业代码重复性验证
  window.Parsley.addValidator('checkcode', function(value){
    return error;
  }, 32);





  // 初始情况下，按钮不可用
  $("#saveExit").attr("disabled","disabled");
}
function initImg(){
	$.ajax({
		url: "<%=appPath %>/fileUpload/getFileByFileTypeAndReferenceId",
		data: {
			'referenceId' :enterpriseId,
			'fileType':'a'
		},
		type: "POST",
		dataType: 'json',
		async: true,
		success: function(data) {
			if(data.status==1){
				var uploadFileInfo=data.data.uploadFileInfo;
				if(uploadFileInfo&&uploadFileInfo.length>0){
					var obj=uploadFileInfo[0];
					$("#uploadPreview img").attr('src',IMG_VIEW_URL+'&businessCode='+obj.businessCode+'&fileID='+obj.fileId);
				}
			}
		}
	});
	$.ajax({
		url: "<%=appPath %>/fileUpload/getFileByFileTypeAndReferenceId",
		data: {
			'referenceId' :enterpriseId,
			'fileType':'b'
		},
		type: "POST",
		dataType: 'json',
		async: true,
		success: function(data) {
			if(data.status==1){
				var uploadFileInfo=data.data.uploadFileInfo;
				if(uploadFileInfo&&uploadFileInfo.length>0){
					var obj=uploadFileInfo[0];
					var imgurl=IMG_VIEW_URL+'&businessCode='+obj.businessCode+'&fileID='+obj.fileId;
                    $("#uploadPreview2 img").attr('src',imgurl);
				}
			}
		}
	});

}
function updateEnterprsie(that){
  if(!validate()){
    return;
  }
  var cantonCode = $("#cantonCode").val();
  if(!cantonCode){
    var options={"status":"danger"};
    $.notify("请选择企业所在行政区",options);
    return;
  }

		var berforeText = $(that).text();
    	$(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
        $.ajax({
            url: "<%=appPath%>/enterprise/updateEnterpriseInfo.htm?etc="+new Date().getTime() + "&ticketId=<%=ticketId %>",
            dataType:'json',
            data: $('form').serialize(),
            success: function(result){
                 if(result.status == 1){
                   localStorage.province=cantonCode.substring(0,2);
                   var options={"status":"success"};
                   $.notify(result.infoList[0],options);
                   back();
               }else{
                   var options={"status":"danger"};
                   $.notify(result.infoList[0],options);
                   $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                   $(that).prev(".btn_loading").remove();
             }
            }
      });
}

//输入代码是否重复
function validateByCode(){
    $.ajax({
          url: "<%=appPath%>/enterprise/isEnterprisecodeExistent.htm?etc="+new Date().getTime() + "&ticketId=<%=ticketId %>",
          dataType:'json',
          data: $('form#editForm').serialize(),
          success: function(result){
                var obj = result;
                if(obj.status == 0){
                    error = false;
                    $("#enterprisecode").attr("data-parsley-checkCode-message",obj.data.msg);
                }  else {
                    error = true;
                    $("#enterprisecode").attr("data-parsley-checkCode-message","");
                }
          }
    });
}
function back(){
    if("${isSysUser}" == "false"){
        location="<%=appPath%>/myenterprise/myEnterprise.htm?ticketId=<%=ticketId %>";
    }else{
        location="<%=appPath%>/enterprisemanagement/list.htm?ticketId=<%=ticketId %>";
    }
}

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
    } else {
        $("#saveExit").attr("disabled","disabled");
    }
}




</script>