package com.mlsc.yifeiwang.actionrecord.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.actionrecord.modal.ActionVO;
import com.mlsc.yifeiwang.actionrecord.entity.ActionRecord;
import com.mlsc.yifeiwang.actionrecord.mapper.ActionRecordMapper;
import com.mlsc.yifeiwang.actionrecord.service.IActionRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yxl
 * @since 2017-07-25
 */
@Service
public class ActionRecordServiceImpl extends ServiceImpl<ActionRecordMapper, ActionRecord> implements IActionRecordService {
    private final static Logger logger = LoggerFactory.getLogger(ActionRecordServiceImpl.class);

    @Override
    public void saveActionRecord(ActionVO actionVO) throws Exception {
        try {
            ActionRecord ar = new ActionRecord();
            ar.setKeywords(actionVO.getKeyWord());
            ar.setFirstAction(actionVO.getEnterpriseId());
            ar.setIpAddr(actionVO.getCip());
            ar.setLocation(actionVO.getCid() + actionVO.getCname());
            ar.setActionTime(new Date());
            this.insert(ar);
        } catch (Exception e) {
            logger.error("保存用户信息时异常", e);
            throw e;
        }

    }
}
