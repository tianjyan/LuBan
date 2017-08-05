package org.tianjyan.luban.model;

import org.tianjyan.luban.utils.DateTimeUtils;

public class ParaHistory {
    private long time;
    private String displayTime;
    private String value;

    public ParaHistory(long time, String value) {
        this.time = time;
        this.value = value;
        this.displayTime = DateTimeUtils.getSystemTime(time);
    }

    public long getTime() {
        return time;
    }

    public String getValue() {
        return value;
    }

    public String getDisplayTime() {
        return displayTime;
    }
}
