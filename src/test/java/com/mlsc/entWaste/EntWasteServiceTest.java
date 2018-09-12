package com.mlsc.entWaste;

import com.mlsc.BaseTest;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.entwaste.model.EntWasteParams;
import com.mlsc.yifeiwang.entwaste.service.IEntWasteService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 2017/9/13.
 */
public class EntWasteServiceTest extends BaseTest {

    @Autowired
    private IEntWasteService entWasteService;

    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;


    @Test
    public void saveEntWasteTest() throws Exception {
        try {
            User user = new User();
            user.setUserId("2255132750874624");
            user.setEnterpriseId("2235868126758912");
            EntWasteParams entWasteParams = new EntWasteParams();
            entWasteParams.setEntId(user.getEnterpriseId());
            entWasteParams.setUnitCode("T");
            entWasteParams.setWasteCode("346-003-07");
            entWasteParams.setWasteId("004d875e03894d799458c114");
            entWasteParams.setWasteName("最新数据库测试");
            entWasteParams.setHarmfulSubstance("有毒的，有铬");
            entWasteParams.setBusinessCode("b1895803255310336,c1895801536759808");
            entWasteService.saveEntWaste(user, entWasteParams);
            entWasteService.deleteById(entWasteParams.getEntWasteId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void listEntWasteTest() throws Exception {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868126758912");
        EntWasteParams entWasteParams = new EntWasteParams();
        PagingParameter pagingParameter = new PagingParameter();
        entWasteParams.setEntId(user.getEnterpriseId());
        entWasteParams.setWasteCode("346-003-07");
        entWasteParams.setWasteName("数据库测试");
        entWasteParams.setWasteTypeDesc("HW07 热处理含氰废物");
        List<EntWasteModel> entWasteList = entWasteService.listEntWaste(user, entWasteParams, pagingParameter);
        Assert.assertTrue(entWasteList.size() > 0);
    }

    @Test
    public void listEntWasteByEntId() throws Exception {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868126758912");
        EntWasteParams entWasteParams = new EntWasteParams();
        entWasteParams.setEntId(user.getEnterpriseId());
        List<EntWasteModel> entWasteList = entWasteService.listEntWasteByEntId(entWasteParams);
        Assert.assertTrue(entWasteList.size() > 0);
    }

    @Test
    public void checkWasteNameDuplicated() throws Exception {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868126758912");
        EntWasteParams entWasteParams = new EntWasteParams();
        entWasteParams.setEntId(user.getEnterpriseId());
        entWasteParams.setUnitCode("T");
        entWasteParams.setWasteCode("346-003-07");
        entWasteParams.setWasteId("004d875e03894d799458c114");
        entWasteParams.setWasteName("最新数据库测试");
        entWasteParams.setHarmfulSubstance("有毒的，有铬");
        entWasteParams.setBusinessCode("b1895803255310336,c1895801536759808");
        entWasteService.saveEntWaste(user, entWasteParams);


        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868126758912");
        entWasteParams.setEntId(user.getEnterpriseId());
        entWasteParams.setWasteId("004d875e03894d799458c114");
        entWasteParams.setWasteName("最新数据库测试");
        boolean duplicated = entWasteService.isExistEnterpriseWaste(entWasteParams);
        Assert.assertTrue(duplicated);
        entWasteParams.setWasteName("最新数据2");
        duplicated = entWasteService.isExistEnterpriseWaste(entWasteParams);
        Assert.assertFalse(duplicated);

        entWasteService.deleteById(entWasteParams.getEntWasteId());
    }


    @Test
    public void getEntWasteByIdTest() throws Exception {

        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868126758912");
        EntWasteParams entWasteParams = new EntWasteParams();
        entWasteParams.setEntId(user.getEnterpriseId());
        entWasteParams.setUnitCode("T");
        entWasteParams.setWasteCode("346-003-07");
        entWasteParams.setWasteId("004d875e03894d799458c114");
        entWasteParams.setWasteName("最新数据库测试");
        entWasteParams.setHarmfulSubstance("有毒的，有铬");
        entWasteParams.setBusinessCode("b1895803255310336,c1895801536759808");
        entWasteService.saveEntWaste(user, entWasteParams);


        entWasteParams.setEntId(user.getEnterpriseId());
        EntWasteModel entWasteModel = entWasteService.getEntWasteDetailById(entWasteParams);
        Assert.assertTrue(entWasteModel.getWasteName().equals("最新数据库测试"));

        entWasteService.deleteById(entWasteParams.getEntWasteId());
    }

    @Test
    public void updateEntWaste() throws Exception {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868126758912");
        EntWasteParams entWasteParams = new EntWasteParams();
        entWasteParams.setEntId(user.getEnterpriseId());
        entWasteParams.setUnitCode("T");
        entWasteParams.setWasteCode("346-003-07");
        entWasteParams.setWasteId("004d875e03894d799458c114");
        entWasteParams.setWasteName("最新数据库测试");
        entWasteParams.setHarmfulSubstance("有毒的，有铬");
        entWasteParams.setBusinessCode("b1895803255310336,c1895801536759808");
        String entWasteId = entWasteService.saveEntWaste(user, entWasteParams);
        entWasteParams = new EntWasteParams();
        entWasteParams.setEntWasteId(entWasteId);
        PagingParameter pagingParameter = new PagingParameter();
        EntWasteModel entWasteModel = entWasteService.getEntWasteDetailById(entWasteParams);
        Assert.assertTrue(entWasteModel != null);
        Assert.assertTrue(entWasteModel.getInquiried() == false);

        entWasteModel.setWasteCode("261-068-38");
        entWasteModel.setWasteId("004d875e03894d799458c0597954a9cd");
        entWasteModel.setWasteName("更新危废名称测试");
        entWasteService.updateEntWaste(user, entWasteModel);


        entWasteModel = entWasteService.getEntWasteDetailById(entWasteParams);
        Assert.assertTrue(entWasteModel != null);
        Assert.assertTrue(entWasteModel.getInquiried() == false);
        Assert.assertTrue(entWasteModel.getWasteId().equals("004d875e03894d799458c0597954a9cd"));
        Assert.assertTrue(entWasteModel.getWasteCode().equals("261-068-38"));

        entWasteService.deleteById(entWasteModel.getEntWasteId());
    }


    @Test
    public void insertEntWaste() throws Exception {
//        List<SysEnterpriseBase> enterpriseBases = enterpriseBaseService.listEnterpriseInfoByIndustry("AutoRepair");
//        for(SysEnterpriseBase enterpriseBase:enterpriseBases){
//            entWasteService.insertEntWaste(enterpriseBase.getEntId());
//        }

    }
}
