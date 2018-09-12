package com.mlsc.waste.licence.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.licence.model.OperationLicenceItem;

import java.util.List;
import java.util.Map;

public interface LicenceItemDao extends EntityDao<OperationLicenceItem>{

    /**
     * 根据许可证ID来删除operationLicenceItem(vaild=0) zhugl
     * 
     * @param operationLicenceItem
     * @ 
     */
    void deleteLicenceItemByLicenceIds(List<String> licenceIds, Map<String, Object> paramMap) throws DaoAccessException;
    
}
