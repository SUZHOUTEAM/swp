package com.mlsc.mobileservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.sms.service.ISysNoticeService;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityReleaseService;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.fw.service.SysCantonService;
import com.mlsc.waste.licence.service.LicenceDetailService;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.myenterprise.service.MyEnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserService;
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
@RequestMapping("/personaluserservice")
@Scope("prototype")
public class PersonalUserServiceController {

    @Autowired
    private MyEnterpriseService myEnterpriseService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private LicenceService licenceService;
    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private DispositionCapacityReleaseService dispositionCapacityReleaseService;

    @Autowired
    private LicenceDetailService licenceDetailService;


    @Autowired
    private ISysNoticeService noticeService;

    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    private SysCantonService sysCantonService;

    @Autowired
    private UserExtendedService userExtendedService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/personalInformation")
    public Result<Map<String, Object>> login(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String enterpriseId = request.getParameter("enterpriseId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        RPCSysEnterpriseBaseVo sysEnterpriseBaseVo = myEnterpriseService.getSysEnterpriseBasesByEntId(ticketId, enterpriseId);
        User sysUser = userService.getUserByUserId(user.getUserId());
        dataMap.put("userInformation", JSONArray.toJSON(sysUser));
        dataMap.put("enterpriseInformation", JSONArray.toJSON(sysEnterpriseBaseVo));
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }


}
