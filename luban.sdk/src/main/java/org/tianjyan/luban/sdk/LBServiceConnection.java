package org.tianjyan.luban.sdk;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import org.tianjyan.luban.aidl.IService;

class LBServiceConnection implements ServiceConnection {
    private SplashHandler splashHandler;

    public LBServiceConnection(SplashHandler handler) {
        this.splashHandler = handler;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        IService iService = IService.Stub.asInterface(service);
        LBInternal.getInstance().setService(iService);
        splashHandler.sendEmptyMessage(SplashHandler.MSG_SERVICE_CONNECTED);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        splashHandler.sendEmptyMessage(SplashHandler.MSG_SERVICE_DISCONNECTED);
    }
}
