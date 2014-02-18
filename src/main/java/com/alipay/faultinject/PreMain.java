/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject;

import java.lang.instrument.Instrumentation;

import com.alipay.faultinject.rmi.server.InvokeServiceServer;

/**
 * 
 * @author yimingwym 
 * @version $Id: PreMain.java, v 0.1 2014-2-13 обнГ1:49:00 yimingwym Exp $
 */
public class PreMain {

    public static void premain(String agentArgs, Instrumentation inst) {

        System.out.println("do nothing");
        try {
            InvokeServiceServer is = new InvokeServiceServer();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
