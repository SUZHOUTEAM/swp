package com.mlsc.yifeiwang.activity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yxl
 * @since 2017-07-31
 */
@TableName("waste_activity")
public class WasteActivity extends Model<WasteActivity> {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    private String id;
    /**
     * 活动名称
     */
    @TableField("activity_name")
    private String activityName;
    /**
     * 活动说明
     */
    @TableField("activity_remark")
    private String activityRemark;
    /**
     * 父活动id
     */
    @TableField("parent_activity_id")
    private String parentActivityId;

    @TableField("industry")
    private String industry;

    /**
     * 开始时间
     */
    @TableField("start_date")
    private Date startDate;

    /**
     * 结束时间
     */
    @TableField("end_date")
    private Date endDate;


    @TableField("rise_end_date")
    private Date riseEndDate;
    /**
     * 折扣
     */
    @TableField("discount")
    private Double discount;
    /**
     * 报名费
     */
    @TableField("enroll_fee")
    private Double enrollFee;
    /**
     * 众筹人数
     */
    @TableField("number_group_buying")
    private Double numberGroupBuying;

    /**
     * 主讲人
     */
    @TableField("presenter")
    private String presenter;
    /**
     * 课程时间
     */
    @TableField("duration")
    private String duration;
    /**
     * 定制课程说明
     */
    @TableField("custom_course_desc")
    private String customCourseDesc;
    /**
     * 付费说明
     */
    @TableField("pay_note")
    private String payNote;

    /**
     * 价格类型
     */
    @TableField("price_type")
    private String priceType;
    /**
     * 活动区域行政区编码
     */
    @TableField("canton_code")
    private String cantonCode;
    /**
     * 宣传图片id
     */
    @TableField("subject_file_id")
    private String subjectFileId;
    /**
     * 询价图标id
     */
    @TableField("inquiry_file_id")
    private String inquiryFileId;
    /**
     * logo图标id
     */
    @TableField("logo_file_id")
    private String logoFileId;
    /**
     * logo图标id
     */
    @TableField("swipe_file_id")
    private String swipeFileId;


    @TableField("video_resource")
    private String videoResource;

    @TableField("file_count")
    private String fileCount;

    @TableField("sms_template")
    private String smsTemplate;

    @TableField("cronJob")
    private String cronJob;

    @TableField("cover_ent_count")
    private Integer coverEntCount;

    @TableField("bit_cion")
    private Integer bitCion;

    @TableField("pay_status")
    private String payStatus;
    /**
     * 数据状态
     */
    private String status;
    @TableField("create_by")
    private String createBy;
    @TableField("create_time")
    private Date createTime;
    @TableField("edit_by")
    private String editBy;
    @TableField("edit_time")
    private Date editTime;
    private String valid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getRiseEndDate() {
        return riseEndDate;
    }

    public void setRiseEndDate(Date riseEndDate) {
        this.riseEndDate = riseEndDate;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getEnrollFee() {
        return enrollFee;
    }

    public void setEnrollFee(Double enrollFee) {
        this.enrollFee = enrollFee;
    }

    public Double getNumberGroupBuying() {
        return numberGroupBuying;
    }

    public void setNumberGroupBuying(Double numberGroupBuying) {
        this.numberGroupBuying = numberGroupBuying;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getInquiryFileId() {
        return inquiryFileId;
    }

    public void setInquiryFileId(String inquiryFileId) {
        this.inquiryFileId = inquiryFileId;
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

    public String getCronJob() {
        return cronJob;
    }

    public void setCronJob(String cronJob) {
        this.cronJob = cronJob;
    }

    public Integer getCoverEntCount() {
        return coverEntCount;
    }

    public void setCoverEntCount(Integer coverEntCount) {
        this.coverEntCount = coverEntCount;
    }

    public Integer getBitCion() {
        return bitCion;
    }

    public void setBitCion(Integer bitCion) {
        this.bitCion = bitCion;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public String getValid() {
        return valid;
    }

    public String getSwipeFileId() {
        return swipeFileId;
    }

    public void setSwipeFileId(String swipeFileId) {
        this.swipeFileId = swipeFileId;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WasteActivity{" +
                "id=" + id +
                ", activityName=" + activityName +
                ", activityRemark=" + activityRemark +
                ", parentActivityId=" + parentActivityId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceType=" + priceType +
                ", cantonCode=" + cantonCode +
                ", subjectFileId=" + subjectFileId +
                ", logoFileId=" + logoFileId +
                ", status=" + status +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", editBy=" + editBy +
                ", editTime=" + editTime +
                "}";
    }
}
