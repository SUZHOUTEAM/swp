package com.mlsc.yifeiwang.enterprise.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.enterprise.entity.EntEvaluate;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.service.IEntEvaluateService;
import com.mlsc.yifeiwang.enterprise.mapper.EntEvaluateMapper;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2017-10-17
 */
@Service
public class EntEvaluateServiceImpl extends ServiceImpl<EntEvaluateMapper, EntEvaluate> implements IEntEvaluateService {
    private final static Logger logger = LoggerFactory.getLogger(EntEvaluateServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<EntEvaluate> addEntEvaluate(User user, EntEvaluate entEvaluate) throws Exception {
        List<EntEvaluate> evaluateList = null;
        try {
            insertEntEvaluate(user, entEvaluate);
            EntEvaluate queryCondition = new EntEvaluate();
            queryCondition.setOrderId(entEvaluate.getOrderId());
            evaluateList = listEntEvaluate(queryCondition);
        } catch (Exception e) {
            logger.error("添加评价时异常", e);
            throw e;
        }
        return evaluateList;
    }

    private void insertEntEvaluate(User user, EntEvaluate entEvaluate) {
        Date date = new Date();
        entEvaluate.setEvaluatedBy(user.getEnterpriseId());
        entEvaluate.setCreateBy(user.getUserId());
        entEvaluate.setCreateTime(date);
        entEvaluate.setEditBy(user.getUserId());
        entEvaluate.setEditTime(date);
        this.baseMapper.insert(entEvaluate);
    }

    @Override
    public boolean validateEntEvaluate(EntEvaluate entEvaluate, List<String> errorList) {
        boolean validate = true;
        if (entEvaluate == null) {
            validate = false;
            errorList.add("评价信息不能为空");
        } else {
            if (StringUtils.isBlank(entEvaluate.getComment())) {
                validate = false;
                errorList.add("评价信息内容不能为空");
            }
            if (StringUtils.isBlank(entEvaluate.getOrderId())) {
                validate = false;
                errorList.add("评价订单不能为空");
            }
            if (entEvaluate.getScore() == null && entEvaluate.getScore() == 0) {
                validate = false;
                errorList.add("评价等级不能为空");
            }
        }
        return validate;
    }

    @Override
    public List<EntEvaluate> listEntEvaluate(EntEvaluate entEvaluate) throws Exception {
        List<EntEvaluate> evaluateList = null;
        try {
            evaluateList = this.baseMapper.listEntEvaluate(entEvaluate);
        } catch (Exception e) {
            logger.error("查看评价时异常", e);
            throw e;
        }
        return evaluateList;
    }

    @Override
    public Boolean CheckEntEvaluate(User user, EntEvaluate entEvaluate) throws Exception {
        boolean isEvalusted = false;
        try {
            List<EntEvaluate> evaluateList = this.baseMapper.listEntEvaluate(entEvaluate);
            if (evaluateList != null && evaluateList.size() < 0) {
                isEvalusted = true;
            }
        } catch (Exception e) {
            logger.error("添加评价时异常", e);
            throw e;
        }
        return isEvalusted;
    }

    @Override
    public List<EntEvaluate> listEntEvaluateByEntId(EntEvaluate entEvaluate) throws Exception {
        List<EntEvaluate> evaluateList = null;
        try {
            evaluateList = this.baseMapper.listEntEvaluate(entEvaluate);
        } catch (Exception e) {
            logger.error("查看企业评价时异常", e);
            throw e;
        }
        return evaluateList;
    }


}
