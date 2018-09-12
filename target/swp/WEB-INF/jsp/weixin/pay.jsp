<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/12
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>微信支付</title>
</head>
<body onload="pay();">
<script type="text/javascript">
    function pay(){
        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        }else{
            onBridgeReady();
        }
    }
    function onBridgeReady(){
        var appid="${appid}";
        var timestamp="${timeStamp}";
        var nocncestr="${nonceStr}";
        var package="${packageValue}";
        var paysign="${paySign}";
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId" : "${appid}",     //公众号名称，由商户传入
                "timeStamp": "${timeStamp}",         //时间戳，自1970年以来的秒数
                "nonceStr" : "${nonceStr}", //随机串
                "package" : "${packageValue}",
                "signType" : "MD5",         //微信签名方式:
                "paySign" : "${paySign}"    //微信签名
            },function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok"){
                    location.href="http://www.yifeiwang.com/swp/common/paySuccess_weixin.jsp?orderNo=${orderNo}";
                }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                    window.location = "http://www.yifeiwang.com/mobile/#/my/recharge";
                }else{
                    location.href="http://www.yifeiwang.com/swp/weixin/payFailed";
                }
            });
    }
</script>
</body>
</html>