/**
 * Created by yinxl on 2017/9/21.
 */
var upload={
    appKey:APPKEY,
    prodID:PRODID,
    businessCode:'',
    IMG_URL:IMG_DETAIN+IMGURL,
    index:0,
    fileIndexList:[],
    accept:'',
    previewContainer:'',
    fileIDObject:{},
    imgFileType:['jpg','png','gif'],
    limit:0,//0是不限制
    init:function (options) {
        this.previewContainer=options.previewContainer;//图片的容器
        this.accept=options.accept||'';//上传文件格式
        this.limit=options.limit||0;
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
    deleteEmptyForm:function () {
        var inputFileList=$('input[type=file]');
        for(var i=0;i<inputFileList.length;i++){
            if(inputFileList.eq(i)[0]['files'].length==0){
                inputFileList.eq(i).parent().remove();
            }
        }
    },
    addFile:function () {
        if(upload.limit>0&&upload.fileIndexList.length>=upload.limit){
            jqueryDialog.alert({msg:'图片数量已达上限',status:'info',timeout:2000});
            return;
        }
        this.deleteEmptyForm();
        var str='<form action="" id="myform'+this.index+'" style="display: none" method="post" enctype="multipart/form-data">'+
            '<input type="hidden" class="appKey" name="appKey" value="" />'+
            '<input type="hidden" class="prodID" name="prodID" value="" />'+
            '<input type="hidden" id="businessCode" name="businessCode" value=""/>'+
            '<input type="hidden" id="fileName" name="name" value="测试图片.jpg"/>'+
            '<input id="uploadImage'+this.index+'" type="file" name="file" accept="'+this.accept+'"/>'+
            '</form>';
        $('body').append(str);
        $('#uploadImage'+this.index).click();
        $('#uploadImage'+this.index).on('change',function () {
            var files = !!this.files ? this.files : [];
            if(!files.length || !window.FileReader) return;
            if(/^image/.test(files[0].type)) {
                upload.fileIndexList.push(upload.index);
                var currentIndex=upload.index;
                upload.index++;
                var reader = new FileReader();
                reader.readAsDataURL(files[0]);
                reader.onloadend = function() {
                    var str='<div class="file-preview-frame" id="file-preview'+currentIndex+'">'+
                        '<img src="'+this.result+'">'+
                        '<div class="file-thumbnail-footer">'+
                        '<a href="javascript:;" title="删除" class="deleteImg" onclick="upload.deleteImg('+currentIndex+')"><i class="fa fa-trash"></i></a>'+
                        '<div class="file-caption-name">等待上传</div>'+
                        '</div>'+
                        '</div>';
                    $(upload.previewContainer).append(str);
                    upload.imgBindEvent();
                }
            }else{
                upload.fileIndexList.push(upload.index);
                var currentIndex=upload.index;
                upload.index++;
                var str='<div class="file-preview-frame" id="file-preview'+currentIndex+'">'+
                    '<div class="text-preview">'+files[0].name+'</div>'+
                    '<div class="file-thumbnail-footer">'+
                    '<a href="javascript:;" title="删除" class="deleteImg" onclick="upload.deleteImg('+currentIndex+')"><i class="fa fa-trash"></i></a>'+
                    '<div class="file-caption-name">等待上传</div>'+
                    '</div>'+
                    '</div>';
                $(upload.previewContainer).append(str);
            }
        });
    },
    imgBindEvent:function () {
        $('.file-preview-frame img').unbind('click');
        $('.file-preview-frame img').on('click',function () {
            if($('.large').length>0){
                $('.large img').attr('src',$(this).attr('src'));
                $('.large').show();
            }else{
                $('body').append('<div class="large"><img src="'+$(this).attr('src')+'"></div>');
                $('.large').on('click',function () {
                    $(this).hide();
                })
            }
        });
    },
    delete:function (fileID) {
        $.ajax({
            url:upload.IMG_URL+'/remove',
            data:{
                appKey:upload.appKey,
                prodID:upload.prodID,
                fileID:fileID
            },
            async: false,
            success:function (data) {
                if(data.success){

                }
            }
        })
    },
    deleteImg:function (currentIndex) {
        var fileID=upload.fileIDObject[currentIndex+''];
        if(fileID){//已上传
            var data= {
                msg: '删除后该文件无法恢复,确认要删除该文件？',
                title: '删除确认',
                type: 'info',
                cancelText: '取消',
                sureText: '确定',
                success: function () {
                    upload.delete(fileID);
                    upload.removeImg(currentIndex);
                }
            };
            jqueryDialog.confirmTable(data);
        }else{
            upload.removeImg(currentIndex);
        }
    },
    removeImg:function (currentIndex) {
        var index = upload.fileIndexList.indexOf(currentIndex);
        if (index > -1) {
            upload.fileIndexList.splice(index, 1);
        }
        $('#myform'+currentIndex).remove();
        $('#file-preview'+currentIndex).remove();
    },
    upload:function (businessCode) {
        if(this.fileIndexList.length==0){
            jqueryDialog.alert({msg:'请上传图片',status:'info',timeout:2000});
            return;
        }
        this.businessCode=businessCode;
        $('input[name=appKey]').val(upload.appKey);
        $('input[name=prodID]').val(upload.prodID);
        $('input[name=businessCode]').val(upload.businessCode);
        for(var i in this.fileIndexList){
            var formIdIndex=this.fileIndexList[i];
            if(upload.fileIDObject[formIdIndex+'']){
                continue;
            }
            $('#myform'+formIdIndex).ajaxSubmit({
                url: upload.IMG_URL+'/upload',
                type: 'POST',
                async: false,
                success: function(data) {
                    console.log(data);
                    if(data.success) {
                        console.log('上传成功');
                        $('#file-preview'+formIdIndex).find('.file-caption-name').html('已上传').css('color','green');
                        upload.fileIDObject[formIdIndex+'']=data.message.fileID;
                    } else {
                        console.log('上传失败');
                    }
                },
                error: function(err) {
                    console.log('上传失败');
                }
            });
        }
    },
    getImgListByBusinessCode:function (businessCode) {
        var files=[];
        $.ajax({
            url:upload.IMG_URL+'/queryList',
            data:{
                appKey:upload.appKey,
                prodID:upload.prodID,
                businessCode:businessCode
            },
            async: false,
            success:function (data) {
                if(data.success){
                    files=data.files;
                }
            }
        });
        return files;
    },
    download:function (fileID) {
        var downloadUrl=upload.IMG_URL+'/download?fileID='+fileID;
        window.location=downloadUrl;
    },
    rendorList:function (files) {
        for(var i in files){
            if(upload.imgFileType.indexOf(files[i]['fileExtension'])>-1){
                var fileID=files[i]['fileID'];
                upload.fileIndexList.push(i*1);
                upload.fileIDObject[i+'']=fileID;
                var imgSrc=upload.IMG_URL+'/view?fileID='+fileID;
                var str='<div class="file-preview-frame img-preview" id="file-preview'+i+'">'+
                    '<img src="'+imgSrc+'">'+
                    '<div class="file-thumbnail-footer">'+
                    '<a href="javascript:;" onclick="upload.download(\''+fileID+'\');" title="下载" class="downloadImg"><i class="fa fa-download"></i></a>'+
                    '<a href="javascript:;" title="删除" class="deleteImg" onclick="upload.deleteImg('+i+')"><i class="fa fa-trash"></i></a>'+
                    '<div class="file-caption-name" style="color: green;">已上传</div>'+
                    '</div>'+
                    '</div>';
                $(upload.previewContainer).append(str);
            }else{
                var fileID=files[i]['fileID'];
                upload.fileIndexList.push(i*1);
                upload.fileIDObject[i+'']=fileID;
                var str='<div class="file-preview-frame img-preview" id="file-preview'+i+'">'+
                    '<div class="text-preview">'+files[i]['fileName']+'</div>'+
                    '<div class="file-thumbnail-footer">'+
                    '<a href="javascript:;" onclick="upload.download(\''+fileID+'\');" title="下载" class="downloadImg"><i class="fa fa-download"></i></a>'+
                    '<a href="javascript:;" title="删除" class="deleteImg" onclick="upload.deleteImg('+i+')"><i class="fa fa-trash"></i></a>'+
                    '<div class="file-caption-name" style="color: green;">已上传</div>'+
                    '</div>'+
                    '</div>';
                $(upload.previewContainer).append(str);
            }
        }
        upload.imgBindEvent();
        upload.index=files.length;
    }
}