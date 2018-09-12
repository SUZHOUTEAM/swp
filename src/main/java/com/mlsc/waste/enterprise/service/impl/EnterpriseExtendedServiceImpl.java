package com.mlsc.waste.enterprise.service.impl;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.dao.EnterpriseExtendedDao;
import com.mlsc.waste.enterprise.model.EnterpriseExtended;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprise.service.EnterpriseExtendedService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhugl
 */
@Service("EnterpriseExtendedService")
public class EnterpriseExtendedServiceImpl implements EnterpriseExtendedService {

    private final static Logger logger = LoggerFactory.getLogger(EnterpriseExtendedServiceImpl.class);

    @Autowired
    private EnterpriseExtendedDao enterpriseExtendedDao;

    @Autowired
    private ICodeValueService codeValueService;

    /**
     * 保存企业扩展表
     */
    @Override
    public String saveEnterpriseExtended(EnterpriseExtended record,String ticketId) {
        String extendedId = Util.uuid32();
        try {
            record.setValid(Constant.IS_VALID);
            enterpriseExtendedDao.save(record, extendedId);
        } catch (Exception e) {
            logger.error("企业扩展表记录保存时异常", e);
            throw new RuntimeException(e);
        }
        return extendedId;
    }

    /**
     * 更新企业扩展表
     */
    @Override
    public void updateEnterpriseExtended(EnterpriseExtended record,String ticketId) {
        try {
            enterpriseExtendedDao.update(record);
        } catch (Exception e) {
            logger.error("企业扩展表记录更新时异常", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据企业id获取企业扩展信息
     */
    @Override
    public EnterpriseExtended getEnterpriseExtendedByEnterpriseId(String enterpriseId) {
        EnterpriseExtended record = new EnterpriseExtended();
        record.setSysEnterpriseBaseId(enterpriseId);
        record.setValid(Constant.IS_VALID);
        try {
            List<EnterpriseExtended> recordlist = enterpriseExtendedDao.query(record);
            if (recordlist != null && recordlist.size() > 0) {
                record = recordlist.get(0);
            }
        } catch (Exception e) {
            logger.error("企业扩展表记录更新时异常", e);
            throw new RuntimeException(e);
        }
        return record;
    }

    /**
     * 根据企业id更新企业扩展信息
     */
    @Override
    public void updateByEnpId(String enterpriseId, String userEventStatus,String valid) {
        enterpriseExtendedDao.updateByEnpId(enterpriseId, userEventStatus,valid);
    }

    @Override
    public List<RPCSysEnterpriseBaseVo> getAllEnterpriseStatusList(String sql,Map<String, Object> paramMap, PagingParameter paging) {
        List<RPCSysEnterpriseBaseVo> enterpriseList = null;
        try {
        	CodeValue codeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS,CodeTypeConstant.USER_EVENT_STATUS_SUBMIT);
        	String codevalueId = null;
        	if(codeValue!=null){
        		codevalueId = codeValue.getId();
        	}
            enterpriseList = enterpriseExtendedDao.getAllEnterpriseStatusList(sql,paramMap,paging,codevalueId);
        } catch (Exception e) {
            logger.error("根据条件所有企业信息及状态 时异常", e);
            throw new RuntimeException(e);
        }
        
        return enterpriseList;
    }

    @Override
    public int getAllEnterpriseStatusCount(String sql,Map<String, Object> paramMap) {
        try {
        	CodeValue codeValue = codeValueService.getCodeValueByCode(CodeTypeConstant.USER_EVENT_STATUS,CodeTypeConstant.USER_EVENT_STATUS_SUBMIT);
        	String codevalueId = null;
        	if(codeValue!=null){
        		codevalueId = codeValue.getId();
        	}
            return  enterpriseExtendedDao.getAllEnterpriseStatusCount(sql,paramMap,codevalueId);
        } catch (Exception e) {
            logger.error("根据条件所有企业信息及状态个数时异常", e);
            throw new RuntimeException(e);
        }
    }

}
