package com.mlsc.waste.wastecircle.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.wastecircle.model.OfflineMessage;
import com.mlsc.waste.wastecircle.model.OfflineMessageVo;

import java.util.List;
import java.util.Map;

public interface OfflineMessageService {

    /**
     * 保存离线消息
     * 
     * @param offlineMessage
     * @param businessCode
     * @throws Exception
     */
    String saveOfflineMessage(OfflineMessage offlineMessage, String businessCode) throws Exception;

    /**
     * 查询离线消息列表
     * 
     * @return
     * @throws Exception
     */
    List<OfflineMessageVo> getOfflineMsgList() throws Exception;

    int getOfflineMsgcount(String where, Map<String, Object> paramMap) throws Exception;

    List<OfflineMessageVo> getOfflineMsgList(String where, Map<String, Object> paramMap, PagingParameter paging);

    OfflineMessageVo getOfflineMsgById(String id) throws Exception;
}
