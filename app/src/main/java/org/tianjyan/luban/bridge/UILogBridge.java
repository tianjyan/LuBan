package org.tianjyan.luban.bridge;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.tianjyan.luban.activity.LogDataAdapter;
import org.tianjyan.luban.event.LogEvent;
import org.tianjyan.luban.model.LogEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UILogBridge {
    private static UILogBridge INSTANCE = new UILogBridge();
    public static UILogBridge getInstance() {
        return INSTANCE;
    }

    private ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private List<LogEntry> logEntries = Collections.synchronizedList(new ArrayList<>());
    private LogDataAdapter logDataAdapter;

    public UILogBridge() {
        EventBus.getDefault().register(this);
    }

    public LogDataAdapter getLogDataAdapter(Context context) {
        if (logDataAdapter == null) {
            logDataAdapter = new LogDataAdapter(context, logEntries, lock);
        }
        return logDataAdapter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogEvent(LogEvent event) {
        lock.writeLock().lock();
        logEntries.addAll(event.getEntries());
        lock.writeLock().unlock();
        // TODO: filter
        logDataAdapter.notifyDataSetChanged();
    }
}
