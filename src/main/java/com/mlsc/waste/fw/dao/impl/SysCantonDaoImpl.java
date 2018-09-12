package com.mlsc.waste.fw.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.waste.fw.dao.SysCantonDao;
import com.mlsc.waste.fw.model.SysCanton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 * 
 */
@Repository("SysCantonDao")
public class SysCantonDaoImpl extends EntityDaoSupport<SysCanton> implements SysCantonDao {
    
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;

	@Override
	public List<SysCanton> queryCantonNameByEnterpriseId(String enterId) {
		Map<String, String> paraMap = new HashMap<>();
		StringBuffer tempSql = new StringBuffer();
		tempSql.append("select ");
		tempSql.append("case when sys_canton.CantonLevel = '3' ");
		tempSql.append("then ( ");
		tempSql.append("select concat(parent.CantonName,' ',subparent.CantonName,' ',sub.CantonName) as cantonname ");
		tempSql.append("from sys_canton parent inner join sys_canton subparent on parent.CantonCode  = subparent.ParentCantonCode ");
		tempSql.append("inner join sys_canton sub on subparent.CantonCode = sub.ParentCantonCode ");
		tempSql.append("where sub.CantonCode = sys_canton.CantonCode ");
		tempSql.append(")  ");
		tempSql.append("when sys_canton.CantonLevel = '2' THEN( ");
		tempSql.append("select concat(parent.CantonName,' ',subparent.CantonName) as cantonname ");
		tempSql.append("from sys_canton parent inner join sys_canton subparent on parent.CantonCode  = subparent.ParentCantonCode ");
		tempSql.append("where subparent.CantonCode =sys_canton.CantonCode) ");
		tempSql.append("when sys_canton.CantonLevel = '1' THEN( ");
		tempSql.append("select parent.CantonName as cantonname ");
		tempSql.append("from sys_canton parent  ");
		tempSql.append("where parent.CantonCode = sys_canton.CantonCode  ");
		tempSql.append(") ");
		tempSql.append("end as CantonName ");
		tempSql.append("from sys_canton ");
		tempSql.append("inner JOIN sys_org_com_canton_relation ");
		tempSql.append("on sys_canton.CantonCode = sys_org_com_canton_relation.CantonCode ");
		tempSql.append("where sys_org_com_canton_relation.ComID = :entId ");
		paraMap.put("entId", enterId);
		
		List<SysCanton> list = namedParameterJdbcTemplate.query(tempSql.toString(), paraMap, new BeanPropertyRowMapper<SysCanton>(SysCanton.class));
        return list;
	}


	@Override
	public SysCanton queryCantonNameByCantonCode(String cantonCode) {
		Map<String, String> paraMap = new HashMap<>();
		StringBuffer tempSql = new StringBuffer();
		tempSql.append("select ");
		tempSql.append("case when sys_canton.CantonLevel = '3' ");
		tempSql.append("then ( ");
		tempSql.append("select case when subparent.CantonName = '县' then concat(parent.CantonName,' ',sub.CantonName) ");
		tempSql.append("else concat(parent.CantonName,' ',subparent.CantonName,' ',sub.CantonName) end as cantonname  ");
		tempSql.append("from sys_canton parent inner join sys_canton subparent on parent.CantonCode  = subparent.ParentCantonCode ");
		tempSql.append("inner join sys_canton sub on subparent.CantonCode = sub.ParentCantonCode ");
		tempSql.append("where sub.CantonCode = sys_canton.CantonCode ");
		tempSql.append(")  ");
		tempSql.append("when sys_canton.CantonLevel = '2' THEN( ");
		tempSql.append("select concat(parent.CantonName,' ',subparent.CantonName) as cantonname ");
		tempSql.append("from sys_canton parent inner join sys_canton subparent on parent.CantonCode  = subparent.ParentCantonCode ");
		tempSql.append("where subparent.CantonCode =sys_canton.CantonCode) ");
		tempSql.append("when sys_canton.CantonLevel = '1' THEN( ");
		tempSql.append("select parent.CantonName as cantonname ");
		tempSql.append("from sys_canton parent  ");
		tempSql.append("where parent.CantonCode = sys_canton.CantonCode  ");
		tempSql.append(") ");
		tempSql.append("end as CantonName ");
		tempSql.append("from sys_canton ");
		tempSql.append("where sys_canton.CantonCode = :cantonCode ");
		paraMap.put("cantonCode", cantonCode);

		List<SysCanton> list = namedParameterJdbcTemplate.query(tempSql.toString(), paraMap, new BeanPropertyRowMapper<SysCanton>(SysCanton.class));
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
    
