package com.mlsc.yifeiwang.codedirectory.model;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;

/**
 * Created by yinxl on 2017/7/22.
 */
public class CodeValueVo extends CodeValue {
    private String isChecked;//是否选中

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

}
