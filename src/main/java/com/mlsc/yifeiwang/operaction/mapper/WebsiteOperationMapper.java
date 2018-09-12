package com.mlsc.yifeiwang.operaction.mapper;

import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOperation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.operaction.model.WebsiteOperationModel;
import com.mlsc.yifeiwang.operaction.model.WebsiteOperationParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
public interface WebsiteOperationMapper extends BaseMapper<WebsiteOperation> {

    int countNoAnnualPlan(WebsiteOperationParam websiteOperationParam);

    List<SysEnterpriseBase> listNoAnnualPlan(WebsiteOperationParam websiteOperationParam);

    int countHasAnnualPlan(WebsiteOperationParam websiteOperationParam);

    List<SysEnterpriseBase> listHasAnnualPlan(WebsiteOperationParam websiteOperationParam);


    List<SysEnterpriseBase> initOutSouringWasteEnterprise(@Param("entName") String entName);

    List<SysEnterpriseBase> listOutSouringWasteEnterprise(WebsiteOperationParam websiteOperationParam);

    List<SysEnterpriseBase> listNoPlanWasteEnterprise(WebsiteOperationParam websiteOperationParam);

    List<SysEnterpriseBase> listNoTransferWasteEnterprise(WebsiteOperationParam websiteOperationParam);

    List<WebsiteOperationModel> listWebSiteOperationInfo(PagingParameter pagingParameter);

    WebsiteOperationModel getWebSiteOperationInfo(@Param("id") String id);

    int countOutSouringWasteEnterprise(WebsiteOperationParam websiteOperationParam);

    int countNoPlanWasteEnterprise(WebsiteOperationParam websiteOperationParam);

    int countNoTransferWasteEnterprise(WebsiteOperationParam websiteOperationParam);

    int countWebSiteOperationInfo();


    List<WebsiteOperation> listStartWebSiteOperationInfo();

    List<WebsiteOperation> listStopWebSiteOperationInfo();


    List<EntWasteModel> listEntWasteByEntId(@Param("entId") String entId);
}