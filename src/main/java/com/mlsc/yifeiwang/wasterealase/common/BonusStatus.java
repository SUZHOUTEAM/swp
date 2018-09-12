package com.mlsc.yifeiwang.wasterealase.common;

/**
 * Created by user on 2018/4/9.
 */
public enum BonusStatus {
    SUBMIT("SUBMIT", "已发布"), SEND("SEND", "已发送红包验证码"), RECEIVED("RECEIVED", "已发送红包");

    private String code;

    private String name;

    BonusStatus(String code, String name) {
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
        for (BonusStatus e : BonusStatus.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (BonusStatus e : BonusStatus.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
