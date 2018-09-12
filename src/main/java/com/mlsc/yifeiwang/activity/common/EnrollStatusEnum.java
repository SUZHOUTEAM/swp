package com.mlsc.yifeiwang.activity.common;

/**
 * Created by user on 2018/2/5.
 */
public enum EnrollStatusEnum {
    SUBMIT("SUBMIT", "等待支付"), PAYMENTSUCCESS("PAYMENTSUCCESS", "付款成功"), PAYMENTFAILED("PAYMENTFAILED", "付款失败");

    private String code;

    private String name;

    EnrollStatusEnum(String code, String name) {
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
        for (EnrollStatusEnum e : EnrollStatusEnum.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (EnrollStatusEnum e : EnrollStatusEnum.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
