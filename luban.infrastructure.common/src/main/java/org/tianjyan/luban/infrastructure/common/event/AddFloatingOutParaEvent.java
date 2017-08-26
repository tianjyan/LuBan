package org.tianjyan.luban.infrastructure.common.event;

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
