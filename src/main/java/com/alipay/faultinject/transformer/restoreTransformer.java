/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.objectweb.asm.ClassReader;

import com.alipay.faultinject.attach.utils.ClassUtils;

/**
 * restore original classes from file
 * @author yimingwym 
 * @version $Id: restoreTransformer.java, v 0.1 2014-2-20 ÏÂÎç1:21:28 yimingwym Exp $
 */
public class restoreTransformer implements ClassFileTransformer {

    private static final Logger logger = LogManager.getLogger(restoreTransformer.class.getName());

    private final String        needToTransClassName;

    public restoreTransformer(String needToTransClassName) {
        this.needToTransClassName = needToTransClassName;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
                                                                                      throws IllegalClassFormatException {
        if (className.equals(needToTransClassName.replace(".", "/"))) {

            logger.info(className + ":start to restore");
            String filePath = ClassUtils.getLocalPath("/home/admin/logs", needToTransClassName);
            InputStream is;
            try {
                is = new FileInputStream(new File(filePath));

                ClassReader cr = new ClassReader(is);

                return cr.b;

            } catch (Exception e) {
                logger.error("Encounter Exception", e);
            }

        }
        return null;
    }
}
