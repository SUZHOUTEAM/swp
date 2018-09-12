package com.mlsc.EntCustomer;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.enterprise.entity.EntCustomer;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.enterprise.service.IEntCustomerService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 2017/10/20.
 */
public class EntCustomerServiceTest extends BaseTest {
    @Autowired
    private IEntCustomerService entCustomerService;

    @Test
    public void addEntCutomerService() {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868126758912");

        EntCustomer entCustomer = new EntCustomer();
        entCustomer.setCustomerName("企业test1");
        entCustomer.setId(Util.uuid32());
        try {
            entCustomerService.saveEntCustomer(user, entCustomer);
            List<EntCustomer> queryList = entCustomerService.listEntCustomersByEntId(user);
            Assert.assertTrue(queryList.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateEntCutomer() {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868126758912");
        EntCustomer entCustomer = new EntCustomer();
        entCustomer.setCustomerName("企业test1");
        entCustomer.setId(Util.uuid32());
        try {
            List<EntCustomer> queryList = entCustomerService.listEntCustomersByEntId(user);
            if (queryList != null) {
                EntCustomer entCustomer1 = queryList.get(0);
                entCustomer1.setCustomerName("changeCustomerTest");
                entCustomer1.setFileId("testFileId");
                entCustomerService.updateEntCustomer(user,entCustomer1);
                EntCustomer updatedRecord = entCustomerService.selectById(entCustomer1.getId());
                if(updatedRecord!=null){
                    Assert.assertTrue(updatedRecord.getCustomerName().equals("changeCustomerTest"));
                    Assert.assertTrue(updatedRecord.getFileId().equals("testFileId"));
                }
                entCustomerService.deleteEntCustomer(updatedRecord);
                Assert.assertTrue(entCustomerService.selectById(updatedRecord)==null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
