package com.mlsc.waste.dispositioncapacityrelease.service;

import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityRelease;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityReleaseVo;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.user.model.User;

import java.util.List;

public interface DispositionCapacityReleaseService {

    /**
     * 根据许可证id批量增加 处置能力发布信息
     * 
     * @param ticketId
     * @param licenceIds
     * @throws Exception
     */
    void saveDispositionCapacityByLicIds(String ticketId, List<String> licenceIds) throws Exception;

    /**
     * 根据许可证id查询出 处置能力发布信息表数据
     * 
     * @param licId
     * @return
     */
    DispositionCapacityRelease getDispositionCapacityReleaseByLicId(String licId);


    /**
     * 根据id查询出 处置能力发布信息表数据
     * 
     * @param capacityReleaseId
     * @return
     */
    DispositionCapacityRelease getDispositionCapacityReleaseById(String capacityReleaseId);


    void updateDispositionCapacityRelease(DispositionCapacityRelease release, User user);
}
