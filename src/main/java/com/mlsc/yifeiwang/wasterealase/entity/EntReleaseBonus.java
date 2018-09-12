package com.mlsc.yifeiwang.wasterealase.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author caoyy
 * @since 2018-04-09
 */
@TableName("ent_release_bonus")
public class EntReleaseBonus extends Model<EntReleaseBonus> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String entId;
    private String releaseId;
    private String userId;
    private String token;
    private String busiStatus;
    private Date sendTime;
    private String createBy;
    private Date createTime;
    private String editBy;
    private Date editTime;
    private String browserType; //0：微信浏览器; 1：非微信浏览器


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

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getBusiStatus() {
        return busiStatus;
    }

    public void setBusiStatus(String busiStatus) {
        this.busiStatus = busiStatus;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EntReleaseBonus{" +
                "id=" + id +
                ", entId=" + entId +
                ", releaseId=" + releaseId +
                ", userId=" + userId +
                ", token=" + token +
                ", busiStatus=" + busiStatus +
                ", sendTime=" + sendTime +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", editBy=" + editBy +
                ", editTime=" + editTime +
                "}";
    }
}
