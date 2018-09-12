package com.mlsc.waste.wastecircle.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.wastecircle.model.CoopMsg;
import com.mlsc.waste.wastecircle.model.CoopMsgVo;
import com.mlsc.waste.wastecircle.model.SearchCondition;

import java.util.List;

public interface CoopMsgService {


    int getCoopMsgCountByEnterId(CoopMsg coopMsg) throws DaoAccessException;


    int getCoopMsgCountForProductionUser(PagingParameter pagingParameter, RPCSysEnterpriseBaseVo enterpriseBaseVo,
                                         String entType, User user, String searchCondition, String cantonCode) throws Exception;


    List<CoopMsgVo> getCoopMsgsForProductionUser(PagingParameter pagingParameter, String ticketId, String entType, User user,
                                                 String searchCondition, String cantonCode, String sortByDistance) throws Exception;


}
