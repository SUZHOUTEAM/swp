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
 * @since 2017-11-20
 */
@TableName("website_opertion_disposal_enterprise")
public class WebsiteOpertionDisposalEnterprise extends Model<WebsiteOpertionDisposalEnterprise> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String operationId;
	private String disposalEnterpriseId;
	private String entName;
	private String licenceId;
	private String busiStatus;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getDisposalEnterpriseId() {
		return disposalEnterpriseId;
	}

	public void setDisposalEnterpriseId(String disposalEnterpriseId) {
		this.disposalEnterpriseId = disposalEnterpriseId;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WebsiteOpertionDisposalEnterprise{" +
			"id=" + id +
			", operationId=" + operationId +
			", disposalEnterpriseId=" + disposalEnterpriseId +
			", entName=" + entName +
			", busiStatus=" + busiStatus +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			"}";
	}
}
