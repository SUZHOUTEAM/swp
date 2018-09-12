package com.mlsc.yifeiwang.bindserve.common;

/**
 * Created by user on 2018/5/2.
 */
public enum PayStatus {
    NONEPAYMENT("NONEPAYMENT", "未付款"), FREE("FREE", "免费"), PAYMENT("PAYMENT", "已付款");
    private String code;
    private String name;

    PayStatus(String code, String name) {
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
        for (PayStatus e : PayStatus.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (PayStatus e : PayStatus.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
