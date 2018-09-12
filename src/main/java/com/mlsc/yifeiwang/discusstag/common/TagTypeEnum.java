package com.mlsc.yifeiwang.discusstag.common;

/**
 * Created by user on 2017/11/13.
 */
public enum TagTypeEnum {
    TAGGING("TAGGING", "自由标记"), SAMPLING("SAMPLING", "取样中"), SAMPLE("SAMPLE", "已送实验室"), CONTRACT("CONTRACT", "合同"), TERMINATE("TERMINATE", "终止"), PRICE("PRICE", "报价");

    private String code;

    private String name;

    TagTypeEnum(String code, String name) {
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
        for (TagTypeEnum e : TagTypeEnum.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (TagTypeEnum e : TagTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
