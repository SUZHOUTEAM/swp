package com.mlsc.yifeiwang.codedirectory.controller;

import com.mlsc.yifeiwang.codedirectory.entity.CodeType;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.codedirectory.model.CodeVo;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.fw.base.controller.BaseController;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.utils.datatable.DataTablesUtils;
import com.mlsc.waste.utils.datatable.FilterConditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典管理Controller
 * 
 * @author Hou qb
 */
@Controller
@RequestMapping("/codeType")
public class CodeTypeController extends BaseController{

    private final static Logger logger = LoggerFactory.getLogger(CodeTypeController.class);
    @Autowired
    private ICodeTypeService codeTypeService;
    @Autowired
    private ICodeValueService codeValueService;

    private static final String PATH = "CodeType/";

    /**
     * 主页
     * 
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "list");
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 返回json格式的行业列表
     * 
     * @return
     * @throws DaoAccessException 
     */
    @RequestMapping("/listForJson")
    @ResponseBody
    public Map<String, Object> listForJson(HttpServletRequest request,int start, int length, String draw) throws DaoAccessException {
        List<CodeVo> list = null;
        int total = 0;

        //构造过滤条件
        FilterConditions filterConditions = DataTablesUtils.generateFilterConditions(request);
        //构造分页对象
        PagingParameter paging=DataTablesUtils.generatePagingParameter(start, length);
        
        //统计符合条件的记录数
        total = codeTypeService.count(filterConditions.getParamMap());
        
        if(total > 0){
            //获取符合条件的记录集合
            list = codeTypeService.list(filterConditions.getParamMap(), paging);
        }
        //构造并返回结果集
        return DataTablesUtils.generateResult(draw, total, total, list);
    }

    /**
     * 查看
     * 
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request) throws DaoAccessException {
        ModelAndView mv = new ModelAndView(PATH + "detail");
        String codeTypeId = request.getParameter("id");
        CodeType codeType = codeTypeService.getCodeTypeById(codeTypeId);
        mv.addObject("typeName", codeType.getType_name());
        List<CodeValue> codeValues = codeValueService.getCodeValuesByTypeId(codeTypeId);
        mv.addObject("codeValueList", codeValues);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 新增页面的初始化
     * 
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(PATH + "add");
        mv.addObject("codeType", new CodeType());
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 编辑页面的初始化
     * 
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @RequestMapping("/edit")
    public ModelAndView edit(HttpServletRequest request) throws DaoAccessException {
        ModelAndView mv = new ModelAndView(PATH + "edit");
        String codeTypeId = request.getParameter("id");
        CodeType codeType = codeTypeService.getCodeTypeById(codeTypeId);// 字典类型数据获取
        List<CodeValue> codeValues = codeValueService.getCodeValuesByTypeId(codeTypeId);// 字典信息数据获取
        mv.addObject("codeType", codeType);
        mv.addObject("codeValues", codeValues);
        mv.addObject("ticketId", request.getParameter("ticketId"));
        return mv;
    }

    /**
     * 保存或者更新字典数据
     * 
     * @param codeType 字典类型实体类
     * @param request
     * @return
     * @throws DaoAccessException 
     */
    @ResponseBody
    @RequestMapping("/saveCodeTypeAndValue")
    public Result<Map<String, String>> saveCodeType(CodeType codeType,HttpServletRequest request) throws DaoAccessException {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        String splice = request.getParameter("splice");// 字典信息是否含有编码
        String[] codeArr = request.getParameterValues("code");// 字典信息编码
        String[] valueArr = request.getParameterValues("value");// 字典信息名称
        String ticketId = request.getParameter("ticketId");
        // 字典类型表保存或更新
        splice = StringUtils.isBlank(splice)?Constant.SPLICE_OFF:Constant.SPLICE_ON;
        codeType.setSplice(splice);

        // 字典信息封装成list
        List<CodeValue> codeValues = initialCodeValue(codeArr, valueArr, null,null, "1");
        codeTypeService.saveCodeTypeAndValue(codeValues, codeType,ticketId);
        result.setStatus(ResultStatus.Success);
        result.setData(map);
        return result;
    }

    /**
     * 保存或者更新字典信息数据
     * 
     * @param codeType 行业类别实体类
     * @param request
     * @return
     * @throws DaoAccessException 
     */
    @ResponseBody
    @RequestMapping("/updateCodeTypeAndValue")
    public Result<Map<String, String>> updateCodeType(CodeType codeType,HttpServletRequest request) throws DaoAccessException {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        String splice = request.getParameter("splice");// 字典信息是否含有编码
        String[] idArr = request.getParameterValues("selectck"); // 字典信息ID
        String[] codeArr = request.getParameterValues("code");// 字典信息编码
        String[] valueArr = request.getParameterValues("value");// 字典信息名称
        String ticketId = request.getParameter("ticketId");
        String msg = null;
        // 字典类型表保存或更新
        CodeType codeTypeEntity = codeTypeService.getCodeTypeById(codeType.getId());
        splice = StringUtils.isBlank(splice)?Constant.SPLICE_OFF:Constant.SPLICE_ON;
        codeTypeEntity.setSplice(splice);

        // 字典信息封装成list
        List<CodeValue> codeValues = initialCodeValue(codeArr, valueArr, idArr,codeTypeEntity.getId(), "2");
        codeTypeService.updateCodeTypeAndValue(codeValues, codeTypeEntity,ticketId);

        msg = "已成功编辑字典信息" + codeType.getType_name();
        result.setStatus(ResultStatus.Success);
        map.put("msg", msg);
        result.setData(map);
        return result;
    }

    /**
     * 保存或者更新字典类型数据
     * 
     *            行业类别实体类
     * @param request
     * @return
     * @throws DaoAccessException 
     */
    @ResponseBody
    @RequestMapping("/saveTypeCode")
    public Result<Map<String, String>> saveTypeCode(CodeType codeType,HttpServletRequest request) throws DaoAccessException {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        // 获取字典类型表
        CodeType codeTypeEntity = codeTypeService.getCodeTypeById(codeType.getId());
        codeTypeEntity.setType_code(codeType.getType_code());
        codeTypeEntity.setType_name(codeType.getType_name());

        // 字典信息封装成list
        codeTypeService.updateCodeTypeAndValue(new ArrayList<CodeValue>(),codeTypeEntity,ticketId);
        result.setStatus(ResultStatus.Success);
        result.setData(map);
        return result;
    }

    /**
     * 根据id,code查询行业
     * 
     * @param request
     * @return
     * @throws DaoAccessException 
     */
    @ResponseBody
    @RequestMapping("/isTypeCodeExistent")
    public Result<Map<String, Object>> isTypeCodeExistent(HttpServletRequest request) throws DaoAccessException {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String id = request.getParameter("id");
        String code = request.getParameter("type_code");
        dataMap.put("checkResult", codeTypeService.isHasData(id, code));
        result.setData(dataMap);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 根据id批量删除codeValue
     * 
     * @param request
     * @return
     * @throws DaoAccessException
     */
    @ResponseBody
    @RequestMapping("/removeCodeValue")
    public Result<Map<String, String>> removeCodeValue(HttpServletRequest request) throws DaoAccessException{
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] idStrings = request.getParameterValues("selectck");
        List<String> ids = Util.stringArrayConvertToList(idStrings);
        codeValueService.deleteCodeValuesByIds(ids);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 根据id批量删除
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping("/removeCodeType")
    public Result<Map<String, String>> removeCodeType(HttpServletRequest request) throws Exception {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] idStrings = request.getParameterValues("id");
        List<String> ids = Util.stringArrayConvertToList(idStrings);
        codeTypeService.removeCodeTypeAndValueByIds(ids);
        result.setStatus(ResultStatus.Success);
        return result;
    }

    /**
     * 根据id批量启用常用字典
     * 
     * @param request
     * @return
     * @throws DaoAccessException 
     */
    @ResponseBody
    @RequestMapping("/enableCodeType")
    public Result<Map<String, String>> enableCodeType(HttpServletRequest request) throws DaoAccessException {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] typeIds = request.getParameterValues("id");
        String ticketId = request.getParameter("ticketId");
        List<String> typeIdList = Util.stringArrayConvertToList(typeIds);
        codeTypeService.updateCodeTypeStatus(typeIdList, Constant.ENABLED,ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }
    
    /**
     * 根据id批量停用常用字典
     * 
     * @param request
     * @return
     * @throws DaoAccessException 
     */
    @ResponseBody
    @RequestMapping("/disableCodeType")
    public Result<Map<String, String>> disableCodeType(HttpServletRequest request) throws DaoAccessException {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        String[] typeIds = request.getParameterValues("id");
        String ticketId = request.getParameter("ticketId");
        List<String> typeIdList = Util.stringArrayConvertToList(typeIds);
        codeTypeService.updateCodeTypeStatus(typeIdList, Constant.DISABLE,ticketId);
        result.setStatus(ResultStatus.Success);
        return result;
    }
    
    /**
     * 字典信息封装成list
     * 
     * @param codeArr 字典信息编码
     * @param valueArr 字典信息名称
     * @param idArr  字典信息ID
     * @param typeId  更新情况下，typeId设置
     * @param model 1:保存；2：更新
     * @return
     * @throws DaoAccessException 
     */
    private List<CodeValue> initialCodeValue(String[] codeArr, String[] valueArr, String[] idArr, String typeId,String model) throws DaoAccessException {
        CodeValue codeValue = null;
        List<CodeValue> codeValues = new ArrayList<CodeValue>();
        if ("1".equals(model)) {
            for (int i = 0; i < valueArr.length; i++) {
                if (StringUtils.isBlank(valueArr[i])) {
                    continue;
                }
                codeValue = new CodeValue();
                codeValue.setCode(codeArr == null ? "" : codeArr[i]);
                codeValue.setValue(valueArr[i]);
                codeValues.add(codeValue);
            }
        } else if ("2".equals(model)) {
            for (int i = 0; i < valueArr.length; i++) {
                if (StringUtils.isBlank(valueArr[i])) {
                    continue;
                }
                // 新增加的字典信息
                if (Constant.CHECKED_ON.equals(idArr[i])) {
                    codeValue = new CodeValue();
                    codeValue.setType_id(typeId);
                    codeValue.setCode(codeArr == null ? "" : codeArr[i]);
                    codeValue.setValue(valueArr[i]);
                } else {
                    // 编辑字典信息
                    codeValue = codeValueService.getCodeValueById(idArr[i]);
                    codeValue.setCode(codeArr[i]);
                    codeValue.setValue(valueArr[i]);
                }
                codeValues.add(codeValue);
            }
        }
        
        return codeValues;
    }

    @RequestMapping("/listCodeValue")
    @ResponseBody
    public Result<List<CodeValue>> listCodeValue(String ticketId,String typeCode){
        Result<List<CodeValue>> result = new Result<>();
        try {
            List<CodeValue> list = codeValueService.listCodeValue(typeCode);
            result.setData(list);
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            logger.error("根据typeCode获取codeValue出错",e);
            result.setStatus(ResultStatus.Error);
        }
        return  result;
    }
}
