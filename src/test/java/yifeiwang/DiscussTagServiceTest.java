package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.discusstag.common.TagStatusEnum;
import com.mlsc.yifeiwang.discusstag.common.TagTypeEnum;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.discusstag.entity.DiscussTag;
import com.mlsc.yifeiwang.discusstag.model.DiscussTagParam;
import com.mlsc.yifeiwang.discusstag.service.IDiscussTagService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 2017/11/13.
 */
public class DiscussTagServiceTest extends BaseTest {
    @Autowired
    private IDiscussTagService discussTagService;

    @Test
    public void saveDiscussTagTest() {
        try {
            User user = new User();
            user.setUserId("2255132750874624");
            user.setEnterpriseId("2235872864520192");
            Date date = new Date();
            DiscussTagParam discussTagParam = new DiscussTagParam();
            discussTagParam.setReleaseId("test123123Test");
            discussTagParam.setTagType(TagTypeEnum.SAMPLING.getCode());
            discussTagParam.setTagStatus(TagStatusEnum.SAMPLING.getCode());
            discussTagParam.setContacts("曹燕燕");
            discussTagParam.setContactsTel("18120046886");
            discussTagParam.setSampleDate(date);
            discussTagService.saveDiscussTag(user, discussTagParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listTagList() {
        try {
            List<DiscussTag> wasteActivityPage = discussTagService.listTagListByReleaseId("test123123Test","2235872864520192");
            Assert.assertTrue(wasteActivityPage != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void countTagInfo() {
        try {
            DiscussTagParam tagParam = discussTagService.countTagInfo("test123123Test","2235872864520192");
            Assert.assertTrue(tagParam != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
