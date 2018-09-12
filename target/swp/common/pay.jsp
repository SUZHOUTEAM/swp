<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>付款</title>
</head>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.mlsc.common.util.AlipayConfig"%>
<%@ page import="com.mlsc.common.util.AlipayUtil"%>
<%@ page import="com.alipay.api.*"%>
<%@ page import="com.alipay.api.request.*"%>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.mlsc.yifeiwang.bindserve.service.IEntRechargeService" %>
<%@ page import="com.mlsc.yifeiwang.bindserve.model.EntRechargeParam" %>
<%@ page import="com.mlsc.yifeiwang.bindserve.entity.EntRecharge" %>
<%

	//获得初始化的AlipayClient
	AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
	//设置请求参数
	AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
	alipayRequest.setReturnUrl(AlipayConfig.return_url);
	alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

	//商户订单号，商户网站订单系统中唯一订单号，必填
	String out_trade_no =new String(request.getParameter("orderNo").getBytes("ISO-8859-1"),"UTF-8");
	ApplicationContext ctx =
			WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	if(ctx != null && ctx.getBean("entRechargeService") != null) {
		IEntRechargeService entRechargeService = (IEntRechargeService) ctx.getBean("entRechargeService");
		EntRechargeParam entBindServeParam = new EntRechargeParam();
		entBindServeParam.setOrderNo(out_trade_no);
		EntRecharge entRecharge = entRechargeService.selectEntRecharge(entBindServeParam);
		String str="{\"out_trade_no\":\""+ out_trade_no +"\","
				+ "\"total_amount\":\""+ entRecharge.getPrice() +"\","
				+ "\"subject\":\""+ entRecharge.getRemark() +"\","
				+ "\"body\":\""+entRecharge.getRemark()+"\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}";
		AlipayUtil.logger.info("调用支付宝接口，订单号为："+out_trade_no+"；请求参数为："+str);
		alipayRequest.setBizContent(str);
		String result = alipayClient.pageExecute(alipayRequest).getBody();
		AlipayUtil.logger.info("调用支付宝接口，订单号为："+out_trade_no+"；返回结果为："+result);
		//输出
		out.println(result);
	}
%>
<body>
</body>
</html>