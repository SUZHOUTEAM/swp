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
   <title>易废网</title>
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
   <link rel="stylesheet" href="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.css">
   <!-- =============== BOOTSTRAP STYLES ===============-->
   <link rel="stylesheet" href="<%=appPath %>/app/css/bootstrap.css" id="bscss">
   <!-- =============== APP STYLES ===============-->
   <link rel="stylesheet" href="<%=appPath %>/app/css/app.css" id="maincss">
   <link rel="stylesheet" href="<%=appPath %>/css/main.css">

   <!-- jQuery -->
   <script type="text/javascript" charset="utf8" src="<%=appPath %>/thirdparty/jquery/dist/jquery.js"></script>
   <!-- BOOTSTRAP-->
   <script src="<%=appPath %>/thirdparty/bootstrap/dist/js/bootstrap.js"></script>
   <!-- STORAGE API-->
   <script src="<%=appPath %>/thirdparty/jQuery-Storage-API/jquery.storageapi.js"></script>
   <!-- JQUERY EASING-->
   <script src="<%=appPath %>/thirdparty/jquery.easing/js/jquery.easing.js"></script>
   <!-- ANIMO-->
   <script src="<%=appPath %>/thirdparty/animo.js/animo.js"></script>
   <!-- =============== PAGE VENDOR SCRIPTS ===============-->
   <!-- =============== APP SCRIPTS ===============-->
   <script src="<%=appPath %>/app/js/app.js"></script>
</head>
<style>
</style>

<body class="layout-h">
    <div class="wrapper">
        <!-- Main section-->
        <section>
            <!-- Page content-->
            <div class="content-wrapper">
                <div class="text-center mb-xl">
                    <div class="text-lg mb-lg">500</div>
                    <p class="lead m0">Oh! Something went wrong :(</p>
                    <p>Don't worry, we're now checking this.</p>
                    <p>In the meantime, please try one of those links below or come back in a moment</p>
                </div>
                <div class="input-group">
                    <input type="text" placeholder="Try with a search" class="form-control">
                    <span class="input-group-btn">
                        <button type="button" class="btn btn-default">
                            <em class="fa fa-search"></em>
                        </button>
                    </span>
                </div>
                <ul class="list-inline text-center text-sm mb-xl">
                    <li><a href="dashboard.html" class="text-muted">Go to App</a>
                    </li>
                    <li class="text-muted">|</li>
                    <li><a href="login.html" class="text-muted">Login</a>
                    </li>
                    <li class="text-muted">|</li>
                    <li><a href="register.html" class="text-muted">Register</a>
                    </li>
                </ul>
            </div>
        </section>
        <footer class="text-center">
            <span>&copy; 2018 - 江苏神彩科技股份有限公司 </span>
        </footer>
    </div>
<script type="text/javascript">

</script>
</body>