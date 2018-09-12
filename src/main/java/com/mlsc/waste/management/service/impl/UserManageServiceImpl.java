package com.mlsc.waste.management.service.impl;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SmsService;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.waste.management.dao.SysUserEnterpriseRelationDao;
import com.mlsc.waste.management.service.UserManageService;
import com.mlsc.waste.user.dao.UserDao;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.UserRole;
import com.mlsc.waste.utils.UserStatus;
import com.mlsc.waste.utils.datatable.DataTablesUtils;
import com.mlsc.waste.utils.datatable.FilterConditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghj on 2017/7/4.
 */
@Service
public class UserManageServiceImpl implements UserManageService{

    @Autowired
    private  UserDao userDao;

    @Autowired
    private SysUserEnterpriseRelationDao sysUserEnterpriseRelationDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SysMsgServcie sysMsgServcie;


    @Override
    public ResultData<User> pageResultData(FilterConditions filterConditions,int start,int length,String userStatus)
            throws DaoAccessException {
        PagingParameter paging = DataTablesUtils.generatePagingParameter(start, length);
        String whereSql = filterConditions.getWhere();
        Map<String, Object> paramMap = filterConditions.getParamMap();
        if (paramMap == null) {
            paramMap = new HashMap<>();
        }
        if (StringUtils.isEmpty(whereSql) || !whereSql.contains("userStatus")) {
            whereSql += " AND userStatus = :userStatus ";
            paramMap.put("userStatus", userStatus);
        }
        return userDao.pageUser(whereSql,paramMap,paging);
    }



    @Override
    public void updateUsers(String userIds, String userStatus,String role,String ticketId) throws Exception {
        if(StringUtils.isNotEmpty(userIds)){
            String[] uids = userIds.split(",");

            List<String> uidList =  Arrays.asList(uids);
            MsgEvent msgEvent = null;
            userDao.updateUserExtendStatusByUserIdList(uidList,userStatus,role);
            if(UserStatus.REJECT.getStatusCode().equals(userStatus)){
                sysUserEnterpriseRelationDao.deleteByUserIdList(uidList);
                msgEvent = new MsgEvent(SmsAction.USER_JOIN_ENT_REFUSE);
            }else{
                if(UserRole.ADMIN.getRoleCode().equals(role)){
                    msgEvent = new MsgEvent(SmsAction.USER_JOIN_ENT_PASS_ADMIN);
                }else {
                    msgEvent = new MsgEvent(SmsAction.USER_JOIN_ENT_PASS_REGULAR);
                }
            }

            List<User> userList = userService.getUserListByUserId(ticketId, uidList);
            User currentUser = LoginStatusUtils.getUserByTicketId(ticketId);
            msgEvent.setReceiveUserList(userList);
            msgEvent.setSendUser(currentUser);
            sysMsgServcie.sendMessageAsync(msgEvent);

        }
    }



}
