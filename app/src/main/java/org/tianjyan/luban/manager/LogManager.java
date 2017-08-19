package org.tianjyan.luban.manager;

import org.greenrobot.eventbus.EventBus;
import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.event.LogEvent;
import org.tianjyan.luban.model.LogEntry;
import org.tianjyan.luban.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LogManager {
    public final static String LogFolder = "Log";
    public final static String LogFileName = "luban_";
    public final static String LogExt = ".log";
    public final static long WriteBuffer = 8192;
    private Thread thread;
    private boolean flag;
    private LinkedBlockingDeque<LogEntry> queue = new LinkedBlockingDeque<>(1000);

    private static LogManager INSTANCE = new LogManager();
    public static LogManager getInstance() {
        return INSTANCE;
    }

    public LogManager() {
        thread = new Thread(() -> {
           try {
               FileWriter writer = new FileWriter(getCurrentLogFile());
               int currentFileSize = 0;
               while (flag) {
                   Thread.sleep(1000);
                   List<LogEntry> tempList = new ArrayList<>();
                   queue.drainTo(tempList);
                   if (tempList.size() > 0) {
                       EventBus.getDefault().post(new LogEvent(tempList));
                   }
                   currentFileSize += saveLogs(writer, tempList);
                   if (currentFileSize > Config.MAX_LOG_FILE_SIZE) {
                       writer.close();
                       currentFileSize = 0;
                       writer = new FileWriter(getCurrentLogFile());
                   }
               }
           } catch (InterruptedException e) {
               e.printStackTrace();
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
        });
        thread.start();
        flag = true;
    }

    public void log(long tid, int level, String tag, String msg) {
        if (level < Config.LOG_DEBUG || level > Config.LOG_ERROR) return;
        queue.offer(new LogEntry(tid, level, tag, msg));
    }

    private File getCurrentLogFile() throws IOException {
        getLogFolder();
        rotateLogFiles();
        File currentLogFile = getLogFile(0);
        if (!currentLogFile.exists()) {
            currentLogFile.createNewFile();
        }
        return currentLogFile;
    }

    private void rotateLogFiles() {
        int startIndex = Config.MAX_LOG_FILES - 1;
        for (int i = startIndex; i > 0; i--) {
            File fromFile = getLogFile(i - 1);
            File toFile = getLogFile(i);
            if (toFile.exists() && i == startIndex) {
                toFile.delete();
            }
            if (fromFile.exists()) {
                fromFile.renameTo(toFile);
            }
        }
    }

    private File getLogFile(int index) {
        String filePath = Utils.getCacheDir() + Utils.FILE_SEP + LogFolder + Utils.FILE_SEP + LogFileName + index + LogExt;
        return new File(filePath);
    }

    private File getLogFolder() {
        String folderPath = Utils.getCacheDir() + Utils.FILE_SEP + LogFolder;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    private int saveLogs(FileWriter writer, List<LogEntry> entryList) throws IOException {
        int length = 0;
        StringBuffer sb = new StringBuffer();
        for (LogEntry entry : entryList) {
            if (sb.length() > WriteBuffer) {
                writer.write(sb.toString());
                sb.setLength(0);
            }
            sb.append(entry.getDisplayMsg());
            sb.append(Utils.LINE_SEP);
            length += entry.getDisplayMsg().length();
        }

        if (sb.length() > 0) {
            writer.write(sb.toString());
        }
        return length;
    }
}
