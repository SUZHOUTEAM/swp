package com.mlsc.yifeiwang.activity.model;

import com.mlsc.yifeiwang.activity.entity.WasteActivityContacts;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanghj on 2017/8/1.
 */
public class ActivityContactsQueryParam implements Serializable {

    private static final long serialVersionUID = -744227371089456949L;

    private String entId;

    private List<String> cantonCodeList;

    private String activityId;

    private String entTypeCode;

    private String status;

    private String industry;

    private String cantonCode;

    private List<WasteActivityContacts> wasteActivityContactsList;

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    public List<String> getCantonCodeList() {
        return cantonCodeList;
    }

    public void setCantonCodeList(List<String> cantonCodeList) {
        this.cantonCodeList = cantonCodeList;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getEntTypeCode() {
        return entTypeCode;
    }

    public void setEntTypeCode(String entTypeCode) {
        this.entTypeCode = entTypeCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    public List<WasteActivityContacts> getWasteActivityContactsList() {
        return wasteActivityContactsList;
    }

    public void setWasteActivityContactsList(List<WasteActivityContacts> wasteActivityContactsList) {
        this.wasteActivityContactsList = wasteActivityContactsList;
    }
}
