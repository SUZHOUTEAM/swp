package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.user.entity.UserExtended;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.waste.utils.Constant;
import com.mlsc.yifeiwang.user.service.IUserExtendedService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 2018/7/11.
 */
public class UserExtendsTest extends BaseTest {
    @Autowired
    private IUserExtendedService userExtendedService;

    @Test
    public void saveUserExtendsTest() {
        UserExtended record = new UserExtended();
        record.setSysUserId("123");
        record.setMobileStatus(Constant.IS_VALID);
        record.setToken("tokenTest");
        userExtendedService.insert(record);
        UserExtended insertCheck = userExtendedService.getUserExtendedByUserId("123");
        Assert.assertTrue(insertCheck != null);
    }


    @Test
    public void updateUserExtendsTest() {
        UserExtended record = userExtendedService.getUserExtendedByUserId("123");
        record.setEmailStatus("2");
        userExtendedService.updateById(record);
        record = userExtendedService.getUserExtendedByUserId("123");
        Assert.assertTrue(record.getEmailStatus().equals("2"));
    }


    @Test
    public void listUserApproveVo() {
        List<UserApproveVo> approveVos = userExtendedService.listUserApproveVo("2766209253935104", "PASS", "REGULAR");
        Assert.assertTrue(approveVos.size() > 0);
    }

    @Test
    public void getAdminUserByEnterpriseId() {
        UserExtended approveVos = userExtendedService.getAdminUserByEnterpriseId("2690062582597632");
        Assert.assertTrue(approveVos != null);
    }

    @Test
    public void saveOrUpdateUserResgistration() {
        try{
            userExtendedService.saveOrUpdateUserResgistration("234s","regTest");
            UserExtended approveVos = userExtendedService.getAdminUserByEnterpriseId("2690062582597632");
            userExtendedService.saveOrUpdateUserResgistration("234s","345");
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
