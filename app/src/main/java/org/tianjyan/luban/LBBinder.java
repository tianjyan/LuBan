package org.tianjyan.luban;

import android.os.RemoteException;

import org.greenrobot.eventbus.EventBus;
import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.event.RegisterInParaEvent;
import org.tianjyan.luban.event.RegisterOutParaEvent;
import org.tianjyan.luban.event.SetOutParaEvent;
import org.tianjyan.luban.manager.ClientManager;
import org.tianjyan.luban.manager.ConnectedClient;
import org.tianjyan.luban.manager.IClient;
import org.tianjyan.luban.manager.LogManager;
import org.tianjyan.luban.model.Const;


public class LBBinder extends IService.Stub {

    @Override
    public int canConnectLB(String pkgName, int versionId) throws RemoteException {
        if (versionId == Config.INTERVAL_VID) {
            return Config.RES_CODE_OK;
        }

        int count = ClientManager.getInstance().getAllClient().size();

        if (count == Config.MAX_CLIENT_SUPPORT) {
            return Config.RES_CODE_REFUSE;
        }

        return Config.RES_CODE_VERSION_INVALID;
    }

    @Override
    public void connectLB(String pkgName) throws RemoteException {
        if (ClientManager.getInstance().getAllClient().size() == Config.MAX_CLIENT_SUPPORT)
            return;

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
        LogManager.getInstance().log(tid, level, tag, msg);
    }

    @Override
    public void registerInPara(InPara inPara) throws RemoteException {
        IClient client = ClientManager.getInstance().getClient(getCallingUid());
        if (client != null && client.getInPara(inPara.getKey()) == null) {
            if (client.getInPara().size() >= Config.MAX_IN_PARA_SUPPORT) {
                return;
            }
            client.registerInPara(inPara);
            EventBus.getDefault().post(new RegisterInParaEvent(inPara));
        }
    }

    @Override
    public void registerOutPara(OutPara outPara) throws RemoteException {
        IClient client = ClientManager.getInstance().getClient(getCallingUid());
        if (client != null && client.getOutPara(outPara.getKey()) == null) {
            if (client.getOutPara().size() >= Config.MAX_OUT_PARA_SUPPORT) {
                return;
            }

            if (outPara.getKey() == Const.Floating_Area_Title ||
                    outPara.getKey() == Const.Normal_Area_Title) {
                return;
            }

            client.registerOutPara(outPara);
            EventBus.getDefault().post(new RegisterOutParaEvent(outPara));
        }
    }

    @Override
    public String getInPara(String key, String origVal) throws RemoteException {
        IClient client = ClientManager.getInstance().getClient(getCallingUid());
        if (client == null) {
            return origVal;
        }
        return client.getInPara(key, origVal);
    }

    @Override
    public void setOutPara(String key, String value) throws RemoteException {
        IClient client = ClientManager.getInstance().getClient(getCallingUid());
        if (client == null) return;
        client.setOutPara(key, value);
        OutPara outPara = client.getOutPara(key);
        if (outPara == null) return;
        EventBus.getDefault().post(new SetOutParaEvent(outPara, value));
    }
}
