package com.mlsc.yifeiwang.useraction.entity;

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
 * @since 2018-01-03
 */
@TableName("user_action")
public class UserAction extends Model<UserAction> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String ticketId;
    private String userId;
    private String entId;
    private String eventCode;
    private String eventValue;
    private String cid;
    private String cname;
    private String cip;
    private String remark;
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventValue() {
        return eventValue;
    }

    public void setEventValue(String eventValue) {
        this.eventValue = eventValue;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
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
        return "UserAction{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", userId=" + userId +
                ", entId=" + entId +
                ", eventCode=" + eventCode +
                ", cid=" + cid +
                ", cname=" + cname +
                ", cip=" + cip +
                ", remark=" + remark +
                ", createTime=" + createTime +
                "}";
    }
}
