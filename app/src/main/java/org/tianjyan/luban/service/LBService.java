package org.tianjyan.luban.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.tianjyan.luban.LBBinder;

public class LBService extends Service {
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*
     * 远程调用使用
     */
    private final IBinder binder = new LBBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_STICKY_COMPATIBILITY;
        return super.onStartCommand(intent, flags, startId);
    }
}
