package org.tianjyan.luban.client;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import org.tianjyan.luban.IService;

public class LBServiceConnection implements ServiceConnection {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        IService iService = IService.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
