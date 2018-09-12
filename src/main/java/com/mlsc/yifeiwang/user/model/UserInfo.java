package com.mlsc.yifeiwang.user.model;

/**
 * Created by yinxl on 2017/7/19.
 */
public class UserInfo implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3695205305128837154L;
    public String userId; // required
    private String userExtendsId;
    private String userName;
    public String phoneNum; // required
    public int userStatus; // required
    private String registrationCode;
    private String password;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }


    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserExtendsId() {
        return userExtendsId;
    }

    public void setUserExtendsId(String userExtendsId) {
        this.userExtendsId = userExtendsId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
