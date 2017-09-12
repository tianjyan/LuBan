package org.tianjyan.luban.plugin.logcat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import org.tianjyan.luban.plugin.common.Utils;
import org.tianjyan.luban.plugin.logcat.activity.CrashDetailActivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FCService extends Service {
    private boolean flag;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    enum ExceptionType {
        None,
        Runtime,
        Native
    }

    @Override
    public void onCreate() {
        super.onCreate();
        flag = true;
        new Thread(() -> {
            Process process = null;
            // Remember run `adb shell pm grant org.tianjyan.luban.host android.permission.READ_LOGS`
            // to grant permission
            try {
                process = Runtime.getRuntime().exec("logcat -v tag\n");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            String line;
            DataInputStream dis = new DataInputStream(process.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(dis));

            while (flag) {
                line = readLine(reader);

                if (Utils.isNullOrEmpty(line)) {
                    continue;
                }

                if (isLogcatProcessDown(line)) {
                    // TODO: 不知道这里要不要重启下Logcat的进程
                    break;
                }

                switch (isForceClose(line)) {
                    case Runtime:
                        gatherRuntimeException(line, reader);
                        break;
                    case Native:
                        gatherNativeException(line, reader);
                        break;
                    case None:
                    default:
                        break;
                }
            }

            try {
                reader.close();
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
    }

    private ExceptionType isForceClose(String line) {
        // Android Runtime Exception
        if (line.contains("FATAL EXCEPTION")) {
            return ExceptionType.Runtime;
        // Native Exception
        } else if (line.contains("*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***")) {
            return ExceptionType.Native;
        }
        return ExceptionType.None;
    }

    private boolean isLogcatProcessDown(String line) {
        if (line.contains("read: Unexpected EOF!")) {
            return true;
        }
        return false;
    }

    private void gatherRuntimeException(String line, BufferedReader reader) {
        StringBuffer sb = new StringBuffer(line);
        while ((line = readLine(reader)) != null) {
            if (line.contains(("E/AndroidRuntime:"))) {
                sb.append("\n");
                sb.append(line);
            } else {
                break;
            }
        }
        showExceptionNotification(sb.toString());
    }

    private void gatherNativeException(String line, BufferedReader reader) {
        StringBuffer sb = new StringBuffer(line);
        while ((line = readLine(reader)) != null) {
            if (line.contains(("DEBUG   :"))) {
                sb.append("\n");
                sb.append(line);
            } else {
                break;
            }
        }
        showExceptionNotification(sb.toString());
    }

    private String readLine(BufferedReader reader) {
        try {
            return  reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showExceptionNotification(String exceptionContent) {
        Intent intent = new Intent(this, CrashDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Exception")
                .setSmallIcon(org.tianjyan.luban.plugin.common.R.drawable.ic_error)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(exceptionContent))
                .setFullScreenIntent(pendingIntent, true);
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) this.
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify("Luban", 1, notification);
        Utils.vibrate();
    }
}
