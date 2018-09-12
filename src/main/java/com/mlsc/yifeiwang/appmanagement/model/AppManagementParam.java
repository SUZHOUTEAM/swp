package com.mlsc.yifeiwang.appmanagement.model;

import com.mlsc.yifeiwang.appmanagement.entity.AppManagement;

/**
 * Created by user on 2017/12/11.
 */
public class AppManagementParam extends AppManagement {
    private int startRowIndex;
    private int rows;

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
