package com.mlsc.yifeiwang.enterprise.mapper;

import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.yifeiwang.enterprise.model.SysEnterpriseModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 企业基本信息 Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-10-20
 */
public interface SysEnterpriseBaseMapper extends BaseMapper<SysEnterpriseBase> {

    int checkEntInfoCompleted(@Param("entId") String enterpriseId);

    void updateEnterpriseInfo(SysEnterpriseBase enterpriseBase);

    SysEnterpriseBase getEnterpriseInfo(SysEnterpriseBase entBase);

    SysEnterpriseModel getEnterpriseSummaryInfo(@Param("entId") String entId, @Param("licenceId") String licenceId);

    RPCSysEnterpriseBaseVo queryEnterpriseByEntId(@Param("entId") String enterpriseId);

    List<RPCSysEnterpriseBaseVo> getEntDropDownList(@Param("entName") String entName);

    RPCSysEnterpriseBaseVo getCoordinateByEntId(@Param("entId") String enterpriseId);

    int checkResponsibleArea(@Param("responsibleArea") String responsibleArea);

    List<SysEnterpriseBase> listEnterpriseInfo();

    void updateEnterprisePosx(SysEnterpriseBase sysEnterpriseBase);

}