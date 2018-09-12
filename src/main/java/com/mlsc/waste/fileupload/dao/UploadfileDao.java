package com.mlsc.waste.fileupload.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.fileupload.model.Uploadfile;

import java.util.List;

/**
 * 文件上传
 * @author sunjl
 * 
 */
public interface UploadfileDao extends EntityDao<Uploadfile> {
    
    /**
     * 根据businessCode取得文件信息
     * @param businessCode
     * @return
     * @throws DaoAccessException
     */
    Uploadfile getFileByBusinessCode(String businessCode) throws DaoAccessException;
    
    /**
     * 根据businessCode取得文件信息
     * @param fileType
     * @param referenceId
     * @return
     * @throws DaoAccessException
     */
    List<Uploadfile> getFileByFileTypeAndReferenceId(String fileType,String referenceId) throws DaoAccessException;
    
    /**
     * 根据fileid删除文件
     * @throws DaoAccessException
     */
    void deleteFileByFileId(String fileId) throws DaoAccessException;
    
    /**
     * 根据businessCode修改文件上传信息表
     * @param businessCode
     * @param enterpriseId
     */
    void updateUploadFileByBusinessCode(String businessCode,String enterpriseId);

	void deleteImgByReferenceIdAndImgType(String enterpriseWasteId, String type);
}
