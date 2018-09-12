package com.mlsc.waste.util;

import com.mlsc.rpc.thrift.client.IRPCServiceClient;

public class RPCServiceClientMock implements IRPCServiceClient {

    @Override
    public com.mlsc.rpc.thrift.api.service.ISysLoginService.Iface getLoginServiceManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public com.mlsc.rpc.thrift.api.service.ISysMenuService.Iface getMenuServiceManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface getOrgComServiceManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public com.mlsc.rpc.thrift.api.service.ISmsService.Iface getSmsServiceManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public com.mlsc.rpc.thrift.api.service.ISysUserService.Iface getUserServiceManager() {
        // TODO Auto-generated method stub
        return null;
    }

}
