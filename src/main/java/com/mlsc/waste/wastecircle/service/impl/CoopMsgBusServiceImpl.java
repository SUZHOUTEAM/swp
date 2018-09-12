package com.mlsc.waste.wastecircle.service.impl;

import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.dao.CoopMsgBusDao;
import com.mlsc.waste.wastecircle.model.CoopMsgBus;
import com.mlsc.waste.wastecircle.service.CoopMsgBusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("coopMsgBusService")
public class CoopMsgBusServiceImpl implements CoopMsgBusService {
    private final static Logger logger = LoggerFactory.getLogger(CoopMsgBusServiceImpl.class);

    @Autowired
    private CoopMsgBusDao coopMsgBusDao;
    
    @Override
    public String saveCoopMsgBus(CoopMsgBus coopMsgBus,String ticketId) throws DaoAccessException {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        coopMsgBus.setValid(Constant.IS_VALID);
        coopMsgBus.setCreateBy(user.getUserId());
        coopMsgBus.setCreateTime(Util.datetimeToString(new Date()));
        coopMsgBus.setEditTime(Util.datetimeToString(new Date()));
        coopMsgBus.setEditBy(user.getUserId());
        String id = coopMsgBusDao.saveCoopMsgBus(coopMsgBus);
        return id;
    }


}
