package yifeiwang;

import com.baomidou.mybatisplus.plugins.Page;
import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.bindserve.entity.EntRecharge;
import com.mlsc.yifeiwang.bindserve.model.EntRechargeParam;
import com.mlsc.yifeiwang.bindserve.service.IEntRechargeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by user on 2018/5/2.
 */
public class EntRechargeServiceTest extends BaseTest {
    @Autowired
    private IEntRechargeService entRechargeService;

    @Test
    public void listRecharge() {
        try {
            EntRechargeParam entBindServeParam = new EntRechargeParam();
            Page<EntRecharge> page = new Page<>(0, 1);
            entBindServeParam.setEntId("2235872864471040");
            Page<EntRecharge> list = entRechargeService.listRecharge(entBindServeParam, page);
            Assert.assertTrue(list.getSize()>0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateUserAccount() {
        try {
            entRechargeService.updateUserAccount("d17c062b004e41e0a955d274126b2b3b");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
