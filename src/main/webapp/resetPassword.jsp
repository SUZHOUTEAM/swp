<%@page import="com.mlsc.waste.utils.Constant"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
   <title>找回密码-易废网</title>
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/fontawesome/css/font-awesome.min.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/simple-line-icons/css/simple-line-icons.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/animate.css/animate.min.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/whirl/dist/whirl.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.css">
   <link rel="stylesheet" href="<%=appPath %>/app/css/bootstrap.css" id="bscss">
    <link rel="stylesheet" href="<%=appPath %>/app/css/app.css?1" id="maincss">
    <link rel="stylesheet" href="<%=appPath %>/css/main.css?1">
    <link rel="stylesheet" href="<%=appPath %>/main/pc/css/common.css?11" />
    <link rel="stylesheet" href="<%=appPath %>/css/user/loginCommon.css?1">
      <link rel="stylesheet" href="<%=appPath %>/css/user/userLogin.css?1">
   <script type="text/javascript" charset="utf8" src="<%=appPath %>/thirdparty/jquery/dist/jquery.js"></script>
   <script src="<%=appPath %>/thirdparty/bootstrap/dist/js/bootstrap.js"></script>
   <script src="<%=appPath %>/thirdparty/jQuery-Storage-API/jquery.storageapi.js"></script>
   <script src="<%=appPath %>/app/js/app.js"></script>
   <script src="<%=appPath %>/thirdparty/layui/layer-v3.0.1/layer/layer.js"></script>
   <script src="<%=appPath %>/app/js/page.js"></script>
    <!-- start Dplus --><script type="text/javascript">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(".");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for("undefined"!=typeof d?h=b[d]=[]:d="dplus",h.people=h.people||[],h.toString=function(a){var b="dplus";return"dplus"!==d&&(b+="."+d),a||(b+=" (stub)"),b},h.people.toString=function(){return h.toString(1)+".people (stub)"},e="disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user".split(" "),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.php?id=1262787667",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init("1262787667");</script><!-- end Dplus -->
</head>

<body class="layout-h">
<div class="header">
    <div class="container">
        <div class="logo">
            <a href="/swp/index.html" tag="0" title="易废网"><img src="main/pc/img/logo_short.png" alt="易废网 logo"></a>
        </div>
        <div class="headerAction" >
            <span class="no-login">易废网欢迎你！
					请<a href="login.jsp" class="login-btn">登录</a><b>/</b>
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
    <div class="content-wrapper container">
            <div class="login-tip">
                <img src="main/pc/img/loginTip.png?1"/>
            </div>
            <div class="block-center mt-xl wd-xl login-body">
                <div class="panel panel-dark panel-flat">
                    <div class="panel-body" id="registerBody">
                        <p class="text-center pv panel-title">重置密码</p>
                        <form class="mb-lg" id="resetForm" data-parsley-validate="true">
                            <div class="form-group has-feedback">
                                <label for="phoneNum" class="text-muted">手机号码</label>
                                <input type="text" id="phoneNum" name="phoneNum" placeholder="请输入手机号码" class="form-control" maxlength="11" data-trigger="change" oninput="poneNumInputCheck(this)" onblur="poneNumBlurCheck(this)"
                                       data-parsley-required="required" data-parsley-required-message="请输入手机号码"  
                                       data-parsley-phonenum="true" data-parsley-phonenum-message=""/>
                                <span class="fa fa-mobile-phone form-control-feedback text-muted fontSize"></span>
                            </div>
                            <div class="form-group has-feedback" id="divIdentify">
                                <label for="identifyCode" class="text-muted">短信验证码</label>
                                <div class="div-display-block">
                                    <input type="text" id="identifyCode" placeholder="请输入四位验证码" class="form-control identify-form-control" data-trigger="change" maxlength="6"
                                           data-parsley-required="required" data-parsley-required-message="请输入验证码" data-parsley-errors-container="#divIdentify"
                                           data-parsley-securitycode="true" data-parsley-securitycode-message="输入的验证码不正确"/>
                                    <span class="fa fa-envelope form-control-feedback identify-form-control-feedback text-muted"></span>
                                    <button type="button" id="btnResendSecurityCode" class="btn btn-md btn-default btn_padding" onclick="resendSecurityCodeClick(this)">获取验证码</button>
                                </div>
                            </div>
                            <div class="form-group">
                                <span id="infoMsg" class="text-success"></span>
                            </div>
                            <div class="form-group has-feedback">
                                <label for="password" class="text-muted">输入新密码</label>
                                <input type="password" id="password" name="password" placeholder="字母和数字，6到18位之间" class="form-control" onblur="passwordCheck(this,'password')" 
                                       maxlength="18" data-trigger="change" oncontextmenu="return false" 
                                       data-parsley-required="required" data-parsley-required-message="请输入密码" 
                                       data-parsley-password="true" data-parsley-password-message="" />
                                <span class="fa fa-lock form-control-feedback text-muted"></span>
                            </div>
                            <div class="form-group has-feedback">
                                <label for="rePassword" class="text-muted">确认密码</label>
                                <input type="password" id="rePassword" placeholder="请再一次输入密码" class="form-control" onblur="passwordCheck(this,'repassword')" 
                                       data-trigger="change" maxlength="18" oncontextmenu="return false"
                                       data-parsley-required="required" data-parsley-required-message="请输入确认密码" 
                                       data-parsley-repassword="true" data-parsley-repassword-message=""/> 
                                <span class="fa fa-lock form-control-feedback text-muted"></span>
                            </div>
                            <button type="button" id="btnResetPassword" class="btn btn-block btn-primary mt-lg" onclick="resetPasswordClick()">重置密码</button>
                        </form>
                    </div>
                    <div class="clearfix" style="padding-bottom: 10px">
                        <div class="pull-left">
                            <span></span><a href="<%=appPath %>/register.jsp" class="text-muted">还没有注册？</a>
                        </div>
                        <div class="pull-right">
                            <span></span><a href="<%=appPath %>/login.jsp" class="text-muted">想起密码了？</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <div class="footer">
        <p><a href="http://www.miibeian.gov.cn" target="_blank">苏ICP备16051104号</a><span> 江苏神彩科技股份有限公司，版权所有©2018</span></p>
    </div>
</div>
    
<script src="<%=appPath %>/thirdparty/parsleyjs/dist/parsley.min.js"></script>
<script src="<%=appPath %>/app/js/notify.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<script src="<%=appPath %>/app/js/RSA.js"></script>
<script src="<%=appPath %>/main/pc/js/index.js?15"></script>
<script type="text/javascript">
var phoneNumError = true;// 手机号码是否已经被注册验证
var securitycodeError = true;// 手机验证码正确性验证
var passwordError = true;// 确认密码正确性验证
var repasswordError = true;// 确认密码正确性验证
var passwordMsg0 = "两次输入的密码不一致，请重新输入。";
var second;

$(document).ready(function(){
    // 密码框不能复制，粘贴
    $("input:password").bind("copy cut paste",function(e){
        return false;
    });
    
    // 手机号码是否已经被注册验证
    window.Parsley.addValidator('phonenum', function(value,elem){
        return phoneNumError;
    }, 32);
    
    // 手机验证码正确性验证
    window.Parsley.addValidator('securitycode', function(value,elem){
        return securitycodeError;
    }, 32);
    
    // 密码正确性验证
    window.Parsley.addValidator('password', function(value,elem){
        return passwordError;
    }, 32);
    
    // 确认密码正确性验证
    window.Parsley.addValidator('repassword', function(value,elem){
        return repasswordError;
    }, 32);
    var height=$(window).height()-135;
    $('.middleDiv').height(height+'px');
    window.onresize=function () {
        var height=$(window).height()-135;
        $('.middleDiv').height(height+'px');
    };
    var flag=/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent);
    if(flag){
        $('.login-tip,.header,.footer').hide();
        $('.login-body').css('float','none');
        $('.middleDiv').css('margin-top',0).css('min-height','fit-content').css('height','auto');
        $('body').css('background-color','#fff');
    }
});

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
//手机号码离焦事件
function poneNumBlurCheck(t){
    // 手机号码格式正确性验证
    phoneNumError = true;
    if (!phoneNumReg3.test($(t).val())) {
        // 手机号码必须是11位数字
        phoneNumError = false;
        $(t).attr("data-parsley-phonenum-message",phoneNumMsg3);
    }
    $(t).parsley().validate();
}
//手机号码存在性验证
function checkPhoneNumExist(){
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "<%=appPath %>/userRegister/isPhoneNumExist.htm?etc=" + new Date().getTime(),
        data: {
            "phoneNum": $.trim($("#phoneNum").val())
        },
        success: function (result, textStatus, jqXHR) {
            if (!result.data.isRegistered) {
                // 手机号码未被注册
                phoneNumError = false;
                $("#phoneNum").attr("data-parsley-phoneNum-message","您的手机号码还未注册，你可以先去注册");
                $("#phoneNum").parsley().validate();
            } else {
                // 手机号码已被注册
                phoneNumError = true;
                $("#phoneNum").attr("data-parsley-phoneNum-message","");
            }
        }
    }));
}
//获取验证码事件
function resendSecurityCodeClick(t){
    $("#phoneNum").parsley().validate();
    if ($("#phoneNum").parsley().isValid()) {
        checkPhoneNumExist();
    }
    if ($("#phoneNum").parsley().isValid()) {
        $("#btnResendSecurityCode").attr("disabled","disabled");
        $.page.ajax($.page.getAjaxSettings({
            async: false,
            type: "POST",
            dataType: "json",
            url: "<%=appPath %>/userRegister/getSmsIdentifyCode.htm?etc=" + new Date().getTime(),
            data: {
                "phoneNum": $.trim($("#phoneNum").val()),
                "smsOrigin": returnCitySN["cip"],
                "smsType": "1"//重置密码
            },
            success: function (result, textStatus, jqXHR) {
                second = second_constants;// 120秒内获取验证码btn不可使用
                setTimeout('change()',1000);
            },
            complete: function (jqXHR, textStatus) {
                var result = jqXHR.responseJSON;
                if (result.status != 1) {
                    $("#btnResendSecurityCode").removeAttr("disabled");
                }
            }
        }));
    }
}
//password检查
function passwordCheck(t, passwordId) {
    if (passwordId == "repassword") {
        repasswordError = passwordValid(t, passwordId);
    } else if (passwordId == "password") {
        passwordError = passwordValid(t, passwordId);
    }
    
    $(t).parsley().validate();
}

// password检查
function passwordValid(t, passwordId) {
    var validResult = true;
    var parsleyid = "data-parsley-" + passwordId + "-message";
    // 密码正则检查
    if (!passwordReg1.test($(t).val())) {
        // 密码长度在6到18位之间。
        validResult = false;
        $(t).attr(parsleyid,passwordMsg1);
    } else if (passwordReg2.test($(t).val())) {
        // 密码不能全是数字。
        validResult = false;
        $(t).attr(parsleyid,passwordMsg2);
    } else if (passwordReg3.test($(t).val())) {
        // 密码不能全是字母。
        validResult = false;
        $(t).attr(parsleyid,passwordMsg3);
    } else if (!passwordReg4.test($(t).val())) {
        // 允许含有的特殊字符
        validResult = false;
        $(t).attr(parsleyid,passwordMsg4);
    } else {
        validResult = true; 
    }

   return validResult;
}

// 密码一致性检查
function repateValid() {
    var $password = $("#password");
    var $repassword = $("#rePassword");
    if ($password.val() != $repassword.val()) {
        repasswordError = false;
        $repassword.attr("data-parsley-repassword-message",passwordMsg0);
        passwordError = false;
        $password.attr("data-parsley-password-message",passwordMsg0);
    }
    
    $("#resetForm").parsley().validate();
}

//重置密码事件
function resetPasswordClick() {
    repateValid();// 密码一致性检查
    if ($("#resetForm").parsley().isValid()) {
        $("#btnResetPassword").attr("disabled","disabled").text("密码重置中...");
        $.page.ajax($.page.getAjaxSettings({
            type: "POST",
            dataType: "json",
            url: "<%=appPath %>/userRegister/checkIdentifyCode.htm?etc=" + new Date().getTime(),
            data: {
                "phoneNum": $.trim($("#phoneNum").val()),
                "identifyCode":$.trim($("#identifyCode").val())
            },
            success: function (result, textStatus, jqXHR) {
                if (result.status === 1) {
                    securitycodeError = true;
                    resetPassword();// 重置密码
                }
            },
            complete: function (jqXHR, textStatus) {
                var result = jqXHR.responseJSON;
                if (result.status != 1) {
                    $("#btnResetPassword").removeAttr("disabled").text("重置密码");
                }
            }
        }));
    }
}

//重置密码动作
function resetPassword() {
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "<%=appPath %>/userLogin/resetPassword.htm?etc=" + new Date().getTime(),
        data: {
            "userName": $.trim($("#userName").val()),
            "phoneNum": $.trim($("#phoneNum").val()),
            "password": FWRSAHelper.encrypt ($.trim($("#password").val()))
        },
        success: function (result, textStatus, jqXHR) {
            if (result.data.isSuccess) {
                var options={"status":"<%= Constant.STATUS_INFO%>"};
                $.notify("密码重置成功，即将跳转至登录页面...",options);
                setTimeout("toUrl()",2000);
            } else {
                var options={"status":"<%= Constant.STATUS_INFO%>"};
                $.notify("抱歉，重置密码失败，请稍后再试",options); 
            }
        },complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
            }
        }
    }));
}

//120秒内获取验证码btn不可使用
function change(){
    second--;
    if(second > -1) {
        $("#infoMsg").text(second + "秒后可重新获取验证码");
        timer = setTimeout('change()',1000);//调用自身实现
    } else {
       clearTimeout(timer);
       $("#btnResendSecurityCode").removeAttr("disabled");
       $("#infoMsg").text("");
    }
}

//页面信息提示框弹出
function toUrl(){
    location="<%=appPath%>/login.jsp";  
}
</script>
</body>