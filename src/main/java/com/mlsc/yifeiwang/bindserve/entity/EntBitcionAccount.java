package com.mlsc.yifeiwang.bindserve.entity;

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
 * @since 2018-04-26
 */
@TableName("ent_bitcion_account")
public class EntBitcionAccount extends Model<EntBitcionAccount> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String entId;
	private String userId;
	private Integer bitcion;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	private Integer deleteFlag;
	private String version;


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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getBitcion() {
		return bitcion;
	}

	public void setBitcion(Integer bitcion) {
		this.bitcion = bitcion;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "EntBitcionAccount{" +
			"id=" + id +
			", entId=" + entId +
			", bitcion=" + bitcion +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", deleteFlag=" + deleteFlag +
			"}";
	}
}
