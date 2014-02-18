package com.alipay.faultinject.rmi.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.alipay.faultinject.rmi.InvokeService;

public class InvokeServiceServer extends UnicastRemoteObject implements InvokeService {

    /**  */
    private static final long serialVersionUID = -2462519510010335421L;

    public InvokeServiceServer() throws RemoteException {

        int port = 10024;
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(port);
            registry.rebind("samplermi", this);
            System.out.println("Server started and listening on port " + port);

        } catch (RemoteException e) {
            System.out.println("remote exception" + e);
        }
    }

    @Override
    public void invokeFaultInject() throws RemoteException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
                "/home/admin/logs/Result")));
            writer.write("method be invoked\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        InvokeServiceServer is = new InvokeServiceServer();
        //is.startServer();
        //is.invokeFaultInject();
    }

}
