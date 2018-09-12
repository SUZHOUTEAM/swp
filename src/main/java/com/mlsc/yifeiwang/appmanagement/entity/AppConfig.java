package com.mlsc.yifeiwang.appmanagement.entity;

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
 * @since 2017-07-28
 */
@TableName("app_config")
public class AppConfig extends Model<AppConfig> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 加载js代码方式：1:直接load本地包
2.本地包需下载更新
3.从线上获取

     */
	@TableField("load_type")
	private Integer loadType;
    /**
     * app版本
     */
	@TableField("app_version")
	private String appVersion;
    /**
     * js下载路径
     */
	@TableField("js_path")
	private String jsPath;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 版本描述
     */
	private String description;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getLoadType() {
		return loadType;
	}

	public void setLoadType(Integer loadType) {
		this.loadType = loadType;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getJsPath() {
		return jsPath;
	}

	public void setJsPath(String jsPath) {
		this.jsPath = jsPath;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AppConfig{" +
			"id=" + id +
			", loadType=" + loadType +
			", appVersion=" + appVersion +
			", jsPath=" + jsPath +
			", updateTime=" + updateTime +
			", description=" + description +
			"}";
	}
}
