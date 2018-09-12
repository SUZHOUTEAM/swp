package com.mlsc.yifeiwang.wasterealase.model;

import com.mlsc.yifeiwang.discusstag.model.DiscussTagParam;

import java.util.List;

/**
 * Created by user on 2017/9/14.
 */
public class EntReleaseModel {
    private String releaseId; //发布id
    private String releaseEntId;//发布企业id
    private String facilitatorEntId;//服务商企业Id
    private String entName;//企业名称
    private String entAddress;//企业地址
    private String releaseUserName; //发布人姓名
    private String totalWasteCount;//发布危废种类总数
    private String totalWasteAmountDesc;//发布危废总量
    private String disposalWasteCount;//可处置危废总数
    private String disposalWasteAmount;//可处置危废总量
    private String releaseRemark;//发布备注
    private String fileId; //企业上传图片
    private List<EntReleaseDetailModel> releaseWasteDetails; //发布危废明细
    private String releaseDate;//发布日期
    private String distance;//距离
    private String releaseOwner;//发布者
    private int inquiryEntCount;//询价企业个数
    private boolean favorited; //是否收藏
    private String activityName; //来源处某个活动
    private String activityId; //活动id
    private String releaseStatus; //状态
    private String releaseStatusValue;  //发布或申请加入活动状态
    private DiscussTagParam tagInfo;
    private String cantonCode;//发布企业所属行政区
    private String cantonCodeName;//发布企业所属行政区 **省 ** 市
    private int releaseCount;
    private String inquiryStatus; //询价状态 1:已询价;0 未询价
    private String releaseEntContact;
    private String releaseEntContactTel;
    private String entBindStatus;//企业是否绑定该发布信息 1:已付费，0未付费
    private int informEntCount;
    private int pendingProcessCount;//等待处理量
    private int deleteFlag;


    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getEntAddress() {
        return entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
    }

    public String getReleaseUserName() {
        return releaseUserName;
    }

    public void setReleaseUserName(String releaseUserName) {
        this.releaseUserName = releaseUserName;
    }

    public String getTotalWasteCount() {
        return totalWasteCount;
    }

    public void setTotalWasteCount(String totalWasteCount) {
        this.totalWasteCount = totalWasteCount;
    }

    public String getTotalWasteAmountDesc() {
        return totalWasteAmountDesc;
    }

    public void setTotalWasteAmountDesc(String totalWasteAmountDesc) {
        this.totalWasteAmountDesc = totalWasteAmountDesc;
    }

    public String getDisposalWasteCount() {
        return disposalWasteCount;
    }

    public void setDisposalWasteCount(String disposalWasteCount) {
        this.disposalWasteCount = disposalWasteCount;
    }

    public String getReleaseRemark() {
        return releaseRemark;
    }

    public void setReleaseRemark(String releaseRemark) {
        this.releaseRemark = releaseRemark;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<EntReleaseDetailModel> getReleaseWasteDetails() {
        return releaseWasteDetails;
    }

    public void setReleaseWasteDetails(List<EntReleaseDetailModel> releaseWasteDetails) {
        this.releaseWasteDetails = releaseWasteDetails;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getReleaseOwner() {
        return releaseOwner;
    }

    public void setReleaseOwner(String releaseOwner) {
        this.releaseOwner = releaseOwner;
    }

    public int getInquiryEntCount() {
        return inquiryEntCount;
    }

    public void setInquiryEntCount(int inquiryEntCount) {
        this.inquiryEntCount = inquiryEntCount;
    }

    public String getDisposalWasteAmount() {
        return disposalWasteAmount;
    }

    public void setDisposalWasteAmount(String disposalWasteAmount) {
        this.disposalWasteAmount = disposalWasteAmount;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getReleaseStatusValue() {
        return releaseStatusValue;
    }

    public void setReleaseStatusValue(String releaseStatusValue) {
        this.releaseStatusValue = releaseStatusValue;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public DiscussTagParam getTagInfo() {
        return tagInfo;
    }

    public void setTagInfo(DiscussTagParam tagInfo) {
        this.tagInfo = tagInfo;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    public String getCantonCodeName() {
        return cantonCodeName;
    }

    public void setCantonCodeName(String cantonCodeName) {
        this.cantonCodeName = cantonCodeName;
    }

    public int getReleaseCount() {
        return releaseCount;
    }

    public void setReleaseCount(int releaseCount) {
        this.releaseCount = releaseCount;
    }

    public String getInquiryStatus() {
        return inquiryStatus;
    }

    public void setInquiryStatus(String inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }

    public String getReleaseEntContact() {
        return releaseEntContact;
    }

    public void setReleaseEntContact(String releaseEntContact) {
        this.releaseEntContact = releaseEntContact;
    }

    public String getReleaseEntContactTel() {
        return releaseEntContactTel;
    }

    public void setReleaseEntContactTel(String releaseEntContactTel) {
        this.releaseEntContactTel = releaseEntContactTel;
    }

    public String getEntBindStatus() {
        return entBindStatus;
    }

    public void setEntBindStatus(String entBindStatus) {
        this.entBindStatus = entBindStatus;
    }

    public int getInformEntCount() {
        return informEntCount;
    }

    public void setInformEntCount(int informEntCount) {
        this.informEntCount = informEntCount;
    }

    public int getPendingProcessCount() {
        return pendingProcessCount;
    }

    public void setPendingProcessCount(int pendingProcessCount) {
        this.pendingProcessCount = pendingProcessCount;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
