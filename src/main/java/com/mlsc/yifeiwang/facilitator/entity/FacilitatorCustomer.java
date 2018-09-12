package com.mlsc.yifeiwang.facilitator.entity;

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
 * @since 2018-01-17
 */
@TableName("facilitator_customer")
public class FacilitatorCustomer extends Model<FacilitatorCustomer> {

	private static final long serialVersionUID = 1L;

	private String id;
	private String facilitatorEntId;
	private String customerId;
	/**
	 * 业务状态
	 */
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

	public String getFacilitatorEntId() {
		return facilitatorEntId;
	}

	public void setFacilitatorEntId(String facilitatorEntId) {
		this.facilitatorEntId = facilitatorEntId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
		return "FacilitatorCustomer{" +
				"id=" + id +
				", facilitatorEntId=" + facilitatorEntId +
				", customerId=" + customerId +
				", busiStatus=" + busiStatus +
				", createBy=" + createBy +
				", createTime=" + createTime +
				", editBy=" + editBy +
				", editTime=" + editTime +
				", deleteFlag=" + deleteFlag +
				"}";
	}
}
