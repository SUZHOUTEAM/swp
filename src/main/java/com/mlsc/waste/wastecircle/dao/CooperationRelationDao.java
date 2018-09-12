package com.mlsc.waste.wastecircle.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.wastecircle.model.CooperationRelation;
import com.mlsc.waste.wastecircle.model.FollowEnterpriseVo;

import java.util.List;

public interface CooperationRelationDao extends EntityDao<CooperationRelation> {
    /**
     * 查询我关注的企业列表
     * @param userId
     * @param pagingParameter
     * @return
     * @throws DaoAccessException
     */
    List<FollowEnterpriseVo> getFollowEnterprises(String userId ,PagingParameter pagingParameter) throws DaoAccessException;
    
    /**
     * 查询我关注的企业的件数
     * @param userId
     * @return
     * @throws DaoAccessException
     */
    int getFollowEnterprisesCount(String userId) throws DaoAccessException;
    
    /**
     * 取消关注
     * @param userId
     * @param followId
     * @param followType
     * @throws DaoAccessException
     */
    void removeFollowByFollowId(String userId,String followId,String followType) throws DaoAccessException;
    
    /**
     * 判断有没有和某个企业或者个人建立关注关系
     * @param userId
     * @param followId
     * @param followType
     * @return
     */
    int isFollowed(String userId,String followId,String followType);
    
    /**
     * 查询关注我的用户信息(关注类型是企业)
     * @param followId
     * @return
     */
    List<RPCSysUser> getUserInfoByfollowId(String followId);
    
}
