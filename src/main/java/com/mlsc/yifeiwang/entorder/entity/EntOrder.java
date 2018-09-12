package com.mlsc.yifeiwang.entorder.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yinxl
 * @since 2017-10-12
 */
@TableName("ent_order")
public class EntOrder extends Model<EntOrder> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 发布企业Id
     */
    private String releaseEntId;
    /**
     * 询价企业id
     */
    private String inquiryEntId;
    /**
     * 服务商企业id
     */
    private String facilitatorEntId;
    /**
     * 处置企业
     */
    private String disEntId;
    /**
     * 发布id
     */
    private String releaseId;
    /**
     * 询价id
     */
    private String inquiryId;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 询价类型
     */
    private Integer quotedType;
    private String totalAmount;
    private Double totalPrice;
    private Date confirmedTime;
    private String busiStatus;
    /**
     * 合同状态
     */
    private String contractStatus;
    private String createBy;
    private Date createTime;
    private String editBy;
    private Date editTime;
    private Integer deleteFlag;
    private String activityId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDisEntId() {
        return disEntId;
    }

    public void setDisEntId(String disEntId) {
        this.disEntId = disEntId;
    }

    public String getInquiryEntId() {
        return inquiryEntId;
    }

    public void setInquiryEntId(String inquiryEntId) {
        this.inquiryEntId = inquiryEntId;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getQuotedType() {
        return quotedType;
    }

    public void setQuotedType(Integer quotedType) {
        this.quotedType = quotedType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(Date confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    public String getBusiStatus() {
        return busiStatus;
    }

    public void setBusiStatus(String busiStatus) {
        this.busiStatus = busiStatus;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
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

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EntOrder{" +
                "id=" + id +
                ", releaseEntId=" + releaseEntId +
                ", inquiryEntId=" + inquiryEntId +
                ", releaseId=" + releaseId +
                ", inquiryId=" + inquiryId +
                ", orderCode=" + orderCode +
                ", quotedType=" + quotedType +
                ", totalAmount=" + totalAmount +
                ", totalPrice=" + totalPrice +
                ", confirmedTime=" + confirmedTime +
                ", busiStatus=" + busiStatus +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", editBy=" + editBy +
                ", editTime=" + editTime +
                ", deleteFlag=" + deleteFlag +
                ", activityId=" + activityId +
                "}";
    }
}
