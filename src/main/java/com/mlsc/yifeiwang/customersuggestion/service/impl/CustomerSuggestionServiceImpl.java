package com.mlsc.yifeiwang.customersuggestion.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mlsc.yifeiwang.customersuggestion.entity.CustomerSuggestion;
import com.mlsc.waste.utils.Constant;
import com.mlsc.yifeiwang.customersuggestion.mapper.CustomerSuggestionMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.customersuggestion.service.ICustomerSuggestionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-16
 */

@Service
public class CustomerSuggestionServiceImpl extends ServiceImpl<CustomerSuggestionMapper, CustomerSuggestion> implements ICustomerSuggestionService {
    private final static Logger logger = LoggerFactory.getLogger(CustomerSuggestionServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveCustomerSuggestion(CustomerSuggestion customerSuggestion) throws Exception {
        try {
            Date date = new Date();
            customerSuggestion.setCreateBy(Constant.SYSTEM);
            customerSuggestion.setCreateTime(date);
            customerSuggestion.setEditBy(Constant.SYSTEM);
            customerSuggestion.setEditTime(date);
            this.baseMapper.insert(customerSuggestion);
        } catch (Exception e) {
            logger.error("新增用户建议时异常", e);
            throw e;
        }
        return true;
    }

    @Override
    public boolean validateCustomerSuggestion(CustomerSuggestion customerSuggestion, List<String> errorList) {
        boolean validate = true;
        if (customerSuggestion == null) {
            validate = false;
            errorList.add("用户建议信息不能为空");
        } else {
            if (StringUtils.isBlank(customerSuggestion.getSuggestion())) {
                validate = false;
                errorList.add("用户建议内容不能为空");
            }
            if (StringUtils.isBlank(customerSuggestion.getPhoneNo())) {
                validate = false;
                errorList.add("用户联系电话不能为空");
            }
        }

        return validate;

    }

    @Override
    public Page<CustomerSuggestion> listCustomerSuggestion(Page<CustomerSuggestion> customerSuggestion) throws Exception {
        EntityWrapper<CustomerSuggestion> ew = new EntityWrapper<>();
        ew.setSqlSelect("id, suggestion,phoneNo,company,createBy,createTime ");
        ew.eq("deleteFlag", 0);
        ew.orderBy(" createTime desc ");
        return selectPage(customerSuggestion, ew);
    }
}
