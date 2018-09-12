package com.mlsc.waste.user.service.impl;

import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.sms.model.MessageBean;
import com.mlsc.waste.user.dao.UserExtendedDao;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.UserRole;
import com.mlsc.waste.utils.UserStatus;
import com.mlsc.waste.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhugl
 */
@Service("UserExtendedService")
public class UserExtendedServiceImpl implements UserExtendedService {
    private final static Logger logger = LoggerFactory.getLogger(UserExtendedServiceImpl.class);

    @Autowired
    private UserExtendedDao userExtendedDao;
    

    @Override
    public void saveUserExtended(UserExtended record, String ticketId) throws DaoAccessException {
        record.setValid(Constant.IS_VALID);
        userExtendedDao.save(record, Util.uuid32());
    }
    
    @Override
    public void updateUserExtended(UserExtended record) {
        try {
            userExtendedDao.update(record);
        } catch (Exception e) {
            logger.error("用户扩展表记录更新时异常",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserExtended getUserExtendedById(String id) {
        UserExtended record = null;
        try {
            record = userExtendedDao.get(id);
        } catch (Exception e) {
            logger.error("用户扩展表记录更新时异常",e);
            throw new RuntimeException(e);
        }
        return record;
    }

    @Override
    public UserExtended getUserExtendedByUserId(
            String userId) {
        
        UserExtended record = new UserExtended();
        record.setSysUserId(userId);
        record.setValid(Constant.IS_VALID);
        
        try {
            List<UserExtended> recordlist = userExtendedDao.query(record);
            if (recordlist != null && recordlist.size() > 0) {
                return recordlist.get(0);
            }
        } catch (Exception e) {
            logger.error("用户扩展表记录更新时异常",e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateUserExtended(String userId) {
        try {
            userExtendedDao.updateWastecycleInit(userId);
        } catch (Exception e) {
            logger.error("用户扩展表记录更新时异常",e);
            throw new RuntimeException(e);
        }
    }

	@Override
	public void saveOrUpdateUserResgistration(String userId,String registrationCode ,String ticketId ) throws Exception{
		try{
			UserExtended userExtended = getUserExtendedByUserId(userId);
	        if(StringUtils.isNotEmpty(userExtended.getId())){
	            userExtended.setRegistrationCode(registrationCode);
	            updateUserExtended(userExtended);
	        }else{
	        	userExtended.setRegistrationCode(registrationCode);
	            saveUserExtended(userExtended, ticketId);
	        }
		}catch(Exception e){
			throw new WasteBusinessException(new MessageBean(MessageBean.STATUS_INFO, MessageBean.NOTICE_TYPE_NOTIFY, "设备绑定失败"));
		}
	}

	
	@Override
	public UserExtended getAdminUserByEnterpriseId(String enterpriseId) {
		UserExtended userExtended = null;
		try{
			userExtended = userExtendedDao.getAdminUserByEnterpriseId(enterpriseId);
		}catch(Exception e){
			logger.error("获取企业管理员时出错",e);
            throw new RuntimeException(e);
		}
		return userExtended;
	}
	
	
	@Override
	public List<UserApproveVo> listUserApproVo(String enterId,String statusCode) {
        String whereSql = " AND  extend.user_status =:statusCode AND relation.EntId "
                + "= :enterpriseId ";
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("enterpriseId", enterId);
        map.put("statusCode", statusCode);
        List<UserApproveVo> list = userExtendedDao.listUserApproVo(whereSql,map);
        if(list!=null&&list.size()>0){
            for(UserApproveVo userApproveVo : list){
                userApproveVo.setRoleName(UserRole.roleName(userApproveVo.getRole()));
            }
        }
		return list;
	}
	
	
	@Override
	public UserExtended getUserAdminByEnterId(String enterId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("enterpriseId", enterId);
        map.put("statusCode", UserStatus.PASS.getStatusCode());
        UserExtended userExtended = userExtendedDao.getAdminUserByEnterpriseId(enterId);
		return userExtended;
	}


    @Override
    public UserExtended getUserAdminByEnterId(String enterId,String userId) {
        UserExtended userExtended = userExtendedDao.getAdminUserByEnterpriseId(enterId,userId);
        return userExtended;
    }



    @Override
    public List<UserExtended>  listUserExtendedByEnterId(String enterId) {
        List<UserExtended>  userExtendeds = userExtendedDao.listUserExtendedByEnterId(enterId);
        return userExtendeds;
    }


}
