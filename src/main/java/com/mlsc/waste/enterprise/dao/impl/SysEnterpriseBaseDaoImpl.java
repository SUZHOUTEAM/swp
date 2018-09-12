package com.mlsc.waste.enterprise.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.utils.StringUtils;
import com.mlsc.waste.enterprise.dao.SysEnterpriseBaseDao;
import com.mlsc.waste.enterprise.model.SysEnterpriseBase;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghj on 2017/7/13.
 */
@Repository
public class SysEnterpriseBaseDaoImpl extends EntityDaoSupport<SysEnterpriseBase> implements SysEnterpriseBaseDao{


    @Override
    public List<SysEnterpriseBase> listSysEnterpriseBase(String whereSql, Map<String, Object> paramMap) {
        if(paramMap==null){
            paramMap = new HashMap<String,Object>();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ent.* FROM ").append(TableNameConstants.TABLE_SYS_ENTERPRISE_BASE);
        sb.append(" AS ent LEFT JOIN ").append(TableNameConstants.TABLE_ENTERPRISE_EXTENDED).append(" AS ex ");
        sb.append(" ON ent.entId = ex.sys_enterprise_base_id LEFT JOIN ").append(TableNameConstants.TABLE_CODE_VALUE);
        sb.append(" AS cv ON ex.enterprise_status = cv.id ");
        sb.append(" WHERE 1=1 AND entStatus = :validStatus ");
        if(StringUtils.isNotNullOrEmpty(whereSql)){
            sb.append(whereSql);
        }
        paramMap.put("validStatus", Constant.VALID_STATUS);
        return this.namedParameterJdbcTemplate.query(sb.toString(),paramMap,new
                BeanPropertyRowMapper<SysEnterpriseBase>(SysEnterpriseBase.class));
    }
}
