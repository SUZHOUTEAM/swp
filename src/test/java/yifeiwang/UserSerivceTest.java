package yifeiwang;

import cn.jiguang.common.utils.StringUtils;
import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by user on 2018/7/18.
 */
public class UserSerivceTest extends BaseTest {
    @Autowired
    private IUserInfoService userInfoService;

    @Test
    public void getCodeByOpenId() {
        try {
//            Map<String, String> resultMapper = userInfoService.getCodeByOpenId("123");
//            String code = resultMapper.get("code");
//            Assert.assertTrue(StringUtils.isNotEmpty(code));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
