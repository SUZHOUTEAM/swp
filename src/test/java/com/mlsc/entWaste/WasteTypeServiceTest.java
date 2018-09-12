package com.mlsc.entWaste;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.waste.entity.WasteType;
import com.mlsc.yifeiwang.waste.service.IWasteTypeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import java.util.List;

/**
 * Created by user on 2017/9/25.
 */
public class WasteTypeServiceTest extends BaseTest {
    @Autowired
    private IWasteTypeService wasteTypeService;

    @Test
    public void listWasteInfoTest() {
        try {
            List<WasteType> wasteInfoList = wasteTypeService.listWasteInfo();
            Assert.assertTrue(wasteInfoList != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
