package com.mlsc.waste.wastecircle.mapper;

import com.mlsc.entity.CooperationMessage;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.waste.wastecircle.model.MessageBodyVo;
import com.mlsc.waste.wastecircle.model.SearchCondition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-12-01
 */
public interface CooperationMessageMapper extends BaseMapper<CooperationMessage> {
    /**
     * 获取处置资源列表
     *
     * @param searchCondition
     * @return
     */
    List<MessageBodyVo> listDispositionReleaseList(SearchCondition searchCondition);

    /**
     * 处置资源计数
     *
     * @param searchCondition
     * @return
     */
    int countDispositionReleaseList(SearchCondition searchCondition);

}