package com.mlsc.yifeiwang.codedirectory.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.yifeiwang.codedirectory.mapper.CodeValueMapper;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxl
 * @since 2017-07-20
 */
@Service
public class CodeValueServiceImpl extends ServiceImpl<CodeValueMapper, CodeValue> implements ICodeValueService {

    @Override
    public void saveCodeValues(List<CodeValue> codeValues, String ticketId)  {
        if (codeValues != null && codeValues.size() > 0) {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            Date date = new Date();
            for (CodeValue codeValue:codeValues) {
                codeValue.setEdit_by(user.getUserId());
                codeValue.setEdit_time(date);
                codeValue.setCreate_by(user.getUserId());
                codeValue.setCreate_time(date);
            }
            insertOrUpdateBatch(codeValues);
        }
    }

    @Override
    public void updateCodeValues(List<CodeValue> codeValues, String ticketId)  {
        if (codeValues != null && codeValues.size() > 0) {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            Date date = new Date();
            for (CodeValue codeValue:codeValues) {
                codeValue.setEdit_by(user.getUserId());
                codeValue.setEdit_time(date);
            }
            this.updateBatchById(codeValues);
        }
    }

    @Override
    public CodeValue getCodeValueById(String id)  {
       return selectById(id);
    }

    @Override
    public List<CodeValue> getCodeValuesByTypeId(String typeId)  {
        EntityWrapper<CodeValue> ew = new EntityWrapper<>();
        ew.eq("type_id", typeId);
        ew.orderBy("code");
        return selectList(ew);
    }

    @Override
    public CodeValue getCodeValueByCode(String typeCode, String code)  {
        return this.baseMapper.getCodeValueByTypeCodeAndCode(typeCode, code);
    }

    @Override
    public boolean isRemoveEnable(String id) {
        return true;
    }

    @Override
    public void deleteCodeValuesByIds(List<String> ids)  {
        deleteBatchIds(ids);
    }

    @Override
    public void deleteCodeValuesByTypeId(String typeId)  {
        EntityWrapper<CodeValue> ew = new EntityWrapper<>();
        ew.eq("type_id", typeId);
        delete(ew);
    }
    @Override
    public void deleteCodeValuesByTypeIds(List<String> typeIds)  {
        EntityWrapper<CodeValue> ew = new EntityWrapper<>();
        ew.in("type_id", typeIds);
        delete(ew);
    }

    @Override
    public List<CodeValue> listCodeValue(String typeCode) {
        return this.baseMapper.listCodeValueByTypeCode(typeCode);
    }

    @Override
    public List<CodeValue> getEnterpriseTypesByEntId(String enterpriseId) {
        return this.baseMapper.getEnterpriseTypesByEntId(enterpriseId, Constant.IS_VALID);
    }

    @Override
    public List<String> getEnterpriseTypeCodesByEntId(String enterpriseId) {
        List<CodeValue> list = getEnterpriseTypesByEntId(enterpriseId);
        List<String> codeList = new ArrayList<>(list.size());
        for(CodeValue cv : list){
            codeList.add(cv.getCode());
        }
        return codeList;
    }

    @Override
    public String getEnterStatusCodeByEntId(String enterpriseId) {
        return this.baseMapper.getEnterStatusCodeByEntId(enterpriseId);
    }
}
