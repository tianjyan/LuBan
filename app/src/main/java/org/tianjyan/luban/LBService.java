package org.tianjyan.luban;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.tianjyan.luban.activity.MainActivity;
import org.tianjyan.luban.utils.NotificationHelper;

public class LBService extends Service {
    private final IBinder binder = new LBBinder();

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
        Notification notification = NotificationHelper.genNotification(
                getApplicationContext(), R.mipmap.ic_launcher,
                "LuBan", "Running", MainActivity.class, true, false, 0);
        startForeground(10, notification);
        return super.onStartCommand(intent, flags, startId);
    }
}
