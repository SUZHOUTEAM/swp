package com.mlsc.waste.enterprise.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.enterprise.dao.EnterpriseTypeDao;
import com.mlsc.waste.enterprise.model.WasteEnterpriseType;
import com.mlsc.waste.enterprise.model.WasteEnterpriseTypeBusiness;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EnterpriseTypeDaoImpl extends EntityDaoSupport<WasteEnterpriseType> implements EnterpriseTypeDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<WasteEnterpriseTypeBusiness> getEnterpriseTypesByEnterpriseId(String enterId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();

        StringBuffer querySql = new StringBuffer("select wasteEnterpriseType.id , ");
        querySql.append(" wasteEnterpriseType.enterprise_id , ");
        querySql.append(" wasteEnterpriseType.enterprise_type_id, ");
        querySql.append(" codevalue.code as enterpriseTypeCode, ");
        querySql.append(" codevalue.value as enterpriseTypeName ");
        querySql.append(" from ");
        querySql.append(TableNameConstants.TABLE_WASTE_ENTERPRISE_TYPE  + " wasteEnterpriseType ");
        querySql.append(" left join " + TableNameConstants.TABLE_CODE_VALUE  + " codevalue on wasteEnterpriseType.enterprise_type_id = codevalue.id ");
        querySql.append(" where wasteEnterpriseType.enterprise_id = :enterprise_id and valid = :valid");
        map.put("enterprise_id", enterId);
        map.put("valid", Constant.IS_VALID);
        
        List<WasteEnterpriseTypeBusiness> resultList = namedParameterJdbcTemplate.query(querySql.toString(), map,
                    new BeanPropertyRowMapper<WasteEnterpriseTypeBusiness>(WasteEnterpriseTypeBusiness.class));
         
        return resultList;
    }
    
    
}
