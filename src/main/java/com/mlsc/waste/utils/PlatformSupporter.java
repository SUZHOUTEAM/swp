package com.mlsc.waste.utils;

import com.mlsc.rpc.thrift.api.service.ISysOrgComService.Iface;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;

public interface PlatformSupporter {
    IRPCServiceClient getIRPCServiceClient();
    
    Iface getOrgComServiceManager();
}
