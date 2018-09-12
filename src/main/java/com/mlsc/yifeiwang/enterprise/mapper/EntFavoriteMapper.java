package com.mlsc.yifeiwang.enterprise.mapper;

import com.mlsc.yifeiwang.enterprise.entity.EntFavorite;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-10-16
 */
public interface EntFavoriteMapper extends BaseMapper<EntFavorite> {

    void deleteFavorite(EntFavorite entFavorite);
}