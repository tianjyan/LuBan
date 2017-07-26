package org.tianjyan.luban.client.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.client.LBConnectListener;

public class LBServiceConnection implements ServiceConnection {
    private SplashHandler splashHandler;

    public LBServiceConnection(SplashHandler handler) {
        this.splashHandler = handler;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        IService iService = IService.Stub.asInterface(service);
        LBInternal.getInstance().setLbService(iService);

        splashHandler.sendEmptyMessage(SplashHandler.MSG_LB_SERVICE_CONNECTED);

        LBConnectListener listener = LBInternal.getInstance().getLbConnectListener();
        if (listener != null) listener.onBind();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        splashHandler.sendEmptyMessage(SplashHandler.MSG_LB_SERVICE_DISCONNECTED);
    }
}
