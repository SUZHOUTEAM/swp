package com.mlsc.yunxin.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author yxl
 * @since 2017-07-12
 */
@TableName("yunxin_onlinestatus")
public class Onlinestatus extends Model<Onlinestatus> {

    private static final long serialVersionUID = 1L;

    /**
     * 云信ID
     */
    @TableId
    private String accid;
    /**
     * 在线状态 1 在线 0 离线
     */
    private Integer clientIosStatus;
    /**
     * 在线状态 1 在线 0 离线
     */
    private Integer clientAosStatus;
    /**
     * 在线状态 1 在线 0 离线
     */
    private Integer clientWebStatus;
    /**
     * 客户端最后操作时间
     */
    private Timestamp lastTime;
    /**
     * 最后手机提醒时间
     */
    private String lastWarnTime;
    private String lastClientIp;

    public Onlinestatus() {
    }

    public Onlinestatus(Integer clientIosStatus, Integer clientAosStatus, Integer clientWebStatus) {
        this.clientIosStatus = clientIosStatus;
        this.clientAosStatus = clientAosStatus;
        this.clientWebStatus = clientWebStatus;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public Integer getClientIosStatus() {
        return clientIosStatus;
    }

    public void setClientIosStatus(Integer clientIosStatus) {
        this.clientIosStatus = clientIosStatus;
    }

    public Integer getClientAosStatus() {
        return clientAosStatus;
    }

    public void setClientAosStatus(Integer clientAosStatus) {
        this.clientAosStatus = clientAosStatus;
    }

    public Integer getClientWebStatus() {
        return clientWebStatus;
    }

    public void setClientWebStatus(Integer clientWebStatus) {
        this.clientWebStatus = clientWebStatus;
    }

    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastWarnTime() {
        return lastWarnTime;
    }

    public void setLastWarnTime(String lastWarnTime) {
        this.lastWarnTime = lastWarnTime;
    }

    public String getLastClientIp() {
        return lastClientIp;
    }

    public void setLastClientIp(String lastClientIp) {
        this.lastClientIp = lastClientIp;
    }

    @Override
    protected Serializable pkVal() {
        return this.accid;
    }

    @Override
    public String toString() {
        return "Onlinestatus{" +
                "accid='" + accid + '\'' +
                ", clientIosStatus=" + clientIosStatus +
                ", clientAosStatus=" + clientAosStatus +
                ", clientWebStatus=" + clientWebStatus +
                ", lastTime=" + lastTime +
                ", lastWarnTime='" + lastWarnTime + '\'' +
                ", lastClientIp='" + lastClientIp + '\'' +
                '}';
    }
}
