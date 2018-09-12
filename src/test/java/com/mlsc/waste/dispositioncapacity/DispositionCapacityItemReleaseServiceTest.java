package com.mlsc.waste.dispositioncapacity;

import com.mlsc.waste.dispositioncapacityrelease.dao.DispositionCapacityDetailReleaseDao;
import com.mlsc.waste.dispositioncapacityrelease.dao.DispositionCapacityItemReleaseDao;
import com.mlsc.waste.dispositioncapacityrelease.dao.DispositionCapacityReleaseDao;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityDetailRelease;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityItemRelease;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityRelease;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityItemReleaseService;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class DispositionCapacityItemReleaseServiceTest {

    @Autowired
    private DispositionCapacityItemReleaseService itemReleaseService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private DispositionCapacityReleaseDao dispositionCapacityReleaseDao;

    @Autowired
    private DispositionCapacityItemReleaseDao dispositionCapacityItemReleaseDao;

    @Autowired
    private DispositionCapacityDetailReleaseDao dispositionCapacityDetailReleaseDao;

    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;

    private String getTicketId() {
        String ticketId = null;
        try {
            ticketId = userLoginService.userLogin("18120046886", "test123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketId;
    }

    private void removeDate(String startTime, String endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        // 删除item表的数据
        String sqltemp1 = "delete from  disposition_capacityitem_release where create_time >= :startTime and create_time <= :endTime";
        namedjdbctemp.update(sqltemp1, paramMap);
        // 删除detail表的数据
        String sqltemp2 = "delete from  disposition_capacitydetail_release where create_time >= :startTime and create_time <= :endTime";
        namedjdbctemp.update(sqltemp2, paramMap);
    }

    @Test
    public void testSaveDispositionCapacityItemByLicId() throws Exception {
        String startTime = Util.datetimeToString(new Date());
        // (String ticketId, String licenceId,String capacityReleaseId)
        String sql = "select * from disposition_capacity_release  where valid = '1'";
        List<DispositionCapacityRelease> dispositionCapacityReleases = dispositionCapacityReleaseDao.query(sql);
        int count1 = dispositionCapacityReleases.size();
        String licenceId = dispositionCapacityReleases.get(0).getOperationLicenceId();
        String capacityReleaseId = dispositionCapacityReleases.get(0).getId();
        itemReleaseService.saveDispositionCapacityItemByLicId(getTicketId(), licenceId, capacityReleaseId);
        List<DispositionCapacityRelease> dispositionCapacityReleases2 = dispositionCapacityReleaseDao.query(sql);
        int count2 = dispositionCapacityReleases2.size();
        // Assert.assertNotEquals(count1, count2);
        Assert.assertTrue(count1 >= count2);
        System.out.println(count1);
        System.out.println(count2);
        String endTime = Util.datetimeToString(new Date());
        removeDate(startTime, endTime);
    }


    @Test
    public void testGetDispositionCapacityItemReleaseById() throws Exception {
        // (String capacityItemReleaseId)
        String sql = "select * from disposition_capacityitem_release where valid = '1'";
        List<DispositionCapacityItemRelease> itemReleazs = dispositionCapacityItemReleaseDao.query(sql);
        String id = itemReleazs.get(0).getId();
        String quotaQuantity = itemReleazs.get(0).getQuotaQuantity();
        DispositionCapacityItemRelease itemRelease = itemReleaseService.getDispositionCapacityItemReleaseById(id);
        Assert.assertNotNull(itemRelease);
        Assert.assertEquals(itemRelease.getQuotaQuantity(), quotaQuantity);

    }

}
