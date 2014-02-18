package com.alipay.faultinject.rmi;

import java.rmi.RemoteException;
import java.util.Properties;

public interface InvokeService extends java.rmi.Remote {
    /**
     * 重新reload jar，进行故障注入
     * 
     * @param pid 远程jvm的pid
     * @throws RemoteException
     */
    public void invokeFaultInject(String pid) throws RemoteException;

    /**
     * 生成故障注入的配置文件
     * 
     * @param pros
     * @throws RemoteException
     */
    public void genConfig(Properties pros) throws Exception;

    public Properties loadConfig() throws Exception;
}
