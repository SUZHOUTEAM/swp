package com.mlsc.yifeiwang.sms.service;

import com.alibaba.fastjson.JSONObject;
import com.mlsc.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zhanghj on 2017/8/17.
 */
public class SysNoticeServiceImplTest extends BaseTest{

    @Autowired
    private ISysNoticeService sysNoticeService;

    @Test
    public void listNoticeCategory() throws Exception {
        String userId = "2255129527502848";
        List<JSONObject> list = sysNoticeService.listNoticeCategory(userId,"PRODUCTION");
    }

}