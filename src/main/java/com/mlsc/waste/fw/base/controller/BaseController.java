package com.mlsc.waste.fw.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.exception.ParamterException;
import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.sms.model.MessageBean;
import com.mlsc.yifeiwang.sms.model.ParamterMessageBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局的异常处理Controller
 * 
 * @author zhugl
 */
@Controller
public class BaseController {
    private Logger logger = Logger.getLogger(BaseController.class);
    /** 基于@ExceptionHandler异常处理 */
    @ExceptionHandler  
    public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
    	String httpStatus = null;
        if (request.getHeader("accept").indexOf("application/json") > -1 || 
           (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {// ajax请求
        	try {
                PrintWriter writer = response.getWriter();
                Result<Map<String, Object>> result = new Result<Map<String, Object>>();
                Map<String, Object> dataMap = new HashMap<String, Object>();
                result.setStatus(ResultStatus.Failure);
                if(ex instanceof WasteBusinessException) {
                	List<MessageBean> messageBeansList = getBusinessMessageBeans(ex);
                	dataMap.put("msgInfo", messageBeansList);
                } else if(ex instanceof ParamterException) {
                    doHandlingParamterMessageBeans(ex);
                    logger.error("系统错误，请联系系统管理员协助解决！\n", ex);
                    dataMap.put("msgInfo", "系统错误，请联系系统管理员协助解决！\n");
                } else {
                    logger.error("系统错误，请联系系统管理员协助解决！\n", ex);
                    dataMap.put("msgInfo", "系统错误，请联系系统管理员协助解决！\n");
                }
                result.setData(dataMap);
                writer.write(JSONObject.toJSONString(result));
                writer.flush();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else {//非ajax请求时
        	request.setAttribute("exception", ex);
        	httpStatus = "500";
        }
        
        return httpStatus;
    }
    
    private String getExceptionDetail(Exception e) {
        StringBuffer msg = new StringBuffer("");  
        if (e != null) {
            String message = e.toString();
            int length = e.getStackTrace().length;  
            if (length > 0) {  
                msg.append(message + "\n");  
                for (int i = 0; i < length; i++) {  
                    msg.append("\t" + e.getStackTrace()[i] + "\n");  
                }  
            } else {  
                msg.append(message);  
            }  
        }  
        return msg.toString();  
    }
    
    private List<MessageBean> getBusinessMessageBeans(Exception ex) {
        List<MessageBean> messageBeansList = null;
        if (((WasteBusinessException) ex).getMessageBeans() == null) {
            messageBeansList = new ArrayList<MessageBean>();
        } else {
            messageBeansList = ((WasteBusinessException) ex).getMessageBeans();
        }
        
        if (((WasteBusinessException) ex).getMessageBean() != null) {
            messageBeansList.add(((WasteBusinessException) ex).getMessageBean());
        } 
        return messageBeansList;  
    }
    
    private List<ParamterMessageBean> doHandlingParamterMessageBeans(Exception ex) {
        List<ParamterMessageBean> messageBeansList = null;
        if (((WasteBusinessException) ex).getMessageBeans() == null) {
            messageBeansList = new ArrayList<ParamterMessageBean>();
        } else {
            messageBeansList = ((ParamterException) ex).getMessageBeans();
        }
        
        if (((ParamterException) ex).getMessageBean() != null) {
            messageBeansList.add(((ParamterException) ex).getMessageBean());
        }
        StringBuffer loginfoSb = null;
        if (messageBeansList != null && messageBeansList.size() > 0) {
            loginfoSb = new StringBuffer();
            for (ParamterMessageBean msgBean : messageBeansList) {
                loginfoSb.append("参数名称："+ msgBean.getParamterName() + ",报错信息："+ msgBean.getErrorContent()  + ";");
            }
            logger.error(loginfoSb.toString(), ex);
        }
        return messageBeansList;  
    }
}
