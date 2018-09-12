package com.mlsc.waste.wastecircle.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.wastecircle.model.OfflineMessage;
import com.mlsc.waste.wastecircle.model.OfflineMessageVo;

import java.util.List;
import java.util.Map;

/**
 * 离线消息持久化
 */
public interface OfflineMessageDao extends EntityDao<OfflineMessage> {

    /**
     * 查询所有离线消息
     *
     * @return
     * @throws DaoAccessException
     */
    List<OfflineMessageVo> getOfflineMsgList() throws DaoAccessException;

    /**
     * 根据条件获取离线消息列表
     *
     * @param where
     * @param paramMap
     * @param paging
     * @return
     */
    List<OfflineMessageVo> getOfflineMsgList(String where, Map<String, Object> paramMap, PagingParameter paging);

    /**
     * 根据Id获取离线消息
     *
     * @param id
     * @return
     * @throws DaoAccessException
     */
    OfflineMessageVo getOfflineMsgById(String id) throws DaoAccessException;
}
