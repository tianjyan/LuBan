package org.tianjyan.luban;

import android.app.Application;

public class LBApp extends Application {
    private static boolean isAppRunning = false;

    public static void setAppRunning(boolean isRunning) {
        isAppRunning = isRunning;
    }

    public static boolean isAppRunning() {
        return isAppRunning;
    }
}
