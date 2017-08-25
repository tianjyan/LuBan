package org.tianjyan.luban.host.event;

import org.tianjyan.luban.aidl.OutPara;

public class RegisterOutParaEvent {
    private OutPara outPara;

    public RegisterOutParaEvent(OutPara outPara) {
        this.outPara = outPara;
    }

    public OutPara getOutPara() {
        return outPara;
    }
}
