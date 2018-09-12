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
@TableName("operation_licence")
public class OperationLicence extends Model<OperationLicence> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("enterprise_id")
	private String enterpriseId;
	@TableField("enterprise_name")
	private String enterpriseName;
	@TableField("licence_no")
	private String licenceNo;
	@TableField("licence_org")
	private String licenceOrg;
	@TableField("licence_date")
	private Date licenceDate;
	@TableField("initiallic_date")
	private Date initiallicDate;
	private String corporate;
	@TableField("register_addr")
	private String registerAddr;
	@TableField("machine_addr")
	private String machineAddr;
	@TableField("start_date")
	private Date startDate;
	@TableField("end_date")
	private Date endDate;
	@TableField("licence_status")
	private String licenceStatus;
	@TableField("operation_mode")
	private String operationMode;
	@TableField("application_time")
	private Date applicationTime;
	@TableField("audit_status")
	private String auditStatus;
	@TableField("approved_by")
	private String approvedBy;
	@TableField("create_by")
	private String createBy;
	@TableField("create_time")
	private Date createTime;
	@TableField("edit_by")
	private String editBy;
	@TableField("edit_time")
	private Date editTime;
	private String valid;
	private String resource;
	@TableField("release_status")
	private String releaseStatus;
	@TableField("external_entId")
	private String externalEntId;

	@TableField("licence_status_code")
	private String licenceStatusCode;
	@TableField("audit_status_code")
	private String auditStatusCode;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public String getLicenceOrg() {
		return licenceOrg;
	}

	public void setLicenceOrg(String licenceOrg) {
		this.licenceOrg = licenceOrg;
	}

	public Date getLicenceDate() {
		return licenceDate;
	}

	public void setLicenceDate(Date licenceDate) {
		this.licenceDate = licenceDate;
	}

	public Date getInitiallicDate() {
		return initiallicDate;
	}

	public void setInitiallicDate(Date initiallicDate) {
		this.initiallicDate = initiallicDate;
	}

	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	public String getRegisterAddr() {
		return registerAddr;
	}

	public void setRegisterAddr(String registerAddr) {
		this.registerAddr = registerAddr;
	}

	public String getMachineAddr() {
		return machineAddr;
	}

	public void setMachineAddr(String machineAddr) {
		this.machineAddr = machineAddr;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLicenceStatus() {
		return licenceStatus;
	}

	public void setLicenceStatus(String licenceStatus) {
		this.licenceStatus = licenceStatus;
	}

	public String getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
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

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getExternalEntId() {
		return externalEntId;
	}

	public void setExternalEntId(String externalEntId) {
		this.externalEntId = externalEntId;
	}


	public String getLicenceStatusCode() {
		return licenceStatusCode;
	}

	public void setLicenceStatusCode(String licenceStatusCode) {
		this.licenceStatusCode = licenceStatusCode;
	}

	public String getAuditStatusCode() {
		return auditStatusCode;
	}

	public void setAuditStatusCode(String auditStatusCode) {
		this.auditStatusCode = auditStatusCode;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OperationLicence{" +
			"id=" + id +
			", enterpriseId=" + enterpriseId +
			", enterpriseName=" + enterpriseName +
			", licenceNo=" + licenceNo +
			", licenceOrg=" + licenceOrg +
			", licenceDate=" + licenceDate +
			", initiallicDate=" + initiallicDate +
			", corporate=" + corporate +
			", registerAddr=" + registerAddr +
			", machineAddr=" + machineAddr +
			", startDate=" + startDate +
			", endDate=" + endDate +
			", licenceStatus=" + licenceStatus +
			", operationMode=" + operationMode +
			", applicationTime=" + applicationTime +
			", auditStatus=" + auditStatus +
			", approvedBy=" + approvedBy +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", valid=" + valid +
			", resource=" + resource +
			", releaseStatus=" + releaseStatus +
			", externalEntId=" + externalEntId +
			"}";
	}
}
