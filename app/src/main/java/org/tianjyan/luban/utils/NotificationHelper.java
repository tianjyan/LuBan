package org.tianjyan.luban.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationHelper {
    public static void notify(Context c, int notifyId, Notification n) {
        final NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(notifyId, n);
    }

    public static Notification genNotification(Context c,
                                               int iconResId,
                                               String titleText,
                                               String contentText,
                                               Class<?> cls,
                                               boolean ongoing,
                                               boolean autoCancel,
                                               int notify_way) {

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
}
