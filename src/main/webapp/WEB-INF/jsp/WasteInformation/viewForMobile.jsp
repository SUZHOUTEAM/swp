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
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,viewport-fit=cover">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit" />
    <script type="text/javascript">
        document.getElementsByTagName('html')[0].style.fontSize=window.innerWidth/10+'px';
        window.onresize=function () {
            document.getElementsByTagName('html')[0].style.fontSize=window.innerWidth/10+'px';
        }
    </script>
    <link rel="stylesheet" href="<%=appPath %>/main/mobile/css/informationDetail.css?11" />
</head>

<body>
    <div class="header">
        <a href="javascript:" onclick="back();" class="back">‹</a>
        <span class="middle-text">${wasteInformation.title}</span>
    </div>
    <div class="con">
        <div class="title">
            ${wasteInformation.title}
        </div>
        <div class="sub-title">
            <span>发布时间：
                <fmt:formatDate value="${wasteInformation.createTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>
            </span>
            <%--<span>阅读次数：{{wasteInformation.clickAmount}}次</span>--%>
        </div>
        <c:if test="${wasteInformation.videoFileId!=null&&wasteInformation.videoFileId!=''}">
            <div class="video-div">
                <video controls preload="auto"  width="100%">
                    <source src="${wasteInformation.videoFileId}" type="video/mp4">
                </video>
            </div>
        </c:if>
        <div class="content" id="content">${wasteInformation.context}</div>
    </div>
    <div class="more">
        <i class="top-bar"></i>
        <div class="more-title">更多阅读：</div>
        <div class="infor-list"></div>
    </div>
    <script>
        var contentText=document.getElementById('content').innerHTML;
        contentText=contentText.replace(/font-size: \w+px;?/g,'');
        document.getElementById('content').innerHTML=contentText;
    </script>
    <script type="text/javascript" src="http://www.yifeiwang.com/swp/main/common/jquery.min.js"></script>
    <script src="http://www.yifeiwang.com/inforList.js?${nowTime}"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var str='';
            for(var i in inforList){
                var obj=inforList[i];
                str+='<a class="infor-item" href="'+obj.htmlSrc+'-mobile.html" onclick="updateClickAmount(\''+obj.id+'\')"><b>'+(i*1+1)+'</b><span class="infor-name">'+obj.title+'</span></a>';
            }
            $('.infor-list').html(str);
        });
        function updateClickAmount(id) {
            localStorage.firstViewInfo=true;
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
        function back() {
          localStorage.firstViewInfo=true;
          history.go(-1);
        }
    </script>
    <script src="http://www.yifeiwang.com/swp/main/mobile/js/weShare.js?7"></script>
</body>

</html>
