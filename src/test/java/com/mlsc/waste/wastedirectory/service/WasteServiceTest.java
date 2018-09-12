package com.mlsc.waste.wastedirectory.service;

import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.calling.service.ICallingService;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.dao.WasteDao;
import com.mlsc.waste.wastedirectory.dao.WasteNameDao;
import com.mlsc.waste.wastedirectory.model.Waste;
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
public class WasteServiceTest {

    @Autowired
    private WasteService wasteService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private WasteDao wasteDao;

    @Autowired
    private WasteNameService wasteNameService;

    @Autowired
    private WasteNameDao wasteNameDao;
    
    @Autowired
    private ICallingService callingService;

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
    public void testUpdateSataus() throws Exception {
        Waste waste = new Waste();
        waste.setCalling_id(Util.uuid32());
        waste.setWaste_type_id(Util.uuid32());
        waste.setCode("222");
        waste.setDescription("hahhahhah");
        waste.setStatus("1");
        waste.setCreate_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setCreate_time(Util.datetimeToString(new Date()));
        waste.setEdit_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setEdit_time(Util.datetimeToString(new Date()));
        waste.setCorrosivity("1");
        waste.setToxicity("1");
        waste.setIgnitability("1");
        waste.setReactivity("1");
        waste.setInfectivity("1");
        String id1 = Util.uuid32();
        wasteDao.save(waste, id1);

        Waste waste1 = new Waste();
        waste1.setCalling_id(Util.uuid32());
        waste1.setWaste_type_id(Util.uuid32());
        waste1.setCode("222");
        waste1.setDescription("hahhahhah");
        waste1.setStatus("1");
        waste1.setCreate_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste1.setCreate_time(Util.datetimeToString(new Date()));
        waste1.setEdit_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste1.setEdit_time(Util.datetimeToString(new Date()));
        waste1.setCorrosivity("1");
        waste1.setToxicity("1");
        waste1.setIgnitability("1");
        waste1.setReactivity("1");
        waste1.setInfectivity("1");
        String id2 = Util.uuid32();
        wasteDao.save(waste1, id2);
        List<String> ids = new ArrayList<String>();
        ids.add(id1);
        ids.add(id2);
        wasteService.updateSataus(ids, "3", getTicketId());
        Waste wasteById = wasteService.getWasteById(id1);
        Waste wasteById2 = wasteService.getWasteById(id2);
        Assert.assertEquals("3", wasteById.getStatus());
        Assert.assertEquals("3", wasteById2.getStatus());
        wasteDao.delete(id1);
        wasteDao.delete(id2);
    }

    /*
     * @Test public void testlist() throws Exception { List<Waste> list =
     * wasteService.list("STRING", null, null); Assert.assertNull(list); }
     */

    /*
     * @Test public void testcount() throws Exception { Integer count =
     * wasteService.count("whwere", null); Assert.assertNull(count); }
     */


    @Test
    public void testGetWasteById() throws DaoAccessException {
        Waste waste = new Waste();
        waste.setCalling_id(Util.uuid32());
        waste.setWaste_type_id(Util.uuid32());
        waste.setCode("222");
        waste.setDescription("hahhahhah");
        waste.setStatus("1");
        waste.setCreate_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setCreate_time(Util.datetimeToString(new Date()));
        waste.setEdit_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setEdit_time(Util.datetimeToString(new Date()));
        waste.setCorrosivity("1");
        waste.setToxicity("1");
        waste.setIgnitability("1");
        waste.setReactivity("1");
        waste.setInfectivity("1");
        String id1 = Util.uuid32();
        wasteDao.save(waste, id1);
        Waste wasteById = wasteService.getWasteById(id1);
        Assert.assertEquals(wasteById.getCode(), waste.getCode());
        Assert.assertEquals(wasteById.getDescription(), waste.getDescription());
        Assert.assertEquals(wasteById.getToxicity(), waste.getToxicity());
        wasteDao.delete(id1);
    }

    @Test
    public void testGetWasteByCode() throws Exception {
        Waste waste = new Waste();
        waste.setCalling_id(Util.uuid32());
        waste.setWaste_type_id(Util.uuid32());
        waste.setCode("aaaa");
        waste.setDescription("hahhahhah1");
        waste.setStatus("1");
        waste.setCreate_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setCreate_time(Util.datetimeToString(new Date()));
        waste.setEdit_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setEdit_time(Util.datetimeToString(new Date()));
        waste.setCorrosivity("1");
        waste.setToxicity("1");
        waste.setIgnitability("1");
        waste.setReactivity("1");
        waste.setInfectivity("1");
        String id1 = Util.uuid32();
        wasteDao.save(waste, id1);
        Waste wasteByCode = wasteService.getWasteByCode("aaaa");
        Assert.assertEquals(wasteByCode.getDescription(), waste.getDescription());
        Assert.assertEquals(wasteByCode.getCode(), waste.getCode());
        wasteDao.delete(id1);
    }

    @Test
    public void testGetWastesByWasteCode() throws Exception {
        Waste waste = new Waste();
        waste.setCalling_id(Util.uuid32());
        waste.setWaste_type_id(Util.uuid32());
        waste.setCode("bbbb");
        waste.setDescription("hahhahhah1");
        waste.setStatus("1");
        waste.setCreate_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setCreate_time(Util.datetimeToString(new Date()));
        waste.setEdit_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setEdit_time(Util.datetimeToString(new Date()));
        waste.setCorrosivity("2");
        waste.setToxicity("1");
        waste.setIgnitability("1");
        waste.setReactivity("1");
        waste.setInfectivity("1");
        String id = Util.uuid32();
        wasteDao.save(waste, id);
        Waste wasteByCode = wasteService.getWasteByCode("bbbb");
        Assert.assertEquals(wasteByCode.getDescription(), waste.getDescription());
        Assert.assertEquals(wasteByCode.getCorrosivity(), waste.getCorrosivity());
        wasteDao.delete(wasteByCode.getId());

    }

    @Test
    public void testGetWastesByWasteTypeId() throws Exception {
        Waste waste = new Waste();
        waste.setCalling_id(Util.uuid32());
        waste.setWaste_type_id(Util.uuid32());
        waste.setCode("bbbb");
        waste.setDescription("hahhahhah1");
        waste.setStatus("1");
        waste.setCreate_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setCreate_time(Util.datetimeToString(new Date()));
        waste.setEdit_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setEdit_time(Util.datetimeToString(new Date()));
        waste.setCorrosivity("2");
        waste.setToxicity("1");
        waste.setIgnitability("1");
        waste.setReactivity("1");
        waste.setInfectivity("1");
        wasteDao.save(waste, Util.uuid32());
        List<Waste> wastesByWasteTypeId = wasteService.getWastesByWasteTypeId(waste.getWaste_type_id());
        Assert.assertEquals(wastesByWasteTypeId.size(), 1);
        wasteDao.delete(wastesByWasteTypeId.get(0).getId());
    }




    @Test
    public void testGetWasteNamesByWasteId() throws Exception {
        Waste waste = new Waste();
        waste.setCalling_id(Util.uuid32());
        waste.setWaste_type_id(Util.uuid32());
        waste.setCode("bbbb");
        waste.setDescription("hahhahhah1");
        waste.setStatus("1");
        waste.setCreate_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setCreate_time(Util.datetimeToString(new Date()));
        waste.setEdit_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setEdit_time(Util.datetimeToString(new Date()));
        waste.setCorrosivity("2");
        waste.setToxicity("1");
        waste.setIgnitability("1");
        waste.setReactivity("1");
        waste.setInfectivity("1");
        String id = Util.uuid32();
        wasteDao.save(waste, id);
        List<WasteName> wasteNamesByWasteId = wasteService.getWasteNamesByWasteId(id);
        Assert.assertEquals(wasteNamesByWasteId.size(), 0);
        
        wasteDao.delete(id);
    }



    @Test
    public void testGetWasteNameByWasteNameId() throws DaoAccessException {
        WasteName wasteName = new WasteName();
        wasteName.setCreate_by("1111111111");
        wasteName.setCreate_time(Util.datetimeToString(new Date()));
        wasteName.setDescription("222222222222222222");
        wasteName.setEdit_by("11111122222222");
        wasteName.setEdit_time(Util.datetimeToString(new Date()));
        wasteName.setName("3333333");
        wasteName.setStatus("2");
        wasteName.setWaste_id("11111111111");
        String id1 = wasteNameService.saveWasteName(wasteName, getTicketId());
        WasteName wasteNameById1 = wasteNameService.getWasteNameById(id1);
        Assert.assertEquals(wasteNameById1.getDescription(), wasteName.getDescription());
        Assert.assertEquals(wasteNameById1.getName(), wasteName.getName());
        Assert.assertEquals(wasteNameById1.getWaste_id(), wasteName.getWaste_id());
        wasteDao.delete(id1);
    }



    @Test
    public void testRemoveWasteByIds() throws Exception {
        List<String> ids = new ArrayList<String>();
        Waste waste = new Waste();
        waste.setCalling_id(Util.uuid32());
        waste.setWaste_type_id(Util.uuid32());
        waste.setCode("bbbb");
        waste.setDescription("hahhahhah1");
        waste.setStatus("1");
        waste.setCreate_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setCreate_time(Util.datetimeToString(new Date()));
        waste.setEdit_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste.setEdit_time(Util.datetimeToString(new Date()));
        waste.setCorrosivity("2");
        waste.setToxicity("1");
        waste.setIgnitability("1");
        waste.setReactivity("1");
        waste.setInfectivity("1");
        String id1 = Util.uuid32();
        Waste waste1 = new Waste();
        waste1.setCalling_id(Util.uuid32());
        waste1.setWaste_type_id(Util.uuid32());
        waste1.setCode("bbbb");
        waste1.setDescription("hahhahhah1");
        waste1.setStatus("1");
        waste1.setCreate_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste1.setCreate_time(Util.datetimeToString(new Date()));
        waste1.setEdit_by(LoginStatusUtils.getUserByTicketId(getTicketId()).getUserId());
        waste1.setEdit_time(Util.datetimeToString(new Date()));
        waste1.setCorrosivity("2");
        waste1.setToxicity("1");
        waste1.setIgnitability("1");
        waste1.setReactivity("1");
        waste1.setInfectivity("1");
        String id2 = Util.uuid32();
        wasteDao.save(waste, id1);
        wasteDao.save(waste1, id2);
        ids.add(id1);
        ids.add(id2);
        Waste wasteById1 = wasteService.getWasteById(id1);
        Waste wasteById2 = wasteService.getWasteById(id2);
        wasteService.removeWasteByIds(ids);
        Waste wasteById_1 = wasteService.getWasteById(id1);
        Waste wasteById_2 = wasteService.getWasteById(id2);
        Assert.assertNotNull(wasteById1);
        Assert.assertNotNull(wasteById2);
        Assert.assertNull(wasteById_1);
        Assert.assertNull(wasteById_2);

    }

    @Test
    public void testGetCodeWasteDropDownList() {
        List<Waste> wasteList = wasteService.getCodeWasteDropDownList("30");
        Assert.assertNotNull(wasteList);
        for (Waste waste : wasteList) {
            Assert.assertTrue(waste.getCode().contains("30"));
        }
    }

    @Test
    public void testGetWasteNameDropDownList() {
        List<Waste> wasteNameList = wasteService.getWasteNameDropDownList("次生");
        Assert.assertNotNull(wasteNameList);
        for (Waste waste : wasteNameList) {
            Assert.assertTrue(waste.getName().contains("次生"));
        }

    }
}
