package com.mlsc.yifeiwang.wasterealase.model;

import com.mlsc.yifeiwang.wasterealase.entity.WasteReference;

/**
 * Created by user on 2018/5/24.
 */
public class WasteReferencePriceModel extends WasteReference {
    private String entWasteId;
    private Double amount;
    private String priceId;//价格id
    private Double price;

    public String getEntWasteId() {
        return entWasteId;
    }

    public void setEntWasteId(String entWasteId) {
        this.entWasteId = entWasteId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
