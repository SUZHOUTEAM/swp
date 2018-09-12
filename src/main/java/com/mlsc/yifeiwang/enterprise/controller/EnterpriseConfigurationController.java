package com.mlsc.yifeiwang.enterprise.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.yifeiwang.enterprise.entity.EnterpriseConfiguration;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.enterprise.mapper.EnterpriseConfigurationModel;
import com.mlsc.yifeiwang.enterprise.mapper.EnterpriseConfigurationParam;
import com.mlsc.yifeiwang.enterprise.service.IEnterpriseConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * @author caoyy
 * @since 2017-12-25
 */
@Controller
@RequestMapping("/enterpriseConfiguration")
public class EnterpriseConfigurationController {
    @Autowired
    private IEnterpriseConfigurationService configurationService;

    /**
     * 系统管理员保存版面企业配置信息
     *
     * @param user
     * @param enterpriseConfiguration
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveEnterpriseConfiguration")
    public Result<Boolean> saveEnterpriseConfiguration(User user, EnterpriseConfiguration enterpriseConfiguration) {
        Result<Boolean> result = new Result<Boolean>();
        List<String> errorList = new ArrayList<String>();
        try {
            boolean validate = configurationService.validateEnterpriseConfiguration(enterpriseConfiguration, errorList);
            if (validate) {
                boolean flag = configurationService.saveEnterpriseConfiguration(user, enterpriseConfiguration);
                result.setData(flag);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setInfoList(errorList);
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 系统管理员更新首页版面企业配置信息
     *
     * @param user
     * @param enterpriseConfiguration
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateEnterpriseConfiguration")
    public Result<Boolean> updateEnterpriseConfiguration(User user, EnterpriseConfiguration enterpriseConfiguration) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean hasCustomer = configurationService.updateEnterpriseConfiguration(user, enterpriseConfiguration);
            result.setData(hasCustomer);
            result.setStatus(ResultStatus.Success);

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 系统管理员删除首页企业配置信息
     *
     * @param user
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteEnterpriseConfiguration")
    public Result<Boolean> deleteEnterpriseConfiguration(User user, @RequestBody List<String> ids) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            if (ids != null && ids.size() > 0) {
                boolean hasCustomer = configurationService.deleteEnterpriseConfiguration(user, ids);
                result.setData(hasCustomer);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setStatus(ResultStatus.Error);
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 首页-根据不同的版面显示企业列表
     *
     * @param user
     * @param configurationParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listEnterpriseInfoBySection")
    public Result<Map<String, Object>> listEnterpriseInfoBySection(User user, @RequestBody EnterpriseConfigurationParam configurationParam) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<EnterpriseConfigurationModel> enterpriseModelList = configurationService.listEnterpriseInfoBySection(user, configurationParam);
            int maxPage = configurationService.getMaxPageBySection(configurationParam);
            resultMap.put("enterpriseModelList", enterpriseModelList);
            resultMap.put("maxPage", maxPage);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 首页-根据所有版面显示企业列表
     *
     * @param user
     * @param configurationParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listAllEnterpriseInfoBySection")
    public Result<Map<String, Object>> listAllEnterpriseInfoBySection(User user, @RequestBody EnterpriseConfigurationParam configurationParam) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            configurationParam.setSection("DISPOSITION");
            List<EnterpriseConfigurationModel> enterpriseModelList = configurationService.listEnterpriseInfoBySection(user, configurationParam);
            resultMap.put("dispositionEnterpriseModelList", enterpriseModelList);
            int maxPage = configurationService.getMaxPageBySection(configurationParam);
            resultMap.put("dispositionMaxPage", maxPage);


            configurationParam.setSection("RECYCLING");
            enterpriseModelList = configurationService.listEnterpriseInfoBySection(user, configurationParam);
            maxPage = configurationService.getMaxPageBySection(configurationParam);
            resultMap.put("recyclingEnterpriseModelList", enterpriseModelList);
            resultMap.put("recyclingMaxPage", maxPage);

            configurationParam.setSection("SPECIALCATEGORY");
            enterpriseModelList = configurationService.listEnterpriseInfoBySection(user, configurationParam);
            resultMap.put("specialCategoryEnterpriseModelList", enterpriseModelList);
            maxPage = configurationService.getMaxPageBySection(configurationParam);
            resultMap.put("specialCategoryMaxPage", maxPage);

            resultMap.put("enterpriseModelList", enterpriseModelList);
            resultMap.put("maxPage", maxPage);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 首页配置数据更新到首页
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateToHome", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> makeHomeData(HttpServletRequest request){
        Result<Boolean> result = new Result<>();
        JSONObject data = new JSONObject();
        String[] cantonCodes={"32","13","35"};
        try {
            for(int i=0;i<cantonCodes.length;i++){
                String cantonCode=cantonCodes[i];
                JSONObject json = new JSONObject();
                EnterpriseConfigurationParam configurationParam=new EnterpriseConfigurationParam();
                configurationParam.setCantonCode(cantonCode);
                configurationParam.setPage(1);
                configurationParam.setSection("DISPOSITION");
                List<EnterpriseConfigurationModel> enterpriseModelList = configurationService.listEnterpriseInfoBySection(null, configurationParam);
                json.put("dispositionEnterpriseModelList", enterpriseModelList);
                int maxPage = configurationService.getMaxPageBySection(configurationParam);
                json.put("dispositionMaxPage", maxPage);

                configurationParam.setSection("RECYCLING");
                enterpriseModelList = configurationService.listEnterpriseInfoBySection(null, configurationParam);
                maxPage = configurationService.getMaxPageBySection(configurationParam);
                json.put("recyclingEnterpriseModelList", enterpriseModelList);
                json.put("recyclingMaxPage", maxPage);

                configurationParam.setSection("SPECIALCATEGORY");
                enterpriseModelList = configurationService.listEnterpriseInfoBySection(null, configurationParam);
                json.put("specialCategoryEnterpriseModelList", enterpriseModelList);
                maxPage = configurationService.getMaxPageBySection(configurationParam);
                json.put("specialCategoryMaxPage", maxPage);
                data.put(cantonCode,json);
            }
            Util.generateStaticHtml("var entList=",data,"/ROOT/home_entList.js");
            result.setData(true);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 首页-根据不同的版面显示企业列表
     *
     * @param pagingParameter
     * @param configurationParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listEnterpriseInfoByCantonCode")
    public Result<Map<String, Object>> listEnterpriseInfoByCantonCode(EnterpriseConfigurationParam configurationParam, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<>();
        try {
               List<EnterpriseConfigurationModel> enterpriseModelList = configurationService.listEnterpriseInfoByCantonCode(configurationParam, pagingParameter);
            resultMap.put("enterpriseModelList", enterpriseModelList);
            resultMap.put("paging", JSONArray.toJSON(pagingParameter));
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 系统管理员-查看首页企业列表
     *
     * @param user
     * @param configurationParam
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listEnterpriseInfo")
    public Result<Map<String, Object>> listEnterpriseInfo(User user, @RequestBody EnterpriseConfigurationParam configurationParam, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<EnterpriseConfigurationModel> enterpriseModelList = configurationService.listEnterpriseInfo(user, configurationParam, pagingParameter);
            dataMap.put("enterpriseModelList", JSONArray.toJSON(enterpriseModelList));
            dataMap.put("paging", JSONArray.toJSON(pagingParameter));
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 系统管理员-获取企业首页设计详细
     *
     * @param user
     * @param configurationParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEnterpriseConfig")
    public Result<EnterpriseConfigurationModel> getEnterpriseConfig(User user, @RequestBody EnterpriseConfigurationParam configurationParam) {
        Result<EnterpriseConfigurationModel> result = new Result<EnterpriseConfigurationModel>();
        try {
            EnterpriseConfigurationModel configurationModel = configurationService.getEnterpriseConfig(configurationParam);
            result.setData(configurationModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 获取权重企业列表
     *
     * @param configurationParam
     * @return
     */
    @ResponseBody
    @RequestMapping("/listEnterpriseInfoByWeight")
    public Result<List<EnterpriseConfigurationModel>> listEnterpriseInfoByWeight(@RequestBody EnterpriseConfigurationParam configurationParam) {
        Result<List<EnterpriseConfigurationModel>> resultMap = new Result<>();
        try {
            List<EnterpriseConfigurationModel> modelList = configurationService.listEnterpriseInfoByWeight(configurationParam);
            resultMap.setData(modelList);
            resultMap.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            resultMap.setStatus(ResultStatus.Error);
        }
        return resultMap;
    }




}
