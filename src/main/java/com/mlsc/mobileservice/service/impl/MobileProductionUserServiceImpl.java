package com.mlsc.mobileservice.service.impl;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.mobileservice.model.DispositionCapacityDetailReleaseVo;
import com.mlsc.mobileservice.service.MobileProductionUserService;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.wastecircle.model.CoopMsgVo;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.waste.wastecircle.service.CoopMsgService;
import com.mlsc.waste.wastecircle.service.WasteCircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("MobileProductionUserService")
public class MobileProductionUserServiceImpl implements MobileProductionUserService {

    @Autowired
    private WasteCircleService wasteCircleService;

    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    private CoopMsgService coopMsgService;

    @Autowired
    private LicenceService licenceService;

    @Autowired
    private ICodeValueService codeValueService;

    @Override
    public List<CoopMsgVo> getMessageListReleaseList(String ticketId, User user, PagingParameter pagingParameter, String searchCondition, String cantonCode, String sortByDistance) {
        EnterpriseInfo enterpriseInfo = null;
        List<CoopMsgVo> messageList = new ArrayList<CoopMsgVo>();
        initPagingParameter(pagingParameter);
        try {
            enterpriseInfo = wasteCircleService.getEnterpriseInfoByUserId(ticketId, user);
            if (enterpriseInfo != null) {
                user.setEnterpriseId(enterpriseInfo.getId());
                messageList = coopMsgService.getCoopMsgsForProductionUser(pagingParameter, ticketId, enterpriseInfo.getEnterType().getCode(), user, searchCondition, cantonCode, sortByDistance);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return messageList;
    }

    private void initPagingParameter(PagingParameter pagingParameter) {
        if (pagingParameter.getPageSize() == 0) {
            pagingParameter.setPageSize(5);
        }

        if (pagingParameter.getPageIndex() == 0) {
            pagingParameter.setPageIndex(1);
        }

        int start = (pagingParameter.getPageIndex() - 1) * pagingParameter.getPageSize();
        pagingParameter.setStart(start);
        pagingParameter.setLimit(pagingParameter.getPageSize());
    }
   

    @Override
    public List<DispositionCapacityDetailReleaseVo> getDispositionCapacityDetailReleaseVo(String licenceId, String entId) throws Exception {
        List<DispositionCapacityDetailReleaseVo> dispositionCapacityDetailReleaseVoList = new ArrayList<DispositionCapacityDetailReleaseVo>();
        dispositionCapacityDetailReleaseVoList = licenceService.dispositionCapacityDetailReleaseVoList(licenceId, entId);
        return dispositionCapacityDetailReleaseVoList;
    }
}
