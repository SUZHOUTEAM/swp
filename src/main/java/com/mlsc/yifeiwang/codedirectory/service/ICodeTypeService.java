package com.mlsc.yifeiwang.codedirectory.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.codedirectory.entity.CodeType;
import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.codedirectory.model.CodeDirectory;
import com.mlsc.yifeiwang.codedirectory.model.CodeVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxl
 * @since 2017-07-20
 */
public interface ICodeTypeService extends IService<CodeType> {
    List<CodeDirectory> listAllCodeType();
    /**
     * 字典数据的新保存
     *
     * @author zhugl date 2016-06-07
     */
    void saveCodeTypeAndValue(List<CodeValue> codeValues, CodeType codeType, String ticketId) throws DaoAccessException;

    /**
     * 字典数据的更新
     *
     * @author zhugl date 2016-06-07
     */
    void updateCodeTypeAndValue(List<CodeValue> codeValues, CodeType codeType, String ticketId) throws DaoAccessException;

    /**
     * 检查字典类型代码是否重复
     * true:存在；flase:不存在
     * @author zhugl date 2016-06-07
     */
    boolean isHasData(String id,String code) throws DaoAccessException;

    /**
     * 根据typeCode查询对应的value列表
     * @param typeCode
     * @return codeVlaue 列表
     * @throws DaoAccessException
     */
    List<CodeValue> getCodeValuesTypeCode(String typeCode) throws DaoAccessException;

    /**
     * 通过ID查询行字典（无效的行业数据也可以查出来）
     *
     * @author zhugl date 2016-06-07
     * @throws DaoAccessException
     */
    CodeType getCodeTypeById(String id) throws DaoAccessException;

    /**
     * 检查是否可删除:可以删除：true，不可以删除：false
     * @param id
     * @return
     */
    boolean isRemoveEnable(String id) throws DaoAccessException;

    void removeCodeTypeAndValueByIds(List<String> ids) throws Exception;

    /**
     * 常用字典信息的停用与启用
     *
     * @author zhugl date 2016-06-06
     * @throws DaoAccessException
     */
    void updateCodeTypeStatus(List<String> ids, String status, String ticketId) throws DaoAccessException;

    /**
     * 查询列表
     * @param paging
     * @return
     * @throws DaoAccessException
     */
    List<CodeVo> list(Map<String, Object> paramMap, PagingParameter paging) throws DaoAccessException;
    /**
     *
     * @param params 查询条件参数
     * @return
     * @throws DaoAccessException
     */
    Integer count(Map<String, Object> params) throws DaoAccessException;
}
