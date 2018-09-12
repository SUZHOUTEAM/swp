package com.mlsc.waste.dispositioncapacityrelease.service;

import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityItemRelease;


public interface DispositionCapacityItemReleaseService {

    /**
     * 根据许可证id批量增加 处置能力发布信息_可处置信息
     *
     * @param ticketId
     * @param licenceId
     * @throws Exception
     */
    void saveDispositionCapacityItemByLicId(String ticketId, String licenceId, String capacityReleaseId) throws Exception;


    /**
     * 根据id查询出 处置能力发布信息表数据
     *
     * @param capacityItemReleaseId
     * @return
     */
    DispositionCapacityItemRelease getDispositionCapacityItemReleaseById(String capacityItemReleaseId);

}
