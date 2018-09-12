package com.mlsc.yifeiwang.waste.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.waste.entity.Waste;
import com.mlsc.yifeiwang.waste.entity.WasteType;
import com.mlsc.yifeiwang.waste.service.IWasteTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.mlsc.yifeiwang.waste.mapper.WasteTypeMapper;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangj
 * @since 2017-08-23
 */
@Service("entWasteTypeService")
public class WasteTypeServiceImpl extends ServiceImpl<WasteTypeMapper, WasteType> implements IWasteTypeService {
    private final static Logger logger = LoggerFactory.getLogger(WasteTypeServiceImpl.class);

    @Override
    public List<WasteType> listWasteInfo() throws Exception{
        List<WasteType> wasteInfoList = null;
        try {
            wasteInfoList = this.baseMapper.listWasteInfo();
        } catch (Exception e) {
            logger.error("获取危废信息列表时异常",e);
            throw e;
        }

        return wasteInfoList;

    }


    @Override
    public List<WasteType> listWasteType() throws Exception{
        List<WasteType> wasteInfoList = null;
        try {
            wasteInfoList = this.baseMapper.listWasteType();
        } catch (Exception e) {
            logger.error("获取二位码信息时异常",e);
            throw e;
        }
        return wasteInfoList;
    }

    @Override
    public  List<Waste> listWasteCode() throws Exception {
        List<Waste> wasteCodeList = null;
        try {
            wasteCodeList = this.baseMapper.listWasteCode();
        } catch (Exception e) {
            logger.error("获取八位码信息时异常",e);
            throw e;
        }
        return wasteCodeList;
    }
}
