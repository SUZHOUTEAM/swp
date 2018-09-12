package com.mlsc.yifeiwang.waste.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.waste.entity.WasteName;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.waste.service.IWasteNameService;
import org.springframework.stereotype.Service;
import com.mlsc.yifeiwang.waste.mapper.WasteNameMapper;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangj
 * @since 2017-08-23
 */
@Service("entWasteNameService")
public class WasteNameServiceImpl extends ServiceImpl<WasteNameMapper, WasteName> implements IWasteNameService {

    @Override
    public void saveWasteName(User user,WasteName wasteName){
        Date date = new Date();
        wasteName.setCreateBy(user.getUserId());
        wasteName.setCreateTime(date);
        wasteName.setEditBy(user.getUserId());
        wasteName.setEditTime(date);
        this.insert(wasteName);
    }

    @Override
    public int getWasteNameId(WasteName wasteName) {
        return this.baseMapper.getWasteNameId(wasteName);
    }


    @Override
    public List<WasteName> listWasteName(WasteName wasteName) {
        return this.baseMapper.listWasteName(wasteName);
    }
}
