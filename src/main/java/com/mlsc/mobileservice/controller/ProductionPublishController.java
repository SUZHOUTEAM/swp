package com.mlsc.mobileservice.controller;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 手机端企业产废购买Controller
 * 
 * @author zhugl
 */
@Controller
@RequestMapping("/mobileProductionPublishService")
@Scope("prototype")
public class ProductionPublishController {
	
	@Autowired
    private ICodeTypeService codeTypeService;
	
	/**
     * 获取单位和企业ID
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUnitList")
    public Result<List<CodeValue>> getCodeValuesTypeCode(HttpServletRequest request) throws Exception{
        Result<List<CodeValue>> result = new Result<List<CodeValue>>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
    	List<String> sqlInfoList=new ArrayList<String>();
    	sqlInfoList.add(user.getEnterpriseId());
    	sqlInfoList.add(user.getRegisterAddr());
    	sqlInfoList.add(user.getPhoneNo());
        List<CodeValue> codeValues=codeTypeService.getCodeValuesTypeCode(CodeTypeConstant.UNIT_TYPE);
        try {
        	result.setData(codeValues);
        	result.setSqlInfoList(sqlInfoList);
            result.setStatus(ResultStatus.Success);
		} catch (Exception e) {
	        result.setStatus(ResultStatus.Failure);
		}
        return result;
    }


}
