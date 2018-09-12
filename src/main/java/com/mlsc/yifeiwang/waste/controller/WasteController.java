package com.mlsc.yifeiwang.waste.controller;

import com.mlsc.yifeiwang.waste.entity.Waste;
import com.mlsc.yifeiwang.waste.entity.WasteType;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.waste.service.IWasteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/9/25.
 */
@Controller("yifeiWasteController")
@RequestMapping(value = "/yifeiwaste", method = {RequestMethod.GET, RequestMethod.POST})
@Scope("prototype")
public class WasteController {
    @Autowired
    private IWasteTypeService wasteTypeService;

    /**
     * 危废名录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listWasteInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<WasteType>> listWasteInfo() {
        Result<List<WasteType>> result = new Result<>();
        try {
            List<WasteType> list = wasteTypeService.listWasteInfo();
            result.setData(list);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            List<String> infoList = new ArrayList<String>();
            infoList.add("获取危废信息列表时异常");
            result.setStatus(ResultStatus.Error);
            result.setInfoList(infoList);
        }
        return result;
    }


    /**
     * 二位码列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listWasteType", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<WasteType>> listWasteType() {
        Result<List<WasteType>> result = new Result<>();
        try {
            List<WasteType> list = wasteTypeService.listWasteType();
            result.setData(list);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            List<String> infoList = new ArrayList<String>();
            infoList.add("获取二位码信息时异常");
            result.setStatus(ResultStatus.Error);
            result.setInfoList(infoList);
        }
        return result;
    }


    /**
     * 八位码列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listWasteCode", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<Waste>> listWasteCode() {
        Result<List<Waste>> result = new Result<>();
        try {
            List<Waste> list = wasteTypeService.listWasteCode();
            result.setData(list);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            List<String> infoList = new ArrayList<String>();
            infoList.add("获取八位码信息时异常");
            result.setStatus(ResultStatus.Error);
            result.setInfoList(infoList);
        }
        return result;
    }


}
