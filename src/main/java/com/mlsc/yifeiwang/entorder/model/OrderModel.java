package com.mlsc.yifeiwang.entorder.model;

import com.mlsc.yifeiwang.discusstag.model.DiscussTagParam;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 2017/9/26.
 */
public class OrderModel {
    private String id; //订单编号
    private String inquiryId; //询价主表Id
    private String releaseEntId;//发布企业id
    private String releaseId; //发布id
    private String releaseEntName; //发布企业名称
    private String inquiryEntId;//询价企业id
    private String inquiryEntName; //询价企业名称
    private String inquiryEntType; //询价企业类型
    private String facilitatorEntId; //服务商企业id
    private String inquiryPersonName; //询价人
    private String disEntName; //处置企业名称
    private String disEntId; // 处置企业id
    private String quotedType; // 询价方式
    private String quotedTypeValue;
    private String totalAmount; // 总量
    private double totalPrice; // 总价
    private String totalPriceStr; // 总价
    private String inquiryRemark; // 询价备注
    private String busiStatus; // 订单状态
    private String orderContractStatus; //合同状态
    private int orderWasteCount;//订单危废种类
    private Date orderDate; //成交时间
    private List<OrderDetailModel> orderDetail;//询价详细
    private boolean evaluated; //是否评价
    private int queryCount; //查询总数
    private String activityName; //来源活动名称
    private String activityId; //来源活动id
    private String recordContractId; //备案id
    private DiscussTagParam tagInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getReleaseEntId() {
        return releaseEntId;
    }

    public void setReleaseEntId(String releaseEntId) {
        this.releaseEntId = releaseEntId;
    }

    public String getReleaseEntName() {
        return releaseEntName;
    }

    public void setReleaseEntName(String releaseEntName) {
        this.releaseEntName = releaseEntName;
    }

    public String getInquiryEntId() {
        return inquiryEntId;
    }

    public void setInquiryEntId(String inquiryEntId) {
        this.inquiryEntId = inquiryEntId;
    }

    public String getInquiryEntName() {
        return inquiryEntName;
    }

    public void setInquiryEntName(String inquiryEntName) {
        this.inquiryEntName = inquiryEntName;
    }

    public String getInquiryEntType() {
        return inquiryEntType;
    }

    public void setInquiryEntType(String inquiryEntType) {
        this.inquiryEntType = inquiryEntType;
    }

    public String getFacilitatorEntId() {
        return facilitatorEntId;
    }

    public void setFacilitatorEntId(String facilitatorEntId) {
        this.facilitatorEntId = facilitatorEntId;
    }

    public String getInquiryPersonName() {
        return inquiryPersonName;
    }

    public void setInquiryPersonName(String inquiryPersonName) {
        this.inquiryPersonName = inquiryPersonName;
    }

    public String getDisEntName() {
        return disEntName;
    }

    public void setDisEntName(String disEntName) {
        this.disEntName = disEntName;
    }

    public String getDisEntId() {
        return disEntId;
    }

    public void setDisEntId(String disEntId) {
        this.disEntId = disEntId;
    }

    public String getQuotedType() {
        return quotedType;
    }

    public void setQuotedType(String quotedType) {
        this.quotedType = quotedType;
    }

    public String getQuotedTypeValue() {
        return quotedTypeValue;
    }

    public void setQuotedTypeValue(String quotedTypeValue) {
        this.quotedTypeValue = quotedTypeValue;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPriceStr() {
        return totalPriceStr;
    }

    public void setTotalPriceStr(String totalPriceStr) {
        this.totalPriceStr = totalPriceStr;
    }

    public String getInquiryRemark() {
        return inquiryRemark;
    }

    public void setInquiryRemark(String inquiryRemark) {
        this.inquiryRemark = inquiryRemark;
    }

    public String getBusiStatus() {
        return busiStatus;
    }

    public void setBusiStatus(String busiStatus) {
        this.busiStatus = busiStatus;
    }

    public String getOrderContractStatus() {
        return orderContractStatus;
    }

    public void setOrderContractStatus(String orderContractStatus) {
        this.orderContractStatus = orderContractStatus;
    }

    public int getOrderWasteCount() {
        return orderWasteCount;
    }

    public void setOrderWasteCount(int orderWasteCount) {
        this.orderWasteCount = orderWasteCount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetailModel> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailModel> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getRecordContractId() {
        return recordContractId;
    }

    public void setRecordContractId(String recordContractId) {
        this.recordContractId = recordContractId;
    }

    public DiscussTagParam getTagInfo() {
        return tagInfo;
    }

    public void setTagInfo(DiscussTagParam tagInfo) {
        this.tagInfo = tagInfo;
    }
}
