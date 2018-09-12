package com.mlsc.yifeiwang.calling.model;

/**
 * Created by yinxl on 2017/7/26.
 */
public class CallingVo {
    private String id;  //主键
    private String name;  //行业名称
    private String rootid; //根级行业代码

    private String code;
    private String calling_id;
    private String callingOriginal;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCalling_id() {
        return calling_id;
    }

    public void setCalling_id(String calling_id) {
        this.calling_id = calling_id;
    }

    public String getCallingOriginal() {
        return callingOriginal;
    }

    public void setCallingOriginal(String callingOriginal) {
        this.callingOriginal = callingOriginal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRootid() {
        return rootid;
    }

    public void setRootid(String rootid) {
        this.rootid = rootid;
    }
}
