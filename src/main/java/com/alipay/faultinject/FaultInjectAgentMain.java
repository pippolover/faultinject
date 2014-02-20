/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.instrument.Instrumentation;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alipay.faultinject.asm.constant.INJECTDEFAULT;
import com.alipay.faultinject.transformer.falutInjectTransformer;
import com.alipay.faultinject.transformer.restoreTransformer;

/**
 * 
 * @author yimingwym 
 * @version $Id: FaultInjectAgentMain.java, v 0.1 2014-2-14 上午10:21:07 yimingwym Exp $
 */
public class FaultInjectAgentMain {
    static final Logger logger = LogManager.getLogger(FaultInjectAgentMain.class.getName());

    public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {

        Properties pros = loadConfig();
        String className = pros.getProperty("class");
        String methodName = pros.getProperty("method");
        String injectFaultName = pros.getProperty("fault");
        String phase = pros.getProperty("phase");
        //先把transformer加入instrumentation, 后续如果发现类已经被加载了，就做retransform
        //否则就直接转化
        if (phase.equals(INJECTDEFAULT.INJECTPHASE)) {
            falutInjectTransformer ft = new falutInjectTransformer(className, methodName,
                injectFaultName);
            inst.addTransformer(ft, true);
        } else if (phase.equals(INJECTDEFAULT.RESTOREPHASE)) {
            restoreTransformer rf = new restoreTransformer(className);
            inst.addTransformer(rf, true);
        }

        Class[] cls = inst.getAllLoadedClasses();
        for (Class clazz : cls) {
            if (clazz.getName().startsWith(className)) {
                logger.info(className + ":already be loaded");
                inst.retransformClasses(clazz);
            }
        }

    }

    private static Properties loadConfig() throws Exception {
        Properties pros = new Properties();
        pros.load(new BufferedReader(new FileReader("/home/admin/logs/faultinject.pros")));
        return pros;
    }
}
