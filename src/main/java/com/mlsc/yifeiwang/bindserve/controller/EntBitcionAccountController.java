package com.mlsc.yifeiwang.bindserve.controller;


import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.yifeiwang.bindserve.model.EntBitcionAccountModel;
import com.mlsc.yifeiwang.bindserve.model.EntBitcionAccountParam;
import com.mlsc.yifeiwang.bindserve.service.IEntBitcionAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2018-04-26
 */
@Controller
@RequestMapping("/entBitcionAccount")
public class EntBitcionAccountController {
    @Autowired
    private IEntBitcionAccountService bitcionAccountService;

    /**
     * 确认帐户余额是否充足
     *
     * @param user
     * @param bitcionAccountParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkEntAccount", method = RequestMethod.POST)
    public Result<EntBitcionAccountModel> checkEntAccount(User user, @RequestBody EntBitcionAccountParam bitcionAccountParam) {
        Result<EntBitcionAccountModel> result = new Result<EntBitcionAccountModel>();
        try {
            EntBitcionAccountModel accountModel = bitcionAccountService.checkEntAccount(user, bitcionAccountParam);
            result.setData(accountModel);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }


    /**
     * 处置企业用户领取易废币
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getFreeBitcion")
    public Result<EntBitcionAccountModel> getFreeBitcion(User user) {
        Result<EntBitcionAccountModel> result = new Result<EntBitcionAccountModel>();
        try {
            bitcionAccountService.saveBitcion(user, Constant.FREE_BITCION);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    /**
     * 处置企业是否有易币帐户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "bitcionAccountExist", method = RequestMethod.POST)
    public Result<Boolean> bitcionAccountExist(User user) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            result.setData(bitcionAccountService.bitcionAccountExist(user));
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }
}
