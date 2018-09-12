package com.mlsc.yifeiwang.im.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.constant.FileTypeEnum;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.yifeiwang.im.model.ImNotice;
import com.mlsc.yifeiwang.im.service.ImService;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.PropertyUtil;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.waste.wastecircle.service.WasteCircleService;
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
@RequestMapping("/im")
@Scope("prototype")
public class ImController extends BaseController {

    @Autowired
    private ImService imService;

    @Autowired
    private UserService userService;

    @Autowired
    private WasteCircleService wasteCircleService;

    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    private LicenceService licenceService;

    /**
     * 获取聊天记录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getChatLoglist")
    public Result<Map<String, Object>> getChatLoglist(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        try {
            List<ImNotice> imNoticeList = imService.getChatLogList(ticketId);
            dataMap.put("imNoticeListData", JSONArray.toJSON(imNoticeList));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/getImEnv")
    public JSONObject getImEnv(HttpServletRequest request) {
        return PropertyUtil.getImEnv();
    }

    @ResponseBody
    @RequestMapping("/refreshToken")
    public Result<Map<String, Object>> refreshToken(String ticketId) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            UserExtended userExtended = userService.getUserExtendedInfoByUserId(user.getUserId());

            String tokenStr = imService.genterateIMToken(user.getPhoneNo(), user.getUserName());
            userExtended.setToken(tokenStr);
            user.setImToken(tokenStr);
            userService.updateUserIMToken(ticketId, userExtended);

            dataMap.put("user", user);
            result.setData(dataMap);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getUinfos")
    public Result<Map<String, Object>> getUinfos(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User userByTicketId = LoginStatusUtils.getUserByTicketId(ticketId);
        String accid = userByTicketId.getPhoneNo();
        String phoneNo = request.getParameter("phoneNo");
        try {
            JSONArray uinfos = imService.getUinfos(phoneNo);
            JSONArray userStaus = imService.getUserStaus(accid, phoneNo);
            RPCSysUser userInfo = userService.getUserInfoByPhoneNum(ticketId, phoneNo);
            User friendUser = new User();
            friendUser.setUserId(userInfo.getUserId());
            EnterpriseInfo enterpriseInfo = wasteCircleService.getEnterpriseInfoByUserId(ticketId, friendUser);
            dataMap.put("userStaus", JSONArray.toJSON(userStaus));
            dataMap.put("uinfos", JSONArray.toJSON(uinfos));
            dataMap.put("enterpriseInfo", JSONArray.toJSON(enterpriseInfo));
            dataMap.put("userInfo", userInfo);
            List<Uploadfile> uploadfile = uploadfileService.getFileByFileTypeAndReferenceId("c", userInfo.getUserId());
            dataMap.put("uploadfile", uploadfile);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }


    @ResponseBody
    @RequestMapping("/listEntImg")
    public Result<Map<String, Object>> listEntImg(String entId) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {

            List<Uploadfile> entImages = uploadfileService.getFileByFileTypeAndReferenceId(FileTypeEnum.ENT_BUS_LICENSE.getCode(), entId);
            dataMap.put("entImages", entImages);

            OperationLicence operationLicence = licenceService.getValidLicIdByEnterpriseId(entId);
            if (operationLicence != null) {
                List<Uploadfile> licenceImages = uploadfileService.getFileByFileTypeAndReferenceId(FileTypeEnum.LICENSE.getCode(), operationLicence.getId());
                dataMap.put("licenceImages", licenceImages);
            }
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }
}
