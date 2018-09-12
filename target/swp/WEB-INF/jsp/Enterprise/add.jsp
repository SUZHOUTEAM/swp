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
<link rel="stylesheet" href="<%=appPath %>/css/enterprise/enterprise_common.css?2">
<link href="<%=appPath %>/thirdparty/city-picker/css/city-picker.css" rel="stylesheet" type="text/css" />

<!-- Main section-->
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="history.go(-1);">企业管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">企业管理新增</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div class="dialog">
        <a href="javascript:" class="close-dialog" title="关闭" onClick="$('.dialog').hide()">×</a>
        <img  class="largeImg"/>
    </div>
   <!-- Page content-->
   <div class="panel panel-body">
      <div class="row">
          <%------- 导入按钮工具栏 -------%>
          <div class="col-md-12">
          <div class="panel panel-default">
              <div class="panel-heading">创建企业</div>
              <div class="panel-body">
              <form:form class="form-horizontal" modelAttribute="enterprise" id="editForm" data-parsley-validate="true">
              		 <input type="hidden" name="businessCode" id="enterBusinessCode"/>
                      <div class="form-group">
                          <label class="col-md-2 control-label text-left">企业名称*</label>
                          <div class="col-md-10">
                              <input id="enterprisename" name="enterprisename" type="text" placeholder="输入企业名称" class="form-control" maxlength="20" 
                              data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入企业名称" 
                              data-parsley-checkcode="true" data-parsley-checkcode-message="">
                            </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-2 control-label text-left">企业代码*</label>
                          <div class="col-md-10">
                              <input id="enterprisecode" name="enterprisecode" type="text" placeholder="输入企业组织机构代码或统一社会信用代码" class="form-control" maxlength="18" 
                              data-trigger="change" data-parsley-required="required" data-parsley-required-message="请输入企业代码" 
                              data-parsley-checkcode="true" data-parsley-checkcode-message="">
                            </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-2 control-label text-left">行政区*</label>
                          <div class="col-md-8" style="position: relative;">
                              <input path="cantonCode" name="cantonCode" type="text" class="form-control" data-toggle="city-picker"/>
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-2 control-label text-left">企业位置*</label>
                          <div class="col-md-8">
                              <input path="enterpriseAddress" name="enterpriseaddress" type="text" placeholder="请选择企业位置"
                                          class="form-control"  placeholder="选择企业位置" data-parsley-required-message="选择企业位置"/>
                          </div>
                      </div>
                      <div class="form-group" style="margin-bottom: 0;">
                          <label class="col-md-2 control-label text-left"></label>
                          <div class="col-md-8">
                              <div id="map" style="display: none">
                                  <div id="allmap" class="map">
                                  </div>
                              </div>
                          </div>
                      </div>
                    </form:form>
                    <form action="" id="myform2" class="form-horizontal" method="post" enctype="multipart/form-data" style="margin-top: 6px;">
                        <div class="form-group">
                            <label class="col-md-2 control-label">营业执照</label>
                            <div class="col-md-8">
                                <input type="hidden" id="appkey" name="appKey" value="5da5441f62e48aedc7a3853ffc75c2db"/>
                                <input type="hidden" id="proId" name="prodID" value="gf"/>
                                <input type="hidden" id="businessCode" name="businessCode" value=""/>
                                <div style="color:#5d9cec;margin-left: 6px;margin-top: 7px;">只支持png,jpg,gif的图片格式</div>
                                <input id="uploadImage2" type="file" class="fimg1" name="file" />
                                <div id="uploadPreview2">
                                    <a href="javascript:void(0);" title="点击查看大图" class="review"><img src="http://www.yifeiwang.com/img/source/upload.png"></a>
                                </div>
                            </div>
                        </div>
                    </form>
                      <div class="form-group">
                          <div class="col-md-offset-2 col-md-10">
                               <label class="control-label">(*)&nbsp;&nbsp;必须输入</label>
                          </div>
                      </div>
                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-10">
                            <button type="button" id="saveExit" class="btn btn-primary" onclick="saveEnterpriseInfo(this)">保存</button>
                            <button type="button" id="cancel" class="btn btn-default" onclick="history.back(-1);">取消</button>
                        </div>
                    </div>

                </div>
            </div>
            </div>
          </div>
          </div>
</section>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<%--
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>

--%><script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.data.js"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.js"></script>
<script src="<%=appPath %>/thirdparty/parsleyjs/dist/parsley.min.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/main/common/jquery.form.js"></script>
<script type="text/javascript" id="enterpriseScript">
var msg = "";
var status = "";
var enterpriseId = "${enterpriseId}";
var map;//map实例
var localSearch;
var addr;//地址信息
var marker;//标注点
var cityName;//当前城市
var error = true;
var businessCode='';
var ticketId='<%=ticketId%>';
$(document).ready(function(){
  var rs = window.globalInit();
  rs.done(function () {
    init();
  });

});
function init() {

    $('[data-toggle="city-picker"]').citypicker({
        responsive:true
    });
  if (msg != "") {
    var options={"status":status};
    $.notify(msg,options);
  }

  // 企业代码重复性验证
  window.Parsley.addValidator('checkcode', function(value){
    return error;
  }, 32);

  //企业名称校验
  $("#enterprisename").blur(function() {
    $("#enterprisename").parsley().validate();
    validateButton();
  });

  //企业位置校验
  $("#enterpriseAddress").blur(function() {
    $("#enterpriseAddress").parsley().validate();
    validateButton();
  });

  //企业代码校验
  $("#enterprisecode").blur(function() {
    validateByCode();
    $("#enterprisecode").parsley().validate();
    validateButton();
  });


  $("#enterpriseAddress").val("");
  $("#latitudeAndLongitude").val("");
  $("#regionalism").val("");

  $("#province").val("");
  $("#city").val("");
  $("#district").val("");

  // 初始情况下，按钮不可用
  $("#saveExit").attr("disabled","disabled");
  bindImgEvent();
}
function showLargeImg() {
    $('.dialog img').attr('src',$('#uploadPreview2 img').attr('src'));
    $('.dialog').show();
}
function bindImgEvent(){
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
//            $('#uploadImg2').click();
            var reader = new FileReader();
            reader.readAsDataURL(files[0]);
            reader.onloadend = function(){
                $("#uploadPreview2 img").attr('src',this.result);
            }
        }
    });
    $('#uploadImg2').click(function(){
    	businessCode='b'+enterpriseId;
    	$('#businessCode').val(businessCode);
        $('#myform2').ajaxSubmit({
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
}
function saveImg(fileFingerPrint,fileID){
    $.ajax({
        url: "<%=appPath %>/fileUpload/updateFileByBusinessCode",
        data: {
            'businessCode' :'b'+enterpriseId,
            'fileFingerPrint':fileFingerPrint,
            'fileId':fileID,
            'fileType':'b',
            'referenceId':enterpriseId,
            'ticketId':ticketId
        },
        type: "POST",
        dataType: 'json',
        async: false,
        success: function(data) {
            if(data.status==1){
//                $.notify("上传营业执照成功",{status:'success',timeout:1000});
//                var imgurl=IMG_VIEW_URL+'businessCode='+businessCode+'&fileID='+fileID;
//                $("#uploadPreview2 img").attr('src',imgurl);
//                $('#addImg2').show();
//                $('#uploadImage2').removeClass('second');
//                $('#hasSelectedImg2').hide();
//                $('#enterBusinessCode').val(businessCode);
            }else{
//                $.notify("上传营业执照失败",{status:'danger'});
            }
        },
        error: function(er) {
            $.notify("保存图片信息出错",{status:'danger'});
        }
    });
}
function changesize(){
    $('#uploadPreview2 img').toggleClass("second");
}
//输入代码是否重复
function validateByCode(){
	$.ajax({
		  url: "<%=appPath%>/enterprise/isEnterprisecodeExistent.htm?etc="+new Date().getTime() + "&ticketId=<%=ticketId %>",
		  dataType:'json',
		  data: $('form').serialize(),
		  success: function(result){
		       var obj=result;
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

//保存企业基本信息
function saveEnterpriseInfo(that){
		/*if($('#hasSelectedImg2').css('display')=='inline-block'){
			var options = { "status": "danger",timeout:1000 };
			$.notify("你有图片未上传，请点击确认上传", options);
			return;
		}*/
		/*if(!businessCode){
			var options={"status":"danger",timeout:1000};
     		$.notify('请上传营业执照',options);
     		return;
		}*/
        if ($('#editForm').parsley().isValid()) {
        	var berforeText = $(that).text();
        	$(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
        	$.ajax({
  		  		url: "<%=appPath%>/enterprise/saveEnterpriseInfo.htm?etc="+new Date().getTime() + "&ticketId=<%=ticketId %>",
  		  		dataType:'json',
  		  		data: $('form').serialize(),
  		  		success: function(result){
  		     	if(result.status == 1){
             		var options={"status":"success"};
             		$.notify(result.infoList[0],options);
                    enterpriseId = result.data;
                    $('#uploadImg2').click();
             		toUrl(enterpriseId);
             	}else{
             		var options={"status":"danger"};
                 	$.notify(result.infoList[0],options);
                 	$(that).text(berforeText).parent().find("button").removeAttr("disabled");
                    $(that).prev(".btn_loading").remove();
             	}
		  	} 
		});
    }
}
//跳到新增企业类型画面
function toUrl(enterpriseId){
	location="<%=appPath%>/enterprise/addEnterpriseType.htm?ticketId=<%=ticketId %>&enterpriseId="+enterpriseId;
}

//确认企业地址
function confirmAddress(){
    $("#chosen").show();
    //隐藏地图
    $("#map").hide();
    $("#select").hide();
	$("#confirm").hide();
}

function change(){
	searchByStationName();
}

//显示地图
function showMap(){
    $("#map").show();
    $("#chosen").hide();
    $("#select").show();
	$("#confirm").show();
}

//给地图单击事件
function showInfo(e){
	//清除掉地图上面的覆盖物点
    map.clearOverlays();
    //获取经纬度
    var msg = e.point.lng + ", " + e.point.lat;
    //创建一个覆盖物点
    var new_point = new BMap.Point(e.point.lng, e.point.lat);
    //创建标注
    marker = new BMap.Marker(new_point);
    //将标注添加到地图中
    map.addOverlay(marker);
    //设置标注可拖动
    marker.enableDragging();
    //给标注点添加--鼠标拖拽结束时触发此事件。
    marker.addEventListener("dragend",moveMark);
    map.panTo(new_point);
    //点击的时候把经纬度坐标显示
    $("#latitudeAndLongitude").val(msg);
    //逆地址解析---根据经纬度拿到地址
    var geoc = new BMap.Geocoder();
    var pt = e.point;
    geoc.getLocation(pt, function(rs){
        var addComp = rs.addressComponents;
        addr = addComp.province  + addComp.city  + addComp.district+ addComp.street  + addComp.streetNumber;
        regionalism = addComp.province  + addComp.city  + addComp.district;
        $("#regionalism").val(regionalism);
        $("#enterpriseAddress").val(addr);

        $("#enterpriseAddress").parsley().validate();
        validateButton();

        
        $("#province").val(addComp.province);
        $("#city").val(addComp.city);
        $("#district").val(addComp.district);
    }); 
    var opts = {
            width : 300, //信息窗口宽度
            height : 10, //信息窗口高度
    };
    //消息窗口的内容根据定位的结果获取
    if (addr) {
    	//创建信息窗口对象 
        var infoWindow = new BMap.InfoWindow("地址：" + addr, opts);
        //开启信息窗口
        map.openInfoWindow(infoWindow, new_point);
    } else {
    	//创建信息窗口对象
        var infoWindow = new BMap.InfoWindow("提示： 无法定位该地址信息，请重新选择！", opts);
        //开启信息窗口
        map.openInfoWindow(infoWindow, new_point);
    }
}

function searchByStationName() {
	//清空原来的标注
    map.clearOverlays();
    addr = $("#enterpriseAddress").val();
    // 创建地址解析器实例
    var myGeo = new BMap.Geocoder();
    // 将地址解析结果显示在地图上,并调整地图视野
    myGeo.getPoint(addr, function(point){
        if (point){
            map.centerAndZoom(cityName,11);
            //创建标注
            marker = new BMap.Marker(point);
            //将标注添加到地图中
            map.addOverlay(marker);
            //设置标注可拖动
            marker.enableDragging();
            //给标注点添加--鼠标拖拽结束时触发此事件
            marker.addEventListener("dragend",moveMark);
            map.panTo(point);      
            //逆地址解析---根据经纬度拿到地址
            var geoc = new BMap.Geocoder();
            geoc.getLocation(point, function(rs){
                var addComp = rs.addressComponents;
                addr = addComp.province  + addComp.city  + addComp.district+ addComp.street  + addComp.streetNumber;
                regionalism = addComp.province  + addComp.city  + addComp.district;
                var msg = point.lng + ", " + point.lat;
            	$("#latitudeAndLongitude").val(msg);
                $("#regionalism").val(regionalism);
                $("#enterpriseAddress").val(addr);
                
                $("#province").val(addComp.province);
                $("#city").val(addComp.city);
                $("#district").val(addComp.district);
            }); 
            var opts = {
                width : 300, //信息窗口宽度
                height : 10, //信息窗口高度
            };
            //消息窗口的内容根据定位的结果获取
            if (addr) {
            	//创建信息窗口对象
                var infoWindow = new BMap.InfoWindow("地址：" + addr, opts);
                //开启信息窗口
                map.openInfoWindow(infoWindow, point);
            }else{
            	//创建信息窗口对象
                var infoWindow = new BMap.InfoWindow("提示： 无法定位该地址信息，请重新选择！", opts);
                //开启信息窗口
                map.openInfoWindow(infoWindow, point);
            }
        }else{
            alert("您选择地址没有解析到结果!");
    }}, cityName);
}
    
//标注点的拖拽结束事件
function moveMark(e){
    var p = marker.getPosition();
    //获取经纬度
    var msg = p.lng + ", " + p.lat;
    //点击的时候把经纬度坐标显示
    $("#latitudeAndLongitude").val(msg);
    //逆地址解析---根据经纬度拿到地址
    var geoc = new BMap.Geocoder();
    var pt = e.point;
    geoc.getLocation(p, function(rs){
        var addComp = rs.addressComponents;
        addr = addComp.province  + addComp.city  + addComp.district+ addComp.street  + addComp.streetNumber;
        regionalism = addComp.province  + addComp.city  + addComp.district;
        $("#regionalism").val(regionalism);
        $("#enterpriseAddress").val(addr);
        
        $("#province").val(addComp.province);
        $("#city").val(addComp.city);
        $("#district").val(addComp.district);
    }); 
    var opts = {
        width : 300, // 信息窗口宽度
        height : 10, // 信息窗口高度
    };
    //消息窗口的内容根据定位的结果获取
    if (addr) {
    	//创建信息窗口对象
        var infoWindow = new BMap.InfoWindow("地址：" + addr, opts);
        //开启信息窗口
        map.openInfoWindow(infoWindow, p);
    }else{
    	//创建信息窗口对象
        var infoWindow = new BMap.InfoWindow("提示： 无法定位该地址信息，请重新选择！", opts); 
        //开启信息窗口
        map.openInfoWindow(infoWindow, p);
    }
}
</script>
<!-- jQuery -->
