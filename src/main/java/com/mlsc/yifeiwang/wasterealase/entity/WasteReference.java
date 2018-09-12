package com.mlsc.yifeiwang.wasterealase.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author caoyy
 * @since 2018-05-24
 */
@TableName("waste_reference")
public class WasteReference extends Model<WasteReference> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("canton_code")
	private String cantonCode;
	@TableField("waste_code")
	private String wasteCode;
	@TableField("default_disposition_type")
	private String defaultDispositionType;
	private String keyword;
	private String remark;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	private String deleteFlag;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCantonCode() {
		return cantonCode;
	}

	public void setCantonCode(String cantonCode) {
		this.cantonCode = cantonCode;
	}

	public String getWasteCode() {
		return wasteCode;
	}

	public void setWasteCode(String wasteCode) {
		this.wasteCode = wasteCode;
	}

	public String getDefaultDispositionType() {
		return defaultDispositionType;
	}

	public void setDefaultDispositionType(String defaultDispositionType) {
		this.defaultDispositionType = defaultDispositionType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WasteReference{" +
			"id=" + id +
			", cantonCode=" + cantonCode +
			", wasteCode=" + wasteCode +
			", defaultDispositionType=" + defaultDispositionType +
			", remark=" + remark +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", deleteFlag=" + deleteFlag +
			"}";
	}
}
