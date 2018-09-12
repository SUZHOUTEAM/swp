/**
 * Created by wudang on 2017/7/17.
 */
var index={
    type:'PRODUCTION',
    bindEvent:function(){
        $('#searchDiv .sd_tab a').on('click',function(){
            index.type=$(this).index()==0?'PRODUCTION':'DISPOSITION';
            $(this).addClass('active').siblings().removeClass('active');
        });
        $('.div3 a').on('click',function () {
            $('.tipDialog').hide();
        })
        $('#search').click(function(){
            setTimeout(function() {
                document.getElementById("searchDiv").scrollIntoView(true);
            }, 100);
        });
        $('#search_btn').on('click',function(){
            dplus.track("【手机端web首页】搜索", {
                "key": $('#search').val(),
            });
            location="search.html?type="+index.type+'&key='+$('#search').val();
        });
        $('.td a.able').on('click',function(){
            var index=$(this).index();
            var currentIndex=$('.td a.active').index();
            if(index==currentIndex){
                return;
            }
            $('#entList').html('');
            $('#loadComplete').hide();
            if(index==0){//产废信息
                search.type='PRODUCTION';
                search.keyWord='';
                search.page=1;
                search.loadComplete=false;
                search.initValueForWaste();
            }else if(index==1){
                search.type='DISPOSITION';
                search.keyWord='';
                search.page=1;
                search.loadComplete=false;
                search.initValue();
            }
            $(this).addClass('active').siblings().removeClass('active');
        });
        $('.dload .close_btn').on('click',function(){
            $('.dload').hide();
        });
        $('.entertip').on('click',function(){
            $(this).hide();
        });
        $('.dload .open_btn').on('click',function(){
            if(index.isWinxin()){
                $('.entertip').show();
                return;
            }
            // var u = navigator.userAgent;
            // var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            // var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            // if(isAndroid){//android设备
            //     window.location.href='hwy://pushui/?ui=RecSynthesizeView';
            //     setTimeout(function(){
            //         window.location.href='http://a.app.qq.com/o/simple.jsp?pkgname=com.GF.platform';
            //     },2000);
            // }else if(isiOS){//IOS设备
            //     window.location.href='hwy://pushui/?ui=RecSynthesizeView';
            //     setTimeout(function(){
            //         window.location.href='http://a.app.qq.com/o/simple.jsp?pkgname=com.GF.platform';
            //     },2000);
            // }
        });
    },
    isWinxin:function(){
        var u = navigator.userAgent, app = navigator.appVersion.toLowerCase();
        var ua = window.navigator.userAgent.toLowerCase();
        var iosflag=!!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);//是否是ios
        var qqflag=(app.indexOf('qq')>=0);//是否是qq
        var weixinflag=ua.match(/MicroMessenger/i) == 'micromessenger';
        return weixinflag||(qqflag&&iosflag);
    },
    init:function(){
        search.keyWord='';
        search.page=1;
        search.initValueForWaste();
        search.bindEvent();
        this.bindEvent();
    }
}
$(document).ready(function(){
    index.init();
})