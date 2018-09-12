package com.mlsc.waste.wastecircle.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.wastecircle.model.CoopMsgVo;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.waste.wastecircle.model.MessageBodyVo;
import com.mlsc.waste.wastecircle.model.SearchCondition;

import java.util.List;
import java.util.Map;

public interface WasteCircleService {

    /**
     * 第一次进入易废圈
     *
     * @param userId
     */
    void updateWasteCycleInit(String userId);

    String getUserExtendedByUserId(String userId);

    RPCSysEnterpriseBase getEnterIdByUserId(String ticketId, String userId);

    EnterpriseInfo getEnterpriseInfoByUserId(String ticketId, User user) throws Exception;

    /**
     * 通过企业名称查询公司
     *
     * @param keyword
     * @return
     */
    Map<String, Object> getEntDropDownList(String keyword);

    /**
     * 通过八位码查询
     *
     * @param keyword
     * @return
     */
    Map<String, Object> getCodeWasteDropDownList(String keyword);

    /**
     * 关注或取消关注
     *
     * @param enterId
     * @param action   1:关注；0：取消关注
     * @param ticketId
     * @return
     */
    void saveOrRemoveFollow(String enterId, String action, String ticketId);

    /**
     * 获取关注类型
     *
     * @param ticketId
     * @param entId
     * @return
     */
    boolean getFollow(String ticketId, String entId);

    int queryMyAllCoopMsg(User user);


    /**
     * 获取危废名称列表
     *
     * @param keyword
     * @return
     */
    Map<String, Object> getWasteNameDropDownList(String keyword);


    List<MessageBodyVo> getMessageListReleaseList(SearchCondition searchCondition, String ticketId, User user, PagingParameter pagingParameter) throws Exception;


    boolean checkHasEnterpriseWaste(String enterpriseId) throws Exception;

}
