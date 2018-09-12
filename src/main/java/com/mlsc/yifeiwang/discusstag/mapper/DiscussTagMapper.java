package com.mlsc.yifeiwang.discusstag.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.discusstag.entity.DiscussTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-11-13
 */
public interface DiscussTagMapper extends BaseMapper<DiscussTag> {
    List<DiscussTag> listTagListByReleaseId(@Param("releaseId") String releaseId, @Param("entId") String entId);
}