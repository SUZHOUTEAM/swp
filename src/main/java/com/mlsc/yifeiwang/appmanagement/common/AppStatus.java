package com.mlsc.yifeiwang.appmanagement.common;

/**
 * Created by user on 2017/12/12.
 */
public enum AppStatus {
    SUBMIT("SUBMIT", "待发布"), RELEASE("RELEASE", "已发布");

    private String code;

    private String name;

    AppStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
