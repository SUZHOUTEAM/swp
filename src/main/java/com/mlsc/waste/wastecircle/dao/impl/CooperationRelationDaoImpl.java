package com.mlsc.waste.wastecircle.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.wastecircle.dao.CooperationRelationDao;
import com.mlsc.waste.wastecircle.model.CooperationRelation;
import com.mlsc.waste.wastecircle.model.FollowEnterpriseVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CooperationRelationDaoImpl extends EntityDaoSupport<CooperationRelation> implements CooperationRelationDao {

    @Override
    public List<FollowEnterpriseVo> getFollowEnterprises(String userId ,PagingParameter paging) throws DaoAccessException{
        Map<String, Object> paraMap = new HashMap<String, Object>();
        StringBuffer querySql = new StringBuffer("select follow.id, ");
        querySql.append(" follow.user_id,");
        querySql.append(" follow.follow_id,");
        querySql.append(" follow.follow_type,");
        querySql.append(" follow.create_by,");
        querySql.append(" follow.create_time,");
        querySql.append(" follow.edit_by,");
        querySql.append(" follow.edit_time,");
        querySql.append(" follow.valid,");
        querySql.append(" sys_ent_base.entName as entName,");
        querySql.append(" sys_ent_base.entAddress as entAddr,");
        querySql.append(" :isyes as isFollow ");
        querySql.append(" from " + TableNameConstants.TABLE_COOPERATION_RELATION + " follow ");
        querySql.append(" inner join " + TableNameConstants.TABLE_SYS_ENTERPRISE_BASE  + " sys_ent_base on follow.follow_id = sys_ent_base.entId ");
        querySql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE  + " followType on follow.follow_type = followType.id and followType.code = :followTypeEnt ");
        querySql.append(" where follow.user_id = :userId and IFNULL(follow.valid,:valid) = :valid ");
        querySql.append(" order by follow.edit_time desc");
        if (paging.getLimit() != 0) {
            querySql.append(" limit " + paging.getStart() + " , " + paging.getLimit() );
        }
        paraMap.put("userId", userId);
        paraMap.put("followTypeEnt", CodeTypeConstant.FOLLOW_TYPE_ORGANIZED);
        paraMap.put("isyes", Constant.IS_YSE);
        paraMap.put("valid", Constant.IS_VALID);
        
        List<FollowEnterpriseVo> resultList = namedParameterJdbcTemplate.query(querySql.toString(), paraMap,
                    new BeanPropertyRowMapper<FollowEnterpriseVo>(FollowEnterpriseVo.class));
         
        return resultList;
    }
    
    @Override
    public int getFollowEnterprisesCount(String userId) throws DaoAccessException{
        Map<String, Object> paraMap = new HashMap<String, Object>();
        StringBuffer querySql = new StringBuffer("select count(1) ");
        querySql.append(" from " + TableNameConstants.TABLE_COOPERATION_RELATION + " follow ");
        querySql.append(" inner join " + TableNameConstants.TABLE_SYS_ENTERPRISE_BASE  + " sys_ent_base on follow.follow_id = sys_ent_base.entId ");
        querySql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE  + " followType on follow.follow_type = followType.id and followType.code = :followTypeEnt ");
        querySql.append(" where follow.user_id = :userId and IFNULL(follow.valid,:valid) = :valid ");
        querySql.append(" order by follow.edit_time desc");
        
        paraMap.put("userId", userId);
        paraMap.put("followTypeEnt", CodeTypeConstant.FOLLOW_TYPE_ORGANIZED);
        paraMap.put("valid", Constant.IS_VALID);
        
        return namedParameterJdbcTemplate.queryForObject(querySql.toString(),paraMap, Integer.class);
    }

    @Override
    public void removeFollowByFollowId(String userId, String followId, String followType) throws DaoAccessException{
        Map<String, Object> paraMap = new HashMap<String, Object>();
        StringBuffer querySql = new StringBuffer("delete ");
        querySql.append(" from " + TableNameConstants.TABLE_COOPERATION_RELATION );
        querySql.append(" where user_id = :userId and follow_id = :followId and follow_type = :followType ");
        paraMap.put("userId", userId);
        paraMap.put("followId", followId);
        paraMap.put("followType", followType);
        
        namedParameterJdbcTemplate.update(querySql.toString(), paraMap);
    }

    @Override
    public int isFollowed(String userId, String followId, String followType) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select count(*) ");
        tempSql.append(" from " + TableNameConstants.TABLE_COOPERATION_RELATION);
        tempSql.append(" where user_id = :userId and follow_id = :followId and follow_type = :followType ");
        paraMap.put("userId", userId);
        paraMap.put("followId", followId);
        paraMap.put("followType", followType);
        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(),paraMap, Integer.class);
    }

    @Override
    public List<RPCSysUser> getUserInfoByfollowId(String followId) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        StringBuffer querySql = new StringBuffer("select follow.user_id as userId, ");
        querySql.append(" sys_user.phoneNum as phoneNum ");// 关注者的手机号码
        querySql.append(" from " + TableNameConstants.TABLE_COOPERATION_RELATION + " follow ");
        querySql.append(" inner join " + TableNameConstants.TABLE_SYS_USER  + " sys_user on follow.user_id = sys_user.userId ");
        querySql.append(" inner join " + TableNameConstants.TABLE_SYS_ENTERPRISE_BASE  + " sys_ent_base on follow.follow_id = sys_ent_base.entId ");
        
        querySql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE  + " followType on follow.follow_type = followType.id and followType.code = :followTypeEnt ");
        querySql.append(" where follow.follow_id = :followId and IFNULL(follow.valid,:valid) = :valid ");
        paraMap.put("followId", followId);
        paraMap.put("followTypeEnt", CodeTypeConstant.FOLLOW_TYPE_ORGANIZED);
        paraMap.put("valid", Constant.IS_VALID);
        
        List<RPCSysUser> resultList = namedParameterJdbcTemplate.query(querySql.toString(), paraMap,
                    new BeanPropertyRowMapper<RPCSysUser>(RPCSysUser.class));
         
        return resultList;
    }
}
