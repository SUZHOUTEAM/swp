/**
 * Created by AaronWang on 2017/8/1 0001.
 */
var wasteActivityDetail={
    ticketId:null,
    activityId:null,
    dateFinal:0,
    dateNow:0,
    statusArr:{'SUBMIT':'已提交报名,待付款','PAYMENTSUCCESS':'已报名成功'},
    currentTime:0,startDate:0,busiStatus:'',videoUrl:'',intervalTimer:null,
    activityName:'',enrollFee:0,discount:1,discountTime:false,
    getActivityDetailById:function () {
        $.ajax({
            url: "../../../wasteActivity/getActivityDetailById.html",
            data: {
                'activityId':wasteActivityDetail.activityId
            },
            type: "POST",
            dataType: 'json',
            async: false,
            success: function(result) {
                if(result.status!=1){
                    return;
                }
                var activityDetailList = result.data.activityDetailList;
                var wasteActivityEnterpriseDatail = activityDetailList[0];
                wasteActivityDetail.activityName=wasteActivityEnterpriseDatail.activityName;
                wasteActivityDetail.enrollFee=wasteActivityEnterpriseDatail.enrollFee;
                wasteActivityDetail.discount=wasteActivityEnterpriseDatail.discount;
                $('.activity-title b').html(wasteActivityEnterpriseDatail.activityName);
                $('.price-text b').html(wasteActivityEnterpriseDatail.enrollFee*wasteActivityEnterpriseDatail.discount);
                $('.old-price-text b').html(wasteActivityEnterpriseDatail.enrollFee);
                var enrollActivityAcount=wasteActivityEnterpriseDatail.enrollActivityAcount;
                var numberGroupBuying=wasteActivityEnterpriseDatail.numberGroupBuying;
                var num=numberGroupBuying-enrollActivityAcount;
                $('.remainText b').html(num<0?0:num);
                $('.riseEndTime b').html(wasteActivityEnterpriseDatail.riseEndDate.substring(0,16));
                $('#progress').attr('value',enrollActivityAcount);
                $('#progress').attr('max',numberGroupBuying);
                var imgUrl=IMG_VIEW_URL+'&fileID='+wasteActivityEnterpriseDatail.logoFileId;
                $('.activity-logo img').attr('src',imgUrl);
                $(".entName .mydata").text(wasteActivityEnterpriseDatail.presenter);
                $(".licNo .mydata").text(wasteActivityEnterpriseDatail.entName);
                $(".licDate .mydata").text(wasteActivityEnterpriseDatail.duration);
                $(".entAddress .mydata").text(wasteActivityEnterpriseDatail.startDate.substring(0,16));
                $("#detail1").html(wasteActivityEnterpriseDatail.activityRemark);
                $("#detail2").html(wasteActivityEnterpriseDatail.customCourseDesc);
                $("#detail3").html(wasteActivityEnterpriseDatail.payNote);
                var longTime=new Date(result.data.serverTime.replace(new RegExp("-","gm"),"/")).getTime();
                var startDate=new Date(wasteActivityEnterpriseDatail.startDate.replace(new RegExp("-","gm"),"/")).getTime();
                var endDate=new Date(wasteActivityEnterpriseDatail.endDate.replace(new RegExp("-","gm"),"/")).getTime();
                var riseEndDate=new Date(wasteActivityEnterpriseDatail.riseEndDate.replace(new RegExp("-","gm"),"/")).getTime();
                wasteActivityDetail.currentTime=longTime;
                wasteActivityDetail.startDate=startDate;
                wasteActivityDetail.videoUrl=wasteActivityEnterpriseDatail.videoResource;
                if(longTime>endDate){
                    $('.remainTime').html('已结束');
                    clearInterval(timer);
                    $('.gobtn').addClass('grey').unbind('click');
                    $('.entry-btn').addClass('grey').unbind('click');
                }else{
                    wasteActivityDetail.createTimerRise(longTime,riseEndDate,startDate,wasteActivityEnterpriseDatail.enrollFee);
                }
                if(index.ticketId&&index.isLogin){
                    wasteActivityDetail.checkEnrollStatus();
                }
            },
            error: function(er) {
                jqueryDialog.alert({'msg':'加载数据失败','timeout':1000,'type':'danger'});
            }
        });
    },
    createTimerRise:function (currentTime,riseEndDate,startDate,enrollFee) {
        if(currentTime<riseEndDate){
            wasteActivityDetail.intervalTimer=setInterval(function () {
                currentTime+=1000;
                // var item=vue.activityList[index];
                var title=$('.price-title b').html();
                if(currentTime<riseEndDate){
                    wasteActivityDetail.discountTime=true;
                    if(title!='优惠倒计时'){
                        $('.price-title b').html('优惠倒计时')
                    }
                    wasteActivityDetail.countDown(riseEndDate,currentTime);
                }else{
                    wasteActivityDetail.discountTime=false;
                    if(title!='距离开播'){
                        $('.price-title b').html('距离开播')
                    }
                    $('.old-price-text,.riseEndTime').hide();
                    $('.price-text b').html(enrollFee);
                    $('.info').eq(1).hide();
                    $('.info').eq(0).find('.price-title').html('报名费用：');
                    wasteActivityDetail.countDown(startDate,currentTime);
                }
            },1000)
        }else if(currentTime<startDate){
            wasteActivityDetail.discountTime=false;
            var title=$('.price-title b').html();
            if(title!='距离开播'){
                $('.price-title b').html('距离开播');
                $('.old-price-text,.riseEndTime').hide();
                $('.price-text b').html(enrollFee);
                $('.info').eq(1).hide();
                $('.info').eq(0).find('.price-title').html('报名费用：');
            }
            wasteActivityDetail.intervalTimer=setInterval(function () {
                if(currentTime>=startDate){
                    $('.remainTime').html('进行中').prev().find('b').html('课程状态');
                    $('.old-price-text').hide();
                    $('.price-text b').html(enrollFee);
                    clearInterval(wasteActivityDetail.intervalTimer);
                    return;
                }
                currentTime+=1000;
                wasteActivityDetail.countDown(startDate,currentTime);
            },1000)
        }else {
            wasteActivityDetail.discountTime=false;
            $('.remainTime').html('进行中').prev().find('b').html('课程状态');
            $('.old-price-text,.riseEndTime').hide();
            $('.price-text b').html(enrollFee);
            $('.info').eq(1).hide();
            $('.info').eq(0).find('.price-title').html('报名费用：');
        }
    },
    countDown:function(dateFinal,dateNow){
        var dateSub = dateFinal - dateNow;  //计算差值，单位毫秒
        var day , hour , minute, second ,dayBase , hourBase, minuteBase, secondBase;
        day = hour = minute = second = dayBase = hourBase = minuteBase = secondBase = 0;  //初始化各个数值
        var timeHtml = '';
        timeHtml += " ";
        dayBase = 24 * 60 * 60 * 1000;  //计算天数的基数，单位毫秒。1天等于24*60*60*1000毫秒
        hourBase = 60 * 60 * 1000;  //计算小时的基数，单位毫秒。1小时等于60*60*1000毫秒
        minuteBase = 60 * 1000;  //计算分钟的基数，单位毫秒。1分钟等于60*1000毫秒
        secondBase = 1000;  //计算秒钟的基数，单位毫秒。1秒钟等于1000毫秒
        day = Math.floor(dateSub / dayBase);  //计算天数，并取下限值。如 5.9天 = 5天
        hour = Math.floor(dateSub % dayBase / hourBase);  //计算小时，并取下限值。如 20.59小时 = 20小时
        minute = Math.floor(dateSub % dayBase % hourBase / minuteBase);  //计算分钟，并取下限值。如 20.59分钟 = 20分钟
        second = Math.floor(dateSub % dayBase % hourBase % minuteBase / secondBase);  //计算秒钟，并取下限值。如 20.59秒 = 20秒
        //当天数小于等于0时，就不用显示
        if(day <= 0){
            timeHtml += wasteActivityDetail.toDouble(hour) + ':' + wasteActivityDetail.toDouble(minute) + ':' + wasteActivityDetail.toDouble(second) ;
        }else{
            timeHtml += day + '天 : ' + wasteActivityDetail.toDouble(hour) + ':' + wasteActivityDetail.toDouble(minute) + ':' + wasteActivityDetail.toDouble(second) ;
        }
        $('.remainTime').html(timeHtml);
    },
     toDouble:function(num){
        if(num < 10){
            return '0'+ num;
        }else{
            return '' + num;
        }
    },
    goIntroduction:function () {
        window.location.href = "../../../introduction.html"
    },
    goActivityList:function () {
        window.location.href = "activityList.html"
    },
    bindEvent:function(){
        $('#selectbtn_company').on('click', function() {
            window.location.href = "company.html";
        });
        $('.button .gobtn,.entry-btn').on('click',function(){
            if(index.ticketId&&index.isLogin){//已登录
                var data= {
                    msg: '该操作将提交报名，是否确定？',
                    title: '操作确认',
                    type: 'info',
                    cancelText: '取消',
                    sureText: '确定',
                    success: function () {
                        ajax({
                            url:'/wasteActivityEnroll/saveWasteActivityEnroll.htm?ticketId='+index.ticketId,
                            data:JSON.stringify({
                                activityId:wasteActivityDetail.activityId
                            }),
                            contentType:'application/json',
                            dataType:'json',
                            type:'post',
                            success:function (result) {
                                if(result.status==1&&result.data==true){
                                    if(result.infoList.length>0){
                                        jqueryDialog.alert({'msg':result.infoList[0],'timeout':2000,'type':'info'});
                                    }else{
                                        var price=wasteActivityDetail.discountTime?wasteActivityDetail.enrollFee*wasteActivityDetail.discount:wasteActivityDetail.enrollFee;
                                        window.location='enterSuccess.html?ticketId='+index.ticketId+'&activityName='+wasteActivityDetail.activityName+'&price='+price;
                                        /*jqueryDialog.alert({'msg':'已提交报名，请按短信要求付款','timeout':5000,'type':'success'});
                                        $('.gobtn').addClass('grey').unbind('click').html(wasteActivityDetail.statusArr['SUBMIT']);
                                        $('.entry-btn').addClass('grey').unbind('click').html(wasteActivityDetail.statusArr['SUBMIT']);
                                        var remainText=$('.remainText b').html();
                                        var value=$('#progress').attr('value');
                                        $('.remainText b').html(remainText*1-1);
                                        $('#progress').attr('value',value*1+1);*/
                                    }
                                }else{
                                    jqueryDialog.alert({'msg':'报名失败','timeout':2000,'type':'info'});
                                }
                            }
                        })
                    }
                };
                jqueryDialog.confirmTable(data);
            }else{
                var data= {
                    msg: '该操作需要先登录，现在去登录？',
                    title: '操作确认',
                    type: 'info',
                    cancelText: '稍后再说',
                    sureText: '去登录',
                    success: function () {
                        location='../../../login.jsp?redirectURL=/main/pc/view/feeActivityDetail.html?activityId='+wasteActivityDetail.activityId;
                    }
                };
                jqueryDialog.confirmTable(data);
            }
        });
        $(".button .backbtn").on('click',function(){
            history.go(-1);
        });

    },
    getParam:function(paraName) {
        var search = document.location.search,
            reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
        if (search && reg.test(search)) {
            return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
        }
        return null;
    },
    showLiveBtn:function () {
      if(wasteActivityDetail.currentTime>wasteActivityDetail.startDate&&wasteActivityDetail.busiStatus=='PAYMENTSUCCESS'){
        $('.goLive').show();
        $('.goLive2').css('display','inline-block');
        $('.entry-btn,.gobtn').hide();
        clearInterval(wasteActivityDetail.intervalTimer);
        $('.remainTime').html('已开始');
          $('.old-price-text').hide();
          $('.price-text b').html($('.old-price-text b').html());
          $('.info').eq(1).hide();
          $('.info').eq(0).find('.price-title').html('报名费用：');
        $('.goLive,.goLive2').on('click',function () {
            var ticketStr=(index.ticketId?('&ticketId='+index.ticketId):'');
            window.location=wasteActivityDetail.videoUrl+'?activityId='+wasteActivityDetail.activityId+ticketStr;
        })
      }
    },
    checkEnrollStatus:function () {
        ajax({
            url:'/wasteActivityEnroll/checkEnrollStatus.htm?ticketId='+index.ticketId,
            data:JSON.stringify({
                activityId:wasteActivityDetail.activityId
            }),
            contentType:'application/json',
            dataType:'json',
            type:'post',
            success:function (result) {
                if(result.status==1&&result.data){
                    var busiStatus=result.data.busiStatus;
                    wasteActivityDetail.busiStatus=busiStatus;
                    var text=wasteActivityDetail.statusArr[busiStatus];
                    $('.gobtn').addClass('grey').unbind('click').html(text);
                    $('.entry-btn').addClass('grey').unbind('click').html(text);
                    wasteActivityDetail.showLiveBtn();
                }
            }
        })
    },
    init:function(){
        wasteActivityDetail.activityId=this.getParam('activityId');
        wasteActivityDetail.getActivityDetailById();
       this.bindEvent();
    }

}
//页面加载完成时初始化功能
var timer,appPath='/swp';
$(document).ready(function() {
   wasteActivityDetail.init();
});

