package com.mlsc.waste.wastecircle.service.impl;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.fileupload.dao.UploadfileDao;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.dao.OfflineMessageDao;
import com.mlsc.waste.wastecircle.model.OfflineMessage;
import com.mlsc.waste.wastecircle.model.OfflineMessageVo;
import com.mlsc.waste.wastecircle.service.OfflineMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("offlineMessageService")
public class OfflineMessageImpl implements OfflineMessageService {

    private final static Logger logger = LoggerFactory.getLogger(OfflineMessageImpl.class);

    @Autowired
    private OfflineMessageDao offlineMessageDao;

    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    private UploadfileDao uploadfileDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveOfflineMessage(OfflineMessage offlineMessage, String businessCode) throws Exception {
        String uuid = Util.uuid32();
        offlineMessageDao.save(offlineMessage, uuid);
        if(StringUtils.isNotBlank(businessCode)){
        	 Uploadfile fileByBusinessCode = uploadfileService.getFileByBusinessCode(businessCode);
             if (StringUtils.isNotBlank(businessCode) && fileByBusinessCode != null) {
                 fileByBusinessCode.setReferenceId(uuid);
                 uploadfileDao.update(fileByBusinessCode);
             }
        }
        return uuid;

    }

    @Override
    public List<OfflineMessageVo> getOfflineMsgList() throws Exception {
        List<OfflineMessageVo> offlineMessageVoList = new ArrayList<OfflineMessageVo>();
        offlineMessageVoList = offlineMessageDao.getOfflineMsgList();
        return offlineMessageVoList;
    }

    @Override
    public int getOfflineMsgcount(String sql, Map<String, Object> paramMap) throws Exception {
        int count = 0;
        try {
            List<OfflineMessageVo> offlineMessageVoList = offlineMessageDao.getOfflineMsgList(sql, paramMap, null);
            if (offlineMessageVoList != null) {
                count = offlineMessageVoList.size();
            }
        } catch (Exception e) {
            logger.error("平台管理员获取企业列表件数时异常", e);
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public List<OfflineMessageVo> getOfflineMsgList(String where, Map<String, Object> paramMap, PagingParameter paging) {
        List<OfflineMessageVo> offlineMessageVoList = new ArrayList<OfflineMessageVo>();
        offlineMessageVoList = offlineMessageDao.getOfflineMsgList(where, paramMap, paging);
        return offlineMessageVoList;
    }

    @Override
    public OfflineMessageVo getOfflineMsgById(String id) throws Exception {
        OfflineMessageVo offlineMessageVo = offlineMessageDao.getOfflineMsgById(id);
        try {
            offlineMessageVo.setStatus("0");
            offlineMessageDao.update(offlineMessageVo);
        } catch (DaoAccessException e) {
            e.printStackTrace();
        }
        return  offlineMessageVo;
    }
}
