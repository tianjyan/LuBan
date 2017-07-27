package org.tianjyan.luban.client.Connect;

import android.os.Process;

import org.tianjyan.luban.aidl.Config;

public abstract class AbsDataCachedConnState implements IConnState {
    protected DataCacheController dataCacheController;

    public AbsDataCachedConnState(DataCacheController dataCacheController) {
        this.dataCacheController = dataCacheController;
    }

    private void log(int level, String tag, String msg) {
        long pid = Process.myPid();
        String[] content = new String[4];
        content[0] = String.valueOf(pid);
        content[1] = String.valueOf(level);
        content[2] = tag;
        content[3] = msg;

        dataCacheController.putLog(content);
    }

    @Override
    public void logI(String tag, String msg) {
        log(Config.LOG_INFO, tag, msg);
    }

    @Override
    public void logD(String tag, String msg) {
        log(Config.LOG_DEBUG, tag, msg);
    }

    @Override
    public void logW(String tag, String msg) {
        log(Config.LOG_WARNING, tag, msg);
    }

    @Override
    public void logE(String tag, String msg) {
        log(Config.LOG_ERROR, tag, msg);
    }

}
