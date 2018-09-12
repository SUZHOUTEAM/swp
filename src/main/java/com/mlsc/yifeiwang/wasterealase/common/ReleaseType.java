package com.mlsc.yifeiwang.wasterealase.common;

/**
 * Created by user on 2017/9/13.
 */
public enum ReleaseType {
    DISPOSITION_CAPACITY_RELEASE("DispositionCapacityRelease", "处置企业发布"), DISPOSITION_PLAN_RELEASE("DispositionPlanRelease", "产废企业发布");
    private String code;

    private String name;

    ReleaseType(String code, String name) {
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
