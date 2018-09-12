package com.mlsc.yifeiwang.entinquiry.common;

/**
 * Created by user on 2018/5/16.
 */
public enum InquiryPriorityEnum {

    URGENT("URGENT", "紧急"), HIGH("HIGH", "高"), GENERAL("GENERAL", "一般"), NONE("NONE", "暂不处理");

    private String code;

    private String name;

    InquiryPriorityEnum(String code, String name) {
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

    public static String getCodeByName(String name) {
        for (InquiryPriorityEnum e : InquiryPriorityEnum.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (InquiryPriorityEnum e : InquiryPriorityEnum.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
