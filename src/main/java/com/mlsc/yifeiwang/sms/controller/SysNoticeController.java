package com.mlsc.yifeiwang.sms.controller;

import com.mlsc.common.constant.BaseConstant;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sysNotice")
public class SysNoticeController {
    private static final String PATH = "Notice/";

    private Logger logger = Logger.getLogger(SysNoticeController.class);

    @Autowired
    private ISysNoticeService sysNoticeService;


    /**
     * 我的通知消息页面跳转
     * @return
     */
    @RequestMapping("/myNotice")
    public ModelAndView myNotice(String ticketId) {
        ModelAndView mv = new ModelAndView(PATH + "list");
        mv.addObject("ticketId", ticketId);
        return mv;
    }
    
    /**
     * 加载我收到的通知信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/pageNotice")
    public Result<ResultData<SysNoticeVo>> pageNotice(SysNoticeQueryParam queryParam,PagingParameter pagingParameter,
            String ticketId) {
        Result<ResultData<SysNoticeVo>> result = new Result<ResultData<SysNoticeVo>>();
        Map<String, Object> dataMap = null;
        User user = null;
        try {
            dataMap = new HashMap<String, Object>();
            user = LoginStatusUtils.getUserByTicketId(ticketId);
            if(queryParam == null){
                queryParam = new SysNoticeQueryParam();
            }
            queryParam.setReceiverId(user.getUserId());
            queryParam.setClientType(BaseConstant.WEB_CLIENT);
            ResultData<SysNoticeVo> resultData = sysNoticeService.pageSysNotice(queryParam,pagingParameter);
            result.setData(resultData);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取通知消息出错",e);
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

}
