package com.mlsc.yifeiwang.operaction.entity;

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
 * @since 2017-11-21
 */
@TableName("annual_management_plan")
public class AnnualManagementPlan extends Model<AnnualManagementPlan> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String entId;
	private String versionCode;
	private String unitCode;
	private Double previousYearPlanAmount;
	private Double previousYearActualAmount;
	private Double yearPlanAmount;
	private Double yearCurrentAmount;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	private String busiStatus;
	private Integer deleteFlag;
	private String resource;
	private String planYear;
	private String externalId;
	private String externalEntId;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Double getPreviousYearPlanAmount() {
		return previousYearPlanAmount;
	}

	public void setPreviousYearPlanAmount(Double previousYearPlanAmount) {
		this.previousYearPlanAmount = previousYearPlanAmount;
	}

	public Double getPreviousYearActualAmount() {
		return previousYearActualAmount;
	}

	public void setPreviousYearActualAmount(Double previousYearActualAmount) {
		this.previousYearActualAmount = previousYearActualAmount;
	}

	public Double getYearPlanAmount() {
		return yearPlanAmount;
	}

	public void setYearPlanAmount(Double yearPlanAmount) {
		this.yearPlanAmount = yearPlanAmount;
	}

	public Double getYearCurrentAmount() {
		return yearCurrentAmount;
	}

	public void setYearCurrentAmount(Double yearCurrentAmount) {
		this.yearCurrentAmount = yearCurrentAmount;
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

	public String getBusiStatus() {
		return busiStatus;
	}

	public void setBusiStatus(String busiStatus) {
		this.busiStatus = busiStatus;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getPlanYear() {
		return planYear;
	}

	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getExternalEntId() {
		return externalEntId;
	}

	public void setExternalEntId(String externalEntId) {
		this.externalEntId = externalEntId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AnnualManagementPlan{" +
			"id=" + id +
			", entId=" + entId +
			", versionCode=" + versionCode +
			", unitCode=" + unitCode +
			", previousYearPlanAmount=" + previousYearPlanAmount +
			", previousYearActualAmount=" + previousYearActualAmount +
			", yearPlanAmount=" + yearPlanAmount +
			", yearCurrentAmount=" + yearCurrentAmount +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", busiStatus=" + busiStatus +
			", deleteFlag=" + deleteFlag +
			", resource=" + resource +
			", externalId=" + externalId +
			", externalEntId=" + externalEntId +
			"}";
	}
}
