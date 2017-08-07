package org.tianjyan.luban.event;

import org.tianjyan.luban.aidl.OutPara;

public class RemoveFloatingOutParaEvent {
    private OutPara outPara;

    public RemoveFloatingOutParaEvent(OutPara outPara) {
        this.outPara = outPara;
    }

    public OutPara getOutPara() {
        return outPara;
    }
}
