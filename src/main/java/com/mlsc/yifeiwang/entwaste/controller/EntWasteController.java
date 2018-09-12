package com.mlsc.yifeiwang.entwaste.controller;


import com.alibaba.fastjson.JSONArray;
import com.mlsc.yifeiwang.waste.entity.WasteName;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.entwaste.model.EntWasteParams;
import com.mlsc.yifeiwang.entwaste.service.IEntWasteService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
@Controller("entWasteController")
@RequestMapping(value = "/entWaste", method = {RequestMethod.GET, RequestMethod.POST})
@Scope("prototype")
public class EntWasteController {

    private final static Logger logger = LoggerFactory.getLogger(EntWasteController.class);

    private static final String PATH = "EnterpriseWaste/";

    @Autowired
    private IEntWasteService entWasteService;

    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;

    /**
     * 产废列表
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "list");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 处置企业，服务商，系统管理员查看产废企业的产废列表
     *
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/listView")
    public ModelAndView listView(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView(PATH + "listView");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        mv.addObject("enterpriseId", request.getParameter("enterpriseId"));
        return mv;
    }

    /**
     * 获取八位码的下拉列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getWasteIdDropDownList")
    public Map<String, Object> getWasteIdDropDownList(HttpServletRequest request) {
        Map<String, Object> datamap = null;
        String keyword = request.getParameter("keyword");
        try {
            datamap = entWasteService.getWasteDropdownList(keyword);
        } catch (Exception e) {
            logger.error("获取八位码的下拉列表存在异常", e);
        }

        return datamap;
    }

    /**
     * 获取危废名称的下拉列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/listWasteName")
    public Result<List<WasteName>> listWasteName(HttpServletRequest request) {
        Result<List<WasteName>> result = new Result<>();
        String wasteName = request.getParameter("keyword");
        String wasteId = request.getParameter("wasteid");
        try {
            List<WasteName> wasteNameList = entWasteService.listWasteName(wasteId, wasteName);
            result.setData(wasteNameList);
            result.setStatus(ResultStatus.Success);

        } catch (Exception e) {
            logger.error("获取危废名称的下拉列表存在异常", e);
        }

        return result;
    }

    /**
     * 根据code查询八位码是否存在,存在的话，并获取该主键ID
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/isWasteCodeExistent")
    public Result<Map<String, String>> isWasteCodeExistent(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        String code = request.getParameter("wasteCode");
        try {
            result.setStatus(ResultStatus.Success);
            String wasteId = entWasteService.getWasteIdByCode(code);
            if (StringUtils.isNotBlank(wasteId)) {
                map.put("wasteId", wasteId);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setStatus(ResultStatus.Failure);
            }
            result.setData(map);
        } catch (Exception e) {
            logger.error("获取八位码异常", e);
            result.setStatus(ResultStatus.Failure);
        }

        return result;
    }

    /**
     * 保存企业危废
     *
     * @param user
     * @param entWasteParams
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<String> saveEntWaste(User user, EntWasteParams entWasteParams) {
        Result<String> result = new Result<String>();
        List<String> errorList = new ArrayList<>();
        boolean validate = checkSaveParameter(entWasteParams, errorList);
        if (validate) {
            if (StringUtils.isBlank(entWasteParams.getEntId())) {
                entWasteParams.setEntId(user.getEnterpriseId());
            }
            try {
                String id = entWasteService.saveEntWaste(user, entWasteParams);
                result.setStatus(ResultStatus.Success);
                result.setData(id);
            } catch (Exception e) {
                result.setStatus(ResultStatus.Error);
                result.getInfoList().add("保存企业危废时异常");
                logger.error("保存企业危废时异常", e);
            }
        } else {
            result.setInfoList(errorList);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    private boolean checkSaveParameter(EntWasteParams entWasteParams, List<String> errorList) {
        boolean validate = true;
        if (entWasteParams == null) {
            errorList.add("新增危废信息为空");
            validate = false;
        }
        if (StringUtils.isBlank(entWasteParams.getEntId())) {
            errorList.add("新增危废企业主键为空");
            validate = false;

        }
        if (StringUtils.isBlank(entWasteParams.getWasteId())) {
            errorList.add("8位码不能为空");
            validate = false;

        }
        if (StringUtils.isBlank(entWasteParams.getWasteName())) {
            errorList.add("危废名称不能为空");
            validate = false;

        }
        if (StringUtils.isBlank(entWasteParams.getUnitCode())) {
            errorList.add("单位不能为空");
            validate = false;

        }
        return validate;
    }

    /**
     * 产废企业-产废列表
     *
     * @param user
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/listEntWasteByEntId", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<EntWasteModel>> listEntWaste(User user, String entId) throws Exception {
        Result<List<EntWasteModel>> result = new Result<List<EntWasteModel>>();
        EntWasteParams entWasteParams = new EntWasteParams();
        List<String> infoList = new ArrayList<String>();
        boolean validate = checkListEntWasteParam(user, entId, infoList);
        if (validate) {
            try {
                if (StringUtils.isBlank(entId)) {
                    entWasteParams.setEntId(user.getEnterpriseId());
                } else {
                    entWasteParams.setEntId(entId);
                }

                List<EntWasteModel> entWasteList = entWasteService.listEntWasteByEntId(entWasteParams);
                result.setData(entWasteList);
                result.setStatus(ResultStatus.Success);
            } catch (Exception e) {
                logger.error("加载企业危废列表时异常", e);
                infoList.add("加载企业危废列表时异常");
                result.setStatus(ResultStatus.Error);
            }
        } else {
            result.setStatus(ResultStatus.Error);
            result.setInfoList(infoList);
        }

        return result;
    }

    private boolean checkListEntWasteParam(User user, String entId, List<String> infoList) {
        boolean validate = true;
        if (StringUtils.isBlank(user.getEnterpriseId()) && StringUtils.isBlank(entId)) {
            infoList.add("企业信息为空");
            validate = false;
        }
        return validate;
    }

    /**
     * 企业危废列表
     *
     * @param user
     * @param entWasteParams
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listEntWaste")
    public Result<Map<String, Object>> listEntWaste(User user, @RequestBody EntWasteParams entWasteParams, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<EntWasteModel> entWasteList = entWasteService.listEntWaste(user, entWasteParams, pagingParameter);
            dataMap.put("entWasteList", JSONArray.toJSON(entWasteList));
            dataMap.put("paging", JSONArray.toJSON(pagingParameter));
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 产废企业-产废列表通过许可证id
     *
     * @param user
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/listEntWasteByLicenceId", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<EntWasteModel>> listEntWasteByLicenceId(User user, String licenceId) throws Exception {
        Result<List<EntWasteModel>> result = new Result<List<EntWasteModel>>();
        try {
            List<EntWasteModel> entWasteList = entWasteService.listEntWasteByLicenceId(licenceId, user);
            result.setData(entWasteList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 产废详情
     *
     * @param user
     * @param entWasteParams
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getEntWasteDetailById", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<EntWasteModel> getEntWasteDetailById(User user, @RequestBody EntWasteParams entWasteParams) throws Exception {
        Result<EntWasteModel> result = new Result<EntWasteModel>();
        List<String> infoList = new ArrayList<String>();
        boolean validate = checkEntWasteDetailPram(infoList, entWasteParams);
        if (validate) {
            try {
                EntWasteModel entWasteModel = entWasteService.getEntWasteDetailById(entWasteParams);
                result.setData(entWasteModel);
                result.setStatus(ResultStatus.Success);
            } catch (Exception e) {
                logger.error("获取企业危废详情时异常", e);
                infoList.add("获取企业危废详情时异常");
                result.setStatus(ResultStatus.Error);
            }
        } else {
            result.setStatus(ResultStatus.Error);
            result.setInfoList(infoList);
        }
        return result;
    }

    private boolean checkEntWasteDetailPram(List<String> infoList, EntWasteParams entWasteParams) {
        boolean validate = true;
        if (StringUtils.isBlank(entWasteParams.getEntWasteId())) {
            infoList.add("企业危废为空");
            validate = false;
        }
        return validate;
    }

    /**
     * 八位码名称是否重复
     *
     * @param user
     * @param entWasteParams
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/checkWasteNameDuplicate", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> checkWasteNameDuplicate(User user, EntWasteParams entWasteParams) throws Exception {
        Result<Boolean> result = new Result<Boolean>();
        List<String> infoList = new ArrayList<String>();
        boolean validate = checkWasteNameDuplicateParam(infoList, entWasteParams);
        if (validate) {
            try {
                if (StringUtils.isBlank(entWasteParams.getEntId())) {
                    entWasteParams.setEntId(user.getEnterpriseId());
                }
                boolean isExist = entWasteService.isExistEnterpriseWaste(entWasteParams);
                result.setData(isExist);
                result.setStatus(ResultStatus.Success);
            } catch (Exception e) {
                logger.error("确认危废名称是否重复时异常", e);
                infoList.add("确认危废名称是否重复时异常");
                result.setStatus(ResultStatus.Error);
            }
        } else {
            result.setStatus(ResultStatus.Error);
            result.setInfoList(infoList);
        }

        return result;
    }

    private boolean checkWasteNameDuplicateParam(List<String> infoList, EntWasteParams entWasteParams) {
        boolean validate = true;
        if (StringUtils.isBlank(entWasteParams.getWasteName())) {
            infoList.add("危废名称不能为空");
            validate = false;
        }
        if (StringUtils.isBlank(entWasteParams.getWasteId())) {
            infoList.add("8位码不能为空");
            validate = false;
        }
        return validate;
    }


    /**
     * 更新企业危废
     *
     * @param ticketId
     * @param entWasteModel
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/updateEntWaste", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> updateEntWaste(String ticketId, @RequestBody EntWasteModel entWasteModel) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        Result<Boolean> result = new Result<Boolean>();
        try {
            if (entWasteModel.getInquiried()) {
                result.setData(false);
                result.setStatus(ResultStatus.Error);
                return result;
            }
            boolean updateResult = entWasteService.updateEntWaste(user, entWasteModel);
            result.setData(updateResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setData(false);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 删除企业危废-没有被询过价的企业危废才可被删除
     *
     * @param ticketId
     * @param entWasteModels
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/deleteEntWaste", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Boolean> deleteEntWaste(String ticketId, @RequestBody List<EntWasteModel> entWasteModels) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean validate = checkDeleteEntWaste(entWasteModels);
            if (validate) {
                boolean deleteResult = entWasteService.deleteEntWaste(user, entWasteModels);
                result.setData(deleteResult);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setData(false);
                result.setStatus(ResultStatus.Error);
            }

        } catch (Exception e) {
            result.setData(false);
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    private boolean checkDeleteEntWaste(List<EntWasteModel> entWasteModels) {
        for (EntWasteModel entWasteModel : entWasteModels) {
            if (StringUtils.isBlank(entWasteModel.getEntWasteId()) || entWasteModel.getInquiried()) {
                return false;
            }
        }
        return true;
    }

}
