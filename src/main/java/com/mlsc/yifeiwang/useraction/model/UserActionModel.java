package com.mlsc.yifeiwang.useraction.model;

import com.mlsc.yifeiwang.useraction.entity.UserAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 2018/1/3.
 */
public class UserActionModel extends UserAction {
    private String entName;
    private String userName;
    private String userPhoneNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logoutTime;
    List<ActionTypeModel> actionTypeModels;

    private String userActionValue;

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public List<ActionTypeModel> getActionTypeModels() {
        return actionTypeModels;
    }

    public void setActionTypeModels(List<ActionTypeModel> actionTypeModels) {
        this.actionTypeModels = actionTypeModels;
    }

    public String getUserActionValue() {
        return userActionValue;
    }

    public void setUserActionValue(String userActionValue) {
        this.userActionValue = userActionValue;
    }
}
