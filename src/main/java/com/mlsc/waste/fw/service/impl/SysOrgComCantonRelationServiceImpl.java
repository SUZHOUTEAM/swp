package com.mlsc.waste.fw.service.impl;

import com.mlsc.waste.fw.dao.SysOrgComCantonRelationDao;
import com.mlsc.waste.fw.model.SysOrgComCantonRelation;
import com.mlsc.waste.fw.service.SysOrgComCantonRelationService;
import com.mlsc.waste.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhugl
 */
@Service("SysOrgComCantonRelationService")
public  class SysOrgComCantonRelationServiceImpl implements SysOrgComCantonRelationService{
    private final static Logger logger = LoggerFactory.getLogger(SysCantonServiceImpl.class);

    @Autowired
    private SysOrgComCantonRelationDao orgComCantonRelationDao;
    
    @Override
    public void saveOrgComCantonRelation(SysOrgComCantonRelation orgComCantonRelation, String ticketId) {
        try {
            orgComCantonRelationDao.save(orgComCantonRelation, Util.uuid32());
        } catch (Exception e) {
            logger.error("组织机构与区域代码的关系表新增保存时异常",e);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void updateOrgComCantonRelation(SysOrgComCantonRelation orgComCantonRelation, String ticketId) {
        try {
            orgComCantonRelationDao.update(orgComCantonRelation);
        } catch (Exception e) {
            logger.error("组织机构与区域代码的关系表更新时异常",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public SysOrgComCantonRelation getSysOrgComCantonRelation(String comId) {
        SysOrgComCantonRelation sysOrgComCantonRelation = new SysOrgComCantonRelation();
        try {
            sysOrgComCantonRelation.setComId(comId);
            sysOrgComCantonRelation.setRelType(0);
            sysOrgComCantonRelation.setSequence(0);
            
            List<SysOrgComCantonRelation> orgComList = orgComCantonRelationDao.query(sysOrgComCantonRelation);
            if (orgComList != null && orgComList.size() > 0) {
                sysOrgComCantonRelation = orgComList.get(0);
            } else {
                sysOrgComCantonRelation = null;
            }
        } catch (Exception e) {
            logger.error("根据ID获取组织机构时异常",e);
            throw new RuntimeException(e);
        }
        
        return sysOrgComCantonRelation;
    }

    @Override
    public SysOrgComCantonRelation getSysOrgComCantonRelationByRelId(
            String relId) {
        SysOrgComCantonRelation sysOrgComCantonRelation = new SysOrgComCantonRelation();
        try {
            sysOrgComCantonRelation = orgComCantonRelationDao.get(relId);
        } catch (Exception e) {
            logger.error("根据ID获取组织机构时异常",e);
            throw new RuntimeException(e);
        }
        
        return sysOrgComCantonRelation;
    }
    
    @Override
    public List<SysOrgComCantonRelation> getSysOrgComCantonRelationByCantonCode(String cantonCode) {
        SysOrgComCantonRelation sysOrgComCantonRelation = new SysOrgComCantonRelation();
        try {
            sysOrgComCantonRelation.setCantonCode(cantonCode);
            return orgComCantonRelationDao.query(sysOrgComCantonRelation);
        } catch (Exception e) {
            logger.error("根据ID获取组织机构时异常",e);
            throw new RuntimeException(e);
        }
    }
}