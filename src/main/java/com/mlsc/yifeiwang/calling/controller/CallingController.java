package com.mlsc.yifeiwang.calling.controller;

import com.alibaba.fastjson.JSONObject;
import com.mlsc.yifeiwang.calling.entity.Calling;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.calling.model.CallingListPojo;
import com.mlsc.yifeiwang.calling.model.CallingVo;
import com.mlsc.yifeiwang.calling.service.ICallingService;
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
 * 行业管理Controller
 * 
 * @author zhugl
 */
@Controller
@RequestMapping("/calling")
@Scope("prototype")
public class CallingController {
    @Autowired
    private ICallingService callingService;

    private static final String PATH = "Calling/";

    private final static Logger logger = LoggerFactory.getLogger(CallingController.class);
    @ResponseBody
    @RequestMapping("/listCallingByPage")
    public JSONObject listActivityByPage(int current, int size, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {
            Integer start=(current-1)*size;
            PagingParameter paging=DataTablesUtils.generatePagingParameter(start, size);
            List<CallingListPojo> callingListPojos= callingService.listCallingByPage(paging);
            Integer total= callingService.totalCalling();
            jsonObject.put("list", callingListPojos);
            jsonObject.put("total",total);
        } catch (Exception e) {
            logger.error("获取参加活动时异常", e);
        }

        return jsonObject;
    }
    /**
     * 保存行业数据
     * 
     * @param calling 行业类别实体类
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveCalling")
    public Result<Map<String, String>> saveCalling(Calling calling, HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        try {
            callingService.saveCalling(calling, ticketId);
            msg = "已成功创建行业" + calling.getName();
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }
    
    /**
     * 更新行业数据
     * 
     * @param calling 行业类别实体类
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateCalling")
    public Result<Map<String, String>> updateCalling(Calling calling, HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        try {
            callingService.updateCalling(calling, ticketId);
            msg = "已成功更新行业" + calling.getName();
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    /**
     * 新增页面的初始化
     * 
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "add");
        initialCallingDropdown(mv); 
        mv.addObject("calling", new Calling());
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
    @RequestMapping("/edit")
    public ModelAndView edit(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "edit");
        String callingId = request.getParameter("id");
        try {
            Calling calling = callingService.getCallingById(callingId);
            initialCallingDropdown(mv);
            mv.addObject("calling", calling);
        } catch (Exception e) {
            logger.error("行业编辑页面初始化异常", e);
            mv.setViewName(Constant.URL_500);
        }
        
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 根据id批量删除行业(物理删除)
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/removeCalling")
    public Result<Map<String, String>> removeCalling(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String[] callingIdArrays = request.getParameterValues("id");
        String msg = null;
        boolean deleteFlag = true;// 删除操作是否可行
        try {
            List<String> callingIds = Util.stringArrayConvertToList(callingIdArrays);
            // 删除前做check，有子节点的行业不允许删除
            for (String callingId : callingIds) {
                if (callingService.isHasChildCalling(callingId)) {
                    msg = "选择的行业存在子行业，不允许删除！";
                    result.setStatus(ResultStatus.Failure);
                    deleteFlag = false;
                    break;
                }
            }

            if (deleteFlag) {
                callingService.removeCalling(callingIds);
                msg = "选择行业删除成功！";
                result.setStatus(ResultStatus.Success);
            }
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }

        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }
    
    /**
     * 根据id批量启用行业
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/enableCalling")
    public Result<Map<String, String>> enableCalling(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String msg = null;
        String status = null;
        String[] callingIdArrays = request.getParameterValues("id");
        String ticketId = request.getParameter("ticketId");
        try {
            List<String> callingIds = Util.stringArrayConvertToList(callingIdArrays);
            callingService.updateCallingStatus(callingIds, Constant.ENABLED, ticketId);
            msg = "选择行业启用成功！";
            status = Constant.STATUS_SUCCESS;
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            status = Constant.STATUS_DANGER;
            result.setStatus(ResultStatus.Failure);
        }

        dataMap.put("msg", msg);
        dataMap.put("status", status);
        result.setData(dataMap);
        return result;
    }
    
    /**
     * 根据id批量停用行业
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/disableCalling")
    public Result<Map<String, String>> disableCalling(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String msg = null;
        String status = null;
        String[] callingIdArrays = request.getParameterValues("id");
        String ticketId = request.getParameter("ticketId");
        try {
            List<String> callingIds = Util.stringArrayConvertToList(callingIdArrays);
            callingService.updateCallingStatus(callingIds, Constant.DISABLE, ticketId);
            msg = "选择行业停用成功！";
            status = Constant.STATUS_SUCCESS;
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            status = Constant.STATUS_DANGER;
            result.setStatus(ResultStatus.Failure);
        }
        
        dataMap.put("msg", msg);
        dataMap.put("status", status);
        result.setData(dataMap);
        return result;
    }

    /**
     * 根据id,code查询行业
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/isCallingCodeExistent")
    public Result<Map<String, String>> isCallingCodeExistent(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String callingId = request.getParameter("id");
        String callingCode = request.getParameter("code");
        String msg = null;
        try {
            // 重复的行业代码不能保存
            result.setStatus(ResultStatus.Success);
            if (callingService.isHasCallingData(callingId, callingCode)) {
                result.setStatus(ResultStatus.Failure);
                msg = "输入的行业代码" + callingCode + "已存在，请重新填写！";
                dataMap.put("msg", msg);
                result.setData(dataMap);
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
            msg = Constant.SYS_MSG;
            dataMap.put("msg", msg);
            result.setData(dataMap);
        }
        return result;
    }

    /**
     * 列出所有行业
     * 
     * @param request 编辑保存后需要弹出提示框，传递参数
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "list");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 返回json格式的行业列表
     * 
     * @return
     */
    @RequestMapping("/listForJson")
    @ResponseBody
    public Map<String, Object> listForJson(HttpServletRequest request, int start, int length, String draw) {
        logger.info("---------------行业list页面获取数据开始---------------");
        List<CallingListPojo> list = null;
        int total = 0;
        
        //构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);
        
        //构造分页对象
        PagingParameter paging=DataTablesUtils.generatePagingParameter(start, length);
        
        //统计符合条件的记录数
        total = callingService.count(filterConditions.getParamMap());
        
        if(total > 0){
            //获取符合条件的记录集合
            list = callingService.list( filterConditions.getParamMap(), paging);
        }
        logger.info("---------------行业list页面获取数据结束---------------");
        //构造并返回结果集
        return DataTablesUtils.generateResult(draw, total, total, list);
    }
    
    private void initialCallingDropdown(ModelAndView mv){
        try {
            Map<String, List<CallingVo>> callingDropdownMap = callingService.queryCallingDropDown();
            mv.addObject("rootCallingList", callingDropdownMap.get("rootCallingList"));
            mv.addObject("subCallingList", callingDropdownMap.get("subCallingList"));
        } catch (Exception e) {
            logger.error("新增行业获取上级节点异常", e);
            mv.setViewName(Constant.URL_500);
        }
    }
}
