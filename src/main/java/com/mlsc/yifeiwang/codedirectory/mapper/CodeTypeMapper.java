package com.mlsc.yifeiwang.codedirectory.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.codedirectory.entity.CodeType;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.yifeiwang.codedirectory.model.CodeDirectory;
import com.mlsc.yifeiwang.codedirectory.model.CodeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yxl
 * @since 2017-07-20
 */
public interface CodeTypeMapper extends BaseMapper<CodeType> {

    List<CodeDirectory> listAllCodeType();

    List<CodeValue> getCodeValuesTypeCode(@Param("typeCode") String typeCode);

    List<CodeVo> listMergeCodeValue(Map<String, Object> paramMap);

    int countMergeCodeValue(Map<String, Object> paramMap);
}