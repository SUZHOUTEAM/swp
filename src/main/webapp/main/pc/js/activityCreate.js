/**
 * Created by wudang on 2017/8/1.
 */
var activity={
    entId:'',entName:'',ticketId:'',fileID_subject:'',fileID_logo:'',fileID_inquiry:'',fileID_swipe:'',activityId:'',
    appPath:'',subjectImgUpload:false,logoImgUpload:false,inquiryImgUpload:false,oldEntId:'',oldEntName:'',
    areaList:['110','120','310','710','810','820'],swipeImgUpload:false,
    bindEvent:function(){
        $('.datetime').datetimepicker({
            format : 'yyyy-mm-dd hh:ii:00',
            language:  'zh-CN',
            weekStart: 1,
            todayBtn: "linked",
            todayHighlight:true,
            autoclose: true,
            startView: 2,
            minView: 'hour',
            forceParse: true,
            pickerPosition: "bottom-right",
        }).on('changeDate', function (ev) {
            activity.reInitDatePicker ($(this));
        });
        $('input[name=activityPrice]').on('click',function () {
            var value=$(this).val();
            if(value=='1'){
                $('#fixed').show();
                $('#range').hide();
            }else{
                $('#fixed').hide();
                $('#range').show();
            }
        });
        $('input[name=hasParent]').on('click',function () {
            var value=$(this).val();
            if(value=='1'){
                $('#parentActivity').show();
                $('#activityTime').hide();
                $('#activityArea').hide();
            }else{
                $('#parentActivity').hide();
                $('#activityTime').show();
                $('#activityArea').show();
            }
        });
        $('input[name=hasParent][value="2"]').click();
        // jquery监听input框中内容变化的时候出发
        var lastTime;
        $('#entName').keyup(function (e) {
            lastTime = e.timeStamp;
            setTimeout(function () {
                if (lastTime - e.timeStamp == 0) {
                    var entName = $.trim($('#entName').val());
                    if(entName.length<2){
                        return;
                    }
                    activity.searchByKey($.trim($('#entName').val()))
                }
            }, 1000);
        });
        $('#subject_file,#upload_again1').on('click',function () {
           $('#uploadImage').click();
        });
        $('#uploadImage').on('change',function () {
            activity.onchange1(this);
        });
        $('#subject_file2,#upload_again2').on('click',function () {
           $('#uploadImage2').click();
        });
        $('#uploadImage2').on('change',function () {
            activity.onchange2(this);
        });
        $('#subject_file3,#upload_again3').on('click',function () {
           $('#uploadImage3').click();
        });
        $('#uploadImage3').on('change',function () {
            activity.onchange3(this);
        });
        $('#subject_file4,#upload_again4').on('click',function () {
           $('#uploadImage4').click();
        });
        $('#uploadImage4').on('change',function () {
            activity.onchange4(this);
        });
        $('#submit').on('click',function(){
            var reg = /^[0-9]+(.[0-9]{1,2})?$/
            var activityName=$('#activityName').val();
            if(!activityName){
                $.notify("请填写活动名称", { status: 'danger',timeout:1500 });
                return;
            }
            if(activity.activityId&&activity.entId==activity.oldEntId&&$('#entName').val()!=activity.oldEntName){
                activity.entId='';
                activity.entName='';
            }
            if(!activity.entId||!activity.entName){
                $.notify("请选择活动企业", { status: 'danger',timeout:1500 });
                return;
            }
            var priceType=$('input[name=activityPrice]:checked').val();
            if(!priceType){
                $.notify("请选择活动价格", { status: 'danger',timeout:1500 });
                return;
            }
            if(priceType==1&&($('#price').val()=='')){
                $.notify("请填写一口价价格", { status: 'danger',timeout:1500 });
                return;
            }
            if(priceType==2&&$('#min-price').val()==''&&$('#max-price').val()==''){
                $.notify("最高价和最低价不能都为空", { status: 'danger',timeout:1500 });
                return;
            }

            if(priceType==1&&($('#price').val()==0||!reg.test($('#price').val()))
                ||(priceType==2&&($('#min-price').val()==0||!reg.test($('#min-price').val()))&&$('#min-price').val()!='')
                ||(priceType==2&&($('#max-price').val()==0||!reg.test($('#max-price').val()))&&$('#max-price').val()!='')){
                $.notify("价格请输入大于0的数字,最多保留两位小数", { status: 'danger',timeout:1500 });
                return;
            }

            if(priceType==2&&$('#min-price').val()!=''&&$('#max-price').val()!=''&&$('#min-price').val()>$('#max-price').val()){
                $.notify("最低价不能大于最高价", { status: 'danger',timeout:1500 });
                return;
            }
            if($('#uploadImage').val().length==0&&activity.fileID_subject==''){
                $.notify("请上传活动宣传大图", { status: 'danger',timeout:1500 });
                return;
            }
            if($('#uploadImage3').val().length==0&&activity.fileID_inquiry==''){
                $.notify("请上传活动宣传长图", { status: 'danger',timeout:1500 });
                return;
            }
            if($('#uploadImage2').val().length==0&&activity.fileID_logo==''){
                $.notify("请上传活动logo", { status: 'danger',timeout:1500 });
                return;
            }
            if($('#uploadImage4').val().length==0&&activity.fileID_swipe==''){
                $.notify("请上传手机轮播图", { status: 'danger',timeout:1500 });
                return;
            }
            var hasParent=$('input[name=hasParent]:checked').val();
            var startDate=$('#start_date').val(),endDate=$('#end_date').val(),
                cantonCode=$('#_area').val(),riseEndDate=$('#riseEndDate').val();
            if(!hasParent){
                $.notify("请选择是否归于综合活动", { status: 'danger',timeout:1500 });
                return;
            }
            if(hasParent==1&&$('#parentActivity select').val()==0){
                $.notify("请选择归于哪种活动", { status: 'danger',timeout:1500 });
                return;
            }
            if(hasParent==2){
                if($('#start_date').val()==''||$('#end_date').val()==''){
                    $.notify("开始时间和结束时间不能为空", { status: 'danger',timeout:1500 });
                    return;
                }
                // if($('#area').val()==''){
                //     $.notify("请选择区域", { status: 'danger',timeout:1500 });
                //     return;
                // }
            }
            if(!reg.test($('#enrollFee').val())){
                $.notify("报名费请输入大于等于0的数字,最多保留两位小数", { status: 'danger',timeout:1500 });
                return;
            }
            if($('#enrollFee').val()>0&&!$('#riseEndDate').val()){
                $.notify("众筹截止时间不能为空", { status: 'danger',timeout:1500 });
                return;
            }
            if($('#enrollFee').val()>0&&!/^0.[0-9]{1,2}?$/.test($('#discount').val())){
                $.notify("折扣请输入小数，最多两位小数", { status: 'danger',timeout:1500 });
                return;
            }
            if($('#enrollFee').val()>0&&!/^[0-9]+$/.test($('#numberGroupBuying').val())){
                $.notify("团购人数请填写整数", { status: 'danger',timeout:1500 });
                return;
            }
            $("#submit").attr("disabled","disabled");
            $("#submit").text("提交中...");
            if(activity.subjectImgUpload){
                activity.uploadSubjectImg();
            }
            if(activity.logoImgUpload){
                activity.uploadLogoImg();
            }
            if(activity.inquiryImgUpload){
                activity.uploadInquiryImg();
            }
            if(activity.swipeImgUpload){
                activity.uploadSwipeImg();
            }
            if(hasParent==1){
                startDate='';endDate='';cantonCode='';
            }else{
                if(startDate.length==10){
                    startDate+=' 00:00:00';
                }
                if(endDate.length==10){
                    endDate+=' 23:59:59';
                }
                var len=cantonCode.length;
                if(len==2){
                    cantonCode+='0000';
                }
                if(len==4){
                    cantonCode+='00';
                }
            }
            var startPrice=0,endPrice=0;
            if(priceType==1){
                startPrice=endPrice=$('#price').val();
            }else{
                startPrice=$('#min-price').val();
                endPrice=$('#max-price').val();
            }
            var param={
                activityName:activityName,
                activityRemark:$('#activityRemark').val(),
                parentActivityId:'',
                startDate:startDate,
                endDate:endDate,
                priceType:priceType,
                cantonCode:cantonCode,
                subjectFileId:activity.fileID_subject,
                logoFileId:activity.fileID_logo,
                inquiryFileId:activity.fileID_inquiry,
                swipeFileId:activity.fileID_swipe,
                entId:activity.entId,
                entName:activity.entName,
                entRemark:'',
                startPrice:startPrice,
                endPrice:endPrice,
                // ticketId:activity.ticketId,
                activityId:activity.activityId,
                videoResource:$('#videoResource').val(),
                smsTemplate:$('#smsTemplate').val(),
                cronJob:$('#cronJob').val(),
                enrollFee:$('#enrollFee').val(),
                discount:$('#discount').val(),
                numberGroupBuying:$('#numberGroupBuying').val(),
                riseEndDate:riseEndDate?(riseEndDate+' 23:59:59'):'',
                presenter:$('#presenter').val(),
                customCourseDesc:$('#customCourseDesc').val(),
                payNote:$('#payNote').val(),
                duration:$('#duration').val(),
                industry:$('#industry').val()
            };
            console.log(param);
            var action=activity.activityId?'修改':'创建';
            $.ajax({
                url:activity.appPath+"/wasteActivity/save.htm?ticketId="+activity.ticketId,
                type:'POST',
                dataType: "json",
                data: JSON.stringify(param),
                contentType:'application/json',
                success: function (resultData) {
                    if (resultData && resultData.status == 1) {
                        $.notify(action+"活动成功", { status: 'success',timeout:1500 });
                        setTimeout(function () {
                            window.location=activity.appPath+'/wasteActivity/list.htm?ticketId='+activity.ticketId;
                        },1500)
                    }else{
                        $.notify(action+"活动失败", { status: 'danger',timeout:1500 });
                    }
                },
                error:function (error) {
                    $.notify(action+"活动失败", { status: 'danger',timeout:1500 });
                }
            });
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
    uploadSubjectImg:function(){
        var businessCode=this.randomChar(19);
        $('#businessCode_subject').val(businessCode);
        $('#myform1').ajaxSubmit({
            url: IMG_PREV+UPLOAD_ACTION,
            type: 'POST',
            async: false,
            success: function(data) {
                if(data.success) {
                    //开始插入数据
                    activity.fileID_subject=data.message.fileID;
                } else {
                    $.notify("上传失败", { status: 'danger',timeout:1500 });
                }
            },
            error: function(err) {
                $.notify("上传失败", { status: 'danger',timeout:1500 });
            }
        });
    },
    uploadLogoImg:function () {
        var businessCode=this.randomChar(19);
        $('#businessCode_logo').val(businessCode);
        $('#myform2').ajaxSubmit({
            url: IMG_PREV+UPLOAD_ACTION,
            type: 'POST',
            async: false,
            success: function(data) {
                if(data.success) {
                    //开始插入数据
                    activity.fileID_logo=data.message.fileID;
                } else {
                    $.notify("上传失败", { status: 'danger',timeout:1500 });
                }
            },
            error: function(err) {
                $.notify("上传失败", { status: 'danger',timeout:1500 });
            }
        });
    },
    uploadInquiryImg:function () {
        var businessCode=this.randomChar(19);
        $('#businessCode_inquiry').val(businessCode);
        $('#myform3').ajaxSubmit({
            url: IMG_PREV+UPLOAD_ACTION,
            type: 'POST',
            async: false,
            success: function(data) {
                if(data.success) {
                    //开始插入数据
                    activity.fileID_inquiry=data.message.fileID;
                } else {
                    $.notify("上传失败", { status: 'danger',timeout:1500 });
                }
            },
            error: function(err) {
                $.notify("上传失败", { status: 'danger',timeout:1500 });
            }
        });
    },
    uploadSwipeImg:function () {
        var businessCode=this.randomChar(19);
        $('#businessCode_swipe').val(businessCode);
        $('#myform4').ajaxSubmit({
            url: IMG_PREV+UPLOAD_ACTION,
            type: 'POST',
            async: false,
            success: function(data) {
                if(data.success) {
                    //开始插入数据
                    activity.fileID_swipe=data.message.fileID;
                } else {
                    $.notify("上传失败", { status: 'danger',timeout:1500 });
                }
            },
            error: function(err) {
                $.notify("上传失败", { status: 'danger',timeout:1500 });
            }
        });
    },
    onchange1:function(obj){
        activity.subjectImgUpload=true;
        var files = !!obj.files ? obj.files : [];
        if(files.length==1&&FILETYPE.indexOf(files[0].type)==-1){
            $.notify(TYPE_ERROR_MSG, {status:'info',timeout:1500 });
            // alert(TYPE_ERROR_MSG);
            $('#previewImg1').hide();
            $('#subject_file').show();
            $(obj).remove();
            $('#myform1').append('<input id="uploadImage" class="uploadImage" type="file" name="file">');
            $('#uploadImage').on('change',function () {
                activity.onchange1(this);
            });
            return;
        }else if(files.length==0){
            $('#previewImg1').hide();
            $('#subject_file').show();
            $(obj).remove();
            $('#myform1').append('<input id="uploadImage" class="uploadImage" type="file" name="file">');
            $('#uploadImage').on('change',function () {
                activity.onchange1(this);
            });
            return;
        }

        if(!files.length || !window.FileReader) return;
        if(/^image/.test(files[0].type)) {
            var reader = new FileReader();
            reader.readAsDataURL(files[0]);
            reader.onloadend = function() {
                $('#previewImg1 img').attr('src', this.result);
                $('#previewImg1').show();
                $('#subject_file').hide();
            }
        }
    },
    onchange2:function(obj){
        activity.logoImgUpload=true;
        var files = !!obj.files ? obj.files : [];
        if(files.length==1&&FILETYPE.indexOf(files[0].type)==-1){
            $.notify(TYPE_ERROR_MSG, {status:'info',timeout:1500 });
            $('#previewImg2').hide();
            $('#subject_file2').show();
            $(obj).remove();
            $('#myform2').append('<input id="uploadImage2" class="uploadImage" type="file" name="file">');
            $('#uploadImage2').on('change',function () {
                activity.onchange2(this);
            });
            return;
        }else if(files.length==0){
            $('#previewImg2').hide();
            $('#subject_file2').show();
            $(obj).remove();
            $('#myform2').append('<input id="uploadImage2" class="uploadImage" type="file" name="file">');
            $('#uploadImage2').on('change',function () {
                activity.onchange2(this);
            });
            return;
        }
        if(!files.length || !window.FileReader) return;
        if(/^image/.test(files[0].type)) {
            var reader = new FileReader();
            reader.readAsDataURL(files[0]);
            reader.onloadend = function() {
                $('#previewImg2 img').attr('src', this.result);
                $('#previewImg2').show();
                $('#subject_file2').hide();
            }
        }
    },
    onchange3:function(obj){
        activity.inquiryImgUpload=true;
        var files = !!obj.files ? obj.files : [];
        if(files.length==1&&FILETYPE.indexOf(files[0].type)==-1){
            $.notify(TYPE_ERROR_MSG, {status:'info',timeout:1500 });
            $('#previewImg3').hide();
            $('#subject_file3').show();
            $(obj).remove();
            $('#myform3').append('<input id="uploadImage3" class="uploadImage" type="file" name="file">');
            $('#uploadImage3').on('change',function () {
                activity.onchange3(this);
            });
            return;
        }else if(files.length==0){
            $('#previewImg3').hide();
            $('#subject_file3').show();
            $(obj).remove();
            $('#myform3').append('<input id="uploadImage3" class="uploadImage" type="file" name="file">');
            $('#uploadImage3').on('change',function () {
                activity.onchange3(this);
            });
            return;
        }
        if(!files.length || !window.FileReader) return;
        if(/^image/.test(files[0].type)) {
            var reader = new FileReader();
            reader.readAsDataURL(files[0]);
            reader.onloadend = function() {
                $('#previewImg3 img').attr('src', this.result);
                $('#previewImg3').show();
                $('#subject_file3').hide();
            }
        }
    },
    onchange4:function(obj){
        activity.swipeImgUpload=true;
        var files = !!obj.files ? obj.files : [];
        if(files.length==1&&FILETYPE.indexOf(files[0].type)==-1){
            $.notify(TYPE_ERROR_MSG, {status:'info',timeout:1500 });
            $('#previewImg4').hide();
            $('#subject_file4').show();
            $(obj).remove();
            $('#myform4').append('<input id="uploadImage4" class="uploadImage" type="file" name="file">');
            $('#uploadImage4').on('change',function () {
                activity.onchange4(this);
            });
            return;
        }else if(files.length==0){
            $('#previewImg4').hide();
            $('#subject_file4').show();
            $(obj).remove();
            $('#myform4').append('<input id="uploadImage4" class="uploadImage" type="file" name="file">');
            $('#uploadImage4').on('change',function () {
                activity.onchange4(this);
            });
            return;
        }
        if(!files.length || !window.FileReader) return;
        if(/^image/.test(files[0].type)) {
            var reader = new FileReader();
            reader.readAsDataURL(files[0]);
            reader.onloadend = function() {
                $('#previewImg4 img').attr('src', this.result);
                $('#previewImg4').show();
                $('#subject_file4').hide();
            }
        }
    },
    searchByKey:function(cname){
        $.ajax({
            url: this.appPath+"/userservice/getSuggestEnterpriseList.htm",
            data: {
                'enterpriseName': cname,
                'ticketId':activity.ticketId,
                'enterpriseType':'DISPOSITION'
            },
            type: "POST",
            dataType: 'json',
            async: true,
            success: function(data) {
                var data=data.data;
                if(data&&data.enterpriselist && data.enterpriselist.length > 0) {
                    var str = '';
                    for(var i = 0; i < data.enterpriselist.length; i++) {
                        var v = data.enterpriselist[i];
                        str += '<li><a href="javascript:;" onclick="activity.onClickA(\'' + v.entId + '\',\''+v.entName+'\')">' + v.entName + '</a></li>';
                    }
                    $('#entName_dropdown ul').html(str);
                    $('#entName_dropdown .dropdown-menu').removeClass('hidden').show();
                    $('#entName_dropdown ul').on('mouseover mousemove', function() {
                        $(this).removeClass('hidden');
                    });
                    $('#entName_dropdown ul').on('mouseout', function() {
                        $(this).addClass('hidden');
                    });
                } else {
                    $('#entName_dropdown .dropdown-menu').addClass('hidden');
                }
            },
            error: function(er) {
                $.notify('加载数据失败', {status:'danger',timeout:1500 });
            }
        });
    },
    onClickA: function(entId,entName) {
        $('#entName').val(entName);
        activity.entId=entId;
        activity.entName=entName;
        $('#entName_dropdown .dropdown-menu').hide();
    },
    getParam: function(paraName) {
        var search = document.location.search,
            reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
        if(search && reg.test(search)) {
            return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
        }
        return null;
    },
    initValue:function(){
        $.ajax({
            url:activity.appPath+'/wasteActivity/getWasteActivityById.htm',
            type:'POST',
            data:{
                ticketId:activity.ticketId,
                activityId:activity.activityId
            },
            dataType:'json',
            success:function(data){
                if(data.status==1){
                    var obj=data.data.activity;
                    $('#activityName').val(obj.activityName);
                    $('#entName').val(obj.entName);
                    activity.entId=obj.entId;
                    activity.oldEntId=obj.entId;
                    activity.entName=obj.entName;
                    activity.oldEntName=obj.entName;
                    $('#activityRemark').val(obj.activityRemark);
                    $('#videoResource').val(obj.videoResource);
                    $('#smsTemplate').val(obj.smsTemplate);
                    $('#cronJob').val(obj.cronJob);
                    $('#enrollFee').val(obj.enrollFee);
                    $('#presenter').val(obj.presenter);
                    $('#duration').val(obj.duration);
                    $('#customCourseDesc').val(obj.customCourseDesc);
                    $('#payNote').val(obj.payNote);
                    $('#industry').val(obj.industry);
                    if(obj.riseEndDate){
                        $('#riseEndDate').val(obj.riseEndDate.substring(0,10));
                    }
                    $('#discount').val(obj.discount);
                    $('#numberGroupBuying').val(obj.numberGroupBuying);
                    if(obj.subjectFileId){
                        activity.fileID_subject=obj.subjectFileId;
                        var src=IMG_VIEW_URL+'&fileID='+obj.subjectFileId;
                        $('#previewImg1 img').attr('src', src);
                        $('#previewImg1').show();
                        $('#subject_file').hide();
                    }
                    if(obj.logoFileId){
                        activity.fileID_logo=obj.logoFileId;
                        var src=IMG_VIEW_URL+'&fileID='+obj.logoFileId;
                        $('#previewImg2 img').attr('src', src);
                        $('#previewImg2').show();
                        $('#subject_file2').hide();
                    }
                    if(obj.inquiryFileId){
                        activity.fileID_inquiry=obj.inquiryFileId;
                        var src=IMG_VIEW_URL+'&fileID='+obj.inquiryFileId;
                        $('#previewImg3 img').attr('src', src);
                        $('#previewImg3').show();
                        $('#subject_file3').hide();
                    }
                    if(obj.swipeFileId){
                        activity.fileID_swipe=obj.swipeFileId;
                        var src=IMG_VIEW_URL+'&fileID='+obj.swipeFileId;
                        $('#previewImg4 img').attr('src', src);
                        $('#previewImg4').show();
                        $('#subject_file4').hide();
                    }

                    $('input[name=activityPrice][value='+obj.priceType+']').click();
                    if(obj.priceType==1){
                        $('#price').val(obj.startPrice);
                    }else{
                        $('#min-price').val(obj.startPrice);
                        $('#max-price').val(obj.endPrice);
                    }
                    if(!obj.parentActivityId){
                        $('input[name=hasParent][value="2"]').click();
                        $('#start_date').val(obj.startDate);
                        $('#end_date').val(obj.endDate);
                    }
                    $('#_area').attr('value',obj.cantonCode);
                    $('[data-toggle="city-picker"]').citypicker({
                        responsive:true
                    });
                    if(window.readonly){
                        $('input[name=activityPrice]').attr("disabled","disabled");
                        $('input[name=hasParent]').attr("disabled","disabled");
                        $(".city-picker-span").off('click');
                    }
                }
            }
        });
    },
    //给开始结束日期控件重新初始化
     reInitDatePicker:function($that){
        var id = $that.attr("id");
        var temp = $that.val();
        if (id == "start_date" ) {
            $('#end_date').datetimepicker('setStartDate',temp);
        } else if (id == "end_date" ) {
            $('#start_date').datetimepicker('setEndDate',temp);
        }
    },
    clearNoNum:function(obj) {
        obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
        if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
            obj.value= parseFloat(obj.value);
        }
    },
    init:function(){
        this.ticketId=ticketId;
        this.appPath=$('#appPath').val();
        this.activityId=$('#activityId').val();
        this.bindEvent();
        if(this.activityId){
            this.initValue();
        }else{
            $('[data-toggle="city-picker"]').citypicker({
                responsive:true
            });
        }
    }
}
$(document).ready(function(){
    activity.init();
})
