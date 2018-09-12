package com.mlsc.yifeiwang.wasterealase.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import com.mlsc.yifeiwang.wasterealase.model.ReleaseStatusModel;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseService;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailParam;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
@Controller("entReleaseController")
@RequestMapping("/entRelease")
@Scope("prototype")
public class EntReleaseController {
    private static final String PATH = "DispositionPlanRelease/";

    @Autowired
    private ICodeTypeService codeTypeService;

    @Autowired
    private IEntReleaseService entReleaseService;

    @Autowired
    private IUserInfoService userInfoService;


    @RequestMapping("/entWasteList")
    public ModelAndView entWasteList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "entWasteList");
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        mv.addObject("ticketId", ticketId);
        mv.addObject("enterpriseId", user.getEnterpriseId());
        return mv;
    }


    @RequestMapping("/entWasteList4gf")
    public ModelAndView entWasteList4gf(HttpServletRequest request, String phoneNo) {
        ModelAndView mv = new ModelAndView(PATH + "entWasteList");


        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        mv.addObject("ticketId", ticketId);

        mv.addObject("enterpriseId", user.getEnterpriseId());
        return mv;
    }


    @RequestMapping("/publish")
    public ModelAndView publish(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "publish");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/cfBuy")
    public ModelAndView cfBuy(HttpServletRequest request, String breadcrumbName) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "cfBuy");
        mv.addObject("unitList", codeTypeService.getCodeValuesTypeCode(CodeTypeConstant.UNIT_TYPE));
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("breadcrumbName", breadcrumbName);
        return mv;
    }

    @RequestMapping("/cfBuy_facilitator")
    public ModelAndView cfBuy_facilitator(HttpServletRequest request, String breadcrumbName) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "cfBuy_facilitator");
        mv.addObject("unitList", codeTypeService.getCodeValuesTypeCode(CodeTypeConstant.UNIT_TYPE));
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("breadcrumbName", breadcrumbName);
        return mv;
    }

    @RequestMapping("/systemInquiry")
    public ModelAndView systemInquiry(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "systemInquiry");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("releaseId", request.getParameter("releaseId"));
        mv.addObject("informEntCount", request.getParameter("informEntCount"));
        return mv;
    }

    /**
     * 产废企业发布危废资源
     *
     * @param user
     * @param entReleaseParams
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result<Map<String, Object>> saveEntRelease(User user, @RequestBody EntReleaseParam entReleaseParams) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<String> info = new ArrayList<String>();
        try {
            boolean corrected = validateEntRelease(entReleaseParams, info);
            if (corrected) {
                entReleaseService.saveEntRelease(user, entReleaseParams);
                result.setData(resultMap);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setStatus(ResultStatus.Error);
                result.setInfoList(info);
            }
        } catch (Exception e) {
            info.add("发布企业危废错误");
            result.setStatus(ResultStatus.Error);
            result.setInfoList(info);
        }
        return result;
    }

    /**
     * 产废企业发布危废资源
     *
     * @param entReleaseParams
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveEntReleaseFromUpStream", method = RequestMethod.POST)
    public Result<Map<String, Object>> saveEntReleaseFromUpStream(@RequestBody EntReleaseParam entReleaseParams) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<String> info = new ArrayList<String>();
        try {
            boolean corrected = validateEntRelease(entReleaseParams, info);
            if (corrected) {
                result.setData(resultMap);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setStatus(ResultStatus.Error);
                result.setInfoList(info);
            }
        } catch (Exception e) {
            info.add("发布企业危废错误");
            result.setStatus(ResultStatus.Error);
            result.setInfoList(info);
        }
        return result;
    }

    /**
     * 产废企业发布并保存危废
     *
     * @param user
     * @param entReleaseParams
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveAndPublish", method = RequestMethod.POST)
    public Result<Map<String, Object>> saveWasteAndPublishEntRelease(User user, @RequestBody EntReleaseParam entReleaseParams) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<String> info = new ArrayList<String>();
        try {
            boolean corrected = validateEntRelease(entReleaseParams, info);
            if (corrected) {
                entReleaseService.saveEntWaste(user, entReleaseParams);
//                EntReleaseModel entRelaseModel = entReleaseService.generateReleasePoster(user.getUserId());
//                resultMap.put("informEnterList", informEnterList);
//                resultMap.put("entRelaseModel", entRelaseModel);
//                EntInquiryModel entInquiryModel =  entReleaseService.getReferencePrice(releaseId);
//                resultMap.put("entInquiryModel", entInquiryModel);
                EntReleaseModel entReleaseModel = entReleaseService.saveEntRelease(user, entReleaseParams);
                resultMap.put("entReleaseModel", entReleaseModel);
                result.setData(resultMap);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setStatus(ResultStatus.Error);
                result.setInfoList(info);
            }
        } catch (Exception e) {
            info.add("发布企业危废错误");
            result.setStatus(ResultStatus.Error);
            result.setInfoList(info);
        }
        return result;
    }


    private boolean validateEntRelease(EntReleaseParam entReleaseParams, List<String> info) {
        if (entReleaseParams == null) {
            info.add("发布信息不能为空");
            return false;
        }
        if (entReleaseParams != null && entReleaseParams.getReleaseDetail().size() == 0) {
            info.add("发布危废信息不能为空");
            return false;
        }
        List<EntReleaseDetailParam> releaseDetails = entReleaseParams.getReleaseDetail();
        for (EntReleaseDetailParam item : releaseDetails) {
            if (StringUtils.isBlank(item.getEntWasteId()) && StringUtils.isBlank(item.getWasteId())) {
                info.add("发布危废信息不能为空");
            } else if (item.getReleaseAmount() == 0) {
                info.add("发布危废数量不能为空");
            }
        }
        return info.size() == 0;
    }

    /**
     * 处置企业资源池列表
     *
     * @param user
     * @param releaseParam
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listWasteEntRelease")
    public Result<List<EntReleaseModel>> listWasteEntRelease(User user, @RequestBody EntReleaseParam releaseParam, PagingParameter pagingParameter) {
        Result<List<EntReleaseModel>> result = new Result<List<EntReleaseModel>>();
        try {
            List<EntReleaseModel> inquiryList = entReleaseService.listWasteEntRelease(user, releaseParam, pagingParameter);
            result.setData(inquiryList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业资源池列表
     *
     * @param user
     * @param releaseParam
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listWasteEntRelease4FacilitatorEnt")
    public Result<List<EntReleaseModel>> listWasteEntRelease4facilitatorEnt(User user, @RequestBody EntReleaseParam releaseParam, PagingParameter pagingParameter) {
        Result<List<EntReleaseModel>> result = new Result<List<EntReleaseModel>>();
        try {
            releaseParam.setCurrentEntId(user.getEnterpriseId());
            List<EntReleaseModel> inquiryList = entReleaseService.listWasteEntRelease4FacilitatorEnt(releaseParam, pagingParameter);
            result.setData(inquiryList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 产废企业-我的发布
     *
     * @param user
     * @param releaseParam
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listWasteEntReleaseByEntId")
    public Result<Map<String, Object>> listWasteEntReleaseByEntId(User user, @RequestBody EntReleaseParam releaseParam, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<EntReleaseModel> wasteEntRelaseList = entReleaseService.listWasteEntReleaseByEnterId(user, releaseParam, pagingParameter);
            dataMap.put("wasteEntRelaseList", JSONArray.toJSON(wasteEntRelaseList));
            dataMap.put("paging", JSONArray.toJSON(pagingParameter));
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 处置企业查看产废企业申请参加活动列表
     *
     * @param user
     * @param releaseParam
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listWasteEntReleaseByActivityId")
    public Result<Map<String, Object>> listWasteEntReleaseByActivityId(User user, @RequestBody EntReleaseParam releaseParam, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            releaseParam.setCurrentEntId(user.getEnterpriseId());
            List<EntReleaseModel> activityList = entReleaseService.listWasteEntReleaseByActivityId(releaseParam, pagingParameter);
            dataMap.put("activityList", activityList);
            dataMap.put("paging", pagingParameter);
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 统计处置企业活动状态
     *
     * @param activityId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "countActivityStatusByActivityId")
    public Result<List<ReleaseStatusModel>> countActivityStatusByActivityId(String activityId) {
        Result<List<ReleaseStatusModel>> result = new Result<List<ReleaseStatusModel>>();
        try {
            if (StringUtils.isNotEmpty(activityId)) {
                List<ReleaseStatusModel> activityList = entReleaseService.countActivityStatusByActivityId(activityId);
                result.setData(activityList);
                result.setStatus(ResultStatus.Success);
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业拒绝产废企业申请参加活动
     *
     * @param user
     * @param releaseParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "refusedWasteEntApply")
    public Result<Boolean> refusedWasteEntApply(User user, @RequestBody EntReleaseParam releaseParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean refusedResult = entReleaseService.refusedWasteEntApply(releaseParam, user);
            result.setData(refusedResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setData(false);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 删除产废企业发布产废资源
     *
     * @param user
     * @param releaseParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteWasteEntReleaseByReleaseId")
    public Result<Boolean> deleteWasteEntReleaseByReleaseId(User user, @RequestBody EntReleaseParam releaseParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            if (validateReleaseParam(releaseParam, result)) {
                boolean deleteResult = entReleaseService.deleteWasteEntReleaseByReleaseId(user, releaseParam);
                result.setData(deleteResult);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setData(false);
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setData(false);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 产废企业完结发布产废资源
     *
     * @param user
     * @param releaseParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "endWasteEntReleaseByReleaseId")
    public Result<Boolean> endWasteEntReleaseByReleaseId(User user, @RequestBody EntReleaseParam releaseParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            if (validateReleaseParam(releaseParam, result)) {
                boolean endResult = entReleaseService.endWasteEntReleaseByReleaseId(user, releaseParam);
                result.setData(endResult);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setData(false);
                result.setStatus(ResultStatus.Error);
            }
        } catch (Exception e) {
            result.setData(false);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    private boolean validateReleaseParam(EntReleaseParam releaseParam, Result<Boolean> result) {
        if (StringUtils.isBlank(releaseParam.getReleaseId())) {
            result.getInfoList().add("发布编号为空");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 产废企业发布产废资源后生成海报，分享朋友圈
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "generateReleasePoster", method = RequestMethod.POST)
    public Result<EntReleaseModel> generateReleasePoster(String userId) {
        Result<EntReleaseModel> result = new Result<EntReleaseModel>();
        List<String> info = new ArrayList<String>();
        try {
            EntReleaseModel entRelaseModel = entReleaseService.generateReleasePoster(userId);
            result.setData(entRelaseModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            info.add("生成发布海报时错误");
            result.setStatus(ResultStatus.Error);
            result.setInfoList(info);
        }
        return result;
    }

    /**
     * 手机浏览器端用户需要帮助
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sendHelpMsg", method = RequestMethod.POST)
    public Result<Boolean> sendHelpMsg(User user) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            entReleaseService.sendHelpMsg(user);
            result.setData(true);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

}
