package com.mlsc.waste.enterprise.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

/**
 * 企业ID映射（外部平台企业与易废网注册企业ID的映射）表
 * @author zhugl
 */
@Table(TableNameConstants.TABLE_DATA_SYNCHRONIZE)
public class DataSynchronize extends Entity {
    
    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("id")
    private String id;  //主键
   
    @Column("internal_id")
    private String internalId;  //易废网的业务ID
    
    @Column("external_id")
    private String externalId;  //易废网以外平台的业务ID
    
    @Column("data_type")
    private String dataType;  //数据类型 （EE：易废网的企业ID与易废网以外的企业ID之间的映射，UE：易废网的企用户ID与易废网以外的企业ID之间的映射）
    
    /**
     * 构造方法，
     */
    public DataSynchronize() {
    }
    
    /**
     * 构造方法
     * @param internalId  易废网的业务ID
     * @param externalId  易废网以外平台的业务ID
     * @param dataType  EE：易废网的企业ID与易废网以外的企业ID之间的映射，UE：易废网的企用户ID与易废网以外的企业ID之间的映射
     */
    public DataSynchronize(String internalId, String externalId, String dataType) {
        this.internalId = internalId;
        this.externalId = externalId;
        this.dataType = dataType;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
