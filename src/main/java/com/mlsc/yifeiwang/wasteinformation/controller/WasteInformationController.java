package com.mlsc.yifeiwang.wasteinformation.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.util.DateUtil;
import com.mlsc.common.util.MakeHtml;
import com.mlsc.common.util.StringUtils;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.PropertyUtil;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.wasteinformation.model.WasteInformationModel;
import com.mlsc.yifeiwang.wasteinformation.model.WasteInformationParam;
import com.mlsc.yifeiwang.wasteinformation.service.IWasteInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2018-06-13
 */
@Controller
@RequestMapping("/wasteInformation")
public class WasteInformationController {
    private static final String PATH = "WasteInformation/";
    @Autowired
    private IWasteInformationService wasteInformationService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "list");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "add");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/view")
    public ModelAndView view(WasteInformationParam wasteInformationParam,String dateTime) throws Exception{
        ModelAndView mv = new ModelAndView(PATH + "view");
        WasteInformationModel wasteInformationModel = wasteInformationService.getWasteInformationById(wasteInformationParam);
        mv.addObject("wasteInformation", wasteInformationModel);
        String nowTime=StringUtils.isNullOrEmpty(dateTime)?(new Date().getTime()+""):dateTime;
        mv.addObject("nowTime", nowTime);
        return mv;
    }
    @RequestMapping("/viewForMobile")
    public ModelAndView viewForMobile(WasteInformationParam wasteInformationParam,String dateTime) throws Exception{
        ModelAndView mv = new ModelAndView(PATH + "viewForMobile");
        WasteInformationModel wasteInformationModel = wasteInformationService.getWasteInformationById(wasteInformationParam);
        mv.addObject("wasteInformation", wasteInformationModel);
        String nowTime=StringUtils.isNullOrEmpty(dateTime)?(new Date().getTime()+""):dateTime;
        mv.addObject("nowTime", nowTime);
        return mv;
    }

    /**
     * 保存咨询
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveWasteInformation", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> saveWasteInformation(User user, WasteInformationParam wasteInformationParam) {
        Result<Boolean> result = new Result<>();
        List<String> errors = result.getInfoList();
        try {
            boolean saveResult = wasteInformationService.saveWasteInformation(wasteInformationParam, user, errors);
            result.setData(saveResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 咨询列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listWasteInformation", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Map<String,Object>> listWasteInformation(User user, WasteInformationParam wasteInformationParam, PagingParameter pagingParameter) {
        Result<Map<String,Object>> result = new Result<>();
        Map<String,Object> resultMap = new HashMap<>();
        List<String> errors = result.getInfoList();
        try {
            List<WasteInformationModel> wasteInformationModels = wasteInformationService.listWasteInformation(wasteInformationParam, pagingParameter);
            resultMap.put("wasteInformationModels",wasteInformationModels);
            resultMap.put("pagingParameter",pagingParameter);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 更新危废课堂
     * @param user
     * @param wasteInformationParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateWasteInformation", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> updateWasteInformation(User user, WasteInformationParam wasteInformationParam) {
        Result<Boolean> result = new Result<>();
        try {
            boolean saveResult = wasteInformationService.updateWasteInformation(wasteInformationParam, user);
            result.setData(saveResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 更新点击量
     * @param user
     * @param wasteInformationParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateClickAmount", method = {RequestMethod.GET, RequestMethod.POST})
    public Result updateClickAmount(User user, WasteInformationParam wasteInformationParam) {
        Result result = new Result<>();
        try {
            wasteInformationService.updateClickAmount(wasteInformationParam);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 获取危废小课堂详情
     * @param user
     * @param wasteInformationParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWasteInformationById", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<WasteInformationModel> getWasteInformationById(User user, WasteInformationParam wasteInformationParam) {
        Result<WasteInformationModel> result = new Result<>();
        try {
            WasteInformationModel wasteInformationModel = wasteInformationService.getWasteInformationById(wasteInformationParam);
            result.setData(wasteInformationModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 删除危废小课堂详情
     * @param user
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteWasteInformationById", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> deleteWasteInformationById(User user,  @RequestBody List<String> ids) {
        Result<Boolean> result = new Result<>();
        try {
            Boolean flag = wasteInformationService.deleteWasteInformationById(user,ids);
            result.setData(flag);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 危废小课堂详情重新生成html
     * @param user
     * @param wasteInformationParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/generateStaticHtml", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> generateStaticHtml(User user,  WasteInformationParam wasteInformationParam) {
        Result<Boolean> result = new Result<>();
        try {
            WasteInformationModel wasteInformation = wasteInformationService.getWasteInformationById(wasteInformationParam);
            String htmlName = wasteInformation.getHtmlSrc();
            String tomcatPath = MakeHtml.getTomcatPath();
            String fileDomain = PropertyUtil.getValue("fileDomain");
            MakeHtml.makeHtml(fileDomain + "/swp/wasteInformation/view?id=" + wasteInformation.getId(),
                    tomcatPath + "/ROOT/knowledge/" + htmlName + ".html");
            result.setData(true);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 危废小课堂列表生成js
     * @param user
     * @param wasteInformationParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/generateStaticJS", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> generateStaticJS(User user,  WasteInformationParam wasteInformationParam, PagingParameter pagingParameter) {
        Result<Boolean> result = new Result<>();
        try {
            pagingParameter.setPageSize(0);
            wasteInformationParam.setLatestNews("1");
            List<WasteInformationModel> wasteInformationModels = wasteInformationService.listWasteInformation(wasteInformationParam, pagingParameter);
            JSONArray data=new JSONArray();
            String dateTime=new Date().getTime()+"";
            for(int i=0;i<wasteInformationModels.size();i++){
                WasteInformationModel wasteInformationModel=wasteInformationModels.get(i);
                JSONObject obj = new JSONObject();
                obj.put("title",wasteInformationModel.getTitle());
                obj.put("createTime",DateUtil.convertToString(wasteInformationModel.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                obj.put("htmlSrc",wasteInformationModel.getHtmlSrc());
                obj.put("id",wasteInformationModel.getId());
                data.add(obj);
                WasteInformationParam wasteInformationParam1=new WasteInformationParam();
                wasteInformationParam1.setId(wasteInformationModel.getId());
                WasteInformationModel wasteInformation = wasteInformationService.getWasteInformationById(wasteInformationParam1);
                String htmlName = wasteInformation.getHtmlSrc();
                String tomcatPath = MakeHtml.getTomcatPath();
                String fileDomain = PropertyUtil.getValue("fileDomain");
                MakeHtml.makeHtml(fileDomain + "/swp/wasteInformation/view?id=" + wasteInformation.getId()+"&dateTime="+dateTime,
                        tomcatPath + "/ROOT/knowledge/" + htmlName + ".html");
                MakeHtml.makeHtml(fileDomain + "/swp/wasteInformation/viewForMobile?id=" + wasteInformation.getId()+"&dateTime="+dateTime,
                        tomcatPath + "/ROOT/knowledge/" + htmlName + "-mobile.html");
            }
            Util.generateStaticHtml("var inforList=",data,"/ROOT/inforList.js");
            result.setData(true);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

}
