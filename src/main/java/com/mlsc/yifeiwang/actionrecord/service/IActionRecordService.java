package com.mlsc.yifeiwang.actionrecord.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.actionrecord.modal.ActionVO;
import com.mlsc.yifeiwang.actionrecord.entity.ActionRecord;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yxl
 * @since 2017-07-25
 */
public interface IActionRecordService extends IService<ActionRecord> {

    void saveActionRecord(ActionVO actionVO) throws Exception;
}
