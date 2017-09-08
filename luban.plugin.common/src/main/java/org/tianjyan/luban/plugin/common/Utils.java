package org.tianjyan.luban.plugin.common;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import org.tianjyan.luban.infrastructure.abs.ILBApp;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Utils {
    private static final Format DateTimeFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
    private static final Format DateTimeFormat2 = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
    public static final String FILE_SEP = System.getProperty("file.separator");
    public static final String LINE_SEP = System.getProperty("line.separator");
    private static ILBApp context;

    public static void init(ILBApp app) {
        if (context == null) {
            context = app;
        }
    }

    public static Notification genNotification(Context c, int iconResId, String titleText,
                                        String contentText, Class<?> cls, boolean ongoing,
                                        boolean autoCancel, int notify_way) {

        Intent intent = null;
        if (cls != null) intent = new Intent(c, cls);

        final PendingIntent pi = PendingIntent.getActivity(c, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(c)
                .setContentTitle(titleText)
                .setContentText(contentText)
                .setContentIntent(pi)
                .setSmallIcon(iconResId)
                .setWhen(System.currentTimeMillis())
                .setOngoing(ongoing)
                .setAutoCancel(autoCancel)
                .setDefaults(notify_way);

        Notification notification = builder.build();

        return notification;
    }

    public static boolean isNullOrEmpty(final String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        if (a != null && b != null) {
            int length = a.length();
            if (length == b.length()) {
                if (a instanceof String && b instanceof String) {
                    return a.equals(b);
                } else {
                    for (int i = 0; i < length; i++) {
                        if (a.charAt(i) != b.charAt(i)) return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static String getDisplayTime() {
        return DateTimeFormat.format(new Date(System.currentTimeMillis()));
    }

    public static String getDisplayTime(long time) {
        return DateTimeFormat.format(new Date(time));
    }

    public static String getFileTime() {
        return DateTimeFormat2.format(new Date(System.currentTimeMillis()));
    }

    public static String getString(int resId) {
        return context.getContext().getString(resId);
    }

    public static File getCacheDir()  {
        File cacheDir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && context.getContext().getExternalCacheDir() != null)
            cacheDir = context.getContext().getExternalCacheDir();
        else {
            cacheDir = context.getContext().getCacheDir();
        }
        return cacheDir;
    }
}
