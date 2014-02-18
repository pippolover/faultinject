/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.instrument.Instrumentation;
import java.util.Properties;

import com.alipay.faultinject.transformer.falutInjectTransformer;

/**
 * 
 * @author yimingwym 
 * @version $Id: FaultInjectAgentMain.java, v 0.1 2014-2-14 ÉÏÎç10:21:07 yimingwym Exp $
 */
public class FaultInjectAgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {
        System.out.println("in agentmain method");
        Properties pros = loadConfig();
        String className = pros.getProperty("class");
        String methodName = pros.getProperty("method");
        String injectFaultName = pros.getProperty("fault");
        Class[] cls = inst.getAllLoadedClasses();
        for (Class clazz : cls) {
            if (clazz.getName().startsWith(className)) {
                falutInjectTransformer ft = new falutInjectTransformer(clazz.getName(), methodName,
                    injectFaultName);
                inst.addTransformer(ft, true);
                inst.retransformClasses(clazz);
                break;
            }
        }
    }

    private static Properties loadConfig() throws Exception {
        Properties pros = new Properties();
        pros.load(new BufferedReader(new FileReader("/home/admin/logs/faultinject.pros")));
        return pros;
    }
}
