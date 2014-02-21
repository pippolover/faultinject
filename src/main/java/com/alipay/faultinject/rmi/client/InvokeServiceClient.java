/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alipay.faultinject.asm.constant.INJECTDEFAULT;
import com.alipay.faultinject.attach.utils.RemoteInfoUtils;
import com.alipay.faultinject.rmi.InvokeService;

/**
 * 
 * @author yimingwym 
 * @version $Id: InvokeServiceClient.java, v 0.1 2014-2-17 ÏÂÎç3:52:31 yimingwym Exp $
 */
public class InvokeServiceClient {
    private static final Logger logger = LogManager.getLogger(InvokeServiceClient.class.getName());
    private final String        host;
    private final int           port;

    public InvokeServiceClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public InvokeService call() throws Exception {
        Registry registry = LocateRegistry.getRegistry(host, port);
        if (registry != null) {
            String[] availRemoteServices = registry.list();
            for (int i = 0; i < availRemoteServices.length; i++) {
                logger.info("Service " + i + ": " + availRemoteServices[i]);
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

    public void restoreFault(InvokeService service, String pid) throws Exception {
        service.restoreClass(pid);
    }

    public static void main(String[] args) throws Exception {
        String targetHost = "cloudenginetest.shd89.alipay.net";
        String pid = new RemoteInfoUtils(targetHost, "9981").getRemotePid();
        InvokeServiceClient client = new InvokeServiceClient(targetHost, 10024);
        InvokeService service = client.call();
        Properties pros = new Properties();
        pros.put("class", "com.alipay.cloudenginetest.performance.service.impl.WsServiceImpl");
        pros.put("method", "WsServicePerfTest");
        pros.put("fault", "runtimeException");
        //pros.put("phase", INJECTDEFAULT.INJECTPHASE);
        pros.put("phase", INJECTDEFAULT.RESTOREPHASE);
        client.genConfig(service, pros);

        //client.injectFault(service, pid);

        client.restoreFault(service, pid);

    }
}
