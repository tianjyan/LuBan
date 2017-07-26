package org.tianjyan.luban.client.communicate;

import android.os.RemoteException;

import org.tianjyan.luban.aidl.IService;

public class ComImpl implements ICom {

    IService service;

    public ComImpl(IService service) {
        this.service = service;
    }

    @Override
    public void initConnect(String pkgName, int pid) {
        try {
            service.initConnect(pkgName, pid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int checkIsCanConnect(String cur_pkgName, int versionId) {
        try {
            return service.checkIsCanConnect(cur_pkgName, versionId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean disconnect(String cur_pkgName) {
        try {
            return service.disconnect(cur_pkgName);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }

    }
}