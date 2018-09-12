<%@page import="com.mlsc.waste.utils.Constant" %>
<%@page import="com.mlsc.waste.user.model.User" %>
<%@page import="com.mlsc.waste.utils.LoginStatusUtils" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
    User user = LoginStatusUtils.getUserByTicketId(ticketId);
    String title = request.getParameter("title");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="description" content="Bootstrap Admin App + jQuery">
    <meta name="keywords" content="app, responsive, jquery, bootstrap, dashboard, admin">
    <link rel="shortcut icon" href="<%=appPath %>/app/img/favicon.ico">
    <title></title>
    <meta name="keywords" content="危废处置,危废询价,危废管家服务,处置危废">
    <meta name="description" content="易废网,危废处置,危废询价,处置危废,提供一站式固废解决方案">
    <!-- =============== VENDOR STYLES ===============-->
    <!-- FONT AWESOME-->
    <link rel="stylesheet" href="<%=appPath %>/thirdparty/fontawesome/css/font-awesome.min.css">
    <!-- SIMPLE LINE ICONS-->
    <link rel="stylesheet" href="<%=appPath %>/thirdparty/simple-line-icons/css/simple-line-icons.css">
    <!-- ANIMATE.CSS-->
    <%--<link rel="stylesheet" href="<%=appPath %>/thirdparty/animate.css/animate.min.css">--%>
    <!-- WHIRL (spinners)-->
    <link rel="stylesheet" href="<%=appPath %>/thirdparty/whirl/dist/whirl.css">
    <!-- =============== PAGE VENDOR STYLES ===============-->
    <!-- SWEET ALERT-->
    <!-- =============== BOOTSTRAP STYLES ===============-->
    <link rel="stylesheet" href="<%=appPath %>/app/css/bootstrap.css" id="bscss">
    <!-- jQuery <script type="text/javascript" charset="utf8" src="<%=appPath %>/thirdparty/jquery/dist/jquery.js"></script>-->
    <script type="text/javascript" charset="utf8"
            src="<%=appPath %>/resources/static/js/jquery1x.js"></script>

    <!-- BOOTSTRAP-->
    <script src="<%=appPath %>/thirdparty/bootstrap/dist/js/bootstrap.js"></script>

    <!-- =============== APP STYLES ===============-->
    <link rel="stylesheet" href="<%=appPath %>/app/css/app.css?3" id="maincss">
    <!-- STORAGE API-->
    <script src="<%=appPath %>/thirdparty/jQuery-Storage-API/jquery.storageapi.js"></script>
    <!-- JQUERY EASING-->
    <script src="<%=appPath %>/thirdparty/jquery.easing/js/jquery.easing.js"></script>
    <!-- =============== PAGE VENDOR SCRIPTS ===============-->
    <!-- =============== APP SCRIPTS ===============-->
    <script src="<%=appPath %>/app/js/app.js"></script>
    <script src="<%=appPath %>/app/js/page.js"></script>
    <script src="<%=appPath %>/app/js/util.js?1"></script>
    <!--[if lt IE 9]>
    <script src="<%=appPath %>/resources/static/js/html5shiv.js"></script>
    <script src="<%=appPath %>/resources/static/js/respond.js"></script>
    <script defer="defer" src="<%=appPath %>/resources/static/js/placeholder.js"></script>
    <![endif]-->
    <!-- DATATABLES-->
  <%--  <link rel="stylesheet" href="<%=appPath %>/thirdparty/datatables-colvis/css/dataTables.colVis.css">
    <link rel="stylesheet"
          href="<%=appPath %>/thirdparty/datatables/media/css/dataTables.bootstrap.css">
    <link rel="stylesheet" href="<%=appPath %>/thirdparty/dataTables.fontAwesome/index.css">--%>
    <!-- DATATABLES-->
    <script src="<%=appPath %>/app/js/notify.js"></script>
    <script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
    <link rel="stylesheet" href="<%=appPath %>/css/main.css?2">
    <link rel="stylesheet" href="<%=appPath %>/css/main-extends.css?2">
    <link rel="stylesheet" href="<%=appPath %>/css/user/usertop.css?6">
    <link rel="stylesheet" href="<%=appPath %>/thirdparty/chatDialog/chatDialog.css?4">
    <%--<script src="<%=appPath %>/thirdparty/chatDialog/chatDialog.js"></script>--%>
    <link rel="stylesheet" href="<%=appPath %>/webdemo/im/css/uiKit.css">
    <link rel="stylesheet" href="<%=appPath %>/css/cloud-msg.css">
    <link rel="stylesheet" href="<%=appPath %>/main/pc/css/que-sug.css" />
    <link rel="stylesheet" href="<%=appPath%>/css/enterprise/qualification.css?4">
    <%--<script src="<%=appPath %>/webdemo/im/js/emoji.js"></script>--%>
    <script type="text/javascript">
      $("title").text("<%=title %>-易废网");
      var ticketId = '<%=ticketId %>';
      var appPath = '<%=appPath %>';
      $(document).ready(function () {
        window.webSiteRootUrl = "<%=appPath %>";
        unreadNoticeCount();
        $('#myUnReadNotice').click(function () {
          return false;
        });
        if ("<%=Constant.SYS_USER_TYPE%>" != "<%=user.getUserType()%>") {// 普通用户不能进入系统平台
          $(".system-platform").remove();
        }
          var heights=document.body.clientHeight;
          $('.m-panel').css('max-height',heights-100+'px');
      });

      //未读消息数量
      function unreadNoticeCount() {
        $.ajax({
          url: "<%=appPath %>/sysNotice/getUnreadNoticeCount.htm?ticketId=<%=ticketId %>",
          dataType: "json",
          success: function (result) {
            if (result.status === 1) {
              if (result.data === 0) {
                $("#unReadCount").hide();
              } else {
                $("#unReadCount").html(result.data);
              }
            }
          }
        });
      }
      //点击小铃铛下拉显示
      function myNotice() {
        if ($('.dropdown.dropdown-list').hasClass('open')) {
          return;
        }
        $("#chatLog").html('');
        var unreadCount = $("#unReadCount").html();
        if (unreadCount === "") {
          unreadCount = 0;
        }
        $("#myUnReadNotice").html(createSystemMsgData(unreadCount));
      }

      //创建系统消息
      function createSystemMsgData(unReadData) {
        var moreNoticeData = '<a href="" class="list-group-item more-notice" onclick="readMoreNotice()"><div class="pull-left"><img alt="" src="<%=appPath %>/app/img/system_notice.png"></div>'
            + '<small class="moreNotice" style="position: absolute;margin-top: 10px;margin-left: -95px;">系统消息</small>'
            +
            '<span id="readMoreNotice" class="label label-danger pull-right unreaddata" style="position: absolute;width: 38px;height: 16px;">'
            + unReadData + '</span>'
            + '<small class="read" style="position: absolute;margin-left: 65px;margin-top: 10px;" onclick="readMoreNotice()">查看</small>'
            + '<div style="margin-right: -205px;margin-top: 7px;"><img alt="" src="<%=appPath %>/app/img/arrow.png"></div></a>';
        return $(moreNoticeData);
      }

      //查看更多系统消息
      function readMoreNotice() {
        location = "<%=appPath %>/sysNotice/myNotice.htm?ticketId=<%=ticketId %>";
      }

      function chat(phoneNum) {
        var scene = 'p2p';
        var account = phoneNum;
        IMChat.window.yunXin.openChatBox(account, scene);
        $('.win').show();
        loadData(waste.ticketId, account);
      }

      function lickIndexPage() {
        location = "<%=appPath %>/index.html?ticketId=<%=ticketId %>";
      }
      function getEmojiHtml(text) {
        var re = /\[([^\]\[]*)\]/g;
        var matches = text.match(re) || [];
        for (var j = 0, len = matches.length; j < len; ++j) {
          if (emoji[matches[j]]) {
            text = text.replace(matches[j],
                '<img class="emoji" src="' + appPath + '/webdemo/im/images/emoji/'
                + emoji[matches[j]].file + '" />');
          }
        }
        return text;
      }

    </script>
    <!-- start Dplus --><script type="text/javascript">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(".");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for("undefined"!=typeof d?h=b[d]=[]:d="dplus",h.people=h.people||[],h.toString=function(a){var b="dplus";return"dplus"!==d&&(b+="."+d),a||(b+=" (stub)"),b},h.people.toString=function(){return h.toString(1)+".people (stub)"},e="disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user".split(" "),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.php?id=1262787667",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init("1262787667");</script><!-- end Dplus -->
</head>
<body>
    <!-- top navbar-->
    <header class="topnavbar-wrapper">
        <!-- START Top Navbar-->
        <nav role="navigation" class="navbar topnavbar">
            <!-- START navbar header-->
            <div class="navbar-header">
                <a href="#/" class="navbar-brand">
                    <div class="brand-logo">
                        <img src="<%=appPath %>/app/img/logo.png" alt="易废网" class="img-responsive"
                             onclick="lickIndexPage()">
                    </div>
                    <div class="brand-logo-collapsed">
                        <img src="<%=appPath %>/app/img/logo/logo_mini.png" alt="易废网"
                             class="img-responsive">
                    </div>
                </a>
            </div>
            <div class="nav-wrapper">
                <ul class="nav navbar-nav">
                    <li>
                        <!-- Button used to collapse the left sidebar. Only visible on tablet and desktops-->
                        <a href="#" data-toggle-state="aside-collapsed" class="hidden-xs">
                            <em class="fa fa-navicon"></em>
                        </a>
                        <!-- Button to show/hide the sidebar on mobile. Visible on mobile only.-->
                        <a href="#" data-toggle-state="aside-toggled" data-no-persist="true"
                           class="visible-xs sidebar-toggle">
                            <em class="fa fa-navicon"></em>
                        </a>
                    </li>
                </ul>
                <div class="home-menu">
                    <a href="<%=appPath%>/index.html" title="首页" id="menu-home">首页</a>
                    <a href="<%=appPath%>/main/pc/view/company.html" title="危废经营单位" id="menu-company">危废经营单位</a>
                    <a href="<%=appPath%>/main/pc/view/activityList.html" title="促销活动" id="menu-activity">促销活动</a>
                    <a href="<%=appPath%>/main/pc/view/information.html" title="危废小课堂" id="menu-information">危废小课堂</a>
                    <a href="<%=appPath%>/wastecircle/tmall.htm?ticketId=<%=ticketId%>" title="本地旗舰店" id="tmall">本地旗舰店</a>
                </div>
                <ul class="nav navbar-nav">
                    <!-- 平台信息-->
                    <li class="platformtitle">
                        <%-- <label class="lead"><%=title%></label> --%>
                    </li>
                </ul>
                <!-- END Left navbar-->
                <ul class="nav navbar-nav navbar-right">
                    <li class="userinfo-li" id="guide_li" style="display: none">
                        <a href="javascript:" onclick="$('.guideDiv').show();"
                           title="打开帮助"><i class="fa fa-question-circle" style="margin-right: 6px"></i>帮助</a>
                    </li>
                    <li class="dropdown dropdown-list" style="top: 2px;">
                        <a href="#" data-toggle="dropdown" aria-expanded="false"
                           onclick="myNotice()">
                            <em class="icon-bell"></em>
                            <span id="unReadCount" class="label label-danger">0</span>
                        </a>
                        <!-- START Dropdown menu-->
                        <ul class="dropdown-menu animated flipInX">
                            <li id="a">
                                <div id="myUnReadNotice" class="list-group"
                                     style="width: 280px;height: 58px;border-bottom:3px solid #d5dde6">
                                </div>
                            </li>
                        </ul>
                    </li>
                    <!-- 用户信息-->
                    <li class="userinfo-li">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false"><%=user.getUserName() %>,你好
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li class="system-platform"><a
                                    href="<%=appPath %>/calling/list.htm?ticketId=<%=ticketId %>">系统平台</a>
                            </li>
                            <li class="quit-btn"><a
                                    href="javascript:" onclick="logout();">退出</a>
                            </li>
                        </ul>
                    </li>
                    <%--<li class="userinfo-li">
                        <a href="<%=appPath %>/index.html?ticketId=<%=ticketId %>"
                           title="返回首页">返回首页</a>
                    </li>--%>
                </ul>
            </div>
        </nav>
        <!-- END Top Navbar-->
    </header>
<div class="wrapper">
    <div class="win" style="display: none;">
        <div class="wTop"><span class="userlogo"><img
                src="/swp/webdemo/im/images/default-icon.png"/></span><span
                class="userName"></span><a href="javascript:" class="closeDialog"
                                           title="关闭"><i class="fa fa-remove"></i></a><span
                class="btn_record">查看云记录</span></div>
        <div class="content">
            <iframe src="" name="IMChat" id="IMChat" scrolling="no" frameborder="0"></iframe>
            <div class="ent"><p><img src="/swp/webdemo/im/images/default-icon.png"></p>
                <p class="entName"></p>
                <p class="entCode"></p>
                <p class="entType"></p>
                <p class="entBtn"><a href="javascript:" class="view-ent-btn">查看资质</a></p>
            </div>
        </div>
    </div>
   <div class="qualification-dialog">
        <div class="qualification">
            <a href="javascript:" class="close-dialog" title="关闭" onclick="$('.qualification-dialog').hide()"><i class="fa fa-remove"></i></a>
            <div class="qualification-title"><span class="item-title-text">经营单位资质</span></div>
            <div class="qualification-imgs">
                <div class="qualification-img item-img1">
                    <div class="image">
                        <img src="" onclick="showLargeImg(this.src)"/>
                    </div>
                    <div class="qualification-text">营业执照</div>
                </div>
                <div  class="qualification-img qualification-img2">
                    <div class="image">
                        <img src="" onclick="showLargeImg(this.src)"/>
                    </div>
                    <div class="qualification-text">经营许可证</div>
                </div>
            </div>
        </div>
    </div>
    <div class="large-dialog">
        <a href="javascript:" class="close-dialog" title="关闭" onclick="$('.large-dialog').hide()"><i class="fa fa-remove"></i></a>
        <img src="" class="largeImg"/>
    </div>
    <div id="sessions">
        <a href="#" onclick="return false" class="chat_icon" style="left:-24px;">聊天</a>
        <p class="contacts_title"><i class="fa fa-circle"></i>最近联系人</p>
        <ul class="m-panel j-session"></ul>
    </div>
    <div class="m-help">
        <div class="m-help-text">
            <i class="m-help-logo"></i>咨询&建议
        </div>
        <div class="m-tip fadeIn">
            <ul>
                <li style="cursor: default">
                    <i class="contactTel"></i>
                    <div class="m-tip-title telTitle">咨询电话</div>
                    <div class="m-tip-content tel">0512-62717018</div>
                </li>
                <li title="在线咨询" onclick="onlineTalk();">
                    <i class="onlineTalk"></i>
                    <div class="m-tip-title">在线咨询</div>
                    <div class="m-tip-content">小易在线提供咨询服务</div>
                </li>
                <li title="聆听·建议" onclick="goSuggest();">
                    <i class="suggestion"></i>
                    <div class="m-tip-title">聆听·建议</div>
                    <div class="m-tip-content" style="line-height: 22px">易废网是不完善的，我们渴望你的建议</div>
                </li>
            </ul>
        </div>
    </div>
    <div class="guideDiv">
        <div class="guide">
            <a href="javascript:" title="关闭" class="guide_close" onclick="$('.guideDiv').hide();"></a>
            <div class="guide_text"><i class="guide_icon"></i><b>帮助向导</b>，欢迎来到易废网！</div>
            <div class="guide_img">
                <img src="<%=appPath%>/main/pc/img/help3.jpg">
            </div>
        </div>
    </div>
    <script type="text/javascript" src="<%=appPath%>/main/common/IMChat.js?10"></script>
    <style>
        #wasteInfoModal td {
            text-align: center;
            padding: 6px 16px;
        }

        .carousel .carousel-control.left, .carousel .carousel-control.right {
            font-size: 27px;
            color: rgb(51, 51, 51);
            width: 30px;
            height: 30px;
            background-color: rgb(182, 240, 245);
            line-height: 24px;
            padding-left: 3px;
            margin-top: 127px;
            border-radius: 15px;
        }

        .borderTd {
            border: solid 1px #DAE2EA;
            background-color: #F6F7FB;
        }

        #wasteInfoModal td > textarea:read-only {
            background-color: #F6F7FB;
            border: none;
            resize: none;
            text-align: center;
            color: #7A7C7E;
            border-radius: 0;
            min-height: 34px;
        }

        #wasteInfoModal .close {
            font-size: 21px;
        }

        #wasteInfoModal .carousel .item {
            padding: 0;
            text-align: center;
        }

        #wasteInfoModal .carousel .item img {
            display: inline;
        }
        .empty{
            text-align: center;
        }
    </style>
    <div id="wasteInfoModal" class="modal" tabindex="-1" role="dialog"
         aria-labelledby="wasteInfoModalLabel" style="margin-top:100px;">
        <div class="modal-dialog" role="document" style="width: 60%;height:80%;">
            <div class="modal-content" style="height: 100%;">
                <div class="modal-header">
                    <div class="headerDiv">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="wasteInfoModalLabel">危废详细信息</h4>
                    </div>
                </div>
                <div class="modal-body" style="overflow-y: auto">
                    <div class="row">
                        <div class="col-md-12">
                            <table id="wasteInfoTable" style="width: 100%;">
                                <tbody>
                                <tr>
                                    <td class="borderTd">危废名称：</td>
                                    <td field="name" class="borderTd"></td>
                                </tr>
                                <tr>
                                    <td style="width:25%;" class="borderTd">
                                        八位码：
                                    </td>
                                    <td field="code_eight" style="width:25%;" class="borderTd">
                                    </td>
                                    <%--<td style="width:25%;" class="borderTd">--%>
                                    <%--处置方式：--%>
                                    <%--</td>--%>
                                    <%--<td field="disposition_method" style="width:25%;"--%>
                                    <%--class="borderTd">--%>
                                    <%--</td>--%>
                                </tr>
                                <tr>
                                    <td class="borderTd">有害物质名称和含量：</td>
                                    <td field="description" class="borderTd"
                                        style="white-space: pre-wrap;">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" id="imgInfo">

                                    </td>
                                </tr>
                                <tr id="photosTr">
                                    <td colspan="4">
                                        <div id="myCarousel" class="carousel slide">
                                            <ol class="carousel-indicators">
                                            </ol>
                                            <div class="carousel-inner">
                                            </div>
                                            <a class="carousel-control left" href="#myCarousel"
                                               data-slide="prev">‹ </a>
                                            <a class="carousel-control right" href="#myCarousel"
                                               data-slide="next">› </a>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h5 class="modal-title" id="myModalLabel">
                        对方不在线，请留言
                    </h5>
                </div>
                <div class="modal-body">
                    <div>
                        <textarea rows="4" cols="40" style="width:100%;resize:none;border: none"
                                  id="msgcontext"
                                  value="我有废物要处理，看到信息请联系我的手机号"> 我有废物要处理，看到信息请联系我的手机号</textarea>
                        <input type="hidden" id="offline_ticketId"/>
                        <input type="hidden" id="offline_enterpriseId"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" type="submit"
                            onclick="saveOfflineMsg()">提交留言
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <div class="contacts">
        <p class="contacts_title"><i class="fa fa-circle"></i>企业联系人
            <a href="javascript:" class="close_btn">
                <i class="fa fa-remove"></i></a>
        </p>
        <div class="contacts_list">
            <%--<a href="javascript:;"><img src="../main/pc/img//qipao.jpg"/>小吴</a>--%>
            <%--<a href="javascript:;"><img src="../main/pc/img//qipao.jpg"/>小王</a>--%>
        </div>
    </div>
    <script>
        function logout() {
            localStorage.removeItem('ticketId');
            var ticketId='<%=ticketId %>';
            collectingUserBehavior(ticketId,'LOGOUT');
            window.location='<%=appPath %>/userLogin/logout.htm?ticketId='+ticketId;
        }
        function onlineTalk() {
            window.location=appPath+'/main/pc/view/online.html'+(ticketId ? ("?ticketId=" + ticketId) : "");
        }
        function goSuggest() {
            window.location=appPath+'/main/pc/view/suggest.html'+(ticketId ? ("?ticketId=" +ticketId) : "");
        }
        $(document).ready(function () {
            $('#menu-home').click(function () {
                collectingUserBehavior('<%=ticketId%>','TOPMEANU_INDEX');
            });
            $('#menu-company').click(function () {
                collectingUserBehavior('<%=ticketId%>','TOPMEANU_DISPOSITION');
            });
            $('#menu-activity').click(function () {
                collectingUserBehavior('<%=ticketId%>','TOPMEANU_ACTIVITY');
            });
            $('#menu-information').click(function () {
                collectingUserBehavior('<%=ticketId%>','TOPMEANU_WASTEINFO');
            });
            $('#menu-czDesc').click(function () {
                collectingUserBehavior('<%=ticketId%>','TOPMEANU_DISPOSAL');
            });
            $('#tmall').click(function () {
                collectingUserBehavior('<%=ticketId%>','TOPMEANU_FLAGSHIP');
            });
        })
    </script>