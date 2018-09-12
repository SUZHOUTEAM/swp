/**
 * Created by wudang on 2017/7/4.
 */
$(document).ready(function () {
    if(readCookie('sdktoken')){
        $('#IMChat').attr('src','/swp/webdemo/im/main.html');
    }else{
        $('#sessions').hide();
        refreshToken();
    }
    $('#sessions .chat_icon').on('mouseover',function (event) {
        event.stopPropagation();
        $('#sessions').css('right','0').addClass('rightIn').removeClass('rightOut');
        getRecordHtml();
    });
    $('#sessions').on('mouseleave',function (event) {
        event.stopPropagation();
        $('#sessions').css('right','-250px').addClass('rightOut').removeClass('rightIn');
        return false;
    });
    $('.win .closeDialog').on('click',function () {
        $('.win').hide();
    });
    // $('body').on('click', function () {
    //     $('.contacts').hide();
    // });
    $('.close_btn').on('click', function (event) {
        event.preventDefault();
        event.stopPropagation();
        $('.contacts').hide();
        return false;
    });
    dragAndDrop();
    initPosition();
    clickShowBtn();
    setTimeout(function () {
        getRecordHtml();
    },2000);
});
function getRecordHtml(){
    var htmlstr='';
    if(IMChat.window.getRecordHtml){
        htmlstr=IMChat.window.getRecordHtml();
    }
    $('#sessions .m-panel.j-session').html(htmlstr?htmlstr:'<li style="text-align: center">暂无信息</li>');
    $('#sessions .m-panel.j-session img').each(function (item,index) {
        var imgUrl=$(this).attr('src');
        if(imgUrl.indexOf('https:')==-1){
            $(this).attr('src',appPath+'/webdemo/im/'+imgUrl);
        }
    });
    var count=0;
    var countList=$('.panel_multi-row .panel_count');
    for(var i=0;i<countList.length;i++){
        var c=countList.eq(i).html()*1;
        count+=c;
    }
    if(count>0){
        $('#sessions .chat_icon').addClass('hasUnRead').html('新消息<b>'+count+'</b>');
    }else{
        $('#sessions .chat_icon').removeClass('hasUnRead').html('聊天');
    }
    bindEvent();
}
function bindEvent(){
    $('#sessions .m-panel .panel_item').on('click',function () {
        var scene=$(this).attr('data-scene');
        var account=$(this).attr('data-account');
        loadData(ticketId,account);
        IMChat.window.yunXin.openChatBox(account,scene);
        setTimeout(function () {
            getRecordHtml();
        },2000);
    })
}
function loadData(ticketId,phoneNo){
    $('.wTop .btn_record').attr('data-account',phoneNo);
    $.ajax({
        url:appPath+"/im/getUinfos.htm",
        data:{
            ticketId:ticketId,
            phoneNo:phoneNo
        },
        // type: "POST",
        dataType: 'json',
        async: true,
        success: function(data) {
            var enterpriseInfo=data.data.enterpriseInfo;
            var imgurl=appPath+"/webdemo/im/images/default-icon.png";
            var userHead=appPath+'/webdemo/im/images/default-icon.png';
            if(!enterpriseInfo){
                var len=phoneNo.length;
                var str=phoneNo.substring(len-3,len);
                if(str<11){
                    $('.win').show();
                    $('.win .wTop .userlogo img').attr('src',userHead);
                    $('.win .content .ent img').attr('src',imgurl);
                    $('.win .content .ent .entName').html('游客');
                    $('.win .wTop .userName').html('游客'+str);
                }else{
                    initPosition();
                    $('.win').show();
                    var userInfo=data.data.userInfo;
                    var uploadfile=data.data.uploadfile;
                    if(uploadfile&&uploadfile.length>0){
                        var obj=uploadfile[0];
                        userHead = IMG_VIEW_URL+'&businessCode=' + obj.businessCode + '&fileID=' + obj.fileId;
                        $('.win .wTop .userlogo img').attr('src',userHead);
                    }
                    $('.win .wTop .userName').html(userInfo?userInfo['chineseName']:'--');
                    $('.win .content .ent').html('<span style="line-height: 308px;color: #333;font-size: 14px;">未绑定企业</span>');
                }
                return;
            }
            initPosition();
            $('.win').show();
            var uinfos=data.data.uinfos;
            var userInfo=data.data.userInfo;
            var uploadfile=data.data.uploadfile;
            if(enterpriseInfo.businessCode&&enterpriseInfo.fileId){
                imgurl = IMG_VIEW_URL+'&businessCode=' + enterpriseInfo.businessCode + '&fileID=' + enterpriseInfo.fileId;
            }
            if(uploadfile&&uploadfile.length>0){
                var obj=uploadfile[0];
                userHead = IMG_VIEW_URL+'&businessCode=' + obj.businessCode + '&fileID=' + obj.fileId;
                $('.win .wTop .userlogo img').attr('src',userHead);
            }
            $('.win .wTop .userName').html(userInfo?userInfo['chineseName']:'--');

            var str='<p><img src="'+imgurl+'"></p>'+
                '<p class="entName">'+enterpriseInfo.enterName+'</p>'+
                '<p class="entCode">企业代码：'+enterpriseInfo.enterCode+'</p>'+
                '<p class="entType '+enterpriseInfo.enterType['code']+'">'+enterpriseInfo.enterType['value']+'</p>';
            if(enterpriseInfo.enterType&&enterpriseInfo.enterType.code=='DISPOSITION'){
                str+='<p class="entBtn"><a href="javascript:;" class="view-ent-btn" onclick="viewQualification('+enterpriseInfo.id+')">查看资质</a></p>';
            }
            if(enterpriseInfo.enterType&&enterpriseInfo.enterType.code=='PRODUCTION'){
                str+='<p style="margin-top: 20px;color: #333">TEL：'+phoneNo+'</p>';
            }
            $('.win .content .ent').html(str);
        }
    });
}
function showLargeImg(url) {
    $('.large-dialog img').attr('src',url);
    $('.large-dialog').show();
}
function viewQualification(entId) {
    console.log(entId);
    $.ajax({
        url:appPath+'/im/listEntImg',
        data:{
            entId:entId
        },
        async: false,
        success:function (result) {
            console.log(result);
            if(result.status==1){
                var fileList=result.data.entImages;//获取许可证
                var fileList2=result.data.licenceImages;//获取许可证
                if(fileList&&fileList.length>0){
                    $('.qualification-img.item-img1 img').attr('src',IMG_VIEW_URL+'&fileID='+fileList[0]['fileId'])
                }else{
                    $('.qualification-img.item-img1 img').attr('src','http://www.yifeiwang.com/img/source/upload.png')
                }
                if(fileList2&&fileList2.length>0){
                    $('.qualification-img.qualification-img2 img').attr('src',IMG_VIEW_URL+'&fileID='+fileList2[0]['fileId'])
                }else{
                    $('.qualification-img.qualification-img2 img').attr('src','http://www.yifeiwang.com/img/source/upload.png')
                }
            }
            $('.qualification-dialog').show();
        }
    });
   /* var fileList=getImgListByBusinessCode('BUSLIC_'+entId);//获取营业执照
    var fileList2=getImgListByBusinessCode('BUSCERT_'+entId);//获取许可证
    if(fileList&&fileList.length>0){
        $('.qualification-img.item-img1 img').attr('src',IMG_VIEW_URL+'&fileID='+fileList[0]['fileID'])
    }else{
        $('.qualification-img.item-img1 img').attr('src','http://www.yifeiwang.com/img/source/upload.png')
    }
    if(fileList2&&fileList2.length>0){
        $('.qualification-img.item-img2 img').attr('src',IMG_VIEW_URL+'&fileID='+fileList2[0]['fileID'])
    }else{
        $('.qualification-img.item-img2 img').attr('src','http://www.yifeiwang.com/img/source/upload.png')
    }*/

}
function dragAndDrop() {
    var _move = false; //移动标记
    var _x, _y; //鼠标离控件左上角的相对位置
    $(".wTop").mousedown(function(e) {
        _move = true;
        _x = e.pageX - parseInt($(".win").css("left"));
        _y = e.pageY - parseInt($(".win").css("top"));
        //$(".wTop").fadeTo(20,0.5);//点击开始拖动并透明显示
    });
    $(document).mousemove(function(e) {
        if(_move) {
            var x = e.pageX - _x; //移动时鼠标位置计算控件左上角的绝对位置
            var y = e.pageY - _y;
            $(".win").css({ top: y, left: x }); //控件新位置
        }
    }).mouseup(function() {
        _move = false;
        //$(".wTop").fadeTo("fast",1);//松开鼠标后停止移动并恢复成不透明
    });
}
function initPosition() {
    //计算初始化位置
    var itop = ($(window).height() - $(".win").height()) / 2;
    var ileft = ($(window).width() - $(".win").width()) / 1.8;
    //设置被拖拽div的位置
    $(".win").css({ top: itop, left: ileft });
}
function clickShowBtn() {//点击显示按钮
    $(".win .closeDialog").click(function () {
        $(".win").hide();
        var scene='p2p';
        var account='888888888888888';
        IMChat.window.yunXin.openChatBox(account,scene);
    });
    $('.btn_record').on('click', function () {
        var str = '<div class="cloud-msg-container" id="cloudMsgContainer">' +
            '<div class="info-box">' +
            '<div class="title tc">' +
            '<button class="btn back-btn radius5px j-backBtn">关闭</button>云记录' +
            '</div>' +
            '<div class="info-content">' +
//								'<div class="u-status tc"><span class="radius5px"><a class="j-loadMore">加载更多记录</a></span></div>'+
            '<ul id="cloudMsgList" class="f-cb"><div class="load"><img src="' +appPath + '/main/pc/img/load.gif"></div></ul>' +
            '</div>' +
            '</div>' +
            '</div>';
        var cloudMsgContainer = $('#cloudMsgContainer');
        if (cloudMsgContainer.length > 0) {
            cloudMsgContainer.remove();
        }
        $('body').append(str);
        $('#cloudMsgContainer .back-btn').on('click', function () {
            $('#cloudMsgContainer').remove();
        });
        var phoneNo=$(this).attr('data-account');
        loadHistoryData(phoneNo);
    })
}
function loadHistoryData(phoneNo){
    $.ajax({
        url:appPath+"/imService/queryChatLog.htm?num="+new Date().getTime(),
        data:{
            ticketId:ticketId,
            toId:phoneNo
        },
        type: "POST",
        dataType: 'json',
        async: false,
        success: function(data) {
            if(data.status==1){
                var chatLog=data.data.chatLog||[];
                var fromUserLog=data.data.fromUserLog;
                var toUserLog=data.data.toUserLog;
                var str='';
                var meFace=appPath+"/webdemo/im/images/default-icon.png";
                if(fromUserLog.length>0&&fromUserLog[0].businessCode&&fromUserLog[0].fileId){
                    meFace = IMG_VIEW_URL+'&businessCode=' + fromUserLog[0].businessCode + '&fileID=' + fromUserLog[0].fileId;
                }
                var youFace=appPath+"/webdemo/im/images/default-icon.png";
                if(toUserLog.length>0&&toUserLog[0].businessCode&&toUserLog[0].fileId){
                    youFace = IMG_VIEW_URL+'&businessCode=' + toUserLog[0].businessCode + '&fileID=' + toUserLog[0].fileId;
                }
                if(chatLog.length==0){
                    str = '<div class="no-msg tc"><span class="radius5px">暂无消息</span></div>';
                }
                for (var i=0;i<chatLog.length;i++) {
                    var obj=chatLog[i];
                    var flag=obj.from==phoneNo;//true是对方  false是自己
                    str+='<p class="u-msgTime">- - - - -&nbsp;'+format(obj.sendtime)+'&nbsp;- -- - -</p>'+
                        '<div class="item item-'+(flag?'you':'me')+'">'+
                        '<img class="img j-img" src="'+(flag?youFace:meFace)+'">'+
                        '<div class="msg msg-text j-msg">'+
                        '<div class="box">'+
                        '<div class="cnt">'+
                        getMsgBody(obj.body)+
                        '</div>'+
                        '</div>'+
                        '</div></div>';
                }
                $('#cloudMsgList').html(str);
                $('.u-audio').on('click',function(){
                    $(this).addClass('play');
                    var time=$(this).find('a').attr('data-dur')*1;
                    setTimeout(function(){
                        $('.u-audio').removeClass('play');
                    },time);
                });
            }
        }
    });
}
function format(time) {
    var now = new Date(time);
    var LSTR_Year = now.getYear() > 1000 ? now.getYear() : (now.getYear() + 1900);
    var LSTR_Month = now.getMonth() + 1;
    var LSTR_Date = now.getDate();
    var LSTR_Hour=now.getHours();
    var LSTR_Minutes=now.getMinutes();
    if (LSTR_Date == 0) {
        LSTR_Date += 1;
    }
    return LSTR_Year + '-'+parseNum(LSTR_Month)+ '-'+parseNum(LSTR_Date)+' '+parseNum(LSTR_Hour)+':'+parseNum(LSTR_Minutes);
}
function parseNum(num){
    return num<10?('0'+num):num;
}
function getMsgBody(body){
    var str='';
    var msg=body.msg;
    if(msg){
        str=getEmojiHtml(msg);
    }else if(body.ext&&body.ext=='aac'){
        str='<div class="u-audio j-mbox left">'+
            '<a href="javascript:;" class="j-play playAudio" data-dur="'+body.dur+'" data-src="'+body.url+'">点击播放</a>' +
            '<b class="j-duration">'+Math.floor(body.dur/1000)+'"</b><span class="u-icn u-icn-play" title="播放音频"></span>' +
            '</div>';
    }else if(body.ext&&body.url){
        str='<a href="'+body.url+'" target="_blank"><img src="'+body.url+'"></a>'
    }
    return str;
}
function shake() {
    document.getElementsByTagName("title")[0].innerText ='有新消息';
    getRecordHtml();
    $('#sessions .chat_icon').addClass("animated_wobble wobble hasUnRead");
    setTimeout(function () {
        $('#sessions .chat_icon').removeClass('animated_wobble wobble');
    },3000);
}
function refreshToken(){
    $.ajax({
        url:appPath+"/im/refreshToken.htm?num="+new Date().getTime(),
        data:{
            ticketId:ticketId
        },
        type: "POST",
        dataType: 'json',
        async: true,
        success: function(data) {
            console.log(data);
            if(data.status==1&&data.data.user){
                $.notify('token已过期，请重新登录',{status:'info',timeout:1500});
                setTimeout(function () {
                    location=appPath+'/login.jsp';
                },1500)
            }
        }
    });
}
function getEnterpriseContacts(enterpriseId,entName,pageName) {
    collectingUserBehavior(ticketId,'CONTACTHIM',entName||'',(pageName?('PC'+pageName):''));
    // var e;
    // if(arguments.callee&&arguments.callee.caller&&arguments.callee.caller.arguments[0]){
    //     e=arguments.callee.caller.arguments[0];
    // }
    var event = event || window.event;
    if(event){
        event.preventDefault();
        event.stopPropagation();
    }
    if (!readCookie('sdktoken')) {
        $.notify("您尚未开通在线服务", {status: 'danger', timeout: 1500});
        return;
    }
    $.ajax({
        url: appPath + "/wastecircle/getEnterpriseContacts.htm?ticketId="
        + ticketId + "&enterpriseId=" + enterpriseId,
        type: "POST",
        dataType: 'json',
        async: false,
        success: function (result) {
            var contactsList = result.data.contactsList;
            if (!contactsList || contactsList.length == 0) {
                /* $.notify("对方暂未开通在线服务",{status:'danger',timeout:1500});
                 return;*/
                $("#offline_ticketId").val(ticketId);
                $("#offline_enterpriseId").val(enterpriseId);
                $('#msgcontext').val(
                    '我有废物要处理，看到信息请联系我的手机号: ' + result.data.myPhone + ' --- '
                    + result.data.myName);
                $('#myModal').modal({
                    keyboard: true,
                    backdrop: false,
                    show: true,
                });
                return;
            }
            if (contactsList.length == 1) {
                showIM(contactsList[0].userId, result.data.myPhone);
                return;
            }
            var str = '';
            if(enterpriseId=='2548143641135104'){
                contactsList.sort(function (o1,o2) {
                    return o1.chineseName.length>o2.chineseName.length?-1:1
                })
            }
            for (var i in contactsList) {
                var obj = contactsList[i];
                str += '<a href="javascript:;" onclick="showIM(\'' + obj.userId
                    + '\',\'' + result.data.myPhone
                    + '\')"><img src="'+appPath+'/main/pc/img//qipao.jpg"/>' + obj.chineseName
                    + '</a>';
            }
            $('.contacts .contacts_list').html(str);
            $('.contacts').show();
            return false;
            //弹出联系人列表
        },
        error: function () {
        }
    });
}
function showIM(publisherId, myPhone) {
    $('.addFriend_loading').show();
    $('.contacts').hide();
    $.ajax({
        url: appPath + "/wastecircle/getPhoneNoByUserId.htm?ticketId="
        + ticketId + "&publisherId=" + publisherId,
        type: "POST",
        dataType: 'json',
        async: false,
        success: function (result) {
            $('.addFriend_loading').hide();
            var phoneNo = result.data.phoneNo;
            dplus.track("【资源池】联系TA", {
                "from": myPhone,
                "to": phoneNo
            });
            var scene = 'p2p';
            var account = phoneNo;
            IMChat.window.yunXin.openChatBox(account, scene);
            $('.win').show();
            loadData(ticketId, account);
            return false;
        },
        error: function () {
            $('.addFriend_loading').hide();
        }
    });
}
function saveOfflineMsg() {
    var enterpriseId = $("#offline_enterpriseId").val();
    var context = $("#msgcontext").val();
    $.ajax({
        url: appPath + "/wastecircle/saveOfflineMsg",
        data: {
            'ticketId': ticketId,
            'toEntId': enterpriseId,
            // 'businessCode': waste.businessCode,
            'context': context
        },
        type: "POST",
        dataType: 'json',
        async: false,
        success: function (result) {
            $('#myModal').modal('hide');
            $.notify("留言已提交，请保持电话畅通！", {status: 'info', timeout: 1500});
        },
        error: function () {
        }
    });

}