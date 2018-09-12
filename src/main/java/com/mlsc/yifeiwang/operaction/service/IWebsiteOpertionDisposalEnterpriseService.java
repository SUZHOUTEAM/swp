package com.mlsc.yifeiwang.operaction.service;

import com.mlsc.yifeiwang.operaction.entity.WebsiteOpertionDisposalEnterprise;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
public interface IWebsiteOpertionDisposalEnterpriseService extends IService<WebsiteOpertionDisposalEnterprise> {
    List<WebsiteOpertionDisposalEnterprise> listDisposalEnterpriseByEntName(String entName) throws Exception;

    String saveOrUpdateDisposalEnterpriseList(List<WebsiteOpertionDisposalEnterprise> disposalEnterpriseList) throws Exception;

    List<WebsiteOpertionDisposalEnterprise> listDisposalEnterprise(WebsiteOpertionDisposalEnterprise disposalEnterprise) throws Exception;

}
