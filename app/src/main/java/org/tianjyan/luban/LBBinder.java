package org.tianjyan.luban;

import android.os.RemoteException;

import org.tianjyan.luban.aidl.IService;

public class LBBinder extends IService.Stub {
    @Override
    public String helloWorld() throws RemoteException {
        return "Hello World";
    }
}
