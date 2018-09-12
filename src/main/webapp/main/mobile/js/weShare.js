/**
 * Created by yinxl on 2018/7/23.
 */
(function(){
    var share={
        link:'',title:'',desc:'',logo:'http://www.yifeiwang.com/swp/main/mobile/img/app_logo3.png',
        init:function () {
            this.title=$('title').text();
            this.desc=$('meta[name=description]').attr('content');
            this.link=window.location.href;
            this.getbuildShareConf();
            this.weixinShareInit();
        },
        isWeixin:function() {
            var ua = navigator.userAgent.toLowerCase();
            var isWeixin = ua.indexOf('micromessenger') != -1;
            if (isWeixin) {
                return true;
            }else{
                return false;
            }
        },
        getParam:function(paraName) {
            var search = document.location.search,
                reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
            if (search && reg.test(search)) {
                return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
            }
            return null;
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
            var imgurl=this.logo;
            var desc = this.desc;
            var descpy = this.desc;
            var wxData = {
                "imgUrl": imgurl,
                "sharelink": window.location.href.split('&from')[0],
                "desc": desc,
                "descpy": descpy,
                "title": this.title,
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
    };
    if(localStorage.firstViewInfo){
        localStorage.firstViewInfo='';
    }else{
        $('body').append('<a href="http://www.yifeiwang.com/swp/WeChat" class="enter-btn"></a>');
    }
    if(share.isWeixin()){
        $.getScript('http://res.wx.qq.com/open/js/jweixin-1.0.0.js',function () {
            $('.header').append('<a href="javascript:;" class="share-btn" onclick="$(\'.share-tip\').show()">分享<\/a>');
            $('body').append('<div class="share-tip" onclick="$(\'.share-tip\').hide()"><img src="http://www.yifeiwang.com/swp/main/mobile/img/sharetip2.png"/><span>请点击右上角的分享按钮</span></div>');
            share.init();
        })
        // document.write('<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"><\/script>');
    }
})();
