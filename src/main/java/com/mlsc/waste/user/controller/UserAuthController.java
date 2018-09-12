package com.mlsc.waste.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.UserRole;
import com.mlsc.waste.utils.UserStatus;
import com.mlsc.waste.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userAuth")
@Scope("prototype")
public class UserAuthController {
	
	private static final String PATH = "User/";
	
	
    @Autowired
    private UserExtendedService userExtendedService;

	
	
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(PATH + "userAuthList");
		mv = Util.stopMessageDisplay(mv, request);
		mv.addObject("ticketId", request.getParameter("ticketId"));
		List<CodeValue> userStatusList = new ArrayList<CodeValue>();
		mv.addObject("userStatus", userStatusList);
		return mv;
	}
	
	@RequestMapping("/getPendingUserList")
	public Result<Map<String, Object>> getAllUserList(HttpServletRequest request) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		String ticketId = request.getParameter("ticketId");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			User user = LoginStatusUtils.getUserByTicketId(ticketId);
			UserExtended userExtended = userExtendedService.getUserExtendedByUserId(user.getUserId());
			if (UserRole.ADMIN.getRoleCode().equals(userExtended.getRole())&& UserStatus.PASS.getStatusCode().equals(userExtended.getUserStatus())) {
				List<UserApproveVo> pendingApproveUserList = userExtendedService.listUserApproVo(user.getEnterpriseId(),
				UserStatus.SUBMIT.getStatusCode());
				dataMap.put("pendingApproveUserList", JSONArray.toJSON(pendingApproveUserList));
				result.setStatus(ResultStatus.Success);
			}
		} catch (Exception e) {
			result.setStatus(ResultStatus.Failure);
		}
		result.setData(dataMap);
		return result;
	}
    

/*	@RequestMapping("/listForJson")
	@ResponseBody
	public Map<String, Object> listForJson(HttpServletRequest request,
			int start, int length, String draw) {
		List<RPCSysEnterpriseBaseVo> list = null;
		int total = 0;

		// 构造过滤条件
		FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);

		// 构造分页对象
		PagingParameter paging = DataTablesUtils.generatePagingParameter(start,length);

		// 统计符合条件的记录数
		total = enterpriseManagementService.getAllEnterpriseBaseCount(filterConditions.getWhere(), filterConditions.getParamMap());

		if (total > 0) {
			// 获取符合条件的记录集合
			list = enterpriseManagementService.getAllEnterpriseBaseList(filterConditions.getWhere(),filterConditions.getParamMap(), paging);
		}
		
		List<CodeValue> codeValueList = codeTypeService.getCodeValuesTypeCode(CodeTypeConstant.USER_STATUS);
		logger.info("---------------行业list页面获取数据结束---------------");
		// 构造并返回结果集
		return DataTablesUtils.generateResult(draw, total, total, list);
	}*/
	
	
	
}
