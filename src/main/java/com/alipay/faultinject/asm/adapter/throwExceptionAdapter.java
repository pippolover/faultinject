/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.asm.adapter;

import static org.objectweb.asm.Opcodes.ASM4;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 
 * @author yimingwym 
 * @version $Id: throwExceptionAdapter.java, v 0.1 2014-2-11 ÏÂÎç3:23:30 yimingwym Exp $
 */
public class throwExceptionAdapter extends MethodVisitor {

    private final String exceptionName;

    public throwExceptionAdapter(MethodVisitor mv, String exceptionName) {
        super(ASM4, mv);
        this.exceptionName = exceptionName;
    }

    @Override
    public void visitCode() {
        System.out.println("add throw exception method");
        mv.visitTypeInsn(Opcodes.NEW, exceptionName);
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, exceptionName, "<init>", "()V");

        mv.visitInsn(Opcodes.ATHROW);
        super.visitEnd();
    }

}
