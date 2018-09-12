package com.mlsc.waste.wastecircle.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.wastecircle.model.FollowEnterpriseVo;
import com.mlsc.waste.wastecircle.service.CooperationRelationService;
import org.apache.solr.common.StringUtils;
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

@Controller
@RequestMapping("/follow")
@Scope("prototype")
public class FollowController {
    private static final String PATH = "Follow/";
    
    @Autowired
    private CooperationRelationService followService;
    
    @Autowired
    private EnterpriseService enterpriseService;
    
    @Autowired
    private ICodeValueService codeValueService;

    /**
     * 初始化我的关注
     * 
     * @param request
     * @return
     */
    @RequestMapping("/myFollow")
    public ModelAndView myFollow(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "list");
        String ticketId = request.getParameter("ticketId");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String entType = user.getEntType();
        if(StringUtils.isEmpty(entType)){
            mv = new ModelAndView();
            mv.setViewName("redirect:/" + "myenterprise/bindEnterprise.htm");
        }else{
            if (Constant.ENTERPRISE_TYPE_PRODCITION.equalsIgnoreCase(entType)) {//产废企业
                mv = new ModelAndView(PATH + "czList");
            }else if(Constant.ENTERPRISE_TYPE_DIS_FACILITATOR.equals(entType)){//处置代理商
                mv = new ModelAndView(PATH + "cfList_facilitator");
            } else {//处置企业
                mv = new ModelAndView(PATH + "cfList");
            }
            try {
                boolean result = enterpriseService.isEnterpriseVaild(ticketId);
                mv.addObject("entIsValid", result);
            } catch (Exception e) {
                mv.setViewName(Constant.URL_500);
            }
        }



        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }
    
    /**
     * 加载我关注企业的信息
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/initFollowData")
    public Result<Map<String, Object>> initFollowData(PagingParameter pagingParameter,HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        List<FollowEnterpriseVo> followEnterpriseList = null;
        try {
            // 初始化页面参数
            initPagingParameter(pagingParameter);
            followEnterpriseList = followService.getFollowEnterprises(user.getUserId(), pagingParameter);
            pagingParameter.setTotalRecord(followService.getFollowEnterprisesCount(user.getUserId()));
            dataMap.put("followData", JSONArray.toJSON(followEnterpriseList));
            dataMap.put("paging", JSONArray.toJSON(pagingParameter));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }
    
    /**
     * 取消关注
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/removeFollow")
    public Result<Map<String, String>> removeFollow(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String cooperationRelationId = request.getParameter("followId");
        try {
            followService.removeCooperationRelation(cooperationRelationId);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        
        result.setData(dataMap);
        return result;
    }
    
    private void initPagingParameter(PagingParameter pagingParameter){
        pagingParameter.setPageSize(9);
        if (pagingParameter.getPageIndex() == 0) {
            pagingParameter.setPageIndex(1);
        }
        
        int start = (pagingParameter.getPageIndex() -1) * pagingParameter.getPageSize();
        pagingParameter.setStart(start);
        pagingParameter.setLimit(9);
    }
    
    @ResponseBody
    @RequestMapping("/getMyFollowCount")
    public Result<Map<String, Object>> getMyPublishCount(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            dataMap.put("myFollowCount", followService.getFollowEnterprisesCount(user.getUserId()));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/getFollowRealtion")
    public Result<Map<String, Object>> getFollow(HttpServletRequest request){
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String,Object>();
        String ticketId = request.getParameter("ticketId");
        String followId = request.getParameter("followId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            CodeValue followType = codeValueService.getCodeValueByCode(CodeTypeConstant.FOLLOW_TYPE, CodeTypeConstant.FOLLOW_TYPE_ORGANIZED);
            boolean followRealtion = followService.isFollowed(user.getUserId(), followId, followType.getId());
            dataMap.put("followRealtion",  followRealtion);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }
}
