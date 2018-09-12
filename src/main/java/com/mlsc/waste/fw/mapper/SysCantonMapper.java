package com.mlsc.waste.fw.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.waste.fw.model.SysCanton;
import com.mlsc.waste.fw.model.SysCantonQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
  * 行政区定义表 Mapper 接口
 * </p>
 *
 * @author zhj
 * @since 2017-08-01
 */
public interface SysCantonMapper extends BaseMapper<SysCanton> {

    List<SysCanton> listSysCanton(SysCantonQueryParam param);

    SysCanton  getCantonCodeByCantonName(@Param("cantonName") String cantonName);

}