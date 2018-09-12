package com.mlsc.yifeiwang.bindserve.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.bindserve.entity.EntBindServe;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.bindserve.model.EntBindServeModel;
import com.mlsc.yifeiwang.bindserve.model.EntBindServeParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-04-26
 */
public interface IEntBindServeService extends IService<EntBindServe> {

    List<String> saveBindServe(User user, EntBindServeParam entBindServeParam) throws Exception;

    void updateBindServe(User user, EntBindServeParam entBindServeParam) throws Exception;

    List<EntBindServeModel> listBindServe(EntBindServeParam entBindServeParam, PagingParameter pagingParameter) throws Exception;

    List listButlerServicesByEntId(EntBindServeParam entBindServeParam );
}
