/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.attach;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * 
 * @author yimingwym 
 * @version $Id: RemoteAttachThread.java, v 0.1 2014-2-17 ����1:23:24 yimingwym Exp $
 */

public class RemoteAttachThread extends Thread {

    private final String host;
    private final String port;

    public RemoteAttachThread(String host, String port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {

        try {
            String url = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";

            JMXServiceURL serviceURL = new JMXServiceURL(url);
            JMXConnector connector = JMXConnectorFactory.connect(serviceURL);

            MBeanServerConnection mbsc = connector.getMBeanServerConnection();

            ObjectName systemInfo = new ObjectName(ManagementFactory.RUNTIME_MXBEAN_NAME);

            System.out.println(ManagementFactory.getRuntimeMXBean().getName());

            Set<ObjectName> mbeans = mbsc.queryNames(systemInfo, null);
            for (ObjectName name : mbeans) {
                RuntimeMXBean runtimeBean;
                runtimeBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, name.toString(),
                    RuntimeMXBean.class);

                System.out.println(runtimeBean.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RemoteAttachThread("dlslock2.d59.alipay.net", "9981").start();
    }
}
