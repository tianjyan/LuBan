package org.tianjyan.luban.utils;

import android.os.Environment;

import org.tianjyan.luban.LBApp;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static final Format DateTimeFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
    private static final Format DateTimeFormat2 = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
    public static final String FILE_SEP = System.getProperty("file.separator");

    public static boolean isNullOrEmpty(final String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return false;
    }

    public static File getCacheDir() {
        File cacheDir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && LBApp.getContext().getExternalCacheDir() != null)
            cacheDir = LBApp.getContext().getExternalCacheDir();
        else {
            cacheDir = LBApp.getContext().getCacheDir();
        }
        return cacheDir;
    }

    public static String getDisplayTime() {
        return DateTimeFormat.format(new Date(System.currentTimeMillis()));
    }

    public static String getDisplayTime(long time) {
        return DateTimeFormat.format(new Date(time));
    }

    public static String getFileTime() {
        return DateTimeFormat.format(new Date(System.currentTimeMillis()));
    }
}
