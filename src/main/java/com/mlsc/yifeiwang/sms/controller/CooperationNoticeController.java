package com.mlsc.yifeiwang.sms.controller;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.sms.model.CooperationNoticeVo;
import com.mlsc.yifeiwang.im.model.ImNotice;
import com.mlsc.yifeiwang.sms.service.ICooperationNoticeService;
import com.mlsc.yifeiwang.im.service.ImService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notice")
@Scope("prototype")
public class CooperationNoticeController {
    private static final String PATH = "Notice/";

    @Autowired
    private ICooperationNoticeService noticeService;

    @Autowired
    private ImService imService;

    /**
     * 初始化我的关注
     *
     * @param request
     * @return
     */
    @RequestMapping("/myNotice")
    public ModelAndView myNotice(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "list");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 加载我收到的通知信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/initNoticeData")
    public Result<Map<String, Object>> initNoticeData(PagingParameter pagingParameter,HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);

        String startDate = request.getParameter("startDate"); //开始时间
        String endDate = request.getParameter("endDate");//结束时间
        String infotype = request.getParameter("infotype");//消息类型
        String isRead = request.getParameter("isRead");//是否只显示未读
        List<CooperationNoticeVo> receiverNoticeList = null;

        //如果有输入的条件就在搜索的
        if(StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)|| StringUtils.isNotBlank(infotype) ||StringUtils.isNotBlank(isRead)){
            try {
                // 初始化页面参数
                initPagingParameter(pagingParameter);
                receiverNoticeList = noticeService.searchMyReceiverNotices(user.getUserId(), startDate,endDate,infotype,isRead, pagingParameter);
                dataMap.put("noticeData", JSONArray.toJSON(receiverNoticeList));
                int totalcount = noticeService.searchMyReceiverNoticesTotalcount(user.getUserId(), startDate,endDate,infotype,isRead);
                pagingParameter.setTotalRecord(totalcount);
                dataMap.put("paging", JSONArray.toJSON(pagingParameter));
                dataMap.put("unReadCount", noticeService.getMyReceiverNoticeCount(user.getUserId(),Constant.IS_NO));
                result.setStatus(ResultStatus.Success);
            } catch (Exception e) {
                result.setStatus(ResultStatus.Failure);
            }
            result.setData(dataMap);
        }else{

            try {
                // 初始化页面参数
                initPagingParameter(pagingParameter);
                receiverNoticeList = noticeService.getMyReceiverNotices(user.getUserId(), null, pagingParameter,ticketId);
                pagingParameter.setTotalRecord(noticeService.getMyReceiverNoticeCount(user.getUserId(),null));
                dataMap.put("noticeData", JSONArray.toJSON(receiverNoticeList));
                dataMap.put("paging", JSONArray.toJSON(pagingParameter));
                dataMap.put("unReadCount", noticeService.getMyReceiverNoticeCount(user.getUserId(),Constant.IS_NO));
                result.setStatus(ResultStatus.Success);
            } catch (Exception e) {
                result.setStatus(ResultStatus.Failure);
            }
            result.setData(dataMap);
        }

        return result;
    }

    /**
     * 加载我发出的通知信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/initNoticeSenderData")
    public Result<Map<String, Object>> initNoticeSenderData(PagingParameter pagingParameter,HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        List<CooperationNoticeVo> senderNoticeList = null;
        try {
            // 初始化页面参数
            initPagingParameter(pagingParameter);
            senderNoticeList = noticeService.getMySenderNotices(user.getUserId(), null, pagingParameter,ticketId);
            pagingParameter.setTotalRecord(noticeService.getMySenderNoticeCount(user.getUserId(), null));
            dataMap.put("senderNoticeData", JSONArray.toJSON(senderNoticeList));
            dataMap.put("paging", JSONArray.toJSON(pagingParameter));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }

    private void initPagingParameter(PagingParameter pagingParameter){
        pagingParameter.setPageSize(8);
        if (pagingParameter.getPageIndex() == 0) {
            pagingParameter.setPageIndex(1);
        }

        int start = (pagingParameter.getPageIndex() -1) * pagingParameter.getPageSize();
        pagingParameter.setStart(start);
        pagingParameter.setLimit(8);
    }

    /**
     * 通知的已读更新
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/noticeIsReaded")
    public Result<String> noticeIsReaded(HttpServletRequest request) {
        Result<String> result = new Result<String>();
        String ticketId = request.getParameter("ticketId");
        String noticeId = request.getParameter("noticeId");
        try {
            noticeService.updateCooperationNoticeIsRead(noticeId, Constant.IS_YSE, ticketId);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        return result;
    }

    /**
     * 加载我收到的通知信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMyNoReadNotice")
    public Result<Map<String, Object>> getMyNoReadNotice(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            dataMap.put("unReadCount", noticeService.getMyReceiverNoticeCount(user.getUserId(),Constant.IS_NO));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }


    /**
     * 获取未读的通知
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUnReadNoticeData")
    public Result<Map<String, Object>> getUnReadNoticeData(PagingParameter pagingParameter,HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String condition = request.getParameter("condition");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            dataMap.put("unReadCount", noticeService.getMyReceiverNoticeCount(user.getUserId(),Constant.IS_NO));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/getUnReadNoticeList")
    public Result<Map<String, Object>> getUnReadNoticeList(PagingParameter pagingParameter,HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        String condition = request.getParameter("condition");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        try {
            List<ImNotice> imNoticeList= imService.getChatLogList(ticketId);
            if(StringUtils.isNotBlank(condition)){
                Iterator<ImNotice> it = imNoticeList.iterator();
                while(it.hasNext()){
                    ImNotice imNotice = it.next();
                    String entName = imNotice.getEntName();
                    if(!entName.contains(condition)){
                        it.remove();
                    }
                }
            }
            // 初始化页面参数
            dataMap.put("unReadCount", noticeService.getMyReceiverNoticeCount(user.getUserId(),Constant.IS_NO));
            dataMap.put("imNoticeListData", JSONArray.toJSON(imNoticeList));
            dataMap.put("chatLogCount", imNoticeList.size());
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }

    @ResponseBody
    @RequestMapping("/signHasRead")
    public Result<Map<String, String>> signHasRead(HttpServletRequest request){
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String noticeId = request.getParameter("noticeId");
        String msg = null;
        try {
            noticeService.updateCooperationNoticeIsRead(noticeId,Constant.IS_YSE,ticketId);
            msg = "已成功标志为已读";
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;

    }


    /**
     * 根据条件筛选我的消息
     * @param pagingParameter
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchNoticeData")
    public Result<Map<String, Object>> searchNoticeData(PagingParameter pagingParameter,HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String ticketId = request.getParameter("ticketId");
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        List<CooperationNoticeVo> receiverNoticeList = null;
        String startDate = request.getParameter("startDate"); //开始时间
        String endDate = request.getParameter("endDate");//结束时间
        String infotype = request.getParameter("infotype");//消息类型
        String isRead = request.getParameter("isRead");//是否只显示未读
        try {
            // 初始化页面参数
            initPagingParameter(pagingParameter);
            receiverNoticeList = noticeService.searchMyReceiverNotices(user.getUserId(), startDate,endDate,infotype,isRead, pagingParameter);
            dataMap.put("noticeData", JSONArray.toJSON(receiverNoticeList));
            int totalcount = noticeService.searchMyReceiverNoticesTotalcount(user.getUserId(), startDate,endDate,infotype,isRead);
            pagingParameter.setTotalRecord(totalcount);
            dataMap.put("paging", JSONArray.toJSON(pagingParameter));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Failure);
        }
        result.setData(dataMap);
        return result;
    }
}
