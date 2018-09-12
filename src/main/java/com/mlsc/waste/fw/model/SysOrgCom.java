/**
 * 
 */
package com.mlsc.waste.fw.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

/**
 * 组织机构实体类
 * @author zhugl
 */
@Table(TableNameConstants.TABLE_SYS_ORG_COM)
public class SysOrgCom extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("comId")
    private String comId;  //机构标识主键
   
    @Column("comName")
    private String comName;  //机构名称
    
    @Column("comCode")
    private String comCode; //机构编码
    
    @Column("comStatus")
    private Integer comStatus;  //机构状态，注销，正常，挂起   0是正常
    
    @Column("comDesc")
    private String comDesc;  //机构说明

    @Column("comType")
    private Integer comType; //机构类别(企业/机构) 1/2
    
    @Column("comFunc")
    private String comFunc; //机构职能(环保厅，交通厅等)
    
    @Column("coordinateId")
    private String coordinateId; //存经纬度相关
    
    @Column("createrId")
    private String createrId;  //创建者
    
    @Column("createTime")
    private String createTime;  //创建时间
    
    @Column("updaterId")
    private String updaterId;  //更新者
    
    @Column("updateTime")
    private String updateTime;  //更新时间

    /**
     * @return the comId
     */
    public String getComId() {
        return comId;
    }

    /**
     * @param comId the comId to set
     */
    public void setComId(String comId) {
        this.comId = comId;
    }

    /**
     * @return the comName
     */
    public String getComName() {
        return comName;
    }

    /**
     * @param comName the comName to set
     */
    public void setComName(String comName) {
        this.comName = comName;
    }

    /**
     * @return the comCode
     */
    public String getComCode() {
        return comCode;
    }

    /**
     * @param comCode the comCode to set
     */
    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    /**
     * @return the comStatus
     */
    public Integer getComStatus() {
        return comStatus;
    }

    /**
     * @param comStatus the comStatus to set
     */
    public void setComStatus(Integer comStatus) {
        this.comStatus = comStatus;
    }

    /**
     * @return the comDesc
     */
    public String getComDesc() {
        return comDesc;
    }

    /**
     * @param comDesc the comDesc to set
     */
    public void setComDesc(String comDesc) {
        this.comDesc = comDesc;
    }

    /**
     * @return the comType
     */
    public Integer getComType() {
        return comType;
    }

    /**
     * @param comType the comType to set
     */
    public void setComType(Integer comType) {
        this.comType = comType;
    }

    /**
     * @return the comFunc
     */
    public String getComFunc() {
        return comFunc;
    }

    /**
     * @param comFunc the comFunc to set
     */
    public void setComFunc(String comFunc) {
        this.comFunc = comFunc;
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
