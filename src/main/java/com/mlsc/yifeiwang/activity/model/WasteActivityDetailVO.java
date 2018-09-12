package com.mlsc.yifeiwang.activity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by AaronWang on 2017/8/1 0001.
 */
public class WasteActivityDetailVO implements Serializable {
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
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
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

    private String inquiryFileId;

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
     * 最低价格
     */
    private String startPrice;
    /**
     * 最高价格
     */
    private String endPrice;

    /**
     * 许可证编号
     */
    private String licenceNo;
    /**
     * 许可证id
     */
    private String licenceId;

    /**
     * 公司地址
     */
    private String machineAddr;

    /**
     * 许可证有效期
     */
    private Date licStartDate;
    /**
     * 许可证有效期
     */
    private Date licEndDate;

    /**
     * 处置能力发布主表id
     */
    private String dispositionCapacityReleaseId;

    /**
     * 处置能力发布详细表id
     */
    private String dispositionCapacitydetailReleaseId;

    /**
     * 处置方式ID
     */
    private String dispositionType;

    /**
     * 处置方式名称
     */
    private String dispositionTypeName;

    /**
     * 处置方式code
     */
    private String dispositionTypeCode;

    /**
     * 危废名称
     */
    private String wasteName;

    /**
     * 危废id
     */
    private String wasteId;


    /**
     * 产废企业id
     *
     * @return
     */
    private String wasteEntId;

    private String wasteDescription;

    private String wasteCode;

    private List<EnterpriseWasteVo> enterpriseWastelist;
    private Date riseEndDate;

    private double discount;

    private double enrollFee;

    private double numberGroupBuying;

    private int enrollActivityAcount;

    private String videoResource;

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
     * 购买过的次数
     */
    private Integer buyCount;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getVideoResource() {
        return videoResource;
    }

    public void setVideoResource(String videoResource) {
        this.videoResource = videoResource;
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

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(String licenceId) {
        this.licenceId = licenceId;
    }

    public String getMachineAddr() {
        return machineAddr;
    }

    public void setMachineAddr(String machineAddr) {
        this.machineAddr = machineAddr;
    }

    public Date getLicStartDate() {
        return licStartDate;
    }

    public void setLicStartDate(Date licStartDate) {
        this.licStartDate = licStartDate;
    }

    public Date getLicEndDate() {
        return licEndDate;
    }

    public void setLicEndDate(Date licEndDate) {
        this.licEndDate = licEndDate;
    }

    public String getDispositionCapacityReleaseId() {
        return dispositionCapacityReleaseId;
    }

    public void setDispositionCapacityReleaseId(String dispositionCapacityReleaseId) {
        this.dispositionCapacityReleaseId = dispositionCapacityReleaseId;
    }

    public String getDispositionCapacitydetailReleaseId() {
        return dispositionCapacitydetailReleaseId;
    }

    public void setDispositionCapacitydetailReleaseId(String dispositionCapacitydetailReleaseId) {
        this.dispositionCapacitydetailReleaseId = dispositionCapacitydetailReleaseId;
    }

    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public String getWasteId() {
        return wasteId;
    }

    public void setWasteId(String wasteId) {
        this.wasteId = wasteId;
    }

    public String getWasteEntId() {
        return wasteEntId;
    }

    public void setWasteEntId(String wasteEntId) {
        this.wasteEntId = wasteEntId;
    }

    public String getWasteDescription() {
        return wasteDescription;
    }

    public void setWasteDescription(String wasteDescription) {
        this.wasteDescription = wasteDescription;
    }

    public String getWasteCode() {
        return wasteCode;
    }

    public void setWasteCode(String wasteCode) {
        this.wasteCode = wasteCode;
    }

    public String getInquiryFileId() {
        return inquiryFileId;
    }

    public void setInquiryFileId(String inquiryFileId) {
        this.inquiryFileId = inquiryFileId;
    }

    public List<EnterpriseWasteVo> getEnterpriseWastelist() {
        return enterpriseWastelist;
    }

    public void setEnterpriseWastelist(List<EnterpriseWasteVo> enterpriseWastelist) {
        this.enterpriseWastelist = enterpriseWastelist;
    }

    public String getDispositionType() {
        return dispositionType;
    }

    public void setDispositionType(String dispositionType) {
        this.dispositionType = dispositionType;
    }

    public String getDispositionTypeName() {
        return dispositionTypeName;
    }

    public void setDispositionTypeName(String dispositionTypeName) {
        this.dispositionTypeName = dispositionTypeName;
    }

    public String getDispositionTypeCode() {
        return dispositionTypeCode;
    }

    public void setDispositionTypeCode(String dispositionTypeCode) {
        this.dispositionTypeCode = dispositionTypeCode;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public String getSwipeFileId() {
        return swipeFileId;
    }

    public void setSwipeFileId(String swipeFileId) {
        this.swipeFileId = swipeFileId;
    }
}
