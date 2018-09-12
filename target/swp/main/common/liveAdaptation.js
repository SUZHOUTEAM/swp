/**
 * Created by wudang on 2017/7/19.
 */
var browser={
    versions:function(){
        var u = navigator.userAgent, app = navigator.appVersion;
        return {
            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
        };
    }(),
    language:(navigator.browserLanguage || navigator.language).toLowerCase()
}
var link=window.location.href;

if(!browser.versions.mobile&&!browser.versions.android&&!browser.versions.ios){
    if(link.indexOf('/main/m')>-1){
        location='/swp/main/pc/view/live.html';
    }
}else{
    if(link.indexOf('/main/m')==-1){
        location='/swp/main/mobile/view/live.html';
    }
}