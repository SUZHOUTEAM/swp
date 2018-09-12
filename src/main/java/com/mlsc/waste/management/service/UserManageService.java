package com.mlsc.waste.management.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.datatable.FilterConditions;

import java.util.Map;

/**
 * Created by zhanghj on 2017/7/4.
 */
public interface UserManageService {

   ResultData<User> pageResultData(FilterConditions filterConditions,int start,int length,String userStatus)
           throws DaoAccessException;

   void updateUsers(String userIds,String userStatus,String role,String ticketId) throws Exception;
}
