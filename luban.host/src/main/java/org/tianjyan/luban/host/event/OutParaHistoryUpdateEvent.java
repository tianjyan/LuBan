package org.tianjyan.luban.host.event;

import org.tianjyan.luban.aidl.OutPara;

public class OutParaHistoryUpdateEvent {
    private OutPara outPara;
    private String value;

    public OutParaHistoryUpdateEvent(OutPara outPara, String value) {
        this.outPara = outPara;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public OutPara getOutPara() {
        return outPara;
    }
}
