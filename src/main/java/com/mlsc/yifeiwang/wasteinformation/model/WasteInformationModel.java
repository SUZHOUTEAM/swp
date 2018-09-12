package com.mlsc.yifeiwang.wasteinformation.model;

import com.mlsc.yifeiwang.wasteinformation.entity.WasteInformation;

/**
 * Created by user on 2018/6/13.
 */
public class WasteInformationModel extends WasteInformation {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
