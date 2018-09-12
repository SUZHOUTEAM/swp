package com.mlsc.waste.user.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;

/**
 * Created by zhanghj on 2017/7/13.
 */
@Table(SysUserAuth.TABLE)
public class SysUserAuth extends Entity{

    private static final long serialVersionUID = -8542893450825512847L;

    public static final String TABLE = "sys_user_auth";
    public static final String COL_USERID = "USERID";
    public static final String COL_CHINESENAME = "CHINESENAME";
    public static final String COL_LOGINNAME = "LOGINNAME";
    public static final String COL_PHONENUM = "PHONENUM";
    public static final String COL_EMAILADDRESS = "EMAILADDRESS";
    public static final String COL_PASSWORD = "PASSWORD";
    public static final String COL_AUTHMODE = "AUTHMODE";
    public static final String COL_IPADDRESS = "IPADDRESS";
    public static final String COL_DEVICENUM = "DEVICENUM";
    public static final String COL_AUTHSTATUS = "AUTHSTATUS";
    public static final String COL_CREATERID = "CREATERID";
    public static final String COL_CREATETIME = "CREATETIME";
    public static final String COL_UPDATERID = "UPDATERID";
    public static final String COL_UPDATETIME = "UPDATETIME";
    @Column(COL_USERID)
    @Id
    private String userId;
    @Column(COL_CHINESENAME)
    private String chineseName;
    @Column(COL_LOGINNAME)
    private String loginName;
    @Column(COL_PHONENUM)
    private String phoneNum;
    @Column(COL_EMAILADDRESS)
    private String emailAddress;
    @Column(COL_PASSWORD)
    private String password;
    @Column(COL_AUTHMODE)
    private Integer authMode;
    @Column(COL_IPADDRESS)
    private String ipAddress;
    @Column(COL_DEVICENUM)
    private Integer deviceNum;
    @Column(COL_AUTHSTATUS)
    private Integer authStatus;
    @Column(COL_CREATERID)
    private String createrID;
    @Column(COL_CREATETIME)
    private String  createTime;
    @Column(COL_UPDATERID)
    private String updaterID;
    @Column(COL_UPDATETIME)
    private String updateTime;


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.userId = userId;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.loginName = loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.password = password;
    }

    public Integer getAuthMode() {
        return this.authMode;
    }

    public void setAuthMode(Integer authMode) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.authMode = authMode;
    }

    public Integer getDeviceNum() {
        return this.deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.deviceNum = deviceNum;
    }

    public Integer getAuthStatus() {
        return this.authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.authStatus = authStatus;
    }

    public String getCreaterID() {
        return this.createrID;
    }

    public void setCreaterID(String createrID) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.createrID = createrID;
    }

    public String getUpdaterID() {
        return this.updaterID;
    }

    public void setUpdaterID(String updaterID) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.updaterID = updaterID;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.phoneNum = phoneNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.emailAddress = emailAddress;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.ipAddress = ipAddress;
    }

    public String getChineseName() {
        return this.chineseName;
    }

    public void setChineseName(String chineseName) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.chineseName = chineseName;
    }
}
