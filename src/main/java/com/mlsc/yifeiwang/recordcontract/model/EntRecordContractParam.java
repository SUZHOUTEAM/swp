package com.mlsc.yifeiwang.recordcontract.model;

import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContract;
import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContractDetail;
import java.util.List;

/**
 * Created by user on 2018/3/1.
 */
public class EntRecordContractParam extends EntRecordContract {
    private int startRowIndex;//第几页
    private int rows;//每页多少行
    private List<EntRecordContractDetail> entRecordContractDetailList;

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

    public List<EntRecordContractDetail> getEntRecordContractDetailList() {
        return entRecordContractDetailList;
    }

    public void setEntRecordContractDetailList(List<EntRecordContractDetail> entRecordContractDetailList) {
        this.entRecordContractDetailList = entRecordContractDetailList;
    }
}
