package com.mlsc.yifeiwang.discusstag.common;

/**
 * Created by user on 2017/11/13.
 */
public enum TagStatusEnum {

    TAGGING("TAGGING", "自由标记"), SAMPLING("SAMPLING", "取样中"), SAMPLED("SAMPLED", "已取样"),DELIVERED_LAB("DELIVERED_LAB", "已送实验室"), QUALIFIED("QUALIFIED", "合格"),
    UNQUALIFIED("UNQUALIFIED", "不合格"),DRAFTCONTRACT("DRAFTCONTRACT", "合同已拟定"),SIGNCONTRACT("SIGNCONTRACT", "合同已签定"),DELIVERYCONTRACT("DELIVERYCONTRACT", "合同已寄出"), TERMINATE("TERMINATE", "终止"), PRICE("PRICE", "报价");

    private String code;

    private String name;

    TagStatusEnum(String code, String name) {
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
        for (TagStatusEnum e : TagStatusEnum.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (TagStatusEnum e : TagStatusEnum.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
