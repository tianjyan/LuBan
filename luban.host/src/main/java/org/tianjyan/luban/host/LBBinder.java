package org.tianjyan.luban.host;

import android.os.RemoteException;

import org.tianjyan.luban.aidl.AidlEntry;
import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.host.infrastructure.abs.IClient;
import org.tianjyan.luban.host.infrastructure.abs.IClientManager;
import org.tianjyan.luban.host.infrastructure.abs.plugin.IFloatingPlugin;
import org.tianjyan.luban.host.infrastructure.abs.plugin.IInParaPlugin;
import org.tianjyan.luban.host.infrastructure.abs.plugin.ILogPlugin;
import org.tianjyan.luban.host.infrastructure.abs.plugin.IOutParaPlugin;
import org.tianjyan.luban.host.plugin.common.Utils;

public class LBBinder extends IService.Stub  {
    private final IClientManager clientManager;
    private final IOutParaPlugin outParaPlugin;
    private final IInParaPlugin inParaPlugin;
    private final ILogPlugin logPlugin;
    private final IFloatingPlugin floatingPlugin;

    public LBBinder(IClientManager clientManager,
                    IOutParaPlugin outParaPlugin,
                    IInParaPlugin inParaPlugin,
                    ILogPlugin logPlugin,
                    IFloatingPlugin floatingPlugin) {
        this.clientManager = clientManager;
        this.outParaPlugin = outParaPlugin;
        this.inParaPlugin = inParaPlugin;
        this.logPlugin = logPlugin;
        this.floatingPlugin = floatingPlugin;
    }

    @Override
    public int canConnectLB(String pkgName, int versionId) throws RemoteException {
        if (versionId == Config.INTERVAL_VID) {
            return Config.RES_CODE_OK;
        }

        int count = clientManager.getAllClient().size();

        if (count == Config.MAX_CLIENT_SUPPORT) {
            return Config.RES_CODE_REFUSE;
        }

        return Config.RES_CODE_VERSION_INVALID;
    }

    @Override
    public void connectLB(String pkgName) throws RemoteException {
        if (clientManager.getAllClient().size() == Config.MAX_CLIENT_SUPPORT)
            return;

        IClient client = clientManager.getClient(getCallingUid());
        if (client == null) {
            clientManager.addClient(getCallingUid(), pkgName);
        }
    }

    @Override
    public boolean disconnectLB(String pkgName) throws RemoteException {
        boolean result = true;
        try {
            IClient client = clientManager.getClient(getCallingUid());
            if (client != null) {
                client.clear();
                clientManager.removeClient(getCallingPid());
                outParaPlugin.clientDisconnect(pkgName);
                inParaPlugin.clientDisconnect(pkgName);
                floatingPlugin.clientDisconnect(pkgName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public void log(long tid, int level, String tag, String msg) throws RemoteException {
        logPlugin.log(tid, level, tag, msg);
    }

    @Override
    public void registerInPara(InPara inPara) throws RemoteException {
        IClient client = clientManager.getClient(getCallingUid());
        if (client != null && client.getInPara(inPara.getKey()) == null) {
            if (client.getInPara().size() >= Config.MAX_IN_PARA_SUPPORT) {
                return;
            }
            client.registerInPara(inPara);
            inParaPlugin.registerInPara(inPara);
        }
    }

    @Override
    public void registerOutPara(OutPara outPara) throws RemoteException {
        IClient client = clientManager.getClient(getCallingUid());
        if (client != null && client.getOutPara(outPara.getKey()) == null) {
            if (client.getOutPara().size() >= Config.MAX_OUT_PARA_SUPPORT) {
                return;
            }

            if (outPara.getKey() == Utils.getString(R.string.para_floating_title) ||
                    outPara.getKey() == Utils.getString(R.string.para_normal_title)) {
                return;
            }

            client.registerOutPara(outPara);
            outParaPlugin.registerOutPara(outPara);
        }
    }

    @Override
    public String getInPara(String key, String origValue) throws RemoteException {
        IClient client = clientManager.getClient(getCallingUid());
        if (client == null) {
            return origValue;
        }
        return client.getInPara(key, origValue);
    }

    @Override
    public void setOutPara(String key, String value) throws RemoteException {
        if (!outParaPlugin.isGathering()) return;
        IClient client = clientManager.getClient(getCallingUid());
        if (client == null) return;
        client.setOutPara(key, value);
        OutPara outPara = client.getOutPara(key);
        if (outPara == null) return;
        outParaPlugin.setOutPara(outPara, value);
        if (outPara.getDisplayProperty() == AidlEntry.DISPLAY_FLOATING) {
            floatingPlugin.outParaValueUpdate(outPara, value);
        }
    }
}
