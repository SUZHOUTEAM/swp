package com.mlsc.yifeiwang.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.common.constant.BaseConstant;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.yifeiwang.sms.entity.SysNotice;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.yifeiwang.sms.common.NoticeRouteUrl;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.mapper.SysNoticeMapper;
import com.mlsc.yifeiwang.sms.model.SysNoticeQueryParam;
import com.mlsc.yifeiwang.sms.model.SysNoticeVo;
import com.mlsc.yifeiwang.sms.service.ISysNoticeService;
import com.mlsc.yifeiwang.codedirectory.mapper.CodeValueMapper;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhanghj
 * @since 2017-08-10
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService {

    @Autowired
    private CodeValueMapper codeValueMapper;

    @Override
    public void readNotice(String noticeId,User user){
        SysNotice sysNotice = new SysNotice();
        sysNotice.setId(noticeId);
        sysNotice.setHasRead(BaseConstant.YES);
        sysNotice.setEditBy(user.getUserId());
        sysNotice.setEditTime(new Date());
        updateById(sysNotice);
    }

    @Override
    public void readAllUnreadNotice(User user) {
        EntityWrapper<SysNotice> ew = new EntityWrapper<>();
        ew.eq("receiver_id",user.getUserId());
        SysNotice sysNotice = new SysNotice();
        sysNotice.setHasRead(BaseConstant.YES);
        sysNotice.setEditBy(user.getUserId());
        sysNotice.setEditTime(new Date());
        this.update(sysNotice, ew);
    }

    @Override
    public void saveNoticeList(List<SysNotice> noticeList, User user) {
        if(noticeList!=null&&noticeList.size()>0){
            Date now = new Date();
            for(SysNotice notice : noticeList){
                notice.setValid(BaseConstant.YES);
                notice.setEditBy(user.getUserId());
                notice.setEditTime(now);
                notice.setCreateBy(user.getUserId());
                notice.setCreateTime(now);
            }
            insertBatch(noticeList);
        }
    }

    @Override
    public List<SysNoticeVo> listSysNotice(SysNoticeQueryParam queryParam) {
        List<SysNoticeVo> list = this.baseMapper.listSysNotice(queryParam);
        setNoticeJumpUrl(list,queryParam.getClientType());
        return list;
    }

    @Override
    public ResultData<SysNoticeVo> pageSysNotice(SysNoticeQueryParam queryParam, PagingParameter pagingParameter) {
        if(pagingParameter!=null){
            if(pagingParameter.getPageIndex()==0){
                pagingParameter.setPageIndex(1);
            }
            pagingParameter.init();
            queryParam.setStartRowIndex(pagingParameter.getStart());
            queryParam.setRows(pagingParameter.getLimit());
        }
        List<SysNoticeVo> list  = listSysNotice(queryParam);
        int total = this.baseMapper.countSysNotice(queryParam);
        ResultData resultData = new ResultData(total,list,pagingParameter);
        return resultData;
    }

    @Override
    public int getUnreadNoticeCount(String receiverId) {
        SysNoticeQueryParam queryParam = new SysNoticeQueryParam(receiverId,BaseConstant.NO);
        return this.baseMapper.countSysNotice(queryParam);
    }

    @Override
    public List<JSONObject> listNoticeCategory(String receiverId,String entType) {
        List<JSONObject> result = new ArrayList<>();
        List<CodeValue> codeValueList = codeValueMapper.listNoticeTypeByTypeCode(CodeTypeConstant.NOTICE_CATEGORY,entType);
        if(codeValueList!=null&&codeValueList.size()>0){
            SysNoticeQueryParam queryParam = new SysNoticeQueryParam(receiverId,BaseConstant.NO);
            List<SysNoticeVo> list = this.baseMapper.listSysNotice(queryParam);
            JSONObject mp = new JSONObject();
            if(list!=null&&list.size()>0){
                for(SysNoticeVo sysNoticeVo : list){
                    String noticeCategory = sysNoticeVo.getNoticeCategory();
                    if(mp.get(noticeCategory)==null){
                        mp.put(sysNoticeVo.getNoticeCategory(),sysNoticeVo);
                    }
                }
            }
            for(CodeValue codeValue : codeValueList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codeValue",codeValue);
                jsonObject.put("notice",mp.get(codeValue.getCode()));
                result.add(jsonObject);
            }
        }
        return result;

    }

    private void setNoticeJumpUrl(List<SysNoticeVo> list,String clientType){
        if(list!=null&&list.size()>0){
            for(SysNoticeVo sysNoticeVo : list){
                SmsAction smsAction = SmsAction.getSmsActionByActionCode(sysNoticeVo.getNoticeType());
                if(smsAction!=null){
                    NoticeRouteUrl noticeRouteUrl = smsAction.getNoticeRouteUrl();
                    if(noticeRouteUrl!=null){
                        sysNoticeVo.setDirectUrl(noticeRouteUrl.getUrlByClientType(clientType));
                    }
                }
            }
        }
    }


}
