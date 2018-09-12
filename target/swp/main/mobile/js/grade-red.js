/**
 * Created by yinxl on 2018/1/29.
 */
var grade={
    userId:'',releaseId:'',link:'',browserType:0,entReleaseBonusId:'',ticketId:'',shared:false,
    initShare:function() {
        var config = {
            url:'http://www.yifeiwang.com/swp/main/mobile/view/grade.html?userId='+this.userId+'&releaseId='+this.releaseId,
            title:'易废网-环保卫士',
            desc:'嗨，我刚刚领取了易废网20块钱现金红包，你也赶快来领取吧',
            img:'http://yifeiwang.com/yfwGrade.jpg',
            img_title:'易废网-环保卫士',
            from:'易废网'
        };
        var share_obj = new nativeShare('share',config);
        if(this.browserType==0){
            this.getbuildShareConf();
            this.weixinShareInit();
        }
        $('#share').on('click',function () {
            if(window.browserType==1){
                $('.bg').show();
                if(window.platform=='iPhone'){
                    $('.ios-arrow').show().siblings().hide();
                }else if(window.platform=='Android'){
                    $('.android-arrow').show().siblings().hide();
                }
            }
            if(window.browserType==2){
                $('.share_box').show();
            }
            if(window.browserType==3||window.browserType==4){
                $('.bg').show();
                $('.weixin-arrow').show().siblings().hide();
            }
        });
        $('.bg').on('click',function () {
            $('.bg').hide();
        });
        $('.bg').on('touchmove',function (event) {
            event.preventDefault();
        });
        $('.share_box >a.close').on('click',function () {
            $('.share_box').hide();
        })
        $('.share_box >a:not(.close)').on('click',function () {
            var type=$(this).attr('data-type');
            if(type=='weixinFriend'){
                config.title=config.desc;
            }else{
                config.title='易废网-环保卫士';
            }
            share_obj = new nativeShare('share',config);
            share_obj.share($(this).attr('data-type'));
        })
        $('.share-close').on('click',function () {
            $('.share-success-dialog').hide();
        });
        $('.make-pattern').on('click',function () {
            $('.makePattern-bg').show();
        })
        $('.makePattern-close').on('click',function () {
            $('.makePattern-bg').hide();
        });
    },
    initValue:function () {
        $.ajax({
            url:'/swp/entRelease/generateReleasePoster',
            type:'POST',
            data:{
                userId:this.userId
            },
            dataType:'json',
            success:function (result) {
                if(result.status==1&&result.data){
                    var obj=result.data;
                    var userName=obj['releaseUserName'];
                    $('.userName').html(userName);
                    if(userName.length>4){
                        $('.other-view-message span').html(userName.substring(0,4));
                        $('.other-view-message b').show();
                    }else{
                        $('.other-view-message span').html(userName).css('');
                    }
                    $('.entName').html(obj['entName']);
                    $('.xunzhan_count').html(obj['releaseCount']);
                    var releaseDate=obj.releaseDate.substring(0,10).replace(/-/g,'.');
                    $('.xunzhan_text').html('- '+releaseDate+'获得 -');
                }
            }
        })
    },
    getParam:function(paraName) {
        var search = document.location.search,
            reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
        if (search && reg.test(search)) {
            return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
        }
        return null;
    },
    isWeixin:function () {
        var a = navigator.appVersion.toLowerCase();
        if (a.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    },
    saveReleaseBonus:function () {
        $.ajax({
            url:'/swp/entReleaseBonus/saveReleaseBonus.htm',
            type:'post',
            dataType:'json',
            data:{
                ticketId:this.ticketId,
                browserType:this.browserType,
                userId:this.userId,
                releaseId:this.releaseId
            },
            success:function (result) {
                console.log(result);
                if(result.status==1){
                    grade.entReleaseBonusId=result.data.entReleaseBonus.id;
                }
            }
        });
    },
    sendMessage:function () {
        $.ajax({
            url:'/swp/entReleaseBonus/sendBonusToken.htm',
            type:'post',
            dataType:'json',
            data:{
                ticketId:this.ticketId,
                id:this.entReleaseBonusId
            },
            success:function (result) {
                console.log(result);
                if(result.status==1&&result.data){
                    grade.shared=true;
                }
            }
        });
    },
    init:function () {
        var cache=this.getParam('cache');
        var ticketId=this.getParam('ticketId');
        this.link=window.location.href;
        if(cache==1){
            localStorage.first=true;
            localStorage.ticketId=ticketId;
            window.location=this.link.split('&cache')[0];
            return;
        }
        this.userId=this.getParam('userId');
        this.releaseId=this.getParam('releaseId');
        this.browserType=this.isWeixin()?0:1;
        this.initShare();
        if(localStorage.first&&localStorage.ticketId){
            $('#share').click();
            $('#gradeBg').attr('src','../img/grade-bg.jpg');
            this.ticketId=localStorage.ticketId;
            localStorage.first='';
            this.saveReleaseBonus();
        }else{
            $('.other-view-message').show();
            $('#gradeBg').attr('src','../img/share-other-view.jpg');
            $('.myMessage,#share').hide();
            $('.xunzhan_text').addClass('other-view');
        }
        this.initValue();
    },
    getbuildShareConf:function(){
        // this.buildShareConf = {
        //     appId:"wx1f7da8ce41f7ac3e",
        //     nonceStr:"dceba45f-21d9-4c0f-8fee-4c933fdd5778",
        //     signature:"091384752a063cc9936f89ea29b827318d383e36",
        //     timestamp:"1517202350"
        // };
        this.buildShareConf = this.getShareConfig(this.link);
        //	alert(this.buildShareConf.appId+' '+this.buildShareConf.timestamp+' '+this.buildShareConf.nonceStr+' '+this.buildShareConf.signature);
        wx.config({
            debug: false,
            appId: this.buildShareConf.appId,
            timestamp: this.buildShareConf.timestamp,
            nonceStr: this.buildShareConf.nonceStr,
            signature: this.buildShareConf.signature,
            jsApiList: [
                'checkJsApi',
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareWeibo'
            ]
        });
    },
    getShareConfig:function(wxUrl){
        var buildShareConf = {};
        $.ajax({
            url: "/swp/weixin/getWeiXinConfig",
            data: {
                'url': wxUrl
            },
            type: "get",
            dataType: 'json',
            async: false,
            success: function(data) {
                if (!data||!data.data) {
                    alert('请求出现异常，请联系官方客服');
                    return;
                }
                buildShareConf=data.data;
            },
            error: function(er) {}
        });
        return buildShareConf;
    },
    weixinShareInit:function(){
        var imgurl='http://yifeiwang.com/yfwGrade.jpg';
        var desc = "嗨，我刚刚领取了易废网20块钱现金红包，你也赶快来领取吧";
        var descpy = "嗨，我刚刚领取了易废网20块钱现金红包，你也赶快来领取吧";
        var wxData = {
            "imgUrl": imgurl,
            "sharelink": window.location.href.split('&from')[0],
            "desc": desc,
            "descpy": descpy,
            "title": "易废网-环保卫士",
        };
        wx.ready(function() {
            wx.checkJsApi({
                jsApiList: ['onMenuShareTimeline',
                    'onMenuShareAppMessage',
                    'onMenuShareQQ',
                    'onMenuShareWeibo'
                ]
            });
            wx.onMenuShareTimeline({
                title: wxData.descpy,
                link: wxData.sharelink,
                imgUrl: wxData.imgUrl,
                trigger: function(res) {},
                success: function(res) {//朋友圈
                    // alert('成功分享朋友圈');
                    if(grade.ticketId&&!grade.shared){
                        $('.share-success-dialog').show();
                        $('.bg').hide();
                        grade.sendMessage();
                    }
                },
                cancel: function(res) {
                },
                fail: function(res) {}
            });
            wx.onMenuShareAppMessage({
                title: wxData.title,
                desc: wxData.desc,
                link: wxData.sharelink,
                imgUrl: wxData.imgUrl,
                success: function() {//好友
                    /*if(grade.ticketId&&!grade.shared){
                     $('.share-success-dialog').show();
                     $('.bg').hide();
                     grade.sendMessage();
                     }*/
                },
                cancel: function() {
                }
            });
            wx.onMenuShareQQ({
                title: wxData.title,
                desc: wxData.desc,
                link: wxData.sharelink,
                imgUrl: wxData.imgUrl,
                success: function() {
                },
                cancel: function() {}
            });
            wx.onMenuShareWeibo({
                title: wxData.title,
                desc: wxData.desc,
                link: wxData.sharelink,
                imgUrl: wxData.imgUrl,
                success: function() {
                },
                cancel: function() {}
            });
        });
    }
}
$(document).ready(function () {
    grade.init();
})