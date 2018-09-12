package com.mlsc.yifeiwang.enterprise.model;

import com.mlsc.yifeiwang.enterprise.entity.EntEvaluate;

/**
 * Created by user on 2018/5/7.
 */
public class EntEvaluateModel extends EntEvaluate {

    private String integratedPercentage;// 企业综合评价

    private int allValuationCount;// 全部评价的件数

    private int goodValuationCount;// 好评的件数

    private int middleValuationCount;// 中评的件数

    private int badValuationCount;// 差评的件数

    private int disposeTimelyValuationCount;// 处置及时的评价件数

    private int serveAttitudeValuationCount;// 服务态度好的评价件数

    private int efficientHighValuationCount;// 效率高的评价件数

    private String responseEnterpriseName;

    public String getIntegratedPercentage() {
        return integratedPercentage;
    }

    public void setIntegratedPercentage(String integratedPercentage) {
        this.integratedPercentage = integratedPercentage;
    }

    public int getAllValuationCount() {
        return allValuationCount;
    }

    public void setAllValuationCount(int allValuationCount) {
        this.allValuationCount = allValuationCount;
    }

    public int getGoodValuationCount() {
        return goodValuationCount;
    }

    public void setGoodValuationCount(int goodValuationCount) {
        this.goodValuationCount = goodValuationCount;
    }

    public int getMiddleValuationCount() {
        return middleValuationCount;
    }

    public void setMiddleValuationCount(int middleValuationCount) {
        this.middleValuationCount = middleValuationCount;
    }

    public int getBadValuationCount() {
        return badValuationCount;
    }

    public void setBadValuationCount(int badValuationCount) {
        this.badValuationCount = badValuationCount;
    }

    public int getDisposeTimelyValuationCount() {
        return disposeTimelyValuationCount;
    }

    public void setDisposeTimelyValuationCount(int disposeTimelyValuationCount) {
        this.disposeTimelyValuationCount = disposeTimelyValuationCount;
    }

    public int getServeAttitudeValuationCount() {
        return serveAttitudeValuationCount;
    }

    public void setServeAttitudeValuationCount(int serveAttitudeValuationCount) {
        this.serveAttitudeValuationCount = serveAttitudeValuationCount;
    }

    public int getEfficientHighValuationCount() {
        return efficientHighValuationCount;
    }

    public void setEfficientHighValuationCount(int efficientHighValuationCount) {
        this.efficientHighValuationCount = efficientHighValuationCount;
    }

    public String getResponseEnterpriseName() {
        return responseEnterpriseName;
    }

    public void setResponseEnterpriseName(String responseEnterpriseName) {
        this.responseEnterpriseName = responseEnterpriseName;
    }
}
