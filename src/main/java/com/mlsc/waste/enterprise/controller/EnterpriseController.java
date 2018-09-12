package com.mlsc.waste.enterprise.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.exception.BusinessException;
import com.mlsc.common.util.Base64Util;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.solr.model.EnterpriseIndex;
import com.mlsc.waste.enterprise.model.*;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业信息管理Controller
 *
 * @author sunjl
 */
@Controller
@RequestMapping("/enterprise")
@Scope("prototype")
public class EnterpriseController {

    private final static Logger logger = LoggerFactory.getLogger(EnterpriseController.class);

    private static final String PATH = "Enterprise/";

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    private IRPCServiceClient client;

    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;


    @RequestMapping("/czDetail")
    public ModelAndView czDetail(HttpServletRequest request, String enterpriseId) {
        ModelAndView mv = new ModelAndView(PATH + "czDetail");
        String ticketId = request.getParameter("ticketId");
        String breadcrumbName = request.getParameter("breadcrumbName");
        RPCSysEnterpriseBaseVo enterpriseBaseVo = enterpriseBaseService.getEnterpriseByEntId(enterpriseId);
        mv.addObject("ticketId", ticketId);
        mv.addObject("enterprise", enterpriseBaseVo);
        mv.addObject("breadcrumbName", breadcrumbName);
        return mv;
    }

    @RequestMapping("/facilitatorDetail")
    public ModelAndView facilitatorDetail(HttpServletRequest request, String enterpriseId) {
        ModelAndView mv = new ModelAndView(PATH + "facilitatorDetail");
        String ticketId = request.getParameter("ticketId");
        String breadcrumbName = request.getParameter("breadcrumbName");
        RPCSysEnterpriseBaseVo enterpriseBaseVo = enterpriseBaseService.getEnterpriseByEntId(enterpriseId);
        mv.addObject("ticketId", ticketId);
        mv.addObject("enterprise", enterpriseBaseVo);
        mv.addObject("breadcrumbName", breadcrumbName);
        return mv;
    }

    @RequestMapping("/czDetailExample")
    public ModelAndView czDetail(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "czDetailExample");
        String ticketId = request.getParameter("ticketId");
        mv.addObject("ticketId", ticketId);
        return mv;
    }

    @RequestMapping("/czEdit")
    public ModelAndView czEdit(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "czEdit");
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        RPCSysEnterpriseBaseVo enterpriseBaseVo = enterpriseBaseService.getEnterpriseByEntId(user.getEnterpriseId());
        mv.addObject("ticketId", ticketId);
        mv.addObject("enterprise", enterpriseBaseVo);
        return mv;
    }


    private void initPagingParameter(PagingParameter pagingParameter) {
        pagingParameter.setPageSize(6);
        if (pagingParameter.getPageIndex() == 0) {
            pagingParameter.setPageIndex(1);
        }
        int start = (pagingParameter.getPageIndex() - 1) * pagingParameter.getPageSize();
        pagingParameter.setStart(start);
        pagingParameter.setLimit(6);
    }


    /**
     * 企业基本信息新增页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "add");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("isSysUser", Util.isSysUser(request.getParameter("ticketId")));// 判断是不是平台管理员
        return mv;
    }

    /**
     * 代理信息登记列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/agencyList")
    public ModelAndView agencyList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "agencyList");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 新增或编辑代理信息登记
     *
     * @param request
     * @return
     */
    @RequestMapping("/addAgency")
    public ModelAndView addAgency(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "addAgency");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 企业类型新增页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addEnterpriseType")
    public ModelAndView addEnterpriseType(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "addEnterpriseType");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("enterpriseId", request.getParameter("enterpriseId"));
        mv.addObject("isSysUser", Util.isSysUser(request.getParameter("ticketId")));// 判断是不是平台管理员
        return mv;
    }

    /**
     * 企业基本信息编辑页面
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/modifyEnterprise")
    public ModelAndView modifyEnterprise(HttpServletRequest request) throws DaoAccessException {
        ModelAndView mv = new ModelAndView(PATH + "modifyEnterprise");
        String ticketId = request.getParameter("ticketId");
        String enterpriseId = request.getParameter("modifyEnterpriseId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        RPCSysEnterpriseBaseVo rpcSysEnterpriseBaseVo = new RPCSysEnterpriseBaseVo();
        try {
            if (StringUtils.isBlank(enterpriseId)) {
                enterpriseId = user.getEnterpriseId();
            }
            rpcSysEnterpriseBaseVo = enterpriseBaseService.getEnterpriseByEntId(enterpriseId);

        } catch (Exception e) {
            logger.error("企业编辑页面初始化异常", e);
            mv.setViewName(Constant.URL_500);
        }
        mv.addObject("ticketId", ticketId);
        mv.addObject("enterprise", rpcSysEnterpriseBaseVo);
        mv.addObject("isSysUser", Util.isSysUser(request.getParameter("ticketId")));// 判断是不是平台管理员
        return mv;
    }


    /**
     * 保存企业基本信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveEnterpriseInfo")
    public Result<String> saveEnterpriseInfo(HttpServletRequest request, String ticketId, String businessCode) {
        Result<String> result = new Result<String>();
        RPCSysEnterpriseBase enterpriseBase = prepareEnterpriseInfo(ticketId, request);
        try {
            String enterpriseId = enterpriseService.saveEnterpriseInformation(ticketId, enterpriseBase);
            if (StringUtils.isNotBlank(businessCode)) {
                uploadfileService.updateUploadFileByBusinessCode(businessCode, enterpriseId);
            }
            result.setStatus(ResultStatus.Success);
            result.getInfoList().add("已成功保存企业基本信息");
            result.setData(enterpriseId);
        } catch (BusinessException e) {
            result.getInfoList().add(e.getMessage());
            result.setStatus(ResultStatus.Failure);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            result.getInfoList().add(Constant.SYS_MSG);
        }
        return result;
    }

    /**
     * 更新企业基本信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateEnterpriseInfo")
    public Result<Map<String, String>> updateEnterpriseInfo(HttpServletRequest request, RPCSysEnterpriseBase enterpriseBase) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String ticketId = request.getParameter("ticketId");
        try {
            // 更新企业信息
            String latitudeAndLongitude = request.getParameter("latitudeAndLongitude");
            RPCSysEnterpriseBase enterpriseBaseVo = enterpriseService.updateEnterpriseBase(ticketId, enterpriseBase, latitudeAndLongitude);
            enterpriseService.updateEnterpriseNewField(enterpriseBase);
            result.setStatus(ResultStatus.Success);
            result.getInfoList().add("已成功更新企业基本信息");
        } catch (BusinessException e) {
            result.getInfoList().add(e.getMessage());
            result.setStatus(ResultStatus.Failure);
        } catch (Exception e) {
            result.getInfoList().add(Constant.SYS_MSG);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveAllEnterpriseInfo")
    public Result<String> saveAllEnterpriseInfo(HttpServletRequest request, String ticketId, String businessCode) {
        Result<String> result = new Result<String>();
        RPCSysEnterpriseBase enterpriseBase = prepareEnterpriseInfo(ticketId, request);
        try {
            String enterpriseId = enterpriseService.saveEnterpriseInformation(ticketId, enterpriseBase);
            if (StringUtils.isNotBlank(businessCode)) {
                uploadfileService.updateUploadFileByBusinessCode(businessCode, enterpriseId);
            }
            String entType = request.getParameter("entType");
            Map<String, String> enterpriseTypeMap = new HashMap<>();
            enterpriseTypeMap.put(entType, "on");
            String responsibleArea = request.getParameter("responsibleArea");
            // 保存企业类型
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            enterpriseService.saveEnterpriseType(ticketId, enterpriseId, enterpriseTypeMap, responsibleArea);
            enterpriseService.informSysAdminUser(enterpriseId, user);
            result.setStatus(ResultStatus.Success);
            result.getInfoList().add("已成功保存企业基本信息");
            result.setData(enterpriseId);
        } catch (BusinessException e) {
            result.getInfoList().add(e.getMessage());
            result.setStatus(ResultStatus.Failure);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            result.getInfoList().add(Constant.SYS_MSG);
        }
        return result;
    }


    /**
     * 企业code重复性检查
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @ResponseBody
    @RequestMapping("/isEnterprisecodeExistent")
    public Result<Map<String, String>> queryByCode(HttpServletRequest request, User user, RPCSysEnterpriseBase enterpriseBase) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String enterpriseId = user.getEnterpriseId();
        String msg = null;
        String enterpriseCode = request.getParameter("enterprisecode");
        try {
            boolean exist = enterpriseService.checkEnterpriseCodeExist(enterpriseId, enterpriseCode);
            result.setStatus(ResultStatus.Success);
            if (exist) {
                result.setStatus(ResultStatus.Failure);
                msg = "企业代码" + enterpriseCode + "已存在";
                dataMap.put("msg", msg);
                result.setData(dataMap);
            }
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 准备企业基本信息
     *
     * @param request
     * @return
     */
    private RPCSysEnterpriseBase prepareEnterpriseInfo(String ticketId, HttpServletRequest request) {
        RPCSysEnterpriseBase enterpriseBase = new RPCSysEnterpriseBase();
        try {


            String cantonCode = request.getParameter("cantonCode");
            String entName = request.getParameter("enterprisename");
            enterpriseBase.setEntName(entName);
            enterpriseBase.setShortName(enterpriseService.getShortName(entName));
            enterpriseBase.setEntCode(request.getParameter("enterprisecode"));
            enterpriseBase.setEntAddress(request.getParameter("enterpriseaddress"));
            if (StringUtils.isBlank(cantonCode)) {
                enterpriseBase.setCantonCode(enterpriseService.getCanonCode(ticketId, request.getParameter("district"), request.getParameter("city")));
            } else {
                enterpriseBase.setCantonCode(cantonCode);
            }
            if (request.getParameter("latitudeAndLongitude") != null) {
                String[] posxAndPosy = request.getParameter("latitudeAndLongitude").split(", ");
                enterpriseBase.setPosx(posxAndPosy[0]);
                enterpriseBase.setPosy(posxAndPosy[1]);
            }

            enterpriseBase.setNameSpelling("");
            enterpriseBase.setLegalName("");
            enterpriseBase.setContacts("");
            enterpriseBase.setContactsTel("");
            enterpriseBase.setContactsEMail("");
            enterpriseBase.setFax("");
            enterpriseBase.setZipCode("");
        } catch (Exception e) {
            logger.error("获取企业信息数据保存异常", e);
            e.printStackTrace();
        }
        return enterpriseBase;
    }

    /**
     * 保存企业类型
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveEnterpriseType")
    public Result<Map<String, String>> saveEnterpriseType(HttpServletRequest request, String ticketId, String enterpriseId) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String msg = null;

        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        if (StringUtils.isBlank(enterpriseId)) {
            enterpriseId = user.getEnterpriseId();
        }
        try {
            // 获取企业类型
            Map<String, String> enterpriseTypeMap = listEntType(request);

            String responsibleArea = request.getParameter("responsibleArea");
            // 保存企业类型
            enterpriseService.saveEnterpriseType(ticketId, enterpriseId, enterpriseTypeMap, responsibleArea);
            enterpriseService.informSysAdminUser(enterpriseId, user);
            msg = "已成功保存企业类别";
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        dataMap.put("enterpriseId", enterpriseId);
        result.setData(dataMap);
        return result;
    }

    /**
     * 获取企业类型
     *
     * @param request
     * @return
     */
    private Map<String, String> listEntType(HttpServletRequest request) {
        Map<String, String> enterpriseTypeMap = new HashMap<String, String>();
        try {
            List<CodeValue> enterpriseTypeList = enterpriseService.getDropDownListByTypeCode(CodeTypeConstant.ENTERPRISE_TYPE);
            enterpriseTypeList.forEach(entType -> {
                String isChecked = request.getParameter(entType.getCode().toLowerCase());
                if (StringUtils.isNotBlank(isChecked)) {
                    enterpriseTypeMap.put(entType.getCode(), isChecked);
                }
            });
        } catch (Exception e) {
            logger.error("获取企业类型异常", e);
        }
        return enterpriseTypeMap;
    }

    @ResponseBody
    @RequestMapping("/checkResponsibleArea")
    public Result<Boolean> checkResponsibleArea(String responsibleArea) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean checkResult = enterpriseBaseService.checkResponsibleArea(responsibleArea);
            result.setData(checkResult);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }

        return result;
    }


    /**
     * 关注或取消关注企业
     */
    @ResponseBody
    @RequestMapping("/saveOrRemoveFollow")
    public Result<Map<String, String>> saveOrRemoveFollow(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String followEnterpriseId = request.getParameter("enterpriseIdValue");
        String action = request.getParameter("action");
        String msg = null;
        try {
            enterpriseService.saveOrRemoveFollow(followEnterpriseId, action, ticketId);
            result.setStatus(ResultStatus.Success);
            if (action.equals(Constant.ENABLED)) {
                msg = "成功关注该企业";
            } else {
                msg = "成功取消关注该企业";
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    /**
     * 一键关注企业
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveFollowAndEnterWasteCircle")
    public Result<Map<String, String>> saveFollowAndEnterWasteCircle(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String entId = request.getParameter("entId");
        try {
            // 检索出企业相关信息
            enterpriseService.saveFollowAndEnterWasteCircle(ticketId, JSONArray.parseArray(entId, String.class));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }


    /**
     * 企业检索 八位码
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCodeWasteDropDownList")
    public Map<String, Object> getCodeWasteDropDownList(HttpServletRequest request) {
        Map<String, Object> datamap = null;
        String keyword = request.getParameter("keyword");
        try {
            if (StringUtils.isNoneBlank(keyword)) {
                datamap = enterpriseService.getCodeWastesDropDownList(keyword);
            }
        } catch (Exception e) {
            logger.error("获取八位码列表异常", e);
        }
        return datamap;
    }

    /**
     * 企业检索 危废名称
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getWasteNameDropDownList")
    public Map<String, Object> getWasteNameDropDownList(HttpServletRequest request) {
        Map<String, Object> datamap = null;
        String keyword = request.getParameter("keyword");
        try {
            if (StringUtils.isNoneBlank(keyword)) {
                datamap = enterpriseService.getWasteNameDropDownList(keyword);
            }
        } catch (Exception e) {
            logger.error("获取八位码列表异常", e);
        }
        return datamap;
    }


    /**
     * 门户首页根据条件检索企业
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getEnterpriseSuggest")
    public Result<Map<String, Object>> getEnterpriseSuggest(HttpServletRequest request, PagingParameter paging) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<EnterpriseIndex> listEneterpriseBaseVo = null;
        String entType = request.getParameter("entType");
        String keywords = request.getParameter("wasteCodeOrName");
        String cantonCode = request.getParameter("cantonCode");
        try {
            paging.setPageSize(Constant.DEFAULT_PAGESIZE);
            Util.initPagingParameter(paging);
            listEneterpriseBaseVo = enterpriseService.listEnterpriseSuggest(cantonCode, entType, keywords, paging);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
            logger.error("门户首页根据条件检索企业失败", e);
        }
        JSON json = (JSON) JSON.toJSON(listEneterpriseBaseVo);
        dataMap.put("entList", Base64Util.encryptBASE64(json.toString()));
        dataMap.put("paging", paging);
        dataMap.put("entType", entType);
        result.setData(dataMap);
        return result;
    }


    /**
     * 企业评价列表展示页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/loadEnterpriseEvalution")
    public ModelAndView loadEnterpriseEvalution(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "enterpriseEvaluation");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("enterpriseId", request.getParameter("enterpriseId"));
        mv.addObject("isSysUser", Util.isSysUser(request.getParameter("ticketId")));// 判断是不是平台管理员
        return mv;
    }


    /**
     * 获取企业基本信息
     *
     * @param entId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getEnterpriseInfoByEntId")
    public Result<RPCSysEnterpriseBaseVo> getEnterpriseInfoByEntId(String entId) {
        Result<RPCSysEnterpriseBaseVo> resultMap = new Result<RPCSysEnterpriseBaseVo>();
        try {
            RPCSysEnterpriseBaseVo baseVo = enterpriseBaseService.getEnterpriseByEntId(entId);
            resultMap.setData(baseVo);
            resultMap.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("获取企业类型异常", e);
            resultMap.setStatus(ResultStatus.Error);
        }
        return resultMap;
    }

}