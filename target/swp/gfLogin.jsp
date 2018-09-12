<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String account = request.getParameter("phoneNum");
    String password = request.getParameter("password");
    String url = request.getParameter("url");
%>
<!DOCTYPE html>
<html lang="en">

<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
   <meta http-equiv="X-UA-Compatible" content="IE=9" />
   <title>登录-易废网</title>
    <style type="text/css">
        html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,center,dl,dt,dd,ol,ul,li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,header,hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;font-size:100%;font:inherit;vertical-align:baseline}article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{display:block}body{line-height:1}ol,ul{list-style:none}blockquote,q{quotes:none}blockquote:before,blockquote:after,q:before,q:after{content:'';content:none}table{border-collapse:collapse;border-spacing:0}body{background:#2B3134;overflow:hidden}canvas{bottom:0;left:0;margin:auto;position:absolute;right:0;top:0}
    </style>
</head>

<body>
<script src="<%=appPath %>/main/common/jquery.min.js"></script>
<script src="<%=appPath %>/main/common/loading.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    var phoneNo='<%=account%>';
    var password='<%=password%>';
    var url='<%=url%>';
    if(phoneNo&&phoneNo!='null'&&password&&password!='null'&&url&&url!='null'){
        var param={
            "phoneNum": phoneNo,
            "password": password,
            "resource":'gf'
        };
        $.ajax({
            url: "<%=appPath %>/userLogin/login.htm?etc=" + new Date().getTime(),
            data: param,
            async: true,
            type: "POST",
            dataType: "json",
            success: function (result) {
                var user = result.data.user;
                setCookie('uid',phoneNo);
                setCookie('sdktoken',user.imToken);
                setCookie('nickName',escape(user.userName));
                var cantonCode=result.data.user?result.data.user.cantonCode:'';
                if(cantonCode&&cantonCode.length>2){
                    localStorage.province=cantonCode.substring(0,2);
                }
                localStorage.entType=user.entType;
                localStorage.entId=user.enterpriseId;
                localStorage.ticketId=result.data.ticketId;
                localStorage.userId=user.userId;
                localStorage.userName=user.userName;
                localStorage.password=password;
                localStorage.phoneNo=phoneNo.toLocaleLowerCase();
                if(url){
                    if(url.indexOf('?')>-1){
                        window.location.replace('<%=appPath %>'+url+'&ticketId='+result.data.ticketId);
                    }else{
                        window.location.replace('<%=appPath %>'+url+'?ticketId='+result.data.ticketId);
                    }
                }
            }
        });
    }
});
function setCookie(name,value) {
    var days = 1;
    var exp = new Date();
    exp.setTime(exp.getTime() + days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
</script>
</body>