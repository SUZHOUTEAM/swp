package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.common.util.RSAEncryptUtils;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.facilitator.service.IFacilitatorCustomerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by user on 2018/1/17.
 */
public class FacilitatorCustomerServiceTest extends BaseTest {
    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private IFacilitatorCustomerService facilitatorCustomerService;

    private String getTicketId1() {
        String ticketId = null;
        try {
            ticketId = userLoginService.userLogin("18051116827",  RSAEncryptUtils.encrypt("test123"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketId;
    }

    @Test
    public void saveEnterprise() {
        String ticketId = getTicketId1();
        try {
            RPCSysEnterpriseBase enterpriseBase = new RPCSysEnterpriseBase();
            enterpriseBase.setEntCode("YFWENTCODE"+Util.uuid32());
            enterpriseBase.setEntAddress("产废企业地址");
            enterpriseBase.setContacts("张三");
            enterpriseBase.setContactsTel("1386889587441");
            enterpriseBase.setEntName("网点产废");
            enterpriseBase.setPosx("19.3");
            enterpriseBase.setPosy("26.3");
            enterpriseBase.setCantonCode("q123");
            facilitatorCustomerService.saveProductionEnt(null,ticketId,enterpriseBase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
