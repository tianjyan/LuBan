package org.tianjyan.luban.host.event;

import org.tianjyan.luban.aidl.OutPara;

public class AddFloatingOutParaEvent {
    private OutPara outPara;

    public AddFloatingOutParaEvent(OutPara outPara) {
        this.outPara = outPara;
    }

    public OutPara getOutPara() {
        return outPara;
    }
}
