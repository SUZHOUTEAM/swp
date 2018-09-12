package com.mlsc.yifeiwang.wasteinformation.service;

import com.mlsc.yifeiwang.wasteinformation.entity.WasteInformation;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.wasteinformation.model.WasteInformationModel;
import com.mlsc.yifeiwang.wasteinformation.model.WasteInformationParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-06-13
 */
public interface IWasteInformationService extends IService<WasteInformation> {

    boolean saveWasteInformation(WasteInformationParam wasteInformationParam, User user, List<String> errors) throws Exception;

    List<WasteInformationModel> listWasteInformation(WasteInformationParam wasteInformationParam, PagingParameter pagingParameter) throws Exception;

    boolean updateWasteInformation(WasteInformationParam wasteInformationParam, User user) throws Exception;

    void updateClickAmount(WasteInformationParam wasteInformationParam) throws Exception;

    WasteInformationModel getWasteInformationById(WasteInformationParam wasteInformationParam) throws Exception;

    boolean deleteWasteInformationById(User user,List<String> ids) throws Exception;
}
