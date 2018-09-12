package com.mlsc.yifeiwang.codedirectory.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxl
 * @since 2017-07-20
 */
public interface ICodeValueService extends IService<CodeValue> {
    /**
     * 保存
     * @return
     * @
     */
    void saveCodeValues(List<CodeValue> codeValues, String ticketId) ;

    /**
     * 更新
     * @return
     * @
     */
    void updateCodeValues(List<CodeValue> codeValues, String ticketId) ;

    /**
     * 通过Id获取codeValue对象queryById
     * @return
     * @
     */
    CodeValue getCodeValueById(String id) ;

    /**
     * 通过TypeId获取codeValue对象
     * @return
     * @
     */
    List<CodeValue> getCodeValuesByTypeId(String typeId) ;

    /**
     * 通过type_code,code获取code_value的数据
     * @param typeCode,code
     * @return
     * @
     */
    CodeValue getCodeValueByCode(String typeCode, String code) ;

    /**
     * 检查是否可删除:可以删除：true，不可以删除：false
     * @param id
     * @return
     */
    boolean isRemoveEnable(String id);

    /**
     * 字典信息数据批量删除
     * @param ids
     * @return
     */
    void deleteCodeValuesByIds(List<String> ids);

    /**
     * 删除指定的type_id的数据
     * @param typeId
     * @return
     * @
     */
    void deleteCodeValuesByTypeId(String typeId) ;

    void deleteCodeValuesByTypeIds(List<String> typeIds) ;

    List<CodeValue> listCodeValue(String typeCode);

    List<CodeValue> getEnterpriseTypesByEntId(String enterpriseId);

    List<String> getEnterpriseTypeCodesByEntId(String enterpriseId);

    String getEnterStatusCodeByEntId(String enterpriseId);

}
