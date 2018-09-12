package com.mlsc.waste.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.cache.CacheUtil;
import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.common.util.StringUtils;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.user.service.UserRegisterService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.NetworkUtil;
import com.mlsc.waste.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用户注册Controller
 * 
 * @author zhugl
 */
@Controller
@RequestMapping("/userRegister")
@Scope("prototype")
public class UserRegisterController extends BaseController{
    private final static Logger logger = LoggerFactory.getLogger(UserRegisterController.class);

    @Autowired
    private UserRegisterService userRegisterService;


    /**
     * 手机号码是否被注册验证
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping("/isPhoneNumExist")
    public Result<Map<String, Object>> isPhoneNumExist( HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String phoneNum = request.getParameter("phoneNum");
        boolean isRegistered = userRegisterService.isPhoneNumExist(phoneNum);
        dataMap.put("isRegistered", isRegistered);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }
    
    /**
     * 向手机发送验证码短信
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping("/getSmsIdentifyCode")
    public Result<String> getIdentifyCode( HttpServletRequest request){
        Result<String> result = new Result<String>();
        result.setStatus(ResultStatus.Success);

        // 手机号码
        String phoneNum = request.getParameter("phoneNum");
        String smsType = request.getParameter("smsType");
        String clientIp = request.getParameter("smsOrigin");
        try {
            if(StringUtils.isNotNullOrEmpty(phoneNum) && StringUtils.isNotNullOrEmpty(clientIp) ){
                Object codeObj = CacheUtil.get(phoneNum);
                if(Objects.isNull(codeObj)){
                    userRegisterService.getIdentifyCode(phoneNum,smsType);
                    result.setData("发送成功");
                }else{
                    logger.info("web-getIdentifyCode,phoneNum: {},clientIp: {}",phoneNum, clientIp);
                    result.setData("发送频率间隔时间应大于120秒以上");
                }
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
            result.setData(e.getMessage());
        }
        return result;
    }
    
    /**
     * 短信验证码验证
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping("/checkIdentifyCode")
    public Result<String> checkIdentifyCode( HttpServletRequest request) throws Exception {
        Result<String> result = new Result<>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 手机号码
        String phoneNum = request.getParameter("phoneNum");
        // 短信验证码
        String identifyCode = request.getParameter("identifyCode");
        // 短信验证码正确性验证
        try {
            userRegisterService.checkIdentifyCode(phoneNum, identifyCode);
            result.setStatus(ResultStatus.Success);
        }catch (Exception e) {
            logger.error("手机短信验证码校验出错",e);
            result.getInfoList().add("手机短信验证码校验出错");
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
    
    /**
     * 用户注册时，用户信息保存
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/userRegisterSave")
    public Result<Map<String, String>> userRegisterSave( HttpServletRequest request) throws Exception {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        RPCSysUser sysUser = initialUserInfo(request);
        String clientMessage = NetworkUtil.getIpAddress(request);
        String openId = request.getParameter("openId");
        // 用户注册时，用户信息保存
        JSONObject retJson = null;
        try {
            if(PropertyUtil.getValue("ssoOpen").equals("1")){
                retJson = userRegisterService.userRegisterSaveForClient(sysUser,clientMessage,openId);
            }else{
                retJson = userRegisterService.userRegisterSave(sysUser,openId);
            }
            String ticketId = retJson.getString("ticketId");
            LoginStatusUtils.putUserToCacheByUserPhoneNum(sysUser.getPhoneNum(),ticketId);
            dataMap.put("ticketId", ticketId);
            dataMap.put("imToken", retJson.getString("imToken"));
            result.setStatus(ResultStatus.Success);
            result.setData(dataMap);
        } catch (Exception e) {
            logger.error("用户注册出错",e);
            result.getInfoList().add(Constant.SYS_MSG);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
    
    /**
     * 对需要保存的用户信息进行封装
     * 
     * @param request
     * @return
     */ 
    private RPCSysUser initialUserInfo (HttpServletRequest request) {
        RPCSysUser sysUser = new RPCSysUser();
        // 登录名
        sysUser.setLoginName(request.getParameter("phoneNum"));
        // 手机号码
        sysUser.setPhoneNum(request.getParameter("phoneNum"));
        // 姓名
        sysUser.setChineseName(request.getParameter("userName"));
        // 密码
        sysUser.setPassword(request.getParameter("password"));
        
        return sysUser;
    }
    
}
