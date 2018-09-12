package com.mlsc.yifeiwang.enterprise.service.impl;

import com.mlsc.yifeiwang.enterprise.entity.EntGlory;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.service.IEntGloryService;
import com.mlsc.yifeiwang.enterprise.mapper.EntGloryMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class EntGloryServiceImpl extends ServiceImpl<EntGloryMapper, EntGlory> implements IEntGloryService {
    private final static Logger logger = LoggerFactory.getLogger(EntGloryServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveEntGlory(User user, EntGlory entGlory) throws Exception {
        try {
            if (entGlory != null) {
                Date date = new Date();
                entGlory.setEntId(user.getEnterpriseId());
                entGlory.setGloryType(entGlory.getGloryType());
                entGlory.setCreateBy(user.getUserId());
                entGlory.setCreateTime(date);
                entGlory.setEditBy(user.getUserId());
                entGlory.setEditTime(date);
                this.baseMapper.insert(entGlory);
            }
        } catch (Exception e) {
            logger.error("保存企业荣誉时异常", e);
            throw e;
        }
        return true;
    }

    @Override
    public boolean validateEntGlory(EntGlory entGlory, List<String> errorList) {
        boolean validate = true;
        if (entGlory == null) {
            validate = false;
            errorList.add("荣誉信息不能为空");
        } else {
            if (StringUtils.isBlank(entGlory.getGloryType())) {
                validate = false;
                errorList.add("荣誉类型不能为空");
            }
        }
        return validate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateEntGlory(User user, EntGlory entGlory) throws Exception {
        try {
            if (entGlory != null && StringUtils.isNotBlank(entGlory.getId())) {
                Date date = new Date();
                entGlory.setEditBy(user.getUserId());
                entGlory.setEditTime(date);
                this.updateById(entGlory);
            }
        } catch (Exception e) {
            logger.error("更新企业荣誉时异常", e);
            throw e;
        }
        return true;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteEntGlory(EntGlory entGlory) throws Exception {
        try {
            if (entGlory != null) {
                Date date = new Date();
                this.baseMapper.deleteById(entGlory);
            }
        } catch (Exception e) {
            logger.error("删除企业荣誉时异常", e);
            throw e;
        }
        return true;
    }


    @Override
    public List<EntGlory> listEntGloryByEntId(User user) throws Exception {
        List<EntGlory> entGlories = null;
        try {
            if (StringUtils.isNotBlank(user.getEnterpriseId())) {
                entGlories = this.baseMapper.listEntGloryByEntId(user.getEnterpriseId());
            }
        } catch (Exception e) {
            logger.error("获取企业荣誉时异常", e);
            throw e;
        }
        return entGlories;
    }

    @Override
    public EntGlory getEntGloryById(String id) throws Exception {
        EntGlory entGlory = null;
        try {
            if (StringUtils.isNotBlank(id)) {
                entGlory = this.selectById(id);
            }
        } catch (Exception e) {
            logger.error("根据id查看企业荣誉时异常", e);
            throw e;
        }
        return entGlory;
    }
}
