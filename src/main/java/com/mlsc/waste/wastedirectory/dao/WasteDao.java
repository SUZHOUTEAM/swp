/**
 * 
 */
package com.mlsc.waste.wastedirectory.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteType;

import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 * 危废（八位码）
 */
public interface WasteDao extends EntityDao<Waste> {
    
    /**
     * 危废类别的查询
     * 
     * @author sunjl date 2016-06-07
     */
    Waste queryByCode(String code) throws DaoAccessException;
    
    /**
     * 查询危废名录列表
     * @param sql
     * @param paramMap
     * @return
     * @throws DaoAccessException
     */
    List<Waste> list(String where, Map<String, Object> paramMap,PagingParameter paging) throws DaoAccessException;
    
    /**
     * 查询危废名录记录数
     * @param where 查询条件 
     * @param params 查询条件参数
     * @return
     * @throws DaoAccessException
     */
    Integer getCount(String where, Map<String, Object> paramMap) throws DaoAccessException;
    
    /**
     * 查询ID
     * @param code
     * @return
     * @throws DaoAccessException
     */
    ResultData<WasteType> getIdByCode(String code) throws DaoAccessException;
    
    /**
     * 根据八位码模糊查询
     */
    List<Waste> queryByWasteCode(String code) throws DaoAccessException;
    /**
     * 根据二位码查询对应的八位码
     * @param wasteTypeId
     * @return
     * @throws DaoAccessException
     */
    List<Waste> queryByWasteTypeId(String wasteTypeId) throws DaoAccessException;

    /**
     * 根据code模糊查询出wasteList
     * @return
     * @throws DaoAccessException
     */
    List<Waste> getCodeWasteDropDownList(String keyword) throws DaoAccessException;

    /**
     * 根据危废名称模糊查询出wasteList
     * @param keyword
     * @return
     * @throws DaoAccessException
     */
    List<Waste> getWasteNameDropDownList(String keyword) throws DaoAccessException;

    void updateWasteEditTimeByWasteNameId(List<String> ids)  throws DaoAccessException;

	List<Waste> getAllWasteCode() throws DaoAccessException;
}
