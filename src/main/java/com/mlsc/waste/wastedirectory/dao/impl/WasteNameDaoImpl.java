   /**
 * 
 */
package com.mlsc.waste.wastedirectory.dao.impl;

   import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
   import com.mlsc.epdp.common.exception.DaoAccessException;
   import com.mlsc.epdp.common.utils.ObjectUtil;
   import com.mlsc.waste.utils.TableNameConstants;
   import com.mlsc.waste.wastedirectory.dao.WasteNameDao;
   import com.mlsc.waste.wastedirectory.model.WasteName;
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
 *
 */
@Repository
public class WasteNameDaoImpl extends EntityDaoSupport<WasteName> implements WasteNameDao {
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;
    
    @Override
    public void deleteByWasteId(String id) throws DaoAccessException {
        String sql = "DELETE from "+ TableNameConstants.TABLE_WASTE_NAME+" where waste_id ='"+ id +"'";
        namedjdbctemp.update(sql, new HashMap<String,String>());
    }
    
    @Override
    public List<WasteName> queryByWasteId(String wasteid)
        throws DaoAccessException {
        StringBuffer sqlWhere = new StringBuffer("select * from " + TableNameConstants.TABLE_WASTE_NAME +" where waste_id = :wasteid order by edit_time");
        return queryForList(sqlWhere.toString(), ObjectUtil.newParamMap("wasteid", wasteid), new BeanPropertyRowMapper<>(WasteName.class));
    }

    @Override
    public List<WasteName> queryByNameAndWasteid(String wasteid, String name,String model)
        throws DaoAccessException {
        StringBuffer sqlWhere = new StringBuffer("select * from " + TableNameConstants.TABLE_WASTE_NAME + " where waste_id = :wasteid");
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(model)) {
            sqlWhere.append(" and name like :name order by edit_time");
            map.put("name", "%" + name + "%");
        } else {
            sqlWhere.append(" and name = :name order by edit_time");
            map.put("name", name);
        }
        
        map.put("wasteid", wasteid);
        return queryForList(sqlWhere.toString(), map, new BeanPropertyRowMapper<>(WasteName.class));
    }

    @Override
    public WasteName getWasteNameByCodeAndName(String code, String name)
            throws DaoAccessException {
        
        StringBuffer sql = new StringBuffer("select * from " + TableNameConstants.TABLE_WASTE_NAME );
        sql.append(" LEFT JOIN waste on waste.id = waste_name.waste_id ");
        sql.append(" WHERE waste.code = :code");
        sql.append(" AND waste_name.name = :name");
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("name", name);
        
        List<WasteName> wnlist =  namedParameterJdbcTemplate.query(sql.toString(), map, new BeanPropertyRowMapper<WasteName>(WasteName.class));
        if (wnlist != null && wnlist.size() > 0) {
            return wnlist.get(0);
        } else {
            return null;
        }
    }

}