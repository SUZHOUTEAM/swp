package com.mlsc.waste.wastecircle.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.dao.CoopCommentDao;
import com.mlsc.waste.wastecircle.model.CoopComment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CoopCommentDaoImpl extends EntityDaoSupport<CoopComment>  implements CoopCommentDao {
    
    @Override
    public void saveCoopComment(CoopComment coopComment) throws DaoAccessException {
        save(coopComment, Util.uuid32());

    }

    @Override
    public List<CoopComment> getCoopComment(CoopComment coopComment) throws DaoAccessException {
        StringBuffer sql = new StringBuffer("SELECT ID, COMMENT_TYPE AS COMMENTTYPE ,COMMENTATOR_ID AS COMMENTATORID , ");
        sql.append("ENTERPRISE_ID AS ENTERID , MESSAGE_ID AS MSGID ,PARENT_COMMENT_ID AS PARENTID , COMMENT_DETAIL AS COMMENTDETAIL" );
        sql.append("FROM ").append(TableNameConstants.TABLE_COOPERATION_COMMENT) ;
        sql.append(" WHERE COMMENTATOR_ID=:COMMENTATOR_ID ORDER BY EDIT_TIME DESC");
        Map<String, String> map = new HashMap<String, String>();
        map.put("commentator_id", coopComment.getCommentatorId());
        
        List<CoopComment> resultList = namedParameterJdbcTemplate.query(sql.toString(), map, new BeanPropertyRowMapper<CoopComment>(CoopComment.class));
        
        if(resultList!=null && resultList.size() >0){
            return resultList;
        }else{
            return null;
        }

    }

    @Override
    public void updateCoopComment(CoopComment coopComment)    throws DaoAccessException {
        String sql = "update " + TableNameConstants.TABLE_COOPERATION_COMMENT + " set comment_detail =:comment_detail, edit_by=:edit_by, edit_time=:edit_time  where id=:id";
        
        Map<String,String> map=new HashMap<String,String>();
        map.put("comment_detail", coopComment.getCommentDetail());
        map.put("edit_by", coopComment.getEditBy());
        map.put("edit_time", coopComment.getEditTime());
        map.put("id", coopComment.getId());
        
        getNamedParameterJdbcTemplate().update(sql, map);
    }

}
