/**
 * 
 */
package com.mlsc.waste.licence.service.impl;

import com.mlsc.waste.licence.dao.LicenceItemDao;
import com.mlsc.waste.licence.model.OperationLicenceItem;
import com.mlsc.waste.licence.service.LicenceItemService;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml"})
public class LicenceItemServiceImplTest {
    
    @Autowired
    private UserLoginService userLoginService;
    
    @Autowired
    private LicenceItemService licenceItemService;
    
    @Autowired
    private LicenceItemDao licenceItemDao;
    
    private OperationLicenceItem doLicenceItemDataReady() throws Exception {
        OperationLicenceItem testItem = new OperationLicenceItem();
        testItem.setLicence_id("AAAAAAAAAA1111");
        testItem.setDisposition_type("CCCCCCCCCC1111");
        testItem.setApproved_quantity("10000");
        testItem.setExcuted_quantity("50");
        testItem.setRemark(null);
        testItem.setCreate_by("2000000000");
        testItem.setCreate_time(Util.datetimeToString(new Date()));
        testItem.setEdit_by("2000000000");
        testItem.setEdit_time(Util.datetimeToString(new Date()));
        testItem.setValid(Constant.IS_VALID);
        
        return testItem;
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceItemServiceImpl#getOperationLicenceItemByItemId(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetOperationLicenceItemByItemId() throws Exception {
        OperationLicenceItem testItem = doLicenceItemDataReady();
        licenceItemDao.save(testItem,Util.uuid32());
      //-------------------------数据准备结束-----------------------------------------
        OperationLicenceItem getItem = licenceItemService.getOperationLicenceItemByItemId(testItem.getId());
        
        Set<String> set = new HashSet<String>();
        set.add("licence_id");
        set.add("disposition_type");
        boolean compareValue = Util.compareValue(testItem,getItem,OperationLicenceItem.class, set);
        assertTrue(compareValue);
      //-------------------------删除操作-----------------------------------------
        licenceItemDao.delete(testItem.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceItemServiceImpl#removeItemById(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testRemoveItemById() throws Exception {
        OperationLicenceItem testItem = doLicenceItemDataReady();
        licenceItemDao.save(testItem,Util.uuid32());
      //-------------------------数据准备结束-----------------------------------------
        OperationLicenceItem getItem = licenceItemService.getOperationLicenceItemByItemId(testItem.getId());
        assertTrue(getItem != null);
        licenceItemService.removeItemById(testItem.getId());
        
        getItem = licenceItemService.getOperationLicenceItemByItemId(testItem.getId());
        assertTrue(getItem == null);
      //-------------------------删除操作-----------------------------------------
        licenceItemDao.delete(testItem.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceItemServiceImpl#saveLicenceItem(com.mlsc.waste.licence.model.OperationLicenceItem, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testSaveLicenceItem() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        OperationLicenceItem testItem = doLicenceItemDataReady();
      //-------------------------数据准备结束-----------------------------------------
        String itemId = licenceItemService.saveLicenceItem(testItem, ticketId);
        OperationLicenceItem getItem = licenceItemService.getOperationLicenceItemByItemId(itemId);
        assertTrue(getItem != null);
        Set<String> set = new HashSet<String>();
        set.add("licence_id");
        set.add("disposition_type");
        boolean compareValue = Util.compareValue(testItem,getItem,OperationLicenceItem.class, set);
        assertTrue(compareValue);
      //-------------------------删除操作-----------------------------------------
        licenceItemDao.delete(itemId);
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceItemServiceImpl#isHasDispositionItem(java.lang.String, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testIsHasDispositionItem() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        OperationLicenceItem testItem = doLicenceItemDataReady();
      //-------------------------数据准备结束-----------------------------------------
        String itemId = licenceItemService.saveLicenceItem(testItem, ticketId);
        String testItemId = licenceItemService.isHasDispositionItem("AAAAAAAAAA2222", "CCCCCCCCCC1111");
        assertTrue(StringUtils.isBlank(testItemId));
        
        testItemId = licenceItemService.isHasDispositionItem("AAAAAAAAAA1111", "CCCCCCCCCC1111");
        assertTrue(itemId.equals(testItemId));
      //-------------------------删除操作-----------------------------------------
        licenceItemDao.delete(itemId);
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceItemServiceImpl#updateLicenceItem(com.mlsc.waste.licence.model.OperationLicenceItem, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testUpdateLicenceItem() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        OperationLicenceItem testItem = doLicenceItemDataReady();
        testItem.setDisposition_type("CCCCCCCCCC2222");
        testItem.setApproved_quantity("20000");
        String itemId = licenceItemService.saveLicenceItem(testItem, ticketId);
      //-------------------------数据准备结束-----------------------------------------
        OperationLicenceItem getItem = licenceItemService.getOperationLicenceItemByItemId(itemId);
        assertTrue(getItem != null);
        assertTrue("CCCCCCCCCC2222".equals(getItem.getDisposition_type()));
        assertTrue("20000.0".equals(getItem.getApproved_quantity()));
        
        getItem.setDisposition_type("CCCCCCCCCC3333");
        getItem.setApproved_quantity("30000");
        
        licenceItemService.updateLicenceItem(getItem, ticketId);
        getItem = licenceItemService.getOperationLicenceItemByItemId(itemId);
        assertTrue(getItem != null);
        assertTrue("CCCCCCCCCC3333".equals(getItem.getDisposition_type()));
        assertTrue("30000.0".equals(getItem.getApproved_quantity()));
        
      //-------------------------删除操作-----------------------------------------
        licenceItemDao.delete(itemId);
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceItemServiceImpl#deleteLicenceItem(java.util.List, java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testDeleteLicenceItem() throws Exception {
        String ticketId = userLoginService.userLogin("15301540690", "test123");
        OperationLicenceItem testItem1 = doLicenceItemDataReady();
        testItem1.setLicence_id("DDDDDDDDD1111");
        licenceItemDao.save(testItem1, Util.uuid32());
        OperationLicenceItem testItem2 = doLicenceItemDataReady();
        testItem2.setLicence_id("DDDDDDDDD2222");
        licenceItemDao.save(testItem2, Util.uuid32());
        OperationLicenceItem testItem3 = doLicenceItemDataReady();
        testItem3.setLicence_id("DDDDDDDDD3333");
        licenceItemDao.save(testItem3, Util.uuid32());
        
      //-------------------------数据准备结束-----------------------------------------
        OperationLicenceItem saveDetail1 = licenceItemDao.get(testItem1.getId());
        assertTrue(saveDetail1 != null);
        assertTrue(Constant.IS_VALID.equals(saveDetail1.getValid()));
        OperationLicenceItem saveDetail2 = licenceItemDao.get(testItem2.getId());
        assertTrue(saveDetail2 != null);
        assertTrue(Constant.IS_VALID.equals(saveDetail2.getValid()));
        OperationLicenceItem saveDetail3 = licenceItemDao.get(testItem3.getId());
        assertTrue(saveDetail3 != null);
        assertTrue(Constant.IS_VALID.equals(saveDetail3.getValid()));
        
        List<String> ids = new ArrayList<String>(3);
        licenceItemService.deleteLicenceItem(ids, ticketId);
        ids.add("DDDDDDDDD1111");
        ids.add("DDDDDDDDD2222");
        ids.add("AAAAAAAAAA4444");
        licenceItemService.deleteLicenceItem(ids, ticketId);
        
        OperationLicenceItem update1 = licenceItemDao.get(testItem1.getId());
        assertTrue(update1 != null);
        assertTrue(Constant.IS_NOT_VALID.equals(update1.getValid()));
        OperationLicenceItem update2 = licenceItemDao.get(testItem2.getId());
        assertTrue(update2 != null);
        assertTrue(Constant.IS_NOT_VALID.equals(update2.getValid()));
        OperationLicenceItem update3 = licenceItemDao.get(testItem3.getId());
        assertTrue(update3 != null);
        assertTrue(Constant.IS_VALID.equals(update3.getValid()));
      //-------------------------删除操作-----------------------------------------
        licenceItemDao.delete(testItem1.getId());
        licenceItemDao.delete(testItem2.getId());
        licenceItemDao.delete(testItem3.getId());
    }
    
    /**
     * Test method for {@link com.mlsc.waste.licence.service.impl.LicenceItemServiceImpl#getOperationLicenceItems(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testGetOperationLicenceItems() throws Exception {
        OperationLicenceItem testItem1 = doLicenceItemDataReady();
        testItem1.setLicence_id("DDDDDDDDD1111");
        licenceItemDao.save(testItem1, Util.uuid32());
        OperationLicenceItem testItem2 = doLicenceItemDataReady();
        testItem2.setLicence_id("DDDDDDDDD1111");
        licenceItemDao.save(testItem2, Util.uuid32());
        OperationLicenceItem testItem3 = doLicenceItemDataReady();
        testItem3.setLicence_id("DDDDDDDDD3333");
        licenceItemDao.save(testItem3, Util.uuid32());
        
      //-------------------------数据准备结束-----------------------------------------
        List<OperationLicenceItem> testItems = licenceItemService.getOperationLicenceItems("DDDDDDDDD1111");
        assertTrue(testItems != null);
        assertTrue(testItems.size() == 2 && "DDDDDDDDD1111".equals(testItems.get(0).getLicence_id()));
      //-------------------------删除操作-----------------------------------------
        licenceItemDao.delete(testItem1.getId());
        licenceItemDao.delete(testItem2.getId());
        licenceItemDao.delete(testItem3.getId());
    }
}
