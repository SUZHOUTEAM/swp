package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.appmanagement.entity.AppManagement;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.appmanagement.service.IAppManagementService;
import com.mlsc.yifeiwang.appmanagement.model.AppManagementParam;
import com.mlsc.yifeiwang.sms.common.JPushUtil;
import com.mlsc.yifeiwang.sms.model.JPushMsgParameter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/12/11.
 */
public class AppManagementServiceTest extends BaseTest {
    @Autowired
    private IAppManagementService appService;

    @Test
    public void appManagementTest() {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235872864520192");
        AppManagement management = new AppManagement();
        try {
            management.setAppType("IOS");
            management.setVersionCode("IOS-1001");
            management.setEntType("PRODUCTION");
            management.setDescription("upload latest ios application");
            appService.saveAppManagement(user, management);
            AppManagement checkManagement = appService.getLatestVersion(management);
            Assert.assertTrue(checkManagement.getVersionCode().equals(management.getVersionCode()));
            management.setFileId("file_1001");
            appService.updateAppManagement(user, management);
            checkManagement = appService.getLatestVersion(management);
            Assert.assertTrue(checkManagement.getFileId().equals(management.getFileId()));
            appService.deleteById(checkManagement.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jPushTest() {
        List<JPushMsgParameter> getJpushMsgParameterList=new ArrayList<>();
        JPushMsgParameter jPushMsgParameter=new JPushMsgParameter();
        JPushUtil.sendMessages(getJpushMsgParameterList);
    }

    @Test
    public void listAppManagementTest() {
        PagingParameter paging = new PagingParameter();
        AppManagementParam managementParam = new AppManagementParam();
        paging.setStart(0);
        paging.setLimit(5);
        managementParam.setAppType("IOS");
        managementParam.setEntType("PRODUCTION");
        try {
            List<AppManagement> list = appService.listAppManagement(paging, managementParam);
            Assert.assertTrue(list.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getLatestVersionTest() {
        PagingParameter paging = new PagingParameter();
        AppManagementParam managementParam = new AppManagementParam();
        paging.setStart(0);
        paging.setLimit(5);
        managementParam.setAppType("ANDROID");
        managementParam.setEntType("DISPOSITION");
        try {
            AppManagement appManagement = appService.getLatestVersion(managementParam);
            Assert.assertTrue(appManagement != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
