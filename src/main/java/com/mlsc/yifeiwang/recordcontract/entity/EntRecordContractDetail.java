package com.mlsc.yifeiwang.recordcontract.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author caoyy
 * @since 2018-03-01
 */
@TableName("ent_record_contract_detail")
public class EntRecordContractDetail extends Model<EntRecordContractDetail> {

    private static final long serialVersionUID = 1L;

	private String id;

	private String recordId;
    /**
     * 订单id
     */
	private String orderId;
	private String orderDetailId;
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

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
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
		return "EntRecordContractDetail{" +
			"id=" + id +
			", orderId=" + orderId +
			", orderDetailId=" + orderDetailId +
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
