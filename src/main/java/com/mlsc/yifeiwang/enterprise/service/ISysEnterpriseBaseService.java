package com.mlsc.yifeiwang.enterprise.service;

import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.model.SysEnterpriseModel;

import java.util.List;

/**
 * <p>
 * 企业基本信息 服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-10-20
 */
public interface ISysEnterpriseBaseService extends IService<SysEnterpriseBase> {

    SysEnterpriseModel checkEntInfoCompleted(User user) throws Exception;

    boolean updateEnterprise(User user, SysEnterpriseBase enterpriseBase) throws Exception;

    SysEnterpriseBase getEnterpriseInfoById(String entId) throws Exception;

    SysEnterpriseBase getEnterpriseInfo(SysEnterpriseBase enterpriseBase) throws Exception;

    SysEnterpriseModel getEnterpriseSummaryInfo(SysEnterpriseBase enterpriseBase)throws Exception;

    RPCSysEnterpriseBaseVo getEnterpriseByEntId(String enterpriseId);

    List<RPCSysEnterpriseBaseVo> listEntDropDown(String entName);

    RPCSysEnterpriseBaseVo getCoordinateByEntId(String entId);

    boolean checkResponsibleArea(String responsibleArea);

    boolean isEnterpriseVaild(String ticketId) throws Exception;

    SysEnterpriseBase getDefaultDispositionEnt() throws Exception;
}
