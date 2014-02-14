/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject;

import java.lang.instrument.Instrumentation;

import com.alipay.faultinject.transformer.falutInjectTransformer;

/**
 * 
 * @author yimingwym 
 * @version $Id: PreMain.java, v 0.1 2014-2-13 обнГ1:49:00 yimingwym Exp $
 */
public class PreMain {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new falutInjectTransformer(""));
        System.out.println("in premain method");
    }

}
