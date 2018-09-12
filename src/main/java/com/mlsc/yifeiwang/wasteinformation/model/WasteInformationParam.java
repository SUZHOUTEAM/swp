package com.mlsc.yifeiwang.wasteinformation.model;

import com.mlsc.yifeiwang.wasteinformation.entity.WasteInformation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by user on 2018/6/13.
 */
public class WasteInformationParam extends WasteInformation {
    private String latestNews;
    private String mostRead;
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDateTime; //起始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDateTime; // 结束日期

    private int startRowIndex;
    private int rows;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLatestNews() {
        return latestNews;
    }

    public void setLatestNews(String latestNews) {
        this.latestNews = latestNews;
    }

    public String getMostRead() {
        return mostRead;
    }

    public void setMostRead(String mostRead) {
        this.mostRead = mostRead;
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

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
