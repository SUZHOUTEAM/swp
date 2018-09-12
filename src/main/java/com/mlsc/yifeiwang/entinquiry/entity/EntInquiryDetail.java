package com.mlsc.yifeiwang.entinquiry.entity;

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
@TableName("ent_inquiry_detail")
public class EntInquiryDetail extends Model<EntInquiryDetail> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 询价主表ID
     */
    private String inquiryId;
    /**
     * 发布明细ID
     */
    private String releaseDetailId;
    /**
     * 单价
     */
    private Double price;
    private Double totalPrice;
    /**
     * 业务状态
     */
    private String busiStatus;
    private String dispositionType;

    private String priceId;

    private String createBy;
    private Date createTime;
    private String editBy;
    private Date editTime;
    private Integer deleteFlag;
    private String responseId;
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBusiStatus() {
        return busiStatus;
    }

    public void setBusiStatus(String busiStatus) {
        this.busiStatus = busiStatus;
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

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EntInquiryDetail{" +
                "id=" + id +
                ", inquiryId=" + inquiryId +
                ", releaseDetailId=" + releaseDetailId +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", busiStatus=" + busiStatus +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", editBy=" + editBy +
                ", editTime=" + editTime +
                ", deleteFlag=" + deleteFlag +
                ", responseId=" + responseId +
                "}";
    }
}
