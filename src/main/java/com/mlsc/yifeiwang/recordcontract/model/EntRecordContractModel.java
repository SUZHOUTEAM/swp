package com.mlsc.yifeiwang.recordcontract.model;

import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContract;

import java.util.List;

/**
 * Created by user on 2018/3/1.
 */
public class EntRecordContractModel extends EntRecordContract {
    private String entName;
    private String inquiryEntName; //询价企业名称
    private String releaseEntName; //产废企业
    private String facilitatorEntName;//服务商企业名称
    private String disposalEntName; //处置企业名称
    private String quotedTypeValue;
    private Double orderTotalPrice;
    private String totalPriceStr; // 总价
    private String busiStatusvalue; //状态描述
    private List<EntRecordContractDetailModel> contractDetailModelList;

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getInquiryEntName() {
        return inquiryEntName;
    }

    public void setInquiryEntName(String inquiryEntName) {
        this.inquiryEntName = inquiryEntName;
    }

    public String getReleaseEntName() {
        return releaseEntName;
    }

    public void setReleaseEntName(String releaseEntName) {
        this.releaseEntName = releaseEntName;
    }

    public String getQuotedTypeValue() {
        return quotedTypeValue;
    }

    public void setQuotedTypeValue(String quotedTypeValue) {
        this.quotedTypeValue = quotedTypeValue;
    }

    public Double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getTotalPriceStr() {
        return totalPriceStr;
    }

    public String getFacilitatorEntName() {
        return facilitatorEntName;
    }

    public void setFacilitatorEntName(String facilitatorEntName) {
        this.facilitatorEntName = facilitatorEntName;
    }

    public String getDisposalEntName() {
        return disposalEntName;
    }

    public void setDisposalEntName(String disposalEntName) {
        this.disposalEntName = disposalEntName;
    }

    public void setTotalPriceStr(String totalPriceStr) {
        this.totalPriceStr = totalPriceStr;
    }

    public String getBusiStatusvalue() {
        return busiStatusvalue;
    }

    public void setBusiStatusvalue(String busiStatusvalue) {
        this.busiStatusvalue = busiStatusvalue;
    }

    public List<EntRecordContractDetailModel> getContractDetailModelList() {
        return contractDetailModelList;
    }

    public void setContractDetailModelList(List<EntRecordContractDetailModel> contractDetailModelList) {
        this.contractDetailModelList = contractDetailModelList;
    }
}
