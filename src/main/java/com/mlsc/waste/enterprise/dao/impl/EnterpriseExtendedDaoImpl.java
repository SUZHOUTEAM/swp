/**
 * 
 */
package com.mlsc.waste.enterprise.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.enterprise.dao.EnterpriseExtendedDao;
import com.mlsc.waste.enterprise.model.EnterpriseExtended;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.utils.TableNameConstants;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhugl
 *
 */
@Repository
public class EnterpriseExtendedDaoImpl extends EntityDaoSupport<EnterpriseExtended> implements EnterpriseExtendedDao {

    @Override
    public void updateByEnpId(String enterpriseId, String userEventStatus,String valid) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" update " + TableNameConstants.TABLE_ENTERPRISE_EXTENDED +" set enterprise_status = :enterprise_status,valid = :valid where sys_enterprise_base_id = :sys_enterprise_base_id ");
        map.put("enterprise_status", userEventStatus);
        map.put("sys_enterprise_base_id", enterpriseId);
        map.put("valid", valid);
        namedParameterJdbcTemplate.update(tempSql.toString(), map);
        
    }

    @Override
    public List<RPCSysEnterpriseBaseVo> getAllEnterpriseStatusList(String sqlWhere,
            Map<String, Object> paramMap, PagingParameter paging,String codeValueId ) {
        StringBuffer sql = new StringBuffer("select enterprisebase.entId,enterprisebase.entName,enterprisebase.entCode, sysuser.ChineseName as userName,sysuser.PhoneNum,date_format(enterprisebase.createTime, '%Y-%m-%d') as createTime, ");
        sql.append("codevalue.code as enterpriseStatus, ");
        sql.append("codevalue.value as enterpriseStatusLabel, ");
        sql.append("enterprisebase.responsibleArea as responsibleArea ");
        sql.append(" from " + TableNameConstants.TABLE_SYS_ENTERPRISE_BASE +  " enterprisebase ");
        sql.append(" inner join " + TableNameConstants.TABLE_ENTERPRISE_EXTENDED +  " enterpriseExtended ");
        sql.append(" on enterprisebase.entId = enterpriseExtended.sys_enterprise_base_id " );
        sql.append(" left join " + TableNameConstants.TABLE_CODE_VALUE +  " codevalue ");
        sql.append(" on codevalue.id = enterpriseExtended.enterprise_status " );
        sql.append(" left join " + TableNameConstants.TABLE_SYS_USER +  " sysuser ");
        sql.append(" on sysuser.UserId = enterprisebase.CreaterID " );
        sql.append(" where 1=1 ");
        sql.append(sqlWhere);
        
        if(sql.toString().indexOf("enterpriseStatusLabel like :enterpriseStatusLabel")==-1){
        	sql.append("and  enterpriseExtended.enterprise_status = :enterpriseStatus");
        	paramMap.put("enterpriseStatus", codeValueId );
        }else{
        	 sql = new StringBuffer(sql.toString().replaceAll(" enterpriseStatusLabel like :enterpriseStatusLabel ", " enterpriseExtended.enterprise_status like :enterpriseStatusLabel "));
        }
        
       

        
        sql.append(" group by enterprisebase.entId ");
        sql.append(" order by enterprisebase.createTime desc");
        
        if (paging != null && paging.getLimit() != 0) {
            sql.append(" limit " + paging.getStart() + " , " + paging.getLimit() );
        }
        
        return namedParameterJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper<RPCSysEnterpriseBaseVo>(RPCSysEnterpriseBaseVo.class));
    }
    
    @Override
    public int getAllEnterpriseStatusCount(String sqlWhere,Map<String, Object> paramMap,String codeValueId) {
        StringBuffer sql = new StringBuffer("select count(enterprisebase.entId) ");
        sql.append(" from " + TableNameConstants.TABLE_SYS_ENTERPRISE_BASE +  " enterprisebase ");
        sql.append(" inner join " + TableNameConstants.TABLE_ENTERPRISE_EXTENDED +  " enterpriseExtended ");
        sql.append(" on enterprisebase.entId = enterpriseExtended.sys_enterprise_base_id " );
        sql.append(" left join " + TableNameConstants.TABLE_CODE_VALUE +  " codevalue ");
        sql.append(" on codevalue.id = enterpriseExtended.enterprise_status " );
        sql.append(" left join " + TableNameConstants.TABLE_SYS_USER +  " sysuser ");
        sql.append(" on sysuser.UserId = enterprisebase.CreaterID " );
        sql.append(" where 1=1 ");
        sql.append(sqlWhere);
        
        if(sql.toString().indexOf("enterpriseStatusLabel like :enterpriseStatusLabel")==-1){
        	sql.append("and  enterpriseExtended.enterprise_status = :enterpriseStatus");
        	paramMap.put("enterpriseStatus", codeValueId );
        }else{
        	sql = new StringBuffer(sql.toString().replaceAll(" enterpriseStatusLabel like :enterpriseStatusLabel ", " enterpriseExtended.enterprise_status like :enterpriseStatusLabel "));
        }
        

        
        return namedParameterJdbcTemplate.queryForObject(sql.toString(),paramMap, Integer.class);    
     }

}
