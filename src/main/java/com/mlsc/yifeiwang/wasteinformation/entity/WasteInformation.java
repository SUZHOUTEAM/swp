package com.mlsc.yifeiwang.wasteinformation.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author caoyy
 * @since 2018-06-13
 */
@TableName("waste_information")
public class WasteInformation extends Model<WasteInformation> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String context;
    private String text;
    private String fileId;
    private Integer clickAmount;
    private String videoFileId;
    private String type;
    /**
     * 业务状态
     */
    private String busiStatus;

    private String keyword;
    private String htmlSrc;
    private String htmlTitle;
    private String desc;


    private String createBy;
    private Date createTime;
    private String editBy;
    private Date editTime;
    private Integer deleteFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getClickAmount() {
        return clickAmount;
    }

    public void setClickAmount(Integer clickAmount) {
        this.clickAmount = clickAmount;
    }

    public String getVideoFileId() {
        return videoFileId;
    }

    public void setVideoFileId(String videoFileId) {
        this.videoFileId = videoFileId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusiStatus() {
        return busiStatus;
    }

    public void setBusiStatus(String busiStatus) {
        this.busiStatus = busiStatus;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getHtmlSrc() {
        return htmlSrc;
    }

    public void setHtmlSrc(String htmlSrc) {
        this.htmlSrc = htmlSrc;
    }

    public String getHtmlTitle() {
        return htmlTitle;
    }

    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WasteInformation{" +
                "id=" + id +
                ", title=" + title +
                ", context=" + context +
                ", fileId=" + fileId +
                ", clickAmount=" + clickAmount +
                ", videoFileId=" + videoFileId +
                ", type=" + type +
                ", busiStatus=" + busiStatus +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", editBy=" + editBy +
                ", editTime=" + editTime +
                ", deleteFlag=" + deleteFlag +
                "}";
    }
}
