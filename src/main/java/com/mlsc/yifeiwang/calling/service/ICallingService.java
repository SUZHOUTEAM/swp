package com.mlsc.yifeiwang.calling.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.calling.entity.Calling;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.calling.model.CallingListPojo;
import com.mlsc.yifeiwang.calling.model.CallingVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxl
 * @since 2017-07-26
 */
public interface ICallingService extends IService<Calling> {
    /**
     * 行业表的保存
     *
     * @author zhugl date 2016-06-06
     */
    String saveCalling(Calling calling, String ticketId);

    /**
     * 行业表的更新
     *
     */
    void updateCalling(Calling calling, String ticketId);

    /**
     * 行业表的物理删除前检查，有子节点的行业不允许删除
     *
     */
    boolean isHasChildCalling(String callingId);

    /**
     * 行业表的物理删除
     *
     * @author zhugl date 2016-06-06
     */
    void removeCalling(List<String> ids);

    /**
     * 行业表的停用与启用
     *
     * @author zhugl date 2016-06-06
     */
    void updateCallingStatus(List<String> ids, String status, String ticketId);

    /**
     * 通过行业代码来查询数据是否存在
     *
     */
    boolean isHasCallingData(String id, String code);

    /**
     * 上级行业下拉框配置
     */
    Map<String, List<CallingVo>> queryCallingDropDown();

    /**
     * 通过ID查询行业（无效的行业数据也可以查出来）
     *
     */
    Calling getCallingById(String id);

    /**
     *
     * @param params
     *            查询条件参数
     * @return
     */
    Integer count(Map<String, Object> params);

    /**
     * 查询行业列表
     *
     * @param paramMap
     * @return
     */
    List<CallingListPojo> list(Map<String, Object> paramMap, PagingParameter paging);

    /**
     * 三位行业列表
     *
     * @return
     */
    List<CallingVo> listThirdLevelCalling();

    List<CallingListPojo> listCallingByPage(PagingParameter paging);
    Integer totalCalling();
}
