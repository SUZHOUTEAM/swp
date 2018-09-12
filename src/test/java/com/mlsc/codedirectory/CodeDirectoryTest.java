package com.mlsc.codedirectory;

import com.mlsc.BaseTest;
import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.codedirectory.model.CodeVo;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yinxl on 2017/7/21.
 */
public class CodeDirectoryTest extends BaseTest {
    @Autowired
    ICodeTypeService codeTypeService;
    @Autowired
    ICodeValueService codeValueService;

    @Test
    public void getCodeValuesTypeCode() throws DaoAccessException {

        List<CodeValue> valueList = codeTypeService.getCodeValuesTypeCode("Disposal_Enter");

        Assert.assertNotNull(valueList);
    }

    @Test
    public void updateCodeTypeStatus() throws DaoAccessException {
        List<String> ids = Arrays.asList("3a3ce9de2ce64fb1b3c3adb3a2c7e03b", "3f696bab2e9944acb7209bbaac55bd13");
        String status = "0";
        String ticketId = null;

        codeTypeService.updateCodeTypeStatus(ids, status, ticketId);
        status = "1";
        codeTypeService.updateCodeTypeStatus(ids, status, ticketId);

    }

    @Test
    public void list() throws DaoAccessException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type_code", "%FOLLOW_TYPE%");
        paramMap.put("type_name", "%易废圈%");
        paramMap.put("information", "SINGLE");
        PagingParameter paging = new PagingParameter();
        paging.setStart(0);
        paging.setLimit(10);
        List<CodeVo> list = codeTypeService.list(paramMap, paging);
        Assert.assertNotNull(list);

        int count = codeTypeService.count(paramMap);
        Assert.assertTrue(count > 0);
    }

    @Test
    public void getCodeValueByCode() throws DaoAccessException {
        CodeValue codeValue = codeValueService.getCodeValueByCode("FOLLOW_TYPE", "SINGLE");
        Assert.assertNotNull(codeValue);
    }

    @Test
    public void deleteCodeValuesByIds() throws DaoAccessException, WasteBusinessException {
        codeValueService.deleteCodeValuesByIds(Arrays.asList("ef435a111f7c4841a4475822d6cff4rr"));
    }

    @Test
    public void listCodeValueByTypeCode() {
        Assert.assertEquals(codeValueService.listCodeValue(null).size(), 0);
        Assert.assertNotNull(codeValueService.listCodeValue("FOLLOW_TYPE"));
    }

}
