package com.mlsc.waste.wastedirectory.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.model.WasteType;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING )
public class WasteTypeServiceTest {
    
    @Autowired
    private WasteTypeService wasteTypeService;
    
    @Autowired
    private UserLoginService userLoginService;
    
    private static String wasteTypeId = "";
    
    private String getTicketId() {
        String ticketId = null;
        try {
            ticketId = userLoginService.userLogin("18120046886", "test123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketId;
    }
    
    private WasteType getWasteTypeInfo(){
        WasteType wasteType = new WasteType();
        wasteType.setCode("69");
        wasteType.setStatus("1");
        wasteType.setDescription("abcdefghijjklmn");
        wasteType.setCreate_by("123456789");
        wasteType.setCreate_time(Util.datetimeToString(new Date()));
        wasteType.setEdit_by("123456789");
        wasteType.setEdit_time(Util.datetimeToString(new Date()));
        return wasteType;
    }
       
    
    @Test
    public void testWasteTypeServiceA() throws  DaoAccessException{
        long timeStart = System.currentTimeMillis();
        
        wasteTypeId =  wasteTypeService.saveWasteType(getWasteTypeInfo(),getTicketId());
        
        long timeEnd = System.currentTimeMillis();
        System.out.println(timeEnd - timeStart);
    }
    
    
    @Test
    public void testWasteTypeServiceB() throws DaoAccessException{
        long timeStart = System.currentTimeMillis();
        WasteType wasteTypeAfter =  wasteTypeService.getWasteTypeById(wasteTypeId);
        Assert.assertEquals(true, wasteTypeService.isWasteTypeCodeExistent(wasteTypeAfter.getId(),wasteTypeAfter.getCode()));
        
        long timeEnd = System.currentTimeMillis();
        System.out.println(timeEnd - timeStart);
    }
    
    
    @Test
    public void testWasteTypeServiceC() throws DaoAccessException{
        Assert.assertEquals(true, StringUtils.isNotBlank(wasteTypeService.getWasteTypeById(wasteTypeId).getCode()));
    }

    
    @Test
    public void testWasteTypeServiceD() throws DaoAccessException{
        
        WasteType wasteTypeBefore = getWasteTypeInfo();
        wasteTypeBefore.setCode("70");
        wasteTypeBefore.setId(wasteTypeId);
        wasteTypeService.updateWasteType(wasteTypeBefore,getTicketId());
        WasteType wasteTypeAfter =  wasteTypeService.getWasteTypeById(wasteTypeId);
        
        
        Assert.assertEquals(Constant.PRV_WASTE_TYPE+wasteTypeBefore.getCode(), wasteTypeAfter.getCode());
        
    }
    
    
    @Test
    public void testWasteTypeServiceE() throws DaoAccessException{
        
        List<String> ids = new ArrayList<String>();
        ids.add(wasteTypeId);
        wasteTypeService.updateWasteTypeSataus(ids, Constant.DISABLE, getTicketId());
        WasteType wasteTypeAfter =  wasteTypeService.getWasteTypeById(wasteTypeId);
        
        Assert.assertEquals(Constant.DISABLE, wasteTypeAfter.getStatus());
        
        wasteTypeService.updateWasteTypeSataus(null, Constant.DISABLE, getTicketId());
        ids = new ArrayList<String>();
        ids.add("12312123123");
        wasteTypeService.updateWasteTypeSataus(null, Constant.DISABLE, getTicketId());
        
        
    }
    
   
    
    @Test
    public void testWasteTypeServiceF() throws DaoAccessException{
        WasteType wasteTypeAfter =  wasteTypeService.getWasteTypeById(wasteTypeId);
        Assert.assertEquals(wasteTypeAfter.getCode(), wasteTypeService.getWasteTypeByWasteCode(wasteTypeAfter.getCode()).getCode());
        wasteTypeAfter =  wasteTypeService.getWasteTypeByWasteCode("123");
        Assert.assertTrue( wasteTypeAfter == null);
    }
    
   
    
    @Test
    public void testWasteTypeServiceG() throws DaoAccessException{
        long timeStart = System.currentTimeMillis();
        String id = Util.uuid32();
        Assert.assertEquals(false, wasteTypeService.isWasteTypeCodeExistent(id,"HW09"));
        
        long timeEnd = System.currentTimeMillis();
        System.out.println(timeEnd - timeStart);
    }
    
    @Test
    public void testWasteTypeServiceH(){
        List<Map<String, Object>> wastetype = wasteTypeService.getWasteTypeIdDescription(new ArrayList<Object>().toArray());
        Assert.assertEquals(true, wastetype.size()>0);
    }
    
    @Test
    public void testWasteTypeServiceI() throws DaoAccessException{
        List<WasteType> wasteTypeList = wasteTypeService.getAllWateType();
        Assert.assertEquals(true, wasteTypeList.size()>0);
    }
    
    @Test
    public void testWasteTypeServiceJ() throws DaoAccessException{
        String sql = "and id =:id";
        Map<String, Object> params = new HashMap<String, Object>();
        PagingParameter paging = new PagingParameter();
        paging.setStart(0);
        paging.setLimit(1);
        params.put("id", wasteTypeId);
        List<WasteType> list = wasteTypeService.list(sql, params, paging);
        Assert.assertEquals(wasteTypeId, list.get(0).getId());
    }
    
    @Test
    public void testWasteTypeServiceK() throws DaoAccessException{
        String sql = "and id =:id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", wasteTypeId);
        int count = wasteTypeService.count(sql, params);
        Assert.assertEquals(1, count);
    }
    
    @Test
    public void testWasteTypeServiceZ() throws DaoAccessException{
        
        List<String> ids = new ArrayList<String>();
        ids.add(wasteTypeId);
        wasteTypeService.removeWasteType(ids);
        WasteType wasteTypeAfter =  wasteTypeService.getWasteTypeById(wasteTypeId);
        Assert.assertEquals(true,wasteTypeAfter==null);
    }

}
 