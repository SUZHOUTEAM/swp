package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseBonus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseBonusService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by user on 2018/4/9.
 */
public class EntReleaseBonusServiceTest extends BaseTest {
    @Autowired
    private IEntReleaseBonusService entReleaseBonusService;

    @Test
    public void saveReleaseBonusTest() {
        User user = new User();
        user.setUserId("2657153369360384");
        user.setEnterpriseId("2235871233280000");
        EntReleaseBonus entReleaseBonus = new EntReleaseBonus();
        entReleaseBonus.setReleaseId("53959ea420684eeb9cb3c8bae94c1d47");
        entReleaseBonus.setBrowserType("1");
        try {
            String result = entReleaseBonusService.saveReleaseBonus(user, entReleaseBonus);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
