/**
 * 
 */
package com.mlsc.waste.licence.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceDetailVo;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
import com.mlsc.waste.licence.model.OperationLicenceVo;

import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 * 
 */
public interface LicenceApprovedDao extends EntityDao<OperationLicence> {

	/**
     * 查询审核许可证列表
     * @param sql
     * @param paramMap
     * @return
     * @throws DaoAccessException
     */
    List<OperationLicenceVo> list(String where, Map<String, Object> paramMap,PagingParameter paging) throws DaoAccessException;
    
    /**
     * 查询审核许可证记录数
     * @param where 查询条件 
     * @param params 查询条件参数
     * @return
     * @throws DaoAccessException
     */
    Integer count(String where, Map<String, Object> paramMap) throws DaoAccessException;
    
    /**
    * 许可证审核详细信息查询
    * @param ids
    * @throws DaoAccessException
    */
    OperationLicenceVo queryById(String id) throws DaoAccessException;
    
    /**
    * 许可证危废信息查询
    * @param id 许可证ID
    * @throws DaoAccessException
    */
    List<OperationLicenceItemVo> queryItemByLicenceId(String licenceId) throws DaoAccessException;
    
    /**
     * 根据licenceId，itemId在detail表中查询二位码
     * @param id 许可证ID
     * @throws DaoAccessException
     */
    List<OperationLicenceDetailVo> queryWasteTypeByLicenceId(String licenceId) throws DaoAccessException;
    
    /**
     * 根据licenceId，itemId在detail表中查询八位码
     * @param id 许可证ID
     * @throws DaoAccessException
     */
    List<OperationLicenceDetailVo> queryWasteCodeByLicenceId(String licenceId,String licenceItemId) throws DaoAccessException;

}
