package com.mlsc.waste.fw.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.waste.fw.model.SysCanton;

import java.util.List;

/**
 * @author sunjl
 * 
 */
public interface SysCantonDao extends EntityDao<SysCanton>{

	List<SysCanton> queryCantonNameByEnterpriseId(String enterId);

	SysCanton queryCantonNameByCantonCode(String cantonCode);
}
