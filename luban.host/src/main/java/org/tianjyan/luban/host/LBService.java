package org.tianjyan.luban.host;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.tianjyan.luban.host.activity.MainActivity;
import org.tianjyan.luban.plugin.common.Utils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LBService extends Service {
    @Inject IBinder binder;
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_STICKY_COMPATIBILITY;
        Notification notification = Utils.genNotification(
                getApplicationContext(), R.drawable.ic_launcher_white,
                "LuBan", "Running", MainActivity.class, true, false, 0);
        startForeground(10, notification);
        return super.onStartCommand(intent, flags, startId);
    }
}
