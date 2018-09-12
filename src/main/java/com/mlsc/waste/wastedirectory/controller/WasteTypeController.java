package com.mlsc.waste.wastedirectory.controller;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.utils.datatable.DataTablesUtils;
import com.mlsc.waste.utils.datatable.FilterConditions;
import com.mlsc.waste.wastedirectory.model.WasteType;
import com.mlsc.waste.wastedirectory.service.WasteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 危废类别管理Controller
 *
 * @author sunjl
 */
@Controller
@RequestMapping("/WasteType")
@Scope("prototype")
public class WasteTypeController extends BaseController {
    @Autowired
    private WasteTypeService wasteTypeService;

    private static final String PATH = "WasteType/";

    /**
     * 危废类别列表
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
     * 返回json格式的危废类别列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/listForJson")
    @ResponseBody
    public Map<String, Object> listForJson(HttpServletRequest request, int start, int length, String draw) throws Exception {
        int total = 0;
        List<WasteType> list = null;

        //构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);

        //构造分页对象
        PagingParameter paging = DataTablesUtils.generatePagingParameter(start, length);

        //统计符合条件的记录数
        total = wasteTypeService.count(filterConditions.getWhere(), filterConditions.getParamMap());

        if (total > 0) {
            //获取符合条件的记录集合
            list = wasteTypeService.list(filterConditions.getWhere(), filterConditions.getParamMap(), paging);
        }
        //构造并返回结果集
        return DataTablesUtils.generateResult(draw, total, total, list);
    }

    /**
     * 危废类别新增画面的初始化
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "add");
        mv.addObject("wasteType", new WasteType());
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 危废类别编辑页面的初始化
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/edit")
    public ModelAndView edit(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "edit");
        String id = request.getParameter("id");
        WasteType wasteType = wasteTypeService.getWasteTypeById(id);
        wasteType.setCode(wasteType.getCode().substring(2, wasteType.getCode().length()));
        mv.addObject("wasteType", wasteType);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 危废类别的删除
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/removeWasteType")
    public Result<Map<String, String>> removeWasteType(HttpServletRequest request) throws Exception {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] wasteTypeIds = request.getParameterValues("id");
        List<String> wasteTypeIdList = Util.stringArrayConvertToList(wasteTypeIds);
        wasteTypeService.removeWasteType(wasteTypeIdList);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 危废类别的停用
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @ResponseBody
    @RequestMapping("/disableWasteType")
    public Result<Map<String, String>> disableWasteType(HttpServletRequest request) throws DaoAccessException {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] wasteTypeIds = request.getParameterValues("id");
        String ticketId = request.getParameter("ticketId");
        List<String> wasteTypeIdList = Util.stringArrayConvertToList(wasteTypeIds);
        wasteTypeService.updateWasteTypeSataus(wasteTypeIdList, Constant.DISABLE, ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 危废类别的启用
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @ResponseBody
    @RequestMapping("/enableWasteType")
    public Result<Map<String, String>> enableWasteType(HttpServletRequest request) throws DaoAccessException {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] wasteTypeIds = request.getParameterValues("id");
        String ticketId = request.getParameter("ticketId");
        List<String> wasteTypeIdList = Util.stringArrayConvertToList(wasteTypeIds);
        wasteTypeService.updateWasteTypeSataus(wasteTypeIdList, Constant.ENABLED, ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 危废类别的新增保存或更新
     *
     * @param wasteType
     * @param request
     * @return
     * @throws IOException
     * @throws DaoAccessException
     */
    @ResponseBody
    @RequestMapping("/saveWasteType")
    public Result<Map<String, String>> saveWasteType(WasteType wasteType, HttpServletRequest request) throws DaoAccessException {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String ticketId = request.getParameter("ticketId");
        wasteTypeService.saveWasteType(wasteType, ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 危废类别的新增保存或更新
     *
     * @param wasteType
     * @param request
     * @return
     * @throws IOException
     * @throws DaoAccessException
     */
    @ResponseBody
    @RequestMapping("/updateWasteType")
    public Result<Map<String, String>> updateWasteType(WasteType wasteType, HttpServletRequest request) throws DaoAccessException {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String ticketId = request.getParameter("ticketId");
        wasteTypeService.updateWasteType(wasteType, ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 二位码唯一性查询
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/isWasteTypeCodeExistent")
    public Result<Map<String, Object>> isWasteTypeCodeExistent(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        String id = request.getParameter("id");
        String code = Constant.PRV_WASTE_TYPE + request.getParameter("code");
        map.put("checkResult", wasteTypeService.isWasteTypeCodeExistent(id, code));
        result.setStatus(ResultStatus.Success);
        result.setData(map);
        return result;
    }

    @RequestMapping("/getAllWasteType")
    @ResponseBody
    public Result<List<WasteType>> getAllWasteType(HttpServletRequest request) throws Exception {
        Result<List<WasteType>> result = new Result<List<WasteType>>();
        List<WasteType> list = null;
        list = wasteTypeService.getAllWateType();
        result.setData(list);
        return result;
    }
}
