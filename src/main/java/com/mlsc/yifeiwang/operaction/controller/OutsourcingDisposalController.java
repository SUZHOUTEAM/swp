package com.mlsc.yifeiwang.operaction.controller;

import com.mlsc.yifeiwang.operaction.entity.OutsourcingDisposal;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.operaction.service.IOutsourcingDisposalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2017-11-21
 */
@Controller
@RequestMapping("/outsourcingDisposal")
public class OutsourcingDisposalController {
    private IOutsourcingDisposalService disposalService;

    /**
     * 委外处置企业
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listOutSouringDisposalEnterprise", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<OutsourcingDisposal>> listOutSouringDisposalEnterprise(OutsourcingDisposal outsourcingDisposal) {
        Result<List<OutsourcingDisposal>> result = new Result<List<OutsourcingDisposal>>();
        try {
            List<OutsourcingDisposal> disposalList = disposalService.listOutSouringDisposalEnterprise(outsourcingDisposal);
            result.setData(disposalList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


}
