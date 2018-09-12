package com.mlsc.yifeiwang.wasterealase.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author caoyy
 * @since 2018-05-24
 */
@TableName("waste_reference_disposition_type")
public class WasteReferenceDispositionType extends Model<WasteReferenceDispositionType> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("refer_id")
    private String referId;
    @TableField("price_id")
    private String priceId;
    @TableField("disposition_type")
    private String dispositionType;
    private String createBy;
    private Date createTime;
    private String editBy;
    private Date editTime;
    private String deleteFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferId() {
        return referId;
    }

    public void setReferId(String referId) {
        this.referId = referId;
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

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WasteReferenceDispositionType{" +
                "id=" + id +
                ", referId=" + referId +
                ", priceId=" + priceId +
                ", dispositionType=" + dispositionType +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", editBy=" + editBy +
                ", editTime=" + editTime +
                ", deleteFlag=" + deleteFlag +
                "}";
    }
}
