package com.mlsc.yifeiwang.entorder.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author yinxl
 * @since 2017-10-12
 */
@TableName("ent_order_detail")
public class EntOrderDetail extends Model<EntOrderDetail> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 订单id
     */
	private String orderId;
    /**
     * 发布明细id
     */
	private String releaseDetailId;
    /**
     * 询价明细id
     */
	private String inquiryDetailId;
	private Double price;
	private Double totalPrice;
	private String busiStatus;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	private Integer deleteFlag;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReleaseDetailId() {
		return releaseDetailId;
	}

	public void setReleaseDetailId(String releaseDetailId) {
		this.releaseDetailId = releaseDetailId;
	}

	public String getInquiryDetailId() {
		return inquiryDetailId;
	}

	public void setInquiryDetailId(String inquiryDetailId) {
		this.inquiryDetailId = inquiryDetailId;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "EntOrderDetail{" +
			"id=" + id +
			", orderId=" + orderId +
			", releaseDetailId=" + releaseDetailId +
			", inquiryDetailId=" + inquiryDetailId +
			", price=" + price +
			", totalPrice=" + totalPrice +
			", busiStatus=" + busiStatus +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", deleteFlag=" + deleteFlag +
			"}";
	}
}
