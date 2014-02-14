/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.faultinject.attach;

import java.util.List;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * 
 * @author yimingwym 
 * @version $Id: AttachThread.java, v 0.1 2014-2-14 ÉÏÎç10:43:51 yimingwym Exp $
 */
@SuppressWarnings("restriction")
public class AttachThread extends Thread {

    private final List<VirtualMachineDescriptor> vmBefore;
    private final String                         agentJar;

    public AttachThread(String jar, List<VirtualMachineDescriptor> vms) {
        this.vmBefore = vms;
        this.agentJar = jar;
    }

    @Override
    public void run() {
        VirtualMachine vm = null;
        List<VirtualMachineDescriptor> vmAfter = null;

        try {
            /*            while (true) {
                            vmAfter = VirtualMachine.list();
                            for (VirtualMachineDescriptor vmd : vmAfter) {
                                if (!vmBefore.contains(vmd)) {
                                    System.out.println("vmd name=" + vmd.displayName());
                                    vm = VirtualMachine.attach(vmd);
                                    System.out.println("get target vm then break");
                                    break;
                                }
                            }

                            if (vm != null) {
                                break;
                            }
                        }*/
            vmAfter = VirtualMachine.list();
            for (VirtualMachineDescriptor vmd : vmAfter) {
                if (vmd.displayName().contains("TestClass")) {
                    vm = VirtualMachine.attach(vmd);
                    vm.loadAgent(agentJar);
                    vm.detach();
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AttachThread(
            "/Users/yimingwym/Documents/workspace/faultinject/target/faultinject-0.0.1.jar",
            VirtualMachine.list()).start();
    }
}
