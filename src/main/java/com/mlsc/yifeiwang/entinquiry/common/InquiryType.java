package com.mlsc.yifeiwang.entinquiry.common;

/**
 * Created by user on 2018/5/24.
 */
public enum InquiryType {
    REFERENCE_PRICE("REFERENCE_PRICE", "参考报价");

    private String code;
    private String name;

    InquiryType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(String code) {
        for (InquiryType e : InquiryType.values()) {
            if (e.code.equals(code)) {
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
