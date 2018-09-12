package com.mlsc.mobileservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.mobileservice.model.DispositionCapacityDetailReleaseVo;
import com.mlsc.mobileservice.service.MobileProductionUserService;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityDetailReleaseService;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityItemReleaseService;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityReleaseService;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.fw.model.SysCanton;
import com.mlsc.waste.fw.service.SysCantonService;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
import com.mlsc.waste.licence.model.OperationLicenceVo;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.myenterprise.service.MyEnterpriseService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.MapUtil;
import com.mlsc.waste.wastecircle.model.CoopMsgVo;
import com.mlsc.waste.wastedirectory.service.WasteNameService;
import com.mlsc.yifeiwang.waste.service.IWasteNameService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/productionUserService")
@Scope("prototype")
public class ProductionUserServiceController {
    @Autowired
    private IWasteNameService entWasteNameService;

    @Autowired
    private UploadfileService uploadFileService;

    @Autowired
    private MyEnterpriseService myEnterpriseService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private LicenceService licenceService;

    @Autowired
    private MobileProductionUserService mobileProductionUserService;

    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    private DispositionCapacityReleaseService dispositionCapacityReleaseService;

    @Autowired
    private DispositionCapacityItemReleaseService dispositionCapacityItemReleaseService;

    @Autowired
    private DispositionCapacityDetailReleaseService dispositionCapacityDetailReleaseService;

    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private WasteNameService owasteNameService;

    @Autowired
    private SysCantonService sysCantonService;

    @ResponseBody
    @RequestMapping("/myHomePage")
    public Result<Map<String, Object>> myHomePage(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");
        String searchCondition = request.getParameter("searchCondition");
        String cantonCode = request.getParameter("cantonCode");
        String sortByCondition = request.getParameter("sortByCondition");
        if (StringUtils.isNotBlank(cantonCode)) {
            cantonCode = cantonCode.replaceAll("0*$", "");
        }

        PagingParameter pagingParameter = new PagingParameter();
        if (StringUtils.isNotBlank(pageIndex)) {
            pagingParameter.setPageIndex(Integer.valueOf(pageIndex));
        }
        if (StringUtils.isNotBlank(pageSize)) {
            pagingParameter.setPageSize(Integer.valueOf(pageSize));
        }

        List<CoopMsgVo> messageList = null;
        try {
            messageList = mobileProductionUserService.getMessageListReleaseList(ticketId, user, pagingParameter, searchCondition, cantonCode, sortByCondition);
            if (messageList != null) {
                for (CoopMsgVo message : messageList) {
                    SysCanton sysCanton = sysCantonService.queryCantonNameByEnterpriseId(message.getEnterId());
                    if (sysCanton != null) {
                        message.setRegion(sysCanton.getCantonName());
                    }

                    String enterpriseId = message.getEnterId();
                    OperationLicence operationLicence = licenceService.getValidLicIdByEnterpriseId(enterpriseId);
                    String licenceId = operationLicence.getId();
                    message.setLicenceId(licenceId);
                }
            }
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }

        dataMap.put("messageList", JSONArray.toJSON(messageList));
        dataMap.put("paging", pagingParameter);

        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/getDispositionCapacityDetailReleaseList")
    public Result<Map<String, Object>> getDispositionCapacityDetailReleaseVo(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String licenceId = request.getParameter("licenceId");
        String entId = request.getParameter("entId");
        String ticketId = request.getParameter("ticketId");
        String wasteEntId = LoginStatusUtils.getUserByTicketId(ticketId).getEnterpriseId();
        RPCSysEnterpriseBase enterpriseInfo = enterpriseService.getEnterpriseInfoById(ticketId, entId);
        List<Uploadfile> file = uploadfileService.getFileByFileTypeAndReferenceId("a", entId);
        List<DispositionCapacityDetailReleaseVo> capacityDetailReleaseList = null;
        String provinceByEntId = enterpriseService.getCantonNameByEnterpriseId(entId);
        String districtByEntId = enterpriseService.getDistrictByEnterpriseId(entId);
        List<RPCSysUser> userList = userService.getUserInfoByEntId(entId);
        String distance = calculateDistance(enterpriseInfo.getPosx(), enterpriseInfo.getPosy(), wasteEntId, ticketId);
        try {
            capacityDetailReleaseList = mobileProductionUserService.getDispositionCapacityDetailReleaseVo(licenceId, wasteEntId);
            dataMap.put("capacityDetailReleaseList", JSONArray.toJSON(capacityDetailReleaseList));
            dataMap.put("enterpriseUserList", userList);
            dataMap.put("enterpriseInfo", JSONArray.toJSON(enterpriseInfo));
            dataMap.put("provinceByEntId", JSONArray.toJSON(provinceByEntId));
            dataMap.put("districtByEntId", JSONArray.toJSON(districtByEntId));
            dataMap.put("file", JSONArray.toJSON(file));
            dataMap.put("distance", JSONArray.toJSON(distance));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }


        result.setData(dataMap);
        return result;
    }




    @ResponseBody
    @RequestMapping("/enterpriseInformation")
    public Result<Map<String, Object>> enterpriseInformation(HttpServletRequest request) throws Exception {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String enterpriseId = request.getParameter("enterpriseId");
        // 1.根据企业ID拿到企业基本表的全部信息；
        RPCSysEnterpriseBaseVo sysEnterpriseBaseVo = myEnterpriseService.getSysEnterpriseBasesByEntId(ticketId, enterpriseId);
        List<CodeValue> enterTypeList = codeValueService.getEnterpriseTypesByEntId(enterpriseId);
        if (enterTypeList != null && enterTypeList.size() > 0) {
            sysEnterpriseBaseVo.setEntTypes(enterTypeList);
        }
        RPCSysEnterpriseBaseVo posxVsPosyByEntId = enterpriseService.getPosxVsPosyByEntId(enterpriseId);
        String distance = calculateDistance(posxVsPosyByEntId.getPosx(), posxVsPosyByEntId.getPosy(), user.getEnterpriseId(), ticketId);
        sysEnterpriseBaseVo.setDistance(distance);
        // 2.拿许可证信息
        OperationLicence operationLicence = licenceService.getValidLicIdByEnterpriseId(enterpriseId);
        String licenceId = operationLicence.getId();
        OperationLicenceVo licence = null;
        List<OperationLicenceItemVo> listVo = null;
        licence = licenceService.getlicenceApprovedById(licenceId);
        listVo = licenceService.getWasteInfoByLicenceId(licenceId);

        dataMap.put("sysEnterpriseBaseVo", JSONArray.toJSON(sysEnterpriseBaseVo));
        dataMap.put("licence", JSONArray.toJSON(licence));
        dataMap.put("listVo", JSONArray.toJSON(listVo));
        result.setStatus(ResultStatus.Success);
        result.setData(dataMap);
        return result;
    }

    private String calculateDistance(String posx, String posy, String loginUserEnterId, String ticketId) {
        String distanceStr = null;
        if (StringUtils.isNotBlank(loginUserEnterId)) {
            RPCSysEnterpriseBase enterBase = enterpriseService.getEnterpriseInfoById(ticketId, loginUserEnterId);

            if (StringUtils.isNotBlank(posx) && StringUtils.isNotBlank(posy) && StringUtils.isNotBlank(enterBase.getPosx()) && StringUtils.isNotBlank(enterBase.getPosy())) {
                Double distance = MapUtil.getDistance(Double.valueOf(posx), Double.valueOf(posy), Double.valueOf(enterBase.getPosx()), Double.valueOf(enterBase.getPosy()));
                distanceStr = MapUtil.getDistanceStr(distance);
            }
        }
        return distanceStr;
    }
}
