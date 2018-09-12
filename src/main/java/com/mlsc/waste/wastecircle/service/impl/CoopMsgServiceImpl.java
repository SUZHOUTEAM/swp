package com.mlsc.waste.wastecircle.service.impl;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.dao.EnterpriseDao;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.wastecircle.dao.CoopMsgDao;
import com.mlsc.waste.wastecircle.model.CoopMsg;
import com.mlsc.waste.wastecircle.model.CoopMsgVo;
import com.mlsc.waste.wastecircle.service.CoopMsgService;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("coopMsgService")
public class CoopMsgServiceImpl implements CoopMsgService {

    private final static Logger logger = LoggerFactory.getLogger(CoopMsgServiceImpl.class);

    @Autowired
    private CoopMsgDao coopMsgDao;

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;

    @Override
    public int getCoopMsgCountByEnterId(CoopMsg coopMsg) throws DaoAccessException {
        return coopMsgDao.getCoopMsgCountByEnterId(coopMsg);
    }


    @Override
    public int getCoopMsgCountForProductionUser(PagingParameter pagingParameter, RPCSysEnterpriseBaseVo enterpriseBaseVo,
                                                String entType, User user, String searchCondition, String cantonCode) throws Exception {
        int count = 0;
        try {
            count = coopMsgDao.getCoopMsgCountForProductionUser(pagingParameter, enterpriseBaseVo, entType, user, searchCondition, cantonCode);
        } catch (Exception e) {
            logger.error("根据条件查询消息个数时出错", e);
            throw e;
        }
        return count;
    }


    @Override
    public List<CoopMsgVo> getCoopMsgsForProductionUser(PagingParameter pagingParameter, String ticketId,
                                                        String entType, User user, String searchCondition, String cantonCode, String sortByDistance) throws Exception {
        RPCSysEnterpriseBaseVo enterpriseBaseVo = null;
        List<CoopMsgVo> msgList = null;
        try {
            enterpriseBaseVo = enterpriseBaseService.getCoordinateByEntId(user.getEnterpriseId());
            msgList = coopMsgDao.getCoopMsgsForProductionUser(pagingParameter, enterpriseBaseVo, entType, user, searchCondition, cantonCode, sortByDistance);
            if (pagingParameter != null) {
                pagingParameter.setTotalRecord(getCoopMsgCountForProductionUser(pagingParameter, enterpriseBaseVo, entType, user, searchCondition, cantonCode));
            }
        } catch (Exception e) {
            logger.error("根据条件查询消息时出错", e);
            throw e;
        }
        return msgList;
    }


}
