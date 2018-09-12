/**
 * 
 */
package com.mlsc.waste.user.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.waste.user.model.UserExtended;

import java.util.List;
import java.util.Map;

/**
 * @author zhugl
 *
 */
public interface UserExtendedDao extends EntityDao<UserExtended> {

    /**
     *根据userId获取扩展表信息
     * @param userExtended
     */
    UserExtended getUserExtendedInfoByUserId(UserExtended userExtended) throws DaoAccessException;
    
    /**
     * 用户扩展表的更新
     * @param userId
     * @throws DaoAccessException
     */
    void updateWastecycleInit(String userId) throws DaoAccessException;

	List<UserApproveVo> listUserApproVo(String whereSql,Map<String,Object> paramMap);

	UserExtended getAdminUserByEnterpriseId(String enterpriseId);

    UserExtended getAdminUserByEnterpriseId(String enterpriseId, String userId);

    List<UserExtended> listUserExtendedByEnterId(String enterId);
}
