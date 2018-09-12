package com.mlsc.yifeiwang.enterprise.service;

import com.mlsc.yifeiwang.enterprise.entity.EntEvaluate;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.waste.user.model.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-10-17
 */
public interface IEntEvaluateService extends IService<EntEvaluate> {

    List<EntEvaluate> addEntEvaluate(User user, EntEvaluate entEvaluate) throws Exception;

    boolean validateEntEvaluate(EntEvaluate entEvaluate, List<String> errorList);

    List<EntEvaluate> listEntEvaluate(EntEvaluate entEvaluate) throws Exception;

    Boolean CheckEntEvaluate(User user, EntEvaluate entEvaluate) throws Exception;

    List<EntEvaluate> listEntEvaluateByEntId(EntEvaluate entEvaluate) throws Exception;
}
