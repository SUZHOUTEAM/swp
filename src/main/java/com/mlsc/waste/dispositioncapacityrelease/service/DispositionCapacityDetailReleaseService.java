package com.mlsc.waste.dispositioncapacityrelease.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityDetailRelease;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityDetailReleaseVo;

import java.util.List;

public interface DispositionCapacityDetailReleaseService {

    /**
     * 根据许可证id增加 处置能力的detail表的数据
     * 
     * @param ticketId
     * @param licenceId
     * @throws Exception
     */
    void saveDispositionCapacityDetails(String ticketId, String licenceId, String licenceItemId, String capacityReleaseId, String capacityItemReleaseId) throws Exception;


    /**
     * 根据id查询出 处置能力发布信息表数据
     * 
     * @param capacityDetailReleaseId
     * @return
     */
    DispositionCapacityDetailRelease getDispositionCapacityDetailReleaseById(String capacityDetailReleaseId);



}
