<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.mlsc.waste.utils.PropertyUtil"%>
<%
    String appPath=PropertyUtil.getValue("fileDomain")+"swp";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>${wasteInformation.htmlTitle}</title>
    <meta name="keywords" content="${wasteInformation.keyword}" />
    <meta name="description" content="${wasteInformation.desc}" />
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit" />
    <link rel=" shortcut icon" href="<%=appPath %>/app/img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link rel="stylesheet" href="<%=appPath %>/main/common/elementui/index.css" />
    <link rel="stylesheet" href="<%=appPath %>/main/pc/css/common.css?11" />
    <link rel="stylesheet" href="<%=appPath %>/main/pc/css/informationDetail.css?7" />
</head>

<body>
<header class="header">
    <div class="container">
        <div class="logo">
            <a href="<%=appPath%>/index.html" tag="0" title="易废网"> <img src="<%=appPath %>/main/pc/img/logo_short.png" alt="易废网 logo"> </a>
        </div>
        <div class="headerAction" >
            <span class="no-login">易废网欢迎你！
                请<a href="<%=appPath %>/login.jsp" class="login-btn">登录</a><b>/</b>
                <a href="<%=appPath %>/selectEntType.html" class="register-btn">注册</a>
            </span>
            <span class="is-login">你好，<b id="userName"></b><a href="javascript:" id="enter">进入易废圈></a></span>
        </div>
        <ul>
            <li>
                <a href="/swp/index.html" id="menu-home" title="首页">首页</a>
            </li>
            <li>
                <a href="/swp/main/pc/view/company.html" id="menu-company" title="危废经营单位">危废经营单位</a>
            </li>
            <li>
                <a href="/swp/main/pc/view/activityList.html" id="menu-activity" title="促销活动">促销活动</a>
            </li>
            <li class="active">
                <a href="/swp/main/pc/view/information.html" id="menu-information" title="危废小课堂">危废小课堂</a>
            </li>
            <li >
                <a href="javascript:" id="tmall">本地旗舰店</a>
            </li>
        </ul>
    </div>
</header>
<main>
        <div class="breadcrumb-div" style="width:800px;margin-bottom: 50px"><span class="text-muted">当前位置：</span> <div aria-label="Breadcrumb" role="navigation" class="el-breadcrumb"><span class="el-breadcrumb__item" onclick="history.go(-1);"><span role="link" class="el-breadcrumb__inner">危废小课堂</span><i class="el-breadcrumb__separator el-icon-arrow-right"></i></span> <span class="el-breadcrumb__item" aria-current="page"><span role="link" class="el-breadcrumb__inner">知识详情</span><i class="el-breadcrumb__separator el-icon-arrow-right"></i></span></div></div>
        <div class="title">
            ${wasteInformation.title}
        </div>
        <div class="sub-title">
                    <span>发布时间：
                        <fmt:formatDate value="${wasteInformation.createTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>
                    </span>
        </div>
         <div class="row">
            <%--<div class="left">--%>

                <c:if test="${wasteInformation.videoFileId!=null&&wasteInformation.videoFileId!=''}">
                    <div class="video-div">
                        <video controls  width="800px" height="450px">
                            <source src="${wasteInformation.videoFileId}" type="video/mp4">
                        </video>
                    </div>
                </c:if>

                <div class="content">${wasteInformation.context}</div>

            <%--</div>--%>
            <%--<div class="right">--%>
                <%--<i class="top-bar"></i>--%>
                <%--<div class="right-title">更多阅读</div>--%>
                <%--<div class="infor-list">--%>
                    <%--&lt;%&ndash;<a class="infor-item">标题标题标题标题标题标题标题</a>&ndash;%&gt;--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>

        <div style="padding-bottom: 20px;margin-top: 50px" id="service">
            <div class="service-title"><i class="service-icon"></i><b>管家服务</b><em>满足环保要求、避免处罚风险、规范管理危废</em></div>
            <div class="package-list">
                <div class="package-item">
                    <div class="package-header-first">尊享服务</div>
                    <div>危废仓库建设及改造咨询</div>
                    <div>危废仓库规范化管理</div>
                    <div>危废包装规范化管理</div>
                    <div>危废年度管理计划申报托管</div>
                    <div>危废月度申报托管</div>
                    <div>危废应急制度编制及演练</div>
                    <div>危废业务知识培训</div>
                    <div>危废管理制度编制</div>
                </div>
                <div class="package-item">
                    <div class="package-header" id="gong">
                        <div class="package-header-title"><i class="header-icon gong"></i>公爵套餐</div>
                        <div class="package-header-price"><b>2万</b>/年</div>
                        <a href="javascript:" class="package-header-btn" @click="doAction">购买</a>
                    </div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                </div>
                <div class="package-item active" id="bo">
                    <div class="package-header">
                        <div class="commend">荐</div>
                        <div class="package-header-title"><i class="header-icon bo"></i>伯爵套餐</div>
                        <div class="package-header-price"><b>1万</b>/年</div>
                        <a href="javascript:" class="package-header-btn focus" @click="doAction">购买</a>
                    </div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star gray"></i></div>
                    <div><i class="star gray"></i></div>
                    <div><i class="star light"></i></div>
                </div>
                <div class="package-item" id="hou">
                    <div class="package-header">
                        <div class="package-header-title"><i class="header-icon hou"></i>候爵套餐</div>
                        <div class="package-header-price"><b>6000</b>/年</div>
                        <a href="javascript:" class="package-header-btn" @click="doAction">购买</a>
                    </div>
                    <div><i class="star gray"></i></div>
                    <div><i class="star gray"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star light"></i></div>
                    <div><i class="star gray"></i></div>
                    <div><i class="star gray"></i></div>
                    <div><i class="star gray"></i></div>
                    <div><i class="star gray"></i></div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
        <div class="more">
            <i class="top-bar"></i>
            <div class="right-title">更多阅读</div>
            <div class="infor-list">
                <%--<a class="infor-item">标题标题标题标题标题标题标题</a>--%>
            </div>
        </div>
    </main>

    <%--<a href="javascript:" class="btn" id="btn">购买相关服务</a>--%>
<footer class="footer">
       <span>COPYRIGHT &nbsp; &copy;  &nbsp; 江苏神彩科技股份有限公司 &nbsp;  www.yifeiwang.com  &nbsp;
        <a href="http://www.miibeian.gov.cn" target="_blank">&nbsp;苏ICP备16051104号-2</a>&nbsp;&nbsp;ICP证: 苏B2-20170197</span>
</footer>
<script src="<%=appPath %>/main/common/jquery.min.js"></script>
<script src="http://www.yifeiwang.com/inforList.js?${nowTime}"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var str='';
        for(var i in inforList){
            var obj=inforList[i];
            str+='<a class="infor-item" href="'+obj.htmlSrc+'.html" onclick="updateClickAmount(\''+obj.id+'\')"><b>'+(i*1+1)+'</b><span class="infor-name">'+obj.title+'</span></a>';
        }
        $('.infor-list').html(str);
        var ticketId=localStorage['ticketId'];
        var ticketStr=ticketId?('?ticketId='+ticketId):'';
        $('#tmall').attr('href','<%=appPath%>/tmall.html');
        if(ticketId){
            $('.no-login').hide();
            $('.is-login').show();
            $('#userName').html(localStorage['userName']+'！');
            if(!localStorage.entType||localStorage.entType=='null'){
                $('#enter').attr('href','<%=appPath%>/enterprisemanagement/list.htm'+ticketStr);
            }else{
                if(localStorage.entType=='FACILITATOR'){
                    $('#enter').attr('href','<%=appPath%>/facilitator/cfList.htm'+ticketStr);
                }else{
                    $('#enter').attr('href','<%=appPath%>/wastecircle/init.htm'+ticketStr);
                }
                if(localStorage.entType=='PRODUCTION'){
                    $('#tmall').attr('href','<%=appPath%>/wastecircle/init.htm'+ticketStr);
                }
            }
        }else{
            $('.no-login').show();
            $('.is-login').hide();
        }

        $(".package-item:not(:first-child)").hover(function(){
            $(this).addClass("active").siblings().removeClass('active');
        });
        $(".package-list").mouseleave(function(){
            $('#bo').addClass("active").siblings().removeClass('active');
        });
        $('.package-header-btn').on('click',function () {
            var url='<%=appPath%>/login.jsp?redirectURL='+encodeURIComponent('/wastecircle/tmall.htm?buyMeal=1');
            var ticketId=localStorage['ticketId'];
            var ticketStr=ticketId?('?ticketId='+ticketId):'';
            if(ticketId&&localStorage.entType&&localStorage.entType=='PRODUCTION'){
                url='<%=appPath%>/wastecircle/tmall.htm'+ticketStr+'&buyMeal=1';
            }
            window.location=url;
        });
    });
    function updateClickAmount(id) {
        $.ajax({
            url: '/swp/wasteInformation/updateClickAmount',
            type: 'post',
            data: {
                id:id
            },
            success: function (result) {

            }
        })
    }
</script>
</body>

</html>
