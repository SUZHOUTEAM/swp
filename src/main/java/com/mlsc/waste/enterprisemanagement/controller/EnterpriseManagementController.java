package com.mlsc.waste.enterprisemanagement.controller;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.solr.model.EnterpriseIndex;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprisemanagement.service.EnterpriseManagementService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.LoginStatusUtils;
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
import java.util.List;
import java.util.Map;

/**
 * 平台管理员管理企业Controller
 * 
 * @author zhugl
 */
@Controller
@RequestMapping("/enterprisemanagement")
@Scope("prototype")
public class EnterpriseManagementController {

    private final static Logger logger = LoggerFactory.getLogger(EnterpriseManagementController.class);

    private static final String PATH = "EnterpriseManagement/";
    
    @Autowired
    private EnterpriseManagementService enterpriseManagementService;
    
    @Autowired
    private ICodeTypeService codeTypeService;
    
    /**
     * 列出所有行业
     * 
     * @param request 编辑保存后需要弹出提示框，传递参数
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "enterpriseList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("auditStatusList", codeTypeService.getCodeValuesTypeCode(CodeTypeConstant.USER_EVENT_STATUS));
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
        List<RPCSysEnterpriseBaseVo> list = null;
        int total = 0;
        
        //构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);
        
        //构造分页对象
        PagingParameter paging=DataTablesUtils.generatePagingParameter(start, length);
        
        //统计符合条件的记录数
        total = enterpriseManagementService.getAllEnterpriseBaseCount(filterConditions.getWhere(), filterConditions.getParamMap());
        
        if(total > 0){
            //获取符合条件的记录集合
            list = enterpriseManagementService.getAllEnterpriseBaseList(filterConditions.getWhere(), filterConditions.getParamMap(), paging);
        }
        logger.info("---------------行业list页面获取数据结束---------------");
        //构造并返回结果集
        return DataTablesUtils.generateResult(draw, total, total, list);
    }
    
    @RequestMapping("/enterpriseIndexInitList")
    public ModelAndView enterpriseIndexList(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "enterpriseIndexList");
        mv = Util.stopMessageDisplay(mv, request);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    @RequestMapping("/enterpriseIndexList")
    @ResponseBody
    public Map<String, Object> enterpriseIndexList(HttpServletRequest request, int start, int length, String draw) {
        List<EnterpriseIndex> list = null;
        int total = 0;

        //构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);

        //构造分页对象
        PagingParameter paging=DataTablesUtils.generatePagingParameter(start, length);

        list = enterpriseManagementService.enterpriseIndexList(filterConditions.getParamMap(), paging);
        total = paging.getTotalRecord();
        logger.info("---------------行业list页面获取数据结束---------------");
        //构造并返回结果集
        return DataTablesUtils.generateResult(draw, total, total, list);
    }


    @RequestMapping("/deleteEnterpriseIndex")
    @ResponseBody
    public Result<Map<String, String>> deleteEnterpriseIndex(HttpServletRequest request){
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String enterpriseId = request.getParameter("enterpriseId");
        String[] enterpriseIds = enterpriseId.split(",");
        try {
            enterpriseManagementService.deleteEnterpriseIndex(enterpriseIds);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("删除企业索引时异常",e);
            result.setStatus(ResultStatus.Failure);
        }
        return result;
    }

    /**
     * 平台管理员审核新加入企业通过
     * @param request
     * @return
     */
    @RequestMapping("/auditEnterprisePass")
    @ResponseBody
    public Result<Map<String, String>> auditEnterprisePass(HttpServletRequest request){
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String enterpriseId = request.getParameter("enterpriseId");
        String[] enterpriseIds = enterpriseId.split(",");
        String ticketId = request.getParameter("ticketId");
        try {
            List<String> userIdList = enterpriseManagementService.updateRecords(ticketId,enterpriseIds,CodeTypeConstant
                    .USER_EVENT_STATUS_PASS);
            User userParam = new User();
            userParam.setUserIdList(userIdList);
            LoginStatusUtils.refreshUserToCacheByUserParam(userParam);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("平台管理员审核企业通过异常",e);
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 平台管理员审核新加入企业退回
     * @param request
     * @return
     */
    @RequestMapping("/auditEnterpriseRefuse")
    @ResponseBody
    public Result<Map<String, String>> auditEnterpriseRefuse(HttpServletRequest request){
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String enterpriseId = request.getParameter("enterpriseId");
        String[] enterpriseIds = enterpriseId.split(",");
        String ticketId = request.getParameter("ticketId");
        try {
            List<String> userIdList = enterpriseManagementService.updateEnterRefuse(enterpriseIds,CodeTypeConstant
                    .USER_EVENT_STATUS_REFUSED,ticketId);
            User userParam = new User();
            userParam.setUserIdList(userIdList);
            LoginStatusUtils.refreshUserToCacheByUserParam(userParam);
            result.setStatus(ResultStatus.Success);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("平台管理员审核企业退回异常",e);
            e.printStackTrace();
        }
        return result;
    }
}