/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.asm.adapter;

import static org.objectweb.asm.Opcodes.ASM4;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import com.alipay.faultinject.asm.constant.ASMEXCEPTIONS;

/**
 * 
 * @author yimingwym 
 * @version $Id: AddSleepAdapter.java, v 0.1 2014-2-10 ÏÂÎç1:47:39 yimingwym Exp $
 */
public class faultInjectAdapter extends ClassVisitor {

    private final String methodName;
    private final String injectFault;

    public faultInjectAdapter(ClassVisitor classvisitor, String methodName, String injectFault) {
        super(ASM4, classvisitor);
        this.methodName = methodName;
        this.injectFault = injectFault;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

        if (name.equalsIgnoreCase(methodName)) {
            return genMethodVisitor(injectFault, methodVisitor);
        } else {
            return methodVisitor;
        }

    }

    private MethodVisitor genMethodVisitor(String injectFault, MethodVisitor methodVisitor) {
        if (injectFault == null) {
            return methodVisitor;
        } else if (injectFault.startsWith("sleep")) {
            return new addSleepMethodAdapter(methodVisitor);
        } else if (injectFault.startsWith("runtimeException")) {
            return new throwExceptionAdapter(methodVisitor, ASMEXCEPTIONS.NULLPOINTEXCEPTION);
        }
        return methodVisitor;
    }

    public static void main(String[] args) {
        System.out.println("aaa");
    }

}
