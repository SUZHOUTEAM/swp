package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.bindserve.service.IEntBitcionAccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by user on 2018/4/28.
 */
public class EntBitcionAccountServiceTest extends BaseTest {
    @Autowired
    private IEntBitcionAccountService entBitcionAccountService;

    @Test
    public void getFreeBitcion() {
        try {
            User user = new User();
            user.setUserId("2255132750874624");
            user.setEnterpriseId("2235872864471041");
            entBitcionAccountService.getAccountByUserId(user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
