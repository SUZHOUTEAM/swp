package com.mlsc.yifeiwang.bindserve.common;

/**
 * Created by user on 2018/4/27.
 */
public enum RechargeStatus {
    SUBMIT("SUBMIT", "订单提交"), SUCCESS("SUCCESS", "已充值"), CANCEL("CANCEL", "取消订单");
    private String code;

    private String name;

    RechargeStatus(String code, String name) {
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
        for (RechargeStatus e : RechargeStatus.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (RechargeStatus e : RechargeStatus.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
