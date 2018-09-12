var upload={
	bclrList:[],bcList:[],businessCode:'',
	fileType:['image/jpeg','image/png','image/gif'],
	deleteImgInServer:function(fileId, md5Id, businessCode) {
		$.ajax({
			url: IMG_PREV+REMOVE_ACTION,
			data: {
				'appKey': APPKEY,
				'prodId': PRODID,
				'businessCode': businessCode,
				'fileFingerPrint': md5Id,
				'fileId': fileId
			},
			type: "POST",
			dataType: 'json',
			async: false,
			success: function(data) {
				if(data.success) {
					console.log('图片删除成功');
				}
			},
			error: function(er) {
				alert(er);
			}
		});
	},
	deleteImg:function(fileId, md5Id, businessCode) {
		$.ajax({
			url: appPath+"/fileUpload/deleteFileByFileId",
			data: {
				'fileId': fileId,
			},
			type: "POST",
			dataType: 'json',
			async: false,
			success: function(data) {
				if(data.status == 1) {
					$.notify("图片删除成功", { status: 'success' });
					$('#img' + fileId).remove();
					if($('#uploadSuccessImgList .uploadItem').length == 0) {
						$('#uploadSuccessImgList').hide();
					}
					upload.removeItemFromBclrList(businessCode);
					upload.deleteImgInServer(fileId, md5Id, businessCode);
				} else {
					$.notify("图片删除失败", { status: 'danger' });
				}
			},
			error: function(er) {
				$.notify("图片删除失败", { status: 'danger' });
			}
		});
	},
	deleteImg2:function(fileId, md5Id, businessCode) {
		$.ajax({
			url: appPath+"/fileUpload/deleteFileByFileId",
			data: {
				'fileId': fileId,
			},
			type: "POST",
			dataType: 'json',
			async: false,
			success: function(data) {
				if(data.status == 1) {
					$.notify("图片删除成功", { status: 'success' });
					$('#img' + fileId).remove();
					if($('#uploadSuccessImgList2 .uploadItem').length == 0) {
						$('#uploadSuccessImgList2').hide();
					}
					upload.removeItemFromBcList(businessCode);
					upload.deleteImgInServer(fileId, md5Id, businessCode);
				} else {
					$.notify("图片删除失败", { status: 'danger' });
				}
			},
			error: function(er) {
				$.notify("图片删除失败", { status: 'danger' });
			}
		});
	},
	removeItemFromBclrList:function(businessCode){
		for(var i in upload.bclrList){
			if(upload.bclrList[i]==businessCode){
				upload.bclrList.splice(i,1);
			}
		}
	},
	removeItemFromBcList:function(businessCode){
		for(var i in upload.bcList){
			if(upload.bcList[i]==businessCode){
				upload.bcList.splice(i,1);
			}
		}
	},
	saveImg_lr:function(fileFingerPrint, fileID) {
		$.ajax({
			url: appPath+"/fileUpload/saveUploadFile",
			data: {
				'businessCode': this.businessCode,
				'fileFingerPrint': fileFingerPrint,
				'fileId': fileID,
				'fileType': "e",
				'ticketId': ticketId
			},
			type: "POST",
			dataType: 'json',
			async: false,
			success: function(data) {
				if(data.status == 1) {
					$.notify("保存图片成功", { status: 'success' });
					$('#previewImg').hide();
					var imgurl = IMG_VIEW_URL+'&businessCode=' + upload.businessCode + '&fileID=' + fileID;
					$("#uploadSuccessImgList").append('<div class="uploadItem" id="img' + fileID + '"><a href="javascript:;" onclick="upload.deleteImg(\'' + fileID + '\',\'' + fileFingerPrint + '\',\'' + upload.businessCode + '\')"><i class="fa fa-remove"></i></a><img src="' + imgurl + '"></div>');
					$('#selectImg').show();
					$('#uploadSuccessImgList').show();
					$('#hasSelectedImg').hide();
					upload.bclrList.push(upload.businessCode);
					var file = $("#uploadImage");  
	                file.replaceWith(file.clone());
	                upload.bindEvent();
					return false;
				} else {
					$.notify("保存图片失败", { status: 'danger' });
				}
			},
			error: function(er) {
				$.notify("保存图片信息出错", { status: 'danger' });
			}
		});
	},
	saveImg:function(fileFingerPrint, fileID) {
		$.ajax({
			url: appPath+"/fileUpload/saveUploadFile",
			data: {
				'businessCode': this.businessCode,
				'fileFingerPrint': fileFingerPrint,
				'fileId': fileID,
				'fileType': "f",
				'ticketId': ticketId
			},
			type: "POST",
			dataType: 'json',
			async: false,
			success: function(data) {
				if(data.status == 1) {
					$.notify("保存图片成功", { status: 'success' });
					$('#previewImg2').hide();
					var imgurl = IMG_VIEW_URL+'&businessCode=' + upload.businessCode + '&fileID=' + fileID;
					$("#uploadSuccessImgList2").append('<div class="uploadItem" id="img' + fileID + '"><a href="javascript:;" onclick="upload.deleteImg2(\'' + fileID + '\',\'' + fileFingerPrint + '\',\'' + upload.businessCode + '\')"><i class="fa fa-remove"></i></a><img src="' + imgurl + '"></div>');
					$('#selectImg2').show();
					$('#uploadSuccessImgList2').show();
					$('#hasSelectedImg2').hide();
					upload.bcList.push(upload.businessCode);
					var file = $("#uploadImage2");  
	                file.replaceWith(file.clone());
	                upload.bindEvent();
					return false;
				} else {
					$.notify("保存图片失败", { status: 'danger' });
				}
			},
			error: function(er) {
				$.notify("保存图片信息出错", { status: 'danger' });
			}
		});
	},
	randomChar:function(l)  {
		var  x="0123456789qwertyuioplkjhgfdsazxcvbnm";
		var  tmp="";
		var timestamp = new Date().getTime();
		for(var  i=0;i<l;i++)  {
		tmp+=x.charAt(Math.ceil(Math.random()*100000000)%x.length);
		}
		return  timestamp+tmp;
	},
	bindEvent:function(){
		$('#uploadImage').unbind('change');
		$('#uploadImage2').unbind('change');
		$('#sureUpload').unbind('click');
		$('#sureUpload2').unbind('click');
		$('#uploadImage').change(function() {
			if(upload.bclrList.length>=3){
				$.notify("产废化验单最多上传3张图片", { status: 'info' });
				return;
			}
			var files = !!this.files ? this.files : [];
			if(files.length==1&&FILETYPE.indexOf(files[0].type)==-1){
				$.notify(TYPE_ERROR_MSG, {status:'info',timeout:1500 });
				return;
			}
			if(!files.length || !window.FileReader) return;
			$('#hasSelectedImg').show();
			$('#selectImg').hide();
			if(/^image/.test(files[0].type)) {
				var reader = new FileReader();
				reader.readAsDataURL(files[0]);
				reader.onloadend = function() {
					$("#previewImg img").attr('src', this.result);
					$('#previewImg').show();
				}
			}
		});
		$('#uploadImage2').change(function() {
			if(upload.bcList.length>=3){
				$.notify("产废图片最多上传3张图片", { status: 'info' });
				return;
			}
			var files = !!this.files ? this.files : [];
			if(files.length==1&&FILETYPE.indexOf(files[0].type)==-1){
				$.notify(TYPE_ERROR_MSG, {status:'info',timeout:1500 });
				return;
			}
			if(!files.length || !window.FileReader) return;
			$('#hasSelectedImg2').show();
			$('#selectImg2').hide();
			if(/^image/.test(files[0].type)) {
				var reader = new FileReader();
				reader.readAsDataURL(files[0]);
				reader.onloadend = function() {
					$("#previewImg2 img").attr('src', this.result);
					$('#previewImg2').show();
				}
			}
		});
		$('#sureUpload').click(function() {
			upload.businessCode=upload.randomChar(19);
			$('#businessCodeLaboratoryReport').val(upload.businessCode);
			$('#myform1').ajaxSubmit({
				url: IMG_PREV+UPLOAD_ACTION,
				type: 'POST',
				async: false,
				success: function(data) {
					if(data.success) {
						//开始插入数据
						upload.saveImg_lr(data.message.fileFingerPrint, data.message.fileID);
					} else {
						$.notify("上传失败", { status: 'danger' });
					}
				},
				error: function(err) {
					$.notify("上传失败", { status: 'danger' });
				}
			});
		});
		$('#sureUpload2').click(function() {
			upload.businessCode=upload.randomChar(19);
			$('#businessCode').val(upload.businessCode);
			$('#myform2').ajaxSubmit({
				url: IMG_PREV+UPLOAD_ACTION,
				type: 'POST',
				async: false,
				success: function(data) {
					if(data.success) {
						//开始插入数据
						upload.saveImg(data.message.fileFingerPrint, data.message.fileID);
					} else {
						$.notify("上传失败", { status: 'danger' });
					}
				},
				error: function(err) {
					$.notify("上传失败", { status: 'danger' });
				}
			});
		});
	},
	init:function(){
		$('.appkey').val(APPKEY);
		$('.prodId').val(PRODID);
		this.bindEvent();
	}
}
$(document).ready(function(){
	upload.init();
})
