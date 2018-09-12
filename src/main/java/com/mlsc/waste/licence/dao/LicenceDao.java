package com.mlsc.waste.licence.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceVo;

import java.util.List;
import java.util.Map;

public interface LicenceDao extends EntityDao<OperationLicence> {

    /**
     * 查询历史许可证列表
     * @param where
     * @param paramMap
     * @return
     * @throws DaoAccessException
     */
    List<OperationLicenceVo> list(String where, Map<String, Object> paramMap,PagingParameter paging,String enterpriseId) throws DaoAccessException;
    
    /**
     * 查询历史许可证记录数
     * @param where 查询条件 
     * @param paramMap 查询条件参数
     * @param enterpriseId 查询条件参数
     * @return
     * @throws DaoAccessException
     */
    Integer count(String where, Map<String, Object> paramMap,String enterpriseId) throws DaoAccessException;
    
    /**
     * 取得有效许可证Id
     * @param enterpriseId 查询条件
     * @return
     * @throws DaoAccessException
     */
    List<OperationLicence> getValidLicIdByEnterpriseId(String enterpriseId) throws DaoAccessException;
    
    /**
     * 获取该许可证的审核状态
     * @return
     * @throws DaoAccessException
     */
    String getAuditStatusByLicenceId(String id) throws DaoAccessException;

    void deleteLicenceByLicenceIds(List<String> licenceIds, Map<String, Object> paramMap) throws DaoAccessException;

    /**
     * 查询许可证编号是否存在
     * @param licenceNo,licenceId
     * @return
     * @throws DaoAccessException
     */
    int queryLicenceNoIsExist(String licenceNo,String licenceId) throws DaoAccessException;
    
    /**
     * 验证许可证有效期是否重复
     * @param entId
     * @param startDate
     * @param endDate
     * @return 
     * @throws DaoAccessException
     */
    Integer isValidityPeriodRepeat(String entId, String licenceId, String startDate, String endDate) throws DaoAccessException;

    /**
     * 获取许可证 zhugl
     * @param licenceId 
     * @return
     */
    OperationLicenceVo getlicenceVoById(String licenceId) throws DaoAccessException;
    
    /**
     * 许可证审核状态的更新 zhugl
     * @param ids
     * @throws DaoAccessException
     */
    void updateAuditStatus(List<String> licenceIds, Map<String, Object> paramMap) throws DaoAccessException;

    /**
     * 查询是否存在无效许可证
     * @param enterpriseId
     * @return
     * @throws DaoAccessException
     */
    int getUsedLicId(String enterpriseId)  throws DaoAccessException;

    int isHasDetails(String licenceId) throws DaoAccessException;
    
    void updateEditTimeByWasteId(String wasteId) throws DaoAccessException;
    
    void updateEditTimeByWasteNameId(List<String> wasteNameIds) throws DaoAccessException;
    
    int getProcessableTotalCount(String licenceId);

    List<OperationLicence> listLiceneByEnterpriseIdAndStatus(String enterpriseId,String status) throws DaoAccessException;

    List<OperationLicenceVo> getSubmitList(String where, Map<String, Object> paramMap, PagingParameter paging, String enterpriseId) throws DaoAccessException;
}
