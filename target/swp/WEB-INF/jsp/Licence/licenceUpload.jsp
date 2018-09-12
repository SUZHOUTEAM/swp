<%@ page contentType="text/html; charset=UTF-8" language="java"
    errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
    String appPath = request.getContextPath();
    String licenceId = request.getParameter("licenceId");
    String ticketId = (String) request.getAttribute("ticketId");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>FileUpload Demo</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<meta http-equiv="refresh" content="1800" />
<!-- bootstrap & fontawesome -->

</head>
<style type="text/css">
	  #uploadImage{
	    position: absolute;
	    background: #FF0000;
	    left: 0;
	    margin: 0 auto;
	    width: 117px;
	    height: 33px;
        margin-left: 30px;
	    cursor: pointer;
	    opacity: 0;
	    filter:alpha(opacity=0); 
	}
	
	#uploadImage.second{
	    width: 86px;
	}
	#hasSelectedImg{
		display: none;
	}
	#uploadPreview{
		margin:10px auto;
	}
	#uploadPreview img{
		
	} 
</style>

<script type="text/javascript">
    //套用jquery-step格式的初始化函数，跟页面本身业务无关
    $(document).ready( function () {
        setParentIframeHeight();
    });
    
    $(window).resize(function($){
        setParentIframeHeight();
    });
    
    function setParentIframeHeight(){
        $(parent.document).find('iframe').each(function(){
            $(this).height($(document).height());
        });
    }
    
    
    function reviewLicence(){
       // var licenceId = $("#licenceId").val(); //topFlg
        var topFlg = "1";
        //alert(" ----");
        //刷新页面
        parent.location="<%=appPath %>/licence/commView.htm?id=<%=licenceId %>"+"&ticketId=${ticketId}" ;
    }
    </script>
<body class="no-skins" style="background-color: #ffffff">
	<div class="p-xl text-primary">
		<form action="" id="myform" method="post" enctype="multipart/form-data">
	       	<input id="uploadImage" type="file" class="fimg1" name="file" />
	       	
	        <a id="addImg" class="btn btn-default">
	        	<i class="fa fa-upload"></i>上传许可证
	        </a>
			<input type="hidden" id="appkey" name="appKey" value="5da5441f62e48aedc7a3853ffc75c2db"/>
			<input type="hidden" id="proId" name="prodID" value="gf"/>
			<input type="hidden" id="businessCode" name="businessCode" value="njknnvskAWD"/>
			<!--<input type="hidden" id="name" name="name" value="营业执照" />-->
			<div id="hasSelectedImg">
				<a id="edit" class="btn btn-default">重新上传</a>
				<a href="javascript:void(0);" id="uploadImg" class="btn btn-default">确认上传</a>
			</div>
			<div id="uploadPreview"><img src=""></div>
		</form>
	</div>
	<script src="<%=appPath %>/app/js/constants.js"></script>
	<script src="<%=appPath %>/main/common/jquery.form.js"></script>
	<script type="text/javascript">
		var imgType='d';
		var licenceId="<%=licenceId %>";
		var ticketId="<%=ticketId %>";
		var businessCode=imgType+licenceId;
		function saveImg(fileFingerPrint,fileID){
			$.ajax({
				url: "<%=appPath %>/fileUpload/updateFileByBusinessCode",
				data: {
					'businessCode' :businessCode,
					'fileFingerPrint':fileFingerPrint,
					'fileId':fileID,
					'fileType':imgType,
					'referenceId':licenceId,
					'ticketId':ticketId
				},
				type: "POST",
				dataType: 'json',
				async: false,
				success: function(data) {
					if(data.status==1){
						$.notify("上传许可证成功",{status:'success'});
						$('#hasSelectedImg').hide();
						$('#addImg').show();
					}else{
						$.notify("修改头像失败",{status:'danger'});
					}
				},
				error: function(er) {
					$.notify("保存图片信息出错",{status:'danger'});
				}
			});
		}
		$(document).ready(function(){
			$('#businessCode').val(businessCode);
			$.ajax({
				url: "<%=appPath %>/fileUpload/getFileByBusinessCode",
				data: {
					'businessCode' :businessCode,
					'ticketId':ticketId
				},
				type: "POST",
				dataType: 'json',
				async: false,
				success: function(data) {
					if(data.status==1){
						//$.notify("修改成功",{status:'success'});
						var uploadFileInfo=data.data.uploadFileInfo;
						if(uploadFileInfo){
							$("#uploadPreview img").attr('src',IMG_VIEW_URL+'&businessCode='+uploadFileInfo.businessCode+'&fileID='+uploadFileInfo.fileId);
						}
					}else{
						$.notify("获取图片信息失败",{status:'danger'});
					}
				},
				error: function(er) {
					$.notify("获取图片信息失败",{status:'danger'});
				}
			});
			$('#uploadImage').change(function(){
				var files = !!this.files ? this.files : [];
				if(files.length==1&&FILETYPE.indexOf(files[0].type)==-1){
					$.notify(TYPE_ERROR_MSG, {status:'info',timeout:1500 });
					return;
				}
			    if (!files.length || !window.FileReader) return;
				$('#uploadImage').addClass('second');
				$('#hasSelectedImg').show();
				$('#addImg').hide();
			    if (/^image/.test( files[0].type)){
			        var reader = new FileReader();
			        reader.readAsDataURL(files[0]);
			        reader.onloadend = function(){
						$("#uploadPreview img").attr('src',this.result);
			        };
			        $('#uploadImg').click();
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
				      	 $.notify('上传失败',{status:'danger'});
				     }
				});
			});
		});
	</script>
</body>

</html>
