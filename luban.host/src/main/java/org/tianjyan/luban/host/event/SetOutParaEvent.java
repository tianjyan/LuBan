package org.tianjyan.luban.host.event;

import org.tianjyan.luban.aidl.OutPara;

public class SetOutParaEvent {
    private OutPara outPara;
    private String value;

    public SetOutParaEvent(OutPara outPara, String value) {
        this.outPara = outPara;
        this.value = value;
    }

    public OutPara getOutPara() {
        return outPara;
    }

    public String getValue() {
        return value;
    }
}
