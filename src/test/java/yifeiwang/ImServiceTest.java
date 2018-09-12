package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.im.service.ImService;
import com.mlsc.yifeiwang.user.entity.UserExtended;
import com.mlsc.yifeiwang.user.model.UserInfo;
import com.mlsc.yifeiwang.user.service.IUserExtendedService;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/12/7.
 */
public class ImServiceTest extends BaseTest {
    @Autowired
    private ImService imService;

    @Autowired
    private IUserExtendedService userExtendedService;

    @Autowired
    private IUserInfoService userInfoService;


    @Test
    public void genterateIMTokenTest() {
        try {
            List<UserInfo> list = userExtendedService.listUserExtends();
            List<UserExtended> userExtendedList = new ArrayList<>();
            for(UserInfo userInfo :list){
                String token = imService.genterateIMToken(userInfo.getPhoneNum(), userInfo.getUserName());
                UserExtended userExtended =  userExtendedService.selectById(userInfo.getUserExtendsId());
                userExtended.setToken(token);
                userExtendedList.add(userExtended);
            }
            userExtendedService.updateBatchById(userExtendedList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("320010".substring(0, 2));

    }
}
