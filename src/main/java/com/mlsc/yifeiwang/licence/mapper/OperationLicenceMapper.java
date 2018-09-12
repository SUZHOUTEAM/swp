package com.mlsc.yifeiwang.licence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.licence.entity.OperationLicence;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangj
 * @since 2017-08-22
 */
public interface OperationLicenceMapper extends BaseMapper<OperationLicence> {
    Boolean getLicenceCreateStatus(@Param("entId") String entId);

}