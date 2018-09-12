package com.mlsc.yifeiwang.licence.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangj
 * @since 2017-08-22
 */
@TableName("operation_licence_item")
public class OperationLicenceItem extends Model<OperationLicenceItem> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("licence_id")
	private String licenceId;
	@TableField("disposition_type")
	private String dispositionType;
	@TableField("approved_quantity")
	private Double approvedQuantity;
	@TableField("excuted_quantity")
	private Double excutedQuantity;
	private String remark;
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

	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}

	public String getDispositionType() {
		return dispositionType;
	}

	public void setDispositionType(String dispositionType) {
		this.dispositionType = dispositionType;
	}

	public Double getApprovedQuantity() {
		return approvedQuantity;
	}

	public void setApprovedQuantity(Double approvedQuantity) {
		this.approvedQuantity = approvedQuantity;
	}

	public Double getExcutedQuantity() {
		return excutedQuantity;
	}

	public void setExcutedQuantity(Double excutedQuantity) {
		this.excutedQuantity = excutedQuantity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getValid() {
		return valid;
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
		return "OperationLicenceItem{" +
			"id=" + id +
			", licenceId=" + licenceId +
			", dispositionType=" + dispositionType +
			", approvedQuantity=" + approvedQuantity +
			", excutedQuantity=" + excutedQuantity +
			", remark=" + remark +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", valid=" + valid +
			"}";
	}
}
