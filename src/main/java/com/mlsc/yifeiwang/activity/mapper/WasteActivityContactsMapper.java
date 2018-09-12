package com.mlsc.yifeiwang.activity.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.activity.entity.WasteActivityContacts;
import com.mlsc.yifeiwang.activity.model.ActivityContactsQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface WasteActivityContactsMapper extends BaseMapper<WasteActivityContacts> {

    List<WasteActivityContacts> obtainActivityContactList(ActivityContactsQueryParam queryParam);

    List<WasteActivityContacts> getUserPhoneByActivityId(@Param("activityId") String activityId);

    List<WasteActivityContacts> getConstactsUserFromEnterContacts(ActivityContactsQueryParam queryParam);

    int countNotifyEnt(ActivityContactsQueryParam queryParam);

    int countConstactsUserFromEnterContacts(ActivityContactsQueryParam queryParam);

}