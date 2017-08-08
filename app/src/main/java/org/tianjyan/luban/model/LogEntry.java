package org.tianjyan.luban.model;

public class LogEntry {
    private long tid;
    private int level;
    private String tag;
    private String msg;
    private String displayTime;

    public LogEntry(long tid, int level, String tag, String msg) {
        this.tid = tid;
        this.level = level;
        this.tag = tag;
        this.msg = msg;
    }
}
