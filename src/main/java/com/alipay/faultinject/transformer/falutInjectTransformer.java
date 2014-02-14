/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.transformer;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import com.alipay.faultinject.asm.adapter.faultInjectAdapter;

/**
 * 
 * @author yimingwym 
 * @version $Id: falutInjectTransformer.java, v 0.1 2014-2-13 ÏÂÎç2:47:59 yimingwym Exp $
 */
public class falutInjectTransformer implements ClassFileTransformer {

    private final String needToTrans;

    public falutInjectTransformer(String needToTrans) {
        this.needToTrans = needToTrans;
    }

    /** 
     * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class, java.security.ProtectionDomain, byte[])
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
                                                                                      throws IllegalClassFormatException {
        System.out.println(needToTrans + ":" + className);
        if (className.equals(needToTrans.replace(".", "/"))) {
            System.err.println("start to transform class");

            ClassReader cr;
            try {
                cr = new ClassReader(className.replace("/", "."));
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor = new faultInjectAdapter(cw);

                cr.accept(classVisitor, ClassReader.SKIP_DEBUG);
                byte[] classFile = cw.toByteArray();
                return classFile;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}
