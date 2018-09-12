package com.mlsc.yifeiwang.facilitator.controller;


import com.mlsc.yifeiwang.facilitator.entity.FacilitatorCustomer;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.facilitator.model.FacilitatorCustomerModel;
import com.mlsc.yifeiwang.facilitator.model.FacilitatorCustomerParam;
import com.mlsc.yifeiwang.facilitator.service.IFacilitatorCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2018-01-15
 */
@Controller
@RequestMapping("/facilitatorCustomer")
public class FacilitatorCustomerController {
    @Autowired
    private IFacilitatorCustomerService facilitatorCustomerService;

    @Autowired
    private EnterpriseService enterpriseService;


    /**
     * 服务商企业保存客户（产废企业）
     *
     * @param user
     * @param facilitatorCutomer
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveFacilitatorCustomer", method = RequestMethod.POST)
    public Result<Boolean> saveFacilitatorCutomer(User user, @RequestBody FacilitatorCustomer facilitatorCutomer) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean saveFlag = facilitatorCustomerService.saveFacilitatorCustomer(user, facilitatorCutomer);
            result.setData(saveFlag);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 服务商企业删除客户
     *
     * @param user
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteFacilitatorCustomer", method = RequestMethod.POST)
    public Result<Boolean> deleteFacilitatorCutomer(User user, @RequestBody List<String> ids) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            boolean saveFlag = facilitatorCustomerService.deleteFacilitatorCustomer(user, ids);
            result.setData(saveFlag);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 列表服务商客户列表
     *
     * @param user
     * @param customerParam
     * @param pagingParameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listFacilitatorCustomer", method = RequestMethod.POST)
    public Result<Map<String, Object>> listFacilitatorCutomer(User user, FacilitatorCustomerParam customerParam, PagingParameter pagingParameter) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            customerParam.setFacilitatorEntId(user.getEnterpriseId());
            List<FacilitatorCustomerModel> sysEnterpriseBases = facilitatorCustomerService.listFacilitatorCustomer(customerParam, pagingParameter);
            resultMap.put("customerList", sysEnterpriseBases);
            resultMap.put("pagingParameter", pagingParameter);
            result.setData(resultMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 服务商新增客户时-输入企业名称时，根据负责区域时显示想相关联的产废企业名称列表
     *
     * @param facilitatorCustomerParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listBindEnterprise", method = RequestMethod.POST)
    public Result<List<FacilitatorCustomerModel>> listBindEnterprise(User user, FacilitatorCustomerParam facilitatorCustomerParam) {
        Result<List<FacilitatorCustomerModel>> result = new Result<List<FacilitatorCustomerModel>>();
        try {
            facilitatorCustomerParam.setFacilitatorEntId(user.getEnterpriseId());
            List<FacilitatorCustomerModel> sysEnterpriseBases = facilitatorCustomerService.listBindEnterprise(facilitatorCustomerParam);
            result.setData(sysEnterpriseBases);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 服务商新增客户时-可自行新增产废企业
     *
     * @param ticketId
     * @param user
     * @param enterpriseBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveProductEnt", method = RequestMethod.POST)
    public Result<String> saveProductEnt(String ticketId, User user, String entType, RPCSysEnterpriseBase enterpriseBase) {
        Result<String> result = new Result<>();
        try {
            if (StringUtils.isNotEmpty(enterpriseBase.getEntName())) {
                String shortName = enterpriseService.getShortName(enterpriseBase.getEntName());
                enterpriseBase.setShortName(shortName);
                String entId = facilitatorCustomerService.saveProductionEnt(ticketId, entType, enterpriseBase);
                result.setData(entId);
                result.setStatus(ResultStatus.Success);
            } else {
                result.setStatus(ResultStatus.Success);
            }


//            List<String> infoList = new ArrayList<String>();
//            boolean validate = checkAreaCoverage(enterpriseBase.getCantonCode(), user.getEnterpriseId(),infoList);
//            if (validate) {
//                facilitatorCustomerService.saveProductionEnt(ticketId, enterpriseBase);
//                result.setData(true);
//                result.setStatus(ResultStatus.Success);
//            } else {
//                result.setData(false);
//                result.setStatus(ResultStatus.Success);
//                result.setInfoList(infoList);
//            }

        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 获取服务商客户资质文件
     *
     * @param facilitatorCustomerParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCustomerImg", method = RequestMethod.POST)
    public Result<FacilitatorCustomerModel> getCustomerImg(User user, FacilitatorCustomerParam facilitatorCustomerParam) {
        Result<FacilitatorCustomerModel> result = new Result<>();
        try {
            FacilitatorCustomerModel  customerModel = facilitatorCustomerService.getCustomerImg(facilitatorCustomerParam);
            result.setData(customerModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


}
