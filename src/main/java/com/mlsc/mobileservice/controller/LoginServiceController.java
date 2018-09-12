package com.mlsc.mobileservice.controller;

import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.waste.wastecircle.service.WasteCircleService;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.NetworkUtil;
import com.mlsc.waste.utils.PropertyUtil;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loginservice")
@Scope("prototype")
public class LoginServiceController extends BaseController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserExtendedService userExtendedService;

    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;

    @Autowired
    private WasteCircleService wasteCircleService;

    @ResponseBody
    @RequestMapping("/login")
    public Result<Map<String, Object>> login(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String phoneNum = request.getParameter("phoneNum");
        String password = request.getParameter("password");
        try {
            String ticketId = "";
            if (PropertyUtil.getValue("ssoOpen").equals("1")) {
                String clientMessage = NetworkUtil.getIpAddress(request);
                ticketId = userLoginService.userLoginForClient(phoneNum, password, clientMessage);
            } else {
                ticketId = userLoginService.userLogin(phoneNum, password);
            }
            dataMap.put("ticketId", ticketId);
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            if (user != null && StringUtils.isNotEmpty(user.getEnterpriseId())) {
                List<CodeValue> enterpriseTypes = codeValueService.getEnterpriseTypesByEntId(user.getEnterpriseId());
                dataMap.put("enterTypes", enterpriseTypes);
                EnterpriseInfo enterpriseInfo = wasteCircleService.getEnterpriseInfoByUserId(ticketId, user);
                dataMap.put("enterpriseInfo", enterpriseInfo);
            }
            dataMap.put("userid", user.getUserId());
            dataMap.put("imaccid", phoneNum);
            dataMap.put("imtocken", user.getImToken());
            dataMap.put("user", user);
            result.setStatus(ResultStatus.Success);
            result.setData(dataMap);
        } catch (WasteBusinessException be) {
            result.getInfoList().add(be.getMessageBean().getErrorContent());
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            result.getInfoList().add(Constant.SYS_MSG);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/userRegistration")
    public Result<Map<String, Object>> userRegistration(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String registrationCode = request.getParameter("registrationCode");
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        userExtendedService.saveOrUpdateUserResgistration(user.getUserId(), registrationCode, ticketId);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/getEnterpriseById")
    public Result<Map<String, Object>> getEnterpriseById(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String enterpriseId = request.getParameter("enterpriseId");
        String ticketId = request.getParameter("ticketId");
        RPCSysEnterpriseBaseVo enterpriseBaseVo = enterpriseBaseService.getEnterpriseByEntId(enterpriseId);
        dataMap.put("enterpriseInfo", enterpriseBaseVo);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }
}
