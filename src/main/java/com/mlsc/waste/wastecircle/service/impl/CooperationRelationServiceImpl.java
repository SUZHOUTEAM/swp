package com.mlsc.waste.wastecircle.service.impl;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.service.WasteEnterpriseTypeService;
import com.mlsc.waste.user.dao.UserDao;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.dao.CooperationRelationDao;
import com.mlsc.waste.wastecircle.model.CooperationRelation;
import com.mlsc.waste.wastecircle.model.FollowEnterpriseVo;
import com.mlsc.waste.wastecircle.service.CooperationRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("CooperationRelationService")
public class CooperationRelationServiceImpl implements CooperationRelationService {
    private final static Logger logger = LoggerFactory.getLogger(CooperationRelationServiceImpl.class);

    @Autowired
    private CooperationRelationDao cooperationRelationDao;
    
    @Autowired
    private WasteEnterpriseTypeService entTypeService;
    
    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private UserDao userDao;
    

    @Override
    public void saveCooperationRelation(
            CooperationRelation cooperationRelation, String ticketId) {
        try {
            // 判断该关注关系有没有建立
            boolean result = isFollowed(cooperationRelation.getUserId(),
                    cooperationRelation.getFollowId(),
                    cooperationRelation.getFollowType());
            
            if (!result) {
                User user = LoginStatusUtils.getUserByTicketId(ticketId);
                cooperationRelation.setValid(Constant.IS_VALID);
                cooperationRelation.setCreateBy(user.getUserId());
                cooperationRelation.setCreateTime(Util.datetimeToString(new Date()));
                cooperationRelation.setEditBy(user.getUserId());
                cooperationRelation.setEditTime(Util.datetimeToString(new Date()));
                cooperationRelationDao.save(cooperationRelation, Util.uuid32());
            }
        } catch (Exception e) {
            logger.error("易废圈关系信息表单条记录保存时异常",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCooperationRelation(String cooperationRelationId) {
        try {
            cooperationRelationDao.delete(cooperationRelationId);
        } catch (Exception e) {
            logger.error("删除易废圈关系信息表单条记录时异常",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int savesCooperationRelation(
            List<CooperationRelation> cooperationRelations, String ticketId) {
        if (cooperationRelations == null) {
            return 0;
        }
        int index = 0;
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            String[] idlist = new String[cooperationRelations.size()];
            CooperationRelation cooperationRelation = null;
            for (Iterator<CooperationRelation> it = cooperationRelations.iterator(); it.hasNext();) {
                cooperationRelation = it.next();
                // 判断该关注关系有没有建立
                boolean result = isFollowed(cooperationRelation.getUserId(),
                        cooperationRelation.getFollowId(),
                        cooperationRelation.getFollowType());
                if (!result) {
                    cooperationRelation.setValid(Constant.IS_VALID);
                    cooperationRelation.setEditBy(user.getUserId());
                    cooperationRelation.setEditTime(Util.datetimeToString(new Date()));
                    cooperationRelation.setCreateBy(user.getUserId());
                    cooperationRelation.setCreateTime(Util.datetimeToString(new Date()));
                    idlist[index++] = Util.uuid32();
                } else {
                    it.remove();
                }
            }
            if (cooperationRelations.size() > 0) {
                cooperationRelationDao.saves(cooperationRelations, idlist);
            }
        } catch (Exception e) {
            logger.error("批量保存易废圈关系信息表记录时异常",e);
            throw new RuntimeException(e);
        }
        return index;
        
    }

    @Override
    public List<FollowEnterpriseVo> getFollowEnterprises(String userId,PagingParameter pagingParameter) {
        List<FollowEnterpriseVo> resultList = null;
        try {
            resultList = cooperationRelationDao.getFollowEnterprises(userId, pagingParameter);
            if (resultList != null) {
                for (FollowEnterpriseVo followEntVo:resultList) {
                    //企业logo
                    followEntVo.setEntLogo("");
                    //企业类型列表
                    followEntVo.setEntType(codeValueService.getEnterpriseTypesByEntId(followEntVo.getFollowId()));
                }
            }
        } catch (Exception e) {
            logger.error("查询我关注的企业列表时异常",e);
            throw new RuntimeException(e);
        }
        
        return resultList;
    }
    
    @Override
    public int getFollowEnterprisesCount(String userId) {
        try {
            return cooperationRelationDao.getFollowEnterprisesCount(userId);
        } catch (Exception e) {
            logger.error("查询我关注的企业数据件数时异常",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeFollowByFollowId(String userId, String followId,
            String followType) {
        try {
            cooperationRelationDao.removeFollowByFollowId(userId, followId, followType);
        } catch (Exception e) {
            logger.error("取消关注企业时异常",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isFollowed(String userId, String followId, String followType) {
        boolean result = false;
        try {
            int count = cooperationRelationDao.isFollowed(userId, followId, followType);
            result = (count != 0);
        } catch (Exception e) {
            logger.error("判断有没有和某个企业或者个人建立关注关系时异常",e);
            throw new RuntimeException(e);
        }
        
        return result;
    }

    @Override
    public List<RPCSysUser> getUserInfoByfollowId(String followId) {
        List<RPCSysUser> list = null;
        try {
            list = cooperationRelationDao.getUserInfoByfollowId(followId);
        } catch (Exception e) {
            logger.error("查询关注我的用户信息(关注类型是企业)时异常",e);
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<User> listUserByFollowIdAndFollowTypeCode(String followId,String followTypeCode) throws
            DaoAccessException {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("followId",followId);
        paramMap.put("followTypeCode",followTypeCode);
        return userDao.listUser(null,paramMap);
    }
}
