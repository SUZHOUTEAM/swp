package com.mlsc.yifeiwang.entinquiry.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.entinquiry.entity.EntInquiry;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
public interface IEntInquiryService extends IService<EntInquiry> {
    boolean saveEntInquiry(User user, EntInquiryParam inquiryParam) throws Exception;

    boolean insertEntInquiry(User user, EntInquiryParam inquiryParam, EntInquiry entInquiry);

    void saveEntDetailInquiry(EntInquiryParam inquiryParam, EntInquiry entInquiry, User user);

    List<EntInquiryModel> listWasteInquiry(User user, PagingParameter pagingParameter, EntInquiryParam inquiryParam) throws Exception;

    EntInquiryModel countTotalInquiryInfo(User user,EntInquiryParam inquiryParam) throws Exception;

    List<EntInquiry> listEntInquiryByReleaseId(String releaseId) throws Exception;

    int countWasteInquiry(EntInquiryParam inquiryParam) throws Exception;

    boolean rejectEntInquiry(User user, EntInquiryParam inquiryParam) throws Exception;

    boolean confirmEntInquiry(User user, EntInquiryParam inquiryParam) throws Exception;

    boolean deleteEntInquiry(User user, EntInquiryParam inquiryParam) throws Exception;

    int countUntreatedInquiry(User user) throws Exception;

    boolean updateResponsibleEntInquiry(User user, EntInquiryParam inquiryParam) throws Exception;

    boolean updateEntInquiryPriority(User user, EntInquiryParam inquiryParam) throws Exception;

    EntInquiryModel getReferencePrice(String releaseId) throws Exception;

    List listReleaseEntNameByEntId(String enterpriseId) throws Exception;
}
