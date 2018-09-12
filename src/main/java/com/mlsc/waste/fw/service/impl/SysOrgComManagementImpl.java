package com.mlsc.waste.fw.service.impl;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysCanton;
import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.waste.fw.dao.SysOrgComCantonRelationDao;
import com.mlsc.waste.fw.dao.SysOrgComDao;
import com.mlsc.waste.fw.model.SysOrgCom;
import com.mlsc.waste.fw.model.SysOrgComCantonRelation;
import com.mlsc.waste.fw.service.SysOrgComCantonRelationService;
import com.mlsc.waste.fw.service.SysOrgComManagementService;
import com.mlsc.waste.fw.service.SysOrgComService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author sunjl
 */
@Service("SysOrgComManagementService")
public  class SysOrgComManagementImpl implements SysOrgComManagementService{
    private final static Logger logger = LoggerFactory.getLogger(SysOrgComManagementImpl.class);

    @Autowired
    private SysOrgComService sysOrgComService;
    
    @Autowired
    private SysOrgComCantonRelationService sysOrgComCantonRelationService;
    
    @Autowired
    private SysOrgComDao sysOrgComDao;
    
    @Autowired
    private SysOrgComCantonRelationDao orgComCantonRelationDao;
    @Autowired
    private IRPCServiceClient client;
    
    @Override
    public int getOrgComCount(String sql, Map<String, Object> paramMap) {
        int count = 0;
        try {
            count = sysOrgComService.getOrgComCount(sql, paramMap);
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
            orgComList = sysOrgComService.getOrgComList(sql, paramMap, paging);
        } catch (Exception e) {
            logger.error("平台管理员获取企业列表时异常", e);
            throw new RuntimeException(e);
        }
        
        return orgComList;
    }
    
    @Override
    public String getOrgComName(String orgComId, String orgComCode) {
        try {
            return sysOrgComService.getOrgComName(orgComId, orgComCode);
        } catch (Exception e) {
            logger.error("获取组织机构名称时异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveOrgComAndCantonRelation(SysOrgCom orgCom, String cantonCode, String ticketId) {
        String statusCode = null;
        String cantonId = null;
        try {
            if(Pattern.matches("^[0-9]{2,6}$", cantonCode)){
                cantonId = cantonCode;
            } else {
                String[] result = isValidCantonValue(cantonCode, ticketId);
                statusCode = result[0];
                if (StringUtils.isNotBlank(statusCode)) {
                    return statusCode;
                }
                cantonId = result[1];
            }
            
            orgCom.setComType(Constant.COM_TYPE_EPA);// 机构
            sysOrgComService.saveOrgCom(orgCom, ticketId);// 组织机构保存
            
            SysOrgComCantonRelation orgComCantonRelation  = new SysOrgComCantonRelation();
            orgComCantonRelation.setComId(orgCom.getComId());
            orgComCantonRelation.setCantonCode(cantonId);
            orgComCantonRelation.setRelType(0);
            orgComCantonRelation.setSequence(0);// 需要改
            sysOrgComCantonRelationService.saveOrgComCantonRelation(orgComCantonRelation, ticketId);
            statusCode = "0";
            
        } catch (Exception e) {
            logger.error("保存组织机构名称时异常", e);
            throw new RuntimeException(e);
        }
        return statusCode;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String updateOrgComAndCantonRelation(SysOrgCom orgCom, String relId, String cantonCode, String ticketId) {
        String statusCode = null;
        String cantonId = null;
        try {
            
            if(Pattern.matches("^[0-9]{2,6}$", cantonCode)){
                cantonId = cantonCode;
            } else {
                String[] result = isValidCantonValue(cantonCode, ticketId);
                statusCode = result[0];
                if (StringUtils.isNotBlank(statusCode)) {
                    return statusCode;
                }
                cantonId = result[1];
            }
            
            SysOrgCom newOrgCom = sysOrgComService.getOrgCom(orgCom.getComId());
            newOrgCom.setComName(orgCom.getComName());
            newOrgCom.setComCode(orgCom.getComCode());
            newOrgCom.setComDesc(orgCom.getComDesc());
            sysOrgComService.updateOrgCom(newOrgCom, ticketId);// 组织机构更新
            
            SysOrgComCantonRelation orgComCantonRelation = sysOrgComCantonRelationService
                    .getSysOrgComCantonRelationByRelId(relId);
            orgComCantonRelation.setCantonCode(cantonId);
            sysOrgComCantonRelationService.updateOrgComCantonRelation(orgComCantonRelation, ticketId);
            statusCode = "0";
        } catch (Exception e) {
            logger.error("保存组织机构名称时异常", e);
            throw new RuntimeException(e);
        }
        return statusCode;
    }

    @Override
    public Map<String, String> getCantonValue(String cantonCode, String ticketId) {
        Map<String, String> cantonMap = null;
        RPCSysCanton canton = null;
        try {
            if(StringUtils.isNotEmpty(cantonCode)){
                cantonMap = new HashMap<String, String>(3);
                Iface service = client.getOrgComServiceManager();
                if (cantonCode.length() >= 2) {
                    String provinceCantonCode = cantonCode.substring(0, 2);
                    canton = service.querySysCantonByCantonCode(ticketId, provinceCantonCode);
                    cantonMap.put("province", canton.getCantonName()); 
                }
                if (cantonCode.length() >= 4) {
                    String cityCantonCode = cantonCode.substring(0, 4);
                    canton = service.querySysCantonByCantonCode(ticketId, cityCantonCode);
                    cantonMap.put("city", canton.getCantonName());
                }
                if (cantonCode.length() >= 6) {
                    canton = service.querySysCantonByCantonCode(ticketId, cantonCode);
                    cantonMap.put("district", canton.getCantonName());
                }
            } 
        } catch (Exception e) {
            logger.error("根据区域code获取区域名称时异常", e);
            throw new RuntimeException(e);
        }
        return cantonMap;
    }
    
    private String[] isValidCantonValue(String cantonValue, String ticketId) throws Exception {
        String[] result = new String[2];// [0]:statusCode , [1]:cantonCode
        String[] cantonValueArr = Util.getCityAndDistrict(cantonValue);
        if (cantonValueArr == null || cantonValueArr.length == 0) {
            result[0] = "1" ;// 1：行政区域无值
            result[1] = "" ;
            return result;
        }
        
        //获取行政区code
        if (cantonValueArr.length == 1) {
            RPCSysCanton rpcSysCanton = client.getOrgComServiceManager().querySysCantonByCantonName(ticketId, cantonValueArr[0]);
            if (rpcSysCanton == null || StringUtils.isBlank(rpcSysCanton.getCantonCode())) {
                result[0] = "2";// 2:所选的区域在sys_canton中无对应数据
                result[1] = "";
            } else {
                result[0] = "" ;
                result[1] = rpcSysCanton.getCantonCode() ;
            }
        } else if (cantonValueArr.length == 2) {
            RPCSysCanton provinceRpcSysCanton = client
                    .getOrgComServiceManager().querySysCantonByCantonName(ticketId, cantonValueArr[0]);// 先获取省的
            List<RPCSysCanton> cityRpcSysCantons = client
                    .getOrgComServiceManager().querySysCantonListByCantonCode(ticketId, provinceRpcSysCanton.getCantonCode());// 获取省下面所有市级的
            if (cityRpcSysCantons != null) {
                boolean isExtend = false;
                for (RPCSysCanton canton : cityRpcSysCantons) {
                    if (cantonValueArr[1].equals(canton.getCantonName())) {
                        result[0] = "" ;
                        result[1] = canton.getCantonCode();
                        isExtend = true;
                        break;
                    }
                }
                
                if (!isExtend) {
                    result[0] = "2";// 2:所选的区域在sys_canton中无对应数据
                    result[1] = "";
                }
            } else {
                result[0] = "2";// 2:所选的区域在sys_canton中无对应数据
                result[1] = "";
            }
        } else if (cantonValueArr.length == 3) {
            //获取行政区code
            RPCSysCanton rpcSysCanton = client.getOrgComServiceManager().querySysCanton(ticketId, cantonValueArr[2], cantonValueArr[1]);
            if (rpcSysCanton == null || StringUtils.isBlank(rpcSysCanton.getCantonCode())) {
                result[0] = "2";// 2:所选的区域在sys_canton中无对应数据
                result[1] = "";
            } else {
                result[0] = "" ;
                result[1] = rpcSysCanton.getCantonCode() ;
            }
        }
        
        return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveOrgComAndCantonRelation(String orgComName, String orgComCode) {
        SysOrgCom orgCom = null;
        SysOrgComCantonRelation orgComCantonRelation = null;
        String orgComId = null;
        try {
            List<SysOrgComCantonRelation> orgComCantonRelations = sysOrgComCantonRelationService.getSysOrgComCantonRelationByCantonCode(orgComCode);
            if (orgComCantonRelations != null && orgComCantonRelations.size() > 0) {
                for (SysOrgComCantonRelation relation : orgComCantonRelations) {
                    orgCom = sysOrgComService.getOrgCom(relation.getComId());
                    if (orgCom != null) {
                        orgComCantonRelation = relation;
                        break;
                    }
                }
            }
            
            if (orgCom != null) {// 新固废系统已经存在就不要同步
                /*orgCom.setComName(orgComName);// 机构名称
                orgCom.setComCode(orgComCode);// 机构代码:默认是用相应的行政区划代码代替
                orgCom.setUpdaterId(Constant.SYS_ADMIN);
                orgCom.setUpdateTime(Util.datetimeToString(new Date()));
                sysOrgComDao.update(orgCom);*/
                orgComId = orgCom.getComId();
            } else {
                orgCom = new SysOrgCom();
                orgCom.setComName(orgComName);// 机构名称
                orgCom.setComCode(orgComCode);// 机构代码:默认是用相应的行政区划代码代替
                orgCom.setComStatus(0);// 机构状态：默认是0
                orgCom.setComDesc(null);// 机构备注：默认没有
                orgCom.setComType(Constant.COM_TYPE_EPA);// 机构
                orgCom.setCreaterId(Constant.SYS_ADMIN);
                orgCom.setCreateTime(Util.datetimeToString(new Date()));
                orgCom.setUpdaterId(Constant.SYS_ADMIN);
                orgCom.setUpdateTime(Util.datetimeToString(new Date()));
                sysOrgComDao.save(orgCom, Util.uuid32());
                orgComId = orgCom.getComId();
                
                orgComCantonRelation = new SysOrgComCantonRelation();
                orgComCantonRelation.setComId(orgCom.getComId());
                orgComCantonRelation.setCantonCode(orgComCode);
                orgComCantonRelation.setRelType(0);
                orgComCantonRelation.setSequence(0);// 需要改
                orgComCantonRelationDao.save(orgComCantonRelation, Util.uuid32());
            }
        } catch (Exception e) {
            logger.error("保存组织机构名称时异常", e);
            throw new RuntimeException(e);
        }
        
        return orgComId;
    }
    
    
}