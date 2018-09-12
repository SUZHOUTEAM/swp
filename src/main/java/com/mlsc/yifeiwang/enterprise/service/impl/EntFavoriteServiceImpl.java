package com.mlsc.yifeiwang.enterprise.service.impl;

import com.mlsc.yifeiwang.enterprise.entity.EntFavorite;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.service.IEntFavoriteService;
import com.mlsc.yifeiwang.enterprise.mapper.EntFavoriteMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
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
 * @since 2017-10-16
 */
@Service
public class EntFavoriteServiceImpl extends ServiceImpl<EntFavoriteMapper, EntFavorite> implements IEntFavoriteService {
    private final static Logger logger = LoggerFactory.getLogger(EntFavoriteServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertEntFavorite(User user, EntFavorite entFavorite) throws Exception {
        Date date = new Date();
        entFavorite.setEntId(user.getEnterpriseId());
        entFavorite.setCreateBy(user.getUserId());
        entFavorite.setCreateTime(date);
        entFavorite.setEditBy(user.getUserId());
        entFavorite.setEditTime(date);
        return this.insert(entFavorite);
    }

    @Override
    public boolean validateEntFavorite(EntFavorite entFavorite, List<String> errorList){
        boolean validate = true;
        if(entFavorite==null){
            validate = false;
            errorList.add("关注信息不能为空");
        }else{
            if(StringUtils.isBlank(entFavorite.getFavoriteType())){
                validate = false;
                errorList.add("关注类型不能为空");
            }
        }
        return validate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean cancelEntFavorite(User user, EntFavorite entFavorite) throws Exception {
        try {
            this.baseMapper.deleteFavorite(entFavorite);
        }catch(Exception e){
            logger.error("取消收藏时异常",e);
            throw e;
        }
        return true;

    }
}
