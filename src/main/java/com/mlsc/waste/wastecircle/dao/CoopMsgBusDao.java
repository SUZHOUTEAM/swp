package com.mlsc.waste.wastecircle.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.wastecircle.model.CoopMsgBus;

public interface CoopMsgBusDao extends EntityDao<CoopMsgBus> {
    /**
     * 保存发布业务
     *
     * @param coopMsgBus
     * @return
     * @throws DaoAccessException
     */
    String saveCoopMsgBus(CoopMsgBus coopMsgBus) throws DaoAccessException;

    /**
     * 根据msgId查询发布业务
     *
     * @param coopMsgBus
     * @return
     * @throws DaoAccessException
     */
    CoopMsgBus queryCoopMsgBusByMsgId(CoopMsgBus coopMsgBus) throws DaoAccessException;

    /**
     * 更新发布业务表
     *
     * @param coopMsgBus
     * @throws DaoAccessException
     */
    void updateCoopMsgBus(CoopMsgBus coopMsgBus) throws DaoAccessException;

    /**
     * 根据msgid拿到对应的businessid
     *
     * @param msgId
     * @return
     * @throws DaoAccessException
     */
    String getCoopMsgBusByMsgId(String msgId) throws DaoAccessException;

    /**
     * 根据msgid删除数据
     *
     * @param msgId
     * @throws DaoAccessException
     */
    void deleteMsgBusByMsgId(String msgId) throws DaoAccessException;

    /**
     * 根据id获取messageId
     *
     * @param busId
     * @return
     * @throws DaoAccessException
     */
    String getMessageIdByBusId(String busId) throws DaoAccessException;
}
