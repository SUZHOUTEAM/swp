package com.mlsc.waste.user.controller;

import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.common.util.RSAEncryptUtils;
import com.mlsc.yifeiwang.bindserve.entity.EntBitcionAccount;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.yifeiwang.bindserve.service.IEntBitcionAccountService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息维护Controller
 *
 * @author Cao Yan Yan
 */
@Controller
@RequestMapping("/personaluser")
@Scope("prototype")
public class PsersonalUserController {
    private final static Logger logger = LoggerFactory.getLogger(PsersonalUserController.class);

    private static final String PATH = "User/";

    @Autowired
    private UserService userService;

    @Autowired
    private IEntBitcionAccountService entBitcionAccountService;

    /**
     * 初始化个人用户界面
     */
    @RequestMapping("/initUserInfo")
    public ModelAndView initUserInfo(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "userInfo");
        String ticketId = request.getParameter("ticketId");
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            RPCSysUser initUser = userService.getUserInfoByUserId(ticketId, user.getUserId());
            user = userService.getUserInfo(user, initUser);
            UserExtended userExtended = userService.getUserExtendedInfoByUserId(user.getUserId());
            if (userExtended != null) {
                userService.getMailStatusAndMobileStatus(user, userExtended);
            }
            mv.addObject("ticketId", ticketId);
            mv.addObject("user", user);
            mv.addObject("editUser", new User());
        } catch (Exception e) {
            mv = new ModelAndView(Constant.URL_500);
            logger.error("初始化个人用户界面异常", e);
        }

        return mv;
    }

    /**
     * 我的账户
     */
    @RequestMapping("/myBit")
    public ModelAndView myBit(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "myBit");
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try{
            if(user!=null && StringUtils.isNotBlank(user.getUserId())){
                EntBitcionAccount bitcionAccount = entBitcionAccountService.getAccountByUserId(user.getUserId());
                mv.addObject("bitcionAccount", bitcionAccount);
                mv.addObject("entName", user.getEnterpriseName());
            }
        }catch (Exception e){
            mv = new ModelAndView(Constant.URL_500);
            logger.error("初始化企业用户信息时异常", e);
        }
        mv.addObject("ticketId", ticketId);
        return mv;
    }
    @ResponseBody
    @RequestMapping("/getMyBit")
    public Result<Map<String, String>> getMyBit(User user) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        try{
            if(user!=null && StringUtils.isNotBlank(user.getUserId())){
                EntBitcionAccount bitcionAccount = entBitcionAccountService.getAccountByUserId(user.getUserId());
                dataMap.put("bitcionAccount", bitcionAccount==null?"0":bitcionAccount.getBitcion().toString());
                dataMap.put("entName", user.getEnterpriseName());
                result.setData(dataMap);
            }
            result.setStatus(ResultStatus.Success);
        }catch (Exception e){
            logger.error("初始化企业用户信息时异常", e);
            result.getInfoList().add(Constant.SYS_MSG);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
    /**
     * 初始化企业帐户余额界面
     */
    @RequestMapping("/recharge")
    public ModelAndView recharge(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "recharge");
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try{
            if(user!=null && StringUtils.isNotBlank(user.getUserId())){
                EntBitcionAccount bitcionAccount = entBitcionAccountService.getAccountByUserId(user.getUserId());
                mv.addObject("bitcionAccount", bitcionAccount);
                mv.addObject("entName", user.getEnterpriseName());
            }
        }catch (Exception e){
            mv = new ModelAndView(Constant.URL_500);
            logger.error("初始化企业用户信息时异常", e);
        }
        mv.addObject("ticketId", ticketId);
        return mv;
    }
    /**
     * 初始化企业帐户余额界面
     */
    @RequestMapping("/paySuccess")
    public ModelAndView paySuccess(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "paySuccess");
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try{
            if(user!=null && StringUtils.isNotBlank(user.getUserId())){
                EntBitcionAccount bitcionAccount = entBitcionAccountService.getAccountByUserId(user.getUserId());
                mv.addObject("bitcionAccount", bitcionAccount);
                mv.addObject("entName", user.getEnterpriseName());
            }
        }catch (Exception e){
            mv = new ModelAndView(Constant.URL_500);
            logger.error("初始化企业用户信息时异常", e);
        }
        mv.addObject("ticketId", ticketId);
        return mv;
    }

    /**
     * 更新用户名称
     */
    @ResponseBody
    @RequestMapping("/updateNewName")
    public Result updateNewName(User user ,String userNewName) {
        Result result = new Result();
        try {
            userService.updateUserName(user);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("更新用户名失败", e);
        }
        return result;
    }

    /**
     * 确认用户密码是否确认
     */
    @ResponseBody
    @RequestMapping("/checkUserPassword")
    public Result<Map<String, String>> checkUserPassword(User userInfo, HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        String status = null;
        try {
            Boolean correct = userService.isPasswordCorrect(ticketId, userInfo.getUserId(), RSAEncryptUtils.encrypt(userInfo.getPassword()));
            if (correct) {
                status = Constant.IS_VALID;
            } else {
                status = Constant.IS_NOT_VALID;
                msg = "密码错误";
            }
        } catch (Exception e) {
            msg = "密码错误";
            logger.error(msg, e);
            status = Constant.IS_NOT_VALID;
        }
        map.put("msg", msg);
        map.put("status", status);
        result.setData(map);
        return result;
    }

    /**
     * 确认用户邮箱是否已被使用
     */
    @ResponseBody
    @RequestMapping("/checkMailAddressExist")
    public Result<Map<String, String>> checkMailAddressExist(User userInfo, HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        String status = null;
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            status = userService.isMailAddressExist(ticketId, userInfo.getMailAddress());
        } catch (Exception e) {
            logger.error(msg, e);
            status = Constant.IS_NOT_VALID;
        }
        map.put("status", status);
        result.setData(map);
        return result;
    }

    /**
     * 更新用户邮箱
     */
    @ResponseBody
    @RequestMapping("/updateUserMailAddress")
    public Result<Map<String, String>> updateUserMailAddress(User userInfo, HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        String status ;
        String mailAddress = userInfo.getMailAddress();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            status = userService.updateUserMailAddress(ticketId, user.getUserId(), mailAddress);
        } catch (Exception e) {
            msg = "邮箱更新失败";
            logger.error(msg, e);
            status = Constant.IS_NOT_VALID;
        }
        map.put("msg", msg);
        map.put("status", status);
        map.put("newMailAddress", mailAddress);
        result.setData(map);
        return result;
    }

    /**
     * 更新用户密码
     */
    @ResponseBody
    @RequestMapping("/updateUserPassword")
    public Result<Map<String, String>> updateUserPassword(User userInfo, HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        String status ;
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            status = userService.updateUserPassword(ticketId, user.getUserId(), RSAEncryptUtils.encrypt(userInfo.getNewPassword()));

        } catch (Exception e) {
            msg = "密码更新失败";
            logger.error(msg, e);
            status = Constant.IS_NOT_VALID;
        }
        map.put("msg", msg);
        map.put("status", status);
        result.setData(map);
        return result;
    }


    /**
     * 查询手机号码是否存在
     */

    @ResponseBody
    @RequestMapping("/checkPhoneNumExist")
    public Result<Map<String, String>> checkPhoneNumExist(User userInfo, HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        String msg = null;
        String status = null;
        String ticketId = request.getParameter("ticketId");
        try {
            Boolean phoneExist = userService.isPhoneNumExist(ticketId, userInfo.getNewPhoneNo());
            if (phoneExist) {
                status = Constant.IS_NOT_VALID;
            } else {
                status = Constant.IS_VALID;
            }

        } catch (Exception e) {
            msg = "手机是否存在验证失败";
            logger.error(msg, e);
            status = Constant.IS_NOT_VALID;
        }
        map.put("msg", msg);
        map.put("status", status);
        result.setData(map);
        return result;
    }

    /**
     * 验证手机验证码
     */


    @ResponseBody
    @RequestMapping("/verifyCode")
    public Result<String> getVerifyCode(User userInfo, HttpServletRequest request) {
        Result<String> result = new Result<String>();
        Map<String, String> map = new HashMap<String, String>();
        String msg = null;
        String status = null;
        try {
            boolean rs = userService.checkIdentifyCode(userInfo.getNewPhoneNo(), userInfo.getUserVerificationCode());
            result.setStatus(ResultStatus.Success);
            result.setData(userInfo.getNewPhoneNo());
        } catch (WasteBusinessException e) {
            logger.error("手机短信验证码校验失败");
            result.getInfoList().add(e.getMessage());
        } catch (Exception e) {
            logger.error("手机短信验证码校验出错");
            result.getInfoList().add("手机短信验证码校验出错");
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 更新手机号
     */
    @ResponseBody
    @RequestMapping("/updateUserPhoneNo")
    public Result<String> updateUserPhoneNo(User userInfo, HttpServletRequest request) {
        Result<String> result = new Result<String>();
        String ticketId = request.getParameter("ticketId");
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            userService.updateUserPhone(ticketId, user.getUserId(), userInfo.getNewPhoneNo());
            result.setData(userInfo.getNewPhoneNo());
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("更新手机号异常", e);
            result.getInfoList().add(Constant.SYS_MSG);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
}
