package com.mlsc.yifeiwang.user.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 平台用户表的一个扩展表
 * </p>
 *
 * @author caoyy
 * @since 2018-07-10
 */
@TableName("user_extended")
public class UserExtended extends Model<UserExtended> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("sys_user_id")
    private String sysUserId;
    @TableField("email_status")
    private String emailStatus;
    @TableField("mobile_status")
    private String mobileStatus;
    @TableField("is_wastecycle_init")
    private String isWastecycleInit;
    @TableField("user_icon")
    private String userIcon;
    private String valid;
    @TableField("registration_code")
    private String registrationCode;
    private String token;
    private String role;
    private String angle;
    @TableField("user_status")
    private String userStatus;
    @TableField("update_time")
    private Date updateTime;
    @TableField("weixin_open_id")
    private Date weiXinOpenId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(String mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public String getIsWastecycleInit() {
        return isWastecycleInit;
    }

    public void setIsWastecycleInit(String isWastecycleInit) {
        this.isWastecycleInit = isWastecycleInit;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getWeiXinOpenId() {
        return weiXinOpenId;
    }

    public void setWeiXinOpenId(Date weiXinOpenId) {
        this.weiXinOpenId = weiXinOpenId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserExtended{" +
                "id=" + id +
                ", sysUserId=" + sysUserId +
                ", emailStatus=" + emailStatus +
                ", mobileStatus=" + mobileStatus +
                ", isWastecycleInit=" + isWastecycleInit +
                ", userIcon=" + userIcon +
                ", valid=" + valid +
                ", registrationCode=" + registrationCode +
                ", token=" + token +
                ", role=" + role +
                ", userStatus=" + userStatus +
                ", updateTime=" + updateTime +
                "}";
    }
}
