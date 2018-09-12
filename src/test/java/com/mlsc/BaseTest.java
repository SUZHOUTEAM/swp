package com.mlsc;

import com.mlsc.common.util.RSAEncryptUtils;
import com.mlsc.waste.user.service.UserLoginService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml"})
public class BaseTest {
    @Autowired
    public ApplicationContext ctx;
    @Autowired
    private UserLoginService userLoginService;

    public String ticketId;

    @Before
    public void setTicket(){
        try {
            ticketId = userLoginService.userLogin("18051116827", RSAEncryptUtils.encrypt("test123"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
