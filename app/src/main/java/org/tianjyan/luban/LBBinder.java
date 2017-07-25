package org.tianjyan.luban;

import android.os.RemoteException;

public class LBBinder extends IService.Stub {
    @Override
    public String helloWorld() throws RemoteException {
        return "Hello World";
    }
}
