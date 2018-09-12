package com.mlsc.mobileservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
import com.mlsc.waste.licence.model.OperationLicenceVo;
import com.mlsc.waste.licence.service.LicenceApprovedService;
import com.mlsc.waste.licence.service.LicenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/licenceservice")
@Scope("prototype")
public class LicenceServiceController {
    
    @Autowired
    private LicenceService licenceService;
    
    @Autowired
    private LicenceApprovedService licenceApprovedService;
    
    /**
     * 许可证基本信息
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/licence")
    public Result<Map<String, Object>> licence( HttpServletRequest request) throws Exception{
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //许可证id
        String licenceId = request.getParameter("licenceId");
        OperationLicenceVo getlicenceVoById = licenceApprovedService.getOperationLicenceVoById(licenceId);
        dataMap.put("licence", JSONArray.toJSON(getlicenceVoById));
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }
    
    
    /**
     * 许可证处置方式详情
     * @param request
     * @return
     * 
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/licenceDetail")
    public Result<Map<String, Object>> licenceDetail( HttpServletRequest request) throws Exception{
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        String licenceId = request.getParameter("licenceId");
        String licenceItemId = request.getParameter("licenceItemId");
        List<OperationLicenceItemVo> wasteInfoByLicenceId = licenceApprovedService.getWasteInfoByLicenceId(licenceId);
        if(wasteInfoByLicenceId != null){
            Iterator<OperationLicenceItemVo> it = wasteInfoByLicenceId.iterator();
            while(it.hasNext()){
                OperationLicenceItemVo operationLicenceItemVo = it.next();
                if(!operationLicenceItemVo.getItemId().equals(licenceItemId)){
                    it.remove();
                }
            }
            if(wasteInfoByLicenceId.size()>0){
                dataMap.put("licenceDetail", JSONArray.toJSON(wasteInfoByLicenceId.get(0)));
            }
        }
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }
    

}
