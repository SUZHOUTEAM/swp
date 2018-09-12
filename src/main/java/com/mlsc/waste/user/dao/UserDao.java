package com.mlsc.waste.user.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 * 
 */
public interface UserDao extends EntityDao<User>{
    
    /**
     * 查询指定企业里面所有的的人员信息
     * @param enterpriseId
     * @return
     * @throws DaoAccessException 
     */
    List<RPCSysUser> getUserInfoByEntId(String enterpriseId) throws DaoAccessException;

	List<User> getUserListByUserId(List<String> userIdList) throws Exception;

	RPCSysUser getUserInfoByBusinessReleaseId(String releaseId) throws DaoAccessException;

	RPCSysUser getUserInfoByOrderId(String releaseId)  throws DaoAccessException;

	ResultData<User> pageUser(String whereSql, Map<String,Object> paramMap, PagingParameter paging)throws
			DaoAccessException;

	void updateUserExtendStatusByUserIdList(List<String> userIdList,String status,String roleCode);

	List<User> listUser(String whereSql,Map<String,Object> paramMap) throws DaoAccessException;
    
}
