package com.mlsc.yifeiwang.operaction.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOperationContacts;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationContactsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
@Controller
@RequestMapping("/websiteOperationContacts")
public class WebsiteOperationContactsController {
    private final static Logger logger = LoggerFactory.getLogger(WebsiteOperationController.class);

    @Autowired
    private IWebsiteOperationContactsService contactsService;

    @ResponseBody
    @RequestMapping(value = "/listOperationContacts", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Page<WebsiteOperationContacts>> listWasteEnterprise(String operationId, Page<WebsiteOperationContacts> page) throws Exception {
        Result<Page<WebsiteOperationContacts>> result = new Result<Page<WebsiteOperationContacts>>();
        Page<WebsiteOperationContacts> enterpriseUserList = null;
        try {
            enterpriseUserList = contactsService.pageWebsiteOperationContacts(operationId, page);
            result.setData(enterpriseUserList);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            result.setStatus(ResultStatus.Error);
            logger.error("分页产废企业列表时异常", e);
        }
        return result;
    }

}
