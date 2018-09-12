package com.mlsc.yifeiwang.enterprise.service;

import com.mlsc.yifeiwang.enterprise.entity.EntGlory;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.waste.user.model.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-10-20
 */
public interface IEntGloryService extends IService<EntGlory> {

    boolean saveEntGlory(User user, EntGlory entGlory) throws Exception;

    boolean validateEntGlory(EntGlory entGlory, List<String> errorList);

    boolean updateEntGlory(User user, EntGlory entGlory) throws Exception;

    boolean deleteEntGlory(EntGlory entGlory) throws Exception;

    List<EntGlory> listEntGloryByEntId(User user) throws Exception;

    EntGlory getEntGloryById(String id) throws Exception;
}
