package org.tianjyan.luban.host.plugin.log.bridge;

import android.content.Context;

import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.host.plugin.log.adapter.LogDataAdapter;
import org.tianjyan.luban.host.plugin.log.model.LogEntry;
import org.tianjyan.luban.host.plugin.log.model.RemoveRangeArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UILogBridge {
    private ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private RemoveRangeArrayList<LogEntry> logEntries = new RemoveRangeArrayList<>();
    private List<String> tags = new ArrayList<>();
    private List<String> levels;
    private LogDataAdapter logDataAdapter;

    public LogDataAdapter getLogDataAdapter(Context context) {
        if (logDataAdapter == null) {
            logDataAdapter = new LogDataAdapter(context, logEntries, lock);
        }
        return logDataAdapter;
    }

    public void NewLogs(List<LogEntry> logs) {
        Observable.just(logs)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(val-> {
                    lock.writeLock().lock();
                    logEntries.addAll(val);
                    if (logEntries.size() > Config.MAX_LOG_SUPPORT) {
                        logEntries.remove(0, logEntries.size() - Config.MAX_LOG_SUPPORT);
                    }
                    for (LogEntry logEntry : val) {
                        if (!tags.contains(logEntry.getTag())) {
                            tags.add(logEntry.getTag());
                        }
                    }
                    lock.writeLock().unlock();
                    if (logDataAdapter != null) {
                        logDataAdapter.onNewEntries(val);
                    }

                });
    }

    public List<String> getTags() {
        List<String> allTags = new ArrayList<>();
        allTags.add(Config.TAG);
        allTags.addAll(tags);
        return allTags;
    }

    public List<String> getLevels() {
        if (levels == null) {
            levels = new ArrayList<>();
            levels.add(Config.VERBOSE);
            levels.add(Config.DEBUG);
            levels.add(Config.INFO);
            levels.add(Config.WARNING);
            levels.add(Config.ERROR);
        }
        return levels;
    }

    public void setTag(String tag) {
        logDataAdapter.setTag(tag);
    }

    public void setLevel(String strlevel) {
        int level = Config.LOG_VERBOSE;
        switch (strlevel) {
            case Config.DEBUG:
                level = Config.LOG_DEBUG;
                break;
            case Config.INFO:
                level = Config.LOG_INFO;
                break;
            case Config.WARNING:
                level = Config.LOG_WARNING;
                break;
            case Config.ERROR:
                level = Config.LOG_ERROR;
                break;
            default:
                break;
        }
        logDataAdapter.setLevel(level);
    }

    public void setMsg(String msg) {
        logDataAdapter.setMsg(msg);
    }
}
