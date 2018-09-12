package com.mlsc.waste.wastedirectory.service;

import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.model.WasteName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class WasteNameServiceTest {

    @Autowired
    private WasteNameService wasteNameService;

    @Autowired
    private UserLoginService userLoginService;

    private String getTicketId() {
        String ticketId = null;
        try {
            ticketId = userLoginService.userLogin("18120046886", "test123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketId;
    }

    @Test
    public void testSaveWasteName() {
        WasteName wasteName = new WasteName();
        wasteName.setCreate_by("1111111111");
        wasteName.setCreate_time(Util.datetimeToString(new Date()));
        wasteName.setDescription("222222222222222222");
        wasteName.setEdit_by("11111122222222");
        wasteName.setEdit_time(Util.datetimeToString(new Date()));
        wasteName.setName("3333333");
        wasteName.setStatus("2");
        wasteName.setWaste_id("11111111111");
        String id = wasteNameService.saveWasteName(wasteName, getTicketId());
        WasteName wasteNameById = wasteNameService.getWasteNameById(id);
        Assert.assertEquals(wasteNameById.getDescription(), wasteName.getDescription());
        Assert.assertNotNull(id);
        Assert.assertEquals(wasteNameById.getId(), wasteNameById.getId());

    }

    @Test
    public void testGetWasteNamesByWasteId() {
        WasteName wasteName = new WasteName();
        wasteName.setCreate_by("1111111111");
        wasteName.setCreate_time(Util.datetimeToString(new Date()));
        wasteName.setDescription("222222222222222222");
        wasteName.setEdit_by("11111122222222");
        wasteName.setEdit_time(Util.datetimeToString(new Date()));
        wasteName.setName("3333333");
        wasteName.setStatus("2");
        wasteName.setWaste_id("88888888");
        List<WasteName> wasteNames = wasteNameService.getWasteNamesByWasteId(wasteName.getWaste_id());
        Assert.assertTrue(wasteNames.size() > 1);
        Assert.assertEquals(wasteNames.get(0).getWaste_id(), wasteName.getWaste_id());
    }

    @Test
    public void testGetWasteNameById() {
        WasteName wasteName = new WasteName();
        wasteName.setCreate_by("1111111111");
        wasteName.setCreate_time(Util.datetimeToString(new Date()));
        wasteName.setDescription("222222222222222222");
        wasteName.setEdit_by("11111122222222");
        wasteName.setEdit_time(Util.datetimeToString(new Date()));
        wasteName.setName("3333333");
        wasteName.setStatus("2");
        wasteName.setWaste_id("88888888");
        String id1 = wasteNameService.saveWasteName(wasteName, getTicketId());
        WasteName wasteNameById = wasteNameService.getWasteNameById(id1);
        Assert.assertEquals(wasteNameById.getDescription(), wasteName.getDescription());
        Assert.assertEquals(wasteNameById.getWaste_id(), wasteName.getWaste_id());
    }

    @Test
    public void testGetWasteNamesByNameAndWasteid() {
        WasteName wasteName = new WasteName();
        wasteName.setCreate_by("1111111111");
        wasteName.setCreate_time(Util.datetimeToString(new Date()));
        wasteName.setDescription("222222222222222222");
        wasteName.setEdit_by("11111122222222");
        wasteName.setEdit_time(Util.datetimeToString(new Date()));
        wasteName.setName("3333333");
        wasteName.setStatus("2");
        wasteName.setWaste_id("88888888");
        List<WasteName> wasteNamesByNameAndWasteid = wasteNameService.getWasteNamesByNameAndWasteid(wasteName.getWaste_id(), wasteName.getName(), null);
        Assert.assertNotNull(wasteNamesByNameAndWasteid);
    }

    @Test
    public void testSaveWasteNames() {

        WasteName wasteName1 = new WasteName();
        wasteName1.setCreate_by("1111111111");
        wasteName1.setCreate_time(Util.datetimeToString(new Date()));
        wasteName1.setDescription("222222222222222222");
        wasteName1.setEdit_by("11111122222222");
        wasteName1.setEdit_time(Util.datetimeToString(new Date()));
        wasteName1.setName("3333333");
        wasteName1.setStatus("2");
        wasteName1.setWaste_id("hahha");

        WasteName wasteName2 = new WasteName();
        wasteName2.setCreate_by("22222222222222222");
        wasteName2.setCreate_time(Util.datetimeToString(new Date()));
        wasteName2.setDescription("222222222222222222");
        wasteName2.setEdit_by("11111122222222");
        wasteName2.setEdit_time(Util.datetimeToString(new Date()));
        wasteName2.setName("2222222222223");
        wasteName2.setStatus("2");
        wasteName2.setWaste_id("cccc");

        List<WasteName> wasteNames = new ArrayList<WasteName>();
        wasteNames.add(wasteName1);
        wasteNames.add(wasteName2);
        wasteNameService.saveWasteNames(wasteNames, getTicketId());
        List<WasteName> wasteNamesByWasteId = wasteNameService.getWasteNamesByWasteId(wasteName1.getWaste_id());
        List<WasteName> wasteNamesByWasteId2 = wasteNameService.getWasteNamesByWasteId(wasteName2.getWaste_id());
        Assert.assertTrue(wasteNamesByWasteId.size() != 0);
        Assert.assertTrue(wasteNamesByWasteId2.size() != 0);
    }

    @Test
    public void testUpdateWasteNames() {
        WasteName wasteName1 = new WasteName();
        wasteName1.setCreate_by("1111111111");
        wasteName1.setCreate_time(Util.datetimeToString(new Date()));
        wasteName1.setDescription("aaaaaaaa");
        wasteName1.setEdit_by("11111122222222");
        wasteName1.setEdit_time(Util.datetimeToString(new Date()));
        wasteName1.setName("3333333");
        wasteName1.setStatus("2");
        wasteName1.setWaste_id("1113311");
        String id = wasteNameService.saveWasteName(wasteName1, getTicketId());
        wasteName1.setId(id);
        wasteName1.setDescription("bbbbbbbbbbbbbbbbbb");
        List<WasteName> wasteNames = new ArrayList<WasteName>();
        wasteNames.add(wasteName1);
        WasteName wasteNameById1 = wasteNameService.getWasteNameById(id);
        Assert.assertEquals(wasteNameById1.getDescription(), "aaaaaaaa");
        wasteNameService.updateWasteNames(wasteNames, getTicketId());
        WasteName wasteNameById = wasteNameService.getWasteNameById(id);
        Assert.assertEquals(wasteNameById.getDescription(), "bbbbbbbbbbbbbbbbbb");
    }

    @Test
    public void testRemoveWasteNamesByWasteId() {
        WasteName wasteName1 = new WasteName();
        wasteName1.setCreate_by("1111111111");
        wasteName1.setCreate_time(Util.datetimeToString(new Date()));
        wasteName1.setDescription("aaaaaaaa");
        wasteName1.setEdit_by("11111122222222");
        wasteName1.setEdit_time(Util.datetimeToString(new Date()));
        wasteName1.setName("3333333");
        wasteName1.setStatus("2");
        wasteName1.setWaste_id("1113311");
        String id = wasteNameService.saveWasteName(wasteName1, getTicketId());
        WasteName wasteNameById1 = wasteNameService.getWasteNameById(id);
        Assert.assertNotNull(wasteNameById1);
        wasteNameService.removeWasteNamesByWasteId(wasteName1.getWaste_id());
        WasteName wasteNameById2 = wasteNameService.getWasteNameById(id);
        Assert.assertNull(wasteNameById2);
    }

}
