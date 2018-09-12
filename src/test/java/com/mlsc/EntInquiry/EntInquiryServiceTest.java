package com.mlsc.EntInquiry;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.entinquiry.common.InquiryStatusEnum;
import com.mlsc.yifeiwang.entinquiry.entity.EntInquiry;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailParam;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryParam;
import com.mlsc.yifeiwang.entinquiry.service.IEntInquiryService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 2017/9/14.
 */
public class EntInquiryServiceTest extends BaseTest {
    @Autowired
    private IEntInquiryService inquiryService;

    @Test
    public void saveEntInquiryTest(){
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868126758912");

        EntInquiryParam inquiryParam =  new EntInquiryParam();
        inquiryParam.setReleaseEntId("ReleaseEntId");
        inquiryParam.setReleaseId("ReleaseId");
        inquiryParam.setQuotedType(1);
        inquiryParam.setTotalAmount("2");
        inquiryParam.setTotalPrice(12.4);
        inquiryParam.setRemark("可以议价");

        EntInquiryDetailParam detail = new EntInquiryDetailParam();
        detail.setReleaseDetailId("ReleaseDetailId");
        detail.setPrice(2);
        detail.setTotalPrice(12.2);

        inquiryParam.getInquiryDetail().add(detail);
        try{
            inquiryService.saveEntInquiry(user,inquiryParam);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void listEntInquiryTest(){
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868915894272");
        PagingParameter pagingParameter = new PagingParameter();
        EntInquiryParam inquiryParam = new EntInquiryParam();

        try{
            List<EntInquiryModel> list = inquiryService.listWasteInquiry(user,pagingParameter,inquiryParam);
            Assert.assertTrue(list.size()>0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void listEntInquiryByReleaseIdTest(){
        PagingParameter pagingParameter = new PagingParameter();
        EntInquiryParam inquiryParam = new EntInquiryParam();
        inquiryParam.setReleaseId("1dcbb8dd983c42948c621cd6c291bb86");
        try{
            List<EntInquiryModel> list = inquiryService.listWasteInquiry(null,pagingParameter,inquiryParam);
            Assert.assertTrue(list.size()>0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void rejectEntInquiryTest(){
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868915894272");
        EntInquiryParam inquiryParam = new EntInquiryParam();
        inquiryParam.setInquiryId("382ab03b5adb42d6ac94c8643998edb2");

        try{
            inquiryService.rejectEntInquiry(user,inquiryParam);
            EntInquiry entInquiry = inquiryService.selectById(inquiryParam.getInquiryId());
            Assert.assertTrue(entInquiry.getBusiStatus().equals(InquiryStatusEnum.REFUSED.getCode()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void confirmEntInquiryTest(){
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235868915894272");
        EntInquiryParam inquiryParam = new EntInquiryParam();
        inquiryParam.setInquiryId("382ab03b5adb42d6ac94c8643998edb2");
        inquiryParam.setReleaseId("b2816556e66848c0ae1e6eb254f44358");
        try{
            inquiryService.confirmEntInquiry(user,inquiryParam);
            EntInquiry entInquiry = inquiryService.selectById(inquiryParam.getInquiryId());
            Assert.assertTrue(entInquiry.getBusiStatus().equals(InquiryStatusEnum.ACCEPT.getCode()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
