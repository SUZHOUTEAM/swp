package com.mlsc.waste.enterprise.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.waste.enterprise.model.SysEnterpriseBase;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanghj on 2017/7/13.
 */
public interface SysEnterpriseBaseDao extends EntityDao<SysEnterpriseBase>{

    List<SysEnterpriseBase> listSysEnterpriseBase(String whereSql,Map<String,Object> paramMap);

}
