package com.mlsc.yifeiwang.enterprise.mapper;

import com.mlsc.yifeiwang.enterprise.entity.EntEvaluate;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.enterprise.model.EntEvaluateModel;
import com.mlsc.yifeiwang.enterprise.model.EntEvaluateParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-10-17
 */
@Component
public interface EntEvaluateMapper extends BaseMapper<EntEvaluate> {

    List<EntEvaluate> listEntEvaluate(EntEvaluate entEvaluate);

    String getTotalScorePercentage(EntEvaluateParam entEvaluateParam);

    Integer countAllOrderValuation(EntEvaluateParam entEvaluateParam);

    List<EntEvaluateModel> listAllOrderValuationData(EntEvaluateParam entEvaluateParam);

    int countAllOrderValuationData(EntEvaluateParam entEvaluateParam);
}