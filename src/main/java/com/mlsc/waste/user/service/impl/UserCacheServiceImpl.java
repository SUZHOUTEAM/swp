package com.mlsc.waste.user.service.impl;

import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserCacheService;
import com.mlsc.waste.utils.Util;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghj on 2017/7/10.
 */
@Service
public class UserCacheServiceImpl implements UserCacheService{


    @Override
    @CachePut(value="ticketUserCache",key = "#ticket")
    public User putUserByTicket(String ticket, User user) {
        return user;
    }

    @Override
    @Cacheable(value="ticketUserCache",key = "#ticket",unless = "#result==null")
    public User getUserByTicket(String ticket) {
        return null;
    }

    @Override
    @CacheEvict(value = "ticketUserCache", key = "#ticket")
    public void removeUserByTicket(String ticket) {

    }

    @Override
    @Cacheable(value="phoneNumTicketCache",key = "#phoneNum",unless = "#result==null")
    public String getTicketByUserPhoneNum(String phoneNum) {
        return null;
    }

    @Override
    @Cacheable(value="phoneNumTicketCache",key = "#phoneNum")
    public String getAndPutTicketByUserPhoneNum(String phoneNum) {
        return Util.uuid32();
    }

    @Override
    @CachePut(value="phoneNumTicketCache",key = "#phoneNum")
    public String putTicketByUserPhoneNum(String phoneNum,String ticket) {
        return ticket;
    }


    @Override
    @CacheEvict(value = "phoneNumTicketCache", key = "#phoneNum")
    public void removeTicketByPhoneNum(String phoneNum) {

    }

    @Override
    @CacheEvict(value="ticketUserCache",allEntries=true)
    public void clearTicketUser() {

    }

    @Override
    @CacheEvict(value="phoneNumTicketCache",allEntries=true)
    public void clearPhoneNumTicket() {

    }


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring.xml");// 加载 spring 配置文件

        UserCacheService s = (UserCacheService) context.getBean("userCacheServiceImpl");
        System.out.println(s.getClass());
        s.putTicketByUserPhoneNum("12312312","1801342638");
        String ticketId = s.putTicketByUserPhoneNum("12312312","1801342638");

//        String ticketId = s.getTicketByUserPhoneNum("18013142638");
        System.out.println(ticketId);
    }
}
