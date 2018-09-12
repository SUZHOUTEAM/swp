package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.operaction.model.WebsiteOperationModel;
import com.mlsc.yifeiwang.operaction.model.WebsiteOperationParam;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOperationContacts;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationContactsService;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/11/29.
 */
public class WebsiteOperationServiceTest extends BaseTest {
    @Autowired
    private IWebsiteOperationService operationService;
    @Autowired
    private IWebsiteOperationContactsService contactsService;

    @Test
    public void updateEnterpriseInfo() {
        try {
            WebsiteOperationModel operationModel = operationService.getWebSiteOperationInfo("22fb5c163db04e8c89cb8f7c6bf501e2");
            Assert.assertTrue(operationModel != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listWebsiteOperationContacts() {
        try {
            WebsiteOperationContacts operationContacts = new WebsiteOperationContacts();
            operationContacts.setOperationId("390c27bbec6748bb88b8d79dc1539845");
            List<WebsiteOperationContacts> list = contactsService.listWebsiteOperationContacts(operationContacts);
            Assert.assertTrue(list.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void listNoPlanWasteEnterprise() {
        try {
            WebsiteOperationContacts operationContacts = new WebsiteOperationContacts();
            List<String> cantonCodes = new ArrayList<String>();
            cantonCodes.add("32");
            List<String> wasteTypes = new ArrayList<String>();
            wasteTypes.add("06");
            WebsiteOperationParam websiteOperationParam = new WebsiteOperationParam();
            websiteOperationParam.setCantonCodes(cantonCodes);
            websiteOperationParam.setWasteTypes(wasteTypes);
            List<SysEnterpriseBase> list = operationService.listNoPlanWasteEnterprise(websiteOperationParam);
            Assert.assertTrue(list.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
