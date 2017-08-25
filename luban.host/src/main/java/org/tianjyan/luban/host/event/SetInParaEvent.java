package org.tianjyan.luban.host.event;


import org.tianjyan.luban.aidl.InPara;

public class SetInParaEvent {
    private InPara inPara;
    private String value;

    public SetInParaEvent(InPara inPara) {
        this.inPara = inPara;
        this.value = value;
    }

    public InPara getInPara() {
        return inPara;
    }
}
