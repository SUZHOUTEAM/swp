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
@TableName("operation_licence_detail")
public class OperationLicenceDetail extends Model<OperationLicenceDetail> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("licence_id")
	private String licenceId;
	@TableField("operation_item_id")
	private String operationItemId;
	@TableField("waste_type")
	private String wasteType;
	@TableField("waste_name_id")
	private String wasteNameId;
	@TableField("waste_id")
	private String wasteId;
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

	public String getOperationItemId() {
		return operationItemId;
	}

	public void setOperationItemId(String operationItemId) {
		this.operationItemId = operationItemId;
	}

	public String getWasteType() {
		return wasteType;
	}

	public void setWasteType(String wasteType) {
		this.wasteType = wasteType;
	}

	public String getWasteNameId() {
		return wasteNameId;
	}

	public void setWasteNameId(String wasteNameId) {
		this.wasteNameId = wasteNameId;
	}

	public String getWasteId() {
		return wasteId;
	}

	public void setWasteId(String wasteId) {
		this.wasteId = wasteId;
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
		return "OperationLicenceDetail{" +
			"id=" + id +
			", licenceId=" + licenceId +
			", operationItemId=" + operationItemId +
			", wasteType=" + wasteType +
			", wasteNameId=" + wasteNameId +
			", wasteId=" + wasteId +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", valid=" + valid +
			"}";
	}
}
