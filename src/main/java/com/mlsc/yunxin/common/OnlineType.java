package com.mlsc.yunxin.common;

/**
 * Created by yinxl on 2017/7/12.
 */
public enum OnlineType{
    ONLINE(1,"在线"),OFFLINE(0,"离线");
    private int value;
    private String name;

    OnlineType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
