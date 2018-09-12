package com.mlsc.EntGlory;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.enterprise.entity.EntGlory;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.enterprise.service.IEntGloryService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 2017/10/20.
 */
public class EntGloryServiceTest extends BaseTest {
    @Autowired
    private IEntGloryService entGloryService;

    @Test
    public void saveEntGloryTest() {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868126758912");
        EntGlory entGlory1 = new EntGlory();
        entGlory1.setGloryType("gloryTest");
        entGlory1.setGetTime(new Date());
        entGlory1.setEntId(user.getEnterpriseId());
        entGlory1.setFileId("gloryFileIdTest");
        entGlory1.setId(Util.uuid32());
        try {
            entGloryService.saveEntGlory(user, entGlory1);
            List<EntGlory> entGlories1 = entGloryService.listEntGloryByEntId(user);
            entGlory1.setGloryType("gloryTyp2");
            entGlory1.setFileId("updatedGloryFileIdTest");
            entGloryService.updateEntGlory(user, entGlory1);
            Assert.assertTrue(entGloryService.getEntGloryById(entGlory1.getId()).getFileId().equals("updatedGloryFileIdTest"));
            entGloryService.deleteEntGlory(entGlory1);
            Assert.assertTrue(entGloryService.getEntGloryById(entGlory1.getId())==null);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
