package com.mlsc.waste.licence.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.mobileservice.model.DispositionCapacityDetailReleaseVo;
import com.mlsc.waste.licence.model.OperationLicenceDetail;

import java.util.List;
import java.util.Map;

public interface LicenceDetailDao extends EntityDao<OperationLicenceDetail> {

    /**
     * 根据 licence_id operation_item_id删除detail表中的相关数据
     * 
     * @param licence_id
     * @param itemId
     * @throws DaoAccessException
     */
    void deleteDetails(String licenceId, String itemId) throws DaoAccessException;

    /**
     * 根据许可证ID来删除datails(vaild=0) zhugl
     * 
     * @param operationLicenceDetail
     *            @
     */
    void deleteLicenceDetailByLicenceIds(List<String> licenceIds, Map<String, Object> paramMap) throws DaoAccessException;

    int isHasLicenceDetail(String licenceId, String licenceItemId, String wasteTypeId, String wasteId, String wasteNameId) throws DaoAccessException;

    /**
     * 判断许可证是否有可处置量
     * 
     * @param licenceId
     * @param dispositionType
     * @param planQuantity
     * @param wasteTypeId
     * @param wasteId
     * @return
     * @throws DaoAccessException
     */
    int checkLicenceStatus(String licenceId, String dispositionType, String wasteTypeId, String wasteId) throws DaoAccessException;

    /**
     * 获取当前危废的可处置的处置方式
     * 根据当前企业的有效许可证ID，剩余量，二位码，八位码来判断当前危废的可处置的处置方式（在表operation_licence_detail）
     */
    List<Map<String, Object>> listDispositionType(String licenceId, String planQuantity, String wasteTypeId, String wasteId) throws DaoAccessException;

    /**
     * 获取许可证的可处置量
     * 
     * @param licenceId
     * @param dispositionType
     * @param planQuantity
     * @param wasteTypeId
     * @param wasteId
     * @return
     * @throws DaoAccessException
     */
    String getProcessQuantityByWasteIdAndWasteTypeId(String licenceId, String dispositionType, String wasteTypeId, String wasteId) throws DaoAccessException;

    /**
     * 获取许可证状态是否有效
     * 
     * @param licenceId
     * @return
     * @throws DaoAccessException
     */
    String getLicenceDetailStatus(String licenceId) throws DaoAccessException;

    /**
     * 根据许可证ID和产废企业的id，查询可处置的危废
     * 
     * @param licenceId
     * @param entId
     * @return
     * @throws DaoAccessException
     */
    List<DispositionCapacityDetailReleaseVo> getDispositionCapacityDetailReleaseCanList(String licenceId, String entId) throws DaoAccessException;

    /**
     * 根据许可证ID和产废企业的id，查询不可处置的危废
     * 
     * @param licenceId
     * @param entId
     * @return
     * @throws DaoAccessException
     */
    List<DispositionCapacityDetailReleaseVo> getDispositionCapacityDetailReleaseNoList(String licenceId, String entId) throws DaoAccessException;
}
