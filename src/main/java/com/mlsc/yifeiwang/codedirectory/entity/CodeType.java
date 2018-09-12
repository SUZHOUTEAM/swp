package com.mlsc.yifeiwang.codedirectory.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yxl
 * @since 2017-07-20
 */
@TableName("code_type")
public class CodeType extends Model<CodeType> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("type_code")
	private String type_code;
	@TableField("type_name")
	private String type_name;
	private String splice;
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

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getSplice() {
		return splice;
	}

	public void setSplice(String splice) {
		this.splice = splice;
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

}
