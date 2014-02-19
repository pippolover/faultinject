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
 * @version $Id: FaultInjectAgentMain.java, v 0.1 2014-2-14 上午10:21:07 yimingwym Exp $
 */
public class FaultInjectAgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {

        Properties pros = loadConfig();
        String className = pros.getProperty("class");
        String methodName = pros.getProperty("method");
        String injectFaultName = pros.getProperty("fault");
        //先把transformer加入instrumentation, 后续如果发现类已经被加载了，就做retransform
        //否则就直接转化
        falutInjectTransformer ft = new falutInjectTransformer(className, methodName,
            injectFaultName);
        inst.addTransformer(ft, true);

        Class[] cls = inst.getAllLoadedClasses();
        for (Class clazz : cls) {
            if (clazz.getName().startsWith(className)) {

                inst.retransformClasses(clazz);
                //break;
            }
        }
    }

    private static Properties loadConfig() throws Exception {
        Properties pros = new Properties();
        pros.load(new BufferedReader(new FileReader("/home/admin/logs/faultinject.pros")));
        return pros;
    }
}
