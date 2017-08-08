package org.tianjyan.luban.manager;

import org.tianjyan.luban.model.LogEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LogManager {
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
               while (flag) {
                   Thread.sleep(1000);
                   List<LogEntry> tempList = new ArrayList<>();
                   queue.drainTo(tempList);
                   // TODO: 发送到UI
               }
           } catch (InterruptedException e) {
               e.printStackTrace();
           };
        });
        thread.start();
    }

    public void log(long tid, int level, String tag, String msg) {
        queue.offer(new LogEntry(tid, level, tag, msg));
    }
}
