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
@TableName("waste_transfer")
public class WasteTransfer extends Model<WasteTransfer> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String entId;
	private String transferNo;
	private String entName;
	private String wasteType;
	private String wasteCode;
	private String wasteName;
	private String unitCode;
	private Date transferTime;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	private String busiStatus;
	private Integer deleteFlag;
	private String resource;
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

	public String getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getWasteType() {
		return wasteType;
	}

	public void setWasteType(String wasteType) {
		this.wasteType = wasteType;
	}

	public String getWasteCode() {
		return wasteCode;
	}

	public void setWasteCode(String wasteCode) {
		this.wasteCode = wasteCode;
	}

	public String getWasteName() {
		return wasteName;
	}

	public void setWasteName(String wasteName) {
		this.wasteName = wasteName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
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
		return "WasteTransfer{" +
			"id=" + id +
			", entId=" + entId +
			", transferNo=" + transferNo +
			", entName=" + entName +
			", wasteType=" + wasteType +
			", wasteCode=" + wasteCode +
			", unitCode=" + unitCode +
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
