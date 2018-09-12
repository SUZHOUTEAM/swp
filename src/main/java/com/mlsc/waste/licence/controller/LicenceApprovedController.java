package com.mlsc.waste.licence.controller;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
import com.mlsc.waste.licence.model.OperationLicenceVo;
import com.mlsc.waste.licence.service.LicenceApprovedService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.datatable.DataTablesUtils;
import com.mlsc.waste.utils.datatable.FilterConditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 经营许可证审核Controller
 * @author sunjl
 */
@Controller
@RequestMapping("/licenceApproved")
@Scope("prototype")
public class LicenceApprovedController extends BaseController{
    private final static Logger logger = LoggerFactory.getLogger(LicenceApprovedController.class);

    private static final String PATH="LicenceApproved/";

    @Autowired
    private LicenceApprovedService licenceApprovedService;
    @Autowired
    private ICodeTypeService codeTypeService;
    /**
     * 待审核许可证列表
     * @param request
     * @return 
     * @throws Exception 
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "list");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("operationModeList", getListByTypeCode(CodeTypeConstant.LIC_MODE));
        mv.addObject("auditStatusList", getListByTypeCode(CodeTypeConstant.LIC_AUDIT));
        return mv;
    }
    
    /**
     * 返回json格式的许可证审核列表
     * @param request
     * @param start
     * @param length
     * @param draw
     * @return
     * @throws Exception 
     */
    @RequestMapping("/listForJson")
    @ResponseBody
    public Map<String, Object> listForJson(HttpServletRequest request,int start, int length, String draw) throws Exception {
        List<OperationLicenceVo> list = null;
        int total = 0;
        //构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);
        
        //构造分页对象
        PagingParameter paging=DataTablesUtils.generatePagingParameter(start, length);
        
        //统计符合条件的记录数
        total = licenceApprovedService.count(filterConditions.getWhere(), filterConditions.getParamMap());
        
        if(total > 0){
            //获取符合条件的记录集合
            list = licenceApprovedService.list(filterConditions.getWhere(), filterConditions.getParamMap(), paging);
        }
        //构造并返回结果集
        return DataTablesUtils.generateResult(draw, total, total, list);
    }
    
    /**
     * 查看许可证信息
     * @param request
     * @return 
     * @throws DaoAccessException 
     */
    @RequestMapping("/view")
    public ModelAndView view(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "view");
        String licenceId = request.getParameter("id");
        OperationLicenceVo licence = licenceApprovedService.getOperationLicenceVoById(licenceId);
        if (licence != null) {
            // 判断许可证的审核状态
            if (StringUtils.isNotBlank(licence.getAuditStatusCode()) && 
                CodeTypeConstant.LIC_AUDIT_PASS.equals(licence.getAuditStatusCode())) {
                licence.setAuditStatusCode(CodeTypeConstant.LIC_AUDIT_PASS);
            }
            mv.addObject("operationLicence", licence);
        } else {
            mv.addObject("msg", "该数据不存在，请重新检索数据。");
            mv.addObject("status", Constant.STATUS_INFO);
            mv.setViewName("redirect:/" + "licenceApproved/list.htm");
            mv.addObject("ticketId", request.getParameter("ticketId"));
            return mv;
        }
        // 许可证危废信息查询
        List<OperationLicenceItemVo> listVo = licenceApprovedService.getWasteInfoByLicenceId(licenceId);
        mv.addObject("listItemVo", listVo);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }
    
    /**
     * 许可证审核通过
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/confirmPass")
    public Result<Map<String, String>> confirmPass(HttpServletRequest request) throws Exception{
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] idStrings = request.getParameterValues("id");
        String ticketId = request.getParameter("ticketId");
        List<String> ids = new ArrayList<>();
        try {
            ids = Arrays.asList(idStrings);
            licenceApprovedService.confirmPass(ids, ticketId);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
            logger.error("审核许可证异常",e);
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 许可证审核退回
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/confirmReject")
    public Result<Map<String, String>> confirmReject(HttpServletRequest request) throws Exception{
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] idStrings = request.getParameterValues("id");
        String ticketId = request.getParameter("ticketId");
        List<String> ids = new ArrayList<>();
        ids = Arrays.asList(idStrings);
        licenceApprovedService.confirmReject(ids, ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }
    
    /**
     * 终止许可证
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/termination")
    public Result<Map<String, String>> termination(HttpServletRequest request) throws Exception{
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] idStrings = request.getParameterValues("id");
        String ticketId = request.getParameter("ticketId");
        List<String> ids = Arrays.asList(idStrings);
        licenceApprovedService.termination(ids, ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }
    
    /**
     * 根据typecode获取许可证审核list
     * @param typeCode
     * @return
     */
    private List<CodeValue> getListByTypeCode(String typeCode) throws Exception{
        List<CodeValue> list = codeTypeService.getCodeValuesTypeCode(typeCode);
        if (CodeTypeConstant.LIC_AUDIT.equals(typeCode)) {
            CodeValue codevo = null;
            for (Iterator<CodeValue> it = list.iterator(); it.hasNext();) {
                codevo = it.next();
                if (CodeTypeConstant.LIC_AUDIT_CREATE.equals(codevo.getCode())) {
                    it.remove();
                    break;
                }
            }
        }
        return list;
    }

}