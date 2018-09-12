package com.mlsc.waste.dispositioncapacityrelease.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityDetailRelease;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityDetailReleaseVo;

import java.util.List;

public interface DispositionCapacityDetailReleaseDao extends EntityDao<DispositionCapacityDetailRelease> {

    /**
     * 据二位码获取八位码处置能力
     * @param entId
     * @param messageId
     * @param dispCode
     * @param wasteTypeId
     * @param wasteCode
     * @param pagingParameter
     * @return
     * @throws DaoAccessException
     */
    List<DispositionCapacityDetailReleaseVo> getEightCodeListByIds(String entId, String messageId, String dispCode, String wasteTypeId, String wasteCode, PagingParameter pagingParameter) throws DaoAccessException;


    List<DispositionCapacityDetailReleaseVo> getEightCodeListByIds(String entId, String releaseId) throws DaoAccessException;

    /**
     * 查询处置能力详情
     * 
     * @param capacityReleaseId
     * @param capacityItemReleaseId
     * @param wasteId
     * @return
     * @throws DaoAccessException
     */
    DispositionCapacityDetailRelease getDispositioncapacityDetailReleaseByIds(String capacityReleaseId, String capacityItemReleaseId, String wasteId) throws DaoAccessException;

}
