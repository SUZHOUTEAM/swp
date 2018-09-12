/**
 * 
 */
package com.mlsc.waste.wastedirectory.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.wastedirectory.model.WasteType;

import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 * 危废类别
 */
public interface WasteTypeService {
     
    /**
     * 危废类别表的保存或更新
     * @param model
     * @param id
     * @throws DaoAccessException
     */
    String saveWasteType(WasteType wasteType,String ticketId) throws DaoAccessException;
    
    /**
     * 危废类别表的保存或更新
     * @param model
     * @param id
     * @throws DaoAccessException
     */
    void updateWasteType(WasteType wasteType,String ticketId)throws DaoAccessException;
     
     /**
      * 危废类别的查找
      * @param id
      * @return
      * @throws DaoAccessException
      */
    WasteType getWasteTypeById(String id) throws DaoAccessException;
    
    /**
     * 危废类别的查找
     * @param id
     * @return
     * @throws DaoAccessException
     */
    WasteType getWasteTypeByWasteCode(String wasteCode) throws DaoAccessException;
     
     /**
      * 危废类别的删除
      * @param ids
      * @throws DaoAccessException
      */
    void removeWasteType(List<String> ids) throws DaoAccessException;
     
     /**
      * 通过危废代码来查询数据的件数
      * true:存在；false:不存在
      * @author sunjl date 2016-06-15
     * @throws DaoAccessException 
      */
    boolean isWasteTypeCodeExistent(String id,String code) throws DaoAccessException;
    
    /**
     * 危废类别的停用与启用
     * 
     * @author sunjl date 2016-06-06
     * @throws DaoAccessException 
     */
    void updateWasteTypeSataus(List<String> ids,String status,String ticketId) throws DaoAccessException;
    
    /**
     * 查询危废类别列表
     * @param sql
     * @param paramMap
     * @return
     * @throws DaoAccessException
     */
    List<WasteType> list(String sql, Map<String, Object> paramMap, PagingParameter paging) throws DaoAccessException;
    
    /**
     * 
     * @param where 查询条件 
     * @param params 查询条件参数
     * @return
     * @throws DaoAccessException
     */
    Integer count(String where, Map<String, Object> params) throws DaoAccessException;
    
    /**
     * 获取危废类别的ID与描述
     * @param sql
     * @param args
     * @return
     * @throws DaoAccessException
     */
    List<Map<String, Object>> getWasteTypeIdDescription(Object... args);
    
    /**
     * 查询所有的二位码
     * @return
     * @throws DaoAccessException
     */
    List<WasteType> getAllWateType() throws DaoAccessException;
    
}
