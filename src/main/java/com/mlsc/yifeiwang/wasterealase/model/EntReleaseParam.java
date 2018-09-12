package com.mlsc.yifeiwang.wasterealase.model;

import com.mlsc.common.constant.BaseConstant;
import com.mlsc.yifeiwang.wasterealase.common.ReleaseStatus;
import com.mlsc.yifeiwang.wasterealase.entity.EntRelease;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2017/9/14.
 */
public class EntReleaseParam extends EntRelease {
    private String currentEntId; //当前登录用户所属企业id
    private String currentUserId;
    private String currentEntType;
    private String releaseEntName;
    private String entReleaseId;
    private String entName; //查询参数：企业名称
    private String wasteName; //危废名称
    private String wateCode; //8位码
    private String area; //区域
    private String licenceId; //处置企业有效许可证id
    private String cantonCode;//处置企业所在省份
    private Integer startRowIndex;//第几页
    private Integer rows;//每页多少行
    private String releaseId;//危废企业发布id
    private String outsideCantonCode;//省外
    private double amountInterval;
    private String releaseEntId; //发布企业id
    private final double infinity = BaseConstant.DISTANCE_INFINITY;
    private final String releaseStatus = ReleaseStatus.RELEASED.getCode();
    private List<EntReleaseDetailParam> releaseDetail = new ArrayList<EntReleaseDetailParam>();
    private List<String> wasteCodeList; //排除8位码
    private List<String> cantonCodeList; //区域
    private List<String> amountIntervalStr; //重量
    private List<AmountInterval> amountIntervalList;
    private Boolean favorited; //我的收藏
    private String status;
    private String releaseEntContactTel;
    private String dateType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDateTime; //起始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDateTime; // 结束日期
    private String inquiryStatus;//询价状态
    private Boolean binding;//绑定状态 false:未绑定 true:已绑定

    private String keyword; //关键字

    public String getCurrentEntId() {
        return currentEntId;
    }

    public void setCurrentEntId(String currentEntId) {
        this.currentEntId = currentEntId;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCurrentEntType() {
        return currentEntType;
    }

    public void setCurrentEntType(String currentEntType) {
        this.currentEntType = currentEntType;
    }

    public String getReleaseEntName() {
        return releaseEntName;
    }

    public void setReleaseEntName(String releaseEntName) {
        this.releaseEntName = releaseEntName;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public String getWateCode() {
        return wateCode;
    }

    public void setWateCode(String wateCode) {
        this.wateCode = wateCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getInfinity() {
        return infinity;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public String getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(String licenceId) {
        this.licenceId = licenceId;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    public Integer getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(Integer startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public List<EntReleaseDetailParam> getReleaseDetail() {
        return releaseDetail;
    }

    public void setReleaseDetail(List<EntReleaseDetailParam> releaseDetail) {
        this.releaseDetail = releaseDetail;
    }

    public String getOutsideCantonCode() {
        return outsideCantonCode;
    }

    public void setOutsideCantonCode(String outsideCantonCode) {
        this.outsideCantonCode = outsideCantonCode;
    }

    public double getAmountInterval() {
        return amountInterval;
    }

    public void setAmountInterval(double amountInterval) {
        this.amountInterval = amountInterval;
    }

    public List<String> getWasteCodeList() {
        return wasteCodeList;
    }

    public void setWasteCodeList(List<String> wasteCodeList) {
        this.wasteCodeList = wasteCodeList;
    }

    public List<String> getCantonCodeList() {
        return cantonCodeList;
    }

    public void setCantonCodeList(List<String> cantonCodeList) {
        this.cantonCodeList = cantonCodeList;
    }

    public List<String> getAmountIntervalStr() {
        return amountIntervalStr;
    }

    public void setAmountIntervalStr(List<String> amountIntervalStr) {
        this.amountIntervalStr = amountIntervalStr;
    }

    public List<AmountInterval> getAmountIntervalList() {
        return amountIntervalList;
    }

    public void setAmountIntervalList(List<AmountInterval> amountIntervalList) {
        this.amountIntervalList = amountIntervalList;
    }

    public String getReleaseEntId() {
        return releaseEntId;
    }

    public void setReleaseEntId(String releaseEntId) {
        this.releaseEntId = releaseEntId;
    }

    public Boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getEntReleaseId() {
        return entReleaseId;
    }

    public void setEntReleaseId(String entReleaseId) {
        this.entReleaseId = entReleaseId;
    }

    public String getReleaseEntContactTel() {
        return releaseEntContactTel;
    }

    public void setReleaseEntContactTel(String releaseEntContactTel) {
        this.releaseEntContactTel = releaseEntContactTel;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getInquiryStatus() {
        return inquiryStatus;
    }

    public void setInquiryStatus(String inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }

    public Boolean isBinding() {
        return binding;
    }

    public void setBinding(Boolean binding) {
        this.binding = binding;
    }
}
