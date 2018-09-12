package com.mlsc.yifeiwang.entinquiry.model;

import com.mlsc.yifeiwang.discusstag.model.DiscussTagParam;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 2017/9/26.
 */
public class EntInquiryModel {
    private String inquiryId; //询价主表Id
    private String releaseEnterName; //发布企业名称
    private String releaseEntId;//发布企业id
    private String facilitatorEntId; //服务商企业Id
    private String entDisEntId;
    private String releaseId; //发布id
    private String releaseUserId;//发布人的id
    private String releaseUserPhoneNum; //发布人电话
    private String inquiryPersonId; //询价人Id
    private String inquiryPhoneNo;//发布人电话
    private String quotedType; // 询价方式
    private String quotedTypeValue;
    private String totalAmount; // 总量
    private double totalPrice; // 总价
    private String totalPriceStr; // 总价
    private String inquiryRemark; // 询价备注
    private String busiStatus; // 报价状态
    private int inquiryWasteCount;//报价危废种类
    private Date inquiryDate; //报价时间
    private String inquiryEnterName; //报价企业名称
    private String inquiryEnterType;
    private String inquiryEntId;//询报价企业id
    private String activityId; //活动id
    private String activityName; //活动名称
    private String inquiryType;
    private List<EntInquiryDetailModel> inquiryDetail;//询价详细
    private DiscussTagParam tagInfo; //处置企业标记信息
    private boolean evaluated;
    private String createrId;
    private String createrName; //录入人
    private String responsibleUserId;
    private String responsibleUserName; //负责人
    private String releaseEntContacts; //发布企业的联系人电话
    private String priority;


    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getReleaseEnterName() {
        return releaseEnterName;
    }

    public void setReleaseEnterName(String releaseEnterName) {
        this.releaseEnterName = releaseEnterName;
    }

    public String getReleaseEntId() {
        return releaseEntId;
    }

    public void setReleaseEntId(String releaseEntId) {
        this.releaseEntId = releaseEntId;
    }

    public String getFacilitatorEntId() {
        return facilitatorEntId;
    }

    public void setFacilitatorEntId(String facilitatorEntId) {
        this.facilitatorEntId = facilitatorEntId;
    }

    public String getEntDisEntId() {
        return entDisEntId;
    }

    public void setEntDisEntId(String entDisEntId) {
        this.entDisEntId = entDisEntId;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getReleaseUserId() {
        return releaseUserId;
    }

    public void setReleaseUserId(String releaseUserId) {
        this.releaseUserId = releaseUserId;
    }

    public String getReleaseUserPhoneNum() {
        return releaseUserPhoneNum;
    }

    public void setReleaseUserPhoneNum(String releaseUserPhoneNum) {
        this.releaseUserPhoneNum = releaseUserPhoneNum;
    }

    public String getInquiryPersonId() {
        return inquiryPersonId;
    }

    public void setInquiryPersonId(String inquiryPersonId) {
        this.inquiryPersonId = inquiryPersonId;
    }

    public String getInquiryPhoneNo() {
        return inquiryPhoneNo;
    }

    public void setInquiryPhoneNo(String inquiryPhoneNo) {
        this.inquiryPhoneNo = inquiryPhoneNo;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String getQuotedType() {
        return quotedType;
    }

    public void setQuotedType(String quotedType) {
        this.quotedType = quotedType;
    }

    public String getQuotedTypeValue() {
        return quotedTypeValue;
    }

    public void setQuotedTypeValue(String quotedTypeValue) {
        this.quotedTypeValue = quotedTypeValue;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        if (totalPrice != 0.0) {
            this.setTotalPriceStr(String.format("%1$.3f", totalPrice));
        }

        this.totalPrice = totalPrice;
    }

    public String getInquiryRemark() {
        return inquiryRemark;
    }

    public void setInquiryRemark(String inquiryRemark) {
        this.inquiryRemark = inquiryRemark;
    }

    public String getBusiStatus() {
        return busiStatus;
    }

    public void setBusiStatus(String busiStatus) {
        this.busiStatus = busiStatus;
    }

    public int getInquiryWasteCount() {
        return inquiryWasteCount;
    }

    public void setInquiryWasteCount(int inquiryWasteCount) {
        this.inquiryWasteCount = inquiryWasteCount;
    }

    public Date getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(Date inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public List<EntInquiryDetailModel> getInquiryDetail() {
        return inquiryDetail;
    }

    public void setInquiryDetail(List<EntInquiryDetailModel> inquiryDetail) {
        this.inquiryDetail = inquiryDetail;
    }

    public String getTotalPriceStr() {
        return totalPriceStr;
    }

    public void setTotalPriceStr(String totalPriceStr) {
        this.totalPriceStr = totalPriceStr;
    }

    public String getInquiryEnterName() {
        return inquiryEnterName;
    }

    public void setInquiryEnterName(String inquiryEnterName) {
        this.inquiryEnterName = inquiryEnterName;
    }

    public String getInquiryEnterType() {
        return inquiryEnterType;
    }

    public void setInquiryEnterType(String inquiryEnterType) {
        this.inquiryEnterType = inquiryEnterType;
    }

    public String getInquiryEntId() {
        return inquiryEntId;
    }

    public void setInquiryEntId(String inquiryEntId) {
        this.inquiryEntId = inquiryEntId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public DiscussTagParam getTagInfo() {
        return tagInfo;
    }

    public void setTagInfo(DiscussTagParam tagInfo) {
        this.tagInfo = tagInfo;
    }


    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(String responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }

    public String getResponsibleUserName() {
        return responsibleUserName;
    }

    public void setResponsibleUserName(String responsibleUserName) {
        this.responsibleUserName = responsibleUserName;
    }

    public String getReleaseEntContacts() {
        return releaseEntContacts;
    }

    public void setReleaseEntContacts(String releaseEntContacts) {
        this.releaseEntContacts = releaseEntContacts;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
