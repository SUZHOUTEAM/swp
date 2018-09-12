package com.mlsc.yifeiwang.enterprise.service;

import com.mlsc.yifeiwang.enterprise.entity.EntFavorite;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.waste.user.model.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-10-16
 */
public interface IEntFavoriteService extends IService<EntFavorite> {

    boolean insertEntFavorite(User user, EntFavorite entFavorite) throws Exception;

    boolean validateEntFavorite(EntFavorite entFavorite, List<String> errorList);

    boolean cancelEntFavorite(User user, EntFavorite entFavorite) throws Exception;
}
