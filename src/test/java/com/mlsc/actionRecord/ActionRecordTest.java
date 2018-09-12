package com.mlsc.actionRecord;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.actionrecord.entity.ActionRecord;
import com.mlsc.yifeiwang.actionrecord.service.IActionRecordService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by yinxl on 2017/7/25.
 */
public class ActionRecordTest extends BaseTest {

    @Autowired
    IActionRecordService actionRecordService;

    @Test
    public void save(){
        ActionRecord ar = new ActionRecord();
        ar.setKeywords("废机油");
        ar.setFirstAction("dddddddd");
        ar.setIpAddr("10.11.11.11");
        ar.setLocation("dddddddd");
        ar.setActionTime(new Date());

        Assert.assertTrue(actionRecordService.insert(ar));
    }
}
