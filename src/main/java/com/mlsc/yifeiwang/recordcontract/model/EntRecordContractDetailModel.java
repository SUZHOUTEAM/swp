package com.mlsc.yifeiwang.recordcontract.model;

import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContractDetail;

/**
 * Created by user on 2018/3/2.
 */
public class EntRecordContractDetailModel extends EntRecordContractDetail {

    private String wasteName; //危废名称
    private String wasteCode; //8位码
    private String amount; //总量
    private String unitValue; // 单位
    private Double wasteTotalPrice; // 总价
    private Double orderWasteTotalPrice;//建议价格
    private String priceStr;
    private String wasteTotalPriceStr;

    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public String getWasteCode() {
        return wasteCode;
    }

    public void setWasteCode(String wasteCode) {
        this.wasteCode = wasteCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public double getWasteTotalPrice() {
        return wasteTotalPrice;
    }

    public void setWasteTotalPrice(double wasteTotalPrice) {
        this.wasteTotalPrice = wasteTotalPrice;
    }

    public void setWasteTotalPrice(Double wasteTotalPrice) {
        this.wasteTotalPrice = wasteTotalPrice;
    }

    public Double getOrderWasteTotalPrice() {
        return orderWasteTotalPrice;
    }

    public void setOrderWasteTotalPrice(Double orderWasteTotalPrice) {
        this.orderWasteTotalPrice = orderWasteTotalPrice;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getWasteTotalPriceStr() {
        return wasteTotalPriceStr;
    }

    public void setWasteTotalPriceStr(String wasteTotalPriceStr) {
        this.wasteTotalPriceStr = wasteTotalPriceStr;
    }
}
