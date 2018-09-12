package com.mlsc.waste.licence.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.licence.dao.LicenceItemDao;
import com.mlsc.waste.licence.model.OperationLicenceItem;
import com.mlsc.waste.utils.TableNameConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public class LicenceItemDaoImpl extends EntityDaoSupport<OperationLicenceItem> implements LicenceItemDao {
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;

    @Override
    public void deleteLicenceItemByLicenceIds(List<String> licenceIds, Map<String, Object> paramMap) throws DaoAccessException{
        StringBuffer tempSql = new StringBuffer("update " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM );
        tempSql.append(" set valid = :valid, ");
        tempSql.append(" edit_by = :editBy, ");
        tempSql.append(" edit_time = :editTime ");
        tempSql.append(" where licence_id in (:licenceIds) ");
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
    }
}
