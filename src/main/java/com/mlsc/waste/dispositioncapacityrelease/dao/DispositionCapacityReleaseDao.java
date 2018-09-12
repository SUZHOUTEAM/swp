package com.mlsc.waste.dispositioncapacityrelease.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityRelease;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityReleaseVo;
import com.mlsc.waste.licence.model.OperationLicence;

import java.util.List;

public interface DispositionCapacityReleaseDao extends EntityDao<DispositionCapacityRelease> {

    /**
     * 根据许可证id查询出 处置能力信息表数据
     * 
     * @param licId
     * @return
     * @throws DaoAccessException
     */
    DispositionCapacityRelease getDispositionCapacityReleaseByLicId(String licId) throws DaoAccessException;

    List<DispositionCapacityReleaseVo> getDispositionCapacityReleaseDetailInfoByReleaseId(String releaseId) throws DaoAccessException;

    OperationLicence getLicenceNoByReleaseId(String releaseId) throws DaoAccessException;


}
