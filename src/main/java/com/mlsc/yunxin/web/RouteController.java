package com.mlsc.yunxin.web;

import com.alibaba.fastjson.JSONObject;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.task.MointorYuXinUserStatus;
import com.mlsc.task.TaskUtils;
import com.mlsc.yunxin.entity.EnableYunXinAccount;
import com.mlsc.yunxin.service.IOnlinestatusService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



/**
 * Created by yinxl on 2017/7/3.
 */
@Controller("routeController")
@RequestMapping(value = "/yunxin", method = {RequestMethod.GET, RequestMethod.POST})
@Scope("prototype")
public class RouteController {
    public static final Logger logger = LoggerFactory.getLogger("fw.controller");
    @Autowired
    private IRPCServiceClient client;

    @Autowired
    private IOnlinestatusService onlinestatusServiceImpl;


    @RequestMapping(value = "monitorStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject mockClient(HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        try {
            // 获取请求体
            byte[] body = readBody(request);
            if (body == null) {
                logger.warn("request wrong, empty body!");
                result.put("code", 414);
                return result;
            }
            String requestBody = new String(body, "utf-8");

            MointorYuXinUserStatus notifyAllUserTask = TaskUtils.getTask(MointorYuXinUserStatus.class);
            notifyAllUserTask.setRequestBody(requestBody);
            TaskUtils.executeTask(notifyAllUserTask);
            logger.info("request body = " + requestBody);
            result.put("code", 200);
            return result;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            result.put("code", 414);
            return result;
        }
    }



    private byte[] readBody(HttpServletRequest request) throws IOException {
        if (request.getContentLength() > 0) {
            byte[] body = new byte[request.getContentLength()];
            IOUtils.readFully(request.getInputStream(), body);
            return body;
        } else
            return null;
    }


    @RequestMapping(value = "getEnableYunXinAccount", method = RequestMethod.POST)
    @ResponseBody
    public Result<EnableYunXinAccount> getEnableYunXinAccount(HttpServletRequest request) throws Exception {
        Result<EnableYunXinAccount> result = new Result<EnableYunXinAccount>();
        try {
            EnableYunXinAccount enableYunXinAccount = onlinestatusServiceImpl.getEnableYunXinAccount();
            result.setData(enableYunXinAccount);
            result.setStatus(ResultStatus.Success);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
}
