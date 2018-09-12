package com.mlsc.yifeiwang.enterprise.entity;

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
 * @since 2017-12-25
 */
@TableName("enterprise_configuration")
public class EnterpriseConfiguration extends Model<EnterpriseConfiguration> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String entId;
	private String cantonCode;
	private String section;
	private Integer weight;
	private Integer page;
	private Integer index;
	private String national;
	private String wasteType;
	private Date startDate;
	private Date endDate;
    /**
     * 业务状态
     */
	private String busiStatus;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	private Integer deleteFlag;


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

	public String getCantonCode() {
		return cantonCode;
	}

	public void setCantonCode(String cantonCode) {
		this.cantonCode = cantonCode;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getNational() {
		return national;
	}

	public void setNational(String national) {
		this.national = national;
	}


	public String getWasteType() {
		return wasteType;
	}

	public void setWasteType(String wasteType) {
		this.wasteType = wasteType;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "EnterpriseConfiguration{" +
			"id=" + id +
			", entId=" + entId +
			", cantonCode=" + cantonCode +
			", section=" + section +
			", weight=" + weight +
			", page=" + page +
			", index=" + index +
			", national=" + national +
			", wasteType=" + wasteType +
			", startDate=" + startDate +
			", endDate=" + endDate +
			", busiStatus=" + busiStatus +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", deleteFlag=" + deleteFlag +
			"}";
	}
}
