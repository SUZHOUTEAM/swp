package com.mlsc.waste.user.model;

/**
 * Created by zhanghj on 2017/7/13.
 */

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;

@Table(SysUser.TABLE)
public class SysUser extends Entity {

    private static final long serialVersionUID = -1432111612863209412L;

    public static final String TABLE = "sys_user";
    public static final String COL_USERID = "USERID";
    public static final String COL_PHONENUM = "PHONENUM";
    public static final String COL_LOGINNAME = "LOGINNAME";
    public static final String COL_USERTYPE = "USERTYPE";
    public static final String COL_CHINESENAME = "CHINESENAME";
    public static final String COL_PHOTOFILEFINGERPRINT = "PHOTOFILEFINGERPRINT";
    public static final String COL_GENDER = "GENDER";
    public static final String COL_BIRTHDAY = "BIRTHDAY";
    public static final String COL_EMAILADDRESS = "EMAILADDRESS";
    public static final String COL_USERSTATUS = "USERSTATUS";
    public static final String COL_CREATERID = "CREATERID";
    public static final String COL_CREATETIME = "CREATETIME";
    public static final String COL_UPDATERID = "UPDATERID";
    public static final String COL_UPDATETIME = "UPDATETIME";
    @Column(COL_USERID)
    @Id
    private String userId;
    @Column(COL_LOGINNAME)
    private String loginName;
    @Column(COL_USERTYPE)
    private Integer userType;
    @Column(COL_CHINESENAME)
    private String chineseName;
    @Column(COL_PHOTOFILEFINGERPRINT)
    private String photoFileFingerprint;
    @Column(COL_GENDER)
    private String gender;
    @Column(COL_BIRTHDAY)
    private String birthday;
    @Column(COL_PHONENUM)
    private String phoneNum;
    @Column(COL_EMAILADDRESS)
    private String emailAddress;
    @Column(COL_USERSTATUS)
    private Integer userStatus;
    @Column(COL_CREATERID)
    private String createrID;
    @Column(COL_CREATETIME)
    private String createTime;
    @Column(COL_UPDATERID)
    private String updaterID;
    @Column(COL_UPDATERID)
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

    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(Integer userType) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.userType = userType;
    }

    public String getChineseName() {
        return this.chineseName;
    }

    public void setChineseName(String chineseName) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.chineseName = chineseName;
    }

    public String getPhotoFileFingerprint() {
        return this.photoFileFingerprint;
    }

    public void setPhotoFileFingerprint(String photoFileFingerprint) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.photoFileFingerprint = photoFileFingerprint;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.gender = gender;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.birthday = birthday;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.phoneNum = phoneNum;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.emailAddress = emailAddress;
    }

    public String getCreaterID() {
        return this.createrID;
    }

    public void setCreaterID(String createrID) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.createrID = createrID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.createTime = createTime;
    }

    public String getUpdaterID() {
        return this.updaterID;
    }

    public void setUpdaterID(String updaterID) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.updaterID = updaterID;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.updateTime = updateTime;
    }

    public Integer getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.userStatus = userStatus;
    }
}
