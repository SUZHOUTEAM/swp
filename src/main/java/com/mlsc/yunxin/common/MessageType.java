package com.mlsc.yunxin.common;

/**
 * Created by yinxl on 2017/7/14.
 */
public enum MessageType {
    PERSON("二人会话数据"),TEAM("群聊数据"),
    CUSTOM_PERSON("个人自定义系统通知"),CUSTOM_TEAM("群组自定义系统通知");
    private String desc;

    MessageType(String desc) {
        this.desc = desc;
    }
}
