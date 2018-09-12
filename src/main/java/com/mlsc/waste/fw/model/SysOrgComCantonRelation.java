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
 * 机构行政区划关系
 * @author zhugl
 */
@Table(TableNameConstants.TABLE_SYS_ORG_COM_CANTON_RELATION)
public class SysOrgComCantonRelation extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("relId")
    private String relId;  //关系标识
   
    @Column("comId")
    private String comId;  //机构标识
    
    @Column("cantonCode")
    private String cantonCode; //区划代码
    
    @Column("relType")
    private Integer relType;  //关系类型（辖区，行政管辖等） 0/1
    
    @Column("sequence")
    private Integer sequence;  //顺序码

    /**
     * @return the relId
     */
    public String getRelId() {
        return relId;
    }

    /**
     * @param relId the relId to set
     */
    public void setRelId(String relId) {
        this.relId = relId;
    }

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
     * @return the relType
     */
    public Integer getRelType() {
        return relType;
    }

    /**
     * @param relType the relType to set
     */
    public void setRelType(Integer relType) {
        this.relType = relType;
    }

    /**
     * @return the sequence
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
