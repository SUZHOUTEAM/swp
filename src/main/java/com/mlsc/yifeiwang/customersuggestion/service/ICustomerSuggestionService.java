package com.mlsc.yifeiwang.customersuggestion.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.mlsc.yifeiwang.customersuggestion.entity.CustomerSuggestion;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-16
 */
public interface ICustomerSuggestionService extends IService<CustomerSuggestion> {

    boolean saveCustomerSuggestion(CustomerSuggestion customerSuggestion) throws Exception;

    boolean validateCustomerSuggestion(CustomerSuggestion customerSuggestion, List<String> errorList);

    Page<CustomerSuggestion> listCustomerSuggestion(Page<CustomerSuggestion> customerSuggestionn) throws Exception;
}
