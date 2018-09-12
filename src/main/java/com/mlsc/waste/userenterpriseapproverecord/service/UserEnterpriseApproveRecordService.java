/**
 * 
 */
package com.mlsc.waste.userenterpriseapproverecord.service;

import com.mlsc.waste.userenterpriseapproverecord.model.AuditUserRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecord;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecordVo;

import java.util.List;

/**
 * @author zhugl
 *
 */
public interface UserEnterpriseApproveRecordService {

    /**
     * 用户/企业关系审核记录的保存
     * 
     * @author zhugl date 2016-08-03
     */
    String saveUserEnterpriseApproveRecord(UserEnterpriseApproveRecord record, String ticketId);
    
    /**
     * 用户/企业关系审核记录的更新
     * 
     * @author zhugl date 2016-08-03
     */
    void updateUserEnterpriseApproveRecord(UserEnterpriseApproveRecord record, String ticketId);
    
    /**
     * 通过ID查询用户/企业关系审核记录（vaild=0的数据也可以查出来）
     * 
     * @author zhugl date 2016-08-03
     */
    UserEnterpriseApproveRecord getUserEnterpriseApproveRecordById(String recordId);
    
    /**
     * 用户/企业关系审核记录列表
     * @param userId 用户ID
     * @param eventType 事件类型Code null时不作为条件
     * @param eventStatus : 事件状态Code  null时不作为条件
     * @return
     */
    List<UserEnterpriseApproveRecordVo> getUserEnterpriseApproveRecordVos(String userId,String entId,String[] eventType,String[] eventStatus);

    /**
     * 根据 时间类型 和 事件状态查询数据
     * @param userEventType
     * @param userEventStatus
     * @param enterpriseId
     * @return
     */
    List<AuditUserRecordVo> getUserEnterpriseApproveRecords(
            String userEventType, String userEventStatus, String enterpriseId);

    /**
     * 平台管理员审核企业退回或者通过
     * @param enterpriseId
     * @param id
     */
    void updateRecordByEnpId(String enterpriseId, String userEventStatus);
}
