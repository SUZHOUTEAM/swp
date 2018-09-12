package com.mlsc.yifeiwang.waste.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.waste.entity.WasteName;
import com.mlsc.waste.user.model.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangj
 * @since 2017-08-23
 */
public interface IWasteNameService extends IService<WasteName> {
    void saveWasteName(User user, WasteName wasteName);

    int getWasteNameId(WasteName wasteName);


    List<WasteName> listWasteName(WasteName wasteName);
}
