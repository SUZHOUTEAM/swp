package com.mlsc.waste.fileupload.service.impl;

import com.mlsc.common.constant.FileTypeEnum;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.fileupload.dao.UploadfileDao;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sunjl
 */
@Service("UploadfileService")
public class UploadfileServiceImpl implements UploadfileService {
    private final static Logger logger = LoggerFactory.getLogger(UploadfileServiceImpl.class);

    @Autowired
    private UploadfileDao uploadfileDao;

    /**
     * 保存上传图片
     */
    @Override
    public void saveUploadFile(String ticketId,String businessCode, String md5Id,
            String fileId, String fileType, String referenceId) {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            Uploadfile uploadFile = new Uploadfile();
            uploadFile.setBusinessCode(businessCode);
            uploadFile.setMd5Id(md5Id);
            uploadFile.setFileId(fileId);
            uploadFile.setFileType(fileType);
            uploadFile.setReferenceId(referenceId);
            uploadFile.setCreateBy(user.getUserId());
            uploadFile.setCreateTime(Util.datetimeToString(new Date()));
            uploadFile.setEditBy(user.getUserId());
            uploadFile.setEditTime(Util.datetimeToString(new Date()));
            uploadfileDao.save(uploadFile, Util.uuid32());
        } catch (DaoAccessException e) {
            logger.error("保存上传文件信息异常", e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 根据businessCode获取文件信息
     */
    @Override
    public Uploadfile getFileByBusinessCode(String businessCode) {
        Uploadfile uploadFile = new Uploadfile();
        try {
            uploadFile = uploadfileDao.getFileByBusinessCode(businessCode);
        } catch (DaoAccessException e) {
            logger.error("获取文件信息异常", e);
            throw new RuntimeException(e);
        }
        return uploadFile;
    }

    /**
     * 根据businessCode更新文件信息
     * @param businessCode
     */
    @Override
    public void updateFileByBusinessCode(String ticketId,String businessCode, String md5Id,
            String fileId, String fileType, String referenceId) {
            try {
                User user = LoginStatusUtils.getUserByTicketId(ticketId);
                //根据businCode判断是新增还是更新
                Uploadfile uploadfile = getFileByBusinessCode(businessCode);
                if(uploadfile != null){
                    //更新上传文件信息
                    uploadfile.setBusinessCode(businessCode);
                    uploadfile.setMd5Id(md5Id);
                    uploadfile.setFileId(fileId);
                    uploadfile.setFileType(fileType);
                    uploadfile.setReferenceId(referenceId);
                    uploadfileDao.update(uploadfile);
                }else{
                    //新增上传文件信息
                    Uploadfile uf = new Uploadfile();
                    uf.setBusinessCode(businessCode);
                    uf.setMd5Id(md5Id);
                    uf.setFileId(fileId);
                    uf.setFileType(fileType);
                    uf.setReferenceId(referenceId);
                    uf.setCreateBy(user.getUserId());
                    uf.setCreateTime(Util.datetimeToString(new Date()));
                    uf.setEditBy(user.getUserId());
                    uf.setEditTime(Util.datetimeToString(new Date()));
                    uploadfileDao.save(uf, Util.uuid32());
                }
            } catch (DaoAccessException e) {
                logger.error("新增或更新文件信息异常", e);
                throw new RuntimeException(e);
            }
    }
    
    /**
     * 根据文件类型和referenceId获取文件信息
     */
    @Override
    public List<Uploadfile> getFileByFileTypeAndReferenceId(String fileType,String referenceId) {
        List<Uploadfile> uploadFileList = new ArrayList<Uploadfile>();
        try {
            uploadFileList = uploadfileDao.getFileByFileTypeAndReferenceId(fileType,referenceId);
            if(uploadFileList!=null&&uploadFileList.size()>0){
                for(Uploadfile file : uploadFileList){
                    file.setFileTypeName(FileTypeEnum.getNameByCode(file.getFileType()));
                }
            }
        } catch (DaoAccessException e) {
            logger.error("获取文件信息异常", e);
            throw new RuntimeException(e);
        }
        return uploadFileList;
    }

    /**
     * 根据fileid删除文件
     */
    @Override
    public void deleteFileByFileId(String fileId) {
        try {
            uploadfileDao.deleteFileByFileId(fileId);
        } catch (DaoAccessException e) {
            logger.error("获取文件信息异常", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据businessCode修改文件上传信息表
     * @param businessCode
     * @param enterpriseId
     */
    @Override
    public void updateUploadFileByBusinessCode(String businessCode,
            String enterpriseId) {
        uploadfileDao.updateUploadFileByBusinessCode(businessCode,enterpriseId);
    }

	@Override
	public void deleteUploadFileByReferenceIdAndImgType(String enterpriseWasteId,
			String type) {
		uploadfileDao.deleteImgByReferenceIdAndImgType(enterpriseWasteId,type);
		
	}
}
