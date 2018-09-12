package com.mlsc.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 处置资源
 * </p>
 *
 * @author zhangj
 * @since 2017-08-16
 */
@TableName("disposition_capacity_release")
public class DispositionCapacityRelease extends Model<DispositionCapacityRelease> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("release_enterprise_id")
	private String releaseEnterpriseId;
	@TableField("capacity_release_code")
	private String capacityReleaseCode;
	@TableField("release_startdate")
	private Date releaseStartdate;
	@TableField("release_enddate")
	private Date releaseEnddate;
	@TableField("operation_licence_id")
	private String operationLicenceId;
	private String remark;
	private Date versioncode;
	@TableField("create_by")
	private String createBy;
	@TableField("create_time")
	private Date createTime;
	@TableField("edit_by")
	private String editBy;
	@TableField("edit_time")
	private Date editTime;
	private String valid;
	@TableField("release_status")
	private String releaseStatus;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReleaseEnterpriseId() {
		return releaseEnterpriseId;
	}

	public void setReleaseEnterpriseId(String releaseEnterpriseId) {
		this.releaseEnterpriseId = releaseEnterpriseId;
	}

	public String getCapacityReleaseCode() {
		return capacityReleaseCode;
	}

	public void setCapacityReleaseCode(String capacityReleaseCode) {
		this.capacityReleaseCode = capacityReleaseCode;
	}

	public Date getReleaseStartdate() {
		return releaseStartdate;
	}

	public void setReleaseStartdate(Date releaseStartdate) {
		this.releaseStartdate = releaseStartdate;
	}

	public Date getReleaseEnddate() {
		return releaseEnddate;
	}

	public void setReleaseEnddate(Date releaseEnddate) {
		this.releaseEnddate = releaseEnddate;
	}

	public String getOperationLicenceId() {
		return operationLicenceId;
	}

	public void setOperationLicenceId(String operationLicenceId) {
		this.operationLicenceId = operationLicenceId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(Date versioncode) {
		this.versioncode = versioncode;
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

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "DispositionCapacityRelease{" +
			"id=" + id +
			", releaseEnterpriseId=" + releaseEnterpriseId +
			", capacityReleaseCode=" + capacityReleaseCode +
			", releaseStartdate=" + releaseStartdate +
			", releaseEnddate=" + releaseEnddate +
			", operationLicenceId=" + operationLicenceId +
			", remark=" + remark +
			", versioncode=" + versioncode +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", valid=" + valid +
			", releaseStatus=" + releaseStatus +
			"}";
	}
}
