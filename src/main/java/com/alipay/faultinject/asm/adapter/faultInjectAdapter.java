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
 * @version $Id: AddSleepAdapter.java, v 0.1 2014-2-10 下午1:47:39 yimingwym Exp $
 */
public class faultInjectAdapter extends ClassVisitor {

    public faultInjectAdapter(ClassVisitor classvisitor) {
        super(ASM4, classvisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

        if (name.equalsIgnoreCase("sayName")) {
            MethodVisitor addSleepVisitor = new addSleepMethodAdapter(methodVisitor);

            return addSleepVisitor;
        } else if (name.equalsIgnoreCase("sayHello")) {
            /**
             * 不需要修改method级别的throws Exception
             * 因为模拟异常时，如果会throw 某种exception，方法中肯定已经包含了此种exception
             * runtime Exception 可以不申明就throw
             */

            MethodVisitor throwExceptionVisitor = new throwExceptionAdapter(methodVisitor,
                ASMEXCEPTIONS.NULLPOINTEXCEPTION);

            return throwExceptionVisitor;
        } else {
            return methodVisitor;
        }

    }

    public static void main(String[] args) {
        System.out.println("aaa");
    }

}
