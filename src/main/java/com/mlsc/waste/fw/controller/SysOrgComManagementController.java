package com.mlsc.waste.fw.controller;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.fw.model.SysOrgCom;
import com.mlsc.waste.fw.model.SysOrgComCantonRelation;
import com.mlsc.waste.fw.service.SysOrgComCantonRelationService;
import com.mlsc.waste.fw.service.SysOrgComManagementService;
import com.mlsc.waste.fw.service.SysOrgComService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.utils.datatable.DataTablesUtils;
import com.mlsc.waste.utils.datatable.FilterConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * 平台管理员管理组织机构Controller
 * 
 * @author zhugl
 */
@Controller
@RequestMapping("/sysorgcom")
@Scope("prototype")
public class SysOrgComManagementController {
    private final static Logger logger = LoggerFactory.getLogger(SysOrgComManagementController.class);

    private static final String PATH = "SysOrgCom/";
    
    @Autowired
    private SysOrgComManagementService sysOrgComManagementService;
    
    @Autowired
    private SysOrgComService sysOrgComService;
    
    @Autowired
    private SysOrgComCantonRelationService sysOrgComCantonRelationService;
    

    /**
     * 列出所有组织机构
     * 
     * @param request 编辑保存后需要弹出提示框，传递参数
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "sysOrgComList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 返回json格式的组织机构列表
     * 
     * @return
     */
    @RequestMapping("/listForJson")
    @ResponseBody
    public Map<String, Object> listForJson(HttpServletRequest request, int start, int length, String draw) {
        List<SysOrgCom> list = null;
        int total = 0;
        
        //构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);
        
        //构造分页对象
        PagingParameter paging=DataTablesUtils.generatePagingParameter(start, length);
        
        //统计符合条件的记录数
        total = sysOrgComManagementService.getOrgComCount(filterConditions.getWhere(), filterConditions.getParamMap());
        
        if(total > 0){
            //获取符合条件的记录集合
            list = sysOrgComManagementService.getOrgComList(filterConditions.getWhere(), filterConditions.getParamMap(), paging);
        }
        //构造并返回结果集
        return DataTablesUtils.generateResult(draw, total, total, list);
    }
    
    /**
     * 新增页面的初始化
     * 
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/addOrgCom")
    public ModelAndView addOrgCom(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "addOrgCom");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }
    
    /**
     * 编辑页面的初始化
     * 
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/editOrgCom")
    public ModelAndView editOrgCom(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "editOrgCom");
        String orgComId = request.getParameter("comId");
        try {
            SysOrgCom sysOrgCom = sysOrgComService.getOrgCom(orgComId);
            mv.addObject("sysOrgCom", sysOrgCom);
        } catch (Exception e) {
            logger.error("组织机构编辑页面初始化异常", e);
            mv.setViewName(Constant.URL_500);
        }
        
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }
    
    /**
     * 组织机构代码获取其所在区域
     * 
     * @return
     */
    @RequestMapping("/getCantonValue")
    @ResponseBody
    public Result<Map<String, String>> getCantonValue(HttpServletRequest request) {
        Map<String, String> cantonMap = null;
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String orgComId = request.getParameter("orgComId");
        try {
            SysOrgComCantonRelation cantonRelation = sysOrgComCantonRelationService.getSysOrgComCantonRelation(orgComId);
            cantonMap = sysOrgComManagementService.getCantonValue(cantonRelation.getCantonCode(), request.getParameter("ticketId"));
            if (cantonMap != null) {
                cantonMap.put("relId", cantonRelation.getRelId());
                cantonMap.put("cantonCode", cantonRelation.getCantonCode());
            }
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("获取组织机构名称时异常", e);
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(cantonMap);
        return result;
    }
    
    /**
     * 输入机构代码是否重复
     * 
     * @return
     */
    @RequestMapping("/isExistentOrgComCode")
    @ResponseBody
    public String isExistentOrgComCode(HttpServletRequest request) {
        String orgComName = null;
        String orgComId = request.getParameter("orgComId");
        String orgComCode = request.getParameter("comCode");
        try {
            orgComName = sysOrgComManagementService.getOrgComName(orgComId, orgComCode);
        } catch (Exception e) {
            logger.error("获取组织机构名称时异常", e);
        }
        return orgComName;
    }
    
    @ResponseBody
    @RequestMapping("/saveOrgCom")
    public Result<Map<String, String>> saveOrgCom(SysOrgCom sysOrgCom, HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String cantonCode = request.getParameter("cantonCode");// 择行政区
        try {
            String statusCode = sysOrgComManagementService.saveOrgComAndCantonRelation(sysOrgCom, cantonCode, ticketId);
            dataMap.put("statusCode", statusCode);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("保存组织机构名称时异常", e);
            result.setStatus(ResultStatus.Failure);
        }
        
        result.setData(dataMap);
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/updateOrgCom")
    public Result<Map<String, String>> updateOrgCom(SysOrgCom sysOrgCom, HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String relId = request.getParameter("relId");// 机构与行政区域关系表ID
        String cantonCode = request.getParameter("cantonCode");// 择行政区
        try {
            String statusCode = sysOrgComManagementService.updateOrgComAndCantonRelation(sysOrgCom, relId, cantonCode, ticketId);
            dataMap.put("statusCode", statusCode);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("保存组织机构名称时异常", e);
            result.setStatus(ResultStatus.Failure);
        }
        
        result.setData(dataMap);
        return result;
    }
    

}