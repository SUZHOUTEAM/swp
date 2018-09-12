package com.mlsc.yifeiwang.useraction.model;

import com.mlsc.yifeiwang.useraction.entity.UserAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by user on 2018/1/3.
 */
public class UserActionParam extends UserAction {
    private String actionTicketId;
    private String DateType;
    private String userName;
    private String entType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDateTime; //起始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDateTime; // 结束日期
    private String cip;
    private int startRowIndex;
    private int rows;


    public String getActionTicketId() {
        return actionTicketId;
    }

    public void setActionTicketId(String actionTicketId) {
        this.actionTicketId = actionTicketId;
    }

    public String getDateType() {
        return DateType;
    }

    public void setDateType(String dateType) {
        DateType = dateType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
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

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
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
