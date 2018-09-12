/**
 * 
 */
package com.mlsc.waste.wastedirectory.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.wastedirectory.model.WasteType;

import java.util.List;
import java.util.Map;

/**
 * @author chenhh
 * 危废类别
 */
public interface WasteTypeDao extends EntityDao<WasteType> {
    
    /**
     * 通过危废代码来查询数据的件数
     * 
     * @author sunjl date 2016-06-07
     */
    int getWasteTypeCountByCode(String id,String code) throws DaoAccessException;
    
    /**
     * 查询危废类别列表
     * @param sql
     * @param paramMap
     * @return
     * @throws DaoAccessException
     */
    List<WasteType> list(String where, Map<String, Object> paramMap,PagingParameter paging) throws DaoAccessException;
    
    /**
     * 查询危废类别记录数
     * @param where 查询条件 
     * @param params 查询条件参数
     * @return
     * @throws DaoAccessException
     */
    Integer count(String where, Map<String, Object> paramMap) throws DaoAccessException;
    
    /**
     * 查询二位危废类别
     * @param where 查询条件 
     * @param params 查询条件参数
     * @return
     * @throws DaoAccessException
     */
    List<Map<String, Object>> search(Object... params) throws DaoAccessException;
    /**
     * 查询所有的二位码类别名称
     * @return
     */
    List<WasteType> queryAllWasteType() throws DaoAccessException;
}
