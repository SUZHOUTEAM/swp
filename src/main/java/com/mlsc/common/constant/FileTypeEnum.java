package com.mlsc.common.constant;


/**
 * 文件类型枚举类
 */
public enum FileTypeEnum {

    ENT_LOGO("a", "企业Logo"), ENT_BUS_LICENSE("b", "企业营业执照"), USER_HEAD_THUMBNAIL("c", "用户头像"), LICENSE("d", "许可证"),
    WASTE_SHEET("e", "危废化验单"), WASTE_IMG("f", "危废图片"),ENT_IMG("g", "企业图片"),ENT_ATT("h", "企业附件"),ENT_CON("i", "企业合同"),
    ENT_BANNER("banner", "企业banner");

    private String code;

    private String name;

    FileTypeEnum(String code, String name) {
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
        for (FileTypeEnum e : FileTypeEnum.values()) {
            if (e.name.equals(name)) {
                return e.code;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (FileTypeEnum e : FileTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return null;
    }
}
