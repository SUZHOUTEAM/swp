package com.mlsc.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 处置资源应答
 * </p>
 *
 * @author zhangj
 * @since 2017-08-16
 */
@TableName("disposition_capacity_response")
public class DispositionCapacityResponse extends Model<DispositionCapacityResponse> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("release_enterprise_id")
	private String releaseEnterpriseId;
	@TableField("response_enterprise_id")
	private String responseEnterpriseId;
	@TableField("capacity_release_id")
	private String capacityReleaseId;
	@TableField("response_start_date")
	private Date responseStartDate;
	@TableField("response_end_date")
	private Date responseEndDate;
	@TableField("quote_type")
	private String quoteType;
	@TableField("total_amount")
	private BigDecimal totalAmount;
	private String remark;
	@TableField("version_code")
	private Date versionCode;
	@TableField("create_by")
	private String createBy;
	@TableField("create_time")
	private Date createTime;
	@TableField("edit_by")
	private String editBy;
	@TableField("edit_time")
	private Date editTime;
	private String valid;
	@TableField("resource_type")
	private String resourceType;
	@TableField("resource_id")
	private String resourceId;


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

	public String getResponseEnterpriseId() {
		return responseEnterpriseId;
	}

	public void setResponseEnterpriseId(String responseEnterpriseId) {
		this.responseEnterpriseId = responseEnterpriseId;
	}

	public String getCapacityReleaseId() {
		return capacityReleaseId;
	}

	public void setCapacityReleaseId(String capacityReleaseId) {
		this.capacityReleaseId = capacityReleaseId;
	}

	public Date getResponseStartDate() {
		return responseStartDate;
	}

	public void setResponseStartDate(Date responseStartDate) {
		this.responseStartDate = responseStartDate;
	}

	public Date getResponseEndDate() {
		return responseEndDate;
	}

	public void setResponseEndDate(Date responseEndDate) {
		this.responseEndDate = responseEndDate;
	}

	public String getQuoteType() {
		return quoteType;
	}

	public void setQuoteType(String quoteType) {
		this.quoteType = quoteType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Date versionCode) {
		this.versionCode = versionCode;
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

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "DispositionCapacityResponse{" +
			"id=" + id +
			", releaseEnterpriseId=" + releaseEnterpriseId +
			", responseEnterpriseId=" + responseEnterpriseId +
			", capacityReleaseId=" + capacityReleaseId +
			", responseStartDate=" + responseStartDate +
			", responseEndDate=" + responseEndDate +
			", quoteType=" + quoteType +
			", totalAmount=" + totalAmount +
			", remark=" + remark +
			", versionCode=" + versionCode +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", valid=" + valid +
			", resourceType=" + resourceType +
			", resourceId=" + resourceId +
			"}";
	}
}
