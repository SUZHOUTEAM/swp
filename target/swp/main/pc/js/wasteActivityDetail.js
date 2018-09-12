/**
 * Created by AaronWang on 2017/8/1 0001.
 */
var wasteActivityDetail={
    ticketId:null,
    activityId:null,
    dateFinal:0,
    dateNow:0,
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
                var activityDetailList = result.data.activityDetailList;
                var wasteActivityEnterpriseDatail = activityDetailList[0];
                var imgUrl=IMG_VIEW_URL+'&fileID='+wasteActivityEnterpriseDatail.subjectFileId;
                $('.activity').css('background-image','url('+imgUrl+')');
                $(".entName .mydata").text(wasteActivityEnterpriseDatail.entName);
                if(wasteActivityEnterpriseDatail.licenceNo != "" && wasteActivityEnterpriseDatail.licenceNo != null){

                    $(".licNo .mydata").text(wasteActivityEnterpriseDatail.licenceNo);
                }else {
                    $(".licNo .mydata").text("----");
                }
                if(wasteActivityEnterpriseDatail.licStartDate != "" && wasteActivityEnterpriseDatail.licStartDate != null
                &&wasteActivityEnterpriseDatail.licEndDate != "" && wasteActivityEnterpriseDatail.licEndDate != null){
                    $(".licDate .mydata").text(wasteActivityEnterpriseDatail.licStartDate.substring(0,10)+"--"+wasteActivityEnterpriseDatail.licEndDate.substring(0,10));
                }else{
                    $(".licDate .mydata").text("----");
                }
                if(wasteActivityEnterpriseDatail.machineAddr != "" && wasteActivityEnterpriseDatail.machineAddr != null){
                    $(".entAddress .mydata").text(wasteActivityEnterpriseDatail.machineAddr);
                }else{
                    $(".entAddress .mydata").text("----");
                }


                if(wasteActivityEnterpriseDatail.activityRemark != "" && wasteActivityEnterpriseDatail.activityRemark != null){
                    $(".remarkdetail").html(wasteActivityEnterpriseDatail.activityRemark);
                }
                var s =  wasteActivityEnterpriseDatail.endDate.replace(new RegExp("-","gm"),"/");
                var e =  result.data.serverTime.replace(new RegExp("-","gm"),"/");
                wasteActivityDetail.dateFinal = (new Date(s)).getTime();
                wasteActivityDetail.dateNow = (new Date(e)).getTime();
                if(wasteActivityDetail.entName='易废网处置旗舰店'){
                    $('.licNo').hide();
                    $('.licDate').hide();
                    $('#know-yfw').hide();
                    $('.row .entName').width('494px').css('margin-right','8px');
                    $('#know-yfw-top').show().width('182px');
                    if(wasteActivityDetail.activityId=='a7ea6b0e017a47a680b57e2d5d48d568'){
                        $('#qixiu-customer img').attr('src','../img/qixiu-customer-long.jpg');
                        $('#qixiu-customer').show();
                    }else{
                        $('#qixiu-customer img').attr('src','../img/yfw-customer2.png');
                        $('#qixiu-customer').show();
                    }
                    // $('#qixiu-customer').show();
                }

            },
            error: function(er) {
                jqueryDialog.alert({'msg':'加载数据失败','timeout':1000,'type':'danger'});
            }
        });
    },
    goIntroduction:function () {
        window.location.href = "../../../introduction.html"
    },
    goActivityList:function () {
        window.location.href = "activityList.html"
    },
    bindEvent:function(){
        // $('#selectbtn_company,.company_menu').on('mouseover',function(){
        //     $('.dropdown-menu#company_dropdown').removeClass('hidden');
        //     return false;
        // });
        $('#selectbtn_company').on('click', function() {
            window.location.href = "company.html" ;
        });
        // $(".button .gobtn").on('click',function(){
        //         alert("立即抢购!")
        // });
        $('.button .gobtn,.activity_btn').on('click',function(){
            if(index.ticketId&&index.isLogin){
                if(index.entType=='PRODUCTION'){
                    window.location='../../../entRelease/entWasteList.htm?ticketId='+index.ticketId;
                }else{
                    // jqueryDialog.alert({msg:'该活动只面向产废企业',tip:'',type:'info',timeout:2000});
                    alert('该活动只面向产废企业');
                }
                // if(index.entType=='FACILITATOR'){
                    // location='../../../facilitator/customerList.htm?ticketId='+index.ticketId;
                    // $.notify("服务商暂不可参加活动",{status: 'info',timeout:10000});
                //     jqueryDialog.alert({msg:'服务商暂不可参加活动',tip:'',type:'info',timeout:2000});
                // }else{
                //     location='../../../wasteActivity/buy.htm?ticketId='+index.ticketId+'&activityId='+wasteActivityDetail.activityId;
                    // location='../../../wasteActivity/buy.htm?ticketId='+index.ticketId+'&activityId='+wasteActivityDetail.activityId;
                // }
            }else{
                window.location='../../../login.jsp';
                // location='../../../login.jsp?redirectURL=/wasteActivity/buy.htm?activityId='+wasteActivityDetail.activityId;
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

    init:function(){
        wasteActivityDetail.activityId=this.getParam('activityId');
       wasteActivityDetail.getActivityDetailById();
       this.bindEvent();
    }

}
//页面加载完成时初始化功能
var timer;
$(document).ready(function() {
   wasteActivityDetail.init();
    wasteActivityDetail.dateNow = wasteActivityDetail.dateNow+1000;
    if(wasteActivityDetail.dateFinal<wasteActivityDetail.dateNow){
        $('.time b').html('已结束');
        clearInterval(timer);
        $('.gobtn').addClass('grey').unbind('click');
        $('.activity_btn').addClass('grey').unbind('click');
        return;
    }
    timer=setInterval(function(){
        wasteActivityDetail.dateNow = wasteActivityDetail.dateNow+1000;
        countDown(wasteActivityDetail.dateFinal,wasteActivityDetail.dateNow);
    },1000);
});

function countDown(dateFinal,dateNow){
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
        timeHtml += toDouble(hour) + ':' + toDouble(minute) + ':' + toDouble(second) ;
    }else{
        timeHtml += day + '天 : ' + toDouble(hour) + ':' + toDouble(minute) + ':' + toDouble(second) ;
    }
    $('.time b').html(timeHtml);
}
//当小时，分钟和秒钟小于 10 的时候会显示为个位数，比较难看，需要在前面加 0。
function toDouble(num){
    if(num < 10){
        return '0'+ num;
    }else{
        return '' + num;
    }
}


