package com.mlsc.yifeiwang.operaction.mapper;

import com.mlsc.yifeiwang.operaction.entity.WebsiteOpertionDisposalEnterprise;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
public interface WebsiteOpertionDisposalEnterpriseMapper extends BaseMapper<WebsiteOpertionDisposalEnterprise> {
    List<WebsiteOpertionDisposalEnterprise> listDisposalEnterprise(@Param("entName") String entName);

}