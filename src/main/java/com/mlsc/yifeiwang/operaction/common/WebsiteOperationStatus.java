package com.mlsc.yifeiwang.operaction.common;

/**
 * Created by user on 2017/11/22.
 */
public enum WebsiteOperationStatus {
    SUBMIT("SUBMIT", "提交"), START("START", "开始"),RUNNING("RUNNING", "运行中"), STOP("STOP", "暂停");
    private String code;

    private String name;

    WebsiteOperationStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static String getCodeByName(String name) {
        for (WebsiteOperationStatus e : WebsiteOperationStatus.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (WebsiteOperationStatus e : WebsiteOperationStatus.values()) {
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
