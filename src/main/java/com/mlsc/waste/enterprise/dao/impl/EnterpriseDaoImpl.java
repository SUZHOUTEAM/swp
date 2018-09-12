package com.mlsc.waste.enterprise.dao.impl;

import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.enterprise.dao.EnterpriseDao;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.wastecircle.model.EnterpriseVo;
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
 * 
 */
@Repository
public class EnterpriseDaoImpl implements EnterpriseDao {

    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;


    @Override
    public RPCSysEnterpriseBaseVo getCantonNameByEnterpriseId(String enterpriseId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer querySql = new StringBuffer("select CantonName");
        querySql.append(" from sys_canton");
        querySql.append(" LEFT JOIN sys_enterprise_base");
        querySql.append(" on sys_canton.CantonCode = left(sys_enterprise_base.CantonCode,2)");
        querySql.append(" where sys_enterprise_base.entId = '" + enterpriseId + "'");
        querySql.append(" and sys_canton.IsEndNode = '0'");
        List<RPCSysEnterpriseBaseVo> resultList = namedjdbctemp.query(querySql.toString(), map, new BeanPropertyRowMapper<RPCSysEnterpriseBaseVo>(RPCSysEnterpriseBaseVo.class));
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public RPCSysEnterpriseBaseVo getDistrictByEnterpriseId(String enterpriseId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer querySql = new StringBuffer("select CantonName");
        querySql.append(" from sys_canton");
        querySql.append(" LEFT JOIN sys_enterprise_base");
        querySql.append(" on sys_canton.CantonCode = sys_enterprise_base.CantonCode ");
        querySql.append(" where sys_enterprise_base.entId = '" + enterpriseId + "'");
        List<RPCSysEnterpriseBaseVo> resultList = namedjdbctemp.query(querySql.toString(), map, new BeanPropertyRowMapper<RPCSysEnterpriseBaseVo>(RPCSysEnterpriseBaseVo.class));
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }


    @Override
    public List<EnterpriseVo> getEnterpriseVosByName(String enterpriseName,String enterpriseType) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer();
        sqlWhere.append("SELECT e.entId,e.entName,e.entAddress, ");
        sqlWhere.append(" u.business_code as businessCode,u.file_id as fileId, ");
        sqlWhere.append(" entType.code as entTypeCode,entType.value as entTypeValue ");
        sqlWhere.append(" from sys_enterprise_base e ");
        sqlWhere.append(" left JOIN waste_enterprise_type enterpriseType on enterpriseType.enterprise_id=e.entId ");
        sqlWhere.append(" LEFT JOIN code_value entType ON enterpriseType.enterprise_type_id = entType.id ");
        sqlWhere.append(" LEFT JOIN upload_file u on u.reference_id=e.entId and u.file_type='a' ");
        sqlWhere.append(" LEFT JOIN enterprise_extended ex ON e.entId = ex.sys_enterprise_base_id AND ex.valid = '1' ");
        sqlWhere.append(" LEFT JOIN code_value entStatus ON ex.enterprise_status = entStatus.id ");
        sqlWhere.append(" where e.entName like '%" + enterpriseName + "%' ");
        sqlWhere.append(" AND entStatus.code = '"+CodeTypeConstant.USER_EVENT_STATUS_PASS+"' ");
        if(StringUtils.isNoneBlank(enterpriseType)){
        	 sqlWhere.append(" AND entType.code = '"+enterpriseType+"' ");
        }
        sqlWhere.append(" limit 100");
        List<EnterpriseVo> queryList = namedjdbctemp.query(sqlWhere.toString(), map, new BeanPropertyRowMapper<EnterpriseVo>(EnterpriseVo.class));
        return queryList;
    }

    @Override
    public List<EnterpriseVo> getCZEnterpriseVosByName(String enterpriseName) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer();
        sqlWhere.append("SELECT e.entId,e.entName,e.entAddress, ");
        sqlWhere.append(" u.business_code as businessCode,u.file_id as fileId, ");
        sqlWhere.append(" entType.code as entTypeCode,entType.value as entTypeValue ");
        sqlWhere.append(" from sys_enterprise_base e ");
        sqlWhere.append(" left JOIN waste_enterprise_type enterpriseType on enterpriseType.enterprise_id=e.entId ");
        sqlWhere.append(" LEFT JOIN code_value entType ON enterpriseType.enterprise_type_id = entType.id ");
        sqlWhere.append(" LEFT JOIN upload_file u on u.reference_id=e.entId and u.file_type='a' ");
        sqlWhere.append(" LEFT JOIN enterprise_extended ex ON e.entId = ex.sys_enterprise_base_id AND ex.valid = '1' ");
        sqlWhere.append(" LEFT JOIN code_value entStatus ON ex.enterprise_status = entStatus.id ");
        sqlWhere.append(" where e.entName like '%" + enterpriseName + "%' ");
        sqlWhere.append(" AND entStatus.code = '"+CodeTypeConstant.USER_EVENT_STATUS_PASS+"' ");
        sqlWhere.append(" AND (entType.code ='DISPOSITION' or entType.code ='DIS_FACILITATOR') ");
        sqlWhere.append(" limit 100");
        List<EnterpriseVo> queryList = namedjdbctemp.query(sqlWhere.toString(), map, new BeanPropertyRowMapper<EnterpriseVo>(EnterpriseVo.class));
        return queryList;
    }


}
