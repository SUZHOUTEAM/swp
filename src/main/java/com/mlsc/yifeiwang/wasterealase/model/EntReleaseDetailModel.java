package com.mlsc.yifeiwang.wasterealase.model;

import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;

/**
 * Created by user on 2017/9/14.
 */
public class EntReleaseDetailModel extends EntWasteModel {
    private String detailId; //发布明细id
    private double wasteAmount;//发布危废数量
    private boolean disposable;//是否可处置

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public double getWasteAmount() {
        return wasteAmount;
    }

    public void setWasteAmount(double wasteAmount) {
        this.wasteAmount = wasteAmount;
    }

    public boolean isDisposable() {
        return disposable;
    }

    public void setDisposable(boolean disposable) {
        this.disposable = disposable;
    }
}
