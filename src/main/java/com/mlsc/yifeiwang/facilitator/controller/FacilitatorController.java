package com.mlsc.yifeiwang.facilitator.controller;


import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.myenterprise.service.MyEnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2018-01-15
 */
@Controller
@RequestMapping("/facilitator")
public class FacilitatorController {

    private static final String PATH = "facilitator/";
    @Autowired
    private MyEnterpriseService myEnterpriseService;
    @Autowired
    private UserExtendedService userExtendedService;
    @Autowired
    private UserService userService;

    @RequestMapping("/customerList")
    public ModelAndView customerList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "customerList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/addCustomer")
    public ModelAndView addCustomer(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "addCustomer");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/publishList")
    public ModelAndView publishList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "publishList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/publish")
    public ModelAndView publish(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "publish");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/addWaste")
    public ModelAndView addWaste(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "addWaste");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/releaseOrderList")
    public ModelAndView releaseOrderList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "releaseOrderList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/inquiryOrderList")
    public ModelAndView inquiryOrderList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "inquiryOrderList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/transferList")
    public ModelAndView transferList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "transferList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/cfList")
    public ModelAndView cfList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "cfList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/buyList")
    public ModelAndView buyList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "buyList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/cfBuy")
    public ModelAndView cfBuy(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "cfBuy");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/contractRecordList")
    public ModelAndView contractRecordList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "contractRecordList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 区域服务商企业详细
     *
     * @param request
     * @return
     */
    @RequestMapping("/entInfo")
    public ModelAndView entInfo(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "entInfo");
        mv = Util.stopMessageDisplay(mv, request);
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        // 1.根据企业ID拿到企业基本表的全部信息；
        RPCSysEnterpriseBaseVo sysEnterpriseBaseVo = myEnterpriseService.getSysEnterpriseBasesByEntId(ticketId, user.getEnterpriseId());
        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(user.getUserId());
        if (UserRole.ADMIN.getRoleCode().equals(userExtended.getRole()) && UserStatus.PASS.getStatusCode().equals(userExtended.getUserStatus())) {
            List<UserApproveVo> pendingApproveUserList = userExtendedService.listUserApproVo(user.getEnterpriseId(),
                    UserStatus.SUBMIT.getStatusCode());
            List<UserApproveVo> approvedUserList = userExtendedService.listUserApproVo(user.getEnterpriseId(),
                    UserStatus.PASS.getStatusCode());
            mv.addObject("pendingApproveUserList", pendingApproveUserList);
            mv.addObject("approvedUserList", approvedUserList);
        }
        // 2.需要额外拿到企业的类型
        // 查询指定企业里面所有的的人员信息
        mv.addObject("userRole", userExtended.getRole());
        mv.addObject("sysEnterpriseBase", sysEnterpriseBaseVo);
        mv.addObject("ticketId", ticketId);
        mv.addObject("");
        return mv;
    }


    @RequestMapping("/userInfo")
    public ModelAndView userInfo(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "userInfo");
        String ticketId = request.getParameter("ticketId");
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            RPCSysUser initUser = userService.getUserInfoByUserId(ticketId, user.getUserId());
            userService.getUserInfo(user, initUser);
            UserExtended userExtended = userService.getUserExtendedInfoByUserId(user.getUserId());
            if (userExtended != null) {
                userService.getMailStatusAndMobileStatus(user, userExtended);
            }
            mv.addObject("ticketId", ticketId);
            mv.addObject("user", user);
            mv.addObject("editUser", new User());
        } catch (Exception e) {
            mv = new ModelAndView(Constant.URL_500);
        }

        return mv;
    }


}
