package org.tianjyan.luban.event;

import org.tianjyan.luban.aidl.OutPara;

public class SetOutParaEvent {
    private OutPara outPara;

    public SetOutParaEvent(OutPara outPara) {
        this.outPara = outPara;
    }

    public OutPara getOutPara() {
        return outPara;
    }
}
