package org.tianjyan.luban.plugin.op.model;

import org.tianjyan.luban.infrastructure.common.utils.Utils;

public class ParaHistory {
    private long time;
    private String displayTime;
    private String value;

    public ParaHistory(long time, String value) {
        this.time = time;
        this.value = value;
        this.displayTime = Utils.getDisplayTime(time);
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
