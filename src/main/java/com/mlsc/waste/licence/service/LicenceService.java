/**
 *
 */
package com.mlsc.waste.licence.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.mobileservice.model.DispositionCapacityDetailReleaseVo;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceDetailExtend;
import com.mlsc.waste.licence.model.OperationLicenceDetailVo;
import com.mlsc.waste.licence.model.OperationLicenceItem;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
import com.mlsc.waste.licence.model.OperationLicenceVo;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteType;

import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 */
public interface LicenceService {

    /**
     * 查询历史许可证列表
     *
     * @param sql
     * @param paramMap
     * @param paging
     * @return
     * @throws Exception
     */
    List<OperationLicenceVo> list(String sql, Map<String, Object> paramMap, PagingParameter paging, String enterpriseId) throws Exception;

    /**
     * @param where  查询条件
     * @param params 查询条件参数
     * @return
     * @throws Exception
     */
    Integer count(String where, Map<String, Object> params, String enterpriseId) throws Exception;

    OperationLicence getValidLicIdByEnterpriseId(String enterpriseId);

    List<OperationLicence> listLicenceByEnterId(String enterpriseId);

    /**
     * 提交审核
     *
     * @param licenceId
     * @param ticketId
     * @throws Exception
     */
    void submitAudit(String licenceId, String ticketId) throws Exception;

    /**
     * 根据许可证ID删除许可证基本信息
     *
     * @throws Exception
     */
    void deleteLicenceByLicenceIds(List<String> licenceIds, String ticketId);

    /**
     * 获取该许可证的审核状态
     *
     * @throws Exception
     */
    String getAuditStatusByLicenceId(String id) throws Exception;

    /**
     * 判断许可证编码是否存在
     *
     * @param licenceNo
     * @return
     */
    boolean isLicenceNoExist(String licenceNo, String licenceId) throws Exception;

    /**
     * 验证许可证有效期是否重复
     *
     * @param startDate
     * @param endDate
     * @return true:存在重复 false：不存在重复
     * @throws DaoAccessException
     * @throws Exception
     */
    boolean isValidityPeriodRepeat(String entId, String licenceId, String startDate, String endDate) throws Exception;

    /**
     * 根据许可证ID获取许可证对象 dinghanqi
     *
     * @param licenceId
     * @return
     * @throws Exception
     */
    OperationLicence getLicneceById(String licenceId);

    /**
     * 删除处置方式
     *
     * @param itemId
     * @param licenceId
     * @param ticketId
     * @throws Exception
     */
    void deleteDispositionType(String itemId, String licenceId, String ticketId) throws Exception;

    /**
     * 查询申请通过的许可证
     *
     * @param licenceId
     * @return
     * @throws Exception
     */
    OperationLicenceVo getlicenceApprovedById(String licenceId) throws Exception;

    /**
     * 查询许可证详细信息
     *
     * @param licenceId
     * @return
     * @throws Exception
     */
    List<OperationLicenceItemVo> getWasteInfoByLicenceId(String licenceId) throws Exception;

    /**
     * 查询所有的二位码
     *
     * @return
     * @throws Exception
     */
    List<WasteType> getAllWateType() throws Exception;

    /**
     * 根据二位码查询对应的八位码
     *
     * @return
     */
    List<Waste> getWasteByWasteTypeId(String wasteTypeId) throws Exception;

    /**
     * 删除许可证
     *
     * @param ids
     * @throws Exception
     */
    boolean deleteLicenceByIds(List<String> ids, String ticketId) throws Exception;

    /**
     * 根据企业ID拿到对应的组织机构
     *
     * @param enterpriseId
     * @return
     * @throws Exception
     */
    List<RPCSysOrgCom> getOrgByEnterpriseId(String ticketId, String enterpriseId) throws Exception;

    /**
     * 通过企业ID判断企业是否存在以及企业类型是否存在 zhugl
     *
     * @param ticketId
     * @return
     */
    boolean isEnterpriseVaild(String ticketId);

    String isHasDispositionItem(String licenceId, String dispositionTypeId) throws Exception;

    String saveOrUpdateDispositionItem(OperationLicenceItem licenceItem, String ticketId) throws Exception;

    Map<String, Object> getWasteNamesByWasteId(String wasteid, String keyword) throws Exception;

    /**
     * 保存处置方式以及危废详情 zhugl
     *
     * @param licenceDetailExList
     * @throws Exception
     */
    void saveDispositionItemAndDetail(List<OperationLicenceDetailExtend> licenceDetailExList, String ticketId) throws Exception;

    /**
     * 查询许可证详细信息 zhugl
     *
     * @param licenceId
     * @return
     */
    List<OperationLicenceItemVo> getDispositionItems(String licenceId) throws Exception;

    List<OperationLicenceDetailVo> getDispositionDetails(String licenceId, String licenceItemId) throws Exception;

    void removeDetail(String licenceId, String detailId, String ticketId) throws Exception;

    String updateWasteName(String licenceId, String detailId, String wasteId, String wasteNameId, String wasteName, String ticketId) throws Exception;

    /**
     * 获取许可证 zhugl
     *
     * @param licenceId
     * @return
     * @throws DaoAccessException
     */
    OperationLicenceVo getlicenceVoById(String licenceId) throws DaoAccessException;

    /**
     * 许可证基本信息保存 zhugl
     *
     * @param operationLicence
     * @return
     * @throws Exception
     * @
     */
    String saveLicenceBaseInfo(OperationLicence operationLicence, String ticketId) throws Exception;

    /**
     * 许可证基本信息更新 zhugl
     *
     * @param operationLicence
     * @return
     * @throws Exception
     * @
     */
    void updateLicenceBaseInfo(OperationLicence operationLicence, String ticketId) throws Exception;

    /**
     * 许可证审核状态的更新 zhugl
     *
     * @param licenceIds  许可证的ID
     * @param auditStatus 需要更新成的状态
     * @throws DaoAccessException
     */
    void updateAuditStatus(List<String> licenceIds, String auditStatus, String ticketId);

    /**
     * 判断企业有无未生效的许可证
     *
     * @param enterpriseId
     * @return
     */
    boolean getUsedLicId(String enterpriseId) throws Exception;

    /**
     * 根据许可证id,判断许可证是否存在detail信息，不存在就没有意义
     *
     * @param licenceId
     * @return
     * @throws Exception
     */
    boolean isHasDetails(String licenceId) throws Exception;

    /**
     * 获取许可证可处置危废总数
     *
     * @param licenceId
     * @return
     */
    int getProcessableTotalCount(String licenceId);

    /**
     * 根据许可证id，和产废企业的id,查询出可处置危废的详细信息
     *
     * @param licenceId
     * @param entId
     * @return
     */
    List<DispositionCapacityDetailReleaseVo> dispositionCapacityDetailReleaseVoList(String licenceId, String entId) throws Exception;

    List<OperationLicence> listLiceneByEnterpriseIdAndStatus(String enterpriseId, String status) throws Exception;

    int submitCount(String where, Map<String, Object> paramMap, String enterpriseId) throws Exception;

    List<OperationLicenceVo> getSubmitList(String where, Map<String, Object> paramMap, PagingParameter paging, String enterpriseId) throws Exception;
}
