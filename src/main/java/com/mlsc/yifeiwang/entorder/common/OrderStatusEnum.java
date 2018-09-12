package com.mlsc.yifeiwang.entorder.common;

/**
 * Created by user on 2017/9/13.
 */
public enum OrderStatusEnum {

    ONGOING("ONGOING", "进行中"), DONE("DONE", "已完成");

    private String code;
    private String name;

    OrderStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static String getNameByCode(String code){
        for (OrderStatusEnum e : OrderStatusEnum.values()) {
            if (e.code.equals(code) ) {
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
