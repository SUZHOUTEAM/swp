package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.model.SysEnterpriseModel;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 2017/10/23.
 */
public class SysEnterpriseBaseServiceTest extends BaseTest {
    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;

    @Test
    public void updateEnterpriseInfo() {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235872864520192");
        SysEnterpriseBase enterpriseBase = new SysEnterpriseBase();
        try {
            enterpriseBase = enterpriseBaseService.getEnterpriseInfoById("2235872864520192");
            enterpriseBase.setSummary("testSummary");
            enterpriseBase.setSalesNote("testSales");
            enterpriseBase.setOtherNote("otherNotes");
            enterpriseBaseService.updateEnterprise(user, enterpriseBase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkEntInfoCompletedTest() {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235872864520192");
        SysEnterpriseModel enterpriseModel = new SysEnterpriseModel();
        enterpriseModel.setEntId("2235872864520192");
        try {
            enterpriseModel = enterpriseBaseService.checkEntInfoCompleted(user);
            Assert.assertTrue(enterpriseModel.isAllCompleted() == false);
            user.setEnterpriseId("2235868915894272");
            enterpriseModel = enterpriseBaseService.checkEntInfoCompleted(user);
            Assert.assertTrue(enterpriseModel.isAllCompleted());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void enterpriseSummaryInfoTest() {
        User user = new User();
        user.setUserId("2235868915894272");
        user.setEnterpriseId("2235868915894272");
        SysEnterpriseBase enterpriseBase = new SysEnterpriseBase();
        enterpriseBase.setEntId("2235868915894272");
        try {
            SysEnterpriseModel enterpriseModel = enterpriseBaseService.getEnterpriseSummaryInfo(enterpriseBase);
            Assert.assertTrue(enterpriseModel != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getEnterpriseByEntIdTest() {
        try {
            RPCSysEnterpriseBaseVo enterpriseBaseVo = enterpriseBaseService.getEnterpriseByEntId("2235868915894272");
            Assert.assertTrue(enterpriseBaseVo != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getEntDropDownListTest() {
        try {
            List<RPCSysEnterpriseBaseVo> sysEnterpriseBaseVoList = enterpriseBaseService.listEntDropDown("盐城");
            Assert.assertTrue(sysEnterpriseBaseVoList != null && sysEnterpriseBaseVoList.size()>0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCoordinateByEntIdTest() {
        try {
            RPCSysEnterpriseBaseVo sysEnterpriseBaseVo = enterpriseBaseService.getCoordinateByEntId("2235868915894272");
            Assert.assertTrue(sysEnterpriseBaseVo != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void checkResponsibleAreaTest() {
        try {
            boolean checkResponsibleArea = enterpriseBaseService.checkResponsibleArea("110228");
            Assert.assertTrue(checkResponsibleArea);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
