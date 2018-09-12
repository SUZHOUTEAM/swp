package yifeiwang;

import com.baomidou.mybatisplus.plugins.Page;
import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.customersuggestion.entity.CustomerSuggestion;
import com.mlsc.yifeiwang.customersuggestion.service.ICustomerSuggestionService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by user on 2017/11/16.
 */
public class CustomerSuggestionServiceTest extends BaseTest {
    @Autowired
    private ICustomerSuggestionService customerSuggestionService;

    @Test
    public void saveCustomerSuggestion(){
        CustomerSuggestion customerSuggestion = new CustomerSuggestion();
        customerSuggestion.setCompany("123");
        customerSuggestion.setSuggestion("123123123123123");
        customerSuggestion.setPhoneNo("181000002");
        try{
            customerSuggestionService.saveCustomerSuggestion(customerSuggestion);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void listCustomerSuggestion(){

        Page<CustomerSuggestion> page = new Page<>(0,20);
        try{
            page = customerSuggestionService.listCustomerSuggestion(page);
            Assert.assertTrue(page.getSize()==1);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}

