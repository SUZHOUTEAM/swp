package com.mlsc.waste.fileupload.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.fileupload.dao.UploadfileDao;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.utils.TableNameConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * @author sunjl
 * 
 */
@Repository
public class UploadfileDaoImpl extends EntityDaoSupport<Uploadfile> implements UploadfileDao {
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;

    @Override
    public Uploadfile getFileByBusinessCode(String businessCode)
            throws DaoAccessException {
        Map<String, Object> map = new  HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer("select uploadfile.id, ");
        sqlWhere.append("uploadfile.business_code, ");
        sqlWhere.append("uploadfile.md5_id, ");
        sqlWhere.append("uploadfile.file_id, ");
        sqlWhere.append("uploadfile.file_type, ");
        sqlWhere.append("uploadfile.reference_id, ");
        sqlWhere.append("uploadfile.create_by, ");
        sqlWhere.append("uploadfile.create_time, ");
        sqlWhere.append("uploadfile.edit_by, ");
        sqlWhere.append("uploadfile.edit_time ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_UPLOAD_FILE +  " uploadfile ");
        sqlWhere.append(" where uploadfile.business_code = :businessCode");
        map.put("businessCode",businessCode);
        List<Uploadfile> queryList = namedjdbctemp.query(sqlWhere
                .toString(), map,new BeanPropertyRowMapper<Uploadfile>(
                        Uploadfile.class));
        if (queryList != null && queryList.size() > 0) {
            return queryList.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public List<Uploadfile> getFileByFileTypeAndReferenceId(String fileType,String referenceId) throws DaoAccessException {
        Map<String, Object> map = new  HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer("select uploadfile.id, ");
        sqlWhere.append("uploadfile.business_code, ");
        sqlWhere.append("uploadfile.md5_id, ");
        sqlWhere.append("uploadfile.file_id, ");
        sqlWhere.append("uploadfile.file_type, ");
        sqlWhere.append("uploadfile.reference_id, ");
        sqlWhere.append("uploadfile.create_by, ");
        sqlWhere.append("uploadfile.create_time, ");
        sqlWhere.append("uploadfile.edit_by, ");
        sqlWhere.append("uploadfile.edit_time ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_UPLOAD_FILE +  " uploadfile ");
        sqlWhere.append(" where uploadfile.file_type = :fileType");
        sqlWhere.append(" and uploadfile.reference_id = :referenceId order by create_time");
        map.put("fileType",fileType);
        map.put("referenceId",referenceId);
        List<Uploadfile> queryList = namedjdbctemp.query(sqlWhere.toString(), map,new BeanPropertyRowMapper<Uploadfile>(Uploadfile.class));
        return queryList;
    }

    @Override
    public void deleteFileByFileId(String fileId) throws DaoAccessException {
        Map<String, Object> map = new  HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer("delete from ");
        sqlWhere.append(TableNameConstants.TABLE_UPLOAD_FILE);
        sqlWhere.append(" where file_id = '"+fileId+"'");
        namedParameterJdbcTemplate.update(sqlWhere.toString(), map);
    }

    @Override
    public void updateUploadFileByBusinessCode(String businessCode,
            String enterpriseId) {
        Map<String, Object> map = new  HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer("update ");
        sqlWhere.append(TableNameConstants.TABLE_UPLOAD_FILE);
        sqlWhere.append(" set reference_id = '"+enterpriseId+"'");
        sqlWhere.append(" where business_code = '"+businessCode+"'");
        namedParameterJdbcTemplate.update(sqlWhere.toString(), map);
    }

	@Override
	public void deleteImgByReferenceIdAndImgType(String enterpriseWasteId,
			String type) {
		Map<String, Object> map = new  HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer("delete from ");
        sqlWhere.append(TableNameConstants.TABLE_UPLOAD_FILE);
        sqlWhere.append(" where reference_id = '"+enterpriseWasteId+"' and file_type='"+type+"'");
        namedParameterJdbcTemplate.update(sqlWhere.toString(), map);
	}
}