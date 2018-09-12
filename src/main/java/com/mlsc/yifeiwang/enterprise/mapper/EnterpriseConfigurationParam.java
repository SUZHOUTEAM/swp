package com.mlsc.yifeiwang.enterprise.mapper;

import com.mlsc.yifeiwang.enterprise.entity.EnterpriseConfiguration;

/**
 * Created by user on 2017/12/25.
 */
public class EnterpriseConfigurationParam extends EnterpriseConfiguration {
    private String releaseEntId;
    private int startRowIndex;//第几页
    private int rows;//每页多少行

    public String getReleaseEntId() {
        return releaseEntId;
    }

    public void setReleaseEntId(String releaseEntId) {
        this.releaseEntId = releaseEntId;
    }

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
