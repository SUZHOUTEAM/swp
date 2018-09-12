package com.mlsc.waste.fw.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.fw.service.SysCantonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * 平台管理员管理组织机构Controller
 * 
 * @author zhugl
 */
@Controller
@RequestMapping("/fw/syscanton")
@Scope("prototype")
public class SysCantonController {
    
    private static final String PATH = "SysOrgCom/";
    
    @Autowired
    private SysCantonService sysCantonService;

    private final static Logger logger = LoggerFactory.getLogger(SysCantonController.class);

    /**
     * 组织机构代码获取其所在区域
     * 
     * @return
     */
    @RequestMapping("/getProvinceDistricts")
    @ResponseBody
    public Result<Map<String, Object>> getProvinceDistricts(HttpServletRequest request) {
        Map<String, Object> cantonMap = null;
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        // String orgComId = request.getParameter("orgComId");
        try {
            cantonMap = sysCantonService.getProvinceDistricts(request.getParameter("ticketId"));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("获取组织机构名称时异常", e);
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(cantonMap);
        return result;
    }
    
    /**
     * 组织机构代码获取其所在区域
     * 
     * @return
     */
    @RequestMapping("/getCityDistricts")
    @ResponseBody
    public Result<Map<String, Object>> getCityDistricts(HttpServletRequest request) {
        Map<String, Object> cantonMap = null;
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        String parentCantonCode = request.getParameter("parentCantonCode");
        String ticketId = request.getParameter("ticketId");
        try {
            cantonMap = sysCantonService.getCityDistricts(parentCantonCode, ticketId);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("根据区域代码加载其子区域代码时异常", e);
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(cantonMap);
        return result;
    }

    /**
     * 生成前端区域文件数据
     *
     * @return
     */
    @RequestMapping("/getAllCityData")
    @ResponseBody
    public Result<Map<String, Object>> getAllCityData(HttpServletRequest request) {
        Map<String, Object> cantonMap = null;
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        String ticketId=request.getParameter("ticketId");
        try {
            cantonMap = sysCantonService.getProvinceDistricts(ticketId);
            JSONObject object=(JSONObject)cantonMap.get("value");
            JSONObject provinces=new JSONObject();
            JSONObject object86=(JSONObject)object.get("86");
            cantonMap.put("86",object86);
            for (Map.Entry<String, Object> entry0 : object86.entrySet()) {
                JSONArray array=(JSONArray)entry0.getValue();
                Iterator<Object> agit = array.iterator();
                while (agit.hasNext()) {
                    JSONObject ob = (JSONObject) agit.next();
                    String code=(String)ob.get("code");
                    provinces.put(code,(String)ob.get("address"));
                    Map<String, Object> cantonMap1 = sysCantonService.getCityDistricts(code, ticketId);
                    JSONObject object1=(JSONObject)cantonMap1.get("value");
                    for (Map.Entry<String, Object> entry : object1.entrySet()) {
                        cantonMap.put(entry.getKey(),entry.getValue());
                        System.out.println(entry.getValue());
                        JSONObject object2=(JSONObject)entry.getValue();
                        for (Map.Entry<String, Object> entry1 : object2.entrySet()) {
                            String code1=entry1.getKey().toString();
                            Map<String, Object> cantonMap2 = sysCantonService.getCityDistricts(code1, ticketId);
                            JSONObject object3=(JSONObject)cantonMap2.get("value");
                            for (Map.Entry<String, Object> entry2 : object3.entrySet()) {
                                cantonMap.put(entry2.getKey(),entry2.getValue());
                            }
                        }
                    }
                }
            }
            cantonMap.put("provinces",provinces);
            cantonMap.remove("value");
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("获取组织机构名称时异常", e);
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(cantonMap);
        return result;
    }

}