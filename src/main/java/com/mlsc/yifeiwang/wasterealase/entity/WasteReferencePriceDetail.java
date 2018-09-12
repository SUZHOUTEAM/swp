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
@TableName("waste_reference_price_detail")
public class WasteReferencePriceDetail extends Model<WasteReferencePriceDetail> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("parent_Id")
    private Double parentId;
    @TableField("start_amount")
    private Double startAmount;
    @TableField("ent_amount")
    private Double entAmount;
    private Double price;
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

    public Double getParentId() {
        return parentId;
    }

    public void setParentId(Double parentId) {
        this.parentId = parentId;
    }

    public Double getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(Double startAmount) {
        this.startAmount = startAmount;
    }

    public Double getEntAmount() {
        return entAmount;
    }

    public void setEntAmount(Double entAmount) {
        this.entAmount = entAmount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
        return "WasteReferencePriceDetail{" +
                "id=" + id +
                ", startAmount=" + startAmount +
                ", entAmount=" + entAmount +
                ", price=" + price +
                ", createTime=" + createTime +
                ", editBy=" + editBy +
                ", editTime=" + editTime +
                ", deleteFlag=" + deleteFlag +
                "}";
    }
}
