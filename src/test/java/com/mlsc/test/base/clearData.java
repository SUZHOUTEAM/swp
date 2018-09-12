package com.mlsc.test.base;

import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.utils.PlatformSupporter;
import com.mlsc.waste.utils.Util;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING )
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:spring.xml" })
public class clearData {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private PlatformSupporter platformSupporter;
    
    
    @Test
    public void TestWasteCircleService() throws Exception{
        //删除
       
        
        List<RPCSysEnterpriseBase> enterIdList = namedParameterJdbcTemplate.query("select entId from sys_enterprise_base", new BeanPropertyRowMapper<RPCSysEnterpriseBase>(RPCSysEnterpriseBase.class));
        for(RPCSysEnterpriseBase enterBase:enterIdList){
            Map<String, Object> datamap = new HashMap<String, Object>();
            datamap.put("entId", enterBase.getEntId());
            List<RPCSysUser> userIdList = namedParameterJdbcTemplate.query("select userId from sys_enterprise_base base, sys_user_enterprise_relation relation where base.entId = relation.EntId and base.entId = :entId", datamap ,new BeanPropertyRowMapper<RPCSysUser>(RPCSysUser.class));
            for(RPCSysUser user: userIdList){
                platformSupporter.getIRPCServiceClient().getOrgComServiceManager().removeUserEnterpriseRelation(Util.uuid32(), user.getUserId(),  enterBase.getEntId());
                platformSupporter.getIRPCServiceClient().getUserServiceManager().removeUser(Util.uuid32(),  user.getUserId());
            }
            platformSupporter.getIRPCServiceClient().getOrgComServiceManager().removeEnterprise(Util.uuid32(),  enterBase.getEntId());
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        namedParameterJdbcTemplate.update("delete from  action_record", paramMap);
        namedParameterJdbcTemplate.update("delete from  calling where name = '111111'", paramMap);
        namedParameterJdbcTemplate.update("delete from  cooperation_message ", paramMap);
        namedParameterJdbcTemplate.update("delete from  cooperation_message_business ", paramMap);
        namedParameterJdbcTemplate.update("delete from  cooperation_notice ", paramMap);
        namedParameterJdbcTemplate.update("delete from  cooperation_relation ", paramMap);
        namedParameterJdbcTemplate.update("delete from  data_synchronized_record where length(id) >10", paramMap);
        namedParameterJdbcTemplate.update("delete from  disposition_capacity_release ", paramMap);
        namedParameterJdbcTemplate.update("delete from  disposition_capacity_response ", paramMap);
        namedParameterJdbcTemplate.update("delete from  disposition_capacitydetail_release ", paramMap);
        namedParameterJdbcTemplate.update("delete from  disposition_capacitydetail_response ", paramMap);
        namedParameterJdbcTemplate.update("delete from  disposition_capacityitem_release ", paramMap);
        namedParameterJdbcTemplate.update("delete from  disposition_plan_release ", paramMap);
        namedParameterJdbcTemplate.update("delete from  disposition_plan_remark ", paramMap);
        namedParameterJdbcTemplate.update("delete from  disposition_plan_response ", paramMap);
        namedParameterJdbcTemplate.update("delete from  disposition_planitem_release ", paramMap);
        namedParameterJdbcTemplate.update("delete from  disposition_planitem_response ", paramMap);
        namedParameterJdbcTemplate.update("delete from  enterprise_extended ", paramMap);
        namedParameterJdbcTemplate.update("delete from  enterprise_waste ", paramMap);
        namedParameterJdbcTemplate.update("delete from  identification_ability ", paramMap);
        namedParameterJdbcTemplate.update("delete from  lucene_index ", paramMap);
        namedParameterJdbcTemplate.update("delete from  operation_licence ", paramMap);
        namedParameterJdbcTemplate.update("delete from  operation_licence_detail ", paramMap);
        namedParameterJdbcTemplate.update("delete from  operation_licence_item ", paramMap);
        namedParameterJdbcTemplate.update("delete from  order_detail ", paramMap);
        namedParameterJdbcTemplate.update("delete from  orders ", paramMap);
        namedParameterJdbcTemplate.update("delete from  shopping_card_detail ", paramMap);
        namedParameterJdbcTemplate.update("delete from  shopping_cart ", paramMap);
        namedParameterJdbcTemplate.update("delete from  orders ", paramMap);
        namedParameterJdbcTemplate.update("delete from  upload_file ", paramMap);
        namedParameterJdbcTemplate.update("delete from  user_extended ", paramMap);
        namedParameterJdbcTemplate.update("delete from  user_enterprise_approve_record ", paramMap);
        namedParameterJdbcTemplate.update("delete from  user_extended ", paramMap);
        namedParameterJdbcTemplate.update("delete from  sys_user_enterprise_relation ", paramMap);
        namedParameterJdbcTemplate.update("delete from  waste_enterprise_type ", paramMap);
        namedParameterJdbcTemplate.update("delete from  sys_user_enterprise_relation ", paramMap);
    }

}
