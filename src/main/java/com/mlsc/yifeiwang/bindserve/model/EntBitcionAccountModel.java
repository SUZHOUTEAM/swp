package com.mlsc.yifeiwang.bindserve.model;

import com.mlsc.yifeiwang.bindserve.entity.EntBitcionAccount;

import java.util.List;

/**
 * Created by user on 2018/4/26.
 */
public class EntBitcionAccountModel extends EntBitcionAccount {
    private List<String> errorList; //错误消息


    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
}
