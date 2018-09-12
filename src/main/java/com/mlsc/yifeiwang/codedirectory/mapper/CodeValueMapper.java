package com.mlsc.yifeiwang.codedirectory.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yxl
 * @since 2017-07-20
 */
public interface CodeValueMapper extends BaseMapper<CodeValue> {
    CodeValue getCodeValueByTypeCodeAndCode(@Param("typeCode") String typeCode, @Param("code") String code);

    List<CodeValue> listCodeValueByTypeCode(@Param("typeCode") String typeCode);

    List<CodeValue> getEnterpriseTypesByEntId(@Param("enterpriseId") String enterpriseId, @Param("valid") String valid);

    String getEnterStatusCodeByEntId(@Param("enterpriseId") String enterpriseId);

    List<CodeValue> listNoticeTypeByTypeCode(@Param("typeCode") String typeCode, @Param("entType") String entType);
}