package com.mlsc.yifeiwang.entinquiry.controller;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryParam;
import com.mlsc.yifeiwang.entinquiry.service.IEntInquiryDetailService;
import com.mlsc.yifeiwang.entinquiry.service.IEntInquiryService;
import com.mlsc.yifeiwang.user.entity.UserExtended;
import com.mlsc.yifeiwang.user.service.IUserExtendedService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Controller
@RequestMapping("/entInquiry")
public class EntInquiryController {
    private static final String PATH = "Inquiry/";
    @Autowired
    private IEntInquiryService entInquiryService;

    @Autowired
    private IUserExtendedService userExtendedService;

    @Autowired
    private IEntInquiryDetailService entInquiryDetailService;

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
     * 处置企业询价产废资源
     *
     * @param user
     * @param InquiryParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveEntInquiry", method = RequestMethod.POST)
    public Result<Boolean> saveEntInquiry(User user, @RequestBody EntInquiryParam InquiryParam) {
        Result<Boolean> result = new Result<Boolean>();
        boolean validated = validateSaveEntInquiry(result, InquiryParam);
        try {
            if (validated) {
                boolean saveFlag = entInquiryService.saveEntInquiry(user, InquiryParam);
                result.setData(saveFlag);
                result.setStatus(ResultStatus.Success);
            }
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    private boolean validateSaveEntInquiry(Result<Boolean> result, EntInquiryParam inquiryParam) {
        boolean validate = true;
        List<String> infoList = new ArrayList<String>();
        if (inquiryParam == null) {
            infoList.add("询价信息为空");
            result.setStatus(ResultStatus.Error);
            return false;
        }
        if (StringUtils.isBlank(inquiryParam.getTotalAmount())) {
            infoList.add("总量为空");
            result.setStatus(ResultStatus.Error);
            validate = false;
        }
        if (inquiryParam.getTotalPrice() == null || inquiryParam.getTotalPrice() == 0.0) {
            infoList.add("总价");
            result.setStatus(ResultStatus.Error);
            validate = false;
        }
        return validate;
    }

    /**
     * 处置企业-我的询价单
     *
     * @param user
     * @param pagingParameter
     * @param inquiryParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listEntInquiry")
    public Result<Map<String, Object>> listEntInquiry(User user, PagingParameter pagingParameter, EntInquiryParam inquiryParam) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            inquiryParam.setLoginEntId(user.getEnterpriseId());
            UserExtended userExtended = userExtendedService.getUserExtendedByUserId(user.getUserId());
            inquiryParam.setUserAngle(userExtended.getAngle());
            List<EntInquiryModel> inquiryList = entInquiryService.listWasteInquiry(user, pagingParameter, inquiryParam);
            dataMap.put("inquiryList", inquiryList);
            dataMap.put("userRole", user.getUserRole());
            dataMap.put("paging", pagingParameter);
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业-我的询价单-统计数据
     *
     * @param user
     * @param inquiryParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "countEntInquiry")
    public Result<EntInquiryModel> countEntInquiry(User user, EntInquiryParam inquiryParam) {
        Result<EntInquiryModel> result = new Result();
        try {
            inquiryParam.setLoginEntId(user.getEnterpriseId());
            EntInquiryModel inquiryModel = entInquiryService.countTotalInquiryInfo(user, inquiryParam);
            result.setData(inquiryModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业管理员更新优先级
     *
     * @param user
     * @param inquiryParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateEntInquiryPriority")
    public Result<Boolean> updateEntInquiryPriority(User user, EntInquiryParam inquiryParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean updatedResult = entInquiryService.updateEntInquiryPriority(user, inquiryParam);
            result.setData(updatedResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业管理员更新负责人
     *
     * @param user
     * @param inquiryParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateResponsibleEntInquiry")
    public Result<Boolean> updateEntInquiry(User user, EntInquiryParam inquiryParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean updatedResult = entInquiryService.updateResponsibleEntInquiry(user, inquiryParam);
            result.setData(updatedResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 产废企业-我的发布-询价详情
     *
     * @param pagingParameter
     * @param inquiryParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listEntInquiryByReleaseId")
    public Result<Map<String, Object>> listEntInquiryByReleaseId(PagingParameter pagingParameter, EntInquiryParam inquiryParam) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<EntInquiryModel> inquiryList = entInquiryService.listWasteInquiry(null, pagingParameter, inquiryParam);
            dataMap.put("inquiryList", inquiryList);
            dataMap.put("paging", pagingParameter);
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 产废企业-拒绝询价
     *
     * @param user
     * @param inquiryParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "rejectEntInquiry")
    public Result<Boolean> rejectEntInquiry(User user, EntInquiryParam inquiryParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean rejectResult = entInquiryService.rejectEntInquiry(user, inquiryParam);
            result.setData(rejectResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            result.setData(false);
        }
        return result;
    }

    /**
     * 处置企业删除询价单
     *
     * @param user
     * @param inquiryParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteEntInquiry")
    public Result<Boolean> deleteEntInquiry(User user, EntInquiryParam inquiryParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean deleteResult = entInquiryService.deleteEntInquiry(user, inquiryParam);
            result.setData(deleteResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            result.setData(false);
        }
        return result;
    }


    /**
     * 产废企业-确认询价
     *
     * @param user
     * @param inquiryParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "confirmEntInquiry")
    public Result<Boolean> confirmEntInquiry(User user, EntInquiryParam inquiryParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean confirmResult = entInquiryService.confirmEntInquiry(user, inquiryParam);
            result.setData(confirmResult);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            result.setData(false);
        }
        return result;
    }

    /**
     * 统计未处理询价
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "countUntreatedInquiry")
    public Result<Integer> countUntreatedInquiry(User user) {
        Result<Integer> result = new Result<Integer>();
        try {
            int count = entInquiryService.countUntreatedInquiry(user);
            result.setData(count);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 产废企业-获取系统报价
     *
     * @param user
     * @param releaseId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getSystemInquiryByReleaseId")
    public Result<Map<String, Object>> getSystemInquiryByReleaseId(User user, String releaseId) {
        Result<Map<String, Object>> result = new Result<>();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            EntInquiryModel entInquiryModel = entInquiryService.getReferencePrice(releaseId);
            List<EntInquiryDetailModel> noneInquiryList = entInquiryDetailService.listNoneWasteInquiryDetail(releaseId);
            resultMap.put("entInquiryModel", entInquiryModel);
            resultMap.put("noneInquiryList", noneInquiryList);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }




    @ResponseBody
    @RequestMapping(value = "listReleaseEntNameByEntId")
    public Result<List<EntInquiryModel>> listReleaseEntNameByEntId(User user) {
        Result<List<EntInquiryModel>> result = new Result<>();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List entInquiryModels = entInquiryService.listReleaseEntNameByEntId(user.getEnterpriseId());
            result.setData(entInquiryModels);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


}
