package com.mlsc.yifeiwang.im.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.im.service.ImService;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
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
@RequestMapping("/imService")
@Scope("prototype")
public class ImServiceController extends BaseController{
    
    @Autowired
    private ImService imService;
    
    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    private UserService userService;
    
    @ResponseBody
    @RequestMapping("/queryChatLog")
    public Result<Map<String, Object>> initDispositionPlan(HttpServletRequest request) throws Exception{
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String fromId = user.getPhoneNo();
        String toId = request.getParameter("toId");
        JSONArray chatLog = imService.queryChatLog(fromId, toId);
        
        List<Uploadfile> fromUserLog = uploadfileService.getFileByFileTypeAndReferenceId("c",userService.getUserInfoByPhoneNum(ticketId,fromId).getUserId());
        List<Uploadfile> toUserLog = uploadfileService.getFileByFileTypeAndReferenceId("c",userService.getUserInfoByPhoneNum(ticketId,toId).getUserId());
        dataMap.put("chatLog", chatLog);
        dataMap.put("fromUserLog", JSONArray.toJSON(fromUserLog));
        dataMap.put("toUserLog", JSONArray.toJSON(toUserLog));
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/getIMAppKey")
    public Result<Map<String, Object>> getIMAppKey(HttpServletRequest request) throws Exception{
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("appKey",  Util.getIMAppkey());
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

}
