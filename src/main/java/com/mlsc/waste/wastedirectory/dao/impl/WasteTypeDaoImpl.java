/**
 * 
 */
package com.mlsc.waste.wastedirectory.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.wastedirectory.dao.WasteTypeDao;
import com.mlsc.waste.wastedirectory.model.WasteType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenhh
 * 危废类别
 */
@Repository
public class WasteTypeDaoImpl extends EntityDaoSupport<WasteType> implements WasteTypeDao {
    
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;
    
    /**
     * 根据危废类别代码查询数据
     * 
     */
    @Override
    public int getWasteTypeCountByCode(String id,String code) throws DaoAccessException {
        Map<String, String> map = new HashMap<String, String>();
        StringBuffer sql = new StringBuffer("select count(1) from " + TableNameConstants.TABLE_WASTE_TYPE + " where code=:code ");
        map.put("code", code);
        if (!StringUtils.isNotBlank(id)) {
            sql.append("and id <> :id");
            map.put("id", id);
        }
        
        return getNamedParameterJdbcTemplate().queryForObject(sql.toString(), map, Integer.class);
    }

    @Override
    public List<WasteType> list(String where, Map<String, Object> paramMap, PagingParameter paging) throws DaoAccessException {
        String tempSql = "select id,code,description,status,create_by,DATE_FORMAT( create_time, '%Y-%m-%d %H:%i:%s') as create_time,"
                + "edit_by,DATE_FORMAT( edit_time, '%Y-%m-%d %H:%i:%s') as edit_time,"
                + "status from " + TableNameConstants.TABLE_WASTE_TYPE;
        
        String sql = tempSql + " where 1 = 1 " +where;
        return namedParameterJdbcTemplate.query(
                sql + " limit " + paging.getStart()+" , " +paging.getLimit()
                , paramMap
                , new BeanPropertyRowMapper<WasteType>(WasteType.class));
        
    }
    
    @Override
    public Integer count(String where, Map<String, Object> paramMap) throws DaoAccessException{
        String tempSql = "select count(1) from " + TableNameConstants.TABLE_WASTE_TYPE;
        
        String sql = tempSql + " where 1=1 " + where;
        return namedParameterJdbcTemplate.queryForObject(sql,paramMap, Integer.class);
        
    }
    
    @Override
    public List<Map<String, Object>> search(Object... params)
            throws DaoAccessException {
        try {
            String sql = "SELECT id as waste_type_id, code,CONCAT(`code`,'-',`description`) AS wasteType FROM " + TableNameConstants.TABLE_WASTE_TYPE;
            return jdbcTemplate.queryForList(sql, params);
        } catch (Exception e) {
            throw new DaoAccessException(e);
        }
    }

    @Override
    public List<WasteType> queryAllWasteType() {
        String sql = "SELECT * FROM " + TableNameConstants.TABLE_WASTE_TYPE + " order by code asc";
        return jdbcTemplate.query(sql,  new BeanPropertyRowMapper<WasteType>(WasteType.class));
        
    }
}

