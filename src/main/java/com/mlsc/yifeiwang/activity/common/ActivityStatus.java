package com.mlsc.yifeiwang.activity.common;

/**
 * 活动状态 DRAFT("0") 待发布, ACTIVE("1") 发布, OVER("2") 结束, PAUSE("4") 暂停,STOP("5") 终止
 * Created by yinxl on 2017/8/1.
 */
public enum  ActivityStatus {

    DRAFT("0"), ACTIVE("1"), OVER("2"),PAUSE("4"),STOP("5");
    private String value;

    ActivityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
