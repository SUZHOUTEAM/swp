package com.mlsc.yifeiwang.wasterealase.common;

/**
 * Created by user on 2018/4/26.
 */
public enum ChargeErrorCode {
    INSUFFICIENT ("INSUFFICIENT", "余额不足");
    private String code;

    private String name;

    ChargeErrorCode(String code, String name) {
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
        for (ChargeErrorCode e : ChargeErrorCode.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (ChargeErrorCode e : ChargeErrorCode.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
