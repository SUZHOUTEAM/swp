package com.mlsc.yifeiwang.enterprise.service.impl;

import com.mlsc.yifeiwang.enterprise.entity.EntCustomer;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.mapper.EntCustomerMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.enterprise.service.IEntCustomerService;
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
 * @since 2017-10-20
 */
@Service
public class EntCustomerServiceImpl extends ServiceImpl<EntCustomerMapper, EntCustomer> implements IEntCustomerService {
    private final static Logger logger = LoggerFactory.getLogger(EntCustomerServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveEntCustomer(User user, EntCustomer entCustomer) throws Exception {
        try {
            if (entCustomer != null) {
                Date date = new Date();
                entCustomer.setEntId(user.getEnterpriseId());
                entCustomer.setCreateBy(user.getUserId());
                entCustomer.setCreateTime(date);
                entCustomer.setEditBy(user.getUserId());
                entCustomer.setEditTime(date);
                this.baseMapper.insert(entCustomer);
            }
        } catch (Exception e) {
            logger.error("保存企业客户时异常", e);
            throw e;
        }
        return true;
    }

    @Override
    public boolean validateEntCustomer(EntCustomer entCustomer, List<String> errorList) {
        boolean validate = true;
        if (entCustomer == null) {
            validate = false;
            errorList.add("客户信息为空");
        }
        if (StringUtils.isBlank(entCustomer.getCustomerName())) {
            validate = false;
            errorList.add("客户信息为空");
        }
        if (StringUtils.isBlank(entCustomer.getEntId())) {
            validate = false;
            errorList.add("客户所属企业编号为空");
        }
        return validate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateEntCustomer(User user, EntCustomer entCustomer) throws Exception {
        try {
            if (entCustomer != null && StringUtils.isNotBlank(entCustomer.getId())) {
                Date date = new Date();
                EntCustomer item = this.baseMapper.selectById(entCustomer.getId());
                if (StringUtils.isNotBlank(entCustomer.getCustomerName())) {
                    item.setCustomerName(entCustomer.getCustomerName());
                }
                if (StringUtils.isNotBlank(entCustomer.getFileId())) {
                    item.setFileId(entCustomer.getFileId());
                }
                item.setEditBy(user.getUserId());
                item.setEditTime(date);
                return this.updateById(item);
            }

        } catch (Exception e) {
            logger.error("更新企业客户时异常", e);
            throw e;
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteEntCustomer(EntCustomer entCustomer) throws Exception {
        try {
            if (entCustomer != null && StringUtils.isNotBlank(entCustomer.getId())) {
                return this.deleteById(entCustomer);
            }
        } catch (Exception e) {
            logger.error("删除企业客户时异常", e);
            throw e;
        }
        return true;
    }


    @Override
    public List<EntCustomer> listEntCustomersByEntId(User user) throws Exception {
        List<EntCustomer> entCustomers = null;
        try {
            if (StringUtils.isNotBlank(user.getEnterpriseId())) {
                entCustomers = this.baseMapper.listEntCustomersByEntId(user.getEnterpriseId());
            }
        } catch (Exception e) {
            logger.error("获取企业用户时异常", e);
            throw e;
        }
        return entCustomers;
    }

    @Override
    public EntCustomer getEntCustomersById(String id) throws Exception {
        EntCustomer entCustomer = null;
        try {
            if (StringUtils.isNotBlank(id)) {
                entCustomer = this.selectById(id);
            }
        } catch (Exception e) {
            logger.error("根据id查看企业用户时异常", e);
            throw e;
        }
        return entCustomer;
    }
}
