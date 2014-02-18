/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.transformer;

import java.io.File;
import java.io.FileOutputStream;
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

    private final String needToTransClassName;
    private final String methodName;
    private final String injectFault;

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
            String rawName = getRawName(needToTransClassName);
            ClassReader cr;
            try {
                cr = new ClassReader(className.replace("/", "."));
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor = new faultInjectAdapter(cw, methodName, injectFault);

                cr.accept(classVisitor, ClassReader.SKIP_DEBUG);

                byte[] classFile = cw.toByteArray();
                //                CheckClassAdapter.verify(cr, false, new PrintWriter(System.out));
                storeClassFile("/home/admin/logs/" + rawName + ".class", classFile);
                return classFile;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private String getRawName(String name) {
        String[] list = name.split("\\.");
        return list[list.length - 1];

    }

    private void storeClassFile(String path, byte[] classFile) {
        try {
            File file = new File(path);
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(classFile);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
