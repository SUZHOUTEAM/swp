package com.mlsc.yifeiwang.discusstag.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.discusstag.common.TagStatusEnum;
import com.mlsc.yifeiwang.discusstag.common.TagTypeEnum;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.discusstag.entity.DiscussTag;
import com.mlsc.yifeiwang.discusstag.mapper.DiscussTagMapper;
import com.mlsc.yifeiwang.discusstag.model.DiscussTagParam;
import com.mlsc.yifeiwang.discusstag.service.IDiscussTagService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-13
 */
@Service
public class DiscussTagServiceImpl extends ServiceImpl<DiscussTagMapper, DiscussTag> implements IDiscussTagService {
    private final static Logger logger = LoggerFactory.getLogger(DiscussTagServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveDiscussTag(User user, DiscussTagParam discussTagParam) throws Exception {
        try {
            DiscussTag discussTag = new DiscussTag();
            discussTag.setTagType(discussTagParam.getTagType());
            discussTag.setBusiStatus(discussTagParam.getTagStatus());
            discussTag.setEntId(user.getEnterpriseId());
            discussTag.setReleaseId(discussTagParam.getReleaseId());
            if (StringUtils.isEmpty(discussTagParam.getComments())) {
                discussTag.setComments(TagStatusEnum.getNameByCode(discussTagParam.getTagStatus()));
            } else {
                discussTag.setComments(discussTagParam.getComments());
            }
            if (TagTypeEnum.SAMPLING.getCode().equalsIgnoreCase(discussTagParam.getTagType())) {
                discussTag.setSampleDate(discussTagParam.getSampleDate());
                discussTag.setContacts(discussTagParam.getContacts());
                discussTag.setContactsTel(discussTagParam.getContactsTel());
            }
            Date date = new Date();
            discussTag.setCreateBy(user.getUserId());
            discussTag.setCreateTime(date);
            discussTag.setEditBy(user.getUserId());
            discussTag.setEditTime(date);
            int record = this.baseMapper.insert(discussTag);
            if (record != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("新增标记时异常", e);
            throw e;
        }
    }

    @Override
    public boolean validateDiscussTag(DiscussTagParam discussTagParam, List<String> errorList) {
        boolean validate = true;
        if (discussTagParam == null) {
            errorList.add("标记不能为空");
            validate = false;
        } else {
            if (StringUtils.isBlank(discussTagParam.getTagType())) {
                errorList.add("标记类型不能为空");
                validate = false;
            }
        }
        return validate;
    }

    @Override
    public List<DiscussTag> listTagListByReleaseId(String releaseId, String entId) throws Exception {
        try {
            if (StringUtils.isNotBlank(releaseId) && StringUtils.isNotBlank(entId)) {
                return this.baseMapper.listTagListByReleaseId(releaseId, entId);
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error("根据产废委托id获取标记列表时异常", e);
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteDiscussTag(User user, String id) throws Exception {
        try {
            if (StringUtils.isNotBlank(id)) {
                DiscussTag discussTag = selectById(id);
                if (discussTag != null) {
                    Date date = new Date();
                    discussTag.setEditBy(user.getUserId());
                    discussTag.setEditTime(date);
                    discussTag.setDeleteFlag(1);
                    return updateById(discussTag);
                }
            }
        } catch (Exception e) {
            logger.error("删除标记列表时异常", e);
            throw e;
        }
        return false;
    }

    @Override
    public DiscussTagParam countTagInfo(String releaseId, String entId) throws Exception {
        DiscussTagParam discussTagParam = new DiscussTagParam();
        try {
            if(StringUtils.isNotBlank(releaseId)&&StringUtils.isNotBlank(entId)){
                EntityWrapper<DiscussTag> ew = new EntityWrapper<>();
                ew.setSqlSelect("id,releaseId,comments,contacts,contactsTel,sampleDate,tagType,busiStatus,createBy,createTime");
                ew.eq("releaseId", releaseId);
                ew.eq("entId", entId);
                ew.eq("deleteFlag", 0);
                ew.orderBy(" createTime desc ");
                DiscussTag discussTag = selectOne(ew);
                if (discussTag != null) {
                    int count = selectCount(ew);
                    discussTagParam.setTagStatus(TagStatusEnum.getNameByCode(discussTag.getBusiStatus()));
                    discussTagParam.setTagType(TagTypeEnum.getNameByCode(discussTag.getTagType()));
                    discussTagParam.setCount(count);
                }
            }
        } catch (Exception e) {
            logger.error("根据releaseId统计标记", e);
            throw e;
        }
        return discussTagParam;
    }
}
