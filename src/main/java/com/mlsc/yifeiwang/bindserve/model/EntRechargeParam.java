package com.mlsc.yifeiwang.bindserve.model;

import com.mlsc.yifeiwang.bindserve.entity.EntRecharge;

/**
 * Created by user on 2018/4/27.
 */
public class EntRechargeParam extends EntRecharge {
    private int startRowIndex;//第几页
    private int rows;//每页多少行

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
