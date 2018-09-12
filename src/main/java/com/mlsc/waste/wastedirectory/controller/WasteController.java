package com.mlsc.waste.wastedirectory.controller;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.calling.model.CallingVo;
import com.mlsc.yifeiwang.calling.service.ICallingService;
import com.mlsc.yifeiwang.codedirectory.model.CodeValueVo;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.utils.datatable.DataTablesUtils;
import com.mlsc.waste.utils.datatable.FilterConditions;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteName;
import com.mlsc.waste.wastedirectory.model.WasteTypeVo;
import com.mlsc.waste.wastedirectory.service.WasteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 危废名录管理Controller
 * @author sunjl
 */
@Controller
@RequestMapping("/Waste")
@Scope("prototype")
public class WasteController extends BaseController{
    @Autowired
    private WasteService wasteService;
    @Autowired
    private ICallingService callingService;

    private static final String PATH="Waste/";

    private final static Logger logger = LoggerFactory.getLogger(WasteController.class);

    /**
     * 危废名录列表
     * @param request 编辑保存后需要弹出提示框，传递参数
     * @return 
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "list");
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
    public Map<String,Object> listForJson(HttpServletRequest request,int start,int length,String draw) throws Exception {
        int total=0;
        List<Waste> list = null;
        	
        //构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);
        
        //构造分页对象
        PagingParameter paging=DataTablesUtils.generatePagingParameter(start, length);
        
        //统计符合条件的记录数
        total = wasteService.count(filterConditions.getWhere(), filterConditions.getParamMap());
        
        if(total > 0){
        	//获取符合条件的记录集合
            list = wasteService.list(filterConditions.getWhere(), filterConditions.getParamMap(), paging);
        }
        //构造并返回结果集
        return DataTablesUtils.generateResult(draw, total, total, list);
    }
    
    /**
     * 危废名录新增画面的初始化
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request){
        ModelAndView mv=new ModelAndView(PATH + "add");
        try {
            initialAddData(mv);
            mv.addObject("waste", new Waste());
        } catch (Exception e) {
            logger.error("新增危废类别获取上级节点异常", e);
            mv.setViewName(Constant.URL_500);
        }
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }
    
    /**
     * 危废名录编辑页面的初始化
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/edit")
    public ModelAndView edit(HttpServletRequest request){
        ModelAndView mv = new ModelAndView(PATH + "edit");
        String wasteId = request.getParameter("id");
        String msg = request.getParameter("msg");
        String status = request.getParameter("status");
        try {
            initialEditData(mv, wasteId);
        } catch (Exception e) {
            logger.error("编辑危废类别获取上级节点异常", e);
            mv.setViewName(Constant.URL_500);
        }
        mv.addObject("msg", msg);
        mv.addObject("status", status);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }
    

    
    /**
     * 危废名录的删除
     * @param request
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping("/removeWaste")
    public Result<Map<String, String>> removeWaste(HttpServletRequest request) throws Exception {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] ids = request.getParameterValues("id");
        List<String> wasteids = Util.stringArrayConvertToList(ids);
        wasteService.removeWasteByIds(wasteids);
        result.setStatus(ResultStatus.Success);
        return result;
    }
    
    /**
     * 危废名录的停用与启用
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping("/enableWaste")
    public Result<Map<String, String>> enableWaste(HttpServletRequest request) throws Exception {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] wasteIds = request.getParameterValues("id"); 
        String ticketId = request.getParameter("ticketId");
        List<String> wasteIdList = Util.stringArrayConvertToList(wasteIds);
        wasteService.updateSataus(wasteIdList,Constant.ENABLED,ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }
    
    /**
     * 危废名录的停用与启用
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/disableWaste")
    public Result<Map<String, String>> disableWaste(HttpServletRequest request)  throws Exception{
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] wasteIds = request.getParameterValues("id"); 
        String ticketId = request.getParameter("ticketId");
        List<String> wasteIdList = Util.stringArrayConvertToList(wasteIds);
        wasteService.updateSataus(wasteIdList,Constant.DISABLE,ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }
    
     /**
      * 获取危险特性
      * @return
      */
     private List<CodeValueVo> getRiskCharacteristicsList(List<CodeValue> riskCharacteristicsList, Waste waste) {
         List<CodeValueVo> list = null;
         if(riskCharacteristicsList != null && riskCharacteristicsList.size() > 0){

             String isChecked = null;
             new ArrayList<>(riskCharacteristicsList.size());
             BeanCopier beanCopier = BeanCopier.create(CodeValue.class,CodeValueVo.class,false);
             for (CodeValue codevo : riskCharacteristicsList) {
                 switch (codevo.getCode()) {
                     case CodeTypeConstant.WASTE_FEATURES_T:
                         isChecked = waste.getToxicity();
                         break;
                     case CodeTypeConstant.WASTE_FEATURES_C:
                         isChecked = waste.getCorrosivity();
                         break;
                     case CodeTypeConstant.WASTE_FEATURES_I:
                         isChecked = waste.getIgnitability();
                         break;
                     case CodeTypeConstant.WASTE_FEATURES_R:
                         isChecked = waste.getReactivity();
                         break;
                     case CodeTypeConstant.WASTE_FEATURES_In:
                         isChecked = waste.getInfectivity();
                         break;
                     default:
                         break;
                 }
                 CodeValueVo cv = new CodeValueVo();
                 beanCopier.copy(codevo, cv, null);
                 cv.setIsChecked(isChecked);
             }
         }
         return list;
     }
    

    
    private void initialAddData(ModelAndView mv){
        try {
            List<CallingVo> callingList = callingService.listThirdLevelCalling();
            List<Map<String, Object>> wasteTypeList = wasteService.getWasteTypeList(new ArrayList<Object>().toArray());
            List<CodeValue> riskCharacteristicsList = wasteService.getDropDownListByTypeCode(CodeTypeConstant.WASTE_FEATURES);
            mv.addObject("riskCharacteristicsList", riskCharacteristicsList);
            mv.addObject("callingList",callingList);
            mv.addObject("wasteTypeList",wasteTypeList);
        } catch (Exception e) {
            logger.error("新增行业获取上级节点异常", e);
            mv.setViewName(Constant.URL_500);
        }
    }
    
    private void initialEditData(ModelAndView mv, String wasteId) {
        try {
            List<WasteName> wasteNames = wasteService.getWasteNamesByWasteId(wasteId);
            mv.addObject("wasteNames", wasteNames);
            Waste waste = wasteService.getWasteById(wasteId);
            waste.setCallingCode(waste.getCode().substring(0, 3));
            waste.setThreeYardsCode(waste.getCode().substring(4, 7));
            waste.setWasteTypeCode(waste.getCode().substring(8, 10));
            List<CallingVo> callingList = callingService.listThirdLevelCalling();
            List<Map<String, Object>> wasteTypeList = wasteService.getWasteTypeList(new ArrayList<Object>().toArray());
            List<CodeValue> riskCharacteristicsList = wasteService.getDropDownListByTypeCode(CodeTypeConstant.WASTE_FEATURES);
            mv.addObject("riskCharacteristicsList", getRiskCharacteristicsList(riskCharacteristicsList, waste));
            mv.addObject("callingList",callingList);
            mv.addObject("wasteTypeList",wasteTypeList);
            mv.addObject("waste", waste);
        } catch (Exception e) {
            logger.error("新增行业获取上级节点异常", e);
            mv.setViewName(Constant.URL_500);
        }
    }
    
    @ResponseBody
    @RequestMapping("/getAllWasteCode")
    public Result< List<WasteTypeVo> > getAllWasteCode(HttpServletRequest request) {
        Result<List<WasteTypeVo> > result = new Result<List<WasteTypeVo> >();
        try {
        	List<WasteTypeVo>  list = wasteService.getAllWasteCode();
        	result.setData(list);
        	result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        return result;
    }
}
