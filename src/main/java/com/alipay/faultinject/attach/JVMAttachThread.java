/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.attach;

import com.sun.tools.attach.VirtualMachine;

/**
 * 
 * @author yimingwym 
 * @version $Id: AttachThread.java, v 0.1 2014-2-14 ÉÏÎç10:43:51 yimingwym Exp $
 */
@SuppressWarnings("restriction")
public class JVMAttachThread extends Thread {

    private final String agentJar;
    private final String pid;

    public JVMAttachThread(String jar, String pid) {
        this.agentJar = jar;
        this.pid = pid;
    }

    @Override
    public void run() {
        VirtualMachine vm = null;
        try {
            vm = VirtualMachine.attach(pid);
            vm.loadAgent(agentJar);
            vm.detach();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JVMAttachThread(
            "/Users/yimingwym/Documents/workspace/faultinject/target/faultinject-0.0.1.jar",
            "TestClass").start();
    }
}
