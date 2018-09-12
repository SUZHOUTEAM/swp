package com.mlsc.activity;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.activity.entity.WasteActivityContacts;
import com.mlsc.yifeiwang.activity.service.IWasteActivityContactsService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanghj on 2017/8/1.
 */
public class WasteActivityContactsServiceImplTest extends BaseTest {

    @Autowired
    private IWasteActivityContactsService wasteActivityContactsService;

    @Test
    @Ignore
    public void insert() throws Exception {
        WasteActivityContacts wasteActivityContacts = new WasteActivityContacts();
        wasteActivityContacts.setActivityId("0336f2f6da3f4a69b5beebac71338003");
        wasteActivityContacts.setUserName("张慧君");
        wasteActivityContacts.setUserPhone("18013142638");
        wasteActivityContacts.setCreateTime(new Date());
        wasteActivityContacts.setCreateBy("test");
        boolean rs = wasteActivityContactsService.insert(wasteActivityContacts);
        System.out.println(wasteActivityContacts.getActivityId());
        Assert.assertTrue(rs);
    }

    @Test
    @Ignore
    public void selectById() throws Exception {
        String id = "391a041ba2e84a5a89d70f00256c5294";
        WasteActivityContacts wasteActivityContacts = wasteActivityContactsService.selectById(id);
        Assert.assertNotNull(wasteActivityContacts);
        Assert.assertEquals("18013142638", wasteActivityContacts.getUserPhone());
    }

    @Test
    @Ignore
    public void selectList() throws Exception {
        EntityWrapper<WasteActivityContacts> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("activity_Id", "0336f2f6da3f4a69b5beebac71338003");
        entityWrapper.eq("status", "0");
        List<WasteActivityContacts> list = wasteActivityContactsService.selectList(entityWrapper);
        Assert.assertEquals(1, list.size());
    }


    @Test
    public void generateWasteActivityContactsByActivity() throws Exception {
        String activityId = "e95e919a39924dcfbf7844f550a9651c";
        wasteActivityContactsService.generateWasteActivityContactsByActivity(activityId);
    }

    @Test
    public void countWasteActivityCoverEnt() throws Exception {
        String activityId = "e95e919a39924dcfbf7844f550a9651c";
        int coverEnt = wasteActivityContactsService.generateWasteActivityCoverEntCount(activityId);
        Assert.assertTrue(coverEnt > 0);
    }

}