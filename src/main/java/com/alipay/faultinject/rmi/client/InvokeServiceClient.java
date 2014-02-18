/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import com.alipay.faultinject.attach.utils.RemoteInfoUtils;
import com.alipay.faultinject.rmi.InvokeService;

/**
 * 
 * @author yimingwym 
 * @version $Id: InvokeServiceClient.java, v 0.1 2014-2-17 ÏÂÎç3:52:31 yimingwym Exp $
 */
public class InvokeServiceClient {
    private final String host;
    private final int    port;

    public InvokeServiceClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public InvokeService call() throws Exception {
        Registry registry = LocateRegistry.getRegistry(host, port);
        if (registry != null) {
            String[] availRemoteServices = registry.list();
            for (int i = 0; i < availRemoteServices.length; i++) {
                System.out.println("Service " + i + ": " + availRemoteServices[i]);
            }
        }
        InvokeService rmiServer = (InvokeService) (registry.lookup("faultInjectService"));

        return rmiServer;
    }

    public void genConfig(InvokeService service, Properties pros) throws Exception {
        service.genConfig(pros);
    }

    public void injectFault(InvokeService service, String pid) throws Exception {
        service.invokeFaultInject(pid);
    }

    public static void main(String[] args) throws Exception {
        String pid = new RemoteInfoUtils("dlslock2.d59.alipay.net", "9981").getRemotePid();
        InvokeServiceClient client = new InvokeServiceClient("dlslock2.d59.alipay.net", 10024);
        InvokeService service = client.call();
        Properties pros = new Properties();
        pros.put("class", "com.alipay.lock.processor.policy.RepleaseQueuePolicy");
        pros.put("method", "getHashIndex");
        pros.put("fault", "runtimeException");
        client.genConfig(service, pros);
        client.injectFault(service, pid);

    }
}
