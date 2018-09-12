package com.mlsc.yifeiwang.entorder.common;

/**
 * Created by user on 2018/2/28.
 */
public enum OrderContractStatus {
    WAITINGUPLOAD("WAITINGUPLOAD", "等待上传"), UPLOADED("UPLOADED", "已上传");
    private String code;
    private String name;

    public static String getNameByCode(String code) {
        for (OrderContractStatus e : OrderContractStatus.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }

    OrderContractStatus(String code, String name) {
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
