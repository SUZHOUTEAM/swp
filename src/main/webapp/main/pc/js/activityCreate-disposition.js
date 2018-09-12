/**
 * Created by wudang on 2017/8/1.
 */
var activity={
    entId:'',entName:'',ticketId:'',activityId:'',
    appPath:'',oldEntId:'',oldEntName:'',
    areaList:['110','120','310','710','810','820'],
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
        $('#submit').on('click',function(){
            var reg = /^[0-9]+(.[0-9]{1,2})?$/
            var activityName=$('#activityName').val();
            if(!activityName){
                $.notify("请填写活动名称", { status: 'danger',timeout:1500 });
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
            if(priceType==2&&$('#min-price').val()!=''&&$('#max-price').val()!=''&&$('#min-price').val()*1>$('#max-price').val()*1){
                $.notify("最低价不能大于最高价", { status: 'danger',timeout:1500 });
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
            }
            $("#submit").attr("disabled","disabled");
            $("#submit").text("提交中...");
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
                entId:globalInit.enterpriseId,
                entName:globalParam.entName,
                entRemark:'',
                startPrice:startPrice,
                endPrice:endPrice,
                activityId:activity.activityId
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
                        if(!activity.activityId){
                            activity.updateActivityCoverEntCount(resultData.data);
                        }else{
                            $.notify(action+"活动成功", { status: 'success',timeout:1500 });
                            setTimeout(function () {
                                history.go(-1);
                            },1500)
                        }
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
    updateActivityCoverEntCount:function (id) {
      $.ajax({
          url:activity.appPath+'/wasteActivity/updateActivityCoverEntCount.htm',
          type:'POST',
          dataType: "json",
          data: {id:id,ticketId:activity.ticketId},
          success:function (result) {
              if(result.status==1){
                  var action=activity.activityId?'修改':'创建';
                  $.notify(action+"活动成功", { status: 'success',timeout:1500 });
                  setTimeout(function () {
                      history.go(-1);
                  },1500)
              }
          }
      })
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
