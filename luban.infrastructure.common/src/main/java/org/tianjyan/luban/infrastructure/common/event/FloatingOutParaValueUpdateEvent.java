package org.tianjyan.luban.infrastructure.common.event;

import org.tianjyan.luban.aidl.OutPara;

public class FloatingOutParaValueUpdateEvent {
    private OutPara outPara;
    private String value;

    public FloatingOutParaValueUpdateEvent(OutPara outPara, String value) {
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
