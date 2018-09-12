package com.mlsc.waste.myenterprise.service;

import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.userenterpriseapproverecord.model.AuditUserRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecordVo;

import java.util.List;

/**
 * @author zhugl
 */
public interface MyEnterpriseService {

    /**
     * 通过ticketId查询当前用户是否绑定了企业
     *
     * @return true:已经绑定过了，false没有绑定企业
     * @throws Exception
     * @author zhugl date 2017-02-06
     */
    boolean isBindedEnterprise(String ticketId) throws Exception;

    /**
     * 获取当前用户的申请记录
     *
     * @throws Exception
     * @author zhugl date 2017-02-06
     */
    List<UserEnterpriseApproveRecordVo> getUserEnterpriseApproveRecordVos(String ticketId) throws Exception;

    /**
     * 用户选择了一个企业加入后，等待审核
     *
     * @author zhugl date 2016-07-22
     */
    void joinEnterprise(String ticketId, String invitationCode, String enterpriseId)
            throws Exception;

    /**
     * 根据 事件类型 和 时间状态 查询records
     *
     * @param enterpriseId
     * @return
     */
    List<AuditUserRecordVo> getUserEnterpriseApproveRecords(String userEventType, String userEventStatus, String enterpriseId);

    /**
     * 根据企业ID拿到 平台企业表的基本信息
     *
     * @param enterpriseId
     * @param ticketId
     * @return
     */
    RPCSysEnterpriseBaseVo getSysEnterpriseBasesByEntId(String ticketId, String enterpriseId);

    /**
     * 查询指定企业里面所有的的人员信息
     *
     * @param enterpriseId
     * @return
     */
    List<RPCSysUser> getUserInfoByEntId(String enterpriseId);


    /**
     * 获取企业的图像
     *
     * @param enterpriseId
     * @return
     */
    String getEnterImgSrc(String enterpriseId);

    void updateUserRoleAndStatus(String ticketId, UserExtended param, String entId) throws Exception;


    void removeEnterpriseUserRelation(String ticketId, UserExtended extended, String entId) throws Exception;


    void checkTickedId(String ticketId, String clientMessage, User user) throws Exception;

    List<RPCSysUser> listEnterpriseUser(String enterpriseId, String facilitatorEntId);
}
