/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.transformer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

import com.alipay.faultinject.asm.adapter.faultInjectAdapter;
import com.alipay.faultinject.attach.utils.ClassUtils;

/**
 * 
 * @author yimingwym 
 * @version $Id: falutInjectTransformer.java, v 0.1 2014-2-13 下午2:47:59 yimingwym Exp $
 */
public class falutInjectTransformer implements ClassFileTransformer {

    private static final Logger logger = LogManager.getLogger(falutInjectTransformer.class);

    private final String        needToTransClassName;
    private final String        methodName;
    private final String        injectFault;

    public falutInjectTransformer(String needToTransClassName, String methodName, String injectFault) {
        this.needToTransClassName = needToTransClassName;
        this.methodName = methodName;
        this.injectFault = injectFault;
    }

    /** 
     * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class, java.security.ProtectionDomain, byte[])
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
                                                                                      throws IllegalClassFormatException {

        if (className.equals(needToTransClassName.replace(".", "/"))) {

            logger.info(className + ":start to transform");

            ClassUtils.storeOriClassFile("/home/admin/logs/", className, classfileBuffer);
            ClassReader cr;
            try {
                cr = new ClassReader(className.replace("/", "."));
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor = new faultInjectAdapter(cw, methodName, injectFault);

                cr.accept(classVisitor, ClassReader.SKIP_DEBUG);

                byte[] classFile = cw.toByteArray();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                CheckClassAdapter.verify(new ClassReader(classFile), false, pw);
                logger.info("CheckClassAdapter: " + sw.toString());

                return classFile;
            } catch (IOException e) {
                logger.error("IO Exception", e);
            } catch (Throwable e) {
                logger.error("遇到未知问题", e);
            }

        }
        return null;
    }

}
