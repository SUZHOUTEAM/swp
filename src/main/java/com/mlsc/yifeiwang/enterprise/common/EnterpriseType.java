package com.mlsc.yifeiwang.enterprise.common;

/**
 * Created by zhanghj on 2017/8/22.
 */
public enum EnterpriseType {

    DISPOSITION("DISPOSITION","处置企业"), PRODUCTION("PRODUCTION","产废企业"),RECYCLING("RECYCLING","综合利用企业"),IDENTIFICATION
            ("IDENTIFICATION","鉴定机构"), TRANSPORTATION("TRANSPORTATION","运输企业"),FACILITATOR("FACILITATOR","服务商");

    private String code;

    private String name;

    EnterpriseType(String code,String name){
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
