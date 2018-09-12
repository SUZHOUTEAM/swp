package com.mlsc.yifeiwang.entorder.model;

/**
 * Created by user on 2017/9/26.
 */
public class OrderDetailModel {
    private String id ; // 主表
    private String orderId; //询价主表Id
    private String wasteName; //危废名称
    private String wasteCode; //8位码
    private String amount; //总量
    private String unitValue; // 单位
    private double price; // 单价
    private double wasteTotalPrice; // 总价
    private String priceStr;
    private String wasteTotalPriceStr;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price!=0.0) {
            this.setPriceStr(String.format("%1$.3f", price));
        }
        this.price = price;
    }

    public double getWasteTotalPrice() {
        return wasteTotalPrice;
    }

    public void setWasteTotalPrice(double wasteTotalPrice) {
        if(wasteTotalPrice!=0.0) {
            this.setWasteTotalPriceStr(String.format("%1$.3f", wasteTotalPrice));
        }
        this.wasteTotalPrice = wasteTotalPrice;
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
