package com.mlsc.yifeiwang.wasterealase.entity;

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
@TableName("ent_release_detail")
public class EntReleaseDetail extends Model<EntReleaseDetail> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 企业发布ID
     */
	private String releaseId;
	private String entWasteId;
    /**
     * 企业发布数量
     */
	private Double releaseAmount;
    /**
     * 业务状态
     */
	private String busiStatus;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	private Integer deleteFlag;
	private String entReleaseDetailId;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public String getEntWasteId() {
		return entWasteId;
	}

	public void setEntWasteId(String entWasteId) {
		this.entWasteId = entWasteId;
	}

	public Double getReleaseAmount() {
		return releaseAmount;
	}

	public void setReleaseAmount(Double releaseAmount) {
		this.releaseAmount = releaseAmount;
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

	public String getEntReleaseDetailId() {
		return entReleaseDetailId;
	}

	public void setEntReleaseDetailId(String entReleaseDetailId) {
		this.entReleaseDetailId = entReleaseDetailId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "EntReleaseDetail{" +
			"id=" + id +
			", releaseId=" + releaseId +
			", entWasteId=" + entWasteId +
			", releaseAmount=" + releaseAmount +
			", busiStatus=" + busiStatus +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", deleteFlag=" + deleteFlag +
			", entReleaseDetailId=" + entReleaseDetailId +
			"}";
	}
}
