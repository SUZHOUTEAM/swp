package com.mlsc.waste.fileupload.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

/**
 * 文件上传表
 * @author zhugl
 */
@Table(TableNameConstants.TABLE_UPLOAD_FILE)
public class Uploadfile extends Entity {
    
    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("id")
    private String id;  //主键
   
    @Column("business_code")
    private String businessCode;  
    
    @Column("md5_id")
    private String md5Id; 
    
    @Column("file_id")
    private String fileId;  

    @Column("file_type")
    private String fileType;  

    @Column("reference_id")
    private String referenceId;  
    
    @Column("create_by")
    private String createBy;
    
    @Column("create_time")
    private String createTime;
    
    @Column("edit_by")
    private String editBy;
    
    @Column("edit_time")
    private String editTime;

    private String fileTypeName;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the businessCode
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * @param businessCode the businessCode to set
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
    
    /**
     * @return the md5Id
     */
    public String getMd5Id() {
        return md5Id;
    }

    /**
     * @param md5Id the md5Id to set
     */
    public void setMd5Id(String md5Id) {
        this.md5Id = md5Id;
    }

    /**
     * @return the fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the referenceId
     */
    public String getReferenceId() {
        return referenceId;
    }

    /**
     * @param referenceId the referenceId to set
     */
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * @return the createBy
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy the createBy to set
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
     * @return the editBy
     */
    public String getEditBy() {
        return editBy;
    }

    /**
     * @param editBy the editBy to set
     */
    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    /**
     * @return the editTime
     */
    public String getEditTime() {
        return editTime;
    }

    /**
     * @param editTime the editTime to set
     */
    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }
}
