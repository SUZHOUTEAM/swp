package com.mlsc.waste.wastecircle.service;

import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.wastecircle.model.CoopMsgBus;

public interface CoopMsgBusService {
    String saveCoopMsgBus(CoopMsgBus coopMsgBus, String ticketId) throws DaoAccessException;
}
