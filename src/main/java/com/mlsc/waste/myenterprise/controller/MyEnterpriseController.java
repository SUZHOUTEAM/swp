package com.mlsc.waste.myenterprise.controller;

import com.mlsc.common.exception.BusinessException;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
import com.mlsc.waste.licence.model.OperationLicenceVo;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.myenterprise.service.MyEnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.userenterpriseapproverecord.model.AuditUserRecordVo;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.NetworkUtil;
import com.mlsc.waste.utils.PropertyUtil;
import com.mlsc.waste.utils.UserRole;
import com.mlsc.waste.utils.UserStatus;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.model.EnterpriseVo;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;
import java.util.Map;

/**
 * 我的企业Controller
 *
 * @author zhugl
 */
@Controller
@RequestMapping("/myenterprise")
@Scope("prototype")
public class MyEnterpriseController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(MyEnterpriseController.class);

    @Autowired
    private MyEnterpriseService myEnterpriseService;

    @Autowired
    private LicenceService licenceService;

    @Autowired
    private EnterpriseService enterpriseService;

    private static final String PATH = "Enterprise/";

    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    private UserExtendedService userExtendedService;

    @Autowired
    private ICodeValueService codeValueService;
    @Autowired
    IRPCServiceClient client;
    @Autowired
    private UserService userService;

    /**
     * 用户信息：我的企业
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/myEnterprise")
    public ModelAndView myEnterprise(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        String ticketId = request.getParameter("ticketId");
        boolean isBinded = myEnterpriseService.isBindedEnterprise(ticketId);
        if (isBinded) {
            mv.setViewName("redirect:/" + "myenterprise/viewOwnEnterprise.htm");
        } else {
            mv.setViewName("redirect:/" + "myenterprise/bindEnterprise.htm");
        }
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 绑定企业
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping("/bindEnterprise")
    public ModelAndView bindEnterprise(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "bindEnterprise");
        String ticketId = request.getParameter("ticketId");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 加载搜索企业
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/loadEnterpriseList")
    public Result<Map<String, Object>> loadEnterpriseList(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> datamap = new HashMap<String, Object>();
        String enterpriseName = request.getParameter("enterpriseName");
        List<EnterpriseVo> enterpriseInfos = enterpriseService.getEnterpriseVosByName(enterpriseName, null);
        datamap.put("enterpriselist", enterpriseInfos);
        result.setData(datamap);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 加载企业log
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getEnterImgSrc")
    public Result<Map<String, Object>> getEnterImgSrc(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String enterpriseId = request.getParameter("enterpriseId");
        String enterpriseImgSrc = myEnterpriseService.getEnterImgSrc(enterpriseId);
        if (StringUtils.isNotBlank(enterpriseImgSrc)) {
            dataMap.put("imgSrc", enterpriseImgSrc);
        }
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    /**
     * 加载企业类型
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getEnterpriseTypesByEntId")
    public Result<Map<String, Object>> getEnterpriseTypesByEntId(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String enterpriseId = request.getParameter("enterpriseId");
        List<CodeValue> enterpriseTypes = codeValueService.getEnterpriseTypesByEntId(enterpriseId);
        dataMap.put("enterpriseTypes", enterpriseTypes);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    /**
     * 加入企业
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @ResponseBody
    @RequestMapping("/joinEnterprise")
    public Result<String> joinEnterprise(HttpServletRequest request) {
        Result<String> result = new Result<String>();
        String ticketId = request.getParameter("ticketId");
        // 待加入的企业id
        String enterpriseId = request.getParameter("enterpriseId");
        // 邀请码(此功能暂时没有实现，后续有需求的时候在实现)
        String invitationCode = request.getParameter("invitationCode");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            myEnterpriseService.joinEnterprise(ticketId, invitationCode, enterpriseId);
            //刷新缓存
            User userParam = new User();
            userParam.setUserId(user.getUserId());
            LoginStatusUtils.refreshUserToCacheByUserParam(userParam);
            result.setStatus(ResultStatus.Success);
        } catch (BusinessException e) {
            logger.error("加入企业申请过程中发生异常，用户已经绑定企业", e);
            result.getInfoList().add(e.getMessage());
            result.setStatus(ResultStatus.Failure);
        } catch (Exception e) {
            logger.error("加入企业申请过程中发生异常", e);
            result.getInfoList().add(Constant.SYS_MSG);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 管理员审核申请的初始化页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/auditUserList")
    public ModelAndView auditUserList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "auditUserList");
        List<AuditUserRecordVo> userList = null;
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String enterpriseId = "";
        try {
            RPCSysEnterpriseBase enterpriseBase = client.getOrgComServiceManager().queryEnterpriseInfo(ticketId, user.getUserId());
            enterpriseId = String.valueOf(enterpriseBase.getEntId());// 根据tickid拿到对应的企业id
        } catch (Exception e) {
            logger.error("查询企业信息出错", e);
            mv.setViewName(Constant.URL_500);
        }
        userList = myEnterpriseService.getUserEnterpriseApproveRecords(CodeTypeConstant.USER_EVENT_TYPE_JOIN, CodeTypeConstant.USER_EVENT_STATUS_SUBMIT, enterpriseId);
        mv.addObject("userList", userList);
        return mv;
    }


    /**
     * 平台管理员审核企业时以及普通的查看企业详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/viewEnterpriseDetail")
    public ModelAndView viewEnterpriseDetail(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "viewEnterpriseDetail");
        String enterpriseId = request.getParameter("enterpriseId");// 需要查看的企业ID
        String onlyViewFlg = request.getParameter("onlyViewFlg");// 控制审核相关按钮是否显示
        String ticketId = request.getParameter("ticketId");
        String breadcrumbName = request.getParameter("breadcrumbName");
        String facilitatorEntId = request.getParameter("facilitatorEntId");// 服务商企业Id
        try {
            if (Constant.TRUE.equalsIgnoreCase(onlyViewFlg)) {
                mv.addObject("onlyViewFlg", true);
            } else {
                mv.addObject("onlyViewFlg", false);
            }
            // 1.根据企业ID拿到企业基本表的全部信息；
            RPCSysEnterpriseBaseVo sysEnterpriseBaseVo = myEnterpriseService.getSysEnterpriseBasesByEntId(ticketId, enterpriseId);
            // 2.需要额外拿到企业的类型
            List<CodeValue> enterpriseTypes = codeValueService.getEnterpriseTypesByEntId(enterpriseId);
            // 查询指定企业里面所有的的人员信息
            List<RPCSysUser> prcSysUserList = myEnterpriseService.listEnterpriseUser(enterpriseId,facilitatorEntId);

            mv.addObject("sysEnterpriseBase", sysEnterpriseBaseVo);
            mv.addObject("enterpriseTypes", enterpriseTypes);
            mv.addObject("prcSysUserList", prcSysUserList);
            mv.addObject("index", request.getParameter("index"));
        } catch (Exception e) {
            logger.error("查看企业详情失败", e);
            mv.setViewName(Constant.URL_500);
        }
        mv.addObject("ticketId", ticketId);
        mv.addObject("breadcrumbName", breadcrumbName);
        mv.addObject("isSysUser", Util.isSysUser(request.getParameter("ticketId")));// 判断是不是平台管理员
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getEnterUserByEnterId")
    public Result<Map<String, Object>> getEnterUserByEnterId(HttpServletRequest request) {
        String enterpriseId = request.getParameter("enterpriseId");// 需要查看的企业ID
        List<RPCSysUser> prcSysUserList = myEnterpriseService.getUserInfoByEntId(enterpriseId);
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("userInfo", prcSysUserList);
        result.setData(dataMap);
        result.setStatus(ResultStatus.Success);
        return result;
    }


    /**
     * 查看自己的企业详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/viewOwnEnterprise")
    public ModelAndView viewOwnEnterprise(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "viewOwnEnterprise");
        String enterpriseId = request.getParameter("enterpriseId");// 需要加入企业的ID
        String dispalyAddBtn = request.getParameter("dispalyAddBtn");// 控制【加入企业】按钮是否显示
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        if (StringUtils.isBlank(enterpriseId)) {//查看自己所属企业
            enterpriseId = user.getEnterpriseId();
            mv.addObject("applicationRecords", myEnterpriseService.getUserEnterpriseApproveRecordVos(ticketId));
        }
        if (Constant.TRUE.equalsIgnoreCase(dispalyAddBtn)) {
            mv.addObject("dispalyAddBtn", true);
        } else {
            mv.addObject("dispalyAddBtn", false);
        }

        // 1.根据企业ID拿到企业基本表的全部信息；
        RPCSysEnterpriseBaseVo sysEnterpriseBaseVo = myEnterpriseService.getSysEnterpriseBasesByEntId(ticketId, enterpriseId);
        if (sysEnterpriseBaseVo.getCreateTime() != null && sysEnterpriseBaseVo.getCreateTime().length() > 10) {
            sysEnterpriseBaseVo.setCreateTime(sysEnterpriseBaseVo.getCreateTime().substring(0, 10));
        }
        // 2.需要额外拿到企业的类型
        List<CodeValue> enterpriseTypes = codeValueService.getEnterpriseTypesByEntId(enterpriseId);
        // 查询指定企业里面所有的的人员信息
        List<RPCSysUser> prcSysUserList = myEnterpriseService.getUserInfoByEntId(enterpriseId);
        mv.addObject("sysEnterpriseBase", sysEnterpriseBaseVo);
        mv.addObject("enterpriseTypes", enterpriseTypes);
        mv.addObject("prcSysUserList", prcSysUserList);
        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(user.getUserId());
        if (UserRole.ADMIN.getRoleCode().equals(userExtended.getRole()) && UserStatus.PASS.getStatusCode().equals(userExtended.getUserStatus())) {
            List<UserApproveVo> pendingApproveUserList = userExtendedService.listUserApproVo(user.getEnterpriseId(),UserStatus.SUBMIT.getStatusCode());
            List<UserApproveVo> approvedUserList = userExtendedService.listUserApproVo(user.getEnterpriseId(), UserStatus.PASS.getStatusCode());
            mv.addObject("pendingApproveUserList", pendingApproveUserList);
            mv.addObject("approvedUserList", approvedUserList);
        }
        mv.addObject("userRole", userExtended.getRole());
        mv.addObject("userExtendId", userExtended.getId());
        mv.addObject("userStatus", userExtended.getUserStatus());
        mv.addObject("ticketId", ticketId);
        mv.addObject("");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getHomePageEnterpriseInfoDetail")
    public Result<Map<String, Object>> getHomePageEnterpriseInfoDetail(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String enterpriseId = request.getParameter("enterpriseId");
        RPCSysEnterpriseBaseVo sysEnterpriseBaseVo = myEnterpriseService.getSysEnterpriseBasesByEntId("", enterpriseId);
        OperationLicence operationLicence = licenceService.getValidLicIdByEnterpriseId(enterpriseId);
        if (operationLicence != null) {
            String licenceId = operationLicence.getId();
            OperationLicenceVo licence = licenceService.getlicenceApprovedById(licenceId);
            List<OperationLicenceItemVo> listVo = licenceService.getWasteInfoByLicenceId(licenceId);
            dataMap.put("licence", licence);
            dataMap.put("operationLicenceItemVo", listVo);
        }

        dataMap.put("sysEnterpriseBaseVo", sysEnterpriseBaseVo);
        result.setData(dataMap);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    @ResponseBody
    @RequestMapping("/checkTickedId")
    public Result<Map<String, Object>> checkTickedId(PagingParameter pagingParameter, HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String clientMessage = NetworkUtil.getIpAddress(request);
        myEnterpriseService.checkTickedId(ticketId,clientMessage,user);

        dataMap.put("user", user);
        dataMap.put("matchPlatform", PropertyUtil.getValue("matchPlatform"));
        dataMap.put("ssoOpen", PropertyUtil.getValue("ssoOpen"));
        result.setData(dataMap);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateUserStatus")
    public Result<User> updateUserStatus(HttpServletRequest request) throws Exception {
        Result<User> result = new Result<>();
        String ticketId = request.getParameter("ticketId");
        String recordid = request.getParameter("recordid");
        String userStatus = request.getParameter("userStatus");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            UserExtended param = new UserExtended();
            User userParam = new User();
            getUserExtendedParam(recordid,param,user,userParam,userStatus);

            myEnterpriseService.updateUserRoleAndStatus(ticketId, param, user.getEnterpriseId());
            //刷新缓存中对应的user
            User rsUser = LoginStatusUtils.refreshUserToCacheByUserParam(userParam);
            result.setStatus(ResultStatus.Success);
            result.setData(rsUser);
        } catch (Exception e) {
            logger.error("申请事项撤销申请时异常", e);
            result.setStatus(ResultStatus.Failure);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/enterpriseExit")
    public Result<Map<String, Object>> enterpriseExit(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String recordid = request.getParameter("recordid");
        try {
            UserExtended param = new UserExtended();
            User userParam = new User();
            getUserExtendedParam(recordid,param,user,userParam,UserStatus.QUIT.getStatusCode());
            myEnterpriseService.updateUserRoleAndStatus(ticketId, param, user.getEnterpriseId());
            //刷新缓存中对应的user
            LoginStatusUtils.refreshUserToCacheByUserParam(userParam);
            result.setStatus(ResultStatus.Success);
        } catch (BusinessException e) {
            logger.error("企业管理员退出企业员工时异常，只剩一个管理员，不能退出", e);
            result.getInfoList().add(e.getMessage());
            result.setStatus(ResultStatus.Failure);
        } catch (Exception e) {
            logger.error("企业管理员退出企业员工时异常", e);
            result.getInfoList().add(Constant.SYS_MSG);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    private void getUserExtendedParam(String recordId, UserExtended param,User user, User userParam,String userStatus) {
        if (StringUtils.isNotEmpty(recordId)) {
            param.setId(recordId);
            userParam.setExtendId(recordId);
        } else {
            param.setSysUserId(user.getUserId());
            userParam.setUserId(user.getUserId());
        }
        param.setUserStatus(userStatus);
    }

    @RequestMapping("/updateUserRole")
    @ResponseBody
    public Result<String> updateUserRole(String ticketId, UserExtended extended) {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        Result<String> result = new Result<>();
        try {
            myEnterpriseService.updateUserRoleAndStatus(ticketId, extended, user.getEnterpriseId());
            //刷新缓存中对应的user
            User userParam = new User();
            userParam.setExtendId(extended.getId());
            LoginStatusUtils.refreshUserToCacheByUserParam(userParam);
            result.setStatus(ResultStatus.Success);
        } catch (BusinessException e) {
            logger.error("修改用户管理员权限发送异常，只剩一个管理员，不能退出", e);
            result.getInfoList().add(e.getMessage());
            result.setStatus(ResultStatus.Failure);
        } catch (Exception e) {
            logger.error("修改用户管理员权限发送异常", e);
            result.getInfoList().add(Constant.SYS_MSG);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }




}
