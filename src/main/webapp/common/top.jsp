<%@page import="com.mlsc.waste.utils.Constant"%>
<%@page import="com.mlsc.waste.user.model.User"%>
<%@page import="com.mlsc.waste.utils.LoginStatusUtils"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
   String appPath=request.getContextPath();
   String ticketId = (String)request.getAttribute("ticketId");
   User user = LoginStatusUtils.getUserByTicketId(ticketId);
   String title = request.getParameter("title");
%>
<!DOCTYPE html>
<html lang="en">

<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <meta name="description" content="Bootstrap Admin App + jQuery">
   <meta name="keywords" content="app, responsive, jquery, bootstrap, dashboard, admin">
   <title></title>
   <meta name="keywords" content="危废处置,危废询价,危废管家服务,处置危废">
   <meta name="description" content="易废网,危废处置,危废询价,处置危废,提供一站式固废解决方案">
   <link rel="shortcut icon" href="<%=appPath %>/app/img/favicon.ico">
   <!-- =============== VENDOR STYLES ===============-->
   <!-- FONT AWESOME-->
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/fontawesome/css/font-awesome.min.css">
   <!-- SIMPLE LINE ICONS-->
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/simple-line-icons/css/simple-line-icons.css">
   <!-- ANIMATE.CSS-->
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/animate.css/animate.min.css">
   <!-- WHIRL (spinners)-->
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/whirl/dist/whirl.css">
   <!-- =============== PAGE VENDOR STYLES ===============-->
   <!-- SWEET ALERT-->
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.css?1">
   <!-- =============== BOOTSTRAP STYLES ===============-->
   <link rel="stylesheet" href="<%=appPath %>/app/css/bootstrap.css" id="bscss">
   <!-- =============== APP STYLES ===============-->
   <link rel="stylesheet" href="<%=appPath %>/app/css/app.css?3" id="maincss">

   <!-- jQuery -->
   <script type="text/javascript" charset="utf8" src="<%=appPath %>/resources/static/js/jquery1x.js"></script>
   <!-- BOOTSTRAP-->
   <script src="<%=appPath %>/thirdparty/bootstrap/dist/js/bootstrap.js"></script>
   <!-- STORAGE API-->
   <script src="<%=appPath %>/thirdparty/jQuery-Storage-API/jquery.storageapi.js"></script>
   <!-- JQUERY EASING-->
   <script src="<%=appPath %>/thirdparty/jquery.easing/js/jquery.easing.js"></script>
   <!-- =============== PAGE VENDOR SCRIPTS ===============-->
   <!-- =============== APP SCRIPTS ===============-->
   <script src="<%=appPath %>/app/js/app.js"></script>
   <script src="<%=appPath %>/app/js/constants.js"></script>
   <script src="<%=appPath %>/thirdparty/layui/layer-v3.0.1/layer/layer.js"></script>
   <script src="<%=appPath %>/app/js/page.js"></script>
   <script src="<%=appPath %>/app/js/notify.js"></script>
   <script src="<%=appPath %>/thirdparty/parsleyjs/dist/parsley.min.js"></script>
   <script src="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.min.js?1"></script>
   <%--
   <!-- DataTables CSS -->
   <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
   <!-- DataTables -->
   <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
   --%>
   <!-- DATATABLES-->
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/datatables-colvis/css/dataTables.colVis.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/datatables/media/css/dataTables.bootstrap.css">
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/dataTables.fontAwesome/index.css">
   <!-- DATATABLES-->
   <%--<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>--%>
   <script src="<%=appPath %>/thirdparty/datatables/media/js/jquery.dataTables.min.js"></script>
   <script src="<%=appPath %>/thirdparty/datatables-colvis/js/dataTables.colVis.js"></script>
   <script src="<%=appPath %>/thirdparty/datatables/media/js/dataTables.bootstrap.js"></script>
   <script src="<%=appPath %>/main/common/main.js"></script>
   <link rel="stylesheet" href="<%=appPath %>/css/main.css?3">
   <link rel="stylesheet" href="<%=appPath %>/css/main-extends.css">
   <!--[if lt IE 9]>
   <script src="<%=appPath %>/resources/static/js/html5shiv.js"></script>
   <script src="<%=appPath %>/resources/static/js/respond.js"></script>
   <script src="<%=appPath %>/resources/static/js/placeholder.js"></script>
   <![endif]-->
   <script type="text/javascript">
       var appPath='<%=appPath%>';
       var ticketId='<%=ticketId%>';
       $("title").text("<%=title %>-易废网");
       $(document).ready(function(){
           window.webSiteRootUrl = "<%=appPath %>";
           if ("<%=Constant.SYS_USER_TYPE%>" == "<%=user.getUserType()%>") {// 平台管理员不能进入业务平台
               $(".business-platform").remove();
           }
       });
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
                  <img src="<%=appPath %>/app/img/logo.png" alt="易废网" class="img-responsive">
               </div>
               <div class="brand-logo-collapsed">
                  <img src="<%=appPath %>/app/img/logo/logo_mini.png" alt="易废网" class="img-responsive">
               </div>
            </a>
         </div>
         <!-- END navbar header-->
         <!-- START Nav wrapper-->
         <div class="nav-wrapper">
            <!-- START Left navbar-->
            <ul class="nav navbar-nav">
               <li>
                  <!-- Button used to collapse the left sidebar. Only visible on tablet and desktops-->
                  <a href="#" data-toggle-state="aside-collapsed" class="hidden-xs">
                     <em class="fa fa-navicon"></em>
                  </a>
                  <!-- Button to show/hide the sidebar on mobile. Visible on mobile only.-->
                  <a href="#" data-toggle-state="aside-toggled" data-no-persist="true" class="visible-xs sidebar-toggle">
                     <em class="fa fa-navicon"></em>
                  </a>
               </li>
            </ul>
            <ul class="nav navbar-nav">
               <!-- 平台信息-->
               <li class="platformtitle">
               </li>
            </ul>
            <!-- END Left navbar-->
            <!-- START Right Navbar-->
            <ul class="nav navbar-nav navbar-right">
               <li style="left: left">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><%=user.getUserName() %>，您好 <span class="caret"></span></a>
                  <ul class="dropdown-menu">
                     <li class="business-platform"><a href="<%=appPath %>/wastecircle/init.htm?ticketId=<%=ticketId %>">业务平台</a></li>
                     <li class="quit-btn"><a href="javascript:" onclick="logout()">退出</a></li>
                  </ul>
               </li>
            </ul>
            <!-- END Right Navbar-->
         </div>
         <!-- END Nav wrapper-->
      </nav>
      <!-- END Top Navbar-->
   </header>
   <script>
      function logout() {
          localStorage.ticketId='';
          window.location='<%=appPath %>/userLogin/logout.htm?ticketId=<%=ticketId %>';
      }
   </script>
<div class="wrapper">