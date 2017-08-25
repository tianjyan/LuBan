package org.tianjyan.luban.host.event;


import org.tianjyan.luban.host.model.LogEntry;

import java.util.List;

public class LogEvent {
    private List<LogEntry>  entries;

    public LogEvent(List<LogEntry> entries) {
        this.entries = entries;
    }

    public List<LogEntry> getEntries() {
        return entries;
    }
}
