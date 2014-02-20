package com.alipay.faultinject.rmi.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import com.alipay.faultinject.attach.JVMAttachThread;
import com.alipay.faultinject.rmi.InvokeService;

public class InvokeServiceServer extends UnicastRemoteObject implements InvokeService {

    /**  */
    private static final long serialVersionUID = -2462519510010335421L;

    public InvokeServiceServer() throws RemoteException {

        int port = 10024;
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(port);
            registry.rebind("faultInjectService", this);
            System.out.println("Server started and listening on port " + port);

        } catch (RemoteException e) {
            System.out.println("remote exception" + e);
        }
    }

    @Override
    public void invokeFaultInject(String pid) throws RemoteException {
        new JVMAttachThread("/home/admin/faultinject-0.0.1.jar", pid).start();

    }

    @Override
    public void genConfig(Properties pros) throws Exception {
        BufferedWriter write = new BufferedWriter(new FileWriter(new File(
            "/home/admin/logs/faultinject.pros")));
        pros.store(write, null);
    }

    @Override
    public Properties loadConfig() throws Exception {
        Properties pros = new Properties();
        pros.load(new BufferedReader(new FileReader("/Users/yimingwym/Downloads/faultinject.pros")));
        return pros;

    }

    @Override
    public void restoreClass(String pid) throws RemoteException {
        new JVMAttachThread("/home/admin/faultinject-0.0.1.jar", pid).start();
    }

    public static void main(String[] args) throws Exception {
        InvokeServiceServer is = new InvokeServiceServer();
        Properties pros = new Properties();
        //        pros.put("class", "com.alipay.beans.TestClass");
        //        is.genConfig(pros);
        pros = is.loadConfig();
        System.out.println(pros);
        System.out.println(pros.get("class"));
        //is.startServer();
        //is.invokeFaultInject();
    }
}
