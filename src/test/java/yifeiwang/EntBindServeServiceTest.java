package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.bindserve.model.EntBindServeModel;
import com.mlsc.yifeiwang.bindserve.model.EntBindServeParam;
import com.mlsc.yifeiwang.bindserve.service.IEntBindServeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 2018/4/28.
 */
public class EntBindServeServiceTest extends BaseTest {
    @Autowired
    private IEntBindServeService saveBindServe;


    @Test
    public void saveBitcionAccount() {
        try {
            User user = new User();
            user.setUserId("2255132750874624");
            user.setEnterpriseId("2235872864471040");
            Date date = new Date();
            EntBindServeParam entBindServeParam = new EntBindServeParam();
            entBindServeParam.setBindServiceId("123");
            entBindServeParam.setServiceType("RESOURCE_POOL");
            entBindServeParam.setConsumeAmount(1);
            saveBindServe.saveBindServe(user, entBindServeParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateBitcion() {
        try {
            User user = new User();
            user.setUserId("2255132750874624");
            EntBindServeParam entBindServeParam = new EntBindServeParam();
            entBindServeParam.setId("3e0ca13332da4020836d62337d11735f");
            entBindServeParam.setRemark("易废网测试");
            saveBindServe.updateBindServe(user, entBindServeParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void listBitcion() {
        try {
            User user = new User();
            user.setUserId("2255132750874624");
            EntBindServeParam entBindServeParam = new EntBindServeParam();
            entBindServeParam.setServiceType("ACTIVITY");
            List<EntBindServeModel> result = saveBindServe.listBindServe(entBindServeParam, null);
            Assert.assertTrue(result.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void listButlerServicesByEntId() {
        try {
            EntBindServeParam entBindServeParam = new EntBindServeParam();
            entBindServeParam.setBindEntId("2461989410768896");
            List<EntBindServeModel> result = saveBindServe.listButlerServicesByEntId(entBindServeParam);
            Assert.assertTrue(result.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
