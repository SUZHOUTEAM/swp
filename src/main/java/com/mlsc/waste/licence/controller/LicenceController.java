package com.mlsc.waste.licence.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.waste.licence.model.*;
import com.mlsc.waste.licence.service.LicenceDetailService;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.utils.datatable.DataTablesUtils;
import com.mlsc.waste.utils.datatable.FilterConditions;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 许可证管理Controller
 *
 * @author sunjl
 */
@Controller
@RequestMapping("/licence")
@Scope("prototype")
public class LicenceController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(LicenceController.class);

    private static final String PATH = "/Licence/";

    @Autowired
    private LicenceService licenceService;
    @Autowired
    private ICodeTypeService codeTypeService;

    @Autowired
    private LicenceDetailService licenceDetailService;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 设置List的最大长度  
        binder.setAutoGrowCollectionLimit(10000);
    }

    /**
     * 添加许可证licenceSteps.jsp  zhugl
     *
     * @param request
     * @return
     */
    @RequestMapping("/licenceSteps")
    public ModelAndView licenceSteps(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "licenceSteps");
        String ticketId = request.getParameter("ticketId");
        String licenceId = request.getParameter("licenceId");
        String enterpriseId = request.getParameter("enterpriseId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        if (StringUtils.isBlank(enterpriseId)) {
            enterpriseId = user.getEnterpriseId();
        }
        if (StringUtils.isNotBlank(licenceId)) {
            mv.addObject("licenceId", licenceId);
        }
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("enterpriseId", enterpriseId);
        mv.addObject("isSysUser", Util.isSysUser(request.getParameter("ticketId")));// 判断是不是平台管理员
        return mv;
    }

    /**
     * 添加许可证基本信息addLicenceBaseInfo.jsp  zhugl
     *
     * @param request
     * @return
     */
    @RequestMapping("/addLicenceBaseInfo")
    public ModelAndView addLicenceBaseInfo(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "addLicenceBaseInfo");
        String licenceId = request.getParameter("licenceId");
        String ticketId = request.getParameter("ticketId");
        String enterpriseId = request.getParameter("enterpriseId");
        OperationLicence licence = null;
        if (StringUtils.isNotBlank(licenceId)) {
            mv.addObject("licenceId", licenceId);
            licence = licenceService.getlicenceVoById(licenceId);
            mv.addObject("operationLicence", licence);
        } else {
            licence = new OperationLicence();
            licence.setEnterprise_id(enterpriseId);
            mv.addObject("operationLicence", licence);
        }
        List<RPCSysOrgCom> rpcSysOrgComs = licenceService.getOrgByEnterpriseId(ticketId, enterpriseId);
        List<CodeValue> licModelList = codeTypeService.getCodeValuesTypeCode(CodeTypeConstant.LIC_MODE);
        mv.addObject("rpcSysOrgComs", rpcSysOrgComs);
        mv.addObject("licModelList", licModelList);
        mv.addObject("ticketId", ticketId);
        return mv;
    }

    /**
     * 验证许可证编码是否重复 zhugl
     *
     * @param request
     * @return
     */
    @RequestMapping("isLicenceNoExist")
    @ResponseBody
    public Result<Boolean> isLicenceNoExist(HttpServletRequest request) throws Exception {
        Result<Boolean> result = new Result<Boolean>();
        String licenceNo = request.getParameter("licenceNo");
        String licenceId = request.getParameter("licenceId");
        result.setData(licenceService.isLicenceNoExist(licenceNo, licenceId));
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 验证许可证有效期是否重复 zhugl
     *
     * @param request
     * @return
     */
    @RequestMapping("isValidityPeriodRepeat")
    @ResponseBody
    public Result<Boolean> isValidityPeriodRepeat(HttpServletRequest request) throws Exception {
        Result<Boolean> result = new Result<Boolean>();
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        String licenceId = request.getParameter("licenceId");
        String enterpriseId = request.getParameter("enterpriseId");
        result.setData(licenceService.isValidityPeriodRepeat(enterpriseId, licenceId, startDate, endDate));
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 保存许可证基本信息 zhugl
     *
     * @param request
     * @return
     */
    @RequestMapping("saveLicenceBaseInfo")
    @ResponseBody
    public Result<String> saveLicenceBaseInfo(OperationLicence operationLicence, HttpServletRequest request) throws Exception {
        Result<String> result = new Result<String>();
        String ticketId = request.getParameter("ticketId");
        String licenceId = operationLicence.getId();
        if (StringUtils.isBlank(operationLicence.getId())) {
            licenceId = licenceService.saveLicenceBaseInfo(operationLicence, ticketId);
        } else {
            licenceService.updateLicenceBaseInfo(operationLicence, ticketId);
        }
        result.setData(licenceId);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 添加许可证 licenceSteps.jsp zhugl
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/addLicence")
    public ModelAndView addLicence(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "addLicence");
        //1.查询所有的处置方式
        List<CodeValue> dispositioTypeList = codeTypeService.getCodeValuesTypeCode(CodeTypeConstant.DISPOSE_TYPE);
        //2.查询所有的二位码
        List<WasteType> wasteTypeList = licenceService.getAllWateType();
        mv.addObject("dispositioTypeList", dispositioTypeList);
        mv.addObject("licenceItem", new OperationLicenceItem());
        mv.addObject("wasteTypeList", wasteTypeList);
        mv.addObject("licenceId", request.getParameter("licenceId"));

        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("getWasteByWasteTypeId")
    @ResponseBody
    public Result<Map<String, Object>> getWasteByWasteTypeId(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //获取前台的废物类别（2位码）
        String wasteTypeId = request.getParameter("wasteTypeId");
        //根据二位码查询对应的八位码
        List<Waste> listWaste = licenceService.getWasteByWasteTypeId(wasteTypeId);
        dataMap.put("wasteData", JSONArray.toJSON(listWaste));
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/getWasteNamesByWasteId")
    public Map<String, Object> getWasteNamesByWasteId(HttpServletRequest request) throws Exception {
        String keyword = request.getParameter("keyword");
        String wasteId = request.getParameter("wasteId");
        return licenceService.getWasteNamesByWasteId(wasteId, keyword);
    }

    @RequestMapping("/isHasDispositionItem")
    @ResponseBody
    public Result<Map<String, Object>> isHasDispositionItem(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String licenceId = request.getParameter("licenceId");
        String dispositionTypeId = request.getParameter("dispositionTypeId");
        String itemId = licenceService.isHasDispositionItem(licenceId, dispositionTypeId);
        if (StringUtils.isNotBlank(itemId)) {
            dataMap.put("checkResult", itemId);
        }
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    @RequestMapping("/saveOrUpdateDispositionItem")
    @ResponseBody
    public Result<Map<String, Object>> saveOrUpdateDispositionItem(OperationLicenceItem licenceItem, HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String licenceItemId = licenceService.saveOrUpdateDispositionItem(licenceItem, ticketId);
        dataMap.put("licenceItemId", licenceItemId);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    @RequestMapping("/saveDispositionDetails")
    @ResponseBody
    public Result<Map<String, Object>> saveDispositionItemAndDetail(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        String ticketId = request.getParameter("ticketId");
        List<OperationLicenceDetailExtend> licenceDetailExList = JSONArray.parseArray(request.getParameter("licenceDetailList"), OperationLicenceDetailExtend.class);
        licenceService.saveDispositionItemAndDetail(licenceDetailExList, ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 许可处置利用方式明细显示 zhugl
     *
     * @param request
     * @return
     */
    @RequestMapping("/getDispositionItems")
    @ResponseBody
    public Result<Map<String, Object>> getDispositionItems(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String licenceId = request.getParameter("licenceId");
        List<OperationLicenceItemVo> itemListVo = licenceService.getDispositionItems(licenceId);
        dataMap.put("itemList", JSONArray.toJSON(itemListVo));
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    /**
     * 许可处置利用方式明细显示 zhugl
     *
     * @param request
     * @return
     */
    @RequestMapping("/removeDetail")
    @ResponseBody
    public Result<Map<String, Object>> removeDetail(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String detailId = request.getParameter("detailId");
        String licenceId = request.getParameter("licenceId");
        String ticketId = request.getParameter("ticketId");
        licenceService.removeDetail(licenceId, detailId, ticketId);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    @RequestMapping("/updateWasteName")
    @ResponseBody
    public Result<Map<String, Object>> updateWasteName(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String licenceId = request.getParameter("licenceId");
        String detailId = request.getParameter("detailId");
        String wasteId = request.getParameter("wasteId");
        String wasteNameId = request.getParameter("wasteNameId");
        String wasteName = request.getParameter("wasteName");
        dataMap.put("wasteNameId", licenceService.updateWasteName(licenceId, detailId, wasteId, wasteNameId, wasteName, ticketId));
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    /**
     * 许可处置利用方式明细显示 zhugl
     *
     * @param request
     * @return
     */
    @RequestMapping("/getDispositionDetails")
    @ResponseBody
    public Result<Map<String, Object>> getDispositionDetails(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String licenceId = request.getParameter("licenceId");
        String licenceItemId = request.getParameter("licenceItemId");
        List<OperationLicenceDetailVo> detailList = licenceService.getDispositionDetails(licenceId, licenceItemId);
        dataMap.put("detailList", JSONArray.toJSON(detailList));
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    /**
     * 根据operation_licence_item主键删除处置方式以及operation_licence_detail数据 zhugl
     *
     * @param request
     * @return
     */
    @RequestMapping("/removeDispostionItem")
    @ResponseBody
    public Result<Map<String, Object>> removeDispostionItem(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String itemId = request.getParameter("itemId");
        String licenceId = request.getParameter("licenceId");
        String ticketId = request.getParameter("ticketId");
        licenceService.deleteDispositionType(itemId, licenceId, ticketId);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    /**
     * 许可证列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "list");
        List<CodeValue> codeValues = codeTypeService.getCodeValuesTypeCode(CodeTypeConstant.LIC_AUDIT);
        Iterator<CodeValue> it = codeValues.iterator(); //过滤掉审核通过的
        CodeValue codeValue = null;
        while (it.hasNext()) {
            codeValue = it.next();
            if (CodeTypeConstant.LIC_AUDIT_PASS.equals(codeValue.getCode())) {
                it.remove();//移除当前的对象  
            }
        }

        String ticketId = request.getParameter("ticketId");
        String enterpriseId = request.getParameter("enterpriseId");
        if (StringUtils.isBlank(enterpriseId)) {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            enterpriseId = user.getEnterpriseId();
        }

        mv.addObject("codeValues", codeValues);
        mv.addObject("ticketId", ticketId);
        mv.addObject("enterpriseId", enterpriseId);
        mv.addObject("isSysUser", Util.isSysUser(request.getParameter("ticketId")));// 判断是不是平台管理员
        return mv;
    }

    /**
     * 返回json格式的危废类别列表
     *
     * @return
     */
    @RequestMapping("/listForJson")
    @ResponseBody
    public Map<String, Object> listForJson(HttpServletRequest request, int start, int length, String draw) throws Exception {
        String ticketId = request.getParameter("ticketId");
        String enterpriseId = request.getParameter("enterpriseId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        if (StringUtils.isBlank(enterpriseId)) {
            enterpriseId = user.getEnterpriseId();
        }

        int total = 0;
        List<OperationLicenceVo> list = null;

        //构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);

        //构造分页对象
        PagingParameter paging = DataTablesUtils.generatePagingParameter(start, length);

        //统计符合条件的记录数
        total = licenceService.count(filterConditions.getWhere(), filterConditions.getParamMap(), enterpriseId);

        if (total > 0) {
            //获取符合条件的记录集合
            list = licenceService.list(filterConditions.getWhere(), filterConditions.getParamMap(), paging, enterpriseId);
        }
        //构造并返回结果集
        return DataTablesUtils.generateResult(draw, total, total, list);
    }

    /**
     * 返回json格式的危废类别列表
     *
     * @return
     */
    @RequestMapping("/listForJsonSubLic")
    @ResponseBody
    public Map<String, Object> listForJsonSubLic(HttpServletRequest request, int start, int length, String draw) throws Exception {
        String ticketId = request.getParameter("ticketId");
        String enterpriseId = request.getParameter("enterpriseId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        if (StringUtils.isBlank(enterpriseId)) {
            enterpriseId = user.getEnterpriseId();
        }
        int total = 0;
        List<OperationLicenceVo> list = null;
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);
        PagingParameter paging = DataTablesUtils.generatePagingParameter(start, length);
        total = licenceService.submitCount(filterConditions.getWhere(), filterConditions.getParamMap(), enterpriseId);
        if (total > 0) {
            list = licenceService.getSubmitList(filterConditions.getWhere(), filterConditions.getParamMap(), paging, enterpriseId);
        }
        return DataTablesUtils.generateResult(draw, total, total, list);
    }


    /**
     * 许可证详情
     *
     * @param request
     * @return
     * @throws Exception
     * @throws DaoAccessException
     */
    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        String ticketId = request.getParameter("ticketId");
        String enterpriseId = request.getParameter("enterpriseId");
        // 获取有效许可证
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        if (StringUtils.isBlank(enterpriseId)) {
            enterpriseId = user.getEnterpriseId();
        }
        if (!licenceService.isEnterpriseVaild(ticketId)) {
            mv.setViewName(PATH + "prompt");
            mv.addObject("promptType", "1");
        } else {
            List<OperationLicence> validLicenceIds = licenceService.listLicenceByEnterId(enterpriseId);
            //增加未审核的列表
            List<OperationLicence> submitLics = licenceService.listLiceneByEnterpriseIdAndStatus(enterpriseId, CodeTypeConstant.LIC_AUDIT_SUBMIT);
            if ((validLicenceIds == null || validLicenceIds.size() == 0) && (submitLics == null || submitLics.size() == 0)) {
                mv.setViewName(PATH + "prompt");
                boolean result = licenceService.getUsedLicId(enterpriseId);//查询是否有无效许可证
                if (result) {
                    mv.addObject("usedLicence", "1");
                }
                //没有有效的许可证并且有有效的提交的许可证，才走审核许可证列表
            } else if (validLicenceIds != null && validLicenceIds.size() > 1) {
                mv.setViewName(PATH + "prompt2");
                //有正在审核的许可证
            } else if ((validLicenceIds == null || validLicenceIds.size() == 0) && (submitLics != null && submitLics.size() > 0)) {
                mv.setViewName(PATH + "submitLicsList");
                mv.addObject("ticketId", ticketId);
                mv.addObject("enterpriseId", enterpriseId);
                mv.addObject("submit", "submit");
            } else {
                mv.setViewName("redirect:/" + "licence/licenceView.htm");
                mv.addObject("id", validLicenceIds.get(0).getId());
            }
        }

        mv.addObject("noTopLeftButtom", request.getParameter("noTopLeftButtom"));
        mv.addObject("ticketId", ticketId);
        mv.addObject("enterpriseId", enterpriseId);
        mv.addObject("isSysUser", Util.isSysUser(request.getParameter("ticketId")));// 判断是不是平台管理员
        return mv;
    }

    /**
     * 当前有效许可证详情
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/licenceView")
    public ModelAndView licenceView(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "detail");
        String licenceId = request.getParameter("id");

        // 许可证危废信息查询
        OperationLicenceVo licence = null;
        List<OperationLicenceItemVo> listVo = null;
        licence = licenceService.getlicenceApprovedById(licenceId);
        listVo = licenceService.getWasteInfoByLicenceId(licenceId);
        mv.addObject("operationLicence", licence);
        mv.addObject("listItemVo", listVo);
        mv.addObject("id", licenceId);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("noTopLeftButtom", request.getParameter("noTopLeftButtom"));
        mv.addObject("isSysUser", Util.isSysUser(request.getParameter("ticketId")));// 判断是不是平台管理员
        return mv;
    }

    /**
     * 许可证查看公共页面
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/commView")
    public ModelAndView commView(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "commView");
        String licenceId = request.getParameter("id");

        // 许可证危废信息查询
        OperationLicenceVo licence = null;
        List<OperationLicenceItemVo> listVo = null;
        try {
            licence = licenceService.getlicenceApprovedById(licenceId);
            listVo = licenceService.getWasteInfoByLicenceId(licenceId);
        } catch (Exception e) {
            logger.error("查询许可证公共页面异常", e);
            mv.setViewName(Constant.URL_500);
        }
        mv.addObject("operationLicence", licence);
        mv.addObject("listItemVo", listVo);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("isSysUser", Util.isSysUser(request.getParameter("ticketId")));// 判断是不是平台管理员
        return mv;
    }

    /**
     * 确认许可证详情
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkLicenceDetail")
    public Result<Map<String, String>> checkLicenceDetail(HttpServletRequest request) throws Exception {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String licenceId = request.getParameter("id");

        List<OperationLicenceDetail> details = licenceDetailService.getlicenceDetails(licenceId);
        if (details == null || details.size() == 0) {
            result.setStatus(ResultStatus.Error);
            result.getInfoList().add("许可证详情不能为空");
        } else {
            result.setStatus(ResultStatus.Success);
        }
        return result;
    }


    /**
     * 提交审核
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/submitAudit")
    public Result<Map<String, String>> submitAudit(HttpServletRequest request) throws Exception {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String licenceId = request.getParameter("id");
        String ticketId = request.getParameter("ticketId");

        String auditStatusCode = licenceService.getAuditStatusByLicenceId(licenceId);
        if (auditStatusCode.equals(CodeTypeConstant.LIC_AUDIT_CREATE)) {
            licenceService.submitAudit(licenceId, ticketId);//提交审核
        }
        dataMap.put("auditStatusCode", auditStatusCode);
        result.setStatus(ResultStatus.Success);


        result.setData(dataMap);
        return result;
    }

    /**
     * 根据许可证ID判断许可证的审核状态 zhugl
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAuditStatus")
    public Result<Map<String, String>> getAuditStatus(HttpServletRequest request) throws Exception {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String licenceId = request.getParameter("id");
        //获取该许可证的审核状态
        String auditStatusCode = licenceService.getAuditStatusByLicenceId(licenceId);
        dataMap.put("auditStatusCode", auditStatusCode);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    /**
     * 删除许可证
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteLicences")
    public Result<Map<String, Object>> deleteLicences(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String[] licenceIdArrays = request.getParameterValues("id");
        String ticketId = request.getParameter("ticketId");
        List<String> licenceIds = Util.stringArrayConvertToList(licenceIdArrays);
        boolean success = licenceService.deleteLicenceByIds(licenceIds, ticketId);
        dataMap.put("isSuccess", success);
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    /**
     * 许可证查看公共页面
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/licenceUpload")
    public ModelAndView upload(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "licenceUpload");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("enterpriseId", request.getParameter("enterpriseId"));
        return mv;
    }
}
