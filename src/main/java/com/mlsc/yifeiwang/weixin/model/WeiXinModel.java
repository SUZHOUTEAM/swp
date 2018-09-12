package com.mlsc.yifeiwang.weixin.model;

/**
 * Created by user on 2018/8/10.
 */
public class WeiXinModel {
    private String returnCode;
    private String resultCode;
    private String prepayId;
    private String orderNo;
    private String paySign;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    @Override
    public String toString() {
        return "WeiXinModel{" +
                "returnCode=" + returnCode +
                ", resultCode=" + resultCode +
                ", prepayId=" + prepayId +
                ", orderNo=" + orderNo +
                "}";
    }
}
