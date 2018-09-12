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
   <title>注册-易废网</title>
    <meta name="keywords" content="危废处置,危废询价,危废管家服务,处置危废">
    <meta name="description" content="易废网,危废处置,危废询价,处置危废,提供一站式固废解决方案">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/fontawesome/css/font-awesome.min.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/simple-line-icons/css/simple-line-icons.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/animate.css/animate.min.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/whirl/dist/whirl.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.css">
   <link rel="stylesheet" href="<%=appPath %>/app/css/bootstrap.css" id="bscss">
    <link rel="stylesheet" href="<%=appPath %>/app/css/app.css?1" id="maincss">
    <link rel="stylesheet" href="<%=appPath %>/css/main.css?1">
    <link rel="stylesheet" href="<%=appPath %>/main/pc/css/common.css?11" />
    <link rel="stylesheet" href="<%=appPath %>/css/user/loginCommon.css">
   <link rel="stylesheet" href="<%=appPath %>/css/user/userRegister.css?2">
   <script type="text/javascript" charset="utf8" src="<%=appPath %>/resources/static/js/jquery1x.js"></script>
   <script src="<%=appPath %>/thirdparty/bootstrap/dist/js/bootstrap.js"></script>
   <script src="<%=appPath %>/thirdparty/jQuery-Storage-API/jquery.storageapi.js"></script>
   <script src="<%=appPath %>/app/js/app.js"></script>
   <script src="<%=appPath %>/thirdparty/layui/layer-v3.0.1/layer/layer.js"></script>
   <script src="<%=appPath %>/app/js/page.js"></script>
    <script src="<%=appPath %>/app/js/util.js"></script>
    <style type="text/css">
        .agreement-bg{
            background: rgba(0,0,0,.5);
            position: fixed;
            left: 0;
            right: 0;
            top:0;
            bottom: 0;
            z-index: 10000;
            display: none;
        }
        .agreement{
            width: 80%;
            max-width: 1000px;
            position: relative;
            margin: 30px auto 0;
            background-color: #e9f3ff;
            border-radius: 5px;
            overflow: hidden;
            font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
        }
        .agreement >p{
            font-size: 14px;
            height: 90%;
            overflow-y: auto;
        }
        .agreement >p::-webkit-scrollbar{
            background-color: transparent;
            width: 8px;
        }
        .agreement >p::-webkit-scrollbar-thumb{
            background-color: #c4d1e5;
            border-radius: 4px;
            margin-right: 5px;
        }
        .agreement .agreement-title{
            padding: 0 15px 0 60px;
            height:72px;
            line-height: 72px;
            color: #333333;
            font-size: 20px;
            background-color: #fff;
        }
        .closeDialog{
            position: absolute;
            width: 24px;
            height: 24px;
            right: 12px;
            cursor: pointer;
            top: 21px;
            background: url(<%=appPath%>/main/pc/img/sprite.png) -96px -0px no-repeat;
        }
        .agreement .btn-div{
            text-align: center;
            padding: 20px 0;
            background-color: #fff;
        }
        .agreement a.btn{
            height: 36px;
            line-height: 36px;
            padding: 0;
            width: 116px;
            background-color: #1171d1;
            color: #fff;
            display: inline-block;
        }
        .agreement p{
            color: #282828;
            font-size: 14px;
            text-align: left;
            padding: 20px 60px 40px;
            line-height: 28px;
        }
    </style>
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
<!--[if lt IE 9]>
        <style type="text/css">
            .sign .signtext{
                margin-left: -12.5px;
            }
        </style>
    <![endif]-->
<body class="layout-h">
<div class="header">
    <div class="container">
        <div class="logo">
            <a href="/swp/index.html" tag="0" title="易废网"><img src="main/pc/img/logo_short.png" alt="易废网 logo"></a>
        </div>
        <div class="headerAction" >
            <span class="no-login">易废网欢迎你！
					请<a href="login.jsp" class="login-btn">登录</a>
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
            <div class="login-tip" style="width: 763px;margin-top: 45px;margin-left: 0">
                <video id="video" controls="" preload="auto" width="100%" height="100%" poster="http://www.yifeiwang.com/img/source/video-poster.png">
                    <source src="http://www.yifeiwang.com/advertising.mp4">
                </video>
            </div>
            <div class="block-center mt-xl wd-xl login-body">
                <div class="panel panel-dark panel-flat">
                    <div class="panel-body" id="registerBody">
                        <p class="text-center pv panel-title">用户注册</p>
                        <div class="sign step1">
                            <p class="step"><span class="cir circle1">1</span><span class="signtext signtext1">手机验证</span></p>
                            <p class="progress progress1"></p>
                            <p class="step"><span class="cir circle2">2</span><span class="signtext signtext2">填写信息</span></p>
                            <p class="progress progress2"></p>
                            <p class="step"><span class="cir circle3">3</span><span class="signtext signtext3">完成注册</span></p>
                        </div>
                        <form class="mb-lg" id="registerForm" data-parsley-validate="true">
                            <div class="form-group has-feedback">
                                <label for="phoneNum" class="text-muted btn-text">手机号码</label><br>
                                <input type="text" id="phoneNum" name="phoneNum" placeholder="请输入手机号码" class="form-control" maxlength="11" data-trigger="change" oninput="poneNumInputCheck(this)" onblur="poneNumBlurCheck(this)"
                                       data-parsley-required="required" data-parsley-required-message="请输入手机号码"  
                                       data-parsley-phonenum="true" data-parsley-phonenum-message=""/>
                                <span class="fa fa-mobile-phone form-control-feedback text-muted fontSize"></span>
                            </div>
                            <div class="form-group has-feedback" id="divIdentify">
                                <label for="identifyCode" class="text-muted btn-text">短信验证码</label>
                                <div class="div-display-block">
                                    <input type="text" id="identifyCode" placeholder="请输入四位验证码" class="form-control identify-form-control" data-trigger="change" maxlength="6"
                                           data-parsley-required="required" data-parsley-required-message="请输入验证码" data-parsley-errors-container="#divIdentify"
                                           data-parsley-securitycode="true" data-parsley-securitycode-message="输入的验证码不正确"/>
                                    <span class="fa fa-envelope form-control-feedback identify-form-control-feedback text-muted"></span>
                                    <button type="button" id="resendSecurityCodebtn" class="btn btn-md btn-default btn_padding" style="height: 35px;vertical-align: top;width: 111px;padding: 0;" onclick="resendSecurityCode(this)">获取验证码</button>
                                </div>
                            </div>
                            <div class="form-group">
                                <span id="infoMsg" class="text-success"></span>
                            </div>
                            <div class="clearfix">
                                <div class="checkbox c-checkbox pull-left mt0">
                                    <label>
                                        <input type="checkbox" id="agreeProtocol" name="agreeProtocol" value="1" onClick="agreeProtocolEvent()">
                                        <span class="fa fa-check"></span><a href="javascript:" onclick="$('.agreement-bg').show();">阅读并同意《易废网用户协议》</a>
                                    </label>
                                </div>
                            </div>
                            <button type="button" id="btnRegister" class="btn btn-block mt-lg" onclick="registerClick()">下一步，完善个人信息</button>
                        </form>
                    </div>
                    <div class="panel-body hidden" id="infoBody">
                        <p class="text-center pv">个人信息</p>
                        <div class="sign step2">
                            <p class="step"><span class="cir circle1">1</span><span class="signtext signtext1">手机验证</span></p>
                            <p class="progress progress1"></p>
                            <p class="step"><span class="cir circle2">2</span><span class="signtext signtext2">填写信息</span></p>
                            <p class="progress progress2"></p>
                            <p class="step"><span class="cir circle3">3</span><span class="signtext signtext3">完成注册</span></p>
                        </div>
                        <form class="mb-lg" id="infoForm" data-parsley-validate="true">
                            <div class="form-group has-feedback">
                                <label for="userName" class="text-muted btn-text">姓名（只允许汉字，字母，数字以及[_.]字符）</label>
                                <input type="text" id="userName" name="userName" placeholder="请输入姓名" class="form-control" data-trigger="change" maxlength="10"
                                       data-parsley-required="required" data-parsley-required-message="请输入姓名" oninput="userNameBlur(this)"
                                       data-parsley-pattern="^[0-9a-zA-Z\u3E00-\u9FA5_.]+$" data-parsley-pattern-message="姓名不能出现特殊字符" /> 
                                <span class="fa icon-user form-control-feedback text-muted"></span>
                            </div>
                            <div class="form-group has-feedback">
                                <label for="password" class="text-muted btn-text">输入密码</label>
                                <input type="password" id="password" name="password" placeholder="字母和数字，6到18位之间" class="form-control" onblur="passwordCheck(this,'password')" 
                                       maxlength="18" data-trigger="change" oncontextmenu="return false" 
                                       data-parsley-required="required" data-parsley-required-message="请输入密码" 
                                       data-parsley-password="true" data-parsley-password-message="" />
                                <span class="fa fa-lock form-control-feedback text-muted"></span>
                            </div>
                            <div class="form-group has-feedback">
                                <label for="rePassword" class="text-muted btn-text">确认密码</label>
                                <input type="password" id="rePassword" placeholder="请再一次输入密码" class="form-control" onblur="passwordCheck(this,'repassword')" 
                                       data-trigger="change" maxlength="18" oncontextmenu="return false"
                                       data-parsley-required="required" data-parsley-required-message="请输入确认密码" 
                                       data-parsley-repassword="true" data-parsley-repassword-message=""/> 
                                <span class="fa fa-lock form-control-feedback text-muted"></span>
                            </div>
                            <button type="button" id="btnRegisterFinish" class="btn btn-block mt-lg" onclick="registerFinishClick()">完成注册</button>
                        </form>
                    </div>
                    <div class="panel-body hidden" id="registerSuccessBody">
                        <p class="text-center pv panel-title">用户注册</p>
                        <div class="sign step3">
                            <p class="step"><span class="cir circle1">1</span><span class="signtext signtext1">手机验证</span></p>
                            <p class="progress progress1"></p>
                            <p class="step"><span class="cir circle2">2</span><span class="signtext signtext2">填写信息</span></p>
                            <p class="progress progress2"></p>
                            <p class="step"><span class="cir circle3">3</span><span class="signtext signtext3">完成注册</span></p>
                        </div>
                        <div class="text-center pv" style="margin:30px 0">
                               <div class="successlogo">
                                   <img src="<%=appPath %>/main/pc/img/register-success.png" alt="Image" class="block-center img-rounded">
                               </div>
                            <div class="lead">注册成功!</div>
                        </div>
                        <p class="text-center pv">
                            <a id="toWasteBusiness" href="<%=appPath %>/myenterprise/myEnterprise.htm?" class="btn btn-block"><span>开始业务</span></a>
                        </p>
                    </div>
                    <div class="panel-body assistInfo" style="padding-bottom: 20px;padding-top: 0">
                        <p class="text-center pv btn-text">已有易废网账号？</p>
                        <a href="<%=appPath %>/login.jsp" class="btn btn-block btn-default">登录</a>
                    </div>
                    <div class="assistInfo">
                        <p class="text-center pv btn-text">危险废物产生单位使用过程中不产生任何费用 <br/>如需帮助，请联系客服  <span class="fontWeight">0512-62717018</span></p>
                    </div>
                </div>
            </div>
        </div>
    <div class="footer">
        <p><a href="http://www.miibeian.gov.cn" target="_blank">苏ICP备16051104号</a><span> 江苏神彩科技股份有限公司，版权所有©2018</span></p>
    </div>
</div>
<div class="agreement-bg">
    <div class="agreement">
        <div class="agreement-title">《易废网》服务协议 <a class="closeDialog" onclick="$('.agreement-bg').hide();"></a></div>
        <p>《易废网服务协议》（以下简称“本协议”）系本网站提供服务的前提和基础，本协议具有合同效力。注册会员时，请您认真阅读本协议，审阅并接受或不接受本协议。<br>
        若您已经注册为本网站会员，即表示您已充分阅读、理解并同意自己与本网站订立本协议，且您自愿受本协议的条款约束。本网站有权随时变更本协议并在本网站上予以公告。经修订的条款一经在本网站的公布后，立即自动生效。<br>
        本协议内容包括协议正文及所有易废网已经发布的各类规则。所有规则为本协议不可分割的一部分，与本协议正文具有同等法律效力。一旦您继续使用本网站，则表示您已接受并自愿遵守经修订后的条款。<br>
        <b>第一条会员资格</b> <br>
        一、只有符合下列条件之一的自然人或法人才能申请成为本网站会员，可以使用本网站的服务。<br>
        （一）年满十八岁，具有民事权利能力和民事行为能力的自然人；　　<br>
        （二）根据中国法律、法规、行政规章成立并合法存在的机关、企事业单位、社团组织和其他组织。<br>
        二、会员需要提供明确的联系地址和联系电话，并提供真实姓名或名称，并且提供注册所需的相关资质证书，并保证资质的真实性。并应在网站提示下，提供包括但不限于银行账户等其他相关信息。如会员提供虚假信息或提供信息不齐全的，将有可能面临自身权益受损的情况。<br>
        <b>第二条会员的权利和义务</b><br>
        一、会员有权根据本协议及本网站发布的相关规则，利用本网站发布任务信息、查询相关信息、提供相关信息，参加本网站的有关活动及有权享受本网站提供的其他服务。　　<br>
        二、会员须自行负责自己的会员账号和密码，且须对在会员账号密码下发生的所有活动承担责任。<br>
        因会员的过错导致的任何损失由会员自行承担，该过错包括但不限于：不按照交易提示操作，未及时进行交易操作，遗忘或泄漏密码，密码被他人破解，您使用的计算机被他人侵入。<br>
        三、会员应当向本网站提供真实准确的注册信息，包括但不限于真实姓名、身份证号、联系电话、地址、邮政编码，银行帐号等。保证本网站可以通过上述联系方式与自己进行联系。同时，会员也应当在相关资料实际变更时及时更新有关注册资料。<br>
        四、会员不得以任何形式擅自转让或授权他人使用自己在本网站的会员帐号。　　<br>
        五、会员有义务确保在本网站上发布的任务信息真实、准确，无误导性。　　<br>
        六、会员在本网站网上发布平台，不得发布国家法律、法规、行政规章规定禁止的信息、侵犯他人知识产权或其他合法权益的信息、违背社会公共利益或公共道德的信息。　　<br>
        七、会员在本网站交易中应当遵守诚实信用原则，不得以干预或操纵发布任务等不正当竞争方式扰乱网上交易秩序，不得从事与网上交易无关的不当行为，不得在交易平台上发布任何违法信息。　　<br>
        八、会员承诺自己在使用本网站实施的所有行为遵守法律、法规、行政规章和本网站的相关规定以及各种社会公共利益或公共道德。如有违反导致任何法律后果的发生，会员将以自己的名义独立承担相应的法律责任。　<br>
        九、会员在本网站网上交易过程中如与其他会员因交易产生纠纷，可以请求本网站从中予以协调处理。会员如发现其他会员有违法或违反本协议的行为，可以向本网站举报。<br>
        十、会员应当自行承担因交易产生或取得的相关费用，并依法纳税。　　<br>
        十一、未经本网站书面允许，会员不得将本网站的任何资料以及在交易平台上所展示的任何信息作商业性利用（包括但不限于以复制、修改、翻译等形式制作衍生作品、分发或公开展示）。　　<br>
        十二、会员有权在同意本网站相关规则的前提下享受交易保障服务。　　<br>
        十三、会员同意接收来自本网站的信息，包括但不限于活动信息、交易信息、促销信息等。<br>
        <b>第三条易废网的权利和义务</b><br>
        一、本网站仅为会员提供一个信息平台，是发布方发布需求和解决方提供解决方案的一个交易市场。网站可视情况提供协助服务，会员自主判断决策，评估风险，本网站不提供任何形式上或实质上的担保和保证义务。<br>
        二、本网站有义务在现有技术水平的基础上努力确保整个网上交流平台的正常运行，尽力避免服务中断或将中断时间限制在最短时间内，保证会员网上交流活动的顺利进行。<br>
        三、本网站有义务对会员在注册使用本网站信息平台中所遇到的与交易或注册有关的问题及反映的情况及时作出回复。　　<br>
        四、本网站有权对会员的注册资料进行审查，对存在任何问题或怀疑的注册资料，本网站有权发出通知询问会员并要求会员做出解释、改正。<br>
        五、会员因在本网站网上交易与其他会员产生纠纷的，会员将纠纷告知本网站，或本网站知悉纠纷情况的，经审核后，本网站有权通过电子邮件及电话联系向纠纷双方了解纠纷情况，并将所了解的情况通过电子邮件互相通知对方；如双方同意，本网站可提供纠纷调处服务。<br>
        六、因网上信息平台的特殊性，本网站没有义务对所有会员的交易行为以及与交易有关的其他事项进行事先审查，但如发生以下情形，本网站有权无需征得会员的同意限制会员的活动、向会员核实有关资料、发出警告通知、暂时中止、无限期中止及拒绝向该会员提供服务：<br>
        （一）会员违反本协议；　　<br>
        （二）存在会员或其他第三方通知本网站，认为某个会员或具体交易事项存在违法或不当行为，并提供相关证据，而本网站无法联系到该会员核证或验证该会员向本网站提供的任何资料；<br>
        （三）存在会员或其他第三方通知本网站，认为某个会员或具体交易事项存在违法或不当行为，并提供相关证据。本网站以普通非专业交易者的知识水平标准对相关内容进行判别，可以明显认为这些内容或行为可能对本网站会员或本网站造成财务损失或法律责任。<br>
        七、根据国家法律、法规、行政规章规定、本协议的内容和本网站所掌握的事实依据，可以认定该会员存在违法或违反本协议行为以及在本网站交易平台上的其他不当行为，本网站有权无需征得会员的同意在本网站交易平台及所在网站上以网络发布形式公布该会员的违法行为，并有权随时作出删除相关信息、终止服务提供等处理。<br>
        八、本网站有权在不通知会员的前提下，删除或采取其他限制性措施处理下列信息：包括但不限于以规避费用为目的；以炒作信用为目的；存在欺诈等恶意或虚假内容；与网上交易无关或不是以交易为目的；存在恶意竞价或其他试图扰乱正常交易秩序因素；该信息违反公共利益或可能严重损害本网站和其他会员合法利益的。<br>
        <b>第四条服务的中断和终止</b>　<br>
        一、本网站可自行全权决定以任何理由(包括但不限于本网站认为会员已违反本协议或相关规则等)终止对会员的服务，并有权在一定期限内保存会员在本网站的全部资料（包括但不限于会员信息、产品信息、交易信息等）。同时本网站可自行全权决定，在发出通知或不发出通知的情况下，随时停止提供全部或部分服务。服务终止后，本网站没有义务为会员保留原账户中或与之相关的任何信息，或转发任何未曾阅读或发送的信息给会员或第三方。<br>
        二、若会员向本网站提出注销本网站注册会员身份，需经本网站审核同意，由本网站注销该注册会员，会员即解除与本网站的协议关系，但本网站仍保留下列权利：<br>
        （一）会员注销后，本网站有权在法律、法规、行政规章规定的时间内保留该会员的资料,包括但不限于以前的会员资料、交易记录等。　　<br>
        （二）会员注销后，若会员注销前在本网站交易平台上存在违法行为或违反本协议的行为，本网站仍可行使本协议所规定的权利。<br>
        三、会员存在下列情况，本网站可以通过注销会员的方式终止服务：　　<br>
        （一）在会员违反本协议及相关规则规定时，本网站有权终止向该会员提供服务。本网站将在中断服务时通知会员。但该会员在被本网站终止提供服务后，再一次直接或间接或以他人名义注册为本网站会员的，本网站有权再次单方面终止为该会员提供服务；<br>
        （二）本网站发现会员注册资料中主要内容是虚假的，本网站有权随时终止为该会员提供服务；<br>
        （三）本协议终止或更新时，会员未确认新的协议的。　　<br>
        （四）其它本网站认为需终止服务的情况。<br>
        <b>第五条本网站的责任范围</b>　<br>
        当会员接受该协议时，会员应当明确了解并同意∶<br>
        一、本网站不能随时预见到任何技术上的问题或其他困难。该等困难可能会导致数据损失或其他服务中断。本网站是在现有技术基础上提供的服务。本网站不保证以下事项∶<br>
        （一）本网站将符合所有会员的要求。<br>
        （二）本网站不受干扰、能够及时提供、安全可靠或免于出错。　　<br>
        （三）本服务使用权的取得结果是正确或可靠的。<br>
        二、是否经由本网站下载或取得任何资料，由会员自行考虑、衡量并且自负风险，因下载任何资料而导致会员电脑系统的任何损坏或资料流失，会员应负完全责任。希望会员在使用本网站时，小心谨慎并运用常识。<br>
        三、会员经由本网站取得的建议和资讯，无论其形式或表现，绝不构成本协议未明示规定的任何保证。　　<br>
        四、基于以下原因而造成的利润、商誉、使用、资料损失或其它无形损失，本网站不承担任何直接、间接、附带、特别、衍生性或惩罚性赔偿（即使本网站已被告知钱款赔偿的可能性）：<br>
        （一）本网站的使用或无法使用。<br>
        （二）会员的传输或资料遭到未获授权的存取或变更。　　<br>
        （三）本网站中任何第三方之声明或行为。　　<br>
        （四）本网站其它相关事宜。　　<br>
        五、本网站只是为会员提供一个服务交易的平台，对于会员所发布的任务的合法性、真实性及其品质，以及会员履行交易的能力等，本网站不负任何担保责任，需要会员自行判断决策。<br>
        <b>第六条知识产权</b><br>
        一、本网站及本网站所使用的任何相关软件、程序、内容，包括但不限于作品、图片、档案、资料、网站构架、网站版面的安排、网页设计、经由本网站或广告商向会员呈现的广告或资讯，均由本网站或其它权利人依法享有相应的知识产权，包括但不限于著作权、商标权、专利权或其它专属权利等，受到相关法律的保护。未经本网站或权利人明示授权，会员保证不修改、出租、出借、出售、散布本网站及本网站所使用的上述任何资料和资源，或根据上述资料和资源制作成任何种类产品。　　<br>
        二、会员不得经由非本网站所提供的界面使用本网站。<br>
        三、会员向本网站提供的任何信息，数据，方法等用于交易的内容，本网站对于其真实性，合法性以及是否侵犯任何第三方的权利，不承担任何意义上的保证责任。会员应自行甄别或可以通过本网站与信息提供方进行核实。<br>
        <b>第七条隐私权</b><br>
        一、信息使用：<br>
        （一）本网站不会向任何人出售或出借会员的个人或法人信息，除非事先得到会员许可。<br>
        （二）本网站亦不允许任何第三方以任何手段收集、编辑、出售或者传播会员的个人或法人信息。任何会员如从事上述活动，一经发现，本网站有权立即终止与该会员的服务协议，查封其账号。<br>
        二、信息披露：会员的个人或法人信息将在下述情况下部分或全部被披露：<br>
        （一）经会员同意，向第三方披露；<br>
        （二）若会员是合法的知识产权使用权人并提起投诉，应被投诉人要求，向被投诉人披露，以便双方处理可能的权利纠纷；<br>
        （三）根据法律的有关规定，或者行政、司法机关的要求，向第三方或者行政、司法机关披露；<br>
        （四）若会员出现违反中国有关法律或者网站规定的情况，需要向第三方披露；<br>
        （五）为提供你所要求的产品和服务，而必须和第三方分享会员的个人或法人信息<br>
        （六）其它本网站根据法律或者网站规定认为合适的披露；<br>
        三、信息安全：<br>
        （一）在使用本网站服务进行网上交易时，请会员妥善保护自己的个人或法人信息，仅在必要的情形下向他人提供；<br>
        （二）如果会员发现自己的个人或法人信息泄密，请会员立即联络本网站客服，以便我们采取相应措施。<br>
        <b>第八条不可抗力</b><br>
        因不可抗力或者其他意外事件，使得本协议的履行不可能、不必要或者无意义的，双方均不承担责任。本合同所称之不可抗力意指不能预见、不能避免并不能克服的客观情况，包括但不限于战争、台风、水灾、火灾、雷击或地震、罢工、暴动、法定疾病、黑客攻击、网络病毒、电信部门技术管制、政府行为或任何其它自然或人为造成的灾难等客观情况。<br>
        <b>第九条保密</b><br>
        会员保证在对讨论、签订、执行本协议中所获悉的属于对方的且无法自公开渠道获得的文件及资料（包括但不限于商业秘密、公司计划、运营活动、财务信息、技术信息、经营信息及其他商业秘密）予以保密。未经该资料和文件的原提供方同意，另一方不得向第三方泄露该商业秘密的全部或者部分内容。但法律、法规、行政规章另有规定或者双方另有约定的除外。<br>
        <b>第十条争议解决方式</b>　　<br>
        一、本协议及其规则的有效性、履行和与本协议及其规则效力有关的所有事宜，将受中华人民共和国法律管辖，任何争议仅适用中华人民共和国法律。　　<br>
        二、本网站有权受理并调处您与其他会员因交易服务产生的争议，因本网站非司法机关，您完全理解并承认，本网站对证据的鉴别能力及对纠纷的处理能力有限，受理交易争议完全是基于您之委托，不保证争议处理结果符合您的期望，本网站有权决定是否参与争议的调处。<br>
        三、凡因本协议及其规则发生的所有争议，争议双方可协商解决，若协商不成的，争议双方同意提交种裁委员会按其仲裁规则进行仲裁。<br>
        第十一条易废网对本服务协议包括基于本服务协议制定的各项规则拥有最终解释权。</p>
        <div class="btn-div">
            <a class="btn" onclick="$('.agreement-bg').hide();">我知道了</a>
        </div>
    </div>
</div>
<script src="<%=appPath %>/thirdparty/parsleyjs/dist/parsley.min.js"></script>
<script src="<%=appPath %>/app/js/notify.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/app/js/RSA.js"></script>
<script src="<%=appPath %>/main/pc/js/index.js?15"></script>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>

<script type="text/javascript">

var phoneNumError = true;// 手机号码是否已经被注册验证
var securitycodeError = true;// 手机验证码正确性验证
var passwordError = true;// 密码正确性验证
var repasswordError = true;// 确认密码正确性验证
var passwordMsg0 = "两次输入的密码不一致，请重新输入。";
var second;

$(document).ready(function(){
    resizeHeight();
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
    
    // 注册页面初始设置,注册btn
    $("#btnRegister").attr("disabled","disabled");
    var height=$(window).height()-135;
    $('.middleDiv').height(height+'px');
    window.onresize=function () {
        var height=$(window).height()-135;
        $('.middleDiv').height(height+'px');
        resizeHeight();
    };
    var flag=/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent);
    if(flag){
        $('.login-tip,.header,.footer').hide();
        $('.login-body').css('float','none');
        $('.middleDiv').css('margin-top',0).css('min-height','fit-content').css('height','auto');
        $('body').css('background-color','#fff');
    }
});
function resizeHeight() {
    var ele=$('.agreement p');
    var devHieght=210;
    if(ele.length==0){
        var timer=setInterval(function () {
            var ele=$('.agreement p');
            if(ele.length==1){
                clearInterval(timer);
                var heights=$(window).height();
                ele.css('max-height',heights-devHieght+'px');
            }
        },500)
    }else{
        var heights=$(window).height();
        ele.css('max-height',heights-devHieght+'px');
    }
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
//手机号码离焦事件
function poneNumBlurCheck(t){
    // 手机号码格式正确性验证
    if (!phoneNumReg3.test($(t).val())) {
        // 手机号码必须是11位数字
        phoneNumError = false;
        $(t).attr("data-parsley-phonenum-message",phoneNumMsg3);
    }
    $(t).parsley().validate();
}
//手机号码重复性验证
function checkPhoneNumExist(){
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "<%=appPath %>/userRegister/isPhoneNumExist.htm?etc=" + new Date().getTime(),
        data: {
            "phoneNum": $.trim($("#phoneNum").val())
        },
        success: function (obj, textStatus, jqXHR) {
            if (!obj.data.isRegistered) {
                phoneNumError = true;
                // 手机号码未被注册
                agreeProtocolEvent();
            } else {
                // 手机号码已被注册
                phoneNumError = false;
                $("#phoneNum").attr("data-parsley-phoneNum-message","该手机号码已经被注册了，请用该手机号码进行登录");
                $("#phoneNum").parsley().validate();
            }
        }
    }));
}
//获取验证码事件
function resendSecurityCode(t){
    $("#phoneNum").parsley().validate();
    if ($("#phoneNum").parsley().isValid()) {
        checkPhoneNumExist();
    }
    if ($("#phoneNum").parsley().isValid()) {
        $("#resendSecurityCodebtn").attr("disabled","disabled");
        $.page.ajax($.page.getAjaxSettings({
            async: false,
            type: "POST",
            dataType: "json",
            url: "<%=appPath %>/userRegister/getSmsIdentifyCode.htm?etc=" + new Date().getTime(),
            data: {
                "phoneNum": $.trim($("#phoneNum").val()),
                "smsOrigin": returnCitySN["cip"],
                "smsType": "0"//注册用户
            },
            success: function (result, textStatus, jqXHR) {
                second = second_constants;// 120秒内获取验证码btn不可使用
                setTimeout('change()',1000);
            },
            complete: function (jqXHR, textStatus) {
                var result = jqXHR.responseJSON;
                if (result.status != 1) {
                    $("#resendSecurityCodebtn").removeAttr("disabled");
                }
            }
        }));
    }
}
//阅读并同意《易废网用户协议》绑定事件
function agreeProtocolEvent(){
    var $btnRegister =  $("#btnRegister");
    if ($("#agreeProtocol").is(':checked') && $("#phoneNum").parsley().isValid()) {
        $btnRegister.removeAttr("disabled");
    }else{
        $btnRegister.attr("disabled","disabled");
    }
}

// 120秒内获取验证码btn不可使用
function change(){
    second--;
    if(second > -1) {
        $("#infoMsg").text(second + "秒后可重新获取验证码");
        timer = setTimeout('change()',1000);//调用自身实现
    } else {
       clearTimeout(timer);
       $("#resendSecurityCodebtn").removeAttr("disabled");
       $("#infoMsg").text("");
    }
}

//注册按钮事件
function registerClick() {
    // 短信验证码验证
    $("#registerForm").parsley().validate();
    if ($("#registerForm").parsley().isValid()) {
        $("#btnRegister").attr("disabled","disabled").text("处理中...");
        $.page.ajax($.page.getAjaxSettings({
            async: false,
            type: "POST",
            dataType: "json",
            url: "<%=appPath %>/userRegister/checkIdentifyCode.htm?etc=" + new Date().getTime(),
            data: {
                "phoneNum":$("#phoneNum").val(),
                "identifyCode":$("#identifyCode").val()
            },
            success: function (result, textStatus, jqXHR) {
                if (result.status == "1") {
                    securitycodeError = true;
                    // 跳转至完善用户信息界面
                    $("#registerBody").addClass("hidden");
                    $("#infoBody").removeClass("hidden");
                }else{
                  var options = {"status": "danger"};
                  $.notify(result.infoList[0], options);
                }
            },
            complete: function (jqXHR, textStatus) {
                var result = jqXHR.responseJSON;
                if (result.status != 1) {
                    $("#btnRegister").removeAttr("disabled").text("下一步，完善个人信息");
                }
            }
        }));
    }
}

//姓名oninput事件
function userNameBlur(t){
    $(t).parsley().validate();
}

// password检查
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
    
    $("#infoForm").parsley().validate();
}

//完成注册按钮事件
function registerFinishClick() {
    // 密码一致性检查
    repateValid();
    if ($("#infoForm").parsley().isValid()){
        $("#btnRegisterFinish").attr("disabled","disabled").text("信息完善中...");
        var param={
            "userName": $.trim($("#userName").val()),
            "phoneNum": $.trim($("#phoneNum").val()),
            "password": FWRSAHelper.encrypt ($.trim($("#password").val()))
        };
        if(isWeixin()&&localStorage.openId&&localStorage.bindStatus=='NOUSER'){
            localStorage.bindStatus='NOENT';
            param.openId=localStorage.openId;
        }
        $.page.ajax($.page.getAjaxSettings({
            async: false,
            type: "POST",
            dataType: "json",
            url: "<%=appPath %>/userRegister/userRegisterSave.htm?etc=" + new Date().getTime(),
            data: param,
            success: function (result, textStatus, jqXHR) {
                if(result.status === 1){
                  // 跳转至注册成功界面
                  localStorage.password = FWRSAHelper.encrypt($.trim($("#password").val()));
                  localStorage.phoneNo = $.trim($("#phoneNum").val());
                  var flag=/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent);
                  if(flag){
                      collectingUserBehavior(result.data.ticketId,'MB_REGISTER_SUCCESS');
                  }else{
                      collectingUserBehavior(result.data.ticketId,'REGISTER_SUCCESS');
                  }
                  $("#infoBody").addClass("hidden");
                  $(".assistInfo").addClass("hidden");
                  $("#registerSuccessBody").removeClass("hidden");
                  setCookie('uid',$("#phoneNum").val());
                  setCookie('nickName',escape($('#userName').val()));
                  setCookie('sdktoken',result.data.imToken);
                  var $toWasteBusiness = $("#toWasteBusiness");
                  var href = $toWasteBusiness.attr("href");
                  href = href + "ticketId=" + result.data.ticketId;
                  $toWasteBusiness.attr("href",href);
                }else{
                  var options={"status":"<%= Constant.STATUS_DANGER%>"};
                  $.notify(result.infoList[0],options);
                }
            },
            complete: function (jqXHR, textStatus) {
                var result = jqXHR.responseJSON;
                if (result.status != 1) {
                    $("#btnRegisterFinish").removeAttr("disabled").text("完成注册");
                }
            }
        }));
    }
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