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
@TableName("code_value")
public class CodeValue extends Model<CodeValue> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("type_id")
	private String type_id;
	@TableField("type_code")
	private String typeCode;
	private String code;
	private String value;
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

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
