package com.mlsc.yifeiwang.calling.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 行业模型表实体类
 * </p>
 *
 * @author yxl
 * @since 2017-07-26
 */
public class Calling extends Model<Calling> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String code;
	@TableField("parent_id")
	private String parent_id;
	private String name;
	private String description;
	private String status;
	@TableField("create_by")
	private String create_by;
	@TableField("create_time")
	private Date create_time;
	@TableField("edit_by")
	private String edit_by;
	@TableField("edit_time")
	private Date edit_time;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getEdit_by() {
		return edit_by;
	}

	public void setEdit_by(String edit_by) {
		this.edit_by = edit_by;
	}

	public Date getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(Date edit_time) {
		this.edit_time = edit_time;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Calling{" +
			"id=" + id +
			", code=" + code +
			", parent_id=" + parent_id +
			", name=" + name +
			", description=" + description +
			", status=" + status +
			", create_by=" + create_by +
			", create_time=" + create_time +
			", edit_by=" + edit_by +
			", edit_time=" + edit_time +
			"}";
	}
}
