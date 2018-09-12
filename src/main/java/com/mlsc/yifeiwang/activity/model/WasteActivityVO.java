package com.mlsc.yifeiwang.activity.model;

import com.mlsc.yifeiwang.wasterealase.model.ReleaseStatusModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yinxl on 2017/7/31.
 */
public class WasteActivityVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 活动id
     */
    private String activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 短信模板
     */
    private String smsTemplate;
    /**
     * 活动状态
     */
    private String status;
    /**
     * 活动说明
     */
    private String activityRemark;
    /**
     * 父活动id
     */
    private String parentActivityId;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    private String cronJob;
    /**
     * 价格类型
     */
    private String priceType;
    /**
     * 活动区域行政区编码
     */
    private String cantonCode;
    /**
     * 宣传图片id
     */
    private String subjectFileId;
    /**
     * logo图标id
     */
    private String logoFileId;

    /**
     * 询价图标id
     */
    private String inquiryFileId;

    /**
     * 手机轮播图id
     */
    private String swipeFileId;

    /**
     * 参与企业id
     */
    private String entId;
    /**
     * 参与企业名称
     */
    private String entName;
    /**
     * 企业说明
     */
    private String entRemark;
    /**
     * 价格类型
     */
    private String startPrice;
    /**
     * 价格类型
     */
    private String endPrice;

    /**
     * 申请量
     */
    private Double applyAmount;

    /**
     * 成交量
     */
    private Double volumeAmount;
    /**
     * 申请企业数
     */
    private int applyEntCount;
    /**
     * 成交企业数
     */
    private int approveEntCount;

    private String videoResource;

    private String fileCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date riseEndDate;

    private double discount;

    private double enrollFee;

    private double numberGroupBuying;

    private int enrollActivityAcount;

    private String industry;

    /**
     * 主讲人
     */
    private String presenter;
    /**
     * 课程时间
     */
    private String duration;
    /**
     * 定制课程说明
     */
    private String customCourseDesc;
    /**
     * 付费说明
     */
    private String payNote;
    /**
     * 支付状态
     */
    private String payStatus;


    /**
     * 覆盖企业数
     */
    private int coverEntCount;
    /**
     * 支付易废币
     */
    private int bitCion;


    private List<ReleaseStatusModel> releaseStatusList;

    public String getInquiryFileId() {
        return inquiryFileId;
    }

    public void setInquiryFileId(String inquiryFileId) {
        this.inquiryFileId = inquiryFileId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public String getActivityRemark() {
        return activityRemark;
    }

    public void setActivityRemark(String activityRemark) {
        this.activityRemark = activityRemark;
    }

    public String getParentActivityId() {
        return parentActivityId;
    }

    public void setParentActivityId(String parentActivityId) {
        this.parentActivityId = parentActivityId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCronJob() {
        return cronJob;
    }

    public void setCronJob(String cronJob) {
        this.cronJob = cronJob;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    public String getSubjectFileId() {
        return subjectFileId;
    }

    public void setSubjectFileId(String subjectFileId) {
        this.subjectFileId = subjectFileId;
    }

    public String getLogoFileId() {
        return logoFileId;
    }

    public void setLogoFileId(String logoFileId) {
        this.logoFileId = logoFileId;
    }

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getEntRemark() {
        return entRemark;
    }

    public void setEntRemark(String entRemark) {
        this.entRemark = entRemark;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    public Double getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(Double applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Double getVolumeAmount() {
        return volumeAmount;
    }

    public void setVolumeAmount(Double volumeAmount) {
        this.volumeAmount = volumeAmount;
    }

    public int getApplyEntCount() {
        return applyEntCount;
    }

    public void setApplyEntCount(int applyEntCount) {
        this.applyEntCount = applyEntCount;
    }

    public int getApproveEntCount() {
        return approveEntCount;
    }

    public void setApproveEntCount(int approveEntCount) {
        this.approveEntCount = approveEntCount;
    }

    public String getVideoResource() {
        return videoResource;
    }

    public void setVideoResource(String videoResource) {
        this.videoResource = videoResource;
    }

    public String getFileCount() {
        return fileCount;
    }

    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
    }

    public Date getRiseEndDate() {
        return riseEndDate;
    }

    public void setRiseEndDate(Date riseEndDate) {
        this.riseEndDate = riseEndDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getEnrollFee() {
        return enrollFee;
    }

    public void setEnrollFee(double enrollFee) {
        this.enrollFee = enrollFee;
    }

    public double getNumberGroupBuying() {
        return numberGroupBuying;
    }

    public void setNumberGroupBuying(double numberGroupBuying) {
        this.numberGroupBuying = numberGroupBuying;
    }

    public int getEnrollActivityAcount() {
        return enrollActivityAcount;
    }

    public void setEnrollActivityAcount(int enrollActivityAcount) {
        this.enrollActivityAcount = enrollActivityAcount;
    }


    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCustomCourseDesc() {
        return customCourseDesc;
    }

    public void setCustomCourseDesc(String customCourseDesc) {
        this.customCourseDesc = customCourseDesc;
    }

    public String getPayNote() {
        return payNote;
    }

    public void setPayNote(String payNote) {
        this.payNote = payNote;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public int getCoverEntCount() {
        return coverEntCount;
    }

    public void setCoverEntCount(int coverEntCount) {
        this.coverEntCount = coverEntCount;
    }

    public int getBitCion() {
        return bitCion;
    }

    public void setBitCion(int bitCion) {
        this.bitCion = bitCion;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public List<ReleaseStatusModel> getReleaseStatusList() {
        return releaseStatusList;
    }

    public void setReleaseStatusList(List<ReleaseStatusModel> releaseStatusList) {
        this.releaseStatusList = releaseStatusList;
    }

    public String getSwipeFileId() {
        return swipeFileId;
    }

    public void setSwipeFileId(String swipeFileId) {
        this.swipeFileId = swipeFileId;
    }
}
