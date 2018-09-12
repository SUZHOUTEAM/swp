package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.wasteinformation.model.WasteInformationModel;
import com.mlsc.yifeiwang.wasteinformation.model.WasteInformationParam;
import com.mlsc.yifeiwang.wasteinformation.service.IWasteInformationService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 2018/6/13.
 */
public class WasteInformationServiceTest extends BaseTest {
    @Autowired
    private IWasteInformationService wasteInformationService;

    @Test
    public void saveWasteInformationtTest() {
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235872864520192");
        WasteInformationParam wasteInformationParam = new WasteInformationParam();
        Result<Boolean> result = new Result<>();
        List<String> errors = result.getInfoList();

        try {
            boolean saveResult = wasteInformationService.saveWasteInformation(wasteInformationParam, user, errors);
            Assert.assertTrue(saveResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listWasteInformation() {
        WasteInformationParam wasteInformationParam = new WasteInformationParam();

        PagingParameter pagingParameter = new PagingParameter();
        pagingParameter.setStart(0);
        pagingParameter.setLimit(10);
        try {
            List<WasteInformationModel> informationModels = wasteInformationService.listWasteInformation(wasteInformationParam, pagingParameter);
            Assert.assertTrue(informationModels.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateWasteInformation() {
        WasteInformationParam wasteInformationParam = new WasteInformationParam();
        wasteInformationParam.setId("4bed3174ddec4640a728b464f6271bd6");
        wasteInformationParam.setTitle("资讯课堂2");
        wasteInformationParam.setContext("资讯课堂--内容2");

        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235872864520192");


        try {
            boolean result = wasteInformationService.updateWasteInformation(wasteInformationParam, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateCheckAmount() {
        WasteInformationParam wasteInformationParam = new WasteInformationParam();
        wasteInformationParam.setId("4bed3174ddec4640a728b464f6271bd6");
        try {
            wasteInformationService.updateClickAmount(wasteInformationParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getWasteInformationById() {
        WasteInformationParam wasteInformationParam = new WasteInformationParam();
        wasteInformationParam.setId("cec7d1440e0542ec99284a3e88c2ad3a");
        try {
            WasteInformationModel wasteInformationModel = wasteInformationService.getWasteInformationById(wasteInformationParam);
            Assert.assertTrue(wasteInformationModel != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}