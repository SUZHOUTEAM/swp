package com.mlsc.waste.user.service;

import com.mlsc.waste.user.model.User;

/**
 * Created by zhanghj on 2017/7/10.
 */
public interface UserCacheService {

    User putUserByTicket(String ticket,User user);

    User getUserByTicket(String ticket);

    String getTicketByUserPhoneNum(String phoneNum);

    String getAndPutTicketByUserPhoneNum(String phoneNum);

    String putTicketByUserPhoneNum(String phoneNum,String ticket);

    void removeUserByTicket(String ticket);

    void removeTicketByPhoneNum(String phoneNum);

    void clearTicketUser();

    void clearPhoneNumTicket();
}
