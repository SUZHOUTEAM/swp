<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
   <meta http-equiv="X-UA-Compatible" content="IE=9" />
   <meta name="description" content="Bootstrap Admin App + jQuery">
   <meta name="keywords" content="app, responsive, jquery, bootstrap, dashboard, admin">
   <link rel="shortcut icon" href="<%=appPath %>/app/img/favicon.ico">
   <title>登录-易废网</title>
    <meta name="keywords" content="危废处置,危废询价,危废管家服务,处置危废">
    <meta name="description" content="易废网,危废处置,危废询价,处置危废,提供一站式固废解决方案">
   <!-- =============== VENDOR STYLES ===============-->
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/fontawesome/css/font-awesome.min.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/simple-line-icons/css/simple-line-icons.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/whirl/dist/whirl.css">
   <link rel="stylesheet" href="<%=appPath %>/app/css/bootstrap.css" id="bscss">
   <link rel="stylesheet" href="<%=appPath %>/app/css/app.css?1" id="maincss">
   <link rel="stylesheet" href="<%=appPath %>/css/main.css?1">
   <link rel="stylesheet" href="<%=appPath %>/main/pc/css/common.css?11" />
   <link rel="stylesheet" href="<%=appPath %>/css/user/userLogin.css?5">
   <link rel="stylesheet" href="<%=appPath %>/css/user/loginCommon.css?1">
    <script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
   <script type="text/javascript" charset="utf8" src="<%=appPath %>/resources/static/js/jquery1x.js"></script>
   <script src="<%=appPath %>/thirdparty/bootstrap/dist/js/bootstrap.js"></script>
   <script src="<%=appPath %>/thirdparty/jQuery-Storage-API/jquery.storageapi.js"></script>
   <script src="<%=appPath %>/app/js/app.js"></script>
   <script src="<%=appPath %>/app/js/page.js"></script>
   <script src="<%=appPath %>/app/js/util.js?1"></script>
   <!--[if lt IE 9]>
        <script src="<%=appPath %>/resources/static/js/html5shiv.js"></script>
        <script src="<%=appPath %>/resources/static/js/respond.js"></script>
       <script defer="defer" src="<%=appPath %>/resources/static/js/placeholder.js"></script>
       <script type="text/javascript">
        if (!Array.isArray) {
          Array.isArray = function(arg) {
            return Object.prototype.toString.call(arg) === '[object Array]';
          };
        }
       </script>
    <![endif]-->
    <!-- start Dplus --><script type="text/javascript">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(".");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for("undefined"!=typeof d?h=b[d]=[]:d="dplus",h.people=h.people||[],h.toString=function(a){var b="dplus";return"dplus"!==d&&(b+="."+d),a||(b+=" (stub)"),b},h.people.toString=function(){return h.toString(1)+".people (stub)"},e="disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user".split(" "),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.php?id=1262787667",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init("1262787667");</script><!-- end Dplus -->
</head>

<body class="layout-h">
<div class="app-dialog-bg">
    <div class="app-dialog">
        <div class="app-logo"></div>
        <div class="app-text">
            处置企业和处置服务商请<br/>下载APP或者在电脑上开展业务
        </div>
        <div class="app-btn">
            <a href="javascript:" class="app-download" onclick="downloadAPP();">下载APP</a>
            <a href="javascript:" class="app-cancel" onclick="$('.app-dialog-bg').hide()">我知道了</a>
        </div>
    </div>
</div>
<div class="header">
    <div class="container">
        <div class="logo">
            <a href="/swp/index.html" tag="0" title="易废网"><img src="main/pc/img/logo_short.png" alt="易废网 logo"></a>
        </div>
        <div class="headerAction" >
            <span class="no-login">易废网欢迎你！
					<a href="selectEntType.html" class="register-btn">注册</a>
				</span>
        </div>
        <ul>
            <li class="active">
                <a href="/swp/index.html" id="menu-home" title="首页">首页</a>
            </li>
            <li>
                <a href="/swp/main/pc/view/company.html" id="menu-company" title="危废经营单位">危废经营单位</a>
            </li>
            <li>
                <a href="/swp/main/pc/view/activityList.html" id="menu-activity" title="促销活动">促销活动</a>
            </li>
            <li>
                <a href="/swp/main/pc/view/information.html" id="menu-information" title="危废小课堂">危废小课堂</a>
            </li>
            <li>
                <a href="javascript:" id="menu-tmall" title="本地旗舰店">本地旗舰店</a>
            </li>
        </ul>
    </div>
</div>

<div class="wrapper middleDiv">
    <%--<section>--%>
        <div class="content-wrapper container">
            <div class="login-tip" style="width: 763px;margin-top: 45px;margin-left: 0">
                <video id="video" controls="" preload="auto" width="100%" height="100%" poster="http://www.yifeiwang.com/img/source/video-poster.png">
                    <source src="http://www.yifeiwang.com/advertising.mp4">
                </video>
            </div>
            <div class="block-center mt-xl wd-xl login-body" style="width: 320px">
                <div class="panel panel-dark panel-flat">
                    <div class="panel-body" id="loginBody">
                        <p class="text-center pv panel-title" id="title">用户登录</p>
                        <form class="mb-lg" id="loginForm" data-parsley-validate="true">
                            <div class="form-group has-feedback">
                                <input type="text" id="phoneNum" name="phoneNum" placeholder="手机号码" class="form-control" maxlength="11" data-trigger="change" oninput="poneNumInputCheck(this)" onblur="poneNumBlurCheck(this)"
                                       data-parsley-required="required" data-parsley-required-message="请输入手机号码"
                                       data-parsley-phonenum="true" data-parsley-phonenum-message=""/>
                                <span class="fa fa-mobile-phone form-control-feedback text-muted fontSize"></span>
                            </div>
                            <div class="form-group has-feedback">
                                <input type="password" id="password" name="password" placeholder="密码" class="form-control"
                                       maxlength="18" data-trigger="change" oncontextmenu="return false" onblur="ponePasswordCheck(this)"
                                       data-parsley-required="required" data-parsley-required-message="请输入密码"
                                       data-parsley-password="true" data-parsley-password-message=""/>
                                <span class="fa fa-lock form-control-feedback text-muted"></span>
                            </div>
                            <div class="clearfix">
                                <div class="pull-right">
                                    <a href="<%=appPath %>/resetPassword.jsp" class="text-muted btn-text">忘记密码了？</a>
                                </div>
                            </div>
                            <button type="button" id="btnLogin" class="btn btn-block btn-primary mt-lg" onclick="loginClick()">登录</button>
                        </form>
                    </div>
                    <div class="panel-body assistInfo" style="margin-top: 40px">
                        <p class="text-center pv btn-text">还没有易废网账号？</p>
                        <a href="javascript:" class="btn btn-block btn-default" id="register-btn">免费注册</a>
                    </div>
                    <div class="panel-body assistInfo">
                        <p class="text-center pv btn-text">危险废物产生单位使用过程中不产生任何费用<br/>如需帮助，请联系客服<br/><span class="fontWeight">0512-62717018</span></p>
                    </div>
                </div>
            </div>
        </div>
    <%--</section>--%>
    <div class="footer">
        <p><a href="http://www.miibeian.gov.cn" target="_blank">苏ICP备16051104号</a><span> 江苏神彩科技股份有限公司，版权所有©2018</span></p>
    </div>
</div>
    
<script src="<%=appPath %>/thirdparty/parsleyjs/dist/parsley.min.js"></script>
<script src="<%=appPath %>/app/js/notify.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/app/js/RSA.js"></script>
<script src="<%=appPath %>/main/pc/js/index.js?15"></script>

<script type="text/javascript">

var phoneNumError = true;// 手机号码是否已经被注册验证
var passwordError = true;// 确认密码正确性验证
var passwordMsg0 = "用户名或者密码不正确，请重新输入";
var flag=/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent);
$(document).ready(function(){
    clearCookie();
    var redirectURL=getParam('redirectURL');
    if(redirectURL&&redirectURL.indexOf('/wastecircle/tmall.htm')>-1){
        $('#title').text('请登录产废企业账号');
    }
    // 密码框不能复制，粘贴
    $("input:password").bind("copy cut paste",function(e){
        return false;
    });
    
    // 手机号码是否已经被注册验证
    window.Parsley.addValidator('phonenum', function(value,elem){
        return phoneNumError;
    }, 32);
    
    // 确认密码正确性验证
    window.Parsley.addValidator('password', function(value,elem){
        return passwordError;
    }, 32);
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ // enter 键
            var phoneNum = document.getElementById('phoneNum');
            var password = document.getElementById('password');
            if (password == document.activeElement||phoneNum==document.activeElement) {
                loginClick();
            }
        }
    };
    var height=$(window).height()-135;
    $('.middleDiv').height(height+'px');
    window.onresize=function () {
        var height=$(window).height()-135;
        $('.middleDiv').height(height+'px');
    };
    $('#register-btn').on('click',function () {
        if(flag){
            window.location='<%=appPath %>/register.jsp?1';
        }else{
            window.location='<%=appPath %>/selectEntType.html';
        }
    });
    if(flag){
        $('.login-tip,.header,.footer').hide();
        $('.login-body').css('float','none');
        $('.middleDiv').css('margin-top',0).css('min-height','fit-content').css('height','auto');
        $('body').css('background-color','#fff');
    }
});
function downloadAPP() {
    collectingUserBehavior('','APP_DOWNLOAD');
    window.location='http://www.yifeiwang.com/yfw.apk';
    $('.app-dialog-bg').hide();
}
//手机号码输入事件
function poneNumInputCheck(t){
    // 手机号码格式正确性验证
    phoneNumError = true;
    if (!phoneNumReg1.test($(t).val())) {
        // 手机号码只能以数字1开头
        phoneNumError = false;
        $(t).attr("data-parsley-phonenum-message",phoneNumMsg1);
    } else if (!phoneNumReg2.test($(t).val())) {
        // 手机号码只能是数字
        phoneNumError = false;
        $(t).attr("data-parsley-phonenum-message",phoneNumMsg2);
    } 
    
    $(t).parsley().validate();
}
function getParam(paraName) {
    var search = document.location.search,
            reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
    if (search && reg.test(search)) {
        return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
    }
    return null;
}
//手机号码离焦事件
function poneNumBlurCheck(t){
    // 手机号码格式正确性验证
    if (!phoneNumReg3.test($(t).val())) {
        // 手机号码必须是11位数字
        phoneNumError = false;
        $(t).attr("data-parsley-phonenum-message",phoneNumMsg3);
    }else{
        phoneNumError = true;
        $(t).attr("data-parsley-phonenum-message",'');
    }
    $(t).parsley().validate();
}
//密码必须输入
function ponePasswordCheck(t){
    $(t).parsley().validate();
}

//登录按钮事件
function loginClick() {
    $("#btnLogin").attr("disabled","disabled").text("登录中...");
    $("#loginForm").parsley().validate();
    var phoneNo = $.trim($("#phoneNum").val());
    var password = $.trim($("#password").val());
    if ($("#loginForm").parsley().isValid()) {
        var param={
            "phoneNum": phoneNo,
            "password": FWRSAHelper.encrypt (password)
        };
        if(isWeixin()&&localStorage.openId&&localStorage.bindStatus=='NOUSER'){
            localStorage.bindStatus='NOENT';
            param.openId=localStorage.openId;
        }
        $.page.ajax($.page.getAjaxSettings({
            async: true,
            type: "POST",
            dataType: "json",
            url: "<%=appPath %>/userLogin/login.htm?etc=" + new Date().getTime(),
            data: param,
            success: function (result, textStatus, jqXHR) {
            	var user = result.data.user;
                if(flag&&(user.entType=='DISPOSITION'||user.entType=='DIS_FACILITATOR')){
                    $('.app-dialog-bg').show();
                    $("#btnLogin").removeAttr("disabled").text("登录");
                    return;
                }
                var redirectURL=getParam('redirectURL');
                setCookie('uid',phoneNo.toLocaleLowerCase());
                setCookie('sdktoken',user.imToken);
                setCookie('nickName',escape(user.userName));
                if(phoneNo!='10000000000'){
                    if(flag){
                        collectingUserBehavior(result.data.ticketId,'MB_LOGIN');
                    }else{
                        collectingUserBehavior(result.data.ticketId,'LOGIN');
                    }
                }
                var cantonCode=result.data.user?result.data.user.cantonCode:'';
                if(cantonCode&&cantonCode.length>2){
                    localStorage.province=cantonCode.substring(0,2);
                }
                localStorage.entType=user.entType;
                localStorage.entId=user.enterpriseId;
                localStorage.ticketId=result.data.ticketId;
                localStorage.userId=user.userId;
                localStorage.userName=user.userName;
                localStorage.password=FWRSAHelper.encrypt (password);
                localStorage.phoneNo=phoneNo.toLocaleLowerCase();

                if(flag&&user.entType=='PRODUCTION'&&user.userStatus=='PASS'){
                    if(redirectURL){
                        window.location.replace('/mobile/#'+redirectURL);
                    }else{
                        window.location.replace('/mobile/#/publish');
                    }
                    return;
                }

                if(user.entType=='FACILITATOR'&&user.userStatus!='PASS'){
                    $.notify('管理员正在审核你的账号,审核通过会有短信通知',{status:'info',timeout:2000});
                    $("#btnLogin").removeAttr("disabled").text("登录");
                    return;
                }
                if(user.entType=='FACILITATOR'&&(!redirectURL||redirectURL.indexOf('feeActivityDetail')==-1)){
                    window.location.replace('<%=appPath %>/facilitator/cfList.htm?ticketId='+result.data.ticketId);
                    return;
                }
                if(redirectURL){
                    if(redirectURL.indexOf('?')>-1){
                        window.location.replace('<%=appPath %>'+getParam('redirectURL')+'&ticketId='+result.data.ticketId);
                    }else{
                        window.location.replace('<%=appPath %>'+getParam('redirectURL')+'?ticketId='+result.data.ticketId);
                    }
                }else{
                    toUrl(result.data.ticketId);
                }
            },
            complete: function (jqXHR, textStatus) {
                var result = jqXHR.responseJSON;
                if (result.status != 1) {
                    $("#btnLogin").removeAttr("disabled").text("登录");
                }
            }
        }));
    } else {
        $("#btnLogin").removeAttr("disabled").text("登录");
    }
}

//页面信息提示框弹出
function toUrl(ticketId){
    window.location.replace("<%=appPath%>/userLogin/loginSuccess.htm?ticketId=" + ticketId);
}
function isWeixin() {
    var ua = navigator.userAgent.toLowerCase();
    var isWeixin = ua.indexOf('micromessenger') != -1;
    if (isWeixin) {
        return true;
    }else{
        return false;
    }
}
</script>
</body>