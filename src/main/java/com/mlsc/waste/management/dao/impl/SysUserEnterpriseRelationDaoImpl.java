package com.mlsc.waste.management.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.waste.management.dao.SysUserEnterpriseRelationDao;
import com.mlsc.waste.user.model.SysUserEnterpriseRelation;
import com.mlsc.waste.utils.TableNameConstants;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghj on 2017/7/4.
 */
@Repository("userEnterpriseRelationDao")
public class SysUserEnterpriseRelationDaoImpl extends EntityDaoSupport<SysUserEnterpriseRelation> implements SysUserEnterpriseRelationDao{

    @Override
    public void  deleteByUserIdList(List<String> userIdList){
        StringBuilder sb = new StringBuilder();
        sb.append(" DELETE FROM ").append(TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION);
        sb.append(" WHERE UserId in (:userIdList) ");
        Map<String,Object> map = new HashMap<>();
        map.put("userIdList",userIdList);
        this.namedParameterJdbcTemplate.update(sb.toString(),map);
    }

    @Override
    public SysUserEnterpriseRelation getSysUserEnterpriseRelationByUserId(String userId) {
        Map<String, String> paraMap = new HashMap<>();
        StringBuffer tempSql = new StringBuffer();
        tempSql.append(" select * from ").append(TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION);
        tempSql.append(" where UserId = :UserId");
        paraMap.put("UserId",userId);
        List<SysUserEnterpriseRelation> list = namedParameterJdbcTemplate.query(tempSql.toString(), paraMap, new BeanPropertyRowMapper<SysUserEnterpriseRelation>(SysUserEnterpriseRelation.class));
        if(list != null && list.size() > 0){
            return  list.get(0);
        }
        return  null;
    }
}
