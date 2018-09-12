package com.mlsc.yifeiwang.wasterealase.model;

import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseBonus;

/**
 * Created by user on 2018/4/10.
 */
public class EntReleaseBonusModel extends EntReleaseBonus {
    private String entName;
    private String userName;
    private String userPhone;

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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
