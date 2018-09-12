package com.mlsc.yifeiwang.waste.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.waste.entity.Waste;
import com.mlsc.yifeiwang.waste.entity.WasteType;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangj
 * @since 2017-08-23
 */
public interface IWasteTypeService extends IService<WasteType> {
    List<WasteType> listWasteInfo() throws Exception;

    List<WasteType> listWasteType() throws Exception;

    List<Waste> listWasteCode() throws Exception;
}
