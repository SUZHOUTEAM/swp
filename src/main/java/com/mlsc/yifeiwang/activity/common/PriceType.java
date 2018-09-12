package com.mlsc.yifeiwang.activity.common;

/**
 * 活动价格类型 1 一口价  2 区间定价
 * Created by yinxl on 2017/7/31.
 *
 */
public enum PriceType {
    SINGLE("1"),RANGE("2");
    private String value;

    PriceType(String value) {
        this.value = value;
    }

    public PriceType getPriceType(String value){
        for (PriceType priceType : PriceType.values()){
            if(priceType.getValue().equalsIgnoreCase(value)){
                return priceType;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
