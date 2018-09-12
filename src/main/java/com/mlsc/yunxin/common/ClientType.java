package com.mlsc.yunxin.common;

/**
 * Created by yinxl on 2017/7/12.
 */
public enum ClientType {
    AOS(1,"AOS"),IOS(2,"IOS"),WEB(3,"WEB");

    private int value;
    private String name;

    ClientType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ClientType getClientType(String name){
        ClientType type = null;
        for (ClientType ct:ClientType.values()) {
            if(ct.getName().equalsIgnoreCase(name)){
                type = ct;
                break;
            }
        }
        return type;
    }
}
