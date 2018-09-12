package com.mlsc.yifeiwang.codedirectory.model;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yinxl on 2017/7/20.
 */
public class CodeDirectory implements Serializable {
    private static final long serialVersionUID = 1L;

    private String typeCode;
    private String typeId;
    private List<CodeValue> codeValueList;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

}
