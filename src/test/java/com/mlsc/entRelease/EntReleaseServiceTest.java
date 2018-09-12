package com.mlsc.entRelease;

import cn.jiguang.common.utils.StringUtils;
import com.mlsc.BaseTest;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.wasterealase.model.*;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/9/13.
 */
public class EntReleaseServiceTest extends BaseTest {
    @Autowired
    private IEntReleaseService entReleaseService;

    @Test
    public void saveEntReleaseTest(){
        User user = new User();
        user.setUserId("2787499190732800");
        user.setUserName("哥");
        user.setEnterpriseName("测试企业");
        user.setPhoneNo("13866523582");
        user.setEnterpriseId("2527082607773696");
        user.setEntType("FACILITATOR");
        EntReleaseParam entReleases = new EntReleaseParam();
        entReleases.setEntId(user.getEnterpriseId());
        entReleases.setReleaseCount(4);
        entReleases.setRemark("有新的危废要发布");

        List<EntReleaseDetailParam> releaseDetail = new ArrayList<EntReleaseDetailParam>();
        EntReleaseDetailParam release1 = new EntReleaseDetailParam();
        release1.setEntWasteId("ff2715c90d19489fa4e1022916d2aa97");
        release1.setReleaseAmount(20.00);
        releaseDetail.add(release1);
        EntReleaseDetailParam release2 = new EntReleaseDetailParam();
        release2.setEntWasteId("ebe8ec26c7764dc8a4f25e65687a3b99");
        release2.setReleaseAmount(10.00);
        releaseDetail.add(release2);

        entReleases.setReleaseDetail(releaseDetail);
        try{
//            String releaseId = entReleaseService.saveEntRelease(user,entReleases);
//            Assert.assertTrue(StringUtils.isNotEmpty(releaseId));
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void listWasteEntReleaseTest(){
        User user = new User();
        user.setEnterpriseId("2235872864520192");
        EntReleaseParam releaseParams = new EntReleaseParam();
        PagingParameter pagingParameter  = new PagingParameter();
        pagingParameter.setPageIndex(0);
        pagingParameter.setPageSize(5);
        releaseParams.setLicenceId("f438bed2a63a4f35b53d5c93260bdbeb");
        releaseParams.setAmountInterval(0.0d);
        AmountInterval amountInterval1 = new AmountInterval();
        amountInterval1.setStartAmount(2.0d);
        amountInterval1.setEndAmount(4.0d);
        AmountInterval amountInterval2 = new AmountInterval();
        amountInterval2.setStartAmount(4.0d);
        amountInterval2.setEndAmount(6.0d);
        List<AmountInterval> amount = new ArrayList<AmountInterval>();
        amount.add(amountInterval1);
        amount.add(amountInterval2);
        List<String> wasteCodeList =  new ArrayList<String>();
        wasteCodeList.add("38");
        releaseParams.setWasteCodeList(wasteCodeList);
        List<String> cantonCodeList = new ArrayList<>();
        cantonCodeList.add("32");
        cantonCodeList.add("01");
        releaseParams.setCantonCodeList(cantonCodeList);
        releaseParams.setAmountIntervalList(amount);


        try{
            List<EntReleaseModel> list =  entReleaseService.listWasteEntRelease(user,releaseParams,pagingParameter);
            Assert.assertTrue(list.size()>0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void listWasteEntRelease4FacilitatorEntTest(){
        User user = new User();
        user.setEnterpriseId("2542529780942848");
        EntReleaseParam releaseParams = new EntReleaseParam();
        PagingParameter pagingParameter  = new PagingParameter();
        pagingParameter.setPageIndex(0);
        pagingParameter.setPageSize(5);
        releaseParams.setAmountInterval(0.0d);
        AmountInterval amountInterval1 = new AmountInterval();
        amountInterval1.setStartAmount(2.0d);
        amountInterval1.setEndAmount(4.0d);
        AmountInterval amountInterval2 = new AmountInterval();
        amountInterval2.setStartAmount(4.0d);
        amountInterval2.setEndAmount(6.0d);
        List<AmountInterval> amount = new ArrayList<AmountInterval>();
        amount.add(amountInterval1);
        amount.add(amountInterval2);
        releaseParams.setAmountIntervalList(amount);

        releaseParams.setCurrentEntId("2542529780942848");
        try{
            List<EntReleaseModel> list =  entReleaseService.listWasteEntRelease4FacilitatorEnt(releaseParams,pagingParameter);
            Assert.assertTrue(list.size()>0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void listWasteEntReleaseByEnterId(){
        User user = new User();
        user.setEnterpriseId("2235872864520192");
        EntReleaseParam releaseParams = new EntReleaseParam();
        PagingParameter pagingParameter  = new PagingParameter();
        pagingParameter.setPageIndex(0);
        pagingParameter.setPageSize(5);
        try{
            List<EntReleaseModel> list =  entReleaseService.listWasteEntReleaseByEnterId(user,releaseParams,pagingParameter);
            Assert.assertTrue(list.size()>0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void listWasteEntReleaseByActivityId(){
        User user = new User();
        user.setEnterpriseId("2235872864520192");
        EntReleaseParam releaseParams = new EntReleaseParam();
        releaseParams.setActivityId("3843a5ac70514c21ad1c982a076a7b3e");
        PagingParameter pagingParameter  = new PagingParameter();
        pagingParameter.setPageIndex(0);
        pagingParameter.setPageSize(5);
        try{
            List<EntReleaseModel> list =  entReleaseService.listWasteEntReleaseByActivityId(releaseParams,pagingParameter);
            Assert.assertTrue(list.size()>0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void countActivityStatusByActivityId(){
        try{
            List<ReleaseStatusModel> list =  entReleaseService.countActivityStatusByActivityId("3843a5ac70514c21ad1c982a076a7b3e");
            Assert.assertTrue(list.size()>0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteWasteEntReleaseByReleaseId(){
        User user = new User();
        EntReleaseParam releaseParams = new EntReleaseParam();
        releaseParams.setReleaseId("1dcbb8dd983c42948c621cd6c291bb86");
        user.setUserId("123");
        try{
            entReleaseService.deleteWasteEntReleaseByReleaseId(user,releaseParams);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
