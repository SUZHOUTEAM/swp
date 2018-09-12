package com.mlsc.yifeiwang.bindserve.model;

import com.mlsc.yifeiwang.bindserve.entity.EntBindServe;


/**
 * Created by user on 2018/4/26.
 */
public class EntBindServeParam extends EntBindServe {
    private String releaseEntId;
    private Integer startRowIndex;
    private Integer rows;

    public String getReleaseEntId() {
        return releaseEntId;
    }

    public void setReleaseEntId(String releaseEntId) {
        this.releaseEntId = releaseEntId;
    }

    public Integer getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(Integer startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
