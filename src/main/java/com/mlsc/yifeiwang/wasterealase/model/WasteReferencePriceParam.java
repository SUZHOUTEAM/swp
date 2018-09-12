package com.mlsc.yifeiwang.wasterealase.model;


import java.util.List;

/**
 * Created by user on 2018/5/24.
 */
public class WasteReferencePriceParam {
    private String id;
    private String cantonCode;
    private String wasteCode;
    private String wasteName;
    private Double price;
    private String priceId;
    private String dispositionType;
    private Double amount;
    private String keyword;
    private List<String> wasteCodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    public String getWasteCode() {
        return wasteCode;
    }

    public void setWasteCode(String wasteCode) {
        this.wasteCode = wasteCode;
    }

    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getDispositionType() {
        return dispositionType;
    }

    public void setDispositionType(String dispositionType) {
        this.dispositionType = dispositionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getWasteCodes() {
        return wasteCodes;
    }

    public void setWasteCodes(List<String> wasteCodes) {
        this.wasteCodes = wasteCodes;
    }
}
