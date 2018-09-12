package com.mlsc.yifeiwang.entinquiry.model;

/**
 * Created by user on 2017/9/15.
 */
public class EntInquiryDetailParam {
    private String inquiryId; //询价主表Id
    private String releaseDetailId; //发布企业危废明细Id
    private String wasteName;//危废名称
    private double amount;
    private String priceId;
    private double price;//单价
    private double totalPrice;//总价
    private String updateUserId; // 更新人
    private String dispositionType; //处置方式
    private String unitCode;//单位
    private double amountT;
    private String remark;//备注


    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getReleaseDetailId() {
        return releaseDetailId;
    }

    public void setReleaseDetailId(String releaseDetailId) {
        this.releaseDetailId = releaseDetailId;
    }

    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDispositionType() {
        return dispositionType;
    }

    public void setDispositionType(String dispositionType) {
        this.dispositionType = dispositionType;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public double getAmountT() {
        return amountT;
    }

    public void setAmountT(double amountT) {
        this.amountT = amountT;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
