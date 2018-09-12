package com.mlsc.yifeiwang.codedirectory.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.codedirectory.entity.CodeType;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.codedirectory.mapper.CodeTypeMapper;
import com.mlsc.yifeiwang.codedirectory.model.CodeDirectory;
import com.mlsc.yifeiwang.codedirectory.model.CodeVo;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxl
 * @since 2017-07-20
 */
@Service
public class CodeTypeServiceImpl extends ServiceImpl<CodeTypeMapper, CodeType> implements ICodeTypeService {


    @Autowired
    ICodeValueService codeValueService;

    public List<CodeDirectory> listAllCodeType(){
        return this.baseMapper.listAllCodeType();
    }

    @Override
    public void saveCodeTypeAndValue(List<CodeValue> codeValues, CodeType codeType, String ticketId) throws DaoAccessException {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        codeType.setStatus(Constant.ENABLED);
        codeType.setCreate_by(user.getUserId());
        codeType.setCreate_time(new Date());
        codeType.setEdit_by(user.getUserId());
        codeType.setEdit_time(codeType.getCreate_time());
        this.insert(codeType);
        // 字典类型代码设置
        for (CodeValue codeValue : codeValues) {
            codeValue.setType_id(codeType.getId());
            codeValue.setTypeCode(codeType.getType_code());
            codeValue.setCreate_by(user.getUserId());
            codeValue.setCreate_time(codeType.getCreate_time());
            codeValue.setEdit_by(user.getUserId());
            codeValue.setEdit_time(codeType.getCreate_time());
        }
        codeValueService.insertOrUpdateBatch(codeValues);
    }

    @Override
    public void updateCodeTypeAndValue(List<CodeValue> codeValues, CodeType codeType, String ticketId) throws DaoAccessException {

        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        codeType.setEdit_by(user.getUserId());
        codeType.setEdit_time(new Date());
        codeType.updateById();

        for (CodeValue codeValue : codeValues) {
            if (StringUtils.isBlank(codeValue.getId())) {
                codeValue.setCreate_by(user.getUserId());
                codeValue.setCreate_time(codeType.getEdit_time());
            }
            codeValue.setType_id(codeType.getId());
            codeValue.setTypeCode(codeType.getType_code());
            codeValue.setEdit_by(user.getUserId());
            codeValue.setEdit_time(codeType.getEdit_time());
        }
        codeValueService.insertOrUpdateBatch(codeValues);
    }

    @Override
    public boolean isHasData(String id, String code) throws DaoAccessException {
        EntityWrapper<CodeType> ew = new EntityWrapper<>();
        ew.eq("type_code", code);
        ew.ne("id",id);
        return this.selectCount(ew) > 0;
    }

    @Override
    public List<CodeValue> getCodeValuesTypeCode(String typeCode) throws DaoAccessException {
        return this.baseMapper.getCodeValuesTypeCode(typeCode);
    }

    @Override
    public CodeType getCodeTypeById(String id) throws DaoAccessException {
        return selectById(id);
    }

    @Override
    public boolean isRemoveEnable(String id) throws DaoAccessException {
        return true;
    }

    @Override
    public void removeCodeTypeAndValueByIds(List<String> ids) throws Exception {
        deleteBatchIds(ids);
        codeValueService.deleteCodeValuesByTypeIds(ids);
    }

    @Override
    public void updateCodeTypeStatus(List<String> ids, String status, String ticketId) throws DaoAccessException {
        List<CodeType> codeTypes = selectBatchIds(ids);
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        Date date = new Date();
        for (CodeType codeType:codeTypes){
            codeType.setStatus(status);
            codeType.setEdit_by(user.getUserId());
            codeType.setEdit_time(date);
        }
        updateBatchById(codeTypes);
    }

    @Override
    public List<CodeVo> list(Map<String, Object> paramMap, PagingParameter paging) throws DaoAccessException {
        paramMap.put("start", paging.getStart());
        paramMap.put("rows", paging.getLimit());
        return this.baseMapper.listMergeCodeValue(paramMap);
    }

    @Override
    public Integer count(Map<String, Object> params) throws DaoAccessException {
        return this.baseMapper.countMergeCodeValue(params);
    }
}
