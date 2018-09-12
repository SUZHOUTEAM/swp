package com.mlsc.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 处置资源-处置方式
 * </p>
 *
 * @author yinxl
 * @since 2017-08-25
 */
@TableName("disposition_capacityitem_release")
public class DispositionCapacityitemRelease extends Model<DispositionCapacityitemRelease> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("capacity_release_id")
	private String capacityReleaseId;
	@TableField("disposition_type")
	private String dispositionType;
	@TableField("quota_quantity")
	private Double quotaQuantity;
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

	public String getCapacityReleaseId() {
		return capacityReleaseId;
	}

	public void setCapacityReleaseId(String capacityReleaseId) {
		this.capacityReleaseId = capacityReleaseId;
	}

	public String getDispositionType() {
		return dispositionType;
	}

	public void setDispositionType(String dispositionType) {
		this.dispositionType = dispositionType;
	}

	public Double getQuotaQuantity() {
		return quotaQuantity;
	}

	public void setQuotaQuantity(Double quotaQuantity) {
		this.quotaQuantity = quotaQuantity;
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
		return "DispositionCapacityitemRelease{" +
			"id=" + id +
			", capacityReleaseId=" + capacityReleaseId +
			", dispositionType=" + dispositionType +
			", quotaQuantity=" + quotaQuantity +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", valid=" + valid +
			"}";
	}
}
