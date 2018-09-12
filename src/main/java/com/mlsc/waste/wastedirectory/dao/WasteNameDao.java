/**
 * 
 */
package com.mlsc.waste.wastedirectory.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.wastedirectory.model.WasteName;

import java.util.List;

/**
 * @author chenhh
 *
 */
public interface WasteNameDao extends EntityDao<WasteName> {
    
    /**
     * 危废名称删除
     * @param id
     * @throws DaoAccessException
     */
    void deleteByWasteId(String id) throws DaoAccessException;
    
    /**
     * 根据八位码查询危废名称
     * @param id
     * @throws DaoAccessException
     */
    List<WasteName> queryByWasteId(String id) throws DaoAccessException;
    
    /**
     * 根据八位码，危废名称模糊查询
     * @param wasteid
     * @param name
     * @throws DaoAccessException
     */
    List<WasteName> queryByNameAndWasteid(String wasteid,String name,String model) throws DaoAccessException;
    
    /**
     * 根据八位码和危废俗称查询危废信息
     * @param code
     * @param name
     * @return
     * @throws DaoAccessException
     */
    WasteName getWasteNameByCodeAndName(String code, String name) throws DaoAccessException;
    
    
    
}
