package com.mlsc.waste.fw.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanghj on 2017/8/1.
 */
public class SysCantonQueryParam implements Serializable{

    private static final long serialVersionUID = -7553567253892652481L;

    private String parentCantonCode;

    private List<String> parentCantonCodeList;

    private String cantonCode;

    public String getParentCantonCode() {
        return parentCantonCode;
    }

    public void setParentCantonCode(String parentCantonCode) {
        this.parentCantonCode = parentCantonCode;
    }

    public List<String> getParentCantonCodeList() {
        return parentCantonCodeList;
    }

    public void setParentCantonCodeList(List<String> parentCantonCodeList) {
        this.parentCantonCodeList = parentCantonCodeList;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }
}
