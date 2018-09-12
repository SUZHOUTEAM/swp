/**
 * 
 */
package com.mlsc.waste.userenterpriseapproverecord.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.userenterpriseapproverecord.model.AuditUserRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecord;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecordVo;

import java.util.List;

/**
 * @author zhugl
 *
 */
public interface UserEnterpriseApproveRecordDao extends EntityDao<UserEnterpriseApproveRecord> {
    
    /**
     * 用户/企业关系审核记录列表
     * @param userId 用户ID
     * @param eventType 事件类型 null时不作为条件
     * @param eventStatus : 事件状态 null时不作为条件
     * @return
     */
    List<UserEnterpriseApproveRecordVo> getUserEnterpriseApproveRecordVos(String userId,String entId,String[] eventType,String[] eventStatus);

    /**
     * 
     * @param userEventType 事件类型
     * @param userEventStatus 事件状态
     * @param enterpriseId 企业ID
     * @return
     * @throws DaoAccessException 
     */
    List<AuditUserRecordVo> getUserEnterpriseApproveRecords(
            String userEventType, String userEventStatus, String enterpriseId) throws DaoAccessException;

    /**
     * 平台管理员审核新加入企业，审核通过或者退回更新数据
     * @param enterpriseId
     * @param userEventStatus
     */
    void updateRecordByEnpId(String enterpriseId, String userEventStatus);
}
