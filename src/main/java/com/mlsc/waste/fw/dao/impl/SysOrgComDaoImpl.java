package com.mlsc.waste.fw.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.waste.fw.dao.SysOrgComDao;
import com.mlsc.waste.fw.model.SysOrgCom;
import com.mlsc.waste.utils.TableNameConstants;
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
@Repository("SysOrgComDao")
public class SysOrgComDaoImpl extends EntityDaoSupport<SysOrgCom> implements SysOrgComDao {
    
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;

    @Override
    public List<SysOrgCom> getOrgComList(String sql, Map<String, Object> paramMap, PagingParameter paging) throws DaoAccessException {
        StringBuffer sqlWhere = new StringBuffer("select orgcom.* ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_SYS_ORG_COM +  " orgcom ");
        sqlWhere.append(" where orgcom.ComType = 2 ");// 机构的类型是2
        if (paramMap.get("comName") != null ) {
            sqlWhere.append(" and orgcom.ComName like :comName ");
        }
        if (paramMap.get("comCode") != null ) {
            sqlWhere.append(" and orgcom.ComCode like :comCode ");
        }
        sqlWhere.append(" group by orgcom.ComID ");
        sqlWhere.append(" order by orgcom.CreateTime desc");
        
        if (paging != null && paging.getLimit() != 0) {
            sqlWhere.append(" limit " + paging.getStart() + " , " + paging.getLimit() );
        }
        
        return namedjdbctemp.query(sqlWhere.toString(), paramMap, new BeanPropertyRowMapper<SysOrgCom>(SysOrgCom.class));
    }

    @Override
    public List<RPCSysOrgCom> querySysOrgComListByCantonID(String ticketId, int comType, String cantonCode) throws DaoAccessException {
        StringBuffer sqlWhere = new StringBuffer("select SOC.ComID as comId, ");
        sqlWhere.append("SOC.ComName as comName, ");
        sqlWhere.append("SOC.ComCode as comCode, ");
        sqlWhere.append("SOC.ComStatus as comStatus, ");
        sqlWhere.append("SOC.ComDesc as comDesc, ");
        sqlWhere.append("SOC.ComType as comType, ");
        sqlWhere.append("SOC.ComFunc as comFunc, ");
        sqlWhere.append("SOCCR.CantonCode as cantonCode, ");
        sqlWhere.append("SCD.POSX centerX, ");
        sqlWhere.append("SCD.POSY centerY, ");
        sqlWhere.append("SCD.COORDINATESYSTEM as coordinateSystem ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_SYS_ORG_COM +  " SOC ");
        sqlWhere.append(" inner join  " + TableNameConstants.TABLE_SYS_ORG_COM_CANTON_RELATION +  " SOCCR ON SOC.ComID = SOCCR.ComID ");
        sqlWhere.append(" left join  " + TableNameConstants.TABLE_SYS_COORDINATE +  " SCD ON SOC.CoordinateId = SCD.CoordinateId ");
        sqlWhere.append(" where SOC.ComType = :comType ");
        sqlWhere.append(" AND SOCCR.CantonCode = :cantonCode ");
        sqlWhere.append(" ORDER BY SOC.ComCode ");
        Map<String, Object> propertyMapping = new HashMap<String, Object>();
        propertyMapping.put("comType", comType);
        propertyMapping.put("cantonCode", cantonCode);
        return namedjdbctemp.query(sqlWhere.toString(), propertyMapping, new BeanPropertyRowMapper<RPCSysOrgCom>(RPCSysOrgCom.class));
    }

    @Override
    public List<RPCSysOrgCom> querySysOrgComListByParentCantonID( String ticketId, int comType, String cantonCode) throws DaoAccessException {
        StringBuffer sqlWhere = new StringBuffer("select SOC.ComID as comId, ");
        sqlWhere.append("SOC.ComName as comName, ");
        sqlWhere.append("SOC.ComCode as comCode, ");
        sqlWhere.append("SOC.ComStatus as comStatus, ");
        sqlWhere.append("SOC.ComDesc as comDesc, ");
        sqlWhere.append("SOC.ComType as comType, ");
        sqlWhere.append("SOC.ComFunc as comFunc, ");
        sqlWhere.append("SOCCR.CantonCode as cantonCode, ");
        sqlWhere.append("SCD.POSX centerX, ");
        sqlWhere.append("SCD.POSY centerY, ");
        sqlWhere.append("SCD.COORDINATESYSTEM as coordinateSystem ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_SYS_ORG_COM +  " SOC ");
        sqlWhere.append(" inner join  " + TableNameConstants.TABLE_SYS_ORG_COM_CANTON_RELATION +  " SOCCR ON SOC.ComID = SOCCR.ComID ");
        sqlWhere.append(" inner join  " + TableNameConstants.TABLE_SYS_CANYON +  " SC ON SOCCR.CantonCode = SC.cantoncode ");
        sqlWhere.append(" left join  " + TableNameConstants.TABLE_SYS_COORDINATE +  " SCD ON SOC.CoordinateId = SCD.CoordinateId ");
        sqlWhere.append(" where SOC.ComType = :comType ");
        sqlWhere.append(" AND SC.ParentCantonCode = :cantonCode ");
        sqlWhere.append(" ORDER BY SOC.ComCode ");
        Map<String, Object> propertyMapping = new HashMap<String, Object>();
        propertyMapping.put("comType", comType);
        propertyMapping.put("cantonCode", cantonCode);
        return namedjdbctemp.query(sqlWhere.toString(), propertyMapping, new BeanPropertyRowMapper<RPCSysOrgCom>(RPCSysOrgCom.class));
    }
    
    
}
