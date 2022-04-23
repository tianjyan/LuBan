package org.tianjyan.luban.infrastructure.crash;

import android.os.Build;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class CrashHandler {
    private static final Format DateTimeFormat2 = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static String  dir;
    private static final String CRASH_HEAD;
    private static final Thread.UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER;
    private static final Thread.UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER;

    static {
        CRASH_HEAD = "\n************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER + // 设备厂商
                "\nDevice Model       : " + Build.MODEL + // 设备型号
                "\nAndroid Version    : " + Build.VERSION.RELEASE + // 系统版本
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT + // SDK版本
                "\nApp CommitId       : " + BuildConfig.GIT_COMMIT_SHA +
                "\n************* Crash Log Head ****************\n\n";

        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

        UNCAUGHT_EXCEPTION_HANDLER = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                if (e == null) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                    return;
                }
                String fileName = getFileTime() + ".txt";
                final String fullPath = dir + fileName;
                if (!createOrExistsFile(fullPath)) return;
                new Thread(() -> {
                    PrintWriter pw = null;
                    try {
                        pw = new PrintWriter(new FileWriter(fullPath, false));
                        pw.write(CRASH_HEAD);
                        e.printStackTrace(pw);
                        Throwable cause = e.getCause();
                        while (cause != null) {
                            cause.printStackTrace(pw);
                            cause = cause.getCause();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        if (pw != null) {
                            pw.close();
                        }
                    }
                }).start();
                if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, e);
                }
            }
        };
    }

    private CrashHandler() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(@NonNull File crashDir) {
        dir = crashDir.getAbsolutePath();
        if (!dir.endsWith(FILE_SEP)) {
            dir += FILE_SEP;
        }
        dir += "Crash" + FILE_SEP;
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static String getFileTime() {
        return DateTimeFormat2.format(new Date(System.currentTimeMillis()));
    }
}
