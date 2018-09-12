package com.mlsc.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 处置资源明细
 * </p>
 *
 * @author yinxl
 * @since 2017-08-25
 */
@TableName("disposition_capacitydetail_release")
public class DispositionCapacitydetailRelease extends Model<DispositionCapacitydetailRelease> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("capacity_release_id")
	private String capacityReleaseId;
	@TableField("capacityitem_id")
	private String capacityitemId;
	@TableField("waste_id")
	private String wasteId;
	@TableField("waste_name_id")
	private String wasteNameId;
	@TableField("used_quantity")
	private Double usedQuantity;
	@TableField("release_status")
	private String releaseStatus;
	@TableField("create_by")
	private String createBy;
	@TableField("create_time")
	private Date createTime;
	@TableField("edit_by")
	private String editBy;
	@TableField("edit_time")
	private Date editTime;
	private String valid;
	@TableField("release_status_code")
	private String releaseStatusCode;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCapacityReleaseId() {
		return capacityReleaseId;
	}

	public void setCapacityReleaseId(String capacityReleaseId) {
		this.capacityReleaseId = capacityReleaseId;
	}

	public String getCapacityitemId() {
		return capacityitemId;
	}

	public void setCapacityitemId(String capacityitemId) {
		this.capacityitemId = capacityitemId;
	}

	public String getWasteId() {
		return wasteId;
	}

	public void setWasteId(String wasteId) {
		this.wasteId = wasteId;
	}

	public String getWasteNameId() {
		return wasteNameId;
	}

	public void setWasteNameId(String wasteNameId) {
		this.wasteNameId = wasteNameId;
	}

	public Double getUsedQuantity() {
		return usedQuantity;
	}

	public void setUsedQuantity(Double usedQuantity) {
		this.usedQuantity = usedQuantity;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
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

	public String getReleaseStatusCode() {
		return releaseStatusCode;
	}

	public void setReleaseStatusCode(String releaseStatusCode) {
		this.releaseStatusCode = releaseStatusCode;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "DispositionCapacitydetailRelease{" +
			"id=" + id +
			", capacityReleaseId=" + capacityReleaseId +
			", capacityitemId=" + capacityitemId +
			", wasteId=" + wasteId +
			", wasteNameId=" + wasteNameId +
			", usedQuantity=" + usedQuantity +
			", releaseStatus=" + releaseStatus +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", valid=" + valid +
			", releaseStatusCode=" + releaseStatusCode +
			"}";
	}
}
