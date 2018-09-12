package com.mlsc.mobileservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.constant.BaseConstant;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.sms.controller.SysNoticeController;
import com.mlsc.yifeiwang.sms.model.SysNoticeQueryParam;
import com.mlsc.yifeiwang.sms.model.SysNoticeVo;
import com.mlsc.yifeiwang.sms.service.ISysNoticeService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghj on 2017/8/15.
 */
@Controller
@RequestMapping("/sysNoticeMb")
public class SysNoticeMbController {

    private Logger logger = Logger.getLogger(SysNoticeController.class);

    @Autowired
    private ISysNoticeService sysNoticeService;


    /**
     * 获取未读的通知数量
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUnreadNoticeCount")
    public Result<Integer> getUnreadNoticeCount(String ticketId) {
        Result<Integer> result = new Result<>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            int count = sysNoticeService.getUnreadNoticeCount(user.getUserId());
            result.setData(count);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取未读通知数量失败",e);
            result.setStatus(ResultStatus.Error);
            result.getInfoList().add(BaseConstant.ERROR_MSG);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listNoticeCategory")
    public Result<List<JSONObject>> listNoticeCategory(String ticketId,String entType){
        Result<List<JSONObject>> result = new Result<>();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            List<JSONObject> list = sysNoticeService.listNoticeCategory(user.getUserId(),entType);
            result.setData(list);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取手机端个人中心展示通知类型列表出错",e);
            result.setStatus(ResultStatus.Error);
            result.getInfoList().add(BaseConstant.ERROR_MSG);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/pageNotice")
    public Result<ResultData<SysNoticeVo>> pageNotice(String ticketId,SysNoticeQueryParam queryParam,PagingParameter
            pagingParameter){
        Result< ResultData<SysNoticeVo>> result = new Result<>();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            if(queryParam == null){
                queryParam = new SysNoticeQueryParam();
            }
            queryParam.setReceiverId(user.getUserId());
            queryParam.setClientType(BaseConstant.APP_CLIENT);
            ResultData<SysNoticeVo> resultData = sysNoticeService.pageSysNotice(queryParam,pagingParameter);
            result.setData(resultData);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取手机端个人中心展示通知类型列表出错",e);
            result.setStatus(ResultStatus.Error);
            result.getInfoList().add(BaseConstant.ERROR_MSG);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/readNotice")
    public Result<String> readNotice(String ticketId,String noticeId) {
        Result<String> result = new Result<String>();
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            sysNoticeService.readNotice(noticeId,user);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("修改通知消息已读状态出错",e);
            result.getInfoList().add(BaseConstant.ERROR_MSG);
        }
        return result;
    }


}
