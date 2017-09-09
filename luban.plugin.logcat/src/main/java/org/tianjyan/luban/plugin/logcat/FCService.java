package org.tianjyan.luban.plugin.logcat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.tianjyan.luban.plugin.common.Utils;

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
    }

    private String readLine(BufferedReader reader) {
        try {
            return  reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
