package com.mlsc.yifeiwang.wasterealase.controller;


import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseParam;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
@Controller
@RequestMapping("/entReleaseDetail")
public class EntReleaseDetailController {
    @Autowired
    private IEntReleaseDetailService releaseDetailService;

    /**
     * 处置询价详情列表
     *
     * @param user
     * @param releaseParams
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/listReleaseDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<EntReleaseModel> listEntWaste(User user, EntReleaseParam releaseParams) {
        Result<EntReleaseModel> result = new Result<EntReleaseModel>();
        try {
            EntReleaseModel releaseModel = releaseDetailService.initInquiryList(user, releaseParams);
            result.setData(releaseModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

}
