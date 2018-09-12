package com.mlsc.mobileservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.cache.CacheUtil;
import com.mlsc.common.exception.BusinessException;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.myenterprise.service.MyEnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserRegisterService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.*;
import com.mlsc.waste.wastecircle.model.EnterpriseVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/userservice")
@Scope("prototype")
public class UserServiceController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private MyEnterpriseService myEnterpriseService;

    @Autowired
    private UserExtendedService userExtendedService;
    
    @Autowired
    private UserService userService;

    /**
     * 获取短信验证码
     */
    @ResponseBody
    @RequestMapping("/getIdentifyCode")
    public Result<Map<String, Object>> getIdentifyCode(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        String phoneNum = request.getParameter("phoneNum");
        try {
            // 判断手机号码是否已经存在
            boolean isRegistered = userRegisterService.isPhoneNumExist(phoneNum);
            if (isRegistered) {
                map.put("msg", "手机号码已经存在！");
                result.setData(map);
                result.setStatus(ResultStatus.Failure);
                return result;
            }
            Object codeObj = CacheUtil.get(phoneNum);
            if(Objects.isNull(codeObj)){
                // 获取验证码,0是新注册，1是密码重置。用之前的接口
                userRegisterService.getIdentifyCode(phoneNum, "0");
                map.put("msg", "发送成功");
            }else{
                logger.info("mobile-getIdentifyCode,phoneNum: {} ",phoneNum);
                map.put("msg", "发送频率间隔时间应大于120秒以上");
            }

        } catch (Exception e) {
            logger.error("获取验证码是出错，请联系后台管理员！", e);
        }
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 验证短信验证码
     */
    @ResponseBody
    @RequestMapping("/checkIdentifyCode")
    public Result<Map<String, Object>> checkIdentifyCode(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String phoneNum = request.getParameter("phoneNum");
        String identifyCode = request.getParameter("identifyCode");
        // 短信验证码正确性验证
        try {
            userRegisterService.checkIdentifyCode(phoneNum, identifyCode);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.getInfoList().add("手机短信验证码校验出错");
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 用户注册时，用户信息保存
     */
    @ResponseBody
    @RequestMapping("/saveUserRegisterInfo")
    public Result<Map<String, String>> userRegisterSave(HttpServletRequest request) throws Exception {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        RPCSysUser sysUser = initialUserInfo(request);
        String clientMessage = NetworkUtil.getIpAddress(request);
        // 用户注册时，用户信息保存
        JSONObject retJson = null;
        if (PropertyUtil.getValue("ssoOpen").equals("1")) {
            retJson = userRegisterService.userRegisterSaveForClient(sysUser, clientMessage,null);
        } else {
            retJson = userRegisterService.userRegisterSave(sysUser,null);
        }
        String ticketId = retJson.getString("ticketId");
        LoginStatusUtils.putUserToCacheByUserPhoneNum(sysUser.getPhoneNum(), ticketId);
        dataMap.put("ticketId", ticketId);
        dataMap.put("imToken", retJson.getString("imToken"));

        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    /**
     * 对需要保存的用户信息进行封装
     */
    private RPCSysUser initialUserInfo(HttpServletRequest request) {
        RPCSysUser sysUser = new RPCSysUser();
        // 登录名
        sysUser.setLoginName(request.getParameter("phoneNum"));
        // 手机号码
        sysUser.setPhoneNum(request.getParameter("phoneNum"));
        // 姓名
        sysUser.setChineseName(request.getParameter("userName"));
        // 密码,确认密码前台验证好传过来
        sysUser.setPassword(request.getParameter("password"));
        return sysUser;
    }

    /**
     * 加载搜索企业
     */
    @ResponseBody
    @RequestMapping("/getSuggestEnterpriseList")
    public Result<Map<String, Object>> loadEnterpriseList(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> datamap = new HashMap<String, Object>();
        String enterpriseName = request.getParameter("enterpriseName");
        String enterpriseType = request.getParameter("enterpriseType");
        // 如果企业类型不正确，直接返回错误
        if (!CodeTypeConstant.ENTERPRISE_TYPE_DISPOSITION.equalsIgnoreCase(enterpriseType) && !CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equalsIgnoreCase(enterpriseType)) {
            datamap.put("msg", "传入的企业类型不正确！");
            result.setStatus(ResultStatus.Failure);
            return result;
        }
        
        List<EnterpriseVo> enterpriseInfos = enterpriseService.getEnterpriseVosByName(enterpriseName, enterpriseType);
        datamap.put("enterpriselist", enterpriseInfos);
        result.setData(datamap);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 加载处置企业和处置代理商
     */
    @ResponseBody
    @RequestMapping("/getCZEnterpriseList")
    public Result<Map<String, Object>> getCZEnterpriseList(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> datamap = new HashMap<String, Object>();
        String enterpriseName = request.getParameter("enterpriseName");
        List<EnterpriseVo> enterpriseInfos = enterpriseService.getCZEnterpriseVosByName(enterpriseName);
        datamap.put("enterpriselist", enterpriseInfos);
        result.setData(datamap);
        result.setStatus(ResultStatus.Success);
        return result;
    }
    /**
     * 加入企业
     */
    @ResponseBody
    @RequestMapping("/joinEnterprise")
    public Result<Map<String, Object>> joinEnterprise(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> datamap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        // 待加入的企业id
        String enterpriseId = request.getParameter("enterpriseId");
        // 邀请码(此功能暂时没有实现，后续有需求的时候在实现)
        String invitationCode = request.getParameter("invitationCode");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            myEnterpriseService.joinEnterprise(ticketId, invitationCode, enterpriseId);
            User userParam = new User();
            userParam.setUserId(user.getUserId());
            LoginStatusUtils.refreshUserToCacheByUserParam(userParam);
            result.setStatus(ResultStatus.Success);
        } catch (BusinessException e) {
            logger.error("加入企业申请过程中发生异常，用户已经绑定企业", e);
            datamap.put("exceptionMsg", e.getMessage());
            result.setStatus(ResultStatus.Failure);
        } catch (Exception e) {
            logger.error("加入企业申请过程中发生异常", e);
            result.setStatus(ResultStatus.Failure);
            datamap.put("exceptionMsg", e.getMessage());
        }
        result.setData(datamap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/getUserAdminByEnterId")
    public Result<String> getUserAdminByEnterId(HttpServletRequest request) {
        Result<String> result = new Result<String>();
        String enterpriseId = request.getParameter("enterpriseId");
        try {
        	UserExtended userExtended = userExtendedService.getUserAdminByEnterId(enterpriseId);
        	
        	if(userExtended!=null && StringUtils.isNotEmpty(userExtended.getSysUserId())){
        		User sysUser = userService.getUserByUserId(userExtended.getSysUserId());
        		result.setData(sysUser.getPhoneNo());
        		result.setStatus(ResultStatus.Success);
        	}else{
        		result.setStatus(ResultStatus.Error);
        	}
           
        } catch (Exception e) {
            logger.error("加入企业申请过程中发生异常", e);
            result.setStatus(ResultStatus.Failure);
        }

        return result;
    }
    
    @ResponseBody
    @RequestMapping("/getEnterpriseContacts")
    public Result<List<RPCSysUser> > getEnterpriseContacts(HttpServletRequest request) {
        Result<List<RPCSysUser> > result = new Result<List<RPCSysUser> >();
        String enterpriseId = request.getParameter("enterpriseId");
        try {
            List<RPCSysUser> contactsList = myEnterpriseService.getUserInfoByEntId(enterpriseId);
            result.setData(contactsList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        
        return result;
    }

}
