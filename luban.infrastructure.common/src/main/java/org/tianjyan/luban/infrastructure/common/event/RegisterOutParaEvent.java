package org.tianjyan.luban.infrastructure.common.event;

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
