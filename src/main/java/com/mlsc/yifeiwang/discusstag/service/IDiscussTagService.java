package com.mlsc.yifeiwang.discusstag.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.discusstag.entity.DiscussTag;
import com.mlsc.yifeiwang.discusstag.model.DiscussTagParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-13
 */
public interface IDiscussTagService extends IService<DiscussTag> {

    boolean saveDiscussTag(User user, DiscussTagParam discussTagParam)throws Exception;

    boolean validateDiscussTag(DiscussTagParam discussTagParam, List<String> errorList);

    List<DiscussTag> listTagListByReleaseId(String releaseId, String entId) throws Exception;

    boolean deleteDiscussTag(User user, String id)throws Exception;

    DiscussTagParam countTagInfo(String releaseId, String entId) throws Exception;
}
