package org.tianjyan.luban.manager;

import org.greenrobot.eventbus.EventBus;
import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.event.LogEvent;
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
                   if (tempList.size() > 0) {
                       EventBus.getDefault().post(new LogEvent(tempList));
                   }
               }
           } catch (InterruptedException e) {
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
}
