var grade={
    userId:'',link:'',
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
                    $('.userName').html(obj['releaseUserName']);
                    $('.entName').html(obj['entName']);
                    $('.xunzhan_count,.xunzhan_text font').html(obj['releaseCount']);
                    var releaseDate=obj.releaseDate.substring(0,10).replace(/-/g,'.');
                    $('.xunzhan_text span').html('- '+releaseDate+'获得 -');
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
    init:function () {
        this.userId=this.getParam('userId');
        this.initValue();
        this.link=window.location.href;
        this.getbuildShareConf();
        this.weixinShareInit();
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
        var desc = "嗨，我在易废网获得绿色环保卫士勋章，你也来试一下吧";
        var descpy = "嗨，我在易废网获得绿色环保卫士勋章，你也来试一下吧";
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