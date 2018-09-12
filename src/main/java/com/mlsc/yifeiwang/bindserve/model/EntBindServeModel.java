package com.mlsc.yifeiwang.bindserve.model;

import com.mlsc.yifeiwang.bindserve.entity.EntBindServe;

import java.util.List;

/**
 * Created by user on 2018/4/26.
 */
public class EntBindServeModel extends EntBindServe {
    private List<String> errorList; //错误消息
    private int remainBitCion; //剩余易废豆
    private String bindEntName;
    private String userName;
    private String PhoneNum;

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public int getRemainBitCion() {
        return remainBitCion;
    }

    public void setRemainBitCion(int remainBitCion) {
        this.remainBitCion = remainBitCion;
    }

    public String getBindEntName() {
        return bindEntName;
    }

    public void setBindEntName(String bindEntName) {
        this.bindEntName = bindEntName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }
}
