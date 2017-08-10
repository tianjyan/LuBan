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

public class UILogBridge {
    private static UILogBridge INSTANCE = new UILogBridge();
    public static UILogBridge getInstance() {
        return INSTANCE;
    }

    private List<LogEntry> logEntries = Collections.synchronizedList(new ArrayList<>());
    private LogDataAdapter logDataAdapter;

    public UILogBridge() {
        EventBus.getDefault().register(this);
    }

    public LogDataAdapter getLogDataAdapter(Context context) {
        if (logDataAdapter == null) {
            logDataAdapter = new LogDataAdapter(context, logEntries);
        }
        return logDataAdapter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogEvent(LogEvent event) {
        for (LogEntry entry : event.getEntries()) {
            logEntries.add(0, entry);
        }
        logDataAdapter.notifyDataSetChanged();
    }
}
