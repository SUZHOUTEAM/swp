package com.mlsc.yifeiwang.bindserve.common;

/**
 * Created by user on 2018/4/26.
 */
public enum EntServeType {
    RESOURCE_POOL("RESOURCE_POOL", "资源池"), ACTIVITY("ACTIVITY", "活动"), GOLDEN("GOLDEN","公爵套餐" ), SLIVER("SLIVER", "伯爵套餐"), CUPRUM("CUPRUM", "候爵套餐");
    private String code;

    private String name;

    EntServeType(String code, String name) {
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
        for (EntServeType e : EntServeType.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (EntServeType e : EntServeType.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
