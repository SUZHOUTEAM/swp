package com.mlsc.calling;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.calling.model.CallingListPojo;
import com.mlsc.yifeiwang.calling.model.CallingVo;
import com.mlsc.yifeiwang.calling.service.ICallingService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by yinxl on 2017/7/26.
 */
public class CallingServiceTest  extends BaseTest{
    @Autowired
    private ICallingService callingService;

    @Test
    public void queryCallingDropDown(){
        Map<String, List<CallingVo>> listMap = callingService.queryCallingDropDown();

        List<CallingListPojo> listPojos = callingService.list(null,null);

        Assert.assertNotNull(listMap);
    }
}
