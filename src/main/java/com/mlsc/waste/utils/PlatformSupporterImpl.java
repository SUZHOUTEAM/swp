package com.mlsc.waste.utils;

import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("platformSuppoter")
public class PlatformSupporterImpl implements PlatformSupporter{

    @Autowired
    private IRPCServiceClient client;
    @Override
    public IRPCServiceClient getIRPCServiceClient(){
        return client;
    }
    
    @Override
    public Iface getOrgComServiceManager(){
        return getIRPCServiceClient().getOrgComServiceManager();
    }
    
    
}
