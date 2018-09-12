/**
 * 
 */
package com.mlsc.waste.licence.service;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.licence.model.OperationLicenceDetailVo;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
import com.mlsc.waste.licence.model.OperationLicenceVo;

import java.util.List;
import java.util.Map;


/**
 * @author sunjl
 *
 */
public interface LicenceApprovedService {
	
	/**
     * 查询审核许可证列表
     * 
     * @param sql
     * @param paramMap
     * @return
	 * @throws Exception 
     * @throws DaoAccessException
     */
    List<OperationLicenceVo> list(String sql, Map<String, Object> paramMap,PagingParameter paging) throws Exception;
    
    /**
     * 件数
     * @param where 查询条件 
     * @param params 查询条件参数
     * @return
     * @throws Exception 
     * @throws DaoAccessException
     */
    Integer count(String where, Map<String, Object> params) throws Exception ;
    
   /**
    * 许可证审核通过
    * @param licenceIds
    * @param ticketId
    * @throws Exception
    */
    void confirmPass(List<String> licenceIds, String ticketId) throws Exception ;
    
    /**
     * 许可证审核退回
     * @param licenceIds
     * @param ticketId
     * @throws Exception
     */
    void confirmReject(List<String> licenceIds, String ticketId) throws Exception ;
     
    /**
     * 许可证审核详细信息查询
     * @param licenceId
     * @throws Exception 
     */
    OperationLicenceVo getOperationLicenceVoById(String licenceId) throws Exception ;
    
    List<OperationLicenceItemVo> getItemByLicenceId(String licenceId) throws Exception ;
    
    List<OperationLicenceDetailVo> getWasteCodeByLicenceId(String licenceId,String licenceItemId) throws Exception;
    
    /**
     * 根据许可证ID从item,detail表抽取详细的危废信息
     * @param id
     * @throws Exception 
     */
    List<OperationLicenceItemVo> getWasteInfoByLicenceId(String id) throws Exception ;
    
    /**
     * 查询数据字典
     * @param typeCode
     * @return
     * @throws Exception 
     */
    List<CodeValue> getCodeValuesTypeCode(String typeCode) throws Exception;

    /**
     * 批量添加处置企业的处置能力
     * @param licenceIds
     * @param ticketId
     * @throws Exception 
     */
    void saveDispositionCapacityByLicIds(List<String> licenceIds, String ticketId) throws Exception;

	void termination(List<String> licenceIds, String ticketId)throws Exception;;
}
