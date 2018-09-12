package com.mlsc.mobileservice.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.mobileservice.model.DispositionCapacityDetailReleaseVo;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.wastecircle.model.CoopMsgVo;

import java.util.List;

public interface MobileProductionUserService {

    List<CoopMsgVo> getMessageListReleaseList(String ticketId, User user, PagingParameter pagingParameter, String searchCondition, String cantonCode, String sortByDistance);

    /**
     * 根据许可证id和处置企业的id，查询出处置的危废详细信息
     * 
     * @param licenceId
     * @param entId
     * @return
     * @throws Exception
     */
    List<DispositionCapacityDetailReleaseVo> getDispositionCapacityDetailReleaseVo(String licenceId, String entId) throws Exception;
}
