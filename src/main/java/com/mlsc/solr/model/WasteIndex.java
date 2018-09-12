package com.mlsc.solr.model;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 危废索引
 */
public class WasteIndex {

    @Field
    private String id;

    @Field
    private String wasteCode;
    @Field
    private String wasteName;
    @Field
    private String wasteDesc;
    
    private String wasteTypeId;
    
    private String wasteCodeDisplay;
    
    private String wasteNameDisplay;
    
    private String wasteDescDisplay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getWasteDesc() {
        return wasteDesc;
    }

    public void setWasteDesc(String wasteDesc) {
        this.wasteDesc = wasteDesc;
    }

    public String getWasteTypeId() {
        return wasteTypeId;
    }

    public void setWasteTypeId(String wasteTypeId) {
        this.wasteTypeId = wasteTypeId;
    }

    public String getWasteCodeDisplay() {
        return wasteCodeDisplay;
    }

    public void setWasteCodeDisplay(String wasteCodeDisplay) {
        this.wasteCodeDisplay = wasteCodeDisplay;
    }

    public String getWasteNameDisplay() {
        return wasteNameDisplay;
    }

    public void setWasteNameDisplay(String wasteNameDisplay) {
        this.wasteNameDisplay = wasteNameDisplay;
    }

    public String getWasteDescDisplay() {
        return wasteDescDisplay;
    }

    public void setWasteDescDisplay(String wasteDescDisplay) {
        this.wasteDescDisplay = wasteDescDisplay;
    }
}
