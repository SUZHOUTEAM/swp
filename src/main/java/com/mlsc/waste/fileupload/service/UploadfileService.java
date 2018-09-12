package com.mlsc.waste.fileupload.service;

import com.mlsc.waste.fileupload.model.Uploadfile;

import java.util.List;


/**
 * 文件上传
 * @author sunjl
 *
 */
public interface UploadfileService {
    
    /**
     * 保存上传文件
     * @param businessCode
     * @param md5Id
     * @param fileId
     * @param fileType
     * @param referenceId
     */
    void saveUploadFile(String ticketId,String businessCode,String md5Id,String fileId,String fileType,String referenceId);
    
    /**
     * 根据businessCode取得文件信息
     * @param businessCode
     * @return
     */
    Uploadfile getFileByBusinessCode(String businessCode);
    
    /**
     * 根据businessCode更新文件信息
     * @param ticketId
     * @param businessCode
     * @param md5Id
     * @param fileId
     * @param fileType
     * @param referenceId
     */
    void updateFileByBusinessCode(String ticketId,String businessCode, String md5Id,String fileId, String fileType, String referenceId);
    
    /**
     * 根据businessCode取得文件信息
     * @param fileType
     * @param referenceId
     * @return
     */
    List<Uploadfile> getFileByFileTypeAndReferenceId(String fileType,String referenceId);
    
    /**
     * 根据fileid删除文件
     * @param fileId
     */
    void deleteFileByFileId(String fileId);
    
    /**
     * 根据businessCode修改文件上传信息表
     * @param businessCode
     * @param enterpriseId
     */
    void updateUploadFileByBusinessCode(String businessCode,String enterpriseId);


	void deleteUploadFileByReferenceIdAndImgType(String enterpriseWasteId,
			String string);
}