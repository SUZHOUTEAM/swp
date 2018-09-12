package com.mlsc.yifeiwang.actionrecord.entity;

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
 * @since 2017-07-25
 */
@TableName("action_record")
public class ActionRecord extends Model<ActionRecord> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String keywords;
	@TableField("first_action")
	private String firstAction;
	@TableField("ip_addr")
	private String ipAddr;
	private String location;
	@TableField("action_time")
	private Date actionTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getFirstAction() {
		return firstAction;
	}

	public void setFirstAction(String firstAction) {
		this.firstAction = firstAction;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
