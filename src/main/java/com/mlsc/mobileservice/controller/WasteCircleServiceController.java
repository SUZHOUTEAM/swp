package com.mlsc.mobileservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityReleaseService;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fw.service.SysCantonService;
import com.mlsc.waste.licence.service.LicenceDetailService;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.wastecircle.model.MessageBodyVo;
import com.mlsc.waste.wastecircle.model.SearchCondition;
import com.mlsc.waste.wastecircle.service.WasteCircleService;
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
@RequestMapping("/wastecircleserivice")
@Scope("prototype")
public class WasteCircleServiceController {

    @Autowired
    private WasteCircleService wasteCircleService;

    @Autowired
    private LicenceService licenceService;

    @Autowired
    private LicenceDetailService licenceDetailService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private DispositionCapacityReleaseService capacityReleaseService;


    @Autowired
    private SysCantonService sysCantonService;

    @ResponseBody
    @RequestMapping("/initwastecircle")
    public Result<Map<String, Object>> initWasteCircle(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");

        SearchCondition searchCondition = JSONArray.parseObject(request.getParameter("searchCondition"), SearchCondition.class);
        PagingParameter pagingParameter = new PagingParameter();
        if (StringUtils.isNotBlank(pageIndex)) {
            pagingParameter.setPageIndex(Integer.valueOf(pageIndex));
        }
        if (StringUtils.isNotBlank(pageSize)) {
            pagingParameter.setPageSize(Integer.valueOf(pageSize));
        }

        List<MessageBodyVo> messageList = null;
        try {
            messageList = wasteCircleService.getMessageListReleaseList(searchCondition, ticketId, user, pagingParameter);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }

        dataMap.put("messageList", JSONArray.toJSON(messageList));
        dataMap.put("paging", pagingParameter);
        dataMap.put("searchCondition", searchCondition);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

}
