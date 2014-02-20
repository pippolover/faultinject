/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.asm.adapter;

import static org.objectweb.asm.Opcodes.ASM4;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * 
 * @author yimingwym 
 * @version $Id: addSleepMethodAdapter.java, v 0.1 2014-2-10 下午3:05:35 yimingwym Exp $
 */
public class addSleepMethodAdapter extends MethodVisitor {
    private static final Logger logger = LogManager.getLogger(addSleepMethodAdapter.class);

    public addSleepMethodAdapter(MethodVisitor methodVisitor) {
        super(ASM4, methodVisitor);

    }

    // 在源方法前去修改方法内容,这部分的修改将加载源方法的字节码之前 
    @Override
    public void visitCode() {
        logger.info("add sleep method");
        /*        // 记载隐含的this对象，这是每个JAVA方法都有的
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                // 加载userName字符到栈顶  
                mv.visitLdcInsn(userName);
                //把userName的值赋值给name字段
                mv.visitFieldInsn(Opcodes.PUTFIELD, Type.getInternalName(TestClass.class), "name",
                    Type.getDescriptor(String.class));
        */
        //below is system.out.println
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("in sleep loop");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
            "(Ljava/lang/String;)V");
        Label l0 = new Label();
        Label l1 = new Label();
        Label l2 = new Label();
        mv.visitTryCatchBlock(l0, l1, l2, "java/lang/InterruptedException");
        mv.visitLabel(l0);
        //below is thread.sleep()
        mv.visitLdcInsn(10000l);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread", "sleep",
            "(" + Type.getDescriptor(long.class) + ")V");
        mv.visitLabel(l1);
        Label l3 = new Label();
        mv.visitJumpInsn(Opcodes.GOTO, l3);
        mv.visitLabel(l2);
        mv.visitFrame(Opcodes.F_SAME1, 0, null, 1,
            new Object[] { "java/lang/InterruptedException" });
        mv.visitVarInsn(Opcodes.ASTORE, 1);
        Label l4 = new Label();
        mv.visitLabel(l4);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/InterruptedException",
            "printStackTrace", "()V");
        mv.visitLabel(l3);

        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

    }

    public static void main(String[] args) {
        System.out.println("aaa");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
