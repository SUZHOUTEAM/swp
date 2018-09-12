package com.mlsc.yifeiwang.entwaste.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author yinxl
 * @since 2017-10-12
 */
@TableName("ent_waste")
public class EntWaste extends Model<EntWaste> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String entId;
	private String wasteId;
    /**
     * 八位码
     */
	private String wasteCode;
	private String wasteName;
	private String unitCode;
    /**
     * 主要有害物
     */
	private String harmfulSubstance;
    /**
     * 业务状态
     */
	private String busiStatus;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	private Integer deleteFlag;
	private String resource;
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

	public String getWasteId() {
		return wasteId;
	}

	public void setWasteId(String wasteId) {
		this.wasteId = wasteId;
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

	public String getHarmfulSubstance() {
		return harmfulSubstance;
	}

	public void setHarmfulSubstance(String harmfulSubstance) {
		this.harmfulSubstance = harmfulSubstance;
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
		return "EntWaste{" +
			"id=" + id +
			", entId=" + entId +
			", wasteId=" + wasteId +
			", wasteCode=" + wasteCode +
			", wasteName=" + wasteName +
			", unitCode=" + unitCode +
			", harmfulSubstance=" + harmfulSubstance +
			", busiStatus=" + busiStatus +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", deleteFlag=" + deleteFlag +
			", resource=" + resource +
			", externalEntId=" + externalEntId +
			"}";
	}
}
