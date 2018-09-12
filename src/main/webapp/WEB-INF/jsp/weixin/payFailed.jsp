<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/12
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java"
         errorPage="" %>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,viewport-fit=cover">
<script>
    document.getElementsByTagName('html')[0].style.fontSize=window.innerWidth/10+'px';
    window.onresize=function() {
        document.getElementsByTagName('html')[0].style.fontSize=window.innerWidth/10+'px';
    };
</script>
<div class="header">
    <a href="javascript:" onclick="myBit();" class="back">‹</a>
    <span class="middle-text">充值失败</span>
    <a href="javascript:" onclick="myBit();" class="over">完成</a>
</div>
<div class="pay-success-div-text1">充值失败！</div><div class="pay-success-div-text2"></div>
<div class="btn-div"><a href="javascript:" onclick="goOn()" class="recharge-btn">继续充值</a><a href="javascript:" onclick="buyMeal()" class="buy-meal">购买套餐</a></div>
<style>
    body{margin:0}.header,body{text-align:center}.header{height:1.2rem;line-height:1.2rem;border-bottom:1px solid #e7ebf6;display:table}.back{color:#38f;text-decoration:none;width:1.4rem}.back,.middle-text{display:table-cell;vertical-align:middle}.middle-text{font-size:.4rem;width:7.2rem;text-overflow:ellipsis;overflow:hidden;white-space:nowrap}.over{width:1.4rem;font-size:.38rem;text-decoration:none;color:#273e67;display:table-cell;vertical-align:middle}.btn-div{text-align:center}.btn-div a{display:inline-block;width:4rem;height:1rem;line-height:1rem;font-size:.4rem;text-decoration:none;border-radius:1rem;border:1px solid #2675ff}.btn-div a.recharge-btn{color:#2675ff;margin-right:.5rem}.btn-div a.buy-meal{background-color:#2675ff;color:#fff}.pay-success-div-text1{font-size:.5rem;margin:.8rem 0}.pay-success-div-text2{color:#6b7c9a;font-size:.38rem;margin-bottom:1rem}.pay-success-div-text2 b{font-size:.6rem;margin:0 .1rem;line-height:.4rem;position:relative;top:.06rem;color:#f96204;font-weight:400}</style>
<script type="text/javascript">
    function goOn() {
        window.location='/mobile/#/my/recharge';
    }
    function buyMeal() {
        window.location='/mobile/#/my/service';
    }
    function myBit() {
        window.location='/mobile/#/my/account';
    }
</script>
