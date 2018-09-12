package com.mlsc.yifeiwang.recordcontract.common;

/**
 * Created by user on 2018/3/1.
 */
public enum RecordContractStatus {
    SUBMIT("SUBMIT", "待审核"), APPROVED("APPROVED", "审核通过"),REFUSED("REFUSED", "审核退回");


    private String code;
    private String name;

    public static String getNameByCode(String code) {
        for (RecordContractStatus e : RecordContractStatus.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }

    RecordContractStatus(String code, String name) {
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
