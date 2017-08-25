package org.tianjyan.luban.host.model;

import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.host.utils.Utils;

public class LogEntry {
    private long pid;
    private int level;
    private String tag;
    private String msg;
    private String displayMsg;

    public LogEntry(long pid, int level, String tag, String msg) {
        this.pid = pid;
        this.level = level;
        this.tag = tag;
        this.msg = msg;

        String sLevel = getLevel(level);
        String time = Utils.getDisplayTime();
        StringBuffer sb = new StringBuffer();
        sb.append(time);
        sb.append(": ");
        sb.append(sLevel);
        sb.append("/");
        sb.append(tag);
        sb.append("(");
        sb.append(pid);
        sb.append("): ");
        sb.append(msg);

        displayMsg = sb.toString();
    }

    public String getDisplayMsg() {
        return displayMsg;
    }

    private String getLevel(int level) {
        String sLevel = "V";
        switch (level) {
            case Config.LOG_DEBUG:
                sLevel = "D";
                break;
            case Config.LOG_INFO:
                sLevel = "I";
                break;
            case Config.LOG_WARNING:
                sLevel = "W";
                break;
            case Config.LOG_ERROR:
                sLevel = "E";
                break;
            default:
                break;
        }
        return sLevel;
    }

    public int getLevel() {
        return level;
    }

    public String getTag() {
        return tag;
    }

    public String getMsg() {
        return msg;
    }
}
