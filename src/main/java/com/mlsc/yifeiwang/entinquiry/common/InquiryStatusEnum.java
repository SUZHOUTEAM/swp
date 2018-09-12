package com.mlsc.yifeiwang.entinquiry.common;

/**
 * Created by user on 2017/9/13.
 */
public enum InquiryStatusEnum {

    ACCEPT("ACCEPT", "接受报价"), CLOSED("CLOSED", "已关闭"), PROCESSED("PROCESSED", "已被其他处置企业确认"),
    REFUSED("REFUSED", "已谢绝"), SUBMIT("SUBMIT", "待确认"),CANCEL("CANCEL","已取消");

    private String code;
    private String name;

    InquiryStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static String getNameByCode(String code){
        for (InquiryStatusEnum e : InquiryStatusEnum.values()) {
            if (e.code.equals(code) ) {
                return e.name;
            }
        }
        return null;
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
