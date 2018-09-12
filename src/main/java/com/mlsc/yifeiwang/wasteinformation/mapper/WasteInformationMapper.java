package com.mlsc.yifeiwang.wasteinformation.mapper;

import com.mlsc.yifeiwang.wasteinformation.entity.WasteInformation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.wasteinformation.model.WasteInformationModel;
import com.mlsc.yifeiwang.wasteinformation.model.WasteInformationParam;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-06-13
 */
public interface WasteInformationMapper extends BaseMapper<WasteInformation> {

    int countWasteInformation(WasteInformationParam wasteInformationParam);

    List<WasteInformationModel> listWasteInformation(WasteInformationParam wasteInformationParam);

    void updateClickAmount(WasteInformationParam wasteInformationParam);

    WasteInformationModel getWasteInformationById(WasteInformationParam wasteInformationParam);
}