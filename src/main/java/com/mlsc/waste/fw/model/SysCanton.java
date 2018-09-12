/**
 * 
 */
package com.mlsc.waste.fw.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

import java.util.Date;

/**
 * 组织机构实体类
 * @author zhugl
 */
@Table(TableNameConstants.TABLE_SYS_CANYON)
public class SysCanton extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("cantonCode")
    private String cantonCode;  //区划代码
   
    @Column("parentCantonCode")
    private String parentCantonCode;  //父级区划代码
    
    @Column("cantonName")
    private String cantonName; //行政区名称
    
    @Column("cantonLevel")
    private Integer cantonLevel;  //行政区级别
    
    @Column("cantonType")
    private Integer cantonType;  //行政区类型（是否是国家承认的编码）承认的是1

    @Column("coordinateId")
    private String coordinateId; //经度ID
    
    @Column("postCode")
    private String postCode; //邮政编码
    
    @Column("areaCode")
    private String areaCode; //区号
    
    @Column("enableDatetime")
    private Date enableDatetime; //生效日期
    
    @Column("disableDatetime")
    private Date disableDatetime; //失效日期
    
    @Column("cantonStatus")
    private Integer cantonStatus; //行政区状态
    
    @Column("isEndNode")
    private Integer isEndNode; //是否末级节点 0:否；1：是
    
    @Column("cantonFullName")
    private String cantonFullName; //行政规划全称
    
    @Column("createrId")
    private String createrId;  //创建人
    
    @Column("createTime")
    private String createTime;  //创建时间
    
    @Column("updaterId")
    private String updaterId;  //更新者
    
    @Column("updateTime")
    private String updateTime;  //更新时间

    /**
     * @return the cantonCode
     */
    public String getCantonCode() {
        return cantonCode;
    }

    /**
     * @param cantonCode the cantonCode to set
     */
    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    /**
     * @return the parentCantonCode
     */
    public String getParentCantonCode() {
        return parentCantonCode;
    }

    /**
     * @param parentCantonCode the parentCantonCode to set
     */
    public void setParentCantonCode(String parentCantonCode) {
        this.parentCantonCode = parentCantonCode;
    }

    /**
     * @return the cantonName
     */
    public String getCantonName() {
        return cantonName;
    }

    /**
     * @param cantonName the cantonName to set
     */
    public void setCantonName(String cantonName) {
        this.cantonName = cantonName;
    }

    /**
     * @return the cantonLevel
     */
    public Integer getCantonLevel() {
        return cantonLevel;
    }

    /**
     * @param cantonLevel the cantonLevel to set
     */
    public void setCantonLevel(Integer cantonLevel) {
        this.cantonLevel = cantonLevel;
    }

    /**
     * @return the cantonType
     */
    public Integer getCantonType() {
        return cantonType;
    }

    /**
     * @param cantonType the cantonType to set
     */
    public void setCantonType(Integer cantonType) {
        this.cantonType = cantonType;
    }

    /**
     * @return the coordinateId
     */
    public String getCoordinateId() {
        return coordinateId;
    }

    /**
     * @param coordinateId the coordinateId to set
     */
    public void setCoordinateId(String coordinateId) {
        this.coordinateId = coordinateId;
    }

    /**
     * @return the postCode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postCode the postCode to set
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * @return the areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * @param areaCode the areaCode to set
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * @return the enableDatetime
     */
    public Date getEnableDatetime() {
        return enableDatetime;
    }

    /**
     * @param enableDatetime the enableDatetime to set
     */
    public void setEnableDatetime(Date enableDatetime) {
        this.enableDatetime = enableDatetime;
    }

    /**
     * @return the disableDatetime
     */
    public Date getDisableDatetime() {
        return disableDatetime;
    }

    /**
     * @param disableDatetime the disableDatetime to set
     */
    public void setDisableDatetime(Date disableDatetime) {
        this.disableDatetime = disableDatetime;
    }

    /**
     * @return the cantonStatus
     */
    public Integer getCantonStatus() {
        return cantonStatus;
    }

    /**
     * @param cantonStatus the cantonStatus to set
     */
    public void setCantonStatus(Integer cantonStatus) {
        this.cantonStatus = cantonStatus;
    }

    /**
     * @return the isEndNode
     */
    public Integer getIsEndNode() {
        return isEndNode;
    }

    /**
     * @param isEndNode the isEndNode to set
     */
    public void setIsEndNode(Integer isEndNode) {
        this.isEndNode = isEndNode;
    }

    /**
     * @return the cantonFullName
     */
    public String getCantonFullName() {
        return cantonFullName;
    }

    /**
     * @param cantonFullName the cantonFullName to set
     */
    public void setCantonFullName(String cantonFullName) {
        this.cantonFullName = cantonFullName;
    }

    /**
     * @return the createrId
     */
    public String getCreaterId() {
        return createrId;
    }

    /**
     * @param createrId the createrId to set
     */
    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updaterId
     */
    public String getUpdaterId() {
        return updaterId;
    }

    /**
     * @param updaterId the updaterId to set
     */
    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    /**
     * @return the updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
