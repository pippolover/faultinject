package com.alipay.faultinject.rmi;

import java.rmi.RemoteException;

public interface InvokeService extends java.rmi.Remote {
    public void invokeFaultInject() throws RemoteException;
}
