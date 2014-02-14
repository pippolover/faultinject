/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import com.alipay.faultinject.transformer.falutInjectTransformer;

/**
 * 
 * @author yimingwym 
 * @version $Id: FaultInjectAgentMain.java, v 0.1 2014-2-14 ÉÏÎç10:21:07 yimingwym Exp $
 */
public class FaultInjectAgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst)
                                                                        throws UnmodifiableClassException {
        System.out.println("in agentmain method");
        for (Class clazz : inst.getAllLoadedClasses()) {
            if (clazz.getName() == "com.alipay.asmtest.beans.TestClass") {

                inst.addTransformer(new falutInjectTransformer(clazz.getName()), true);
                inst.retransformClasses(clazz);
            }
        }

    }
}
