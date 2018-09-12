package com.mlsc.yifeiwang.operaction.controller;


import com.mlsc.yifeiwang.operaction.entity.WebsiteOpertionDisposalEnterprise;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOpertionDisposalEnterpriseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
@Controller
@RequestMapping("/disposalEnterprise")
public class WebsiteOpertionDisposalEnterpriseController {
    @Autowired
    private IWebsiteOpertionDisposalEnterpriseService disposalEnterpriseService;
    private final static Logger logger = LoggerFactory.getLogger(WebsiteOpertionDisposalEnterpriseController.class);

    /**
     * 处置企业列表
     *
     * @param entName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listDisposalEnterprise", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<WebsiteOpertionDisposalEnterprise>> listDisposalEnterprise(String entName) {
        Result<List<WebsiteOpertionDisposalEnterprise>> result = new Result<List<WebsiteOpertionDisposalEnterprise>>();
        try {
            List<WebsiteOpertionDisposalEnterprise> disposalEnterpriseList = disposalEnterpriseService.listDisposalEnterpriseByEntName(entName);
            result.setData(disposalEnterpriseList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("获取处置企业时异常", e);
        }
        return result;
    }


    /**
     * 保存处置企业
     *
     * @param disposalEnterpriseList
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateDisposalEnterpriseList", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<String> saveOrUpdateDisposalEnterpriseList(@RequestBody List<WebsiteOpertionDisposalEnterprise> disposalEnterpriseList) {
        Result<String> result = new Result<String>();
        try {
            String operationId = disposalEnterpriseService.saveOrUpdateDisposalEnterpriseList(disposalEnterpriseList);
            if (StringUtils.isNotBlank(operationId)) {
                result.setData(operationId);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("保存运营处置企业时异常", e);
        }
        return result;
    }

}
