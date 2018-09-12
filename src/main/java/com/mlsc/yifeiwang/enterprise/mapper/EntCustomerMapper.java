package com.mlsc.yifeiwang.enterprise.mapper;

import com.mlsc.yifeiwang.enterprise.entity.EntCustomer;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-10-20
 */
public interface EntCustomerMapper extends BaseMapper<EntCustomer> {

    List<EntCustomer> listEntCustomersByEntId(@Param("entId") String enterpriseId);
}