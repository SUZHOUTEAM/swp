package com.mlsc.yifeiwang.wasterealase.common;

/**
 * Created by user on 2017/9/18.
 */
public enum QuotedTypeEnum {
    WHOLE_QUOTE(0, "打包报价"), SINGLE_QUOTE(1, "单价报价");

    private int code;

    private String name;

    QuotedTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(int code) {
        for (QuotedTypeEnum e : QuotedTypeEnum.values()) {
            if (e.code == code) {
                return e.name;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
