package com.mlsc.yifeiwang.useraction.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.useraction.entity.UserAction;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.utils.StringUtils;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import com.mlsc.yifeiwang.useraction.mapper.UserActionMapper;
import com.mlsc.yifeiwang.useraction.model.ActionTypeModel;
import com.mlsc.yifeiwang.useraction.model.UserActionModel;
import com.mlsc.yifeiwang.useraction.model.UserActionParam;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.useraction.service.IUserActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2018-01-03
 */
@Service
public class UserActionServiceImpl extends ServiceImpl<UserActionMapper, UserAction> implements IUserActionService {
    private final static Logger logger = LoggerFactory.getLogger(UserActionServiceImpl.class);
    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveUserAction(String ticketId, User user, UserAction userAction) throws Exception {
        try {
            Date date = new Date();
            if (user != null) {
                userAction.setUserId(user.getUserId());
                userAction.setTicketId(ticketId);
                userAction.setEntId(user.getEnterpriseId());
            }
            userAction.setCreateTime(date);
            return this.insert(userAction);
        } catch (Exception e) {
            logger.error("保存用户信息异常", e);
            throw e;
        }

    }

    @Override
    public List<UserActionModel> listUserAction(UserActionParam userActionParam, PagingParameter pagingParameter) throws Exception {
        List<UserActionModel> userActionModels = null;
        try {
            Util.initPagingParameter(pagingParameter);
            userActionParam.setStartRowIndex(pagingParameter.getStart());
            userActionParam.setRows(pagingParameter.getLimit());

            int count = this.baseMapper.countUserAction(userActionParam);
            if (count > 0) {
                userActionModels = this.baseMapper.listUserAction(userActionParam);
                pagingParameter.setTotalRecord(count);
                Iterator<UserActionModel> it = userActionModels.iterator();
                while (it.hasNext()) {
                    UserActionModel userActionModel = it.next();
                    if (StringUtils.isNotNullOrEmpty(userActionModel.getTicketId()) && StringUtils.isNullOrEmpty(userActionModel.getEntName())) {
                        userActionModel.setEntName(getEntNameByTicketId(userActionModel.getTicketId()));
                    }
                    if (StringUtils.isNullOrEmpty(userActionModel.getTicketId())) {
                        userActionParam.setCip(userActionModel.getCip());
                        userActionParam.setActionTicketId("");
                    } else {
                        userActionParam.setActionTicketId(userActionModel.getTicketId());
                        userActionParam.setCip("");
                    }
                    List<ActionTypeModel> actionTypeModels = listActionType(userActionParam);
                    userActionModel.setActionTypeModels(actionTypeModels);
                }
            }

        } catch (Exception e) {
            logger.error("获取用户登录信息时异常", e);
            throw e;
        }
        return userActionModels;
    }


    private String getEntNameByTicketId(String ticketId) throws Exception {
        EntityWrapper<UserAction> ew = new EntityWrapper<>();
        ew.setSqlSelect("entId");
        ew.eq("ticketId", ticketId);
        ew.isNotNull("entId");
        UserAction userAction = this.selectOne(ew);

        if (userAction != null && StringUtils.isNotNullOrEmpty(userAction.getEntId())) {
            SysEnterpriseBase enterpriseBase = enterpriseBaseService.getEnterpriseInfoById(userAction.getEntId());
            if (enterpriseBase != null && StringUtils.isNotNullOrEmpty(enterpriseBase.getEntName())) {
                return enterpriseBase.getEntName();
            }
        }
        return null;
    }


    @Override
    public List<ActionTypeModel> listActionType(UserActionParam userActionParam) throws Exception {
        List<ActionTypeModel> actionTypeModels = null;
        try {
            actionTypeModels = this.baseMapper.listActionType(userActionParam);
        } catch (Exception e) {
            logger.error("获取用户行为时异常", e);
            throw e;
        }
        return actionTypeModels;
    }

    @Override
    public List<UserActionModel> listUserActionDetailByIp(UserActionParam userActionParam, PagingParameter pagingParameter) throws Exception {
        List<UserActionModel> userActionModels = null;
        try {
            Util.initPagingParameter(pagingParameter);
            userActionParam.setStartRowIndex(pagingParameter.getStart());
            userActionParam.setRows(pagingParameter.getLimit());

            int count = this.baseMapper.countUserActionDetailByIp(userActionParam);
            if (count > 0) {
                userActionModels = this.baseMapper.listUserActionDetailByIp(userActionParam);
                pagingParameter.setTotalRecord(count);
            }

        } catch (Exception e) {
            logger.error("根据ip获取用户操作详情异常", e);
            throw e;
        }
        return userActionModels;
    }

    @Override
    public List<UserActionModel> listUserActionDetailByTicketId(UserActionParam userActionParam, PagingParameter pagingParameter) throws Exception {
        List<UserActionModel> userActionModels = null;
        try {
            Util.initPagingParameter(pagingParameter);
            userActionParam.setStartRowIndex(pagingParameter.getStart());
            userActionParam.setRows(pagingParameter.getLimit());

            int count = this.baseMapper.countUserActionDetailByTicketId(userActionParam);
            if (count > 0) {
                userActionModels = this.baseMapper.listUserActionDetailByTicketId(userActionParam);
                pagingParameter.setTotalRecord(count);
            }

        } catch (Exception e) {
            logger.error("根据TicketId获取用户操作详情异常", e);
            throw e;
        }
        return userActionModels;
    }
}
