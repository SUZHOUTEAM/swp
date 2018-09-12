package com.mlsc.waste.fw.service.impl;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.waste.fw.dao.SysOrgComDao;
import com.mlsc.waste.fw.model.SysOrgCom;
import com.mlsc.waste.fw.service.SysOrgComService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhugl
 */
@Service("SysOrgComService")
public  class SysOrgComServiceImpl implements SysOrgComService{
    private final static Logger logger = LoggerFactory.getLogger(SysOrgComServiceImpl.class);

    @Autowired
    private SysOrgComDao sysOrgComDao;
    
    @Override
    public int getOrgComCount(String sql, Map<String, Object> paramMap) {
        int count = 0;
        try {
            List<SysOrgCom> orgComList = sysOrgComDao.getOrgComList(sql, paramMap, null);
            if (orgComList != null) {
                count = orgComList.size();
            }
        } catch (Exception e) {
            logger.error("平台管理员获取企业列表件数时异常", e);
            throw new RuntimeException(e);
        }
        return count;
    }
    
    @Override
    public List<SysOrgCom> getOrgComList(String sql, Map<String, Object> paramMap, PagingParameter paging) {
        List<SysOrgCom> orgComList = null;
        
        try {
            orgComList = sysOrgComDao.getOrgComList(sql, paramMap, paging);
        } catch (Exception e) {
            logger.error("平台管理员获取组织机构列表时异常", e);
            throw new RuntimeException(e);
        }
        
        return orgComList;
    }

    @Override
    public void saveOrgCom(SysOrgCom orgCom, String ticketId) {
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            orgCom.setComStatus(0);//正常
            orgCom.setCreaterId(user.getUserId());
            orgCom.setCreateTime(Util.datetimeToString(new Date()));
            orgCom.setUpdaterId(user.getUserId());
            orgCom.setUpdateTime(Util.datetimeToString(new Date()));
            sysOrgComDao.save(orgCom, Util.uuid32());
        } catch (Exception e) {
            logger.error("组织机构新增保存时异常",e);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void updateOrgCom(SysOrgCom orgCom, String ticketId) {
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            orgCom.setUpdaterId(user.getUserId());
            orgCom.setUpdateTime(Util.datetimeToString(new Date()));
            sysOrgComDao.update(orgCom);
        } catch (Exception e) {
            logger.error("组织机构新增更新时异常",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getOrgComName(String orgComId, String orgComCode) {
        String orgComName = null;
        try {
            SysOrgCom orgCom = new SysOrgCom();
            orgCom.setComCode(orgComCode);
            List<SysOrgCom> orgComList = sysOrgComDao.query(orgCom);
            if (orgComList != null && orgComList.size() > 0) {
                if (StringUtils.isNotBlank(orgComId)) {
                    for (SysOrgCom sysOrgCom : orgComList) {
                        if (!orgComId.equals(sysOrgCom.getComId())) {
                            orgComName = sysOrgCom.getComName();
                            break;
                        }
                    }
                } else {
                    orgComName = orgComList.get(0).getComName();
                }
            }
        } catch (Exception e) {
            logger.error("组织机构新增保存时异常",e);
            throw new RuntimeException(e);
        }
        
        return orgComName;
    }

    @Override
    public SysOrgCom getOrgCom(String comId) {
        SysOrgCom sysOrgCom = null;
        try {
            sysOrgCom = sysOrgComDao.get(comId);
        } catch (Exception e) {
            logger.error("根据ID获取组织机构时异常",e);
            throw new RuntimeException(e);
        }
        
        return sysOrgCom;
    }

    @Override
    public List<RPCSysOrgCom> querySysOrgComListByCantonID(String ticketId, int comType, String cantonCode) {
        List<RPCSysOrgCom> orgComList = null;
        
        try {
            orgComList = sysOrgComDao.querySysOrgComListByCantonID(ticketId, comType, cantonCode);
        } catch (Exception e) {
            logger.error("通过区域代码获取组织机构时异常", e);
            throw new RuntimeException(e);
        }
        
        return orgComList;
    }

    @Override
    public List<RPCSysOrgCom> querySysOrgComListByParentCantonID(String ticketId, int comType, String cantonCode) {
        List<RPCSysOrgCom> orgComList = null;
        
        try {
            orgComList = sysOrgComDao.querySysOrgComListByParentCantonID(ticketId, comType, cantonCode);
        } catch (Exception e) {
            logger.error("通过父区域区域代码获取组织机构列表时异常", e);
            throw new RuntimeException(e);
        }
        
        return orgComList;
    }
}