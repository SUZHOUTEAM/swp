package com.mlsc.yifeiwang.activity.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.mlsc.common.util.DateUtil;
import com.mlsc.common.util.StringUtils;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.activity.model.ActivityQueryParam;
import com.mlsc.yifeiwang.activity.model.EnterpriseWasteVo;
import com.mlsc.yifeiwang.activity.model.WasteActivityDetailVO;
import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import com.mlsc.yifeiwang.activity.service.IWasteActivityService;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.activity.entity.WasteActivity;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yxl
 * @since 2017-07-31
 */
@Controller
@RequestMapping("/wasteActivity")
public class WasteActivityController {
    private final static Logger logger = LoggerFactory.getLogger(WasteActivityController.class);
    private static final String PATH = "Activity/";

    @Autowired
    private IWasteActivityService wasteActivityService;


    @Autowired
    private ICodeValueService codeValueService;


    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "list");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/buyList")
    public ModelAndView buyList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "buyList");
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("entType", user.getEntType());
        return mv;
    }

    @RequestMapping("/enterList")
    public ModelAndView enterList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "enterList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/raiseList")
    public ModelAndView raiseList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "raiseList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "add");
        String activityId = request.getParameter("activityId");
        mv.addObject("activityId", StringUtils.isNullOrEmpty(activityId) ? "" : activityId);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "show");
        String activityId = request.getParameter("activityId");
        mv.addObject("activityId", StringUtils.isNullOrEmpty(activityId) ? "" : activityId);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/buy")
    public ModelAndView buy(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "buy");
        String activityId = request.getParameter("activityId");
        mv.addObject("activityId", StringUtils.isNullOrEmpty(activityId) ? "" : activityId);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/myActivity")
    public ModelAndView myActivity(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "myActivity");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/addActivity")
    public ModelAndView addActivity(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "addActivity");
        String activityId = request.getParameter("activityId");
        mv.addObject("activityId", StringUtils.isNullOrEmpty(activityId) ? "" : activityId);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/releaseBonusList")
    public ModelAndView releaseBonusList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "releaseBonusList");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getActivityDetailById")
    public Result<Map<String, Object>> getActivityDetailById(ActivityQueryParam param, HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String msg = null;
        try {
            List<WasteActivityDetailVO> activityDetailList = wasteActivityService.getActivityDetailById(param);
            dataMap.put("activityDetailList", activityDetailList);
            if (activityDetailList.size() == 0) {
                msg = "没有查到活动相关数据";
            }
            dataMap.put("serverTime", DateUtil.convertToString(DateTime.now()));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("getActivityDetailById", e);
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/save")
    public Result<String> save(@RequestBody WasteActivityVO wasteActivity, HttpServletRequest request) {
        Result<String> result = new Result<String>();
        String ticketId = request.getParameter("ticketId");
        try {
            WasteActivity wa = wasteActivityService.save(ticketId, wasteActivity);
            if (wa != null) {
                result.setData(wa.getId());
            }
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("save", e);
            List<String> msg = new ArrayList<>();
            msg.add(Constant.SYS_MSG);
            result.setInfoList(msg);
            result.setStatus(ResultStatus.Failure);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getCurrentActivity")
    public Result<Map<String, Object>> getCurrentActivity(HttpServletRequest request, String cantonCode) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String msg = null;
        try {
            List<WasteActivityVO> activity = wasteActivityService.getCurrentActivity(cantonCode);
            dataMap.put("activityList", activity);
            dataMap.put("serverTime", DateUtil.convertToString(DateTime.now()));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("getCurrentActivity", e);
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }

        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateToHome")
    public Result<Boolean> updateToHome(String[] ids, HttpServletRequest request) {
        Result<Boolean> result = new Result<>();
        JSONObject data = new JSONObject();
        String[] cantonCodes={"32","13"};
        try {
            for(int i=0;i<cantonCodes.length;i++){
                String cantonCode=cantonCodes[i];
                List<WasteActivityVO> activity = wasteActivityService.getCurrentActivity(cantonCode);
                data.put(cantonCode,activity);
            }
            Util.generateStaticHtml("var activitys=",data,"/ROOT/home_activityList.js");
            result.setData(true);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getWasteActivityById")
    public Result<Map<String, Object>> getWasteActivityById(ActivityQueryParam param, HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String msg = null;
        try {
            WasteActivityVO activity = wasteActivityService.getWasteActivityVoById(param);
            dataMap.put("activity", activity);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("getWasteActivityById", e);
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }

        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/listActivity")
    public Result<Map<String, Object>> listActivity(Page<WasteActivity> page, HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<>();
        Map<String, Object> dataMap = new HashMap<>();
        String msg = null;
        try {

            Page<WasteActivity> activityPage = wasteActivityService.listWasteActivity(page);
            dataMap.put("list", activityPage);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("listActivity", e);
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }

        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result<Map<String, Object>> delete(String[] ids, HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<>();
        Map<String, Object> dataMap = new HashMap<>();
        String msg = null;
        try {
            wasteActivityService.deleteLogicBatchIds(Arrays.asList(ids));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("delete", e);
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/stopActivity")
    public Result<Map<String, Object>> stopActivity(String ticketId, String[] ids, String status) {
        Result<Map<String, Object>> result = new Result<>();
        Map<String, Object> dataMap = new HashMap<>();
        String msg = null;
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            if (ids != null && ids.length > 0) {
                List<String> idList = Arrays.asList(ids);
                wasteActivityService.stopActivity(idList, user, status);
            }
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("delete", e);
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateActivityCoverEntCount")
    public Result updateActivityCoverEntCount(String ticketId, String id) {
        Result result = new Result();
        Map<String, Object> dataMap = new HashMap<>();
        List<String> info = new ArrayList<String>();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            wasteActivityService.updateActivityCoverEntCount(user, id);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("delete", e);
            info.add(Constant.SYS_MSG);
            result.setStatus(ResultStatus.Failure);
        }
        result.setInfoList(info);
        return result;
    }


    @ResponseBody
    @RequestMapping("/publish")
    public Result<Map<String, Object>> publish(String[] ids, HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<>();
        Map<String, Object> dataMap = new HashMap<>();
        String msg = null;
        try {
            String ticketId = request.getParameter("ticketId");
            wasteActivityService.publishByBatchIds(ticketId, Arrays.asList(ids));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("publish", e);
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }


    @ResponseBody
    @RequestMapping("/getAllAcitiveByEnterId")
    public Map<String, Object> getAllAcitiveByEnterId(HttpServletRequest request) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String keyword = request.getParameter("keyword");
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            ActivityQueryParam activityVo = new ActivityQueryParam();
            activityVo.setEntId(user.getEnterpriseId());
            activityVo.setActivityName(keyword);
            List<WasteActivityVO> activityList = wasteActivityService.listWasteActivity(activityVo);
            dataMap.put("value", JSONArray.toJSON(activityList));
        } catch (Exception e) {
            logger.error("获取参加活动时异常", e);
        }
        return dataMap;
    }


    @ResponseBody
    @RequestMapping("/listAcitivityByEnterId")
    public Result<Map<String, Object>> listAcitivityByEnterId(String ticketId, PagingParameter pagingParameter, @RequestBody ActivityQueryParam activityVo) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            activityVo.setEntId(user.getEnterpriseId());
            List<WasteActivityVO> activityList = wasteActivityService.listWasteActivityByEntId(activityVo, pagingParameter);
            dataMap.put("list", JSONArray.toJSON(activityList));
            dataMap.put("paging", pagingParameter);
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("获取企业活动列表时异常", e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listOrderActiviyNameByApplyEntId")
    public Result<List<WasteActivityVO>> listOrderActiviyNameByApplyEntId(String ticketId) {
        Result<List<WasteActivityVO>> result = new Result<List<WasteActivityVO>>();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            List<WasteActivityVO> activityList = wasteActivityService.listOrderActiviyNameByApplyEntId(user.getEnterpriseId());
            result.setData(activityList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("参加活动名称时异常", e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listActiviyNameByInquiryEntId")
    public Result<List<WasteActivityVO>> listActiviyNameByInquiryEntId(String ticketId) {
        Result<List<WasteActivityVO>> result = new Result<List<WasteActivityVO>>();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            List<WasteActivityVO> activityList = wasteActivityService.listActiviyNameByInquiryEntId(user.getEnterpriseId());
            result.setData(activityList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("处置企业询价活动时异常", e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listActiviyNameByApplyEntId")
    public Result<List<WasteActivityVO>> listActiviyNameByApplyEntId(String ticketId) {
        Result<List<WasteActivityVO>> result = new Result<List<WasteActivityVO>>();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            List<WasteActivityVO> activityList = wasteActivityService.listActiviyNameByApplyEntId(user.getEnterpriseId());
            result.setData(activityList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("产废企业参加活动时异常", e);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/listOrderActiviyName")
    public Result<List<WasteActivityVO>> listOrderActiviyName(String ticketId) {
        Result<List<WasteActivityVO>> result = new Result<List<WasteActivityVO>>();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            List<WasteActivityVO> activityList = wasteActivityService.listOrderActiviyName(user.getEnterpriseId());
            result.setData(activityList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("参加活动名称时异常", e);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/listAcitiveByShoppingCardId")
    public Map<String, Object> getAllAcitiveByEnterIdByShoppingCarId(HttpServletRequest request) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String keyword = request.getParameter("keyword");
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            ActivityQueryParam activityVo = new ActivityQueryParam();
            activityVo.setEntId(user.getEnterpriseId());
            activityVo.setActivityName(keyword);
            List<WasteActivityVO> activityList = wasteActivityService.listAcitiveByShoppingCardId(activityVo);
            dataMap.put("value", JSONArray.toJSON(activityList));
        } catch (Exception e) {
            logger.error("获取参加活动时异常", e);
        }

        return dataMap;
    }


    @ResponseBody
    @RequestMapping("/listAcitiveByMySelled")
    public Map<String, Object> getAllAcitiveByMySelled(HttpServletRequest request) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String keyword = request.getParameter("keyword");
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            ActivityQueryParam activityVo = new ActivityQueryParam();
            activityVo.setEntId(user.getEnterpriseId());
            activityVo.setActivityName(keyword);
            List<WasteActivityVO> activityList = wasteActivityService.listAcitiveByOrderId(activityVo);
            dataMap.put("value", JSONArray.toJSON(activityList));
        } catch (Exception e) {
            logger.error("获取参加活动时异常", e);
        }

        return dataMap;
    }

    @ResponseBody
    @RequestMapping("/listActivityByPage")
    public JSONObject listActivityByPage(Page<WasteActivity> page, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {

            wasteActivityService.selectPage(page);
            jsonObject.put("list", page.getRecords());
            jsonObject.put("total", page.getTotal());

        } catch (Exception e) {
            logger.error("获取参加活动时异常", e);
        }

        return jsonObject;
    }

    @ResponseBody
    @RequestMapping("/listAcitiveByResponseEnter")
    public Map<String, Object> listAcitiveByResponseEnter(HttpServletRequest request) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String keyword = request.getParameter("keyword");
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            ActivityQueryParam activityVo = new ActivityQueryParam();
            activityVo.setResponseEnterId(user.getEnterpriseId());
            activityVo.setActivityName(keyword);
            List<WasteActivityVO> activityList = wasteActivityService.listAcitiveByOrderId(activityVo);
            dataMap.put("value", JSONArray.toJSON(activityList));
        } catch (Exception e) {
            logger.error("获取参加活动时异常", e);
        }

        return dataMap;
    }


    @ResponseBody
    @RequestMapping("/listCapacityreleaseByActivityId")
    public Result<Map<String, Object>> listCapacityreleaseByActivityId(ActivityQueryParam param, HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String msg = null;
        try {
            String ticketId = param.getTicketId();
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            //活动基本信息
            List<WasteActivityDetailVO> activityInfo = wasteActivityService.getActivityDetailById(param);
            dataMap.put("activityInfo", activityInfo);
            //非产废企业不可以参加活动
            List<CodeValue> enterpriseTypes = codeValueService.getEnterpriseTypesByEntId(user.getEnterpriseId());
            if(enterpriseTypes != null && enterpriseTypes.size() > 0 ){
                String entType = enterpriseTypes.get(0).getCode();
                if (!CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equalsIgnoreCase(entType)) {
                    dataMap.put("enterTypeCode", entType);
                    dataMap.put("msg", "非产废企业不可以参加活动");
                    result.setData(dataMap);
                    result.setStatus(ResultStatus.Success);
                    return result;
                }
            }

            //判断产废企业是否在该活动区域
            String cantonCodes = activityInfo.get(0).getCantonCode();
            Integer isInActivityCanton = wasteActivityService.isInActivityCanton(cantonCodes, user);
            dataMap.put("isInActivityCanton", isInActivityCanton);
            //如果产废企业在活动区域内，继续查询 危废名称+购买的次数
            if (isInActivityCanton > 0) {
//                List<WasteActivityDetailVO> activityDetailListByEnt = wasteActivityService.listCapacityreleaseByActivityId(user,param);
//                dataMap.put("activityDetailListByEnt", activityDetailListByEnt);
//                Integer count = wasteActivityService.getWasteCountByWasteCode(activityDetailListByEnt);
//                dataMap.put("count", count);
            }
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("listCapacityreleaseByActivityId", e);
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/listEnterpriseWasteByWasteId")
    public Result<Map<String, Object>> listEnterpriseWasteByWasteId(ActivityQueryParam param, HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String msg = null;
        try {
            List<EnterpriseWasteVo> enterpriseWastelist = wasteActivityService.listEnterpriseWasteByWasteId(param);
            dataMap.put("enterpriseWastelist", enterpriseWastelist);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.info("listEnterpriseWasteByWasteId", e);
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }


    @ResponseBody
    @RequestMapping("/listHomePageWasteActivity")
    public Result<Map<String, Object>> listHomePageWasteActivity(PagingParameter pagingParameter, @RequestBody ActivityQueryParam activityVo) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<WasteActivityVO> activityList = wasteActivityService.listHomePageWasteActivity(activityVo, pagingParameter);
            dataMap.put("serverTime", DateUtil.convertToString(DateTime.now()));
            dataMap.put("list", JSONArray.toJSON(activityList));
            dataMap.put("paging", pagingParameter);
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("首页获取企业活动列表时异常", e);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/listLiveWasteActivity")
    public Result<Map<String, Object>> listLiveWasteActivity( @RequestBody ActivityQueryParam activityVo) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<WasteActivityVO> activityList = wasteActivityService.listLiveWasteActivity(activityVo);
            dataMap.put("serverTime", DateUtil.convertToString(DateTime.now()));
            dataMap.put("list", JSONArray.toJSON(activityList));
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("首页获取企业活动列表时异常", e);
        }
        return result;
    }

}
