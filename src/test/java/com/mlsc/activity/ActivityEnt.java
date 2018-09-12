package com.mlsc.activity;

import com.mlsc.waste.enterprise.model.SysEnterpriseBase;

/**
 * Created by yinxl on 2017/7/31.
 */
public class ActivityEnt extends SysEnterpriseBase {

    public ActivityEnt(String entId, String entName) {
        this.setEntId(entId);
        this.setEntName(entName);
    }
}
