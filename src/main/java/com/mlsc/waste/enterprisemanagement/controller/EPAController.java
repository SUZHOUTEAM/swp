package com.mlsc.waste.enterprisemanagement.controller;

import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.waste.user.dao.UserExtendedDao;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 环保局Controller
 * 
 * @author zhugl
 */
@Controller
@RequestMapping("/removeUserEnt")
@Scope("prototype")
public class EPAController {

    private static final String PATH = "EPA/";
    @Autowired
    private IRPCServiceClient client;

    @Autowired
    private UserExtendedDao userExtendedDao;
    
    @Autowired
    private UserService userService;
    
    /**
     * 产废企业index
     * 
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) throws DaoAccessException {
        ModelAndView mv = new ModelAndView(PATH + "removeUserEnt");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }
    
    /**
     * 输入手机号码删除企业
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/removeEntByPhoneNum")
    public Result<Map<String, String>> removeEntByPhoneNum(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String phoneNum = request.getParameter("phoneNum");
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        try {
            
            // 通过手机号码查询企业ID
            RPCSysUser sysUser= client.getUserServiceManager().queryUserInfoByPhoneNum(ticketId, phoneNum);
            if (sysUser == null || StringUtils.isBlank(sysUser.getUserId())) {
                msg = "该手机号码不存在对应的用户";
                result.setStatus(ResultStatus.Failure);
            } else {
                RPCSysEnterpriseBase enterpriseBase = client.getOrgComServiceManager().queryEnterpriseInfo(ticketId, sysUser.getUserId());
                if (enterpriseBase == null || StringUtils.isBlank(enterpriseBase.entId)) {
                    msg = "该手机号码不存在对应的企业";
                    result.setStatus(ResultStatus.Failure);
                } else {
                    removeEnterprise(ticketId, sysUser.getUserId(), enterpriseBase.getEntId());
                    result.setStatus(ResultStatus.Success);
                }
            }
        } catch (Exception e) {
            msg = "删除过程中有异常发生了，请找管理员";
            result.setStatus(ResultStatus.Failure);
        }

        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }
    /**
     * 输入手机号码删除企业
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/removeUser")
    public Result<Map<String, String>> removeUser(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String phoneNum = request.getParameter("phoneNum");
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        try {
            
            // 通过手机号码查询企业ID
            RPCSysUser sysUser= client.getUserServiceManager().queryUserInfoByPhoneNum(ticketId, phoneNum);
            if (sysUser == null || StringUtils.isBlank(sysUser.getUserId())) {
                msg = "该手机号码不存在对应的用户";
                result.setStatus(ResultStatus.Failure);
            } else {
                RPCSysEnterpriseBase enterpriseBase = client.getOrgComServiceManager().queryEnterpriseInfo(ticketId, sysUser.getUserId());
                removeUser(ticketId, sysUser.getUserId(), enterpriseBase.getEntId());
                result.setStatus(ResultStatus.Success);
            }
        } catch (Exception e) {
            msg = "删除过程中有异常发生了，请找管理员";
            result.setStatus(ResultStatus.Failure);
        }
        
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }
    
   /* *//**
     * 输入企业ID删除企业
     * 
     * @param request
     * @return
     *//*
    @ResponseBody
    @RequestMapping("/removeEntById")
    public Result<Map<String, String>> removeEntById(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String entId = request.getParameter("entId");
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        try {
            // 通过企业ID查询企业
            RPCSysEnterpriseBase queryEnterprise = client.getOrgComServiceManager().queryEnterprise(ticketId, entId);
            if (queryEnterprise == null || StringUtils.isBlank(queryEnterprise.getEntId())) {
                msg = "该ID不存在对应的企业";
                result.setStatus(ResultStatus.Failure);
            } else {
                removeEnterprise(ticketId, sysUser.getUserId(), enterpriseBase.getEntId());
            }
        } catch (Exception e) {
            msg = "删除过程中有异常发生了，请找管理员";
            result.setStatus(ResultStatus.Failure);
        }
        
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }*/
        
    private void removeUser (String ticketId,String userId,String entId) throws Exception {
        client.getOrgComServiceManager().removeUserEnterpriseRelation(ticketId, userId, entId);
        client.getUserServiceManager().removeUser(ticketId, userId);
        
        // 删除用户扩展表
        UserExtended entex = new UserExtended();
        entex.setSysUserId(userId);
        List<UserExtended> query = userExtendedDao.query(entex);
        if (query != null && !query.isEmpty()) {
            List<String> ids = new ArrayList<String>();
            for (UserExtended entexs: query){
                ids.add(entexs.getId());
            }
            userExtendedDao.deletes(ids);
        }
    }
    private void removeEnterprise(String ticketId, String userId, String entId)
			throws Exception {
		// 删除企业扩展表
		if (StringUtils.isNotBlank(entId)) {
			client.getOrgComServiceManager().removeUserEnterpriseRelation(
					ticketId, userId, entId);
			UserExtended userExtend = userService.getUserExtendedInfoByUserId(userId);
			userExtend.setRole("");
			userExtend.setUserStatus(" ");
			userExtend.setUpdateTime(Util.datetimeToString(new Date()));
			userExtendedDao.update(userExtend);
		}
	}
}
