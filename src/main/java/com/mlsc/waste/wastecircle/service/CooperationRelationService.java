package com.mlsc.waste.wastecircle.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.wastecircle.model.CooperationRelation;
import com.mlsc.waste.wastecircle.model.FollowEnterpriseVo;

import java.util.List;


public interface CooperationRelationService {
    
    /**
     * 易废圈关系信息表的单条记录保存
     * 
     * @author zhugl date 2016-08-23
     */
    void saveCooperationRelation(CooperationRelation cooperationRelation, String ticketId);
    
    /**
     * 易废圈关系信息表的单条记录物理删除
     * 
     * @author zhugl date 2016-08-23
     */
    void removeCooperationRelation(String cooperationRelationId);
    
    /**取消关注
     * 
     * @author zhugl date 2016-08-23
     */
    void removeFollowByFollowId(String userId,String followId,String followType);
    
    /**
     * 易废圈关系信息表的批量保存
     * 
     * @author zhugl date 2016-08-23
     */
    int savesCooperationRelation(List<CooperationRelation> cooperationRelations, String ticketId);
    
    
    
    /**查询我关注的企业列表
     * 
     * @author zhugl date 2016-08-23
     */
    List<FollowEnterpriseVo> getFollowEnterprises(String userId, PagingParameter pagingParameter);
    
    /**查询我关注的企业的件数
     * 
     * @author zhugl date 2016-08-23
     */
    int getFollowEnterprisesCount(String userId);
    
    /**判断有没有和某个企业或者个人建立关注关系
     * 
     * @author zhugl date 2016-08-25
     */
    boolean isFollowed(String userId,String followId,String followType);
    
    /**
     * 查询关注我的用户信息(关注类型是企业)
     * 
     * @author zhugl date 2016-09-26
     */
    List<RPCSysUser> getUserInfoByfollowId(String followId);

    List<User> listUserByFollowIdAndFollowTypeCode(String followId,String followTypeCode) throws DaoAccessException;
}
