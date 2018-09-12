package com.mlsc.yifeiwang.controller;


import cn.jiguang.common.utils.StringUtils;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.model.SysEnterpriseModel;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 企业基本信息 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2017-10-20
 */
@Controller
@RequestMapping("/sysEnterpriseBase")
public class SysEnterpriseBaseController {
    @Autowired
    private ISysEnterpriseBaseService sysEnterpriseBaseService;

    /**
     * 用户登录时-确认企业信息是否完整
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkEntInfoCompleted")
    public Result<SysEnterpriseModel> checkEntInfoCompleted(User user) {
        Result<SysEnterpriseModel> result = new Result<SysEnterpriseModel>();
        try {
            SysEnterpriseModel enterpriseModel = sysEnterpriseBaseService.checkEntInfoCompleted(user);
            result.setData(enterpriseModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 更新企业概况
     *
     * @param user
     * @param enterpriseBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateEnterpriseSummary")
    public Result<Boolean> updateEnterpriseSummary(User user, @RequestBody SysEnterpriseBase enterpriseBase) {
        Result<Boolean> result = new Result<Boolean>();
        List<String> infoList = new ArrayList<String>();
        try {
            if (StringUtils.isNotEmpty(enterpriseBase.getSummary())) {
                boolean updated = sysEnterpriseBaseService.updateEnterprise(user, enterpriseBase);
                result.setData(updated);
                result.setStatus(ResultStatus.Success);
            } else {
                infoList.add("企业概述为空");
                result.setData(false);
                result.setStatus(ResultStatus.Error);
                result.setInfoList(infoList);
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 更新企业销售明细
     *
     * @param user
     * @param enterpriseBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateEnterpriseSalesNote")
    public Result<Boolean> updateEnterpriseSalesNote(User user, @RequestBody SysEnterpriseBase enterpriseBase) {
        Result<Boolean> result = new Result<Boolean>();
        List<String> infoList = new ArrayList<String>();
        try {
            if (StringUtils.isNotEmpty(enterpriseBase.getSalesNote()) || StringUtils.isNotEmpty(enterpriseBase.getPaymentRule())) {
                boolean updated = sysEnterpriseBaseService.updateEnterprise(user, enterpriseBase);
                result.setData(updated);
                result.setStatus(ResultStatus.Success);
            } else {
                infoList.add("企业销售说明或付款规则为空");
                result.setData(false);
                result.setStatus(ResultStatus.Error);
                result.setInfoList(infoList);
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 获取企业概况
     *
     * @param entId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getEnterpriseSummaryInfo")
    public Result<SysEnterpriseModel> getEnterpriseSummaryInfo(String entId) {
        Result<SysEnterpriseModel> result = new Result<SysEnterpriseModel>();
        try {
            SysEnterpriseBase enterpriseBase = new SysEnterpriseBase();
            enterpriseBase.setEntId(entId);
            SysEnterpriseModel sysEnterpriseModel = sysEnterpriseBaseService.getEnterpriseSummaryInfo(enterpriseBase);
            result.setData(sysEnterpriseModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


}
