package com.mlsc.yifeiwang.wasterealase.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.wasterealase.entity.EntRelease;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseParam;
import com.mlsc.yifeiwang.wasterealase.model.InformEnterpriseModel;
import com.mlsc.yifeiwang.wasterealase.model.ReleaseStatusModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
public interface EntReleaseMapper extends BaseMapper<EntRelease> {
    List<EntReleaseModel> listWasteEntRelease(EntReleaseParam releasePram);

    List<EntReleaseModel> listWasteEntReleaseByActivityId(EntReleaseParam releasePram);

    int countEntRelease(EntReleaseParam releasePram);

    EntReleaseModel getEntReleaseById(EntReleaseParam entReleaseParam);

    List<InformEnterpriseModel> informEnterpriseByReleaseId(EntReleaseParam entReleaseParam);

    List<InformEnterpriseModel> listDisFacilitatorEnt(EntReleaseParam entReleaseParam);


    List<EntReleaseModel> listWasteEntReleaseByEnterId(EntReleaseParam releaseParams);

    int countWasteEntReleaseByEnterId(EntReleaseParam releaseParams);

    int countWasteEntReleaseByActivityId(EntReleaseParam releaseParams);

    List<ReleaseStatusModel> countActivityStatusByActivityId(@Param("activityId")String activityId);


    EntReleaseModel getReleasePosterInfo(@Param("userId") String userId);


    List<EntReleaseModel> listWasteEntRelease4FacilitatorEnt(EntReleaseParam releasePram);

    int countWasteEntRelease4FacilitatorEnt(EntReleaseParam releasePram);

    List<EntReleaseModel> listReleaseEntName(@Param("entId") String enterpriseId);
}