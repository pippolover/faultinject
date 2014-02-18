package com.alipay.faultinject.rmi;

import java.rmi.RemoteException;
import java.util.Properties;

public interface InvokeService extends java.rmi.Remote {
    /**
     * ����reload jar�����й���ע��
     * 
     * @param pid Զ��jvm��pid
     * @throws RemoteException
     */
    public void invokeFaultInject(String pid) throws RemoteException;

    /**
     * ���ɹ���ע��������ļ�
     * 
     * @param pros
     * @throws RemoteException
     */
    public void genConfig(Properties pros) throws Exception;

    public Properties loadConfig() throws Exception;
}
