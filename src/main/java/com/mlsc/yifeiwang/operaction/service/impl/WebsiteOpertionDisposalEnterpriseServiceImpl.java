package com.mlsc.yifeiwang.operaction.service.impl;

import cn.jiguang.common.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOpertionDisposalEnterprise;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.operaction.mapper.WebsiteOpertionDisposalEnterpriseMapper;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOpertionDisposalEnterpriseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
@Service
public class WebsiteOpertionDisposalEnterpriseServiceImpl extends ServiceImpl<WebsiteOpertionDisposalEnterpriseMapper, WebsiteOpertionDisposalEnterprise> implements IWebsiteOpertionDisposalEnterpriseService {
    private final static Logger logger = LoggerFactory.getLogger(WebsiteOpertionDisposalEnterpriseServiceImpl.class);

    @Override
    public List<WebsiteOpertionDisposalEnterprise> listDisposalEnterpriseByEntName(String entName) throws Exception {
        List<WebsiteOpertionDisposalEnterprise> enterpriseList;
        try {
            String websiteOperationId = Util.uuid32();
            enterpriseList = this.baseMapper.listDisposalEnterprise(entName);
            if (enterpriseList != null) {
                for (WebsiteOpertionDisposalEnterprise disposalEnterprise : enterpriseList) {
                    disposalEnterprise.setOperationId(websiteOperationId);
                }
            }
        } catch (Exception e) {
            logger.error("获取处置企业时异常", e);
            throw e;
        }
        return enterpriseList;
    }

    @Override
    public String saveOrUpdateDisposalEnterpriseList(List<WebsiteOpertionDisposalEnterprise> disposalEnterpriseList) throws Exception {
        try {
            if (disposalEnterpriseList != null && disposalEnterpriseList.size() > 0) {
                String operationId = disposalEnterpriseList.get(0).getOperationId();
                EntityWrapper<WebsiteOpertionDisposalEnterprise> ew = new EntityWrapper<WebsiteOpertionDisposalEnterprise>();
                ew.setSqlSelect("id");
                ew.eq("operationId", operationId);
                List<WebsiteOpertionDisposalEnterprise> list = selectList(ew);
                if (list != null && list.size() > 0) {
                    ew = new EntityWrapper<WebsiteOpertionDisposalEnterprise>();
                    ew.eq("operationId", operationId);
                    delete(ew);
                }
                this.insertBatch(disposalEnterpriseList);
                return disposalEnterpriseList.get(0).getOperationId();
            }

        } catch (Exception e) {
            logger.error("保存运营处置企业时异常", e);
            throw e;
        }
        return null;

    }

    @Override
    public List<WebsiteOpertionDisposalEnterprise> listDisposalEnterprise(WebsiteOpertionDisposalEnterprise disposalEnterprise) throws Exception {
        EntityWrapper<WebsiteOpertionDisposalEnterprise> ew = new EntityWrapper<WebsiteOpertionDisposalEnterprise>();
        ew.setSqlSelect("entName,licenceId");
        if (StringUtils.isNotEmpty(disposalEnterprise.getOperationId())) {
            ew.eq("operationId", disposalEnterprise.getOperationId());
        }
        return selectList(ew);
    }

}
