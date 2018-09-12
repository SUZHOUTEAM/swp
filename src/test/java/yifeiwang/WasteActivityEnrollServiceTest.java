package yifeiwang;

import cn.jiguang.common.utils.StringUtils;
import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.activity.common.EnrollStatusEnum;
import com.mlsc.yifeiwang.activity.entity.WasteActivityEnroll;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.activity.model.WasteActivityEnrollModel;
import com.mlsc.yifeiwang.activity.service.IWasteActivityEnrollService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/2/6.
 */
public class WasteActivityEnrollServiceTest extends BaseTest {

    @Autowired
    private IWasteActivityEnrollService wasteActivityEnrollService;

    @Test
    public void saveWasteActivityEnrollTest() {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235872864520192");
        WasteActivityEnroll wasteActivityEnroll = new WasteActivityEnroll();
        wasteActivityEnroll.setActivityId("cc0b472c19dc44589a463910b79bee61");
        try{
            wasteActivityEnrollService.saveWasteActivityEnroll(user,wasteActivityEnroll);
            String id = wasteActivityEnroll.getId();
            Assert.assertTrue(StringUtils.isNotEmpty(id));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void auditAproveTest() {
        User user = new User();
        user.setUserId("1000000000000");
        List<String> ids = new ArrayList<String>();
        ids.add("1fc43b6b8fe447f5a2582a30b670705b");
        try{
            wasteActivityEnrollService.auditApprove(user,ids);
            WasteActivityEnroll activityEnroll = wasteActivityEnrollService.selectById("1fc43b6b8fe447f5a2582a30b670705b");
            Assert.assertTrue(activityEnroll.getBusiStatus().equals(EnrollStatusEnum.PAYMENTSUCCESS.getCode()));
            wasteActivityEnrollService.auditReject(user,ids);
            activityEnroll = wasteActivityEnrollService.selectById("1fc43b6b8fe447f5a2582a30b670705b");
            Assert.assertTrue(activityEnroll.getBusiStatus().equals(EnrollStatusEnum.PAYMENTFAILED.getCode()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void listActivityEnrollTest() {
        User user = new User();
        user.setUserId("2255132750874624");
        WasteActivityEnrollModel wasteActivityEnroll = new WasteActivityEnrollModel();
//        wasteActivityEnroll.setActivityId("cc0b472c19dc44589a463910b79bee61");
        wasteActivityEnroll.setEntId("2235872864520192");
        List<String> infoList = new ArrayList<String>();
        PagingParameter pagingParameter = new PagingParameter();
        try{
            List<WasteActivityEnrollModel> list =  wasteActivityEnrollService.listActivityEnroll(wasteActivityEnroll,pagingParameter);
            Assert.assertTrue(list.size()>0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void registrationEnrollActivityTest() {
        User user = new User();
        user.setUserId("2255132750874624");
        WasteActivityEnrollModel wasteActivityEnroll = new WasteActivityEnrollModel();
        List<String> infoList = new ArrayList<String>();
        PagingParameter pagingParameter = new PagingParameter();
        try{
            List<WasteActivityEnrollModel> list =  wasteActivityEnrollService.registrationEnrollActivity(wasteActivityEnroll,pagingParameter);
            Assert.assertTrue(list.size()>0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
