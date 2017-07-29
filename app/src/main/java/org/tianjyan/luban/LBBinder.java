package org.tianjyan.luban;

import android.os.RemoteException;
import android.util.Log;

import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;


public class LBBinder extends IService.Stub {

    @Override
    public int canConnectLB(String pkgName, int versionId) throws RemoteException {
        return Config.RES_CODE_OK;
    }

    @Override
    public void connectLB(String pkgName, int pid) throws RemoteException {

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
        Log.d("ytj", "registerOutPara");
    }

    @Override
    public void setInPara(String key, String value) throws RemoteException {

    }

    @Override
    public void setOutPara(String key, String value) throws RemoteException {
        Log.d("ytj", "setOutPara");
    }
}
