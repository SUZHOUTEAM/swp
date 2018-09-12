
/**
 * 
 */
package com.mlsc.waste.user.service;

import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.waste.user.model.UserExtended;

import java.util.List;

/**
 * @author zhugl
 *
 */
public interface UserExtendedService {

    /**
     * 用户/企业关系审核记录的保存
     * 
     * @author zhugl date 2016-08-03
     */
    void saveUserExtended(UserExtended record, String ticketId) throws DaoAccessException;
    
    /**
     * 用户/企业关系审核记录的更新
     * 
     * @author zhugl date 2016-08-03
     */
    void updateUserExtended(UserExtended record);
    
    /**
     * 通过ID查询扩展表（vaild=0的数据也可以查出来）
     * 
     * @author zhugl date 2016-08-03
     */
    UserExtended getUserExtendedById(String id);
    
    /**
     * 通过平台企业表的ID查询
     * 
     * @author zhugl date 2016-08-03
     */
    UserExtended getUserExtendedByUserId(String userId);
    
    /**
     * 用户/企业关系审核记录的更新
     * 
     * @author sunjl date 2016-08-09
     */
    void updateUserExtended(String userId);
    
    /**
     * 保存或更新用户设备号
     * @param userId
     * @param registrationCode
     * @param ticketId
     */
    void saveOrUpdateUserResgistration(String userId,String registrationCode ,String ticketId ) throws Exception;
    
    List<UserApproveVo> listUserApproVo(String enterId,String statusCode);

	UserExtended getAdminUserByEnterpriseId(String enterpriseId);

	UserExtended getUserAdminByEnterId(String enterId);

    UserExtended getUserAdminByEnterId(String enterId,String userId);


    List<UserExtended>  listUserExtendedByEnterId(String enterId);
}
