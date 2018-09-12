package com.mlsc.yifeiwang.operaction.controller;


import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.operaction.model.WebsiteOperationModel;
import com.mlsc.yifeiwang.operaction.model.WebsiteOperationParam;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
@Controller
@RequestMapping("/websiteOperation")
public class WebsiteOperationController {
    private static final String PATH = "WebsiteOperation/";
    private final static Logger logger = LoggerFactory.getLogger(WebsiteOperationController.class);

    @Autowired
    private IWebsiteOperationService websiteOperationService;


    @RequestMapping("/operationList")
    public ModelAndView operationList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "operationList");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/addOperation")
    public ModelAndView addOperation(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "addOperation");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/initOutSouringWasteEnterprise", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<SysEnterpriseBase>> initOutSouringWasteEnterprise(String entName) {

        Result<List<SysEnterpriseBase>> result = new Result<List<SysEnterpriseBase>>();
        try {
            if (StringUtils.isNotBlank(entName)) {
                List<SysEnterpriseBase> enterpriseUserList = websiteOperationService.initOutSouringWasteEnterprise(entName);
                result.setData(enterpriseUserList);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("获取处置企业时异常", e);

        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/listWasteEnterprise", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Map<String, Object>> listWasteEnterprise(String type, PagingParameter pagingParameter, @RequestBody WebsiteOperationParam websiteOperationParam) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<SysEnterpriseBase> enterpriseUserList = null;
        try {
            enterpriseUserList = websiteOperationService.listWasteEnterpriseList(type, pagingParameter, websiteOperationParam);
            resultMap.put("enterList", enterpriseUserList);
            resultMap.put("pagingParameter", pagingParameter);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("获取产废企业列表时异常", e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateWebSiteOperationInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<String> saveOrUpdateWebSiteOperationInfo(User user, String type, @RequestBody WebsiteOperationParam websiteOperationParam) {
        Result<String> result = new Result<String>();
        try {
            List<SysEnterpriseBase> enterpriseUserList = websiteOperationService.listWasteEnterpriseList(type, null, websiteOperationParam);
            if (enterpriseUserList != null && enterpriseUserList.size() > 0) {
                String operactionId = websiteOperationService.saveOrUpdateWebSiteOperationInfo(user, websiteOperationParam, enterpriseUserList);
                result.setData(operactionId);
                result.setStatus(ResultStatus.Success);
            }else{
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("保存运营信息时异常", e);
        }
        return result;
    }

    /**
     * 创建运营-第三步保存
     *
     * @param user
     * @param type
     * @param websiteOperationParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateWebSiteOperationInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> updateWebSiteOperationInfo(User user, String type, @RequestBody WebsiteOperationParam websiteOperationParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean updateResult = websiteOperationService.updateWebSiteOperationInfo(user, websiteOperationParam);
            result.setData(updateResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("保存运营信息时异常", e);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/startWebSiteOperationInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> startWebSiteOperationInfo(User user, @RequestBody List<String> operationIds) {

        Result<Boolean> result = new Result<Boolean>();
        try {
            if(operationIds!=null && operationIds.size()>0){
                result.setData(websiteOperationService.startWebSiteOperationInfo(user, operationIds));
                result.setStatus(ResultStatus.Success);
            }else{
                result.setStatus(ResultStatus.Error);
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("启动运营信息时异常", e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/stopWebSiteOperationInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> stopWebSiteOperationInfo(User user, @RequestBody List<String> operationIds) {

        Result<Boolean> result = new Result<Boolean>();
        try {
            if (operationIds != null && operationIds.size() > 0) {
                boolean stopResult = websiteOperationService.stopWebSiteOperationInfo(user, operationIds);
                result.setData(stopResult);
                result.setStatus(ResultStatus.Success);
            }else{
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("停止运营信息时异常", e);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/deleteWebSiteOperationInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> deleteWebSiteOperationInfo(User user, @RequestBody List<String> operationIds) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            if (operationIds != null && operationIds.size() > 0) {
                boolean deleteResult = websiteOperationService.deleteWebSiteOperationInfo(user, operationIds);
                result.setData(deleteResult);
                result.setStatus(ResultStatus.Success);
            }else{
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("停止运营信息时异常", e);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/listWebSiteOperationInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Map<String, Object>> listWebSiteOperationInfo(PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<WebsiteOperationModel> websiteOperationList = null;
        try {
            websiteOperationList = websiteOperationService.listWebSiteOperationInfo(pagingParameter);
            resultMap.put("websiteOperationList", websiteOperationList);
            resultMap.put("pagingParameter", pagingParameter);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("获取运营列表时异常", e);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/getWebSiteOperationInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<WebsiteOperationModel> getWebSiteOperationInfo(String id) {
        Result<WebsiteOperationModel> result = new Result<WebsiteOperationModel>();
        try {
            WebsiteOperationModel operationModel = websiteOperationService.getWebSiteOperationInfo(id);
            result.setData(operationModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("查看运营信息时异常", e);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/listEntWasteByEntId", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<EntWasteModel>> listEntWasteByEntId(String entId) {
        Result<List<EntWasteModel>> result = new Result<List<EntWasteModel>>();
        try {
            List<EntWasteModel> entWastes = websiteOperationService.listEntWasteByEntId(entId);
            result.setData(entWastes);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("查看企业危废信息时异常", e);
        }
        return result;
    }
}
