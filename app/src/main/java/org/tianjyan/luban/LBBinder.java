package org.tianjyan.luban;

import android.os.RemoteException;

import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.manager.ClientManager;
import org.tianjyan.luban.manager.ConnectedClient;
import org.tianjyan.luban.manager.IClient;


public class LBBinder extends IService.Stub {

    @Override
    public int canConnectLB(String pkgName, int versionId) throws RemoteException {
        if (versionId == Config.INTERVAL_VID) {
            return Config.RES_CODE_OK;
        }
        return Config.RES_CODE_VERSION_INVALID;
    }

    @Override
    public void connectLB(String pkgName) throws RemoteException {
        IClient client = ClientManager.getInstance().getClient(getCallingUid());
        if (client == null) {
            client = new ConnectedClient(pkgName);
            ClientManager.getInstance().addClient(getCallingUid(), client);
        }
    }

    @Override
    public boolean disconnectLB(String pkgName) throws RemoteException {
        boolean result = true;
        try {
            IClient client = ClientManager.getInstance().getClient(getCallingUid());
            if (client != null) {
                client.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public void log(long tid, int level, String tag, String msg) throws RemoteException {

    }

    @Override
    public void registerInPara(InPara inPara) throws RemoteException {
        IClient client = ClientManager.getInstance().getClient(getCallingUid());
        client.registerInPara(inPara);
    }

    @Override
    public void registerOutPara(OutPara outPara) throws RemoteException {
        IClient client = ClientManager.getInstance().getClient(getCallingUid());
        client.registerOutPara(outPara);
    }

    @Override
    public String getInPara(String key, String origVal) throws RemoteException {
        IClient client = ClientManager.getInstance().getClient(getCallingUid());
        return client.getInPara(key, origVal);
    }

    @Override
    public void setOutPara(String key, String value) throws RemoteException {
        IClient client = ClientManager.getInstance().getClient(getCallingUid());
        client.setOutPara(key, value);
    }
}
