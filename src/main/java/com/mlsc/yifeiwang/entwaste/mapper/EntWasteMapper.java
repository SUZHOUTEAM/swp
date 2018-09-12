package com.mlsc.yifeiwang.entwaste.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.entwaste.entity.EntWaste;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.entwaste.model.EntWasteParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
public interface EntWasteMapper extends BaseMapper<EntWaste> {
    List<EntWasteModel> listEntWaste(EntWasteParams entWasteParams);

    List<EntWasteModel> listEntWasteByEntId(EntWasteParams entWasteParams);

    int getEntWasteId(EntWasteParams entWasteParams);

    EntWasteModel getEntWasteDetailById(EntWasteParams entWasteParams);

    List<EntWasteModel> listEntWasteByLicenceId(@Param("licenceId") String licenceId,@Param("entId") String entId);

    int countEntWaste(EntWasteParams entWasteParams);

}