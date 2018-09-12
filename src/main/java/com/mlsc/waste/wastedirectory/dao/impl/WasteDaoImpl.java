/**
 * 
 */
package com.mlsc.waste.wastedirectory.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.wastedirectory.dao.WasteDao;
import com.mlsc.waste.wastedirectory.model.Waste;
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
 * @author sunjl
 * 危废（八位码）
 */
@Repository
public class WasteDaoImpl extends EntityDaoSupport<Waste> implements WasteDao {
    
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;
    
    @Override
    public List<Waste> list(String where, Map<String, Object> paramMap, PagingParameter paging) throws DaoAccessException {
        String tempSql = "select * from (select concat(wt.description,' ',wt.code) as wasteType,"
                + "concat(c.name,' ',c.code) as callingName,w.code,w.description,"
                + "(select substring(CONCAT((CASE WHEN w.corrosivity = 1 THEN ',C-腐蚀性' ELSE '' END),"
                + "(CASE WHEN w.toxicity = 1 THEN ',T-毒性' ELSE '' END),"
                + "(CASE WHEN w.ignitability = 1 THEN ',I-易燃性' ELSE '' END),"
                + "(CASE WHEN w.reactivity = 1 THEN ',R-反应性' ELSE '' END),"
                + "(CASE WHEN w.infectivity = 1 THEN ',In-感染性' ELSE '' END)),2)) AS riskCharacteristics,"
                + "DATE_FORMAT( w.edit_time, '%Y-%m-%d %H:%i:%s') as edit_time,w.id,wname.w_name "
                + "from " + TableNameConstants.TABLE_WASTE + " w INNER JOIN " + TableNameConstants.TABLE_WASTE_TYPE + " wt on wt.id = w.waste_type_id "
                + "left JOIN(select substring_index( group_concat( name order by edit_time),',',10) as w_name,waste_id "
                + "from " + TableNameConstants.TABLE_WASTE_NAME + " as wn  GROUP BY wn.waste_id) as wname on wname.waste_id = w.id "
                        + " left join " + TableNameConstants.TABLE_CALLING + " c on w.calling_id = c.id) tmp ";
        
        String sql = tempSql + " where 1 = 1 " +where;
        return namedParameterJdbcTemplate.query(
                sql + " limit " + paging.getStart()+" , " +paging.getLimit()
                , paramMap
                , new BeanPropertyRowMapper<Waste>(Waste.class));
    }
    
    
    
    @Override
    public Integer getCount(String where, Map<String, Object> paramMap) throws DaoAccessException {
        String tempSql = "select count(1)  from (select concat(wt.description,' ',wt.code) as wasteType,"
                + "concat(c.name,' ',c.code) as callingName,w.code,w.description,"
                + "(select substring(CONCAT((CASE WHEN w.corrosivity = 1 THEN ',C-腐蚀性' ELSE '' END),"
                + "(CASE WHEN w.toxicity = 1 THEN ',T-毒性' ELSE '' END),"
                + "(CASE WHEN w.ignitability = 1 THEN ',I-易燃性' ELSE '' END),"
                + "(CASE WHEN w.reactivity = 1 THEN ',R-反应性' ELSE '' END),"
                + "(CASE WHEN w.infectivity = 1 THEN ',In-感染性' ELSE '' END)),2)) AS riskCharacteristics,"
                + "DATE_FORMAT( w.edit_time, '%Y-%m-%d %H:%i:%s') as edit_time,w.id,wname.w_name "
                + "from " + TableNameConstants.TABLE_WASTE + " w INNER JOIN " + TableNameConstants.TABLE_WASTE_TYPE + " wt on wt.id = w.waste_type_id "
                + "left JOIN(select substring_index( group_concat( name order by edit_time),',',10) as w_name,waste_id "
                + "from " + TableNameConstants.TABLE_WASTE_NAME + " as wn  GROUP BY wn.waste_id) as wname on wname.waste_id = w.id "
                        + " left join " + TableNameConstants.TABLE_CALLING + " c on w.calling_id = c.id) tmp ";

        String sql = tempSql + " where 1 = 1 " +where;
        return namedParameterJdbcTemplate.queryForObject(sql,paramMap, Integer.class);
    }

    @Override
    public Waste queryByCode(String code) throws DaoAccessException {
        StringBuffer sql = new StringBuffer("select * from "
                + TableNameConstants.TABLE_WASTE + " where code=:code and status=:enable");
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("enable", Constant.ENABLED);
        
        List<Waste> wlist =  namedParameterJdbcTemplate.query(sql.toString(), map, new BeanPropertyRowMapper<Waste>(Waste.class));
        if (wlist != null && wlist.size() > 0) {
            return wlist.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public ResultData<WasteType> getIdByCode(String code) throws DaoAccessException{
        String sql = "SELECT id from " + TableNameConstants.TABLE_WASTE_TYPE + " where code='"+code+"'";
        return queryDataStore(
                sql
                , null
                , new BeanPropertyRowMapper<WasteType>(WasteType.class)
                , null);
    }
    
    @Override
    public List<Waste> queryByWasteCode(String code) throws DaoAccessException {
        StringBuffer sqlWhere = new StringBuffer("select * from " + TableNameConstants.TABLE_WASTE + " where status = :enable ");
        Map<String, Object> map = new  HashMap<String, Object>();
        map.put("enable",Constant.ENABLED);
        if (StringUtils.isNotBlank(code)) {
            sqlWhere.append(" and code like :code");
            map.put("code","%"+ code + "%");
        }
        
        return namedParameterJdbcTemplate.query(sqlWhere.toString(), map, new BeanPropertyRowMapper<Waste>(Waste.class));
    }

    @Override
    public List<Waste> queryByWasteTypeId(String wasteTypeId) {
        String sql = "select * from " + TableNameConstants.TABLE_WASTE + " where waste_type_id = :wasteTypeId and status = :enable order by substring(code, 1, 3) asc, substring(code, 5, 3) asc";
        Map<String, Object> map = new  HashMap<String, Object>();
        map.put("wasteTypeId",wasteTypeId);
        map.put("enable",Constant.ENABLED);
        List<Waste> wList =  namedParameterJdbcTemplate.query(sql, map, new BeanPropertyRowMapper<Waste>(Waste.class));
        return wList;
    }



    @Override
    public List<Waste> getCodeWasteDropDownList(String keyword) throws DaoAccessException {
        Map<String, Object> map = new  HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer("select waste.id, ");
        sqlWhere.append("waste.waste_type_id, ");
        sqlWhere.append("waste.calling_id, ");
        sqlWhere.append("waste.code, ");
        sqlWhere.append("waste.description, ");
        sqlWhere.append("waste.status, ");
        sqlWhere.append("waste.create_by, ");
        sqlWhere.append("waste.create_time, ");
        sqlWhere.append("waste.edit_by, ");
        sqlWhere.append("waste.edit_time, ");
        sqlWhere.append("waste.corrosivity, ");
        sqlWhere.append("waste.toxicity, ");
        sqlWhere.append("waste.ignitability ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_WASTE );
        sqlWhere.append(" where waste.code like :code");
        map.put("code", "%"+keyword+"%");
        List<Waste> wasteList = namedjdbctemp.query(sqlWhere.toString(), map, new BeanPropertyRowMapper<Waste>(Waste.class));
        return wasteList;
    }



    @Override
    public List<Waste> getWasteNameDropDownList(String keyword)
            throws DaoAccessException {
        Map<String, Object> map = new  HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer("select  distinct waste.id, ");
        sqlWhere.append("waste.waste_type_id, ");
        sqlWhere.append("waste.calling_id, ");
        sqlWhere.append("waste.code, ");
        sqlWhere.append("waste.description, ");
        sqlWhere.append("waste.status, ");
        sqlWhere.append("waste.create_by, ");
        sqlWhere.append("waste.create_time, ");
        sqlWhere.append("waste.edit_by, ");
        sqlWhere.append("waste.edit_time, ");
        sqlWhere.append("waste.corrosivity, ");
        sqlWhere.append("waste.toxicity, ");
        sqlWhere.append("waste.ignitability, ");
        sqlWhere.append("wastename.name  as name ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_WASTE );
        sqlWhere.append(" join ").append(TableNameConstants.TABLE_WASTE_NAME).append(" wastename ");
        sqlWhere.append(" on waste.id = wastename.waste_id and wastename.name like :keyword ");
        map.put("keyword", "%"+StringUtils.trim(keyword)+"%");
        List<Waste> wasteList = namedjdbctemp.query(sqlWhere.toString(), map, new BeanPropertyRowMapper<Waste>(Waste.class));
        return wasteList;
    }



    @Override
    public void updateWasteEditTimeByWasteNameId(List<String> ids) throws DaoAccessException {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        StringBuffer tempSql = new StringBuffer("update " + TableNameConstants.TABLE_WASTE );
        tempSql.append(" set edit_time = SYSDATE() ");
        tempSql.append(" where id in ( ");
        tempSql.append(" select waste_id from ");
        tempSql.append(TableNameConstants.TABLE_WASTE_NAME);
        tempSql.append(" where id in (:wasteNameId) ");
        tempSql.append(" )");
        paramMap.put("wasteNameId", ids);
       
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
    }



	@Override
	public List<Waste> getAllWasteCode() throws DaoAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		StringBuffer sqlWhere = new StringBuffer(" select wasteType.id as waste_type_id,  ");
		sqlWhere.append(" wasteType.description as wasteTypeDesc, ");
		sqlWhere.append(" wasteType.code as wasteTypeCode, ");
		sqlWhere.append(" waste.id as id , ");
		sqlWhere.append(" waste.code , ");
		sqlWhere.append(" waste.description as description  ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_WASTE );
        sqlWhere.append(" inner join " + TableNameConstants.TABLE_WASTE_TYPE+" wasteType ");
        sqlWhere.append(" on waste.waste_type_id = wasteType.id " );
        sqlWhere.append(" order by waste.code ASC ");
        List<Waste> wasteList = namedjdbctemp.query(sqlWhere.toString(), paramMap, new BeanPropertyRowMapper<Waste>(Waste.class));
        return wasteList;
	}
}
