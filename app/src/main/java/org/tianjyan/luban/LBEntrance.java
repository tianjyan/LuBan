package org.tianjyan.luban;

import android.content.Context;
import android.content.Intent;

public class LBEntrance {

    public static void open(Context context) {
        Intent aidlIntent = new Intent(context, LBService.class);
        aidlIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(aidlIntent);
        LBApp.setAppRunning(true);
    }

    public static void close(Context context) {
        Intent aidlIntent = new Intent(context, LBService.class);
        context.stopService(aidlIntent);
        LBApp.setAppRunning(false);
        System.exit(0);
    }
}
