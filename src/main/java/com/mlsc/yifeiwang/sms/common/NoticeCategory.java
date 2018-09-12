package com.mlsc.yifeiwang.sms.common;

/**
 * Created by zhanghj on 2017/7/25.
 */
public enum NoticeCategory {

    PURCHASE_STATUS("PURCHASE_STATUS","购买动态"),NEW_ORDER("NEW_ORDER","新的订单"),NEW_RESOURCELIST("NEW_RESOURCELIST",
            "新的资源单"),SYS_TYPE("SYS_TYPE","系统消息"),ACTIVITY_STATUS("ACTIVITY_STATUS","活动动态");

    private String code;

    private String name;

    NoticeCategory(String code, String name){
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
