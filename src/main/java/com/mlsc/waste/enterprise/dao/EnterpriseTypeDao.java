package com.mlsc.waste.enterprise.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.enterprise.model.WasteEnterpriseType;
import com.mlsc.waste.enterprise.model.WasteEnterpriseTypeBusiness;

import java.util.List;

public interface EnterpriseTypeDao extends EntityDao<WasteEnterpriseType> {

    List<WasteEnterpriseTypeBusiness> getEnterpriseTypesByEnterpriseId(String enterId) throws DaoAccessException;
}
