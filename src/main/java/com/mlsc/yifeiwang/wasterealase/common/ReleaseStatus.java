package com.mlsc.yifeiwang.wasterealase.common;

/**
 * Created by user on 2017/9/13.
 */
public enum ReleaseStatus {

    RELEASED("RELEASED", "已发布"), //产废企业发布状态或产废企业参加活动状态
    SUBMIT("SUBMIT", "待确认"),REFUSED("REFUSED","已谢绝"),//待确认是处置企业同产废企业参加活动并已询价，已谢绝是指处置企业谢绝产废企业的活动申请
    DONE("DONE", "交易完成"),
    END("END","交易完成");

    private String code;
    private String name;

    public static String getNameByCode(String code) {
        for (ReleaseStatus e : ReleaseStatus.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }

    ReleaseStatus(String code, String name) {
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
