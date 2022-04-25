package org.tianjyan.luban.host.plugin.logcat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import org.tianjyan.luban.host.R;
import org.tianjyan.luban.host.plugin.common.Utils;
import org.tianjyan.luban.host.plugin.logcat.activity.CrashDetailActivity;

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
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
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
        StringBuffer sb = new StringBuffer(line.substring(18));
        String packageName = null;
        while ((line = readLine(reader)) != null) {
            if (line.contains(("E/AndroidRuntime:"))) {
                if (line.contains("Process: ")) {
                    packageName = line.substring(27, line.indexOf(", PID:"));
                }
                sb.append("\n");
                sb.append(line.substring(18));
            } else {
                break;
            }
        }
        showExceptionNotification(packageName, sb.toString());
    }

    private void gatherNativeException(String line, BufferedReader reader) {
        StringBuffer sb = new StringBuffer(line.substring(12));
        String packageName = null;
        while ((line = readLine(reader)) != null) {
            if (line.contains(("DEBUG   :"))) {
                if (line.contains(">>> ") && line.contains(" <<<")) {
                    packageName = line.substring(line.indexOf(">>> ") + 4, line.indexOf(" <<<"));
                }
                sb.append("\n");
                sb.append(line.substring(12));
            } else {
                break;
            }
        }
        showExceptionNotification(packageName, sb.toString());
    }

    private String readLine(BufferedReader reader) {
        try {
            return  reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showExceptionNotification(String packName, String exceptionContent) {
        Intent intent = new Intent(this, CrashDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("packageName", packName);
        intent.putExtra("content", exceptionContent);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        Notification.Builder builder;

        NotificationManager manager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "2", NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(channel);
            builder = new Notification.Builder(this, "1");
        } else {
            builder = new Notification.Builder(this);
        }

        builder.setContentTitle(packName)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_error)
                .setStyle(new Notification.BigTextStyle().bigText(exceptionContent))
                .setFullScreenIntent(pendingIntent, true);
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) this.
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify("Luban", 1, notification);
        Utils.vibrate();
    }
}