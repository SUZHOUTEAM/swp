/**
 * 
 */
package com.mlsc.waste.wastedirectory.service;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteName;
import com.mlsc.waste.wastedirectory.model.WasteTypeVo;

import java.util.List;
import java.util.Map;

/**
 * @author sunjl 危废（八位码）
 */
public interface WasteService {


    /**
     * 
     * @param id
     * @return
     * @throws DaoAccessException
     */
    Waste getWasteById(String id);

    /**
     * 危废类别的查询
     * 
     * @param code
     * @return
     * @throws DaoAccessException
     */
    Waste getWasteByCode(String code) throws Exception;

    /**
     * 危废类别的停用与启用
     * 
     * @author sunjl date 2016-06-06
     */
    void updateSataus(List<String> ids, String status, String ticketId) throws Exception;

    /**
     * 查询危废名录列表
     * 
     * @param sql
     * @param paramMap
     * @return
     * @throws DaoAccessException
     */
    List<Waste> list(String sql, Map<String, Object> paramMap, PagingParameter paging) throws DaoAccessException;

    /**
     * 
     * @param where
     *            查询条件
     * @param params
     *            查询条件参数
     * @return
     * @throws DaoAccessException
     */
    Integer count(String where, Map<String, Object> params) throws DaoAccessException;


    /**
     * 根据危废类别（二位码）查询对应的八位码
     * 
     * @param wasteTypeId
     * @return
     * @throws DaoAccessException
     */
    List<Waste> getWastesByWasteTypeId(String wasteTypeId);

    /**
     * 危废类别
     * 
     * @param args
     * @return
     * @throws DaoAccessException
     */
    List<Map<String, Object>> getWasteTypeList(Object... args);


    /**
     * 获取危废名称
     * 
     * @param wasteId
     * @return
     */
    List<WasteName> getWasteNamesByWasteId(String wasteId);


    /**
     * 删除危废及危废名称
     * 
     * @param ids
     * @throws Exception
     */
    void removeWasteByIds(List<String> ids) throws Exception;

    /**
     * @param typeCode
     * @return
     */
    List<CodeValue> getDropDownListByTypeCode(String typeCode);

    /**
     * 根据code模糊回去waste
     * 
     * @param keyword
     * @return
     */
    List<Waste> getCodeWasteDropDownList(String keyword);

    /**
     * 获取危废名称
     * 
     * @param keyword
     * @return
     */
    List<Waste> getWasteNameDropDownList(String keyword);

    List<WasteTypeVo>  getAllWasteCode();
}
