package com.mlsc.waste.wastecircle.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.myenterprise.service.MyEnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.waste.wastecircle.model.MessageBodyVo;
import com.mlsc.waste.wastecircle.model.OfflineMessage;
import com.mlsc.waste.wastecircle.model.SearchCondition;
import com.mlsc.waste.wastecircle.service.CooperationRelationService;
import com.mlsc.waste.wastecircle.service.OfflineMessageService;
import com.mlsc.waste.wastecircle.service.WasteCircleService;
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
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wastecircle")
@Scope("prototype")
public class WasteCircleController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(WasteCircleController.class);

    private static final String PATH = "WasteCircle/";

    @Autowired
    private WasteCircleService wasteCircleService;

    @Autowired
    private CooperationRelationService followService;

    @Autowired
    private UserService userService;

    @Autowired
    private MyEnterpriseService myEnterpriseService;

    @Autowired
    private OfflineMessageService offlineMessageService;

    /**
     * 初始化易废圈页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/init")
    public ModelAndView initPage(HttpServletRequest request) {
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String entType=user.getEntType();
        if(StringUtils.isEmpty(entType)){//未绑定企业
            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:/" + "myenterprise/bindEnterprise.htm?ticketId="+ticketId);
            return mv;
        }else if(CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equalsIgnoreCase(entType)){//产废企业
            ModelAndView mv = new ModelAndView(PATH + "tmall");
            return mv;
        }else if(CodeTypeConstant.ENTERPRISE_TYPE_DISPOSITION.equalsIgnoreCase(entType)){//处置企业
            ModelAndView mv = new ModelAndView(PATH + "cfList");
            return mv;
        }else{//处置服务商
            ModelAndView mv = new ModelAndView(PATH + "cfList_facilitator");
            return mv;
        }
    }

    @RequestMapping("/tmall")
    public ModelAndView tmall(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String entType=user.getEntType();
        if(StringUtils.isEmpty(entType)){
            mv.setViewName("redirect:/" + "myenterprise/bindEnterprise.htm");
        }else{
            if(CodeTypeConstant.ENTERPRISE_TYPE_FACILITATOR.equalsIgnoreCase(entType)){//服务商
                mv = new ModelAndView("facilitator/"+ "tmall");
            }else{
                mv = new ModelAndView(PATH + "tmall");
            }
        }
        mv.addObject("ticketId", ticketId);
        return mv;
    }

    @ResponseBody
    @RequestMapping("/initWasteCircleData")
    public Result<Map<String, Object>> initWasteCircleData(HttpServletRequest request,String ticketId,User user ,PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SearchCondition searchCondition = JSONArray.parseObject(request.getParameter("searchCondition"), SearchCondition.class);
        List<MessageBodyVo> messageList = null;
        try {
            messageList = wasteCircleService.getMessageListReleaseList(searchCondition, ticketId, user, pagingParameter);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取消息体列表出错", e);
            result.setStatus(ResultStatus.Failure);
        }

        dataMap.put("messageList", JSONArray.toJSON(messageList));
        dataMap.put("paging", pagingParameter);
        dataMap.put("searchCondition", searchCondition);

        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/getEnterpriseInfoByUserId")
    public Result<Map<String, Object>> getEnterpriseInfoByUserId(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {

            EnterpriseInfo enterpriseInfo = wasteCircleService.getEnterpriseInfoByUserId(ticketId, user);
            if (enterpriseInfo != null) {
                dataMap.put("enterpriseInfo", enterpriseInfo);
            }
            dataMap.put("userInfo", user);
            String wastecycleInit = wasteCircleService.getUserExtendedByUserId(user.getUserId());
            dataMap.put("wastecycleInit", wastecycleInit);
            dataMap.put("myFollowCount", followService.getFollowEnterprisesCount(user.getUserId()));
            dataMap.put("myPublishCount", wasteCircleService.queryMyAllCoopMsg(user));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据用户id获取企业信息出错", e);
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }

    /**
     * 确认网站使用情况
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/confirmKnowStep")
    public Result<Map<String, String>> confirmKnowStep(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            // 获取企业Id
            String enterpriseId = user.getEnterpriseId();
            if (enterpriseId != null && StringUtils.isNotBlank(enterpriseId)) {
                wasteCircleService.updateWasteCycleInit(user.getUserId());
            }
            msg = "您已大概了解如何使用本网站，请开始您的废物处理之旅";
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    /**
     * 消息检索 === 企业名称字段
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getEntDropDownList")
    public Map<String, Object> getEntDropDownList(HttpServletRequest request) {
        Map<String, Object> datamap = null;
        String keyword = request.getParameter("keyword");
        try {
            if (StringUtils.isNoneBlank(keyword)) {
                datamap = wasteCircleService.getEntDropDownList(keyword);
            }
        } catch (Exception e) {
            logger.error("获取企业名称列表异常", e);
        }
        return datamap;
    }

    /**
     * 消息检索 === 八位码
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCodeWasteDropDownList")
    public Map<String, Object> getCodeWasteDropDownList(HttpServletRequest request) {
        Map<String, Object> datamap = null;
        String keyword = request.getParameter("keyword");
        try {
            if (StringUtils.isNoneBlank(keyword)) {
                datamap = wasteCircleService.getCodeWasteDropDownList(keyword);
            }
        } catch (Exception e) {
            logger.error("获取八位码列表异常", e);
        }
        return datamap;
    }

    @ResponseBody
    @RequestMapping("/saveFollow")
    public Result<String> saveFollow(HttpServletRequest request) {
        Result<String> result = new Result<String>();
        String entId = request.getParameter("entId");
        String ticketId = request.getParameter("ticketId");
        String action = request.getParameter("action");
        try {
            wasteCircleService.saveOrRemoveFollow(entId, action, ticketId);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);

        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getFollow")
    public Map<String, Object> getFollow(HttpServletRequest request) {
        Map<String, Object> datamap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String entId = request.getParameter("entId");

        try {
            String follow = wasteCircleService.getFollow(ticketId, entId) ? "取消关注" : "关注";
            datamap.put("follow", follow);
        } catch (Exception e) {
            logger.error("判断有没有和某个企业或者个人建立关注关系时", e);
        }
        return datamap;	
    }

    @RequestMapping("/publishList")
    public ModelAndView publishList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "publishList");
        String ticketId = request.getParameter("ticketId");
        mv.addObject("ticketId", ticketId);
        return mv;
    }


    /**
     * 获取企业联系人列表(云信)
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getEnterpriseContacts")
    public Result<Map<String, Object>> getEnterpriseContacts(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String enterpriseId = request.getParameter("enterpriseId");
        String ticketId = request.getParameter("ticketId");
        User userByTicketId = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            List<RPCSysUser> contactsList = myEnterpriseService.getUserInfoByEntId(enterpriseId);
            dataMap.put("contactsList", contactsList);
            dataMap.put("myPhone", userByTicketId.getPhoneNo());
            dataMap.put("myName", userByTicketId.getUserName());
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }



    @ResponseBody
    @RequestMapping("/getPhoneNoByUserId")
    public Result<Map<String, Object>> getPhoneNoByUserId(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String receivedAccId = request.getParameter("publisherId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        RPCSysUser receivedUser = null;
        String flag = null;
        try {
            receivedUser = userService.getUserInfoByUserId(ticketId, receivedAccId);
            dataMap.put("phoneNo", receivedUser.getPhoneNum());
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/checkWaste")
    public Result<String> checkHasEnterpriseWaste(HttpServletRequest request) {
        Result<String> result = new Result<>();
        String ticketId = request.getParameter("ticketId");
        String enterpriseId = request.getParameter("enterpriseId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        if (StringUtils.isBlank(enterpriseId)) {
            enterpriseId = user.getEnterpriseId();
        }
        try {
            boolean rs = wasteCircleService.checkHasEnterpriseWaste(enterpriseId);
            if (rs) {
                result.setStatus(ResultStatus.Success);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("判断企业是否含有产废信息出错", e);
            result.setStatus(ResultStatus.Error);
            result.getInfoList().add(Constant.SYS_MSG);
        }
        return result;
    }



    @ResponseBody
    @RequestMapping("/saveOfflineMsg")
    public Result<Integer> saveOfflineMsg(HttpServletRequest request) {
        Result<Integer> result = new Result<>();
        String ticketId = request.getParameter("ticketId");
        String toEntId = request.getParameter("toEntId");
        String businessCode = request.getParameter("businessCode");
        String context = request.getParameter("context");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        OfflineMessage offlineMessage = newOfflineMessage(user, toEntId, context);
        try {
            offlineMessageService.saveOfflineMessage(offlineMessage, businessCode);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("保存离线信息失败！", e);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    private OfflineMessage newOfflineMessage(User user, String toEntId, String context) {
        OfflineMessage offlineMessage = new OfflineMessage();
        offlineMessage.setFromEntId(user.getEnterpriseId());
        offlineMessage.setFromUserId(user.getUserId());
        offlineMessage.setToEntId(toEntId);
        offlineMessage.setContext(context);
        offlineMessage.setCreateBy(user.getUserId());
        offlineMessage.setCreateTime(Util.datetimeToString(new Date()));
        offlineMessage.setStatus("1");
        return offlineMessage;

    }



}
