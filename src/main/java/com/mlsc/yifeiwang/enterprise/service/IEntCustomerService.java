package com.mlsc.yifeiwang.enterprise.service;

import com.mlsc.yifeiwang.enterprise.entity.EntCustomer;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.waste.user.model.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-10-20
 */
public interface IEntCustomerService extends IService<EntCustomer> {

    boolean saveEntCustomer(User user, EntCustomer entCustomer) throws Exception;

    boolean validateEntCustomer(EntCustomer entCustomer, List<String> errorList);

    boolean updateEntCustomer(User user, EntCustomer entCustomer) throws Exception;

    boolean deleteEntCustomer(EntCustomer entCustomer) throws Exception;

    List<EntCustomer> listEntCustomersByEntId(User user) throws Exception;

    EntCustomer getEntCustomersById(String id) throws Exception;
}
