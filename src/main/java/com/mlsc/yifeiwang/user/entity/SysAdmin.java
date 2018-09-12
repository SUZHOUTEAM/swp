package com.mlsc.yifeiwang.user.entity;

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
 * @since 2018-02-28
 */
@TableName("sys_admin")
public class SysAdmin extends Model<SysAdmin> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String userId;
	private String createBy;
	private Date createTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysAdmin{" +
			"id=" + id +
			", userId=" + userId +
			", createBy=" + createBy +
			", createTime=" + createTime +
			"}";
	}
}
