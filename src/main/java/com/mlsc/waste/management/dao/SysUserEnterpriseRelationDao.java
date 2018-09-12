package com.mlsc.waste.management.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.waste.user.model.SysUserEnterpriseRelation;

import java.util.List;

/**
 * Created by zhanghj on 2017/7/4.
 */
public interface SysUserEnterpriseRelationDao extends EntityDao<SysUserEnterpriseRelation>{

   void deleteByUserIdList(List<String> userIdList);

   SysUserEnterpriseRelation getSysUserEnterpriseRelationByUserId(String userId);
}
