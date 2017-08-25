package org.tianjyan.luban.host;

import android.os.RemoteException;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

public class LBBinder extends IService.Stub  {
    @Override
    public int canConnectLB(String pkgName, int versionId) throws RemoteException {
        return 0;
    }

    @Override
    public void connectLB(String pkgName) throws RemoteException {

    }

    @Override
    public boolean disconnectLB(String pkgName) throws RemoteException {
        return false;
    }

    @Override
    public void log(long tid, int level, String tag, String msg) throws RemoteException {

    }

    @Override
    public void registerInPara(InPara inPara) throws RemoteException {

    }

    @Override
    public void registerOutPara(OutPara outPara) throws RemoteException {

    }

    @Override
    public String getInPara(String key, String origValue) throws RemoteException {
        return null;
    }

    @Override
    public void setOutPara(String key, String value) throws RemoteException {

    }
}
