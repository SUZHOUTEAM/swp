package com.mlsc.waste.fw.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.waste.fw.dao.SysOrgComCantonRelationDao;
import com.mlsc.waste.fw.model.SysOrgComCantonRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author sunjl
 * 
 */
@Repository("SysOrgComCantonRelationDao")
public class SysOrgComCantonRelationDaoImpl extends EntityDaoSupport<SysOrgComCantonRelation> implements SysOrgComCantonRelationDao {
    
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;

    
    
    
}
