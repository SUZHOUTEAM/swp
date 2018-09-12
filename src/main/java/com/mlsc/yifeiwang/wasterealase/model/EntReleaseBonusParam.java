package com.mlsc.yifeiwang.wasterealase.model;

import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseBonus;

/**
 * Created by user on 2018/4/10.
 */
public class EntReleaseBonusParam extends EntReleaseBonus {
    private String entName;
    private String bonusStatus;
    private int startRowIndex;//第几页
    private int rows;//每页多少行

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }


    public String getBonusStatus() {
        return bonusStatus;
    }

    public void setBonusStatus(String bonusStatus) {
        this.bonusStatus = bonusStatus;
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
