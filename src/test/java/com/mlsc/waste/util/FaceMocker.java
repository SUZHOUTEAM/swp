package com.mlsc.waste.util;

import com.mlsc.rpc.thrift.api.dto.RPCSysCanton;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.rpc.thrift.api.dto.ReObject;
import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceMocker implements Iface {

    @Override
    public ReObject checkEnterpriseCodeExist(String arg0, String arg1,
            String arg2) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RPCSysEnterpriseBase queryEnterprise(String arg0, String arg1)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RPCSysEnterpriseBase> queryEnterpriseByName(String arg0,
            String arg1) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RPCSysEnterpriseBase> queryEnterpriseByNameAndCantonCode(
            String arg0, String arg1, String arg2) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RPCSysEnterpriseBase queryEnterpriseInfo(String ticketId,String userId)
            throws Exception {
        RPCSysEnterpriseBase base = new RPCSysEnterpriseBase();
        base.setEntId("E001");
        Map<String,RPCSysEnterpriseBase> enterMap = new HashMap<String,RPCSysEnterpriseBase>();
        enterMap.put("U001", base);
        
        return enterMap.get(userId);
    }

    @Override
    public List<RPCSysEnterpriseBase> queryEnterpriseList(String arg0,
            List<String> arg1) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RPCSysEnterpriseBase> queryEnterpriseListWithPage(String arg0,
            String arg1, int arg2, int arg3) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RPCSysCanton querySysCanton(String arg0, String arg1, String arg2)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RPCSysCanton querySysCantonByCantonCode(String ticketId, String cantonCode)
            throws Exception {
        RPCSysCanton base = new RPCSysCanton();
        if ("34".equals(cantonCode)) {
            base.setParentCantonCode("root");
        } else if ("3408".equals(cantonCode)) {
            base.setParentCantonCode("34");
        } else if ("340823".equals(cantonCode)) {
            base.setParentCantonCode("3408");
        }
        return base;
    }

    @Override
    public RPCSysCanton querySysCantonByCantonName(String arg0, String arg1)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RPCSysCanton> querySysCantonListByCantonCode(String arg0,
            String arg1) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RPCSysOrgCom querySysOrgCom(String arg0, String arg1)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RPCSysOrgCom> querySysOrgComList(String arg0, List<String> arg1)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RPCSysOrgCom> querySysOrgComListByCantonID(String arg0,
            int arg1, String arg2) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RPCSysOrgCom> querySysOrgComListByParentCantonID(String arg0,
            int arg1, String arg2) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RPCSysOrgCom querySysOrgComTree(String arg0, int arg1, String arg2)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReObject removeEnterprise(String arg0, String arg1)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReObject removeUserEnterpriseRelation(String arg0, String arg1,
            String arg2) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RPCSysEnterpriseBase saveEnterpriseInfo(String arg0,
            RPCSysEnterpriseBase arg1) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReObject saveUserEnterpriseRelationMaster(String arg0, String arg1,
            String arg2) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReObject saveUserEnterpriseRelationSub(String arg0, String arg1,
            String arg2) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
