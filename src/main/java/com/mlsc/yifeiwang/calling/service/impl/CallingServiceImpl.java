package com.mlsc.yifeiwang.calling.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.calling.entity.Calling;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.calling.mapper.CallingMapper;
import com.mlsc.yifeiwang.calling.model.CallingListPojo;
import com.mlsc.yifeiwang.calling.model.CallingVo;
import com.mlsc.yifeiwang.calling.service.ICallingService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxl
 * @since 2017-07-26
 */
@Service
public class CallingServiceImpl extends ServiceImpl<CallingMapper, Calling> implements ICallingService {

    @Override
    public String saveCalling(Calling calling, String ticketId) {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        calling.setStatus(Constant.ENABLED);
        calling.setCreate_by(user.getUserId());
        calling.setCreate_time(new Date());
        calling.setEdit_by(user.getUserId());
        calling.setEdit_time(calling.getCreate_time());

        return insert(calling)? calling.getId(): null;
    }

    @Override
    public void updateCalling(Calling calling, String ticketId) {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        Calling callingEntity = getCallingById(calling.getId());
        callingEntity.setCode(calling.getCode());
        callingEntity.setName(calling.getName());
        callingEntity.setDescription(calling.getDescription());
        callingEntity.setEdit_by(user.getUserId());
        callingEntity.setEdit_time(new Date());
        updateById(callingEntity);
    }

    @Override
    public boolean isHasChildCalling(String callingId) {
        EntityWrapper<Calling> ew = new EntityWrapper<>();
        ew.eq("parent_id",callingId);
        return selectCount(ew) > 0;
    }

    @Override
    public void removeCalling(List<String> ids) {
        deleteBatchIds(ids);
    }

    @Override
    public void updateCallingStatus(List<String> ids, String status, String ticketId) {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        List<Calling> callingList = selectBatchIds(ids);
        Date now = new Date();
        for(Calling calling : callingList){
            calling.setStatus(status);
            calling.setEdit_by(user.getUserId());
            calling.setEdit_time(now);
        }
        updateBatchById(callingList);
    }

    @Override
    public boolean isHasCallingData(String id, String code) {
        EntityWrapper<Calling> ew = new EntityWrapper<>();
        ew.eq("code",code);
        ew.ne("id",id);
        return selectCount(ew) > 0;
    }

    @Override
    public Map<String, List<CallingVo>> queryCallingDropDown() {
        Map<String, List<CallingVo>> resultMap = new HashMap<String, List<CallingVo>>();
        List<CallingVo> rootCallingList = new ArrayList<>();
        List<CallingVo> subAllList = new ArrayList<>();
        resultMap.put("rootCallingList", rootCallingList);
        resultMap.put("subCallingList", subAllList);

        EntityWrapper<Calling> ew = new EntityWrapper<>();
        ew.le("length(code)",3);
        ew.orderBy("parent_id, code");
        List<Calling> callingList = selectList(ew);
        for(Calling calling : callingList){
            CallingVo rc = new CallingVo();
            rc.setId(calling.getId());
            rc.setName(calling.getCode()+ "  "+calling.getName());
            if("-1".equals(calling.getParent_id())){
                rootCallingList.add(rc);
            }else{
                subAllList.add(rc);
            }
        }
        return resultMap;
    }

    @Override
    public Calling getCallingById(String id) {
        return selectById(id);
    }

    @Override
    public Integer count(Map<String, Object> params) {
        return this.baseMapper.countCalling(params);
    }

    @Override
    public List<CallingListPojo> list(Map<String, Object> paramMap, PagingParameter paging) {
        paramMap.put("start", paging.getStart());
        paramMap.put("rows", paging.getLimit());
        return this.baseMapper.listCalling(paramMap);
    }

    @Override
    public List<CallingVo> listThirdLevelCalling() {
        return this.baseMapper.listThirdLevelCalling();
    }

    @Override
    public List<CallingListPojo> listCallingByPage(PagingParameter paging) {
        return this.baseMapper.listCallingByPage(paging);
    }

    @Override
    public Integer totalCalling() {
        return this.baseMapper.totalCalling();
    }

}
