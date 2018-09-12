package com.mlsc.yifeiwang.appmanagement.controller;

import com.mlsc.yifeiwang.appmanagement.entity.AppConfig;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.appmanagement.service.IAppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/app")
@Scope("prototype")
public class AppConfigController {
    @Autowired
    private IAppConfigService appConfigService;

    /**
     * 获取app_config
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAPPConfig")
    public Result<AppConfig> getCodeValuesTypeCode(HttpServletRequest request) throws Exception {
        Result<AppConfig> result = new Result<>();
        try {
            AppConfig appConfig = appConfigService.getLastUpdateConfig();
            String prefixUrl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();
            if (appConfig.getLoadType() == 2) {
                appConfig.setJsPath(prefixUrl + appConfig.getJsPath());
            }
            result.setData(appConfig);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        return result;
    }
}
