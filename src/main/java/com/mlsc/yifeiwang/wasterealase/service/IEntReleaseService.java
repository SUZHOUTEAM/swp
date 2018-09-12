package com.mlsc.yifeiwang.wasterealase.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.wasterealase.entity.EntRelease;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseParam;
import com.mlsc.yifeiwang.wasterealase.model.InformEnterpriseModel;
import com.mlsc.yifeiwang.wasterealase.model.ReleaseStatusModel;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
public interface IEntReleaseService extends IService<EntRelease> {

    //    List<InformEnterpriseModel> saveEntRelease(String ticketId, User user, EntReleaseParam entReleaseParam) throws Exception;
    EntReleaseModel saveEntRelease( User user, EntReleaseParam entReleaseParam) throws Exception;

    int informEnterpriseByReleaseId(User user, EntReleaseParam entReleaseParam) throws Exception;

    List<EntReleaseModel> listWasteEntRelease(User user, EntReleaseParam releaseParam, PagingParameter pagingParameter) throws Exception;

    int countEntRelease(EntReleaseParam releaseParams);

    EntReleaseModel getEntReleaseById(EntReleaseParam entReleaseParam) throws Exception;

    List<EntReleaseModel> listWasteEntReleaseByEnterId(User user, EntReleaseParam releaseParam, PagingParameter pagingParameter) throws Exception;

    boolean deleteWasteEntReleaseByReleaseId(User user, EntReleaseParam releaseParam) throws Exception;


    List<EntReleaseModel> listWasteEntReleaseByActivityId(EntReleaseParam releaseParam, PagingParameter pagingParameter) throws Exception;

    boolean refusedWasteEntApply(EntReleaseParam releaseParam, User user) throws Exception;

    List<ReleaseStatusModel> countActivityStatusByActivityId(String activityId) throws Exception;

    boolean endWasteEntReleaseByReleaseId(User user, EntReleaseParam releaseParam) throws Exception;

    EntReleaseModel generateReleasePoster(String userId) throws Exception;

    List<EntReleaseModel> listWasteEntRelease4FacilitatorEnt(EntReleaseParam releaseParam, PagingParameter pagingParameter) throws Exception;

    void saveEntWaste(User user, EntReleaseParam entReleaseParams) throws Exception;

    void sendHelpMsg(User user) throws Exception;

}
