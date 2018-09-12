package com.mlsc.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 上传文件
 * </p>
 *
 * @author zhangj
 * @since 2017-08-23
 */
@TableName("upload_file")
public class UploadFile extends Model<UploadFile> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("business_code")
	private String businessCode;
	@TableField("md5_id")
	private String md5Id;
	@TableField("file_id")
	private String fileId;
	@TableField("file_type")
	private String fileType;
	@TableField("reference_id")
	private String referenceId;
	@TableField("create_by")
	private String createBy;
	@TableField("create_time")
	private Date createTime;
	@TableField("edit_by")
	private String editBy;
	@TableField("edit_time")
	private Date editTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getMd5Id() {
		return md5Id;
	}

	public void setMd5Id(String md5Id) {
		this.md5Id = md5Id;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
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
		return "UploadFile{" +
			"id=" + id +
			", businessCode=" + businessCode +
			", md5Id=" + md5Id +
			", fileId=" + fileId +
			", fileType=" + fileType +
			", referenceId=" + referenceId +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			"}";
	}
}
